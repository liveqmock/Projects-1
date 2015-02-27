package  com.hps.cms.mgr;

import java.util.List;

import com.hps.cms.mgr.Cmscontent;
import com.newsoft.common.log.LogParam;
import com.newsoft.common.mybatis.BaseDAO;


public interface CmscontentDAO extends BaseDAO {
	public List<Cmscontent> getCmscontentList();

	public Cmscontent getCmscontentById(String id);

	@LogParam(logDes = "发布文章", operateModule = "文章管理")
	public int addCmscontent(Cmscontent cmscontent) throws Exception;
	
	@LogParam(logDes = "更新文章", operateModule = "文章管理")
	public int updateCmscontent(Cmscontent cmscontent) throws Exception;

	@LogParam(logDes = "删除文章", operateModule = "文章管理")
	public int deleteCmscontentById(String id) throws Exception;	
}
