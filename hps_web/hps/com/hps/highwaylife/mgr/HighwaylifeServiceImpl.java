package  com.hps.highwaylife.mgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HighwaylifeServiceImpl implements HighwaylifeService {
	private Logger logger = LoggerFactory.getLogger(HighwaylifeServiceImpl.class);
	
	@Autowired
	private HighwaylifeDAO highwaylifeDAO;
	
	public List<Highwaylife> getHighwaylifeList(){
		try{
		     return highwaylifeDAO.getHighwaylifeList();
		}
		catch(Exception e){
			logger.error("Error when query Highwaylife list data",e);
		}
		return null;
	}

	public Highwaylife getHighwaylifeById(String id){
	   try{
		 return highwaylifeDAO.getHighwaylifeById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Highwaylife by id: "+id,e);		
		}
		return null;
	}

	public boolean addHighwaylife(Highwaylife highwaylife) throws Exception {
		return highwaylifeDAO.addHighwaylife(highwaylife) > 0;
	}
	
	public boolean updateHighwaylife(Highwaylife highwaylife) throws Exception {
		 return highwaylifeDAO.updateHighwaylife(highwaylife) > 0;	    
	}

	public boolean deleteHighwaylifeById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = highwaylifeDAO.deleteHighwaylifeById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}

	@Override
	public List<Highwaylife> getHighwaylifeListByType(int type) {
		try{
		     return highwaylifeDAO.getHighwaylifeListByType(type);
		}
		catch(Exception e){
			logger.error("Error when query Highwaylife list data",e);
		}
		return null;
	}	
}
