<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface to financial Ratio Analysis
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
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
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('financialRatioAnalysisForm');" >
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	<logic:notPresent name="underWriterViewData">
	<html:form action="/ratioAnalysisBehindAction" styleId="financialRatioAnalysisForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	   <fieldset>	  
	<legend><bean:message key="lbl.ratioDetail"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td colspan="2"><bean:message key="lbl.dealNo"/></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10" readonly="true" value="${financialDetails[0].deal}"  tabindex="-1"/>

     </td>
	<td width="17%"><bean:message key="lbl.customerName"/></td>
	<td width="28%">
	<html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" maxlength="50" value="${financialDetails[0].customername}"/>
	</td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.applicationDate"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="applicationDate" styleId="applicationDate" styleClass="text" maxlength="50" value="${financialDetails[0].applicationdate}" readonly="true" tabindex="-1"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.product"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="product" styleId="product" value="${financialDetails[0].product}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.scheme"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="scheme" styleId="scheme" styleClass="text" maxlength="50" value="${financialDetails[0].scheme}" readonly="true" tabindex="-1"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.loanAmount"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${financialDetails[0].loanAmount}" maxlength="50" tabindex="-1" readonly="true"/>
    </td>
		</tr>
		
	  <tr>
	  
	  <td colspan="2"><bean:message key="lbl.tenure"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="tenure" styleId="tenure" styleClass="text" maxlength="50" value="${financialDetails[0].tenure}" tabindex="-1" readonly="true"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.sourceType"/></td>
	<td width="28%">
	<html:hidden  styleClass="text" property="sourceType" styleId="sourceType" value="R"/>
	<html:text  styleClass="text" property="sourceDesc" styleId="sourceDesc" value="RATIO ANALYSIS" maxlength="1" readonly="true"/>
	</td>

	  </tr>
	  	  </table>
	  <fieldset>	
		 <legend><bean:message key="lbl.ratioCodeDetail"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
<!--	     <td><center><b><bean:message key="lbl.ratioCode"/></b></center></td>-->
	     <td><center><b><bean:message key="lbl.ratio"/></b></center></td>
	     <td><center><b><bean:message key="lbl.benchMarkRatio"/></b></center></td>
        <td><center><b>${requestScope.year1}</b></center></td>
        <td><center><b>${requestScope.year2}</b></center></td>
        <td><center><b>${requestScope.year3}</b></center></td>
        <td><center><b>${requestScope.year4}</b></center></td>
        <td><center><b>${requestScope.year5}</b></center></td>
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year1}"/>
	</tr>
	<tr>
		<logic:present name="ratioAnalysisList">
		
		<logic:iterate name="ratioAnalysisList" id="subRatioAnalysisList" indexId="count">
		 
		<tr  class="white1">
	    
	   
		<td > ${subRatioAnalysisList.ratioName}
	    <input type="hidden" name="pName" id="pName" value="${subRatioAnalysisList.ratioName}" /></td>
		<td > ${subRatioAnalysisList.benchBranchRatio}</td>
		<td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioFirstYear}" /></td>
	    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioSecondYear}"" /></td>
	    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioThirdYear}" /></td>
	    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioFourthYear}" /></td>
	    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioFifthYear}" /></td>
	     <input type="hidden" name="pCode" id="pCode" value="${subRatioAnalysisList.ratioParamCode}"/>
	 </tr>	
 	</logic:iterate>

		</logic:present>
    	 
	</tr>
 </table>    </td>
</tr>
</table>

	</fieldset>
	  
 
		</table>
		

	
	
	<logic:present name="back">
				<script type="text/javascript">
	alert("You can't move without Fill Up Bank Analysis Detail!!!");
	
    </script>
    </logic:present>
	
	 
	</fieldset>	
	<br/>

</html:form>
</logic:notPresent>
<%--   -------------- -------------------- ---------View  mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="underWriterViewData">
<html:form action="/ratioAnalysisBehindAction" styleId="financialRatioAnalysisForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	   <fieldset>	  
	<legend><bean:message key="lbl.ratioDetail"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td colspan="2"><bean:message key="lbl.dealNo"/></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10" readonly="true" value="${financialDetails[0].deal}"  tabindex="-1"/>

     </td>
	<td width="17%"><bean:message key="lbl.customerName"/></td>
	<td width="28%">
	<html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" maxlength="50" value="${financialDetails[0].customername}"/>
	</td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.applicationDate"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="applicationDate" styleId="applicationDate" styleClass="text" maxlength="50" value="${financialDetails[0].applicationdate}" readonly="true" tabindex="-1"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.product"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="product" styleId="product" value="${financialDetails[0].product}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.scheme"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="scheme" styleId="scheme" styleClass="text" maxlength="50" value="${financialDetails[0].scheme}" readonly="true" tabindex="-1"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.loanAmount"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${financialDetails[0].loanAmount}" maxlength="50" tabindex="-1" readonly="true"/>
    </td>
		</tr>
		
	  <tr>
	  
	  <td colspan="2"><bean:message key="lbl.tenure"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="tenure" styleId="tenure" styleClass="text" maxlength="50" value="${financialDetails[0].tenure}" tabindex="-1" readonly="true"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.sourceType"/></td>
	<td width="28%">
	<html:hidden  styleClass="text" property="sourceType" styleId="sourceType" value="R"/>
	<html:text  styleClass="text" property="sourceDesc" styleId="sourceDesc" value="RATIO ANALYSIS" maxlength="1" readonly="true"/>
	</td>

	  </tr>
	  	  </table>
	  <fieldset>	
		 <legend><bean:message key="lbl.ratioCodeDetail"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
<!--        <td><center><b><bean:message key="lbl.ratioCode"/></b></center></td>-->
        <td><center><b><bean:message key="lbl.ratio"/></b></center></td>
         <td><center><b><bean:message key="lbl.benchMarkRatio"/></b></center></td>
        <td><center><b>${requestScope.year1}</b></center></td>
        <td><center><b>${requestScope.year2}</b></center></td>
        <td><center><b>${requestScope.year3}</b></center></td>
        <td><center><b>${requestScope.year4}</b></center></td>
        <td><center><b>${requestScope.year5}</b></center></td>
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year1}"/>
	</tr>
	<tr>
		<logic:present name="ratioAnalysisList">
		
		<logic:iterate name="ratioAnalysisList" id="subRatioAnalysisList" indexId="count">
		 
		<tr  class="white1">
	    
	  
		<td > ${subRatioAnalysisList.ratioName}
	    <input type="hidden" name="pName" id="pName" value="${subRatioAnalysisList.ratioName}" /></td>
	    <td > ${subRatioAnalysisList.benchBranchRatio}</td>
		<td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioFirstYear}" /></td>
	    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioSecondYear}"" /></td>
	    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioThirdYear}" /></td>
	    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioFourthYear}" /></td>
	    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" readonly="readonly" value="${subRatioAnalysisList.ratioFifthYear}" /></td>
	    <input type="hidden" name="pCode" id="pCode" value="${subRatioAnalysisList.ratioParamCode}"/>
	 </tr>	
 	</logic:iterate>

		</logic:present>
    	 
	</tr>
 </table>    </td>
</tr>
</table>

	</fieldset>
	  
 
		</table>
		

	
	
	<logic:present name="back">
				<script type="text/javascript">
	alert("You can't move without Fill Up Bank Analysis Detail!!!");
	
    </script>
    </logic:present>
	
	 
	</fieldset>	
	<br/>

</html:form>
	
</logic:present>
</div>



</div>
<script>
	setFramevalues("financialRatioAnalysisForm");
</script>
 
</body>
</html:html>