package  com.hps.buyerInfo.mgr;

import java.util.List;

public interface BuyerinfoService {

	public List<Buyerinfo> getBuyerinfoList();
	
	public List<Buyerinfo> getAllInfoById(String userId);

	public Buyerinfo getBuyerinfoById(String id);

	public boolean addBuyerinfo(Buyerinfo buyerinfo) throws Exception;
	
	public boolean updateBuyerinfo(Buyerinfo buyerinfo) throws Exception;

	public boolean deleteBuyerinfoById(String[] idArray) throws Exception;
}
