package com.hps.userscore;

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

import com.hps.level.mgr.ScorelevelService;
import com.hps.report.ReportServer;
import com.hps.report.mgr.ReportVo;
import com.hps.rest.common.RestBase;
import com.hps.userscore.mgr.Userscoredetail;
import com.hps.userscore.mgr.UserscoredetailService;
import com.newsoft.utils.JSONTool;


@Controller
@RequestMapping("/hps/userscore")
public class UserScoreServer extends RestBase{

	private static final Log logger = LogFactory.getLog(UserScoreServer.class);
	
	@Autowired
	private UserscoredetailService usdetailService;
	
	@RequestMapping("/queryScoreInfo")
	public void queryScoreInfo(HttpServletRequest request,String userId,int page,int pageSize,PrintWriter writer) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			Userscoredetail usdetail=new Userscoredetail();
			List<Userscoredetail> usdList=usdetailService.getUserScoreDetailByUser(userId);
			StringBuilder json=new StringBuilder();
			int total=usdList.size();
			int allPage=total/pageSize+1;
			json.append("{\"responseCode\":").append(0).append(",");
			json.append("\"allPage\":").append(allPage).append(",");
			json.append("\"info\":").append("[");
			if(page<allPage) {
				for(int i=0;i<pageSize;i++){
					usdetail=usdList.get(page*pageSize+i);
					json.append('{');
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String time=sd.format(usdetail.getCratetime());
					json.append("\"time\":\"").append(time).append("\",");
					json.append("\"event\":\"").append(usdetail.getLevedes()).append("\",");
					json.append("\"score\":\"").append(usdetail.getScore());
					json.append("},");
				}
			} else {
				int j=(page-1)*pageSize;
				for(int i=0;i<total-j;i++){
					usdetail=usdList.get(j+i);
					json.append('{');
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String time=sd.format(usdetail.getCratetime());
					json.append("\"time\":\"").append(time).append("\",");
					json.append("\"event\":\"").append(usdetail.getLevedes()).append("\",");
					json.append("\"score\":\"").append(usdetail.getScore());
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
		

}
