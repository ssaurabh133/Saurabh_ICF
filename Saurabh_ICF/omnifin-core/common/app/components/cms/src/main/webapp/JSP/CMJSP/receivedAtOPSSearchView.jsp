<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ include file="/JSP/sessioncheck.jsp"%>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle" %>
<%@ page errorPage="/JSP/errorJsp.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<logic:present name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
			
		<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>	   
		 
	
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
				
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>
		<script type="text/javascript" src="<%=path%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
 		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	    <%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
		request.setAttribute("no",no); %>
	 	<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
 	 	<script type="text/javascript" src="<%=path%>/js/cmScript/fileTackingSystem.js"></script>
    </head>
		<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('fileTrackingForm').loanButton.focus();init_fields();">
		<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
		<div id="centercolumn">
		<div id="revisedcontainer">
	
		<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
    	</logic:present>
		<logic:notPresent name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
		</logic:notPresent>
		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
		<html:form action="/fileTrackingDispatchAction" method="post" styleId="fileTrackingBranch" >
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
    	<fieldset>
 
		<legend><bean:message key="lbl.ReceivedAtOps"/></legend>   
	    
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
	 	<tr>	
	 	<td width="13%"><bean:message key="lbl.loanNo"/><span><font color="red">*</font></span></td>
			 <td>
		     	<html:text styleClass="text" property="loanNoSearch" styleId="loanNoSearch" maxlength="20"   readonly="true" tabindex="-1"/>
             	<html:button property="loanAccountButton" styleId="loanAccountButton" onclick="openLOVCommon(2046,'fileTrackingForm','loanNoSearch','','lbxLoanNoHIDSearch','','','','customerName')" value=" " styleClass="lovbutton"/>
             	<html:hidden  property="lbxLoanNoHIDSearch" styleId="lbxLoanNoHIDSearch"  />
             	<html:hidden  property="customerName" styleId="customerName" value="" />
            </td>
      	
      	<td width="13%"><bean:message key="lbl.Branch"/></td>
      <td >		
		<html:text property="defaultBranch" styleClass="text" styleId="defaultBranch" maxlength="50" readonly="true" value="${defaultBranch}"/>
		<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="${lbxBranchId}" />
		<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(567,'chequeStatusAction','lbxBranchId','','', '','','','defaultBranch')" value=" " styleClass="lovbutton"  ></html:button>
	</td>
	</tr>
    <tr>
		<td><bean:message key ="lbl.fromDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	    <td >
	    <html:text property="startDate"  styleClass="text" styleId="startDate"  maxlength="50" ></html:text>
	    </td>
	    <td><bean:message key ="lbl.todate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	    <td >
	    <html:text property="endDate"  styleClass="text" styleId="endDate"  maxlength="50" ></html:text>
	    </td>
	 </tr>	
	<tr>
			<td><bean:message key="lbl.allBranch"/></td>
			<td> <input type="checkbox" name=allBranches id="allBranches"  /></td>
		</tr>
		</table>
 		<table width="100%" border="0" cellspacing="1" cellpadding="0">
		<tr>
		<td colspan="3">
		<button type="button" name="Search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return receivedAtOpsSearch();" ><bean:message key="button.search" /></button>
		</td>
		</tr> 		  
		</table>
		</td>
    	</tr>
    	</table>	 
		</fieldset>	
		</html:form>
		<br/>
		</div>
	 
	<fieldset>
			<legend><bean:message key="lbl.ReceivedAtOps" /></legend>

  		<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
  		<tr>
  		<td class="gridtd">
		<input type="hidden" name="psize" id="psize" />
		<input type="hidden" name="pcheck" id="pcheck" />
		<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
		 <tr class="white2"> 
		 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" /></td>
		 <td ><b><bean:message key="lbl.loanNo" /></b></td>
		 <td ><b><bean:message key="lbl.customerName" /></b></td>
		 <td ><b><bean:message key="lbl.Branch" /></b></td>
		 <td ><b><bean:message key="lbl.receivedDate" /></b></td>
		 <td ><b><bean:message key="lbl.remark" /></b></td>
		</tr>
  		<logic:present name="list">
		<logic:notEmpty name="list">	 
		<logic:iterate name="list" id="sublist" indexId="counter">
		<tr class="white1">
		<td>
		<input type="checkbox" name="chk"  id="chk<%=counter.intValue()+1%>" value="${sublist.lbxLoanNoHID}" />
		<input type="hidden" name="lbxLoanNoHID"  id="lbxLoanNoHID<%=counter.intValue()+1%>" value="${sublist.lbxLoanNoHID}" />
		</td>
		<td>	
		${sublist.loanNo}
		</td>
		<td>${sublist.customerName}</td>
		<td>${sublist.branch}</td>
		<td>
		<input type="text" name="fileTrackReceivedDate"  id="fileTrackReceivedDate<%=counter.intValue()+1%>" value="" class="text" onchange="return checkDate('markingDate<%=counter.intValue()+1%>');"/>
		<script type="text/javascript">
  		$(function() {
         		
			var contextPath =document.getElementById('contextPath').value ;
			$("#fileTrackReceivedDate<%=counter.intValue()+1%>").datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
 			changeMonth: true,
 			changeYear: true,
 			yearRange: '1900:+10',
 			showOn: 'both',
 			buttonImage: contextPath+'/images/theme1/calendar.gif',
			buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value

			});
			});
  		</script>
		</td>
		<td>
		<textarea name="remarks" id="remarks<%=counter.intValue()+1%>"  class="text"></textarea>
		</tr>
		</logic:iterate>
		</logic:notEmpty>
					
						
		<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
		<tr class="white1">
		<td align="left" colspan="7">
		<button type="button"  title="Alt+A" accesskey="A" onclick="return receivedAtOpsSave();" class="topformbutton2"><bean:message key="button.save" /></button>
			</td>
			</tr>
	
		</table>
		</logic:present>
 		</table>
 		</td>
 		</tr>
 		</table>
		</fieldset>

		</div>
		<logic:present name="sms">
		<script type="text/javascript">
	
		if('<%=request.getAttribute("sms")%>'=='S'){
	    alert('<bean:message key="lbl.dataSavedSucc"/>');
		location.href="<%=request.getContextPath()%>/receivedAtOpsBehindAction.do?method=operationFileTrackingOps";
	}
		else if('<%=request.getAttribute("sms")%>'=='E'){
		alert('<bean:message key="lbl.dataNtSavedSucc"/>');
		location.href="<%=request.getContextPath()%>/receivedAtOpsBehindAction.do?method=operationFileTrackingOps";
	}
		</script>
		</logic:present>
		</body>
		</html:html>