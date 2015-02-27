package  com.hps.level.mgr;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hps.level.mgr.Scorelevel;

@Service
public class ScorelevelServiceImpl implements ScorelevelService {
	private Logger logger = LoggerFactory.getLogger(ScorelevelServiceImpl.class);
	
	@Autowired
	private ScorelevelDAO ScorelevelDAO;
	
	private static Map<String, Scorelevel> cacheMap = new ConcurrentHashMap<String, Scorelevel>();
	
	public List<Scorelevel> getScorelevelList(){
		try{
		     return ScorelevelDAO.getScorelevelList();
		}
		catch(Exception e){
			logger.error("Error when query Scorelevel list data",e);
		}
		return null;
	}

	public Scorelevel getScorelevelById(String id){
	   try{
		 return ScorelevelDAO.getScorelevelById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Scorelevel by id: "+id,e);		
		}
		return null;
	}

	public boolean addScorelevel(Scorelevel Scorelevel) throws Exception {
		return ScorelevelDAO.addScorelevel(Scorelevel) > 0;
	}
	
	public boolean updateScorelevel(Scorelevel Scorelevel) throws Exception {
		 return ScorelevelDAO.updateScorelevel(Scorelevel) > 0;	    
	}

	public boolean deleteScorelevelById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = ScorelevelDAO.deleteScorelevelById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}

	@Override
	public Scorelevel getCacheScorelevelById(String id) {
		Scorelevel Scorelevel = cacheMap.get(id);
		if (Scorelevel==null) {
			Scorelevel = ScorelevelDAO.getScorelevelById(id);
			if (Scorelevel!=null) {
				cacheMap.put(id, Scorelevel);
			}
			return Scorelevel;
		}
		return Scorelevel;
	}	
}
