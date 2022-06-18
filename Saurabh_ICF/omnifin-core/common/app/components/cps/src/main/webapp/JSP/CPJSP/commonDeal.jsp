<%--
      Author Name-  Prashant kumar    
      Date of creation -12/05/2011
      Purpose-   InitLoan Info       
      Documentation-      
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/contentstyle.css"/>
		 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/subpage.css"/>
	   
		 
	<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	
	
		<script type="text/javascript" src="js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		
		<!--[if gte IE 5]>
	<style type="text/css">
	
	.white1 {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->

    
	</head>
	<body onload="enableAnchor();document.getElementById('commonForm').dealButton.focus();init_fields();" onclick="parent_disable();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

	<html:form action="/commondeal" method="post" styleId="commonForm">
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>
	<fieldset>	  
	  <legend><bean:message key="lbl.ac.detail"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		 <table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
		<td width="20%"><bean:message key="lbl.dealNo"/></td>
		<td width="35%">
		
		<logic:equal value="3000206" name="common">
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" value="" tabindex="-1"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${dealdetails[0].lbxDealNo}"/>
			<html:button property="dealButton" style="dealButton" tabindex="1" onclick="openLOVCommon(21,'commonForm','dealNo','','', '','','','customername')" value=" " styleClass="lovbutton"></html:button>
	       <%--<img onclick="openLOVCommon(21,'commonForm','dealNo','','', '','','','customername')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>  	  
       </logic:equal>
		
		<logic:equal value="3000296" name="common">
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" value="" tabindex="-1"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${dealdetails[0].lbxDealNo}"/>
			<html:button property="dealButton" style="dealButton" tabindex="1" onclick="openLOVCommon(58,'commonForm','dealNo','','', '','','','customername')" value=" " styleClass="lovbutton"></html:button>
	      <%--<img onclick="openLOVCommon(58,'commonForm','dealNo','','', '','','','customername')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>   	  
       </logic:equal>
       
       
       
	  </td>
	  <td><bean:message key="lbl.customerName"/></td>
		<td width="13%" ><html:text property="customername" tabindex="2" value="" styleClass="text" styleId="customername" maxlength="50" onchange="upperMe('customerName');"/></td> 
		<td width="15%" ></td>
	   
	    </tr>
	  
	    <tr>
		<td><bean:message key="lbl.applicationDate"/></td>
		<td ><html:text property="applicationdate"  tabindex="3" styleClass="text" styleId="applicationdate" maxlength="10" onchange="return checkDate('applicationdate');"/></td> 
	    
		 <td width="17%"><bean:message key="lbl.applicationNo"/></td>
	    <td colspan="2"><html:text property="applicationno" styleClass="text"styleId="applicationno" tabindex="4" maxlength="20" value="" onkeyup="return upperMe('applicationno');"/></td>
	    </tr>
		
		<tr> 
		<td><bean:message key="lbl.product"/></td> 
       
        <td > 
      
	        <html:text  property="product" styleId="product" styleClass="text" readonly="true" value="" tabindex="5"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" value=""/>
	        <html:button property="productButton" style="productButton" onclick="openLOVCommon(87,'commonForm','product','','scheme','lbxscheme','','','productId')" value=" " styleClass="lovbutton"></html:button>
	       <%-- <img onclick="openLOVCommon(87,'commonForm','product','','scheme','lbxscheme','','','productId')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%> 	         
    
      
        </td>
        <input type="hidden" id="productId" name="productId"/> 
        
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" value="" tabindex="6"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme" value="" />
          		<html:button property="schemeButton" style="schemeButton" onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Select Product LOV','','schemeId')" value=" " styleClass="lovbutton"></html:button>
               <%--<img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Select Product LOV','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%> 
           						    
                </td>
                <input type="hidden" id="schemeId" name="schemeId"/> 
        <td ></td>
		</tr> 	
				
		 <tr>
		
             <td align="right">                          </td>
		     <td align="right">&nbsp;</td>
		     <td align="right">&nbsp;</td>
		     <td align="left">
		     
		     <logic:equal name="common" value="3000206">
		            <button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return newSearchDeal('P');"><bean:message key="button.search" /></button>
		     </logic:equal>
		     
		     <logic:equal name="common" value="3000296">
		            <button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return newSearchDeal('F');"><bean:message key="button.search" /></button>
		     </logic:equal>
		     
            <logic:notEqual value="3000296" name="common"> 
            <button type="button" name="new" id="button" title="Alt+N" accesskey="N" class="topformbutton2" onclick="newEntryDeal();" ><bean:message key="button.new" /></button></logic:notEqual> </td>
		     <td align="right"></td>
		 </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>

	</html:form>

	</div>

<fieldset>	
		 <legend><bean:message key="lbl.applicationDetails"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">

        <td   ><b><bean:message key="lbl.dealNo"/></b></td>
         <td   ><b><bean:message key="lbl.customerName"/></b></td>
	
        <td  ><b><bean:message key="lbl.applicationDate"/></b></td>
       	<td   ><b><bean:message key="lbl.applicationNo"/></b></td>
      
        <td   ><b><bean:message key="lbl.product"/></b></td>
        <td  ><b><bean:message key="lbl.scheme"/> </b></td>
        

       

	</tr>
<logic:present name="dealdetails">

<logic:iterate name="dealdetails" id="subdealdetails">
	<tr class="white1">
	
	 <logic:equal value="3000206" name="common">

   	  <td  id="">

   	       <a href="dealCapturing.do?method=leadEntryCapturing&dealId=${subdealdetails.lbxDealNo}">${subdealdetails.dealNo}</a>
   	       
   	  </td>
      
     </logic:equal>
     
     <logic:equal value="16" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/field_verification_frameset.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="17" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/tradeinitiation.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="18" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CPS_frames_Buyer.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="19" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CPS_frames_Buyer.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="20" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CollateralInitiation.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="21" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CollateralCapturing.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="22" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CollateralCompletion.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="23" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CMS_frames_FundflowAnalysis.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="33" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/financial_analysis.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="34" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/Scoringdetails.jsp" >546666</a></td>
     
     </logic:equal>
          
      <logic:equal value="21" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CollateralCapturing.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="22" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CollateralCompletion.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="23" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/CMS_frames_FundflowAnalysis.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="33" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/financial_analysis.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="34" name="common">
	
   	  <td  id=""><a href="JSP/CPJSP/Scoringdetails.jsp" >546666</a></td>
     
     </logic:equal>
     
      <logic:equal value="3000296" name="common">
	
   	  <td  id=""><a href="commonPageBehind.do?dealId=${subdealdetails.lbxDealNo}" >${subdealdetails.dealNo}</a></td>
   	  
     </logic:equal>
     
     <td  id="">${subdealdetails.customername}</td>
	
     <td  id="">${subdealdetails.applicationdate}</td>
	
	  <td  id="">${subdealdetails.applicationno}</td>
      <td  id="">${subdealdetails.product}</td>
      <td  id="">${subdealdetails.scheme}</td>
     
	</tr>
	</logic:iterate>
</logic:present>	
	
 </table>    </td>
</tr>
</table>

	</fieldset>


</div>

</body>
</html:html>