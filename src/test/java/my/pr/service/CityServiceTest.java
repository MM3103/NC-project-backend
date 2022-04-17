package my.pr.service;

import my.pr.model.City;
import my.pr.repository.CityRepository;
import my.pr.status.CityAndStreetStatus;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles({"default", "dev"})
public class CityServiceTest {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;


    private City city1 = new City("c1");
    private City city2 = new City("c2");
    private City city3 = new City("c3");

    @After
    public void tearDown() {
        cityRepository.deleteAll();
    }

    @Test
    public void addCityTest() {
        cityService.addCity(city1);
        cityService.addCity(city2);
        cityService.addCity(city3);
        assertEquals(3, cityService.getAllCities().size());
    }

    @Test
    public void getAllCitiesTest() {
        assertEquals(0, cityService.getAllCities().size());
        cityService.addCity(city1);
        cityService.addCity(city2);
        cityService.addCity(city3);
        assertEquals(3, cityService.getAllCities().size());
    }

    @Test
    public void getCityByIdTest() {
        cityService.addCity(city1);
        long id = cityService.getAllCities().get(0).getId();
        assertEquals(city1, cityService.getCityById(id));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getCityByIdFailedTest() {
        cityService.addCity(city1);
        long id = cityService.getAllCities().get(0).getId();
        cityService.deleteCity(id);
        assertEquals(city1, cityService.getCityById(id));
    }

    @Test
    public void getActiveCityTest() {
        city2.setCityStatus(CityAndStreetStatus.INACTIVE);
        cityService.addCity(city1);
        cityService.addCity(city2);
        assertEquals(1, cityService.getActiveCities().size());
    }

    @Test
    public void getInactiveCityTest() {
        city2.setCityStatus(CityAndStreetStatus.INACTIVE);
        cityService.addCity(city1);
        cityService.addCity(city2);
        assertEquals(1, cityService.getInactiveCities().size());
    }

    @Test
    public void deleteCityTest() {
        cityService.addCity(city1);
        cityService.addCity(city2);
        long id = cityService.getAllCities().get(0).getId();
        cityService.deleteCity(id);
        assertEquals(1, cityService.getAllCities().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteCityFailedTest() {
        cityService.addCity(city1);
        cityService.addCity(city2);
        long id = cityService.getAllCities().get(0).getId();
        cityService.deleteCity(id);
        cityService.deleteCity(id);
        assertEquals(1, cityService.getInactiveCities().size());
    }

    @Test
    public void updateCityTest() {
        cityService.addCity(city1);
        long id = cityService.getAllCities().get(0).getId();
        cityService.updateCity(id, city2);
        assertEquals(city2.getName(), cityService.getAllCities().get(0).getName());

    }

    @Test(expected = EntityNotFoundException.class)
    public void updateCityFailedTest() {
        cityService.addCity(city1);
        long id = cityService.getAllCities().get(0).getId();
        cityService.updateCity(id, city2);
        cityService.deleteCity(id);
        cityService.updateCity(id, city1);
    }
}
