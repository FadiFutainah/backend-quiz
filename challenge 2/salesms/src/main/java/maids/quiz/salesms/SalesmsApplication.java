package maids.quiz.salesms;

import maids.quiz.salesms.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SalesmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesmsApplication.class, args);
    }
}