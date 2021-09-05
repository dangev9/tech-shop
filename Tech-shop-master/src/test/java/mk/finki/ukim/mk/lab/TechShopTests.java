package mk.finki.ukim.mk.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TechShopTests {

    MockMvc mockMvc;

//    @Autowired
//    BalloonService balloonService;
//
//    @Autowired
//    ManufacturerService manufacturerService;
//
//    @Autowired
//    AuthenticationService userService;

//    private static Balloon c1;
//    private static Manufacturer m1;
//    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        //initData();
    }
/*    private void initData() {
        if (!dataInitialized) {
            c1 = balloonService.create("c1", "c1");
            balloonService.save(c1);

            m1 = new Manufacturer("manufacturer");
            manufacturerService.save(m1);

            String user = "user";
            userService.register(user, user, user, user, user);
            dataInitialized = true;
        }
    }*/


    @Test
    void contextLoads() {
    }

    @Test
    public void testGetBalloons() throws Exception {
        MockHttpServletRequestBuilder balloonRequest = MockMvcRequestBuilders.get("/balloons");
        this.mockMvc.perform(balloonRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("balloons"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "listBalloons"))
                .andExpect(MockMvcResultMatchers.view().name("master-template"));

    }

//    @Test
//    public void testDeleteBalloon() throws Exception {
//        Balloon balloon = balloonService.save(new Balloon(c1.getId(), "test", "test", m1, BalloonStatus.BIRTHDAY));
//            MockHttpServletRequestBuilder productDeleteRequest = MockMvcRequestBuilders
//                .delete("/balloons/delete/" + balloon.getId());
//
//        this.mockMvc.perform(productDeleteRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/balloons"));
//    }


}
