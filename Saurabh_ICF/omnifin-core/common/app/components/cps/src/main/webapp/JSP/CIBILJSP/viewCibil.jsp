<!--Author Name : Manish Shukla-->
<!--Date of Creation : -->
<!--Purpose  :Cibil Initiation & Verification-->
<!--Documentation : -->

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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=path%>/js/cpScript/cibilVerification.js"></script>
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

<body onload="enableAnchor();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="cibilVerification"  method="post"  action="/viewCibilDispatchAction" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.cibilView" /></legend>
  <table width="100%">
    <tr>
    	<td><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
  	<td>
				<html:hidden styleClass="text" property="dealID" styleId="dealID" value="${gridList[0].dealID}"/>
				<html:text styleClass="text" maxlength="10" property="dealNO" styleId="dealNO" readonly="true" value="${gridList[0].dealNO}" tabindex="-1"/>
				<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(2010,'cibilVerificationDispatchAction','dealNO','','','','','','customerName');" value=" " styleClass="lovbutton"></html:button>
   		</td>  
   	
   		<td><bean:message key="lbl.customerName"/></td>
		<td><html:text styleClass="text" maxlength="10" property="customerName" styleId="customerName" style="" readonly="true" value="${gridList[0].customerName}" maxlength="100"/></td>
   </tr>
 
   <tr>
   		<td>
   		<button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return searchCibilDeal();"><bean:message key="button.search" /></button>
   	
   		</td>
   		
   </tr> 
 </table>		
</fieldset>

<logic:present name="gridList">	
<logic:notEmpty name="gridList">
<fieldset><legend><bean:message key="lbl.deals"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td class="gridtd">
		   <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		<tr class="white2">
    	 		<td align="center"><b>Select</b></td>
	 	 		<td align="center"><b><bean:message key="lbl.dealNo"/></b></td>
	 	 		<td align="center"><b><bean:message key="lbl.cibilId"/></b></td>
	 	 		<td align="center"><b><bean:message key="lbl.cibilReponse"/></b></td>
	 	 		<td align="center"><b><bean:message key="lbl.responseDate"/></b></td>
	 	 		<td align="center"><b><bean:message key="lbl.customerName"/></b></td>
         		<td align="center"><b><bean:message key="lbl.Type"/></b></td>
         		<td align="center"><b><bean:message key="lbl.gender"/></b></td>
         		<td align="center"><b><bean:message key="lbl.customerRole"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.memberName"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.memberId"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.inquiryPurpus"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.refranceNo"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.dob"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address1"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address2"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address3"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.addressType"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.addressDetail"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.dist"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.state"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.pincode"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.country"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.panNo"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.No"/></b></td>
	     		<td align="center"><b><bean:message key="voterId"/></b></td>
	     		<td align="center"><b><bean:message key="passport"/></b></td>
	     		<td align="center"><b><bean:message key="drivingLicense"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.primary"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.alter"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.toll"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.fax"/></b></td>
		    </tr>
			<logic:iterate name="gridList" id="sublist">
			<tr class="white1">
				<td align="center"><input type="radio" name="radioId" id="radioId" value="${sublist.lbxCibilId}" /></td>
	     		<td>${sublist.dealNO}</td>
	     		<td>${sublist.lbxCibilId}</td>
	     		<td>${sublist.cibilResponse}</td>
	     		<td>${sublist.cibilDate}</td>
	     		<td>${sublist.customerName}</td>
	    		<td>${sublist.customerType}</td>
	    		<td>${sublist.customerGender}</td>	    
	    		<td>${sublist.customerRole}</td>
	    		<td>${sublist.memberName}</td>	
	    		<td>${sublist.memberId}</td>	
	    		<td>${sublist.inquiryPurpus}</td>	
	    		<td>${sublist.refranceNo}</td>	
	    		<td>${sublist.customerDob}</td>
	    		<td>${sublist.address1}</td>	
	    		<td>${sublist.address2}</td>
	    		<td>${sublist.address3}</td>
	    		<td>${sublist.addressType}</td>
	    		<td>${sublist.addressDetail}</td>
	    		<td>${sublist.distict}</td>
	    		<td>${sublist.state}</td>	
	    		<td>${sublist.pincode}</td>	
	    		<td>${sublist.country}</td>	
	    		<td>${sublist.panNo}</td>	
	    		<td>${sublist.regNo}</td>	
	    		<td>${sublist.voterId}</td>	
	    		<td>${sublist.passport}</td>	
	    		<td>${sublist.drivingLicense}</td>	
	    		<td>${sublist.primaryPhone}</td>	
	    		<td>${sublist.alterPhone}</td>	
	    		<td>${sublist.tollPhone}</td>	
	    		<td>${sublist.faxNo}</td>    
	    		<input type="hidden" name="cibilResponse" id="cibilResponse" value="${sublist.cibilResponse}" />		    	
	     	</tr> 
   			</logic:iterate>
   		 	<td colspan="32" class="white1"><button type="button" name="viewGenerate" title="Alt+S" accesskey="S" id="button" class="topformbutton3" onclick="return viewCibilReport();"><bean:message key="button.viewCibilReport" /></button></td>
   		 </table>
   	</td>
   </tr>
   </table>
</fieldset>
</logic:notEmpty>
</logic:present>

</html:form>		
	
	<logic:present name="success">
	<script type="text/javascript">	
		alert('Successfully Response Recived From CIBIL.');
	</script>
	</logic:present>
	<logic:present name="error">
	<script type="text/javascript">	
		alert('Some Error Occured During Communication.');
	</script>
	</logic:present>	
	</body>	
	</html:html>