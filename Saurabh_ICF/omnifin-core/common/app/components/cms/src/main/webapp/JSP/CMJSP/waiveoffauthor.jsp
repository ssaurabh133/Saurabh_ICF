<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
	 	
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/waiveOff.js"></script>
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
	<body onload="enableAnchor();document.getElementById('waiveOffAuthorForm').loanButton.focus();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
	<div id="centercolumn">
	<div id="revisedcontainer">
	<html:form action="/waiveOffAuthorAction" styleId="waiveOffAuthorForm">
	
	<fieldset>	  
	  <legend><bean:message key="lbl.WaiveOffAuthorSearch"/> </legend>         
	   
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		  <tr>
             
             <td><bean:message key="lbl.lan"/> </td>
		    <td width="35%">
			  <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		      <html:text styleClass="text" property="loanAccountNo" readonly="true" styleId="loanAccountNo" maxlength="20" value="" tabindex="-1"/> 
              <!-- <img onclick="openLOVCommon(154,'waiveOffAuthorForm','loanAccountNo','','', '','','','customerName','hCommon','hCommon')" src="<%= request.getContextPath() %>/images/lov.gif"/> -->
              <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(154,'waiveOffAuthorForm','loanAccountNo','userId','loanAccountNo', 'userId','','','customerName','hCommon','hCommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
              <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
               <html:hidden  property="hCommon" styleId="hCommon" value="" />
             </td> 
            
       
		    <td><bean:message key="lbl.CustomerName"/> </td>
		    <td><html:text property="customerName" styleId="customerName" readonly="" value="" styleClass="text" maxlength="50" onkeypress="return isCharKey(event);"/></td>
		    </tr>
			  <tr>
            <td> <bean:message key="lbl.businessPartnerType"/> </td>
		    <td nowrap="nowrap" >
		    
		 
		    					<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="" tabindex="-1"/>
								<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              					<html:hidden property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="" />
              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(45,'waiveOffAuthorForm','businessPartnerType','loanAccountNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerName','businessPartnerTypeDesc');closeLovCallonLov('loanAccountNo');" value=" " styleClass="lovbutton"></html:button>
              					<!-- <img onclick="openLOVCommon(45,'waiveOffAuthorForm','businessPartnerType','loanAccountNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerName','businessPartnerTypeDesc')" src="<%= request.getContextPath() %>/images/lov.gif"/> -->
				
		    		</td>
            	         			
            
		    <td nowrap="nowrap"><bean:message key="lbl.businessPartnerName"/> </td>	    
		    
		    <td nowrap="nowrap">	
		      <html:text property="businessPartnerName" styleId="businessPartnerName" readonly="" value="" styleClass="text" maxlength="50" onkeypress="return isCharKey(event);"/>
							
			 </td>					
   
		  </tr>
		  <tr>
            <td><bean:message key="lbl.AdviceAmount"/> </td>
		    <td nowrap="nowrap" ><label>
		    <html:text property="adviceAmount"  styleId="adviceAmount" value="" styleClass="text" maxlength="22" onkeypress="return isNumberKey(event);"/>
		    </label></td>
		    <td><bean:message key="lbl.waiveOffAmount"/></td>
		    <td nowrap="nowrap" ><html:text property="waivOffAmount" styleId="waivOffAmount" value="" styleClass="text" maxlength="22" onkeypress="return isNumberKey(event);"/></td>
		    </tr>
		    	
			<tr>
				 <td>
				 <bean:message key="lbl.userName" />
				 </td>
				 <td>
				    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
				    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
				    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
				    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
				    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'waiveOffAuthorForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
				 </td>

		    </tr>
		    
		  <tr>
            <td align="left" ><button type="button" name="search" onclick="return searchWaiveOffAuthor()" class="topformbutton2" id="search" title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button></td>
		    <td align="left" >&nbsp;</td>
		    <td align="left" >&nbsp;</td>
		    <td  align="right">
		    
	   </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>
	
	</html:form>
	</div>
 
   <fieldset>	
		<legend><bean:message key="lbl.WaiveOffmakerRecord"/> </legend>    

 		<logic:present name="searchList">
		<logic:notEmpty name="searchList"> 
 <display:table  id="searchList"  name="searchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${searchList[0].totalRecordSize}" requestURI="/WaiveOffAuthorBehindAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="loanAccountNo" titleKey="lbl.lan"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.CustomerName"  sortable="true"  />
	<display:column property="businessPartnerDescription" titleKey="lbl.businessPartnerType"  sortable="true"  />
	<display:column property="businessPartnerName" titleKey="lbl.businessPartnerName"  sortable="true"  />
	<display:column property="adviceAmount" titleKey="lbl.AdviceAmount"  sortable="true"  />
	<display:column property="waivOffAmount" titleKey="lbl.waiveOffAmount"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
	
</display:table>


	</logic:notEmpty>

<logic:empty name="searchList">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
      <td style="width:220px;"><strong><bean:message key="lbl.lan"/> </strong></td>
      <td style="width:250px;"><strong><bean:message key="lbl.CustomerName"/> </strong></td>
      <td style="width:220px;"><strong><bean:message key="lbl.businessPartnerType"/></strong></td>
      <td style="width:220px;"><strong><bean:message key="lbl.businessPartnerName"/></strong></td>
      <td style="width:220px;"><span class="white2" style="width:220px;"><strong><bean:message key="lbl.AdviceAmount"/>  </strong></span></td>
      <td style="width:220px;"><strong><bean:message key="lbl.waiveOffAmount"/></strong></td>
      <td style="width:220px;"><strong><bean:message key="lbl.userName"/></strong></td>
	</tr>
	<tr class="white2">
<td colspan="7"> 
<bean:message key="lbl.noDataFound" />
</td>
</tr>
 </table>    </td>
</tr>

</table>

</logic:empty>
  </logic:present> 

	</fieldset>

  
      <logic:present name="searchWaiveOffAuthorNoData">
 <script type="text/javascript">
    if('<%=request.getAttribute("searchWaiveOffAuthorNoData")%>'=='No Result Found')
	{
		// alert('<bean:message key="lbl.NoDatafound" />');
	}
	else if('<%=request.getAttribute("searchWaiveOffAuthorNoData")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}

 </script>
  </logic:present> 
     
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</body>
</html:html>