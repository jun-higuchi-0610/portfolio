package task.myproject.repository;

import org.apache.ibatis.annotations.*;
import task.myproject.entity.Users;
import java.util.List;

@Mapper
public interface UsersMapper {

    List<Users> findAll();
    Users findById(int id);
    void create(Users user);
    void update(Users user);
    void delete(int id);
    Users findByMailAndPassword(
        @Param("mail") String mail,
        @Param("password") String password);

    //IDと名前で部分一致検索
    List<Users> searchByIdAndName(
        @Param("id") Integer id,
        @Param("name") String name);
}
