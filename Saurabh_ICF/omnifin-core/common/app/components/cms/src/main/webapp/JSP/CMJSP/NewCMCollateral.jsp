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
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
          
    </head>
<body onunload="closeAllWindowCallUnloadBody();" oncontextmenu="return false" onload="enableAnchor();checkChanges('CollateralForm');document.getElementById('CollateralForm').assetCollateral.focus();" onclick="parent_disable();" >
<div id=centercolumn>
<div id=revisedcontainer>
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
<logic:notPresent name="viewDeal">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<html:form action="/collateralBehindAction" styleId="CollateralForm" method="post" >
<input type="hidden" name="productCat" id="productCat" value="${requestScope.productCat }" />

<center><font color="red"><html:errors /></font></center>
  <fieldset>
   <legend><bean:message key="lbl.asscolDetails"/></legend>
    <table cellspacing=0 cellpadding=0 width="100%" border=0>   
     <tr>
      <td>
        <table cellspacing=1 cellpadding=1 width="100%" border=0>
        <tr>
           <td>
               <bean:message key="lbl.asscollTypes"/>
               </td>
	            <td> 
	            
	              <select name="assetCollateralType" id="assetCollateralType" class="text" onchange="return assetORcollat();">
	              	   <option  value="">---Select---</option>
	              	   <logic:notPresent name="repayType">
	              	   		  <option  value="ASSET">ASSET</option>
	              	   </logic:notPresent>    
		               <option  value="COLLATERAL">COLLATERAL</option>
	              </select>
	            </td>
       <td><bean:message key="lbl.assestCollateral"/></td>
         <td>
           <div id=forAll>
            <select name="assetCollateral" id="assetCollateral"  class="text" disabled="disabled" onchange="existingAssetCollateral('L');callOnLinkOrButtonWindowCollateral('assetCollateral');">
               <option  value="">---Select---</option>				    
               <option  value="N"><bean:message key="lbl.new"/></option>
              <option  value="E"><bean:message key="lbl.existing"/></option>
            </select>
            </div>
            <div id="forAsset" style="display: none">
             <select name="assetCollateral" id="assetCollateral"  class="text" disabled="disabled" onchange="existingAssetCollateral('L');callOnLinkOrButtonWindowCollateral('assetCollateral');">
             		    
               <option  value="N"><bean:message key="lbl.new"/></option>
           
            </select>
            </div>
          </td>
       
           	<td>
    			  <bean:message key="lbl.asscollClass"/> 
    			</td> 				                                                             					
   				<td>
   	 	  	  
				 <select name="assetClass" id="assetClass" disabled="disabled" class="text" onchange="assetsClass();">
				 
				     <option value="">---Select---</option>
				     <logic:present name="assets">
				     	<logic:iterate name="assets" id="assetsOb">
				     		<option  value="${assetsOb.id }">${assetsOb.name }</option>
				     	</logic:iterate>
				     </logic:present>
		             
	             </select> 

    </td>
         
        
		<td>
			<bean:message key="lbl.collClass" />
		</td>
	   <td>
			 
				 <select name="collateralClass" id="collateralClass" disabled="disabled" class="text" onchange="assetsClass();">
	              <option value="">---Select---</option>
	              <logic:present name="collaterals">
				     	<logic:iterate name="collaterals" id="collateralsOb">
				     		<option  value="${collateralsOb.id}">${collateralsOb.name }</option>
				     	</logic:iterate>
				     </logic:present>
	              </select>
                         
	      </td>
			
         </tr>
        </table>
       </td>
      </tr>  
     </table>
  </fieldset>
 <fieldset>
	<legend><bean:message key="lbl.details"/></legend>
	
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1"  id="dtable" cellpadding="1">
    		       <tr class="white2">        
        			  <td width="3%" ><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/></td>
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
	                   <td >${showCollateralDetails1.colltype2 }<input type="hidden" name="collType" value="${showCollateralDetails1.colltype2 }" /></td>
	                    <td ><a href="#" id="collupdate" onclick="collateralUpdate('${showCollateralDetails1.colltype1 }','${showCollateralDetails1.colltype2 }','${showCollateralDetails1.assetsId }');"/>${showCollateralDetails1.colltype1 }</td>	                      
	                  <td >${showCollateralDetails1.assetsCollateralDesc }</td>
	                  <td >${showCollateralDetails1.assetsCollateralValue }</td>	               
	              <% i++;%>
	               </tr>	 
                    </logic:iterate>
                    </logic:present>      
                </table>
              </td>
            </tr>            				
		<tr>	
<logic:equal value="Y" name="assetDeleteButton">
 <td>
 
   <logic:present name="showCollateralDetails">
 <button type="button" name="Submit20" id="collateralDelet" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return deleteCollateralDetails('L','N');"><bean:message key="button.delete" /></button>
 </logic:present>
  
  </td>
    </logic:equal>
		 
		  </tr>	
		    <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		    
		 </table>
</fieldset>
</html:form>

</logic:notPresent>
<logic:present name="viewDeal">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="productCat" id="productCat" value="${requestScope.productCat }" />
<html:form action="/collateralBehindAction" styleId="CollateralForm" method="post" >

<center><font color="red"><html:errors /></font></center>
  <fieldset>
   <legend><bean:message key="lbl.asscolDetails"/></legend>
    <table cellspacing=0 cellpadding=0 width="100%" border=0>   
     <tr>
      <td>
        <table cellspacing=1 cellpadding=1 width="100%" border=0>
        <tr>
          <td>
               <bean:message key="lbl.asscollTypes"/>
               </td>
	            <td> 
	            
	              <select name="assetCollateralType" id="assetCollateralType" disabled="disabled" class="text" disabled="disabled" onchange="return assetORcollat();">
	              	   <option  value="">---Select---</option>		    
		               <option  value="ASSET">ASSET</option>
		               <option  value="COLLATERAL">COLLATERAL</option>
	              </select>
	            </td>
           
      <td><bean:message key="lbl.assestCollateral"/></td>
         <td>
           <div id=forAll>
            <select name="assetCollateral" id="assetCollateral"  class="text" onchange="existingAssetCollateral('L');callOnLinkOrButtonWindowCollateral('assetCollateral');">
               <option  value="">---Select---</option>				    
               <option  value="N"><bean:message key="lbl.new"/></option>
              <option  value="E"><bean:message key="lbl.existing"/></option>
            </select>
            </div>
            <div id="forAsset" style="display: none">
             <select name="assetCollateral" id="assetCollateral"  class="text" onchange="existingAssetCollateral('L');callOnLinkOrButtonWindowCollateral('assetCollateral');">
             		    
               <option  value="N"><bean:message key="lbl.new"/></option>
           
            </select>
            </div>
          </td>
    			<td>
    			  <bean:message key="lbl.asscollClass"/> 
    			</td> 				                                                             					
   				<td>
   	 	  	  
				 <select name="assetClass" id="assetClass" disabled="disabled" class="text" onchange="return assetsClass();">
				 
				     <option value="">---Select---</option>
				     <logic:present name="assets">
				     	<logic:iterate name="assets" id="assetsOb">
				     		<option  value="${assetsOb.name }">${assetsOb.name }</option>
				     	</logic:iterate>
				     </logic:present>
		             
	             </select> 

    </td>
         
        
		<td>
			<bean:message key="lbl.collClass" />
		</td>
	   <td>
			 
				 <select name="collateralClass" id="collateralClass" disabled="disabled" class="text" onchange="return assetsClass();">
	              <option value="">---Select---</option>
	              <logic:present name="collaterals">
				     	<logic:iterate name="collaterals" id="collateralsOb">
				     		<option  value="${collateralsOb.name }">${collateralsOb.name }</option>
				     	</logic:iterate>
				     </logic:present>
	              </select>
                         
	      </td>
			
         </tr>
        </table>
       </td>
      </tr>  
     </table>
  </fieldset>
 <fieldset>
	<legend><bean:message key="lbl.details"/></legend>
	
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1"  id="dtable" cellpadding="1">
    		       <tr class="white2">        
        			  <td width="3%" ><input type="checkbox" name="allchk" disabled="disabled" id="allchk" onclick="allChecked();"/></td>
        			   <td  ><b><bean:message key="lbl.assetCollateralId" /></b></td>
        			  <td  ><b><bean:message key="lbl.asscollTypes" /></b></td>
        			  <td  ><b><bean:message key="lbl.Assets/Collateral" /></b></td>
        	    	  <td  ><b><bean:message key="lbl.collateralDescription" /></b></td>
		              <td ><b><bean:message key="lbl.collateralValue" /></b></td>
                 		            	
	               </tr>
	                <logic:present name="showCollateralDetails">
	                 <%int i=1; %> 
	                 <logic:iterate id="showCollateralDetails1" name="showCollateralDetails" >		                
	               <tr class="white1"> 	             
  				  <td >
  				  <input type="checkbox" name="chk" id="chk<%=i%>"  disabled="disabled" value="${showCollateralDetails1.assetsId}" /></td>	  				          
	                   <td >${showCollateralDetails1.assetsId }</td>
	                   <td >${showCollateralDetails1.colltype2 }</td>
	                    <td ><a href="#" id="collupdate" onclick="return collateralUpdate('${showCollateralDetails1.colltype1 }','${showCollateralDetails1.colltype2 }','${showCollateralDetails1.assetsId }');"/>${showCollateralDetails1.colltype1 }</td>	                      
	                  <td >${showCollateralDetails1.assetsCollateralDesc }</td>
	                  <td >${showCollateralDetails1.assetsCollateralValue }</td>	               
	              <% i++;%>
	               </tr>	 
                    </logic:iterate>
                    </logic:present>      
                </table>
              </td>
            </tr>            				
		<tr>	
<!-- <td><input type="button" name="Submit20" value="Delete" class="topformbutton2" onclick="return deleteCollateralDetails('D');"/> </td>-->
		  </tr>	
		    <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		    
		 </table>
</fieldset>
</html:form>
</logic:present>
</div>
</div>

<logic:present name="sms">
<script type="text/javascript">

if('<%=request.getAttribute("sms")%>'=='S')
	{
		alert('<bean:message key="lbl.assetSuccess" />');
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert('<bean:message key="lbl.assetError" />');
	}	
  // opener.document.location.reload(true);
   window.close();
   opener.document.location.reload(true);
   ///alert("alert");
   //opener.window.location.href="collaterlInCMBehindAction.do";
   opener.window.close();
</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>


	<logic:present name="assetNameVerification">
	<logic:notEmpty name="assetNameVerification">
	<script type="text/javascript">
	
		if(confirm("<%=request.getAttribute("assetNameVerification")%>"+" <bean:message key="lbl.verifCapturingWarning" />"))
		{
			deleteCollateralDetails('L','Y');
		}
			
	</script>
	</logic:notEmpty>
</logic:present>	
<logic:present name="deleteMsg">
	<script type="text/javascript">
	
		
		if('<%=request.getAttribute("deleteMsg")%>'=='S')
		{
				alert("<bean:message key="lbl.dataDeleted" />");
	
		}
					
	</script>
</logic:present>
<script>
	setFramevalues("CollateralForm");
</script>



  </body>
  
  
  
</html:html>


