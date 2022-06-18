<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
    
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
    

<script type="text/javascript">
var names = new Array;
names[0]='a';
names[1]='b';
names[2]='c';
names[3]='d';
names[4]='e';
names[5]='f';
names[6]='g';
names[7]='h';
names[8]='i';

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
</script>
<base target="document" />

</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');">
<div class="tab-bg">
<logic:equal name="recStatus" value="A">

<div id="ddtabs4" class="ddcolortabs"><ul>

<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=applicationDocs&entityType=APPL" id="a" onclick="activeTab(id);"><span><bean:message key="lbl.applicationDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=applicantDocs&entityType=PRAPPL" id="b" onclick="activeTab(id);"><span><bean:message key="lbl.applicantDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=coApplicantDocs&entityType=COAPPL" id="c" onclick="activeTab(id);"><span><bean:message key="lbl.coApplDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=guarantorDocs&entityType=GUARANTOR" id="d" onclick="activeTab(id);"><span><bean:message key="lbl.guarrantorDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=collateralDocs&entityType=COLLATERAL" id="e" onclick="activeTab(id);"><span><bean:message key="lbl.collateralDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=assetDocs&entityType=ASSET" id="f" onclick="activeTab(id);"><span><bean:message key="lbl.assetDocs" /></span></a></li>
</ul>
</div>
</logic:equal>



<logic:equal name="recStatus" value="F">
<div id="ddtabs4" class="ddcolortabs"><ul>

<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=applicationDocs&entityType=APPL" id="a" onclick="activeTab(id);"><span><bean:message key="lbl.applicationDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=applicantDocs&entityType=PRAPPL" id="b" onclick="activeTab(id);"><span><bean:message key="lbl.applicantDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=coApplicantDocs&entityType=COAPPL" id="c" onclick="activeTab(id);"><span><bean:message key="lbl.coApplDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=guarantorDocs&entityType=GUARANTOR" id="d" onclick="activeTab(id);"><span><bean:message key="lbl.guarrantorDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=collateralDocs&entityType=COLLATERAL" id="e" onclick="activeTab(id);"><span><bean:message key="lbl.collateralDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=assetDocs&entityType=ASSET" id="f" onclick="activeTab(id);"><span><bean:message key="lbl.assetDocs" /></span></a></li>
<li><a href="<%=request.getContextPath() %>/documentBehindActionPOD.do?method=postDisbursalDocumentAuthor" id="g" onclick="activeTab(id);"><span><bean:message key="lbl.docAuthor" /></span></a></li>
</ul>
</div>
</logic:equal>

<div class="ddcolortabsline"></div>

<div id="tabs">
</div>




	
</div>

</body>
</html>