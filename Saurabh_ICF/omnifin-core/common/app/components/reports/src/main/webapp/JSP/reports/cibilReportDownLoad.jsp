<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 08-May-2012  -->
<!--Documentation : -->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"	content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<title>Reports</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/report/report.js"></script>
		
		<script type="text/javascript">
		
			$(function() {
				$("#fromDate").datepicker({
					changeMonth: true,
					changeYear: true,
            		yearRange: '1900:+10',
            		showOn: 'both',
						<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',
    		</logic:notPresent>
					buttonImageOnly: true,
					dateFormat:"<bean:message key="lbl.dateFormat"/>",
					defaultDate:'${userobject.businessdate }'
					});
			});		
					
			$(function() {
				$("#toDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
						<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',
    		</logic:notPresent>
					buttonImageOnly: true,
					dateFormat:"<bean:message key="lbl.dateFormat"/>",
					defaultDate:'${userobject.businessdate }'
		
					});
			});	
	function TUDF()
			{
				var report=document.getElementById("report").value;	
			//	alert(report);
				if(report=='cibilReport' || report== 'CIBIL_GUARANTOR_REPORT')
					{					
						document.getElementById("tudf").style.display="";
						document.getElementById("tudf1").style.display="";
						document.getElementById("cutOffDate").style.display="";
						document.getElementById("toDate").style.display="";
						document.getElementById("toDate").parentNode.childNodes[1].style.display=""
					}
				else{
						document.getElementById("tudf").style.display="none";
						document.getElementById("tudf1").style.display="none";
						document.getElementById("cutOffDate").style.display="none";
						document.getElementById("toDate").parentNode.childNodes[1].style.display="none";
						document.getElementById("toDate").style.display="none";
				}
				return true;
			}
										
		</script> 
	</head>
	
	<body onload="enableAnchor();TUDF();" >
	<html:form action="/cibilReportGenerate" styleId="cibilReportGenerate" >
		<fieldset>
				<legend><bean:message key="lbl.cibilReportDownLoad"/></legend>  
				<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
				<html:hidden property="defaultDate" styleId="defaultDate" value="${cutOffDate}" />
				<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />		
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
						<td><bean:message key="lbl.ReportType" /><span><font color="red">*</font></span></td>
						<td><html:select property="report" styleClass="text" onchange="TUDF();" styleId="report">  
						<html:option value="">---Select---</html:option>
							<html:option value="CIBIL_INDIVIDUAL_DOWNLOAD">CIBIL INDIVIDUAL</html:option>
							<html:option value="CIBIL_CORPORATE_DOWNLOAD">CIBIL CORPORATE</html:option>
							<html:option value="CIBIL_CORPORATE_DOWNLOAD_LC">CIBIL CORPORATE - LC</html:option>
							<!-- <html:option value="cibilReport">Cibil Report</html:option>
							<html:option value="CIBIL_GUARANTOR_REPORT">Cibil Guarantor Report</html:option> -->
							</html:select>
						</td>
												
						<td id="tudf" style="display: none"><bean:message key="lbl.cutOffDate"/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type=checkbox id="status" name="status" onclick="cutOffDateFlag()"/></td>
						<td><html:text property="cutOffDate"  readonly="true" styleId="cutOffDate" style="display: none" styleClass="text" value=""/></td>
						<td></td>
						<td></td>
						
					</tr>
					<tr>
						<td><bean:message key="lbl.fromApprovalDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="fromDate"  styleId="fromDate" styleClass="text" value="" maxlength="10" onchange="checkDate('fromDate');validateLeadDate();"/></td>
					
						<td id="tudf1" style="display: none"><bean:message key="lbl.toApprovalDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="toDate"  styleId="toDate" styleClass="text" value="" style="display: none" maxlength="10" onchange="checkDate('toDate');validateLeadDate();"/></td>
				
					</tr>
					<tr>
						<td ><button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="cibilReport()"><bean:message key="button.generate" /></button></td>
					</tr>	
				</table>
		</fieldset>
	</html:form>
	<logic:present name="error">
	<script type="text/javascript">	
		alert('${error}');
	</script>
	</logic:present>
	<logic:present name="overFlow">
	<script type="text/javascript">	
		var msg="Loan range is too long.You can select only ";
		msg=msg+'${loanLimit}';
		msg=msg+" Loans.";
		alert(msg);
	</script>
	</logic:present>
		<logic:present name="msg">
	<script type="text/javascript">	
	if('<%=request.getAttribute("msg")%>'=='S')
	{
		var msg="Cibil File generated on : ";
		msg=msg+'${FileName}';
		alert(msg);
	}
	else if('<%=request.getAttribute("msg")%>'=='E')
	{
		alert("Some error occurs in file generation....");
	}
	</script>
	</logic:present>
</body>
</html>