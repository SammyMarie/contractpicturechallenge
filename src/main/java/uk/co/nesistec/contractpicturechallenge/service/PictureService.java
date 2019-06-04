package uk.co.nesistec.contractpicturechallenge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;

import java.io.IOException;

public interface PictureService {
    Picture findOneImage(String filename);
    Page<Picture> retrieveAllPictureDetails(Pageable pageable);
    Picture createPicture(MultipartFile picture) throws IOException;
    void deletePicture(String filename) throws IOException;
}
