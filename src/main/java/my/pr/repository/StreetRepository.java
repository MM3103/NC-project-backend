package my.pr.repository;

import my.pr.model.Street;
import my.pr.status.CityAndStreetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {

    List<Street> findStreetsByStreetStatus(CityAndStreetStatus status);

}
