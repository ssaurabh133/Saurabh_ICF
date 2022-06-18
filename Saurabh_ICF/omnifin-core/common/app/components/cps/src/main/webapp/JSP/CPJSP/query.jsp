<!--  
Author Name:- Amit Kumar
Date of Creation:- 30/04/2011
Purpose:-  
-->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		


		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/underWriter.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    <script type="text/javascript">
    function getQueryTime()
    	{
			var currentTime = new Date();
			var hour = currentTime.getHours();
			var minute = currentTime.getMinutes();
			var time = hour+":"+minute;
			document.getElementById("queryTime").value=time;
		}
	</script>
<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->
    </head>
    <logic:present  name="showList">
    	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('queryForm');"/>	
    </logic:present>
    <logic:notPresent  name="showList">
    	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('queryForm');getQueryTime();"/>
    </logic:notPresent>
	
	
	
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
    </div>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Under Writer</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	<logic:notPresent name="strFlagDV">
	<logic:notPresent name="showList">
	<html:form styleId="queryForm" action="/query" method="post">
	<html:errors/>
	<fieldset>
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
		  <legend><bean:message key="lbl.queryInitiation"/></legend>
	   
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<html:hidden property="dealId"  styleId="dealId" value='${sessionScope.dealId}'/>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td ><bean:message key="lbl.queryDateTime"/><font color="red">*</font></td>
			 <td >
			
						<html:text property="queryDate" styleId="date" styleClass="text3" readonly="true" value="${userobject.businessdate}" maxlength="10" onchange="return checkDate('date');"/>
		                <html:text property="queryTime" styleId="queryTime" styleClass="text5" value="" maxlength="5" onchange="validateQueryTime()" /> 24 Hrs. Format
          	</td>
	<td ><bean:message key="lbl.queryRemarks"/><font color="red">*</font></td>
	<td ><textarea name="queryRemarks" class="text"  id="queryRemarks" maxlength="1000" ></textarea></td>
	    </tr>
	    
	    <tr>
			<td ><bean:message key="lbl.initiateTo"/><font color="red">*</font></td>
			<td >
				<html:text property="userId" styleClass="text" styleId="userId" tabindex="-1" maxlength="100" readonly="true" value=""/>
				<html:hidden property="lbxUserId" styleId="lbxUserId" />
				<html:button property="UserButton" styleId="UserButton"  onclick="openLOVCommon(278,'userDetailsForm','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
			<td ><bean:message key="lbl.resolutionStatus"/><font color="red">*</font></td>
		  <td >
            <html:select property="resolutionStatus" value="" styleId="resolutionStatus" styleClass="text" onchange="resultofQuery();">
	               <html:option value="">---Select---</html:option>
	              <html:option value="P">Pending</html:option>
	              <html:option value="R">Resolved</html:option>
            </</html:select>
          </td>
	    </tr>
	    
		<tr>
		  
		  <td><bean:message key="lbl.resolutionDateTime"/><font color="red">*</font></td>
		  <td>
		  <div id="notshowDate" style="display: none; float: left;">
		  <input type="text" id="to_datepicker12" class="text6" disabled="disabled" value="" maxlength="10" onchange="return checkDate('to_datepicker12');"/></div>
		  	<div id="showDate" style="float: left;">
		  	<html:text property="resolutionDate" styleId="to_datepicker" styleClass="text3" value="" maxlength="10" onchange="return checkDate('to_datepicker');"/></div>
			<html:text property="resolutionTime" styleId="resolutionTime" styleClass="text5" value="" maxlength="5" onchange="validateResolutionTime()" /> 24 Hrs. Format
          	</td>
          	<td><bean:message key="lbl.resolutionRemarks"/><font color="red">*</font></td>
		  <td><textarea name="resolutionRemarks" class="text" id="resolutionRemarks" maxlength="1000"></textarea></td>
		</tr>
		<tr>
		<td>

		<bean:message key="lbl.queryType" />
		</td>
		<td>
          <html:select property="queryType" value="" styleId="queryType" styleClass="text" >
	              <html:option value="CRT">CRITERIA RELATED</html:option>
	              <html:option value="CLA">CLARIFICATION RELATED</html:option>
	              <html:option value="BNK">BANK RELATED</html:option>
	              <html:option value="DOC">DOCUMENTS RELATED</html:option>
	              <html:option value="MJD">MAJOR DEBTOR</html:option>
	              <html:option value="MAC">MAJOR CREDITOR</html:option>
            </html:select>
        </td>
	</tr>
		
		 <tr>
		   <td align="left" class="form2" colspan="4">
												<div align="left">
													<button type="button" name="saveQuery" id="saveQuery" onclick="submitQueryData()" title="Alt+V" accesskey="V" class="topformbutton2" ><bean:message key="button.save" /></button>
												</div>
											</td>
		   </tr>
		</table>
		
	      </td>
	</tr>	
	</table>
	 
	</fieldset>
	
	</html:form>
	</logic:notPresent>
		</logic:notPresent>
	<logic:present name="showList">
	<html:form styleId="queryForm" action="/query" method="post" >
	
	<fieldset>
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
	<html:hidden property="dealQueryId" styleId="dealQueryId" value="${showList[0].dealQueryId}" />
	
		  <legend><bean:message key="lbl.queryInitiation"/></legend>
	   
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<html:hidden property="dealId" styleId="dealId" value='${sessionScope.dealId}'/>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td><bean:message key="lbl.queryDateTime"/></td>
			 <td>
			
						<html:text property="queryDate" styleId="queryDate" styleClass="text6" value="${showList[0].queryDate}" readonly="true" maxlength="100"/>
		                <html:text property="queryTime" styleId="queryTime" styleClass="text5" size="4" value="${showList[0].queryTime}" readonly="true" maxlength="5" onchange="validateQueryTime()" /> 24 Hrs. Format  
          	</td>
	<td><bean:message key="lbl.queryRemarks"/></td>
	<td><html:textarea property="queryRemarks" styleClass="text" value="${showList[0].queryRemarks}" disabled="true" styleId="queryRemarks" ></html:textarea></td>
	    </tr>
		 <tr>
			<td ><bean:message key="lbl.initiateTo"/><font color="red">*</font></td>
			<td >
				<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" tabindex="-1" readonly="true" value="${showList[0].userId}"/>
				<html:hidden property="lbxUserId" styleId="lbxUserId" value="${showList[0].lbxUserId}"/>
				<html:button property="UserButton" styleId="UserButton"  onclick="openLOVCommon(278,'userDetailsForm','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
			<td ><bean:message key="lbl.resolutionStatus"/><font color="red">*</font></td>
		  <td >
            <html:select property="resolutionStatus" value="" styleId="resolutionStatus" styleClass="text" onchange="resultofQuery();" value="${showList[0].resolutionStatus}" >
	               <html:option value="">---Select---</html:option>
	              <html:option value="P">Pending</html:option>
	              <html:option value="R">Resolved</html:option>
            </</html:select>
          </td>
	    </tr>
	    
		<tr>
		  
		  <td><bean:message key="lbl.resolutionDateTime"/><font color="red">*</font></td>
		  <td>
		  <div id="notshowDate" style="float: left;">
		     <input type="text" id="to_datepicker12" class="text6" disabled="disabled" value="" maxlength="10" onchange="return checkDate('to_datepicker12');"/></div>
		  	<div id="showDate" style="display: none; float: left;">
		  	<html:text property="resolutionDate" styleId="to_datepicker" readonly="true" styleClass="text3" value="" maxlength="10" onchange="return checkDate('to_datepicker');"/></div>
			<html:text property="resolutionTime" styleId="resolutionTime" readonly="true" styleClass="text5" value="" maxlength="5" onchange="validateResolutionTime()" /> 24 Hrs. Format
          	</td>
          	<td><bean:message key="lbl.resolutionRemarks"/><font color="red">*</font></td>
		  <td><textarea name="resolutionRemarks" class="text" readonly="readonly" id="resolutionRemarks" maxlength="1000"></textarea></td>
		</tr>
		<tr>
		<td>

		<bean:message key="lbl.queryType" />
		</td>
		<td>
          <html:select property="queryType" value="" styleId="queryType" styleClass="text" >
	              <html:option value="CRT">CRITERIA RELATED</html:option>
	              <html:option value="CLA">CLARIFICATION RELATED</html:option>
	              <html:option value="BNK">BANK RELATED</html:option>
	              <html:option value="DOC">DOCUMENTS RELATED</html:option>
	              <html:option value="MJD">MAJOR DEBTOR</html:option>
	              <html:option value="MAC">MAJOR CREDITOR</html:option>
            </html:select>
        </td>
	</tr>
		 <tr>
		   <td>
									
			<button type="button" name="update" id="update" onclick="updateQueryData();" class="topformbutton2"><bean:message key="button.save" /></button>	
		    <button type="button" name="clearQueryButton" id="clearQueryButton" onclick="clearQuery();" class="topformbutton2"><bean:message key="button.clear" /></button>
		    
			</td>
		   </tr>
		</table>
		
	      </td>
	</tr>	
	</table>
	 
	</fieldset>
	
	</html:form>
	</logic:present>
	</div>

<fieldset>	
		 <legend><bean:message key="lbl.queryDetails"/></legend>

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td><b><bean:message key="lbl.queryDateTime"/></b></td>
		 <td><b><bean:message key="lbl.queryRemarks"/></b></td>
		  <td><b><bean:message key="lbl.resolutionStatus"/></b></td>
		  <td><b><bean:message key="lbl.resolutionDateTime"/></b></td>
          <td><b><bean:message key="lbl.resolutionRemarks"/></b></td>
            <td><b><bean:message key="lbl.ver.initiatedby"/></b></td>
          <td><b><bean:message key="lbl.ver.initiatedto"/></b></td>
          <td><b><bean:message key="lbl.queryType"/></b></td>
        </tr>
        <logic:present name="queryList">
        <logic:iterate name="queryList" id="queryListData">
	<tr class="white1">
	<logic:equal name="queryListData" property="resolution" value="Resolved">
     <td>${queryListData.queryDate}</td>
    </logic:equal>
    <logic:equal name="queryListData" property="resolution" value="Pending">
     <td><a href="#" onclick="showQueryData('${queryListData.dealQueryId}');">${queryListData.queryDate}</a></td>
    </logic:equal>
	 <td>${queryListData.queryRemarks}</td>
	 <td>${queryListData.resolution}</td>
	 <td>${queryListData.resolutionDate}</td>
     <td>${queryListData.resolutionRemarks}</td>
     <td>${queryListData.initiatedBy}</td>
     <logic:equal name="queryListData" property="userId" value="">
     	 <td>${queryListData.initiatedTo}</td>
     </logic:equal>
      <logic:notEqual name="queryListData" property="userId" value="">
     	<td>${queryListData.userId}</td>
     </logic:notEqual>
      <td>${queryListData.queryTypeDesc}</td>
    
	 </tr>
	 </logic:iterate>
	 </logic:present>
 </table>    </td>
</tr>
</table>

	</fieldset>


</div>

<script>
	setFramevalues("queryForm");
</script>
</body>
</html:html>


<logic:present name="sms">
<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
	}
</script>
</logic:present>