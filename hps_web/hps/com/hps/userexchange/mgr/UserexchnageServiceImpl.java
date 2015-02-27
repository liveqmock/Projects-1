package  com.hps.userexchange.mgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hps.userexchange.mgr.Userexchnage;
import com.newsoft.security.acegi.SessionUtil;

@Service
public class UserexchnageServiceImpl implements UserexchnageService {
	private Logger logger = LoggerFactory.getLogger(UserexchnageServiceImpl.class);
	
	@Autowired
	private UserexchnageDAO userexchnageDAO;
	
	public boolean handleUserExchange(String id) throws Exception{
		Userexchnage exchange = userexchnageDAO.getUserexchnageById(id);
		exchange.setHandler(SessionUtil.getSessionUserId());
		exchange.setHandlername(SessionUtil.getSessionUser().getUserName());
		exchange.setStatus(Userexchnage.HANDLED);
		
		userexchnageDAO.updateUserexchnage(exchange);
		
		return true;
	}
	
	public List<Userexchnage> getUserexchnageList(){
		try{
		     return userexchnageDAO.getUserexchnageList();
		}
		catch(Exception e){
			logger.error("Error when query Userexchnage list data",e);
		}
		return null;
	}

	public Userexchnage getUserexchnageById(String id){
	   try{
		 return userexchnageDAO.getUserexchnageById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Userexchnage by id: "+id,e);		
		}
		return null;
	}

	public boolean addUserexchnage(Userexchnage userexchnage) throws Exception {
		return userexchnageDAO.addUserexchnage(userexchnage) > 0;
	}
	
	public boolean updateUserexchnage(Userexchnage userexchnage) throws Exception {
		 return userexchnageDAO.updateUserexchnage(userexchnage) > 0;	    
	}

	public boolean deleteUserexchnageById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = userexchnageDAO.deleteUserexchnageById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}	
}
