<!--Author Name : Pawan Kumar Singh-->
<!--Date of Creation : 14-April-2011-->
<!--Purpose  : Information of Buyer-->
<!--Documentation : -->

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
  
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
    <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	
	 <!-- css and jquery for Datepicker -->
		 
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>	
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>			
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/customerEntry.js"></script>	
     <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/buyerSupplier.js"></script>

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
	<body oncontextmenu="return false" onload="enableAnchor();document.getElementById('BuyerForm').businessPartnerName.focus();init_fields();">
	<div id="centercolumn">
	<div id="revisedcontainer">
    <fieldset>   
    <table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Buyer Supplier</td>
          </tr>
        </table> 
</td>
</tr>
</table>
    </fieldset>	
<logic:notPresent name="viewDeal">
<html:form action="/buyerProcessAction" styleId="BuyerForm" method="post">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />

	<fieldset id="buyerInfo" style="">	  	
	  <legend><bean:message key="lbl.buyersInformation"/></legend>
         <fieldset>
	       <legend><bean:message key="lbl.personalDetails"/></legend>      
	         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">                    
                     <tr>
                       <td width="23%" style="width:23%"><bean:message key="lbl.businessPartnerName" /><font color="red">*</font></td>
                       <td width="26%" style="width:26%"><html:text styleClass="text" property="businessPartnerName"  styleId="businessPartnerName" value="${buyerList[0].businessPartnerName}" maxlength="50" onkeyup="return upperMe('businessPartnerName');" /></td>
                        <td style="width:23%"><bean:message key="lbl.contactPerson" /></td>
                       <td><html:text styleClass="text" property="contactPerson"  styleId="contactPerson"  value="${buyerList[0].contactPerson}" maxlength="50" onkeyup="return upperMe('contactPerson');" />
                       </td>
                    </tr>
                    <tr>
                      <td ><bean:message key="lbl.designation" /></td>
                      <td><html:text styleClass="text" property="designation" styleId="designation"  value="${buyerList[0].designation}" maxlength="25"/></td>
                      <td><bean:message key="lbl.mobile" /></td>
                      <td><html:text styleClass="text" property="mobile" styleId="mobile"  value="${buyerList[0].mobile}" style="text-align: right" maxlength="10" />
                      </td>
                   </tr>
                   <tr>
                    <td ><bean:message key="lbl.email" /></td>
                    <td><html:text styleClass="text" property="email" styleId="email"  value="${buyerList[0].email}" maxlength="100"/>
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                   </tr>
                  </table>
                </td>
               </tr>
            </table>
         </fieldset>
         <fieldset>
	       <legend><bean:message key="lbl.businessTerms"/></legend>
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>      
                  <td>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                      <tr>
                       <td  style="width:23%"><bean:message key="lbl.averageMonthlySales" /><font color="red">*</font></td>
                       <td style="width:26%"><html:text styleClass="text"  property="averagePurchaseSales" maxlength="18"  styleId="averagePurchaseSales"  style="text-align: right" value="${buyerList[0].averagePurchaseSales}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
                       </td>
                       <td style="width:23%"><bean:message key="lbl.paymentTerms" /> <font color="red">*</font></td>
                        <td><html:text styleClass="text" property="paymentTerms"  styleId="paymentTerms" value="${buyerList[0].paymentTerms}" maxlength="25"/>
                       </td>
                      </tr>
                      <tr>
                       <td><bean:message key="lbl.productType" /><font color="red">*</font></td>
                       <td><html:text styleClass="text" property="productType" styleId="productType"  value="${buyerList[0].productType}" maxlength="25"/></td>
                       <td><bean:message key="lbl.vintageOfRelationship" /><font color="red">*</font></td>
                       <td><html:text styleClass="text" property="vintageOfRelationship"  styleId="vintageOfRelationship" value="${buyerList[0].vintageOfRelationship}"  style="text-align: right" maxlength="5"/></td>
                      </tr>            
                    </table>
                 </td>
               </tr>
            </table>
        </fieldset>
        <fieldset>
          <legend><bean:message key="lbl.commDetails"/></legend>
           <table width="100%"  border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td>
             <table cellspacing="1" cellpadding="1" border="0" width="100%">
	           <tr>
                 <td style="width:23%"><bean:message key="lbl.addressType" /></td>
                 <td style="width:26%">
                 <html:select property="address" styleId="address" styleClass="text"  value="${buyerList[0].address}">
                 <html:option value=""><bean:message key="lbl.select"/></html:option>
                 <logic:present name="detailcountryList"><html:optionsCollection name="detailcountryList" label="name" value="id" /></logic:present>
                 </html:select>
                 </td>
     
                 <td style="width:23%"><bean:message key="lbl.addressLine1" /></td>
                 <td ><html:text styleClass="text" property="addressLine1" styleId="addressLine1"  value="${buyerList[0].addressLine1}" maxlength="50" onkeyup="return upperMe('addressLine1');"/></td>
               </tr>
               <tr>
                 <td style="width:23%"><bean:message key="lbl.addressLine2" /></td>
                 <td ><html:text styleClass="text" property="addressLine2" styleId="addressLine2"  value="${buyerList[0].addressLine2}" maxlength="50" onkeyup="return upperMe('addressLine2');"/></td>
                 <td style="width:23%"><bean:message key="lbl.addressLine3" /></td>
                 <td ><html:text styleClass="text" property="addressLine3" styleId="addressLine3"  value="${buyerList[0].addressLine3}" maxlength="50" onkeyup="return upperMe('addressLine3');"/></td>
              </tr>
              <tr>
                 <td ><bean:message key="lbl.country" /></td>
                  <td><input type="hidden" name="hCommon" id="hCommon" value=""/>
                  
                  <html:text property="txtCountryCode" styleClass="text" styleId="txtCountryCode"  value="${buyerList[0].txtCountryCode}" maxlength="50" onkeyup="return upperMe('txtCountryCode');"/>
                  
                <%--  <html:text property="country" styleId="country" size="20" readonly="true" value="${buyerList[0].country}" styleClass="text" tabindex="-1"></html:text>
                 <html:hidden property="txtCountryCode" styleId="txtCountryCode"  value="${buyerList[0].txtCountryCode}" />                                              

                <html:button property="countryButton" styleId="countryButton" onclick="openLOVCommon(4,'BuyerForm','country','','','','','lovcountry','hCommon')" value=" " styleClass="lovbutton"> </html:button> 
            --%>
              <%--  <img onClick="openLOVCommon(4,'BuyerForm','country','','', '','','lovCountry','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif"></td> --%>               
              
                <td ><bean:message key="lbl.stateOrProvidence" /></td>
                 <td>
                  <html:text property="txtStateCode" styleClass="text" styleId="txtStateCode"  value="${buyerList[0].txtStateCode}" maxlength="50" onkeyup="return upperMe('txtStateCode');"/>
               <%-- 
                 <html:text property="state" styleId="state" size="20" readonly="true" value="${buyerList[0].state}" styleClass="text" tabindex="-1"></html:text>
                 <html:hidden property="txtStateCode" styleId="txtStateCode"  value="${buyerList[0].txtStateCode}" />
                 <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'BuyerForm','state','country','txtStateCode', 'txtCountryCode','Select First Country','lovState','hCommon')" value=" " styleClass="lovbutton"> </html:button>                                                         
                <img onClick="openLOVCommon (5,'BuyerForm','state','country','txtStateCode', 'txtCountryCode','Select First Country','lovState','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif"></td>
              
              --%>   
               
                </tr>
                <tr>
                      
         	   <td ><bean:message key="lbl.city" /></td>
         	   
          	   <td >
          	    <html:text property="txtDistCode" styleId="txtDistCode" styleClass="text"  maxlength="50" value="${buyerList[0].txtDistCode}" onkeyup="return upperMe('txtDistCode');"/>  
<%-- 
          	    <html:text property="dist" styleId="dist" size="20" readonly="true" value="${buyerList[0].dist}" styleClass="text" tabindex="-1"></html:text>
                <html:hidden property="txtDistCode" styleId="txtDistCode"  value="${buyerList[0].txtDistCode}" />  
                <html:button property="distButton" styleId="distButton" onclick="openLOVCommon(20,'BuyerForm','dist','state','txtDistCode', 'txtStateCode','Select First State','','hCommon')" value=" " styleClass="lovbutton"> </html:button>                                                    
               <img onClick="openLOVCommon (20,'BuyerForm','dist','state','txtDistCode', 'txtStateCode','Select First State','','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif"> --%> 
                </td>  
                <td ><bean:message key="lbl.tahsil" /></td> 
                 <td>
          	    <html:text property="txtTahsil" styleId="txtTahsil" styleClass="text"  maxlength="50" value="${buyerList[0].txtTahsil}" onkeyup="return upperMe('txtTahsil');"/>  </td>
               </tr>       
             <tr>     	 
             <td ><bean:message key="lbl.pincode" /></td>           
          	  <td ><html:text styleClass="text" property="pincode" styleId="pincode" value="${buyerList[0].pincode}"  style="text-align: right" maxlength="6" onkeypress="return isNumberKey(event);"/></td>        
  		 	  <td ><html:hidden property="bpType" value="B" styleClass="topformbutton2"/></td>  		 	  
  		 	  <td><html:hidden property="bpId" value="1" styleClass="topformbutton2"/></td>   
<!--  		 	  <td><html:hidden property="dealId" value="1" styleClass="topformbutton2"/></td>   -->
  		 	  <td><html:hidden property="chkvalue" styleId="chkvalue" value="${buyerList[0].primaryId}"/></td>  
            </tr>
            <tr> 
           <td align="left" colspan="3" class="form2">   
              <button type="button" name="button1" class="topformbutton2" id="saveBuyer" title="Alt+V" accesskey="V" onclick="return saveBuyerDetails();"><bean:message key="button.save" /></button>  
              <button type="button" name="button1" class="topformbutton2" id="saveNForBuyer" title="Alt+F" accesskey="F" onclick="return saveNForBuyerDetails();"><bean:message key="button.forward" /></button> 
             </td> 
           </tr>           
      	</table>
      	</td>
      	</tr>
      	</table>
	   </fieldset>
 	   <fieldset>	
	     <legend><bean:message key="lbl.buyerDetails"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2">        
        			 <td width="3%"><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"></td>
        			 <td ><strong><bean:message key="lbl.businessRelationship" /></strong></td>
		             <td ><strong><bean:message key="lbl.businessPartnerName" /></strong></td>
                     <td ><strong><bean:message key="lbl.averageMonthlySales" /></strong></td>
                     <td ><strong><bean:message key="lbl.paymentTerms" /></strong></td>
		             <td ><strong><bean:message key="lbl.productType" /></strong></td>
                     <td ><strong><bean:message key="lbl.vintageOfRelationship" /></strong></td>
<!--		             <td class="white2" style="width:100px;"><span style="width:23%"><strong><bean:message key="lbl.city" /></strong></span></td>		-->
	               </tr>
	                 <logic:present name="showdetails">
	                 <logic:iterate id="showdetails1" name="showdetails">	 
	               <tr class="white1"> 
	  				  <td ><input type="checkbox" name="chk" id="chk" value="${showdetails1.primaryId }"/></td>
	                  <td >${showdetails1.businessRelationship }</td>
	                  <td ><a href="#" onclick="return modifyBuyer(${showdetails1.primaryId });">${showdetails1.businessPartnerName }</a></td>
	                  <td >${showdetails1.averagePurchaseSales }</td>
	                  <td >${showdetails1.paymentTerms }</td>
	                  <td >${showdetails1.productType }</td> 	
	                  <td >${showdetails1.vintageOfRelationship }</td> 	
<!--	                  <td class="white">${showdetails1.dist }</td> 	-->
	               </tr>	 
                    </logic:iterate>
                    </logic:present>      
                </table>
              </td>
            </tr>
             <tr>
               <td ><button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteBuyer('N');"><bean:message key="button.delete" /></button>	
                           	 
	          </td>           
	        </tr> 	       
	      </table>   
	   </fieldset>	
     </fieldset>

</html:form>
</logic:notPresent>

<logic:present name="viewDeal">
<html:form action="/buyerProcessAction" styleId="BuyerForm" method="post">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />

<center><font color="red"><html:errors />${requestScope.sms }</font></center>  
	<fieldset id="buyerInfo" style="">	  	
	  <legend><bean:message key="lbl.buyersInformation"/></legend>
         <fieldset>
	       <legend><bean:message key="lbl.personalDetails"/></legend>      
	         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                    
                     <tr>
                       <td width="25%"><bean:message key="lbl.businessPartnerName" /><font color="red">*</font></td>
                       <td width="25%"><html:text styleClass="text" disabled="true" property="businessPartnerName" styleId="businessPartnerName"  value="${buyerList[0].businessPartnerName}" maxlength="50"/></td>
                       <td width="25%"><bean:message key="lbl.contactPerson" /></td>
                       <td ><html:text styleClass="text" disabled="true" property="contactPerson" styleId="contactPerson"  value="${buyerList[0].contactPerson}" maxlength="50"/>
                       </td>
                    </tr>
                    <tr>
                      <td><bean:message key="lbl.designation" /></td>
                      <td><html:text styleClass="text" disabled="true" property="designation" styleId="designation"  value="${buyerList[0].designation}" maxlength="25"/></td>
                      <td><bean:message key="lbl.mobile" /></td>
                      <td><html:text styleClass="text" disabled="true" property="mobile" style="text-align: right" styleId="mobile" value="${buyerList[0].mobile}" maxlength="10"/>
                      </td>
                   </tr>
                   <tr>
                    <td ><bean:message key="lbl.email" /></td>
                    <td><html:text styleClass="text" disabled="true" property="email" styleId="email"  value="${buyerList[0].email}" maxlength="100"/>
                    </td>
                    <td></td>
                    <td></td>
                   </tr>
                  </table>
                </td>
               </tr>
            </table>
         </fieldset>
         <fieldset>
	       <legend><bean:message key="lbl.businessTerms"/></legend>
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>      
                  <td>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                      <tr>
                       <td width="25%"><bean:message key="lbl.averageMonthlySales" /></td>
                       <td width="25%"><html:text styleClass="text" disabled="true" property="averagePurchaseSales"  style="text-align: right"  maxlength="20" onkeyup="checkNumber(this.value, event);" onblur="return formatNumber(this.value);" styleId="averagePurchaseSales" value="${buyerList[0].averagePurchaseSales}" />
                       </td>
                       <td width="25%"><bean:message key="lbl.paymentTerms" /> </td>
                       <td><html:text styleClass="text" disabled="true" property="paymentTerms" styleId="paymentTerms"  value="${buyerList[0].paymentTerms}" maxlength="25" onkeypress="return isNumberKey(event);"/>
                       </td>
                      </tr>
                      <tr>
                       <td><bean:message key="lbl.productType" /></td>
                       <td><html:text styleClass="text" disabled="true" property="productType" styleId="productType"  value="${buyerList[0].productType}" maxlength="25" onkeypress="return isNumberKey(event);"/></td>
                       <td><bean:message key="lbl.vintageOfRelationship" /></td>
                       <td><html:text styleClass="text" disabled="true" property="vintageOfRelationship"  styleId="vintageOfRelationship"  style="text-align: right" value="${buyerList[0].vintageOfRelationship}" maxlength="5" /></td>
                      </tr>            
                    </table>
                 </td>
               </tr>
            </table>
        </fieldset>
        <fieldset>
          <legend><bean:message key="lbl.commDetails"/></legend>
           <table width="100%"  border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td>
             <table cellspacing="1" cellpadding="1" border="0" width="100%">
	           <tr>
                 <td width="25%"><bean:message key="lbl.addressType" /></td>
                 <td width="25%">
                 <html:select property="address" styleId="address" styleClass="text"  value="${buyerList[0].address}" disabled="true">
                 <html:option value=""><bean:message key="lbl.select"/></html:option>
                 <logic:present name="detailcountryList"><html:optionsCollection name="detailcountryList" label="name" value="id" /></logic:present>
                 </html:select>
                 </td>
     
                 <td width="25%"><bean:message key="lbl.addressLine1" /></td>
                 <td><html:text styleClass="text" disabled="true" property="addressLine1"  styleId="addressLine1" value="${buyerList[0].addressLine1}" maxlength="50"/></td>
               </tr>
               <tr>
                 <td style="width:23%"><bean:message key="lbl.addressLine2" /></td>
                 <td ><html:text styleClass="text" property="addressLine2" styleId="addressLine2"  value="${buyerList[0].addressLine2}" maxlength="50" onkeyup="return upperMe('addressLine2');"/></td>
                 <td style="width:23%"><bean:message key="lbl.addressLine3" /></td>
                 <td ><html:text styleClass="text" property="addressLine3" styleId="addressLine3"  value="${buyerList[0].addressLine3}" maxlength="50" onkeyup="return upperMe('addressLine3');"/></td>
                </tr>
                <tr>
                 <td><bean:message key="lbl.country" /></td>
                  <td>
                 <html:hidden property="country"  styleId="country" value="${buyerList[0].country}" styleClass="text"  />
                 <html:text property="txtCountryCode" disabled="true" styleId="txtCountryCode"  maxlength="50" value="${buyerList[0].txtCountryCode}" styleClass="text"/>
				</td>
  	

                <td><bean:message key="lbl.stateOrProvidence" /></td>
                 <td>
                 <html:hidden property="state"  styleId="state"  value="${buyerList[0].state}" styleClass="text"  />
                 <html:text property="txtStateCode" styleId="txtStateCode" styleClass="text" disabled="true" maxlength="50" value="${buyerList[0].txtStateCode}" styleClass="text"/>
				</td>
                  </tr>
                  <tr>      
         	   <td><bean:message key="lbl.city" /></td>         	   
          	   <td> 
          	    <html:hidden property="dist" disabled="true" styleId="dist"  value="${buyerList[0].dist}" styleClass="text"  />
                <html:text property="txtDistCode" styleId="txtDistCode" disabled="true" maxlength="50" value="${buyerList[0].txtDistCode}" styleClass="text"/></td>
                <td ><bean:message key="lbl.tahsil" /></td> 
                 <td>
          	    <html:text property="txtTahsil" styleId="txtTahsil" styleClass="text"  maxlength="50" value="${buyerList[0].txtTahsil}" onkeyup="return upperMe('txtTahsil');"/>  </td>
                
                </tr>       
             <tr>	   
             <td><bean:message key="lbl.pincode" /></td>           
          	  <td><html:text styleClass="text" disabled="true" property="pincode" styleId="pincode" value="${buyerList[0].pincode}"  style="text-align: right" maxlength="6"/></td>        
  		 	  <td><html:hidden property="bpType" value="DB" styleClass="topformbutton2"/></td>  		 	  
  		 	  <td><html:hidden property="bpId" value="1" styleClass="topformbutton2"/></td>   
  		 	  <td><html:hidden property="dealId" value="1" styleClass="topformbutton2"/></td>   
  		 	  <td><html:hidden property="chkvalue" styleId="chkvalue" value="${buyerList[0].primaryId}"/></td>  
            </tr>
            <tr> 
           <td align="left" colspan="3" class="form2">   
<!--              <html:button property="button1" styleClass="topformbutton2" value="Save" onclick="return saveBuyerDetails();"/> </td>      -->
              
           </tr>           
      	</table>
      	</td>
      	</tr>
      	</table>
	   </fieldset>
	   <logic:notPresent name="verificationType">
 	   <fieldset>	
	     <legend><bean:message key="lbl.buyerDetails"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2" >        
        			 <td width="3%" ><input type="checkbox" disabled="disabled" name="allchk" id="allchk" onclick="allChecked();"></td>
        			 <td><strong><bean:message key="lbl.businessRelationship" /></strong></td>
		             <td><strong><bean:message key="lbl.businessPartnerName" /></strong></td>
                     <td><strong><bean:message key="lbl.averageMonthlySales" /></strong></td>
                     <td ><strong><bean:message key="lbl.paymentTerms" /></strong></td>
		             <td><strong><bean:message key="lbl.productType" /></strong></td>
                     <td ><strong><bean:message key="lbl.vintageOfRelationship" /></strong></td>
<!--		             <td class="white2" style="width:100px;"><span style="width:23%"><strong><bean:message key="lbl.city" /></strong></span></td>		-->
	               </tr>
	                 <logic:present name="showdetails">
	                 <logic:iterate id="showdetails1" name="showdetails">	 
	               <tr class="white1"> 
	  				  <td ><input type="checkbox" disabled="disabled" name="chk" id="chk" value="${showdetails1.primaryId }"/></td>
	                  <td >${showdetails1.businessRelationship }</td>
	                  <td ><a href="#" onclick="return modifyBuyer(${showdetails1.primaryId });">${showdetails1.businessPartnerName }</a></td>
	                  <td >${showdetails1.averagePurchaseSales }</td>
	                  <td >${showdetails1.paymentTerms }</td>
	                  <td >${showdetails1.productType }</td> 	
	                  <td>${showdetails1.vintageOfRelationship }</td> 	
<!--	                  <td class="white">${showdetails1.dist }</td> 	-->
	               </tr>	 
                    </logic:iterate>
                    </logic:present>      
                </table>
              </td>
            </tr>
             <tr>
<!--               <td class="white"><html:button property="button1" styleClass="topformbutton2" value="Delete" onclick="return deleteBuyer();"/>	-->
                           	 
      
	        </tr> 	       
	      </table>   
	   </fieldset>
	   </logic:notPresent>	
     </fieldset>

</html:form>
</logic:present>
</div>
</div>


<logic:present name="insertSuccess">
<script type="text/javascript">
	if('<%=request.getAttribute("insertSuccess").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.buyerSuccess" />');
	}
	else
	{
		alert('<bean:message key="lbl.errorSuccess" />');
	}	
</script>
</logic:present>


<logic:present name="updateSuccess">
<script type="text/javascript">
	if('<%=request.getAttribute("updateSuccess").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.updateSuccess" />');
	}
	else
	{
		alert('<bean:message key="lbl.erroruSuccess" />');
	}	
</script>
</logic:present>



<logic:present name="forwardSuccess">
<script type="text/javascript">
	if('<%=request.getAttribute("forwardSuccess").toString()%>'=='CNF')
	{
		alert('<bean:message key="lbl.cantForward" />');
	}
	else if('<%=request.getAttribute("forwardSuccess").toString()%>'=='CF')
	{
		alert('<bean:message key="lbl.canForward" />');
		parent.location="<%=request.getContextPath()%>/buyerSuppBehindAction.do?method=buyerSuppMaker";
	}else{
        	alert('${checkStageM}');
	}	
</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>
<logic:present name="buyerName">
	<script type="text/javascript">
	
		if(confirm("<%=request.getAttribute("buyerName")%>"+" <bean:message key="lbl.verifCapturingWarning" />"))
		{
			deleteBuyer('Y');
		}
			
	</script>
</logic:present>
<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.datadeleteSucc" />');
	}
	else
	{
		alert('<bean:message key="lbl.dataNtdeleteSucc" />');
	}	
</script>
</logic:present>	
</body>
</html:html>