<!--Author Name : Vinod Kumar Gupta-->
<!--Date of Creation : 01-05-2013-->
<!--Purpose  : Add JSp for Repo detail by agency-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.connect.CommonFunction"%>
<%@page import="com.repo.vo.RepoDetailVo"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoDetailByAgency.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('repossessedBy').focus();
   return true;
}

$(function() {
		$("#repoDate").datepicker({
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

<body onload="enableAnchor();fntab();">
<html:form styleId="repoDetailByAgencyForm"  method="post"  action="/repoDetailbyAgencyDispatchAction" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.repoDetialByAgencyDetail"/></legend>
  <table width="100%">
  
 
	<tr>
	
		<td><bean:message key="lbl.repoLoanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${requestScope.list[0].loanNo}" />
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${requestScope.list[0].loanId}" />
		<html:hidden property="repoId" styleClass="text" styleId="repoId" value="${requestScope.list[0].repoId}" />
		</td>
		
		<td><bean:message key="lbl.repoCustomerName" /></td>
		
		<td>
		<html:text property="customerName" styleClass="text" readonly="true" styleId="customerName" value="${requestScope.list[0].customerName}" />
		<html:hidden property="customerId" styleId="customerId" value="${requestScope.list[0].customerId}" />
		
		</td>
		
	  
	</tr>
	
	<tr>
	
		
		<td><bean:message key="lbl.repoProduct" /></td>
		
		<td>
		<html:text property="productDesc" styleClass="text" readonly="true" styleId="productDesc" value="${requestScope.list[0].productDesc}" />
		<html:hidden property="productId" styleId="productId" value="${requestScope.list[0].productId}" />
		</td>
		
		<td><bean:message key="lbl.repoScheme" /></td>
		
		<td><html:text property="schemeDesc" styleClass="text" readonly="true" styleId="schemeDesc" value="${requestScope.list[0].schemeDesc}" /></td>
	  
	</tr>
	
	<tr>
	
		
		<td><bean:message key="lbl.repoAssetDesc" /></td>
		
		<td>
		<html:text property="assetDesc" styleClass="text" readonly="true" styleId="assetDesc" value="${requestScope.list[0].assetDesc}" />
		<html:hidden property="assetClass" styleId="assetClass" value="${requestScope.list[0].assetClass}" />
		</td>
		
		<td><bean:message key="lbl.repoAssetCost" /></td>
		
		<td><html:text property="assetCost" styleClass="text" readonly="true" styleId="assetCost" value="${requestScope.list[0].assetCost}" /></td>
	  
	</tr>
	
	
	<tr>
	
		<td><bean:message key="lbl.repossessedBy" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="repossessedBy" maxlength="100" styleClass="text" styleId="repossessedBy" value="${requestScope.list[0].repossessedBy}" /></td>
		
		
		<td><bean:message key="lbl.repoAgencyName" /></td>
		
		<td>
		<html:text property="agencyName" styleClass="text" readonly="true" styleId="agencyName" value="${requestScope.list[0].agencyName}" />
		<html:hidden property="agencyId" styleId="agencyId" value="${requestScope.list[0].agencyId}" />
		</td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.repoAdd1" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="repoAdd1" styleClass="text" styleId="repoAdd1" value="${requestScope.list[0].repoAdd1}" /></td>
		
		
		
		<td><bean:message key="lbl.repoAdd2" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="repoAdd2" styleClass="text" styleId="repoAdd2" value="${requestScope.list[0].repoAdd2}" /></td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.repoState" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="stateDesc" styleId="stateDesc" value="${requestScope.list[0].stateDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxState" styleId="lbxState" value="${requestScope.list[0].lbxState}"  />
		<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov1();openLOVCommon(1606,'repoDetailByAgencyForm','stateDesc','','','','','','lbxState');" value=" " styleClass="lovbutton"> </html:button>
		</td>
	
		<td><bean:message key="lbl.repoDistrict" /><span><font color="red">*</font></span></td>
	
		<td>
		<html:text property="districtDesc" styleId="districtDesc" value="${requestScope.list[0].districtDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxDistrict" styleId="lbxDistrict" value="${requestScope.list[0].lbxDistrict}"  />
		<html:button property="districtButton" styleId="districtButton" onclick="openLOVCommon(1607,'repoDetailByAgencyForm','districtDesc','stateDesc','lbxDistrict', 'lbxState','Please select state first!!!','','lbxDistrict');closeLovCallonLov('stateDesc');" value=" " styleClass="lovbutton"> </html:button>
		</td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.repoFrom" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="repoFrom" maxlength="100" styleClass="text" styleId="repoFrom" value="${requestScope.list[0].repoFrom}" /></td>
		
		<td><bean:message key="lbl.relationToCustomer" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="relationToCustomer" maxlength="100" styleClass="text" styleId="relationToCustomer" value="${requestScope.list[0].relationToCustomer}" /></td>
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.repoDateTime" /><span><font color="red">*</font></span></td>
		
		<td>
		 <html:text property="repoDate" styleClass="text3" styleId="repoDate" maxlength="10"  value="${requestScope.list[0].repoDate}" onchange="checkDate('repoDate');compareTwoDates('formatD','repoDate','bDate','Repo date should not be greater than Business Date.');" />
		 <html:text property="repoTime"  styleId="repoTime" onchange="validateTime('repoTime');" maxlength="5"  styleClass="text5" value="${requestScope.list[0].repoTime}" />&nbsp;(24hrs Format)
		 <input type = "hidden" id="bDate" value = "${userobject.businessdate}"/>
         </td>
		
		<td><bean:message key="lbl.repoStockyard" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="stockYardDesc" styleId="stockYardDesc" value="${requestScope.list[0].stockYardDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxStockYard" styleId="lbxStockYard" value="${requestScope.list[0].lbxStockYard}"  />
		<html:button property="stockyardButton" styleId="stockyardButton" onclick="closeLovCallonLov1();openLOVCommon(1608,'repoDetailByAgencyForm','stockYardDesc','','','','','','lbxStockYard');" value=" " styleClass="lovbutton"> </html:button>
		</td>
	
	
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.repoAgencyRemarks" /><span><font color="red">*</font></span></td>
		
		<td><textarea name = "agencyRemarks" class="text" id = "agencyRemarks" maxlength = "500" >${requestScope.list[0].agencyRemarks}</textarea></td>
	
	</tr>


	</table>		


</fieldset>
<fieldset>
<legend><bean:message key="lbl.repoCheckListDtl" /></legend>
	<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
				<td ><b><bean:message key="lbl.repoAssetCheckList" /></b></td>
				<td ><b><bean:message key="lbl.repoCheckListStatus" /></b></td>
				<td ><b><bean:message key="lbl.repoCheckListRemarks" /></b></td>
      		</tr>
      		
      		<% ArrayList<RepoDetailVo> list = new ArrayList<RepoDetailVo>();
      		
      		list = (ArrayList<RepoDetailVo>)request.getAttribute("checkList");
      		
      		if(list!=null)
      		{
      			for(int i=0;i<list.size();i++)
      			{
      				
      				RepoDetailVo vo = list.get(i);
      			
      		 %>
      		 <tr class="white1" style="width: 25px;">
      		  		
      		  <td>
      		  <%=vo.getCheckList()%>
      		  <input type="hidden" id="checkList<%=i%>" value = "<%=vo.getCheckList()%>"/>
      		  </td>
      		  
      		  <td>
      		 		<html:select property="checkListStatus" styleId='<%="checkListStatus"+i%>'  styleClass="text" value="<%=vo.getCheckListStatus()%>" >
                 <html:option value="">---Select---</html:option>
                <html:option value="YES">Yes</html:option>
                <html:option value="NO">No</html:option>
                <html:option value="NA">NA</html:option>
                
       </html:select>
      		  </td>
      		  
      		    <td>
      		  
      		  <textarea name="checkListRemarks" id="checkListRemarks<%=i%>" ><%=vo.getCheckListRemarks()%></textarea>
      		  </td>
      		  
			 </tr>
      		<%}} %>
      		
    		</table>
    </tr>
    
    
    
    <tr><td>
    
    	<%if(list!=null){ %>
       <input type="hidden" name="listSize" id="listSize" value="<%=list.size()%>" />
    <%}%>
     	  
		<button type="button" name=save id="save" title="Alt+V" accesskey="V" onclick="return fnSaveRepoDetailByAgency('S');" class="topformbutton2" ><bean:message key="button.save" /></button>
		<button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return fnSaveRepoDetailByAgency('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
		<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		<input type="hidden" name ="saveForwardFlag" id="saveForwardFlag" />
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" /> 
	
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.RepoDetailSavedSucc" />");
		document.getElementById("repoDetailByAgencyForm").action="repoDetailbyAgencyDispatchAction.do?method=openEditRepoDetailbyAgency&repoId="+<%=request.getAttribute("repoId")%>;
	    document.getElementById("repoDetailByAgencyForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="msg.RepoDetailForwSucc" />");
		document.getElementById("repoDetailByAgencyForm").action="repoDetailbyAgencyDispatchAction.do?method=searchRepoDetailbyAgency";
	    document.getElementById("repoDetailByAgencyForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
		
	
	
</script>
</logic:present>
  </body>
		</html:html>