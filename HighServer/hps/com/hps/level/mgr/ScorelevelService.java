package  com.hps.level.mgr;

import java.util.List;

import com.hps.level.mgr.Scorelevel;

public interface ScorelevelService {

	public List<Scorelevel> getScorelevelList();

	public Scorelevel getScorelevelById(String id);

	public boolean addScorelevel(Scorelevel Scorelevel) throws Exception;
	
	public boolean updateScorelevel(Scorelevel Scorelevel) throws Exception;

	public boolean deleteScorelevelById(String[] idArray) throws Exception;
	
	public Scorelevel getCacheScorelevelById(String id);
}
