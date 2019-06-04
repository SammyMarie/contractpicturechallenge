package uk.co.nesistec.contractpicturechallenge.data;

import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;
import uk.co.nesistec.contractpicturechallenge.domain.business.PictureDTO;

import java.time.LocalDate;

public class PictureTestData {

    public static PictureDTO setupBusinessPicture() {
        return PictureDTO.builder()
                         .id(34L)
                         .filename("pumbaa")
                         .uploadDate(LocalDate.ofYearDay(2019, 31))
                         .format("image/gif")
                         .country("USA")
                         .city("California")
                         .coordinates(new byte[0])
                         .build();
    }

    public static Picture setupApiPicture() {
        return Picture.builder()
                      .id(12L)
                      .filename("timon")
                      .uploadDate(LocalDate.now())
                      .format("image/jpeg")
                      .country("UK")
                      .city("London")
                      .coordinates(new byte[0])
                      .build();
    }
}
