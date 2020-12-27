package ru.itis.hm3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.hm3.model.Car;
import ru.itis.hm3.model.Driver;
import ru.itis.hm3.model.PetrolTank;
import ru.itis.hm3.model.Wheel;
import ru.itis.hm3.service.ServicesService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicesService service;

    @BeforeEach
    public void setup() {
        Car car = null;
        car = Car.builder()
                .id(1)
                .driver(new Driver(1, null))
                .petrolTank(new PetrolTank(1, 45))
                .w1(new Wheel(1, 100, 2.5))
                .w2(new Wheel(2, 100, 2.5))
                .w3(new Wheel(3, 100, 2.5))
                .w4(new Wheel(4, 100, 2.5))
                .build();
        car.getDriver().setCars(Collections.singleton(car));
        when(service.fillWheels(anyInt())).thenReturn(car);
    }

    @Test
    public void fillCarWheelsTest() throws Exception {
        mockMvc.perform(put("/cars/1/fill-wheels")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("fill_car_wheels"));
    }
}
