<!--Author Name : Vinod Kumar Gupta -->
<!--Date of Creation : 19-04-2013-->
<!--Purpose  : Information of court processing maker-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.connect.CommonFunction"%>
<%@page import="com.legal.vo.LegalCourtProcessingVo"%>
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
   document.getElementById('legalCourtProcessingMakerForm').loanNo.focus();
   return true;
}

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
<html:form styleId="legalCourtProcessingMakerForm"  method="post"  action="/legalCourtProcessingMakerDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.legalCaseFileMaker" /></legend>
  <table width="100%">
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileMakerLoanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${requestScope.list[0].loanNo}" /></td>
		<html:hidden property="legalId" styleClass="text" styleId="legalId" value="${requestScope.list[0].legalId}" />
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${requestScope.list[0].loanId}" />
		
		
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
		
		<td><html:text property="caseTypeDesc" styleClass="text" readonly="true" styleId="caseTypeDesc" value="${requestScope.list[0].caseTypeDesc}" />
		<html:hidden property="lbxCaseTypeCode" styleId="lbxCaseTypeCode" value="${requestScope.list[0].lbxCaseTypeCode}" /></td>
		
		<td><bean:message key="lbl.legalCaseFileMakerAdvocate" /></td>
		
		<td>
		<html:text property="advocateDesc" styleClass="text" readonly="true" styleId="advocateDesc" value="${requestScope.list[0].advocateDesc}" />
		<html:hidden property="advocate" styleId="advocate" value="${requestScope.list[0].advocate}" />
		
		</td>
		
	  
	</tr>
	
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileMakerCaseNo" /></td>
		
		<td><html:text property="caseNo" readonly="true" styleClass="text" styleId="caseNo" value="${requestScope.list[0].caseNo}" /></td>
		
		
		<td><bean:message key="lbl.legalCaseFileMakerCourtFee" /></td>
		
		<td><html:text property="courtFee" styleClass="text" readonly="true" style="text-align: right" styleId="courtFee" value="${requestScope.list[0].courtFee}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseFileDate" /></td>
		
		<td><html:text property="caseFileDate" readonly="true" styleClass="text" styleId="caseFileDate" value="${requestScope.list[0].caseFileDate}" /></td>
		
		
		
		<td><bean:message key="lbl.legalDateofHearing" /></td>
		
		<td><html:text property="dateOfHearing" readonly="true" styleClass="text" styleId="dateOfHearing" value="${requestScope.list[0].dateOfHearing}" /></td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalStage" /></td>
		
		<td>
		<html:text property="stageDesc" styleId="stageDesc" value="${requestScope.list[0].stageDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxStage" styleId="lbxStage" value="${requestScope.list[0].lbxStage}"  />
		</td>
	
		<td><bean:message key="lbl.legalSection" /></td>
		
		<td><html:text property="section" readonly="true" styleClass="text" styleId="section" value="${requestScope.list[0].section}" /></td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalCourtType" /></td>
		
		<td>
		<html:text property="courtTypeDesc" styleId="courtTypeDesc" value="${requestScope.list[0].courtTypeDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCourtType" styleId="lbxCourtType" value="${requestScope.list[0].lbxCourtType}"  />
		</td>
		
		<td><bean:message key="lbl.legalCourtName" /></td>
		
		<td>
		<html:text property="courtNameDesc" styleId="courtNameDesc" value="${requestScope.list[0].courtNameDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCourtName" styleId="lbxCourtName" value="${requestScope.list[0].lbxCourtName}"  />
		</td>
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.legalJudgeName" /></td>
		
		<td><html:text property="judgeName" readonly="true" styleClass="text" styleId="judgeName" value="${requestScope.list[0].judgeName}" /></td>
		
		<td><bean:message key="lbl.legalPoa" /></td>
		
		<td>
		<html:text property="poaDesc" styleId="poaDesc" value="${requestScope.list[0].poaDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxPoa" styleId="lbxPoa" value="${requestScope.list[0].lbxPoa}"  />
		</td>
	
	
	</tr>

    
    <tr>
	
		<td><bean:message key="lbl.legalRecoveryAmount" /></td>
		
		<td><html:text property="recoveryAmount" style="text-align: right" readonly="true" styleClass="text" styleId="recoveryAmount" value="${requestScope.list[0].recoveryAmount}" /></td>
		
		<td><bean:message key="lbl.legalFileMakerRemarks" /></td>
		
	  	<td><textarea class="text" name="fileMakerRemarks" readonly="readonly" id="fileMakerRemarks" >${requestScope.list[0].fileMakerRemarks}</textarea></td>
	</tr>
	


	</table>
	
	</fieldset>
	<fieldset>
<legend><bean:message key="lbl.legalStageDetail" /></legend>
	<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
				<td ><b><bean:message key="lbl.legalSNo" /></b></td>
				<td ><b><bean:message key="lbl.stageName" /></b></td>
				<td ><b><bean:message key="lbl.caseNo" /></b></td>
				<td ><b><bean:message key="lbl.remarks" /></b></td>
				<td ><b><bean:message key="lbl.hearingDate" /></b></td>
				<td ><b><bean:message key="lbl.poa" /></b></td>
				<td ><b><bean:message key="lbl.advocateName" /></b></td>
				<td ><b><bean:message key="lbl.systemDateAndTime" /></b></td>
				<td ><b><bean:message key="lbl.add" /></b></td>
				<td ><b><bean:message key="lbl.delete" /></b></td>
    			 
      		</tr>
      		
      		<% ArrayList<LegalCourtProcessingVo> list = new ArrayList<LegalCourtProcessingVo>();
      		
      		list = (ArrayList<LegalCourtProcessingVo>)request.getAttribute("stageList");
      		
      		if(list!=null)
      		{
      			for(int i=0,j=1;i<list.size();i++,j++)
      			{
      				
      				LegalCourtProcessingVo vo = list.get(i);
      			
      		 %>
      		 <tr class="white1" style="width: 25px;">
      		  		
      		  <td>
      		  <%=j%>
      		  <input type="hidden" id="approvalFlag<%=i%>" value = "<%=vo.getApprovalFlag()%>"/>
      		  <input type ="hidden" id="makerDate<%=i%>" value = "<%=vo.getMakerDate()%>"/>
      		  </td>
      		  
      		  <td><%=vo.getStageDesc()%>
      		  <input type="hidden" value="<%=vo.getStageDesc()%>" id="stageDesc<%=i%>" />
      		  <input type="hidden" value="<%=vo.getLbxStage()%>" id="stageCode<%=i%>" />
      		  </td>
      		  <%if(!CommonFunction.checkNull(vo.getApprovalFlag()).equalsIgnoreCase("Y")){ %>
      		  <td><input type="text" value="<%=vo.getCaseNo()%>" id="caseNO<%=i%>" /></td>		
      		  
      		  <td><textarea rows="3" cols="5" id="remarks<%=i%>"><%=vo.getMakerRemarks()%></textarea></td>
      		  
      		  <td><input type="text" readonly="readonly" value="<%=vo.getDateOfHearing()%>" onchange="compareTwoDates('formatD','dateOfHearing','hearingDate<%=i%>','Next Hearing should be greater than Date of Hearing.');" id="hearingDate<%=i%>" />
      		  <script type="text/javascript">
			    		  $(function() {
					$("#hearingDate"+<%=i%>).datepicker({
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
      		  
      		  </td>
      		  	
      		  <td>
      		  <input type="text" class="text" id="poaDesc<%=i%>" value="<%=vo.getPoaDesc()%>" name="poaDesc" readonly="readonly" />
      		  <input type="hidden" name="lbxPoa" id="lbxPoa<%=i%>" value="<%=vo.getLbxPoa()%>" />
      		  <input type="button" id="poaButton<%=i%>" class="lovbutton" onclick="closeLovCallonLov1();openLOVCommon(1529,'legalDeclineDispatchNoticeForm','poaDesc<%=i%>','','','','','','lbxPoa<%=i%>');"  />
      		  </td>
      		  <%}else{ %>
      		  <td><input type="text" value="<%=vo.getCaseNo()%>" readonly="readonly" id="caseNO<%=i%>" /></td>	
      		  <td><textarea rows="3" cols="5" readonly="readonly" id="remarks<%=i%>"><%=vo.getMakerRemarks()%></textarea></td>	
      		  <td><input type="text" readonly="readonly" value="<%=vo.getDateOfHearing()%>" id="hearingDate<%=i%>" /></td>
      		  <td><input type="text" class="text" id="poaDesc<%=i%>" value="<%=vo.getPoaDesc()%>" name="poaDesc" readonly="readonly" />
      		  <input type="hidden" name="lbxPoa" id="lbxPoa<%=i%>" value="<%=vo.getLbxPoa()%>" />
      		   </td>
      		  <%} %>
      		  <td><%=vo.getAdvocateDesc()%>
      		  <input type="hidden" value="<%=vo.getAdvocate()%>" id="advocate<%=i%>" />
      		  </td>
      		  
      		  <td><%=vo.getMakerDate()%></td>
      		  
      		  <%if(!CommonFunction.checkNull(vo.getCaseNo()).equalsIgnoreCase("")){ %>
      		  <td><input type="button"  id="<%=i%>" onclick="addRow('<%=i%>','M');"  value="+" /></td>
      		  <%if(!CommonFunction.checkNull(vo.getApprovalFlag()).equalsIgnoreCase("Y")){ %>
              <td><input type="button" id="<%=i%>" onclick="deleteRow('<%=i%>','M');" value="-" /></td>
      		  <%}else{ %>
      		  <td><input type="button" value="-" disabled="disabled" /></td>
      		  <%}}else{ %>
      		  
      		  <td><input type="button" value="+" disabled="disabled" /></td>
              <td><input type="button" value="-" disabled="disabled" /></td>
      		  
      		  <%} %>
			 </tr>
      		<%}} %>
      		
    		</table>
    </tr>
    
    
    
    <tr><td>
    
    	<%if(list!=null){ %>
       <input type="hidden" name="listSize" id="listSize" value="<%=list.size()%>" />
    <%}%>
       
    <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
    <input type="hidden" name="saveForwardFlag" id="saveForwardFlag" />
    
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveLegalCourtProcessing('S','M');" class="topformbutton2" ><bean:message key="button.save" /></button>
    <button type="button" name="forward" id="forward" title="Alt+V" accesskey="V" onclick="return saveLegalCourtProcessing('F','M');" class="topformbutton2" ><bean:message key="button.forward" /></button>
  <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" /> 
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		if('<%=request.getAttribute("saveDeleteFlag").toString()%>'=='D')
		{
			alert("<bean:message key="msg.CaseDelSucc" />");
		}
		else
		{
			alert("<bean:message key="msg.CourtProccessingSucc" />");
		}
		
		
		var legalId = <%=request.getAttribute("legalId")%>;//legalId is a number
		
		//alert("legalId : "+legalId);
		//caseType is a String
		var caseType = '<%=request.getAttribute("caseType").toString()%>'; 
		
		//alert("caseType : "+caseType);
		
		document.getElementById("legalCourtProcessingMakerForm").action="legalCourtProcessingMakerDispatch.do?method=openLegalCourtProcessingMaker&caseType="+caseType+"&legalId="+legalId;
	    document.getElementById("legalCourtProcessingMakerForm").submit();
	    
	}
		else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		if('<%=request.getAttribute("makerAuthorFlag").toString()%>'=='A')
		{
			
			if('<%=request.getAttribute("saveDeleteFlag").toString()%>'=='D')
			{
				alert("<bean:message key="msg.CaseDelSucc" />");
			}
			else
			{
				alert("<bean:message key="msg.CourtProccessingSucc" />");
			}
		
			var legalId = <%=request.getAttribute("legalId")%>;//legalId is a number
			
			//alert("legalId : "+legalId);
			//caseType is a String
			var caseType = '<%=request.getAttribute("caseType").toString()%>'; 
			
			//alert("caseType : "+caseType);
			
			parent.location="<%=request.getContextPath()%>/legalCourtProcessingAuthorDispatch.do?method=openLegalCourtProcessingAuthor&caseType="+caseType+"&legalId="+legalId;
		}
		else
		{
			alert("<bean:message key="msg.CourtProccessingFwSucc" />");
			document.getElementById("legalCourtProcessingMakerForm").action="legalCourtProcessingMakerDispatch.do?method=searchLegalCourtProcessingMaker";
		    document.getElementById("legalCourtProcessingMakerForm").submit();
	    }
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>