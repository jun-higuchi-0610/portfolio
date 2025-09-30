package task.myproject.form;

//lombokでgetter.setterを省略
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UsersForm {

    private Integer id;

    @NotBlank(message = "名前は必須です。")//空文字や空白のみをできない
    @Size(max = 50, message = "名前は50文字以内で入力してください。")//最大文字制限
    private String name;

    @NotBlank(message = "メールアドレスは必須です。")//空文字や空白のみをできない
    @Email(message = "メールアドレスを正しく入力してください。")//メール形式の制限
    @Size(max = 100, message = "メールアドレスは100文字以内で入力してください。")//最大文字制限
    private String mail;

    @NotBlank(message = "パスワードは必須です。")//空文字や空白のみをできない
    @Size(min = 6, max = 20, message = "パスワードは6文字以上20文字以内で入力してください。")//最小・最大文字制限
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "パスワードは大文字、小文字、数字を含む必要があります。")//パスワードの制限
    private String password;

    private String authority;

    private String keyword;

    private String idKeyword;

    private String nameKeyword;
}
