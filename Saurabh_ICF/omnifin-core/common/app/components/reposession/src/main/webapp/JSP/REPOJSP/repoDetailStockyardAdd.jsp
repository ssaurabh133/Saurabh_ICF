<!--Author Name : Richa Bansal-->
<!--Date of Creation : 21-May-2011-->
<!--Purpose  : Information of country Master-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.connect.CommonFunction"%>
<%@page import="com.repo.vo.RepoDetailStockyardVo"%>
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
<script type="text/javascript">
  $(function() {
		$("#stockyardEntryDate").datepicker({
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


<script type="text/javascript">
function fntab()
{
   document.getElementById('caseTypeButton').focus();
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

<body onload="enableAnchor();fntab();">
<html:form styleId="repoDetailStockyardAddForm"  method="post"  action="/repoDetailByStockyardDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
    </logic:present>
<logic:notPresent name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>
<fieldset>
<legend><bean:message key="lbl.repoDetailByStockyard" /></legend>
  <table width="100%">
  
	<tr>
	
		<td><bean:message key="lbl.loanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${requestScope.list[0].loanNo}" /></td>
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${requestScope.list[0].loanId}" />
		 <html:hidden property="repoId" styleClass="text" styleId="repoId" value="${requestScope.list[0].repoId}" />
		
		<td><bean:message key="lbl.customerName" /></td>
		
		<td><html:text property="customer" readonly="true" styleClass="text" styleId="customer" value="${requestScope.list[0].customer}" /></td>
		
		
	</tr>
	
	<tr>
	<td><bean:message key="lbl.product" /></td>
		
		<td><html:text property="product" styleClass="text" readonly="true" styleId="product" value="${requestScope.list[0].product}" /></td>
        <html:hidden property="productId"  styleId="productId" value="${requestScope.list[0].productId}" />

		<td><bean:message key="lbl.scheme" /></td>
		
		<td><html:text property="scheme" styleClass="text" readonly="true" styleId="scheme" value="${requestScope.list[0].scheme}" /></td>
		
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.assetDescription" /></td>
		
		<td><html:text property="assetDescription" readonly="true" styleClass="text" styleId="assetDescription" value="${requestScope.list[0].assetDescription}" /></td>
		 <html:hidden property="assetId"  styleId="assetId" value="${requestScope.list[0].assetId}" />
		
		<td><bean:message key="lbl.assetCost" /></td>
		
		<td><html:text property="assetCost" styleClass="text" readonly="true" styleId="assetCost" value="${requestScope.list[0].assetCost}" /></td>
		
</tr>
	
	<tr>
	
	
		<td><bean:message key="lbl.repossesedBy" /></td>
		
		<td><html:text property="repossesedBy" readonly="true" styleClass="text" styleId="repossesedBy" value="${requestScope.list[0].repossesedBy}" /></td>
	 <td><bean:message key="lbl.agencyName" /></td>
		
		<td><html:text property="agencyName" styleClass="text" readonly="true" styleId="agencyName" value="${requestScope.list[0].agencyName}" /></td>
	</tr>
    
    <tr>
   
		

		<td><bean:message key="lbl.repoAddress1" /></td>
		
		<td><html:text property="repoAddress1" styleClass="text" readonly="true" styleId="repoAddress1" value="${requestScope.list[0].repoAddress1}" /></td>
  
  <td><bean:message key="lbl.repoAddress2" /></td>
		
		<td>
		<html:text property="repoAddress2" styleId="repoAddress2" value="${requestScope.list[0].repoAddress2}" styleClass="text" readonly="true" tabindex="-1"/>
		
		
		</td> 
	</tr>  
    
    <tr>

		<td><bean:message key="lbl.state" /></td>
		
		<td><html:text property="state" styleId="state" value="${requestScope.list[0].state}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
		<td><bean:message key="lbl.district" /></td>
		
		<td><html:text property="district" styleId="district" value="${requestScope.list[0].district}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
	  
	</tr>
	
	<tr>

		<td><bean:message key="lbl.repoFrom" /></td>
		
		<td><html:text property="repoFrom" styleId="repoFrom" value="${requestScope.list[0].repoFrom}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
		<td><bean:message key="lbl.relationToCustomer" /></td>
		
		<td><html:text property="relationToCustomer" styleId="relationToCustomer" value="${requestScope.list[0].relationToCustomer}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.repoDateTime" /></td>
			
		<td>
		 <html:text property="repoDate" readonly="true" styleClass="text3" styleId="repoDate" maxlength="10"  value="${requestScope.list[0].repoDate}" />
		<html:text property="repoTime" readonly="true"  styleId="repoTime" onchange="validateTime('repoTime');" maxlength="5"  styleClass="text5" value="${requestScope.list[0].repoTime}" />&nbsp;(24hrs Format)
		</td>
		
		<td><bean:message key="lbl.stockyard" /></td>
		
		<td><html:text property="stockyard" styleId="stockyard" value="${requestScope.list[0].stockyard}" styleClass="text"  tabindex="-1" readonly="true"/></td>
		
		
	</tr>
	
	<tr>
		<td><bean:message key="lbl.stockyardManager" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="stockyardManager" styleId="stockyardManager" value="${requestScope.list[0].stockyardManager}" styleClass="text"  tabindex="-1"/></td>
		
		<td><bean:message key="lbl.sEntryDateTime" /><span><font color="red">*</font></span></td>
		
		<td>
		 <html:text property="stockyardEntryDate" styleClass="text3" styleId="stockyardEntryDate" maxlength="10"  value="${requestScope.list[0].stockyardEntryDate}" onchange="checkDate('stockyardEntryDate');compareTwoDates('formatD','stockyardEntryDate','bDate','Stockyard date should not be greater than Business Date.');"  />
		<html:text property="stockyardEntryTime"  styleId="stockyardEntryTime" onchange="validateTime('stockyardEntryTime');" maxlength="5"  styleClass="text5" value="${requestScope.list[0].stockyardEntryTime}" />&nbsp;(24hrs Format)
		<input type = "hidden" id="bDate" value = "${userobject.businessdate}"/>
		</td>
	  
	</tr>
	
	<tr>
		
		<td><bean:message key="lbl.repoStockyardRemarks" /><span><font color="red">*</font></span></td>
		
		<td><textarea name = "stockyardRemarks" class="text" id = "stockyardRemarks" maxlength = "500" >${requestScope.list[0].stockyardRemarks}</textarea></td>
	
	</tr>
	
	</table>		


</fieldset>
   <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
    <input type="hidden" name ="updateForwardFlag" id="updateForwardFlag" />
<fieldset>
<legend><bean:message key="lbl.repoCheckListDtl" /></legend>


<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	<td ><b><bean:message key="lbl.assetChecklist" /></b></td>
				<td ><b><bean:message key="lbl.status" /></b></td>
				<td ><b><bean:message key="lbl.remarks" /></b></td>
      		</tr>
      		
      		<% ArrayList<RepoDetailStockyardVo> list = new ArrayList<RepoDetailStockyardVo>();
      		
      		list = (ArrayList<RepoDetailStockyardVo>)request.getAttribute("checkList");
      		
      		if(list!=null)
      		{
      			for(int i=0;i<list.size();i++)
      			{
      				
      				RepoDetailStockyardVo vo = list.get(i);
      				System.out.println("vo.getAcheckList("+i+") : "+vo.getAssetChecklist());
      				System.out.println("vo.getCheckListStatus("+i+") : "+vo.getCheckListStatus());
      				System.out.println("vo.getCheckListRemarks("+i+") : "+vo.getCheckListRemarks());
      		 %>
      		 <tr class="white1" style="width: 25px;">
      	
      		  <td ><input type="hidden" name="assetCheckList" id="assetCheckList<%=i%>" value="<%=vo.getAssetChecklist()%>" />
      		 
      		  <%=vo.getAssetChecklist()%></td>
      		  
      		<td>
      		
      		<html:select property="checkListStatus" styleId='<%="checkListStatus"+i%>'  styleClass="text" value="<%=vo.getCheckListStatus()%>" >
                 <html:option value="">---Select---</html:option>
                <html:option value="YES">Yes</html:option>
                <html:option value="NO">No</html:option>
                <html:option value="NA">NA</html:option>
                
       </html:select>
      		</td>
			
			 <td>
			 <textarea name = "checkListRemarks" id="checkListRemarks<%=i%>"><%=vo.getCheckListRemarks()%></textarea></td>
			
			 </tr>
      		<%}} %>
      		
    		</table>
    </tr>
    
    
    
    <tr><td>
    
    	<%if(list!=null){ %>
       <input type="hidden" name="listSize" id="listSize" value="<%=list.size()%>" />
    <%}%>
     <button type="button" name="update" id="update" title="Alt+V" accesskey="V" onclick="return fnUpdateRepoDetailByStockyard('U');" class="topformbutton2" ><bean:message key="button.save" /></button>
	      <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return fnUpdateRepoDetailByStockyard('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
   	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
   	<input type="hidden" name ="updateForwardFlag" id="updateForwardFlag" />
    <br></td> </tr>

	</table>

     </fieldset>
   </html:form>
  
           
<logic:present name="sms">
<script type="text/javascript">
	
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.RepoDetailSavedSucc" />");
		document.getElementById("repoDetailStockyardAddForm").action="repoDetailByStockyardDispatch.do?method=openEditRepoDetailByStockyard&repoId="+<%=request.getAttribute("repoId")%>;
	    document.getElementById("repoDetailStockyardAddForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="msg.RepoDetailForwSucc" />");
		document.getElementById("repoDetailStockyardAddForm").action="repoDetailByStockyardDispatch.do?method=searchRepoDetailByStockyard";
	    document.getElementById("repoDetailStockyardAddForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
	
</script>
</logic:present>
  </body>
		</html:html>