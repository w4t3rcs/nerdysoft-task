package io.w4t3rcs.task.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "NerdySoft Task App",
                description = "Tech task for NerdySoft company",
                version = "1",
                contact = @Contact(name = "GitHub", url = "https://github.com/w4t3rcs/nerdysoft-task")
        )
)
@Configuration
public class OpenApiConfig {
}
