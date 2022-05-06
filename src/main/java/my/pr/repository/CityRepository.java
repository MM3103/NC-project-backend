package my.pr.repository;

import my.pr.model.City;
import my.pr.status.CityAndStreetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findCitiesByCityStatus(CityAndStreetStatus status);

}
