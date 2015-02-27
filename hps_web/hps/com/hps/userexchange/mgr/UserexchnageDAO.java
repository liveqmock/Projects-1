package  com.hps.userexchange.mgr;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;


public interface UserexchnageDAO extends BaseDAO {
	public List<Userexchnage> getUserexchnageList();

	public Userexchnage getUserexchnageById(String id);

	public int addUserexchnage(Userexchnage userexchnage) throws Exception;
	
	public int updateUserexchnage(Userexchnage userexchnage) throws Exception;

	public int deleteUserexchnageById(String id) throws Exception;	
}
