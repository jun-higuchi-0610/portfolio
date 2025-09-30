package task.myproject.service;

import org.springframework.stereotype.Service;
import task.myproject.entity.Users;
import task.myproject.repository.UsersMapper;
import lombok.RequiredArgsConstructor;
import java.util.List;


@Service
@RequiredArgsConstructor//finalが付いたフィールドのコンストラクタを自動生成
public class UsersService {
    private final UsersMapper usersMapper;

    public Users findById(Integer id) {
        Users users = usersMapper.findById(id);
        if (users == null) {
            throw new IllegalArgumentException("ユーザーが見つかりません。ID:" + id);
        }
        return users;
    }

    public void create(Users user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("ユーザー名は必須です。");
        }
        usersMapper.create(user);
    }

    public boolean update(Users user) {
        if (user.getId() == null || usersMapper.findById(user.getId()) == null) {
            return false;
        }
        usersMapper.update(user);
        return true;
    }

    public void delete(Integer id) {
        if (usersMapper.findById(id) == null) {
            throw new IllegalArgumentException("削除ユーザーが存在しません。ID:" + id);
        }
        usersMapper.delete(id);
    }

    public Users findByMailAndPassword(String mail, String password) {
        return usersMapper.findByMailAndPassword(mail, password);
    }


    // ユーザー一覧取得
    public List<Users> findAll() {
        return usersMapper.findAll();
    }

    // ユーザーIDまたは名前で検索
    public List<Users> searchByIdAndName(Integer id, String name) {
        if ((id == null) && (name == null || name.isEmpty())) {
            return usersMapper.findAll();
        }
        return usersMapper.searchByIdAndName(id, name);
    }
}
