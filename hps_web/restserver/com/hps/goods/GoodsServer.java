package com.hps.goods;

import java.io.PrintWriter;
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

import com.hps.goods.mgr.Goods;
import com.hps.goods.mgr.GoodsService;
import com.hps.rest.common.RestBase;
import com.newsoft.common.attach.po.Attach;
import com.newsoft.utils.JSONTool;

@Controller
@RequestMapping("/hps/goods")
public class GoodsServer extends RestBase{

	private static final Log logger = LogFactory.getLog(GoodsServer.class);
	
	@Autowired
	private GoodsService goodsService;
	@Value("${imagePath}")
	private String imagePath = "";
	
	@RequestMapping("/goodsStore")
	public void goodsStore(HttpServletRequest request,int page,int pageSize,PrintWriter writer) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!super.validate(request.getHeader("X-APP-Token"))){
				map.put("msg", "验证错误，非法请求");
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			Goods good=new Goods();
			List<Goods> goodList=goodsService.getGoodsImageList();
			String imageDir=new String();
			String largeImageDir=new String();
			StringBuilder json=new StringBuilder();
			int total=goodList.size();
			int allPage=total/pageSize+1;
			json.append("{\"responseCode\":").append(0).append(",");
			json.append("\"allPage\":").append(allPage).append(",");
			json.append("\"goodsInfo\":").append("[");
			if(page<allPage) {
				for(int i=0;i<pageSize;i++){
					good=goodList.get(page*pageSize+i);
					if(good.getImage()!=null && !good.getImage().equals("")){
						imageDir=good.getImagePath().replace(imagePath, "\\image")+good.getImageName();
					}
					if(good.getLargeimage()!=null && !good.getLargeimage().equals("")){
						largeImageDir=good.getLargeImagePath().replace(imagePath, "\\image")+good.getLargeImageName();
					}
					json.append('{');
					json.append("\"goodsId\":\"").append(good.getId()).append("\",");
					json.append("\"image\":\"").append(imageDir).append("\",");
					json.append("\"largeImage\":\"").append(largeImageDir).append("\",");
					json.append("\"title\":\"").append(good.getTitle()).append("\",");
					json.append("\"score\":").append(good.getScore()).append(",");
					json.append("\"detail\":\"").append(good.getDetail()).append("\",");
					json.append("\"type\":").append(good.getType());
					json.append("},");
				}
			} else {
				int j=(page-1)*pageSize;
				for(int i=0;i<total-j;i++){
					good=goodList.get(j+i);
					if(good.getImage()!=null && !good.getImage().equals("")){
						imageDir=good.getImagePath().replace(imagePath, "\\image")+good.getImageName();
					}
					if(good.getLargeimage()!=null && !good.getLargeimage().equals("")){
						largeImageDir=good.getLargeImagePath().replace(imagePath, "\\image")+good.getLargeImageName();
					}
					json.append('{');
					json.append("\"goodsId\":\"").append(good.getId()).append("\",");
					json.append("\"image\":\"").append(imageDir).append("\",");
					json.append("\"largeImage\":\"").append(largeImageDir).append("\",");
					json.append("\"title\":\"").append(good.getTitle()).append("\",");
					json.append("\"score\":").append(good.getScore()).append(",");
					json.append("\"detail\":\"").append(good.getDetail()).append("\",");
					json.append("\"type\":").append(good.getType());
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
