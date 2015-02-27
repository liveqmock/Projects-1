package  com.hps.report.mgr;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ReportServiceImpl implements ReportService {
	private Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private ReportDAO reportDAO;
	
	public List<ReportVo> getReportList(){
		try{
		     return reportDAO.getReportList();
		}
		catch(Exception e){
			logger.error("Error when query Report list data",e);
		}
		return null;
	}

	public ReportVo getReportById(String id){
	   try{
		 return reportDAO.getReportById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Report by id: "+id,e);		
		}
		return null;
	}

	public boolean addReport(ReportVo report) throws Exception {
		return reportDAO.addReport(report) > 0;
	}
	
	public boolean updateReport(ReportVo report) throws Exception {
		 return reportDAO.updateReport(report) > 0;	    
	}

	public boolean deleteReportById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = reportDAO.deleteReportById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}

	@Override
	public List<ReportVo> getReportListByXY(ReportVo re) {
		return reportDAO.getReportListByXY(re);
	}	
}
