  
function getLastDateOfMonth(Year,Month){
	var date =(new Date((new Date(Year, Month,1))-1));
	var dd=(date.getMonth()+ 1);
	if(dd==1 ||dd==2 ||dd==3 ||dd==4 ||dd==5 ||dd==6 ||dd==7 ||dd==8 ||dd==9){
		dd='0'+dd;
	}
 	var lastDate= date.getDate()+ '-'+dd+ '-'+date.getFullYear();
 	return lastDate;
 }

function checkReportsValidation()
			{
				var reportName=document.getElementById("reportName").value;	
				if(reportName=="select")
				{	alert("Report Type Required");
					document.getElementById('reportName').focus();
					return false;
				}
			//alert(reportName);
				/*changes by shubham*/
				if(document.getElementById("passwordPdf")){
					var passwordPdf=document.getElementById("passwordPdf").value;
					var reportformat=document.getElementById("reportformat").value;
					if(passwordPdf=='Y' && reportformat!='P'){
						alert("Only Report Format PDF should be Password Protected.\n");
						return false;
					}
					}
				/*end*/
				if(reportName=="Repayment_Schedule_for_Partners" || reportName=="Partners_Payout_Report" || reportName=="Booking_Reports_For_Partners" || reportName=="Cumulative_RSP_Report_for_Payment")
				{	
					if(reportName=="Repayment_Schedule_for_Partners"){
						var partnerName=document.getElementById("partnerName").value;	
						var loan_NO=document.getElementById("loan_NO").value;	
						if(partnerName==''){
							alert("Please Select Partner");
							document.getElementById('partnerName').focus();
							return false;
						}
						if(loan_NO==''){
							alert("Please Select Loan No");
							document.getElementById('loan_NO').focus();
							return false;
						}
						
					}
					
					if(reportName=="Partners_Payout_Report"){
						var asonDate=document.getElementById("asonDate").value;	
						var partnerType=document.getElementById("partnerType").value;
						var lbxPartnerId=document.getElementById("lbxPartnerId").value;
						if(partnerType=='Specific'){
							if(lbxPartnerId==''){
								alert("Please Select The Partner Code");
								document.getElementById('partnerCode').focus();
								return false;
							}
						}
						if(asonDate==''){
							alert("Please Select Last Date of Month");
							document.getElementById('asonDate').focus();
							return false;
						}
						var date=asonDate;
						var dd=date.substring(0,2);
						var mm=date.substring(3,5);
						var yy=date.substring(6,10);
						var last=getLastDateOfMonth(yy,mm);
						if(date!=last){
							alert("Select Only Last Date of Month");
							document.getElementById('asonDate').focus();
							return false;						
						}
					}
					if(reportName=="Booking_Reports_For_Partners" || reportName=="Cumulative_RSP_Report_for_Payment"){
						var fromDate=document.getElementById("fromDate").value;	
						var toDate=document.getElementById("toDate").value;	
						var partnerType=document.getElementById("partnerType").value;
						var lbxPartnerId=document.getElementById("lbxPartnerId").value;
						if(fromDate==''){
							alert("Please Select Starting Date");
							document.getElementById('fromDate').focus();
							return false;
						}
						if(toDate==''){
							alert("Please Select Ending Date");
							document.getElementById('toDate').focus();
							return false;
						}
						if(partnerType=='Specific'){
							if(lbxPartnerId==''){
								alert("Please Select The Partner Code");
								document.getElementById('partnerCode').focus();
								return false;
							}
						}
					}
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
				}
				
				//alert('reportName111::::'+reportName);
				if(reportName=="industryRiskReport" ||reportName=="concentrationRiskReport" ||reportName=="rpt_audit_report" ||reportName=="geographical_Risk_Report" ||reportName=="maturity_Profile_Report" ||reportName=="product_Risk_Report")
				{
					
					var asonDate=document.getElementById("asonDate").value;	
					if(asonDate=='')
					{
						alert("As on Date is required.");
						return false;
					}
					else
					{
						if(reportName!="rpt_audit_report")
						{
							var cutOffDateList=document.getElementById("eodList").value;
							//alert(cutOffDateList);
							//alert("cutOffDateList : " + cutOffDateList);
							var dateList = cutOffDateList.split('|');
							var stri = '';
							var i=0;
							for(i; i<dateList.length; i++)
							{
								if(dateList[i]==asonDate)
									break;
							}
							if(i==dateList.length)
							{
								alert("Invalid Cut Off Date,Only last date of month is valid,which should be greater than or equal to "+dateList[0]);
								return false;
							}
						}
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}					
				}
				var rangeLimit=document.getElementById("dateRengeLimit").value;
				if(rangeLimit=='')
					rangeLimit='0';
				var dateRengeLimit=parseFloat(rangeLimit);
				//sidharth
				var RangeLimitSpecial=document.getElementById("DateRangeLimitSpecial").value;
				if(RangeLimitSpecial=='')
					RangeLimitSpecial='0';
				var DateRangeLimitSpecial=parseFloat(RangeLimitSpecial);
				//alert("dateRengeLimit  :  "+dateRengeLimit);
				var basePath=document.getElementById("contextPath").value;
				var formatD=document.getElementById("formatD").value;
				if(reportName=="receiptMemoReport")
				{				
						var  instrumentId=document.getElementById("instrumentId").value;
						if(instrumentId=="")
						{	
							alert("Receipt No is required.");
							return false;
						}
						else
						{
							var contextPath = document.getElementById("contextPath").value;
							document.getElementById("reportid").action=contextPath+"/AllReports.do";
							document.getElementById("reportid").submit();
						 	return true;
						}
				}
				if(reportName=="Disbursal_Done_During_a_Period"||reportName=="earlyClosureReport")
					{
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					}
				//abhishek start
				if(reportName=="earlyClosureReport")
					{
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;
					if(branch=="specific" && branch_name=="")
						{
						alert("branch Name is required");
						document.getElementById("dealButton").focus();
						return false;
						}
					var fromDate=document.getElementById("fromDate").value;
	                var toDate=document.getElementById("toDate").value;
					var msg="";
	                if(fromDate=="" && toDate=="")
	                    msg=msg+"*Date Range is Required.";
	                if(msg!="")
	                {
	                    alert(msg);
	                    return false;
	                }
	               
	                if(fromDate!="" && toDate!="")
	                {
	                    var formatD=document.getElementById("formatD").value;
	                    var dt1=getDateObject(fromDate,formatD.substring(2,3));
	                    var dt2=getDateObject(toDate,formatD.substring(2,3));
	                    if(dt2<dt1)
	                    {
	                        alert("To Date can't be less than From Date.");
	                        return false;
	                    }
	                }
					}
				
			
			
                 //end by abhishk sharma
				
				
// Kumar Aman Changes Started				
				
				if(reportName == "IKF_DUE_List_Report" || reportName == "IKF_Bucket_Wise_Report" || reportName == "user_Detail_Report_all" || reportName == "PDC_Replinishment_letter" || reportName=="BOUNCE_LETTER"  || reportName == "Repricing_Reduction_ROI" || reportName == "Repricing_Inc_ROI" || reportName == "Yearly_Interest_Certificate" )
					
					{
					var branch=document.getElementById("branch4").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					}
					if(reportName == "PDC_Replinishment_letter" || reportName=="BOUNCE_LETTER"  || reportName == "Repricing_Reduction_ROI" || reportName == "Repricing_Inc_ROI" || reportName == "Yearly_Interest_Certificate"){

						var fromDate="";
						var toDate="";
						if(reportName == "Yearly_Interest_Certificate"){
					 fromDate=document.getElementById("intCerFromDate").value;	
					 toDate=document.getElementById("intCerToDate").value;	
						}else{
					 fromDate=document.getElementById("fromDate").value;
					 toDate=document.getElementById("toDate").value;
						}
					var loan_no=document.getElementById("loanno").value;
					var loan_from=document.getElementById("from").value;
					var loan_from_id=document.getElementById("lbx_loan_from_id").value;
					var loan_to=document.getElementById("to").value;
					var loan_to_id=document.getElementById("lbx_loan_to_id").value;
					var msg="";
					if(fromDate=="" && toDate=="")
						msg=msg+"*Date Range is Required.";
					if(msg!="")
					{
						alert(msg);
						return false;
					}
					
					if(fromDate!="" && toDate!="")
					{
						var formatD=document.getElementById("formatD").value;
						var dt1=getDateObject(fromDate,formatD.substring(2,3));
						var dt2=getDateObject(toDate,formatD.substring(2,3));
						if(dt2<dt1)
						{
							alert("To Date can't be less than From Date."); 
							return false;
						}
					}
					
					if((fromDate=="" && toDate!="")||(fromDate!="" && toDate==""))
					{
						alert("Please Enter From Date and To Date Simultaneously.");
						return false;
					}
					if(loan_no=="R" && reportName!="RTGS_NEFT_From_Bank")
					{	
						if(loan_to=="" && loan_from=="")
						{
							alert("Please enter Loan Range.");
							document.getElementById('loanFromButton').focus();
							return false;
						}
						if(loan_from && loan_to=="")
						{
							alert("Please enter Loan To also.");
							return false;
						}
						if(loan_to && loan_from=="")
						{
							alert("Please enter Loan From also.");
							return false;
						}
					
						if(parseFloat(loan_from_id) > parseFloat(loan_to_id))
						{	
							alert("Initial Loan number should be less than or equal to Final Loan number.");
							return false;				
						}
						
			
						
					}
				
				}
				
				
				
// Kumar Aman Changes Ended
				
				
				
				
				//alert("n2");
				if(reportName=="NHB_Monthly_Report" || reportName=="exposure_calculation"|| reportName=="loan_dump_report" ||reportName=="customer_bank_branch_report" ||reportName=="instrument_capturing_stage")
{						
					if(reportName!="NHB_Monthly_Report"){// add by saorabh
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}
					}
					//changes done by amandeep
					if(reportName=="loan_dump_report"){
						
						var fromDate=document.getElementById("fromDate").value;
						var toDate=document.getElementById("toDate").value;
						var msg="";
						if(fromDate=="" && toDate=="")
							msg=msg+"*Date Range is Required.";
						if(msg!="")
						{
							alert(msg);
							return false;
						}
						
						if(fromDate!="" && toDate!="")
						{
							var formatD=document.getElementById("formatD").value;
							var dt1=getDateObject(fromDate,formatD.substring(2,3));
							var dt2=getDateObject(toDate,formatD.substring(2,3));
							if(dt2<dt1)
							{
								alert("To Date can't be less than From Date."); 
								return false;
							}
						}
						
						if((fromDate=="" && toDate!="")||(fromDate!="" && toDate==""))
						{
							alert("Please Enter From Date and To Date Simultaneously.");
							return false;
						}
						
					}
					if(reportName=="pre_emi_to_be_accrued" || reportName=="Repricing_Letter_for_increased_ROI_interest_rate_policy")
						{
						var formatD=document.getElementById("formatD").value;
						
						var businessdate=document.getElementById("businessdate").value;
						var Date=document.getElementById("asonDate").value;
							if(document.getElementById("asonDate").value=='')
							{
									Date='';
							}
						
							else
							{
								Date=getDateObject(Date,formatD.substring(2, 3));
							}
							
							businessdate=getDateObject(businessdate,formatD.substring(2, 3));
						
							if(Date=="")
							{	alert("Please Select As on Date.");
								return false;
							}
							if((Date < businessdate)){
								alert(" As On Date cannot be less than business date ");
							    return false;
							  }
						
						
						}
// amandeep changes end
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
			}

				//alert("n3");
				if(reportName=="Future_EMI_Receivable")
				{
					var checkId=document.getElementsByName("detailOrSum");
					var from_date=document.getElementById("fromDate").value;
					var to_date=document.getElementById("toDate").value;
					var detailOrSum="";
					var msg="";
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					for(var i=0;i<checkId.length;i++)
					{
					 	if( checkId[i].checked==true)
					 	{
						 	detailOrSum=checkId[i].value;
					  	}
					}				
					 	if(detailOrSum=="")
							msg+="*Type is required\n";
					 	if(from_date=="")
							msg+="*From Date is required\n";
					 	if(to_date=="")
							msg+="*To Date is required\n";
						if(msg !="")
						{
							alert(msg);
							return false;
						}
						else
						{
							var contextPath = document.getElementById("contextPath").value;
							document.getElementById("reportid").action=contextPath+"/AllReports.do";
							document.getElementById("reportid").submit();
						 	return true;
						}
				}
				// change start by priyanka 
				if(reportName=="GST_Reports"){
					
					var from_date=document.getElementById("fromDate").value;
					var to_date=document.getElementById("toDate").value;
					
					var msg="";
								
					 
					 	if(from_date=="")
							msg+="*From Date is required\n";
					 	if(to_date=="")
							msg+="*To Date is required\n";
						if(msg !="")
						{
							alert(msg);
							return false;
						}
						
					var rangeLimit=document.getElementById("dateRengeLimit").value;
					if(rangeLimit=='')
						rangeLimit='0';
					var dateRengeLimit=parseFloat(rangeLimit);
					var formatD=document.getElementById("formatD").value;
					var dt1=getDateObject(from_date,formatD.substring(2,3));
					var dt2=getDateObject(to_date,formatD.substring(2,3));
					if(dt2<dt1)
					{
						alert("To Date can't be less than From Date."); 
						return false;
					}
					var day=dateDifference(dt1,dt2);
					if(day>dateRengeLimit)
					{
						alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
						return false;
					}
				}
				// end by priyanka
				//alert("n4");
				if(reportName=="ecs_notpad")
				{
					var sponsorDate=document.getElementById("sponsorDate").value;
					var sponsor=document.getElementById("sponsor").value;
					var msg="";
					if(sponsorDate=="")
						msg="Presentation Date is Required.\n";
					if(sponsor=="")
						msg=msg+"Sponsor Code is Required.";
					if(msg !="")
					{
						alert(msg);
						return false;
					}
					else
					{
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}
				}
				//alert("n5");
				if(reportName=="interest_certificate" || reportName=="PRODUCT_INTEREST_CERTIFICATE")
				{
					var loan_no=document.getElementById("specificLoanNo").value;
					var intCerFromDate=document.getElementById("intCerFromDate").value;
					var intCerToDate=document.getElementById("intCerToDate").value;
					var msg="";
					if(loan_no=="")
						msg="Loan No is Required.\n";
					if(intCerFromDate=="")
						msg="From Date is Required.\n";
					if(intCerToDate=="")
						msg=msg+"To Date is Required.";
					if(msg !="")
					{
						alert(msg);
						return false;
					}
					else
					{
						var formatD=document.getElementById("formatD").value;
						var dt1=getDateObject(intCerFromDate,formatD.substring(2,3));
						var dt2=getDateObject(intCerToDate,formatD.substring(2,3));
						if(dt2<dt1)
						{
							alert("To Date can't be less than From Date."); 
							return false;
						}
//						var day=dateDifference(dt1,dt2);
//						if(day>dateRengeLimit)
//						{
//							alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
//							return false;
//						}
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}
				}
				//alert("n6");
				//Nishant Space starts
				if(reportName=="balance_certificate_report")
				{
					var loan_no=document.getElementById("specificLoanNo").value;
					var balConfFromDate=document.getElementById("balConfFromDate").value;
					var balConfToDate=document.getElementById("balConfToDate").value;
					var asOnDate=document.getElementById("installmentDate").value;
					var msg="";
					if(loan_no=="")
						msg="Loan No is Required.\n";
					if(asOnDate=="")
						msg=msg+"As On Date is Required.\n";
					if(balConfFromDate=="")
						msg=msg+"From Date is Required.\n";
					if(balConfToDate=="")
						msg=msg+"To Date is Required.";
					if(msg !="")
					{
						alert(msg);
						return false;
					}
					else
					{
						var formatD=document.getElementById("formatD").value;
						var dt1=getDateObject(balConfFromDate,formatD.substring(2,3));
						var dt2=getDateObject(balConfToDate,formatD.substring(2,3));
						if(dt2<dt1)
						{
							alert("To Date can't be less than From Date."); 
							return false;
						}
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}
				}
				//Nishant space ends
				if(reportName=="Sanction Letter Report"  ||reportName=="Sanctionletter(MM)" || reportName=="letter_of_offer_cum_acceptance"  ||reportName=="Sanction Letter Report For X 10")
				{
					document.getElementById("reportformat").value='P';
					var specificDealNo=document.getElementById("specificDealNo").value;
					if(specificDealNo=="")
					{	alert("Deal Number is Required");
					
					return false;
					}
					else
					{	var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
					}
					
				}
				if( reportName=="knockoff_1" || reportName=="manual_advice"  )
				{
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
				}
				//alert("n7");
				if(reportName=="deposited_instrument_bank_detail")
				{
					var  loanno=document.getElementById("specificLoanNo").value;
					var  Date=document.getElementById("installmentDate").value;
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					if(Date=="" )
					{	
						alert("Date is Required.");
						return false;
					}				
					else
					{	var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
					}
				}
				//alert("n8");
	//swati
				if(reportName=="DISBURSAL_ADVICE")
				{
					var  loanno=document.getElementById("specificLoanNo").value;
					
					if(loanno=="" )
					{	
						alert("Loan number is Required.");
						return false;
					}				
					else
					{	
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
					}
				}
					   

				if(reportName=="Insurance_details")
				{
					var loan_no=document.getElementById("loanno").value;
					var loan_from=document.getElementById("from").value;
					var loan_from_id=document.getElementById("lbx_loan_from_id").value;
					var loan_to=document.getElementById("to").value;
					var loan_to_id=document.getElementById("lbx_loan_to_id").value;
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					if(loan_no=="R" && reportName!="RTGS_NEFT_From_Bank")
					{	
						if(loan_to=="" && loan_from=="")
						{
							alert("Please enter Loan Range.");
							document.getElementById('loanFromButton').focus();
							return false;
						}
						if(loan_from && loan_to=="")
						{
							alert("Please enter Loan To also.");
							return false;
						}
						if(loan_to && loan_from=="")
						{
							alert("Please enter Loan From also.");
							return false;
						}
					
						if(parseFloat(loan_from_id) > parseFloat(loan_to_id))
						{	
							alert("Initial Loan number should be less than or equal to Final Loan number.");
							return false;				
						}
						else
						{
							var contextPath = document.getElementById("contextPath").value;
							document.getElementById("reportid").action=contextPath+"/AllReports.do";
							document.getElementById("reportid").submit();
						 	return true;
						}
			
						
					}				
					else
					{	var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
					}
					
				}
			if(reportName=="RTGS_NEFT_From_Bank"){
					
					var approvalDateFrom=document.getElementById("approvalDateFrom").value;
					var approvalDateTo=document.getElementById("approvalDateTo").value;
					var rtgsNeft = document.getElementById("rtgsNeft").value;
					var msg="";
					if(approvalDateFrom=="" && approvalDateTo=="")
						msg=msg+"*Date Range is Required.";
					if(msg!="")
					{
						alert(msg);
						return false;
					}
					
					if(approvalDateFrom!="" && approvalDateTo!="")
					{
						var formatD=document.getElementById("formatD").value;
						var dt1=getDateObject(approvalDateFrom,formatD.substring(2,3));
						var dt2=getDateObject(approvalDateTo,formatD.substring(2,3));
						if(dt2<dt1)
						{
							alert("To Date can't be less than From Date."); 
							return false;
						}
					}	
					if(rtgsNeft==''){
						alert("Please Select Report Type First!"); 
						return false;
					}
					if(approvalDateFrom==''){
						alert("From Disbursal Date(DD-MM-YYYY) Is Required!"); 
							return false; 
					}
					if(approvalDateTo==''){
						alert("To Disbursal Date(DD-MM-YYYY) Is Required!"); 
							return false; 
					}
				}	
				
				//alert("n9");
				if(reportName=="SD_reports")
				{
					var fromdate=document.getElementById("fromdateforsd").value;
					var todate=document.getElementById("todateforsd").value;
					var asOndate=document.getElementById("AsOnDateForSD").value;
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}		
					
					if(fromdate=='' && todate=='' && asOndate=='N')
					{
						alert("Select Either Date Range or As On Date Field.");
						return false;
					}
					if(fromdate && todate=="")
					{	alert("Please select To Date Also.");
						return false;
					}
					if(todate && fromdate=="")
					{	alert("Please select From Date Also.");
						return false;
					}
					var formatD=document.getElementById("formatD").value;
					var dt1=getDateObject(fromdate,formatD.substring(2,3));
					var dt2=getDateObject(todate,formatD.substring(2,3));
					if(dt2<dt1)
					{
						alert("To Date can't be less than From Date."); 
						return false;
					}
					var day=dateDifference(dt1,dt2);
					if(day>dateRengeLimit)
					{
						alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
						return false;
					}
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;				
				}
				//alert("n10");
				if(reportName=="welcome_letter_report" || reportName=="AUwelcome_report" || reportName=="welcome_letter_report1" || reportName == "IKF_welcome_letter_report" || reportName == "welcome_letter_report_colanding" )
				{
					document.getElementById("reportformat").value='P';
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	alert("Select Branch Name.");
						document.getElementById("dealButton").focus();
						return false;
					}		
					var fromdate=document.getElementById("approvalDateFrom").value;
					var todate=document.getElementById("approvalDateTo").value;
					var loanNumber=document.getElementById("loanNumber").value;
					if(fromdate=='' && todate=='' && loanNumber=='')
					{
						alert("Select Either Date Range or Loan Number Field.");
						return false;
					}
					if(fromdate && todate=="")
					{	alert("Please select To Date Also.");
						return false;
					}
					if(todate && fromdate=="")
					{	alert("Please select From Date Also.");
						return false;
					}
					var formatD=document.getElementById("formatD").value;
					var dt1=getDateObject(fromdate,formatD.substring(2,3));
					var dt2=getDateObject(todate,formatD.substring(2,3));
					if(dt2<dt1)
					{
						alert("To Date can't be less than From Date."); 
						return false;
					}
					var day=dateDifference(dt1,dt2);
					if(day>dateRengeLimit)
					{
						alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
						return false;
					}
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
				}	

				
				if(reportName=="waiveoff_pending_reject")
					{
						var branch=document.getElementById("branch").value;
						var branch_name=document.getElementById("lbxBranchId").value;	
						if(branch=="Specific" && branch_name=="")
						{	
							alert("Branch Name is Required");
							document.getElementById("dealButton").focus();
							return false;
						}
						else
						{
							var contextPath = document.getElementById("contextPath").value;
							document.getElementById("reportid").action=contextPath+"/AllReports.do";
							document.getElementById("reportid").submit();
						 	return true;
						}
						
					}
				if(reportName=="paymentReport")
				{
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}
					else
					{
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}
					
				}
				//alert("n11");
				if(reportName=="batch_report" )
				{
					var presentationDate=document.getElementById("presentationDateForBatch").value;
					var batchNo=document.getElementById("batchNo").value;
					var batchGenerates=document.getElementById("batchGenerates").value;					
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					if(presentationDate=='' && batchNo=='' )
					{
						alert("Select Either Presentation Date or Batch Number Field.");
						return false;
					}
					if((presentationDate=='' || batchGenerates=='' ) && batchNo=="" )
					{
						alert("Presentation Date and Batch Generates both are mandatory.");
						return false;
					}
					else
					{
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}							
				}	
				//alert("n12");
				if(reportName=="EMI_DUE_Report" ||reportName=="Recon_Reports" || reportName=="maturity_report" ||  reportName=="asset_master_report" || reportName=="ACH_TRANSACTION")
				{
					var fromdate=document.getElementById("dateFrom").value;
					var todate=document.getElementById("dateTo").value;
					var loanNumber=document.getElementById("specificLoanNo").value;
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					if(fromdate=='' && todate=='' && loanNumber=='')
					{
						alert("Select Either Date Range or Loan Number Field.");
						return false;
					}
					if(fromdate && todate=="")
					{
						alert("Please select To Date Also.");
						return false;
					}
					if(todate && fromdate=="")
					{	
						alert("First select From Date Also.");
						return false;
					}
					var formatD=document.getElementById("formatD").value;
					var dt1=getDateObject(fromdate,formatD.substring(2,3));
					var dt2=getDateObject(todate,formatD.substring(2,3));
					if(dt2<dt1)
					{
						alert("To Date can't be less than From Date."); 
						return false;
					}
					var day=dateDifference(dt1,dt2);
					if(day>dateRengeLimit)
					{
						alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
						return false;
					}
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
				}	
				//alert("n13");
				if(reportName=="billed_EMIs_report")
				{
					var  loanno=document.getElementById("specificLoanNo").value;
					var  Date=document.getElementById("installmentDate").value;
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
				if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					if(Date=="" )
					{	
						alert("Billing Date is Required");
						return false;
					}					
					else
					{	
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
						return true;
					}
				}	
				//alert("n14");
				if(reportName=="OD_Calculation_Report" || reportName=="LPP_calculation_report" ||reportName=="OD_SIMULATION_REPORT"||reportName=="OD_Intrest_For_All_Loans" ||reportName=="LPP_SIMULATION_REPORT" ||reportName=="OD_Disbursal_Wise_Report")
				{
					var  loanno=document.getElementById("specificLoanNo").value;
					var  Date=document.getElementById("installmentDate").value;
					var interestType = document.getElementById("interestType").value;
					var rateInterest = document.getElementById("rateInterest").value;
					if(reportName=="OD_Calculation_Report")
					{
						interestType="L";
					}
					var msg="";
					if(loanno=="" && reportName!="OD_Intrest_For_All_Loans")
						msg=msg+"*Loan Number is Required.\n";
					if(Date=="" )
						msg=msg+"*As On Date is Required.\n";
					if(interestType=="U" && rateInterest=="" && reportName!="OD_Intrest_For_All_Loans")
						msg=msg+"*Rate of Interest is Required.";
					if(msg!="")
					{
						alert(msg);
						return false;
					}
					else
					{
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
						return true;
					}
				}	
				//alert("n15");
				if(reportName=="bank_details")
				{				
						var  bankid=document.getElementById("BankID").value;
						if(bankid=="")
						{	
							alert("Bank Name is Required ");
							return false;
						}
						else
						{
							var contextPath = document.getElementById("contextPath").value;
							document.getElementById("reportid").action=contextPath+"/AllReports.do";
							document.getElementById("reportid").submit();
						 	return true;
						}
				}
				//Nishant space starts
				if(reportName=="pending_branch_ack" || reportName=="pending_ho_ack")
				{				
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
				}
				//Nishant Space ends
//	Manish space starts
				if(reportName=="drawing_power_statement")
				{
					var bank = document.getElementById("lbxBankID").value;
					var branch = document.getElementById("lbxBranchID").value;
					var fromDate =	document.getElementById("fromdateforsd").value;
					var toDate = document.getElementById("todateforsd").value;
					var msg="";
					if(bank=="")
						msg=msg+"*Bank is Required.\n";
					if(branch=="")
						msg=msg+"*Bank Branch Name is Required.\n";
					if(fromDate=="")
						msg=msg+"From Date is Required.\n";
					if(toDate=="")
						msg=msg+"To Date is Required.";
						
					if(msg!="")
					{
						alert(msg);
						return false;
					}
					
					var formatD=document.getElementById("formatD").value;
					var dt1=getDateObject(fromDate,formatD.substring(2,3));
					var dt2=getDateObject(toDate,formatD.substring(2,3));
					if(dt1>dt2)
						{
						alert("To Date can't be less than From Date.");
						return false;
						}
					
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
				}
				//Manish space ends				
				
				if(reportName=="Presentation_batch_report" || reportName=="incipient_report" || reportName=="skewnessReport" || reportName=="Delinquency_Report_For_Month_End")
				{
					var  Date=document.getElementById("installmentDate").value;
					var branch=document.getElementById("branch").value;
					if(reportName!="incipient_report"){// add by saorabh
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}
					}
					if(Date=="")
					{	alert("Please Select Date");
						return false;
					}
					if(reportName=="incipient_report")
					{
						var status=document.getElementById('status').checked;
						if(status)
						document.getElementById("installmentDate").value=document.getElementById("cutOffDateForIncipient").value;
												
					}
					if(reportName=="Delinquency_Report_For_Month_End")
					{
						var branch=document.getElementById("branch").value;
						var branch_name=document.getElementById("lbxBranchId").value;	
						if(branch=="Specific" && branch_name=="")
						{	
							alert("Branch Name is Required");
							document.getElementById("dealButton").focus();
							return false;
						}	
						
						var cutOffDateList=document.getElementById("eodList").value;
						//alert("cutOffDateList : "+cutOffDateList);
						var dateList = cutOffDateList.split('|');
						var stri ='';
						var i=0;
						for(i; i<dateList.length; i++)
						{
							//alert("dateList["+i+"] ="+dateList[i]+"  Date ="+Date);
							if(dateList[i]==Date)
								break;
						}
						if(i==dateList.length)
						{


							alert("Invalid Cut Off Date,Only last date of month is valid,which should be greater than or equal to "+dateList[0]);
							return false;
						}
					}
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
				}
				//alert("n17");
				if( reportName=="Aging_of_the_receivables" || reportName=="DP_Statement")
				{
					var formatD=document.getElementById("formatD").value;
					var businessdate=document.getElementById("businessdate").value;
					var  Date=document.getElementById("installmentDate").value;
					if(document.getElementById("installmentDate").value=='')
						{
						Date='';
						}
					else
						{
					Date=getDateObject(Date,formatD.substring(2, 3));
						}
					businessdate=getDateObject(businessdate,formatD.substring(2, 3));
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					//alert("Date:::"+Date);
					if(Date=="")
					{	alert("Please Select As on Date.");
						return false;
					}
					if((Date > businessdate)){
						alert(" As On Date is greater than business date ");
					    return false;
					  }
					else
					{
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}
				}
				//alert("n18");
				if(reportName=="presentation_date_report")
				{
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					
					var  Date=document.getElementById("presentationdate").value;
					var fromdate=document.getElementById("approvalDateFrom").value;
					var todate=document.getElementById("approvalDateTo").value;
					
					if(fromdate=='' && todate=='' && Date=='')
					{
						alert("Select Either Date Range or Presentation Date Field.");
						return false;
					}
					if(fromdate!='' && todate=="")
					{	alert("Please select To Date Also.");
						return false;
					}
					if(todate!='' && fromdate=="")
					{	alert("Please select From Date Also.");
						return false;
					}
					var formatD=document.getElementById("formatD").value;
					var dt1=getDateObject(fromdate,formatD.substring(2,3));
					var dt2=getDateObject(todate,formatD.substring(2,3));
					if(dt2<dt1)
					{
						alert("To Date can't be less than From Date."); 
						return false;
					}
					var day=dateDifference(dt1,dt2);
					if(day>dateRengeLimit)
					{
						alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
						return false;
					}
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
				}
				//alert("n19");
				if((reportName=="Business_Comparison_Report" ) || reportName=="disbursal_dump")
				{
					var fromdate=document.getElementById("fromDate").value;
					var todate=document.getElementById("toDate").value;
					if(fromdate=="" && todate=="")
					{
						alert("Select Date Range.");
						return false;
					}
					if(fromdate && todate=="")
					{	alert("Please select To Date Also.");
						return false;
					}
					if(todate && fromdate=="")
					{	alert("Please select From Date Also.");
						return false;
					}
					if(fromdate!="" && todate!="")
					{
						var formatD=document.getElementById("formatD").value;
						var dt1=getDateObject(fromdate,formatD.substring(2,3));
						var dt2=getDateObject(todate,formatD.substring(2,3));
						if(dt2<dt1)
						{
							alert("To Date can't be less than From Date."); 
							return false;
						}
						var day=dateDifference(dt1,dt2);
						if(day>dateRengeLimit)
						{
							alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
							return false;
						}
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
						return true;				 					
					}
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;					
				}
				
				//changes by richa
				
				if((reportName=="Stationary_report"))
				{
					
					var fromdate=document.getElementById("fromDate").value;
					var todate=document.getElementById("toDate").value;
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	alert("Select Branch Name.");
						document.getElementById("dealButton").focus();
						return false;
					}		

					
					if(fromdate=="" && todate=="")
					{
						alert("Select Date Range.");
						return false;
					}
					
					if(fromdate && todate=="")
					{	alert("Please select To Date Also.");
						return false;
					}
					if(todate && fromdate=="")
					{	alert("Please select From Date Also.");
						return false;
					}
					if(fromdate!="" && todate!="")
					{
						var formatD=document.getElementById("formatD").value;
						var dt1=getDateObject(fromdate,formatD.substring(2,3));
						var dt2=getDateObject(todate,formatD.substring(2,3));
						if(dt2<dt1)
						{
							alert("To Date can't be less than From Date."); 
							return false;
						}
						
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
						return true;				 					
					}
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;					
				}
				
				

				//changes by Richa for PaySlip(Receipt Upload) Report
								
								if((reportName=="PaySlip(Receipt Upload)"))
								{
									
									
									var receiptDate=document.getElementById("receiptDate").value;
									var lbxBatchNO=document.getElementById("lbxBatchNO").value;
									var lbxBankID=document.getElementById("lbxBankID").value;
									var lbxBranchID=document.getElementById("lbxBranchID").value;
									var branch=document.getElementById("branch").value;
									var branch_name=document.getElementById("lbxBranchId").value;	
									if(branch=="Specific" && branch_name=="")
									{	
										alert("Branch Name is Required");
										document.getElementById("dealButton").focus();
										return false;
									}		
									if(receiptDate!=''&& receiptDate!=undefined)
									{
									var dt1=getDateObject(receiptDate,formatD.substring(2,3));
									}
									if(receiptDate=="")
									{
										
										alert('Please Select Received Date first');
										return false;
									}
									if(lbxBatchNO=="")
									{
										
										alert('Please Select Batch No. first');
										return false;
									}
									if( lbxBankID="")
									{
										
										alert('Please Select Bank first');
										return false;
									}
									if( lbxBranchID=="")
									{
										
										alert('Please Select Branch first');
										return false;
									}
									/*if(depositDate!=''&& depositDate!=undefined)
									{
									var dt2=getDateObject(depositDate,formatD.substring(2,3));
									}*/
									
									
									
								/*	if(fromdate=="" && todate=="")
									{
										alert("Select Date Range.");
										return false;
									}
									
									if(fromdate && todate=="")
									{	alert("Please select To Date Also.");
										return false;
									}
									if(todate && fromdate=="")
									{	alert("Please select From Date Also.");
										return false;
									}
									if(fromdate!="" && todate!="")
									{
										var formatD=document.getElementById("formatD").value;
										var dt1=getDateObject(fromdate,formatD.substring(2,3));
										var dt2=getDateObject(todate,formatD.substring(2,3));
										if(dt2<dt1)
										{
											alert("To Date can't be less than From Date."); 
											return false;
										}
										
										var contextPath = document.getElementById("contextPath").value;
										document.getElementById("reportid").action=contextPath+"/AllReports.do";
										document.getElementById("reportid").submit();
										return true;				 					
									*/
									var contextPath = document.getElementById("contextPath").value;
									document.getElementById("reportid").action=contextPath+"/AllReports.do";
									document.getElementById("reportid").submit();
								 	return true;					
								}
								
								// Richa Changes ends
				
				//alert("n20");
if(reportName=="maturityClousreReport")
				{
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}
					else
					{
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}
					
				}
				if(reportName=="Manual_Advices_Raised_During_a_Period")
				{
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}
					else
					{
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}
					
				}
				if(reportName=="ECS_Mandete_Cover_Letter_Header" || reportName=="login_breakup_report" || reportName=="tat_report" ||reportName=="payable_receivable_payment" ||reportName=="ops_mis_report"|| reportName=="Booking_Report"|| reportName=="Booking_Report_Monthly" || reportName=="transit_files_report" || reportName=="DailyLoginReport" ||reportName=="Login_Detail_MIS" ||reportName=="cashTransactionReport" ||reportName=="login_Details_Report" ||reportName=="mis_Send_To_Store" || reportName=="hold_Marked_Cases"||reportName=="CERSAI_entry" || reportName=="Insurance")
				{
					var fromdate=document.getElementById("fromDate").value;
					var todate=document.getElementById("toDate").value;
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(reportName=="tat_report"){
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
				}
					if(fromdate=="" && todate=="")
					{
						alert("Select Date Range.");
						return false;
					}
					if(fromdate && todate=="")
					{	alert("Please select To Date Also.");
						return false;
					}
					if(todate && fromdate=="")
					{	alert("Please select From Date Also.");
						return false;
					}
					if(fromdate!="" && todate!="")
					{
						var formatD=document.getElementById("formatD").value;
						var dt1=getDateObject(fromdate,formatD.substring(2,3));
						var dt2=getDateObject(todate,formatD.substring(2,3));
						if(dt2<dt1)
						{
							alert("To Date can't be less than From Date."); 
							return false;
						}
						var day=dateDifference(dt1,dt2);
						if((reportName=="Booking_Report" || reportName =="Booking_Report_Monthly" || reportName=="transit_files_report" || reportName=="hold_Marked_Cases") && day>DateRangeLimitSpecial )
						{
							alert("Days Difference can't be greater than "+RangeLimitSpecial+" Days."); 
							return false;
						}
						else if((reportName !="Booking_Report" && reportName !="Booking_Report_Monthly" && reportName != "transit_files_report" && reportName != "hold_Marked_Cases") && day>dateRengeLimit)
						{
							alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
							return false;
						}
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
						return true;				 					
					}
					if(reportName=="payable_receivable_payment")
					{
						var chargesId=document.getElementById("chargesId").value;
						var branch=document.getElementById("branch").value;
						var branch_name=document.getElementById("lbxBranchId").value;	
						if(branch=="Specific" && branch_name=="")
						{	
							alert("Branch Name is Required");
							document.getElementById("dealButton").focus();
							return false;
						}		
						if(chargesId =='')
						{
							alert("Charge Code is Required.");
							return false;
						}
					}					
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
				}
				//alert("n21");
				if(reportName=="rp_repayment_calculation_closed"||reportName=="rp_repayment_calculation"||reportName=="rp_collateral_mony_receipt"||reportName=="loan_advice_register" || reportName=="PDC_ECS_HISTORY" ||reportName=="statement_of_account"  ||reportName=="PROVISIONAL_INTEREST_CERTIFICATE" ||reportName=="loan_details_header" ||reportName=="termSheet_Report" || reportName=="loan_allocation_detail" ||reportName=="NOC_report" || reportName=="NOC_report_asset_Funded" || reportName=="forClosureMemoReport" || reportName=="Foreclosure_One" ||reportName=="Delivery_Order_Report"  ||reportName=="foreClosure_simulation_report" || reportName=="forclosurereport" ||reportName=="AUforeClosure_simulation_report" ||reportName=="SD_LIQUIDATION_REPORT" || reportName=="sanction_letter_after_loan" || reportName=="subsequentDisbursementVoucher"  || reportName=="CVDisbursalVoucher" || reportName=="loan_credit_appraisal_memo" || reportName=="WelcomeLetter(MM)" || reportName=="margin_deposit_receipt(mm)" || reportName=="reset_letter(mm)" || reportName=="GET_ACTUAL_IRR" ||reportName=="INSTRUMENT_SWAPPING_REPORT"||reportName=="Early_Closure_AFC_Report" ||reportName=="Maturity_Closure_AFC_Report" || reportName=="Gold_loan_Aruna" || reportName=="Gold_loan" || reportName=="Gold_loan_Aruna" || reportName=="LOD_Letter" || reportName=="PDC_exhaustion_letter" || reportName=="NOC_Report_With_LOD" || reportName=="NOC_Report_WithOut_LOD" || reportName=="ACH_SLIP_REPORT" || reportName=="sanction_letter" ||reportName=="statement_of_account_for_cancel_loan" || reportName=="sanction_letter_after_deal" || reportName=="SME_Sanction_Letter_Report_For_CP" || reportName=="Repricing_Letter_for_increased_ROI_interest_rate_policy" || reportName=="SME_Sanction_Letter_Report_For_CM")

				{
					var loan_no=document.getElementById("specificLoanNo").value;
					if(reportName=="GET_ACTUAL_IRR")
					{
						var foreClosureCharges=document.getElementById("foreClosureCharges").value;
						var exitLoad=document.getElementById("exitLoad").value;
						if(foreClosureCharges=='')
						{
							foreClosureCharges="0.00";
							document.getElementById("foreClosureCharges").value="0.00";
						}
						if(exitLoad=='')
						{
							exitLoad="0.00";
							document.getElementById("exitLoad").value="0.00";
						}
						if(parseFloat(foreClosureCharges)<0 || parseFloat(exitLoad)<0)
						{
							alert("foreClosureCharges or exitLoad can noy be negative.");
							return false;
						}
						if(loan_no=="" && reportName!="Repricing_Letter_for_increased_ROI_interest_rate_policy")
						{
							alert("Loan Number is Required");
							return false;
						}
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do?source=R";
						document.getElementById("reportid").submit();
						return true;
					}
					if(reportName=="PROVISIONAL_INTEREST_CERTIFICATE" || reportName=="statement_of_account_for_cancel_loan")
					{
						var fromDate=document.getElementById("fromDate").value;
						var toDate=document.getElementById("toDate").value;
						var msg="";
						if(loan_no=="")
							msg=msg+"*Loan Number is Required.\n";
						if(fromDate=="" && toDate=="")
							msg=msg+"*Date Range is Required.";
						if(msg!="")
						{
							alert(msg);
							return false;
						}
						if((fromDate=="" && toDate!="")||(fromDate!="" && toDate==""))
						{
							alert("Please Enter From Date and To Date Simultaneously.");
							return false;
						}
						if(fromDate!="" && toDate!="")
						{
							var formatD=document.getElementById("formatD").value;
							var dt1=getDateObject(fromDate,formatD.substring(2,3));
							var dt2=getDateObject(toDate,formatD.substring(2,3));
							if(dt2<dt1)
							{
								alert("To Date can't be less than From Date."); 
								return false;
							}
						}
						if(reportName=="PROVISIONAL_INTEREST_CERTIFICATE")
						{
							var day= toDate.substring(0,2);
							var month=toDate.substring(3,5);
							var frmDay= fromDate.substring(0,2);
							var toMonth=fromDate.substring(3,5);
							var frmYear= fromDate.substring(6,10);
							var toYear=toDate.substring(6,10);
							var yearDiff = (toYear - frmYear)
							if(toMonth!='04' ||frmDay!='01')
							{
								alert("From date should be 1'st of April.");
								return false;
							}
							if(month!='03' ||day!='31')
							{
								alert("To date should be 31'st of March.");
								return false;
							}	
							if(yearDiff > 1)
							{
								alert("Provisional Interest Certificate is allowed for one financial year at a time.");
								return false;
							}
						}
						if((reportName=="statement_of_account" ||reportName=="statement_of_account_for_cancel_loan") && document.getElementById("rvFlag").value=="YES")
						{
							var warning=confirm("Not allowed to generate SOA, please contact Central OPS Team. Do you want to continue?");
							if(!warning)
							{
								return false;
							}
						}
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do?source=R";
						document.getElementById("reportid").submit();
						return true;
					}
					if(reportName=="statement_of_account"){
						var branch=document.getElementById("branch4").value;
		                    var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						return false;
					}

					var loan_no=document.getElementById("loanno").value;
					var loan_from=document.getElementById("from").value;
					var loan_from_id=document.getElementById("lbx_loan_from_id").value;
					var loan_to=document.getElementById("to").value;
					var loan_to_id=document.getElementById("lbx_loan_to_id").value;
					var fromdate=document.getElementById("fromDate").value;
					var todate=document.getElementById("toDate").value;
				
					if(loan_no=="R" && fromdate=="" && todate=="")
					{
						if((loan_from=="" && loan_to=="") && (fromdate=="" && todate=="")){
						alert("Either select Date Range or Loan Range.");
						return false;
						}
					}
					if(loan_no=="R" )
					{
						if(loan_from=="" && loan_to=="")
						{
						alert("Please select Loan From and Loan to also.");
						return false;
						}
						if(loan_from=="" && loan_to!="")
						{
						alert("Please select Loan From also.");
						return false;
						}
						if(loan_from!="" && loan_to=="")
						{
						alert("Please select Loan TO also.");
						return false;
						}
					}

					
						if(fromdate=="" && todate=="")
						{
						alert("Please enter from date and To Date also.");
						return false;
						}
						if(fromdate=="" && todate!="")
						{
						alert("Please enter From Date also.");
						return false;
						}
						if(fromdate!="" && todate=="")
						{
						alert("Please enter To Date also.");
						return false;
						}
					
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do?source=R";
					document.getElementById("reportid").submit();
					return true;
                   }
					if(loan_no=="" && reportName!="Repricing_Letter_for_increased_ROI_interest_rate_policy" )
					{
						alert("Loan Number is Required");
						return false;
					}
					else
					{	
						if(reportName=="foreClosure_simulation_report" || reportName=="AUforeClosure_simulation_report"||reportName=="Early_Closure_AFC_Report" ||reportName=="Maturity_Closure_AFC_Report" )
						{
							var fromDate=document.getElementById("businessdate").value;
							var toDate=document.getElementById("installmentDate").value;
							if(toDate=="")
							{
								alert("Effective Date is Required.");
								return false;
							}
							else
							{
								var formatD=document.getElementById('formatD').value;
								var dt1=getDateObject(fromDate,formatD.substring(2,3));
								var dt2=getDateObject(toDate,formatD.substring(2,3));
								if(dt2<dt1)
								{
									alert("Effective Date can't be less than Business Date."); 
									return false;
								}
							}
							
						}
						if(reportName=="forclosurereport")
							{
							var fromDate=document.getElementById("businessdate").value;
							var asonDate=document.getElementById("asonDate").value;
							
							if(asonDate=="")
							{
								alert("As on Date is Required.");
								return false;
							}
							else
							{
								var formatD=document.getElementById('formatD').value;
								var dt1=getDateObject(fromDate,formatD.substring(2,3));
								var dt2=getDateObject(asonDate,formatD.substring(2,3));
								if(dt2<dt1)
								{
									alert("As on Date can't be less than Business Date."); 
									return false;
								}
							}
							}
						if(reportName=="AUforeClosure_simulation_report" && document.getElementById("rvFlag").value=="YES")
						{
							var warning=confirm("Not allowed to generate FC, please contact Central OPS Team. Do you want to continue?");
						//	alert(warning);

							if(!warning)
							{
								return false;
							}
						}
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					 	return true;
					}
				}
	// Avinash code starts for tax invoice report
				
				if(reportName=="GST_Reports"){
					
					var branch=document.getElementById('branch4').value;
					var lbxBranchId=document.getElementById('lbxBranchId').value;
					var loanno=document.getElementById('loanno').value;
					var loanFrom=document.getElementById('lbx_loan_from_id').value;
					var loanTo=document.getElementById('lbx_loan_to_id').value;
					var fromDate=document.getElementById('fromDate').value;
					var toDate=document.getElementById('toDate').value;
					
					
					if(branch=="Specific" && lbxBranchId=="" )
						{
						alert("Branch name is required.");
						return false;
						}
					if(loanno=="R" && (loanFrom=="" || loanTo=="")){
					alert("Loan range is required.");	
					return false;
						
					}
					if((fromDate=="" && toDate!="")||(fromDate!="" && toDate==""))
					{
						alert("Please Enter From Date and To Date Simultaneously.");
						return false;
					}
					if(fromDate!="" && toDate!="")
					{
						var formatD=document.getElementById("formatD").value;
						var dt1=getDateObject(fromDate,formatD.substring(2,3));
						var dt2=getDateObject(toDate,formatD.substring(2,3));
						if(dt2<dt1)
						{
							alert("To Date can't be less than From Date."); 
							return false;
						}
					}
					
					var contextPath = document.getElementById("contextPath").value;
					document.getElementById("reportid").action=contextPath+"/AllReports.do";
					document.getElementById("reportid").submit();
				 	return true;
					
					
				}
				
				// Avinash code ends for tax invoice report
				
				
				
				
				//alert("reportName:::"+reportName);
				if(reportName=="repricing_report" ||reportName=="deferal_Report"||reportName=="part_Prepayment_Report" )
				{
					//alert("n23");
					var loan_no=document.getElementById("rdpLoanNumber").value;
					if(loan_no=="" && reportName!="deferal_Report")
					{	
						alert("Loan Number is Required");
						return false;
					}
					else
					{	
						var branch=document.getElementById("branch").value;
						var branch_name=document.getElementById("lbxBranchId").value;	
						if(reportName=="deferal_Report" )
						{
							if(branch=="Specific" && branch_name=="" && reportName!="part_Prepayment_Report" )
						{	
							alert("Branch Name is Required");
							document.getElementById("dealButton").focus();
							return false;
						}
						}
						
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
						return true;
					}
				}
				else
				{
					//alert("n24");
						
					var loan_no=document.getElementById("loanno").value;
					var loan_from=document.getElementById("from").value;
					var loan_from_id=document.getElementById("lbx_loan_from_id").value;
					var loan_to=document.getElementById("to").value;
					var loan_to_id=document.getElementById("lbx_loan_to_id").value;
					//alert("n25");
					if(loan_no=="R" && reportName!="IKF_DUE_List_Report" && reportName!= "IKF_Bucket_Wise_Report"  && reportName!= "ikf_od_report" && reportName!= "user_Detail_Report_all" &&  reportName!="BOUNCE_LETTER" &&   reportName!= "PDC_Replinishment_letter" && reportName!= "Daily_Pledge_Report" && reportName!= "releasereport" && reportName != "pre_emi_to_be_accrued" && reportName!= "Repricing_Reduction_ROI" && reportName!= "Repricing_Inc_ROI" && reportName!= "Yearly_Interest_Certificate" && reportName!="Past_Cash_Flow" && reportName!="RSData" && reportName!="Repricing_Letter_for_increased_ROI_interest_rate_policy")
					{
						
						if(reportName =="Deals_Pending_Approved_Approved_Rejected_Cancelled_During_a_Period" || reportName=="Applicants_Linked_To_Deals" || reportName=="FI_REPORT" || reportName=="Deals_Pending_Approved_Rejected_Cancelled_During_a_Period" )
						{
							var deal_from=document.getElementById("fromDeal").value;
							var deal_from_id=document.getElementById("lbxDealNo").value;
							var deal_to=document.getElementById("toDeal").value;
							var deal_to_id=document.getElementById("lbxDealTo").value;
							var fromdate=document.getElementById("fromDate").value;
							var todate=document.getElementById("toDate").value;
							var branch=document.getElementById("branch").value;
							var branch_name=document.getElementById("lbxBranchId").value;	
							if(branch=="Specific" && branch_name=="")
							{	
								alert("Branch Name is Required");
								document.getElementById("dealButton").focus();
								return false;
							}	
							if(deal_from && deal_to=="")
							{
								alert("Please enter Deal To also.");
								return false;
							}
							if(deal_to && deal_from=="")
							{
								alert("Please enter Deal From also.");
								return false;
							}
							if(deal_to=="" && deal_from=="")
							{
								alert("Please enter Deal Range.");
								return false;
							}
							if(parseFloat(deal_from_id) > parseFloat(deal_to_id))
							{	
								alert("Initial Deal Number should  be less than or equal to Final Deal Number.");
								return false;				
							}
							if (fromdate=="" && todate =="" ) {
								alert("Date Range is Required.");
								return false;
							}
						}
							if(reportName=="PRESENTAION_RECEIPT_STATUS"){
							
							var fromDate=document.getElementById("fromDate").value;
							var toDate=document.getElementById("toDate").value;
							var msg="";
							if(fromDate=="" && toDate=="")
								msg=msg+"*Date Range is Required.";
							if(msg!="")
							{
								alert(msg);
								return false;
							}
							
							if(fromDate!="" && toDate!="")
							{
								var formatD=document.getElementById("formatD").value;
								var dt1=getDateObject(fromDate,formatD.substring(2,3));
								var dt2=getDateObject(toDate,formatD.substring(2,3));
								if(dt2<dt1)
								{
									alert("To Date can't be less than From Date."); 
									return false;
								}
							}
							
							if((fromDate=="" && toDate!="")||(fromDate!="" && toDate==""))
							{
								alert("Please Enter From Date and To Date Simultaneously.");
								return false;
							}
						}
						else
						{
							if(loan_to=="" && loan_from=="" && reportName!="RTGS_NEFT_From_Bank" && reportName!="bulk_rp_repayment_calculation" && reportName!="OverDue_Summary_Report")
							{
								alert("Please enter Loan Range.");
								document.getElementById('loanFromButton').focus();
								return false;
							}
							if(loan_from && loan_to=="")
							{
								alert("Please enter Loan To also.");
								return false;
							}
							if(loan_to && loan_from=="")
							{
								alert("Please enter Loan From also.");
								return false;
							}
						
							if(parseFloat(loan_from_id) > parseFloat(loan_to_id))
							{	
								alert("Initial Loan number should be less than or equal to Final Loan number.");
								return false;				
							}
						}
					}
					if(reportName=="Pending_Disbursal_Report"||reportName=="Delinquency_Report"||reportName=="Installment_Recived_summary_rpt" ||reportName=="Stock_Due_Report")
					{
						if(reportName=="Delinquency_Report" || reportName=="Stock_Due_Report"){
							var branch=document.getElementById("branch").value;
							var branch_name=document.getElementById("lbxBranchId").value;	
							if(branch=="Specific" && branch_name=="")
							{	
								alert("Branch Name is Required");
								document.getElementById("dealButton").focus();
								return false;
							}
							var contextPath = document.getElementById("contextPath").value;// add by saorabh
							document.getElementById("reportid").action=contextPath+"/AllReports.do";
							document.getElementById("reportid").submit();
						 	return true;
						}
						
						var asondate=document.getElementById("asonDate").value;
						if(asondate=="")
						{	alert("First select As on Date");
							return false;
						}
						else
						{	
							var contextPath = document.getElementById("contextPath").value;
							document.getElementById("reportid").action=contextPath+"/AllReports.do";
							document.getElementById("reportid").submit();
						 	return true;
						}
					}
					else
					{
						//alert("n26");
						var fromdate=document.getElementById("fromDate").value;
						var todate=document.getElementById("toDate").value;
						//alert("n26  fromdate  : "+fromdate);
						//alert("n26  todate    : "+todate);
						if(reportName=="Loan_Initiated_During_a_Period" || reportName=="interest_due_report" || reportName=="chequebouncemain")
						{
							var branch=document.getElementById("branch").value;
							var branch_name=document.getElementById("lbxBranchId").value;	
							if(branch=="Specific" && branch_name=="")
							{	
								alert("Branch Name is Required");
								document.getElementById("dealButton").focus();
								return false;
							}	
							
							
							if(fromdate=='' && todate=='')
							{
								alert("Date range is required.");
								return false;
							}
						}
						if(reportName=="receiptReport" || reportName=="approved_rejected" || reportName== "Installment_Due_Report"|| reportName== "Installment_Received_Report"|| reportName== "Installment_Recived_summary_rpt")
							{
								var branch=document.getElementById("branch").value;
								var branch_name=document.getElementById("lbxBranchId").value;	
								if(branch=="Specific" && branch_name=="")
								{	
									alert("Branch Name is Required");
									document.getElementById("dealButton").focus();
									return false;
								}	
								
								
								
								var loan_no=document.getElementById("loanno").value;
								var loan_from=document.getElementById("from").value;
								var loan_from_id=document.getElementById("lbx_loan_from_id").value;
								var loan_to=document.getElementById("to").value;
								var loan_to_id=document.getElementById("lbx_loan_to_id").value;
								if(loan_to=="" && loan_from=="" && fromdate=="" && todate=="" && loan_no=="A")
								{	
									alert("Please enter From Date and To Date");
									return false;
								}
							}
							if(reportName=="bulk_rp_repayment_calculation"){
 

								var rstatus=document.getElementById("rstatus").value;

                                                               var branch=document.getElementById("branch4").value;
 
	 			                                var branch_name=document.getElementById("lbxBranchId").value;	
								if(branch=="Specific" && branch_name=="")
								{	
									alert("Branch Name is Required");
									return false;
								}
 
								var loan_no=document.getElementById("loanno").value;
								var loan_from=document.getElementById("from").value;
								var loan_from_id=document.getElementById("lbx_loan_from_id").value;
								var loan_to=document.getElementById("to").value;
								var loan_to_id=document.getElementById("lbx_loan_to_id").value;
								
								if(rstatus=="")
									{
									alert("Please Select status also.");
									return false;
									}
								if(loan_no=="R" && fromdate=="" && todate=="")
								{
									if((loan_from=="" && loan_to=="") && (fromdate=="" && todate=="")){
									alert("Either select Date Range or Loan Range.");
									return false;
									}
								}
								if(loan_no=="R" )
								{
									if(loan_from=="" && loan_to=="")
									{
									alert("Please select Loan From and Loan to also.");
									return false;
									}
									if(loan_from=="" && loan_to!="")
									{
									alert("Please select Loan From also.");
									return false;
									}
									if(loan_from!="" && loan_to=="")
									{
									alert("Please select Loan TO also.");
									return false;
									}
								}

								if(fromdate!="" || todate!=""){
									if(fromdate=="" && todate=="")
									{
									alert("Please enter from date and To Date also.");
									return false;
									}
									if(fromdate=="" && todate!="")
									{
									alert("Please enter From Date also.");
									return false;
									}
									if(fromdate!="" && todate=="")
									{
									alert("Please enter To Date also.");
									return false;
									}
								}
                                                        }

					//Pooja code Start for Past_Cash_Flow
						if(reportName=="Past_Cash_Flow" || reportName=="RSData"){
							
							var fromDate=document.getElementById("fromDate").value;
							var toDate=document.getElementById("toDate").value;
							var msg="";
							if(fromDate=="" && toDate=="")
								msg=msg+"*Date Range is Required.";
							if(msg!="")
							{
								alert(msg);
								return false;
							}
							
							if(fromDate!="" && toDate!="")
							{
								var formatD=document.getElementById("formatD").value;
								var dt1=getDateObject(fromDate,formatD.substring(2,3));
								var dt2=getDateObject(toDate,formatD.substring(2,3));
								if(dt2<dt1)
								{
									alert("To Date can't be less than From Date."); 
									return false;
								}
							}
							
							if((fromDate=="" && toDate!="")||(fromDate!="" && toDate==""))
							{
								alert("Please Enter From Date and To Date Simultaneously.");
								return false;
							}
							if(reportName=="Past_Cash_Flow"){
							var contextPath = document.getElementById("contextPath").value;
							document.getElementById("reportid").action=contextPath+"/SecurtizationReportDispatchAction.do?method=securtizationReportGenerator&fromDate="+fromDate+"&toDate="+toDate;
							document.getElementById("reportid").submit();
						 	return true;
							}
							if(reportName=="RSData"){
								var contextPath = document.getElementById("contextPath").value;
								document.getElementById("reportid").action=contextPath+"/SecurtizationRSDataReportGenerator.do?method=securtizationRSDataReportGenerator&fromDate="+fromDate+"&toDate="+toDate;
								document.getElementById("reportid").submit();
							 	return true;
								}
						}
						//Pooja code end for Past_Cash_Flow														
						//alert("n27");
						if(fromdate && todate=="")
						{	alert("Please select To Date Also.");
							return false;
						}
						//alert("n28");
						if(todate && fromdate=="")
						{	alert("First select From Date Also.");
							return false;
						}
						//alert("n29");
						if(fromdate!="" && todate!="")
						{
							//alert("n27");
							var formatD=document.getElementById("formatD").value;
							//alert("n28");
							var dt1=getDateObject(fromdate,formatD.substring(2,3));
							var dt2=getDateObject(todate,formatD.substring(2,3));
							if(dt2<dt1)
							{
								alert("To Date can't be less than From Date."); 
								return false;
							}
							var day=dateDifference(dt1,dt2);
							if(day>dateRengeLimit)
							{
								alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
								return false;
							}
							var contextPath = document.getElementById("contextPath").value;
							document.getElementById("reportid").action=contextPath+"/AllReports.do";
							document.getElementById("reportid").submit();
						}	
						//alert("n30");
						var contextPath = document.getElementById("contextPath").value;
						document.getElementById("reportid").action=contextPath+"/AllReports.do";
						document.getElementById("reportid").submit();
					}
				}
			}	
			
//	 First function End....
function sendEmail(){
	var reportName=document.getElementById("reportName").value;	
	if(reportName=="select")
	{	alert("Report Type Required");
		document.getElementById('reportName').focus();
		return false;
	}
	var reportformat=document.getElementById("reportformat").value;
	if(reportformat!="P")
	{	alert("Report Format should be PDF ");
		document.getElementById('reportformat').focus();
		return false;
	}
	if(reportName == "PDC_Replinishment_letter" || reportName=="BOUNCE_LETTER"  || reportName == "Repricing_Reduction_ROI" || reportName == "Repricing_Inc_ROI" || reportName == "Yearly_Interest_Certificate"|| reportName == "statement_of_account"){
			
		var fromDate="";
		var toDate="";
		if(reportName == "Yearly_Interest_Certificate"){
	 fromDate=document.getElementById("intCerFromDate").value;	
	 toDate=document.getElementById("intCerToDate").value;	
		}else{
	 fromDate=document.getElementById("fromDate").value;
	 toDate=document.getElementById("toDate").value;
		}
	var loan_no=document.getElementById("loanno").value;
	var loan_from=document.getElementById("from").value;
	var loan_from_id=document.getElementById("lbx_loan_from_id").value;
	var loan_to=document.getElementById("to").value;
	var loan_to_id=document.getElementById("lbx_loan_to_id").value;
	var msg="";
	if(fromDate=="" && toDate=="")
		msg=msg+"*Date Range is Required.";
	if(msg!="")
	{
		alert(msg);
		return false;
	}
	
	if(fromDate!="" && toDate!="")
	{
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(fromDate,formatD.substring(2,3));
		var dt2=getDateObject(toDate,formatD.substring(2,3));
		if(dt2<dt1)
		{
			alert("To Date can't be less than From Date."); 
			return false;
		}
	}
	
	if((fromDate=="" && toDate!="")||(fromDate!="" && toDate==""))
	{
		alert("Please Enter From Date and To Date Simultaneously.");
		return false;
	}
	if(loan_no=="R" && reportName!="RTGS_NEFT_From_Bank")
	{	
		if(loan_to=="" && loan_from=="")
		{
			alert("Please enter Loan Range.");
			document.getElementById('loanFromButton').focus();
			return false;
		}
		if(loan_from && loan_to=="")
		{
			alert("Please enter Loan To also.");
			return false;
		}
		if(loan_to && loan_from=="")
		{
			alert("Please enter Loan From also.");
			return false;
		}
	
		if(parseFloat(loan_from_id) > parseFloat(loan_to_id))
		{	
			alert("Initial Loan number should be less than or equal to Final Loan number.");
			return false;				
		}
		

		
	}

}
if(reportName=="PROVISIONAL_INTEREST_CERTIFICATE" || reportName=="statement_of_account_for_cancel_loan")
	{
		var fromDate=document.getElementById("fromDate").value;
		var toDate=document.getElementById("toDate").value;
		var msg="";
		if(loan_no=="")
			msg=msg+"*Loan Number is Required.\n";
		if(fromDate=="" && toDate=="")
			msg=msg+"*Date Range is Required.";
		if(msg!="")
		{
			alert(msg);
			return false;
		}
		if((fromDate=="" && toDate!="")||(fromDate!="" && toDate==""))
		{
			alert("Please Enter From Date and To Date Simultaneously.");
			return false;
		}
		if(fromDate!="" && toDate!="")
		{
			var formatD=document.getElementById("formatD").value;
			var dt1=getDateObject(fromDate,formatD.substring(2,3));
			var dt2=getDateObject(toDate,formatD.substring(2,3));
			if(dt2<dt1)
			{
				alert("To Date can't be less than From Date."); 
				return false;
			}
		}
		if(reportName=="PROVISIONAL_INTEREST_CERTIFICATE")
		{
			var day= toDate.substring(0,2);
			var month=toDate.substring(3,5);
			var frmDay= fromDate.substring(0,2);
			var toMonth=fromDate.substring(3,5);
			var frmYear= fromDate.substring(6,10);
			var toYear=toDate.substring(6,10);
			var yearDiff = (toYear - frmYear)
			if(toMonth!='04' ||frmDay!='01')
			{
				alert("From date should be 1'st of April.");
				return false;
			}
			if(month!='03' ||day!='31')
			{
				alert("To date should be 31'st of March.");
				return false;
			}	
			if(yearDiff > 1)
			{
				alert("Provisional Interest Certificate is allowed for one financial year at a time.");
				return false;
			}
		}
		if((reportName=="statement_of_account_for_cancel_loan") && document.getElementById("rvFlag").value=="YES")
		{
			var warning=confirm("Not allowed to generate SOA, please contact Central OPS Team. Do you want to continue?");
			if(!warning)
			{
				return false;
			}
		}
		
	}
var contextPath = document.getElementById("contextPath").value;
document.getElementById("reportid").action=contextPath+"/AllReports.do?source=S";
document.getElementById("reportid").submit();
return true;
	
}			
			function dateDifference(startDate, endDate) 
			{
				var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
				var diff=Math.abs((startDate.getTime()-endDate.getTime())/(oneDay));//getTime()method return no of milliseconds since midnight of jan-1970
				return diff;
			}
			function checkReportsValidationReset()
			{
				var contextPath =document.getElementById('contextPath').value ;
	 			document.getElementById("reportid").action=contextPath+"/firstReports.do";
	 			document.getElementById("reportid").submit(); 
			}
			
			function hideLovofBranch()
			{
				var lovid=document.getElementById("branch").value;
				if(lovid=='All')
				{
					document.getElementById("dealButton").style.visibility="hidden";
					document.getElementById("branchid").options.length ="";
					document.getElementById("lbxBranchId").value="";
					//document.getElementById("r10").style.display="none";	
					//document.getElementById("r11").style.display="";	
				}
				else
				{					
					document.getElementById("dealButton").style.visibility="visible";
					//document.getElementById("r10").style.display="";
					//document.getElementById("r11").style.display="none";
				}
					
			}
			
// Kumar Aman Changes Started
			function hideLovofBranch3()
			{
				var lovid=document.getElementById("branch4").value;
				if(lovid=='All')
				{
					document.getElementById("dealButton").style.visibility="hidden";
					document.getElementById("branchid").options.length ="";
					document.getElementById("lbxBranchId").value="";
					//document.getElementById("r10").style.display="none";	
					//document.getElementById("r11").style.display="";	
				}
				else
				{					
					document.getElementById("dealButton").style.visibility="visible";
					//document.getElementById("r10").style.display="";
					//document.getElementById("r11").style.display="none";
				}
					
			}
			
// Kumar Aman Changes Ended	
			
// Kumar Aman Change Start for OD Report			
	
			
			
			
// Kumar Aman Change End for OD Report			
	
			function hideLovofPartner()
			{
				var lovid=document.getElementById("partnerType").value;
				if(lovid=='All')
				{
					document.getElementById("partnerCodeButton").style.visibility="hidden";
					//document.getElementById("branchid").options.length ="";
					document.getElementById("lbxpartnerCodeID").value="";
				}
				else
				{					
					document.getElementById("partnerCodeButton").style.visibility="visible";
				}
			}

			function hideLovofLoan()
			{
				var lovid=document.getElementById("loanno").value;
				var reportName=document.getElementById("reportName").value;	
				if(reportName=="select")
				{	alert("Report Type Required");
					document.getElementById('reportName').focus();
					return false;
				}
				if(lovid=='A')
				{	
					document.getElementById("loan_from_lov1").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_to_lov1").style.display="none";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("from").value="";
					document.getElementById("to").value="";
					document.getElementById("fromDeal").value="";
					document.getElementById("toDeal").value="";
					document.getElementById("r20").style.display="none";
					document.getElementById("r21").style.display="";
					document.getElementById("r30").style.display="none";
					document.getElementById("r31").style.display="";
					document.getElementById("d10").style.display="none";
					document.getElementById("d11").style.display="";
					document.getElementById("d20").style.display="none";
					document.getElementById("d21").style.display="";
				}
				else
				{
					if(reportName=="Insurance_details" || reportName=="interest_due_report" || reportName=="receiptReport" ||reportName=="knockoff_1" || reportName=="manual_advice" || reportName=="waiveoff_pending_reject" || reportName=="approved_rejected" || reportName=="paymentReport" || reportName=="Manual_Advices_Raised_During_a_Period" || reportName=="Loan_Initiated_During_a_Period" || reportName=="chequebouncemain" || reportName=="Stock_Due_Report" || reportName=="Disbursal_Done_During_a_Period" || reportName=="Pending_Disbursal_Report" || reportName=="Installment_Due_Report" || reportName=="Installment_Received_Report" || reportName=="Delinquency_Report"||reportName=="Installment_Recived_summary_rpt"|| reportName == "Yearly_Interest_Certificate" ||reportName=="Repricing_Reduction_ROI"||reportName=="Repricing_Inc_ROI"||reportName=="PDC_Replinishment_letter"  || reportName=="BOUNCE_LETTER" ||reportName=="bulk_rp_repayment_calculation" || reportName=="statement_of_account")
					{
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov1").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="inline";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="none";
					}
					if(reportName=="earlyClosureReport" || reportName=="maturityClousreReport" )
					{
						document.getElementById("loan_from_lov2").style.display="inline";
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov1").style.display="none";
						document.getElementById("loan_to_lov1").style.display="none";
						document.getElementById("loan_to_lov2").style.display="inline";
						document.getElementById("loan_to_lov3").style.display="none";
					}					
					if(reportName=="cancellationReport" )
					{
						document.getElementById("loan_from_lov1").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov3").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="none";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="inline";
					}
					if(reportName=="GST_Reports" )
					{
						document.getElementById("loan_from_lov1").style.display="inline";
						document.getElementById("loan_to_lov2").style.display="inline";
					}
					document.getElementById("r20").style.display="";
					document.getElementById("r21").style.display="none";
					document.getElementById("r30").style.display="";
					document.getElementById("r31").style.display="none";
					document.getElementById("d10").style.display="";
					document.getElementById("d11").style.display="none";
					document.getElementById("d20").style.display="";
					document.getElementById("d21").style.display="none";
				}
			}		
			function disableCal(reportName)
			{   
				document.getElementById("partnershipType").style.display="none";
				document.getElementById("partnerName1").style.display="none";
 				document.getElementById("partnerCode1").style.display="none";
				document.getElementById("ecs_notpad").style.display="none";
				document.getElementById("payable_receivable").style.display="none";
				document.getElementById("reportformat").removeAttribute("disabled",true);
				document.getElementById("customerRoleLab").style.display="none";
				document.getElementById("customerRole").style.display="none";
				//document.getElementById("product").style.display="none";
				document.getElementById("rateMethod").style.display="none";
				document.getElementById("future1").style.display="none";
				document.getElementById("future2").style.display="none";
				document.getElementById("soaFor1").style.display="none";
				document.getElementById("soaFor2").style.display="none";
				document.getElementById("sgk").style.display="none";
				document.getElementById("sgk1").style.display="none";
				//document.getElementById("auFc").style.display="none";
				document.getElementById("rsptmemo").style.display="none";
				document.getElementById("statusL").style.display="none";
				document.getElementById("statusV").style.display="none";
				document.getElementById("approvalL").style.display="none";
				document.getElementById("approvalV").style.display="none";
				document.getElementById("productL").style.display="none";
				document.getElementById("productV").style.display="none";
				//document.getElementById("branchL").style.display="none";
				//document.getElementById("branchV").style.display="none";
				document.getElementById("comLogo").style.display="none";
				document.getElementById("balance_certificate_report").style.display="none";
				document.getElementById("GET_ACTUAL_IRR_ID").style.display="none";	
				document.getElementById("pbatchLable").style.display="none";
				document.getElementById("pbatchR").style.display="none";
				document.getElementById("paySlip_Manual").style.display="none";
				document.getElementById("paySlipManual").style.display="none";
				document.getElementById("loanno").value="R";
				document.getElementById("branch").value="Specific";
				
				document.getElementById("productCat").style.display="none";
				document.getElementById("branchS").style.display="none";	
				document.getElementById("loanCAl").style.display="none";
				document.getElementById("scheme_2").style.display="none";
				document.getElementById("scheme1").style.display="none";
				document.getElementById("scheme3").style.display="none";
				document.getElementById("branch3").style.display="none";
				document.getElementById("month").style.display="none";
				document.getElementById("reporttype").style.display="none";
				document.getElementById("trreporttype12").style.display="none";
				document.getElementById("trreporttype13").style.display="none";
				 document.getElementById("gst_report").style.display="none";  // priyanka 
				if (document.getElementById("trreporttype11"))
					document.getElementById("trreporttype11").style.display="none";
				document.getElementById("drawing_power_statement").style.display="none";
				document.getElementById("rtgsNeft").style.display="none";
				document.getElementById("sendEmail").style.display="none";
				document.getElementById("IRR").style.display="none";
 				document.getElementById("IRR1").style.display="none";
 				document.getElementById("soaFor3").style.display="none";
					document.getElementById("soaFor4").style.display="none";
					document.getElementById("pdf").style.display="none";  // shubham
				
				if(reportName=="Loan_Initiated_During_a_Period" || reportName=="chequebouncemain")
				{
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("from").value="";
					document.getElementById("to").value="";
					document.getElementById("b1").style.display="none";
					document.getElementById("asonDate").value="";
					document.getElementById("withcal").style.display="none";
					document.getElementById("deal").style.display="none";
					document.getElementById("withOutcal").style.display="";			
					document.getElementById("dateFromD").style.display="";
					document.getElementById("dateF1").style.display="";
					document.getElementById("dateF2").style.display="none";
					document.getElementById("dateT1").style.display="";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("r40").style.display="none";
					document.getElementById("r41").style.display="";
					document.getElementById("asonDate").value="";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("instrument_mode").style.display="none";
					document.getElementById("paySlip_Manual").style.display="none";
					document.getElementById("paySlipManual").style.display="none";	
					document.getElementById("branchS").style.display="";
					document.getElementById("productCat").style.display="";
					document.getElementById("loanCAl").style.display="";
				}
				if(reportName=="Stock_Due_Report")
				{
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("from").value="";
					document.getElementById("to").value="";
					document.getElementById("rf").value="";
					document.getElementById("rfv").value="";
					//document.getElementById("1").value="";
					document.getElementById("lh").style.display="";
					document.getElementById("lv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("3").style.display=""; 
					document.getElementById("r20").style.display=""; 
					document.getElementById("r21").style.display="none";
					document.getElementById("r30").style.display=""; 	
					document.getElementById("r31").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("d10").style.display=""; 	
					document.getElementById("d11").style.display=""; 	
					document.getElementById("d20").style.display=""; 	
					document.getElementById("d21").style.display=""; 						
					document.getElementById("6").style.display="none";					
					document.getElementById("4").style.display="none";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="none";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateFromD").style.display="none";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateToD").style.display="none";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("r40").style.display="none";
					document.getElementById("r41").style.display="none";
					document.getElementById("withcal").style.display="none";
					document.getElementById("withOutcal").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("bnkid1").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("loan_number1").style.display="none";
					document.getElementById("loan_number2").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("instrument_mode").style.display="none";
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";

				}
				if(reportName=="Disbursal_Done_During_a_Period")
				{
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("from").value="";
					document.getElementById("to").value="";
					document.getElementById("b1").style.display="none";
					document.getElementById("asonDate").value="";
					document.getElementById("withcal").style.display="none";
					document.getElementById("withOutcal").style.display="";		
					document.getElementById("deal").style.display="none";
					document.getElementById("dateFromD").style.display="";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("withOutdateToD").style.display="none";	
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("r40").style.display="none";
					document.getElementById("r41").style.display="";
					document.getElementById("asonDate").value="";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("instrument_mode").style.display="none";
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";
				}
				if(reportName=="Pending_Disbursal_Report")
				{
					document.getElementById("branchid").value="";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("fromDate").style.display="none";
					document.getElementById("toDate").style.display="none";
					document.getElementById("dateFromD").style.display="none";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("deal").style.display="none";
					document.getElementById("dateToD").style.display="none";
					document.getElementById("withOutdateToD").style.display="none";	
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="none";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("withcal").style.display="";
					document.getElementById("withOutcal").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("r40").style.display="";
					document.getElementById("r41").style.display="none";
					document.getElementById("asonDate").value="";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("instrument_mode").style.display="none";
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";
				}
				if(reportName=="Installment_Due_Report")
				{
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("from").value="";
					document.getElementById("to").value="";
					document.getElementById("b1").style.display="none";
					document.getElementById("asonDate").value="";
					document.getElementById("withcal").style.display="none";
					document.getElementById("withOutcal").style.display="";			
					document.getElementById("deal").style.display="none";
					document.getElementById("dateFromD").style.display="";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("r40").style.display="none";
					document.getElementById("r41").style.display="";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("instrument_mode").style.display="none";
					document.getElementById("loanCAl").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("productCat").style.display="";
				}
				if(reportName=="Installment_Received_Report")
				{
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("from").value="";
					document.getElementById("to").value="";
					document.getElementById("b1").style.display="none";
					document.getElementById("asonDate").value="";
					document.getElementById("withcal").style.display="none";
					document.getElementById("deal").style.display="none";
					document.getElementById("withOutcal").style.display="";
					document.getElementById("dateFromD").style.display="";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("r40").style.display="none";
					document.getElementById("r41").style.display="";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				document.getElementById("loanCAl").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("productCat").style.display="";
									
				}
				if(reportName=="Delinquency_Report"||reportName=="Installment_Recived_summary_rpt")
					{
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("dateFromD").style.display="none";
					document.getElementById("withOutdateFromD").style.display="";
					document.getElementById("deal").style.display="none";
					document.getElementById("dateToD").style.display="none";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("withOutdateToD").style.display="";
					document.getElementById("withcal").style.display="";
					document.getElementById("withOutcal").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("r40").style.display="";
					document.getElementById("r41").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				document.getElementById("productCat").style.display="";
     				document.getElementById("branchS").style.display="";
     				document.getElementById("loanCAl").style.display="";
									
									
				}	
								
				if(reportName=="Receivable_Aging_Report")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("dateFromD").style.display="none";
					document.getElementById("withOutdateFromD").style.display="";
					document.getElementById("deal").style.display="none";
					document.getElementById("dateToD").style.display="none";
					document.getElementById("withOutdateToD").style.display="";
					document.getElementById("withcal").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
									
				}
				if(reportName=="Manual_Advices_Raised_During_a_Period")
				{
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("asonDate").value="";
					document.getElementById("withcal").style.display="none";
					document.getElementById("withOutcal").style.display="";
					document.getElementById("dateFromD").style.display="";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("deal").style.display="none";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("r40").style.display="none";
					document.getElementById("r41").style.display="";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				document.getElementById("productCat").style.display="";
    				document.getElementById("branchS").style.display="";
    				document.getElementById("loanCAl").style.display="";					
				
				}
				if(reportName=="rp_repayment_calculation_closed"||reportName=="rp_repayment_calculation"||reportName=="rp_collateral_mony_receipt")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("sp_loan1").style.display="inline";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("6").style.display="";
					document.getElementById("b1").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("bnkid1").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
				    document.getElementById("ind6").style.display="none";
				    document.getElementById("ind7").style.display="none";
				    document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
									
					
					if(reportName=="rp_collateral_mony_receipt" )
					{
						document.getElementById("rf").style.display="none";
						document.getElementById("rfv").style.display="none";
						document.getElementById("loan_no").style.display="none";
					}
					/*Shubham*/
					if(reportName=="rp_repayment_calculation" )
					{
						document.getElementById("pdf").style.display=""; 	
				}	
					/*end*/
				}	
				if(reportName!="rp_repayment_calculation_closed" && reportName!="rp_repayment_calculation" && reportName!="rp_collateral_mony_receipt" && reportName!="Recon_Reports" && reportName!="EMI_DUE_Report" && reportName!="maturity_report" && reportName!="ACH_TRANSACTION")
				{
//					document.getElementById("pdc").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="";
					document.getElementById("deal").style.display="none";
					document.getElementById("lv").style.display="";
					document.getElementById("3").style.display="";
					document.getElementById("4").style.display="";
					document.getElementById("5").style.display="";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					//document.getElementById("1").style.display="";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     			
									
					
				}
			if(reportName=="Recon_Reports" || reportName=="maturity_report" || reportName=="EMI_DUE_Report" ||reportName=="asset_master_report" || reportName=="ACH_TRANSACTION")
			{
				document.getElementById("pdc").style.display="none";
				document.getElementById("sp_loan1").style.display="none";
				document.getElementById("specific_loan").style.display="inline";
				document.getElementById("sp_loan2").style.display="none";
				document.getElementById("sp_loan3").style.display="none";
				document.getElementById("sp_loan4").style.display="none";
				document.getElementById("sp_loan5").style.display="none";
				document.getElementById("sp_loan6").style.display="none";
				document.getElementById("specificLoanNo").value="";
				document.getElementById("6").style.display="";
				document.getElementById("p1").style.display="";
				document.getElementById("b1").style.display="none";
				document.getElementById("bnkId").style.display="none";
				document.getElementById("bnkid1").style.display="none";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind1").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("ind3").style.display="none";
				document.getElementById("ind4").style.display="none";
				document.getElementById("ind5").style.display="none";
				document.getElementById("ind6").style.display="none";
				document.getElementById("ind7").style.display="none";
			    document.getElementById("ind8").style.display="none";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("deal").style.display="none";
				//document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="none";
				document.getElementById("dateF1").style.display="none";
				document.getElementById("dateF2").style.display="none";
				document.getElementById("dateT1").style.display="none";
				document.getElementById("dateT2").style.display="none";
				document.getElementById("dateFromD").style.display="none";
				document.getElementById("dateToD").style.display="none";
				document.getElementById("withOutdateFromD").style.display="none";
				document.getElementById("withOutdateToD").style.display="none";
				document.getElementById("5").style.display="none";
				document.getElementById("rf").style.display="";
				document.getElementById("rfv").style.display="";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="none";
				document.getElementById("datewithcal").style.display="none";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterangewithoutcal").style.display="none";
				document.getElementById("daterange").style.display="none";
				document.getElementById("SDreport").style.display="none";
				document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="";
				document.getElementById("fromDatewithcal").style.display="";
				document.getElementById("fromDatewitoutcal").style.display="none";
				document.getElementById("toDatewithcal").style.display="";
				document.getElementById("toDatewithoutcal").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";
 				document.getElementById("instrument_mode").style.display="none";
 				document.getElementById("productCat").style.display="";
 				document.getElementById("branchS").style.display="";
 				document.getElementById("loanCAl").style.display="";
				
			}

			if(reportName=="welcome_letter_report" || reportName=="AUwelcome_report" || reportName=="welcome_letter_report1" || reportName== "IKF_welcome_letter_report" || reportName == "welcome_letter_report_colanding" )
			{
				if(reportName=="welcome_letter_report1")
					document.getElementById("comLogo").style.display="";
				
                if(reportName=="welcome_letter_report" || reportName == "IKF_welcome_letter_report" || reportName == "welcome_letter_report_colanding" )
				{
					document.getElementById("FA").style.display="";
					document.getElementById("TA").style.display="";
					document.getElementById("FD").style.display="none";
					document.getElementById("TD").style.display="none";
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";
				}
				else
				{
					document.getElementById("FA").style.display="none";
					document.getElementById("TA").style.display="none";
					document.getElementById("FD").style.display="";
					document.getElementById("TD").style.display="";
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
				}
                document.getElementById("loanNumber").value="";
				document.getElementById("pdc").style.display="none";
				document.getElementById("6").style.display="none";
				document.getElementById("p1").style.display="none";
				document.getElementById("b1").style.display="none";
				document.getElementById("bnkId").style.display="none";
				document.getElementById("bnkid1").style.display="none";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind1").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("ind3").style.display="none";
				document.getElementById("ind4").style.display="none";
				document.getElementById("ind5").style.display="none";
				document.getElementById("ind6").style.display="none";
				document.getElementById("ind7").style.display="none";
			    document.getElementById("ind8").style.display="none";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="none";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("deal").style.display="none";
				//document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="none";
				document.getElementById("rf").style.display="none";
				document.getElementById("rfv").style.display="none";
				document.getElementById("5").style.display="none";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="";
				document.getElementById("LoanNoLov").style.display="";
				document.getElementById("LoanNoLov1").style.display="";
				document.getElementById("loannumber_lov1").style.display="Inline";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterange").style.display="";
				document.getElementById("daterangewithoutcal").style.display="none";
			    document.getElementById("datewithcal").style.display="";
			    document.getElementById("SDreport").style.display="none";
			    document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";
 				document.getElementById("instrument_mode").style.display="none";
 				document.getElementById("productCat").style.display="";
				document.getElementById("branchS").style.display="";
 				if(reportName=="AUwelcome_report")
 				{
 					document.getElementById("customerRoleLab").style.display="";
 					document.getElementById("customerRole").style.display="";
 					//document.getElementById("product").style.display="";
 					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";
 				}
 				if(reportName=="welcome_letter_report" || reportName == "welcome_letter_report_colanding")
				{
 					document.getElementById("pdf").style.display=""; 	
				}					
								
			}
			if(reportName=="Presentation_batch_report" || reportName=="incipient_report"  || reportName=="Aging_of_the_receivables" || reportName=="DP_Statement" || reportName=="skewnessReport" || reportName=="Delinquency_Report_For_Month_End" || reportName=="ikf_od_report")
			{
				document.getElementById("pdc").style.display="none";
				document.getElementById("b1").style.display="none";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("6").style.display="";
				document.getElementById("p1").style.display="none";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("installmentDate").value="";
				document.getElementById("ind4").style.display="none"
				document.getElementById("ind1").style.display="";
				//Nishant starts
				if(reportName=="Presentation_batch_report")
				{
					document.getElementById("pbatchLable").style.display="";
					document.getElementById("pbatchR").style.display="";
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";
				}
				//Nishant ends
				if( reportName=="Aging_of_the_receivables" || reportName=="DP_Statement" )
				{
					document.getElementById("ind5").style.display="";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";
				}else if( reportName=="incipient_report"){
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind3").style.display="";
					document.getElementById("ind7").style.display="";
					document.getElementById("ind8").style.display="";
					document.getElementById("productCat").style.display="none";
					document.getElementById("branchS").style.display="none";
					document.getElementById("loanCAl").style.display="none";
				}else {
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind3").style.display="";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
				}
				if(reportName=="ikf_od_report")
					{
					document.getElementById("productCat").style.display="none";
					document.getElementById("branchS").style.display="none";
					document.getElementById("loanCAl").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("month").style.display="";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("incptId").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("branchS").style.display="";
					document.getElementById("reporttype").style.display="";
					document.getElementById("ind1").style.display="none";
					document.getElementById("installmentDate").value="none";
					document.getElementById("6").style.display="none";
										
					}
				document.getElementById("ind6").style.display="none";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="none";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("deal").style.display="none";
				//document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="none";
				document.getElementById("5").style.display="none";
				document.getElementById("rf").style.display="";
				document.getElementById("rfv").style.display="";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="none";
				document.getElementById("datewithcal").style.display="none";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterangewithoutcal").style.display="none";
				document.getElementById("SDreport").style.display="none";
				document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";
 				document.getElementById("instrument_mode").style.display="none";
 				if(reportName=="Delinquency_Report_For_Month_End")
     			{
 					document.getElementById("productCat").style.display="";
 	 				document.getElementById("branchS").style.display="";
 	 				document.getElementById("loanCAl").style.display="";
     				document.getElementById("reportformat").value="E";     					
     				document.getElementById("reportformat").setAttribute("disabled",true);
     			}
 				if(reportName=="skewnessReport")
     			{
 					document.getElementById("productL").style.display="";
    				document.getElementById("productV").style.display=""; 
     			}
								
			}
			if( reportName=="presentation_date_report" )
			{
				document.getElementById("pdc").style.display="";
				document.getElementById("b1").style.display="none";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("6").style.display="none";
				document.getElementById("p1").style.display="none";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("ind3").style.display="none";
				document.getElementById("ind4").style.display="none";
				document.getElementById("ind1").style.display="none";
				document.getElementById("ind5").style.display="none";
				document.getElementById("ind6").style.display="none";
				document.getElementById("ind7").style.display="none";
				document.getElementById("ind8").style.display="none";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="none";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("deal").style.display="none";
				//document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="none";
				document.getElementById("5").style.display="none";
				document.getElementById("rf").style.display="";
				document.getElementById("rfv").style.display="";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="none";
				document.getElementById("datewithcal").style.display="none";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterangewithoutcal").style.display="none";
				document.getElementById("daterange").style.display="";
				document.getElementById("SDreport").style.display="none";
				document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";

 				document.getElementById("productCat").style.display="";
 				document.getElementById("branchS").style.display="";
 				document.getElementById("loanCAl").style.display="";

 				document.getElementById("instruments_mode").style.display="";

								
			}
			if(reportName=="customer_bank_branch_report" )
			{
				document.getElementById("pdc").style.display="none";
				document.getElementById("sp_loan1").style.display="inline";
				document.getElementById("sp_loan2").style.display="none";
				document.getElementById("sp_loan3").style.display="none";
				document.getElementById("sp_loan4").style.display="none";
				document.getElementById("sp_loan5").style.display="none";
				document.getElementById("sp_loan6").style.display="none";
				document.getElementById("specific_loan").style.display="none";
				document.getElementById("specificLoanNo").value="";
				document.getElementById("b1").style.display="none";
				document.getElementById("6").style.display="";
				document.getElementById("p1").style.display="";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind1").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("ind3").style.display="none";
				document.getElementById("ind4").style.display="none";
				document.getElementById("ind5").style.display="none";
				document.getElementById("ind6").style.display="none";
				document.getElementById("ind7").style.display="none";
				document.getElementById("ind8").style.display="none";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("loan_number1").style.display="none";
				document.getElementById("loan_number2").style.display="none";
				document.getElementById("rdp").value="R";
				document.getElementById("rdpLoanNumber").value="none";
				document.getElementById("lbxloannumber").value="none";
				document.getElementById("userName").value="none";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("deal").style.display="none";
				//document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="none";
				document.getElementById("5").style.display="none";
				document.getElementById("rf").style.display="";
				document.getElementById("rfv").style.display="";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="none";
				document.getElementById("datewithcal").style.display="none";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterangewithoutcal").style.display="none";
				document.getElementById("daterange").style.display="none";
				document.getElementById("SDreport").style.display="none";
				document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";
 				document.getElementById("instrument_mode").style.display="none";

 				document.getElementById("productCat").style.display="";
 				document.getElementById("branchS").style.display="";
 				document.getElementById("loanCAl").style.display="";

			}
			if(reportName=="deposited_instrument_bank_detail")
			{
				document.getElementById("pdc").style.display="none";
				document.getElementById("sp_loan1").style.display="inline";
				document.getElementById("sp_loan2").style.display="none";
				document.getElementById("sp_loan3").style.display="none";
				document.getElementById("sp_loan4").style.display="none";
				document.getElementById("sp_loan5").style.display="none";
				document.getElementById("sp_loan6").style.display="none";
				document.getElementById("specific_loan").style.display="none";
				document.getElementById("specificLoanNo").value="";
				document.getElementById("b1").style.display="none";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("6").style.display="";
				document.getElementById("p1").style.display="";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("ind3").style.display="";
				document.getElementById("installmentDate").value="";
				document.getElementById("ind4").style.display="none";
				document.getElementById("ind5").style.display="none";
				document.getElementById("ind6").style.display="none";
				document.getElementById("ind7").style.display="none";
				document.getElementById("ind8").style.display="none";
				document.getElementById("ind1").style.display="";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("deal").style.display="none";
			//	document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="none";
				document.getElementById("5").style.display="none";
				document.getElementById("rf").style.display="";
				document.getElementById("rfv").style.display="";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="none";
				document.getElementById("datewithcal").style.display="none";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterangewithoutcal").style.display="none";
				document.getElementById("daterange").style.display="none";
				document.getElementById("SDreport").style.display="none";
				document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";
 				document.getElementById("instrument_mode").style.display="none";
 				document.getElementById("productCat").style.display="";
 				document.getElementById("branchS").style.display="";
 				document.getElementById("loanCAl").style.display="";
								
			}
	if(reportName=="DISBURSAL_ADVICE")
			{
				document.getElementById("pdc").style.display="none";
				document.getElementById("sp_loan1").style.display="inline";
				document.getElementById("sp_loan2").style.display="none";
				document.getElementById("sp_loan3").style.display="none";
				document.getElementById("sp_loan4").style.display="none";
				document.getElementById("sp_loan5").style.display="none";
				document.getElementById("sp_loan6").style.display="none";
				document.getElementById("specific_loan").style.display="none";
				document.getElementById("specificLoanNo").value="";
				document.getElementById("b1").style.display="none";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("6").style.display="";
				document.getElementById("p1").style.display="";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("ind3").style.display="none";
				document.getElementById("installmentDate").value="none";
				document.getElementById("ind4").style.display="none";
				document.getElementById("ind5").style.display="none";
				document.getElementById("ind6").style.display="none";
				document.getElementById("ind7").style.display="none";
				document.getElementById("ind8").style.display="none";
				document.getElementById("ind1").style.display="none";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("deal").style.display="none";
			//	document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="none";
				document.getElementById("5").style.display="none";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="none";
				document.getElementById("datewithcal").style.display="none";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterangewithoutcal").style.display="none";
				document.getElementById("daterange").style.display="none";
				document.getElementById("SDreport").style.display="none";
				document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";
 				document.getElementById("instrument_mode").style.display="none";
 				document.getElementById("productCat").style.display="none";
 				document.getElementById("branchS").style.display="none";
 				document.getElementById("loanCAl").style.display="none";
 				document.getElementById("IRR").style.display="none";
 				document.getElementById("IRR1").style.display="none";
 				document.getElementById("rf").style.display="";
 				document.getElementById("reportformat").style.display="";
 				document.getElementById("rfv").style.display="";
				document.getElementById("instrumentsMode").style.display="none";
				document.getElementById("interest_certificate").style.display="none";
				document.getElementById("dateF1").style.display="none";
				document.getElementById("dateT1").style.display="none";
								
			}
			if(reportName=="RTGS_NEFT_From_Bank")
			{
				document.getElementById("pdc").style.display="none";
				document.getElementById("sp_loan1").style.display="inline";
				document.getElementById("sp_loan2").style.display="none";
				document.getElementById("sp_loan3").style.display="none";
				document.getElementById("sp_loan4").style.display="none";
				document.getElementById("sp_loan5").style.display="none";
				document.getElementById("sp_loan6").style.display="none";
				document.getElementById("specific_loan").style.display="none";
				document.getElementById("specificLoanNo").value="none";
				document.getElementById("b1").style.display="none";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("6").style.display="none";
				document.getElementById("p1").style.display="none";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("ind3").style.display="none";
				document.getElementById("installmentDate").value="none";
				document.getElementById("ind4").style.display="none";
				document.getElementById("ind5").style.display="none";
				document.getElementById("ind6").style.display="none";
				document.getElementById("ind7").style.display="none";
				document.getElementById("ind8").style.display="none";
				document.getElementById("ind1").style.display="none";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="none";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("deal").style.display="none";
			//	document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="";
				document.getElementById("5").style.display="none";
				document.getElementById("rf").style.display="";
				document.getElementById("rfv").style.display="";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="none";
				document.getElementById("datewithcal").style.display="";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterangewithoutcal").style.display="none";
				document.getElementById("daterange").style.display="";
				document.getElementById("SDreport").style.display="none";
				document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";
 				document.getElementById("instrument_mode").style.display="none";
 				document.getElementById("productCat").style.display="none";
 				document.getElementById("branchS").style.display="none";
 				document.getElementById("loanCAl").style.display="none";
 				document.getElementById("IRR").style.display="none";
 				document.getElementById("IRR1").style.display="none";
 				document.getElementById("FD").style.display="";
				document.getElementById("FA").style.display="none";
 				document.getElementById("dateF2").style.display="none";
 				document.getElementById("dateT2").style.display="none";
 				document.getElementById("fromDate").style.display="none";
 				document.getElementById("approvalDateFrom").style.display="";
 				document.getElementById("TD").style.display="";
				document.getElementById("TA").style.display="none";
 				document.getElementById("approvalDateTo").style.display="";
				document.getElementById("toDate").style.display="none";
				document.getElementById("dateFromD").style.display="none";
				document.getElementById("dateToD").style.display="none";
				document.getElementById("interest_certificate").style.display="none";
				document.getElementById("dateF1").style.display="none";
				document.getElementById("dateT1").style.display="none";
				document.getElementById("rtgsNeft").style.display="";
				
}
			// Rudra start
				if(reportName=="Repayment_Schedule_for_Partners" || reportName=="Partners_Payout_Report" || reportName=="Booking_Reports_For_Partners" || reportName=="Cumulative_RSP_Report_for_Payment")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("bnkid1").style.display="none";
					document.getElementById("BankID").value="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="none";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none"; 
     				document.getElementById("b1").style.display="none";
     				document.getElementById("partnershipType").style.display="none";
     				document.getElementById("partnerCode1").style.display="none";
     				document.getElementById("5").style.display="none";
 					document.getElementById("r40").style.display="none";
     				document.getElementById("withcal").style.display="none";
     				document.getElementById("dateF2").style.display="none";
     				document.getElementById("dateT2").style.display="none";
     				
     				if(reportName=="Repayment_Schedule_for_Partners")
     				{
     					document.getElementById("partnerName1").style.display="";
         			}
     				
     				if(reportName=="Partners_Payout_Report")
     				{
     					document.getElementById("5").style.display="";
     					document.getElementById("r40").style.display="";
         				document.getElementById("withcal").style.display="";
         				document.getElementById("partnershipType").style.display="";
         				document.getElementById("partnerCode1").style.display="";
     				}
     				if(reportName=="Booking_Reports_For_Partners" || reportName=="Cumulative_RSP_Report_for_Payment")
     				{
     					document.getElementById("4").style.display="";
         				document.getElementById("dateF1").style.display="";
         				document.getElementById("dateFromD").style.display="";
         				document.getElementById("dateT1").style.display="";
         				document.getElementById("dateToD").style.display="";
         				document.getElementById("partnershipType").style.display="";
         				document.getElementById("partnerCode1").style.display="";
         			}
				}
				
				// Rudra End
				
	//			if(reportName=="bank_details"|| reportName=="receiptMemoReport" || reportName=="pending_branch_ack" || reportName=="pending_ho_ack")

				if(reportName=="bank_details"|| reportName=="receiptMemoReport" || reportName=="pending_branch_ack" || reportName=="pending_ho_ack" ||reportName=="IKF_DUE_List_Report" || reportName == "IKF_Bucket_Wise_Report" || reportName == "user_Detail_Report_all" ||  reportName=="BOUNCE_LETTER" ||  reportName == "PDC_Replinishment_letter" || reportName == "Repricing_Reduction_ROI" || reportName == "Repricing_Inc_ROI" || reportName == "Yearly_Interest_Certificate")

				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("b1").style.display="";
					document.getElementById("bnkId").style.display="";
					document.getElementById("bnkid1").style.display="";
					document.getElementById("BankID").value="";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="none";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none"; 
 
// Kumar Aman Changes Started
     				
     				if(reportName=="IKF_DUE_List_Report" || reportName == "IKF_Bucket_Wise_Report" || reportName == "user_Detail_Report_all" || reportName == "PDC_Replinishment_letter" || reportName == "Repricing_Reduction_ROI" || reportName == "Repricing_Inc_ROI" || reportName == "Yearly_Interest_Certificate" || reportName=="BOUNCE_LETTER"  )
     					{
     					document.getElementById("lh").style.display="none";
     					document.getElementById("b1").style.display="none";
    					document.getElementById("bnkId").style.display="none";
    					document.getElementById("bnkid1").style.display="none";
    					document.getElementById("BankID").value="none";
    					
         				document.getElementById("branch3").style.display="";
         				document.getElementById("branchS").style.display="";
         				document.getElementById("branch4").selectedIndex="";
         				document.getElementById("reporttype").style.display="none";
         				document.getElementById("trreporttype12").style.display="none";
         				
         				if(reportName == "IKF_Bucket_Wise_Report")
         					{
         					document.getElementById("5").style.display="";
         					document.getElementById("r40").style.display="";
         					document.getElementById("withcal").style.display="";
         					
         					}
         				if(reportName=="IKF_DUE_List_Report")
         					{
         				document.getElementById("reporttype").style.display="none";
         				document.getElementById("trreporttype12").style.display="";
         					}
    			//		document.getElementById("loan_no").value="";
						if( reportName == "PDC_Replinishment_letter" || reportName == "Repricing_Reduction_ROI" || reportName == "Repricing_Inc_ROI" || reportName == "Yearly_Interest_Certificate" || reportName=="BOUNCE_LETTER"  ){
         					document.getElementById("lh").style.display="";
        					document.getElementById("lv").style.display="";
        					document.getElementById("3").style.display=""; 
        					document.getElementById("r20").style.display=""; 
        					document.getElementById("r21").style.display="none";
        					document.getElementById("r30").style.display=""; 	
        					document.getElementById("r31").style.display="none";
        					
        					document.getElementById("4").style.display="";
    						document.getElementById("dateF1").style.display="";
    						document.getElementById("dateF2").style.display="none";
    						document.getElementById("dateFromD").style.display="";
    						document.getElementById("withOutdateFromD").style.display="none";
    						document.getElementById("dateT1").style.display="";
    						document.getElementById("dateT2").style.display="none";
    						document.getElementById("dateT3").style.display="none";
    						document.getElementById("dateF3").style.display="none";
    						document.getElementById("dateToD").style.display="";
    						document.getElementById("fromDate").value="";
    						document.getElementById("toDate").value="";
    						if(reportName=="Yearly_Interest_Certificate")
    	         			{
    							document.getElementById("productL").style.display="";
    							document.getElementById("productV").style.display="";
    	     					document.getElementById("interest_certificate").style.display="";
    	     					document.getElementById("4").style.display="none";
    	     					document.getElementById("sendEmail").style.display="";
    	     					document.getElementById("pdf").style.display="";
    	     					
    	     				}else{
    						document.getElementById("interest_certificate").style.display="none";
    						document.getElementById("4").style.display="";
    						document.getElementById("sendEmail").style.display="none";
    	     				}
    						document.getElementById("withOutdateToD").style.display="none";
         				}    			//		document.getElementById("loan_no").value="";
     					}
     			   			
     				
// Kumar Aman Changes Ended
     				
     				if(reportName=="receiptMemoReport")
     				{
     					document.getElementById("b1").style.display="none";
     					document.getElementById("rsptmemo").style.display="";
     				}
     				if(reportName=="pending_branch_ack" || reportName=="pending_ho_ack")
     				{
     					document.getElementById("b1").style.display="none";
    					document.getElementById("bnkId").style.display="none";
    					document.getElementById("bnkid1").style.display="none";
    					document.getElementById("BankID").value="none";
     				}
     				if(reportName=="Repricing_Inc_ROI" || reportName == "Repricing_Reduction_ROI" || reportName == "BOUNCE_LETTER")  // shubham
     				{
     					document.getElementById("pdf").style.display="";
				}
				}
				
				if(reportName=="loan_advice_register" || reportName=="PDC_ECS_HISTORY")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("sp_loan1").style.display="inline";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("6").style.display="";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("np").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("bnkid1").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
					
				}
				if(reportName=="forClosureMemoReport" || reportName=="Foreclosure_One"||reportName=="foreClosure_simulation_report" || reportName=="forclosurereport" ||reportName=="AUforeClosure_simulation_report" ||reportName=="Delivery_Order_Report" || reportName=="GET_ACTUAL_IRR" ||reportName=="INSTRUMENT_SWAPPING_REPORT"||reportName=="Early_Closure_AFC_Report" ||reportName=="Maturity_Closure_AFC_Report" || reportName=="Gold_loan" || reportName=="Gold_loan_Aruna")
				{
					if(reportName=="GET_ACTUAL_IRR")
						document.getElementById("GET_ACTUAL_IRR_ID").style.display="";	
					document.getElementById("pdc").style.display="none";
					document.getElementById("6").style.display="";
					document.getElementById("p").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="none";
					document.getElementById("rfv").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="";
 					document.getElementById("specificLoanNo").style.display="";
 					document.getElementById("specificLoanButton").style.display="";
 					document.getElementById("instruments_mode").style.display="none";	
					if(reportName=="foreClosure_simulation_report" ||reportName=="AUforeClosure_simulation_report" || reportName=="forclosurereport" ||reportName=="Early_Closure_AFC_Report" ||reportName=="Maturity_Closure_AFC_Report")
					{
						document.getElementById("ind6").style.display="";
						document.getElementById("ind1").style.display="";
//						if(reportName=="AUforeClosure_simulation_report")
//							document.getElementById("auFc").style.display="";						
					}
					if( reportName=="forclosurereport" )
					{
						document.getElementById("ind6").style.display="none";
						document.getElementById("ind1").style.display="none";
						document.getElementById("IRR").style.display="none";
						document.getElementById("IRR1").style.display="none";						
						document.getElementById("5").style.display="";
						document.getElementById("pdf").style.display="";
					}
// Kumar Aman Changes Started for Early Closure AFC Report
					if(reportName=="Early_Closure_AFC_Report" ||reportName=="Maturity_Closure_AFC_Report")
					{
						document.getElementById("trreporttype13").style.display="";
					}
					
// Kumar Aman Changes Started for Early Closure AFC Report					
					
					
					
					
					
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan1").style.display="none";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="inline";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("instrument_mode").style.display="none";
				}
				if(reportName=="loan_details_header" || reportName=="NOC_report" || reportName=="NOC_report_asset_Funded" ||  reportName=="sanction_letter_after_loan" || reportName=="pre_emi_to_be_accrued" || reportName=="ecs_notpad" || reportName=="interest_certificate" || reportName=="NHB_Monthly_Report"  || reportName=="subsequentDisbursementVoucher" || reportName=="CVDisbursalVoucher" || reportName=="loan_credit_appraisal_memo" || reportName=="WelcomeLetter(MM)" || reportName=="margin_deposit_receipt(mm)" || reportName=="reset_letter(mm)" || reportName=="balance_certificate_report"|| reportName=="PaySlip(Receipt Upload)" || reportName=="LOD_Letter"  || reportName=="PRODUCT_INTEREST_CERTIFICATE" || reportName=="PDC_exhaustion_letter" || reportName=="NOC_Report_With_LOD"|| reportName=="NOC_Report_WithOut_LOD"  || reportName=="ACH_SLIP_REPORT" || reportName=="sanction_letter" || reportName=="Past_Cash_Flow" || reportName=="RSData"  || reportName=="SME_Sanction_Letter_Report_For_CM" || reportName=="Repricing_Letter_for_increased_ROI_interest_rate_policy" || reportName=="PRESENTAION_RECEIPT_STATUS")
				{
					
				if(reportName=="Repricing_Letter_for_increased_ROI_interest_rate_policy" ){
					document.getElementById("reportformat").style.display="none";
					document.getElementById("pdf").style.display="";
				}
					
					if(reportName=="NOC_Report_With_LOD"|| reportName=="NOC_Report_WithOut_LOD" || reportName=="sanction_letter" || reportName=="OverDue_Summary_Report" || reportName=="Past_Cash_Flow" || reportName=="RSData"){
						document.getElementById("IRR").style.display="none";
						document.getElementById("IRR1").style.display="none";
					}					if(reportName=="NOC_report")
						document.getElementById("comLogo").style.display="";
					document.getElementById("reportformat").value="P";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("pdc").style.display="none";
					document.getElementById("6").style.display="";
					document.getElementById("p").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="none";
					document.getElementById("rfv").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				document.getElementById("ecs_notpad").style.display="none";
     				document.getElementById("p").style.display="";
 					document.getElementById("specificLoanNo").style.display="";
 					document.getElementById("specificLoanButton").style.display="";
 					document.getElementById("balance_certificate_report").style.display="none";
     				document.getElementById("rt_month1").style.display="none";	
 					document.getElementById("rt_month2").style.display="none";	
 					document.getElementById("paySlip_Manual").style.display="none";
					document.getElementById("paySlipManual").style.display="none";
					document.getElementById("instruments_mode").style.display="none";	
					document.getElementById("expectedIRR").style.display="none";
					document.getElementById("IRR").style.display="none";
     				if(reportName=="NHB_Monthly_Report")
         			{
     					document.getElementById("rf").style.display="";
    					document.getElementById("rfv").style.display="";
     					document.getElementById("5").style.display="";
     					document.getElementById("r40").style.display="";
     					document.getElementById("r41").style.display="none";
     					document.getElementById("withcal").style.display="none";
     					document.getElementById("withOutcal").style.display="";
     					document.getElementById("sp_loan5").style.display="none";
     					document.getElementById("p").style.display="none";
     					document.getElementById("specificLoanNo").style.display="none";     					
     					document.getElementById("specificLoanButton").style.display="none";
     					document.getElementById("paySlip_Manual").style.display="none";
						document.getElementById("paySlipManual").style.display="none";
     					document.getElementById("WasonDate").value=document.getElementById("businessdate").value;
     					document.getElementById("instruments_mode").style.display="none";					
         			}
     				if(reportName=="interest_certificate" || reportName=="PRODUCT_INTEREST_CERTIFICATE")
         			{
     					document.getElementById("interest_certificate").style.display="";
     				}
     				if(reportName=="OverDue_Summary_Report" )
     					{
     					document.getElementById("6").style.display="none";
     					document.getElementById("rf").style.display="";
    					document.getElementById("rfv").style.display="";
     					}
					if(reportName=="Past_Cash_Flow" || reportName=="RSData")
     					{
     					document.getElementById("6").style.display="none";
     					document.getElementById("rf").style.display="none";
    					document.getElementById("rfv").style.display="none";
     					}
					// Start by abhishek sharma
 					if(reportName=="PRESENTAION_RECEIPT_STATUS")
 					{
 					document.getElementById("6").style.display="none";
 					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("reportformat").style.display="";
					//document.getElementById("6").style.display="";
					document.getElementById("p").style.display="none";
					document.getElementById("p1").style.display="none";
					document.getElementById("specificLoanNo").value="none";
					//document.getElementById("p").style.display="none";
 					document.getElementById("specificLoanNo").style.display="none";
 					document.getElementById("specificLoanButton").style.display="none";
 					document.getElementById("4").style.display="";
					document.getElementById("dateF1").style.display="";
					document.getElementById("dateF2").style.display="none";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateT1").style.display="";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					
 					}
 					//End by abhishek sharma
 				if(reportName=="balance_certificate_report")
         			{
     					document.getElementById("balance_certificate_report").style.display="";
     					document.getElementById("interest_certificate").style.display="none";
     					document.getElementById("ind5").style.display="";
     					document.getElementById("ind1").style.display="";
     					document.getElementById("paySlip_Manual").style.display="none";
						document.getElementById("paySlipManual").style.display="none";
     					var val=document.getElementById("balConfFromDate").value;
     					var year= parseInt(val.substring(6)); 
     					year=year+1;
     					var maxToDate="31-03-"+year;
     					document.getElementById("balConfToDate").value=maxToDate;     					
     				}
     				if(reportName=="ecs_notpad")
         			{
     					document.getElementById("ecs_notpad").style.display="";
     					document.getElementById("6").style.display="none";
    					document.getElementById("p").style.display="none";
    					document.getElementById("p1").style.display="none";
    					document.getElementById("paySlip_Manual").style.display="none";
						document.getElementById("paySlipManual").style.display="none";
						document.getElementById("instruments_mode").style.display="none";	
         			}
     				if(reportName=="pre_emi_to_be_accrued" )
     				{
     					document.getElementById("6").style.display="none";
    					document.getElementById("p").style.display="none";
    					document.getElementById("p1").style.display="none";
    					document.getElementById("rf").style.display="";
    					document.getElementById("rfv").style.display="";
    					document.getElementById("paySlip_Manual").style.display="none";
						document.getElementById("paySlipManual").style.display="none";
						document.getElementById("5").style.display="";
    					document.getElementById("r40").style.display="";
    					document.getElementById("r41").style.display="none";
    					document.getElementById("trreporttype11").style.display="none";
    					document.getElementById("productCat").style.display="none";
    					document.getElementById("branchS").style.display="none";
    					document.getElementById("loanCAl").style.display="none";

    					document.getElementById("instruments_mode").style.display="none";	

     				}
     				
     				if(reportName=="Repricing_Letter_for_increased_ROI_interest_rate_policy")
     				{
     					document.getElementById("6").style.display="none";
    					document.getElementById("p").style.display="none";
    					document.getElementById("p1").style.display="none";
    					document.getElementById("rf").style.display="none";
    					document.getElementById("rfv").style.display="none";
    					document.getElementById("paySlip_Manual").style.display="none";
						document.getElementById("paySlipManual").style.display="none";
						document.getElementById("5").style.display="";
    					document.getElementById("r40").style.display="";
    					document.getElementById("r41").style.display="none";
    					document.getElementById("trreporttype11").style.display="none";
    					document.getElementById("productCat").style.display="none";
    					document.getElementById("branchS").style.display="none";
    					document.getElementById("loanCAl").style.display="none";

    					document.getElementById("instruments_mode").style.display="none";	

     				}
     				
     		
					if(reportName=="NOC_report" || reportName=="NOC_report_asset_Funded" )
					{
						document.getElementById("sp_loan3").style.display="inline";
						document.getElementById("sp_loan1").style.display="none";
						document.getElementById("sp_loan2").style.display="none";	
						document.getElementById("sp_loan4").style.display="none";
						document.getElementById("sp_loan5").style.display="none";
						document.getElementById("sp_loan6").style.display="none";
						document.getElementById("specific_loan").style.display="none";
						document.getElementById("paySlip_Manual").style.display="none";
						document.getElementById("paySlipManual").style.display="none";
						document.getElementById("instruments_mode").style.display="none";	
					}
					
					if(reportName=="PaySlip(Receipt Upload)")
	 				{
						document.getElementById("specific_loan").style.display="none";
						document.getElementById("sp_loan1").style.display="none";
						document.getElementById("sp_loan2").style.display="none";
						document.getElementById("6").style.display="none";
						document.getElementById("p").style.display="none";
						document.getElementById("p1").style.display="none";
						//document.getElementById("specificLoanNo").value="";
						document.getElementById("paySlip_Manual").style.display="";
						document.getElementById("paySlipManual").style.display="";
						document.getElementById("productCat").style.display="";
						document.getElementById("branchS").style.display="";
						document.getElementById("loanCAl").style.display="";

						document.getElementById("instruments_mode").style.display="none";	
						
					
	 				}
					/*else 
					{
						document.getElementById("sp_loan3").style.display="none";
						document.getElementById("sp_loan1").style.display="inline";
						document.getElementById("sp_loan2").style.display="none";
						document.getElementById("sp_loan4").style.display="none";
						document.getElementById("sp_loan5").style.display="none";
						document.getElementById("sp_loan6").style.display="none";
						document.getElementById("specific_loan").style.display="none";
					}*/
				/*changes by shubham*/
					if(reportName=="LOD_Letter" || reportName=="NOC_Report_With_LOD" ||reportName=="NOC_Report_WithOut_LOD" ||reportName=="SME_Sanction_Letter_Report_For_CM" ){
						document.getElementById("pdf").style.display="";
				}
					/*end*/
				}
				
				if( reportName=="SD_LIQUIDATION_REPORT")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("6").style.display="";
					document.getElementById("p").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
				//	document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="none";
					document.getElementById("rfv").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan1").style.display="none";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan4").style.display="inline";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				
				}
				if(reportName=="Sanction Letter Report" || reportName=="letter_of_offer_cum_acceptance" ||reportName=="Sanctionletter(MM)"  ||reportName=="Sanction Letter Report For X 10")
				{
					document.getElementById("reportformat").value='P';
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("specificDealNo").Value="";
					document.getElementById("specific_deal").style.display="";
					document.getElementById("6").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="none";
					document.getElementById("rfv").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				/*changes by shubham*/
     				if(reportName=="Sanction Letter Report"){
						document.getElementById("pdf").style.display="";
				}
     				/*end*/
				}
				if(reportName=="loan_dump_report" || reportName=="industryRiskReport" ||reportName=="concentrationRiskReport" ||reportName=="rpt_audit_report" ||reportName=="geographical_Risk_Report" ||reportName=="maturity_Profile_Report" ||reportName=="product_Risk_Report" )
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="none";
					document.getElementById("p1").style.display="none";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="none";
					document.getElementById("rfv").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";	
				
     				if(reportName=="industryRiskReport" ||reportName=="concentrationRiskReport" ||reportName=="rpt_audit_report" ||reportName=="geographical_Risk_Report" ||reportName=="maturity_Profile_Report" ||reportName=="product_Risk_Report")
     				{
     					document.getElementById("5").style.display="";
     					document.getElementById("rf").style.display="";
    					document.getElementById("rfv").style.display="";
     				}
     				//changes done by amandeep 
     				if(reportName=="loan_dump_report")
     					{
									
     					document.getElementById("4").style.display="";
						document.getElementById("dateF1").style.display="";
						document.getElementById("dateF2").style.display="none";
						document.getElementById("dateFromD").style.display="";
						document.getElementById("withOutdateFromD").style.display="none";
						document.getElementById("dateT1").style.display="";
						document.getElementById("dateT2").style.display="none";
						document.getElementById("dateT3").style.display="none";
						document.getElementById("dateF3").style.display="none";
						document.getElementById("dateToD").style.display="";
						document.getElementById("fromDate").value="";
						document.getElementById("toDate").value="";
						document.getElementById("withOutdateToD").style.display="none";
     					
				}
     				//changes ends by amandeep
				}
				if( reportName=="loan_allocation_detail" ||reportName=="PROVISIONAL_INTEREST_CERTIFICATE" ||reportName=="statement_of_account_for_cancel_loan")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("sp_loan1").style.display="none";
					document.getElementById("sp_loan2").style.display="inline";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("b1").style.display="none";
					document.getElementById("6").style.display="";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";	
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("np").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("instrument_mode").style.display="none";
					//code Added by neeraj tripathi
					if(reportName=="loan_allocation_detail")
					{
						document.getElementById("4").style.display="none";						
					}
					else
					{
						document.getElementById("4").style.display="";
						document.getElementById("dateF1").style.display="";
						document.getElementById("dateF2").style.display="none";
						document.getElementById("dateFromD").style.display="";
						document.getElementById("withOutdateFromD").style.display="none";
						document.getElementById("dateT1").style.display="";
						document.getElementById("dateT2").style.display="none";
						document.getElementById("dateT3").style.display="none";
						document.getElementById("dateF3").style.display="none";
						document.getElementById("dateToD").style.display="";
						document.getElementById("fromDate").value="";
						document.getElementById("toDate").value="";
						document.getElementById("withOutdateToD").style.display="none";
					}
					//tripathi's space end
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instruments_mode").style.display="none";	
     				if(reportName=="PROVISIONAL_INTEREST_CERTIFICATE")
	     			{
	     				document.getElementById("reportformat").value="P";     					
	     				document.getElementById("reportformat").setAttribute("disabled",true);
	     			}
     				if(reportName=="statement_of_account_for_cancel_loan")
	     			{
     					document.getElementById("soaFor1").style.display="";
     					document.getElementById("soaFor2").style.display="";
     				
     				
				}
     				
				}
				if(reportName=="cancellationReport" || reportName=="earlyClosureReport"  || reportName=="maturityClousreReport"   || reportName=="paymentReport"  || reportName=="receiptReport"  || reportName=="deal_loan_details"   || reportName=="approved_rejected" || reportName=="knockoff_1"   || reportName=="manual_advice"  || reportName=="Notepad_Report"  || reportName=="waiveoff_pending_reject"|| reportName=="GST_Reports" ||reportName=="bulk_rp_repayment_calculation" ||reportName=="statement_of_account"  )
				{
					
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("from").value="";
					document.getElementById("to").value="";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					//document.getElementById("1").style.display="";
					document.getElementById("lh").style.display="";
					document.getElementById("lv").style.display="";
					document.getElementById("deal").style.display="none";
					document.getElementById("st").style.display="";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="";
					document.getElementById("3").style.display="";
					document.getElementById("4").style.display="";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("dateFromD").style.display="";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
 				document.getElementById("branchS").style.display="none";
     				document.getElementById("productCat").style.display="none";
     				document.getElementById("loanCAl").style.display="none";				
				document.getElementById("rs6").style.display="none";
					
					
					if(reportName=="earlyClosureReport" || reportName=="maturityClousreReport" )
					{
						document.getElementById("loan_from_lov2").style.display="inline";
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov1").style.display="none";
						document.getElementById("loan_to_lov1").style.display="none";
						document.getElementById("loan_to_lov2").style.display="inline";
						document.getElementById("loan_to_lov3").style.display="none";
						document.getElementById("b1").style.display="none";
						document.getElementById("df").style.display="none";
						document.getElementById("s1").style.display="";
						document.getElementById("s2").style.display="none";
						document.getElementById("s3").style.display="none";
						document.getElementById("s4").style.display="none";
						document.getElementById("s5").style.display="none";
						document.getElementById("loanCAl").style.display="";
						document.getElementById("branchS").style.display="";
						document.getElementById("productCat").style.display="";
					}
					if(reportName=="cancellationReport" )
					{
						document.getElementById("loan_from_lov1").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov3").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="none";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="inline";
						document.getElementById("b1").style.display="none";
						document.getElementById("df").style.display="none";
						document.getElementById("s1").style.display="";
						document.getElementById("s2").style.display="none";
						document.getElementById("s3").style.display="none";
						document.getElementById("s4").style.display="none";
						document.getElementById("s5").style.display="none";
						document.getElementById("branchS").style.display="";
	     				document.getElementById("productCat").style.display="";
	     				document.getElementById("loanCAl").style.display="";
					}
					if( reportName=="paymentReport" )
					{
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov1").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="inline";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="none";
						document.getElementById("b1").style.display="none";
						document.getElementById("df").style.display="none";
						document.getElementById("s1").style.display="";
						document.getElementById("s2").style.display="none";
						document.getElementById("s3").style.display="none";
						document.getElementById("s4").style.display="none";
						document.getElementById("s5").style.display="none";
						document.getElementById("productCat").style.display="";
						document.getElementById("branchS").style.display="";
						document.getElementById("loanCAl").style.display="";
					}
					if(reportName=="approved_rejected")
					{
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov1").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="inline";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="none";
						document.getElementById("b1").style.display="none";
						document.getElementById("df").style.display="none";
						document.getElementById("s1").style.display="none";
						document.getElementById("s2").style.display="";
						document.getElementById("s3").style.display="none";
						document.getElementById("s4").style.display="none";
						document.getElementById("s5").style.display="none";
						document.getElementById("branchS").style.display="";
	     				document.getElementById("productCat").style.display="";
	     				document.getElementById("loanCAl").style.display="";
					}					
					if(reportName=="bulk_rp_repayment_calculation")
					{
						document.getElementById("reportformat").value="P";
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov1").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="inline";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="none";
						document.getElementById("b1").style.display="none";
						document.getElementById("df").style.display="none";
						document.getElementById("s1").style.display="none";
						document.getElementById("s2").style.display="none";
						document.getElementById("s3").style.display="none";
						document.getElementById("s4").style.display="none";
						document.getElementById("s5").style.display="none";
						document.getElementById("s6").style.display="none";
						document.getElementById("branch3").style.display="";
						document.getElementById("branchS").style.display="";
	     				document.getElementById("productCat").style.display="none";
	     				document.getElementById("loanCAl").style.display="none";
						document.getElementById("r40").style.display="none";
						document.getElementById("5").style.display="none";					
						document.getElementById("rs6").style.display="";
						document.getElementById("productL").style.display="";
						document.getElementById("productV").style.display="";
						document.getElementById("reportformat").disabled=true;
						document.getElementById("pdf").style.display=""; 	
					
					}					
					if(reportName=="statement_of_account")
					{
						document.getElementById("reportformat").value="P";
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov1").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="inline";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="none";
						document.getElementById("b1").style.display="none";
						document.getElementById("df").style.display="none";
						document.getElementById("s1").style.display="none";
						document.getElementById("s2").style.display="none";
						document.getElementById("s3").style.display="none";
						document.getElementById("s4").style.display="none";
						document.getElementById("s5").style.display="none";
						document.getElementById("s6").style.display="none";
						document.getElementById("branch3").style.display="";
						document.getElementById("branchS").style.display="";
	     				document.getElementById("productCat").style.display="none";
	     				document.getElementById("loanCAl").style.display="none";
						document.getElementById("r40").style.display="none";
						document.getElementById("5").style.display="none";					
						document.getElementById("rs6").style.display="none";
						document.getElementById("st").style.display="none";
						document.getElementById("productL").style.display="none";
						document.getElementById("productV").style.display="none";
						document.getElementById("reportformat").disabled=true;
						
							document.getElementById("soaFor3").style.display="";
	     					document.getElementById("soaFor4").style.display="";
	     					document.getElementById("sendEmail").style.display="";	
	     					document.getElementById("pdf").style.display="";
					}
					if( reportName=="knockoff_1" || reportName=="manual_advice" || reportName=="waiveoff_pending_reject" )
					{
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov1").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="inline";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="none";
						document.getElementById("b1").style.display="none";
						document.getElementById("df").style.display="none";
						document.getElementById("s1").style.display="none";
						document.getElementById("s2").style.display="none";
						document.getElementById("s3").style.display="none";
						document.getElementById("s4").style.display="";
						document.getElementById("s5").style.display="none";
						document.getElementById("productCat").style.display="";
						document.getElementById("branchS").style.display="";
						document.getElementById("loanCAl").style.display="";
					
					}
					if(reportName=="receiptReport")
					{
						document.getElementById("loan_from_lov3").style.display="none";
						document.getElementById("loan_from_lov2").style.display="none";
						document.getElementById("loan_from_lov1").style.display="inline";
						document.getElementById("loan_to_lov1").style.display="inline";
						document.getElementById("loan_to_lov2").style.display="none";
						document.getElementById("loan_to_lov3").style.display="none";
						document.getElementById("b1").style.display="none";
						document.getElementById("df").style.display="none";
						document.getElementById("s1").style.display="";
						document.getElementById("s2").style.display="none";
						document.getElementById("s3").style.display="none";
						document.getElementById("s4").style.display="none";
						document.getElementById("s5").style.display="none";
						document.getElementById("instrument_mode").style.display="";
                        document.getElementById("receipt").style.display="";
						document.getElementById("receipt_report").style.display="";
						document.getElementById("interest").style.display="none";
						document.getElementById("int_due").style.display="none";
						document.getElementById("productCat").style.display="";
						document.getElementById("branchS").style.display="";
						document.getElementById("loanCAl").style.display="";

					}
					if(reportName=="GST_Reports"){
						document.getElementById("st").style.display="none";
						document.getElementById("stDrop").style.display="none";	
						document.getElementById("5").style.display="none";
                        document.getElementById("gst_report").style.display="";
                        document.getElementById('branch').value="Specific";
                        document.getElementById('loanno').value="R";
                        document.getElementById("branch3").style.display="";
                        document.getElementById("branchS").style.display="";
					}
				}
				if( reportName=="interest_due_report")
				{
					document.getElementById("branchid").value="";
					document.getElementById("pdc").style.display="none";
					document.getElementById("loan_from_lov3").style.display="none";
					document.getElementById("loan_from_lov2").style.display="none";
					document.getElementById("loan_from_lov1").style.display="inline";
					document.getElementById("loan_to_lov1").style.display="inline";
					document.getElementById("loan_to_lov2").style.display="none";
					document.getElementById("loan_to_lov3").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
				//	document.getElementById("1").style.display="";
					document.getElementById("lh").style.display="";
					document.getElementById("lv").style.display="";
					document.getElementById("deal").style.display="none";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="";
					document.getElementById("stDrop").style.display="";
					document.getElementById("3").style.display="";
					document.getElementById("4").style.display="";
					document.getElementById("dateFromD").style.display="";
					document.getElementById("dateF1").style.display="";
					document.getElementById("dateF2").style.display="none";
					document.getElementById("dateT1").style.display="";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("df").style.display="none";
					document.getElementById("s1").style.display="none";
					document.getElementById("s2").style.display="none";
					document.getElementById("s3").style.display="none";
					document.getElementById("s4").style.display="none";
					document.getElementById("s5").style.display="";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="";
					document.getElementById("receipt").style.display="none";
					document.getElementById("receipt_report").style.display="none";
					document.getElementById("interest").style.display="";
					document.getElementById("int_due").style.display="";

					document.getElementById("branchS").style.display="";
     				document.getElementById("productCat").style.display="";
     				document.getElementById("loanCAl").style.display="";				

					document.getElementById("instruments_mode").style.display="none";	
									

				}
				if( reportName=="billed_EMIs_report")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("loan_no").style.display="none";

					document.getElementById("6").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("sp_loan1").style.display="inline";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="";
					document.getElementById("installmentDate").value="";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none"
					document.getElementById("ind4").style.display="";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="none";
					document.getElementById("np").style.display="";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
			document.getElementById("branchS").style.display="";
     				document.getElementById("productCat").style.display="";
     				document.getElementById("loanCAl").style.display="";				

				}
				
				if(reportName=="repricing_report")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("loan_no").style.display="";
					document.getElementById("loan_number1").style.display="";
					document.getElementById("loan_number2").style.display="none";
					document.getElementById("rdp").value="R";
					document.getElementById("rdpLoanNumber").value="";
					document.getElementById("lbxloannumber").value="";
					document.getElementById("userName").value="";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				document.getElementById("pdf").style.display="";
									
				}
				if(reportName=="part_Prepayment_Report" || reportName=="Daily_Pledge_Report" || reportName=="releasereport")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("loan_no").style.display="";
					document.getElementById("loan_number1").style.display="";
					document.getElementById("loan_number2").style.display="none";
					document.getElementById("rdp").value="P";
					document.getElementById("rdpLoanNumber").value="";
					document.getElementById("lbxloannumber").value="";
					document.getElementById("userName").value="";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
									
				}
				
//  changes started by Kumar Aman				
				if(reportName=="Daily_Pledge_Report" || reportName=="releasereport")
					{
					document.getElementById("loan_no").style.display="none";
					document.getElementById("loan_number1").style.display="none";
					document.getElementById("rdpLoanNumber").value="none";
					document.getElementById("lbxloannumber").value="none";
					document.getElementById("4").style.display="";
					}
				
// changes ended by Kumar Aman				
				
				if(reportName=="deferal_Report")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("loan_no").style.display="";
					document.getElementById("loan_number1").style.display="none";
					document.getElementById("loan_number2").style.display="";
					document.getElementById("rdp").value="D";
					document.getElementById("rdpLoanNumber").value="";
					document.getElementById("lbxloannumber").value="";
					document.getElementById("userName").value="";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";

     				document.getElementById("productCat").style.display="";
     				document.getElementById("branchS").style.display="";
     				document.getElementById("loanCAl").style.display="";

									
				}
				if(reportName=="instrument_capturing_stage")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("sp_loan1").style.display="inline";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("b1").style.display="none";
					document.getElementById("6").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("p").style.display="none";
					document.getElementById("np").style.display="";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("loan_number1").style.display="none";
					document.getElementById("loan_number2").style.display="none";
					document.getElementById("rdp").value="R";
					document.getElementById("rdpLoanNumber").value="none";
					document.getElementById("lbxloannumber").value="";
					document.getElementById("userName").value="";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
				//	document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";

     				document.getElementById("productCat").style.display="";
     				document.getElementById("branchS").style.display="";
     				document.getElementById("loanCAl").style.display="";

									
				}
				if(reportName=="exposure_calculation")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("p1").style.display="none";
					document.getElementById("p").style.display="none";
					document.getElementById("np").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("loan_number1").style.display="none";
					document.getElementById("loan_number2").style.display="none";
					document.getElementById("rdp").value="R";
					document.getElementById("rdpLoanNumber").value="none";
					document.getElementById("lbxloannumber").value="";
					document.getElementById("userName").value="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="";
					document.getElementById("groupid").style.display="";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("LoanNoLov1").style.display="none";
					document.getElementById("loannumber_lov1").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";

     				document.getElementById("productCat").style.display="";
     				document.getElementById("branchS").style.display="";
     				document.getElementById("loanCAl").style.display="";

									
				}
				
				if(reportName=="SD_reports")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("sp_loan1").style.display="none";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("p1").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("bnkid1").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="none";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="none";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateFromD").style.display="none";
					document.getElementById("dateToD").style.display="none";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="";
					document.getElementById("datewithcal1").style.display="";
					document.getElementById("datewithoutcal1").style.display="";
					document.getElementById("daterange1").style.display="";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
					document.getElementById("df").style.display="none";
					document.getElementById("s6").style.display="";
					document.getElementById("productCat").style.display="";
					document.getElementById("branchS").style.display="";
					document.getElementById("loanCAl").style.display="";

					document.getElementById("instruments_mode").style.display="none";	
									
				}
				if(reportName=="batch_report")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("sp_loan1").style.display="none";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("p1").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("bnkId").style.display="none";
					document.getElementById("bnkid1").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="none";
					document.getElementById("np").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("dateF1").style.display="none";
					document.getElementById("dateF2").style.display="none";
					document.getElementById("dateT1").style.display="none";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateFromD").style.display="none";
					document.getElementById("dateToD").style.display="none";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="";
     				document.getElementById("Presentation_generate_batch").style.display="";
     				document.getElementById("Batch1").style.display="";
     				document.getElementById("Batch2").style.display="inline";
     				document.getElementById("batch_1").style.display="";
     				document.getElementById("pDatewithcal").style.display="";
     				document.getElementById("pDatewithoutcal").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				document.getElementById("loanCAl").style.display="";
     				document.getElementById("branchS").style.display="";
     				document.getElementById("productCat").style.display="";
									
				}
				
				if(reportName=="Insurance_details")
				{
				document.getElementById("pdc").style.display="none";
//				document.getElementById("sp_loan1").style.display="none";
//				document.getElementById("specific_loan").style.display="inline";
//				document.getElementById("sp_loan2").style.display="none";
//				document.getElementById("sp_loan3").style.display="none";
//				document.getElementById("sp_loan4").style.display="none";
				document.getElementById("6").style.display="none";
				document.getElementById("p1").style.display="";
				document.getElementById("b1").style.display="none";
				document.getElementById("bnkId").style.display="none";
				document.getElementById("bnkid1").style.display="none";
				document.getElementById("ind").style.display="none";
				document.getElementById("ind1").style.display="none";
				document.getElementById("ind2").style.display="none";
				document.getElementById("ind3").style.display="none";
				document.getElementById("ind4").style.display="none";
				document.getElementById("ind5").style.display="none";
				document.getElementById("ind6").style.display="none";
				document.getElementById("ind7").style.display="none";
				document.getElementById("ind8").style.display="none";
				document.getElementById("p").style.display="none";
				document.getElementById("np").style.display="";
				document.getElementById("loan_no").style.display="none";
				document.getElementById("lh").style.display="";
				document.getElementById("lv").style.display="";
				document.getElementById("deal").style.display="none";
				//document.getElementById("1").style.display="none";
				document.getElementById("3").style.display="";
				document.getElementById("r21").style.display="";
				document.getElementById("r20").style.display="none";
				document.getElementById("loan_from_lov1").style.display="inline";
				document.getElementById("loan_from_lov2").style.display="none";
				document.getElementById("loan_from_lov3").style.display="none";
				document.getElementById("r31").style.display="";
				document.getElementById("r30").style.display="none";
				document.getElementById("loan_to_lov1").style.display="inline";
				document.getElementById("loan_to_lov2").style.display="none";
				document.getElementById("loan_to_lov3").style.display="none";
				document.getElementById("4").style.display="";
				document.getElementById("dateF1").style.display="none";
				document.getElementById("dateF2").style.display="none";
				document.getElementById("dateF3").style.display="";
				document.getElementById("fromDate").value="";
				document.getElementById("toDate").value="";
				document.getElementById("dateT1").style.display="none";
				document.getElementById("dateT2").style.display="none";
				document.getElementById("dateT3").style.display="";
				document.getElementById("dateFromD").style.display="";
				document.getElementById("dateToD").style.display="";
				document.getElementById("withOutdateFromD").style.display="none";
				document.getElementById("withOutdateToD").style.display="none";
				document.getElementById("5").style.display="none";
				document.getElementById("rf").style.display="";
				document.getElementById("rfv").style.display="";
				document.getElementById("st").style.display="none";
				document.getElementById("st1").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("dealRange").style.display="none";
				document.getElementById("specific_deal").style.display="none";
				document.getElementById("group_id").style.display="none";
				document.getElementById("loannumber").style.display="none";
				document.getElementById("datewithcal").style.display="none";
				document.getElementById("datewithoutcal").style.display="none";
				document.getElementById("daterangewithoutcal").style.display="none";
				document.getElementById("daterange").style.display="none";
				document.getElementById("SDreport").style.display="none";
				document.getElementById("datewithcal1").style.display="none";
				document.getElementById("datewithoutcal1").style.display="none";
				document.getElementById("daterange1").style.display="none";
				document.getElementById("daterangewithoutcal1").style.display="none";
				document.getElementById("dateRangeForMaturity").style.display="none";
				document.getElementById("fromDatewithcal").style.display="none";
				document.getElementById("fromDatewitoutcal").style.display="none";
				document.getElementById("toDatewithcal").style.display="none";
				document.getElementById("toDatewithoutcal").style.display="none";
				document.getElementById("batch").style.display="none";
 				document.getElementById("Presentation_generate_batch").style.display="none";
 				document.getElementById("Batch1").style.display="none";
 				document.getElementById("Batch2").style.display="none";
 				document.getElementById("batch_1").style.display="none";
 				document.getElementById("instrument_mode").style.display="none";

 				document.getElementById("productCat").style.display="";
 				document.getElementById("branchS").style.display="";
 				document.getElementById("loanCAl").style.display="";

								
			}
			//Amit Starts
			if(reportName!="LPP_calculation_report" || reportName!="OD_Calculation_Report"||reportName=="OD_SIMULATION_REPORT" ||reportName=="OD_Intrest_For_All_Loans"||reportName=="LPP_SIMULATION_REPORT" ||reportName!="OD_Disbursal_Wise_Report")
			{
				document.getElementById("rate").style.display="none";
			}
			//Amit Ends

				if( reportName=="LPP_calculation_report" || reportName=="OD_Calculation_Report"||reportName=="OD_SIMULATION_REPORT" ||reportName=="OD_Intrest_For_All_Loans"||reportName=="LPP_SIMULATION_REPORT" ||reportName=="OD_Disbursal_Wise_Report")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("6").style.display="";
					document.getElementById("p1").style.display="";
					document.getElementById("specificLoanNo").value="";
					document.getElementById("sp_loan1").style.display="none";
					document.getElementById("sp_loan2").style.display="none";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="inline";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind1").style.display="";
					document.getElementById("installmentDate").value="";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="";
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="";
					document.getElementById("np").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("4").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
     				document.getElementById("Batch1").style.display="none";
     				document.getElementById("Batch2").style.display="none";
     				document.getElementById("batch_1").style.display="none";
     				document.getElementById("instrument_mode").style.display="none";
     				document.getElementById("rt_month1").style.display="none";	
 					document.getElementById("rt_month2").style.display="none";
 					document.getElementById("instruments_mode").style.display="none";	
					if (reportName=="OD_SIMULATION_REPORT"||reportName=="LPP_SIMULATION_REPORT"||reportName=="LPP_calculation_report"||reportName=="OD_Disbursal_Wise_Report")
     				document.getElementById("rate").style.display="";	
     				if (reportName=="OD_SIMULATION_REPORT" ||reportName=="OD_Intrest_For_All_Loans"||reportName=="LPP_SIMULATION_REPORT")
     				{
     				document.getElementById("rateMethod").style.display="";	
     				}
     				if (reportName=="OD_Intrest_For_All_Loans")
     				{
     					document.getElementById("p").style.display="none";
     					document.getElementById("p1").style.display="none";
     					document.getElementById("rateMethod").style.display="none";
     					document.getElementById("rt_month1").style.display="";	
     					document.getElementById("rt_month2").style.display="";
     					document.getElementById("scheme_2").style.display="";
     					document.getElementById("scheme1").style.display="";
     					document.getElementById("scheme3").style.display="";
     				}
				}
				
				if(reportName=="ECS_Mandete_Cover_Letter_Header" || reportName=="Business_Comparison_Report" || reportName=="disbursal_dump" || reportName=="Stationary_report" || reportName=="login_breakup_report" || reportName=="tat_report" ||reportName=="payable_receivable_payment" ||reportName=="ops_mis_report"|| reportName=="Future_EMI_Receivable"|| reportName=="Booking_Report"|| reportName=="Booking_Report_Monthly" || reportName=="transit_files_report" || reportName=="hold_Marked_Cases" || reportName=="DailyLoginReport" ||reportName=="Login_Detail_MIS" ||reportName=="cashTransactionReport"  ||reportName=="login_Details_Report" ||reportName=="mis_Send_To_Store"||reportName=="CERSAI_entry" || reportName=="Insurance")
				{
					document.getElementById("pdc").style.display="none";
					document.getElementById("sp_loan1").style.display="none";
					document.getElementById("sp_loan2").style.display="inline";
					document.getElementById("sp_loan3").style.display="none";
					document.getElementById("sp_loan4").style.display="none";
					document.getElementById("sp_loan5").style.display="none";
					document.getElementById("sp_loan6").style.display="none";
					document.getElementById("specific_loan").style.display="none";
					document.getElementById("specificLoanNo").value="none";
					document.getElementById("b1").style.display="none";
					document.getElementById("6").style.display="none";
					document.getElementById("ind1").style.display="none";
					document.getElementById("ind").style.display="none";
					document.getElementById("ind2").style.display="none";
					document.getElementById("ind3").style.display="none";
					document.getElementById("ind4").style.display="none";
					document.getElementById("ind5").style.display="none";	
					document.getElementById("ind6").style.display="none";
					document.getElementById("ind7").style.display="none";
					document.getElementById("ind8").style.display="none";
					document.getElementById("p").style.display="none";
					document.getElementById("p1").style.display="";
					document.getElementById("np").style.display="none";
					document.getElementById("lh").style.display="none";
					document.getElementById("lv").style.display="none";
					document.getElementById("deal").style.display="none";
					//document.getElementById("1").style.display="none";
					document.getElementById("3").style.display="none";
					document.getElementById("instrument_mode").style.display="none";
					document.getElementById("4").style.display="";
					document.getElementById("dateF1").style.display="";
					document.getElementById("dateF2").style.display="none";
					document.getElementById("dateFromD").style.display="";
					document.getElementById("withOutdateFromD").style.display="none";
					document.getElementById("dateT1").style.display="";
					document.getElementById("dateT2").style.display="none";
					document.getElementById("dateT3").style.display="none";
					document.getElementById("dateF3").style.display="none";
					document.getElementById("dateToD").style.display="";
					document.getElementById("fromDate").value="";
					document.getElementById("toDate").value="";
					document.getElementById("withOutdateToD").style.display="none";
					document.getElementById("5").style.display="none";
					document.getElementById("rf").style.display="";
					document.getElementById("rfv").style.display="";
					document.getElementById("st").style.display="none";
					document.getElementById("st1").style.display="none";
					document.getElementById("stDrop").style.display="none";
					document.getElementById("dealRange").style.display="none";
					document.getElementById("specific_deal").style.display="none";
					document.getElementById("loan_no").style.display="none";
					document.getElementById("group_id").style.display="none";
					document.getElementById("loannumber").style.display="none";
					document.getElementById("datewithcal").style.display="none";
					document.getElementById("datewithoutcal").style.display="none";
					document.getElementById("daterangewithoutcal").style.display="none";
					document.getElementById("daterange").style.display="none";
					document.getElementById("SDreport").style.display="none";
					document.getElementById("datewithcal1").style.display="none";
					document.getElementById("datewithoutcal1").style.display="none";
					document.getElementById("daterange1").style.display="none";
					document.getElementById("daterangewithoutcal1").style.display="none";
					document.getElementById("dateRangeForMaturity").style.display="none";
					document.getElementById("batch").style.display="none";
     				document.getElementById("Presentation_generate_batch").style.display="none";
	     			document.getElementById("Batch1").style.display="none";
	     			document.getElementById("Batch2").style.display="none";
	     			document.getElementById("batch_1").style.display="none";
	     			
	     			document.getElementById("bucket").value="A";
    				document.getElementById("productDescs").value="";
    				document.getElementById("productIds").value="";
    				//document.getElementById("loginBranchDesc").value="";
    				//document.getElementById("loginBranchId").value="";
    				document.getElementById("productCat").style.display="";
    				document.getElementById("branchS").style.display="";
    				document.getElementById("loanCAl").style.display="";
   
   				
    				if(reportName=="payable_receivable_payment"){
    					document.getElementById("productCat").style.display="";
        				document.getElementById("branchS").style.display="";
        				document.getElementById("loanCAl").style.display="";
        				document.getElementById("payable_receivable").style.display="";
    				}
        				if(reportName=="ops_mis_report"){
        					document.getElementById("productCat").style.display="";
            				document.getElementById("branchS").style.display="";
            				document.getElementById("loanCAl").style.display="";
        					
        				}
    				

    			//	document.getElementById("loginBranchDesc").value="";
    				//document.getElementById("loginBranchId").value="";
    				document.getElementById("instruments_mode").style.display="none";	
                    if(reportName=="payable_receivable_payment")

     					document.getElementById("payable_receivable").style.display="";
     				if(reportName=="ECS_Mandete_Cover_Letter_Header")
	     			{
	     				document.getElementById("reportformat").value="P";     					
	     				document.getElementById("reportformat").setAttribute("disabled",true);
	     			}
     				//sidharth
     				if( reportName=="Future_EMI_Receivable")
     				{
     					//alert('Future_EMI_Receivable');
     					document.getElementById("future1").style.display="";
     					document.getElementById("future2").style.display="";
     					document.getElementById("productCat").style.display="";
        				document.getElementById("branchS").style.display="";
        				document.getElementById("loanCAl").style.display="";
     				}	
     				if( reportName=="tat_report")
     				{
     					document.getElementById("pdc").style.display="none";
    					document.getElementById("sp_loan1").style.display="none";
    					document.getElementById("sp_loan2").style.display="inline";
    					document.getElementById("sp_loan3").style.display="none";
    					document.getElementById("sp_loan4").style.display="none";
    					document.getElementById("sp_loan5").style.display="none";
    					document.getElementById("sp_loan6").style.display="none";
    					document.getElementById("specific_loan").style.display="none";
    					document.getElementById("specificLoanNo").value="none";
    					document.getElementById("b1").style.display="none";
    					document.getElementById("6").style.display="none";
    					document.getElementById("ind1").style.display="none";
    					document.getElementById("ind").style.display="none";
    					document.getElementById("ind2").style.display="none";
    					document.getElementById("ind3").style.display="none";
    					document.getElementById("ind4").style.display="none";
    					document.getElementById("ind5").style.display="none";	
    					document.getElementById("ind6").style.display="none";
    					document.getElementById("ind7").style.display="none";
    					document.getElementById("ind8").style.display="none";
    					document.getElementById("p").style.display="none";
    					document.getElementById("p1").style.display="";
    					document.getElementById("np").style.display="none";
    					document.getElementById("lh").style.display="none";
    					document.getElementById("lv").style.display="none";
    					document.getElementById("deal").style.display="none";
    					//document.getElementById("1").style.display="none";
    					document.getElementById("3").style.display="none";
    					document.getElementById("instrument_mode").style.display="none";
    					document.getElementById("4").style.display="";
    					document.getElementById("dateF1").style.display="";
    					document.getElementById("dateF2").style.display="none";
    					document.getElementById("dateFromD").style.display="";
    					document.getElementById("withOutdateFromD").style.display="none";
    					document.getElementById("dateT1").style.display="";
    					document.getElementById("dateT2").style.display="none";
    					document.getElementById("dateT3").style.display="none";
    					document.getElementById("dateF3").style.display="none";
    					document.getElementById("dateToD").style.display="";
    					document.getElementById("fromDate").value="";
    					document.getElementById("toDate").value="";
    					document.getElementById("withOutdateToD").style.display="none";
    					document.getElementById("5").style.display="none";
    					document.getElementById("rf").style.display="";
    					document.getElementById("rfv").style.display="";
    					document.getElementById("st").style.display="none";
    					document.getElementById("st1").style.display="none";
    					document.getElementById("stDrop").style.display="none";
    					document.getElementById("dealRange").style.display="none";
    					document.getElementById("specific_deal").style.display="none";
    					document.getElementById("loan_no").style.display="none";
    					document.getElementById("group_id").style.display="none";
    					document.getElementById("loannumber").style.display="none";
    					document.getElementById("datewithcal").style.display="none";
    					document.getElementById("datewithoutcal").style.display="none";
    					document.getElementById("daterangewithoutcal").style.display="none";
    					document.getElementById("daterange").style.display="none";
    					document.getElementById("SDreport").style.display="none";
    					document.getElementById("datewithcal1").style.display="none";
    					document.getElementById("datewithoutcal1").style.display="none";
    					document.getElementById("daterange1").style.display="none";
    					document.getElementById("daterangewithoutcal1").style.display="none";
    					document.getElementById("dateRangeForMaturity").style.display="none";
    					document.getElementById("batch").style.display="none";
         				document.getElementById("Presentation_generate_batch").style.display="none";
    	     			document.getElementById("Batch1").style.display="none";
    	     			document.getElementById("Batch2").style.display="none";
    	     			document.getElementById("batch_1").style.display="none";
    	     			
    	     			document.getElementById("bucket").value="A";
        				document.getElementById("productDescs").value="";
        				document.getElementById("productIds").value="";
        				//document.getElementById("loginBranchDesc").value="";
        				//document.getElementById("loginBranchId").value="";
        				document.getElementById("instruments_mode").style.display="none";	
     					document.getElementById("productCat").value="";
        				document.getElementById("branchS").value="";
        				
     				}	
     				
     				if( reportName=="Booking_Report"|| reportName=="Booking_Report_Monthly"||reportName=="CERSAI_entry" || reportName=="Insurance")
     				{
     					document.getElementById("rf").style.display="none";
    					document.getElementById("rfv").style.display="none";
    					document.getElementById("sgk").style.display="";
    					document.getElementById("sgk1").style.display="";
    					document.getElementById("branchS").style.display="";
    					document.getElementById("productCat").style.display="";
    					document.getElementById("loanCAl").style.display="";
    					
     				}	
     				
     				if(reportName=="CERSAI_entry" || reportName=="Insurance")
     				{
     					document.getElementById("rf").style.display="none";
    					document.getElementById("rfv").style.display="none";
    					document.getElementById("branchS").style.display="none";
    					document.getElementById("productCat").style.display="none";
    					document.getElementById("loanCAl").style.display="none";
    					
     				}
     				
     				
     				
     				if(reportName=="transit_files_report" || reportName=="hold_Marked_Cases")
     				{
     					document.getElementById("rf").style.display="";
    					document.getElementById("rfv").style.display="";
    					document.getElementById("sgk").style.display="";
    					document.getElementById("sgk1").style.display="";
     				}
     				//sidharth ends
     				document.getElementById("statusL").style.display="none";
    				document.getElementById("statusV").style.display="none";
    				document.getElementById("approvalL").style.display="none";
    				document.getElementById("approvalV").style.display="none";
    				document.getElementById("productL").style.display="none";
    				document.getElementById("productV").style.display="none";
    			//	document.getElementById("branchL").style.display="none";
    				//document.getElementById("branchV").style.display="none"; 	
    				
    				
    				if(reportName=="login_breakup_report")
    				{
    					document.getElementById("approvalL").style.display="";
        				document.getElementById("approvalV").style.display="";
        				document.getElementById("productL").style.display="";
        				document.getElementById("productV").style.display="";  
    				}
    				if(reportName=="DailyLoginReport")
    				{
    					document.getElementById("productL").style.display="";
        				document.getElementById("productV").style.display=""; 
        				document.getElementById("productCat").style.display="none";
        				document.getElementById("loanCAl").style.display="none";
        				document.getElementById("branchS").style.display="none";
    				}	
    				if(reportName=="Login_Detail_MIS")
    				{
    				//	document.getElementById("branchL").style.display="";
        				//document.getElementById("branchV").style.display="";
        				document.getElementById("productL").style.display="";
        				document.getElementById("productV").style.display=""; 
        				document.getElementById("statusL").style.display="";
        				document.getElementById("statusV").style.display="";         				
    				}								
			}
			if (reportName=="login_Details_Report")
				{
				document.getElementById("loanCAl").style.display="none";
				}
			if(reportName=="chequebouncemain")
			{
				document.getElementById("rf").style.display="none";
				document.getElementById("rfv").style.display="none";
				document.getElementById("branchS").style.display="";
				document.getElementById("productCat").style.display="";
				document.getElementById("loanCAl").style.display="";
			}
			//Manish Code Start here
			if( reportName=="drawing_power_statement")
			{
				document.getElementById("branchid").value="none";
				document.getElementById("5").style.display="none";
				document.getElementById("rf").style.display="";
				document.getElementById("rfv").style.display="";
				document.getElementById("lh").style.display="none";
				document.getElementById("lv").style.display="none";
				document.getElementById("st").style.display="none";
				document.getElementById("stDrop").style.display="none";
				document.getElementById("3").style.display="none";
				document.getElementById("4").style.display="none";
				document.getElementById("fromDate").value="";
				document.getElementById("toDate").value="";
				document.getElementById("b1").style.display="none";
				document.getElementById("daterange1").style.display="";
				document.getElementById("drawing_power_statement").style.display="";
				document.getElementById("bankName1").value='';
				document.getElementById("bankBranch").value='';
				document.getElementById("fromdateforsd").value='';
				document.getElementById("todateforsd").value='';				
			}
			//Manish Code End here	
			// Pooja For LOD_Letter
			if( reportName=="LOD_Letter" ||  reportName=="PDC_exhaustion_letter"  || reportName=="SME_Sanction_Letter_Report_For_CM"){
			document.getElementById("IRR").style.display="none";
			document.getElementById("IRR1").style.display="none";
			document.getElementById("expectedIRR").style.display="none";
			}
			//Pooja End For LOD_Letter
			if( reportName=="Past_Cash_Flow" || reportName=="RSData"){
				document.getElementById("4").style.display="";
			}
     	}
		function generateReport()
		{
			var generateReport =document.getElementById("generateReport").value;
			var defReporttype =document.getElementById("defReporttype").value;	
			document.getElementById("companyLogo").checked=false;
			if(generateReport=='Y')
			{
				document.getElementById("generateReport").value="N";
				var reportName =document.getElementById("defReportName").value;	
				disableCal(reportName);
				var contextPath = document.getElementById("contextPath").value;	
				var url=contextPath+"/GenerateAllReports.do";	
				if(defReporttype=='H')
				{
					if(reportName=='receiptAllocationCollection')
					{
						if(loadFancyBox(url))
						{
							setTimeout(function(){printFancyBox('fancybox-frame')},5000);					
						}
					}
					else
					{
						loadFancyBox(url);
					}
				}
				else
				{
						document.getElementById("reportid").action=url;
						document.getElementById("reportid").submit();
				}
				return true;
			}		
		}
		function loadFancyBox(url)
		{
			$(document).ready(function () 
			{
				$.fancybox(
				{
				  //  'width': '100%',
					 'width': '1024px',
				    'height': '100%',
					'autoScale': true,
					'transitionIn': 'fade',
					'transitionOut': 'fade',
					'type': 'iframe',
					'href': url
				});
			});
			return true;
		}
		function printFancyBox(iFrame)
		{
			if (window.frames[iFrame].innerHTML != "") 
			{
				window.frames[iFrame].focus();            
	            try
	            {
	            	window.frames[iFrame].print();
	            }
	            catch(err)
	            {} 
	        } 
			return true;
		}
			
		function chckValidForRepay()
		{
			    var basePath=document.getElementById("contextPath").value;
			    var reportName=document.getElementById("reportName").value;	
			    var loanNo= document.getElementById("loanNo").value;
			    var fromdate=document.getElementById("fromDate").value;
				var todate=document.getElementById("toDate").value;
				var custName=document.getElementById("custName").value;	
				var InstrumentMode= document.getElementById("InstrumentMode").value;
				var status=document.getElementById("status").value;
				var day1= fromdate.substring(0,2);
				var day2= todate.substring(0,2);
				var month1=fromdate.substring(3,5);
				var month2=todate.substring(3,5);
				var year1=fromdate.substring(6);
				var year2=todate.substring(6);
				var formatD=document.getElementById("formatD").value;
				var dt1=getDateObject(fromdate,formatD.substring(2,3));
				var dt2=getDateObject(todate,formatD.substring(2,3));
				var rangeLimit=document.getElementById("dateRengeLimit").value;
				if(rangeLimit=='')
					rangeLimit='0';
				var dateRengeLimit=parseFloat(rangeLimit);
				var day=dateDifference(dt1,dt2);
			if(reportName=="")
			{	alert("First select Report Type");
				return false;
			}
			else				
			{
				if(loanNo=="" && fromdate=="" && todate=="")
					{	alert("Please enter From Date and To Date");
						return false;
					}
				if(fromdate && todate=="")
				{	alert("Please enter To Date Also");
					return false;
				}
				if(todate && fromdate=="")
				{	alert("Please enter From Date Also");
					return false;
				}
				if(parseFloat(year1)>parseFloat(year2))
				{	alert("To Date should greater than or equal to  From Date");
					return false;
				}
				else
				{
					 if(parseFloat(year1)==parseFloat(year2))
					 {
						if(parseFloat(month1)>parseFloat(month2))
						{	alert("To Date must greater than or equal to  From Date");
							return false;
						}
						else 
						{
							if(parseFloat(month1)==parseFloat(month2))
							{
								if(parseFloat(day1)>parseFloat(day2))
								{	alert("To Date must greater than or equal to  From Date");
									return false;
								}
							}
							if(day>dateRengeLimit)
							{
								alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
								return false;
							}

						}
					 }												
				 }
				
				document.getElementById("repaymentServicesReport").action=basePath+"/repaymentServicesReportsAction.do";
				document.getElementById("repaymentServicesReport").submit();
			}
		}
 function documentCollection()
			
	 {
		 
		 var fromLoan=document.getElementById("fromLoan").value;
		 var toLoan=document.getElementById("toLoan").value;
		 var fromDeal=document.getElementById("fromDeal").value;
		 var toDeal=document.getElementById("toDeal").value;
		 var loan_from_id=document.getElementById("lbx_loan_from_id").value;
		 var loan_to_id=document.getElementById("lbx_loan_to_id").value;
		 var Deal_from=document.getElementById("lbxDealNo").value;
		 var Deal_To=document.getElementById("lbxDealTo").value;
		 var stage=document.getElementById("stage").value;
		 if(fromLoan=="" && toLoan=="" && fromDeal=="" && toDeal=="")
		 {
	         alert("Deal-Range / Loan-Range is Required ");
	         
				 return false;
		 }
		 if(fromLoan  && (fromDeal  && toDeal))
		 {
			 alert("either select Deal-Range or Loan-Range ");
		 		 return false;
		 }	 
		 if(toLoan  && (fromDeal  && toDeal))
		 {
			 alert("either select Deal-Range or Loan-Range ");
		 		 return false;
		 }
		 if(fromDeal  && (fromLoan  && toLoan))
		 {
			 alert("either select Deal-Range or Loan-Range ");
		 		 return false;
		 }
		 if(toDeal  && (fromLoan  && toLoan))
		 {
			 alert("either select Deal-Range or Loan-Range ");
		 		 return false;
		 }
		 if(fromLoan  && (fromDeal  || toDeal)&& toLoan=="")
		 { 
		  alert("either select Deal-Range or Loan-Range");
		 		 return false;	
		 }
		 if(toLoan  && (fromDeal  || toDeal)&& fromLoan=="")
		 {
			 alert("either select Deal-Range or Loan-Rang"); 
			 return false;	
		 }
		 if(fromDeal  && (fromLoan  || toLoan)&& toDeal=="")
		 {
			 alert("either select Deal-Range or Loan-Range");  
			 return false;	
		 }
		 if(toDeal  && (fromLoan  || toLoan)&& fromDeal=="")
		 {
			 alert("either select Deal-Range or Loan-Range");  
			 return false;	
		 }
		 if(fromLoan  && !(fromDeal && toDeal)&& toLoan=="")
		 { 
		  alert("Please select toLoan also");
		 		 return false;	
		 }	 
		 if(toLoan  && !(fromDeal  && toDeal)&& fromLoan=="")
		 {
			 alert("Please select fromLoan also"); 
			 return false;	
		 }	
		 if(fromDeal  && !(fromLoan  && toLoan)&& toDeal=="")
		 {
			 alert("Please Select toDeal also");  
			 return false;	
		 }
		 if(toDeal  && !(fromLoan  && toLoan)&& fromDeal=="")
		 {
			 alert("Please Select fromDeal also");  
			 return false;
		 }
		 
		 
		 if(parseFloat(loan_from_id) > parseFloat(loan_to_id))
			{	alert("Initial loan no. must be less than or equal to Final loan no");
				return false;				
			}
		 if(parseFloat(Deal_from) > parseFloat(Deal_To))
			{	alert("Initial deal no. must be less than or equal to Final deal no");
				return false;				
			}
		 if(stage=="" )
			{	alert("Please select the stage.");
				return false;				
			} 
			 document.getElementById("documentCollectionForm").submit();
			 Window.location.reload();
	 }
 		function documentCollectionReset()
 		{
 			var contextPath =document.getElementById('contextPath').value ;
 			document.getElementById("documentCollectionForm").action=contextPath+"/documentCollection.do";
 			document.getElementById("documentCollectionForm").submit(); 
	 
 		}
 
	 function chequeStatusReport()
	 {
		 
		 var fromdate=document.getElementById("fromDate").value;
		 var todate=document.getElementById("toDate").value;	
		 var branch=document.getElementById("branch").value;
			var branch_name=document.getElementById("lbxBranchId").value;	
			if(branch=="Specific" && branch_name=="")
			{	
				alert("Branch Name is Required");
				document.getElementById("dealButton").focus();
				return false;
			}
		 if(fromdate=="" && todate=="")
			{	alert("Date Range Required");
					return false;
			} 
		if(fromdate && todate=="")
		{	alert("Please select To Date Also");
				return false;
		}
		if(todate && fromdate=="")
		{	alert("Please select From Date Also");
				return false;
	    }
		 var day1= fromdate.substring(0,2);
			var day2= todate.substring(0,2);
			var month1=fromdate.substring(3,5);
			var month2=todate.substring(3,5);
			var year1=fromdate.substring(6);
			var year2=todate.substring(6);
			
			if(parseInt(year1)>parseInt(year2))
			{	alert("To date must greater than or equal to  From date");
				return false;
			}
			else
			{
				 if(parseFloat(year1)==parseFloat(year2))
				 {
					if(parseFloat(month1)>parseFloat(month2))
					{	alert("To date must greater than or equal to  From date");
						return false;
					}
					else 
					{
						if(parseFloat(month1)==parseFloat(month2))
						{
							if(parseFloat(day1)>parseFloat(day2))
							{	alert("To date must greater than or equal to  From date");
								return false;
							}
							else
							{	document.getElementById("chequeStatusForm").submit();
								Window.location.reload();	
							}
						}
						else
						{	document.getElementById("chequeStatusForm").submit();
							Window.location.reload();
						}
			 		}
				}
				else
				{	document.getElementById("chequeStatusForm").submit();	
					Window.location.reload();
				}							
			 }
		      
		 document.getElementById("chequeStatusForm").submit();
		 Window.location.reload();
	 }
	
	 function loggedUserRefresh()
		{
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("userLoggedForm").action=contextPath+"/userLoggedInReportBehindAction.do";
			document.getElementById("userLoggedForm").submit(); 
	 
		}
	 
	 
	 function collectionDumpReportValidation()
		{
		
		var fromDate=document.getElementById('fromDate').value;		 
		 var toDate=document.getElementById('toDate').value;		
		 var formatD=document.getElementById('formatD').value;	
		 var dt1=getDateObject(fromDate,formatD.substring(2, 3));		
		 var dt3=getDateObject(toDate,formatD.substring(2, 3));		
		 var c="";
		 var msg="";
		 
		 if( fromDate=="" || toDate=="" )
			{		
				if(fromDate=="")
					c="*From Date is required.\n";
				if(toDate=="")
					c+="* To Date is required.\n";
				alert(c);
		
		if(toDate=="")
				document.getElementById("toDate").focus();		
		if(fromDate=="")
				document.getElementById("fromDate").focus();
		return false;
			}
		 if(fromDate!='' && toDate !='')
			 
			 {
			 	if(dt1>dt3)
			 	{
			 	msg="To date can not be smaller than From date";
				//alert("To date can not be smaller than From date");
				  document.getElementById("mybutton").removeAttribute("disabled");
				  return false;
			 	}
			 	var rangeLimit=document.getElementById("dateRengeLimit").value;
				if(rangeLimit=='')
					rangeLimit='0';
				var day=dateDifference(dt1,dt3);
				var dateRengeLimit=parseFloat(rangeLimit);
				if(day>dateRengeLimit)
				{
					msg=+"Days Difference can't be greater than "+rangeLimit+" Days.";
					//alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
					return false;
				}
				  }	
		 if(msg=="" && c=="")
			 {
			 	//document.getElementById("processingImage").style.display = '';
				document.getElementById("collectionDumpReportForm").submit();
				Window.location.reload();
				
			return true;
			 }

	 
		} 

	 
	 
	 
	 
	 
	 //neeraj payment memo
function paymentMemoReport()
{
	var loanNumber =document.getElementById('loanNumber').value ;
	if(loanNumber=='')
	{
		alert("Loan No is required.");
		return false;
	}
	else
	{
		document.getElementById("paymentMemoReport").submit();
		Window.location.reload(); 
	}
	 
}
//neeraj dump download
function genrateDumpDownload()
{
	
	var asOnDatePrs=document.getElementById("asOnDatePrs").value;
	var dateRangeFlag=document.getElementById("dateRangeFlag").value;
	 var recordDesc = document.getElementById("recordDesc").value;
		if(recordDesc=='')
			{
				alert(" Record Description is required.");
				return false;
			}
	if(asOnDatePrs=='N' && dateRangeFlag=='Y')
	{
		var formate=document.getElementById("formatD").value;
		var fromDate=document.getElementById("fromDate").value;
		var toDate=document.getElementById("toDate").value;
		if(fromDate=="" && toDate=="")
		{
			alert("Date range is required.");
			return false;		
		}
		else
		{
			if((fromDate=="" && toDate !="")||(fromDate!="" && toDate ==""))
			{
				if(fromDate=="" && toDate !="")
				{
					alert("From Date is required.");
					return false;
				}
				if(fromDate!="" && toDate =="")
				{
					alert("To Date is required.");
					return false;		
				}
			}
			else if(fromDate!="" && toDate !="")
			{
				var formatD=document.getElementById("formatD").value;
				var dt1=getDateObject(fromDate,formatD.substring(2,3));
				var dt2=getDateObject(toDate,formatD.substring(2,3));
				if(dt2<dt1)
				{
					alert("To date can't be less than From date.");
					return false;
				}			
			}
		}
	}
	else if(asOnDatePrs=='Y' && dateRangeFlag=='N')
	{
		var asOnDate=document.getElementById("asOnDate").value;
		if(asOnDate=="")
		{
			alert("As on Date is required.");
			return false;
		}
	}
	var keyOnePr=document.getElementById("keyOnePr").value;
	var keyTwoPr=document.getElementById("keyTwoPr").value;
	var label1=document.getElementById("label1").value;
	var label2=document.getElementById("label2").value;
	var query1Flag=document.getElementById("query1Flag").value;
	var query1Label=document.getElementById("query1Label").value;
	var query2Flag=document.getElementById("query2Flag").value;
	var query2Label=document.getElementById("query2Label").value;
	var query3Flag=document.getElementById("query3Flag").value;
	var query3Label=document.getElementById("query3Label").value;
	var query4Flag=document.getElementById("query4Flag").value;
	var query4Label=document.getElementById("query4Label").value;
	if(query1Flag.trim()=="Y")
	{
		var query1=document.getElementById("query1").value;
		if(query1.trim()=="")
		{
			alert(query1Label+" is Required.");
			return false;
		}
	}
	if(query2Flag.trim()=="Y")
	{
		var query2=document.getElementById("query2").value;
		if(query2.trim()=="")
		{
			alert(query2Label+" is Required.");
			return false;
		}
	}
	if(query3Flag.trim()=="Y")
	{
		var query3=document.getElementById("query3").value;
		if(query3.trim()=="")
		{
			alert(query3Label+" is Required.");
			return false;
		}
	}
	if(query4Flag.trim()=="Y")
	{
		var query4=document.getElementById("query4").value;
		if(query4.trim()=="")
		{
			alert(query4Label+" is Required.");
			return false;
		}
	}
	if(keyOnePr.trim()=="Y")
	{
		var paramValueOne=document.getElementById("paramValueOne").value;
		if(paramValueOne.trim()=="")
		{
			alert(label1+" is Required.");
			return false;
		}
	}
	if(keyTwoPr.trim()=="Y")
	{
		var paramValueTwo=document.getElementById("paramValueTwo").value;
		if(paramValueTwo.trim()=="")
		{
			alert(label2+" is Required.");
			return false;
		}
	}
	//Virender code start
	var recordDesVal = document.getElementById("recordDesc").value;
	if(recordDesVal=='HUNTER_DUMP'){
	
		var formatVar=document.getElementById("format").value;
		if(formatVar.trim()=="")
		{
			alert("Format is Required.");
			return false;
		}
		
		var custTypeVar=document.getElementById("custType").value;
		if(custTypeVar.trim()=="")
		{
			alert("custType is Required.");
			return false;
		}
		
	var contextPath = document.getElementById("contextPath").value;
		document.getElementById("dumpDownLoad").action=contextPath+"/HunterDumpDownLoadDispatchAction.do?method=hunterReportGenerator&formatVar='"+formatVar+"'&custTypeVar='"+custTypeVar+"'";
		document.getElementById("dumpDownLoad").submit();
	 	return true;
	}
	else{
		var contextPath = document.getElementById("contextPath").value;
	document.getElementById("dumpDownLoad").action=contextPath+"/dumpDownLoadDispatchAction.do?method=reportGenerator";
	document.getElementById("dumpDownLoad").submit();
	 	return true;
	}
}
//neeraj for cibil report
function cibilReport()
{
	var report =document.getElementById("report").value;
	var fromDate=document.getElementById("fromDate").value;
	if(report=='cibilReport' || report== 'CIBIL_GUARANTOR_REPORT')
		{
	
	var toDate=document.getElementById("toDate").value;
	if(fromDate=='' && toDate=='')
	{
		alert('Date range is Required.');
		document.getElementById("fromDate").focus();
		return false;
	}
	else if(fromDate=='')
	{
		alert('From Approval Date is required.');
		document.getElementById("fromDate").focus();
		return false;
	}
	else if(toDate=='')
	{
		alert('To Approval Date is required.');
		document.getElementById("toDate").focus();
		return false;
	}
	else if(fromDate!='' && toDate != '')
	{
			var formatD=document.getElementById("formatD").value;
			var dt1=getDateObject(fromDate,formatD.substring(2,3));
			var dt2=getDateObject(toDate,formatD.substring(2,3));
			if(dt2<dt1)
			{
				alert("To Approval Date can't be less than From Approval Date.");
				return false;
			}			
	}

		}
	else{
		
		if(fromDate=='')
		{
			alert('From Approval Date is required.');
			document.getElementById("fromDate").focus();
			return false;
		}
	}
	var contextPath = document.getElementById("contextPath").value;
	document.getElementById("cibilReportGenerate").action=contextPath+"/cibilReportGenerate.do";
	document.getElementById("cibilReportGenerate").submit();
	return true;
}

//Shashank Agnihotri for Nesl Report
function neslReport()
{
	var report =document.getElementById("report").value;
	var fromDate=document.getElementById("fromDate").value;
	if(report=='NeSL_DOWNLOAD_BIL_LAP' || report== 'NeSL_DOWNLOAD_LC')
		{
	
	if(fromDate=='')
	{
		alert('Date is Required.');
		document.getElementById("fromDate").focus();
		return false;
	}
	
	else (fromDate!='')
	{

	var contextPath = document.getElementById("contextPath").value;
	document.getElementById("neslReportGenerate").action=contextPath+"/neslReportGenerate.do";
	document.getElementById("neslReportGenerate").submit();
	return true;
}
		}
}

//aditi for welcome letter

function disabledloan()
{

var approvalDateFrom=document.getElementById('approvalDateFrom').value;
	var approvalDateTo=document.getElementById('approvalDateTo').value;
	if(approvalDateFrom=='' && approvalDateTo=='' )
	{
		document.getElementById("loannumber_lov1").style.display="inline";
	}
	else
	{
		document.getElementById("loannumber_lov1").style.display="none";
	}
	
}


//aditi :  code for CP Reports

function hideLov()
{
	var lovid=document.getElementById("loanno").value;
	if(lovid=='A') 
	{
		document.getElementById("dealNoFromSButton").style.visibility="hidden";
		document.getElementById("dealNoToSButton").style.visibility="hidden";
		document.getElementById("from").value="";
		document.getElementById("to").value="";
		document.getElementById("lbxDealNo").value="";
		document.getElementById("lbxDealTo").value="";
		document.getElementById("fromDeal").value="";
		document.getElementById("toDeal").value="";
		document.getElementById("d10").style.display="none";
		document.getElementById("d11").style.display="";
		document.getElementById("d20").style.display="none";
		document.getElementById("d21").style.display="";
	}
	else
	{
		document.getElementById("dealNoFromSButton").style.visibility="visible";
		document.getElementById("dealNoToSButton").style.visibility="visible";
		document.getElementById("d10").style.display="";
		document.getElementById("d11").style.display="none";
		document.getElementById("d20").style.display="";
		document.getElementById("d21").style.display="none";
	}
}

function submitReport()
{
	var reportName=document.getElementById("reportName").value;	
	if(reportName=="select")
	{	alert("Report Type Required");
		document.getElementById('reportName').focus();
		return false;
	}
	if(reportName=="tat_report" || reportName =="Deals_Pending_Approved_Approved_Rejected_Cancelled_During_a_Period" || reportName=="Applicants_Linked_To_Deals" || reportName=="FI_REPORT" || reportName =="Deals_Pending_Approved_Rejected_Cancelled_During_a_Period" )
	{	
	
		var loanno=document.getElementById("loanno").value;		
		if(loanno=='R')
		{
			
			var deal_from=document.getElementById("fromDeal").value;
			var deal_from_id=document.getElementById("lbxDealNo").value;
			var deal_to=document.getElementById("toDeal").value;
			var deal_to_id=document.getElementById("lbxDealTo").value;			
			if(deal_from && deal_to=="")
			{
				alert("Please enter Deal To also.");
				return false;
			}
			if(deal_to && deal_from=="")
			{
				alert("Please enter Deal From also.");
				return false;
			}
			if(deal_to=="" && deal_from=="")
			{
				alert("Deal Range is required.");
				return false;
			}
			if(parseFloat(deal_from_id) > parseFloat(deal_to_id))
			{	
				alert("Initial Deal Number should  be less than or equal to Final Deal Number.");
				return false;				
			}			
		}

		var formatD=document.getElementById("formatD").value;
		var fromdate=document.getElementById("fromDate").value;
		var todate=document.getElementById("toDate").value;
		var branch=document.getElementById("branch").value;
		var branch_name=document.getElementById("lbxBranchId").value;	
		if(branch=="Specific" && branch_name=="")
		{	
			alert("Branch Name is Required");
			document.getElementById("dealButton").focus();
			return false;
		}	
		if(fromdate=='' && todate !='')
		{
			alert("Please enter From date.");
			return false;
		}
		if(fromdate!='' && todate =='')
		{
			alert("Please enter To date.");
			return false;
		}
		if(fromdate!='' && todate !='')
		{		
			var dt1=getDateObject(fromdate,formatD.substring(2,3));
			var dt2=getDateObject(todate,formatD.substring(2,3));
			if(dt2<dt1)
			{
				alert("To Date can't be less than From Date."); 
				return false;
			}
		}
		if(reportName =="Deals_Pending_Approved_Approved_Rejected_Cancelled_During_a_Period" || reportName =="Applicants_Linked_To_Deals" || reportName=="FI_REPORT" || reportName=="Deals_Pending_Approved_Rejected_Cancelled_During_a_Period" )
		{ var branch=document.getElementById("branch").value;
		var branch_name=document.getElementById("lbxBranchId").value;	
		if(branch=="Specific" && branch_name=="")
		{	
			alert("Branch Name is Required");
			document.getElementById("dealButton").focus();
			return false;
		}	
			if (fromdate=="" && todate =="" ) 
			{  
				alert("Date Range is Required.");
				return false;
			}
			if(fromdate!='' && todate !='')
			{	
				var rangeLimit=document.getElementById("dateRengeLimit").value;
				if(rangeLimit=='')
					rangeLimit='0';
				var day=dateDifference(dt1,dt2);
				var dateRengeLimit=parseFloat(rangeLimit);
				if(day>dateRengeLimit)
				{
					alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
					return false;
				}
			}
		}
		document.getElementById("reportForm").submit();	
		Window.location.reload();
	}
	if(reportName=="deal_details_header" ||reportName=="deal_Movement_Report" ||reportName=="LEAD_FOLLOWUP_REPORT" || reportName=="termSheet_Report" || reportName=="sanction_letter_after_deal" || reportName=="SME_Sanction_Letter_Report_For_CP")
	{
		var specificDealNo=document.getElementById("specificDealNo").value;
		if(specificDealNo=="")
		{	alert("Deal Number is Required");		
			return false;
		}
		document.getElementById("reportForm").submit();	
		Window.location.reload();
			
	}	
	//pooja code for MIS_REPORT
	if(reportName=="MIS_REPORT")
	{
		var formatD=document.getElementById("formatD").value;
		var asonDate=document.getElementById("fromDate").value;
		var businessdate=document.getElementById("businessdate").value;
		if(asonDate=="")
		{	alert("As On Date is Required");		
			return false;
		}
		if(asonDate!='' && businessdate !='')
		{		
			var dt1=getDateObject(businessdate,formatD.substring(2,3));
			var dt2=getDateObject(asonDate,formatD.substring(2,3));			
			
			if(dt2>=dt1)
			{
				alert("As On Date should be less then Business Date."); 
				return false;
			}
			
		}
		document.getElementById("reportForm").submit();	
		Window.location.reload();
	}
	//pooja code for MIS_REPORT
	if(reportName=="credit_appraisal_memo")
	{	
		var dealID=document.getElementById("repayDealNo").value;
		if(dealID=='')
		{
			alert("Deal No is Required");
			document.getElementById('reportName').focus();
			return false;
		}
		document.getElementById("reportForm").submit();	
		Window.location.reload();
	}	
	if(reportName=="LOAN_APPLICATION_FORM" || reportName=="Insurance_Application_Form"  ){
		var salesDealId=document.getElementById("salesDealId").value;
		if(salesDealId=="")
		{	alert("Sales / Deal Number is Required");
			return false;
		}
		document.getElementById("reportForm").submit();	
		Window.location.reload();
	}
	if(reportName=="deal_repayment_calculation")
	{	
		var dealID=document.getElementById("repayDealNo").value;
		if(dealID=='')
		{
			alert("Deal No is Required");
			document.getElementById('reportName').focus();
			return false;
		}
		document.getElementById("reportForm").submit();	
		Window.location.reload();
	}		
	if(reportName=="deal_dump_report" )
	{	
		document.getElementById("reportForm").submit();	
		Window.location.reload();
	}	
	if(reportName=="lead_Report" )
	{
		var formatD=document.getElementById("formatD").value;
		var fromdate=document.getElementById("fromDate").value;
		var todate=document.getElementById("toDate").value;
		var branch=document.getElementById("branchDet").value;
		var branch=document.getElementById("branch").value;
		var branch_name=document.getElementById("lbxBranchId").value;	
		if(branch=="Specific" && branch_name=="")
		{	
			alert("Branch Name is Required");
			document.getElementById("dealButton").focus();
			return false;
		}	
		
		if(fromdate=="" && todate=="" && branch =="" )
		{
			alert("Date Range is Required.");
			return false;
		}
		if(fromdate=='' && todate !='')
		{
			alert("Please enter From date.");
			return false;
		}
		if(fromdate!='' && todate =='')
		{
			alert("Please enter To date.");
			return false;
		}
		if(fromdate!='' && todate !='')
		{		
			var dt1=getDateObject(fromdate,formatD.substring(2,3));
			var dt2=getDateObject(todate,formatD.substring(2,3));
			var rangeLimit=document.getElementById("dateRengeLimit").value;
			if(rangeLimit=='')
				rangeLimit='0';
			var dateRengeLimit=parseFloat(rangeLimit);
			var day=dateDifference(dt1,dt2);
			if(dt2<dt1)
			{
				alert("To Date can't be less than From Date."); 
				return false;
			}
			if(day>dateRengeLimit)
			{
				alert("Days Difference can't be greater than "+rangeLimit+" Days."); 
				return false;
			}
		}
		document.getElementById("reportForm").submit();	
		Window.location.reload();
	}
	if(reportName=="Branch_credit_login_Report" )
	{
		var formatD=document.getElementById("formatD").value;
	    var fromdate=document.getElementById("fromDate").value;
		var todate=document.getElementById("toDate").value;
		var branch=document.getElementById("branch").value;
		var branch_name=document.getElementById("lbxBranchId").value;	
		if(branch=="Specific" && branch_name=="")
		{	
			alert("Branch Name is Required");
			document.getElementById("dealButton").focus();
			return false;
		}	
		
		if(fromdate=="" && todate=="" )
		{
			alert("Date Range is Required.");
			return false;
		}
		if(fromdate=='' && todate !='')
		{
			alert("Please enter From date.");
			return false;
		}
		if(fromdate!='' && todate =='')
		{
			alert("Please enter To date.");
			return false;
		}
		if(fromdate!='' && todate !='')
		{		
			var dt1=getDateObject(fromdate,formatD.substring(2,3));
			var dt2=getDateObject(todate,formatD.substring(2,3));			
			
			if(dt2<dt1)
			{
				alert("To Date can't be less than From Date."); 
				return false;
			}
			
		}
		document.getElementById("reportForm").submit();	
		Window.location.reload();
	}

}


function disableReport()
{
	var reportName=  document.getElementById("reportName").value;
	document.getElementById("mulProduct").style.display="none";
	//alert("report name : " + reportName);
	if((reportName=="Deals_Pending_Approved_Approved_Rejected_Cancelled_During_a_Period") ||reportName=="tat_report" || reportName=="Applicants_Linked_To_Deals" || reportName=="FI_REPORT" || reportName=="Deals_Pending_Approved_Rejected_Cancelled_During_a_Period" )
	{
		if(reportName=="tat_report")
		{
			document.getElementById("mulProduct").style.display="";
			document.getElementById("productCat").style.display="";
			//document.getElementById("loanCAl").style.display="";
			document.getElementById("branchS").style.display="";
		}

		document.getElementById("branchRMRow").style.display="none";
		document.getElementById("rf").style.display="";
		document.getElementById("rfv").style.display="";
		//document.getElementById("branchRow").style.display="";
		document.getElementById("dealnStatus").style.display="";
		document.getElementById("deal").style.display="";
		document.getElementById("lv").style.display="";
		document.getElementById("dateRow").style.display="";
		document.getElementById("dealRange").style.display="";
		document.getElementById("dateFromD").style.display="";
		document.getElementById("dateF1").style.display="none";
		document.getElementById("dateF2").style.display="";
		document.getElementById("dateT1").style.display="none";
		document.getElementById("dateT2").style.display="";
		document.getElementById("withOutdateFromD").style.display="none";
		document.getElementById("dateToD").style.display="";
		document.getElementById("withOutdateToD").style.display="none";
		document.getElementById("specific_deal").style.display="none";
		document.getElementById("specific_lead").style.display="none";
		document.getElementById("repay_calc").style.display="none";
		document.getElementById("st").style.display="";
		document.getElementById("stDrop").style.display="";
		document.getElementById("s1").style.display="";
		document.getElementById("s2").style.display="none";
		document.getElementById("productCat").style.display="";
		//document.getElementById("loanCAl").style.display="";
		document.getElementById("branchS").style.display="";
		
		if(reportName=="Applicants_Linked_To_Deals" || reportName=="FI_REPORT")
		{
			document.getElementById("s1").style.display="none";
			document.getElementById("s2").style.display="";
			document.getElementById("productCat").style.display="";
			//document.getElementById("loanCAl").style.display="";
			document.getElementById("branchS").style.display="";
		}
		if(reportName=="FI_REPORT"){
			document.getElementById("st").style.display="none";
			document.getElementById("stDrop").style.display="none";
			document.getElementById("productCat1").style.display="none";
			document.getElementById("productCat2").style.display="none";
		}
	
	}
	if(reportName=="deal_repayment_calculation" || reportName=="credit_appraisal_memo")
	{
		document.getElementById("branchRMRow").style.display="none";
		document.getElementById("rf").style.display="";
		document.getElementById("rfv").style.display="";
		//document.getElementById("branchRow").style.display="none";
		document.getElementById("dealnStatus").style.display="none";
		document.getElementById("dealRange").style.display="none";
		document.getElementById("dateRow").style.display="none";
		document.getElementById("specific_deal").style.display="none";
		document.getElementById("specific_lead").style.display="none";
		document.getElementById("repay_calc").style.display="";
		document.getElementById("productCat").style.display="none";
		document.getElementById("branchS").style.display="none";
		//document.getElementById("loanCAl").style.display="none";
	}	
	if(reportName=="deal_details_header" || reportName=="deal_Movement_Report" || reportName=="LEAD_FOLLOWUP_REPORT" || reportName=="termSheet_Report" || reportName=="sanction_letter_after_deal" || reportName=="SME_Sanction_Letter_Report_For_CP")
	{
		document.getElementById("branchRMRow").style.display="none";
		document.getElementById("rf").style.display="none";
		document.getElementById("rfv").style.display="none";
		//document.getElementById("branchRow").style.display="none";
		document.getElementById("dealnStatus").style.display="none";
		document.getElementById("dealRange").style.display="none";
		document.getElementById("dateRow").style.display="none";
		document.getElementById("specific_deal").style.display="";
		document.getElementById("specific_lead").style.display="none";
		document.getElementById("repay_calc").style.display="none";
		document.getElementById("productCat").style.display="none";
		//document.getElementById("loanCAl").style.display="none";
		document.getElementById("branchS").style.display="none";
		if(reportName=="SME_Sanction_Letter_Report_For_CP" )  //shubham
		{
			document.getElementById("pdf").style.display=""; 	
	}	
	}
	if(reportName=="LOAN_APPLICATION_FORM" || reportName=="Insurance_Application_Form" ) 
	{
		document.getElementById("specific_deal").style.display="none";
		document.getElementById("lvl").style.display="";	
		document.getElementById("lvl2").style.display="";
		document.getElementById("salesDeal").style.display="";
	}
	// Pooja For MIS_REPORT
	if( reportName=="MIS_REPORT"){
		document.getElementById("branchRMRow").style.display="none";
		document.getElementById("rf").style.display="none";
		document.getElementById("rfv").style.display="none";
		//document.getElementById("branchRow").style.display="none";
		document.getElementById("dealnStatus").style.display="none";
		document.getElementById("dealRange").style.display="none";
		document.getElementById("dateRow").style.display="";
		document.getElementById("dateT1").style.display="none";
		document.getElementById("dateT2").style.display="none";
		document.getElementById("dateToD").style.display="none";
		document.getElementById("specific_deal").style.display="none";
		document.getElementById("specific_lead").style.display="none";
		document.getElementById("repay_calc").style.display="none";
		document.getElementById("productCat").style.display="none";
		//document.getElementById("loanCAl").style.display="none";
		document.getElementById("branchS").style.display="none";
		//document.getElementById("dateToD").style.display="none";
	}
	//Pooja End For MIS_REPORT
	if( reportName=="deal_dump_report" ||reportName=="lead_Report")
	{
		document.getElementById("rf").style.display="none";
		document.getElementById("rfv").style.display="none";
	//	document.getElementById("branchRow").style.display="none";
		document.getElementById("dealnStatus").style.display="none";
		document.getElementById("dealRange").style.display="none";
		document.getElementById("dateRow").style.display="none";
		document.getElementById("specific_deal").style.display="none";
		document.getElementById("branchRMRow").style.display="none";
		document.getElementById("specific_lead").style.display="none";
		document.getElementById("repay_calc").style.display="none";	
		document.getElementById("productCat").style.display="none";
		document.getElementById("branchS").style.display="none";
		//document.getElementById("loanCAl").style.display="none";
	}
	if(reportName=="lead_Report")
	{
		//document.getElementById("branchRow").style.display="none";
		document.getElementById("dealnStatus").style.display="none";
		document.getElementById("dealRange").style.display="none";
		document.getElementById("specific_deal").style.display="none";
		document.getElementById("specific_lead").style.display="none";
		document.getElementById("repay_calc").style.display="none";			
		document.getElementById("rf").style.display="";
		document.getElementById("rfv").style.display="";
		document.getElementById("branchRMRow").style.display="none";
		document.getElementById("dateRow").style.display="";
		document.getElementById("dateFromD").style.display="";
		document.getElementById("dateF1").style.display="none";
		document.getElementById("dateF2").style.display="";
		document.getElementById("dateT1").style.display="none";
		document.getElementById("dateT2").style.display="";
		document.getElementById("withOutdateFromD").style.display="none";
		document.getElementById("dateToD").style.display="";
		document.getElementById("withOutdateToD").style.display="none";
		document.getElementById("productCat").style.display="";
		document.getElementById("branchS").style.display="";
		//document.getElementById("loanCAl").style.display="";
	}
	if(reportName=="Branch_credit_login_Report")
	{	
		//document.getElementById("branchRow").style.display="none";
		document.getElementById("dealnStatus").style.display="none";
		document.getElementById("dealRange").style.display="none";
		document.getElementById("specific_deal").style.display="none";
		document.getElementById("specific_lead").style.display="none";
		document.getElementById("repay_calc").style.display="none";			
		document.getElementById("rf").style.display="";
		document.getElementById("rfv").style.display="";
		document.getElementById("branchRMRow").style.display="none";
		document.getElementById("dateRow").style.display="";
		document.getElementById("dateFromD").style.display="";
		document.getElementById("dateF1").style.display="";
		document.getElementById("dateF2").style.display="none";
		document.getElementById("dateT1").style.display="";
		document.getElementById("dateT2").style.display="none";
		document.getElementById("withOutdateFromD").style.display="none";
		document.getElementById("dateToD").style.display="";
		document.getElementById("withOutdateToD").style.display="none";
		document.getElementById("productCat").style.display="";
		document.getElementById("branchS").style.display="";
		//document.getElementById("loanCAl").style.display="";
	
	}	
}
function loadReportJSP()
{
	document.getElementById("fromDate").value="";
	document.getElementById("toDate").value="";
	document.getElementById("branchid").value="";
	document.getElementById("from").value="";
	document.getElementById("to").value="";
	document.getElementById("reportName").selectedIndex='';
	document.getElementById("branch").selectedIndex="";
	document.getElementById("loanno").selectedIndex="";
}
//method added by neeraj tripathi
function summaryReport()
{
//	alert("summaryReport");
	var fromdate=document.getElementById("fromDate").value;
	var todate=document.getElementById("toDate").value;
	
	var formatD=document.getElementById("formatD").value;
	
	if(fromdate=='' && todate !='')
	{
		alert("Please enter From date.");
		return false;
	}
	if(fromdate!='' && todate !='')
	{		
		var dt1=getDateObject(fromdate,formatD.substring(2,3));
		var dt2=getDateObject(todate,formatD.substring(2,3));
		if(dt2<dt1)
		{
			alert("From Date can't be greater than To Date."); 
			return false;
		}
	}  
	var reportformat=document.getElementById("reportformat").value;
	var contextPath = document.getElementById("contextPath").value;
	var source=document.getElementById("source").value;

	if(source=='CP')
	{
	  document.getElementById("summaryReport").action=contextPath+"/summaryReportAction.do?method=cpStateReport&p_report_format="+reportformat+"&p_form_date="+fromdate+"&p_to_date="+todate+"&flow=F&parent=N";
	}
	if(source=='CM')
	{
		var dashboard=document.getElementById("dashboard").value;
	
		if(dashboard=='P')
		{
		  document.getElementById("summaryReport").action=contextPath+"/summaryReportAction.do?method=cm1StateReport&p_report_format="+reportformat+"&p_form_date="+fromdate+"&p_to_date="+todate+"&flow=F&parent=N";
		}
		if(dashboard=='T')
		{
		  document.getElementById("summaryReport").action=contextPath+"/summaryReportAction.do?method=cm2StateReport&p_report_format="+reportformat+"&p_form_date="+fromdate+"&p_to_date="+todate+"&flow=F&parent=N";
		}
	}
	document.getElementById("summaryReport").submit();
	return true;
	
}

function SDreportdisable(){
	if(document.getElementById("AsOnDateForSD").checked==true){
		document.getElementById("AsOnDateForSD").value = 'Y';
		document.getElementById("datewithcal1").style.display="none";
		document.getElementById("datewithoutcal1").style.display="";
	    document.getElementById("daterange1").style.display="none";
		document.getElementById("daterangewithoutcal1").style.display="";
		}
	else
	{
		document.getElementById("AsOnDateForSD").value = 'N';
	    document.getElementById("datewithcal1").style.display="";
		document.getElementById("datewithoutcal1").style.display="none";
		document.getElementById("daterange1").style.display="";
		document.getElementById("daterangewithoutcal1").style.display="none";
		}
}

function disabledcheckbox()
{

var Fromdate=document.getElementById('fromdateforsd').value;
	var todate=document.getElementById('todateforsd').value;
	if(Fromdate=='' && todate=='' )
	{
		document.getElementById("SDreport").style.display="";
	}
	else
	{
		document.getElementById("SDreport").style.display="none";
	}
	
}

//aditi :  code for Maturity ,reconcillation,EMI due reports

function disableLoanFormaturity()
{

var DateFrom=document.getElementById('dateFrom').value;
	var DateTo=document.getElementById('dateTo').value;
	if(DateFrom=='' && DateTo=='' )
	{
		document.getElementById("specific_loan").style.display="inline";
	}
	else
	{
		document.getElementById("specific_loan").style.display="none";
	}
	
}
//aditi :  code for batch Report

function disableBatchNo()
{

var presentationDate=document.getElementById('presentationDateForBatch').value;
	if(presentationDate=='')
	{
		document.getElementById("Batch2").style.display="inline";
	}
	else
	{
		document.getElementById("Batch2").style.display="none";
	}
	
}
//METHOD ADDED BY NEERAJ  FOR POPUP OF DASHBOARD
function openNewWindowForReport(url,windoName) 
{
  url=url+"&windoName="+windoName;
  popupWin= window.open(url,windoName,'menubar, toolbar, location, directories, status, scrollbars, resizable, dependent, width=640, height=480, left=400, top=400')
  if (window.focus) 
     popupWin.focus();
}

//aditi :  code for EMI DUE REPORT

function callLOV()
{
	var reportName=document.getElementById("reportName").value;	
if(reportName=="EMI_DUE_Report" || reportName=="ACH_TRANSACTION")
	openLOVCommon(168,'reportid','specificLoanNo','disbursalStatus','', 'disbursalStatus','','disableDateForMaturity','abc');
	else
	{
			openLOVCommon(168,'reportid','specificLoanNo','','', '','','disableDateForMaturity','abc');
	}
}
function callForClosureLOV()
{
	var reportName=document.getElementById("reportName").value;	
	if(reportName=="forClosureMemoReport" || reportName=="Foreclosure_One" )
		openLOVCommon(402,'reportid','specificLoanNo','','', '','','','abc');
	else if(reportName=="Delivery_Order_Report")
		openLOVCommon(490,'reportid','specificLoanNo','','', '','','','abc');
	else if(reportName=="GET_ACTUAL_IRR")
		openLOVCommon(563,'reportid','specificLoanNo','','', '','','','abc');
	else if(reportName=="AUforeClosure_simulation_report")
		openLOVCommon(405,'reportid','specificLoanNo','','', '','','','abc','rvFlag');
	else if(reportName=="INSTRUMENT_SWAPPING_REPORT")
		openLOVCommon(5055,'reportid','specificLoanNo','','', '','','','abc');
	else if(reportName=="Early_Closure_AFC_Report")
		openLOVCommon(720,'reportid','specificLoanNo','','', '','','','abc');
	else if(reportName=="Maturity_Closure_AFC_Report")
		openLOVCommon(721,'reportid','specificLoanNo','','', '','','','abc');
	else if(reportName=="Gold_loan" || reportName=="Gold_loan_Aruna")
		openLOVCommon(6080,'reportid','specificLoanNo','','', '','','','abc');
	else
		openLOVCommon(405,'reportid','specificLoanNo','','', '','','','abc','rvFlag');
}
//sidharth  report
function genrateAdHocReport()
{
	var query=(document.getElementById("query").value).trim();
	var trimedQuery=query.substring(0,6);
	if(query=='')
	{
		alert("Please enter the query");
		return false;		
	}
	else if(trimedQuery.toUpperCase()!="SELECT")
		{
		alert("Please enter select query only");
		return false;
		}
		
	else{
	var contextPath = document.getElementById("contextPath").value;
	document.getElementById("adhocForm").action=contextPath+"/adhocreportsDispatchAction.do?method=reportAdHoc";
	document.getElementById("adhocForm").submit();
 	return true;
	}
}

//aditi :  code for OD and LPP

function LOVForOD()
{
	var reportName=document.getElementById("reportName").value;	
	if(reportName=="OD_Calculation_Report"||reportName=="OD_SIMULATION_REPORT"||reportName=="OD_Disbursal_Wise_Report")
		openLOVCommon(404,'reportid','specificLoanNo','','', '','','','abc');
	else
		openLOVCommon(406,'reportid','specificLoanNo','','', '','','','abc');	
}

//Amit Starts
function checkRate(val)
{
	var rate = document.getElementById(val).value;
	//alert("Passed Value: "+rate);
	if(rate=='')
	{
		rate=0;
		//alert("Please Enter the value");
		//	return false;
	}
    var intRate = parseFloat(rate);
    //  alert(intRate);
    if(intRate>=0 && intRate<=100)
    {
    	return true;
    }
    else
   {
    	alert("Please Enter the value b/w 0 to 100");
        document.getElementById(val).value="";
		//document.getElementById(val).focus;
        return false;
   }
}

function changeRateType()
{
	if(document.getElementById("interestType").value=="L")
	{
		document.getElementById("rateInterest").value="";
		document.getElementById("rateInterest").setAttribute("readonly","true");
	}
	else if(document.getElementById("interestType").value=="U")
		document.getElementById("rateInterest").removeAttribute("readonly","true");
}
//Amit Ends
function openWelcomeLOV()
{
	var reportName=document.getElementById('reportName').value;	
	if(reportName=="AUwelcome_report")
		openLOVCommon(419,'reportid','loanNumber','','', '','','disabledate','disbursalAMT','disbursalDateN','disbursalStatus');
	else if(reportName=="welcome_letter_report_colanding")
	{
		openLOVCommon(192035,'reportid','loanNumber','','', '','','disabledate','disbursalAMT','disbursalDateN','disbursalStatus');
	}
	else
		openLOVCommon(346,'reportid','loanNumber','','', '','','disabledate','abc');
}



//added by aditi
function calculateMaxDate(val,id)
{
	var year= parseInt(val.substring(6));
	year=year+1;
	var maxToDate="31-03-"+year;
	document.getElementById("maxToDate").value=maxToDate;
	document.getElementById(id).value=maxToDate;
	
	
}
function checkToDate(val,id)
{
	if(checkDate(id))
	{
		var toDate=document.getElementById("maxToDate").value;
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(val,formatD.substring(2,3));
		var dt2=getDateObject(toDate,formatD.substring(2,3));
		if(dt2<dt1)
		{
			alert("To Date can't be greater than '"+toDate+"'"); 
			document.getElementById(id).value=toDate;			
			return false;
		}
	}
}

//aditi space end

function cutOffDateFlag()
{
	var status=document.getElementById('status').checked;
	if(status)
		document.getElementById('cutOffDate').value=document.getElementById('defaultDate').value;
	else
		document.getElementById('cutOffDate').value='';
}
//code added by neeraj tripathi
function arrangeView()
{
	
	var report=document.getElementById('reportId').value;
	if(report=='NHB_Monthly_Report')
	{
		var cutOffDateList=document.getElementById("eodList").value;
		var dateList = cutOffDateList.split('|');
		document.getElementById('asonDate').value=dateList[dateList.length-1];		
		document.getElementById("dateRange").style.display="none";
		document.getElementById("schemeRow").style.display="none";
		document.getElementById("asOnDateRow").style.display="";
		document.getElementById("rt_month1").style.display="none";	
		document.getElementById("rt_month2").style.display="none";	
		
	}
	if(report=='NHB_Report_4_1')
	{
		document.getElementById("dateRange").style.display="";
		document.getElementById("schemeRow").style.display="";
		document.getElementById("asOnDateRow").style.display="none";
	}
	if(report=='NHB_Report_4_1D'||report=='NHB_Report_4_2' ||report=='NHB_Report_4_5' ||report=='NHB_Report_4_3'|| report=='NHB_Report_4_4')
	{
		document.getElementById("dateRange").style.display="";
		document.getElementById("schemeRow").style.display="none";
		document.getElementById("asOnDateRow").style.display="none";
	}
	
	
}
function generateNHBReport()
{
	var report=document.getElementById('reportId').value;
	if(report=='')
	{
		alert("Report Type is Required.");
		return false;
	}
	if(report=='NHB_Monthly_Report')
	{
		var cutOffDateList=document.getElementById("eodList").value;
		var asonDate=document.getElementById("asonDate").value;
		var dateList = cutOffDateList.split('|');
		var stri = '';
		var i=0;
		for(i; i<dateList.length; i++)
		{
			if(dateList[i]==asonDate)
				break;
		}
		if(i==dateList.length)
		{
			alert("Invalid As on Date,Only last date of month is valid,\nwhich should be greater than or equal to "+dateList[0]+" and Less than or equal to "+dateList[i-1]);
			document.getElementById("asonDate").value="";
			return false;
		}
	}
	var contextPath = document.getElementById("contextPath").value;
	document.getElementById("nhbReportId").action=contextPath+"/nhbReportAction.do";
	document.getElementById("nhbReportId").submit();
 	return true;
}

//metod added by neeraj tripathi
function openLoanLov()
{
	var reportName=document.getElementById('reportName').value;	
	if(reportName=="rp_repayment_calculation_closed"  || reportName=="sanction_letter_after_loan"  || reportName=="subsequentDisbursementVoucher" || reportName=="CVDisbursalVoucher" || reportName=="WelcomeLetter(MM)" || reportName=="margin_deposit_receipt(mm)" || reportName=="reset_letter(mm)" || reportName=="LOD_Letter" || reportName=="BOUNCE_LETTER" || reportName=="PDC_exhaustion_letter" || reportName=="NOC_Report_With_LOD"|| reportName=="NOC_Report_WithOut_LOD" || reportName=="sanction_letter" || reportName=="welcome_letter_report_colanding" )
	{
		if( reportName=="CVDisbursalVoucher")
			openLOVCommon(508,'reportid','specificLoanNo','','', '','','','abc');	
		else
			openLOVCommon(477,'reportid','specificLoanNo','','', '','','','abc');
		if(reportName=="margin_deposit_receipt(mm)")
		{
			openLOVCommon(551,'reportid','specificLoanNo','','', '','','','abc');	
		}
		if(reportName=="reset_letter(mm)")
		{
			openLOVCommon(553,'reportid','specificLoanNo','','', '','','','abc');
		}
		 if(reportName=="BOUNCE_LETTER" )
		{			
			openLOVCommon(19105,'reportid','specificLoanNo','','', '','','','abc');
		}
		 if(reportName=="PDC_exhaustion_letter")
			{			
				openLOVCommon(19106,'reportid','specificLoanNo','','', '','','','abc');
			}
		 if(reportName=="LOD_Letter")
		{
			openLOVCommon(18005,'reportid','specificLoanNo','','', '','','','abc');
		}
		if(reportName=="NOC_Report_With_LOD" || reportName=="NOC_Report_WithOut_LOD")
		{
			openLOVCommon(19108,'reportid','specificLoanNo','','', '','','','abc');
		}
		if(reportName=="sanction_letter")
		{
			openLOVCommon(19168,'reportid','specificLoanNo','','', '','','','abc');
		}
		 if(reportName=="SME_Sanction_Letter_Report_For_CM")
		{
			openLOVCommon(192034,'reportid','specificLoanNo','','', '','','','abc');
		}
		if(reportName=="Repricing_Letter_for_increased_ROI_interest_rate_policy")
		{
			openLOVCommon(519,'reportid','specificLoanNo','','', '','','','abc');
		}
		/*shivesh*/
		if(reportName=="rp_repayment_calculation_closed")
		{
			openLOVCommon(192074,'reportid','specificLoanNo','','', '','','','abc');
		}
		/*shivesh*/
	}
	else
		openLOVCommon(168,'reportid','specificLoanNo','','', '','','','abc');
}

//metod added by neeraj tripathi
function openDealLov()
{
	var reportName=document.getElementById('reportName').value;	
	if(reportName=="letter_of_offer_cum_acceptance"  ||reportName=="Sanction Letter Report For X 10")
		openLOVCommon(478,'reportid','specificDealNo','','', '','','','abc');
	else if(reportName=="termSheet_Report")
		openLOVCommon(484,'reportForm','specificDealNo','','', '','','','abc');
	else if(reportName=="deal_repayment_calculation")
		openLOVCommon(500,'reportForm','repayDealNo','','', '','','','abc');
	else if(reportName=="Sanction Letter Report" ||reportName=="Sanctionletter(MM)")
		openLOVCommon(529,'reportid','specificDealNo','','', '','','','abc');
	else if(reportName=="credit_appraisal_memo")
		openLOVCommon(2008,'reportid','repayDealNo','','', '','','','abc');
	else if(reportName=="sanction_letter_after_deal" ||reportName=="SME_Sanction_Letter_Report_For_CP")
		openLOVCommon(19242,'reportForm','specificDealNo','','', '','','','abc');
	else
		openLOVCommon(172,'reportid','specificDealNo','','', '','','','abc');
}
//method added by neeraj

// Kumar Aman Change Start

function openMonthLov()
{
	var reportName=document.getElementById('reportName').value;	
	if(reportName == "ikf_od_report")
		openLOVCommon(6073,'reportid','startPeriod','','', '','','','startDate1');
}

// Kumar Aman Change End


function validDate()
{
	var cutOffDateList=document.getElementById("eodList").value;
	var asonDate=document.getElementById("asonDate").value;
	var dateList = cutOffDateList.split('|');
	var stri = '';
	var i=0;
	for(i; i<dateList.length; i++)
	{
		if(dateList[i]==asonDate)
			break;
	}
	if(i==dateList.length)
	{
		alert("Invalid As on Date,Only last date of month is valid,\nwhich should be greater than or equal to "+dateList[0]+" and Less than or equal to "+dateList[i-1]);
		document.getElementById("asonDate").value="";
		return false;
	}
}
function openLeadLOV()
{
	var reportName=document.getElementById("reportName").value;	
	if(reportName=="LEAD_FOLLOWUP_REPORT")
		openLOVCommon(497,'reportForm','specificLeadNo','','', '','','','abc')
}
//method add by sidharth 
function openLoanPROVLov()
{
	var reportName=document.getElementById('reportName').value;	
	if(reportName=="PROVISIONAL_INTEREST_CERTIFICATE")
		openLOVCommon(512,'reportid','specificLoanNo','','', '','','','abc');
	if(reportName=="statement_of_account_for_cancel_loan")
		openLOVCommon(19169,'reportid','specificLoanNo','','', '','','','abc');
	else
		openLOVCommon(341,'reportid','specificLoanNo','','', '','','','abc','rvFlag');
}

function generateTcScorecardReport()
{
	var date=document.getElementById('fromDate').value;	
	var user=document.getElementById('user').value;	
	if(date=='' && user=='')
	{
		alert("Select any one field.");
		return false;
	}
	else
	{
		document.getElementById("teleCallerScorecardReportForm").submit();
		Window.location.reload(); 
		return true;
	}
}

//method added by Nishant
function openChequeBounceFromLov()
{
	var reportName=document.getElementById('reportName').value;	
	if(reportName=="chequebouncemain")
		openLOVCommon(526,'reportid','from','lbxBranchId','', 'lbxBranchId','','','abc');
	else if(reportName=="Insurance_details")
		openLOVCommon(577,'reportid','from','','', '','','','abc');
	else
		openLOVCommon(168,'reportid','from','','', '','','','abc');
}

function openChequeBounceToLov()
{
	var reportName=document.getElementById('reportName').value;	
	if(reportName=="chequebouncemain")
		openLOVCommon(527,'reportid','to','lbxBranchId','', 'lbxBranchId','','','abc1');
	else if(reportName=="Insurance_details")
		openLOVCommon(578,'reportid','to','','', '','','','abc1');
	else
		openLOVCommon(200,'reportid','to','','', '','','','abc1');
}

function calBalAmt1()
{
	//alert("calBalAmt1");
	var adviceType1=document.getElementById('adviceType1').value;	
	var adviceAmount1=document.getElementById('adviceAmount1').value;	
	var adjustedAmount1=document.getElementById('adjustedAmount1').value;
	var chargeType1=document.getElementById('chargeType1').value;
	var chargeAmount1=document.getElementById('chargeAmount1').value;
	if(adviceAmount1=='')
	{
		adviceAmount1='0.00';
		document.getElementById('adviceAmount1').value='0.00';
	}
	if(adjustedAmount1=='')
	{
		adjustedAmount1='0.00';
		document.getElementById('adjustedAmount1').value='0.00';
	}
	if(chargeAmount1=='')
	{
		chargeAmount1='0.00';
		document.getElementById('chargeAmount1').value='0.00';
	}
//	alert("adviceType1     : "+adviceType1);
//	alert("adviceAmount1   : "+adviceAmount1);
//	alert("adjustedAmount1 : "+adjustedAmount1);
//	alert("chargeType1     : "+chargeType1);
//	alert("chargeAmount1   : "+chargeAmount1);
	var adviceAmount=parseFloat(adviceAmount1);
	var adjustedAmount=parseFloat(adjustedAmount1);
	var chargeAmount=parseFloat(chargeAmount1);
	var blanceAmount=adviceAmount-adjustedAmount;
	if(adviceType1=='P')
		blanceAmount=blanceAmount*(-1);
	if(chargeType1=='P')
		chargeAmount=chargeAmount*(-1);
	blanceAmount=blanceAmount+chargeAmount;
	document.getElementById('blanceAmount1').value=blanceAmount;
}
function calBalAmt2()
{
	//alert("calBalAmt2");
	var adviceType2=document.getElementById('adviceType2').value;	
	var adviceAmount2=document.getElementById('adviceAmount2').value;	
	var adjustedAmount2=document.getElementById('adjustedAmount2').value;
	var chargeType2=document.getElementById('chargeType2').value;
	var chargeAmount2=document.getElementById('chargeAmount2').value;
	if(adviceAmount2=='')
	{
		adviceAmount2='0.00';
		document.getElementById('adviceAmount2').value='0.00';
	}
	if(adjustedAmount2=='')
	{
		adjustedAmount2='0.00';
		document.getElementById('adjustedAmount2').value='0.00';
	}
	if(chargeAmount2=='')
	{
		chargeAmount2='0.00';
		document.getElementById('chargeAmount2').value='0.00';
	}
//	alert("adviceType     : "+adviceType2);
//	alert("adviceAmount   : "+adviceAmount2);
//	alert("adjustedAmount : "+adjustedAmount2);
//	alert("chargeType     : "+chargeType2);
//	alert("chargeAmount   : "+chargeAmount2);
	var adviceAmount=parseFloat(adviceAmount2);
	var adjustedAmount=parseFloat(adjustedAmount2);
	var chargeAmount=parseFloat(chargeAmount2);
	var blanceAmount=adviceAmount-adjustedAmount;
	if(adviceType2=='P')
		blanceAmount=blanceAmount*(-1);
	if(chargeType2=='P')
		chargeAmount=chargeAmount*(-1);
	blanceAmount=blanceAmount+chargeAmount;
	document.getElementById('blanceAmount2').value=blanceAmount;
}
function calBalAmt3()
{
	//alert("calBalAmt3");
	var adviceType3=document.getElementById('adviceType3').value;	
	var adviceAmount3=document.getElementById('adviceAmount3').value;	
	var adjustedAmount3=document.getElementById('adjustedAmount3').value;
	var chargeType3=document.getElementById('chargeType3').value;
	var chargeAmount3=document.getElementById('chargeAmount3').value;
	if(adviceAmount3=='')
	{
		adviceAmount3='0.00';
		document.getElementById('adviceAmount3').value='0.00';
	}
	if(adjustedAmount3=='')
	{
		adjustedAmount3='0.00';
		document.getElementById('adjustedAmount3').value='0.00';
	}
	if(chargeAmount3=='')
	{
		chargeAmount3='0.00';
		document.getElementById('chargeAmount3').value='0.00';
	}
//	alert("adviceType     : "+adviceType3);
//	alert("adviceAmount   : "+adviceAmount3);
//	alert("adjustedAmount : "+adjustedAmount3);
//	alert("chargeType     : "+chargeType3);
//	alert("chargeAmount   : "+chargeAmount3);
	var adviceAmount=parseFloat(adviceAmount3);
	var adjustedAmount=parseFloat(adjustedAmount3);
	var chargeAmount=parseFloat(chargeAmount3);
	var blanceAmount=adviceAmount-adjustedAmount;
	if(adviceType3=='P')
		blanceAmount=blanceAmount*(-1);
	if(chargeType3=='P')
		chargeAmount=chargeAmount*(-1);
	blanceAmount=blanceAmount+chargeAmount;
	document.getElementById('blanceAmount3').value=blanceAmount;
}

// search function for chequeAllocationReport by Nazia/Richa
function chequeAllocationReport()
	{
		
		
	
		 var fromdate=document.getElementById("fromDate").value;
     var todate=document.getElementById("toDate").value;
     var formatD=document.getElementById('formatD').value;	
	 var dt1=getDateObject(fromdate,formatD.substring(2, 3));		
 	 var dt3=getDateObject(todate,formatD.substring(2, 3));	
	

if(fromdate && todate=="")
{	alert("*To Date is required");
		return false;
}
if(todate && fromdate=="")
{	alert("*From Date is required");
		return false;

	}
 else if(dt1>dt3){
		  alert("To date can not be smaller than From date");
		  return false;
		  }	
	 var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("ChequeAllocationForm").action=contextPath+"/chequeAllocationDetail.do?actionName=search";
	document.getElementById("ChequeAllocationForm").submit(); 
		
	} 

function hideAsterik(value){
	  
	  if(value!='A')
	  {
		  document.getElementById("hideAsterik").style.display ='';
	  }
	  else
	  {
		  document.getElementById("hideAsterik").style.display ='none';
	  }
		  
}
document.onkeydown=function(e) 
{
    var event = window.event || e;  
    if (((event.keyCode==80||event.keyCode==112) && e.ctrlKey)||((event.keyCode==67||event.keyCode==99) && e.ctrlKey)||((event.keyCode==83||event.keyCode==115) && e.ctrlKey)||((event.keyCode==65||event.keyCode==97) && e.ctrlKey)) {
        event.keyCode = 0;
        return false;
    }
}
//var message="Right Click is disabled";
function clickIE4()
{
	if (event.button==2)
		return false;
}
function clickNS4(e)
{
	if (document.layers||document.getElementById&&!document.all)
	if (e.which==2||e.which==3)
	return false;
}
if (document.layers)
{
	document.captureEvents(Event.MOUSEDOWN);
	document.onmousedown=clickNS4;
}
else if (document.all&&!document.getElementById)
{
	document.onmousedown=clickIE4;
}
document.oncontextmenu=new Function("return false");

function mngAppLevel()

{
	var bucket=document.getElementById("bucket").value;
	if(bucket=='C'||bucket=='H')
	{
		document.getElementById("approvalL").style.display="";
		document.getElementById("approvalV").style.display="";
	}
	else
	{
		document.getElementById("approvalL").style.display="none";
		document.getElementById("approvalV").style.display="none";
	}
}

function vaildateStage()
{
	var dealId = document.getElementById("lbxDealNo").value;
	var loanId = document.getElementById("lbx_loan_from_id").value;
	var stage = document.getElementById("stage").value;
	
	if(dealId == '' && loanId == '')
	{
		alert("Please select the Deal-Range / Loan-Range first.");
		document.getElementById("stage").value='';
		return false;
	}
	else
	{
		if(dealId != '' && stage!='PRS')
		{
			alert("Document Stage Invalid for Deal.");
			document.getElementById("stage").value='';
			return false;
		}
		if(loanId != '' && stage=='PRS')
		{
			alert("Document Stage Invalid for Loan.");
			document.getElementById("stage").value='';
			return false;
		}
		
	}
}
function incptCutOff()
{
	var status=document.getElementById('status').checked;
	if(status)
	{
		document.getElementById("incptId").style.display="none";
		document.getElementById("cutOffDateForIncipient").style.display="";
	}
	else
	{
		document.getElementById("incptId").style.display="";
		document.getElementById("cutOffDateForIncipient").style.display="none";
	}
}


function generateCMSDownLoadReport()
{
	var report=document.getElementById('reportId').value;
	if(report=='')
	{
		alert("Report Type is Required.");
		return false;
	}
	 var fromdate=document.getElementById("fromDate").value;
     var todate=document.getElementById("toDate").value;
   //  var asonDate=document.getElementById("asonDate").value;	
     var formatD=document.getElementById('formatD').value;	
	 var dt1=getDateObject(fromdate,formatD.substring(2, 3));		
 	 var dt3=getDateObject(todate,formatD.substring(2, 3));	

	if( fromdate && todate=="")
	{	alert("*To Date is required");
			return false;
	}
	if( todate && fromdate=="")
	{	alert("*From Date is required");
			return false;
	}
	if( dt1>dt3)
	{
		 alert("To date can not be smaller than From date");
		 return false;
	 }	
	
	if(todate=='' && fromdate=='')
	{
		alert("TO Date and From Date is required.");
		return false;
	}
	var contextPath = document.getElementById("contextPath").value;
	document.getElementById("CMSDownLoadReportFormId").action=contextPath+"/cmsdownLoadReportProcessAction.do";
	document.getElementById("CMSDownLoadReportFormId").submit();
 	return true;
}
function changeDateFormateForPaySlip()
{
	var prestDate= document.getElementById("receiptDate").value;
	if(prestDate!='')
	{
		var result=prestDate.substring(6)+"-"+prestDate.substring(3,5)+"-"+prestDate.substring(0,2);
		document.getElementById("receiptDateName").value=result;
	}
	else
	{
		document.getElementById("receiptDateName").value='';
	}
}

/*****************Sanjay Kushwaha added for CMS UPL/DWL */
function generateCMSDownLoadReport()
{
	var report=document.getElementById('reportId').value;
	if(report=='')
	{
		alert("Report Type is Required.");
		return false;
	}
	 var fromDate=document.getElementById('fromDate').value;		 
	 var toDate=document.getElementById('toDate').value;		
	 var formatD=document.getElementById('formatD').value;	
	 var dt1=getDateObject(fromDate,formatD.substring(2, 3));		
	 var dt3=getDateObject(toDate,formatD.substring(2, 3));		
	 var c="";
	 var msg="";
	 
	 if( fromDate=="" || toDate=="" )
		{		
			if(fromDate=="")
				c="*From Date is required.\n";
			if(toDate=="")
				c+="* To Date is required.\n";
			alert(c);
	

	if(todate=='' && fromdate=='')
	{
		alert("TO Date and From Date is required.");
		return false;
	}
	/*if(day>rangeLimit)
	{
		alert("Difference cannot be greater than 360");
		return false;
	}*/
	

	if(toDate=="")
			document.getElementById("toDate").focus();		
	if(fromDate=="")
			document.getElementById("fromDate").focus();

	return false;
		}
	 if(fromDate!='' && toDate !='')
		 {
		 	if(dt1>dt3)
		 	{
		 	msg="To date can not be smaller than From date";
			//alert("To date can not be smaller than From date");
			  document.getElementById("mybutton").removeAttribute("disabled");
			  return false;
			  }	
		 	}
	var contextPath = document.getElementById("contextPath").value;
	document.getElementById("CMSDownLoadReportFormId").action=contextPath+"/cmsdownLoadReportProcessAction.do";
	document.getElementById("CMSDownLoadReportFormId").submit();
 	return true;
}


/*****************Sanjay Kushwaha added for CMS UPL/DWL */

function changeLov(){
	var level=document.getElementById("level").value;
	if(level==''){
		alert("Select level first");
	}
	if(level=='STAGE'){
		openLOVCommon(19202,'reportid','salesDealId','','', '','','','abc');
	}
	if(level=='DEAL'){
		openLOVCommon(172,'reportid','salesDealId','','', '','','','abc');
	}
}