<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@page import="java.util.Date"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();

%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
	
		<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
        int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
        request.setAttribute("no",no); %>
        
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
    	<script type="text/javascript" src="<%=path%>/js/sczScript/poolIDMakerAuthor.js"></script>
   
   
		<script type="text/javascript">
		
		 function btn_reset() {
		 	//parent.location.reload(); 
		 }
		 
	        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
			
			function defaultFocus()
			{
				document.getElementById('sourcingForm').loanNoButton.focus();
			}
		</script>
	
	</head>
	<body onclick="parent_disable();init_fields();" onload="enableAnchor();document.getElementById('sourcingForm').loanNoButton.focus();" onunload="closeAllLovCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/DownloadPoolDataProcessAction" method="post" styleId="sourcingForm">
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
		<input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate %>"/>
    
	<fieldset>
  	<legend><bean:message key="lbl.downloadPoolUploads"/></legend>   
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><bean:message key="lbl.poolID" /></td>
				<td><html:text property="poolID" readonly="true" value="${poolID}" /></td>
				<td><bean:message key="lbl.downloadType" /></td>
				<td>
					<html:select property="downloadType">
						<html:option value="">-Select-</html:option>
						<html:option value="P">Pool Upload</html:option>
						<html:option value="B">Bank Upload</html:option>
						<html:option value="R">Re-Payment Upload</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><button type="button" name="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="downloadPoolIdData();"><bean:message key="button.downloadPool" /></button></td>
			</tr>
		</table>
	</fieldset>
  </html:form>
	</div>
	<logic:present name="msg">
   	<script type="text/javascript">
   		alert("Data Saved Successfully");
   		parent.frames['menu'].location.reload();
   	</script>
   </logic:present>
 </div>
</body>
</html:html>