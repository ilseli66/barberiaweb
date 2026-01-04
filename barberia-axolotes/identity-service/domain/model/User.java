package identity.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String login;
    private String password;

    public boolean isLoginValid() {
        return login != null && login.length() >= 5;
    }
}
