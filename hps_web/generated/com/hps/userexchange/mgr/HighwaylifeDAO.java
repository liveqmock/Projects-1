package  com.hps.userexchange.mgr;

import java.util.List;

import com.newsoft.common.log.annotation.LogParam;
import com.newsoft.common.mybatis.BaseDAO;
import com.hps.userexchange.mgr.Highwaylife;


public interface HighwaylifeDAO extends BaseDAO {
	public List<Highwaylife> getHighwaylifeList();

	public Highwaylife getHighwaylifeById(String id);

	@LogParam(logDes = "Add Highwaylife", operateModule = "Highwaylife Management")
	public int addHighwaylife(Highwaylife highwaylife) throws Exception;
	
	@LogParam(logDes = "Update Highwaylife", operateModule = "Highwaylife Management")
	public int updateHighwaylife(Highwaylife highwaylife) throws Exception;

	@LogParam(logDes = "Delete Highwaylife", operateModule = "Highwaylife Management")
	public int deleteHighwaylifeById(String id) throws Exception;	
}
