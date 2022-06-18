
<!-- 
Author Name :- Manisha Tomar
Date of Creation :15-04-2011
Purpose :-  screen for Labels used or Loan Initiation
-->
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
names[1]='b';
names[2]='c';
names[3]='d';
names[4]='e';
names[5]='f';
names[6]='g';
names[7]='h';
names[8]='j';
names[9]='k';
names[10]='l';
names[11]='m';
names[12]='n';
names[13]='o';
names[13]='q';


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
<base target="body" />
</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');" style="position: fixed; width:3000px;">
<form name="test" id="test">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>

<logic:notPresent name="searchLoan">
<div class="tab-bg">
<div  class="ddcolortabs"><ul>
<%-- <li><a href="<%=request.getContextPath() %>/JSP/CMJSP/CMS_frames_SubLoanHeader.jsp" 					    id="a" onclick="activeTab(id);"><span ><bean:message key="lbl.loanDetails" /> </span></a></li> --%>
<li><a href="<%=request.getContextPath()%>/loanDetailInCMBehindAction.do?method=openTabLoanDetailBehind" id="a" onclick="activeTab(id);"><span ><bean:message key="lbl.loanDetails" /> </span></a></li>
<li><a href="<%=request.getContextPath()%>/docsCollectionInCMBehindAction.do" 								id="b" onclick="activeTab(id);"><span ><bean:message key="lbl.docsCollection" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/viewSpecialConditionDisb.do?method=viewSpecialCondition&loanId=" id="c" onclick="activeTab(id);"><span ><bean:message key="lbl.specialCondition" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/noOfDisbInCMBehind.do" 											id="d" onclick="activeTab(id);"><span ><bean:message key="lbl.noOfDisb" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/notePadPageInCMBehind.do?typ=np" 								id="e" onclick="activeTab(id);"><span ><bean:message key="lbl.notePad" /></span></a></li>
<logic:present name="cmAuthor">
	<logic:notPresent name="viewLoan">
	<logic:notPresent name="viewDeviation">
	<li><a href="<%=request.getContextPath()%>/aproveLoanInit.do" 											id="f" onclick="activeTab(id);"><span ><bean:message key="lbl.approve" /></span></a></li>
	</logic:notPresent>
	</logic:notPresent>
</logic:present>
<li><a href="<%=request.getContextPath()%>/showCustomerDetailBehindAction.do" 								id="g" onclick="activeTab(id);"><span ><bean:message key="lbl.custDetails" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/loanDeviationBehindAction.do" 									id="h" onclick="activeTab(id);"><span ><bean:message key="lbl.deviationTab" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/termSheetDispatchAction.do?method=openTermSheetCM" 				id="j" onclick="activeTab(id);" ><span >Term Sheet</span></a></li>

<li><a href="<%=request.getContextPath()%>/documentUploadAction.do" 										id="q" onclick="activeTab(id);"><span ><bean:message key="lbl.docUpload" /></span></a></li>
<logic:present name="mis">
<li><a href="<%=request.getContextPath()%>/misAction.do" 													id="m" onclick="activeTab(id);"><span ><bean:message key="lbl.mis" /></span></a></li>
</logic:present>




<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>
<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>
<li><input type="hidden" name="getFormName" id="getFormName"/></li>




</div>
<div class="ddcolortabsline"></div>
<div id="tabs">
</div>
</div>
</logic:notPresent>


<logic:present name="searchLoan">

<div class="tab-bg">
<div  class="ddcolortabs"><ul>
<%-- <li><a href="<%=request.getContextPath() %>/JSP/CMJSP/CMS_frames_SubLoanHeader.jsp"   							id="a" onclick="activeTab('a');"><span ><bean:message key="lbl.loanDetails" /> </span></a></li> --%>
<li><a href="<%=request.getContextPath()%>/loanDetailInCMBehindAction.do?method=loanDetailBehind" id="a" onclick="activeTab(id);"><span ><bean:message key="lbl.loanDetails" /> </span></a></li>
<li><a href="<%=request.getContextPath()%>/docsCollectionInCMBehindAction.do" 					 			    id="b" onclick="activeTab(id);"><span ><bean:message key="lbl.docsCollection" /></span></a></li> 
<li><a href="<%=request.getContextPath()%>/viewSpecialConditionDisb.do?method=viewSpecialCondition&loanId=" 	id="c" onclick="activeTab(id);"><span ><bean:message key="lbl.specialCondition" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/noOfDisbInCMBehind.do" 												id="d" onclick="activeTab(id);"><span ><bean:message key="lbl.noOfDisb" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/notePadPageInCMBehind.do?typ=np" 									id="e" onclick="activeTab(id);"><span ><bean:message key="lbl.notePad" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/showCustomerDetailBehindAction.do" 									id="f" onclick="activeTab(id);"><span ><bean:message key="lbl.custDetails" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/loanDeviationBehindAction.do" 										id="g" onclick="activeTab(id);"><span ><bean:message key="lbl.deviationTab" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/termSheetDispatchAction.do?method=openTermSheetCM" 					id="h" onclick="activeTab(id);" ><span >Term Sheet</span></a></li>
<li><a href="<%=request.getContextPath()%>/documentUploadAction.do" 											id="j" onclick="activeTab(id);"><span ><bean:message key="lbl.docUpload" /></span></a></li>
<logic:present name="mis">
<li><a href="<%=request.getContextPath()%>/misAction.do" 														id="m" onclick="activeTab(id);"><span ><bean:message key="lbl.mis" /></span></a></li>
</logic:present> 
 
<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>
<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>
<li><input type="hidden" name="getFormName" id="getFormName"/></li> 
</ul>
</div>
<div class="ddcolortabsline"></div>
<div id="tabs">
</div>
</div>
	
</logic:present>



</form>


</body>

</html>