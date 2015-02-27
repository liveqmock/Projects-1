package com.newsoft.common.attach.service.impl;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newsoft.common.attach.dao.FrameAttachDAO;
import com.newsoft.common.attach.po.Attach;
import com.newsoft.common.attach.po.AttachRelation;
import com.newsoft.common.attach.service.FrameAttachService;
import com.newsoft.utils.FileTools;
import com.newsoft.utils.UUIDTool;

/**
 * @author mengxw
 * 
 */
@Service
public class FrameAttachServiceImpl implements FrameAttachService {

	private static final Logger logger = Logger.getLogger(FrameAttachServiceImpl.class);

	@Autowired
	private FrameAttachDAO attachDAO;

	/**
	 * 保存附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public String addAttach(Attach attach) throws Exception {
		attachDAO.addAttach(attach);
		return null;
	}

	/**
	 * 通过主键删除一条附件
	 * 
	 * @param Long
	 *            attachId{return null;}
	 */
	public String deleteAttachByIds(String objectId, String attachIds) throws Exception {
		attachIds = StringUtils.removeEndIgnoreCase(attachIds, ",");
		String[] attIds = attachIds.split(",");
		for (String id : attIds) {
			AttachRelation attachRelation = new AttachRelation();
			attachRelation.setAttachId(id);
			attachRelation.setObjectId(objectId);
			// 删除关联关系
			attachDAO.deleteAttachRelation(attachRelation);

		}
		return null;
	}

	/**
	 * 删除附件的关联关系
	 * 
	 * @param objectId
	 * @throws Exception
	 */
	public void deleteAttachRelationByObjId(String objectId) throws Exception {
		attachDAO.deleteAttachRelationByObjId(objectId);
	}

	/**
	 * 通过主键删除一条附件
	 * 
	 * @param Long
	 *            attachId{return null;}
	 */
	public String deleteAttachByIds(String attachIds) throws Exception {
		attachIds = StringUtils.removeEndIgnoreCase(attachIds, ",");
		String[] attIds = attachIds.split(",");
		for (String id : attIds) {
			attachDAO.deleteAttachById(id);
		}
		return null;
	}

	/**
	 * 删除附件关联信息
	 * 
	 * @param Long
	 *            attachId;
	 */
	public String deleteAttachRelation(AttachRelation attachRelation) throws Exception {
		attachDAO.deleteAttachRelation(attachRelation);
		return null;
	}

	public Attach getAttachById(String attachId) {
		return attachDAO.getAttachById(attachId);
	}

	public List<Attach> getAttachListByObject(String objectId) {
		return attachDAO.getAttachListByObjectId(objectId);
	}

	public String addAttachRelation(AttachRelation attachRelation) throws Exception {
		attachDAO.addAttachRelation(attachRelation);
		return null;
	}

	public String addAttachRelation(String fileIds, String objectId) throws Exception {
		fileIds = StringUtils.removeEndIgnoreCase(fileIds, ",");
		String[] fileIdArray = fileIds.split(",");
		for (String id : fileIdArray) {
			AttachRelation attachRelation = new AttachRelation();
			attachRelation.setAttachId(id);
			attachRelation.setObjectId(objectId);
			attachRelation.setAttachType(0);
			attachDAO.addAttachRelation(attachRelation);
		}
		return null;
	}

	/**
	 * 上传附件到数据库
	 */
	public String uploadAttachToDB(Map<String, MultipartFile> fileMap) throws Exception {
		StringBuffer attachIds = new StringBuffer();
		for (Map.Entry<String, MultipartFile> f : fileMap.entrySet()) {
			MultipartFile file = f.getValue();
			String id = UUIDTool.getUUID();
			String originalFileName = file.getOriginalFilename();
			attachIds.append(id + ",");
			Attach attach = new Attach();
			attach.setAttachId(id);
			attach.setFileName(originalFileName);
			attach.setFileSize(file.getSize());
			attach.setContentType(FileTools.getFileExtentionByOriginalFileName(originalFileName));
			attach.setContent(file.getBytes());
			attach.setFileType(1);
			attach.setCreateTime(new Date());
			addAttach(attach);
		}
		return attachIds.toString();
	}

	public String uploadAttachToDBWithCut(Map<String, MultipartFile> fileMap) throws Exception{
		StringBuffer attachIds = new StringBuffer();
		for (Map.Entry<String, MultipartFile> f : fileMap.entrySet()) {
			MultipartFile file = f.getValue();
			// 裁剪图片开始
			String originalFileName = UUIDTool.getUUID() + "_" + file.getOriginalFilename();
			File files = new File(originalFileName);
			files.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(files);
			Image src = javax.imageio.ImageIO.read(file.getInputStream()); // 构造Image对象
			// 原图
			int width = src.getWidth(null); // 得到原图宽
			int height = src.getHeight(null); // 得到原图高

			// 原图如果宽度大于600就生成宽度是600的小图。
			// BufferedImage yttag = null;
			if (width > 303) {
				double m = width / 303.0000; // 宽固定900
				int ytheight = (int) (height / m);// 得到缩略图的高
				Thumbnails.of(file.getInputStream()).size(303, ytheight).outputQuality(1.0f).toOutputStream(fileOutputStream);
			} else {
				fileOutputStream.write(file.getBytes());
			}
			// 生成小图结束
			fileOutputStream.flush();
			
			FileInputStream fileInputStream = new FileInputStream(files);
			int size = fileInputStream.available();
			byte[] content = new byte[size];
			fileInputStream.read(content);
			fileInputStream.close();
			
			String id = UUIDTool.getUUID();
			attachIds.append(id + ",");
			Attach attach = new Attach();
			attach.setAttachId(id);
			attach.setFileName(originalFileName);
			attach.setFileSize(file.getSize());
			attach.setContentType(FileTools.getFileExtentionByOriginalFileName(originalFileName));
			attach.setContent(content);
			attach.setFileType(1);
			attach.setCreateTime(new Date());
			addAttach(attach);
		}
		return attachIds.toString();
	}
	
	/**
	 * 上传附件到文件夹目录
	 */
	public String uploadAttachToDirectoryWithCut(Map<String, MultipartFile> fileMap, String path) {
		Date currentTime = new Date();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String folder = new Integer(year).toString() + new Integer(month).toString();
		long prefix = currentTime.getTime();
		StringBuffer attachIds = new StringBuffer();
		String storPath = path + File.separator + folder + File.separator;
		for (Map.Entry<String, MultipartFile> f : fileMap.entrySet()) {
				MultipartFile file = f.getValue();
				String originalFileName = prefix + "_" + file.getOriginalFilename();
				File fileDir = new File(storPath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}

				File files = new File(storPath + originalFileName);
				FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(files);
				fileOutputStream.write(file.getBytes());
				fileOutputStream.flush();
				String id = UUIDTool.getUUID();
				attachIds.append(id + ",");
				Attach attachDir = new Attach();
				attachDir.setAttachId(id);
				attachDir.setFileName(originalFileName);
				attachDir.setFileSize(file.getSize());
				attachDir.setContentType(FileTools.getFileExtentionByOriginalFileName(originalFileName));
				attachDir.setFileDir(storPath);
				attachDir.setCreateTime(currentTime);
				attachDir.setFileType(2);
				addAttach(attachDir);

			} catch (FileNotFoundException e) {
				logger.error("上传文件到目录" + storPath + "时发生错误", e);
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("上传文件到目录" + storPath + "时发生错误", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("上传文件到目录" + storPath + "时发生错误", e);
				e.printStackTrace();
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						logger.error("上传文件到目录" + path + folder + "时发生错误", e);
						e.printStackTrace();
					}
				}
			}

		}
		return attachIds.toString();
	}
	
	/**
	 * 上传附件到文件夹目录
	 */
	public String uploadAttachToDirectory(Map<String, MultipartFile> fileMap, String path) {
		Date currentTime = new Date();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String folder = new Integer(year).toString() + new Integer(month).toString();
		long prefix = currentTime.getTime();
		StringBuffer attachIds = new StringBuffer();
		String storPath = path + File.separator + folder + File.separator;
		for (Map.Entry<String, MultipartFile> f : fileMap.entrySet()) {
				MultipartFile file = f.getValue();
				String originalFileName = prefix + "_" + file.getOriginalFilename();
				File fileDir = new File(storPath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}

				File files = new File(storPath + originalFileName);
				FileOutputStream fileOutputStream = null;
			try {
				
				fileOutputStream = new FileOutputStream(files);
				
				if (path.indexOf("MEDIA")==-1) {
					// 裁剪图片开始
					Image src = javax.imageio.ImageIO.read(file.getInputStream()); // 构造Image对象
					// 原图
					int width = src.getWidth(null); // 得到原图宽
					int height = src.getHeight(null); // 得到原图高

					// 原图如果宽度大于600就生成宽度是600的小图。
					// BufferedImage yttag = null;
					if (width > 600) {
						double m = width / 600.0000; // 宽固定900
						int ytheight = (int) (height / m);// 得到缩略图的高
						Thumbnails.of(file.getInputStream()).size(900, ytheight).toOutputStream(fileOutputStream);
					} else {
						fileOutputStream.write(file.getBytes());
					}
					// 生成小图结束
					fileOutputStream.flush();
				} else {
					fileOutputStream.write(file.getBytes());
				}
				

				String id = UUIDTool.getUUID();
				attachIds.append(id + ",");
				Attach attachDir = new Attach();
				attachDir.setAttachId(id);
				attachDir.setFileName(originalFileName);
				attachDir.setFileSize(file.getSize());
				attachDir.setContentType(FileTools.getFileExtentionByOriginalFileName(originalFileName));
				attachDir.setFileDir(storPath);
				attachDir.setCreateTime(currentTime);
				attachDir.setFileType(2);
				addAttach(attachDir);

			} catch (FileNotFoundException e) {
				logger.error("上传文件到目录" + storPath + "时发生错误", e);
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("上传文件到目录" + storPath + "时发生错误", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("上传文件到目录" + storPath + "时发生错误", e);
				e.printStackTrace();
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						logger.error("上传文件到目录" + path + folder + "时发生错误", e);
						e.printStackTrace();
					}
				}
			}

		}
		return attachIds.toString();
	}
}
