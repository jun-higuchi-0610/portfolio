package task.myproject.control;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import task.myproject.entity.Goods;
import task.myproject.service.GoodsService;
import task.myproject.form.GoodsForm;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor //finalが付いたフィールドのコンストラクタを自動生成
public class GoodsController {
    private final GoodsService goodsService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/search")
    public String searchGoods(
        @RequestParam(name = "id", required = false) Integer id,
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "size", required = false) String size,
        Model model) {

        List<Goods> goodsList;
        String message = null;

        // IDだけ入力されている場合
        if (id != null && (name == null || name.isBlank()) && (size == null || size.isBlank())) {
            goodsList = goodsService.findAll(); // 全件表示
            message = "IDだけでは検索できません";
        }
        // 検索条件が空の場合
        else if ((id == null) && (name == null || name.isBlank()) && (size == null || size.isBlank())) {
            goodsList = goodsService.findAll(); // 初期表示
        }
        // 名前・サイズで検索
        else {
            goodsList = goodsService.searchGoods(id, name, size);
            if (goodsList.isEmpty()) {
                message = "検索条件に該当する商品が見つかりません。";
            }
        }
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("message", message);
        return "search/goodsSearch";
    }

    // 商品管理画面
    @GetMapping("/control/goodsControl")
    public String goodsControl(Model model) {
        model.addAttribute("goodsList", goodsService.findAll());
        return "control/goodsControl";
    }

    // 商品の登録・更新・削除処理
    @PostMapping("/control")
    public String createGoods(
        @Valid @ModelAttribute("goodsForm") GoodsForm form,
        BindingResult result,
        @RequestParam("action") String action,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("goodsList", goodsService.findAll());
            return "control/goodsControl";
        }
        Goods goods = new Goods();
        goods.setId(form.getId());
        goods.setName(form.getName());
        goods.setPrice(form.getPrice());
        goods.setQuantity(form.getQuantity());
        goods.setSize(form.getSize());

        // 画像アップロード処理
        MultipartFile imageFile = form.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                // ディレクトリがなければ作成
                Files.createDirectories(filePath.getParent());

                // ファイル保存
                imageFile.transferTo(filePath.toFile());

                // DBには相対パスを保存
                goods.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "画像アップロードに失敗しました。");
            }
        }

        switch (action) {
            case "create":
                goodsService.insert(goods);
                model.addAttribute("message", "商品を登録しました。");
                break;
            case "update":
                goodsService.update(goods);
                model.addAttribute("message", "商品を更新しました。");
                break;
            case "delete":
                Integer id = form.getId();
                if (id != null) {
                    try {
                        goodsService.delete(id);
                        model.addAttribute("message", "商品を削除しました。");
                    } catch (IllegalArgumentException e) {
                        model.addAttribute("message", e.getMessage());
                    }
                }
                break;
            default:
                model.addAttribute("message", "不正な操作です。");
        }
        model.addAttribute("goodsList", goodsService.findAll());
        return "control/goodsControl";
    }

    // 商品詳細画面
    @GetMapping("/menu/manager")
    public String managermenu() {
        return "menu/manager";
    }

    // 商品検索画面
    @GetMapping("/search/goodsSearch")
    public String goodsSearch(Model model) {
        model.addAttribute("goodsList", goodsService.findAll());
        return "search/goodsSearch";
    }
}