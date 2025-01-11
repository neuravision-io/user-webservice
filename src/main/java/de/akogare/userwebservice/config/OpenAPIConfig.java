package de.akogare.userwebservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

	@Value("${keycloak.auth-server-url}")
	private String keycloakUrlAuth;

	@Value("${keycloak.token.url}")
	private String keycloakTokenUrl;

	@Value("${gateway.url}")
	private String gatewayUrl;

	@Bean
	public OpenAPI customOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl(gatewayUrl);
		devServer.setDescription("Localhost Akogare REST API");

		Contact contact = new Contact();
		contact.setEmail("info@abramov-samuel.de");
		contact.setName("Samuel Abramov");
		contact.setUrl("https://www.abramov-samuel.de");

		Info info = new Info();
		info.setTitle("Akogare Platform - User Service");
		info.setVersion("1.0.0");
		info.setDescription("REST API for Akogare User Service");
		info.setContact(contact);

		return new OpenAPI()
				.components(new Components()
						            .addSecuritySchemes("oauth2", new SecurityScheme()
								            .type(SecurityScheme.Type.OAUTH2)
								            .description("OAuth2 Authorization Code Flow")
								            .flows(new OAuthFlows()
										                   .authorizationCode(new OAuthFlow()
												                                      .authorizationUrl(keycloakUrlAuth)
												                                      .tokenUrl(keycloakTokenUrl)
												                                      .scopes(new Scopes()
														                                              .addString(
																                                              "openid",
																                                              "openid")
														                                              .addString(
																                                              "profile",
																                                              "profile")
														                                              .addString("email",
														                                                         "email")
														                                              .addString("roles",
														                                                         "roles")
												                                      )
										                   )
								            )
						            )
				).addSecurityItem(new SecurityRequirement().addList("oauth2"))
				.info(info)
				.addServersItem(devServer);
	}
}
