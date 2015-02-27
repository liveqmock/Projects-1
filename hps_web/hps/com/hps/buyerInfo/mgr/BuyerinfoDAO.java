package  com.hps.buyerInfo.mgr;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;


public interface BuyerinfoDAO extends BaseDAO {
	public List<Buyerinfo> getBuyerinfoList();
	
	public List<Buyerinfo> getAllInfoById(String userId);

	public Buyerinfo getBuyerinfoById(String id);

	public int addBuyerinfo(Buyerinfo buyerinfo) throws Exception;
	
	public int updateBuyerinfo(Buyerinfo buyerinfo) throws Exception;

	public int deleteBuyerinfoById(String id) throws Exception;	
}
