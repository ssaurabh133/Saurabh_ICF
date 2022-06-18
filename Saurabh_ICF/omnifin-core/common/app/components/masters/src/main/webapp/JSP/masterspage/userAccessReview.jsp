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
			$(function() {
				$("#UserDeactivatedStartDate").datepicker({
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
				$("#UserDeactivatedEndDate").datepicker({
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
			function userAccessReviewReport()
			{
			
			var userCreationStartDate=document.getElementById("fromDate").value;
			var userCreationEndDate=document.getElementById("toDate").value;
			var userDeactivatedStartDate=document.getElementById("UserDeactivatedStartDate").value;
			var userDeactivatedEndDate=document.getElementById("UserDeactivatedEndDate").value;
			var userType=document.getElementById("userType").value;						
			if(userType=="A" ) 
				{	
					if(userDeactivatedStartDate !=='' || userDeactivatedEndDate !=='')
					{
					alert("Please select User Creation Date Range for Active Users");
					return false;
				}
					}
			
		if(userDeactivatedStartDate =='' && userDeactivatedEndDate=='' && userCreationStartDate =='' && userCreationEndDate=='')
			{
			alert("First select mandatory field");
			return false;
		}
			if(userCreationStartDate !=='' && userCreationEndDate=='')
			{
			alert("Please select User Creation End Date");
			return false;
		}	 
			if(userDeactivatedStartDate !=='' && userDeactivatedEndDate=='')
			{
			alert("Please select User Deactivated End Date");
			return false;
		}	 	
			if(userCreationStartDate =='' && userCreationEndDate !=='')
			{
			alert("Please select User Creation Start Date");
			return false;
		}	 
			if(userDeactivatedStartDate =='' && userDeactivatedEndDate !=='')
			{
			alert("Please select User Deactivated Start Date");
			return false;
		}	
			var formatD=document.getElementById("formatD").value;
			var dt1=getDateObject(userCreationStartDate,formatD.substring(2,3));
			var dt2=getDateObject(userCreationEndDate,formatD.substring(2,3));
			if(dt2<dt1)
			{
				alert("User Creation End Date can't be less than User Creation Start Date."); 
				return false;
			}
			var dt3=getDateObject(userDeactivatedStartDate,formatD.substring(2,3));
			var dt4=getDateObject(userDeactivatedEndDate,formatD.substring(2,3));
			if(dt4<dt3)
			{
				alert("User Deactivated End Date can't be less than User Deactivated Start Date."); 
				return false;
			}
			if(userType=="X"){
			
			if((dt3<dt1) && (userCreationEndDate !=='' && userDeactivatedEndDate !==''))
			{
				
				alert(" User Deactivated Start Date can't be less than User Creation Start Date."); 
				return false;
				
			}
			}
				document.getElementById("userAccessReview").submit(); 
				document.getElementById("fromDate").value="";
				document.getElementById("toDate").value="";
				document.getElementById("UserDeactivatedStartDate").value="";
				document.getElementById("UserDeactivatedEndDate").value="";
				document.getElementById("userType").value="";	
				Window.location.reload();
					return false;
			}
										
		</script> 
	</head>
	
	<body onload="enableAnchor();loadJSP()" >
	<html:form action="/UserAccessReviewAction" styleId="userAccessReview" >
		<fieldset>
				<legend><bean:message key="lbl.UserAccessReview"/></legend>  
				<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
				<html:hidden property="defaultDate" styleId="defaultDate" value="${cutOffDate}" />
				<!--   <input type="hidden" name="formatD" id="formatD" value="dd-mm-yy">   -->
				<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />		
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
					<td id="rf"><bean:message key="lbl.ReportFormat"/></td>
									<td id="rfv">
										<html:select property="reportformat" styleClass="text">
											<html:option value="P">PDF</html:option>
											<html:option value="E">EXCEL</html:option>
											<html:option value="H">HTML</html:option>
											</html:select> 
          	   		</td>
          	   		
						<td><bean:message key="lbl.userType" /><span><font color="red">*</font></span></td>
						<td><html:select property="userType" styleClass="text"   styleId="userType">  
						<html:option value="">---Select---</html:option>
							<html:option value="A">ACTIVE</html:option>
							<html:option value="X">INACTIVE</html:option>
											
							</html:select>
						</td>
												
						<td id="tudf" style="display: none"><bean:message key="lbl.cutOffDate"/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type=checkbox id="status" name="status" onclick="cutOffDateFlag()"/></td>
						<td><html:text property="cutOffDate"  readonly="true" styleId="cutOffDate" style="display: none" styleClass="text" value=""/></td>
						<td></td>
						<td></td>
						
					</tr>
					<tr>
						<td><bean:message key="lbl.UserCreationStartDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="fromDate"  styleId="fromDate" styleClass="text" value="" maxlength="10" onchange="checkDate('fromDate');validateLeadDate();"/></td>
					
						<td ><bean:message key="lbl.UserCreationEndDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="toDate"  styleId="toDate" styleClass="text" value=""  maxlength="10" onchange="checkDate('toDate');validateLeadDate();"/></td>
				
					</tr>
					<tr>
						<td><bean:message key="lbl.UserDeactivatedStartDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="UserDeactivatedStartDate"  styleId="UserDeactivatedStartDate" styleClass="text" value="" maxlength="10" onchange="checkDate('UserDeactivatedStartDate');validateLeadDate();"/></td>
					
						<td ><bean:message key="lbl.UserDeactivatedEndDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="UserDeactivatedEndDate"  styleId="UserDeactivatedEndDate" styleClass="text" value=""  maxlength="10" onchange="checkDate('UserDeactivatedEndDate');validateLeadDate();"/></td>
				
					</tr>
					<tr>
						<td ><button type="button" name="mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="userAccessReviewReport();"><bean:message key="button.generate" /></button></td>
					</tr>	
				</table>
		</fieldset>
	</html:form>
	<logic:present name="error">
	<script type="text/javascript">	
		alert('${error}');
		alert("Report can not be Generate.");
	</script>
	</logic:present>
	<%-- <logic:present name="overFlow">
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
	</logic:present> --%>
</body>
</html>
