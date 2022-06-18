<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"%>
<%@ page session="false"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>

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
<!-- Rohit Changes for security Starts -->
<script type="text/javascript" src=" <%=request.getContextPath()%>/js/md5.js"></script> 
<script type="text/javascript" async="" src="<%=request.getContextPath()%>/js/ga.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/sha.js"></script>
<!-- Rohit Changes for security end -->
<script type="text/javascript">
function max()
{

var obj = new ActiveXObject("Wscript.shell");
obj.SendKeys("{F11}");
}
//Rohit Changes for security Starts.........
function getXMLHTTPObject() 
{
var xmlhttpObject = null;
try 
{
 // For Old Microsoft Browsers
 xmlhttpObject = new ActiveXObject("Msxml2.XMLHTTP"); 
}
catch (e) 
{
        try 
        {
        // For Microsoft IE 6.0+
        xmlhttpObject = new ActiveXObject("Microsoft.XMLHTTP"); 
        }
        catch (e1) 
        {
        // No Browser accepts the XMLHTTP Object then false
        xmlhttpObject = false; 
        }
}

if (!xmlhttpObject && typeof XMLHttpRequest != 'undefined') 
{
        //For Mozilla, Opera Browsers
        xmlhttpObject = new XMLHttpRequest(); 
}
        // Mandatory Statement returning the ajax object created
        return xmlhttpObject; 
}

        
// Change the value of the outputText field
//function setAjaxOutput() 
//{
//document.getElementById('userNameDiv').innerHTML = xmlhttpObject.responseText;
//}

function handleServerResponse() 
{
        if (xmlhttpObject.readyState == 4) 
        {
                if (xmlhttpObject.status == 200) 
                {
                document.write(xmlhttpObject.responseText) ;
				}
                else 
                {
                    alert("Error during AJAX call. Please try again");
                }
        }
}
        
// Implement business logic
function doAjaxCall() 
{
   xmlhttpObject = getXMLHTTPObject();
   if (xmlhttpObject != null)
	var name=document.getElementById("userName").value;
	if((document.getElementById("userName").value)=="")
		{
	alert("Please enter Username first");
	return false;
	
	}
   {
     var URL = "<%=request.getContextPath()%>/forgetLoginAction.do?method=getquestion&name="+ document.getElementById('userName').value;
     xmlhttpObject.open("POST", URL, true);
   xmlhttpObject.onreadystatechange = handleServerResponse;
   xmlhttpObject.send(null);
   }
}
<%-- function openquespopup(){
//Rohit Changes for security end.........
function openquespopup(){
var name=document.getElementById("userName").value;
	if((document.getElementById("userName").value)==""){
	alert("Please enter Username first");
	return false;
	
	}

	
		window.open("<%=request.getContextPath()%>/forgetLoginAction.do?method=getquestion&name="+name,"popid","scrollbars,resizable,width=400,height=250" );

}
--%>
function clearpass()
{
	 alert("Please do not use Remember Password feature in browser");
	//sleep(4000);
	document.getElementById("userPassword").value='';
}
function checkVersion()
{
 var browser = '';
 var browserVersion = 0;

if (/Opera[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    browser = 'Opera';
} else if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)) {
    browser = 'MSIE';
} else if (/Navigator[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    browser = 'Netscape';
} else if (/Chrome[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    browser = 'Chrome';
} else if (/Safari[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    browser = 'Safari';
    /Version[\/\s](\d+\.\d+)/.test(navigator.userAgent);
    browserVersion = new Number(RegExp.$1);
} else if (/Firefox[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    browser = 'Firefox';
}
if(browserVersion === 0){
    browserVersion = parseInt(new Number(RegExp.$1));
}
 // alert("Browser Name: "+browser + "\n Browser Name: " + browserVersion);
  if((browser=='MSIE' && browserVersion==8)||(browser=='Netscape' && browserVersion==17)||(browser=='Firefox' && browserVersion==17))
  {
  	return true;
  }
  else 
  {
    alert("OmniFin is not supporting "+browserVersion+" version of "+browser+" browser ");
    document.getElementById("userName").value='';
    document.getElementById("userPassword").value='';
  	self.close();
  	return false;
  }
    
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
 
<style type="text/css">
<!--
#Layer1 {
	position:absolute;
	width:503px;
	height:46px;
	z-index:10000000;
	top: 56px;
	left: 65px;
}
.style3 {font-size: 36px}
-->
</style>
</head>
<body  oncontextmenu="return false" marginwidth="0" marginheight="0" scroll="yes"  topmargin="0" leftmargin="0" >
<input type="hidden" name="randomAlphaNumericSaltFroPass" id='randomAlphaNumericSaltFroPass' value="${requestScope.randomAlphaNumericSaltFroPass}" /><!--  added by Rohit for security -->
<div class="maincontainer">
<div id="toprightdiv">

<div id="toprightdivbox">
</div>

</div>

<div style="height: 89px;">
<div style="width: 500px;">
<div style="float:left; width:50px; height:72px; padding:4px 0 0 8px;"><img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath()%>/images/theme1/Logo.png" width="46" height="68" /></div>
<div ondragstart="return false" onselectstart="return false" style="background: url(<%=request.getContextPath()%>/images/theme1/logoname.png) no-repeat 14px 14px;float:left; width:400px; height:90px;"></div>
</div>

<div style="float:right; padding:8px 6px 0 0;"><img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath()%>/images/theme1/headerLogo.jpg"></img></div>

  </div>
</div> 	
<div class="ddcolortabsline">&nbsp;</div>
<table width="105%" cellspacing="0" cellpadding="0" class="new-login-bg" style="padding-top:3%;">
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
<table width="100%" height="332" cellspacing="0" cellpadding="0" border="0" style="border-left: 1px solid #08499a; border-right: 1px solid #08499a;" class="new-login-mid-tile">

	<tbody><tr>

		<td width="12%"></td>

<td width="30%" align="center" valign="top" align="center" style="padding-top:4%">
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

        <td width="60%" align="center" valign="top" align="center" style="padding-top:4%">
	<html:form  action="/loginInput" styleId="loginForm" onsubmit='return validateUser();'>


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
											<td width="25%" align="right" class="new-login-text-user">User ID</td>
											<td width="2%">&nbsp;</td>
											<td width="10%"><html:text property="userName"  styleId="userName" style="width: 150px;"  onblur="fnCaseChange();clearpass();" /></td>
											<td width="5%"></td>
							  </tr>
										<tr>
											<td height="10" colspan="5"></td>

										</tr>
                                        <tr>
											<td width="5%"></td>
											<td width="20%" align="right" class="new-login-text-user">Password </td>
											<td width="2%">&nbsp;</td>
											<td width="10%"><html:password property="userPassword" styleId="userPassword" style="width: 150px"  onchange="calcMD5();" /></td><!-- Rohit Changes -->
											<td width="10%"></td>
										</tr>
										<tr>
											<td height="10" colspan="5"></td>

										</tr>
										
									<tr>
											<td height="10" colspan="5">  <a style="width: 166px;color: rgb(255, 255, 255); "  onclick="return doAjaxCall()" id="lnkForgot">Forgot Your Username/Password?</a></td><!-- Rohit Changes -->

							  </tr>
                                    <tr>
										<td width="5%"></td>
											<td width="20%" align="center" class="new-login-text-user"></td>
											<td width="2%"></td>
											<td align="left"><html:submit property="loginbutton"  styleClass="buttonss"   /></td>
									</tr>

						           <tr>
										<td valign="top" height="30" align="center" colspan="5">
											<span style="color: rgb(255, 0, 0);font-size:10px;" ><bean:message key="msg.noReminderPasswordMsg" /></span>
										</td>
									</tr>         
						                
							</tbody></table>
						</td>
						<td class="new-login-form-right"><img ondragstart="return false" onselectstart="return false" width="14" height="191" src="<%=request.getContextPath()%>/images/theme1/spacer.gif" /></td>
					</tr>
				</tbody></table>

</html:form>
<div align="center" class="newlogin-footer-normal">
 <logic:present name="aboutCompany">
	 Email: ${aboutCompany[0].emailId }<br />
	 Phone: ${aboutCompany[0].phoneNo }<br />
	 Licensed To: ${aboutCompany[0].compName }
 </logic:present>
 <logic:notPresent name="aboutCompany">
  <input type="hidden" name="randomAlphaNumericSaltFroPass" id='randomAlphaNumericSaltFroPass' value="${requestScope.randomAlphaNumericSaltFroPass}" />
 		Email: lms.support@a3spl.com<br/>
        Phone: 0120 4645614<br/>
        Licensed To: A S Softwares Services Pvt. Ltd.
 </logic:notPresent>               
 </div>
</td></tr>


<logic:present name="flag">

<logic:notEmpty name="flag">
 <script type="text/javascript">
 
if('<%=request.getAttribute("flag")%>'=='error')
 	{
	
 	if(('<%=request.getAttribute("flag")%>'=='error') && ('<%=request.getAttribute("flag1")%>'=='L')){
 	
 		alert("User Name or Password is incorrect.");
		alert("Your Account is locked. Please contact your administrator to unlock your account.");
		document.getElementById("userPassword").value="";
 		document.getElementById("userName").value="";
 		//Rohit Changes for Security Starts
 		  //<%-- document.getElementById('loginForm').action="<%=request.getContextPath()%>/loginInput.do"; --%>
		//document.getElementById('loginForm').submit();
 		//window.parent.window.close();
		window.location.replace("<%=request.getContextPath()%>/loadLMS.do");
		//end......
 	}
 	else{
 	
 	alert("User Name or Password is incorrect.");
 	document.getElementById("userPassword").value="";
 	document.getElementById("userName").value="";
 	//Rohit Changes for Security Starts
 	//<%-- document.getElementById('loginForm').action="<%=request.getContextPath()%>/loginInput.do"; --%>
	//document.getElementById('loginForm').submit();
 	//window.parent.window.close();
 	window.location.replace("<%=request.getContextPath()%>/loadLMS.do");
	//end.....
 	}
 }

 if('<%=request.getAttribute("flag")%>'=="EXP"){
 	
 		
		alert("Your Account is Expired Please contact your administrator ");
		document.getElementById("userPassword").value="";
 		document.getElementById("userName").value="";
	    //document.getElementById('loginForm').action="<%=request.getContextPath()%>/loginInput.do";
		//document.getElementById('loginForm').submit();
 	}
 	
if(('<%=request.getAttribute("flag")%>'== "PE") && ('<%=request.getAttribute("aflag")%>'== "AE"))
 	{
 	    alert("Your account will Expire after " + <%= request.getAttribute("dayLeft")%> +" days. Please contact your administrator.");
 	    alert("Your password will expire soon. Please change the password.");
		document.getElementById('loginForm').action="<%=request.getContextPath()%>/LoadMenuAction.do";
		document.getElementById('loginForm').submit();
 	}
 	else if('<%=request.getAttribute("flag")%>'== "PE"){
 		alert("Your password will expire soon. Please change the password.");
		document.getElementById('loginForm').action="<%=request.getContextPath()%>/LoadMenuAction.do";
		document.getElementById('loginForm').submit();
 	}

 
if('<%=request.getAttribute("flag")%>'=='L'){
 	
 		
		alert("Your Account is locked. Please contact your administrator to unlock your account.");
		document.getElementById("userPassword").value="";
 		document.getElementById("userName").value="";
	    //document.getElementById('loginForm').action="<%=request.getContextPath()%>/loginInput.do";
		//document.getElementById('loginForm').submit();
 	}

 	if('<%=request.getAttribute("flag")%>'=='userdonotexist'){
 		
 	alert("User Donot Exist.");
 	document.getElementById("userPassword").value="";
 	document.getElementById("userName").value="";
 	//document.getElementById('loginForm').action="<%=request.getContextPath()%>/loginInput.do";
	//document.getElementById('loginForm').submit();
	
 	}
	</script>
	</logic:notEmpty>
</logic:present>	
<logic:present name="aflag">
<logic:notPresent name="flag">
	<logic:notEmpty name="aflag">
	<script type="text/javascript">		 
	if('<%=request.getAttribute("aflag")%>'=='AE')
 	{
 		alert("Your account will Expire after " + <%= request.getAttribute("dayLeft")%> +" days. Please contact your administrator.");
 	    document.getElementById('loginForm').action="<%=request.getContextPath()%>/LoadMenuAction.do";
		document.getElementById('loginForm').submit();
	}	 
	 </script>
	</logic:notEmpty>
	</logic:notPresent>
</logic:present>

<tr><td height="15" colspan="5"></td></tr>

		
        <tr><td width="5%"></td>
	</tr>
 
</tbody></table>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody><tr>

			<td class="login-footer-left"><img ondragstart="return false" onselectstart="return false" width="13" height="30" src="<%=request.getContextPath()%>/images/theme1/spacer.gif" /></td>
			<td width="100%" align="center" class="login-footer-middle">
			 &copy; Copyright 2011  <a href="http://a3spl.com" target="_blank">
A S Software Services Pvt Ltd</a> | <a href="#">Terms &amp; Conditions</a>
   </td>
			<td class="login-footer-right"><img ondragstart="return false" onselectstart="return false" width="15" height="30" src="<%=request.getContextPath()%>/images/theme1/spacer.gif" /></td>
	</tr>
</tbody></table>

	</td>
    <td width="8%"></td>

</tr>
</tbody></table>
 
     
</body>
</html:html>