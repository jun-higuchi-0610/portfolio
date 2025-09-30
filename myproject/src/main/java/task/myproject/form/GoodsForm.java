package task.myproject.form;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GoodsForm {

    private Integer id;

    @NotBlank(message = "商品名は必須です。")//空文字や空白のみをできない
    @Size(max = 100, message = "商品名は100文字以内で入力してください。")//最大文字制限
    private String name;

    private MultipartFile imageFile;

    @NotNull(message = "価格は必須です。")//nullを許可しない
    @Min(value = 0, message = "価格は0以上でなければなりません。")//最小値制限
    private Integer price;

    @NotNull(message = "在庫数は必須です。")//nullを許可しない
    @Min(value = 0, message = "在庫数は0以上で入力してください。")//最小値制限
    private Integer quantity;

    @NotBlank(message = "サイズは必須です。")//空文字や空白のみをできない
    @Size(max = 50, message = "サイズは50文字以内で入力してください。")//最大文字制限
    private String size;
}
