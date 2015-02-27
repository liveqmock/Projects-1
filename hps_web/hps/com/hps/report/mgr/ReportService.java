package  com.hps.report.mgr;

import java.text.SimpleDateFormat;
import java.util.List;

public interface ReportService {

	public List<ReportVo> getReportList();

	public ReportVo getReportById(String id);

	public boolean addReport(ReportVo report) throws Exception;
	
	public boolean updateReport(ReportVo report) throws Exception;

	public boolean deleteReportById(String[] idArray) throws Exception;

	public List<ReportVo> getReportListByXY(ReportVo re);
}
