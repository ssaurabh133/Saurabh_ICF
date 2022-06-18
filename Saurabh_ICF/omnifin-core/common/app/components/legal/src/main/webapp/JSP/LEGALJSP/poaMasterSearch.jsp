
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>


<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/poaMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);

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

<body onload="enableAnchor();document.getElementById('userbutton').focus();">
<html:form action="/poaMaster" styleId="poaMasterForm" method="post" > 	
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:hidden property="path" styleId="path" value="<%=request.getContextPath()%>"/>
<html:errors/>
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<fieldset>
<legend><bean:message key="lbl.poaMaster"/></legend>
	<table width="100%">
	
	<tr>
    <td><bean:message key="lbl.poa" /></td>
     <td ><html:text property="userId" styleClass="text" styleId="userId" readonly="true" />
     <html:button property="userbutton" styleId="userbutton"
      onclick="openLOVCommon(1534,'poaMasterForm','userId','','', '','','','lbxUserId')" 
      value=" " styleClass="lovbutton"></html:button>
		<html:hidden  property="lbxUserId" styleId="lbxUserId"  />
     </td> 
	 
	 <td><bean:message key="lbl.Branch" /></td>
     <td><html:text property="branch" styleClass="text" styleId="branch" maxlength="10"
      onblur="fnChangeCase('poaMasterForm','branch')"  readonly="true" />
     <html:button property="userButton" styleId="userButton" 
      onclick="openLOVCommon(1507,'poaMasterForm','branch','','', '','','','lbxBranchId')" 
      value=" " styleClass="lovbutton"></html:button></td>      
        <html:hidden  property="lbxBranchId" styleId="lbxBranchId" />
     <td><bean:message key="lbl.courtName" /></td>
	 
	 <td>
	 <html:text property="courtName" styleClass="text" styleId="courtName" maxlength="10"
	  onclick="fnChangeCase('poaMasterForm','courtName')"  readonly="true" />
	 <input type="hidden" name = "lbxCourtNameCode" id="lbxCourtNameCode"  />
	 <html:button property="userButton" styleId="userButton" 
	 onclick="openLOVCommon(1503,'poaMasterForm','courtName','','', '','','','lbxCourtNameCode')" 
	 value=" " styleClass="lovbutton"></html:button>
	 </td>
     </tr>
	 <tr>
	 <td>
	 	  <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return fnSearchPOA('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
	 	  <button type="button"  name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="return newAddPOA()" ><bean:message key="button.add" /></button>
    </td>
    </tr>
	</table>
    </fieldset>
 <br/>

<fieldset>
		 <legend><bean:message key="lbl.poaDetail"/></legend> 

  <logic:present name="list">
   <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/poaMasterBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="poaCode" titleKey="lbl.poaCode"  sortable="true"  />
	<display:column property="poaDesc" titleKey="lbl.poaDesc"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.Branchlov"  sortable="true"  />
	<display:column property="courtName" titleKey="lbl.courtName"  sortable="true"  />
	<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.poaCode" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.poaDesc" /> <br> </b>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.branch" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.courtName" /> <br> </b>
									</td>
																		
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.active" /> <br> </b>
									</td>
								</tr>
									<tr class="white2" >
	                                  <td colspan="7"><bean:message key="lbl.noDataFound" /></td>
                                    </tr>
							
			            </table>
						</td>
					</tr>
				</table>
</logic:empty>

  </logic:present>
</fieldset>    
           
	</html:form>		
	<logic:present name="sms">
<script type="text/javascript">
if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}</script>
</logic:present>		
  </body>
</html:html>