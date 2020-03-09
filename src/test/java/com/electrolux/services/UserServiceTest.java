package com.electrolux.services;

import com.electrolux.ElectroLuxRun;
import com.electrolux.entity.User;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElectroLuxRun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/users";
    }

    static {
        System.setProperty("login", "Jean-Luc Picard");
    }

    @Test
    public void testGetAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl(), HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserByLogin() {
        User user = restTemplate.getForObject(getRootUrl() + "/search/Jean-Luc Picard", User.class);
        assertNotNull(user);
    }

    @Test
    public void testCreateUser() {
        String login = System.getProperty("login");
        final User user = new User();
        user.setLogin(login);
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl(), user, User.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateMessage() {
        int id = 1;
        User user = restTemplate.getForObject(getRootUrl() + "/" + id , User.class);
        user.setLastName("LName");
        restTemplate.put(getRootUrl(), user);
        User updatedUser = restTemplate.getForObject(getRootUrl() + "/" + id, User.class);
        assertNotNull(updatedUser);
    }
}

