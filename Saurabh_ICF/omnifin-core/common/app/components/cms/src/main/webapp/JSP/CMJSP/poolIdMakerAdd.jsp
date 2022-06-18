<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@page import="java.util.Date"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();

%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
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
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	 	

<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>

    <script type="text/javascript" src="<%=path%>/js/cmScript/poolIDMakerAuthor.js"></script>
   
   
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
			
			function defaultFocus()
			{
				document.getElementById('sourcingForm').loanNoButton.focus();
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
	<body onclick="parent_disable();init_fields();" onload="enableAnchor();document.getElementById('sourcingForm').loanNoButton.focus();" onunload="closeAllLovCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	
	<html:form action="/poolIdMakerProcessAction" method="post" styleId="sourcingForm" enctype="multipart/form-data" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	<input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate %>"/>
    <fieldset>
    
    <logic:present name="poolIdMaker"> 
	<legend><bean:message key="lbl.poolIdMaker"/></legend>   
	</logic:present>	    
	
	<logic:present name="poolIdAuthor"> 
	<legend><bean:message key="lbl.poolIdAuthor"/></legend>   
	</logic:present>   
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
	 <!  -----------      For Pool ID Maker Saved Data          -------------------------------------------- -->
		 <logic:present name="poolIdSavedDataList"> 
		 	
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 <tr>			 	
		   <td><bean:message key="lbl.poolID"/></td>
		   <td>
	        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${requestScope.poolNo}" readonly="true" tabindex="-1"/>   
           </td>
           <td><bean:message key="lbl.PoolName"/><font color="red">*</font></td>
	       <td>
	        <html:text styleClass="text" property="poolName" styleId="poolName" value="${requestScope.poolIdSavedDataList[0].poolName}" maxlength="50"  readonly="true"/>
	       </td>
	 
        </tr>
        
	    <tr>   
            <td><bean:message key="lbl.PoolCreationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="poolCreationDate" styleId="poolCreationDate11" value="${requestScope.poolIdSavedDataList[0].poolCreationDate}" maxlength="20" disabled="true" readonly="true"/>
	       </label></td>	
         
     
     
	       <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="cutOffDate" styleId="cutOffDate11" value="${requestScope.poolIdSavedDataList[0].cutOffDate}" maxlength="20" disabled="true" readonly="true"/>
	       </label></td>	
		
		</tr>
		<tr>
		 <td><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      <td><label>
	      <html:select property="poolType" styleId="poolType" styleClass="text" value="${requestScope.poolIdSavedDataList[0].poolType}" disabled="true">
	           <html:option value="">--Select--</html:option>
		        <html:option value="S">Securitized</html:option>
		        <html:option value="R">Re-finance</html:option>
		   </html:select> 
	        </label>
	      </td>
     
	      <td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	    
		   <td>
			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="${requestScope.poolIdSavedDataList[0].instituteID}" readonly="true" tabindex="-1"/>   
		     <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
		     <input type="hidden" name="hcommon" id="hcommon" />
			<!-- <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button> -->  
           </td>
        <td></td>
        <td></td>
	       </tr>
	     
		  
	</table>
	<table>
	<tr>
			
	        <td align="left">  			
           
	        <button type="button" name="button" id="frwrd" class="topformbutton2" title="Alt+F" accesskey="F" onclick="return forwardPoolIdMaker();"><bean:message key="button.forward" /></button>
	      </td>
	      <td></td>
	      <td></td>
	      <td></td>
		</tr>
 
	</table>
	</logic:present>
       
        	
        	
        	
        	
    <!-- ---------For Pool ID Author Saved Data      -------------------------------------------- -->    	
        	
        	 <logic:present name="authorPoolIdSavedList"> 	
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 <tr>			 	
		   <td><bean:message key="lbl.poolID"/></td>
		   <td>
	        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${sessionScope.authorPoolIdSavedList[0].poolID}" readonly="true" tabindex="-1"/>   
           </td>
           <td><bean:message key="lbl.PoolName"/><font color="red">*</font></td>
	       <td>
	        <html:text styleClass="text" property="poolName" styleId="poolName" value="${sessionScope.authorPoolIdSavedList[0].poolName}" maxlength="50"  readonly="true"/>
	       </td>
	 
        </tr>
        
	    <tr>   
           <td><bean:message key="lbl.PoolCreationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="poolCreationDate" styleId="poolCreationDate" value="${sessionScope.authorPoolIdSavedList[0].poolCreationDate}" maxlength="20" disabled="true" readonly="true"/>
	       </label></td>	
         
     
	       <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="cutOffDate" styleId="cutOffDate" value="${sessionScope.authorPoolIdSavedList[0].cutOffDate}" maxlength="20" disabled="true" readonly="true"/>
	       </label></td>	
		
		</tr>
		<tr>
		  <td><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      <td><label>
	      <html:select property="poolType" styleId="poolType" styleClass="text" value="${sessionScope.authorPoolIdSavedList[0].poolType}" disabled="true">
	           <html:option value="">--Select--</html:option>
		        <html:option value="S">Securitized</html:option>
		        <html:option value="R">Re-finance</html:option>
		   </html:select> 
	        </label>
	      </td>
	      <td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	    
		   <td>
			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="${sessionScope.authorPoolIdSavedList[0].instituteID}" readonly="true" tabindex="-1"/>   
		     <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
		     <input type="hidden" name="hcommon" id="hcommon" />
			<!-- <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button> -->  
           </td>
        <td></td>
        <td></td>
	       </tr>
	     
		  
	</table>

        	</logic:present>	
      
        	
        	   <!-- ---------For Pool ID Maker  For New Screen      -------------------------------------------- -->    
        	   
        	 <logic:notPresent name="poolIdSavedDataList"> 
        	 <logic:notPresent name="authorPoolIdSavedList"> 
        	 	
        	
            
        	 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        	 <tr>
		<td>&nbsp;<bean:message key="lbl.poolID"/></td>
		<td> 
		 <input type="text" name="poolID" id="poolID" value="${requestScope.poolNo}" readonly="readonly"/>
	       <!--  <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="50"  />  -->
	       
	       </td>
		
           <td><bean:message key="lbl.PoolName"/><font color="red">*</font></td>
	       <td>
	        <html:text styleClass="text" property="poolName" styleId="poolName" maxlength="50" value="" />
	       </td>
	 
        </tr>
        
	    <tr>   
          <td><bean:message key="lbl.PoolCreationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="poolCreationDate" styleId="poolCreationDate" value="" maxlength="20" />
	       </label></td>	
         
             
	       <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="cutOffDate" styleId="cutOffDate"  maxlength="20" value=""/>
	       </label></td>	
		
		</tr>
		<tr>
		   <td><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      <td><label>
	    
		   	   <select id="poolType" class="text" name="poolType" >
			<option value="">--Select--</option>
			<option value="S">Securitized</option>
			<option value="R">Re-finance</option>
			
			</select>
	
	        </label>
	      </td>
	      <td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	    
		   <td>
			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="" readonly="false"
			 onchange="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon','','','Y');closeLovCallonLov1();"
			  tabindex="-1"/>   
		     <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
		     <input type="hidden" name="hcommon" id="hcommon" />
			 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
           </td>
        <td></td>
        <td></td>
			
    </tr>
    <tr>
		
	      <td><bean:message key="lbl.fileDescription"/><font color="red">*</font></td>
	      <td>
		  <html:file size="40" property="docFile" styleId="docFile" styleClass="text"/> 
		  </td>
		  <td></td>
			<td></td>
    </tr>
	  
		  
	</table>
	<table>
	<tr>
			
	        <td align="left">  
			 <button type="button" name="button" id="save" class="topformbutton3" title="Alt+S" accesskey="S" onclick="return submitPoolIdUpload();"><bean:message key="button.updsave" /></button> 
            <button type="button" name="button" id="err" class="topformbutton2" title="Alt+E" accesskey="E" onclick="return errorLogPoolIdMaker();"><bean:message key="button.error" /></button> 
	        <button type="button" name="button" id="frwrd" class="topformbutton2" title="Alt+F" accesskey="F" onclick="return forwardOnNew();"><bean:message key="button.forward" /></button>
	      </td>
	      <td></td>
	      <td></td>
	      <td></td>
		</tr>
 
	</table>
		</logic:notPresent>
		</logic:notPresent>
		
	</td>
    </tr>
    </table>	 
	</fieldset>	
	
	
	<fieldset>	


	<legend><bean:message key="lbl.poolIdMakerRecords"/></legend>  
	
	
	 

 
 <logic:present name="poolIdMaker">
 
  <logic:present name="forNew">
<logic:empty name="mainList">			
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
			    <td class="gridtd">
			    <table width="100%" border="0" cellspacing="1" cellpadding="1">
			    <tr class="white2">
		
    
         
		<td><b><bean:message key="lbl.poolID"/></b></td>
        <td><strong><bean:message key="lbl.LoanID"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.customerName"/></strong></td>
        
        <td><b><bean:message key="lbl.loanCustType"/></b></td>
        <td><strong><bean:message key="lbl.loanCustCate"/></strong></td>
        <td><strong><bean:message key="lbl.loanCustConstitution"/></strong></td>
        <td><strong><bean:message key="lbl.loanCustBusSeg"/></strong></td>
        <td><strong><bean:message key="lbl.loanIndustry"/></strong></td>
        
        <td><b><bean:message key="lbl.loanSubIndustry"/></b></td>
        <td><strong><bean:message key="lbl.loanDisbursalDate"/></strong></td>
        <td><strong><bean:message key="lbl.loanMaturityDate"/></strong></td>
        <td><strong><bean:message key="lbl.loanTenure"/></strong></td>
        <td><strong><bean:message key="lbl.loanBalanceTenure"/></strong></td>
        
        <td><b><bean:message key="lbl.loanInstNum"/></b></td>
        <td><strong><bean:message key="lbl.LoanAdvEMINum"/></strong></td>
        <td><strong><bean:message key="lbl.loanInitRate"/></strong></td>
        <td><strong><bean:message key="lbl.loanDisbursalStatus"/></strong></td>
        <td><strong><bean:message key="lbl.loanNPAFlag"/></strong></td>
        
        <td><b><bean:message key="lbl.loanDPD"/></b></td>
        <td><strong><bean:message key="lbl.LoanDPDString"/></strong></td>
         <td><strong><bean:message key="lbl.LoanAssetCost"/></strong></td>
        <td><strong><bean:message key="lbl.loanAmount"/></strong></td>
        <td><strong><bean:message key="lbl.loanEmi"/></strong></td>
        <td><strong><bean:message key="lbl.loanAdvEMIAmount"/></strong></td>
        
        <td><b><bean:message key="lbl.loanBalPrinc"/></b></td>
        <td><strong><bean:message key="lbl.loanOverduePrin"/></strong></td>
        <td><strong><bean:message key="lbl.loanRecvdPrinc"/></strong></td>
        <td><strong><bean:message key="lbl.loanOverdueInsNum"/></strong></td>
        <td><strong><bean:message key="lbl.loanOverdueAmount"/></strong></td>
        
        <td><b><bean:message key="lbl.loanBalInsNum"/></b></td>
        <td><strong><bean:message key="lbl.loanBalInstAmount"/></strong></td> 
               
        
				</tr>	
		
			 </table>    </td>
			</tr>
		
			 </table>   
			

   </logic:empty>
   </logic:present>
 
   
   
   </logic:present>

	
 	<logic:notEmpty name="mainList" >
 			<logic:iterate id="mainListobj" name="mainList">
		  <input type="hidden" name="poolIDList" id="poolIDList" value="${mainListobj.poolID}"/>
          <input type="hidden" name="loanIDList" id="loanIDList" value="${mainListobj.loanID}"/>
          
     </logic:iterate>
			
	<display:table  id="mainList"  name="mainList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${mainList[0].totalRecordSize}" requestURI="/poolIdMakerProcessAction.do?poolID=${mainListobj.poolID}" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>  

	<display:column property="checkBoxDis" title="<input type='checkbox' name='checkId' id='allchkd' onclick='allChecked();' />"/>
	<display:column property="poolID" titleKey="lbl.poolID"    />
	<display:column property="loanID" titleKey="lbl.LoanID"   />
	<display:column property="loanProduct" titleKey="lbl.product"  />
	<display:column property="loanScheme" titleKey="lbl.loanScheme"   />
	<display:column property="loanCustomerID" titleKey="lbl.customerName" />
	
	<display:column property="loanCustomerType" titleKey="lbl.loanCustType"    />
	<display:column property="loanCustomerCtegory" titleKey="lbl.loanCustCate"   />
	<display:column property="loanCustomerConstituion" titleKey="lbl.loanCustConstitution"  />
	<display:column property="loanCustomerBusinessSeg" titleKey="lbl.loanCustBusSeg"   />
	<display:column property="loanIndustry" titleKey="lbl.loanIndustry" />
	
	<display:column property="loanSubIndustry" titleKey="lbl.loanSubIndustry"    />
	<display:column property="loanDisbursalDate" titleKey="lbl.loanDisbursalDate"   />
	<display:column property="loanMaturityDate" titleKey="lbl.loanMaturityDate"  />
	<display:column property="loanTenure" titleKey="lbl.loanTenure"   />
	<display:column property="loanBalanceTenure" titleKey="lbl.loanBalanceTenure" />
	
	<display:column property="loanInstallmentNum" titleKey="lbl.loanInstNum"    />
	<display:column property="loanAdvEMINUm" titleKey="lbl.LoanAdvEMINum"   />
	<display:column property="loanInitRate" titleKey="lbl.loanInitRate"  />
	<display:column property="loanDisbursalStatus" titleKey="lbl.loanDisbursalStatus"   />
	<display:column property="loanNPAFlag" titleKey="lbl.loanNPAFlag" />
	
	<display:column property="loanDPD" titleKey="lbl.loanDPD"    />
	<display:column property="loanDPDString" titleKey="lbl.LoanDPDString"   />
	<display:column property="loanAssetCost" titleKey="lbl.LoanAssetCost"  />
	<display:column property="laonAmount" titleKey="lbl.loanAmount"   />
	<display:column property="loanEMI" titleKey="lbl.loanEmi" />
	
	<display:column property="loanAdvEMIAmount" titleKey="lbl.loanAdvEMIAmount"/>
	<display:column property="loanBalPrincipal" titleKey="lbl.loanBalPrinc"   />
	<display:column property="loanOverduePrincipal" titleKey="lbl.loanOverduePrin"  />
	<display:column property="loanReceivedPrincipal" titleKey="lbl.loanRecvdPrinc"   />
	<display:column property="loanOverdueInstNo" titleKey="lbl.loanOverdueInsNum" />
	
	<display:column property="loanOverdueAmount" titleKey="lbl.loanOverdueAmount"  />
	<display:column property="loanBalnceInstNo" titleKey="lbl.loanBalInsNum"   />
	<display:column property="loanBalInstlAmount" titleKey="lbl.loanBalInstAmount" />	
	
	</display:table>
	
	</logic:notEmpty>
	
	


 

 <logic:present name="mainList">
 <logic:empty name="mainList">			
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
			    <td class="gridtd">
			    <table width="100%" border="0" cellspacing="1" cellpadding="1">
			    <tr class="white2">
		
    
         
		<td><b><bean:message key="lbl.poolID"/></b></td>
        <td><strong><bean:message key="lbl.LoanID"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.customerName"/></strong></td>
        
        <td><b><bean:message key="lbl.loanCustType"/></b></td>
        <td><strong><bean:message key="lbl.loanCustCate"/></strong></td>
        <td><strong><bean:message key="lbl.loanCustConstitution"/></strong></td>
        <td><strong><bean:message key="lbl.loanCustBusSeg"/></strong></td>
        <td><strong><bean:message key="lbl.loanIndustry"/></strong></td>
        
        <td><b><bean:message key="lbl.loanSubIndustry"/></b></td>
        <td><strong><bean:message key="lbl.loanDisbursalDate"/></strong></td>
        <td><strong><bean:message key="lbl.loanMaturityDate"/></strong></td>
        <td><strong><bean:message key="lbl.loanTenure"/></strong></td>
        <td><strong><bean:message key="lbl.loanBalanceTenure"/></strong></td>
        
        <td><b><bean:message key="lbl.loanInstNum"/></b></td>
        <td><strong><bean:message key="lbl.LoanAdvEMINum"/></strong></td>
        <td><strong><bean:message key="lbl.loanInitRate"/></strong></td>
        <td><strong><bean:message key="lbl.loanDisbursalStatus"/></strong></td>
        <td><strong><bean:message key="lbl.loanNPAFlag"/></strong></td>
        
        <td><b><bean:message key="lbl.loanDPD"/></b></td>
        <td><strong><bean:message key="lbl.LoanDPDString"/></strong></td>
         <td><strong><bean:message key="lbl.LoanAssetCost"/></strong></td>
        <td><strong><bean:message key="lbl.loanAmount"/></strong></td>
        <td><strong><bean:message key="lbl.loanEmi"/></strong></td>
        <td><strong><bean:message key="lbl.loanAdvEMIAmount"/></strong></td>
        
        <td><b><bean:message key="lbl.loanBalPrinc"/></b></td>
        <td><strong><bean:message key="lbl.loanOverduePrin"/></strong></td>
        <td><strong><bean:message key="lbl.loanRecvdPrinc"/></strong></td>
        <td><strong><bean:message key="lbl.loanOverdueInsNum"/></strong></td>
        <td><strong><bean:message key="lbl.loanOverdueAmount"/></strong></td>
        
        <td><b><bean:message key="lbl.loanBalInsNum"/></b></td>
        <td><strong><bean:message key="lbl.loanBalInstAmount"/></strong></td> 
               
        
				</tr>
		
		
			 </table>    </td>
			</tr>
		
			 </table>  
			
</logic:empty>
</logic:present>

  
 <logic:notPresent name="mainList">
 <logic:present name="myList">
 
 <logic:notEmpty name="myList">

 	<display:table  id="myList"  name="sessionScope.myList" style="width: 100%"  class="dataTable" style="font-size:12px !important" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${sessionScope.myList[0].totalRecordSize}" requestURI="/poolIdAuthorProcessAction.do?method=getPoolSearched&poolID=${sessionScope.authorPoolIdSavedList[0].poolID}" >
  
   <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>  

	
	<display:column property="poolID" titleKey="lbl.poolID"    />
	<display:column property="loanID" titleKey="lbl.LoanID"   />
	<display:column property="loanProduct" titleKey="lbl.product"  />
	<display:column property="loanScheme" titleKey="lbl.loanScheme"   />
	<display:column property="loanCustomerID" titleKey="lbl.customerName" />
	
	<display:column property="loanCustomerType" titleKey="lbl.loanCustType"    />
	<display:column property="loanCustomerCtegory" titleKey="lbl.loanCustCate"   />
	<display:column property="loanCustomerConstituion" titleKey="lbl.loanCustConstitution"  />
	<display:column property="loanCustomerBusinessSeg" titleKey="lbl.loanCustBusSeg"   />
	<display:column property="loanIndustry" titleKey="lbl.loanIndustry" />
	
	<display:column property="loanSubIndustry" titleKey="lbl.loanSubIndustry"    />
	<display:column property="loanDisbursalDate" titleKey="lbl.loanDisbursalDate"   />
	<display:column property="loanMaturityDate" titleKey="lbl.loanMaturityDate"  />
	<display:column property="loanTenure" titleKey="lbl.loanTenure"   />
	<display:column property="loanBalanceTenure" titleKey="lbl.loanBalanceTenure" />
	
	<display:column property="loanInstallmentNum" titleKey="lbl.loanInstNum"    />
	<display:column property="loanAdvEMINUm" titleKey="lbl.LoanAdvEMINum"   />
	<display:column property="loanInitRate" titleKey="lbl.loanInitRate"  />
	<display:column property="loanDisbursalStatus" titleKey="lbl.loanDisbursalStatus"   />
	<display:column property="loanNPAFlag" titleKey="lbl.loanNPAFlag" />
	
	<display:column property="loanDPD" titleKey="lbl.loanDPD"    />
	<display:column property="loanDPDString" titleKey="lbl.LoanDPDString"   />
	<display:column property="loanAssetCost" titleKey="lbl.LoanAssetCost"  />
	<display:column property="laonAmount" titleKey="lbl.loanAmount"   />
	<display:column property="loanEMI" titleKey="lbl.loanEmi" />
	
	<display:column property="loanAdvEMIAmount" titleKey="lbl.loanAdvEMIAmount"    />
	<display:column property="loanBalPrincipal" titleKey="lbl.loanBalPrinc"   />
	<display:column property="loanOverduePrincipal" titleKey="lbl.loanOverduePrin"  />
	<display:column property="loanReceivedPrincipal" titleKey="lbl.loanRecvdPrinc"   />
	<display:column property="loanOverdueInstNo" titleKey="lbl.loanOverdueInsNum" />
	
	<display:column property="loanOverdueAmount" titleKey="lbl.loanOverdueAmount"  />
	<display:column property="loanBalnceInstNo" titleKey="lbl.loanBalInsNum"   />
	<display:column property="loanBalInstlAmount" titleKey="lbl.loanBalInstAmount" />	
	
	</display:table>	

	</logic:notEmpty>

 	
<logic:empty name="myList">
 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td class="gridtd">
		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
		    <tr class="white2">       	    
				<td><b><bean:message key="lbl.poolID"/></b></td>
		        <td><strong><bean:message key="lbl.LoanID"/></strong></td>
		        <td><strong><bean:message key="lbl.product"/></strong></td>
		        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
		        <td><strong><bean:message key="lbl.customerName"/></strong></td>        
		        <td><b><bean:message key="lbl.loanCustType"/></b></td>
		        <td><strong><bean:message key="lbl.loanCustCate"/></strong></td>
		        <td><strong><bean:message key="lbl.loanCustConstitution"/></strong></td>
		        <td><strong><bean:message key="lbl.loanCustBusSeg"/></strong></td>
		        <td><strong><bean:message key="lbl.loanIndustry"/></strong></td>        
		        <td><b><bean:message key="lbl.loanSubIndustry"/></b></td>
		        <td><strong><bean:message key="lbl.loanDisbursalDate"/></strong></td>
		        <td><strong><bean:message key="lbl.loanMaturityDate"/></strong></td>
		        <td><strong><bean:message key="lbl.loanTenure"/></strong></td>
		        <td><strong><bean:message key="lbl.loanBalanceTenure"/></strong></td>        
		        <td><b><bean:message key="lbl.loanInstNum"/></b></td>
		        <td><strong><bean:message key="lbl.LoanAdvEMINum"/></strong></td>
		        <td><strong><bean:message key="lbl.loanInitRate"/></strong></td>
		        <td><strong><bean:message key="lbl.loanDisbursalStatus"/></strong></td>
		        <td><strong><bean:message key="lbl.loanNPAFlag"/></strong></td>        
		        <td><b><bean:message key="lbl.loanDPD"/></b></td>
		        <td><strong><bean:message key="lbl.LoanDPDString"/></strong></td>
		         <td><strong><bean:message key="lbl.LoanAssetCost"/></strong></td>
		        <td><strong><bean:message key="lbl.loanAmount"/></strong></td>
		        <td><strong><bean:message key="lbl.loanEmi"/></strong></td>
		        <td><strong><bean:message key="lbl.loanAdvEMIAmount"/></strong></td>        
		        <td><b><bean:message key="lbl.loanBalPrinc"/></b></td>
		        <td><strong><bean:message key="lbl.loanOverduePrin"/></strong></td>
		        <td><strong><bean:message key="lbl.loanRecvdPrinc"/></strong></td>
		        <td><strong><bean:message key="lbl.loanOverdueInsNum"/></strong></td>
		        <td><strong><bean:message key="lbl.loanOverdueAmount"/></strong></td>        
		        <td><b><bean:message key="lbl.loanBalInsNum"/></b></td>
		        <td><strong><bean:message key="lbl.loanBalInstAmount"/></strong></td>        
		</tr>		
	
		 </table>    </td>
			</tr>
			
			</table>		
</logic:empty>

</logic:present>
</logic:notPresent>




<logic:present name="mainList">
<logic:notPresent name="fileIsBlank">
<logic:notPresent name="noinserted">

 <table>
	<tr>	
	       <td align="left">  
			 <html:button property="button" value="Delete" styleId="Delete" styleClass="topformbutton2" title="Alt+D" accesskey="D" onclick="return deletePoolID();"> </html:button> 
            <html:button property="button" value="Add" styleId="add" styleClass="topformbutton2" title="Alt+A" accesskey="A" onclick="return addPoolID();"> </html:button> 
	      </td>
	      <td></td>
	      <td></td>
	      <td></td>
		</tr>
 
	</table>
	</logic:notPresent>
	</logic:notPresent>
 </logic:present>
 


	</fieldset>
	
	
</html:form>
	</div>
	
	<logic:present name="sms">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.sms" />');
 </script>
  </logic:present> 
  <logic:present name="deleteOk">
 <script type="text/javascript">	
 if(('<%=request.getAttribute("deleteOk")%>') == 'Y'){
	 alert('<bean:message key="lbl.deleteOk" />');
	
	}
		
 </script>
  </logic:present> 
  
  	<logic:present name="smsk">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smsk" />');
 </script>
  </logic:present> 
    	<logic:present name="smks">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smks" />');
 </script>
  </logic:present> 
  
  <logic:present name="smsno">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smsno" />');
 </script>
  </logic:present>
  
   <logic:present name="maxCount">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.maxCount" />');
 </script>
  </logic:present>
   <logic:present name="inserted">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.processingCompleted" />');
 </script>
  </logic:present>

  <logic:present name="noinserted">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.rejectProcessingError" />');
		
 </script>
  </logic:present>
	
   
 <logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'Y')
		{
			alert("<bean:message key="lbl.forwardSuccess" />");
			document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdBehindMaker.do";
	        document.getElementById("sourcingForm").submit();
		}
	else if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("Data not Found ");
	
	}
	
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdBehindMaker.do";
	    document.getElementById("sourcingForm").submit();
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked1')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdBehindAuthor.do";
	    document.getElementById("sourcingForm").submit();
	}
	</script>
	</logic:present>
	
	    


</div>
</body>
</html:html>