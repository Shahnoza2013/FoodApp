package uz.pdp.fastfoodapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Fast food app",
                description = "You can order here for your enjoy",
                termsOfService = "/termsOfApp",
                contact = @Contact(
                        name = "G40",
                        url = "https://pdp.uz",
                        email = "pdp@gmail.com"
                ),
                version = "v0.0.1"
        ),
        servers = {
                @Server(url = "http://localhost:8081",description = "For Local Mode"),
               // @Server(url = "http://57.180.252.209:8082",description = "For Server Mode"),

        }
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "bearerAuth",
        description = "You have to add JWT Token",
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
