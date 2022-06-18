<%--	Created By Abhimanyu kumar	--%> 
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>    
     		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
			</logic:present>
			<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
			</logic:notPresent>
   <link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" /> 
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/manualDeviationMaster.js"></script> 	
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
   <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
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
  <body onload="enableAnchor();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <html:form styleId="manualdeviationfrm" action="/manualDeviationAddProcessAction" >
<logic:present name="list">
 <input type="hidden" name="manualid" id="manualid" value="${list[0].manualid}" />
</logic:present>
<logic:notPresent name="list">
<input type="hidden" name="manualid" id="manualid" value="<%=CommonFunction.checkNull(request.getParameter("manualid"))%>" />
</logic:notPresent> 
 <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
  <fieldset>
  <legend><bean:message key="lbl.manualdevmasterLegend"/></legend>  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="4" cellspacing="2">    
  <tr>
    <td><bean:message key="product.desc"/><span><font color="red">*</font></span></td>
     <td>
      <html:text property="product" styleId="product"  styleClass="text" readonly="true" value="${list[0].product}" tabindex="-1"/>
       <html:hidden  property="lbxProductID" value="${list[0].lbxProductID}" styleId="lbxProductID" />
  	  <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(89,'manualdeviationfrm','product','','scheme', '','','','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>
  </td>
    <td><bean:message key="scheme.desc"/><span><font color="red">*</font></span></td>
    <td><html:text property="scheme" styleId="scheme" styleClass="text" value="${list[0].scheme}" readonly="true" tabindex="-1" />
    <html:hidden  property="lbxSchemeID" value="${list[0].lbxSchemeID}" styleId="lbxSchemeID" />
  	 <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(75,'manualdeviationfrm','scheme','product','', 'lbxProductID','Select Product First','','lbxSchemeID');closeLovCallonLov('schemeId');" value=" " styleClass="lovbutton"> </html:button>
  	 </td>
  </tr>
  <tr>
    <td><bean:message key="lbl.stageDesc"/><span><font color="red">*</font></span></td>
    <td><html:text property="stage" styleId="stage"  styleClass="text" readonly="true" value="${list[0].stage}" tabindex="-1"/>
  	 <html:button property="schemeButton" styleId="schemeButton" value=" " onclick="openLOVCommon(251,'manualdeviationfrm','lbxPCDStage','','', '','','','stage');closeLovCallonLov('schemeId');" styleClass="lovbutton"> </html:button>
  	 <html:hidden  property="lbxPCDStage" value="${list[0].lbxPCDStage}" styleId="lbxPCDStage" /></td>  
  	 
  	
    <td><bean:message key="lbl.PCDAppLevel" /><span><font color="red">*</font></span></td>
    <td><html:select styleClass="approvalLevel" property="approvalLevel" styleId="approvalLevel" value="${list[0].approvalLevel}" >
		 <logic:present name="pmstSize">
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { 
   				if(i==1){%>
   					<html:option value="">Select</html:option>	
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option> 
				  <%} else { %> 
				  <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>  
				<%} 				
			} %>
		</logic:present>
		</html:select>
  	 </tr>  
  <tr> 
  		<td><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td>
		<html:select property="subRuleType" styleId="subRuleType" styleClass="text" value="${list[0].subRuleType}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td> 
     <td>Status</td>
   		<td>   		
   			<logic:notPresent name="list">
	  			<input type=checkbox id="status" name="status" checked="checked"/>
	  		</logic:notPresent>
	  		<logic:empty name="list">
	  		<logic:present name="X">
	  		<input type=checkbox id="status" name="status" checked="checked"/>
	  		</logic:present>
	  		</logic:empty>
	  		
	  		<logic:notEmpty name="list">
	  		<logic:present name="list">
	  			<logic:present name="A">
	  				<input type=checkbox id="status" name="status" checked="checked"/>
	  			</logic:present>
	  			<logic:present name="X">
	  				<input type=checkbox id="status" name="status"/>
	  			</logic:present>
	  		</logic:present>
	  		</logic:notEmpty>	  
	  </td>    
    </tr>  
    <tr>
    	<td><bean:message key="lbl.devRuleDesc"/><span><font color="red">*</font></span></td>
	  	<td>
		  	 <html:textarea property="ruleDescription" value="${list[0].ruleDescription}" style='width:400px; max-width:400px; min-width:400px' onchange="chkLength();" 
		  	 styleClass="textarea" styleId="ruleDescription">  	  
		     </html:textarea>
	    </td> 
    </tr>
  <tr>
 	 <td align="left" colspan="2" >
 	 <logic:present name="search" >   			
   				 <button type="button" name="save" id="save" accesskey="V" title="ALT+V"  class="topformbutton2"  onclick="return saveManulaDeviation();"><bean:message key="button.save" /></button>
   				 <!--  <button type="button" name="update" id="update" class="topformbutton2" onclick="return updateMakeModelRecord();"><bean:message key="button.save" /></button>-->
   	</logic:present>
   	<logic:notPresent name="search">
   				 <button type="button" name="save" id="save" accesskey="V" title="ALT+V"  class="topformbutton2"  onclick="return saveManulaDeviation();"><bean:message key="button.save" /></button>
   	</logic:notPresent>
<!-- 	 <button type="button" name="button" id="button" title="Alt+S" accesskey="S" class="topformbutton2" onclick="return searchManualDeviation();" ><bean:message key="button.search" /></button>-->
<!-- 	 <button type="button"  id="Add" title="Alt+A" accesskey="A" class="topformbutton2" onclick="return manualDeviationAddRow();"><bean:message key="button.add" /></button></td>-->
  </tr>
 </table></td>
  </tr></table>
  </fieldset>
<fieldset style="display: none;">
<legend><bean:message key="lbl.manualdevmasterLegend" /></legend>  
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.productId" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.schemeCode" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.stageDesc" /> <br> </b>
									</td>
									 <td width="220" class="white2" style="width: 250px;">
									     <b><bean:message key="lbl.PCDAppLevel" /> <br> </b>
									     
									</td> 
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.devRuleDesc" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.PCDStatus" /> <br> </b>
									</td>	
										
									
									
 					
							    </tr>
							    <tr id="p1"></tr>
							    
			            </table>
						</td>
					</tr>
</table>
</fieldset>
<logic:present name="save">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataSave" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("manualdeviationfrm").action=contextPath+"/manualDevMasterBehindAction.do";
		document.getElementById("manualdeviationfrm").submit();
	</script>
</logic:present>
<logic:present name="update">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataSave" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("manualdeviationfrm").action=contextPath+"/manualDevMasterBehindAction.do";
		document.getElementById("manualdeviationfrm").submit();		
	</script>
</logic:present>

</html:form>  
</body>
</html:html>
