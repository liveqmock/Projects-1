package com.newsoft.common.attach.dao;

import com.newsoft.common.attach.po.FrameEditorAttach;
import com.newsoft.common.mybatis.BaseDAO;

/**
 * 编辑器上传的附件管理DAO
 * 
 * @author mengxw
 * 
 */
public interface FrameEditorAttachDAO extends BaseDAO {

	/**
	 * 保存附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public int addAttach(FrameEditorAttach attach) throws Exception;

	/**
	 * 通过主键删除一条附件
	 * 
	 * @param attachId
	 * @throws Exception
	 */
	public void deleteAttachById(String attachId) throws Exception;

	/**
	 * 根据ID获取附件
	 * 
	 * @param attachId
	 *            附件id
	 * @return
	 */
	public FrameEditorAttach getAttachById(String attachId);

	public void updateAttach(FrameEditorAttach attach);

}
