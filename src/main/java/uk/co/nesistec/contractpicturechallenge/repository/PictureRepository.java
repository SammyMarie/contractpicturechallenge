package uk.co.nesistec.contractpicturechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.nesistec.contractpicturechallenge.domain.business.PictureDTO;

@Repository
public interface PictureRepository extends JpaRepository<PictureDTO, Long> {
    PictureDTO findByFilename(String filename);
}