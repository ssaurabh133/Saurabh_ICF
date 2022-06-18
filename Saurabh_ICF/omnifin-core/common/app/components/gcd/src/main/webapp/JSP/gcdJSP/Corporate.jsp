<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Corporate Details
 	Documentation    :- 
 -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
     <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/contentstyle.css"/>
	 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/subpage.css"/>
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
    <!-- CSS for Tab Menu #1 -->
<link href="<%=request.getContextPath() %>/ddtabmenufiles/ddtabmenu.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #2 -->
<link href="<%=request.getContextPath() %>/ddtabmenufiles/glowtabs.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #3 -->
<link href="<%=request.getContextPath() %>/ddtabmenufiles/solidblocksmenu.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #4 -->
<link href="<%=request.getContextPath() %>/ddtabmenufiles/ddcolortabs.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #5 -->

<link href="<%=request.getContextPath() %>/ddtabmenufiles/chromemenu.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
 
 <script type="text/javascript">
 function pageOnload(){
 alert("on pageload()");
          <%
			String contentPage = (String)request.getAttribute("contentPage");
		    
			if(contentPage == null || contentPage.equals(""))
				contentPage = "CORPORATEDETAIL";
           %>
		var content_page = '<%=contentPage%>';
		alert("content page------- " + content_page);
		if(content_page == 'ADDRESS') {
			//alert("include ASEET");
			//sendAjaxRequest('GliAsset.jsp','tabs-1');
			sendAjaxRequest('../../gliAssetAC.do?method=displayGliAsset','tabs-1');
		} else if(content_page == 'STACK') {
			//alert("include POLICY"); 
			sendAjaxRequest('','tabs-1');
		} else if(content_page == 'CREDIT') {
			//alert("include POLICY"); 
			sendAjaxRequest('GliPolicy.jsp','tabs-1');
		}  else {
	     	//alert("include Corporate---");
			sendAjaxRequest('corporateDetailAction.do?method=displayCorporateDetails','tabs');
		}
		displayErrorMsg();
 }
    var http_request = false;
	var ajaxTargetDiv ="";
function populateXmlHttpRequest() {
		if (window.XMLHttpRequest) { 
		   http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {
				
			}
	   }else if (window.ActiveXObject) { 
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					http_request = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
				}
			}
		}
		
		if (!http_request) {
			alert('Giving up :( Cannot create an XMLHTTP instance');
			return false;
		}
	}
	function sendAjaxRequest(url, divId) {
		
		var subs_id = document.getElementById("hidSubscriberId").value;
		var asset_id = document.getElementById("hidAssetId").value;
			
		if(url.substring(0,16) == "../../gliAssetAC")
			url = url + "&subs_id=" + subs_id;
		else if(url.substring(0,16) == "../../gliPolicyAC")
			url = url + "&asset_id=" + asset_id;
		
		if(url.substring(0,16) == "../../gliAssetAC" && subs_id == "") {
			alert("Please select a Subscriber first.");
		}
		else if(url.substring(0,17) == "../../gliPolicyAC" && asset_id == "") {
			alert("Please select an Asset first.");
		}
		else {
			//var url = "GliSubscriber.jsp";
			ajaxTargetDiv =divId;
		  alert("URL : " + url + " -------- div :" + divId);
			populateXmlHttpRequest();
			
			//alert(http_request);
			http_request.onreadystatechange = displayGliSubsFormDiv;
			http_request.open('GET', url, true);
			http_request.send(null);
		}
	}
	
 	function displayGliSubsFormDiv() {
			
		if (http_request.readyState == 4) {
			if (http_request.status == 200) {
				
			alert(" ........ " + ajaxTargetDiv + " ---- " + http_request.responseText);
			document.getElementById(ajaxTargetDiv).innerHTML = http_request.responseText;
				
				
			} else {
				alert('There was a problem with the request.' + http_request.status
						+ http_request.readyState);
			}
		}
	}
 
 function displayErrorMsg() {
		<%
			String errorMsg = (String)request.getAttribute("errorMsg");
			
			if(errorMsg == null)
				errorMsg = "";
			System.out.println("---" + errorMsg);
			
		%>
		var error_msg = '<%=errorMsg%>';
		
		if(error_msg.length > 0) {
			alert("An error has occured.");
		}
		
	}
	window.onload = pageOnload;
 </script>
  </head>
  
  <body onload="enableAnchor();pageOnload();init_fields();">
  <div class="maincontainer">



<div id="ddtabs4" class="ddcolortabs"><ul>

<li><a id="gliSubsA" href="#tabs-1"  target="body"	onclick="sendAjaxRequest('corporateDetailAction.do?method=displayCorporateDetails','tabs');"><span style="color: rgb(255, 255, 255);"><bean:message key="corporate.details" /></span></a></li>
<li><a href="#tabs-2" target="body" onclick="sendAjaxRequest('corporateAddressAction.do?method=displayCorporateAddress','tabs');"><span style="color: rgb(255, 255, 255);"><bean:message key="corporateAddress.details" /></span></a></li>
<li><a href="CorporateStakeHolderDetail.htm" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="stake.detail" /></span></a></li>
<li><a href="CorporateCreditRating.htm" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="credit.rating" /></span></a></li>
</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">

</div>
 <input type='hidden' id='hidSubscriberId' />
	<input type='hidden' id='hidAssetId' />
  </div>
  </body>
</html>
