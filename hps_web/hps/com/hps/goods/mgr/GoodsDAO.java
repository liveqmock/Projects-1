package  com.hps.goods.mgr;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;


public interface GoodsDAO extends BaseDAO {
	public List<Goods> getGoodsList();
	
	public List<Goods> getGoodsImageList();

	public Goods getGoodsById(String id);

	public int addGoods(Goods goods) throws Exception;
	
	public int updateGoods(Goods goods) throws Exception;

	public int deleteGoodsById(String id) throws Exception;	
}
