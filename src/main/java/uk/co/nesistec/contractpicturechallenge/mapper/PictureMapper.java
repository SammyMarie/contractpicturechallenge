package uk.co.nesistec.contractpicturechallenge.mapper;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

    public static List<Picture> toApis(List<PictureDTO> businessDTO){
    	List<Picture> pictures = new ArrayList<>();
    	
    	businessDTO.forEach(p -> pictures.add(toApi(p)));
    	
        return pictures;
    }
}
