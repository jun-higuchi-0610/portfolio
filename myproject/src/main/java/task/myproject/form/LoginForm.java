package task.myproject.form;

import lombok.Data; // Lombokでgetter, setterを省略
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank(message = "メールアドレスは必須です。")
    private String mail;

    @NotBlank(message = "パスワードは必須です。")
    private String password;
}
