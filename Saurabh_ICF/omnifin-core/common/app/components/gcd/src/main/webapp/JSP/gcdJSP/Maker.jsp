<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Maker Details
 	Documentation    :- 
 -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 	<logic:present name="css">
				<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
			<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="js/gcdScript/search_customer_detail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	 
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
%>	
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
	
	<body onload="enableAnchor();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();init_fields();">
	 <input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<form action="" id="maker" method="post">
	                 
    <fieldset>	  
	<legend><bean:message key="indiCorp.details"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
		
	<tr>
					
    <td width="13%">
		   
	<bean:message key="lbl.customer.id"/> </td>
	<td>
	<input type="hidden" id="hcommon" />
	<html:text property="codeId" styleId="codeId" styleClass="text" maxlength="10" value="" readonly="true"></html:text>	
	
	<logic:present name="PCrecStat">
		<input type="hidden" id="recStat" value="P" />
		<html:button property="lovButton" value=" " styleClass="lovbutton" onclick="closeLovCallonLov1();openLOVCommon(131,'maker','codeId','recStat','codeId', 'recStat','','','customerName');" />
	</logic:present>
	<logic:present name="PIrecStat">
		<input type="hidden" id="recStat" value="P" />
		<html:button property="lovbutton" value=" " styleClass="lovbutton" onclick="closeLovCallonLov1();openLOVCommon(132,'maker','codeId','recStat','codeId', 'recStat','','','customerName');" />
	</logic:present>
	<logic:present name="PArecStat">
		<input type="hidden" id="recStat" value="A" />
		<html:button property="lovbutton" value=" " styleClass="lovbutton" onclick="closeLovCallonLov1();openLOVCommon(133,'maker','codeId','recStat','codeId', 'recStat','','','hcommon');" />
	</logic:present>
	<logic:present name="PFrecStat">
		<input type="hidden" id="recStat" value="F" />
		<html:button property="lovbutton" value=" " styleClass="lovbutton" onclick="closeLovCallonLov1();openLOVCommon(134,'maker','codeId','recStat','codeId', 'recStat','','','hcommon');" />
	</logic:present>
		
	</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td width="19%"><bean:message key="customer.name"/> </td>
	<td width="37%"><html:text property="customerName" styleId="customerName" styleClass="text" maxlength="50" value=""></html:text>
	</td>
    </tr>

		<tr>
	      <td align="left" class="form2">
	       <logic:notPresent name="corporate">
	       <logic:notPresent name="individual">
	       <html:hidden property="recStatus" styleId="recStatus" value="A"/>
	      
	       <logic:present name="approve">
	       		<input type="hidden" id="statusCase" name="statusCase" value="" />
	       </logic:present>
	       <logic:notPresent name="approve">
	       
	       <tr>
					
		   <td width="13%">
		   
	      <bean:message key="lbl.searchByStatus"/> </td>
		  <td><div style="float:left;">
	      <select name="statusCase" id="statusCase" class="text">
	      <option value="Approved">Approved</option>
	      <option value="UnApproved">Draft</option>
	      </select>		
	      </div></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td width="19%"></td>
	      <td width="37%">
	      </td>
		  </tr>
		  
	       
	       </logic:notPresent>
	       </logic:notPresent>
	       </logic:notPresent>
	       </td>
	     </tr>
	     <tr>
	     <td colspan="2">
	      <button type="button"  name="search" id="search" class="topformbutton2" accesskey="S" title="Alt+S" onclick="return show_customer_detail();"><bean:message key="button.search" /></button>
	     
		  <logic:present name="corporate">
	      <html:hidden property="recStatus" styleId="recStatus" value="PC"/>
    	  <button type="button"  name="new" class="topformbutton2" id="new" accesskey="N" title="Alt+N" onclick="return newCust();"><bean:message key="button.new" /></button>
	      </logic:present >
	      <logic:present name="individual">
	     <html:hidden property="recStatus" styleId="recStatus" value="PI"/>
      	<button type="button"  name="new" class="topformbutton2" id="new" accesskey="N" title="Alt+N" onclick="return newCust();"><bean:message key="button.new" /></button>
	      </logic:present>
			</td>
		 </tr>
		 </table>
		
	      </td>
	      </tr>
	</table>
</fieldset>	
<br/>

<fieldset id="showlist" ><legend><bean:message key="indiCorp.details"/></legend> 

<logic:present name="updateCustomerAuthorGrid">
<logic:notEmpty name="updateCustomerAuthorGrid">
	<display:table  id="updateCustomerAuthorGrid"  name="updateCustomerAuthorGrid" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${updateCustomerAuthorGrid[0].totalRecordSize}" requestURI="/updateCustomerAuthor.do" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="id" titleKey="customer.id"  sortable="true"  />
		<display:column property="name" titleKey="customer.name"  sortable="true"  />
		<display:column property="custContitution" titleKey="constitution"  sortable="true"  />
		<display:column property="businessSegment" titleKey="businessSegment"  sortable="true"  style="text-align: center"/>
		<display:column property="fCustType" titleKey="customer.type"  sortable="true"  style="text-align: center"/>
	</display:table>				
</logic:notEmpty>
<logic:empty name="updateCustomerAuthorGrid">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr class="white2">
			<td><strong><bean:message key="customer.id" /> </strong></td>
			<td><strong><bean:message key="customer.name" /> </strong></td>
			<td><strong><bean:message key="constitution" /> </strong></td>
			<td><strong><bean:message key="businessSegment" /> </strong></td>
			<td><strong><bean:message key="customer.type" /> </strong></td>
		</tr>
		<tr class="white2">
			<td colspan="5"><bean:message key="lbl.noDataFound" /></td>
		</tr>
		</table>
		</td>
	</tr>				
	</table>
</logic:empty>
</logic:present>
<logic:present name="updateMaker">
<logic:notEmpty name="updateMaker">

	<display:table  id="updateMaker"  name="updateMaker" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${updateMaker[0].totalRecordSize}" requestURI="/showCustomerDetailAction.do?method=getCustomerDetail" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="id" titleKey="customer.id"  sortable="true"  />
		<display:column property="name" titleKey="customer.name"  sortable="true"  />
		<display:column property="custContitution" titleKey="constitution"  sortable="true"  />
		<display:column property="businessSegment" titleKey="businessSegment"  sortable="true"  style="text-align: center"/>
		<display:column property="fCustType" titleKey="customer.type"  sortable="true"  style="text-align: center"/>
	</display:table>				
</logic:notEmpty>
<logic:empty name="updateMaker">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr class="white2">
			<td><strong><bean:message key="customer.id" /> </strong></td>
			<td><strong><bean:message key="customer.name" /> </strong></td>
			<td><strong><bean:message key="constitution" /> </strong></td>
			<td><strong><bean:message key="businessSegment" /> </strong></td>
			<td><strong><bean:message key="customer.type" /> </strong></td>
		</tr>
		<tr class="white2">
			<td colspan="5"><bean:message key="lbl.noDataFound" /></td>
		</tr>
		</table>
		</td>
	</tr>				
	</table>
</logic:empty>
</logic:present>
<logic:present name="updateCustomerMaker">
<logic:notEmpty name="updateCustomerMaker">

	<display:table  id="updateCustomerMaker"  name="updateCustomerMaker" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${updateCustomerMaker[0].totalRecordSize}" requestURI="/updateCustomerMaker.do" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="id" titleKey="customer.id"  sortable="true"  />
		<display:column property="name" titleKey="customer.name"  sortable="true"  />
		<display:column property="custContitution" titleKey="constitution"  sortable="true"  />
		<display:column property="businessSegment" titleKey="businessSegment"  sortable="true"  style="text-align: center"/>
		<display:column property="fCustType" titleKey="customer.type"  sortable="true"  style="text-align: center"/>
	</display:table>				
</logic:notEmpty>
<logic:empty name="updateCustomerMaker">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr class="white2">
			<td><strong><bean:message key="customer.id" /> </strong></td>
			<td><strong><bean:message key="customer.name" /> </strong></td>
			<td><strong><bean:message key="constitution" /> </strong></td>
			<td><strong><bean:message key="businessSegment" /> </strong></td>
			<td><strong><bean:message key="customer.type" /> </strong></td>
		</tr>
		<tr class="white2">
			<td colspan="5"><bean:message key="lbl.noDataFound" /></td>
		</tr>
		</table>
		</td>
	</tr>				
	</table>
</logic:empty>
</logic:present>
<logic:present name="corpogridList">
<logic:notEmpty name="corpogridList">

	<display:table  id="corpogridList"  name="corpogridList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${corpogridList[0].totalRecordSize}" requestURI="/corporateFrame.do" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="id" titleKey="customer.id"  sortable="true"  />
		<display:column property="name" titleKey="customer.name"  sortable="true"  />
		<display:column property="custContitution" titleKey="constitution"  sortable="true"  />
		<display:column property="businessSegment" titleKey="businessSegment"  sortable="true"  style="text-align: center"/>
		<display:column property="fCustType" titleKey="customer.type"  sortable="true"  style="text-align: center"/>
	</display:table>				
</logic:notEmpty>
<logic:empty name="corpogridList">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr class="white2">
			<td><strong><bean:message key="customer.id" /> </strong></td>
			<td><strong><bean:message key="customer.name" /> </strong></td>
			<td><strong><bean:message key="constitution" /> </strong></td>
			<td><strong><bean:message key="businessSegment" /> </strong></td>
			<td><strong><bean:message key="customer.type" /> </strong></td>
		</tr>
		<tr class="white2">
			<td colspan="5"><bean:message key="lbl.noDataFound" /></td>
		</tr>
		</table>
		</td>
	</tr>				
	</table>
</logic:empty>
</logic:present>
<logic:present name="indigridList">
<logic:notEmpty name="indigridList">

	<display:table  id="indigridList"  name="indigridList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${indigridList[0].totalRecordSize}" requestURI="/individualFrame.do" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="id" titleKey="customer.id"  sortable="true"  />
		<display:column property="name" titleKey="customer.name"  sortable="true"  />
		<display:column property="custContitution" titleKey="constitution"  sortable="true" />
		<display:column property="fCustType" titleKey="customer.type"  sortable="true"  style="text-align: center"/>
	</display:table>				
</logic:notEmpty>
<logic:empty name="indigridList">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr class="white2">
			<td><strong><bean:message key="customer.id" /> </strong></td>
			<td><strong><bean:message key="customer.name" /> </strong></td>
			<td><strong><bean:message key="constitution" /> </strong></td>
			<td><strong><bean:message key="customer.type" /> </strong></td>
		</tr>
		<tr class="white2">
			<td colspan="5"><bean:message key="lbl.noDataFound" /></td>
		</tr>
		</table>
		</td>
	</tr>				
	</table>
</logic:empty>
</logic:present>
<logic:present name="copoCustomer_detail">
<logic:notEmpty name="copoCustomer_detail">

	<display:table  id="copoCustomer_detail"  name="copoCustomer_detail" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${copoCustomer_detail[0].totalRecordSize}" requestURI="/showCustomerDetailAction.do?method=getCustomerDetail" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="id" titleKey="customer.id"  sortable="true"  />
		<display:column property="name" titleKey="customer.name"  sortable="true"  />
		<display:column property="custContitution" titleKey="constitution"  sortable="true"  />
		<display:column property="businessSegment" titleKey="businessSegment"  sortable="true"  style="text-align: center"/>
		<display:column property="fCustType" titleKey="customer.type"  sortable="true"  style="text-align: center"/>
	</display:table>				
</logic:notEmpty>
<logic:empty name="copoCustomer_detail">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr class="white2">
			<td><strong><bean:message key="customer.id" /> </strong></td>
			<td><strong><bean:message key="customer.name" /> </strong></td>
			<td><strong><bean:message key="constitution" /> </strong></td>
			<td><strong><bean:message key="businessSegment" /> </strong></td>
			<td><strong><bean:message key="customer.type" /> </strong></td>
		</tr>
		<tr class="white2">
			<td colspan="5"><bean:message key="lbl.noDataFound" /></td>
		</tr>
		</table>
		</td>
	</tr>				
	</table>
</logic:empty>
</logic:present>
<logic:present name="indiCustomer_detail">
<logic:notEmpty name="indiCustomer_detail">

	<display:table  id="indiCustomer_detail"  name="indiCustomer_detail" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${indiCustomer_detail[0].totalRecordSize}" requestURI="/showCustomerDetailAction.do?method=getCustomerDetail" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="id" titleKey="customer.id"  sortable="true"  />
		<display:column property="name" titleKey="customer.name"  sortable="true"  />
		<display:column property="custContitution" titleKey="constitution"  sortable="true" />
		<display:column property="fCustType" titleKey="customer.type"  sortable="true"  style="text-align: center"/>
	</display:table>				
</logic:notEmpty>
<logic:empty name="indiCustomer_detail">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr class="white2">
			<td><strong><bean:message key="customer.id" /> </strong></td>
			<td><strong><bean:message key="customer.name" /> </strong></td>
			<td><strong><bean:message key="constitution" /> </strong></td>
			<td><strong><bean:message key="customer.type" /> </strong></td>
		</tr>
		<tr class="white2">
			<td colspan="5"><bean:message key="lbl.noDataFound" /></td>
		</tr>
		</table>
		</td>
	</tr>				
	</table>
</logic:empty>
</logic:present>
</fieldset>

 </form>
</div>

</div>


<logic:present name="sms">
<script type="text/javascript">
//alert("hello");

	if('<%=request.getAttribute("sms")%>'=='NoResult')
	{
		alert('<bean:message key="lbl.noResultFound" />');
	}
	else if('<%=request.getAttribute("sms")%>'=='forApprove')
	{
		alert('<bean:message key="lbl.alreadGoneForApprove" />');
	}
	else if('<%=request.getAttribute("sms")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}
	
	</script>
</logic:present>
   	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</body>
</html>
