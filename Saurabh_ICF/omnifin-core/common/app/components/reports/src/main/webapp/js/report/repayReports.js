function generateReport(type)
{
	var reportName=type;
	if(reportName=="deal_repayment_calculation")
	{	
			document.getElementById("repaymentSchedule").action="CPReportsAction.do";
			document.getElementById("repaymentSchedule").submit();	
	}
	if(reportName=="rp_repayment_calculation")
	{	
			document.getElementById("repaymentSchedule").action="CPReportsAction.do";
			document.getElementById("repaymentSchedule").submit();	
	}
}
