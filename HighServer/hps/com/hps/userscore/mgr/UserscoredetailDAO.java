package  com.hps.userscore.mgr;

import java.util.List;

import com.hps.userscore.mgr.Userscoredetail;
import com.newsoft.common.mybatis.BaseDAO;


public interface UserscoredetailDAO extends BaseDAO {
	public List<Userscoredetail> getUserscoredetailList();

	public Userscoredetail getUserscoredetailById(String id);

	public int addUserscoredetail(Userscoredetail userscoredetail) throws Exception;
	
	public int updateUserscoredetail(Userscoredetail userscoredetail) throws Exception;

	public int deleteUserscoredetailById(String id) throws Exception;	
	
	public List<Userscoredetail> getTodayUserScoreList(Userscoredetail userscoredetail) throws Exception;
}
