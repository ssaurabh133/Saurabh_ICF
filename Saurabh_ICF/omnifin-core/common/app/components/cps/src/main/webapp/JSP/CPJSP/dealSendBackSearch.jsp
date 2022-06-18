<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page language="java"%>
	<%@ page session="true"%>
	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
	<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <title>Insert title here</title> -->

<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		
		
	
	<script type="text/javascript">
  			function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>
	
	
	<body onload="enableAnchor();document.getElementById('searchForCPForm').dealNoButton.focus();init_fields();">
	
	<div id="centercolumn">
	 
	<div id="revisedcontainer">
	
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:form action="/searchCPBehindAction" method="post" styleId="searchForCPForm">
	 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>

		      <fieldset>	  
	    <legend><bean:message key="lbl.search"/></legend>         
	   
	   <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		  
		   <tr>
		   
            <td><bean:message key="lbl.dealNo"></bean:message> </td>
		     <td>
		     <html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="20"  value="${dealNo}" readonly="true" tabindex="-1"/>
             <html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(6088,'searchForCPForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
             <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${dealIdfromCM}" />
            <!--   <img onclick="openLOVCommon(123,'paymentMakerSearch','loanAccountNumber','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/>-->
        
		  </td>
		  

	      <td><bean:message key="lbl.customerName"></bean:message></td>
          <td >
	      <html:text property="customerName"  styleClass="text" styleId="customerName" value="${customerName}" maxlength="50" readonly="true"></html:text>
		  </td>
		  </tr>
		  
	        <tr>
		    <td>

	      <button type="button" class="topformbutton2" id="search" onclick="return onsearchDealSendBack('<bean:message key="msg.DealNo"/>');" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
	      
	      </td>
		  </tr>
		 </table>	
	     </td>	
	     </tr>
	
	</table>
	 
	</fieldset>	
	
		
	 
          <logic:present name="true">
	
		
     <fieldset>	
		 <legend><bean:message key="lbl.dealMovementDetails"/></legend>  

         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td><strong><bean:message key="lbl.seqNo"/></strong></td>
        				<td><strong><bean:message key="lbl.stage"/></strong></td>
						<td><strong><bean:message key="lbl.dealReceived"/></strong></td>
						<td><strong><bean:message key="lbl.dealForwarded"/></strong></td>
						<td><strong><bean:message key="lbl.action"/></strong></td>
        				<td><strong><bean:message key="lbl.dealForwardedUser"/></strong></td>
    				</tr>
    				<logic:present name="dealMovementList">
    				
		 			<logic:iterate name="dealMovementList" id="subDealMovementList">
		 			<logic:equal name="subDealMovementList" property="action" value="INITIATED">
						<tr bgcolor="#8CD1E6">
						
			     			<td>${subDealMovementList.seqNo}</td>
				 			<td>${subDealMovementList.stage}</td>
				 			<td>${subDealMovementList.dealReceived}</td>
				 			<td>${subDealMovementList.dealForwarded}</td>
				 			<td>${subDealMovementList.action}</td>
				 			<td>${subDealMovementList.dealForwardedUser}</td>
				 			
				 			
				 			
				 		</tr>
			 		</logic:equal>
			 		<logic:equal name="subDealMovementList" property="action" value="COMPLETED">
						<tr bgcolor="#aaff91">
						
			     			<td>${subDealMovementList.seqNo}</td>
				 			<td>${subDealMovementList.stage}</td>
				 			<td>${subDealMovementList.dealReceived}</td>
				 			<td>${subDealMovementList.dealForwarded}</td>
				 			<td>${subDealMovementList.action}</td>
				 			<td>${subDealMovementList.dealForwardedUser}</td>
				 			
				 		</tr>
			 		</logic:equal>
			 		<logic:equal name="subDealMovementList" property="action" value="PENDING">
						<tr  bgcolor="#E0E04E">
						
			     			<td>${subDealMovementList.seqNo}</td>
				 			<td>${subDealMovementList.stage}</td>
				 			<td>${subDealMovementList.dealReceived}</td>
				 			<td>${subDealMovementList.dealForwarded}</td>
				 			<td>${subDealMovementList.action}</td>
				 			<td>${subDealMovementList.dealForwardedUser}</td>
				 			
				 		</tr>
			 		</logic:equal>
	    			</logic:iterate>
	   				</logic:present>
 				</table>
			</td>
		</tr>
	</table>

	</fieldset>
	
	

	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
	<td><bean:message key="lbl.sendBackStage" />
	<span><font style="color: red;"> *</font></span>
	</td>
        	<td>
        	<%-- <html:select property="sendBackStage" styleId="sendBackStage" styleClass="text" onchange="showCheckBox1();" value="${leadInfo[0].sendBackStage}"  >
          	  	<html:option value="">---Select---</html:option>
				<logic:present name="workFlowStage">
          	  		<html:optionsCollection name="workFlowStage" label="name" value="id" />
          	  	</logic:present>
          	  
          	  	</html:select> --%>
<%-- 		    	 <html:text styleClass="text" property="lbxsendBackStage" styleId="lbxsendBackStage" maxlength="20"  value="" readonly="true" tabindex="-1"/> --%>
		    	 <select id="lbxsendBackStage"  name="lbxsendBackStage" size="5" multiple="multiple"  value="" style="width: 150px"></select>
          	  	<html:button property="sendBackStageButton" styleId="sendBackStageButton" onclick="return openMultiSelectLOVCommon(6069,'searchForCPForm','lbxsendBackStage','','', '','','','sendBackStage')" value=" " styleClass="lovbutton"> </html:button>
				<html:hidden property="sendBackStage" styleId="sendBackStage" value="" />
        	        	
        	</td>
	
	 <td >

													</td>
													<td>
											
													</td>

	
	 <td><bean:message key="lbl.senbackRemark"></bean:message>
	 <span><font style="color: red;"> *</font></span>
	 </td>
	         <td>
	         <textarea name="remarks" id="remarks" maxlength="1000" class="text" property="remarks" styleId="remarks" value="${leadInfo[0].remarks}"></textarea>      
	        </td>
	
	</tr>
	
	 <tr>
		   <td >
												
		    <button type="button" name="saveButton" id="saveButton" onclick="saveDealSendBack();" title="Alt+V" accesskey="V" class="topformbutton2" ><bean:message key="button.save" /></button>
											
			</td>
			
			
		   </tr>
	
	</table>
	</fieldset>
	
        </logic:present>
		
		
		<logic:present name="sms">
      
			<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.approveUnder" />");
	    parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=F';
 
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.rejectUnder" />");
		//parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=F';
		
	}
	else if('<%=request.getAttribute("sms")%>'=='P')
	{
		alert("<bean:message key="lbl.revertUnder" />");		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='X')
	{
		alert('${addStatus }');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='U')
	{
		alert("<bean:message key="lbl.requirmentUnder" />");
		
	}
	</script>
		</logic:present>
		
  </html:form>
  <logic:present name="datalist">
		<script type="text/javascript">
			alert("<bean:message key="lbl.noDataFound" />");	
		
		</script>
		</logic:present>	
</div>



</div>
</body>
	</head>
</head>
<body>

</body>
</html>