<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 21-May-2011-->
<!--Purpose  : Information of country Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalCaseFileMaker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('caseNo').focus();
   return true;
}

$(function() {
		$("#caseFileDate").datepicker({
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
				defaultDate:'${userobject.businessdate }',
				maxDate : '${userobject.businessdate }'
})
});

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

<body onload="fntab();">
<html:form styleId="legalCaseFileMakerForm"  method="post"  action="/legalCaseFileMakerDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.legalCaseFileMaker" /></legend>
  <table width="100%">
  
  <logic:present name="list">
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileMakerLoanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${requestScope.list[0].loanNo}" /></td>
		<html:hidden property="legalId" styleClass="text" styleId="legalId" value="${requestScope.list[0].legalId}" />
		
		
		<td><bean:message key="lbl.legalCaseFileMakerCustName" /></td>
		
		<td><html:text property="customerName" styleClass="text" readonly="true" styleId="customerName" value="${requestScope.list[0].customerName}" /></td>
		
	  
	</tr>
	
	<tr>
	
		
		<td><bean:message key="lbl.legalCaseFileMakerProduct" /></td>
		
		<td><html:text property="product" styleClass="text" readonly="true" styleId="product" value="${requestScope.list[0].product}" /></td>
		
		<td><bean:message key="lbl.legalCaseFileMakerScheme" /></td>
		
		<td><html:text property="scheme" styleClass="text" readonly="true" styleId="scheme" value="${requestScope.list[0].scheme}" /></td>
		
	  
	</tr>
	
	<tr>
	
		
		<td><bean:message key="lbl.legalCaseFileMakerCaseType" /></td>
		
		<td><html:text property="caseTypeDesc" styleClass="text" readonly="true" styleId="caseTypeDesc" value="${requestScope.list[0].caseTypeDesc}" /></td>
		
		<td><bean:message key="lbl.legalCaseFileMakerAdvocate" /></td>
		
		<td><html:text property="advocate" styleClass="text" readonly="true" styleId="advocate" value="${requestScope.list[0].advocate}" /></td>
	  
	</tr>
	
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileMakerCaseNo" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="caseNo" maxlength="50" styleClass="text" styleId="caseNo" value="${requestScope.list[0].caseNo}" /></td>
		
		
		<td><bean:message key="lbl.legalCaseFileMakerCourtFee" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="courtFee" styleClass="text" maxlength="18" styleId="courtFee" value="${requestScope.list[0].courtFee}" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileDate" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="caseFileDate" onchange="checkDate('caseFileDate');" styleClass="text" styleId="caseFileDate" value="${requestScope.list[0].caseFileDate}" />
		
		</td>
		
		
		
		<td><bean:message key="lbl.legalDateofHearing" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="dateOfHearing" onchange="checkDate('dateOfHearing');compareTwoDates('formatD','caseFileDate','dateOfHearing','Date of Hearing should be greater than Case File Date.');" styleClass="text" styleId="dateOfHearing" value="${requestScope.list[0].dateOfHearing}" /></td>
		
		
 		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalStage" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="stageDesc" styleId="stageDesc" value="${requestScope.list[0].stageDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxStage" styleId="lbxStage" value="${requestScope.list[0].lbxStage}"  />
		<html:button property="stageButton" styleId="stageButton" onclick="closeLovCallonLov1();openLOVCommon(1526,'legalCaseFileMakerForm','stageDesc','','','','','','lbxStage');" value=" " styleClass="lovbutton"> </html:button>
		</td>
	
		<td><bean:message key="lbl.legalSection" /><span><font color="red">*</font></span></td>
		<td><html:text property="section" maxlength="50" styleClass="text" styleId="section" value="${requestScope.list[0].section}" /></td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalCourtType" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="courtTypeDesc" styleId="courtTypeDesc" value="${requestScope.list[0].courtTypeDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCourtType" styleId="lbxCourtType" value="${requestScope.list[0].lbxCourtType}"  />
		<html:button property="courtTypeButton" styleId="courtTypeButton" onclick="closeLovCallonLov1();openLOVCommon(1527,'legalCaseFileMakerForm','courtTypeDesc','','','','','','lbxCourtType');" value=" " styleClass="lovbutton"> </html:button>
		</td>
		
		<td><bean:message key="lbl.legalCourtName" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="courtNameDesc" styleId="courtNameDesc" value="${requestScope.list[0].courtNameDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCourtName" styleId="lbxCourtName" value="${requestScope.list[0].lbxCourtName}"  />
		<html:button property="courtNameButton" styleId="courtNameButton" onclick="closeLovCallonLov1();openLOVCommon(1528,'legalCaseFileMakerForm','courtNameDesc','','','','','','lbxCourtName');" value=" " styleClass="lovbutton"> </html:button>
		</td>
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalJudgeName" /><span><font color="red">*</font></span></td>
		<td><html:text property="judgeName" maxlength="100" styleClass="text" styleId="judgeName" value="${requestScope.list[0].judgeName}" /></td>
		
		<td><bean:message key="lbl.legalPoa" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="poaDesc" styleId="poaDesc" value="${requestScope.list[0].poaDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxPoa" styleId="lbxPoa" value="${requestScope.list[0].lbxPoa}"  />
		<html:button property="poaButton" styleId="poaButton" onclick="closeLovCallonLov1();openLOVCommon(1529,'legalCaseFileMakerForm','poaDesc','','','','','','lbxPoa');" value=" " styleClass="lovbutton"> </html:button>
		</td>
	
	
	</tr>

    
    <tr>
		<td><bean:message key="lbl.legalRecoveryAmount" /><span><font color="red">*</font></span></td>
		<td><html:text property="recoveryAmount" maxlength="18" styleClass="text" styleId="recoveryAmount" value="${requestScope.list[0].recoveryAmount}" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" /></td>
		
		<td><bean:message key="lbl.legalFileMakerRemarks" /><span><font color="red">*</font></span></td>
		<td><textarea class="text" name="fileMakerRemarks" id="fileMakerRemarks" maxlength="500" >${requestScope.list[0].fileMakerRemarks}</textarea></td>
	</tr>
	
	
    
	<tr>
		<td>
	      <button type="button" name=save id="save" title="Alt+V" accesskey="V" onclick="return fnSaveLegalCaseFileMaker('S');" class="topformbutton2" ><bean:message key="button.save" /></button>
	      <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return fnSaveLegalCaseFileMaker('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
		  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	   	  <input type="hidden" name ="saveForwardFlag" id="saveForwardFlag" />
	   	  <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" /> 
	    </td> 
    </tr>
    
	</logic:present>
	
	<logic:present name="caseFileAuthorList">
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileMakerLoanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${sessionScope.caseFileAuthorList[0].loanNo}" /></td>
		<html:hidden property="legalId" styleClass="text" styleId="legalId" value="${sessionScope.caseFileAuthorList[0].legalId}" />
		
		
		<td><bean:message key="lbl.legalCaseFileMakerCustName" /></td>
		
		<td><html:text property="customerName" styleClass="text" readonly="true" styleId="customerName" value="${sessionScope.caseFileAuthorList[0].customerName}" /></td>
		
	  
	</tr>
	
	<tr>
	
		
		<td><bean:message key="lbl.legalCaseFileMakerProduct" /></td>
		
		<td><html:text property="product" styleClass="text" readonly="true" styleId="product" value="${sessionScope.caseFileAuthorList[0].product}" /></td>
		
		<td><bean:message key="lbl.legalCaseFileMakerScheme" /></td>
		
		<td><html:text property="scheme" styleClass="text" readonly="true" styleId="scheme" value="${sessionScope.caseFileAuthorList[0].scheme}" /></td>
		
	  
	</tr>
	
	<tr>
	
		
		<td><bean:message key="lbl.legalCaseFileMakerCaseType" /></td>
		
		<td><html:text property="caseTypeDesc" styleClass="text" readonly="true" styleId="caseTypeDesc" value="${sessionScope.caseFileAuthorList[0].caseTypeDesc}" /></td>
		
		<td><bean:message key="lbl.legalCaseFileMakerAdvocate" /></td>
		
		<td><html:text property="advocate" styleClass="text" readonly="true" styleId="advocate" value="${sessionScope.caseFileAuthorList[0].advocate}" /></td>
		
	  
	</tr>
	
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileMakerCaseNo" /></td>
		
		<td><html:text property="caseNo"  readonly="true" styleClass="text" styleId="caseNo" value="${sessionScope.caseFileAuthorList[0].caseNo}" /></td>
		
		
		<td><bean:message key="lbl.legalCaseFileMakerCourtFee" /></td>
		
		<td><html:text property="courtFee" styleClass="text" readonly="true" style="text-align: right" styleId="courtFee" value="${sessionScope.caseFileAuthorList[0].courtFee}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileDate" /></td>
		
		<td><html:text property="caseFileDate" readonly="true" styleClass="text" value="${sessionScope.caseFileAuthorList[0].caseFileDate}" /></td>
		
		
		
		<td><bean:message key="lbl.legalDateofHearing" /></td>
		
		<td><html:text property="dateOfHearing" readonly="true" styleClass="text"  value="${sessionScope.caseFileAuthorList[0].dateOfHearing}" /></td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalStage" /></td>
		
		<td>
		<html:text property="stageDesc" styleId="stageDesc" value="${sessionScope.caseFileAuthorList[0].stageDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxStage" styleId="lbxStage" value="${sessionScope.caseFileAuthorList[0].lbxStage}"  />
		</td>
	
		<td><bean:message key="lbl.legalSection" /></td>
		
		<td><html:text property="section" readonly="true" styleClass="text" styleId="section" value="${sessionScope.caseFileAuthorList[0].section}" /></td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalCourtType" /></td>
		
		<td>
		<html:text property="courtTypeDesc" styleId="courtTypeDesc" value="${sessionScope.caseFileAuthorList[0].courtTypeDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCourtType" styleId="lbxCourtType" value="${sessionScope.caseFileAuthorList[0].lbxCourtType}"  />
		</td>
		
		<td><bean:message key="lbl.legalCourtName" /></td>
		
		<td>
		<html:text property="courtNameDesc" styleId="courtNameDesc" value="${sessionScope.caseFileAuthorList[0].courtNameDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCourtName" styleId="lbxCourtName" value="${sessionScope.caseFileAuthorList[0].lbxCourtName}"  />
		</td>
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalJudgeName" /></td>
		
		<td><html:text property="judgeName" readonly="true" styleClass="text" styleId="judgeName" value="${sessionScope.caseFileAuthorList[0].judgeName}" /></td>
		
		<td><bean:message key="lbl.legalPoa" /></td>
		
		<td>
		<html:text property="poaDesc" styleId="poaDesc" value="${sessionScope.caseFileAuthorList[0].poaDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxPoa" styleId="lbxPoa" value="${sessionScope.caseFileAuthorList[0].lbxPoa}"  />
		</td>
	
	
	</tr>

    
    <tr>
	
		<td><bean:message key="lbl.legalRecoveryAmount" /></td>
		
		<td><html:text property="recoveryAmount" style="text-align: right" readonly="true" styleClass="text" styleId="recoveryAmount" value="${sessionScope.caseFileAuthorList[0].recoveryAmount}" /></td>
		
		<td><bean:message key="lbl.legalFileMakerRemarks" /></td>
		<td><textarea class="text" name="fileMakerRemarks" readonly="readonly" id="fileMakerRemarks" >${sessionScope.caseFileAuthorList[0].fileMakerRemarks}</textarea></td>
	  
	</tr>

    

	</logic:present>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.CaseFileSucc" />");
		document.getElementById("legalCaseFileMakerForm").action="legalCaseFileMakerDispatch.do?method=openLegalCaseFileMaker&legalId="+<%=request.getAttribute("legalId")%>;
	    document.getElementById("legalCaseFileMakerForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="msg.FileCaseFwdSucc" />");
		document.getElementById("legalCaseFileMakerForm").action="legalCaseFileMakerDispatch.do?method=searchLegalCaseFileMaker";
	    document.getElementById("legalCaseFileMakerForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
		
	
	
</script>
</logic:present>
  </body>
		</html:html>