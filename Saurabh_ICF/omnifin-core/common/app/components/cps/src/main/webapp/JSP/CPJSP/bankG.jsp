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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('BGForm').bgType.focus();init_fields();">
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
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>    
     <TR>
          <td style="width:23%"><bean:message key="lbl.bgType"/></td>
          <td nowrap="nowrap"><html:select property="bgType" styleId="bgType" styleClass="text" value="${fetchCollateralDetails[0].bgType}">
             <option value="F">Financial</option>
             <option value="P">Performance</option>
              
           </html:select></td>
          <td><bean:message key="lbl.bgAmount"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" style="text-align: right" styleId="assetsCollateralValue" property="assetsCollateralValue"  value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
         
          </TR>    
      
          <TR>
		     <td><bean:message key="lbl.bgInsurerDate"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="bgInDate" property="bgInDate" onchange="checkDate('bgInDate');checkInsurerDate('bgInDate');"  value="${fetchCollateralDetails[0].bgInDate}" /></td>
		     <td><bean:message key="lbl.bgValidity"/><font color="red">*</font></td>
            <td nowrap="nowrap"><html:text styleClass="text" styleId="bgValidity" property="bgValidity" onchange="checkDate('bgValidity');checkValidityDate('bgValidity');" value="${fetchCollateralDetails[0].bgValidity}" /></td> 
           </TR>  
           <TR>
		     <td><bean:message key="lbl.bgIssuing"/><font color="red">*</font></td>
		     <td>
		    	 <html:text styleClass="text" style="text-align: right"  styleId="bgIssuing" property="bgIssuing"  value="${fetchCollateralDetails[0].bgIssuing}" />
		     </td>		     
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

<%--          			view Deal 					
											 --%>
<logic:present name="viewDeal">
<html:form action="/bankGProcessAction" styleId="BGForm" method="post">

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
          <td nowrap="nowrap"><html:select property="bgType" styleId="bgType" styleClass="text" value="${fetchCollateralDetails[0].bgType}" disabled="true">
             <option value="F">Financial</option>
             <option value="P">Performance</option>
              
           </html:select></td>
          <td><bean:message key="lbl.bgAmount"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" disabled="true" style="text-align: right" styleId="assetsCollateralValue" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" /></td>
         
          </TR>    
      
          <TR>
		     <td><bean:message key="lbl.bgInsurerDate"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" disabled="true"  property="bgInDate"  value="${fetchCollateralDetails[0].bgInDate}" /></td>
		     <td><bean:message key="lbl.bgValidity"/><font color="red">*</font></td>
            <td nowrap="nowrap"><html:text styleClass="text" disabled="true"  property="bgValidity" value="${fetchCollateralDetails[0].bgValidity}" /></td> 
           </TR>  
           <TR>
		     <td><bean:message key="lbl.bgIssuing"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" disabled="true" style="text-align: right" styleId="bgIssuing" property="bgIssuing" onkeypress="return isNumberKey(event);" value="${fetchCollateralDetails[0].bgIssuing}" /></td>
		     
           </TR>  
		 		  <tr>
 		  <td> 
<!-- 		  <html:button property="button1" styleClass="topformbutton2" value="Save" onclick="return saveBGDetails();"/> 	-->
 		
 		    </td>	
		  </tr>	
		 
		
		 

	</TABLE>	
	 
	
	</TD>
	</TR>
	</TABLE>

</FIELDSET> 
</html:form>
</logic:present>
	<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewBgInCM">
	<html:form action="/bankGProcessAction" styleId="BGForm" method="post">

<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  


<html:hidden property="assetsIdBG" value="${viewBgInCM[0].assetsId}" styleClass="text" styleId ="assetsIdBG"/>
<html:hidden property="colltype1" value="${viewBgInCM[0].colltype1}${assetClass }" styleClass="text" styleId ="bg1"/>
<html:hidden property="colltype2" value="${viewBgInCM[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="bg2"/>
<html:hidden property="colltype3" value="${viewBgInCM[0].colltype3}${new }" styleClass="text" styleId ="bg3"/>


<FIELDSET>
<LEGEND><bean:message key="lbl.propertyDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>    
     <TR>
          <td style="width:23%"><bean:message key="lbl.bgType"/></td>
          <td nowrap="nowrap"><html:select property="bgType" styleId="bgType" styleClass="text" value="${viewBgInCM[0].bgType}" disabled="true">
             <option value="F">Financial</option>
             <option value="P">Performance</option>
              
           </html:select></td>
          <td><bean:message key="lbl.bgAmount"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" disabled="true" style="text-align: right" styleId="assetsCollateralValue" property="assetsCollateralValue" value="${viewBgInCM[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
         
          </TR>    
      
          <TR>
		     <td><bean:message key="lbl.bgInsurerDate"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" disabled="true"  property="bgInDate"  value="${viewBgInCM[0].bgInDate}" /></td>
		     <td><bean:message key="lbl.bgValidity"/><font color="red">*</font></td>
            <td nowrap="nowrap"><html:text styleClass="text" disabled="true"  property="bgValidity" value="${viewBgInCM[0].bgValidity}" /></td> 
           </TR>  
           <TR>
		     <td><bean:message key="lbl.bgIssuing"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" disabled="true" style="text-align: right" styleId="bgIssuing" property="bgIssuing" onkeypress="return isNumberKey(event);" value="${viewBgInCM[0].bgIssuing}" /></td>
		     
           </TR>  
		 		  <tr>
 		  <td> 
<!-- 		  <html:button property="button1" styleClass="topformbutton2" value="Save" onclick="return saveBGDetails();"/> 	-->
 		
 		    </td>	
		  </tr>	
		 
		
		 

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
          <td nowrap="nowrap"><html:text styleClass="text" style="text-align: right" styleId="assetsCollateralValue" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" /></td>
         
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


