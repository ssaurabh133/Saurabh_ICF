<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
         <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	
     

<script type="text/javascript">


var names = new Array;
names[0]='a';
names[1]='c';
function activeTab(now){


for (z=0;z<names.length;z++)
{
	if(now == names[z])
	{
		document.getElementById(now).className='current';
		document.getElementById(now).setAttribute("onclick","return false");
	}
	else
	{
		document.getElementById(names[z]).className='';
		//document.getElementById(names[z]).removeAttribute("onclick","activeTab(id)");
		document.getElementById(names[z]).setAttribute("onclick","activeTab(id)");
	}

  }
  
}
function downloadDocument(sourcepath)
{
	alert("here i am");
	var sourcepath=document.getElementById("contextPath").value;
	alert(sourcepath);
	var url=sourcepath+"/DocumentDownloadBehindAction.do?method=downloadDocument";
				
				window.child =window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				anotherWindows[curWinAnother++]= window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes');

				 return true;
}
</script>
<base target="subBody" />
</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');" style="position: fixed; width:3000px;">
<form name="test" id="test">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>


<div class="tab-bg">
<div  class="ddcolortabs"><ul>

	
	<li><a href="<%=request.getContextPath()%>/underwritingDocUpload.do?method=documentUpload" id="a" onclick="activeTab(id);"><span >Upload document</span></a></li>


	 

<%-- 	 	 
	<li><logic:present name="paymentMaker"><a href="<%=request.getContextPath()%>/underwritingDocUpload.do?method=documentDownload" id="c" style="display:none" onclick="activeTab(id);"><span >download document</span></a>
	</logic:present>
	<logic:notPresent name="paymentMaker">
	<a href="<%=request.getContextPath()%>/underwritingDocUpload.do?method=documentDownload" id="c" onclick="activeTab(id);"><span >download document</span></a>
	 </logic:notPresent></li>
 --%>	 
	 
</body>
</html>