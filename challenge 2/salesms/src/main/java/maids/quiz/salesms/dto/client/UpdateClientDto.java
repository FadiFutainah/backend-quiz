package maids.quiz.salesms.dto.client;

import jakarta.validation.constraints.Email;

public class UpdateClientDto {
    String firstname;
    String lastname;
    String address;
    String mobileNumber;
    @Email
    String email;
}
