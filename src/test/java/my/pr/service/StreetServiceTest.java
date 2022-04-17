package my.pr.service;

import my.pr.model.City;
import my.pr.model.Street;
import my.pr.repository.CityRepository;
import my.pr.repository.StreetRepository;
import my.pr.status.CityAndStreetStatus;
import org.junit.After;
import org.junit.Before;
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
public class StreetServiceTest {

    @Autowired
    private StreetService streetService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StreetRepository streetRepository;


    private City city1 = new City("c1");
    private Street street1 = new Street("street1");
    private Street street2 = new Street("street2");
    private Street street3 = new Street("street3");

    @Before
    public void setUp() throws Exception {
        cityRepository.save(city1);
        street1.setCity(city1);
        street2.setCity(city1);
        street3.setCity(city1);
    }

    @After
    public void tearDown() {
        streetRepository.deleteAll();
        cityRepository.delete(city1);
    }

    @Test
    public void addStreetTest() {
        streetService.addStreet(street1);
        streetService.addStreet(street2);
        streetService.addStreet(street3);
        assertEquals(3, streetService.getAllStreets().size());
    }

    @Test
    public void getAllStreetsTest() {
        assertEquals(0, streetService.getAllStreets().size());
        streetService.addStreet(street1);
        streetService.addStreet(street2);
        streetService.addStreet(street3);
        assertEquals(3, streetService.getAllStreets().size());
    }

    @Test
    public void getStreetByIdTest() {
        streetService.addStreet(street1);
        long id = streetService.getAllStreets().get(0).getId();
        assertEquals(street1, streetService.getStreetById(id));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getStreetByIdFailedTest() {
        streetService.addStreet(street1);
        long id = streetService.getAllStreets().get(0).getId();
        streetService.deleteStreet(id);
        assertEquals(street1, streetService.getStreetById(id));
    }

    @Test
    public void getActiveStreetTest() {
        street2.setStreetStatus(CityAndStreetStatus.INACTIVE);
        streetService.addStreet(street1);
        streetService.addStreet(street2);
        assertEquals(1, streetService.getActiveStreets().size());
    }

    @Test
    public void getInactiveStreetTest() {
        street2.setStreetStatus(CityAndStreetStatus.INACTIVE);
        streetService.addStreet(street1);
        streetService.addStreet(street2);
        assertEquals(1, streetService.getInactiveStreets().size());
    }

    @Test
    public void deleteStreetTest() {
        streetService.addStreet(street1);
        streetService.addStreet(street2);
        long id = streetService.getAllStreets().get(0).getId();
        streetService.deleteStreet(id);
        assertEquals(1, streetService.getAllStreets().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteStreetFailedTest() {
        streetService.addStreet(street1);
        streetService.addStreet(street2);
        long id = streetService.getAllStreets().get(0).getId();
        streetService.deleteStreet(id);
        streetService.deleteStreet(id);
        assertEquals(1, streetService.getInactiveStreets().size());
    }

    @Test
    public void updateStreetTest() {
        streetService.addStreet(street1);
        long id = streetService.getAllStreets().get(0).getId();
        streetService.updateStreet(id, street2);
        assertEquals(street2.getName(), streetService.getAllStreets().get(0).getName());

    }

    @Test(expected = EntityNotFoundException.class)
    public void updateStreetFailedTest() {
        streetService.addStreet(street1);
        long id = streetService.getAllStreets().get(0).getId();
        streetService.updateStreet(id, street2);
        streetService.deleteStreet(id);
        streetService.updateStreet(id, street1);
    }
}

