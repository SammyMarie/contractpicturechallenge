package uk.co.nesistec.contractpicturechallenge.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.co.nesistec.contractpicturechallenge.data.PictureTestData.setupApiPicture;
import static uk.co.nesistec.contractpicturechallenge.data.PictureTestData.setupBusinessPicture;
import static uk.co.nesistec.contractpicturechallenge.mapper.PictureMapper.toApi;
import static uk.co.nesistec.contractpicturechallenge.mapper.PictureMapper.toBusiness;

import org.junit.jupiter.api.Test;
import uk.co.nesistec.contractpicturechallenge.domain.api.Picture;
import uk.co.nesistec.contractpicturechallenge.domain.business.PictureDTO;

class PictureMapperTest{

    @Test
    void convertToApi_shouldSucceed() {
    	Picture pic = toApi(setupBusinessPicture());
    	assertThat(setupBusinessPicture().getId()).isEqualTo(pic.getId());
    	assertThat(setupBusinessPicture().getFilename()).isEqualTo(pic.getFilename());
    	assertThat(setupBusinessPicture().getUploadDate()).isEqualTo(pic.getUploadDate());
    	assertThat(setupBusinessPicture().getFormat()).isEqualTo(pic.getFormat());
    	assertThat(setupBusinessPicture().getCountry()).isEqualTo(pic.getCountry());
    	assertThat(setupBusinessPicture().getCity()).isEqualTo(pic.getCity());
    	assertThat(setupBusinessPicture().getCoordinates()).isEqualTo(pic.getCoordinates());
    }
    
    @Test
    void convertToBusiness_shouldSucceed() {
    	PictureDTO picDTO = toBusiness(setupApiPicture());
    	assertThat(setupApiPicture().getId()).isEqualTo(picDTO.getId());
    	assertThat(setupApiPicture().getFilename()).isEqualTo(picDTO.getFilename());
    	assertThat(setupApiPicture().getUploadDate()).isEqualTo(picDTO.getUploadDate());
    	assertThat(setupApiPicture().getFormat()).isEqualTo(picDTO.getFormat());
    	assertThat(setupApiPicture().getCountry()).isEqualTo(picDTO.getCountry());
    	assertThat(setupApiPicture().getCity()).isEqualTo(picDTO.getCity());
    	assertThat(setupApiPicture().getCoordinates()).isEqualTo(picDTO.getCoordinates());
    }
}
