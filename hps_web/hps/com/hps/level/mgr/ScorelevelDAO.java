package  com.hps.level.mgr;

import java.util.List;

import com.hps.level.mgr.Scorelevel;
import com.newsoft.common.mybatis.BaseDAO;


public interface ScorelevelDAO extends BaseDAO {
	public List<Scorelevel> getScorelevelList();

	public Scorelevel getScorelevelById(String levelkey);

	public int addScorelevel(Scorelevel Scorelevel) throws Exception;
	
	public int updateScorelevel(Scorelevel Scorelevel) throws Exception;

	public int deleteScorelevelById(String id) throws Exception;	
}
