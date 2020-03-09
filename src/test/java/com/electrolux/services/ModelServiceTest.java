package com.electrolux.services;

import com.electrolux.ElectroLuxRun;
import com.electrolux.entity.Model;
import com.electrolux.entity.User;
import com.electrolux.utils.Converter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElectroLuxRun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModelServiceTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/model";
    }

    static {
        System.setProperty("modelName", "Name");
        System.setProperty("mainsVoltage", "220");
        System.setProperty("hardnessWater", "50");
        System.setProperty("HexCodeCollor", "123");
        System.setProperty("volume", "5");
        System.setProperty("washingNumber", "0");
        System.setProperty("isDisplay", "true");
        System.setProperty("warrantyExpirationDate", "2023-12-12");
    }

    @Test
    public void testGetAllWM() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl(), HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetWMByLogin() {
        Model model= restTemplate.getForObject(getRootUrl() + "/search/Name", Model.class);
        assertNotNull(model);
    }

    @Test
    public void testCreateModel() {
        final Model model = new Model();
        String modelName = System.getProperty("modelName");
        Integer mainsVoltage = Integer.valueOf(Objects.requireNonNull(System.getProperty("mainsVoltage")));
        Integer hardnessWater = Integer.valueOf(Objects.requireNonNull(System.getProperty("hardnessWater")));
        String HexCodeCollor = System.getProperty("HexCodeCollor");
        Integer volume = Integer.valueOf(Objects.requireNonNull(System.getProperty("volume")));
        Integer washingNumber = Integer.valueOf(Objects.requireNonNull(System.getProperty("washingNumber")));
        Boolean isDisplay = Boolean.valueOf(Objects.requireNonNull(System.getProperty("isDisplay")));
        Date warrantyExpirationDate = Converter.converter(System.getProperty("warrantyExpirationDate"));
        model.setModelName(modelName);
        model.setMainsVoltage(mainsVoltage);
        model.setHardnessWater(hardnessWater);
        model.setHexCodeCollor(HexCodeCollor);
        model.setVolume(volume);
        model.setWashingNumber(washingNumber);
        model.setIsDisplay(isDisplay);
        model.setWarrantyExpirationDate(warrantyExpirationDate);
        ResponseEntity<Model> postResponse = restTemplate.postForEntity(getRootUrl(), model, Model.class);
        assertNotNull(postResponse);
    }
}

