<!--Author Name : Ritu Jindal-->
<!--Date of Creation : -->
<!--Purpose  : Information of Branch Master Add-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/branchScript.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('branchMasterForm').branchAccount.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('branchMasterForm').branchShortCode.focus();
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

<body onload="enableAnchor();fntab();" onunload="closeAllLovCallUnloadBody();">
<html:errors/>



<html:form styleId="branchMasterForm"  method="post"  action="/branchMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <input type="hidden"  name="branchID" id="branchID"  value="${sessionScope.branchID}"/>
<fieldset>
<legend><bean:message key="lbl.branchMaster" /></legend>
  <table width="100%">
   
   
 <html:hidden  property="branchId" styleId="branchId"  value="${requestScope.list[0].branchId}" />
   
   <logic:present name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
   <logic:iterate id="listObj" name="list">
     <tr> 
     
      <td><bean:message key="lbl.branchShortCode" /><span><font color="red">*</font></span></td>
      <td><html:text property="branchShortCode" styleClass="text" styleId="branchShortCode" maxlength="6" readonly="true" value="${listObj.branchShortCode}" onblur="fnChangeCase('branchMasterForm','branchShortCode')"/></td>
      
      <td><bean:message key="lbl.branchDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="branchDesc" styleClass="text" styleId="branchDesc" maxlength="50" readonly="true"  value="${listObj.branchDesc}" /></td>
   </tr>
   
   <tr> 
      <td><bean:message key="lbl.branchAccount" /></td>
      <td><html:text property="branchAccount" styleClass="text" style="text-align: right" styleId="branchAccount" maxlength="16"  value="${listObj.branchAccount}" onkeypress="return isNumberKey(event);" /></td> 
   
      <td><bean:message key="lbl.company" /><span><font color="red">*</font></span></td>
      <td><html:text property="companyId" styleClass="text" styleId="companyId"  readonly="true" value="${listObj.lbxCompanyID}" tabindex="-1"/>
      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
      <html:hidden  property="lbxCompanyID" styleId="lbxCompanyID"  value="${listObj.companyId}"/>
      <html:button property="companyButton" styleId="companyButton" onclick="openLOVCommon(14,'branchMasterForm','companyId','','', '','','','lbxCompanyID');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
      <%-- <img onClick="openLOVCommon(14,'branchMasterForm','companyId','','', '','','','lbxCompanyID')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>
      </td>
      </tr>
  
  <tr>
  <td><bean:message key="lbl.country"/></td>
    <td><html:text property="country" styleId="country" maxlength="10" styleClass="text" value="${listObj.country}" readonly="true" tabindex="-1"/>
    <html:button property="countryButton" styleId="countryButton" onclick="openLOVCommon(4,'branchMasterForm','country','','','','','clearCountryLovChild','txtCountryCode');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChild','hcommon');"> --%>
    <html:hidden  property="txtCountryCode" styleId="txtCountryCode" value="${listObj.txtCountryCode}" /></td>
      
      <td><bean:message key="lbl.zone"/></td>
    <td><html:text property="zone" styleId="zone" maxlength="10" styleClass="text" value="${listObj.zone}" readonly="true" tabindex="-1"/>
    <html:button property="zoneButton" styleId="zoneButton" onclick="openLOVCommon(2058,'branchMasterForm','zone','','','','','','txtZoneCode');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChild','hcommon');"> --%>
    <html:hidden  property="txtZoneCode" styleId="txtZoneCode" value="${listObj.txtZoneCode}" /></td>
     </tr> 
     <tr>  
     <td><bean:message key="lbl.state"/><span><font color="red">*</font></span></td>
    <td><html:text property="state" styleId="state" styleClass="text" maxlength="10" value="${listObj.state}" readonly="true" tabindex="-1"/>
    <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'branchMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearDistrictLovChild','txtStateCode');" value=" " styleClass="lovbutton"> </html:button>
   <%--  <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(5,'bankBranchMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChild','hcommon');"> --%>
    <html:hidden property="tahsil" styleId="tahsil" styleClass="text" value=""/>
    <html:hidden property="tahsilDesc" styleId="tahsilDesc" styleClass="text" value=""/>
    <html:hidden  property="txtStateCode" styleId="txtStateCode" value="${listObj.txtStateCode}" /></td>
    
    <td><bean:message key="lbl.cluster"/></td>
    <td><html:text property="cluster" styleId="cluster" maxlength="10" styleClass="text" value="${listObj.cluster}" readonly="true" tabindex="-1"/>
    <html:button property="clusterButton" styleId="clusterButton" onclick="openLOVCommon(2059,'branchMasterForm','cluster','','','','','','txtclusterCode');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChild','hcommon');"> --%>
    <html:hidden  property="txtclusterCode" styleId="txtclusterCode" value="${listObj.txtclusterCode}" /></td>

     </tr>

  
   <tr> 
   <td><bean:message key="lbl.dist"/></td>
    <td><html:text property="district" styleId="dist" styleClass="text" maxlength="10" value="${requestScope.list[0].district}" readonly="true" tabindex="-1"/>
    <html:button property="districtButton" styleId="districtButton" onclick="openLOVCommon(20,'branchMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','txtDistCode');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(20,'bankBranchMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','hcommon');"> --%>
    <html:hidden  property="txtDistCode" styleId="txtDistCode" value="${requestScope.list[0].txtDistCode}" /></td>
    
    
  
      
      <td><bean:message key="lbl.region" /><span><font color="red">*</font></span></td>
      <td><html:text property="regionId" styleClass="text" styleId="regionId"  readonly="true" value="${listObj.lbxRegionID}" tabindex="-1"/>
      <html:hidden  property="lbxRegionID" styleId="lbxRegionID"  value="${listObj.regionId}"/>
      <html:button property="regionButton" styleId="regionButton" onclick="openLOVCommon(15,'branchMasterForm','regionId','','', '','','','lbxRegionID');" value=" " styleClass="lovbutton"> </html:button>
      <%-- <img onClick="openLOVCommon(15,'branchMasterForm','regionId','','', '','','','lbxRegionID')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>
      </td>
      </tr>
      
     <tr> 
     
       <td><bean:message key="lbl.areaCode" /></td> 
  		 <td>
  		      
   		
 <html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal" value="${requestScope.areaDescIds}" />
   			      
   			<html:select property="areaCodename" styleId="areaCodename" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="areaCodeList">
       			 <html:optionsCollection name="areaCodeList" value="areaCode" label="areaDesc"/>
      		</logic:present>
     		</html:select>
      		<html:button property="areaCodeButton" styleId="areaCodeButton" onclick="openMultiSelectLOVCommon(372,'branchMasterForm','areaCodename','','', '','','','lbxareaCodeVal');" value=" " styleClass="lovbutton"></html:button>

     <!--  <img SRC="/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"> -->
       </td>
      <td><bean:message key="lbl.active" /></td>
      
      <logic:equal value="Active" name="status">
       <td><input type="checkbox" name="branchStatus" id="branchStatus" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="Active" name="status">
       <td><input type="checkbox" name="branchStatus" id="branchStatus"  /></td>
      </logic:notEqual>
     </tr>
     <tr>
     <td><bean:message key="lbl.branchAddress1" /></td>
      <td><html:text property="branchAddress1" styleClass="text" styleId="branchAddress1" maxlength="50" value="${listObj.branchAddress1}" /></td>
      <td><bean:message key="lbl.branchAddress2" /></td>
      <td><html:text property="branchAddress2" styleClass="text" styleId="branchAddress2" maxlength="50" value="${listObj.branchAddress2}" /></td>
   </tr>
   <tr>
     <td><bean:message key="lbl.branchAddress3" /></td>
      <td><html:text property="branchAddress3" styleClass="text" styleId="branchAddress3" maxlength="50" value="${listObj.branchAddress3}" /></td>
     
		 <td><bean:message key="lbl.branchPincode" /></td>
      <td><html:text property="branchPincode" styleClass="text" style="text-align: right" styleId="branchPincode" maxlength="6"  value="${listObj.branchPincode}" onkeypress="return isNumberKey(event);" /></td> 
   </tr> 
   </logic:iterate>
   </logic:present>
   
   <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
   <tr>
   
   	  <td><bean:message key="lbl.branchShortCode" /><span><font color="red">*</font></span></td>
      <td><html:text property="branchShortCode" styleClass="text" styleId="branchShortCode" maxlength="6" onblur="fnChangeCase('branchMasterForm','branchShortCode')"/></td>
      
      <td><bean:message key="lbl.branchDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="branchDesc" styleClass="text" styleId="branchDesc" maxlength="50"  onblur="fnChangeCase('branchMasterForm','branchDesc')" /></td>
    </tr>
    
	<tr>
      <td><bean:message key="lbl.branchAccount" /></td>
      <td><html:text property="branchAccount" styleClass="text" style="text-align: right" styleId="branchAccount"  maxlength="16" onkeypress="return isNumberKey(event);"/></td>
      
 	  <td><bean:message key="lbl.company" /><span><font color="red">*</font></span></td>
      <td><html:text property="companyId" styleClass="text" styleId="companyId"  readonly="true" tabindex="-1"/>
      <html:hidden  property="lbxCompanyID" styleId="lbxCompanyID"  />
      <%-- <img onClick="openLOVCommon(14,'branchMasterForm','companyId','','', '','','','lbxCompanyID')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>
      <html:button property="companyButton" styleId="companyButton" onclick="openLOVCommon(14,'branchMasterForm','companyId','','', '','','','lbxCompanyID');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
      </td>
    </tr>
    
    <tr>
     <td><bean:message key="lbl.country"/></td>
     <td>
	    <logic:present name="defaultcountry">
	      <html:text property="country" styleId="country" maxlength="10" styleClass="text" value="${defaultcountry[0].defaultcountryname}" readonly="true" tabindex="-1"/>
	    </logic:present>
	    <logic:notPresent name="defaultcountry">
	      <html:text property="country" styleId="country" maxlength="10" styleClass="text" readonly="true" tabindex="-1"/>
	    </logic:notPresent>
	                                                                                                                                             
	    <html:button property="countryButton" styleId="countryButton" onclick="openLOVCommon(4,'branchMasterForm','country','','','','','clearCountryLovChild','txtCountryCode');" value=" " styleClass="lovbutton"> </html:button>
	    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChild','hcommon');"> --%>
	    <logic:present name="defaultcountry">
	      <html:hidden  property="txtCountryCode" styleId="txtCountryCode" value="${defaultcountry[0].defaultcountryid}" />
	    </logic:present>
	    <logic:notPresent name="defaultcountry">
	      <html:hidden  property="txtCountryCode" styleId="txtCountryCode"  />
	    </logic:notPresent>
	    
     </td>
     <td><bean:message key="lbl.zone"/></td>
    <td><html:text property="zone" styleId="zone" maxlength="10" styleClass="text" value="${listObj.zone}" readonly="true" tabindex="-1"/>
    <html:button property="zoneButton" styleId="zoneButton" onclick="openLOVCommon(2058,'branchMasterForm','zone','','','','','','txtZoneCode');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChild','hcommon');"> --%>
    <html:hidden  property="txtZoneCode" styleId="txtZoneCode" value="${listObj.txtZoneCode}" /></td>
    </tr>
     
     <tr>
      <td><bean:message key="lbl.state"/><span><font color="red">*</font></span></td>
     <td>
	     <html:text property="state" styleId="state" styleClass="text" maxlength="10" readonly="true" tabindex="-1"/>
	     <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'branchMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearDistrictLovChild','txtStateCode');" value=" " styleClass="lovbutton"> </html:button>
	     <html:hidden property="tahsil" styleId="tahsil" styleClass="text" value=""/>
	     <html:hidden property="tahsilDesc" styleId="tahsilDesc" styleClass="text" value=""/>
	     <html:hidden  property="txtStateCode" styleId="txtStateCode"  />
     </td>
     <td><bean:message key="lbl.cluster"/></td>
    <td><html:text property="cluster" styleId="cluster" maxlength="10" styleClass="text" value="${listObj.cluster}" readonly="true" tabindex="-1"/>
    <html:button property="clusterButton" styleId="clusterButton" onclick="openLOVCommon(2059,'branchMasterForm','cluster','','','','','','txtclusterCode');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChild','hcommon');"> --%>
    <html:hidden  property="txtclusterCode" styleId="txtclusterCode" value="${listObj.txtclusterCode}" /></td>
                            
     </tr>
  

  
	<tr>
	
	 <td><bean:message key="lbl.dist"/></td>
    <td>
	    <html:text property="district" styleId="dist" styleClass="text" maxlength="10" readonly="true" tabindex="-1"/>
	    <html:button property="districtButton" styleId="districtButton" onclick="openLOVCommon(20,'branchMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','txtDistCode');" value=" " styleClass="lovbutton"> </html:button>
	    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(20,'bankBranchMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first','','hcommon');">--%>
	    <html:hidden  property="txtDistCode" styleId="txtDistCode" />
	</td>
    
    <td><bean:message key="lbl.region" /><span><font color="red">*</font></span></td>
      <td><html:text property="regionId" styleClass="text" styleId="regionId"  readonly="true" tabindex="-1" />
      <html:hidden  property="lbxRegionID" styleId="lbxRegionID"  />
      <html:button property="regionButton" styleId="regionButton" onclick="openLOVCommon(15,'branchMasterForm','regionId','','', '','','','lbxRegionID');" value=" " styleClass="lovbutton"> </html:button>
      <%-- <img onClick="openLOVCommon(15,'branchMasterForm','regionId','','', '','','','lbxRegionID')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>
      </td>
    
      </tr>
      <tr>
       <td><bean:message key="lbl.areaCode" /></td> 
  		 <td>
  		      
   			<html:select property="areaCodename" styleId="areaCodename" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="areaCodelist">
       			 <html:optionsCollection name="areaCodelist" value="areaCode" label="areaDesc"/>
      		</logic:present>
     		</html:select>
     			
      		<html:button property="areaCodeButton" styleId="areaCodeButton" onclick="openMultiSelectLOVCommon(372,'branchMasterForm','areaCodename','','', '','','','lbxareaCodeVal');" value=" " styleClass="lovbutton"></html:button>
     <html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal" value=""  />

     <!--  <img SRC="/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"> -->
       </td>
      
      <td><bean:message key="lbl.active" /></td>
       <td><input type="checkbox" name="branchStatus" id="branchStatus" checked="checked" /></td>
    </tr>
	 <tr>
    <td><bean:message key="lbl.branchAddress1" /></td>
      <td><html:text property="branchAddress1" styleClass="text" styleId="branchAddress1" maxlength="50"  onblur="fnChangeCase('branchMasterForm','branchAddress1')" /></td>
      <td><bean:message key="lbl.branchAddress2" /></td>
      <td><html:text property="branchAddress2" styleClass="text" styleId="branchAddress2" maxlength="50"  onblur="fnChangeCase('branchMasterForm','branchAddress2')" /></td>
    </tr>
     <tr>
    <td><bean:message key="lbl.branchAddress3" /></td>
      <td><html:text property="branchAddress3" styleClass="text" styleId="branchAddress3" maxlength="50"  onblur="fnChangeCase('branchMasterForm','branchAddress3')" /></td>
     	
		 <td><bean:message key="lbl.branchPincode" /></td>
      <td><html:text property="branchPincode" styleClass="text" style="text-align: right" styleId="branchPincode" maxlength="6"  value="${listObj.branchPincode}" onkeypress="return isNumberKey(event);" /></td> 
    </tr> 
   </logic:notPresent>
   
     <tr><td>
    
    <br>
    <logic:present name="editVal">
<!-- button.update is changed to button.save ------By Nishant Rai   -->
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditBranch();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="editValUpdate">
     <button type="button"  name="save" id="save" title="Alt+U" accesskey="U" onclick="return fnEditBranch();" class="topformbutton2" ><bean:message key="button.update" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveBranch();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
	
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("branchMasterForm").action="branchMasterBehind.do";
	    document.getElementById("branchMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("branchMasterForm").action="branchMasterBehind.do";
	    document.getElementById("branchMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.branchCodeExist" />");
		
	}
	else
	{
		alert("<bean:message key="msg.notepadError" />");		
		
	}
	
	
</script>
</logic:present>
  </body>
		</html:html>