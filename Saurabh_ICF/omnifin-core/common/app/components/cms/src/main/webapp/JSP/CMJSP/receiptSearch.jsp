<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
    int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
    request.setAttribute("no",no); %>
	
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
	   	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-latest.min.js"></script>
	    <script> $113 = jQuery.noConflict();</script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<link rel="stylesheet" href="js/fancyBox/source/jquery.fancybox.css" type="text/css" media="screen" />		

		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>		
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		
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
	<body  onload="generateReceiptReport();enableAnchor();document.getElementById('receiptMakerSearch').loanAccountButton.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/receiptMakerProcessAction" method="post" styleId="receiptMakerSearch">
	
	
		<input type="hidden" name="forward" id="forward" value="${forward}" />
		<input type="hidden" name="frdLoanID" id="frdLoanID" value="${frdLoanID}" />
		<input type="hidden" name="frdInstrumentID" id="frdInstrumentID" value="${frdInstrumentID}" />
		<input type="hidden" name="autoManualFlag" id="autoManualFlag" value="${autoManualFlag}"/>
		<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		   
		    <logic:present name="fromAuthor">  
		      
	   <fieldset>	  
	<legend><bean:message key="lbl.receiptsAuthorSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
	 <tr>	   
          <td><bean:message key="lbl.loanAccount"></bean:message> </td>
		   <td>
		   <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		  <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="" readonly="true" tabindex="-1"/>
          <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
          <html:button property="loanAccountButton" styleId="loanAccountButton" onclick="closeLovCallonLov1();openLOVCommon(144,'receiptMakerSearch','loanAccountNumber','userId','loanAccountNumber', 'userId','','','customerName')" value=" " styleClass="lovbutton"></html:button>
        <!--   <img onclick="openLOVCommon(144,'receiptMakerSearch','loanAccountNumber','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/>-->
     	  </td>
	       <td><bean:message key="lbl.customerName"></bean:message></td>
	       <td >
	      <html:text property="customerName"  styleClass="text" styleId="customerName" value="" maxlength="50" ></html:text>
		  
		   </td>
		  </tr>	
		   <tr>
		    <td><bean:message key ="lbl.businessPartnerType"></bean:message></td>
			<td>
			<html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"   readonly="true" tabindex="-1"/>  
			<html:hidden  property="lbxBPType" styleId="lbxBPType" />
            <html:button property="businessPartnerButton" styleId="businessPartnerButton" onclick="openLOVCommon(147,'receiptMakerSearch','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
            <!--  <img onclick="openLOVCommon(147,'receiptMakerSearch','businessPartnerType','lbxLoanNoHID','lbxBPType', 'loanAccountNumber','Select loanAccountNumber','','lbxBPNID','businessPartnerName')" src="<%= request.getContextPath()%>/images/lov.gif"/>  -->
		   
		    </td>    
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
		 <html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  value="" readonly="true"/>
         <html:hidden  property="lbxBPNID" styleId="lbxBPNID" />        
		  </td>
		 </tr>
		   <tr>
			<td><bean:message key="lbl.receiptAmount"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="receiptAmount" styleClass="text" styleId="receiptAmount" value="" 
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
			 <html:hidden  property="txnAdvicedID" styleId="txnAdvicedID"  />
			 </div></td>
			 
			  <td><bean:message key="lbl.instrumentNumber"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="" maxlength="20"></html:text>
			 	 <html:hidden  property="instrumentID" styleId="instrumentID"  />
			 </div></td>
		   </tr> 

		   <tr>
		   <td><bean:message key ="lbl.receiptMode"></bean:message></td>
		 <td><span style="float:left;">
		 <html:select property="receiptMode" styleId="receiptMode" styleClass="text" value="" >
		 <html:option value="">--Select--</html:option> 
		  <html:option value="C">Cash</html:option> 
		 <html:option value="Q">Cheque</html:option> 
		 <html:option value="D">DD</html:option> 
		 <html:option value="N">NEFT</html:option> 
		 <html:option value="R">RTGS</html:option>	  
		 <html:option value="DIR">Direct Debit</html:option>	  
		 <html:option value="S">Adjustment</html:option>	            
           </html:select>
           <input type="hidden"  name="lbxreceiptMode" id="lbxreceiptMode"/>
		 </span></td>
		 
				 <td>
				 <bean:message key="lbl.userName" />
				 </td>
				 <td>
				    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
				    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
				    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
				    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
				    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'receiptMakerSearch','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
				 </td>

		    </tr>
		   

		    <tr>		   
          <td>
	       <button type="button" class="topformbutton2"  id="search" onclick="return receiptAuthorSearch('<bean:message key="msg.plsSelOneCriteria"/>');" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
		</td>
		  </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
	 	  
		  </logic:present>
		  <logic:notPresent name="fromAuthor">	
		    
	   <fieldset>	  
	<legend><bean:message key="lbl.receiptsMakerSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

	       <tr>	   
           <td><bean:message key="lbl.loanAccount"></bean:message> </td>
		   <td>	
		   <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />	   
		 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="" readonly="true" tabindex="-1"/>
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
         <html:button property="loanAccountButton" styleId="loanAccountButton" onclick="closeLovCallonLov1();openLOVCommon(137,'receiptMakerSearch','loanAccountNumber','userId','loanAccountNumber', 'userId','','','customerName')" value=" " styleClass="lovbutton"></html:button>
        <!--   <img onClick="openLOVCommon(137,'receiptMakerSearch','loanAccountNumber','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" > -->
        
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message></td>
	<td >
	<html:text property="customerName"  styleClass="text" styleId="customerName" value="" maxlength="50" ></html:text>
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	</td>
		</tr>
		<tr>
		    <td><bean:message key ="lbl.businessPartnerType"></bean:message></td>
			<td>
			<html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="${sessionScope.pParentGroup}" readonly="true" tabindex="-1"/>
			<html:hidden  property="lbxBPType" styleId="lbxBPType" />
			<html:button property="businessPartnerButton" styleId="businessPartnerButton" onclick="openLOVCommon(147,'receiptMakerSearch','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
            <!--  <img onclick="openLOVCommon(147,'receiptMakerSearch','businessPartnerType','lbxLoanNoHID','lbxBPType', 'loanAccountNumber','Select loanAccountNumber','','lbxBPNID','businessPartnerName')" src="<%= request.getContextPath()%>/images/lov.gif"/> --> 
		   
		    </td>    
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
		 <html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  value="" readonly="true"/>
         <html:hidden  property="lbxBPNID" styleId="lbxBPNID" />        
		  </td>
		 </tr>
		   <tr>
			<td><bean:message key="lbl.receiptAmount"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="receiptAmount" styleClass="text" styleId="receiptAmount" value="" 
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
			 <html:hidden  property="txnAdvicedID" styleId="txnAdvicedID"  />
			 </div></td>
			 
			  <td><bean:message key="lbl.instrumentNumber"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="" maxlength="20"></html:text>
			 	 <html:hidden  property="instrumentID" styleId="instrumentID"  />
			 </div></td>
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
				    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'receiptMakerSearch','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
				 </td>


		   <td><bean:message key ="lbl.receiptMode"></bean:message></td>
		 <td><span style="float:left;">
		 <html:select property="receiptMode" styleId="receiptMode" styleClass="text" value="">
		 <html:option value="">--Select--</html:option> 
		  <html:option value="C">Cash</html:option> 
		 <html:option value="Q">Cheque</html:option> 
		 <html:option value="D">DD</html:option> 
		 <html:option value="N">NEFT</html:option> 
		 <html:option value="R">RTGS</html:option>
		 <html:option value="DIR">Direct Debit</html:option>	  
		 <html:option value="S">Adjustment</html:option>	            
           </html:select>
           <input type="hidden"  name="lbxreceiptMode" id="lbxreceiptMode"/>
		 </span></td>
		    </tr>	
		    <tr>	   
          <td>
 
	     <button type="button" class="topformbutton2" id="search" onclick="return receiptSearch('<bean:message key="msg.plsSelOneCriteria"/>');" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
	 	 <button type="button" name="button2" class="topformbutton2" onclick="return newReceiptMaker();" title="Alt+N" accesskey="N"><bean:message key="button.new" /></button>
	
		</td>
		  </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
   </logic:notPresent>
	 
	       
	  
	     
	
	 
       
	
		
     <fieldset>	
		
 <legend><bean:message key="lbl.receiptSearchRecords"/></legend>  
  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
     <logic:notPresent name="authordetailList">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
     <logic:notPresent name="receiptMakerSearchGrid">
     
    <tr class="white2">
	 
         <td><b><bean:message key="lbl.loanAccount"/></b></td>
	     <td><b><bean:message key="lbl.customerName"/></b></td>
         <td><b><bean:message key="lbl.businessPartnerType"/></b></td>
         <td><b><bean:message key="lbl.businessPartnerName"/></b></td>
	     <td><b><bean:message key="lbl.receiptAmount"/></b></td>
		 <td><b><bean:message key="lbl.instrumentNumber"/> </b></td>
         <td><b><bean:message key="lbl.receiptMode"/> </b></td>
		 <td><b><bean:message key="lbl.userName"/> </b></td>


		


		
		</tr>
<tr class="white2">
<td colspan="8"> 
<bean:message key="lbl.noDataFound" />
</td>
</tr>
		</logic:notPresent>
		</table>
		</logic:notPresent>
		</td>
	   </tr>
		</table>
		
		<logic:present name="receiptMakerSearchGrid">
		<logic:notEmpty name="list"> 
    <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/receiptMakerSearch.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="modifyNo" titleKey="lbl.loanAccount"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="businessPartnerType" titleKey="lbl.businessPartnerType"  sortable="true"  />
	<display:column property="businessPartnerName" titleKey="lbl.businessPartnerName"  sortable="true"  />
	<display:column property="receiptAmount" titleKey="lbl.receiptAmount"  sortable="true"  />
	<display:column property="instrumentNumber" titleKey="lbl.instrumentNumber"  sortable="true"  />
    <display:column property="receiptMode" titleKey="lbl.receiptMode"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />


	

	
	

</display:table>
</logic:notEmpty>

<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
         <td><b><bean:message key="lbl.loanAccount"/></b></td>
	     <td><b><bean:message key="lbl.customerName"/></b></td>
         <td><b><bean:message key="lbl.businessPartnerType"/></b></td>
         <td><b><bean:message key="lbl.businessPartnerName"/></b></td>
	     <td><b><bean:message key="lbl.receiptAmount"/></b></td>
		 <td><b><bean:message key="lbl.instrumentNumber"/> </b></td>
         <td><b><bean:message key="lbl.receiptMode"/> </b></td>
		 <td><b><bean:message key="lbl.userName"/> </b></td>


		

		

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
	
  
  <logic:present name="authordetailList">	
		<logic:notEmpty name="authordetailList"> 
    <display:table  id="sublist"  name="authordetailList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${authordetailList[0].totalRecordSize}" requestURI="/receiptAuthorProcessAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="modifyNo" titleKey="lbl.loanAccount"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="businessPartnerType" titleKey="lbl.businessPartnerType"  sortable="true"  />
	<display:column property="businessPartnerName" titleKey="lbl.businessPartnerName"  sortable="true"  />
	<display:column property="receiptAmount" titleKey="lbl.receiptAmount"  sortable="true"  />
	<display:column property="instrumentNumber" titleKey="lbl.instrumentNumber"  sortable="true"  />
    <display:column property="receiptMode" titleKey="lbl.receiptMode"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />


	

	
	

</display:table>
</logic:notEmpty>

<logic:empty name="authordetailList">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
         <td><b><bean:message key="lbl.loanAccount"/></b></td>
	     <td><b><bean:message key="lbl.customerName"/></b></td>
         <td><b><bean:message key="lbl.businessPartnerType"/></b></td>
         <td><b><bean:message key="lbl.businessPartnerName"/></b></td>
	     <td><b><bean:message key="lbl.receiptAmount"/></b></td>
		 <td><b><bean:message key="lbl.instrumentNumber"/> </b></td>
         <td><b><bean:message key="lbl.receiptMode"/> </b></td>
		 <td><b><bean:message key="lbl.userName"/> </b></td>


		

		

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

	  </html:form>
    <logic:present name="msg">
		<script type="text/javascript">
		
		
	   if('<%=request.getAttribute("msg").toString()%>'=='F')
		{
			alert("<bean:message key="lbl.forwardSuccess" />");
			
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
		
		}
		else if('<%=request.getAttribute("msg")%>'=='Locked')
	   {
		   alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	   }
		else 
		{
			alert("<bean:message key="lbl.erroruSuccess" />");
			
		}
		
		</script>
		</logic:present>
</div>



</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</body>
</html>