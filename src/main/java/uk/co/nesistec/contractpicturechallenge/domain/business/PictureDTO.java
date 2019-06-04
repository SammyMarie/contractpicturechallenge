package uk.co.nesistec.contractpicturechallenge.domain.business;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "Pictures")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PictureDTO {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1)
    private String filename;

    @NotNull
    private LocalDate uploadDate;

    private String format;
    private String country;
    private String city;

    @Lob
    @NotNull
    private byte[] coordinates;
}
