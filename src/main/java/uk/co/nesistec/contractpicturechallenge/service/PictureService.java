package uk.co.nesistec.contractpicturechallenge.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;

public interface PictureService {
    Picture findOneImage(String filename);
    List<Picture> retrieveAllPictureDetails();
    Picture createPicture(MultipartFile picture) throws IOException;
    void deletePicture(String filename) throws IOException;
}