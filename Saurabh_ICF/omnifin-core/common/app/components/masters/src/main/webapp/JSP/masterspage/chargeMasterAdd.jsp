<!--Author Name : Ritu Jindal-->
<!--Date of Creation : 12 May 2011-->
<!--Purpose  : Information of Charge Master Add-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/chargeScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('chargeMasterForm').schemeButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('chargeMasterForm').productButton.focus();
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

<body onload="enableAnchor();fndisable();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="chargeMasterForm"  method="post"  action="/chargeMasterAdd" >
		
<fieldset>
<legend><bean:message key="lbl.chargeMaster" /></legend>
 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
  <table width="100%">
    <html:hidden  property="charge" styleId="charge"  value="${requestScope.list[0].charge}" />

    <logic:present name="editVal">
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
     <tr>
     <td><bean:message key="lbl.product" /><span><font color="red">*</font></span></td>
      <td><html:text property="productId" styleClass="text" styleId="productId" maxlength="10" readonly="true" value="${requestScope.list[0].productId}" /></td>
       
       <td><bean:message key="lbl.scheme" /></td>
      <td><html:text property="schemeId" styleClass="text" styleId="schemeId" maxlength="10" readonly="true" value="${requestScope.list[0].schemeId}" tabindex="-1"/>
     <%--  <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(25,'chargeMasterForm','schemeId','','', '','','','lbxScheme')" value=" " styleClass="lovbutton"> </html:button>--%>
      <%-- <img onClick="openLOVCommon(25,'chargeMasterForm','schemeId','','', '','','','lbxScheme')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>
      <html:hidden  property="lbxScheme" styleId="lbxScheme"  value="${requestScope.list[0].lbxScheme}"/>
     </td>
     </tr>
    <tr>
       <td><bean:message key="lbl.chargeCode" /><span><font color="red">*</font></span></td>
       <td><html:text property="chargeCode" styleClass="text" styleId="chargeCode" maxlength="10" readonly="true" tabindex="-1" value="${requestScope.list[0].lbxCharge}" />
       <html:hidden property="lbxCharge" styleId="lbxCharge" value="${requestScope.list[0].lbxCharge}"/>
       </td>
       <td><bean:message key="lbl.chargeDescription"/></td>
       <td><html:text property="chargeDes" styleClass="text" styleId="chargeDes"  readonly="true" value="${requestScope.list[0].chargeId}" tabindex="-1"/>
       
     </tr>
       
    </logic:present>
    
    <logic:notPresent name="editVal">
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
     <tr>
    <td><bean:message key="lbl.product" /><span><font color="red">*</font></span></td>
      <td><html:text property="productId" styleClass="text" styleId="productId"  readonly="true" tabindex="-1" />
     <%--  <img onClick="openLOVCommon(17,'chargeMasterForm','productId','','', '','','','lbxProductID')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
     <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(17,'chargeMasterForm','productId','','schemeId', 'lbxScheme','','','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>
     <html:hidden  property="lbxProductID" styleId="lbxProductID"  />
     </td>
    
     <td><bean:message key="lbl.scheme" /></td>
      <td><html:text property="schemeId" styleClass="text" styleId="schemeId"  readonly="true"  tabindex="-1" />
     <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(25,'chargeMasterForm','schemeId','productId','lbxScheme', 'lbxProductID','Please Select Product','','lbxScheme');closeLovCallonLov('productId');" value=" " styleClass="lovbutton"> </html:button>
      <%-- <img onClick="openLOVCommon(25,'chargeMasterForm','schemeId','','', '','','','lbxScheme')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
      <html:hidden  property="lbxScheme" styleId="lbxScheme"  />
     </td>

    </tr>
    <tr>
     <td><bean:message key="lbl.chargeCode" /><span><font color="red">*</font></span></td>
     <td><html:text property="chargeCode" styleClass="text" styleId="chargeCode"  readonly="true" tabindex="-1" />
      <html:button property="chargeButton" styleId="chargeButton" onclick="openLOVCommon(124,'chargeMasterForm','chargeDes','','', '','','','chargeCode');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
      <%-- <img onClick="openLOVCommon(124,'chargeMasterForm','chargeDes','','', '','','','chargeCode')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
      <html:hidden  property="lbxCharge" styleId="lbxCharge" />
     </td>
    
      <td><bean:message key="lbl.chargeDescription"/></td>
       <td><html:text property="chargeDes" styleClass="text" styleId="chargeDes" maxlength="10" readonly="true" tabindex="-1"/>
    </tr>
   
    </logic:notPresent >
      <logic:present name="editVal">
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
     <tr>  
      <td><bean:message key="lbl.stage" /></td>
       <td><html:text property="stageId" styleClass="text" styleId="stageId"  readonly="true" value="${requestScope.list[0].stageId}" tabindex="-1" />
       <html:button property="stageButton" styleId="stageButton" onclick="openLOVCommon(23,'chargeMasterForm','stageId','','', '','','','lbxStage');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
       <%-- <img onClick="openLOVCommon(23,'chargeMasterForm','stageId','','', '','','','lbxStage')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
       <html:hidden  property="lbxStage" styleId="lbxStage"  value="${requestScope.list[0].lbxStage}"/>
      </td>
    
     <td><bean:message key="lbl.appStage" /></td>
      <td><html:text property="appStageId" styleClass="text" styleId="appStageId"  readonly="true" value="${requestScope.list[0].appStageId}" tabindex="-1" />
     <html:button property="appStageButton" styleId="appStageButton" onclick="openLOVCommon(26,'chargeMasterForm','appStageId','','', '','','','lbxAppStage');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
      <%-- <img onClick="openLOVCommon(26,'chargeMasterForm','appStageId','','', '','','','lbxAppStage')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
      <html:hidden  property="lbxAppStage" styleId="lbxAppStage"  value="${requestScope.list[0].lbxAppStage}"/>
     </td></tr>
     <tr>
     <td><bean:message key="lbl.chargeBPType" /><span><font color="red">*</font></span></td>
      <td><html:text property="chargeBPType" styleClass="text" styleId="chargeBPType" readonly="true" value="${requestScope.list[0].hBuyerSupplierBPType}" tabindex="-1" />
      <html:button property="chargeBPTypeButton" styleId="chargeBPTypeButton" onclick="openLOVCommon(19,'chargeMasterForm','chargeBPType','','', '','','','hBuyerSupplierBPType');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
      <%-- <img onClick="openLOVCommon(19,'chargeMasterForm','chargeBPType','','', '','','','hBuyerSupplierBPType')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
      <html:hidden  property="hBuyerSupplierBPType" styleId="hBuyerSupplierBPType"  value="${requestScope.list[0].chargeBPType}"/>
     </td>
     
      <td><bean:message key="lbl.chargeType" /><span><font color="red">*</font></span></td>
		    <td><html:select property="chargeType" styleId="chargeType" styleClass="text" value="${requestScope.list[0].chargeType}">
              	     <html:option  value="">--Select--</html:option>
              	      <html:option  value="P">Payable</html:option>
		      	      <html:option value="R">Receivable</html:option>
	 </html:select></td></tr>
	 
	 <tr>
	 
	 
	 <td><bean:message key="lbl.chargeMethod" /><span><font color="red">*</font></span></td>
		    <td><html:select property="chargeMethod" styleId="chargeMethod" styleClass="text" onchange="fnDisabled();fnApplicable();return fnMinChargeMehtod('minChargeAmount');return fnApplicable1('chargeAmount');" value="${requestScope.list[0].chargeMethod}" >
              	      <html:option  value="F">Flat</html:option>
		      	      <html:option value="P">Percent</html:option>
	 </html:select></td>
	 
	 
	 <td><bean:message key="lbl.chargeAmount" /><span><font color="red">*</font></span></td>

      <td><html:text property="chargeAmount" styleClass="text" style="text-align: right" styleId="chargeAmount" onkeypress="return numbersonly(event,id,16);" onblur="formatNumber(this.value,id);fnApplicable1('chargeAmount');" onkeyup="checkNumber(this.value, event, 16,id);" onfocus="keyUpNumber(this.value, event, 16,id);"  value="${requestScope.list[0].chargeAmount}" /></td>
	 </tr>
	 </logic:present>
	 
	 
	 <logic:notPresent name="editVal">
	 <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
     <tr>  
      <td><bean:message key="lbl.stage" /></td>
       <td><html:text property="stageId" styleClass="text" styleId="stageId" tabindex="-1" readonly="true"  />
       <html:button property="stageButton" styleId="stageButton" onclick="openLOVCommon(23,'chargeMasterForm','stageId','','', '','','','lbxStage');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
      <%--  <img onClick="openLOVCommon(23,'chargeMasterForm','stageId','','', '','','','lbxStage')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
      <html:hidden  property="lbxStage" styleId="lbxStage" />
      </td>
    
     <td><bean:message key="lbl.appStage" /></td>
      <td><html:text property="appStageId" styleClass="text" styleId="appStageId" tabindex="-1" readonly="true"  />
     <html:button property="appStageButton" styleId="appStageButton" onclick="openLOVCommon(26,'chargeMasterForm','appStageId','','', '','','','lbxAppStage');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
     <%--  <img onClick="openLOVCommon(26,'chargeMasterForm','appStageId','','', '','','','lbxAppStage')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
     <html:hidden  property="lbxAppStage" styleId="lbxAppStage" />
     </td></tr>
     <tr>
     <td><bean:message key="lbl.chargeBPType" /><span><font color="red">*</font></span></td>
      <td><html:text property="chargeBPType" styleClass="text" styleId="chargeBPType" tabindex="-1" readonly="true"  />
     <html:button property="chargeBPTypeButton" styleId="chargeBPTypeButton" onclick="openLOVCommon(19,'chargeMasterForm','chargeBPType','','', '','','','hBuyerSupplierBPType');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>     
     <%-- <img onClick="openLOVCommon(19,'chargeMasterForm','chargeBPType','','', '','','','hBuyerSupplierBPType')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
    <html:hidden  property="hBuyerSupplierBPType" styleId="hBuyerSupplierBPType"  />
     </td>
     
      <td><bean:message key="lbl.chargeType" /><span><font color="red">*</font></span></td>
		    <td><html:select property="chargeType" styleId="chargeType" styleClass="text">
              	     <html:option  value="">--Select--</html:option>
              	      <html:option  value="P">Payable</html:option>
		      	      <html:option value="R">Receivable</html:option>
	 </html:select></td></tr>
	 
	 <tr>
	 
	 
	 <td><bean:message key="lbl.chargeMethod" /><span><font color="red">*</font></span></td>
		    <td><html:select property="chargeMethod" styleId="chargeMethod" styleClass="text" onchange="fnDisabled();fnApplicable();return fnMinChargeMehtod('minChargeAmount');return fnApplicable1('chargeAmount');"  >
              	      <html:option  value="F">Flat</html:option>
		      	      <html:option value="P">Percent</html:option>
	 </html:select></td>
	 
	 
	 <td><bean:message key="lbl.chargeAmount" /><span><font color="red">*</font></span></td>
    <td><html:text property="chargeAmount" styleClass="text" style="text-align: right" styleId="chargeAmount" onkeypress="return numbersonly(event,id,16);" onblur="formatNumber(this.value,id);fnApplicable1('chargeAmount');" onkeyup="checkNumber(this.value, event, 16,id);" onfocus="keyUpNumber(this.value, event, 16,id);"  /></td>
	 </tr>
	 </logic:notPresent>
	 <tr>

	<logic:present name="list">
		
	<logic:iterate name="list" id="sublist">
	 
		<td><bean:message key="lbl.calculatedOn" /></td>
		     <td>
		    <logic:equal name="sublist" property="chargeMethod" value="P"> 
		     <div id="lov" >
			     <html:text property="calculatedOn" styleClass="text" styleId="calculatedOn" tabindex="-1" readonly="true" value="${requestScope.list[0].calculatedOn}" />
			     <html:hidden  property="lbxCalculatedOn" styleId="lbxCalculatedOn"  value="${requestScope.list[0].lbxCalculatedOn}"/>
			     <html:button property="calculatedButton" styleId="calculatedButton" onclick="openLOVCommon(60,'chargeMasterForm','lbxCalculatedOn','','', '','','','mincalculatedOn');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
			     <%-- <img onClick="openLOVCommon(60,'chargeMasterForm','calculatedOn','','', '','','','lbxCalculatedOn')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
			 </div>
			 <div id="disabledLov" style="display: none;">
				 	<html:text property="calculatedOn" styleClass="text" styleId="calculatedOn" maxlength="10" disabled="true" />
				 </div>
			 </logic:equal>
			<logic:equal name="sublist" property="chargeMethod" value="F">
			
				<div id="lov" style="display: none;">
				     <html:text property="calculatedOn" styleClass="text" styleId="calculatedOn" tabindex="-1" readonly="true" value="${requestScope.list[0].calculatedOn}" />
				     <html:hidden  property="lbxCalculatedOn" styleId="lbxCalculatedOn"  value="${requestScope.list[0].lbxCalculatedOn}"/>
				     <html:button property="calculatedButton" styleId="calculatedButton" onclick="openLOVCommon(60,'chargeMasterForm','lbxCalculatedOn','','', '','','','mincalculatedOn');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
				    <%--  <img onClick="openLOVCommon(60,'chargeMasterForm','calculatedOn','','', '','','','lbxCalculatedOn')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
			    </div>
				 <div id="disabledLov">
				 	<html:text property="calculatedOn" styleClass="text" styleId="calculatedOn" maxlength="10" disabled="true" />
				 </div>
			 </logic:equal>
		    </td>
	    
	  </logic:iterate>
  </logic:present>
  
  
  <logic:notPresent name="list">
  
			 <td><bean:message key="lbl.calculatedOn" /></td>
		     <td>
		     
		     <div id="lov" style="display: none;">
			     <html:text property="calculatedOn" styleClass="text" styleId="calculatedOn" maxlength="10" readonly="true" value="${requestScope.list[0].calculatedOn}" />
			     <html:hidden property="lbxCalculatedOn" styleId="lbxCalculatedOn"  value="${requestScope.list[0].lbxCalculatedOn}"/>
			     <html:button property="calculatedButton" styleId="calculatedButton" onclick="openLOVCommon(60,'chargeMasterForm','lbxCalculatedOn','','', '','','','mincalculatedOn');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
			     <!-- <img onClick="openLOVCommon(60,'chargeMasterForm','calculatedOn','','', '','','','lbxCalculatedOn')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
			 </div>
			 
			 <div id="disabledLov">
			 	<html:text property="calculatedOn" styleClass="text" styleId="calculatedOn" maxlength="10" disabled="true" />
			 </div>
		    </td>
		    
 </logic:notPresent>
  
  
     </tr>
     <logic:present name="editVal">
     <tr>
      <td><bean:message key="lbl.tdsApplicable" /></td>
      <logic:equal value="Y" name="tdsStatus">
         <td><input type="checkbox" name="tdsStatus" id="tdsStatus" onclick="fndisable();" checked="checked" /></td>
       </logic:equal>
     
      <logic:notEqual value="Y" name="tdsStatus">
         <td><input type="checkbox" name="tdsStatus" id="tdsStatus"  onclick="fndisable();"/></td>
      </logic:notEqual>
      
      <logic:present name="list">
        <td><bean:message key="lbl.tdsRate" /></td>
      <logic:iterate name="list" id="subList">
      <logic:equal name="subList" property="tdsStatus" value="A">
      <td><html:text property="tdsRate" styleClass="text" style="text-align: right" styleId="tdsRate" disabled="false" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('tdsRate');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" value="${requestScope.list[0].tdsRate}" /></td>
  </logic:equal>
  <logic:notEqual name="subList" property="tdsStatus" value="A">
        <td><html:text property="tdsRate" styleClass="text" style="text-align: right" styleId="tdsRate" disabled="true" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('tdsRate');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" value="${requestScope.list[0].tdsRate}" /></td>
  </logic:notEqual>
    </logic:iterate>
    </logic:present>
     </tr>
     </logic:present>
     <logic:notPresent name="editVal">
     <tr>
      <td><bean:message key="lbl.tdsApplicable" /></td>
      <logic:equal value="Y" name="tdsStatus">
         <td><input type="checkbox" name="tdsStatus" id="tdsStatus" onclick="fndisable();" checked="checked" /></td>
       </logic:equal>
     
      <logic:notEqual value="Y" name="tdsStatus">
         <td><input type="checkbox" name="tdsStatus" id="tdsStatus"  onclick="fndisable();"/></td>
      </logic:notEqual>
      
      <td><bean:message key="lbl.tdsRate" /></td>
      <td><html:text property="tdsRate" styleClass="text" styleId="tdsRate" disabled="true" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('tdsRate');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"   /></td>
     </tr>
     </logic:notPresent>
     <logic:present name="editVal">
     <tr>
      <td><bean:message key="lbl.taxApplicable" /></td>
       <logic:equal value="Y" name="taxStatus">
         <td><input type="checkbox" name="taxStatus" id="taxStatus" onclick="fndisable();" checked="checked" /></td>
       </logic:equal>
      
      <logic:notEqual value="Y" name="taxStatus">
        <td> <input type="checkbox" name="taxStatus" id="taxStatus" onclick="fndisable();" /></td>
      </logic:notEqual>
      
      <td><bean:message key="lbl.taxInclusive" /></td>
       <logic:equal value="Y" name="taxInclusiveStatus">
        <td><input type="checkbox" name="taxInclusiveStatus" id="taxInclusiveStatus"  checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="Y" name="taxInclusiveStatus">
         <td><input type="checkbox" name="taxInclusiveStatus" id="taxInclusiveStatus"  /></td>
      </logic:notEqual></tr>
      
      
      <tr>
      <logic:present name="list">
      <logic:iterate name="list" id="subList">
      <logic:equal name="subList" property="taxStatus" value="A">  
      <td><bean:message key="lbl.taxRate1" /></td>
      <td><html:text property="taxRate1" styleClass="text" style="text-align: right" styleId="taxRate1" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('taxRate1');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" disabled="false" value="${requestScope.list[0].taxRate1}" /></td>
     
      <td><bean:message key="lbl.taxRate2" /></td>
      <td><html:text property="taxRate2" styleClass="text" style="text-align: right" styleId="taxRate2" disabled="false" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('taxRate2');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" value="${requestScope.list[0].taxRate2}" /></td>
   </logic:equal>
   
   <logic:notEqual name="subList" property="taxStatus" value="A">  
      <td><bean:message key="lbl.taxRate1" /></td>
      <td><html:text property="taxRate1" styleClass="text" style="text-align: right" styleId="taxRate1" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('taxRate1');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" disabled="true" value="${requestScope.list[0].taxRate1}" /></td>
     
      <td><bean:message key="lbl.taxRate2" /></td>
      <td><html:text property="taxRate2" styleClass="text" style="text-align: right" styleId="taxRate2" disabled="true" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('taxRate2');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" value="${requestScope.list[0].taxRate2}" /></td>
   </logic:notEqual>
   
   
    </logic:iterate> 
    </logic:present>
     
      
      
      
</tr>
     </logic:present>
     
     <logic:notPresent name="editVal">
     <tr>
      <td><bean:message key="lbl.taxApplicable" /></td>
       <logic:equal value="Y" name="taxStatus">
         <td><input type="checkbox" name="taxStatus" id="taxStatus" onclick="fndisable();" checked="checked" /></td>
       </logic:equal>
      
      <logic:notEqual value="Y" name="taxStatus">
        <td> <input type="checkbox" name="taxStatus" id="taxStatus" onclick="fndisable();" /></td>
      </logic:notEqual>
      
      <td><bean:message key="lbl.taxInclusive" /></td>
       <logic:equal value="Y" name="taxInclusiveStatus">
        <td><input type="checkbox" name="taxInclusiveStatus" id="taxInclusiveStatus" onclick="fndisable();" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="Y" name="taxInclusiveStatus">
         <td><input type="checkbox" name="taxInclusiveStatus" id="taxInclusiveStatus" onclick="fndisable();" /></td>
      </logic:notEqual></tr>
      
      
      <tr>
      <td><bean:message key="lbl.taxRate1" /></td>
      <td><html:text property="taxRate1" styleClass="text" style="text-align: right" styleId="taxRate1" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('taxRate1');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" disabled="true"  /></td>
     
      <td><bean:message key="lbl.taxRate2" /></td>
      <td><html:text property="taxRate2" styleClass="text" style="text-align: right" styleId="taxRate2" disabled="true" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('taxRate2');" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"  /></td>
     </tr>
     </logic:notPresent>
     
     <tr>

     <logic:present name="editVal">
     <logic:iterate name="list" id="sublist">
      <td><bean:message key="lbl.minchargeMethod" /><span><font color="red">*</font></span></td>
      <td><logic:equal name="sublist" property="chargeMethod" value="F"> 
   		 <div id="flat">
		   		<html:select property="minchargeMethod" styleId="minchargeMethod" styleClass="text" value="${requestScope.list[0].minchargeMethod}" >
              	      	<html:option  value="F" >Flat</html:option>
	 			</html:select></div>
	 		</logic:equal>
	 	<logic:equal name="sublist" property="chargeMethod" value="P"> 
	 	<div id="per">
	 			<html:select property="minchargeMethod" styleId="minchargeMethod" styleClass="text" value="${requestScope.list[0].minchargeMethod}" >
		      	      	<html:option value="P">Percent</html:option>
		      	</html:select></div></logic:equal>
		      	
		    <div id="flat" style="display: none;">
		   		<html:select property="minchargeMethod" styleId="minchargeMethod" styleClass="text" value="${requestScope.list[0].minchargeMethod}" >
              	      	<html:option  value="F" >Flat</html:option>
	 			</html:select></div>
	 	<div id="per" style="display: none;">
	 			<html:select property="minchargeMethod" styleId="minchargeMethod" styleClass="text" value="${requestScope.list[0].minchargeMethod}" >
		      	      	<html:option value="P">Percent</html:option>
		      	</html:select></div></td>
		      	
      <td><bean:message key="lbl.minChargeAmount" /></td>
      <td><html:text property="minChargeAmount" styleClass="text" style="text-align: right" styleId="minChargeAmount" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);fnMinChargeMehtod('minChargeAmount');" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"   value="${requestScope.list[0].minChargeAmount}" /></td>
    </logic:iterate>
     </logic:present >
     
     <logic:notPresent name="editVal">
      <td><bean:message key="lbl.minchargeMethod" /><span><font color="red">*</font></span></td>
		<td>  <div id="flat">
		   		<html:select property="minchargeMethod" styleId="minchargeMethod" styleClass="text" value="${requestScope.list[0].minchargeMethod}" >
              	      	<html:option  value="F" >Flat</html:option>
	 			</html:select></div>
	 	<div id="per" style="display: none;">
	 			<html:select property="minchargeMethod" styleId="minchargeMethod" styleClass="text"  value="${requestScope.list[0].minchargeMethod}" >
		      	      	<html:option value="P">Percent</html:option>
		      	</html:select></div></td>
		      	
      <td><bean:message key="lbl.minChargeAmount" /></td>
      <td><html:text property="minChargeAmount" styleClass="text" style="text-align: right" styleId="minChargeAmount" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);fnMinChargeMehtod('minChargeAmount');" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"  /></td>
     </logic:notPresent >
     
     </tr>
 	 <tr>

	<logic:present name="list">
		<td><bean:message key="lbl.calculatedOn" /></td>
		  <td>
			    <html:text property="mincalculatedOn" styleClass="text" styleId="mincalculatedOn" maxlength="10" readonly="true" value="${requestScope.list[0].calculatedOn}" />
			  </td>
  </logic:present>
  
  
  <logic:notPresent name="list">
  
			 <td><bean:message key="lbl.calculatedOn" /></td>
		     <td>
			     <html:text property="mincalculatedOn" styleClass="text" styleId="mincalculatedOn" maxlength="10" readonly="true" value="${requestScope.list[0].calculatedOn}" />
			  </td>
		    
 </logic:notPresent>
  

      <td><bean:message key="lbl.active" /></td>
       <logic:notPresent name="editVal">
       <td><input type="checkbox" name="chargeStatus" id="chargeStatus" checked="checked" /></td>
       </logic:notPresent>
       
      <logic:present name="editVal">
      <logic:equal value="Active" name="chargeStatus">
         <td><input type="checkbox" name="chargeStatus" id="chargeStatus" checked="checked" /></td>
       </logic:equal>
    
      <logic:notEqual value="Active" name="chargeStatus">
       <td><input type="checkbox" name="chargeStatus" id="chargeStatus"  /></td> 
      </logic:notEqual>
      </logic:present>
    </tr><tr><td>
    
    <br>
    <logic:present name="editVal">
       <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditCharge('${requestScope.list[0].charge}');" class="topformbutton2" > <bean:message key="button.save" /></button>
   </logic:present>
   
  
   
   <logic:notPresent name="editVal">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveCharge();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>
	<logic:present name="sms">
<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("chargeMasterForm").action="chargeMasterBehind.do";
	    document.getElementById("chargeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("chargeMasterForm").action="chargeMasterBehind.do";
	    document.getElementById("chargeMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{	
		alert("<bean:message key="lbl.dataExist" />");
	}
	
</script>
</logic:present>	
  </body>
		</html:html>