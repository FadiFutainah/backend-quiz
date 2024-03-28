package maids.quiz.salesms;

import maids.quiz.salesms.service.AuthenticationService;
import maids.quiz.salesms.dto.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static maids.quiz.salesms.enums.Role.ADMIN;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SalesmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesmsApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
//            TODO: remove
//            var admin = RegisterRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("admin@mail.com")
//                    .password("password")
//                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
        };
    }
}