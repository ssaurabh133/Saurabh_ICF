<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/manualAdviceMaker.js"></script>
		  <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		  <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

		<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>	

	 		<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>		
	<script type="text/javascript">
  
		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
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
	<body onload="enableAnchor();document.getElementById('manualAdviceSerchForm').loanButton.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
	
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>

	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/manualAdviceSearchProcessAction" method="post" styleId="manualAdviceSerchForm">
		  
	   <fieldset>
	  
	<legend>
	 <logic:present name="manualApprove">
	   <bean:message key="msg.manualAdviceAuthor"/>
	   </logic:present>	  
	   <logic:notPresent name="manualApprove">
	   <bean:message key="msg.manualAdviceMaker"/>
	   
	    </logic:notPresent>
	</legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath() %>"/>
		<tr>
					
		   <td width="20%">
	<tr>	   
 <td><bean:message key="lbl.loanAccountNumber"></bean:message><font color="red">*</font> </td>
		   <td>
		<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />  
		
		<html:text styleClass="text" property="loanAccountNo" readonly="true" styleId="loanAccountNo" maxlength="20" value="" tabindex="-1"/> 
		<logic:present name="manualApprove">
			<html:button property="loanButton" styleId="loanButton" onclick="closeLovCallonLov1();openLOVCommon(152,'manualAdviceSerchForm','loanAccountNo','userId','loanAccountNo', 'userId','','','customerName')" value=" " styleClass="lovbutton"></html:button>  
			<!-- <img onclick="openLOVCommon(152,'','loanAccountNo','userId','loanAccountNo', 'userId','','','customerName')" src="<%= request.getContextPath() %>/images/lov.gif"/> -->
		</logic:present>
		
		<logic:notPresent name="manualApprove">
			<!-- <img onclick="openLOVCommon(151,'','loanAccountNo','','', '','','','customerName')" src="<%= request.getContextPath() %>/images/lov.gif"/> -->
			<html:button property="loanButton" styleId="loanButton" onclick="closeLovCallonLov1();openLOVCommon(151,'manualAdviceSerchForm','loanAccountNo','userId','loanAccountNo', 'userId','','','customerName')" value=" " styleClass="lovbutton"></html:button>
		</logic:notPresent>
              
              <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
               <html:hidden  property="hCommon" styleId="hCommon" value="" />
		
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message></td>
	<td >
	<html:text property="customersName"  styleClass="text" styleId="customerName" maxlength="100" value="" ></html:text>
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	</td>
		</tr>
	  <tr>
		
		   <td>		   
	<bean:message key ="lbl.businessPartnerType"></bean:message>	   
	 </td>
			<td>	
				
								<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              					<html:hidden property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="" />
		    					<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="" tabindex="-1"/>
								<html:button property="bpButton" styleId="bpButton" onclick="closeLovCallonLov1();openLOVCommon(142,'manualAdviceSerchForm','businessPartnerType','loanAccountNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerName','businessPartnerTypeDesc');closeLovCallonLov('loanAccountNo');" value=" " styleClass="lovbutton"></html:button>
              					<!-- <img onclick="openLOVCommon(142,'manualAdviceSerchForm','businessPartnerType','loanAccountNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerName','businessPartnerTypeDesc')" src="<%= request.getContextPath() %>/images/lov.gif"/> -->


				</td>     
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
				<html:text property="businessPartnerName" styleId="businessPartnerName" readonly="" value="" styleClass="text" maxlength="50" onkeypress="return isCharKey(event);"/>
			</td>		</tr>
		   <tr>
        <td><bean:message key="lbl.chargeCode"/></td>
		<td>
		 <input type="hidden" name="lbxCharge" id="lbxCharge" size="20" value=""/>
		<html:text property="chargeCode" styleClass="text" styleId="chargeCode" value="" readonly="true" maxlength="100" size="20" tabindex="-1"/>
        
		 <html:button property="chargeButton" styleId="chargeButton" onclick="openLOVCommon(159,'manualAdviceSerchForm','chargeCode','businessPartnerTypeDesc|loanAccountNo','lbxCharge', 'businessPartnerType|lbxLoanNoHID','Select BP Type LOV First|Select Loan Account LOV First','','chargeAmount');;closeLovCallonLov('businessPartnerTypeDesc');" value=" " styleClass="lovbutton"></html:button>
         <!-- <img src="<%= request.getContextPath()%>/images/lov.gif" onclick="return openLOVCommon(159,'manualAdviceSerchForm','chargeCode','businessPartnerTypeDesc|loanAccountNo','lbxCharge', 'businessPartnerType|lbxLoanNoHID','Select BP Type LOV First|Select Loan Account LOV First','','chargeAmount');"/> -->
		

		</td>
        <td><bean:message key="lbl.chargeAmount"/></td>
		<td><html:text property="chargeAmount" styleClass="text" styleId="chargeAmount" value="" maxlength="22" size="10" onkeypress="return isNumberKey(event);" onchange="totAdviceAmount();"/></td> 
       </tr>
       
       	
			<tr>
			 <td>
			 <bean:message key="lbl.userName" />
			 </td>
			 <logic:present name="manualApprove">
			 <td>
			    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
			    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
			    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
			    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'manualAdviceSerchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			 </td>
			 </logic:present>
			 <logic:notPresent name="manualApprove">
			 <td>
			    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
			    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
			    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
			    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'manualAdviceSerchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			 </td>
			 </logic:notPresent>

		    </tr>
	  
	   
		  
            <td>
 
<!--	     <logic:present name="fromAuthor">-->
<!--	     <html:button property="button2" styleClass="topformbutton2" value="Search" onclick="return authorSearch();"></html:button>-->
<!--	     </logic:present>-->
<!--	     <logic:notPresent name="fromAuthor">-->
	     <button type="button" name="button2" class="topformbutton2"  title="Alt+S" accesskey="S"  onclick="return manualMakersearch();"><bean:message key="button.search" /></button>
	     <logic:notPresent name="manualApprove">
	          <button type="button" name="button2" class="topformbutton2"  title="Alt+N" accesskey="N" onclick="location.href='manualAdviceCreationBehindAction.do?newManual=N'"><bean:message key="button.new" /></button>
	     </logic:notPresent>
<!--	     </logic:notPresent>-->
		</td>
		 <td align="left" >&nbsp;</td>
		   <td align="left" >&nbsp;</td>
            <td align="left" >&nbsp;</td>
		  </tr>
		</table>
		
	      </td>
	</tr>
	
	</table>
	 
	</fieldset>	
	
 <fieldset>	
  
   <legend><bean:message key="lbl.deals"/></legend> 
  
	<logic:present name="true">
	 <logic:present name="list">

<logic:notEmpty name="list"> 
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/manualAdviceSearchBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanNo" titleKey="lbl.loanAccountNumber"  sortable="true"  />
    <display:column property="customersName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="bpTypeDesc" titleKey="lbl.businessPartnerType"  sortable="true"  />
	<display:column property="bpNameDesc" titleKey="lbl.businessPartnerName"  sortable="true"  />
	<display:column property="chargeCodeDesc" titleKey="lbl.chargeCode"  sortable="true"  />
	<display:column property="chargeAmount" titleKey="lbl.chargeAmount"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>

<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td  style="width:220px;"><b><bean:message key="lbl.loanAccountNumber"/></b></td>
		<td  style="width:220px;"><b><bean:message key="lbl.customerName"/></b></td>
        <td style="width:250px;"><b><bean:message key="lbl.businessPartnerType"/></b></td>
        <td style="width:220px;"><b><bean:message key="lbl.businessPartnerName"/></b></td>
		<td style="width:220px;"><b><bean:message key="lbl.chargeCode"/></b></td>
		<td  style="width:220px;"><b><bean:message key="lbl.chargeAmount"/> </b></td>
		<td  style="width:220px;"><b><bean:message key="lbl.userName"/> </b></td>
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
  </logic:present>
   <logic:present name="authordetailList">

<logic:notEmpty name="authordetailList"> 
  <display:table  id="authordetailList"  name="authordetailList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${authordetailList[0].totalRecordSize}" requestURI="/manualAdviceSearchBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanNo" titleKey="lbl.loanAccountNumber"  sortable="true"  />
    <display:column property="customersName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="bpTypeDesc" titleKey="lbl.businessPartnerType"  sortable="true"  />
	<display:column property="bpNameDesc" titleKey="lbl.businessPartnerName"  sortable="true"  />
	<display:column property="chargeCodeDesc" titleKey="lbl.chargeCode"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>

<logic:empty name="authordetailList">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td  style="width:220px;"><b><bean:message key="lbl.loanAccountNumber"/></b></td>
		<td  style="width:220px;"><b><bean:message key="lbl.customerName"/></b></td>
        <td style="width:250px;"><b><bean:message key="lbl.businessPartnerType"/></b></td>
        <td style="width:220px;"><b><bean:message key="lbl.businessPartnerName"/></b></td>
		<td style="width:220px;"><b><bean:message key="lbl.chargeCode"/></b></td>
		<td style="width:220px;"><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	</tr>
<tr class="white2">
<td colspan="6"> 
<bean:message key="lbl.noDataFound" />
</td>
</tr>
 </table>    </td>

</table>
</logic:empty>
</logic:present>
	</fieldset>
  
  </html:form>
   
</div>

</div>

<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='N')
	{
		// alert('<bean:message key="lbl.noDataFound" />');

		
	}
    else if('<%=request.getAttribute("msg")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}

</script>
</logic:present>
</body>
</html>