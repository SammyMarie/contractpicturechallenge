package uk.co.nesistec.contractpicturechallenge.domain.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Picture {
    private Long id;

    @ApiModelProperty(required = true)
    private String filename;
    private LocalDate uploadDate;
    private String format;
    private String country;
    private String city;

    @ApiModelProperty(required = true)
    private byte[] coordinates;
}
