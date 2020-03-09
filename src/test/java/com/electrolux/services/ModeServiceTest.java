package com.electrolux.services;

import com.electrolux.ElectroLuxRun;
import com.electrolux.entity.Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.utils.Converter;
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
public class ModeServiceTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/mode";
    }

    static {
        System.setProperty("nameMode", "Name");
        System.setProperty("spidSpeed", "500");
        System.setProperty("washingTemperature", "50");
        System.setProperty("washingTimer", "30");
        System.setProperty("saveWater", "low");
    }

    @Test
    public void testGetAllModes() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl(), HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetWMByLogin() {
        WorkMode mode= restTemplate.getForObject(getRootUrl() + "/search/Name", WorkMode.class);
        assertNotNull(mode);
    }

    @Test
    public void testCreateModel() {
        final WorkMode mode = new WorkMode();
        String nameMode = System.getProperty("nameMode");
        Integer spidSpeed = Integer.valueOf(Objects.requireNonNull(System.getProperty("spidSpeed")));
        Integer washingTemperature = Integer.valueOf(Objects.requireNonNull(System.getProperty("washingTemperature")));
        Integer washingTimer = Integer.valueOf(Objects.requireNonNull(System.getProperty("washingTimer")));
        String saveWater = System.getProperty("saveWater");
        WorkMode workMode = new WorkMode();
        workMode.setNameMode(nameMode);
        workMode.setWashingTemperature(washingTemperature);
        workMode.setSpidSpeed(spidSpeed);
        workMode.setWashingTimer(washingTimer);
        workMode.setSaveWater(saveWater);
        ResponseEntity<WorkMode> postResponse = restTemplate.postForEntity(getRootUrl(), mode, WorkMode.class);
        assertNotNull(postResponse);
    }
}

