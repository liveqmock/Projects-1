package  com.hps.highwaylife.mgr;

import java.util.List;

public interface HighwaylifeService {

	public List<Highwaylife> getHighwaylifeList();
	
	public List<Highwaylife> getHighwaylifeListByType(int type);

	public Highwaylife getHighwaylifeById(String id);

	public boolean addHighwaylife(Highwaylife highwaylife) throws Exception;
	
	public boolean updateHighwaylife(Highwaylife highwaylife) throws Exception;

	public boolean deleteHighwaylifeById(String[] idArray) throws Exception;
}
