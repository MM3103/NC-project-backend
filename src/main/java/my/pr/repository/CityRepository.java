package my.pr.repository;

import my.pr.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository  extends JpaRepository<City,Long> {

    @Query(value = "select * from cities where name = :name ", nativeQuery = true)
    City findByName(@Param("name") String name);

}
