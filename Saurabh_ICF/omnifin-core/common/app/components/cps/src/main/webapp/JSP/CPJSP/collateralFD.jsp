<%--
      Author Name-      Prashant Kumar
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

	<head>
 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>

	
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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('FDForm').assetsCollateralDesc.focus();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<%
	String assetClass=request.getParameter("assetClass");
	String assetCollateralType=request.getParameter("assetCollateralType");
	if(assetClass!=null)
	{
		request.setAttribute("assetClass",assetClass);
	}
	
	if(assetCollateralType!=null)
	{
		request.setAttribute("assetCollateralType",assetCollateralType);
	    request.setAttribute("new","N");
	}
	
 %>

<div id=centercolumn>
<div id=revisedcontainer>	
<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">

<html:form action="/collateralFDProcessAction" styleId="FDForm" method="post">
<html:javascript formName="CollateralFixedDepositDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdfd" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdfd"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="fd1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="fd2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="fd3"/>
<fieldset>
<legend><bean:message key="lbl.fixedDepositDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>   
 
 		<tr>
 		  <td style="width:23%"><bean:message key="lbl.fixedDepositDesc"/><font color="red">*</font></TD>
 		  <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 		  <td><bean:message key="lbl.fixedDeposit"/><font color="red">*</font></td>
 		  <td noWrap><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 		  </tr>
 		<tr>
 		  <td style="width:23%"><bean:message key="lbl.fdSecurityMargin"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="collateralSecurityMargin" style="text-align: right" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeypress="return numbersonly(event, id, 3)" onblur="formatNumber(this.value, id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);"/></td>
 		  <td style="width:23%"><bean:message key="lbl.fdDepositAmount"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="fdAmount" property="fdAmount" value="${fetchCollateralDetails[0].fdAmount}" style="text-align: right" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 		  </tr>
 		<tr>
 		  <td><bean:message key="lbl.tenure"/><font color="black">(In months)</font><font color="red">*</font></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="fdTenure" property="fdTenure" maxlength="3" value="${fetchCollateralDetails[0].fdTenure}" style="text-align: right" onkeypress="return isNumberKey(event);"/></td>
 		  <td><bean:message key="lbl.rate"/><font color="red">*</font></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="fdRate" property="fdRate" maxlength="3" value="${fetchCollateralDetails[0].fdRate}" style="text-align: right" onkeyup="return checkRate('fdRate');"/></td>
		 </tr>
		 <tr>
		   <td><bean:message key="lbl.bookDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdBookDate" property="fdBookDate" value="${fetchCollateralDetails[0].fdBookDate}" onchange="return checkDate('fdBookDate');"/></td>
		   <td><bean:message key="lbl.maturityDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdMatureDate" property="fdMatureDate" value="${fetchCollateralDetails[0].fdMatureDate}" onchange="checkDate('fdMatureDate'); validMaturity();"/></td>
		 </tr>
		 <tr>
		   <td><bean:message key="lbl.agencyName"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdAgencyName" property="fdAgencyName" maxlength="50" value="${fetchCollateralDetails[0].fdAgencyName}" /></td>
		   <td><bean:message key="lbl.agencyRating"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdAgencyRating" property="fdAgencyRating" maxlength="10" value="${fetchCollateralDetails[0].fdAgencyRating}" /></td>
		 </tr>

		   <tr>	     
		   <td>
		   <bean:message key="lbl.bank"/><font color="red">*</font>
	     </td>
	
	     <td>	 
		 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].bank}"/>
	     <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${fetchCollateralDetails[0].lbxBankID}"/>
	      <div id="disId" style="display:inline; ">
	     <html:button property="loanBankButton" styleId="loanBankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'CollateralFixedDepositDynaValidatorForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>
    
    	</div></td>
		<td><bean:message key="lbl.branch"/><font color="red">*</font></td>
		<td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].branch}"/>
  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${fetchCollateralDetails[0].lbxBranchID}" />
		 <input type= "hidden" id="mirc"/>
	     <input type= "hidden" id="ifsc"/>

	     
  		  <div id="disIdBranch" style="display:inline; ">
  		 <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'CollateralFixedDepositDynaValidatorForm','branch','bank','','lbxBankID','Select Bank LOV','','ifsc','mirc','ifsc');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button>
   		 
  	   	</div></td>
		</tr>
		 
		  <tr>
		    <td><bean:message key="lbl.fdApplicants"/><font color="red">*</font></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="fdApplicants" property="fdApplicants" maxlength="50" value="${fetchCollateralDetails[0].fdApplicants}" />
		     </td>
		   </tr>


		 <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick=" saveFDDetails();"><bean:message key="button.save" /></button>  </td>
		  </tr>  
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>   
	</table>
	</td>
	</tr>
	</table>
	</fieldset>	
	
</html:form>
</logic:present>
<%--          			view Deal 																 --%>
<logic:present name="viewDeal">
<html:form action="/collateralFDProcessAction" styleId="FDForm" method="post">
<html:javascript formName="CollateralFixedDepositDynaValidatorForm"/>
<html:hidden property="assetsIdfd" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdfd"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="fd1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="fd2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="fd3"/>
<fieldset>
<legend><bean:message key="lbl.fixedDepositDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>   
 
 		<tr>
 		  <td style="width:23%"><bean:message key="lbl.fixedDepositDesc"/><font color="red">*</font></TD>
 		  <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 		  <td><bean:message key="lbl.fixedDeposit"/><font color="red">*</font></td>
 		  <td noWrap><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return isNumberKey(event);"/></td>
 		  </tr>
 		<tr>
 		  <td style="width:23%"><bean:message key="lbl.fdSecurityMargin"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" disabled="true" styleId="collateralSecurityMargin" style="text-align: right" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
 		  <td style="width:23%"><bean:message key="lbl.fdDepositAmount"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="fdAmount" disabled="true" property="fdAmount" style="text-align: right" value="${fetchCollateralDetails[0].fdAmount}" onkeypress="return isNumberKey(event);"/></td>
 		  </tr>
 		<tr>
 		  <td><bean:message key="lbl.tenure"/><font color="black">(In months)</font><font color="red">*</font></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="fdTenure" disabled="true" property="fdTenure" style="text-align: right"  value="${fetchCollateralDetails[0].fdTenure}" /></td>
 		  <td><bean:message key="lbl.rate"/><font color="red">*</font></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="fdRate" disabled="true" property="fdRate" style="text-align: right" value="${fetchCollateralDetails[0].fdRate}" onkeyup="return checkRate('fdRate');"/></td>
		 </tr>
		 <tr>
		   <td><bean:message key="lbl.bookDate"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text"  disabled="true" property="fdBookDate" value="${fetchCollateralDetails[0].fdBookDate}" /></td>
		   <td><bean:message key="lbl.maturityDate"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text"  disabled="true" property="fdMatureDate" value="${fetchCollateralDetails[0].fdMatureDate}" /></td>
		 </tr>
		 <tr>
		   <td><bean:message key="lbl.agencyName"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdAgencyName" disabled="true" property="fdAgencyName" value="${fetchCollateralDetails[0].fdAgencyName}" /></td>
		   <td><bean:message key="lbl.agencyRating"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdAgencyRating" disabled="true" property="fdAgencyRating" value="${fetchCollateralDetails[0].fdAgencyRating}" /></td>
		 </tr>
		  <tr>	     
		   <td>
		   <bean:message key="lbl.bank"/><font color="red">*</font>
	     </td>
	
	     <td>	 
		 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].bank}"/>
	     <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${fetchCollateralDetails[0].lbxBankID}"/>
	     </td>
		<td><bean:message key="lbl.branch"/><font color="red">*</font></td>
		<td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].branch}"/>
  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${fetchCollateralDetails[0].lbxBranchID}" />
		 </td>
		</tr>
		 
		 
		  <tr>
		    <td><bean:message key="lbl.fdApplicants"/><font color="red">*</font></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="fdApplicants" disabled="true" property="fdApplicants" value="${fetchCollateralDetails[0].fdApplicants}" />
		     </td>
		   </tr>
		  
<!--		 <tr>-->
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveFDDetails();"/>  </td>-->
<!--		  </tr>  -->
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>   
	</table>
	</td>
	</tr>
	</table>
	</fieldset>	
	
</html:form>
</logic:present>
</div>

<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewFDInCM">
	<html:form action="/collateralFDProcessAction" styleId="FDForm" method="post">

<fieldset>
<legend><bean:message key="lbl.fixedDepositDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>   
 
 		<tr>
 		  <td style="width:23%"><bean:message key="lbl.fixedDepositDesc"/><font color="red">*</font></TD>
 		  <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${viewFDInCM[0].assetsCollateralDesc}" /></td>
 		  <td><bean:message key="lbl.fixedDeposit"/><font color="red">*</font></td>
 		  <td noWrap><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" style="text-align: right" property="assetsCollateralValue" value="${viewFDInCM[0].assetsCollateralValue}" onkeypress="return isNumberKey(event);"/></td>
 		  </tr>
 		<tr>
 		  <td style="width:23%"><bean:message key="lbl.fdSecurityMargin"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" disabled="true" styleId="collateralSecurityMargin" style="text-align: right"  property="collateralSecurityMargin" value="${viewFDInCM[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
 		  <td style="width:23%"><bean:message key="lbl.fdDepositAmount"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="fdAmount" disabled="true" property="fdAmount" style="text-align: right"  value="${viewFDInCM[0].fdAmount}" onkeypress="return isNumberKey(event);"/></td>
 		  </tr>
 		<tr>
 		  <td><bean:message key="lbl.tenure"/><font color="black">(In months)</font><font color="red">*</font></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="fdTenure" disabled="true" property="fdTenure" style="text-align: right" value="${viewFDInCM[0].fdTenure}" /></td>
 		  <td><bean:message key="lbl.rate"/><font color="red">*</font></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="fdRate" disabled="true" property="fdRate" style="text-align: right" value="${viewFDInCM[0].fdRate}" onkeyup="return checkRate('fdRate');"/></td>
		 </tr>
		 <tr>
		   <td><bean:message key="lbl.bookDate"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text"  disabled="true" property="fdBookDate" value="${viewFDInCM[0].fdBookDate}" /></td>
		   <td><bean:message key="lbl.maturityDate"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text"  disabled="true" property="fdMatureDate" value="${viewFDInCM[0].fdMatureDate}" /></td>
		 </tr>
		 <tr>
		   <td><bean:message key="lbl.agencyName"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdAgencyName" disabled="true" property="fdAgencyName" value="${viewFDInCM[0].fdAgencyName}" /></td>
		   <td><bean:message key="lbl.agencyRating"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdAgencyRating" disabled="true" property="fdAgencyRating" value="${viewFDInCM[0].fdAgencyRating}" /></td>
		 </tr>
		  <tr>	     
		   <td>
		   <bean:message key="lbl.bank"/><font color="red">*</font>
	     </td>
	
	     <td>	 
		 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  tabindex="-1" readonly="true" value="${viewFDInCM[0].bank}"/>
	     <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${viewFDInCM[0].lbxBankID}"/>
	     </td>
		<td><bean:message key="lbl.branch"/><font color="red">*</font></td>
		<td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" readonly="true" value="${viewFDInCM[0].branch}"/>
  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${viewFDInCM[0].lbxBranchID}" />
		 </td>
		</tr>
		 
		  <tr>
		    <td><bean:message key="lbl.fdApplicants"/><font color="red">*</font></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="fdApplicants" disabled="true" property="fdApplicants" value="${viewFDInCM[0].fdApplicants}" />
		     </td>
		   </tr>
		  
<!--		 <tr>-->
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveFDDetails();"/>  </td>-->
<!--		  </tr>  -->
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>   
	</table>
	</td>
	</tr>
	</table>
	</fieldset>	
	
</html:form>
</logic:present>


<%--          			Insertion in LOAN iNITIATION													 --%>
<logic:present name="loanInit">
	

<html:form action="/collateralFDProcessAction" styleId="FDForm" method="post">
<html:javascript formName="CollateralFixedDepositDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdfd" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdfd"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="fd1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="fd2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="fd3"/>
<fieldset>
<legend><bean:message key="lbl.fixedDepositDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>   
 
 		<tr>
 		  <td style="width:23%"><bean:message key="lbl.fixedDepositDesc"/><font color="red">*</font></TD>
 		  <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 		  <td><bean:message key="lbl.fixedDeposit"/><font color="red">*</font></td>
 		  <td noWrap><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 		  </tr>
 		<tr>
 		  <td style="width:23%"><bean:message key="lbl.fdSecurityMargin"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}"  onkeypress="return numbersonly(event, id, 3)" onblur="formatNumber(this.value, id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);"/></td>
 		  <td style="width:23%"><bean:message key="lbl.fdDepositAmount"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="fdAmount" property="fdAmount" style="text-align: right" value="${fetchCollateralDetails[0].fdAmount}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 		  </tr>
 		<tr>
 		  <td><bean:message key="lbl.tenure"/><font color="black">(In months)</font><font color="red">*</font></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="fdTenure" property="fdTenure" maxlength="3" style="text-align: right"  value="${fetchCollateralDetails[0].fdTenure}" onkeypress="return isNumberKey(event);"/></td>
 		  <td><bean:message key="lbl.rate"/><font color="red">*</font></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="fdRate" property="fdRate" maxlength="3" style="text-align: right" value="${fetchCollateralDetails[0].fdRate}" onkeyup="return checkRate('fdRate');"/></td>
		 </tr>
		 <tr>
		   <td><bean:message key="lbl.bookDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdBookDate" property="fdBookDate" value="${fetchCollateralDetails[0].fdBookDate}" onchange="return checkDate('fdBookDate');"/></td>
		   <td><bean:message key="lbl.maturityDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdMatureDate" property="fdMatureDate" value="${fetchCollateralDetails[0].fdMatureDate}" onchange="checkDate('fdMatureDate'); validMaturity();"/></td>
		 </tr>
		 <tr>
		   <td><bean:message key="lbl.agencyName"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdAgencyName" property="fdAgencyName" maxlength="50" value="${fetchCollateralDetails[0].fdAgencyName}" /></td>
		   <td><bean:message key="lbl.agencyRating"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="fdAgencyRating" property="fdAgencyRating" maxlength="10" value="${fetchCollateralDetails[0].fdAgencyRating}" /></td>
		 </tr>
		  <tr>	     
		   <td>
		   <bean:message key="lbl.bank"/><font color="red">*</font>
	     </td>
	
	     <td>	 
		 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].bank}"/>
	     <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${fetchCollateralDetails[0].lbxBankID}"/>
	      <div id="disId" style="display:inline; ">
	     <html:button property="loanBankButton" styleId="loanBankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'CollateralFixedDepositDynaValidatorForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>
    
    	</div></td>
		<td><bean:message key="lbl.branch"/><font color="red">*</font></td>
		<td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].branch}"/>
  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${fetchCollateralDetails[0].lbxBranchID}" />
		 <input type= "hidden" id="mirc"/>
	     <input type= "hidden" id="ifsc"/>

	     
  		  <div id="disIdBranch" style="display:inline; ">
  		 <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'CollateralFixedDepositDynaValidatorForm','branch','bank','','lbxBankID','Select Bank LOV','','ifsc','mirc','ifsc');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button>
   		 
  	   	</div></td>
		</tr>
		  <tr>
		    <td><bean:message key="lbl.fdApplicants"/><font color="red">*</font></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="fdApplicants" property="fdApplicants" maxlength="50" value="${fetchCollateralDetails[0].fdApplicants}" />
		     </td>
		   </tr>
		  
		 <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V"  class="topformbutton2" onclick="return saveFDDetails();"><bean:message key="button.save" /></button>  </td>
		  </tr>  
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>   
	</table>
	</td>
	</tr>
	</table>
	</fieldset>	
	
</html:form>
</logic:present>
</div>

</body>