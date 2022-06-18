<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
		
	
	</head>
	<body onload="enableAnchor();init_fields();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/creditListAction" method="post" styleId="CreditForm">
	
	<input type="hidden" name="contextPath" id="contextPath" value="<%=path %>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
		  
	   <fieldset>	  
	<legend><bean:message key="lbl.accDetail"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					
		   <td width="20%">
		   
	<bean:message key="lbl.dealNo"/></td>
	<td width="35%">
	
	     <html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" value=""/>
		 <html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
		     
            <logic:equal value="4000106" name="common">
               <img onclick="openLOVCommon(85,'CreditForm','dealNo','','', '','','','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif" />
            </logic:equal>
            <logic:equal value="4000111" name="common">
               <img onclick="openLOVCommon(102,'CreditForm','dealNo','','', '','','','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif" />
            </logic:equal> 		  
	
	</td>
	<td><bean:message key="lbl.loanNumber"/> </td>
	<td>
	         <input type="hidden" name="hCommon" id="hCommon"/>   
	         <html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" value=""/>
          		<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
          	<logic:equal name="common" value="4000106">
                <img onclick="openLOVCommon(73,'CreditForm','loanNo','dealNo','lbxLoanNoHID', 'lbxDealNo','Select Deal LOV','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" />
            </logic:equal>
            <logic:equal name="common" value="4000111">
                <img onclick="openLOVCommon(77,'CreditForm','loanNo','dealNo','lbxLoanNoHID', 'lbxDealNo','Select Deal LOV','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" />
            </logic:equal>
         </td>				
	        
		</tr>
	  <tr>
		<td>
		   
	<bean:message key="lbl.agrementDate"/></td>
			<td  > 
	<html:text styleClass="text" property="agrementDate" styleId="agrementDate" value="" maxlength="10" /> </td>
		   <td>
		   
		   
	<bean:message key="lbl.customerName"/> </td>
			<td  > 
	<html:text styleClass="text" property="customerName" styleId="customerName" value=""  maxlength="50" /> </td>     
	 </tr>
		 <tr>
				  
		   <td>
		   
	<bean:message key="lbl.product"/>  </td>
		<td > 
		 <html:text  property="product" styleId="product" styleClass="text" readonly="true" />
         <html:hidden  property="lbxProductID" styleId="lbxProductID" />
        <img onclick="openLOVCommon(86,'','product','','', '','','','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif" /> 	 
		</td>
		 <td><bean:message key="lbl.scheme"/>  </td>
		 <td  > 
	      <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" value=""/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme" value="" />
                <img onclick="openLOVCommon(22,'CreditForm','scheme','product','lbxscheme', 'lbxProductID','Select Product LOV','','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif" />				
	     </td>
		</tr>
		
		<tr>

	      <td align="right" class="form2" colspan="4">
	      
	         <logic:equal name="common" value="4000106">
		            <button type="button"  title="Alt+S" accesskey="S"  name="search"  id="button" class="topformbutton2" onclick="return newSearchLoan('P');"><bean:message key="button.save" /></button>
		     </logic:equal>
		     
		     <logic:equal name="common" value="4000111">
		            <button type="button"  title="Alt+S" accesskey="S"  name="search" id="button" class="topformbutton2" onclick="return newSearchLoan('F');"><bean:message key="button.save" /></button>
		     </logic:equal>
	        <logic:equal value="4000106" name="common">
	            <button type="button" name="button2" title="Alt+N" accesskey="N" class="topformbutton2"  onclick="newLoanInit();"><bean:message key="button.new" /></button>
	         </logic:equal>
	      </td>
	 
		</tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
	
			 
     <fieldset>	
		 <legend><bean:message key="lbl.loanDetail"/></legend>  

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
	 
        <td ><b><bean:message key="lbl.loan"/></b></td>
	
        <td><b><bean:message key="lbl.agrementDate"/></b></td>
        <td><b><bean:message key="lbl.customerName"/></b></td>
		<td><b><bean:message key="lbl.product"/> </b></td>
		<td><b><bean:message key="lbl.scheme"/> </b></td>
		
		
	</tr>
	
<logic:present name="loandetails">
<logic:iterate name="loandetails" id="subloandetails">
    <tr class="white1">
    <logic:equal value="26" name="common">
	
   	  <td  id=""><a href="JSP/CMJSP/CMS_frames_rescheduling.jsp" >30364</a></td>
     
         </logic:equal>
         <logic:equal value="27" name="common">
         <td  id=""><a href="JSP/CMJSP/CMS_frames_deferal.jsp" >30364</a></td>
     
         </logic:equal>
         <logic:equal value="28" name="common">
         <td  id=""><a href="JSP/CMJSP/CMS_frames_partpayment.jsp" >30364</a></td>
         </logic:equal>
          <logic:equal value="41" name="common">
         <td id=""><a href="JSP/CMJSP/CPS_frames_Repayment_services.jsp" >30364</a></td>
         </logic:equal>
         <logic:equal value="42" name="common">
         <td  id=""><a href="JSP/CMJSP/CPS_frames_hold_instrument.jsp" >30364</a></td>
         </logic:equal>
         <logic:equal value="43" name="common">
         <td  id=""><a href="JSP/CMJSP/CPS_release_instrument.jsp" >30364</a></td>
         </logic:equal>
        
         <logic:equal value="49" name="common">
         <td  id=""><a href="JSP/CMJSP/CMS_Frames_manual.jsp" >30364</a></td>
         </logic:equal>
         
          <logic:equal value="50" name="common">
          <td  id=""><a href="JSP/CMJSP/CMS_Frames_WaiveOff.jsp" >30364</a></td>
         </logic:equal>
         
         <logic:equal value="44" name="common">
         <td  id=""><a href="JSP/CMJSP/CPS_frames_generate_report.jsp" >30364</a></td>
         </logic:equal>
          <logic:equal value="45" name="common">
         <td  id=""><a href="JSP/CMJSP/CPS_frames_presentation.jsp" >30364</a></td>
         </logic:equal>
          <logic:equal value="46" name="common">
         <td  id=""><a href="JSP/CMJSP/CPS_frames_bounce.jsp" >30364</a></td>
         </logic:equal>
         <logic:equal value="47" name="common">
			<td  id=""><a href="JSP/CMJSP/CMS_frames_Payment.jsp" >30364</a></td>
		</logic:equal>
           
		<logic:equal value="48" name="common">
			<td  id=""><a href="JSP/CMJSP/CMS_frames_Receipt.jsp" >30364</a></td>
		</logic:equal>
		  <logic:equal value="4000106" name="common">
             <td  id=""><a href="loanInitBehindAction.do?loanId=${subloandetails.lbxLoanNoHID}" >${subloandetails.loanNo}</a></td>
         </logic:equal>
         
          <logic:equal value="4000111" name="common">
             <td  id=""><a href="loanInitBehindAction.do?loanId=${subloandetails.lbxLoanNoHID}" >${subloandetails.loanNo}</a></td>
          </logic:equal>
         
           <logic:equal value="40" name="common">
         <td  id=""><a href="JSP/CMJSP/CMS_frames_DisbursedInitiation.jsp" >30364</a></td>
         </logic:equal>
         
         <logic:equal value="51" name="common">
         	<td  id=""><a href="manualAdviceCreationBehindAction.do?dealNo=2">30364</a></td>
         </logic:equal>
         
         <logic:equal value="52" name="common">
         	<td  id=""><a href="JSP/CMJSP/CMS_Frames_CheckStatus.jsp">30364</a></td>
         </logic:equal>
         
        
         
        <td  id="">${subloandetails.agrementDate}</td>
        <td  id="">${subloandetails.customerName}</td>
        <td  id="">${subloandetails.product}</td>
		<td  id="">${subloandetails.scheme}</td>
		
		
   </tr>
 
  </logic:iterate>
  </logic:present> 
    <logic:equal value="58" name="common">
            <td  id=""><a href="knockOffBehindAction.do" >1</a></td>
         </logic:equal>
  
 </table>
    
    </td>
</tr>
</table>

	</fieldset>

	



  </html:form>
  
</div>



</div>
</body>
</html>