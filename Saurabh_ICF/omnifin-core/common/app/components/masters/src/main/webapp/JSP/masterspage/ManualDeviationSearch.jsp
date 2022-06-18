<%--
	Created By Abhimanyu kumar   	
 --%> 
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %>
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
 <html:form styleId="manualdeviationfrm" action="/manualDevMasterBehindAction" >

 <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
  <fieldset>
  <legend><bean:message key="lbl.manualdevmastersearchLegend"/></legend>  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="4" cellspacing="2">    
  <tr>
    <td><bean:message key="product.desc"/></td>
     <td>
      <html:text property="product" styleId="product" value="" styleClass="text" readonly="true" tabindex="-1"/>
       <html:hidden  property="lbxProductID" value="" styleId="lbxProductID" />
  	  <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(89,'manualdeviationfrm','product','','scheme', '','','','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>
  </td>
    <td><bean:message key="scheme.desc"/></td>
    <td><html:text property="scheme" styleId="scheme" value="" styleClass="text" readonly="true" tabindex="-1" />
    <html:hidden  property="lbxSchemeID" value="" styleId="lbxSchemeID" />
  	 <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(75,'manualdeviationfrm','scheme','product','', 'lbxProductID','Select Product First','','lbxSchemeID');closeLovCallonLov('schemeId');" value=" " styleClass="lovbutton"> </html:button>
  	 </td>
  </tr>
  <tr>
    <td><bean:message key="lbl.stageDesc"/></td>
    <td><html:text property="stage" styleId="stage"  styleClass="text" readonly="true" value="" tabindex="-1"/>
  	 <html:button property="schemeButton" styleId="schemeButton" value=" " onclick="openLOVCommon(251,'manualdeviationfrm','lbxPCDStage','','', '','','','stage');closeLovCallonLov('schemeId');" styleClass="lovbutton"> </html:button>
  	 <html:hidden  property="lbxPCDStage" value="" styleId="lbxPCDStage" /></td>   	
  </tr>
  <tr>
 	 <td align="left" colspan="2" >
 	 <button type="button" name="button" id="button" title="Alt+S" accesskey="S" class="topformbutton2" onclick="return searchManualDeviation();" ><bean:message key="button.search" /></button>
 	 <button type="button"  id="Add" title="Alt+A" accesskey="A" class="topformbutton2" onclick="return manualDeviationAdd();"><bean:message key="button.add" /></button></td>
  </tr>
 </table></td>
  </tr></table>
  </fieldset>

<fieldset>
<legend><bean:message key="lbl.manualdevmasterLegend" /></legend> 
		 
  <logic:present name="list">
  <logic:notEmpty name="list"> 
   <display:table  id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/manualDeviationAddProcessAction.do?method=searchManualDeviation" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="manualid" titleKey="lbl.manualid" sortable="true"  />
	<display:column property="product" titleKey="product.desc" sortable="true"  />
	<display:column property="scheme" titleKey="scheme.desc" sortable="true"  />
	<display:column property="ruleDescription" titleKey="lbl.devRuleDesc" sortable="true"  />
	<display:column property="stage" titleKey="lbl.manualStage" sortable="true"  />
	<display:column property="status" titleKey="lbl.stageDesc" sortable="true"  />
</display:table>
</logic:notEmpty>
  </logic:present>
  <logic:notPresent name="list">
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="product.desc" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="scheme.desc" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.devRuleDesc" /> <br> </b>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.stageDesc" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.PCDStatus" /> <br> </b>
									</td>
									<tr class="white2">
	                                  <td colspan="7"><bean:message key="lbl.noDataFound" /></td>
                                    </tr>
							</tr>
			            </table>
						</td>
					</tr>
				</table>
</logic:empty>
</logic:notPresent>
</fieldset>
</html:form>  
  </body>
</html:html>
