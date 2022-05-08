package my.pr.service;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import my.pr.model.City;
import my.pr.model.Order;
import my.pr.model.Street;
import my.pr.repository.CityRepository;
import my.pr.repository.OrderRepository;
import my.pr.repository.StreetRepository;
import my.pr.status.Status;
import my.pr.status.TypeOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles({"default", "dev"})
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StreetRepository streetRepository;

    private Order m1 = new Order();
    private Order m2 = new Order();
    private Order m3 = new Order();
    private City c1 = new City("c1");
    private Street s1 = new Street("s1", c1);

    @Before
    public void setUp() throws Exception {
        cityRepository.save(c1);
        streetRepository.save(s1);
        m1.setTypeOrder(TypeOrder.Connection);
        m2.setTypeOrder(TypeOrder.Repair);
        m3.setTypeOrder(TypeOrder.Deactivation);
        m1.setCity(c1);
        m2.setCity(c1);
        m3.setCity(c1);
        m1.setStreet(s1);
        m2.setStreet(s1);
        m3.setStreet(s1);
        m1.setHouse(1);
        m2.setHouse(2);
        m3.setHouse(3);
        m1.setFlat(1);
        m2.setFlat(2);
        m3.setFlat(3);
        m1.setSelfInstallation(true);
        m2.setSelfInstallation(true);
        m3.setSelfInstallation(true);


    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void createOrderTest() {
        orderService.createOrder(m1);
        assertEquals(1, orderService.getAll().size());
        assertEquals(m1.getTypeOrder(), orderService.getAll().get(0).getTypeOrder());
        assertEquals(m1.getStreet(), orderService.getAll().get(0).getStreet());
        assertEquals(m1.getCity(), orderService.getAll().get(0).getCity());
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void getAllOrdersTest()  {
        orderService.createOrder(m1);
        orderService.createOrder(m2);
        orderService.createOrder(m3);
        assertEquals(3, orderService.getAll().size());
        assertEquals(m1.getAddress(), orderService.getAll().get(0).getAddress());
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void deleteOrderTest()  {
        orderService.createOrder(m1);
        orderService.createOrder(m2);
        orderService.createOrder(m3);
        assertEquals(3, orderService.getAll().size());
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.delete(uuid);
        assertEquals(2, orderService.getAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void deleteOrderFailedTest()  {
        orderService.createOrder(m1);
        orderService.createOrder(m2);
        orderService.createOrder(m3);
        assertEquals(3, orderService.getAll().size());
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.delete(uuid);
        assertEquals(2, orderService.getAll().size());
        orderService.delete(uuid);
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void getOrderByEmailTest()  {
        Order orderAnotherUser = new Order();
        orderAnotherUser.setTypeOrder(TypeOrder.Connection);
        orderAnotherUser.setCity(c1);
        orderAnotherUser.setStreet(s1);
        orderAnotherUser.setHouse(1);
        orderAnotherUser.setFlat(1);
        orderAnotherUser.setSelfInstallation(true);
        orderAnotherUser.setAddress("o4");
        orderAnotherUser.setEmail("o4");
        orderAnotherUser.setFirstName("o4");
        orderAnotherUser.setLastName("o4");
        orderAnotherUser.setOrderStatus(Status.WAITING);
        orderService.createOrder(m1);
        orderService.createOrder(m2);
        orderService.createOrder(m3);
        repository.save(orderAnotherUser);
        assertEquals(4, orderService.getAll().size());
        assertEquals(3, orderService.getByEmail().size());
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void updateOrderTest()  {
        orderService.createOrder(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.update(uuid, m2);
        assertEquals(TypeOrder.Repair, orderService.getAll().get(0).getTypeOrder());
    }

    @Test(expected = EntityNotFoundException.class)
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void updateOrderFailedTest()  {
        orderService.createOrder(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.update(uuid, m2);
        assertEquals(TypeOrder.Repair, orderService.getAll().get(0).getTypeOrder());
        orderService.delete(uuid);
        orderService.update(uuid, m1);
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void archiveStatusTest()  {
        orderService.createOrder(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.archivedStatus(uuid);
        assertEquals(Status.ARCHIVED, orderService.getAll().get(0).getOrderStatus());
    }

    @Test(expected = EntityNotFoundException.class)
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void archivedStatusFailedTest()  {
        orderService.createOrder(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.delete(uuid);
        orderService.archivedStatus(uuid);
        assertEquals(Status.ARCHIVED, orderService.getAll().get(0).getOrderStatus());
    }

}
