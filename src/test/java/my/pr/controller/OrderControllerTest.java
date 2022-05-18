package my.pr.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import my.pr.model.City;
import my.pr.model.Order;
import my.pr.model.Street;
import my.pr.repository.CityRepository;
import my.pr.repository.OrderRepository;
import my.pr.repository.StreetRepository;
import my.pr.service.OrderService;
import my.pr.status.Status;
import my.pr.status.TypeOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "keycloak.auth-server-url=http://localhost:8080/auth",
        "keycloak.realm=my_realm",
        "keycloak.resource=my_client"
})
@AutoConfigureMockMvc
@ActiveProfiles({"default", "dev"})
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private StreetRepository streetRepository;

    @Autowired
    private CityRepository cityRepository;

    private Order m1 = new Order();

    @Before
    public void setUp() throws Exception {
        m1.setTypeOrder(TypeOrder.Connection);
        m1.setSelfInstallation(true);
        City city = new City("c1");
        cityRepository.save(city);
        Street street = new Street("s1", city);
        streetRepository.save(street);
        m1.setCity(city);
        m1.setHouse(1);
        m1.setFlat(1);
        m1.setStreet(street);
        m1.setAddress("o1");
        m1.setEmail("o1");
        m1.setFirstName("o1");
        m1.setLastName("o1");
        m1.setOrderStatus(Status.WAITING);
        repository.save(m1);
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void acceptOrderTest() throws Exception {
        UUID uuid = orderService.getAll().get(0).getId();
        this.mockMvc.perform(get("/order/acceptOrder/" + uuid)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Order accepted")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void acceptOrderFailedTest() throws Exception {
        UUID uuid = orderService.getAll().get(0).getId();
        this.mockMvc.perform(get("/order/acceptOrder/" + uuid))
                .andDo(print()).andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void rejectOrderTest() throws Exception {
        UUID uuid = orderService.getAll().get(0).getId();
        this.mockMvc.perform(get("/order/rejectOrder/" + uuid))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Order rejected")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void rejectOrderFailedTest() throws Exception {
        UUID uuid = orderService.getAll().get(0).getId();
        this.mockMvc.perform(get("/order/rejectOrder/" + uuid))
                .andDo(print()).andExpect(status().is(403));
    }

}
