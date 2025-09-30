package task.myproject.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import task.myproject.service.UsersService;
import org.springframework.ui.Model;
import task.myproject.entity.Users;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import task.myproject.form.LoginForm;
import task.myproject.form.UsersForm;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor // finalが付いたフィールドのコンストラクタを自動生成
public class UsersController {
    private final UsersService usersService;
    private final HttpSession session; // セッション管理用

    // ログインフォーム表示
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login";
        }

        // メールとパスワードでDBからユーザーを検索
        Users user = usersService.findByMailAndPassword(form.getMail(), form.getPassword());

        // ユーザーが見つからないときはエラーメッセージを表示
        if (user == null) {
            model.addAttribute("loginError", "メールアドレスまたはパスワードが間違っています。");
            return "login";
        }

        // セッションにユーザー情報を保存
        session.setAttribute("loggedInUser", user);

        // 権限によって画面遷移
        if ("manager".equals(user.getAuthority())) {
            return "menu/manager";
        } else if ("general".equals(user.getAuthority())) {
            return "menu/general";
        } else {
            model.addAttribute("loginError", "ログインに失敗しました。");
            return "login";
        }
    }

    // ログイン後メニュー画面
    @GetMapping("/menu")
    public String menu() {
        Users user = (Users) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        if ("manager".equals(user.getAuthority())) {
            return "menu/manager";
        } else {
            return "menu/general";
        }
    }

    // ユーザー管理画面
    @GetMapping("control/usersControl")
    public String usersControl(Model model) {
        model.addAttribute("usersForm", new UsersForm());
        model.addAttribute("usersList", usersService.findAll());
        return "control/usersControl";
    }

    // ユーザー管理処理
    @PostMapping("/control/usersControl")
    public String usersControl(
        @Valid @ModelAttribute("usersForm") UsersForm usersForm,
        BindingResult bindingResult,
        @RequestParam("action") String action,
        Model model) {

            if (bindingResult.hasErrors()) {
                model.addAttribute("usersList", usersService.findAll());
                model.addAttribute("usersForm", usersForm);
                return "control/usersControl";
            }

            String message = "";

            Users userEntity = toEntity(usersForm);

            switch(action) {
                case "create":
                    usersService.create(userEntity);
                    message = "登録完了しました。";
                    break;
                case "update":
                    boolean updated = usersService.update(userEntity);
                    if (updated) {
                        message = "更新完了しました。";
                    } else {
                        message = "更新対象のユーザーが存在しません。";
                    }
                    break;
                case "delete":
                    usersService.delete(userEntity.getId());
                    message = "削除完了しました。";
                    break;
            }
            model.addAttribute("usersList", usersService.findAll());
            model.addAttribute("message", message);
            return "control/usersControl";
        }

        // 変換用メソッド
        private Users toEntity(UsersForm form) {
            Users user = new Users();
            user.setId(form.getId());
            user.setName(form.getName());
            user.setMail(form.getMail());
            user.setPassword(form.getPassword());
            user.setAuthority(form.getAuthority());
            return user;
        }

    // ユーザー検索画面（初期表示）
    @GetMapping("search/usersSearch")
    public String usersSearch(Model model) {
        model.addAttribute("usersForm", new UsersForm());
        model.addAttribute("usersList", usersService.findAll());
        return "search/usersSearch";
    }

    // ユーザー検索処理
    @PostMapping("/search/usersSearch")
    public String usersSearch(@ModelAttribute("usersForm") UsersForm form, Model model) {

        // セッションチェック
        Users user = (Users) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        // 検索処理
        List<Users> usersList = usersService.searchByIdAndName(
            form.getId(),
            form.getName()
        );
        model.addAttribute("usersList", usersList);

        if (usersList.isEmpty()) {
            model.addAttribute("message", "検索条件に該当するユーザーが見つかりません。");
        }
        return "search/usersSearch";
    }
}
