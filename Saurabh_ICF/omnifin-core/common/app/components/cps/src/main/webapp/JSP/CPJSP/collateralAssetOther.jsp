<%--
      Author Name-      Pawan Kumar Singh
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
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		 
		 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('OthersForm').assetsCollateralDesc.focus();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>

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

<div id=centercolumn>
<div id=revisedcontainer>	
<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">

<html:form action="/collateralAssetProcessAction" styleId="OthersForm" method="post">
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />
<html:hidden property="assetsIdOther" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdOther"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="oth"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="oth1"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="oth2"/>
<fieldset>
<legend><bean:message key="lbl.description"/></legend>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
    <TR>
     <TD width="23%"><bean:message key="lbl.assetsDescription"/><font color="red">*</font></TD>
     <TD width="26%" noWrap><html:text styleClass="text" styleId="assetsCollateralDesc" maxlength="100" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></TD>
     <TD><bean:message key="lbl.assetsValue"/><font color="red">*</font></TD>
     <TD width="32%" noWrap><html:text styleClass="text" styleId="assetsCollateralValue" style="text-align: right" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onchange="checkNumber(this.value, event, 18, id)" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></TD>
                                                                                          
    </TR>	
    	<tr>
 
 <logic:present name="fetchCollateralDetails">
 <logic:iterate name="fetchCollateralDetails" id="subList">
 <logic:equal name="subList" property="colltype2" value="ASSET">
 	<td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>
 </logic:equal>
  </logic:iterate>
 </logic:present>
 <logic:notPresent name="fetchCollateralDetails">
<logic:present name="assetCollateralTypeAsset" >
 <td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" styleClass="text" >
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>
                        
</logic:present> 
 </logic:notPresent>  
 			<logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="${fetchCollateralDetails[0].securityTypes}">
			     <logic:present name="securityType">
					<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
					</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>
		      <logic:notPresent name="fetchCollateralDetails">
		  	 <logic:present name="assetCollateralTypeAsset" >
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="" >
			     <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:present>
		      </logic:notPresent> 	
    	
    </tr>
    
    <tr>
		  	<logic:present name="assetCollateralType">
		  	<logic:equal value="ASSET" name="assetCollateralType">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  /></td>
		  	</logic:equal>
		  	</logic:present>
		  	
		  	<logic:equal name="actype" value="ASSET">
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" /></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	
		  	</tr>
		  		
		<tr>
          	<td><button type="button" id="assetButton" name="Submit20" value="Save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveOthersDetails();"><bean:message key="button.save" /></button>  
          </td>       
          
		 </tr>
		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>  
  </TABLE>
</TD>
</TR>
</TABLE>
</fieldset>
</html:form>
</logic:present>
<%--          			view Deal 																 --%>

<logic:present name="viewDeal">
<html:form action="/collateralAssetProcessAction" styleId="OthersForm" method="post">
<html:hidden property="assetsIdOther" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdOther"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="oth"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="oth1"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="oth2"/>
<fieldset>
<legend><bean:message key="lbl.description"/></legend>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
    <TR>
     <TD width="23%"><bean:message key="lbl.assetsDescription"/><font color="red">*</font></TD>
     <TD width="26%" noWrap><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></TD>
     <TD><bean:message key="lbl.assetsValue"/><font color="red">*</font></TD>
     <TD width="32%" noWrap><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" style="text-align: right"  property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></TD>
    </TR>
    
    <tr>
   			 <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].securityTypes}">
			       <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			     
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>
		      
		    <logic:equal name="actype" value="ASSET">
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked" disabled="disabled"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" disabled="disabled"/></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	
    </tr>	
    	
<!--		<tr>-->
<!--          	<td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveOthersDetails();"/>  -->
<!--          </td>       -->
          
		 </tr>
		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>  
  </TABLE>
</TD>
</TR>
</TABLE>
</fieldset>

 
</html:form>
</logic:present>
</div>

<%--          			Loan Initiation Author part																	 --%>

<logic:present name="viewOtherInCM">

	<html:form action="/collateralAssetProcessAction" styleId="OthersForm" method="post">

<fieldset>
<legend><bean:message key="lbl.description"/></legend>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
    <TR>
     <TD width="23%"><bean:message key="lbl.assetsDescription"/><font color="red">*</font></TD>
     <TD width="26%" noWrap><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${viewOtherInCM[0].assetsCollateralDesc}" /></TD>
     <TD><bean:message key="lbl.assetsValue"/><font color="red">*</font></TD>
     <TD width="32%" noWrap><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" value="${viewOtherInCM[0].assetsCollateralValue}" onkeypress="return isNumberKey(event);"/></TD>
    </TR>	
    <tr>
    
    
     	<logic:present name="viewOtherInCM">
			 <logic:iterate name="viewOtherInCM" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" disabled="true" value="${viewOtherInCM[0].securityTypes}">
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present> 
			 </html:select>
		      </td>
		      </logic:equal>
		      
		    <logic:equal value="ASSET" name="acType">
		    <logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked" disabled="disabled"/></td>
		  	</logic:equal>
		  		
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" disabled="disabled"/></td>
		  	</logic:notEqual>
		  	</logic:equal>
		  	
		      </logic:iterate>
		      </logic:present>
		      
		  	
    	</tr>
<!--		<tr>-->
<!--          	<td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveOthersDetails();"/>  -->
<!--          </td>       -->
          

		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>  
  </TABLE>
</TD>
</TR>
</TABLE>
</fieldset>

 
</html:form>

</logic:present>
<%--          			Insertion in LOAN iNITIATION													 --%>
<logic:present name="loanInit">
	<html:form action="/collateralAssetProcessAction" styleId="OthersForm" method="post">
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />
<html:hidden property="assetsIdOther" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdOther"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="oth"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="oth1"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="oth2"/>
<fieldset>
<legend><bean:message key="lbl.description"/></legend>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
    <TR>
     <TD width="23%"><bean:message key="lbl.assetsDescription"/><font color="red">*</font></TD>
     <TD width="26%" noWrap><html:text styleClass="text" styleId="assetsCollateralDesc" maxlength="100" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></TD>
     <TD><bean:message key="lbl.assetsValue"/><font color="red">*</font></TD>
     <TD width="32%" noWrap><html:text styleClass="text" styleId="assetsCollateralValue" style="text-align: right" maxlength="22.4" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}"  property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></TD>
    </TR>	
    <tr>
   <logic:present name="fetchCollateralDetails">
 <logic:iterate name="fetchCollateralDetails" id="subList">
 <logic:equal name="subList" property="colltype2" value="ASSET">
 	<td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>
 </logic:equal>
  </logic:iterate>
 </logic:present>
 <logic:notPresent name="fetchCollateralDetails">
<logic:present name="assetCollateralTypeAsset" >
 <td><br><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" styleClass="text" >
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>
                
</logic:present> 
 </logic:notPresent>   	
    
    
    
    </tr>	
		<tr>
          	<td><button type="button" name="Submit20" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveOthersDetails();"><bean:message key="button.save" /></button>  
          </td>       
          
		 </tr>
		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>  
  </TABLE>
</TD>
</TR>
</TABLE>
</fieldset>
</html:form>
</logic:present>
</div>

</body>