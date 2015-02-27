package  com.hps.highwaylife.mgr;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;


public interface HighwaylifeDAO extends BaseDAO {
	public List<Highwaylife> getHighwaylifeList();
	
	public List<Highwaylife> getHighwaylifeListByType(int type);

	public Highwaylife getHighwaylifeById(String id);

	public int addHighwaylife(Highwaylife highwaylife) throws Exception;
	
	public int updateHighwaylife(Highwaylife highwaylife) throws Exception;

	public int deleteHighwaylifeById(String id) throws Exception;	
}
