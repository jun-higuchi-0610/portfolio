package task.myproject.repository;

import org.apache.ibatis.annotations.*;
import task.myproject.entity.Goods;
import java.util.List;

@Mapper
public interface GoodsMapper {

    List<Goods> findAll();
    Goods findById(int id);
    void insert(Goods goods);
    void update(Goods goods);
    void delete(int id);
    List<Goods> findGoods(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("size") String size
    );
}
