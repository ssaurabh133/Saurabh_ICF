<!--Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface to financial balance sheet 
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
 		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/uploadAuthor.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
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
	<body onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('balancesheetforAuthor');" >
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	<logic:notPresent name="viewInfo">
	<html:form action="/balancesheetforAuthor" styleId="balancesheetforAuthor" method="post" >
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	  <input type="hidden" name="updateFlag" id="updateFlag" value="${updateFlag}" />
	  <input type="hidden" name="caseId" id="caseId" value="${caseId}" />
	    
	    
	    
	<fieldset>	
	
	
		 
		<legend>Parameter details for ${requestScope.docType}</legend>
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
       <%--  <td><center><b><bean:message key="lbl.subType"/></b></center></td> --%>
        <td><center><b><bean:message key="lbl.parameCode"/></b></center></td>
        <td><center ><b>${paramList[0].financialYear}</b></center><input type="hidden" name="yr01" id="yr01" value="${paramList[0].financialYear}"/></td>
      
		
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year}"/>
	</tr>
	<tr>
	
 
<logic:present name="paramList">	
<logic:iterate name="paramList" id="subParamList" indexId="count">
	
	<tr  class="white1">
	<!-- <td >
					<input type="hidden" name="subType" id="subType" value="${subBalanceSheetAllDetailsByDeal.subType}"/>
					</td> -->
	<td ><b>${subParamList.questionDesc}
		<%-- <input type="hidden" name="financialFormula" id="financialFormula" value="${subParamList.financialFormula}"> --%> 
		 <input type="hidden" name="pCode" id="pCode" value="${subParamList.questionCode}"/>
		 <input type="hidden" name="financialIds" id="financialIds" value="${subParamList.questionId}"/></b>
		 <input type="hidden" name="docType" id="docType" value=""/>
		 <input type="hidden" name="docId" id="docId" value=""/>
		 <input type="hidden" name="businessDate" id="businessDate" value=""/>
		 <input type="hidden" name="pName" id="pName" value="${subParamList.questionDesc}"/>
		 
	</td>
	<td ><b><center><input class="text" name="year1"  id="year1<%= count.intValue()+1 %>" value="${subParamList.firstYear}" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></center></b></td>
	</tr>	
  
    
    
   
</logic:iterate>
</logic:present>


 
	</tr>
 </table>    </td>
</tr>
</table>

</fieldset>
<tr>	  

			<td  colspan="10" class="white">
			<logic:present name="financialDealId">
			    <input type="hidden" id="dealId" value="${sessionScope.financialDealId}"/>
			</logic:present>
		    
		     
		     
		     
		    
			 
			 
			 <button type="button" name="Save" class="topformbutton2" id="Save" onclick="saveFinancialBalanceSheet('${requestScope.docId}','${requestScope.docType}');" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>	
		<!-- 	 <button type="button" name="button"  id="Forward" title="Alt+F" accesskey="F" class="topformbutton3" onclick="return financialAnalysisForward();"><bean:message key="button.forward" /></button>
            <button type="button" value="calculate" class="topformbutton3" onclick="financialAutoCalculation();" accesskey="A" title="Alt+A"><bean:message key="button.autoCalculate" /></button>-->
			</td>
</tr>
		</table>
		

	
	
	<logic:present name="back">
				<script type="text/javascript">
	alert("You can't move without Fill Up Bank Analysis Detail!!!");
	
    </script>
    </logic:present>
	
	 
	
	<br/>

</html:form>
</logic:notPresent>
<%--   -------------- -------------------- ---------View  mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="viewInfo">
	<html:form action="/balancesheetforAuthor" styleId="balancesheetforAuthor" method="post" >
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	  <input type="hidden" name="updateFlag" id="updateFlag" value="${updateFlag}" />
	   
	<fieldset>	
		 <legend><bean:message key="lbl.balParamDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
       <%--  <td><center><b><bean:message key="lbl.subType"/></b></center></td> --%>
        <td><center><b><bean:message key="lbl.parameCode"/></b></center></td>
        <td><center ><b>${paramList[0].financialYear}</b></center><input type="hidden" name="yr01" id="yr01" value="${paramList[0].financialYear}"/></td>
		
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year}"/>
	</tr>
	<tr>
	

<logic:present name="paramList">	
<logic:iterate name="paramList" id="subParamList" indexId="count">
	
	<tr  class="white1">
	<!-- <td >
					<input type="hidden" name="subType" id="subType" value="${subBalanceSheetAllDetailsByDeal.subType}"/>
					</td> -->
	<td ><b>${subParamList.questionDesc}
		<%-- <input type="hidden" name="financialFormula" id="financialFormula" value="${subParamList.financialFormula}"> --%> 
		 <input type="hidden" name="pCode" id="pCode" value="${subParamList.questionCode}"/>
		 <input type="hidden" name="financialIds" id="financialIds" value="${subParamList.questionId}"/></b>
		 <input type="hidden" name="docType" id="docType" value=""/>
		 <input type="hidden" name="docId" id="docId" value=""/>
		 <input type="hidden" name="businessDate" id="businessDate" value=""/>
		 <input type="hidden" name="pName" id="pName" value="${subParamList.questionDesc}"/>
		 
	</td>
	<td ><b><center><input class="text" readonly="readonly" name="year1"  id="year1<%= count.intValue()+1 %>" value="${subParamList.firstYear}" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></center></b></td>
	</tr>	
  
    
    
   
</logic:iterate>
</logic:present>


 
	</tr>
 </table>    </td>
</tr>
</table>

</fieldset>
<tr>	  

			
</tr>
		</table>
		

	
	
	<logic:present name="back">
				<script type="text/javascript">
	alert("You can't move without Fill Up Bank Analysis Detail!!!");
	
    </script>
    </logic:present>
	
	 
	
	<br/>

</html:form>
</logic:present>
</div>



</div>
<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		parent.window.close();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='B')
	{
			alert("<bean:message key="lbl.firstUpdateThenForward" />");
			location.href="balanceSheetBehindAction.do?method=balanceSheetBehindDetail";
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='BB')
	{
			alert("<bean:message key="lbl.updateRatioAnalysis" />");
			location.href="balanceSheetBehindAction.do?method=balanceSheetBehindDetail";   
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.forwardSuccess" />");
		parent.location.href="financialAnalysisSearchBehindAction.do";
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.window.close();
	}
	
	</script>
</logic:present>

<logic:present name="uploadError">
	<script type="text/javascript">
	alert('${uploadError}');
	</script>
</logic:present>
	

<logic:present name="notForward">

	<script type="text/javascript">
	
		alert("<bean:message key="lbl.notForward" />");
		
</script>
</logic:present>
<logic:present name="checkStageM">

	<script type="text/javascript">
	
		alert('${checkStageM}');
		
</script>
</logic:present>
<script>
	setFramevalues("financialBalacnceSheetForm");
</script>

</body>
</html:html>