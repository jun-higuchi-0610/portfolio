package task.myproject.entity;

import lombok.Data;//lombokでgetter.setterを省略
import java.time.LocalDateTime;

@Data
public class Users {

    private Integer id;
    private String name;
    private String mail;
    private String password;
    private String authority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
