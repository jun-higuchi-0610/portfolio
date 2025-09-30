package task.myproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;

import task.myproject.repository.GoodsMapper;
import task.myproject.entity.Goods;

@SpringBootTest
class MyprojectApplicationTests {

	@Autowired
	GoodsMapper goodsMapper;

	@Disabled("後で対応予定")
	@Test
	void testFindGoodsById() {
		// テスト用のデータ
		Goods testGoods = new Goods();
		testGoods.setName("テスト商品");
		testGoods.setImage("test.jpg");
		testGoods.setPrice(1000);
		testGoods.setQuantity(5);
		testGoods.setSize("DS");

		//　登録
		goodsMapper.insert(testGoods);

		//　ID取得(@Optionsが必要)
		Integer id = testGoods.getId();
		assertThat(id).isNotNull();

		//　取得して検証
		Goods result = goodsMapper.findById(id);
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("テスト商品");
	}

}
