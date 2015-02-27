package  com.hps.goods.mgr;

import java.util.List;

public interface GoodsService {

	public List<Goods> getGoodsList();
	
	public List<Goods> getGoodsImageList();

	public Goods getGoodsById(String id);

	public boolean addGoods(Goods goods) throws Exception;
	
	public boolean updateGoods(Goods goods) throws Exception;

	public boolean deleteGoodsById(String[] idArray) throws Exception;
}
