<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 17-May-2011-->
<!--Purpose  : Information of Bank Master1-->
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>	 
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('productMaster').productDes.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('productMaster').productId.focus();
     }
     return true;
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

<body onload="disableFieldForDealMandatory();enableAnchor();fntab();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
  <html:form styleId="productMaster" action="/productMasterAdd">
 	<html:errors/>
   <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 
  <fieldset>
	<legend><bean:message key="lbl.productMaster"/></legend>
	<table width="100%"  >
  <tr>
  <td>
   
  
  <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
    <logic:iterate id="listObj" name="list">
    <tr>
    <td><bean:message key="lbl.productId"/><span><font color="red">*</font></span></td>
    <td><html:text property="productId" styleId="productId" maxlength="10" styleClass="text" onblur="fnChangeCase('productMaster','productId')" value="${listObj.productId}" readonly="true"/></td>
     
     <td><bean:message key="product.desc"/><span><font color="red">*</font></span></td>
    <td><html:text property="productDes" styleId="productDes" styleClass="text" maxlength="50" onblur="fnChangeCase('productMaster','productDes')" value="${listObj.productDes}"/></td>

 <td><bean:message key="lbl.productCategory"/><span><font color="red">*</font></span></td>
    <!-- <td><html:text property="productCategory" styleId="productCategory" styleClass="text" maxlength="10" onblur="fnChangeCase('productMaster','productCategory')" value="${listObj.productCategory}"/></td> -->
	<td> 
	
	
	  <html:select property="productCategory" styleId="productCategory" styleClass="text" value="${listObj.productCategory}">
			 <html:option  value="">--<bean:message key="lbl.select" />--</html:option >
			  <logic:present name="productCategory">
			 <html:optionsCollection name="productCategory" label="categoryDesc" value="category" />
			 </logic:present>
		</html:select>
	</td>
</tr>
 
 <tr>
  
      <td>
       <bean:message key="lbl.repaymentType"/></td>
       <td>
       
       <html:select property="repaymentType" styleId="repaymentType" styleClass="text" value="${listObj.repaymentType}" >
                <html:option value="I">INSTALLMENT BASED</html:option>
                <html:option value="N">NON INSTALLMENT BASED</html:option>
       </html:select>
     
      
      </td>
      
       
    <td>
       <bean:message key="lbl.productType"/></td>
       <td>
       
			<html:select property="assetFlag" styleId="assetFlag"  styleClass="text" value="${listObj.assetFlag}" onchange="disableFieldForDealMandatory();">
                <html:option value="A">ASSET BASED</html:option>
                <html:option value="N">NON-ASSET BASED  </html:option>
       </html:select>
		
       
      </td>
      
       <td>
       <bean:message key="lbl.revolvingFlag"/> </td>
       <td>
   			   <logic:equal value="Yes" name="revolvingFlag">
            		  <input type="checkbox" name="revolvingFlag" id="revolvingFlag" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Yes" name="revolvingFlag">
      	 		  <input type="checkbox" name="revolvingFlag" id="revolvingFlag"  />
         </logic:notEqual></td>
   
        
   </tr>
  
  <tr>
<td>
       <bean:message key="lbl.daysbasis"/></td>
       <td>
       <html:select property="daysBasis" styleId="daysBasis" styleClass="text" value="${listObj.daysBasis}">
                <html:option value="U">US 360</html:option>
                <html:option value="E">EURO 360</html:option>
        		<html:option value="A">ACTUAL</html:option>
        		<html:option value="I">360</html:option>
       </html:select>
      </td>
      
            <td>
       <bean:message key="lbl.daysPerYear"/></td>
    <td>
           <html:select property="daysPerYear" styleId="daysPerYear" styleClass="text" value="${listObj.daysPerYear}">
                <html:option value="360">360</html:option>
                <html:option value="365">365</html:option>
       </html:select></td>
      
      <td>
       <bean:message key="lbl.insRound"/></td>
       <td>
           <html:select property="insRound" styleId="insRound" styleClass="text" value="${listObj.insRound}">
                <html:option value="R">ROUND</html:option>
                <html:option value="U">ROUND UP</html:option>
                <html:option value="D">ROUND DOWN</html:option>
                <html:option value="A">ROUND NEAREST 5</html:option>
                <html:option value="B">ROUND UP NEAREST 5</html:option>
                <html:option value="C">ROUND DOWN NEAREST 5</html:option>
       </html:select>
     </td>
  </tr>
  <tr>    
      <td>
       <bean:message key="lbl.insRounding"/></td>
       <td>
      <html:select property="insRounding" styleId="insRounding" styleClass="text" value="${listObj.insRounding}">
               <html:option value="0" >&nbsp;0</html:option>
                <html:option value="1" >&nbsp;1</html:option>
                <html:option value="2">&nbsp;2</html:option>
                <html:option value="3">&nbsp;3</html:option>
                <html:option value="-1">-1</html:option>
                <html:option value="-2">-2</html:option>
                <html:option value="-3">-3</html:option>
       </html:select>
     </td>
      
      <td>
       <bean:message key="lbl.intRounType"/></td>
       <td>
       <html:select property="intRounType" styleId="intRounType" styleClass="text" value="${listObj.intRounType}">
                 <html:option value="R">ROUND</html:option>
                <html:option value="U">ROUND UP</html:option>
                <html:option value="D">ROUND DOWN</html:option>
                
       </html:select>
     </td>
      
      <td>
       <bean:message key="lbl.intRounding"/></td>
       <td>
            <html:select property="intRounding" styleId="intRounding" styleClass="text" value="${listObj.intRounding}">
                <html:option value="0">&nbsp;0</html:option>
                <html:option value="1" >&nbsp;1</html:option>
                <html:option value="2">&nbsp;2</html:option>
                <html:option value="3">&nbsp;3</html:option>
                <html:option value="-1">-1</html:option>
                <html:option value="-2">-2</html:option>
                <html:option value="-3">-3</html:option>
       </html:select>
     </td>
  </tr>
  <tr>
        <td>
       <bean:message key="lbl.opportunityRate"/></td>
     
    <td><html:text property="opportunityRate" style="text-align: right" styleId="opportunityRate" styleClass="text" value="${listObj.opportunityRate}" onchange="return checkRate('opportunityRate');"   onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
     <td><bean:message key="lbl.oneDealOneLoan"/></td>
    <td>
    <html:select property="oneDealOneLoan" styleId="oneDealOneLoan" styleClass="text" value="${listObj.oneDealOneLoan}" >
                <html:option value="N">No</html:option>
                <html:option value="Y" >Yes</html:option>
               </html:select>
    </td>
    <!-- Changes Start By Richa -->
   	<td><bean:message key="lbl.productLoanType"/><span><font color="red">*</font></span></td>
    <td>
    <html:select property="productLoanType" styleId="productLoanType" styleClass="text" value="${listObj.productLoanType}" >
    			<html:option value="">--Select--</html:option>
                <html:option value="HP">Higher Purchase</html:option>
                <html:option value="LS" >Lease</html:option>
                <html:option value="LO" >Loan</html:option>
               </html:select>
    </td>
</tr>
    <tr>
        <td id="dis1" ><bean:message key="lbl.assetMandatoryAtDeal"/></td>
    <td id="dis2" >
    <html:select property="assetMandatoryAtDeal" styleId="assetMandatoryAtDeal" styleClass="text" value="${listObj.assetMandatoryAtDeal}">
                <html:option value="N">No</html:option>
                <html:option value="Y" >Yes</html:option>
    </html:select>
    </td>
    
    
    <!-- Changes end By Richa -->
   <td>
     	<bean:message key="lbl.recStatus"/> </td>
        <td>
     			 <logic:equal value="Active" name="status">
              			<input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Active" name="status">
      	 		  <input type="checkbox" name="recStatus" id="recStatus"  />
         </logic:notEqual></td>
         
         <td>
     	<bean:message key="lbl.interestAdvance"/> </td>
        <td>
     			 <logic:equal value="Yes" name="interestAdvanceActive">
              			<input type="checkbox" name="interestAdvance" id="interestAdvance" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Yes" name="interestAdvanceActive">
      	 		  <input type="checkbox" name="interestAdvance" id="interestAdvance"  />
         </logic:notEqual></td>
  
 </tr>
  
       </logic:iterate>
   </logic:present>
    
    
    <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
    <tr>
    <td><bean:message key="lbl.productId"/><span><font color="red">*</font></span></td>
    <td><html:text property="productId" styleId="productId" maxlength="10" styleClass="text" onblur="fnChangeCase('productMaster','productId')"  /></td>
     
     <td><bean:message key="product.desc"/><span><font color="red">*</font></span></td>
    <td><html:text property="productDes" styleId="productDes" styleClass="text" maxlength="50" onblur="fnChangeCase('productMaster','productDes')" /></td>

 <td><bean:message key="lbl.productCategory"/><span><font color="red">*</font></span></td>
    <!--  <td><html:text property="productCategory" styleId="productCategory" styleClass="text" maxlength="10" onblur="fnChangeCase('productMaster','productCategory')"  onchange="return checkRate('opportunityRate');"/></td>-->
  <td> 
	  <html:select property="productCategory" styleId="productCategory" styleClass="text" value="${requestScope.list[0].productCategory}">
		<html:option  value="">--<bean:message key="lbl.select" />--</html:option >
			 <logic:present name="productCategory">
			 <html:optionsCollection name="productCategory" label="categoryDesc" value="category" />
			 </logic:present>
		</html:select>
	</td>
</tr>
 
<tr>
  
      <td>
       <bean:message key="lbl.repaymentType"/></td>
       <td>
      
       <html:select property="repaymentType" styleId="repaymentType" styleClass="text" >
                <html:option value="I">INSTALLMENT BASED</html:option>
                <html:option value="N">NON INSTALLMENT BASED</html:option>
       </html:select>
      
      
       </td>
      
       
    <td>
       <bean:message key="lbl.productType"/></td>
       <td>
       
       
			<html:select property="assetFlag" styleId="assetFlag"  styleClass="text" onchange="disableFieldForDealMandatory();">
                <html:option value="A">ASSET BASED</html:option>
                <html:option value="N">NON-ASSET BASED  </html:option>
       </html:select>
	
      
      </td>
      
       <td>
       <bean:message key="lbl.revolvingFlag"/> </td>
       <td>
   			   <logic:equal value="Yes" name="revolvingFlag">
            		  <input type="checkbox" name="revolvingFlag" id="revolvingFlag" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Yes" name="revolvingFlag">
      	 		  <input type="checkbox" name="revolvingFlag" id="revolvingFlag"  />
         </logic:notEqual></td>
   
        
   </tr>
  
      
  <tr>
<td>
       <bean:message key="lbl.daysbasis"/></td>
       <td>
       <html:select property="daysBasis" styleId="daysBasis" styleClass="text" >
               <html:option value="U">US 360</html:option>
               <html:option value="E">EURO 360</html:option>
        	   <html:option value="A">ACTUAL</html:option>
        	   <html:option value="I">360</html:option>
       </html:select>
      </td>
      
      <td>
       <bean:message key="lbl.daysPerYear"/></td>
    <td>
           <html:select property="daysPerYear" styleId="daysPerYear" styleClass="text" >
                <html:option value="360">360</html:option>
                <html:option value="365">365</html:option>
       </html:select></td>
      
      <td>
       <bean:message key="lbl.insRound"/></td>
       <td>
           <html:select property="insRound" styleId="insRound" styleClass="text" >
                <html:option value="R">ROUND</html:option>
                <html:option value="U">ROUND UP</html:option>
                <html:option value="D">ROUND DOWN</html:option>
                 <html:option value="A">ROUND NEAREST 5</html:option>
                <html:option value="B">ROUND UP NEAREST 5</html:option>
                <html:option value="C">ROUND DOWN NEAREST 5</html:option>
       </html:select>
     </td>
  </tr>
  <tr>    
      <td>
       <bean:message key="lbl.insRounding"/></td>
       <td>
      <html:select property="insRounding" styleId="insRounding" styleClass="text" >
               <html:option value="0">&nbsp;0</html:option>
                <html:option value="1" >&nbsp;1</html:option>
                <html:option value="2">&nbsp;2</html:option>
                <html:option value="3">&nbsp;3</html:option>
                <html:option value="-1">-1</html:option>
                <html:option value="-2">-2</html:option>
                <html:option value="-3">-3</html:option>
       </html:select>
     </td>
      
      <td>
       <bean:message key="lbl.intRounType"/></td>
       <td>
       <html:select property="intRounType" styleId="intRounType" styleClass="text" >
                 <html:option value="R">ROUND</html:option>
                <html:option value="U">ROUND UP</html:option>
                <html:option value="D">ROUND DOWN</html:option>
               
       </html:select>
     </td>
      
      <td>
       <bean:message key="lbl.intRounding"/></td>
       <td>
            <html:select property="intRounding" styleId="intRounding" styleClass="text" >
                <html:option value="0">&nbsp;0</html:option>
                <html:option value="1" >&nbsp;1</html:option>
                <html:option value="2">&nbsp;2</html:option>
                <html:option value="3">&nbsp;3</html:option>
                <html:option value="-1">-1</html:option>
                <html:option value="-2">-2</html:option>
                <html:option value="-3">-3</html:option>
       </html:select>
     </td>
  </tr>
  <tr>
        <td>
       <bean:message key="lbl.opportunityRate"/></td>
    <td><html:text property="opportunityRate" style="text-align: right" styleId="opportunityRate" styleClass="text" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('opportunityRate');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
      <td><bean:message key="lbl.oneDealOneLoan"/></td>
    <td>
    <html:select property="oneDealOneLoan" styleId="oneDealOneLoan" styleClass="text" >
                <html:option value="N">No</html:option>
                <html:option value="Y" >Yes</html:option>
               </html:select>
    </td>
        <!-- Changes Start By Richa -->
   	<td><bean:message key="lbl.productLoanType"/><span><font color="red">*</font></span></td>
    <td>
    <html:select property="productLoanType" styleId="productLoanType" styleClass="text"  >
    			<html:option value="">--Select--</html:option>
                <html:option value="HP">Higher Purchase</html:option>
                <html:option value="LS" >Lease</html:option>
                <html:option value="LO" >Loan</html:option>
               </html:select>
    </td>
</tr>
    <tr>
    <!-- Changes end By Richa -->   
    <td id="dis1"><bean:message key="lbl.assetMandatoryAtDeal"/></td>
    <td id="dis2">
    <html:select property="assetMandatoryAtDeal" styleId="assetMandatoryAtDeal" styleClass="text" >
                <html:option value="N">No</html:option>
                <html:option value="Y" >Yes</html:option>
    </html:select>
    </td>
    
   <td>
     	<bean:message key="lbl.recStatus"/> </td>
        <td>
              			<input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
         </td>
         
         <td>
     	<bean:message key="lbl.interestAdvance"/> </td>
        <td>
              			<input type="checkbox" name="interestAdvance" id="interestAdvance" checked="checked" />
         </td>
  
 </tr>

   </logic:notPresent>

   
	 <logic:present name="editVal">
	 	 <td>
	 	 <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditProduct();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
	 </logic:present>

     <logic:present name="editValUpdate">
	 	 <td>
	 	 <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditProduct();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
	 </logic:present>
	 
	 <logic:present name="save">
	  <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
		<td>
		<button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnproductSave();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
	 </logic:present>	
	 
	 
 </tr>

  </table></fieldset>
</html:form>

<logic:present name="sms">
		<script type="text/javascript">

			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" />");
				document.getElementById("productMaster").action="productMasterBehind.do";
	    		document.getElementById("productMaster").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.dataModify" />");
				document.getElementById("productMaster").action="productMasterBehind.do";
	    		document.getElementById("productMaster").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="E")
					{
						alert("<bean:message key="lbl.dataExist" />");
					}
				else
					{
						alert("<bean:message key="msg.notepadError" />");
					}
	</script>
</logic:present>
   
  </body>
</html:html>
