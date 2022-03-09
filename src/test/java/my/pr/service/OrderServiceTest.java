package my.pr.service;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import my.pr.model.Order;
import my.pr.status.Status;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
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

    private Order m1 = new Order("o1", "o1");
    private Order m2 = new Order("o2", "o2");
    private Order m3 = new Order("o3", "o3");

    @After
    public void cleanOrderService(){
        orderService.cleanRepository();
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void addOrderTestTrue() throws MessagingException {
        orderService.add(m1);
        List<Order> orders = new ArrayList<>();
        orders.add(m1);
        assertEquals(orders.size(), orderService.getAll().size());
        assertEquals(orders.get(0).getTypeOrder(), orderService.getAll().get(0).getTypeOrder());
        assertEquals(orders.get(0).getAddress(), orderService.getAll().get(0).getAddress());

    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void addOrderTestFalse() throws MessagingException {
        orderService.add(m1);
        List<Order> orders = new ArrayList<>();
        orders.add(m1);
        orders.add(m2);
        assertEquals(orders.size(), orderService.getAll().size());
        assertEquals(orders.get(0).getTypeOrder(), orderService.getAll().get(1).getTypeOrder());
        assertEquals(orders.get(0).getAddress(), orderService.getAll().get(1).getAddress());
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void getAllTestTrue() throws MessagingException {
        orderService.add(m1);
        orderService.add(m2);
        orderService.add(m3);
        List<Order> orders = new ArrayList<>();
        orders.add(m1);
        orders.add(m2);
        orders.add(m3);
        assertEquals(orders.size(), orderService.getAll().size());
        assertEquals(orders.toString(), orderService.getAll().toString());
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void getAllTestFalse() throws MessagingException {
        orderService.add(m1);
        orderService.add(m2);
        orderService.add(m3);
        List<Order> orders = new ArrayList<>();
        orders.add(m1);
        orders.add(m2);
        assertEquals(orders.size(), orderService.getAll().size());
        assertEquals(orders.toString(), orderService.getAll().toString());
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void deleteTestTrue() throws MessagingException {
        orderService.add(m1);
        orderService.add(m2);
        orderService.add(m3);
        assertEquals(orderService.getAll().size(),3);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.delete(uuid);
        assertEquals(orderService.getAll().size(),2);
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void deleteTestFalse() throws MessagingException {
        orderService.add(m1);
        orderService.add(m2);
        orderService.add(m3);
        assertEquals(orderService.getAll().size(),3);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.delete(uuid);
        assertEquals(orderService.getAll().size(),3);
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void getByEmailTestTrue() throws MessagingException {
        orderService.add(m1);
        orderService.add(m2);
        orderService.add(m3);
        List<Order> orders = new ArrayList<>();
        orders.add(m1);
        orders.add(m2);
        orders.add(m3);
        assertEquals(orders.size(), orderService.getByEmail().size());
        assertEquals(orders.toString(), orderService.getByEmail().toString());
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void getByEmailTestFalse() throws MessagingException {
        orderService.add(m1);
        orderService.add(m2);
        orderService.add(m3);
        List<Order> orders = new ArrayList<>();
        orders.add(m1);
        orders.add(m2);
        assertEquals(orders.size(), orderService.getByEmail().size());
        assertEquals(orders.toString(), orderService.getByEmail().toString());
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void updateOrderTestTrue() throws MessagingException{
        orderService.add(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.update(uuid,m2);
        assertEquals(orderService.getAll().get(0).getTypeOrder(),"o2");
        assertEquals(orderService.getAll().get(0).getAddress(),"o2");

    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void updateOrderTestFalse() throws MessagingException{
        orderService.add(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        orderService.update(uuid,m2);
        assertEquals(orderService.getAll().get(0).getTypeOrder(),"o1");
        assertEquals(orderService.getAll().get(0).getAddress(),"o1");
    }

    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void acceptOrderTestTrue() throws MessagingException{
        orderService.add(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        assertEquals(orderService.getAll().get(0).getOrderStatus(), Status.WAITING);
        orderService.acceptedOrder(uuid);
        assertEquals(orderService.getAll().get(0).getOrderStatus(), Status.ACCEPTED);
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void acceptOrderTestFalse() throws MessagingException{
        orderService.add(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        assertEquals(orderService.getAll().get(0).getOrderStatus(), Status.WAITING);
        orderService.acceptedOrder(uuid);
        assertEquals(orderService.getAll().get(0).getOrderStatus(), Status.WAITING);
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void rejectOrderTestTrue() throws MessagingException{
        orderService.add(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        assertEquals(orderService.getAll().get(0).getOrderStatus(), Status.WAITING);
        orderService.unacceptedOrder(uuid);
        assertEquals(orderService.getAll().get(0).getOrderStatus(), Status.REJECTED);
    }
    @Test
    @WithMockKeycloakAuth(
            authorities = { "ADMIN" },
            claims = @OpenIdClaims(
                    email = "adminAdmin@yandex.ru",
                    familyName = "admin",
                    givenName = "admin"
            ))
    public void rejectOrderTestFalse() throws MessagingException{
        orderService.add(m1);
        UUID uuid = orderService.getAll().get(0).getId();
        assertEquals(orderService.getAll().get(0).getOrderStatus(), Status.WAITING);
        orderService.unacceptedOrder(uuid);
        assertEquals(orderService.getAll().get(0).getOrderStatus(), Status.WAITING);
    }

}
