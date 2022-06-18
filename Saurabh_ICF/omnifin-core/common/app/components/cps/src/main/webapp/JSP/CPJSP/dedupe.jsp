<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 01-Dec-2012  -->
<!--Documentation : -->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"	content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<%
			String path=request.getContextPath();
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
			request.setAttribute("no",no);
		%>	
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>	
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		</head>
	<script type="text/javascript">	
		
		function searchDeal()
		{
			DisButClass.prototype.DisButMethod();
			var dealID=document.getElementById('dealID').value;
			var customerName=document.getElementById('customerName').value;
			var panNo=document.getElementById('panNo').value;
			var aadhaar=document.getElementById('aadhaar').value;
			var regNo=document.getElementById('regNo').value;
			var dlNo=document.getElementById('dlNo').value;
			var passportNo=document.getElementById('passportNo').value;
			var voterID=document.getElementById('voterID').value;
			var phNo=document.getElementById('phNo').value;
			var groupName=document.getElementById('groupName').value;
			
			if(dealID==''&& customerName==''&& panNo==''&& regNo==''&& dlNo==''&& passportNo==''&& voterID==''&& phNo==''&& groupName=='' && aadhaar=='')
			{
				alert("Please fill at least one field.");
				document.getElementById('dealButton').focus();
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			var contextPath = document.getElementById("contextPath").value;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("dedupe").action=contextPath+"/dedupe.do?method=searchDuplicateRecord";
			document.getElementById("dedupe").submit();
 			return true;
		}
		function replaceCustomerID()
		{
		    DisButClass.prototype.DisButMethod();
			var ch=document.getElementsByName("chk");
			var customerID="";
			var noRecord=0;
			//alert("ch.length  :  "+ch.length);
			for(var i=0;i<ch.length;i++)
			{
				if(ch[i].checked == true)
				{
					customerID=ch[i].value;
					noRecord=1;
					break;
				}
			}
			if(noRecord==0)
			{
				alert("Please select at least one recrod");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			var contextPath = document.getElementById("contextPath").value;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("dedupe").action=contextPath+"/dedupe.do?method=replaceCustomerID&tarCustID="+customerID;
			document.getElementById("dedupe").submit();
 			return true;
		}
		function check()
		{
			var phNo=document.getElementById("phNo").value;
			var last=phNo.charCodeAt((phNo.length)-1);
			if(last==44)
			{
				alert("Last Character can't be comma(,).");
				document.getElementById("phNo").value="";
				return false;
			}
		}
		
	</script>
	<!--[if IE]>
		<style type="text/css">
		.opacity
		{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
	<! [endif] -->

<body onload="enableAnchor();document.getElementById('dealButton').focus();">
	<html:form action="/dedupe" styleId="dedupe" >
	<fieldset><legend><bean:message key="lbl.DedupeParameterForm"/></legend>  
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
		<td>
			<html:hidden styleClass="text" property="dealID" styleId="dealID" value="${resultList[0].dealID}"/>
			<html:text styleClass="text" maxlength="10" property="dealNO" styleId="dealNO" readonly="true" value="${resultList[0].dealNO}" tabindex="-1"/>
			<html:hidden styleClass="text"  property="customerID" styleId="customerID" value="${resultList[0].customerID}" />
			<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(481,'dedupe','dealNO','','','','','getDefltCustDtl','customerName','customerRole','customerID');closeLovCallonLov('dealNO');" value=" " styleClass="lovbutton"></html:button>
        </td>  
        <td><bean:message key="lbl.customerRole"/></td>
		<td><html:text styleClass="text" maxlength="10" property="customerRole" styleId="customerRole" style="" readonly="true" value="${resultList[0].customerRole}" tabindex="-1"/></td>   
    </tr>
    <tr>
        <td><bean:message key="lbl.customerName"/></td>
		<td><html:text styleClass="text" maxlength="10" property="customerName" styleId="customerName" style="" readonly="false" value="${resultList[0].customerName}" maxlength="100"/></td>
		<td><bean:message key="lbl.panNo"/></td>
		<td><html:text styleClass="text" maxlength="10" property="panNo" styleId="panNo" style="" readonly="false" value="${resultList[0].panNo}" maxlength="10"/></td>     
    </tr>
    <tr>
        <td><bean:message key="lbl.RegNumber"/></td>
		<td><html:text styleClass="text" maxlength="10" property="regNo" styleId="regNo" style="" readonly="false" value="${resultList[0].regNo}" maxlength="25"/></td>
		<td><bean:message key="drivingLicense"/></td>
		<td><html:text styleClass="text" maxlength="10" property="dlNo" styleId="dlNo" style="" readonly="false" value="${resultList[0].dlNo}" maxlength="20"/></td>     
    </tr>
    <tr>
        <td><bean:message key="passport"/></td>
		<td><html:text styleClass="text" maxlength="10" property="passportNo" styleId="passportNo" style="" readonly="false" value="${resultList[0].passportNo}" maxlength="20"/></td>
		<td><bean:message key="voterId"/></td>
		<td><html:text styleClass="text" maxlength="10" property="voterID" styleId="voterID" style="" readonly="false" value="${resultList[0].voterID}" maxlength="20"/></td>     
    </tr>
    <tr>
        <td><bean:message key="lbl.phonenumber"/></td>
		<td><html:text styleClass="phone" property="phNo" styleId="phNo" style="" readonly="false" value="${resultList[0].phNo}" maxlength="60" style="text-align: right" onkeypress="return isNumberKeyComma(event);" onchange="check()"/></td>
		<td><bean:message key="lbl.groupName"/></td>
		<td><html:text styleClass="text" maxlength="50" property="groupName" styleId="groupName" style="" readonly="false" value="${resultList[0].groupName}" /></td>
		
	</tr>
	<tr><td></td><td>( Separate multiple phone no with a comma',' )</td><td></td></tr>
	<tr>
	  
	       <td><bean:message key="aadhaar" /></td>
	       <td><html:text property="aadhaar" styleClass="text" styleId="aadhaar" readonly="true" onkeypress="return isNumber(event);" value="" style="text-align: right" maxlength="12" size="10" /></td>
     </tr>
	<tr><td><button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return searchDeal();"><bean:message key="button.search" /></button></td></tr>	
	</table>
	</fieldset>
	
	
	<logic:present name="dupList">
			<fieldset><legend><bean:message key="lbl.matchingRecord"/></legend>		
			<logic:notEmpty name="dupList">
			        <display:table  id="dupList"  name="dupList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${dupList[0].totalRecordSize}" requestURI="/dedupe.do?method=searchDuplicateRecord" >
	    				<display:setProperty name="paging.banner.placement"  value="bottom"/>
	    				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					    
					    <display:column property="radioButton" titleKey="lbl.select"  sortable="true" style="text-align: center"/>
					    <display:column property="dupDealNO" titleKey="lbl.dealNo"  sortable="true" />
					    <display:column property="dealAmount" titleKey="lbl.dealAmount"  sortable="true"  style="text-align: right"/>
					    <display:column property="dealStatus" titleKey="lbl.dealStatus"  sortable="true"  />
					    <display:column property="dupCustomerRole" titleKey="lbl.customerRole"  sortable="true" />
					    <display:column property="dupCustomerID" titleKey="lbl.customerID"  sortable="true" style="text-align: center"/>
					    <display:column property="dupCustomerName" titleKey="lbl.customerName"  sortable="true" />
					    <display:column property="dupPanNo" titleKey="lbl.panNo"  sortable="true" />
					    <display:column property="dupAadhaar" titleKey="aadhaar"  sortable="true" />
					    <display:column property="dupRegNo" titleKey="lbl.RegNumber"  sortable="true" />
					    <display:column property="dupDlNo" titleKey="drivingLicense"  sortable="true" />
					    <display:column property="dupPassportNo" titleKey="passport"  sortable="true"  />
					    <display:column property="dupVoterID" titleKey="voterId"  sortable="true"  />
					    <display:column property="dupPhNo" titleKey="lbl.phonenumber"  sortable="true"  />
					    <display:column property="groupName" titleKey="lbl.groupName"  sortable="true"  />
					    <display:column property="loanNo" titleKey="lbl.loanNumber"  sortable="true"  />
					    <display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  style="text-align: right"/>
					    <display:column property="loanEffDate" titleKey="lbl.loanEffDate"  sortable="true"  />
					    <display:column property="loanEndDate" titleKey="lbl.loanEndDate"  sortable="true"  />
					    <display:column property="loanStatus" titleKey="lbl.loanStatus"  sortable="true"  />
					    <display:column property="disbursalStatus" titleKey="lbl.disbursalStatus"  sortable="true"  />
					    <display:column property="blsPrincipal" titleKey="lbl.loanBalPrinc"  sortable="true"  style="text-align: right"/>
					    <display:column property="overduePrincipal" titleKey="lbl.loanOverduePrin"  sortable="true"  style="text-align: right"/>
					    <display:column property="loanDPD" titleKey="lbl.loanDPD"  sortable="true"  />
				
			 		</display:table>
			 		<logic:present name="show">
			 		<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr><td><button type="button" name="replace" title="Alt+R" accesskey="R" id="button" class="topformbutton2" onclick="return replaceCustomerID();"><bean:message key="button.replace" /></button></td></tr>
					</table>
					</logic:present>
			 		
			 		
			</logic:notEmpty>
			<logic:empty name="dupList">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr class="white2">
								<td><strong><bean:message key="lbl.select" /> </strong></td>
								<td><strong><bean:message key="lbl.dealNo" /> </strong></td>
								<td><strong><bean:message key="lbl.dealAmount" /> </strong></td>
								<td><strong><bean:message key="lbl.dealStatus" /> </strong></td>
								<td><strong><bean:message key="lbl.customerRole" /> </strong></td>
								<td><strong><bean:message key="lbl.customerID" /> </strong></td>
								<td><strong><bean:message key="lbl.customerName" /> </strong></td>
								<td><strong><bean:message key="lbl.panNo" /> </strong></td>
								<td><strong><bean:message key="aadhaar" /> </strong></td>
								<td><strong><bean:message key="lbl.RegNumber" /> </strong></td>
								<td><strong><bean:message key="drivingLicense" /> </strong></td>
								<td><strong><bean:message key="passport" /> </strong></td>
								<td><strong><bean:message key="voterId" /> </strong></td>
								<td><strong><bean:message key="lbl.loanNumber" /> </strong></td>
								<td><strong><bean:message key="lbl.loanAmount" /> </strong></td>
								<td><strong><bean:message key="lbl.loanEffDate" /> </strong></td>
								<td><strong><bean:message key="lbl.loanEndDate" /> </strong></td>
								<td><strong><bean:message key="lbl.loanStatus" /> </strong></td>
								<td><strong><bean:message key="lbl.disbursalStatus" /> </strong></td>
								<td><strong><bean:message key="lbl.loanBalPrinc" /> </strong></td>
								<td><strong><bean:message key="lbl.loanOverduePrin" /> </strong></td>
								<td><strong><bean:message key="lbl.loanDPD" /> </strong></td>
						</tr>
						<tr class="white2">
							<td colspan="21"><bean:message key="lbl.noDataFound" /></td>
						</tr>
						</table>
					</td>
				</tr>				
				</table>
			</logic:empty>
		 </fieldset>
		</logic:present>
	</html:form>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>	
	<logic:present name="error">
	<script type="text/javascript">	
		alert('${error}');
	</script>
	</logic:present>
	<logic:present name="done">
	<script type="text/javascript">	
		alert('Replacement is Completed successfully.');
	</script>
	</logic:present>
	<logic:present name="notDone">
	<script type="text/javascript">	
		alert('Some error occur,Please Contact System Administrator.....');
	</script>
	</logic:present>
	
		
</body>
</html>