<!--Author Name : nazia parvez -->
<!--Date of Creation : 1-april-2013-->
<!--Purpose  : Information of Repo Confirmation-->
<!--Documentation : -->




<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.connect.CommonFunction"%>
<%@page import="com.repo.vo.RepoConfirmationtVo"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoDetailByStockyard.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>


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

<body onload="enableAnchor();">
<html:form styleId="repoConfirmationForm"  method="post"  action="/RepoConfirmationDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
    </logic:present>
<logic:notPresent name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>
<fieldset>
<legend><bean:message key="lbl.repoDetail" /></legend>
  <table width="100%">
  
	<tr>
	
		<td><bean:message key="lbl.loanNo" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${sessionScope.repoConfirmationList[0].loanNo}" /></td>
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${sessionScope.repoConfirmationList[0].loanId}" />
		 <html:hidden property="repoId" styleClass="text" styleId="repoId" value="${sessionScope.repoConfirmationList[0].repoId}" />
		
		<td><bean:message key="lbl.customerName" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="customerName" styleClass="text" readonly="true" styleId="customerName" value="${sessionScope.repoConfirmationList[0].customerName}" /></td>
		
		
	</tr>
	
	<tr>
	<td><bean:message key="lbl.product" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="productDescription" styleClass="text" readonly="true" styleId="productDescription" value="${sessionScope.repoConfirmationList[0].productDescription}" /></td>
        <html:hidden property="productId"  styleId="productId" value="${sessionScope.repoConfirmationList[0].productId}" />

		<td><bean:message key="lbl.scheme" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="scheme" styleClass="text" readonly="true" styleId="scheme" value="${sessionScope.repoConfirmationList[0].scheme}" /></td>
		
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.assetDesc" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="assetDescription" readonly="true" styleClass="text" styleId="assetDescription" value="${sessionScope.repoConfirmationList[0].assetDescription}" /></td>
		 <html:hidden property="assetId"  styleId="assetId" value="${sessionScope.repoConfirmationList[0].assetId}" />
		<td><bean:message key="lbl.assetCost" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="assetCost" styleClass="text" readonly="true" styleId="assetCost" value="${sessionScope.repoConfirmationList[0].assetCost}" /></td>
		
</tr>
	
	<tr>
	
	
		<td><bean:message key="lbl.repossessedBy" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="repossessedBy" readonly="true" styleClass="text" styleId="repossessedBy" value="${sessionScope.repoConfirmationList[0].repossessedBy}" /></td>
	 <td><bean:message key="lbl.agencyName" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="agencyName" readonly="true" styleClass="text" styleId="agencyName" value="${sessionScope.repoConfirmationList[0].agencyName}" /></td>
	</tr>
    
    <tr>
   
		

		<td><bean:message key="lbl.repoAddress1" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="repoAddress1" styleClass="text" readonly="true" styleId="repoAddress1" value="${sessionScope.repoConfirmationList[0].repoAddress1}" /></td>
  
  <td><bean:message key="lbl.repoAddress2" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="repoAddress2" styleId="repoAddress2" value="${sessionScope.repoConfirmationList[0].repoAddress2}" styleClass="text" readonly="true" tabindex="-1"/>
		
		
		</td> 
	</tr>  
    
    <tr>

		<td><bean:message key="lbl.state" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="state" styleId="state" value="${sessionScope.repoConfirmationList[0].state}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
		<td><bean:message key="lbl.district" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="district" styleId="district" value="${sessionScope.repoConfirmationList[0].district}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
	  
	</tr>
	
	<tr>

		<td><bean:message key="lbl.repoFrom" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="repoFrom" styleId="repoFrom" value="${sessionScope.repoConfirmationList[0].repoFrom}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
		<td><bean:message key="lbl.relationToCustomer" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="relationToCustomer" styleId="relationToCustomer" value="${sessionScope.repoConfirmationList[0].relationToCustomer}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.repoDateTime" /><span><font color="red">*</font></span></td>
			
		<td>
		 <html:text property="repoDate" readonly="true" styleClass="text3" styleId="repoDate" maxlength="10" value="${sessionScope.repoConfirmationList[0].repoDate}" />
		<html:text property="repoTime" readonly="true"  styleId="repoTime" onchange="validateTime('repoTime');" maxlength="5"  styleClass="text5" value="${sessionScope.repoConfirmationList[0].repoTime}" />&nbsp;(24hrs Format)
		</td>
		
		<td><bean:message key="lbl.stockyard" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="stockyard" styleId="stockyard" value="${sessionScope.repoConfirmationList[0].stockyard}" styleClass="text"  tabindex="-1" readonly="true"/></td>
	  
	</tr>
	
	<tr>

		
		
		<td><bean:message key="lbl.stockyardManager" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="stockyardManagerName" readonly="true" styleId="stockyardManagerName" value="${sessionScope.repoConfirmationList[0].stockyardManagerName}" styleClass="text"  tabindex="-1"/></td>
		
		
			<td><bean:message key="lbl.sEntryDateTime" /><span><font color="red">*</font></span></td>
		
		<td>
		 <html:text property="stockyardEntryDate" readonly="true" styleClass="text3" styleId="stockyardEntryDate" maxlength="10"  value="${sessionScope.repoConfirmationList[0].stockyardEntryDate}" />
		<html:text property="stockyardEntryTime" readonly="true"  styleId="stockyardEntryTime" maxlength="5"  styleClass="text5" value="${sessionScope.repoConfirmationList[0].stockyardEntryTime}" />&nbsp;(24hrs Format)
		</td>
		
	  
	</tr>
	<tr>

		<td><bean:message key="lbl.repoAgencyRemarks" /><span><font color="red">*</font></span></td>
		
		<td><textarea name = "agencyRemarks" readonly="readonly" class="text" id = "agencyRemarks" maxlength = "500" >${sessionScope.repoConfirmationList[0].agencyRemarks}</textarea></td>
		
	   	<td><bean:message key="lbl.repoStockyardRemarks" /><span><font color="red">*</font></span></td>
		
		<td><textarea name = "stockyardRemarks" readonly="readonly" class="text" id = "stockyardRemarks" maxlength = "500" >${sessionScope.repoConfirmationList[0].stockyardRemarks}</textarea></td>  
	    
	</tr>
	
	
	</table>		


</fieldset>
   <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
   <input type="hidden" name ="updateForwardFlag" id="updateForwardFlag" />
   
<table width="100%">

<tr>
	
	<td>
	
		<fieldset>
<legend><bean:message key="lbl.checkListDtlByAgency" /></legend>


<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	<td ><b><bean:message key="lbl.assetChecklist" /></b></td>
				<td ><b><bean:message key="lbl.status" /></b></td>
				<td ><b><bean:message key="lbl.remarks" /></b></td>
      		</tr>
      		
      		<% ArrayList<RepoConfirmationtVo> agencyList = new ArrayList<RepoConfirmationtVo>();
      		
      		agencyList = (ArrayList<RepoConfirmationtVo>)session.getAttribute("repoConfirmationCheckListForAgency");
      		
      		if(agencyList!=null)
      		{
      			for(int i=0;i<agencyList.size();i++)
      			{
      				
      				RepoConfirmationtVo vo = agencyList.get(i);
      				System.out.println("vo.getAcheckList("+i+") : "+vo.getAssetChecklist());
      				System.out.println("vo.getCheckListStatus("+i+") : "+vo.getCheckListStatus());
      				System.out.println("vo.getCheckListRemarks("+i+") : "+vo.getCheckListRemarks());
      		 %>
      		 <tr class="white1" style="width: 25px;">
      	
      		  <td ><%=vo.getAssetChecklist()%></td>
      		  <td><%=vo.getCheckListStatus()%></td>
      		  <td><%=vo.getCheckListRemarks()%></td>
      		  
      		
			
			 </tr>
      		<%}} %>
      		
    		</table>
    </tr>
    
    
    
   

	</table>

     </fieldset>
	
	</td>
	
	<td>
	
		   <fieldset>
<legend><bean:message key="lbl.checkListDtlByStockyard" /></legend>


<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	<td ><b><bean:message key="lbl.assetChecklist" /></b></td>
				<td ><b><bean:message key="lbl.status" /></b></td>
				<td ><b><bean:message key="lbl.remarks" /></b></td>
      		</tr>
      		
      		<% ArrayList<RepoConfirmationtVo> stockyardList = new ArrayList<RepoConfirmationtVo>();
      		
      		stockyardList = (ArrayList<RepoConfirmationtVo>)session.getAttribute("repoConfirmationCheckListForStrockyard");
      		
      		if(stockyardList!=null)
      		{
      			for(int i=0;i<stockyardList.size();i++)
      			{
      				
      				RepoConfirmationtVo vo = stockyardList.get(i);
      				System.out.println("vo.getAcheckList("+i+") : "+vo.getAssetChecklist());
      				System.out.println("vo.getCheckListStatus("+i+") : "+vo.getCheckListStatus());
      				System.out.println("vo.getCheckListRemarks("+i+") : "+vo.getCheckListRemarks());
      		 %>
      		 <tr class="white1" style="width: 25px;">
      	
      		  <td ><%=vo.getAssetChecklist()%></td>
      		  <td><%=vo.getCheckListStatus()%></td>
      		  <td><%=vo.getCheckListRemarks()%></td>
      		  
      		
			
			 </tr>
      		<%}} %>
      		
    		</table>
    </tr>
    
    
    
   

	</table>

     </fieldset>
	
	
	</td>



</tr>


</table> 

<%--   
   
<fieldset>
<legend><bean:message key="lbl.checkListDtlByAgency" /></legend>


<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	<td ><b><bean:message key="lbl.assetChecklist" /></b></td>
				<td ><b><bean:message key="lbl.status" /></b></td>
				<td ><b><bean:message key="lbl.remarks" /></b></td>
      		</tr>
      		
      		<% ArrayList<RepoConfirmationtVo> agencyList = new ArrayList<RepoConfirmationtVo>();
      		
      		agencyList = (ArrayList<RepoConfirmationtVo>)session.getAttribute("repoConfirmationCheckListForAgency");
      		
      		if(agencyList!=null)
      		{
      			for(int i=0;i<agencyList.size();i++)
      			{
      				
      				RepoConfirmationtVo vo = agencyList.get(i);
      				System.out.println("vo.getAcheckList("+i+") : "+vo.getAssetChecklist());
      				System.out.println("vo.getCheckListStatus("+i+") : "+vo.getCheckListStatus());
      				System.out.println("vo.getCheckListRemarks("+i+") : "+vo.getCheckListRemarks());
      		 %>
      		 <tr class="white1" style="width: 25px;">
      	
      		  <td ><%=vo.getAssetChecklist()%></td>
      		  <td><%=vo.getCheckListStatus()%></td>
      		  <td><%=vo.getCheckListRemarks()%></td>
      		  
      		
			
			 </tr>
      		<%}} %>
      		
    		</table>
    </tr>
    
    
    
   

	</table>

     </fieldset>
     
   <fieldset>
<legend><bean:message key="lbl.checkListDtlByStockyard" /></legend>


<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	<td ><b><bean:message key="lbl.assetChecklist" /></b></td>
				<td ><b><bean:message key="lbl.status" /></b></td>
				<td ><b><bean:message key="lbl.remarks" /></b></td>
      		</tr>
      		
      		<% ArrayList<RepoConfirmationtVo> stockyardList = new ArrayList<RepoConfirmationtVo>();
      		
      		stockyardList = (ArrayList<RepoConfirmationtVo>)session.getAttribute("repoConfirmationCheckListForStrockyard");
      		
      		if(stockyardList!=null)
      		{
      			for(int i=0;i<stockyardList.size();i++)
      			{
      				
      				RepoConfirmationtVo vo = stockyardList.get(i);
      				System.out.println("vo.getAcheckList("+i+") : "+vo.getAssetChecklist());
      				System.out.println("vo.getCheckListStatus("+i+") : "+vo.getCheckListStatus());
      				System.out.println("vo.getCheckListRemarks("+i+") : "+vo.getCheckListRemarks());
      		 %>
      		 <tr class="white1" style="width: 25px;">
      	
      		  <td ><%=vo.getAssetChecklist()%></td>
      		  <td><%=vo.getCheckListStatus()%></td>
      		  <td><%=vo.getCheckListRemarks()%></td>
      		  
      		
			
			 </tr>
      		<%}} %>
      		
    		</table>
    </tr>
    
    
    
   

	</table>

     </fieldset>
 --%>     
     
     
   </html:form>
             

  </body>
		</html:html>