package  com.hps.goods.mgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements GoodsService {
	private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);
	
	@Autowired
	private GoodsDAO goodsDAO;
	
	public List<Goods> getGoodsList(){
		try{
		     return goodsDAO.getGoodsList();
		}
		catch(Exception e){
			logger.error("Error when query Goods list data",e);
		}
		return null;
	}

	public Goods getGoodsById(String id){
	   try{
		 return goodsDAO.getGoodsById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Goods by id: "+id,e);		
		}
		return null;
	}

	public boolean addGoods(Goods goods) throws Exception {
		return goodsDAO.addGoods(goods) > 0;
	}
	
	public boolean updateGoods(Goods goods) throws Exception {
		 return goodsDAO.updateGoods(goods) > 0;	    
	}

	public boolean deleteGoodsById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = goodsDAO.deleteGoodsById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}

	@Override
	public List<Goods> getGoodsImageList() {
		try{
		     return goodsDAO.getGoodsImageList();
		}
		catch(Exception e){
			logger.error("Error when query Goods list data",e);
		}
		return null;
	}	
}
