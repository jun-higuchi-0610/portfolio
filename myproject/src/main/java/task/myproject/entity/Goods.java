package task.myproject.entity;

import lombok.Data;//lombokでgetter.setterを省略
import java.time.LocalDateTime;

@Data
public class Goods {

   private Integer id;
   private String name;
   private String image;
   private Integer price;
   private Integer quantity;
   private String size;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   private LocalDateTime deletedAt;
}
