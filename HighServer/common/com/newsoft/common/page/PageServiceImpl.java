/**
 * 
 */
package com.newsoft.common.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

import com.newsoft.common.mybatis.ObjectDao;
import com.newsoft.utils.JSONTool;

/**
 * @author fengmq
 * 
 */
@Service
public class PageServiceImpl implements PageService {
	@Autowired
	private ObjectDao objectDao;

	/**
	 * 分页查询返回Page对象
	 * 
	 * @param daoClassName
	 *            查询获取数据的DAO类名（包含包名全路径）
	 * @param queryMethodName
	 *            查询获取数据的DAO接口的方法名称（也是DAO.xml配置文件中查询数据的sql的ID）
	 * @param countMethodName
	 *            统计总记录数的DAO接口的方法名称（也是DAO.xml配置文件中查询数据的sql的ID）
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页显示数量
	 * @param params
	 *            查询条件参数，可以是任意类型的对象
	 * @return Page对象
	 */
	public Page<?> pageQuery(HttpServletRequest request, String daoClassName,
			String queryMethodName, String countMethodName, Object params) {
		PageParams pageParams = getPageParamsFromRequst(request);
		Page<?> pageData = objectDao.pageQuery(daoClassName, queryMethodName,
				countMethodName, pageParams, params);
		return pageData;
	}

	/**
	 * grid分页查询
	 * 
	 * @param daoClassName
	 *            查询获取数据的DAO类名（包含包名全路径）
	 * @param queryMethodName
	 *            查询获取数据的DAO接口的方法名称（也是DAO.xml配置文件中查询数据的sql的ID）
	 * @param countMethodName
	 *            统计总记录数的DAO接口的方法名称（也是DAO.xml配置文件中查询数据的sql的ID）
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页显示数量
	 * @param params
	 *            查询条件参数，可以是任意类型的对象
	 * @return 返回jqgrid所需的json格式字符串
	 */
	public String gridPageQuery(HttpServletRequest request,
			String daoClassName, String queryMethodName,
			String countMethodName, Object params) {
		PageParams pageParams = getPageParamsFromRequst(request);
		Page<?> pageData = objectDao.pageQuery(daoClassName, queryMethodName,
				countMethodName, pageParams, params);
		List<?> list = pageData.getResult();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("records", pageData.getTotalCount());// 记录总数
		map.put("total", pageData.getLastPageNumber());// 总页数
		map.put("page", pageParams.getPage());// 当前页数
		map.put("rows", list);// 记录数据
		return JSONTool.toJson(map);
	}

	/**
	 * 将page对象转换成grid所需的JSON格式数据
	 * 
	 * @param page
	 *            Page对象
	 * @return
	 */
	public String page2GridJson(Page<?> page) {
		List<?> list = page.getResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("records", page.getTotalCount());// 记录总数
		map.put("total", page.getLastPageNumber());// 总页数
		map.put("page", page.getThisPageNumber());// 当前页数
		map.put("rows", list);// 记录数据
		return JSONTool.toJson(map);
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	private PageParams getPageParamsFromRequst(HttpServletRequest request) {
		PageParams pageParams = new PageParams();
		// 当前页数
		int page = 1;
		if (request.getParameter("page")==null) {
			page = 1;
		} else{
			page = Integer.parseInt(request.getParameter("page"));
		}
		// 每页显示数量
		int rows = 10;
		if (request.getParameter("rows")==null) {
			rows = 10;
		} else {
			rows = Integer.parseInt(request.getParameter("rows"));
		}
		// grid排序字段
		String sidx = request.getParameter("sidx");
		// grid排序方式,asc or desc
		String sord = request.getParameter("sord");
		ServletWebRequest webRequest = new ServletWebRequest(request, null);
		Map<String, String[]> parameterMap = webRequest.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			if (entry.getValue().length > 0) {
				String key = entry.getKey();
				if (key.startsWith(PageParams.GRID_FILTER_PREFIX)) {
					// 获取名称和类型
					String nt = key.substring(PageParams.GRID_FILTER_PREFIX
							.length());
					// 解析名称和类型
					String[] nameType = nt
							.split(PageParams.FILTERTYPESEPARATOR);
					// 获取值
					String[] value = entry.getValue();
					if (value.length > 0 && !"".equals(value[0])) {
						FilterDataType data = new FilterDataType();
						data.setKey(nameType[0]);
						data.setType(nameType[1]);
						data.setValue(value);
						pageParams.getFilter().put(nt, data);
					}
				}
			}
		}
		pageParams.setPage(page);
		pageParams.setRows(rows);
		pageParams.setSidx(sidx);
		pageParams.setSord(sord);
		return pageParams;
	}
}
