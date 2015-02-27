package  com.hps.userexchange.mgr;

import java.util.List;

import com.newsoft.common.log.annotation.LogParam;
import com.newsoft.common.mybatis.BaseDAO;
import com.hps.userexchange.mgr.Userexchnage;


public interface UserexchnageDAO extends BaseDAO {
	public List<Userexchnage> getUserexchnageList();

	public Userexchnage getUserexchnageById(String id);

	@LogParam(logDes = "Add Userexchnage", operateModule = "Userexchnage Management")
	public int addUserexchnage(Userexchnage userexchnage) throws Exception;
	
	@LogParam(logDes = "Update Userexchnage", operateModule = "Userexchnage Management")
	public int updateUserexchnage(Userexchnage userexchnage) throws Exception;

	@LogParam(logDes = "Delete Userexchnage", operateModule = "Userexchnage Management")
	public int deleteUserexchnageById(String id) throws Exception;	
}
