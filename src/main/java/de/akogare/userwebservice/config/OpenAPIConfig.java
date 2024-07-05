package de.akogare.userwebservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server devServer = new Server();
        devServer.setDescription("Localhost Akogare Platform REST API");
        devServer.setUrl("http://localhost:8080");

        Contact contact = new Contact();
        contact.setEmail("info@abramov-samuel.de");
        contact.setName("Samuel Abramov");
        contact.setUrl("https://www.abramov-samuel.de.com");

        Info info = new Info();
        info.setTitle("Akogare Platform REST API");
        info.setVersion("1.0.0");
        info.setDescription("REST API for Akogare Platform");
        info.setContact(contact);

        return new OpenAPI()
                .info(info)
                .addServersItem(devServer);

    }
}
