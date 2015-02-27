package  com.hps.userscore.mgr;

import java.util.List;

import com.hps.userscore.mgr.Userscoredetail;

public interface UserscoredetailService {

	public List<Userscoredetail> getUserscoredetailList();

	public Userscoredetail getUserscoredetailById(String id);

	public boolean addUserscoredetail(Userscoredetail userscoredetail) throws Exception;
	
	public boolean updateUserscoredetail(Userscoredetail userscoredetail) throws Exception;

	public boolean deleteUserscoredetailById(String[] idArray) throws Exception;
	
	public List<Userscoredetail> getTodayUserScoreList(Userscoredetail userscoredetail) throws Exception;
}
