<!--Author Name : Vinod Kumar Gupta-->
<!--Date of Creation : 21-Apr-2013-->
<!--Purpose  : Information of Add Stage Court Processing-->
<!--Documentation : -->

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
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalCourtProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('legalCourtProcessingMakerForm').caseTypeCode.focus();
   return true;
}
$(function() {
		$("#dateOfHearing").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1900:+10',
            showOn: 'both',
				<logic:present name="image">
  	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
          </logic:present>
  		<logic:notPresent name="image">
  			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
   		</logic:notPresent>
				buttonImageOnly: true,
				dateFormat:"<bean:message key="lbl.dateFormat"/>",
				defaultDate:'${userobject.businessdate }'
})
});

</script>
<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->

</head>

<body onload="fntab();init_fields();">
<html:form styleId="legalCourtProcessingMakerForm"  method="post"  action="/legalCourtProcessingMakerDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.legalStageDetail" /></legend>

<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
				
				<td ><b><bean:message key="lbl.stageName" /></b></td>
				<td ><b><bean:message key="lbl.caseNo" /></b></td>
				<td ><b><bean:message key="lbl.remarks" /></b></td>
				<td ><b><bean:message key="lbl.hearingDate" /></b></td>
				<td ><b><bean:message key="lbl.poa" /></b></td>

    			 
      		</tr>
      	
      		 <tr class="white1" style="width: 25px;">
      		 		
      		 
      		  
      		  <td><%=request.getAttribute("stageDesc")%>
      		  <input type="hidden" value="<%=request.getAttribute("stageDesc")%>" name="stageDesc" />
      		  <input type="hidden" value="<%=request.getAttribute("stageCode")%>" name="lbxStage" />
      		  </td>
      		  
      		  <td><input type="text" value="<%=request.getAttribute("caseNO")%>" id="caseNO" name = "caseNo" readonly="readonly" /></td>		
      		  
      		  <td><textarea rows="3" cols="5" name="courtProcessingMakerRemarks" id = "courtProcessingMakerRemarks"></textarea></td>
      		  
      		  <td>
      		  <input type="text" name="dateOfHearing" id="dateOfHearing" onchange="compareTwoDates('formatD','hearingDate','dateOfHearing','Next Hearing should be greater than Previous Hearing Date.');" />
      		  <input type="hidden" name="hearingDate" id="hearingDate" value="<%=request.getAttribute("preHearingDate")%>" />
      		  </td>
      		  	
      		  <td>
      		  <input type="text" class="text" id="poaDesc" name="poaDesc" readonly="readonly" />
      		  <input type="hidden" name="lbxPoa" id="lbxPoa"  />
      		  <input type="button" id="poaButton" class="lovbutton" onclick="closeLovCallonLov1();openLOVCommon(1529,'legalDeclineDispatchNoticeForm','poaDesc','','','','','','lbxPoa');"  />
      		  </td>
      		  
      		  
      		 
      		
      		  
      		  
			 </tr>
      		
      		
    		</table>
    </tr>
    
    
    
    <tr><td>
    
    
       
    <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
   <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
    
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveAddStageDetail();" class="topformbutton2" ><bean:message key="button.save" /></button>
   
  
    <br></td> </tr>

	</table>
  		


</fieldset>
   			  <input type="hidden" value="<%=request.getAttribute("advocate")%>" name="advocate" />
      		  <input type="hidden" value="<%=request.getAttribute("legalId")%>" name="legalId" id = "legalId" />
      		  <input type="hidden" value="<%=request.getAttribute("loanId")%>" id="loanId" name = "loanId" />
      		   <input type="hidden" value="<%=request.getAttribute("lbxCaseTypeCode")%>" id="lbxCaseTypeCode" name = "lbxCaseTypeCode" />
      		   <input type="hidden" value="<%=request.getAttribute("makerAuthorFlag")%>" id="makerAuthorFlag" name = "makerAuthorFlag" />
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.CourtProccessingSucc" />");
		
		var legalId = <%=request.getAttribute("legalId")%>;//legalId is a number
		var caseType = '<%=request.getAttribute("caseType").toString()%>'; 
		
		if('<%=request.getAttribute("makerAuthorFlag").toString()%>'=='A')
		{
			window.opener.location.href="<%=request.getContextPath()%>/legalCourtProcessingAuthorDispatch.do?method=makerScreen";
		}
		else
		{
		 	window.opener.location.href="<%=request.getContextPath()%>/legalCourtProcessingMakerDispatch.do?method=openLegalCourtProcessingMaker&caseType="+caseType+"&legalId="+legalId;
		}
		 self.close();
	
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>