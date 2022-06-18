<%--
      Author Name-     Pawan Kumar Singh
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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('StockForm').assetsCollateralDesc.focus();init_fields();">
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
		request.setAttribute("assetCollateralType",assetCollateralType);
	    request.setAttribute("new","N");
	}
	
 %>

<div id=centercolumn>
<div id=revisedcontainer>	

 <%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">
<html:form action="/collateralStockProcessAction" styleId="StockForm" method="post">
<html:javascript formName="CollateralStockDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdStock" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdStock"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="stock1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="stock2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="stock3"/>

<FIELDSET>
<LEGEND><bean:message key="lbl.stockDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>  
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockDescription"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc"  maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 	   <td><bean:message key="lbl.stockValue"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" maxlength="18" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return isNumberKey(event);"/></td>
 	   </TR>
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockSecurityMargin"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" style="text-align: right" maxlength="3" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
 	   <td style="width:23%"><bean:message key="lbl.stockType"/><font color="red">*</font></TD>
 	   <td width="26%" style="width:26%"><html:select property="stockType" styleId="stockType" styleClass="text" value="${fetchCollateralDetails[0].stockType}">
 	     <option value="Master">Raw Material</option>
 	     <option value="Master">Semi Finished</option>
 	     <option value="Master">Finished</option>
 	     </html:select></td>
 	   </TR>
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockNature"/><font color="red">*</font> </td>
 	   <td style="width:26%"><html:select property="stockNature" styleId="stockNature" styleClass="text" value="${fetchCollateralDetails[0].stockNature}">
 	     <option value="Master">Perishable</option>
 	     <option value="Master">Non Perishable</option>
 	     </html:select></td>
 	   <td><bean:message key="lbl.stockGodownAddress"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="stockAddress" maxlength="50" property="stockAddress" value="${fetchCollateralDetails[0].stockAddress}"/></td>
         </TR>
        <TR>
          <td><bean:message key="lbl.stockInventoryCycle"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="stockCycle" maxlength="50" property="stockCycle" value="${fetchCollateralDetails[0].stockCycle}" /></td>
          
		 </TR>
		   <TR>
		     
		    </TR>	 
			  <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveStockDetails();"><bean:message key="button.save" /></button>  </td>
		  </tr>  
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>    
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
</html:form>
</logic:present>
<%--          			view Deal 																 --%>


 <logic:present name="viewDeal">
<html:form action="/collateralStockProcessAction" styleId="StockForm" method="post">
<html:javascript formName="CollateralStockDynaValidatorForm"/>
<html:hidden property="assetsIdStock" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdStock"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="stock1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="stock2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="stock3"/>

<FIELDSET>
<LEGEND><bean:message key="lbl.stockDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>  
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockDescription"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 	   <td><bean:message key="lbl.stockValue"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 	   </TR>
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockSecurityMargin"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
 	   <td style="width:23%"><bean:message key="lbl.stockType"/><font color="red">*</font></TD>
 	   <td width="26%" style="width:26%"><html:select property="stockType" styleId="stockType" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].stockType}">
 	     <option value="Master">Raw Material</option>
 	     <option value="Master">Semi Finished</option>
 	     <option value="Master">Finished</option>
 	     </html:select></td>
 	   </TR>
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockNature"/><font color="red">*</font> </td>
 	   <td style="width:26%"><html:select property="stockNature" styleId="stockNature" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].stockNature}">
 	     <option value="Master">Perishable</option>
 	     <option value="Master">Non Perishable</option>
 	     </html:select></td>
 	   <td><bean:message key="lbl.stockGodownAddress"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="stockAddress" disabled="true" property="stockAddress" value="${fetchCollateralDetails[0].stockAddress}"/></td>
         </TR>
        <TR>
          <td><bean:message key="lbl.stockInventoryCycle"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="stockCycle" disabled="true" property="stockCycle" value="${fetchCollateralDetails[0].stockCycle}" /></td>
          
		 </TR>
		   <TR>
		     
		    </TR>	 
<!--			  <tr>-->
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveStockDetails();"/>  </td>-->
<!--		  </tr>  -->
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>    
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
</html:form>
</logic:present>
</div>
	<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewStockInCM">

	<html:form action="/collateralStockProcessAction" styleId="StockForm" method="post">


<FIELDSET>
<LEGEND><bean:message key="lbl.stockDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>  
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockDescription"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${viewStockInCM[0].assetsCollateralDesc}" /></td>
 	   <td><bean:message key="lbl.stockValue"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" style="text-align: right" value="${viewStockInCM[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 	   </TR>
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockSecurityMargin"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" property="collateralSecurityMargin" style="text-align: right" value="${viewStockInCM[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
 	   <td style="width:23%"><bean:message key="lbl.stockType"/><font color="red">*</font></TD>
 	   <td width="26%" style="width:26%"><html:select property="stockType" styleId="stockType" disabled="true" styleClass="text" value="${viewStockInCM[0].stockType}">
 	     <option value="Master">Raw Material</option>
 	     <option value="Master">Semi Finished</option>
 	     <option value="Master">Finished</option>
 	     </html:select></td>
 	   </TR>
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockNature"/><font color="red">*</font> </td>
 	   <td style="width:26%"><html:select property="stockNature" styleId="stockNature" disabled="true" styleClass="text" value="${viewStockInCM[0].stockNature}">
 	     <option value="Master">Perishable</option>
 	     <option value="Master">Non Perishable</option>
 	     </html:select></td>
 	   <td><bean:message key="lbl.stockGodownAddress"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="stockAddress" disabled="true" property="stockAddress" value="${viewStockInCM[0].stockAddress}"/></td>
         </TR>
        <TR>
          <td><bean:message key="lbl.stockInventoryCycle"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="stockCycle" disabled="true" property="stockCycle" value="${viewStockInCM[0].stockCycle}" /></td>
          
		 </TR>
		   <TR>
		     
		    </TR>	 
<!--			  <tr>-->
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveStockDetails();"/>  </td>-->
<!--		  </tr>  -->
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>    
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
</html:form>
</logic:present>

<%--          			Insertion in LOAN iNITIATION													 --%>
<logic:present name="loanInit">
	<html:form action="/collateralStockProcessAction" styleId="StockForm" method="post">
<html:javascript formName="CollateralStockDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdStock" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdStock"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="stock1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="stock2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="stock3"/>

<FIELDSET>
<LEGEND><bean:message key="lbl.stockDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>  
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockDescription"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc"  maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 	   <td><bean:message key="lbl.stockValue"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" maxlength="18" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 	   </TR>
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockSecurityMargin"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" style="text-align: right" maxlength="3" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
 	   <td style="width:23%"><bean:message key="lbl.stockType"/><font color="red">*</font></TD>
 	   <td width="26%" style="width:26%"><html:select property="stockType" styleId="stockType" styleClass="text" value="${fetchCollateralDetails[0].stockType}">
 	     <option value="Master">Raw Material</option>
 	     <option value="Master">Semi Finished</option>
 	     <option value="Master">Finished</option>
 	     </html:select></td>
 	   </TR>
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.stockNature"/><font color="red">*</font> </td>
 	   <td style="width:26%"><html:select property="stockNature" styleId="stockNature" styleClass="text" value="${fetchCollateralDetails[0].stockNature}">
 	     <option value="Master">Perishable</option>
 	     <option value="Master">Non Perishable</option>
 	     </html:select></td>
 	   <td><bean:message key="lbl.stockGodownAddress"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="stockAddress" maxlength="50" property="stockAddress" value="${fetchCollateralDetails[0].stockAddress}"/></td>
         </TR>
        <TR>
          <td><bean:message key="lbl.stockInventoryCycle"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="stockCycle" maxlength="50" property="stockCycle" value="${fetchCollateralDetails[0].stockCycle}" /></td>
          
		 </TR>
		   <TR>
		     
		    </TR>	 
			  <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveStockDetails();"><bean:message key="button.save" /></button>  </td>
		  </tr>  
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>    
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
</html:form>
</logic:present>

</div>
</body>