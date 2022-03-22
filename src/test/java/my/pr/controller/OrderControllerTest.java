package my.pr.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import my.pr.model.Order;
import my.pr.repository.OrderRepository;
import my.pr.service.OrderService;
import my.pr.status.Status;
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
@SpringBootTest(properties = {"keycloak.auth-server-url=http://localhost:8080/auth", "keycloak.realm=my_realm", "keycloak.resource=my_client"})
@AutoConfigureMockMvc
@ActiveProfiles({"default", "dev"})
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository repository;

    private Order m1 = new Order();

    @Before
    public void setUp() throws Exception {
        m1.setTypeOrder("o1");
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
        this.mockMvc.perform(get("/order/acceptOrder/" + uuid)).andDo(print()).andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void rejectOrderTest() throws Exception {
        UUID uuid = orderService.getAll().get(0).getId();
        this.mockMvc.perform(get("/order/rejectOrder/" + uuid)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Order rejected")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void rejectOrderFailedTest() throws Exception {
        UUID uuid = orderService.getAll().get(0).getId();
        this.mockMvc.perform(get("/order/rejectOrder/" + uuid)).andDo(print()).andExpect(status().is(403));
    }

}
