
<!-- 
Author Name :- Ankit Agarwal
Date of Creation :27-02-2012
Purpose :-  Limit Enhancement Maker
-->

<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	

		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/limitEnhancement.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
	
	<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>	

		
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>
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

	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
	<body onload="enableAnchor();checkChanges('LimitAuthorForm');document.getElementById('LimitAuthorForm').decison.focus();">

	<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
	</logic:notPresent>
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
 	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
 	<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${limitAuthorList[0].lbxDealNo}" />
 	 <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo"  value="${limitAuthorList[0].lbxLoanNo}" />
 	<html:hidden property="newLoanAmount" styleId="newLoanAmount"  value="${limitAuthorList[0].newLoanAmount}"/>
<html:form action="/limitEnhanceAuthor" method="post" styleId="LimitAuthorForm">

  <fieldset>
  
 <legend><bean:message key="lbl.lmitEnsAuthor"/></legend>
<table cellspacing=0 cellpadding=0 width="100%" border=0>
	<tr>
     <td><bean:message key="lbl.decison"/><font color="red">*</font></td>
         <td><html:select property="decison" styleId="decison" styleClass="text" value="">
		 	<option value="A">Approved</option>
			<option value="X">Rejected</option>
			<option value="P">SendBack</option>
 		</html:select></td>       
        
        <td><bean:message key="lbl.remark"/><font color="red">*</font></td>
          <td><label>
          
             <textarea name="textarea" class="text" maxlength="1000" id="textarea" cols="70" rows="1"></textarea>
         </label></td>
	</tr>
<tr>
 <td>
<button type="button" name="search" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveAuthorRemarks();"><bean:message key="button.save" /></button>
 </td>
</tr>
 </table>

</fieldset>

 <logic:present name="sms">
	<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='A'){
	alert('<bean:message key="msg.approvedSuccessfully"/>');
	parent.location="<%=request.getContextPath()%>/limitEnhansmentBehind.do?method=limitAuthorSearch";
		
	}
	if('<%=request.getAttribute("sms").toString()%>'=='P'){
	alert('<bean:message key="msg.sendBackSuccessfully"/>');
	parent.location="<%=request.getContextPath()%>/limitEnhansmentBehind.do?method=limitAuthorSearch";
		
	}
	if('<%=request.getAttribute("sms").toString()%>'=='X'){
	alert('<bean:message key="msg.Datareject"/>');
	parent.location="<%=request.getContextPath()%>/limitEnhansmentBehind.do?method=limitAuthorSearch";
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E'){
	      alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	} 
    </script>
	</logic:present>
</html:form>
<script>
	setFramevalues("LimitAuthorForm");
</script>
</body>
</html>
