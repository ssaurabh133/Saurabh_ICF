<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/fileTackingSystem.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript">
		$(function() {
				$("#fileTrackReceivedDate").datepicker({
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

<style type="text/css">
textarea {

color:#000;
font-family:arial,serif;
font-size:13px;
padding-left:2px;
width:835px;
resize:none;
height:150px;
}

</style>

	</head>
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('fileTrackingForm').loanAccountButton.focus();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	
	<html:form action="/fileTrackingDispatchAction" method="post" styleId="fileTrackingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<fieldset>
 
	<legend><bean:message key="lbl.branchFileTracking"/></legend>   
	
	 <logic:present name="editVal"> 
	 
	  <html:hidden  property="trackingId" styleId="trackingId" value="${list[0].trackingId}" />
	    	
	 <logic:notPresent name="viewMode">
	 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr><td><input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/></td></tr>
		<tr>	   
            <td><bean:message key="lbl.loanNo"></bean:message><span><font color="red">*</font></span></td>
		    <td>
		     	<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${list[0].loanNo}" readonly="true" tabindex="-1"/>
             	<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${list[0].lbxLoanNoHID}" />
             	<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${list[0].lbxDealNo}" />
             	<html:hidden  property="customerName" styleId="customerName" value="${list[0].customerName}" />
             	
            </td>
            <td><bean:message key="lbl.fileTrackReceivedDate"/><span><font color="red">*</font></span></td>
	     	<td>
	     	  <html:text property="fileTrackReceivedDate"  styleClass="text" styleId="fileTrackReceivedDate" onchange="checkDate('fileTrackReceivedDate');validateFileTrackReceivedDate();" value="${list[0].fileTrackReceivedDate}" maxlength="50" />
	     	</td>
			
		</tr>	
		<tr>
			<td><bean:message key="lbl.fileTrackStatus"/></td>
			<td>
			<html:select property="fileTrackStatus"  styleId="fileTrackStatus" styleClass="text" value="${list[0].fileTrackStatus}" >
			  <html:option value="SO">Send To OPS</html:option>
			</html:select>
			</td>
		</tr>
		</table>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">	
		<tr>
		<td width="132px"><bean:message key="lbl.fileTrackRemark"/><span><font color="red">*</font></span></td>
	     	<td>
	     	<textarea name="remarks"  style="width:75%" id="remarks" maxlength="500" >${list[0].remarks}</textarea>
	     	</td>
		</tr>
		
		
		</table>

<tr>
	<td>
		<button type="button" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return fileTrackUpdate();"><bean:message key="button.fileTrackSave" /></button>
		<button type="button" class="topformbutton2"  id="deal_viewer"  title="Alt+D" accesskey="D" onclick="return dealViewerForFileTrack();"><bean:message key="button.dealViewer" /></button>
		<button type="button" class="topformbutton2" id="loan_viewer"  onclick="return loanViewerPresentationForFileTracking();" title="Alt+L" accesskey="L"><bean:message key="button.loanViewer" /></button>
	</td>
</tr>
</table>
	 </logic:notPresent>  
	 
	 
	  <logic:present name="viewMode">   
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr><td><input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/></td></tr>
		<tr>	   
            <td><bean:message key="lbl.loanNo"></bean:message><span><font color="red">*</font></span></td>
		    <td>
		     	<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${list[0].loanNo}" readonly="true" tabindex="-1"/>
             	<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${list[0].lbxLoanNoHID}" />
             	<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${list[0].lbxDealNo}" />
             	<html:hidden  property="customerName" styleId="customerName" value="${list[0].customerName}" />
             	
            </td>
            <td><bean:message key="lbl.fileTrackReceivedDate"/><span><font color="red">*</font></span></td>
	     	<td>
	     	  <html:text property="fileTrackReceivedDate" readonly="true" styleClass="text"  onchange="checkDate('fileTrackReceivedDate');validateFileTrackReceivedDate();" value="${list[0].fileTrackReceivedDate}" maxlength="50" />
	     	</td>
			
		</tr>	
		<tr>
			<td><bean:message key="lbl.fileTrackStatus"/></td>
			<td>
			<html:select property="fileTrackStatus" disabled="true"  styleId="fileTrackStatus" styleClass="text" value="${list[0].fileTrackStatus}" >
			  <html:option value="SO">Send To OPS</html:option>
			  <html:option value="RO">Received By OPS</html:option>
			  <html:option value="RU">Received By User</html:option>
			  <html:option value="H">Hold</html:option>
			  <html:option value="C">Clear</html:option>
			  <html:option value="SS">Send To Store</html:option>
			  <html:option value="RS">Received By Store</html:option>
			</html:select>
			</td>
		</tr>
		</table>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">	
		<tr>
		<td width="132px"><bean:message key="lbl.fileTrackRemark"/><span><font color="red">*</font></span></td>
	     	<td>
	     	<textarea name="remarks"  style="width:75%" id="remarks" readonly="readonly" maxlength="500" >${list[0].remarks}</textarea>
	     	</td>
		</tr>
		
		
		</table>

<tr>
	<td>
		<button type="button" class="topformbutton2"  id="deal_viewer"  title="Alt+D" accesskey="D" onclick="return dealViewerForFileTrack();"><bean:message key="button.dealViewer" /></button>
		<button type="button" class="topformbutton2" id="loan_viewer"  onclick="return loanViewerPresentationForFileTracking();" title="Alt+L" accesskey="L"><bean:message key="button.loanViewer" /></button>
	</td>
</tr>
</table>
	  </logic:present>
</logic:present>


<logic:notPresent name="editVal">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr><td><input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/></td></tr>
		<tr>	   
            <td><bean:message key="lbl.loanNo"></bean:message><span><font color="red">*</font></span></td>
		    <td>
		     	<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
             	<html:button property="loanAccountButton" styleId="loanAccountButton" onclick="openLOVCommon(2031,'fileTrackingForm','loanNo','','lbxLoanNoHID','','','getDealNumber','customerName')" value=" " styleClass="lovbutton"/>
             	<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
             	<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
             	<html:hidden  property="customerName" styleId="customerName" value="" />
            </td>
            <td><bean:message key="lbl.fileTrackReceivedDate"/><span><font color="red">*</font></span></td>
	     	<td>
	     	  <html:text property="fileTrackReceivedDate"  styleClass="text" styleId="fileTrackReceivedDate" onchange="checkDate('fileTrackReceivedDate');validateFileTrackReceivedDate();" value="" maxlength="50" />
	     	</td>
			
		</tr>	
		<tr>
			<td><bean:message key="lbl.fileTrackStatus"/></td>
			<td>
			<html:select property="fileTrackStatus"  styleId="fileTrackStatus" styleClass="text" >
			 <html:option value="SO">Send To OPS</html:option>
			</html:select>
			</td>
			
		</tr>
	</table>
	
	<table width="100%" border="0" cellspacing="1" cellpadding="1">	
		<tr>
		<td width="128px"><bean:message key="lbl.fileTrackRemark"/><span><font color="red">*</font></span></td>
	     	<td >
	     	<textarea name="remarks" style="width:77%" class="text" id="remarks" maxlength="500"></textarea>
	     	</td>
		</tr>
		</table>	
	</td>	
</tr>

<tr>
	<td>
		<button type="button" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return fileTrackSave();"><bean:message key="button.fileTrackSave" /></button>
		<button type="button" class="topformbutton2"  id="deal_viewer" disabled="disabled"  title="Alt+D" accesskey="D" onclick="return dealViewerForFileTrack();"><bean:message key="button.dealViewer" /></button>
		<button type="button" class="topformbutton2" id="loan_viewer" disabled="disabled" onclick="return loanViewerPresentationForFileTracking();" title="Alt+L" accesskey="L"><bean:message key="button.loanViewer" /></button>
	</td>
</tr>
</table>
</logic:notPresent>


 </fieldset>	  
</html:form>
<br/>



	</div>
	
</div>
<logic:present name="sms">
	<script type="text/javascript">
	
	if('<%=request.getAttribute("sms")%>'=='S'){
	
		alert('<bean:message key="lbl.dataSavedSucc"/>');
		location.href="<%=request.getContextPath()%>/fileTrackingBehindAction.do?method=branchFileTracking";
	
	}
	else if('<%=request.getAttribute("sms")%>'=='E'){
	
		alert('<bean:message key="lbl.dataNtSavedSucc"/>');
		location.href="<%=request.getContextPath()%>/fileTrackingBehindAction.do?method=branchFileTracking";

	}
	
  </script>
	</logic:present>
</body>
</html:html>