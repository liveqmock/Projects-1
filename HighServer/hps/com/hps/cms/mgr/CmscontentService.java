package  com.hps.cms.mgr;

import java.util.List;

import com.hps.cms.mgr.Cmscontent;

public interface CmscontentService {

	public List<Cmscontent> getCmscontentList();

	public Cmscontent getCmscontentById(String id);

	public boolean addCmscontent(Cmscontent cmscontent) throws Exception;
	
	public boolean updateCmscontent(Cmscontent cmscontent) throws Exception;

	public boolean deleteCmscontentById(String[] idArray) throws Exception;
}
