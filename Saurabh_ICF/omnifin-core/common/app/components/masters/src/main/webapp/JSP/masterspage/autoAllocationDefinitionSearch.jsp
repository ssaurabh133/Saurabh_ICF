<!--Author Name : Ravindra-->
<!--Date of Creation : 18-Oct-2011-->
<!--Purpose  : auto Allocation Definition search-->
<!--Documentation : -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>


<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/autoAllocationDefinition.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
%>

</head>

<body onload="enableAnchor();document.getElementById('autoAllocationDefinitionSearch').npaStageButton.focus();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/autoAllocationDefinitionSearchBehind" styleId="autoAllocationDefinitionSearch" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.autoAllocationDefinitionSearch"/></legend>
	<table width="100%">
	<tr>
      	<td width="13%">
      		<bean:message key="lbl.loanStage" />
      	</td>
      	<td width="13%">
      		
      		<html:text property="npaStage" styleClass="text" styleId="npaStage" maxlength="100" readonly="true" value=""/>
			<html:hidden property="lbxNPAStageId" styleId="lbxNPAStageId" value=""/>
			<html:hidden property="hid" styleId="hid" value=""/>
			<html:button property="npaStageButton" styleId="npaStageButton" tabindex="1" onclick="openLOVCommon(465,'autoAllocationDefinitionSearch','npaStage','','', '','','','hid')" value=" " styleClass="lovbutton"  ></html:button>
      	</td>
      	<td width="13%">
      		<bean:message key="lbl.allocationType" /></span>
      	</td>
      	<td width="13%">
      			<html:select property="type" styleId="type" styleClass="text"  value="">
					<html:option value="" >---Select---</html:option>
					<html:option value="D">Date First</html:option>
					<html:option value="C">Charge First</html:option>
					
				</html:select>
	  	</td>
	</tr>
	
	<tr> 
     <td>
     	  <button type="button"  id="save" title="Alt+S" accesskey="S" class="topformbutton2" onclick="return autoAllocationSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
            <button type="button"  name="button2" id="add" title="Alt+A" accesskey="A" class="topformbutton2" onclick="return newAdd();"><bean:message key="button.add" /></button></td>
  </tr>
	
	</table>
	
</fieldset>
</html:form>

<logic:present name="list">
<fieldset><legend><bean:message key="lbl.autoAllocationDef"/></legend> 
<logic:empty name="list">
  <table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.loanStage" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.type" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.makerId" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.Status" /> <br> </b>
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
<logic:notEmpty name="list"> 
	<logic:present name="list">

	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="/autoAllocationDefinitionSearchBehind.do" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	 <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
         <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    
		<display:column property="autoAllocationDefModify" titleKey="lbl.loanStage"  sortable="true"  />
		<display:column property="type" titleKey="lbl.allocationType"  sortable="true"  />
		<display:column property="repayType" titleKey="lbl.repayType"  sortable="true"  />
		<display:column property="allocationStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center"/>
		<display:column property="makerId" titleKey="lbl.makerId"  sortable="true"  style="text-align: center" />
	</display:table>
		
</logic:present>
</logic:notEmpty>
</fieldset>
</logic:present>

</body>	
</html:html>
	