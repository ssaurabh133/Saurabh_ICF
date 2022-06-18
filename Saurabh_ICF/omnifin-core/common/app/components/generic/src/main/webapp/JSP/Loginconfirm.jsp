<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"%>
<%@ page session="false"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html:html>
<head>

     <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	 <meta http-equiv="pragma" content="no-cache" />
	 <meta http-equiv="cache-control" content="no-cache" />
	 <meta http-equiv="expires" content="0" />
	 <meta name="author" content="" />
	 <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/titleLogo.png" /> 
<%
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Expires","0");
response.setDateHeader("Expires",0); 
%>
<title><bean:message key="title.name"/></title>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/login.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/contentstyle.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/subpage.css"/>
<link href="<%=request.getContextPath()%>/ddtabmenufiles/theme1/ddcolortabs.css" type="text/css" rel="stylesheet"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/mainstyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>

     <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	 <meta http-equiv="pragma" content="no-cache" />
	 <meta http-equiv="cache-control" content="no-cache" />
	 <meta http-equiv="expires" content="0" />
	 <meta name="author" content="" />

<title><bean:message key="title.name"/></title>
  <script type="text/javascript">
  function loginconfirm(){
 var flag="logininput";
 if(flag!=null){
 	var logval=confirm("Your Last Session was not Properly Terminated. Do you wish to Login Again ?");
 if (logval){
document.getElementById('flag').value=flag;

  document.getElementById('loginConfirm').action="loginInput.do";
  document.getElementById('loginConfirm').submit();

 }else{
  document.getElementById('loginConfirm').action="<%=request.getContextPath()%>/JSP/Login.jsp";
  document.getElementById('loginConfirm').submit();
 }}
	}
	
	 //By Richa starts
 document.onkeydown=function(e) {
    var event = window.event || e;
    if (event.keyCode == 80 && e.ctrlKey) {
        event.keyCode = 0;
        return false;
    }
}
//by richa ends
</script> 

  </head>
  
 
  <body  onload="loginconfirm();" marginwidth="0" marginheight="0" background="<%=request.getContextPath()%>/images/theme1/bg_loginpage.gif" scroll="yes"  topmargin="0" leftmargin="0" >

<div class="maincontainer">
<div id="toprightdiv">

<div id="toprightdivbox">




</div>

</div>

<div style="height: 79px;">
<div style="width: 500px;">
<div style="float:left; width:50px; height:72px; padding:4px 0 0 8px;"><img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath()%>/images/theme1/Logo.png" width="46" height="68" /></div>
<div ondragstart="return false" onselectstart="return false" style="background: url(<%=request.getContextPath()%>/images/theme1/logoname.png) no-repeat 14px 14px;float:left; width:400px; height:90px;"></div>
</div>

<div style="float:right; padding:8px 6px 0 0;"><img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath()%>/images/theme1/headerLogo.jpg"></img></div>

  </div>
</div> 	
<div class="ddcolortabsline">&nbsp;</div>




</div>



<table width="105%" cellspacing="0" cellpadding="0" class="new-login-bg">
<tbody><tr>

	<td width="8%"></td>
	<td width="64%" valign="top">

	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody><tr><td height="20" colspan="3"></td></tr>
		<tr>
			<td class="new-login-tp-lft"></td>
			<td valign="top" class="new-login-tp-mid">
				<table width="100%" cellspacing="0" cellpadding="0" border="0">

					<tbody><tr>
					<td valign="top" height="31" align="center" colspan="5">
					<table width="100%" height="31" cellspacing="0" cellpadding="0" border="0" align="center">
					
					<tbody><tr>

					<td width="50%" height="14"></td>

					<td height="14" align="center" colspan="4">
					</td>
					</tr>

					</tbody></table>
					</td>
					</tr>
					</tbody></table>
			  </td>
					<td class="new-login-tp-rig">&nbsp;</td>
			</tr>
		  </tbody></table>
<table width="100%" height="332" cellspacing="0" cellpadding="0" border="0" style="border-left: 1px solid rgb(70, 142, 213); border-right: 1px solid rgb(70, 142, 213);" class="new-login-mid-tile">

	<tbody><tr>

		<td width="12%"></td>

<td width="30%" align="center">
<table cellspacing="0" cellpadding="0" border="0">
<tbody><tr>
	<td><img ondragstart="return false" onselectstart="return false" border="0" src="<%=request.getContextPath()%>/images/theme1/banner5.jpg" /></td>
</tr>
<tr>
	<td height="10"></td>
</tr>

</tbody></table>


	</td>


		<td width="5%"></td>

        <td width="60%" align="center">
  <html:form  action="/loginInput" styleId="loginConfirm" method="post" >
  				<table width="90%" cellspacing="0" cellpadding="0" border="0">

	
<tbody>

                        <tr><td align="center" colspan="5">
                        
                        </td></tr>




                        <tr>
                        <td class="new-login-form-lft"><img ondragstart="return false" onselectstart="return false" width="16" height="191" src="<%=request.getContextPath()%>/images/theme1/spacer.gif" /></td>
                        <td width="93%" valign="top" align="center" class="new-login-form-middle">
                        <table width="90%" cellspacing="0" cellpadding="0" align="left">


                        <tbody><tr>
                          <td height="15" align="center" colspan="5"><font color="#FF0000"><b>${requestScope.errorlogin}</b></font> 


                        
                        

                        </td>
                        </tr>

<tr>
                              <td valign="top" height="30" align="center" colspan="5">

                                <input type="hidden" value="html" name="clienttype2" />
                                &nbsp;&nbsp; <span class="new-login-text-user">
													User Authentication
													
                                </span></td>
                            </tr>
									<tr>

											<td width="5%"></td>
											<td width="25%" align="right" class="new-login-text-user">Username</td>
											<td width="2%">&nbsp;</td>
											<td width="10%"><html:text property="userName"  styleId="userName" style="width: 150px;" onblur="return fnCaseChange();" /></td>
											<td width="5%"></td>
							  </tr>
										<tr>
											<td height="10" colspan="5"></td>

										</tr>
                                        <tr>
											<td width="5%"></td>
											<td width="20%" align="right" class="new-login-text-user">Password </td>
											<td width="2%">&nbsp;</td>
											<td width="10%"><html:password property="userPassword" styleId="userPassword" style="width: 150px;" /></td>
											<td width="10%"></td>
										</tr>
										<tr>
											<td height="10" colspan="5"></td>

										</tr>
										
									<tr>
											<td height="10" colspan="5">  <a style="width: 166px;color: rgb(255, 255, 255); "  onclick="return openquespopup()" id="lnkForgot">Forgot Your Username/Password?</a></td>

							  </tr>
                                    <tr>
										<td width="5%"></td>
											<td width="20%" align="center" class="new-login-text-user"></td>
											<td width="2%"></td>
											<td align="left"><html:submit property="loginbutton"  styleClass="buttonss"   /></td>

											
						                
							  </tr>






							</tbody></table>
						</td>
						<td class="new-login-form-right"><img ondragstart="return false" onselectstart="return false" width="14" height="191" src="<%=request.getContextPath()%>/theme1/images/spacer.gif" /></td>
					</tr>
				</tbody></table>
   <input type="hidden" name="userName" id="userName11" value="<%=request.getAttribute("userName") %>"/>
<input type="hidden" name="flag" id="flag" />
<input type="hidden" name="userPassword" id="userPassword" style="width: 150px;" value="<%=request.getAttribute("userPassword") %>" />
 <logic:present name="flag">
 <script type="text/javascript">
 	
if(('<%=request.getAttribute("flag")%>'=='PE') && ('<%=request.getAttribute("aflag")%>'== "AE"))
 	{
 		alert("Your account will Expire after " + <%= request.getAttribute("dayLeft")%> +" days. Please contact your administrator.");
 	   	alert("Please change your password.");
		document.getElementById('loginForm').action="<%=request.getContextPath()%>/LoadMenuAction.do";
		document.getElementById('loginForm').submit();
 	}
 	else if('<%=request.getAttribute("flag")%>'=='PE'){
 		alert("Please change your password.");
		document.getElementById('loginForm').action="<%=request.getContextPath()%>/LoadMenuAction.do";
		document.getElementById('loginForm').submit();
 	}


 	
	</script>
</logic:present>
 <logic:present name="aflag">
 <logic:notPresent name="flag">
 <logic:empty name="flage">
 <script type="text/javascript">
 if(('<%=request.getAttribute("aflag")%>')== "AE")
 	{	
 	    alert("Your account will Expire after " + <%= request.getAttribute("dayLeft")%> +" days. Please contact your administrator.");
		
		
 	}
 	</script>
 	</logic:empty>
 	</logic:notPresent>
 </logic:present>
 </html:form></td></tr>

<tr><td height="15" colspan="5"></td></tr>

		
        <tr><td width="5%"></td>
	</tr>
 
</tbody></table>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody><tr>

			<td class="login-footer-left"><img ondragstart="return false" onselectstart="return false" width="13" height="30" src="<%=request.getContextPath()%>/images/theme1/spacer.gif" /></td>
			<td width="100%" align="center" class="login-footer-middle">
   </td>
			<td class="login-footer-right"><img ondragstart="return false" onselectstart="return false" width="15" height="30" src="<%=request.getContextPath()%>/images/theme1/spacer.gif" /></td>
	</tr>
</tbody></table>

 <div align="center" class="newlogin-footer-normal"><img ondragstart="return false" onselectstart="return false" border="0" align="absmiddle" src="<%=request.getContextPath()%>/images/theme1/copyright.gif" name="copy" />
            
2010 <a href="#" style="color:#366DB1;">
 A S Software Services Pvt Ltd </a><br/>
  <logic:present name="aboutCompany">
	 Email: ${aboutCompany[0].emailId }<br />
	 Phone: ${aboutCompany[0].phoneNo }<br />
	 Licensed To: ${aboutCompany[0].compName }
 </logic:present>
 <logic:notPresent name="aboutCompany">
 		Email: lms.support@a3spl.com<br/>
        Phone: 0120 4645614<br/>
        Licensed To: A S Softwares Services Pvt. Ltd.
 </logic:notPresent> 
 
 </div>

	</td>
    <td width="8%"></td>

</tr>
</tbody></table>
 
              
</body>
</html:html>