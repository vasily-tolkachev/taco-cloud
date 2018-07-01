package sia.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sia.tacocloud.config.SecurityConfig;
import sia.tacocloud.config.SwaggerConfig;
import sia.tacocloud.config.WebConfig;

@SpringBootApplication
@EnableJpaRepositories("sia.tacocloud.data")
@Import({SecurityConfig.class, WebConfig.class, SwaggerConfig.class})
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}
}
