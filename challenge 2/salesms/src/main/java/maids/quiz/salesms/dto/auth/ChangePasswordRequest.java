package maids.quiz.salesms.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    String currentPassword;
    String newPassword;
    String confirmationPassword;
}
