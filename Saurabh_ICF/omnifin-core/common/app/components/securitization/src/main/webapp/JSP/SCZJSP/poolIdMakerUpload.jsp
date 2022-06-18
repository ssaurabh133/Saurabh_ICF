<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.login.roleManager.UserObject"%>

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
		  <!-- css and jquery for Datepicker -->
			<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
		    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		 <!-- css and jquery for Datepicker -->
		 	
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/sczScript/poolIDMakerAuthor.js"></script>
		  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

		<%	
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
			request.setAttribute("no",no); 
		%>	

	 	<%
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>		

</head>
<body onload="enableAnchor();document.getElementById('manualAdviceSerchForm').loanButton.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
		<logic:present name="image">
    	   	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
    	<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath() %>"/>
    	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div id="centercolumn">
		<div id="revisedcontainer">
			<html:form action="/poolIdMakerUploadAction" enctype="multipart/form-data" styleId="sourcingForm" method="post">
				<fieldset>
					<legend>Pool Upload</legend>         
	   					<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									 <td><bean:message key="lbl.poolID"/></td>
									 <td>
								        <%--<html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${requestScope.poolNo}" readonly="true" tabindex="-1"/> comment by Akbar--%>
								         <%--change by Akbar --%>
								        <input type="hidden" name="userId" id="userId" value="${userobject.userId}" /> 
										 <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100"  readonly="false"	 tabindex="-1"/>   
									  	 <%-- <html:hidden property="lbxPoolID" styleId="lbxPoolID" /> --%>
									     <!-- <input type="hidden" name="lbxPoolID" id="lbxPoolID" value=""/> -->
									     <input type="hidden" name="lbxPoolID" id="lbxPoolID" value="${userobject.userId}"/>
										 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(12006,'sourcingForm','poolID','userId','poolID','userId','','','lbxPoolID');" value=" " styleClass="lovbutton"></html:button>  
							         <%--change by Akbar--%>
							         </td>
								</tr>
								
								<tr>
		       						<td><bean:message key="lbl.fileDescription"/><font color="red">*</font></td>
		      						<td><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
	       						</tr>
								<tr>
									<td>
									<button type="button" name="button" id="err" class="topformbutton2" title="Alt+E" accesskey="E" onclick="return errorLogPoolIdMaker()"><bean:message key="button.error" /></button>
									<logic:present name="type">
										<logic:equal name="type" value="poolUpload">
											<button type="button" name="button" onclick="return poolUpload();" class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.poolUpload" /></button>
										</logic:equal>
										<logic:equal name="type" value="repamentSchdUpload">
											<button type="button" name="button" onclick="return repaymentScheduleUpload();" class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.repamentSchdUpload" /></button>
										</logic:equal>
										<logic:equal name="type" value="bankUpload">
											<button type="button" name="button" onclick="bankUploadSave();" class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.bankUpload" /></button>
										</logic:equal>
									</logic:present>
								</td>
								
								</tr>
							</table>
				</fieldset>
				
			</html:form>
		</div>
	</div>

<logic:present name="msgFlag">
<script type="text/javascript">

if('<%=request.getAttribute("msgFlag").toString()%>'=='S')
{
	alert("Data Saved Successfully ");
	window.close();
		window.opener.location="<%=request.getContextPath()%>/poolIdMakerProcessAction.do?method=getPoolSearchedData&aa=temp&poolID=<%=request.getAttribute("poolId")%>";
}
else if('<%=request.getAttribute("msgFlag")%>'=='E')
{
	alert('File Not Upload');
	window.close();
	window.opener.location="<%=request.getContextPath()%>/poolIdMakerProcessAction.do?method=getPoolSearchedData&aa=temp&poolID=<%=request.getAttribute("poolId")%>";
}
		
</script>
</logic:present>
<!-- Added by Akbar for Securtization -->
<logic:present name="fieldUpdate">
       <script type="text/javascript">	
			alert("<%=request.getAttribute("fieldUpdate")%>");
       </script>
 </logic:present>
 <!-- Added by Akbar for Securtization -->
 <logic:present name="procStatusMSG">
       <script type="text/javascript">	
			alert("<%=request.getAttribute("procStatusMSG")%>");
       </script>
 </logic:present>
 
</body>
</html>