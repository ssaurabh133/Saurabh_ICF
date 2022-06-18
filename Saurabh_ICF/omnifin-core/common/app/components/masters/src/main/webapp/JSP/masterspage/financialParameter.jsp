<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 14-Oct-2011-->
<!--Purpose  : Information of Financial Parameter-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page language="java" import="java.util.*" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/financialPrameterMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

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

<body onload="enableAnchor();document.getElementById('financialPramForm').pramSearchCode.focus();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/finPramMasterSearch" styleId="financialPramForm" method="post" > 	
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.financailPram"/></legend>
	<table width="100%">
	
	<tr>
      <td width="13%"><bean:message key="lbl.pramCode" /></td>
      <td width="13%"><html:text property="pramSearchCode" styleClass="text" styleId="pramSearchCode" maxlength="20" /></td>
      
       <td width="13%"><bean:message key="lbl.pramName"/></td>
       <td width="13%"><html:text property="pramSearchName" styleClass="text" styleId="pramSearchName" maxlength="50" onblur="fnChangeCase('financialPramForm','pramSearchName')" /></td>
      
       </tr>
       <tr>
        <td><bean:message key="lbl.pramType"/></td>
              <td><html:select property="pramTypeSearch" styleId="pramTypeSearch" styleClass="text" >
                   <html:option value="">SELECT</html:option>
               		<html:option value="B">BALANCE SHEET</html:option>
               		 <html:option value="P">PROFIT AND LOSS</html:option>
               		 <html:option value="O">OTHERS</html:option>
               		 <html:option value="I">INDIVIDUAL ANALYSIS</html:option>
      		 </html:select></td>
       </tr>
	 <tr>
	 <td>
	 <html:hidden property="path" styleId="path" value="<%=request.getContextPath()%>"/>
	 	  <button type="button" id="save" title="Alt+S" accesskey="S" class="topformbutton2" onclick="return fnpramSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
	 	  <button type="button" name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="return finAdd()"  ><bean:message key="button.add" /></button></td>
	
	</tr>
	 </table>
</fieldset>

<br/>
<fieldset>
		 <legend><bean:message key="lbl.financailDetail"/></legend> 


  <logic:present name="list">
  <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/financialPramBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="pramCodeModify" titleKey="lbl.pramCode"  sortable="true"  />
	<display:column property="pramName" titleKey="lbl.pramName"  sortable="true"  />
	<display:column property="pramType" titleKey="lbl.pramType"  sortable="true"  />
	<display:column property="subType" titleKey="lbl.subType"  sortable="true"  />
	<display:column property="sequenceNo" titleKey="lbl.sequenceNo"  sortable="true"  />
	<display:column property="pramStatus" titleKey="lbl.active"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.pramCode" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.pramName" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.pramType" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.subType" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.sequenceNo" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.active" /> <br> </b>
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
  </logic:present>

</fieldset>   
    
           
	</html:form>		
	<logic:present name="sms">
<script type="text/javascript">

    
	
	
	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
	
	
	
</script>
</logic:present>	
  </body>
		</html:html>