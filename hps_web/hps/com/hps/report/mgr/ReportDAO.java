package  com.hps.report.mgr;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;


public interface ReportDAO extends BaseDAO {
	public List<ReportVo> getReportList();
	
	public List<ReportVo> getReportListByXY(ReportVo report);

	public ReportVo getReportById(String id);

	public int addReport(ReportVo report) throws Exception;
	
	public int updateReport(ReportVo report) throws Exception;

	public int deleteReportById(String id) throws Exception;	
}
