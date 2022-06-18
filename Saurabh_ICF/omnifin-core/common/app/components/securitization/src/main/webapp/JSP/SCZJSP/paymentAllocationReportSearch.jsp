
<%@page import="org.springframework.web.context.request.RequestScope"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.text.SimpleDateFormat"%>
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
	
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	
	<html:form styleId="sourcingForm" action="/paymentAllocationProcessAction" method="post" >   
	 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	<input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate %>"/>

	<input type="hidden" name="monthType" id="monthType" value="${requestScope.s1}"/>
	<fieldset><legend><bean:message key="lbl.paymentAllocationSearch"/></legend>  
		
		<table width="100%"  border="0" cellspacing="1" cellpadding="4">
		<tr>
			<td><bean:message key="lbl.poolID"/></td>
			<td>
				<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />  
					<html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100"  readonly="true" tabindex="-1"/>   
   				<html:hidden property="lbxPoolID" styleId="lbxPoolID" />
    			<input type="hidden" name="hcommon" id="hcommon" />
    			<logic:notPresent name="s1">
   					<html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(12002,'sourcingForm','poolID','userId','poolID', 'userId','','','poolName','poolCreationDate1');" value=" " styleClass="lovbutton"></html:button>
   				</logic:notPresent>
	 		</td>
           	<td><bean:message key="lbl.PoolName"/></td>
		 	<td><html:text styleClass="text" property="poolName" styleId="poolName" readonly="true" maxlength="50"  /> </td>
		</tr>
		<tr>   
			<td><bean:message key="lbl.PoolCutOffDate"/></td>
			<td><html:text styleClass="text" property="poolCreationDate" readonly="true" styleId="poolCreationDate1" onchange="checkDate('poolCreationDate')" /></td>
     		
     		<td><bean:message key="lbl.selectMonthOfReport"/><font color="red">*</font></td>
     		
     		<td>
     			<logic:present name="s1">
     				<html:text styleClass="text" readonly="true" property="month" styleId="month11" maxlength="20" />
     			</logic:present>
	      		<logic:notPresent name="s1">
	       			<html:text styleClass="text" property="month" styleId="month" maxlength="20" />
	      		</logic:notPresent>
	     	</td>
	       
     	</tr>
		<tr>
		    <td align="left">  
		    	<button type="button" name="button" class="topformbutton3" title="Alt+S" accesskey="O" onclick="checkValid();">OK</button>
			
			<logic:present name="s1">
				<button type="button" name="button" id="search" class="topformbutton3" title="Alt+S" accesskey="S" onclick="generateReportForPaymentAllocationDetails();">GenerateReport</button>
			</logic:present>	
			<logic:notPresent name="s1">
				<button type="button" disabled="disabled" name="button" title="Alt+S" accesskey="S" >GenerateReport</button>
			</logic:notPresent>
			
			</td>
		</tr>
		</table>
    </fieldset>
	</html:form>
</body>
</html:html>
<logic:present name="msg">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</logic:present>
<logic:present name="ref">
	<script type="text/javascript">
		if(!confirm('${ref}')){
			window.location="<%=request.getContextPath()%>/paymentAllocationBehindAction.do";	
			//document.getElementById("search").disabled = true;
		}
		
	</script>
</logic:present>
<logic:present name="msg1">
	<script type="text/javascript">
		//alert('${msg1}');
	</script>
</logic:present>
