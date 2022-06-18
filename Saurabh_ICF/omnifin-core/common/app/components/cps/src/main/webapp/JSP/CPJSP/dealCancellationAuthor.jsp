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
		
		<logic:present name="css">
				<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
			<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/dealClosure.js"></script>
		
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

	<body onload="enableAnchor();checkChanges('dealAuthorForm');document.getElementById('dealAuthorForm').decison.focus();">

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
 	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	<html:hidden  property="dealId" styleId="dealId" value="${list[0].dealId}" />
<html:form action="/dealCancellationAuthor" method="post" styleId="dealAuthorForm">

  <fieldset>
  
 <legend><bean:message key="lbl.dealCanAuthor"/></legend>
<table cellspacing=0 cellpadding=0 width="100%" border=0>
	<tr>
     <td><bean:message key="lbl.decison"/><font color="red">*</font></td>
         <td><html:select property="decison" styleId="decison" styleClass="text" value="">
		 	<option value="A">Approve</option>
		 	<option value="X">Rejected</option>
			<option value="P">Send Back</option>
 		</html:select></td>       
        
        <td><bean:message key="lbl.remark"/><font color="red">*</font></td>
          <td><label>
          
             <textarea name="textarea" class="text" maxlength="1000" id="textarea" cols="70" rows="1"></textarea>
         </label></td>
	</tr>
<tr>
 <td>
<button type="button" name="search" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return dealAuthorRemarks();"><bean:message key="button.save" /></button>
 </td>
</tr>
 </table>

</fieldset>
</html:form>
</div>

</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("dealAuthorForm");
</script>
</body>
</html>

 <logic:present name="message">
	<script type="text/javascript">
		if('<%=request.getAttribute("message").toString()%>'=="SB")
		{
			alert("<bean:message key="msg.sendBackSuccessfully" />");
			parent.location="<%=request.getContextPath()%>/dealClosureMakerBehindAction.do?method=dealAuthorSearch";
		}
		else if('<%=request.getAttribute("message").toString()%>'=="RJCT")
		{
			alert("<bean:message key="msg.Datareject" />");
			parent.location="<%=request.getContextPath()%>/dealClosureMakerBehindAction.do?method=dealAuthorSearch";
		}
		else if('<%=request.getAttribute("message").toString()%>'=="APP")
		{
			alert("<bean:message key="msg.approvedSuccessfully" />");
			parent.location="<%=request.getContextPath()%>/dealClosureMakerBehindAction.do?method=dealAuthorSearch";
		}
		
		else if('<%=request.getAttribute("message").toString()%>'=="C" || '<%=request.getAttribute("message").toString()%>'=="E")
			{
	      			alert('<%=request.getAttribute("status").toString()%>');  
		} 
    </script>
</logic:present>

		