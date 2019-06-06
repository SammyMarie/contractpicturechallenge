package uk.co.nesistec.contractpicturechallenge.api;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.CREATED;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;
import uk.co.nesistec.contractpicturechallenge.service.PictureService;

@RestController
@RequestMapping("/api")
public class PictureController {

    private final PictureService picService;
    private static final String BASE_PATH = "/pictures";
    private static final String FILENAME = "{filename:.+}";

    @Autowired
    public PictureController(PictureService picService) {
        this.picService = picService;
    }

    @PostMapping(value = BASE_PATH)
    public ResponseEntity<?> createFile(@RequestParam("picture") MultipartFile picture) throws IOException {
        Picture pic = picService.createPicture(picture);

        return ofNullable(pic)
                .map(r -> ResponseEntity.status(CREATED).body("Successfully stored => " + r.getFilename()))
                .orElse(ResponseEntity.badRequest().body("Could not store file " + picture.getOriginalFilename() + ". Please try again!"));

    }

    @GetMapping(value = BASE_PATH + "/" + FILENAME + "/raw")
    public ResponseEntity<?> retrievePicture(@PathVariable String filename) {

        Picture file = ofNullable(picService.findOneImage(filename)).orElseGet(() -> Picture.builder().build());

        if (StringUtils.isNotBlank(file.getFormat())) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getFormat()))
                                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                                 .body("File Uploaded " + file.getUploadDate());
        } else {
            return ResponseEntity.badRequest().body("Couldn't find => " + filename);
        }
    }

    @GetMapping(value = BASE_PATH + "/" + FILENAME)
    public ResponseEntity<Picture> retrievePictureDetail(@PathVariable String filename) {
        Picture file = picService.findOneImage(filename);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(file);
    }

    @GetMapping(value = BASE_PATH)
    public ResponseEntity<List<Picture>> retrievePictureDetails() {
        final List<Picture> pictures = picService.retrieveAllPictureDetails();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(pictures);
    }

    @DeleteMapping(value = BASE_PATH + "/" + FILENAME)
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) throws IOException {
        picService.deletePicture(filename);
        return ResponseEntity.noContent().build();
    }
}
