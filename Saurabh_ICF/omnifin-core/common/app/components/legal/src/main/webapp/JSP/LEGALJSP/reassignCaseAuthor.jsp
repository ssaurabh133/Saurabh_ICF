<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 


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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	     <script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/reassignCase.js"></script>
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
	<body onload="checkChanges('reassignCase');" onunload="closeAllWindowCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/reassignCaseAction" method="post" styleId="reassignCaseForm">
 
   <fieldset>
  
  <legend><bean:message key="lbl.author"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments" /><span><font color="red">*</font></span></td>
			 <td width="35%"><div style="float:left;">
			  <textarea name="remarks" id="remarks" maxlength="1000" ></textarea>
			  
			  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			  </div></td>
		
		<td width="17%">&nbsp;</td>
		
		</tr>
		
		<tr>
		<td width="13%"><bean:message key="lbl.advocate" /><span><font color="red">*</font></span></td>
		<td>
		<html:text property="advocateDesc" styleId="advocateDesc" value="${requestScope.list[0].advocateDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxUserId" styleId="lbxUserId"  />
		<html:button property="advocateButton" styleId="advocateButton" onclick="closeLovCallonLov1();openLOVCommon(1535,'reassignCase','advocateDesc','','','','','','lbxUserId');" value=" " styleClass="lovbutton"> </html:button>
		</td>   
		
		
		</tr>		  
		<tr>
	        <td> 
           
	       <button type="button" class="topformbutton2" id="save" onclick="return saveReassignCase('<bean:message key="msg.plsSelReqField" />');"  title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	      
	       
	       </td>
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>

  </html:form>
  <logic:present name="sms">
		<script type="text/javascript">
		
		//alert("vinod");
		
		if('<%=request.getAttribute("sms").toString()%>'=='S')
		{
	
			
			alert("<bean:message key="msg.CaseReass" />");
			parent.location="<%=request.getContextPath()%>/reassignCaseBehind.do";
		
		}
	
		else if('<%=request.getAttribute("sms").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
		}
		
		</script>
		</logic:present>
</div>



</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("reassignCase");
</script>
</body>
</html:html>