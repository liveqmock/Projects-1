package  com.hps.buyerInfo.mgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerinfoServiceImpl implements BuyerinfoService {
	private Logger logger = LoggerFactory.getLogger(BuyerinfoServiceImpl.class);
	
	@Autowired
	private BuyerinfoDAO buyerinfoDAO;
	
	public List<Buyerinfo> getBuyerinfoList(){
		try{
		     return buyerinfoDAO.getBuyerinfoList();
		}
		catch(Exception e){
			logger.error("Error when query Buyerinfo list data",e);
		}
		return null;
	}

	public Buyerinfo getBuyerinfoById(String id){
	   try{
		 return buyerinfoDAO.getBuyerinfoById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Buyerinfo by id: "+id,e);		
		}
		return null;
	}

	public boolean addBuyerinfo(Buyerinfo buyerinfo) throws Exception {
		return buyerinfoDAO.addBuyerinfo(buyerinfo) > 0;
	}
	
	public boolean updateBuyerinfo(Buyerinfo buyerinfo) throws Exception {
		 return buyerinfoDAO.updateBuyerinfo(buyerinfo) > 0;	    
	}

	public boolean deleteBuyerinfoById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = buyerinfoDAO.deleteBuyerinfoById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}

	@Override
	public List<Buyerinfo> getAllInfoById(String userId) {
		try{
		     return buyerinfoDAO.getAllInfoById(userId);
		}
		catch(Exception e){
			logger.error("Error when query Buyerinfo list data",e);
		}
		return null;
	}	
}
