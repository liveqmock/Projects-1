package  com.hps.userexchange.mgr;

import java.util.List;
import com.hps.userexchange.mgr.Highwaylife;

public interface HighwaylifeService {

	public List<Highwaylife> getHighwaylifeList();

	public Highwaylife getHighwaylifeById(String id);

	public boolean addHighwaylife(Highwaylife highwaylife) throws Exception;
	
	public boolean updateHighwaylife(Highwaylife highwaylife) throws Exception;

	public boolean deleteHighwaylifeById(String[] idArray) throws Exception;
}
