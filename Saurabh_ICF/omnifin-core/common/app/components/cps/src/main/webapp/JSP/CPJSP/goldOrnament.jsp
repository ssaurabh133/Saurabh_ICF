<%--
      Author Name-      Parvez
      Date of creation -31/12/2014
      Purpose-          Entry of Collateral Detail
      Documentation-     
      
 --%>


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

	<head>
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/assetCollateral.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>	
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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('GoldOrnamentForm').bgType.focus();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>

			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<%
	String assetClass=request.getParameter("assetClass");
	String assetCollateralType=request.getParameter("assetCollateralType");
	if(assetClass!=null)
	{
		request.setAttribute("assetClass",assetClass);
	}
	
	if(assetCollateralType!=null)
	{
	   if(assetCollateralType.equalsIgnoreCase("ASSET")){
	   request.setAttribute("assetCollateralTypeAsset",assetCollateralType);
	    request.setAttribute("assetCollateralType",assetCollateralType);
	    request.setAttribute("new","N");
	   }else{
	   request.setAttribute("assetCollateralTypeColl",assetCollateralType);
	     request.setAttribute("assetCollateralType",assetCollateralType);
	    request.setAttribute("new","N");
	   }
	   
		
	}
	
 %>
<div id="bgId">
<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">
<html:form action="/collateralGoldOrnamentProcessAction" styleId="GoldOrnamentForm" method="post">

<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  


<html:hidden property="assetsIdGoldOrnament" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdGoldOrnament"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="bg1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="bg2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="bg3"/>
<html:hidden property="goldSchemeId" value="${sessionScope.goldSchemeId}" styleClass="text" styleId ="goldSchemeId"/>
<html:hidden property="goldloanAmount" value="${sessionScope.goldloanAmount}" styleClass="text" styleId ="goldloanAmount"/>

<FIELDSET>
<LEGEND><bean:message key="lbl.goldOrnamentDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>    
     <TR>
     
     
     <td><bean:message key="lbl.ornamentType" /></td>
          <td >
           
           <html:text  property="ornamentType" styleId="ornamentType" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].ornamentType}" tabindex="-1"/>
          <html:hidden  property="lbxOrnamentType" styleId="lbxOrnamentType" value="${fetchCollateralDetails[0].lbxOrnamentType}"/>
          <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(6074,'GoldOrnamentForm','ornamentType','','ornamentStandard','','','','ornamentId','lbxOrnamentType')" value=" " styleClass="lovbutton"> </html:button>
            <input type="hidden" id="ornamentId" name="ornamentId"/>
	   </td>
          <td><bean:message key="lbl.ornamentStandard" /></td>
          <td>
          <html:text property="ornamentStandard" styleId="ornamentStandard" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].ornamentStandard}" tabindex="-1"/>
          		<html:hidden  property="lbxOrnamentStandard" styleId="lbxOrnamentStandard" value="${fetchCollateralDetails[0].lbxOrnamentStandard}" />
                <html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('ornamentType');openLOVCommon(6075,'GoldOrnamentForm','ornamentStandard','ornamentType','lbxOrnamentStandard', 'lbxOrnamentType','Select Gold Ornament LOV','','lbxOrnamentStandard','goldOrnamentLTV');make_blank()" value=" " styleClass="lovbutton"></html:button>  								    
				</td> 
         <TR>
		  <td><bean:message key="lbl.quantity"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="quantity" property="quantity"  value="${fetchCollateralDetails[0].quantity}" onkeypress="return numbersonly2(event, id, 18)" />
		     </td>
          <td><bean:message key="lbl.rateGoldOrnament"/></font></td>
         <!-- <td nowrap="nowrap"><html:text styleClass="text" style="text-align: right" styleId="rateGoldOrnament" property="rateGoldOrnament"  onchange="calgoldOrnamnet();"  value="${fetchCollateralDetails[0].rateGoldOrnament}" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td> -->
		  
		  <td >
           
           <html:text  property="rateGoldOrnament" styleId="rateGoldOrnament" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].rateGoldOrnament}" tabindex="-1"/>
          <html:hidden  property="lbxrateGoldOrnament" styleId="rateGoldOrnament" value="${fetchCollateralDetails[0].lbxrateGoldOrnament}"/>
          <html:button property="productButton" styleId="productButton" onclick="defaultValueLtv();closeLovCallonLov1();openLOVCommon(6077,'GoldOrnamentForm','rateGoldOrnament','','','','','calgoldOrnamnet','ornamentId','lbxrateGoldOrnament')" value=" " styleClass="lovbutton"> </html:button>
            <input type="hidden" id="ornamentId" name="ornamentId"/>
	   </td>
		  
         </TR> 
         <TR>
		 <td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" readonly="true" styleId="goldOrnamentLTV" property="goldOrnamentLTV" onchange="calgoldOrnamnet();"  maxlength="3" style="text-align: right"  onkeyup="return checkRate('goldOrnamentLTV');" value="${fetchCollateralDetails[0].goldOrnamentLTV}" onkeypress="return isNumberKey(event);" /></td>
		 
		     <td><bean:message key="lbl.grossWeight"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="grossWeight" property="grossWeight"  value="${fetchCollateralDetails[0].grossWeight}" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);calgoldOrnamnet();" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>	
			  </TR>  
           <TR>
		     <td><bean:message key="lbl.deduction"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="deduction" property="deduction"  value="${fetchCollateralDetails[0].deduction}"   onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);calgoldOrnamnet();" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>	
          
		     <td><bean:message key="lbl.netWeight"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="netWeight" property="netWeight"   value="${fetchCollateralDetails[0].netWeight}"  readonly="true"  onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>
			 </TR>  
		   
		   <TR>
            <td><bean:message key="lbl.loanAmountEligible"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="loanAmountEligible" property="loanAmountEligible"  value="${fetchCollateralDetails[0].loanAmountEligible}"  readonly="true" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>			     
           
		    <td><bean:message key="lbl.netOrnamentValue"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="netOrnamentValue" property="netOrnamentValue"  value="${fetchCollateralDetails[0].netOrnamentValue}" readonly="true" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>
          
		    </TR>  
		   <tr>
		  <td><bean:message key="lbl.primaryEvaluator" /></td>
          <td >
           <html:text  property="primaryEvaluator" styleId="primaryEvaluator" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].primaryEvaluator}" tabindex="-1"/>
          <html:hidden  property="lbxprimaryEvaluator" styleId="primaryEvaluator" value="${fetchCollateralDetails[0].lbxprimaryEvaluator}"/>
          <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(6076,'GoldOrnamentForm','primaryEvaluator','','','','','','ornamentId','lbxprimaryEvaluator')" value=" " styleClass="lovbutton"> </html:button>
            <input type="hidden" id="ornamentId" name="ornamentId"/>
	   </td>
		  </tr>	 
        <tr>
 		  <td> 
 		  <button type="button" name="button1" class="topformbutton2" title="ALt+V" accesskey="V" onclick="return savegoldOrnamentDetails();"><bean:message key="button.save" /></button>	
 		</td>	
		  </tr>	
		 </TABLE>	
	 
	
	</TD>
	</TR>
	</TABLE>

</FIELDSET> 
</html:form>
</logic:present>

<%--          			view Deal 					
											 --%>
<logic:present name="viewDeal">
<html:form action="/collateralGoldOrnamentProcessAction" styleId="GoldOrnamentForm" method="post">

<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  


<html:hidden property="assetsIdGoldOrnament" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdGoldOrnament"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="bg1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="bg2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="bg3"/>


<FIELDSET>
<LEGEND><bean:message key="lbl.goldOrnamentDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>    
     <TR>
     
     
        <td><bean:message key="lbl.ornamentType" /></td>
          <td >
           
           <html:text  property="ornamentType" styleId="ornamentType" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].ornamentType}" tabindex="-1"/>
          <html:hidden  property="lbxOrnamentType" styleId="lbxOrnamentType" value="${fetchCollateralDetails[0].lbxOrnamentType}"/>
          
            <input type="hidden" id="ornamentId" name="ornamentId"/>
	   </td>
          <td><bean:message key="lbl.ornamentStandard" /></td>
          <td>
          <html:text property="ornamentStandard" styleId="ornamentStandard" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].ornamentStandard}" tabindex="-1"/>
          		<html:hidden  property="lbxOrnamentStandard" styleId="lbxOrnamentStandard" value="${fetchCollateralDetails[0].lbxOrnamentStandard}" />
               

                </td> 
     
     

			
			<TR>
			<td><bean:message key="lbl.quantity"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="quantity" property="quantity"  value="${fetchCollateralDetails[0].quantity}" onkeypress="return numbersonly2(event, id, 18)" />
		     </td>
			
          <td><bean:message key="lbl.rateGoldOrnament"/></font></td>
          <td nowrap="nowrap"><html:text styleClass="text" style="text-align: right" styleId="rateGoldOrnament" property="rateGoldOrnament" disabled="true" onchange="calgoldOrnamnet();"  value="${fetchCollateralDetails[0].rateGoldOrnament}" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		   </TR> 
         <TR>
         <td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="goldOrnamentLTV" property="goldOrnamentLTV" disabled="true" onchange="calgoldOrnamnet();"  maxlength="3" style="text-align: right"    onkeyup="return checkRate('goldOrnamentLTV');"  value="${fetchCollateralDetails[0].goldOrnamentLTV}" onkeypress="return isNumberKey(event);" /></td>
            
     
		     <td><bean:message key="lbl.grossWeight"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="grossWeight" property="grossWeight" disabled="true" onchange="calgoldOrnamnet();" value="${fetchCollateralDetails[0].grossWeight}" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>	
			 </TR>  
           <TR>
		     <td><bean:message key="lbl.deduction"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="deduction" property="deduction" disabled="true" onchange="calgoldOrnamnet();" value="${fetchCollateralDetails[0].deduction}"   onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>	
           
		     <td><bean:message key="lbl.netWeight"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="netWeight" property="netWeight" disabled="true"  value="${fetchCollateralDetails[0].netWeight}"  readonly="true"  onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>
			  </TR>  
		   
		   <TR>
            <td><bean:message key="lbl.loanAmountEligible"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="loanAmountEligible" property="loanAmountEligible" disabled="true" value="${fetchCollateralDetails[0].loanAmountEligible}"  readonly="true" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>			     
          
		    <td><bean:message key="lbl.netOrnamentValue"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="netOrnamentValue" property="netOrnamentValue"  value="${fetchCollateralDetails[0].netOrnamentValue}" readonly="true" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>
         </TR>  
		  
		  <TR>
		<td><bean:message key="lbl.primaryEvaluator" /></td>
          <td >
           
           <html:text  property="primaryEvaluator" styleId="primaryEvaluator" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].primaryEvaluator}" tabindex="-1"/>
          <html:hidden  property="lbxprimaryEvaluator" styleId="primaryEvaluator" value="${fetchCollateralDetails[0].lbxprimaryEvaluator}"/>
        
            <input type="hidden" id="ornamentId" name="ornamentId"/>
	   </td>
		   
           </TR>  
		   </TABLE>	
	 </TD>
	</TR>
	</TABLE>

</FIELDSET> 
</html:form>
</logic:present>
	<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewGolOrnamentInCM">
	<html:form action="/collateralGoldOrnamentProcessAction" styleId="GoldOrnamentForm" method="post">

<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  


<html:hidden property="assetsIdGoldOrnament" value="${viewGolOrnamentInCM[0].assetsId}" styleClass="text" styleId ="assetsIdGoldOrnament"/>
<html:hidden property="colltype1" value="${viewGolOrnamentInCM[0].colltype1}${assetClass }" styleClass="text" styleId ="bg1"/>
<html:hidden property="colltype2" value="${viewGolOrnamentInCM[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="bg2"/>
<html:hidden property="colltype3" value="${viewGolOrnamentInCM[0].colltype3}${new }" styleClass="text" styleId ="bg3"/>



<FIELDSET>
<LEGEND><bean:message key="lbl.goldOrnamentDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>    
     <TR>
     
     
        <td><bean:message key="lbl.ornamentType" /></td>
          <td >
           
           <html:text  property="ornamentType" styleId="ornamentType" styleClass="text" disabled="true" value="${viewGolOrnamentInCM[0].ornamentType}" tabindex="-1"/>
          <html:hidden  property="lbxOrnamentType" styleId="lbxOrnamentType" value="${viewGolOrnamentInCM[0].lbxOrnamentType}"/>
          
            <input type="hidden" id="ornamentId" name="ornamentId"/>
	   </td>
          <td><bean:message key="lbl.ornamentStandard" /></td>
          <td>
          <html:text property="ornamentStandard" styleId="ornamentStandard" styleClass="text" disabled="true" value="${viewGolOrnamentInCM[0].ornamentStandard}" tabindex="-1"/>
          		<html:hidden  property="lbxOrnamentStandard" styleId="lbxOrnamentStandard" value="${viewGolOrnamentInCM[0].lbxOrnamentStandard}" />
               

                </td> 
     
     

			
			<TR>
			<td><bean:message key="lbl.quantity"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="quantity" property="quantity"  value="${viewGolOrnamentInCM[0].quantity}" onkeypress="return numbersonly2(event, id, 18)" />
		     </td>
			
          <td><bean:message key="lbl.rateGoldOrnament"/></font></td>
          <td nowrap="nowrap"><html:text styleClass="text" style="text-align: right" styleId="rateGoldOrnament" property="rateGoldOrnament" disabled="true" onchange="calgoldOrnamnet();"  value="${viewGolOrnamentInCM[0].rateGoldOrnament}" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		   </TR> 
         <TR>
         <td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="goldOrnamentLTV" property="goldOrnamentLTV" disabled="true" onchange="calgoldOrnamnet();"  maxlength="3" style="text-align: right"    onkeyup="return checkRate('goldOrnamentLTV');" value="${viewGolOrnamentInCM[0].goldOrnamentLTV}" onkeypress="return isNumberKey(event);" /></td>
            
     
		     <td><bean:message key="lbl.grossWeight"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="grossWeight" property="grossWeight" disabled="true" onchange="calgoldOrnamnet();" value="${viewGolOrnamentInCM[0].grossWeight}" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>	
			 </TR>  
           <TR>
		     <td><bean:message key="lbl.deduction"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="deduction" property="deduction" disabled="true" onchange="calgoldOrnamnet();" value="${viewGolOrnamentInCM[0].deduction}"   onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>	
           
		     <td><bean:message key="lbl.netWeight"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="netWeight" property="netWeight" disabled="true"  value="${viewGolOrnamentInCM[0].netWeight}"  readonly="true"  onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>
			  </TR>  
		   
		   <TR>
            <td><bean:message key="lbl.loanAmountEligible"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="loanAmountEligible" property="loanAmountEligible" disabled="true" value="${viewGolOrnamentInCM[0].loanAmountEligible}"  readonly="true" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>			     
          
		    <td><bean:message key="lbl.netOrnamentValue"/></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="netOrnamentValue" property="netOrnamentValue"  value="${viewGolOrnamentInCM[0].netOrnamentValue}" readonly="true" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" />
		     </td>
         </TR>  
		  
		  <TR>
		<td><bean:message key="lbl.primaryEvaluator" /></td>
          <td >
           
           <html:text  property="primaryEvaluator" styleId="primaryEvaluator" styleClass="text" disabled="true" value="${viewGolOrnamentInCM[0].primaryEvaluator}" tabindex="-1"/>
          <html:hidden  property="lbxprimaryEvaluator" styleId="primaryEvaluator" value="${viewGolOrnamentInCM[0].lbxprimaryEvaluator}"/>
        
            <input type="hidden" id="ornamentId" name="ornamentId"/>
	   </td>
		   
           </TR>  
		   </TABLE>	
	 </TD>
	</TR>
	</TABLE>

</FIELDSET>

</html:form>
</logic:present>

<%--          			Insertion in LOAN iNITIATION													 --%>
<logic:present name="loanInit">
<html:form action="/bankGProcessAction" styleId="BGForm" method="post">
<html:javascript formName="CollateralBGDynaValidatorForm" />
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  


<html:hidden property="assetsIdBG" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdBG"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="bg1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="bg2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="bg3"/>


<FIELDSET>
<LEGEND><bean:message key="lbl.propertyDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>    
     <TR>
          <td style="width:23%"><bean:message key="lbl.bgType"/></td>
          <td nowrap="nowrap"><html:select property="bgType" styleId="bgType" styleClass="text" value="${fetchCollateralDetails[0].bgType}">
             <option value="F">Financial</option>
             <option value="P">Performance</option>
              
           </html:select></td>
          <td><bean:message key="lbl.bgAmount"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" style="text-align: right" styleId="netOrnamentValue" property="netOrnamentValue" value="${fetchCollateralDetails[0].netOrnamentValue}" onkeypress="return numbersonly2(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber2(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" /></td>
         
          </TR>    
      
          <TR>
		     <td><bean:message key="lbl.bgInsurerDate"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="bgInDate" property="bgInDate" onchange="checkDate('bgInDate');checkInsurerDate('bgInDate');"  value="${fetchCollateralDetails[0].bgInDate}" /></td>
		     <td><bean:message key="lbl.bgValidity"/><font color="red">*</font></td>
            <td nowrap="nowrap"><html:text styleClass="text" styleId="bgValidity" property="bgValidity"  onchange="checkDate('bgValidity');checkValidityDate('bgValidity');" value="${fetchCollateralDetails[0].bgValidity}" /></td> 
           </TR>  
           <TR>
		     <td><bean:message key="lbl.bgIssuing"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" style="text-align: right" styleId="bgIssuing" property="bgIssuing" onkeypress="return isNumberKey(event);"  value="${fetchCollateralDetails[0].bgIssuing}" /></td>
		     
           </TR>  
		 		  <tr>
 		  <td> 
 		  <button type="button" name="button1" class="topformbutton2" title="ALt+V" accesskey="V" onclick="return saveBGDetails();"><bean:message key="button.save" /></button> 	
 		
 		    </td>	
		  </tr>	
		 
		
		 

	</TABLE>	
	 
	
	</TD>
	</TR>
	</TABLE>

</FIELDSET> 
</html:form>

</logic:present>

</div>
