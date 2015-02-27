package com.hps.highwaylife;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hps.highwaylife.mgr.Highwaylife;
import com.hps.highwaylife.mgr.HighwaylifeService;
import com.hps.rest.common.RestBase;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.UUIDTool;

@Controller
@RequestMapping("/hps/highwaylife")
public class HighWayLifeServer extends RestBase{

	private static final Log logger = LogFactory.getLog(HighWayLifeServer.class);
	
	@Autowired
	private HighwaylifeService highWayLifeService;
	@Autowired
	private UserMgrDAO userMgrDAO;
	
	@RequestMapping(value = "/publishHighWayLife",method=RequestMethod.POST)
	public void publishHighWayLife(HttpServletRequest request,PrintWriter writer,@RequestParam("userId") String userId,@RequestParam("type") int type,
			@RequestParam("title") String title,@RequestParam("detail") String detail) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			UserVo user = userMgrDAO.getUserByUserId(userId);
			if(user.getType()!=1){
				map.put("success", false);
				map.put("msg", "非企业用户，无法发布");
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			Highwaylife hwl=new Highwaylife();
			hwl.setId(UUIDTool.getUUID());
			hwl.setUserid(userId);
			hwl.setType(type);
			hwl.setCreatetime(new Date());
			hwl.setTitle(title);
			hwl.setDetail(detail);
			highWayLifeService.addHighwaylife(hwl);
			
			map.put("success", true);
			map.put("responseCode", 0);
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	@RequestMapping("/highWayLife")
	public void highWayLife(HttpServletRequest request,int type,int page,int pageSize,PrintWriter writer) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			Highwaylife hwl=new Highwaylife();
			List<Highwaylife> hwlList=highWayLifeService.getHighwaylifeListByType(type);
			StringBuilder json=new StringBuilder();
			int total=hwlList.size();
			int allPage=total/pageSize+1;
			json.append("{\"responseCode\":").append(0).append(",");
			json.append("\"allPage\":").append(allPage).append(",");
			json.append("\"message\":").append("[");
			if(page<allPage) {
				for(int i=0;i<pageSize;i++){
					hwl=hwlList.get(page*pageSize+i);
					json.append('{');
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String time=sd.format(hwl.getCreatetime());
					json.append("\"time\":\"").append(time).append("\",");
					json.append("\"title\":\"").append(hwl.getTitle()).append("\",");
					json.append("\"info\":\"").append(hwl.getDetail()).append("\",");
					json.append("\"id\":\"").append(hwl.getId()).append("\"");
					json.append("},");
				}
			} else {
				int j=(page-1)*pageSize;
				for(int i=0;i<total-j;i++){
					hwl=hwlList.get(j+i);
					json.append('{');
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String time=sd.format(hwl.getCreatetime());
					json.append("\"time\":\"").append(time).append("\",");
					json.append("\"title\":\"").append(hwl.getTitle()).append("\",");
					json.append("\"info\":\"").append(hwl.getDetail()).append("\",");
					json.append("\"id\":\"").append(hwl.getId()).append("\"");
					json.append("},");
				}
			}
			json.deleteCharAt(json.length()-1);
			json.append("]}");
			writer.write(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	@RequestMapping("/highWayLifeInfo")
	public void highWayLifeInfo(HttpServletRequest request,String messageId,PrintWriter writer) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			Highwaylife hwl=new Highwaylife();
			hwl=highWayLifeService.getHighwaylifeById(messageId);
			map.put("responseCode", 0);
			map.put("title", hwl.getTitle());
			map.put("detail", hwl.getDetail());
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}
