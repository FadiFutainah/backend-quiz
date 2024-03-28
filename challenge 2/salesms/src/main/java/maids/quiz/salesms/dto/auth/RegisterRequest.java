package maids.quiz.salesms.dto.auth;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maids.quiz.salesms.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @Size(min = 3, max = 54)
    String firstname;
    String lastname;
    String address;

    @NotNull
    String mobileNumber;

    @Email
    @NotNull
    String email;

    @Size(min = 8, max = 60)
    @NotNull
    String password;

    @Enumerated(EnumType.STRING)
    Role role;
}
