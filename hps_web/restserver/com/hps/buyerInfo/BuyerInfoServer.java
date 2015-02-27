package com.hps.buyerInfo;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hps.buyerInfo.mgr.Buyerinfo;
import com.hps.buyerInfo.mgr.BuyerinfoService;
import com.hps.goods.GoodsServer;
import com.hps.goods.mgr.Goods;
import com.hps.goods.mgr.GoodsService;
import com.hps.level.mgr.Scorelevel;
import com.hps.report.mgr.ReportVo;
import com.hps.rest.common.RestBase;
import com.hps.userscore.mgr.Userscoredetail;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.UUIDTool;

@Controller
@RequestMapping("/hps/buyerinfo")
public class BuyerInfoServer extends RestBase{

	private static final Log logger = LogFactory.getLog(BuyerInfoServer.class);
	
	@Autowired
	private BuyerinfoService buyerInfoService;
	@Autowired
	private UserMgrDAO userMgrDAO;
	@Autowired
	private GoodsService goodsService;
	@Value("${imagePath}")
	private String imagePath = "";
	
	@RequestMapping(value = "/buyGoods",method=RequestMethod.POST)
	public void buyGoods(HttpServletRequest request,PrintWriter writer,@RequestParam("userId") String userId,@RequestParam("goodsId") String goodsId,
			@RequestParam("realName") String realName,@RequestParam("address") String address,@RequestParam("phone") String phone,
			@RequestParam("postCode") String postCode) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			UserVo user = userMgrDAO.getUserByUserId(userId);
			Goods good = goodsService.getGoodsById(goodsId);
			if(user.getPoint()<good.getScore()){
				map.put("success", false);
				map.put("msg", "积分不足，购买失败");
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			Buyerinfo info=new Buyerinfo();
			info.setId(UUIDTool.getUUID());
			info.setGoodsid(goodsId);
			info.setUserid(userId);
			info.setPhone(phone);
			info.setPostcode(postCode);
			info.setAddress(address);
			info.setRealname(realName);
			buyerInfoService.addBuyerinfo(info);
			
			user.setPoint(user.getPoint()-good.getScore());
			userMgrDAO.updateUser(user);
			
			map.put("score", user.getPoint());
			map.put("success", true);
			map.put("responseCode", 0);
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	@RequestMapping("/orderGoods")
	public void orderGoods(HttpServletRequest request,String userId,PrintWriter writer) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			String imageDir=new String();
			List<Buyerinfo> blist=buyerInfoService.getAllInfoById(userId);
			StringBuilder json=new StringBuilder();
			json.append("{\"responseCode\":").append(0).append(",");
			json.append("\"orderGoodsInfo\":").append("[");
			for(Buyerinfo buy:blist){
				if(buy.getImageName()!=null && !buy.getImageName().equals("")){
					imageDir=buy.getImagePath().replace(imagePath, "\\image")+buy.getImageName();
				}
				json.append('{');
				json.append("\"goodsInfo\":{");
				json.append("\"goodsId\":\"").append(buy.getGoodsid()).append("\",");
				json.append("\"score\":").append(buy.getScore()).append(",");
				json.append("\"image\":\"").append(imageDir).append("\"},");
				json.append("\"userInfo\":{");
				json.append("\"name\":\"").append(buy.getRealname()).append("\",");
				json.append("\"address\":\"").append(buy.getAddress()).append("\",");
				json.append("\"phoneNum\":\"").append(buy.getPhone()).append("\",");
				json.append("\"postcode\":\"").append(buy.getPostcode()).append("\"");
				json.append("},");
				json.append("\"type\":").append(buy.getType()).append("},");
			}
			json.deleteCharAt(json.length()-1);
			json.append("]}");
			writer.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}
