<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
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
	 	
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/cmLoanInitjs.js"></script>
   
		<script type="text/javascript">
	        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      

		
		
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
		</script>
<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>			
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
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('sourcingForm').loanButton.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/holdInstrumentBankingMakerMain" method="post" styleId="sourcingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
    <fieldset>	
     
    <logic:present name="holdinstrumentCapturingMakerSearch"> 
	<legend><bean:message key="lbl.holdinstrumentCapturingMakerSearch"/></legend>         
	 </logic:present>  
	 
	  <logic:present name="holdinstrumentCapturingAuthorSearch"> 
	<legend><bean:message key="lbl.holdinstrumentCapturingAuthorSearch"/></legend>         
	 </logic:present> 
	 
	  <logic:present name="releaseinstrumentCapturingMakerSearch"> 
	<legend><bean:message key="lbl.releaseinstrumentCapturingMakerSearch"/></legend>         
	 </logic:present> 
	 
	  <logic:present name="releaseinstrumentCapturingAuthorSearch"> 
	<legend><bean:message key="lbl.releaseinstrumentCapturingAuthorSearch"/></legend>         
	 </logic:present> 
	 
	 
	 
	 
	 
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td><input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		 <tr>			
		   <td><bean:message key="lbl.loanNumber"/></td>
		   
		    <logic:present name="maker">
		   <td>
	<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100"  readonly="true"/>   
	<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
    <!-- <img onclick="openLOVCommon(



,'sourcingForm','loanAccNo','','', '','','','customerName')" SRC="<%= request.getContextPath()%>/images/lov.gif"> -->
    <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(116,'sourcingForm','loanAccNo','userId','loanAccNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>     
           
           
           </td>
           </logic:present>
           
           
           <logic:present name="author">
		   <td>
	<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100"  readonly="true"/>   
	<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
    <!-- <img onclick="openLOVCommon(600,'sourcingForm','loanAccNo','','', '','','','customerName')" SRC="<%= request.getContextPath()%>/images/lov.gif"> -->
    <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(117,'sourcingForm','loanAccNo','userId','loanAccNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>        
           
           
           </td>
           </logic:present>
           
           <logic:present name="release">
		   <td>
	<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100" readonly="true"/>   
	<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
    <!-- <img onclick="openLOVCommon(602,'sourcingForm','loanAccNo','','', '','','','customerName')" SRC="<%= request.getContextPath()%>/images/lov.gif"> -->
    <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(602,'sourcingForm','loanAccNo','userId','loanAccNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>        
           
           </td>
           </logic:present>
           
           <logic:present name="releaseAuthor">
		   <td>
	<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100"  readonly="true"/>   
	<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
    <!-- <img onclick="openLOVCommon(600,'sourcingForm','loanAccNo','','', '','','','customerName')" SRC="<%= request.getContextPath()%>/images/lov.gif"> -->
    <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(600,'sourcingForm','loanAccNo','userId','loanAccNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>      
           
           
           </td>
           </logic:present>
           
           <td><bean:message key="lbl.customerName"/></td>
	<td>
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="50"    /> 
	
	</td>
	</tr>
           
          <tr> 
          
          
          <td>
		   <bean:message key="lbl.bank"></bean:message>
	    </td>
	   
	   
	      <logic:present name="maker"> 
	   
	 <td><div style="float:left;">		 
 			 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100" readonly="true" />
    		 <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
      		 <!-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
      		 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(166,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>  	 
     </div></td>
     
           </logic:present>
           
         <logic:present name="author"> 
	   
	 <td><div style="float:left;">		 
 			 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100" readonly="true" />
    		 <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
      		 <!-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
      		 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(167,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>  	 
     </div></td>
     
           </logic:present>
           
          <logic:present name="release"> 
           <td><div style="float:left;">		 
 			 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100" readonly="true" />
    		 <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
      		 <!-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
      		 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(166,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>  	 
     </div></td>
     
           </logic:present>
           
            <logic:present name="releaseAuthor">
              <td><div style="float:left;">		 
 			 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100" readonly="true" />
    		 <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
      		 <!-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
      		 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(167,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>  	 
     </div></td>
     
           </logic:present>
           
           
           
          <td><bean:message key="lbl.branch"></bean:message></td>
		<td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  readonly="true"/>
         <html:hidden  property="lbxBranchID" styleId="lbxBranchID" />
         <!-- <img onclick="openLOVCommon(8,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','lbxBranchID')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
         <html:button property="branchButton" styleId="branchButton" onclick="openLOVCommon(8,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','lbxBranchID');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button>
       </td>	
          
        	  
        	  </tr>
	    <tr>
		<td><bean:message key="lbl.instrumentMode"/></td>
	       <td>
	<html:select property="instrumentType" styleId="instrumentType" styleClass="text">
	            <html:option value="">--Select--</html:option>
		        <html:option value="Q">PDC</html:option>
		        <html:option value="E">ECS</html:option>
		        <html:option value="DIR">Direct Debit</html:option>
		        <html:option value="H">ACH</html:option>
   </html:select> 
          </td>
	
 
		   <td><bean:message key="lbl.product"/></td>    
			<td>
				<html:text styleClass="text" property="product" styleId="product" maxlength="100"  readonly="true"/>   
				<html:hidden property="lbxProductID" styleId="lbxProductID" />
			    <!-- <img onclick="openLOVCommon(17,'sourcingForm','product','','', '','','','lbxProductID')" SRC="<%= request.getContextPath()%>/images/lov.gif"> -->
			    <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(17,'sourcingForm','product','','', '','','','lbxProductID');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
		   </td>     
    	 </tr>
			
		 <logic:present name="maker">
		 
		 <tr>
		 <td align="left">  
	               <button type="button"  id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchholdInstrumentMaker('<bean:message key="lbl.plsSelAtLeastOneCriteria"/>');"><bean:message key="button.search" /></button>
			</td>
			<td align="left" >&nbsp;</td>
			<td align="left" >&nbsp;</td>
	        <td align="left" >&nbsp;</td>
	        
		</tr>
       </logic:present>
        <logic:present name="author">
		 <tr>
		 <td align="left">  
	        
	         <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchholdInstrumentAuthor('<bean:message key="lbl.plsSelAtLeastOneCriteria"/>');"><bean:message key="button.search" /></button>

			</td>
			<td align="left" >&nbsp;</td>
			<td align="left" >&nbsp;</td>
	        <td align="left" >&nbsp;</td>
	        
		</tr>
       </logic:present>
       <logic:present name="release">
		 <tr>
		 <td align="left">
           <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchreleaseMaker('<bean:message key="lbl.plsSelAtLeastOneCriteria"/>');"><bean:message key="button.search" /></button>

	      </td>
		   <td align="left" >&nbsp;</td>
		   <td align="left" >&nbsp;</td>
           <td align="left" >&nbsp;</td>
           
		</tr>
		 </logic:present> 
		   <logic:present name="releaseAuthor">
		 <tr>
		  <td align="left">
           
           <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchreleaseAuthor('<bean:message key="lbl.plsSelAtLeastOneCriteria"/>');"><bean:message key="button.search" /></button>

	      </td>
		   <td align="left" >&nbsp;</td>
		   <td align="left" >&nbsp;</td>
           <td align="left" >&nbsp;</td>
          
		</tr>
		 </logic:present> 
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	
</html:form>
	</div>
	
	
	
	
<fieldset>	

		 <logic:present name="holdinstrumentCapturingMakerSearch"> 
	<legend><bean:message key="lbl.holdinstrumentCapturingMakerRecords"/></legend>   
	</logic:present>
   
  	<logic:present name="holdinstrumentCapturingAuthorSearch"> 
	<legend><bean:message key="lbl.holdinstrumentCapturingAuthorRecords"/></legend>         
	</logic:present> 
	
	  <logic:present name="releaseinstrumentCapturingMakerSearch"> 
	<legend><bean:message key="lbl.releaseinstrumentCapturingMakerRecords"/></legend>         
	 </logic:present> 
	 
	  <logic:present name="releaseinstrumentCapturingAuthorSearch"> 
	<legend><bean:message key="lbl.releaseinstrumentCapturingAuthorRecords"/></legend>         
	 </logic:present> 
  
  <logic:present name="holdinstrumentCapturingMakerSearch">
<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/holdInstrumentBankingMakerMain.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanAccNo" titleKey="lbl.loanNumber"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
    <display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.loanScheme"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="holdUser" titleKey="lbl.holdUser"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
      <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>
 <td><strong><bean:message key="lbl.customerName"/></strong> </td>
        <td><b><bean:message key="lbl.loanAmount"/></b></td>
        <td><strong><bean:message key="lbl.loanApprovalDate"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.branch"/></strong></td>
        <td><strong><bean:message key="lbl.holdUser"/></strong></td>
	</tr>
	<tr class="white2">
<td colspan="9">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
	
 </table>    </td>
</tr>

</table>


</logic:empty>
  </logic:present>
         
         <logic:present name="holdinstrumentCapturingAuthorSearch">
		<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/holdInstrumentBankingMakerMain.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanAccNo" titleKey="lbl.loanNumber"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
    <display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.loanScheme"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="userName" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
      <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>
 <td><strong><bean:message key="lbl.customerName"/></strong> </td>
        <td><b><bean:message key="lbl.loanAmount"/></b></td>
        <td><strong><bean:message key="lbl.loanApprovalDate"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.branch"/></strong></td>
        <td><strong><bean:message key="lbl.userName"/></strong></td>
	</tr>
	<tr class="white2">
<td colspan="8">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
	
 </table>    </td>
</tr>

</table>


</logic:empty>
  </logic:present>
    
         <logic:present name="releaseinstrumentCapturingMakerSearch">
		<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/holdInstrumentBankingMakerMain.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanAccNo" titleKey="lbl.loanNumber"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
    <display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.loanScheme"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="userName" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
      <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>
 <td><strong><bean:message key="lbl.customerName"/></strong> </td>
        <td><b><bean:message key="lbl.loanAmount"/></b></td>
        <td><strong><bean:message key="lbl.loanApprovalDate"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.branch"/></strong></td>
        <td><strong><bean:message key="lbl.userName"/></strong></td>

	</tr>
	<tr class="white2">
<td colspan="8"> 
<bean:message key="lbl.noDataFound" />
</td>
</tr>
	
 </table>    </td>
</tr>

</table>

</logic:empty>
  </logic:present>         
         <logic:present name="releaseinstrumentCapturingAuthorSearch">
		<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/holdInstrumentBankingMakerMain.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanAccNo" titleKey="lbl.loanNumber"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
    <display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.loanScheme"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="userName" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
      <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>
		 <td><strong><bean:message key="lbl.customerName"/></strong> </td>
        <td><b><bean:message key="lbl.loanAmount"/></b></td>
        <td><strong><bean:message key="lbl.loanApprovalDate"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.branch"/></strong></td>
        <td><strong><bean:message key="lbl.userName"/></strong></td>
	</tr>
	<tr class="white2">
<td colspan="8"> 
<bean:message key="lbl.noDataFound" />
</td>
</tr>
	
 </table>    </td>
</tr>

</table>

</logic:empty>
  </logic:present>
  
	</fieldset>

<logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 //alert("Data not Found ");
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/holdInstrumentBankingMaker.do";
	    document.getElementById("sourcingForm").submit();
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='AuthorLocked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/holdInstrumentBankingAuthor.do";
	    document.getElementById("sourcingForm").submit();
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='ReleaseMLocked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/releaseInstrumentMaker.do";
	    document.getElementById("sourcingForm").submit();
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='ReleaseALocked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/releaseInstrumentAuthor.do";
	    document.getElementById("sourcingForm").submit();
	}
	</script>
	</logic:present>
	
</div>
</body>
</html:html>