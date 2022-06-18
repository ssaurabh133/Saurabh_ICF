<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.connect.CommonFunction"%>
<%@page import="com.connect.ConnectionDAO"%>
<%@page import="com.logger.LoggerMsg"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
	   
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/capsScript/collectionProcess.js"></script>
		
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
	
<body onload="enableAnchor();JavaScript:refreshClassificationProcess(5000);">
	
	<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form action="/classificationProcessAction" styleId="classificationForm"> 
  <fieldset>
  <legend><bean:message key="lbl.classificationProcess" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr>
      <td class="white2" style="width:220px;"><strong><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.processName"/> </strong></td>
      <%--<td class="white2" style="width:220px;" align="center"><strong> <bean:message key="lbl.imagetimer"/> </strong></td>--%>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.laststartTime"/></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.lastendTime"/> </strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.beginingOfDaystatus"/></strong></td>
 
    </tr>
    <logic:present name="classificationData">
    <logic:notEmpty  name="classificationData">
    <logic:iterate id="classificationDataObj" name="classificationData" indexId="count">
	<tr>
	 <td class="white2" style="width:220px;"><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></td>
	 <td class="white2" style="width:220px;"><bean:write name="classificationDataObj" property="processName"/></td>
     <td class="white2" style="width:220px;"><bean:write name="classificationDataObj" property="lastStartTime"/></td>
     <td class="white2" style="width:220px;"><bean:write name="classificationDataObj" property="lastEndStartTime"/></td>
     <td class="white2" style="width:220px;">
     <input type="hidden" name="statusFlag" id="statusFlag<%=count.intValue()+1%>" value="<bean:write name='classificationDataObj' property='status'/>" />
     <logic:notPresent name="processflag">
      <div id="process " style="display: block">
     &nbsp;&nbsp;&nbsp;<strong><font color="Black"><bean:message key="lbl.noProcess"/></font></strong>
      </div>
      </logic:notPresent>
      <logic:present name="processflag">

     <logic:equal value="P" name="classificationDataObj" property="status">
    <div id="process " style="display:block">
    <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/loder-blue.gif" width="20" height="20" ></img>
 	&nbsp;&nbsp;&nbsp;<strong><bean:message key="lbl.process"/></strong>
    </div>
    </logic:equal>
      <logic:equal value="F" name="classificationDataObj" property="status">
     <div id="processFinish " style="display: block">
   <img align="middle" src="<%=request.getContextPath()%>/images/theme1/green.gif"  width="20" height="20" ></img>
  	 &nbsp;&nbsp;&nbsp;<strong><font color="black"><bean:message key="lbl.processFinish"/></font></strong>
    </div>
    </logic:equal>
     <logic:equal value="E" name="classificationDataObj" property="status">
     <div id="errorProcess " style="display: block">
   <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/red.gif" width="20" height="20" ></img>
    &nbsp;&nbsp;&nbsp;<strong><font color="Red"><bean:message key="lbl.ErrorInProcess"/></font></strong>
    </div>
    </logic:equal>
    </logic:present> 
     </td>
 
	</tr>
	</logic:iterate>
	</logic:notEmpty>
	</logic:present>
	
 </table>
 </fieldset>
  <table width="30%" border="0" cellspacing="1" cellpadding="1">
   <tr>
	   <td><button type="button" name="startProcess" id="startProcess" class="topformbutton2" title="Alt+P" accesskey="P" onclick="classificationStartprocess();"><bean:message key="button.startProcess" /></button></td>
	   <td><b><bean:message key="lbl.autoRefresh" /></b></td>
	   <td>
		    <html:select property="refreshFlag" styleId="refreshFlag" value="${refreshFlag}">
		    	<html:option value="Y">YES</html:option>
		    	<html:option value="N">NO</html:option>
		    </html:select>
	    </td>
   </tr>
   </table>
  </html:form>
</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>
