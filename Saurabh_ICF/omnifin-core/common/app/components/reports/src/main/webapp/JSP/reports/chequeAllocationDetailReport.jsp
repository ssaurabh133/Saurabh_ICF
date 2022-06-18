<!--//Author Name - Nazia/Richa-->
<!--//Date - 29_may_2013-->




<%@ page language="java" pageEncoding="ISO-8859-1"%>    
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

<html>

	<head>
	    <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
	
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<title>Reports</title>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/report/report.js"></script>
		
		
		<script type="text/javascript">
	
	
			
				function changeval(val)
				{
				
					if(val=="R")
					{
						document.getElementById("replaceRecievable").style.display="none";
						document.getElementById("replacePayable").style.display="";
						
					}
					if(val=="P")
					{
						document.getElementById("replacePayable").style.display="none";
						document.getElementById("replaceRecievable").style.display="";
						
					}
					
				}	
				
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
		
				
		</script> 
		
		
	
		<style type="text/css">
		
			.white {
			background:repeat-x scroll left bottom #FFFFFF;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
			.white2 {
<!--			background:repeat-x scroll left bottom #CDD6DD;-->
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
		</style>
						<%
				ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
				
				int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
				request.setAttribute("no",no);
				
				
				%>
	</head>
	
	
	
	<body onload="enableAnchor();">
	<div id="centercolumn">	
			<div id="revisedcontainer">	
				<html:form action="/chequeAllocationDetail" styleId="ChequeAllocationForm" >
					<fieldset>
						<legend><bean:message key="lbl.ReportParameterForm"/></legend>  
						<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
						<html:hidden property="reportId" styleId="contextPath" value="cheque_Status" />
						<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
							
								<tr>
									<td><bean:message key="lbl.loanNumber"/></td>
									<td><html:text property="loanNumber" styleClass="text" styleId="loanNumber" readonly="false"
									onchange="openLOVCommon(341,'ChequeAllocationForm','loanNumber','','', '','','','customerName','','','Y')"
									  />
										<html:hidden property="lbx_loan_from_id" styleId="lbx_loan_from_id"  />
										<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
										<html:button property="loanFromButton" styleId="loanFromButton" tabindex="1" value=" " onclick="openLOVCommon(341,'ChequeAllocationForm','loanNumber','','', '','','','customerName')" styleClass="lovbutton"  />
									</td>
									<td><bean:message key="lbl.customer_name"/></td>
									<td>
										<html:text property="customerName" styleClass="text" styleId="customerName" maxlength="100" readonly="true" />
									</td>
								</tr>
								<tr>
									<td><bean:message key="lbl.instrumenttype"/></td>
									<td>
										<html:select property="instrumentTypesearch" styleId="instrumentTypesearch"   onchange="changeval(this.value);" styleClass="text" >
											
											<html:option value="R">Recievable</html:option>
											<html:option value="P">Payable</html:option>
											<html:option value="K">Knock Off</html:option>
											
										</html:select>
									</td>
<!--									<td><bean:message key="lbl.statusType"/></td>-->
									
									<td>
										<div id="replaceRecievable" style="display:none">
									  		<html:select property="statusTypeR" styleId="statusTypeR"  styleClass="text" >
											<html:option value="All">All</html:option>
										<html:option value="C">Sent To Customer</html:option>
										<html:option value="S">Stopped</html:option>
										<html:option value="R">Realized</html:option>
										<html:option value="X">Cancelled</html:option>
											</html:select>
										
										</div>
									<div id="rf"><bean:message key="lbl.ReportFormat"/><span><font color="red">*</font></span></div>
								<td id="rfv"> <html:select  property="reportformat" styleClass="text"  styleId="reportformat" >
            			   	  <logic:present name="list2">
   							  <logic:notEmpty  name="list2">
      									<html:optionsCollection name="list2" label="reportformatid" value="reportformat" />
     						</logic:notEmpty>
      					 	</logic:present>
      					 	</html:select>
      					 	

										
									</td>
									</tr>
										<tr>
											<td ><bean:message key="lbl.FromDate"/><span><font color="red">*</font></span></td>
										<td>
										
										<html:text property="fromDate"  styleId="fromDate" styleClass="text" maxlength="10" onchange="checkDate('fromDate');"/>
										</td>										
										<td><bean:message key="lbl.ToDate"/><span><font color="red">*</font></span></td>
										<td>
									           <html:text property="toDate"  styleId="toDate" styleClass="text" maxlength="10" />

										</td>
										</tr>
								<tr>
									<td><bean:message key="lbl.InsNo"/></td>
									<td>
									<html:text property="instrumentNoSearch"  styleId="instrumentNoSearch" styleClass="text" maxlength="20" />
									</td>
									
									<td><bean:message key="lbl.reciptNo"/></td>
									<td>
									<html:text property="reciptNo"  styleId="reciptNo" styleClass="text" maxlength="20" />
									</td>
								</tr>
	                                 	<tr>
										<td ><html:button property=" mybutton"  value="Search"  styleClass="topformbutton2" onclick="chequeAllocationReport();"  /></td>
									</tr>	
							</table>
						</fieldset>
						
						
	<br/>
<logic:present name="showGrid">	
<fieldset>
		 <legend><bean:message key="lbl.instrumentDetails"/></legend> 
<logic:present name="list">

   <logic:notEmpty name="list">
    <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/chequeAllocationDetail.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>	
	<display:column property="instrumentId" titleKey="lbl.SerialNo"  sortable="true"  />
	<display:column property="loanNumber" titleKey="lbl.loan_deal_number"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="reciptNo" titleKey="lbl.reciptNo"  sortable="true"  />
	<display:column property="instrumentno" titleKey="lbl.InstrNo"  sortable="true"  />
	<display:column property="instrumentType" titleKey="lbl.InstrType"  sortable="true"  />
	<display:column property="instrumentDate" titleKey="lbl.instrumentDate"  sortable="true"  />
	<display:column property="instrumentAmount" titleKey="lbl.InstrAmt"  sortable="true"  />
	<display:column property="tdsAmount" titleKey="lbl.tdsAmount"  sortable="true"  />
	<display:column property="defaultBranch" titleKey="lbl.defaultBranchId" sortable="true"  />
	<display:column property="depositBank" titleKey="lbl.depositBankName"  sortable="true"  />
	<display:column property="depositBankBranch" titleKey="lbl.depositBranchName"  sortable="true"  />
	<display:column property="depositBankAccount" titleKey="lbl.depositBnkAcc"  sortable="true"  />
	<display:column property="instrumentStatus" titleKey="lbl.active"  sortable="true"  />
	
	
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.SerialNo" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.loan_deal_number" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.customerName" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.branch" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.reciptNo" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.InstrNo" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.InstrType" /> <br> </b>
									</td>
										<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.instrumentDate" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.InstrAmt" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.tdsAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.defaultBranchId" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.depositBankName" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.depositBranchName" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.depositBnkAcc" /> <br> </b>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.active" /> <br> </b>
									</td>
									
									
									
									<tr class="white2" >
	                                  <td colspan="15"><bean:message key="lbl.noDataFound" /></td>
                                    </tr>
							</tr>
			            </table>
						</td>
					</tr>
				</table>
</logic:empty>

  </logic:present>
  
</fieldset>  
	
	</logic:present>	
           
				
			</html:form>	
			</div>
		</div>

	</body>
</html>