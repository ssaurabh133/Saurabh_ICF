<%@page import="org.springframework.web.context.request.RequestScope"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@page import="java.util.Date"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();

%>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />		 
		<!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
    	<script type="text/javascript"  src="<%=request.getContextPath() %>/js/sczScript/securitizationReportGeneration.js"></script>
		
     
		<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
        int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
        request.setAttribute("no",no); %>
	</head>
	<body onload="enableAnchor();">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	
	<div id="centercolumn">

<div id="revisedcontainer">

	<html:form styleId="futureFlowReportGeneration" action="/futureFlowProcessAction" method="post">   
	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" /> 
	<fieldset><legend><bean:message key="lbl.futureFlowSearch"/></legend>  
		<fieldset>
		 <legend><bean:message key="lbl.downloadReport"/></legend>  
		<table width="100%"  border="0" cellspacing="1" cellpadding="4">
		  <tr>
			<td>Select Pool ID</td>
			<td>
				<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />  
		  		<html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100"  readonly="true" tabindex="-1"/>   
   				<html:hidden property="lbxPoolID" styleId="lbxPoolID" />
    			<input type="hidden" name="hcommon" id="hcommon" />
	 			<html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(12001,'futureFlowReportGeneration','poolID','userId','poolID', 'userId','','','poolName','poolCreationDate1');" value=" " styleClass="lovbutton"></html:button>   
           </td>
           <td>Pool Creation Date</td>
          	<td><html:text styleClass="text" readonly="true" property="poolCreationDate" styleId="poolCreationDate1"  /></td>
           <html:hidden styleClass="text" property="poolName" styleId="poolName" />
         </tr>
           
           <tr>
         	<td><bean:message key="lbl.selectMonth"/><font color="red">*</font></td>
	     	<td >
	     		<html:select property="mm" styleId="mm">
	     			<html:option value="">--Select--</html:option>
	     			<html:option value="01">January</html:option>
	     			<html:option value="02">February</html:option>
	     			<html:option value="03">March</html:option>
	     			<html:option value="04">April</html:option>
	     			<html:option value="05">May</html:option>
	     			<html:option value="06">June</html:option>
	     			<html:option value="07">July</html:option>
	     			<html:option value="08">August</html:option>
	     			<html:option value="09">September</html:option>
	     			<html:option value="10">October</html:option>
	     			<html:option value="11">November</html:option>
	     			<html:option value="12">December</html:option>
	     		</html:select>
	     		<select name="year" style="width: 50px" id="year">
	     			<option value="">--Year--</option>
	     			<logic:iterate id="year" name="yy">
	     				<option><bean:write name="year"/></option>
	     			</logic:iterate>
	     		</select>
	     	</td>
		</tr>
		<tr>
			<td colspan="4">
		  		<button type="button" name="generateReport" class="topformbutton4" id="generateReport" title="Alt+G" accesskey="G" onclick="generateReportForFutureFlow();"><bean:message key="button.generatereport"/></button>
		  	</td>
		</tr>		
		</table>
		</fieldset>
    </fieldset>
	</html:form>
	</div>
	</div>
</body>
</html:html>

<logic:present name="msg">
	<script type="text/javascript">
	alert('${msg}');
	</script>
</logic:present>
