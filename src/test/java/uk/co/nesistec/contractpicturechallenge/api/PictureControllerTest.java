package uk.co.nesistec.contractpicturechallenge.api;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.co.nesistec.contractpicturechallenge.data.PictureTestData.setupApiPicture;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;
import uk.co.nesistec.contractpicturechallenge.service.PictureService;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PictureControllerTest{

    @MockBean
    private PictureService picService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PictureController controller;
    
    private MockMultipartFile pictureFile;
    
    @BeforeEach
    void setup() {
    	pictureFile = new MockMultipartFile("picture", "filename.gif", MediaType.IMAGE_GIF_VALUE, "filename.gif".getBytes());
    }
    
    @Test
    void checkContext_notNull() {
    	assertThat(controller).isNotNull();
    }
    
    @Test
    void savePicture_shouldSucceed() throws Exception {
    	when(picService.createPicture(any(MockMultipartFile.class))).thenReturn(setupApiPicture());
    	mockMvc.perform(multipart("/api/pictures")
    			.file(pictureFile)
    			.accept(MediaType.parseMediaType(setupApiPicture().getFormat())))
    		   .andExpect(status().isCreated())
    		   .andExpect(content().string(containsString("Successfully stored => " + setupApiPicture().getFilename())))
    		   .andDo(print());
    }
    
    @Test
    void savePicture_shouldFail() throws Exception {
    	mockMvc.perform(multipart("/api/pictures")
    			.file(pictureFile)
    			.accept(MediaType.parseMediaType(Objects.requireNonNull(pictureFile.getContentType()))))
    		   .andExpect(status().isBadRequest())
    		   .andExpect(content().string(containsString("Could not store file " + pictureFile.getOriginalFilename())))
    		   .andDo(print());
    }
    
    @Test
    void retrievePicture_shouldSucceed() throws Exception {
    	String filename = "timon";
    	when(picService.findOneImage(anyString())).thenReturn(setupApiPicture());
    	mockMvc.perform(get("/api/pictures/" + filename + "/raw"))
    		   .andExpect(status().isOk())
    		   .andDo(print());
    }

    @Test
    void retrievePictureDetail_shouldSucceed() throws Exception {
        String filename = "timon";
        when(picService.findOneImage(anyString())).thenReturn(setupApiPicture());
        mockMvc.perform(get("/api/pictures/" + filename))
               .andExpect(status().isOk())
               .andExpect(content().json("{\"filename\": \"timon\"}"))
               .andDo(print());
    }

    @Test
    void retrievePictureDetails_shouldSucceed() throws Exception {
        List<Picture> pictures = Arrays.asList(setupApiPicture(), setupApiPicture());
        Page<Picture> picturePage = new PageImpl<>(pictures);
        when(picService.retrieveAllPictureDetails(any(Pageable.class))).thenReturn(picturePage);
        mockMvc.perform(get("/api/pictures").param("page", "1")
                                        .param("size", "1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content", hasSize(2)))
               .andExpect(jsonPath(".content[0].filename", hasItem("timon")))
               .andDo(print());
    }

    @Test
    void retrievePictureDetail_shouldFail() throws Exception {
        String filename = "timon";
        when(picService.findOneImage(anyString())).thenReturn(null);
        mockMvc.perform(get("/api/pictures/" + filename))
               .andExpect(content().string(""))
               .andDo(print());
    }
    
    @Test
    void retrievePicture_shouldFail() throws Exception {
    	String filename = "timon";
        when(picService.findOneImage(anyString())).thenReturn(null);
    	mockMvc.perform(get("/api/pictures/" + filename + "/raw"))
    		   .andExpect(status().isBadRequest())
    		   .andExpect(content().string(containsString("Couldn't find => " + filename)))
    		   .andDo(print());
    }

    @Test
    void deletePicture_shouldSucceed() throws Exception {
        String filename = "timon";
        when(picService.findOneImage(anyString())).thenReturn(setupApiPicture());
        mockMvc.perform(delete("/api/pictures/" + filename))
               .andExpect(status().isNoContent())
               .andDo(print());
    }
}
