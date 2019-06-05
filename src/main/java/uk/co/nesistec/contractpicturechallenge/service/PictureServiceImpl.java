package uk.co.nesistec.contractpicturechallenge.service;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static uk.co.nesistec.contractpicturechallenge.mapper.PictureMapper.toApi;
import static uk.co.nesistec.contractpicturechallenge.mapper.PictureMapper.toApis;
import static uk.co.nesistec.contractpicturechallenge.mapper.PictureMapper.toBusiness;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;
import uk.co.nesistec.contractpicturechallenge.domain.business.PictureDTO;
import uk.co.nesistec.contractpicturechallenge.mapper.PictureMapper;
import uk.co.nesistec.contractpicturechallenge.repository.PictureRepository;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository repository;

    @Autowired
    public PictureServiceImpl(PictureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Picture findOneImage(String filename) {
        PictureDTO pictureDTO = ofNullable(repository.findByFilename(filename)).orElseGet(() -> PictureDTO.builder().build());

        return toApi(pictureDTO);
    }

    @Override
    public List<Picture> retrieveAllPictureDetails() {
        return toApis(repository.findAll());
    }

    @Override
    public Picture createPicture(MultipartFile picture) throws IOException {

        Picture pic = null;
        PictureDTO businessPictureDTO = null;
        if (!picture.isEmpty()) {
            String fileName = StringUtils.substringBefore(picture.getOriginalFilename(), ".");
            businessPictureDTO = PictureDTO.builder()
                                           .filename(fileName)
                                           .format(picture.getContentType())
                                           .uploadDate(LocalDate.now())
                                           .coordinates(picture.getBytes())
                                           .build();

        }

        PictureDTO businessPic = ofNullable(businessPictureDTO).map(PictureDTO::getFilename)
                                                               .map(this::findOneImage)
                                                               .map(PictureMapper::toBusiness)
                                                               .orElseGet(() -> PictureDTO.builder().build());

        if (!Arrays.equals(businessPic.getCoordinates(), requireNonNull(businessPictureDTO).getCoordinates())) {
            pic = toApi(repository.save(businessPictureDTO));
        }

        return pic;
    }

    @Override
    public void deletePicture(String filename) {
        final PictureDTO businessPictureDTO = ofNullable(toBusiness(findOneImage(filename))).orElseThrow(() -> new IllegalStateException("Picture not found..!"));
        repository.delete(businessPictureDTO);
    }
}
