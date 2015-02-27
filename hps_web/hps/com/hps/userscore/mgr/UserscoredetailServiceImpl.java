package  com.hps.userscore.mgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hps.userscore.mgr.Userscoredetail;

@Service
public class UserscoredetailServiceImpl implements UserscoredetailService {
	private Logger logger = LoggerFactory.getLogger(UserscoredetailServiceImpl.class);
	
	@Autowired
	private UserscoredetailDAO userscoredetailDAO;
	
	public List<Userscoredetail> getUserscoredetailList(){
		try{
		     return userscoredetailDAO.getUserscoredetailList();
		}
		catch(Exception e){
			logger.error("Error when query Userscoredetail list data",e);
		}
		return null;
	}

	public Userscoredetail getUserscoredetailById(String id){
	   try{
		 return userscoredetailDAO.getUserscoredetailById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Userscoredetail by id: "+id,e);		
		}
		return null;
	}

	public boolean addUserscoredetail(Userscoredetail userscoredetail) throws Exception {
		return userscoredetailDAO.addUserscoredetail(userscoredetail) > 0;
	}
	
	public boolean updateUserscoredetail(Userscoredetail userscoredetail) throws Exception {
		 return userscoredetailDAO.updateUserscoredetail(userscoredetail) > 0;	    
	}

	public boolean deleteUserscoredetailById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = userscoredetailDAO.deleteUserscoredetailById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}

	@Override
	public List<Userscoredetail> getTodayUserScoreList(Userscoredetail userscoredetail) throws Exception {
		return userscoredetailDAO.getTodayUserScoreList(userscoredetail);
	}

	@Override
	public List<Userscoredetail> getUserScoreDetailByUser(String userId)
			throws Exception {
		// TODO Auto-generated method stub
		return userscoredetailDAO.getUserScoreDetailByUser(userId);
	}	
}
