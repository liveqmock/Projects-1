package com.newsoft.common.attach.dao;

import java.util.List;
import com.newsoft.common.attach.po.Attach;
import com.newsoft.common.attach.po.AttachRelation;
import com.newsoft.common.mybatis.BaseDAO;

/**
 * 附件管理DAO
 * @author mengxw
 * 
 */
public interface FrameAttachDAO extends BaseDAO {

	/**
	 * 保存附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public int addAttach(Attach attach) throws Exception;

	/**
	 * 保存附件与业务信息的关联关系
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public int addAttachRelation(AttachRelation attachRelation)
			throws Exception;

	/**
	 * 通过主键删除一条附件
	 * 
	 * @param attachId
	 * @throws Exception
	 */
	public void deleteAttachById(String attachId) throws Exception;

	/**
	 * 删除附件的关联关系
	 * 
	 * @param attachRelation
	 * @throws Exception
	 */
	public void deleteAttachRelation(AttachRelation attachRelation)
			throws Exception;
	
	/**
	 * 删除附件的关联关系
	 * 
	 * @param objectId
	 * @throws Exception
	 */
	public void deleteAttachRelationByObjId(String objectId)
			throws Exception;
	
	/**
	 * 通过内容id获取该内容的所有附件信息
	 * 
	 * @param objectId
	 * @return
	 */
	public List<Attach> getAttachListByObjectId(String objectId);
	
	/**
	 * 通过附件ID获取关联关系列表
	 * 
	 * @param attachId
	 * @return
	 */
	public List<AttachRelation> getAttachRelationListByAttachId(String attachId);

	/**
	 * 根据ID获取附件
	 * 
	 * @param attachId
	 *            附件id
	 * @return
	 */
	public Attach getAttachById(String attachId);
	
	
	/**
	 * 查询附件的脏数据
	 * @return
	 */
	public List<Attach> getDirtyAttachListByObjectId() throws Exception;

}
