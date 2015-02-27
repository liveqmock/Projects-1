package  com.hps.cms.mgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hps.cms.mgr.Cmscontent;

@Service
public class CmscontentServiceImpl implements CmscontentService {
	private Logger logger = LoggerFactory.getLogger(CmscontentServiceImpl.class);
	
	@Autowired
	private CmscontentDAO cmscontentDAO;
	
	public List<Cmscontent> getCmscontentList(){
		try{
		     return cmscontentDAO.getCmscontentList();
		}
		catch(Exception e){
			logger.error("Error when query Cmscontent list data",e);
		}
		return null;
	}

	public Cmscontent getCmscontentById(String id){
	   try{
		 return cmscontentDAO.getCmscontentById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Cmscontent by id: "+id,e);		
		}
		return null;
	}

	public boolean addCmscontent(Cmscontent cmscontent) throws Exception {
		return cmscontentDAO.addCmscontent(cmscontent) > 0;
	}
	
	public boolean updateCmscontent(Cmscontent cmscontent) throws Exception {
		 return cmscontentDAO.updateCmscontent(cmscontent) > 0;	    
	}

	public boolean deleteCmscontentById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = cmscontentDAO.deleteCmscontentById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}	
}
