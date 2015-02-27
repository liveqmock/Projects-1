package  com.hps.userexchange.mgr;

import java.util.List;
import com.hps.userexchange.mgr.Userexchnage;

public interface UserexchnageService {

	public List<Userexchnage> getUserexchnageList();

	public Userexchnage getUserexchnageById(String id);

	public boolean addUserexchnage(Userexchnage userexchnage) throws Exception;
	
	public boolean updateUserexchnage(Userexchnage userexchnage) throws Exception;

	public boolean deleteUserexchnageById(String[] idArray) throws Exception;
	
	public boolean handleUserExchange(String id) throws Exception;
}
