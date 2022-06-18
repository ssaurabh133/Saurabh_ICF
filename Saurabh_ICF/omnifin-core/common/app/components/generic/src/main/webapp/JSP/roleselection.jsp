<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta name="author" content="" />
	<title>Login to IAPMS</title>

	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/css/contentstyle.css" />
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/css/subpage.css" />

	<!-- CSS for Tab Menu #1 -->
	<link href="<%=request.getContextPath()%>/ddtabmenufiles/ddtabmenu.css"
		type="text/css" rel="stylesheet" />

	<!-- CSS for Tab Menu #2 -->
	<link href="<%=request.getContextPath()%>/ddtabmenufiles/glowtabs.css"
		type="text/css" rel="stylesheet" />

	<!-- CSS for Tab Menu #3 -->
	<link
		href="<%=request.getContextPath()%>/ddtabmenufiles/solidblocksmenu.css"
		type="text/css" rel="stylesheet" />

	<!-- CSS for Tab Menu #4 -->
	<link
		href="<%=request.getContextPath()%>/ddtabmenufiles/ddcolortabs.css"
		type="text/css" rel="stylesheet" />

	<!-- CSS for Tab Menu #5 -->
	<link
		href="<%=request.getContextPath()%>/ddtabmenufiles/chromemenu.css"
		type="text/css" rel="stylesheet" />
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/css/mainstyle.css" />
	<style type="text/css">
#Login {
	border: 1px solid #DFA641;
	color: #8A9AA6;
	float: left;
	height: 310px;
	position: relative;
	width: 335px;
}

#Signup #Login .SubHeader {
	color: #DFA641;
}

#Signup .SubHeader {
	padding: 0;
}

.SubHeader {
	color: #4A7195;
	font-family: Georgia;
	font-size: 18px;
	font-weight: normal;
	margin: 3px 0;
}

#LoginInput {
	margin: 20px;
}

#Login label {
	display: block;
	margin: 10px 0 0;
}

#Login .txtbox {
	display: block;
	width: 270px;
}

label {
	color: #4A7195;
	font-weight: bold;
}

element.style {
	width: 166px;
}

#lnkForgot {
	display: block;
	font-size: 10px;
	margin: 0 auto 10px;
	width: 105px;
}

a {
	color: #0364A4;
}

a {
	cursor: pointer;
	text-decoration: none;
}

.error-msg {
	background-color: #FFFFCC;
	border: 1px solid #F8E682;
	color: #990000;
	display: block;
	margin: 5px 0;
	padding: 5px 10px;
}

p {
	height: 0px;
	background: url("images/bg_topbar.gif") repeat-x;
	font-family: verdana, arial, sans-serif;
	font-size: 8pt;
	margin-bottom: 0px;
	background-color: black;
	color: green;
}

p .a {
	color: green;
}
</style>
	<script type="text/javascript">
     function rolevalidation(){
	     if( document.getElementById('roleId').value== '0'){
	     document.getElementById('errormsg').style.display='block';
		 return false; 		     
	     }else{
	     return true;
	     }     
     }
     
	     
</script>
</head>
<body onload="enableAnchor();">
	<div class="maincontainer">

		<div id="toprightdiv">

			<div id="toprightdivbox">




			</div>

		</div>

		<div style="height: 2px;">
			&nbsp;
		</div>
		<a href="#" title=""><img border="0"
				src="<%=request.getContextPath()%>/images/logo.png" />
		</a><a href="#" title=""><img border="0"
				src="<%=request.getContextPath()%>/images/he4.png" />
		</a>
		<div style="height: 4px;">
			&nbsp;
		</div>

		<div class="ddcolortabsline">
			&nbsp;
		</div>




	</div>
	<table align="center">
		<tr>
			<td>
				<fieldset class="InfoBox" id="Login">
					<legend class="SubHeader">
						Role Selection
					</legend>
					<html:form method="post" action="roleselection"
						styleId="roleselectionformid" onsubmit="return rolevalidation();">
						<div id="LoginInput">


							<html:messages id="loginError" property="errorlogin"
								message="true">
								<span id="errormsg" class="error-msg"> <bean:write
										name="loginError" /> </span>
							</html:messages>



							<span style="display: none;" id="errormsg" class="error-msg">Please
								select your Role NAme</span>
							<label for="role">
								Select Role 
							</label>

							<logic:present  name="roleselectionlist" >
							 <html:select property="roleId" styleId="roleId" onchange="removeMsg('rolenameerror')">
								<option value="0">
									SELECT				
								</option>
							<br/>
								<logic:iterate id="userRolelistId" name="roleselectionlist">
											<option value="<bean:write name="userRolelistId" property="roleID"/>"><bean:write name="userRolelistId" property="roleName"/>  
										</option>
								</logic:iterate>
							</html:select>  
						   </logic:present>
							
							<div style="text-align: center;">
								<input type="submit" value="Submit" 
									class="topformbutton" />
							</div>


						</div>
					</html:form>
				</fieldset>
			</td>
		</tr>
	</table>

	<p align="center" id="footer">
		Copyright &copy; 2010-2010
		<a href="#"> Website Name.</a> Please read
		<a href="#">Terms Of Use here</a>.
	</p>
</body>
</html:html>