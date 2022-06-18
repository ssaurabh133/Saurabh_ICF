<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 29-Nov-2011-->
<!--Purpose  : Show branches for user -->
<!--Documentation : -->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userApprovalMatrix.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
%>
</head>

<body onload="enableAnchor();">	
<fieldset>	
	<legend><bean:message key="lbl.listOfBranch"/></legend>    
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
		 		<tr>
		    		<td class="gridtd">
        				<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
    					<tr class="white2">
	        	 			<td align="center"><b><bean:message key="lbl.user_id"/></b></td>
        		 			<td align="center"><b><bean:message key="lbl.branchDesc"/></b></td>
			 			</tr>
						<logic:present name="list">   
						<logic:iterate name="list" id="subdealdetails" >
	   					<tr class="white1">	        
          	 				<td>${subdealdetails.userId}</td>          	 
         	 				<td>${subdealdetails.branchName}</td>
         	 			</tr>
						</logic:iterate>
						</logic:present>
						</table>
					</td>
			</tr> 
 		</table>
 		</td>
 	</tr>
 	</table>
 </fieldset>
</body>
</html:html>