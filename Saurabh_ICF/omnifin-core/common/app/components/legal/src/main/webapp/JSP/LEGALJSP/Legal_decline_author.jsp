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
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>    
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	     <script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalDeclineDispatchNotice.js"></script>
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
	<body onload="checkChanges('legalDeclineDispatch');" onunload="closeAllWindowCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/legalDeclineDispatchNoticeAction" method="post" styleId="legalDeclineDispatch">
 
   <fieldset>
  
  <legend><bean:message key="lbl.author"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments" /><span id="hideAsterik1" style="display:none;"><font color="red">*</font></span></td>
			 <td width="35%"><div style="float:left;">
			  <textarea name="comments" id="comments" maxlength="1000" ></textarea>
			  <html:hidden property="lbxLoanNoHID"  styleId="lbxLoanNoHID" value="${sessionScope.loanID}"/>
			  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			  </div></td>
		
		<td width="17%">&nbsp;</td>
		
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"></bean:message></td>
		 <td  id=""><span style="float: left;">
		   <html:select property="checkerDecision" styleId="checkerDecision" onchange="hideAsterik(document.getElementById('checkerDecision').value)">
		     <html:option value="D">Dispatch</html:option>
             <html:option value="R">Decline</html:option>
          
		     </html:select>
		 </span></td>
		 
		</tr> 
	<%--		<tr>
 	<td width="13%"><bean:message key="lbl.legalReason" /><span id="hideAsterik2" style="display:none;"><font color="red">*</font></span></td>
		<td>
		<html:text property="reasonDesc" styleId="reasonDesc" value="${requestScope.list[0].reasonDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxReasonId" styleId="lbxReasonId"  />
		<html:button property="reasonButton" styleId="reasonButton" onclick="closeLovCallonLov1();openLOVCommon(1520,'legalDeclineDispatch','reasonDesc','','','','','','lbxReasonId');" value=" " styleClass="lovbutton"> </html:button>
		</td>   
		
		
		</tr>		--%>  
		<tr>
	        <td> 
           
	       <button type="button" class="topformbutton2" id="save" onclick="return onSavelegalDeclineDispatchNotice('<bean:message key="msg.plsSelReqField" />');"  title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	      
	       
	       </td>
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>

  </html:form>

<logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='NONE')
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
		
	}
	</script>
</logic:present>
     <logic:present name="sms">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("sms").toString()%>'=='S')
		{
	
			if('<%=request.getAttribute("decision").toString()%>'=='D')
			{
			alert("<bean:message key="msg.NoticeDispatchSucc" />");
			parent.location="<%=request.getContextPath()%>/legalDeclineDispatchNoticeBehind.do";
			}
			if('<%=request.getAttribute("decision").toString()%>'=='R')
			{
			alert("<bean:message key="msg.NoticeDeclineSucc" />");
			parent.location="<%=request.getContextPath()%>/legalDeclineDispatchNoticeBehind.do";
			}
		
		}
	
		else if('<%=request.getAttribute("sms").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			parent.location="<%=request.getContextPath()%>/noticeCheckerBehind.do";
		}
		
		</script>
		</logic:present>
</div>



</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("legalDeclineDispatch");
</script>
</body>
</html:html>