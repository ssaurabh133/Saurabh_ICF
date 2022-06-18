<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"%>
<%@ page session="true" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
   
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
    <!-- CSS for Tab Menu #1 -->

  

<style>
 <logic:present name="image">
    	   div.flower {background:url(<%=request.getContextPath()%>/${image }/logo.png) no-repeat; }
			div.rose {background:url(<%=request.getContextPath()%>/${image }/lo.png) no-repeat; height:79px; width:498px}
       </logic:present>
    	<logic:notPresent name="image">
    		div.flower {background:url(<%=request.getContextPath()%>/images/theme1/logo.png) no-repeat; }
			div.rose {background:url(<%=request.getContextPath()%>/images/theme1/lo.png) no-repeat; height:79px; width:498px}
    	</logic:notPresent>

#Layer1 {	position:absolute;
	width:503px;
	height:46px;
	z-index:10000000;
	top: 48px;
	left: 123px;
}
.maincontainer .logo
{
height:80px;
width:111px;
padding: 1px;
float:left;
}

.welcome
{
height:15px;
}

.maincontainer .style4 {font-size: 36px;width:930px;float:left;height:80px;}

.style5 {color: #000033}


.ttip
{ BORDER-BOTTOM: #00cccc 1px dotted;
  color: red;
  FONT-SIZE: 18px;}
</style>
<!--[if gte IE 5]>
<style type="text/css">
div.flower {
background:none;
filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/logo.png' ,sizingMethod='crop');
}
div.rose {
background:none;
filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/lo.png' ,sizingMethod='crop');
}
</style>
<![endif]-->	

<script type="text/javascript">

function toHome()
{

       parent.location='<%=request.getContextPath()%>/JSP/home.jsp';
     
}


function openURL()
			{
		var url="<%=request.getContextPath()%>/logindata.do?method=getbranch";
					
        window.open("<%=request.getContextPath()%>/logindata.do?method=getbranch","popid","scrollbars,resizable,width=250,height=250" );
	return false;
			}
 

</script> 
<script type="text/javascript">
		 
		 	function changeTheme1(val)
		 	{
		 		//alert(val);
		 		var v=val;
		 		location.href="<%=request.getContextPath() %>/theme.do?val="+v;
		 	   		
		 	}
		 	function changeTheme2(val)
		 	{
		 		//alert(val);
		 		var v=val;
		 		location.href="<%=request.getContextPath() %>/theme.do?val="+v;
		 	   		
		 	}
		 	function changeTheme3(val)
		 	{
		 		//alert(val);
		 		var v=val;
		 		location.href="<%=request.getContextPath() %>/theme.do?val="+v;
		 	   		
		 	}
		 	
		 </script>
</head>
<body onload="enableAnchor();" onclick="parent_disable();" oncontextmenu="return false">

<form action="" method="post" name="headerdetailsForm" id="headerdetailsForm">
<div class="maincontainer">
<div style="height: 97px;">
<table width="100%">
<tr><td>
<div style="width: 500px; float:left;">
<div style="float:left; width:50px; height:72px; padding:4px 0 0 8px;">

        <logic:present name="image">
    	   <img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath()%>/${image }/Logo.png" height="68" width="46">
       </logic:present>
    	<logic:notPresent name="image">
    		<img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath() %>/images/theme1/Logo.png"></img>
    	</logic:notPresent>


</div>
 <logic:present name="image">
    	  <div ondragstart="return false" onselectstart="return false" style=" float:left; width:400px;height:90px; background:url(<%=request.getContextPath()%>/${image }/logoname.png) 14px 14px no-repeat;"></div>
       </logic:present>
    	<logic:notPresent name="image">
    		  <div ondragstart="return false" onselectstart="return false" style=" float:left; width:400px;height:90px; background:url(<%=request.getContextPath()%>/images/theme1/logoname.png) 14px 14px no-repeat;"></div>
    	</logic:notPresent>

</div>
</td>
<td>
  <div class="userinfo">
  <logic:present name="userobject" scope="session">
  <table width="461" height="97" cellpadding="0" cellspacing="0" border="0">
  <tr>
  <td>
  <table width="151" cellpadding="0" cellspacing="0" border="0" style="padding:0 30px 0 0;">
  <tr>
  <td style="padding:8px 0 0 0;"><img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath()%>/images/blue.png" onclick="changeTheme1('Blue');"></img></td>
  <td><img src="<%=request.getContextPath()%>/images/brown.png" ondragstart="return false" onselectstart="return false" onclick="changeTheme2('Brown');"></img></td>
  </tr>
  <tr>
  <td colspan="2" style="padding:2px 0 0 40px;"><img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath()%>/images/green.png" onclick="changeTheme3('Green');"></img></td>
  </tr>
  </table>
  </td>
  
  <td>
  <table width="350" height="97" cellpadding="0" cellspacing="0" border="0">
      
  <tr>
  	<td style="padding-left:21px; padding-bottom: 5px;">
     <ul>
      <li><a href="<%=request.getContextPath()%>/JSP/home.jsp" onclick="toHome();">Home</a></li>
      <%-- <li class="gap"><html:link forward="logoff" target="_parent">Logout</html:link></li> --%><!-- Rohit Changes for security --> 
     <li class="gap"><a href="#" onclick="return toLogoutHome('logout');">Logout</a></li><!-- Rohit Changes for security -->
     </ul>
    </td>
  </tr>
  <tr>
  <td valign="bottom">
  <table width="100%" height="22" cellpadding="0" cellspacing="0" border="0">

  
  <tr>
    <td width="32" style="padding-right:7px">
     <logic:present name="image">
    	 <img src="<%=request.getContextPath()%>/${image }/usericon.png" ondragstart="return false" onselectstart="return false" width="24" height="22" alt="usericon" />
       </logic:present>
    	<logic:notPresent name="image">
    		<img src="<%=request.getContextPath()%>/images/theme1/usericon.png" ondragstart="return false" onselectstart="return false" width="24" height="22" alt="usericon" />
    	</logic:notPresent>
    </td>
    <td nowrap="nowrap" width="100%" style="padding-left:1px"><strong>Welcome !&nbsp;</strong>
    	<bean:write name="userobject" property="userName"/>
    </td>    
  </tr>  

  </table>
  </td>

  </tr>
  
  <tr>
  <td valign="top" class="align">
   <table width="100%" cellpadding="0" cellspacing="0" border="0">
   <tr>
   <td class="widthcon"><strong>Last Log in</strong></td>
   <td><strong>:</strong></td>
   <td><bean:write name="userobject" property="lastLoginTime"/></td>
   </tr>
   <tr>
   <td class="widthcon"><strong>Business Date</strong></td>
   <td><strong>:</strong></td>
   <td><bean:write name="userobject" property="businessdate"/></td>
   </tr>
   </table>
  </td>
  </tr>
  
  <tr>
  <td valign="bottom" class="align">
  <table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
  <td class="widthcon"><strong>Branch </strong></td>
  <td><strong>:</strong></td>
  <input type="hidden"  name="branchName" id="branchName" />
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
      <input type="hidden"  name="lbxBranchId" id="lbxBranchId" />
      <input type="hidden"  name="userId" id="userId" value="<bean:write name="userobject" property="userId"/>"/>
      
  <td><div style="float:left;" id="branch"><bean:write name="userobject" property="branchName"/></div><div style="float:left;"><span class="lovalign">
  
  
   <logic:present name="image">
    	 <img ondragstart="return false" onselectstart="return false" onclick="openLOVCommon(148,'headerdetailsForm','branchName','userId','lbxBranchId', 'userId','','setBranchId','lbxBranchId')" src="<%= request.getContextPath()%>/${image }/lov.jpg" width="13" height="13" alt="lov" align="absmiddle"  />
       </logic:present>
    	<logic:notPresent name="image">
    		<img ondragstart="return false" onselectstart="return false" onclick="openLOVCommon(148,'headerdetailsForm','branchName','userId','lbxBranchId', 'userId','','setBranchId','lbxBranchId')" src="<%= request.getContextPath()%>/images/theme1/lov.jpg" width="13" height="13" alt="lov" align="absmiddle"  />
    	</logic:notPresent>
  
 </span></div></td>
  </tr>
  </table>
  </td> 
  </tr>
  </table>
  </tr> 
  </table> 
  </logic:present>
</div>
</td>
</tr>
</table>  
  </div>

</div>

<div class="ddcolortabsline"></div>



</form>

<%-- 
<script type="text/javascript">	
openpopupDiv();
function openpopupDiv(){
var timLimit = '<%= session.getMaxInactiveInterval()%>';
	window.open('http://www.a3spl.com','mywin', 'left=20,top=20,width=500,height=500,toolbar=1,resizable=0');
	setTimeout(openpopupDiv,3000);
}

</script>
--%>
 </body>
</html>