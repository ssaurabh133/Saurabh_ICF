<%--
      Author Name-     Pawan  Kumar Singh
      Date of creation -20/04/2011
      Purpose-          Entry of Collateral Detail
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
	<style type="text/css">
		#assets_label_first{
		 margin-left:188px;
		}
		#assets_label_second{
		 margin-left:150px;
		}
		#assets_label_third{
		 margin-left:100px;
		}
		#assets_label_four{
			margin-left:170px;
		}


	</style>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript">
			var curWin = 0;
			otherWindows = new Array();
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
<body oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllWindowCallUnloadBody();" onload="enableAnchor();checkChanges('CollateralForm');">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<div id=centercolumn>
<div id=revisedcontainer>
<logic:notPresent name="cmAuthor">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<input type="hidden" name="productCat" id="productCat" value="${requestScope.productCat }" />
<html:form action="/collateralBehindAction" styleId="CollateralForm" method="post" >

<center><font color="red"><html:errors /></font></center>
<fieldset>

<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td >
           <input type="hidden" id="loanId" value="${loanHeader[0].loanId}"/>
          <b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}
          <input type="hidden" id="dealCustomerId" value="${loanHeader[0].dealCustomerId}"/>
          </td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>	
 
          
 <fieldset>
	<legend><bean:message key="lbl.asset/collDetails"/></legend>
	
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd" colspan="2">    
    		    <table width="100%" border="0" cellspacing="1"  id="dtable" cellpadding="1">
    		       <tr class="white2">        
        			  <td width="3%" ><input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked();"/></td>
        			  <td  ><b><bean:message key="lbl.assetCollateralId" /></b></td>
        			  <td  ><b><bean:message key="lbl.asscollTypes" /></b></td>
        			  <td  ><b><bean:message key="lbl.Assets/Collateral" /></b></td>
        	    	 <td  ><b><bean:message key="lbl.collateralDescription" /></b></td>
		              <td  ><b><bean:message key="lbl.collateralValue" /></b></td>
                 		            	
	               </tr>
	                <logic:present name="showCollateralDetails">
	                 <%int i=1; %> 
	                 <logic:iterate id="showCollateralDetails1" name="showCollateralDetails" >		                
	               <tr class="white1"> 	             
  				  <td >
  				  <input type="checkbox" name="chk" id="chk<%=i%>"  value="${showCollateralDetails1.assetsId}" /></td>	  				          
	              <td >${showCollateralDetails1.assetsId }</td>
	                   <td >${showCollateralDetails1.colltype2}<input type="hidden" name="collType" value="${showCollateralDetails1.colltype2}" /></td>
	                    <td ><a href="#" id="collupdate" onclick="collateralView('${showCollateralDetails1.colltype2}','${showCollateralDetails1.colltype1}','${showCollateralDetails1.assetsId}');"/>${showCollateralDetails1.colltype1}</td>	                      
	                 
	                  <td >${showCollateralDetails1.assetsCollateralDesc}
	                  <input type="hidden" name="assetsCollateralDesc" id="assetsCollateralDesc" value="${showCollateralDetails1.assetsCollateralDesc }" /></td>
	                  <td>${showCollateralDetails1.assetsCollateralValue}
	                  <input type="hidden" name="assetsCollateralValue" id="assetsCollateralValue" value="${showCollateralDetails1.assetsCollateralValue }" /></td>	               
	              <% i++;%>
	               </tr>	 
                    </logic:iterate>
                    </logic:present>      
                </table>
              </td>
            </tr>            				
		<tr >	
			 <td align="left">
<%-- <button type="button" name="Submit20" class="topformbutton2" onclick="return deleteCollateralDetails('L','');" accesskey="D" title="Alt+D"><bean:message key="button.delete" />
</button>
--%>
			 
<button type="button" name="button" id="getCollateralButton" class="topformbutton4" title="Alt+G" accesskey="G"  onclick="getCollateral();" ><bean:message key="button.getasse/Coll" /></button>
			 </td>
		 	 
		</tr>	
		    <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		    
		 </table>
</fieldset>
</html:form>

</logic:notPresent>


<logic:present name="cmAuthor">

	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="productCat" id="productCat" value="${requestScope.productCat }" />
<html:form action="/collateralBehindAction" styleId="CollateralForm" method="post" >

<center><font color="red"><html:errors /></font></center>
<fieldset>

<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td >
           <input type="hidden" id="loanId" value="${loanHeader[0].loanId}"/>
          <b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}
          <input type="hidden" id="dealCustomerId" value="${loanHeader[0].dealCustomerId}"/>
          </td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>	
 
          
 <fieldset>
	<legend><bean:message key="lbl.asset/collDetails"/></legend>
	
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd" colspan="2">    
    		    <table width="100%" border="0" cellspacing="1"  id="dtable" cellpadding="1">
    		       <tr class="white2">        
        			  <td width="3%" ><input type="checkbox" disabled="disabled" name="allchk" id="allchk" onclick="allChecked();"/></td>
        			   <td  ><b><bean:message key="lbl.assetCollateralId" /></b></td>
        			  <td  ><b><bean:message key="lbl.asscollTypes" /></b></td>
        			  <td  ><b><bean:message key="lbl.Assets/Collateral" /></b></td>
        	    	 <td  ><b><bean:message key="lbl.collateralDescription" /></b></td>
		              <td  ><b><bean:message key="lbl.collateralValue" /></b></td>
                 		            	
	               </tr>
	                <logic:present name="showCollateralDetails">
	                 <%int i=1; %> 
	                 <logic:iterate id="showCollateralDetails1" name="showCollateralDetails" >		                
	               <tr class="white1"> 	             
  				  <td >
  				  <input type="checkbox" disabled="disabled" name="chk" id="chk<%=i%>"  value="${showCollateralDetails1.assetsId}" /></td>	  				          
	                  <td >${showCollateralDetails1.assetsId }</td>
	                  <td >${showCollateralDetails1.colltype2}<input type="hidden" name="collType" value="${showCollateralDetails1.colltype2}" /></td>
	                    <td ><a href="#" id="collupdate" onclick="collateralView('${showCollateralDetails1.colltype2}','${showCollateralDetails1.colltype1}','${showCollateralDetails1.assetsId}');"/>${showCollateralDetails1.colltype1}</td>	                      
	                 
	                  <td >${showCollateralDetails1.assetsCollateralDesc}
	                  <input type="hidden" name="assetsCollateralDesc" id="assetsCollateralDesc" value="${showCollateralDetails1.assetsCollateralDesc }" /></td>
	                  <td>${showCollateralDetails1.assetsCollateralValue}
	                  <input type="hidden" name="assetsCollateralValue" id="assetsCollateralValue" value="${showCollateralDetails1.assetsCollateralValue }" /></td>	               
              
	              <% i++;%>
	               </tr>	 
                    </logic:iterate>
                    </logic:present>      
                </table>
              </td>
            </tr>
            <%--     				
		<tr >	
			 <td align="left">
			 <input type="button" name="Submit20" value="Delete" class="topformbutton2" onclick="return deleteCollateralDetails('L');" accesskey="D" title="Alt+D"/> 
			 
			 	<input type="button" name="button" id="getCollateralButton" class="topformbutton4" title="Alt+G" accesskey="G"  value="Get Asset/Collateral" onclick="getCollateral();" />
			 </td>
		 	 
		</tr>	
		--%>        
		    <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		    
		 </table>
</fieldset>
</html:form>
</logic:present>
</div>
</div>
<logic:present name="sms">
<script type="text/javascript">

if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.assetSuccess" />');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('<bean:message key="lbl.assetError" />');
	}	
  // opener.document.location.reload(true);
   window.close();
   opener.window.document.location.reload(true)
   //alert("alert");
   opener.window.close();
   opener.window.location.href="collaterlInCMBehindAction.do";
   
</script>
</logic:present>
<script>
	parent.menu.document.test.getFormName.value = document.getElementById("CollateralForm").id;
</script>
  </body>
  
  
  
</html:html>


