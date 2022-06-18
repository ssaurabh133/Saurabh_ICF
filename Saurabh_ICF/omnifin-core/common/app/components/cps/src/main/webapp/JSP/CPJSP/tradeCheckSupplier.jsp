<%--
      Author Name-  Ankit agarwal    
      Date of creation -15/09/2011
      Purpose-          
      Documentation-     
      
 --%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       Date currentDate = new Date();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
	   	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/tradeCheck.js"></script>
	   	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	   
  
     
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
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
	</head>

	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('tradeSuplierForm').refrenceNO.focus();init_fields();">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
	
		<%
		
	String tradeCheck=request.getParameter("tradeCheck");
	request.setAttribute("tradeCheck",tradeCheck);
	
	String dealNo=request.getParameter("dealNo");
	request.setAttribute("dealNo",dealNo);
	
	String verId=request.getParameter("verId");
	request.setAttribute("verId",verId);

	String entType=request.getParameter("entType");
	request.setAttribute("entType",entType);
	
	String entID=request.getParameter("entID");
	request.setAttribute("entID",entID);
 %>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
 
<html:form action="/tradeBuyerDetails" styleId="tradeSuplierForm" method="POST">
<html:javascript formName="TradeCheckCapBuyerSupplierDynaValidatorForm"/>
<html:hidden property="tradeCheck1" styleId="tradeCheck1" styleClass="text" value="S" />
<html:hidden property="dealNo1" styleId="dealNo1" styleClass="text" value="${dealNo}" />
<html:hidden property="verId" styleId="verId" styleClass="text" value="${verId}" />
<html:hidden property="entityType1" styleId="entityType1" styleClass="text" value="${entType}" />
<html:hidden property="entityId1" styleId="entityType1" styleClass="text" value="${entID}" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<fieldset>
	<legend><bean:message key="lbl.buyersCheck"/> </legend>
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1"> 
                    <logic:notPresent name="list">                  
					<tr>
                       <td ><bean:message key="lbl.refrenceNo" /></td>
                       <td ><html:text styleClass="text" property="refrenceNO" styleId="refrenceNO" value="" onblur="fnChangeCase('tradeSuplierForm','refrenceNO')" maxlength="20"/></td>
                       <td><bean:message key="lbl.tradeAppraiserName"/><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="appraiser" styleId="appraiser" value="" onblur="fnChangeCase('tradeSuplierForm','appraiser')" maxlength="50"/></td>
                    </tr>
                 <tr>
                   <td><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
                   <td><html:text property="appraisalDate" styleId="appraisalDate" styleClass="text" readonly="true" onchange="validateLeadDateApp();checkDateApp();" /></td>
					<td><bean:message key="lbl.reportDate" /></td>
					<td><html:text property="reportDate" styleId="reportDate" maxlength="10" styleClass="text" tabindex="-1" value="${userobject.businessdate }" disabled="true"/>
        	  			 <html:hidden property="reportDate" styleClass="text" styleId="reportDate" value="${userobject.businessdate }" />
        	   </td>
                  </tr>
           <tr>
                 	<td><bean:message key="lbl.verificationMode" /></td>
						 <td>
							 <html:select property="verificationMode" styleId="verificationMode" styleClass="text">
								    <option value="F">Field Visit</option>
									<option value="P">Phone</option>
									<option value="W">Website</option>
							</html:select> 
						</td>
			
                    	 <td><bean:message key="lbl.contactPerson"/><font color="red">*</font></td>
                    	 <td><html:text styleClass="text" property="contactPerson" styleId="contactPerson" onblur="fnChangeCase('tradeSuplierForm','contactPerson')" value="" maxlength="50"/></td>
                    	</tr>                    
                     <tr> 
                    	 <td><bean:message key="lbl.relation"/><font color="red">*</font></td>
                    	 <td><html:text styleClass="text" property="relation" maxlength="50" styleId="relation" onblur="fnChangeCase('tradeSuplierForm','relation')" value=""/> </td>
		               	<td><bean:message key="lbl.mobile"/></td>
                      	<td><html:text styleClass="text" property="phone1" styleId="phone1" style="text-align: right" value="" onkeypress="return isNumberKey(event);" maxlength="10" ></html:text> </td>
                      	 </tr> 
                      <tr>
                      	<td><bean:message key="lbl.tradePhone"/></td>
                      	<td><html:text styleClass="text" property="phone2" styleId="phone2" style="text-align: right" value="" onkeypress="return isNumberKey(event);" maxlength="20" ></html:text> </td>
                      	<td ><bean:message key="lbl.email" /></td>
                    	<td><html:text styleClass="text" property="email" styleId="email" value="" maxlength="100"/></td>
                      		</tr>
                      	<tr>
                      	<td ><bean:message key="lbl.TradeaverageMontSale" /><font color="red">*</font></td>
                      	<td><html:text styleClass="text"  property="averagePurchase" maxlength="18" value="" styleId="averagePurchaseSales"  style="text-align: right" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
                      	</td>
                      	<td><bean:message key="lbl.paymentTerms" /><font color="red">*</font></td>
                      	<td><html:text styleClass="text" property="paymentTerms"  styleId="paymentTerms" value="" maxlength="25" />
                      	</td>
                      	</tr>
                      	<tr>
               			<td><bean:message key="lbl.productType" /><font color="red">*</font></td>
                       <td><html:text styleClass="text" property="productType" styleId="productType" value="" maxlength="50"/></td>      
                       <td><bean:message key="lbl.vintageOfRelationship" /><font color="red">*</font></td>
                       <td><html:text styleClass="text" property="vintageOfRelationship" value="" styleId="vintageOfRelationship" style="text-align: right" maxlength="3" onkeypress="return isNumberKey(event);"/></td>						    
               </tr>
                      	<tr>
 				<td><bean:message key="lbl.address" /></td>
                <td><html:text styleClass="text" property="address" value="" onblur="fnChangeCase('tradeSuplierForm','address')" styleId="address" onblur="fnChangeCase('tradeSuplierForm','address')" maxlength="500" /></td>
				<td ><bean:message key="lbl.Tradecity" /></td>
          	    <td><html:text property="city" styleId="city" styleClass="text" onblur="fnChangeCase('tradeSuplierForm','city')" value="" maxlength="50"/></td>	
					</tr>
          	 <tr>
			   <td><bean:message key="lbl.stateOrProvidence" /></td>
                <td><html:text property="txtStateCode" styleClass="text" styleId="txtStateCode" onblur="fnChangeCase('tradeSuplierForm','txtStateCode')" value="" maxlength="50"/></td>
                 <td ><bean:message key="lbl.country" /></td>
                 <td><html:text property="txtCountryCode" styleClass="text" styleId="txtCountryCode" onblur="fnChangeCase('tradeSuplierForm','txtCountryCode')" value="" maxlength="50" /></td>
                      </tr> 
           <tr>
                 <td ><bean:message key="lbl.pincode" /></td>           
          	  <td ><html:text styleClass="text" property="pincode" styleId="pincode" value=""  style="text-align: right" maxlength="20" onkeypress="return isNumberKey(event);"/></td>        
         		<td><bean:message key="lbl.appRemark"/><font color="red">*</font></td>
				<td><textarea name="textarea" class="text" maxlength="1000" id="textarea" cols="70" onblur="fnChangeCase('tradeSuplierForm','textarea')" rows="1"></textarea></td>
				</tr>
             <tr>
            <td><button type="button" name="buyerButton" id="save"  class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveTradeSuplierDetails('${dealNo}')"><bean:message key="button.save" /></button> </td>
            </tr>
      </logic:notPresent> 
      
               <!-- TradeCheck for modify screen -->
        
         <logic:present name="list">
         <html:hidden property="tradeCheckId" styleId="tradeCheckId" value="${list[0].tradeCheckId}"/>
          <tr>
                       <td ><bean:message key="lbl.refrenceNo" /><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="refrenceNO" styleId="refrenceNO" onblur="fnChangeCase('tradeSuplierForm','refrenceNO')" value="${list[0].refrenceNO}" maxlength="20"/></td>
                       <td><bean:message key="lbl.tradeAppraiserName"/><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="appraiser" styleId="appraiser" onblur="fnChangeCase('tradeSuplierForm','appraiser')"  value="${list[0].appraiser}" maxlength="50"/></td>
                    </tr>
                 <tr>
                   <td><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
                   <td><html:text property="appraisalDate" styleId="appraisalDate" styleClass="text" readonly="true" value="${list[0].appraisalDate}" onchange="validateLeadDateApp();checkDateApp();" /></td>
                   <td><bean:message key="lbl.reportDate" /></td>
					<td><html:text property="reportDate" styleId="reportDate" maxlength="10" styleClass="text" value="${userobject.businessdate }" disabled="true" tabindex="-1"/>
        	  			 <html:hidden property="reportDate" styleClass="text" styleId="reportDate" value="${userobject.businessdate }" />
        	   </td>
                  </tr>
           <tr>
                 	<td><bean:message key="lbl.verificationMode" /></td>
						 <td>
							 <html:select property="verificationMode" styleId="verificationMode" styleClass="text" value="${list[0].verificationMode}">
								    <html:option value="F">Field Visit</html:option>
									<html:option value="P">Phone</html:option>
									<html:option value="W">Website</html:option>
							</html:select> 
						</td>
			
                    	 <td><bean:message key="lbl.contactPerson"/><font color="red">*</font></td>
                    	 <td><html:text styleClass="text" property="contactPerson" styleId="contactPerson" onblur="fnChangeCase('tradeSuplierForm','contactPerson')" value="${list[0].contactPerson}" maxlength="50"/></td>
                    	</tr>                    
                     <tr> 
                    	 <td><bean:message key="lbl.relation"/><font color="red">*</font></td>
                    	 <td><html:text styleClass="text" property="relation" maxlength="50" onblur="fnChangeCase('tradeSuplierForm','relation')"  styleId="relation" value="${list[0].relation}" /> </td>
		               	<td><bean:message key="lbl.mobile"/></td>
                      	<td><html:text styleClass="text" property="phone1" styleId="phone1" style="text-align: right" value="${list[0].phone1}" onkeypress="return isNumberKey(event);" maxlength="10" ></html:text> </td>
                      	 </tr> 
                      <tr>
                      	<td><bean:message key="lbl.tradePhone"/></td>
                      	<td><html:text styleClass="text" property="phone2" styleId="phone2" style="text-align: right" value="${list[0].phone2}" onkeypress="return isNumberKey(event);" maxlength="20" ></html:text> </td>
                      	<td ><bean:message key="lbl.email" /></td>
                    	<td><html:text styleClass="text" property="email" styleId="email" value="${list[0].email}" maxlength="100"/></td>
                      		</tr>
                      	<tr>
                      	<td ><bean:message key="lbl.TradeaverageMontSale" /><font color="red">*</font></td>
                      	<td><html:text styleClass="text"  property="averagePurchase" maxlength="18" value="${list[0].averagePurchase}" styleId="averagePurchaseSales"  style="text-align: right" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
                      	</td>
                      	<td><bean:message key="lbl.paymentTerms" /><font color="red">*</font></td>
                      	<td><html:text styleClass="text" property="paymentTerms"  styleId="paymentTerms" value="${list[0].paymentTerms}" maxlength="25" />
                      	</td>
                      	</tr>
                      	<tr>
               			<td><bean:message key="lbl.productType" /><font color="red">*</font></td>
                       <td><html:text styleClass="text" property="productType" styleId="productType" value="${list[0].productType}" maxlength="50"/></td>      
                       <td><bean:message key="lbl.vintageOfRelationship" /><font color="red">*</font></td>
                       <td><html:text styleClass="text" property="vintageOfRelationship" value="${list[0].vintageOfRelationship}" styleId="vintageOfRelationship" style="text-align: right" maxlength="3" onkeypress="return isNumberKey(event);"/></td>						    
               </tr>
                      	<tr>
 				<td><bean:message key="lbl.address" /></td>
                <td><html:text styleClass="text" property="address" value="${list[0].address}" onblur="fnChangeCase('tradeSuplierForm','address')" styleId="address" maxlength="500" /></td>
				<td ><bean:message key="lbl.Tradecity" /></td>
          	    <td><html:text property="city" styleId="city" styleClass="text" onblur="fnChangeCase('tradeSuplierForm','city')" value="${list[0].city} " maxlength="50"/></td>	
					</tr>
          	 <tr>
			   <td><bean:message key="lbl.stateOrProvidence" /></td>
                <td><html:text property="txtStateCode" styleClass="text" styleId="txtStateCode" onblur="fnChangeCase('tradeSuplierForm','txtStateCode')" value="${list[0].txtStateCode}" maxlength="50"/></td>
                 <td ><bean:message key="lbl.country" /></td>
                 <td><html:text property="txtCountryCode" styleClass="text" styleId="txtCountryCode" onblur="fnChangeCase('tradeSuplierForm','txtCountryCode')" value="${list[0].txtCountryCode}" maxlength="50" /></td>
                      </tr> 
           <tr>
                 <td ><bean:message key="lbl.pincode" /></td>           
          	  <td ><html:text styleClass="text" property="pincode" styleId="pincode" value="${list[0].pincode}"  style="text-align: right" maxlength="20" onkeypress="return isNumberKey(event);"/></td>        
         		<td><bean:message key="lbl.appRemark"/><font color="red">*</font></td>
      		<td><textarea class="text" maxlength="1000" name="textarea" id="textarea" cols="70" onblur="fnChangeCase('tradeSuplierForm','textarea')" rows="1" >${list[0].textarea}</textarea></td>
      		</tr>
            <tr>
                <td><button type="button" name="buyerButton" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return modifyTradeSuplierDetails()"><bean:message key="button.save" /></button> </td>
            </tr>
         </logic:present>
    </table>
</td>
</tr>
</table>
</fieldset>

	</html:form>
</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
</body>
</html:html>