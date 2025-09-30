package task.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import task.myproject.entity.Goods;
import task.myproject.repository.GoodsMapper;
import java.util.List;

@Service
@RequiredArgsConstructor//finalが付いたフィールドのコンストラクタを自動生成
public class GoodsService {
    private final GoodsMapper goodsMapper;

    public List<Goods> findAll() {
        return goodsMapper.findAll();
    }

    public Goods findById(int id) {
        Goods goods = goodsMapper.findById(id);
        if (goods == null) {
            throw new IllegalArgumentException("商品が見つかりません。ID:" + id);
        }
        return goods;
    }

    public void insert(Goods goods) {
        goodsMapper.insert(goods);
    }

    public void update(Goods goods) {
        if (goods.getId() == null || goodsMapper.findById(goods.getId()) == null) {
            throw new IllegalArgumentException("更新対象の商品が存在しません。ID:" + goods.getId());
        }
        goodsMapper.update(goods);
    }

    public void delete(int id) {
        if (goodsMapper.findById(id) == null) {
            throw new IllegalArgumentException("削除対象の商品が存在しません。ID:" + id);
        }
        goodsMapper.delete(id);
    }

    public List<Goods> searchGoods(Integer id, String name, String size) {
        return goodsMapper.findGoods(id, name, size);
    }
}
