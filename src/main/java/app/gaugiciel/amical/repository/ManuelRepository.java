package app.gaugiciel.amical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.gaugiciel.amical.model.Manuel;

@Repository
public interface ManuelRepository extends JpaRepository<Manuel, Long>, JpaSpecificationExecutor<Manuel> {

}
