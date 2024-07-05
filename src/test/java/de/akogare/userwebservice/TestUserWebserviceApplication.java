package de.akogare.userwebservice;

import org.springframework.boot.SpringApplication;

public class TestUserWebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.from(UserWebserviceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
