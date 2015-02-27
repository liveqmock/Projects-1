package com.newsoft.common.attach.timer;

import java.io.File;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.common.attach.dao.FrameAttachDAO;
import com.newsoft.common.attach.po.Attach;

/**
 * 清理多余的附件记录的任务
 * 
 * @author mengxw
 * 
 */
@Service
public class ClearDirtyAttachTimer {

	private static final Logger logger = Logger
			.getLogger(ClearDirtyAttachTimer.class);

	@Autowired
	private FrameAttachDAO attachDAO;

	public void clearDirtyAttach() {
		try {
			List<Attach> dirtyList = attachDAO.getDirtyAttachListByObjectId();
			for (Attach attach : dirtyList) {
				int fileType = attach.getFileType();

				if (fileType == 1) {
					// 存储在数据库中的文件
					attachDAO.deleteAttachById(attach.getAttachId());
				} else if (fileType == 2) {
					// 存储在磁盘目录中的文件
					String fileDir = attach.getFileDir();
					String fileName = attach.getFileName();
					File file = new File(fileDir + fileName);
					file.deleteOnExit();
				}
			}
		} catch (Exception e) {
			logger.error("定时器清理冗余的附件信息时出错", e);
			e.printStackTrace();
		}

	}

}
