package my.pr.service;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import my.pr.model.Order;
import my.pr.repository.OrderRepository;
import my.pr.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private Order m1 = new Order();
    private Order m2 = new Order();
    private Order m3 = new Order();

    @Before
    public void setUp() throws Exception {
        m1.setTypeOrder("o1");
        m2.setTypeOrder("o2");
        m3.setTypeOrder("o3");
        m1.setAddress("o1");
        m2.setAddress("o2");
        m3.setAddress("o3");
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
    public void createOrderTest() throws MessagingException, IOException {
        orderService.createOrder(m1);
        assertEquals(1, orderService.getAll().size());
        assertEquals(m1.getTypeOrder(), orderService.getAll().get(0).getTypeOrder());
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
    public void getAllOrdersTest() throws MessagingException, IOException {
        orderService.createOrder(m1);
        orderService.createOrder(m2);
        orderService.createOrder(m3);
        List<Order> orders = new ArrayList<>();
        orders.add(m1);
        orders.add(m2);
        orders.add(m3);
        assertEquals(3, orderService.getAll().size());
        assertEquals(orders, orderService.getAll());
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void deleteOrderTest() throws MessagingException, IOException {
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
    public void deleteOrderFailedTest() throws MessagingException, IOException {
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
    public void getOrderByEmailTest() throws MessagingException, IOException {
        Order orderAnotherUser = new Order();
        orderAnotherUser.setTypeOrder("o4");
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
    public void updateOrderTest() throws MessagingException, IOException {
        orderService.createOrder(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.update(uuid, m2);
        assertEquals("o2", orderService.getAll().get(0).getTypeOrder());
        assertEquals("o2", orderService.getAll().get(0).getAddress());

    }

    @Test(expected = EntityNotFoundException.class)
    @WithMockKeycloakAuth(
            authorities = {"ADMIN"},
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void updateOrderFailedTest() throws MessagingException, IOException {
        orderService.createOrder(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.update(uuid, m2);
        assertEquals("o2", orderService.getAll().get(0).getTypeOrder());
        orderService.delete(uuid);
        orderService.update(uuid,m1);
    }

}
