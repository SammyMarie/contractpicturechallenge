package uk.co.nesistec.contractpicturechallenge.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;
import uk.co.nesistec.contractpicturechallenge.domain.business.PictureDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PictureMapper{

    public static Picture toApi(PictureDTO businessDTO){
        return Picture.builder().id(businessDTO.getId())
        						.filename(businessDTO.getFilename())
        						.uploadDate(businessDTO.getUploadDate())
        						.format(businessDTO.getFormat())
        						.country(businessDTO.getCountry())
        						.city(businessDTO.getCity())
        						.coordinates(businessDTO.getCoordinates())
        						.build();
    }

    public static PictureDTO toBusiness(Picture apiDTO){
        return PictureDTO.builder().id(apiDTO.getId())
        						   .filename(apiDTO.getFilename())
        						   .uploadDate(apiDTO.getUploadDate())
        						   .format(apiDTO.getFormat())
        						   .country(apiDTO.getCountry())
        						   .city(apiDTO.getCity())
        						   .coordinates(apiDTO.getCoordinates())
        						   .build();
    }

    public static Page<Picture> toApiPageable(Page<PictureDTO> businessDTO){
        return businessDTO.map(PictureMapper::toApi);
    }
}
