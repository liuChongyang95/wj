package JavaBean;

public class Report {
    private String reportId;
    private Long reportEnd;
    private String reportType;
    private String reportCont;

    public Report(String reportId,Long reportEnd,String reportType,String reportCont){
        this.reportId=reportId;
        this.reportEnd=reportEnd;
        this.reportType=reportType;
        this.reportCont=reportCont;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Long getReportEnd() {
        return reportEnd;
    }

    public void setReportEnd(Long reportEnd) {
        this.reportEnd = reportEnd;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportCont() {
        return reportCont;
    }

    public void setReportCont(String reportCont) {
        this.reportCont = reportCont;
    }
}
