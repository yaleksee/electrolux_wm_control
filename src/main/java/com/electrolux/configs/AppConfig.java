package com.electrolux.configs;

import com.electrolux.entity.Model;
import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.services.UserService;
import com.electrolux.services.WashingMachineService;
import com.electrolux.services.WorkModelService;
import com.electrolux.utils.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Objects;

@Configuration
@PropertySource("classpath:private.properties")
@RequiredArgsConstructor
public class AppConfig {

    private final Environment environment;
    private final UserService userService;
    private final WorkModelService workModelService;
    private final WashingMachineService washingMachineService;

    @PostConstruct
    public void execute() {
        String login = this.environment.getProperty("login");
        User user = new User();
        user.setLogin(login);
        User createdUser = userService.createUser(user);

        String nameMode = this.environment.getProperty("nameMode");
        Integer spidSpeed = Integer.valueOf(Objects.requireNonNull(this.environment.getProperty("spidSpeed")));
        Integer washingTemperature = Integer.valueOf(Objects.requireNonNull(this.environment.getProperty("washingTemperature")));
        Integer washingTimer = Integer.valueOf(Objects.requireNonNull(this.environment.getProperty("washingTimer")));
        String saveWater = this.environment.getProperty("saveWater");
        WorkMode workMode = new WorkMode();
        workMode.setNameMode(nameMode);
        workMode.setWashingTemperature(washingTemperature);
        workMode.setSpidSpeed(spidSpeed);
        workMode.setWashingTimer(washingTimer);
        workMode.setSaveWater(saveWater);
        workModelService.createWorkMode(user.getId(), workMode);

        String modelName = this.environment.getProperty("modelName");
        Integer mainsVoltage = Integer.valueOf(Objects.requireNonNull(this.environment.getProperty("mainsVoltage")));
        Integer hardnessWater = Integer.valueOf(Objects.requireNonNull(this.environment.getProperty("hardnessWater")));
        String HexCodeCollor = this.environment.getProperty("HexCodeCollor");
        Integer volume = Integer.valueOf(Objects.requireNonNull(this.environment.getProperty("volume")));
        Integer washingNumber = Integer.valueOf(Objects.requireNonNull(this.environment.getProperty("washingNumber")));
        Boolean isDisplay = Boolean.valueOf(Objects.requireNonNull(this.environment.getProperty("isDisplay")));
        Date warrantyExpirationDate = Converter.converter(this.environment.getProperty("warrantyExpirationDate"));
        Model model = new Model();
        model.setModelName(modelName);
        model.setMainsVoltage(mainsVoltage);
        model.setHardnessWater(hardnessWater);
        model.setHexCodeCollor(HexCodeCollor);
        model.setVolume(volume);
        model.setWashingNumber(washingNumber);
        model.setIsDisplay(isDisplay);
        model.setWarrantyExpirationDate(warrantyExpirationDate);
        washingMachineService.createModel(createdUser.getId(), model);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }

}
