package uk.co.nesistec.contractpicturechallenge.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;
import uk.co.nesistec.contractpicturechallenge.domain.business.PictureDTO;
import uk.co.nesistec.contractpicturechallenge.repository.PictureRepository;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.nesistec.contractpicturechallenge.data.PictureTestData.setupBusinessPicture;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class PictureServiceImplTest {

    @Autowired
    private PictureService pictureService;

    @MockBean
    private PictureRepository repository;

    private MockMultipartFile pictureFile;

    @BeforeEach
    void setup(){
        pictureFile = new MockMultipartFile("data", "filename.gif", MediaType.IMAGE_GIF_VALUE,
                                            "filename.gif".getBytes());
    }

    @Test
    void checkContextLoads() {
        assertThat(pictureService).isNotNull();
    }

    @Test
    void retrievePicture_shouldSucceed() {
        when(repository.findByFilename(anyString())).thenReturn(setupBusinessPicture());
        assertThat(pictureService.findOneImage("pumbaa")).isNotNull().isInstanceOf(Picture.class);
    }

    @Test
    void uploadPicture_shouldSucceed() throws IOException {
        PictureDTO picParts = PictureDTO.builder()
                                        .filename(pictureFile.getOriginalFilename())
                                        .uploadDate(LocalDate.now())
                                        .format(pictureFile.getContentType())
                                        .coordinates(pictureFile.getBytes())
                                        .build();
        when(repository.save(any())).thenReturn(picParts);

        assertThat(pictureService.createPicture(pictureFile)).isEqualToComparingFieldByField(picParts);
    }

    @Test
    void uploadDuplicatePicture_shouldFail() throws IOException {
        PictureDTO firstPicPart = PictureDTO.builder()
                                            .filename(StringUtils.substringBefore(pictureFile.getOriginalFilename(), "."))
                                            .uploadDate(LocalDate.now())
                                            .format(pictureFile.getContentType())
                                            .coordinates(pictureFile.getBytes())
                                            .build();

        PictureDTO secondPicPart = PictureDTO.builder()
                                             .filename(pictureFile.getOriginalFilename())
                                             .uploadDate(LocalDate.now())
                                             .format(pictureFile.getContentType())
                                             .coordinates(pictureFile.getBytes())
                                             .build();
        when(repository.save(any())).thenReturn(firstPicPart);
        pictureService.createPicture(pictureFile);
        when(repository.findByFilename(anyString())).thenReturn(secondPicPart);
        when(repository.save(any())).thenReturn(secondPicPart);

        pictureService.createPicture(pictureFile);

        verify(repository).save(firstPicPart);
    }

    @Test
    void deletePicture_shouldSucceed() throws IOException {
        when(repository.findByFilename(anyString())).thenReturn(setupBusinessPicture());
        pictureService.deletePicture(setupBusinessPicture().getFilename());

        verify(repository).delete(setupBusinessPicture());
    }

    @Test
    void deletePicture_shouldFailWithException() {
        when(repository.findByFilename(anyString())).thenThrow(new IllegalStateException("Picture not found..!"));

        Throwable thrown = catchThrowable(() -> pictureService.deletePicture(setupBusinessPicture().getFilename()));

        verify(repository, never()).delete(setupBusinessPicture());

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasMessage("Picture not found..!");
    }
}
