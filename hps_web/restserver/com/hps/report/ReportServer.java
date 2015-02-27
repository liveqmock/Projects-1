package com.hps.report;

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

import com.hps.level.mgr.Scorelevel;
import com.hps.level.mgr.ScorelevelService;
import com.hps.report.mgr.ReportService;
import com.hps.report.mgr.ReportVo;
import com.hps.rest.common.RestBase;
import com.hps.userscore.mgr.Userscoredetail;
import com.hps.userscore.mgr.UserscoredetailService;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.UUIDTool;

@Controller
@RequestMapping("/hps/report")
public class ReportServer extends RestBase{

	private static final Log logger = LogFactory.getLog(ReportServer.class);
	
	@Autowired
	private UserMgrService userMgrService;
	@Autowired
	private UserscoredetailService userscoredetailService;
	@Autowired
	private ScorelevelService scorelevelService;
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value = "/addReport",method=RequestMethod.POST)
	public void addReport(HttpServletRequest request,PrintWriter writer,@RequestParam("xcode") String xcode,@RequestParam("ycode") String ycode,
			@RequestParam("occtime") String occtime,@RequestParam("type") String type,@RequestParam("userId") String userId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			Scorelevel sc=new Scorelevel();
			sc=scorelevelService.getScorelevelById("BAOLIAO");
			
			Userscoredetail userscoredetail=new Userscoredetail();
			userscoredetail.setId(UUIDTool.getUUID());
			userscoredetail.setUserid(userId);
			userscoredetail.setLevelkey(sc.getLevelkey());
			userscoredetail.setScore(sc.getScore());
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d=sim.parse(occtime);
			userscoredetail.setCratetime(d);
			userscoredetailService.addUserscoredetail(userscoredetail);
			
			User user=new User();
			user=userMgrService.getUserById(userId);
			int point=0;
			if(user.getPoint()==null || user.getPoint()==0){
				point=0;
			} else {
				point=user.getPoint();
			}
			user.setPoint(point+sc.getScore());
			userMgrService.updateUser(user);
			
			ReportVo report=new ReportVo();
			report.setId(UUIDTool.getUUID());
			report.setType(type);
			report.setUserid(userId);
			report.setXcode(xcode);
			report.setYcode(ycode);
			report.setOcctime(d);
			reportService.addReport(report);
			
			map.put("success", true);
			map.put("responseCode", 0);
			map.put("score", sc.getScore());
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	@RequestMapping("/userShareInfo")
	public void userShareInfo(HttpServletRequest request,String xcode,String ycode,PrintWriter writer) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			Date d=new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String time=df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000));
			Date date=df.parse(time);
			double x = Double.valueOf(xcode);
			double xup=x+0.1;
			double xlow=x-0.1;
			double y = Double.valueOf(ycode);
			double yup=y+0.1;
			double ylow=y-0.1;
			ReportVo re=new ReportVo();
			re.setXup(xup);
			re.setXlow(xlow);
			re.setYup(yup);
			re.setYlow(ylow);
			re.setOcctime(date);
			List<ReportVo> reList=reportService.getReportListByXY(re);
			if(reList.size()==0){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			StringBuilder json=new StringBuilder();
			json.append("{\"traffic\":");
			json.append('[');
			for(ReportVo report:reList){
				json.append('{');
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String stime=sd.format(report.getOcctime());
				json.append("\"createTime\":\"").append(stime).append("\",");
				json.append("\"type\":\"").append(report.getType()).append("\",");
				json.append("\"coor_x\":\"").append(report.getXcode()).append("\",");
				json.append("\"coor_y\":\"").append(report.getYcode()).append("\"");
				json.append("},");
			}
			json.deleteCharAt(json.length()-1);
			json.append("],");
			json.append("\"responseCode\":").append(0).append("}");
			writer.write(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	

}
