<!--Author Name : Manish Shukla-->
<!--Date of Creation : June-2013-->
<!--Purpose  : Rate Approval Matrix Maker-->
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
<logic:present name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
</logic:present>
<logic:notPresent name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>

<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/rateApprovalMaker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	

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

<body onload="enableAnchor();fntab();">
<html:form styleId="rateApprovalMakerForm"  method="post"  action="/rateMatrixMakerDispatchAction" >
 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" id="businessdate" value="${userobject.businessdate }" />

<input type="hidden" name="saveForwardFlag" id="saveForwardFlag"  />
<fieldset>
<legend><bean:message key="lbl.rateApprovalMatrix" /></legend>
  <table width="100%">
  
  <tr>
		
	<td>
		<bean:message key="lbl.rateApprovalProduct"/>  </td>
	<td > 
		 <html:text  property="product"  styleId="product" styleClass="text" value="${rateApprovalList[0].product}" readonly="true" tabindex="-1"/>
         <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${requestScope.list[0].lbxProductID}" />
<!-- <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(2017,'rateApprovalMakerForm','product','','','','','deleteScheme','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>-->
	</td>
	<td> <bean:message key="lbl.rateApprovalScheme"/>  </td>
	<td > 
	      <html:text property="scheme" styleId="scheme" styleClass="text" value="${rateApprovalList[0].scheme}" readonly="true" tabindex="-1"/>
          <html:hidden  property="lbxScheme" styleId="lbxScheme" value="${requestScope.list[0].lbxScheme}" />
    <!--  	  <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(2018,'rateApprovalMakerForm','scheme','product','', 'lbxProductID','Please Select Product','','lbxScheme')" value=" " styleClass="lovbutton"></html:button>
   -->
   </td>
	     
	</tr>
     
    <tr>
		
		<td><bean:message key="lbl.rackRate" /></td>
		<td><html:text property="rackRate"  disabled="true" style="text-align: right" styleId="rackRate" maxlength="18" value="${rateApprovalList[0].rackRate}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td> 
		
		<td><bean:message key="lbl.rackProcessingFee" /></td>
		<td><html:text property="rackProcessingFee"  disabled="true" style="text-align: right" styleId="rackProcessingFee" maxlength="18" value="${rateApprovalList[0].rackProcessingFee}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>  
		     
    </tr>
       
    <tr>
	
		<td>
    
	    <logic:present name="editVal">
	      <button type="button" name="update" id="update" title="Alt+V" accesskey="V" onclick="return updateRateApproval('P');" class="topformbutton2" ><bean:message key="button.save" /></button>
	      <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return updateRateApproval('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
	   </logic:present>
   
	   <logic:present name="save">
	    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRateApproval('P');" class="topformbutton2" ><bean:message key="button.save" /></button>
	    <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return saveRateApproval('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
	   </logic:present>
	   
    	<br>
   		 </td> 
   	</tr>
    
    </table>
	
</fieldset>
 
</html:form>


 </body>
		</html:html>