
<!--Author Name : Anil Yadav-->
<!--Date of Creation : 28 November 2011  -->
<!--Purpose  : U/W Approval Level Def Master Search-->
<!--Documentation : -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
%>
<html:html>
<script type="text/javascript">
function fntab()
{
   	 document.getElementById('approvalLevelDefSearchForm').productButton.focus();
     return true;
}
</script>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/approvalLevelDefScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
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
<body onload="enableAnchor();fntab();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="approvalLevelDefSearchForm"  method="post"  action="/approvalLevelDefSearchBehind" >

<html:errors/>
<fieldset>
<legend><bean:message key="lbl.approvalLevelMaster" /></legend>

<logic:present name="makerCheckerFlag" >
<table>
  <tr>  
  <td width="13%"><bean:message key="lbl.product2" /></td>
  
      <td width="13%" >
        <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
        <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
		<html:text property="productSearch" styleId="product" styleClass="text" value=""	readonly="true" tabindex="-1" />
		<input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(89,'approvalLevelDefSearchForm','product','','','','','','lbxProductID')" value=" " tabindex="26" name="dealButton">
		<input type="hidden" name="lbxProductID" id="lbxProductID" value=""/>
	  </td>

	<td width="13%"><bean:message key="lbl.schemeLOV"/></td>
         
      <td width="20%">
            
		<html:text property="scheme" styleId="scheme" styleClass="text" value="" readonly="true" tabindex="-1"/> 
		<html:hidden property="lbxSchemeId" styleId="lbxSchemeId" value="" />		
		<html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(287,'approvalLevelDefSearchForm','scheme','product','lbxSchemeId', 'lbxProductID','Please firstly select Product then Scheme','','lbxSchemeId');" value=" " styleClass="lovbutton"> </html:button>           						    
      </td>
      
     </tr>
     <tr>
   <td width="13%"><bean:message key="lbl.approvalLevel" /></td>
      <td width="13%" ><html:select property="findApprovalSearch" styleClass="text" value="${requestScope.Vo.findApprovalSearch}" styleId="findApprovalLevel">
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
   </td>
   
   <td><bean:message key="lbl.searchByStatus" /></td>
   <td>
   <html:select property="searchStatus" styleClass="text"  styleId="searchStatus" >
   		<html:option value="D">Draft</html:option>
   		<html:option value="A">Approved</html:option>   
   </html:select>
   </td>
   
  </tr>
    
    <tr> 
    <td>
     <button type="button"  id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return SearchApprovalLevel('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
    <button type="button" name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="AddApprovalLevel('Y');"><bean:message key="button.add" /></button></td>
  </tr>
</table>



</logic:present>
<logic:notPresent name="makerCheckerFlag"  >

<table>
  <tr>  
  <td width="13%"><bean:message key="lbl.product2" /></td>
      <td width="13%" >
      <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
							<html:text property="productSearch" styleId="product" styleClass="text" value=""	readonly="true" tabindex="-1" />
							<input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(89,'approvalLevelDefSearchForm','product','','','','','','lbxProductID')" value=" " tabindex="26" name="dealButton">
							<input type="hidden" name="lbxProductID" id="lbxProductID" value=""/></td>
							
	<td width="13%"><bean:message key="lbl.schemeLOV"/></td>
            <td width="20%">
            
             <html:text property="scheme" styleId="scheme" styleClass="text" value="" readonly="true" tabindex="-1"/> 
          		<html:hidden property="lbxSchemeId" styleId="lbxSchemeId" value="" />
          		
          		 <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(287,'approvalLevelDefSearchForm','scheme','product','lbxSchemeId', 'lbxProductID','Please firstly select Product then Scheme','','lbxSchemeId');" value=" " styleClass="lovbutton"> </html:button>
               
           						    
                </td>
     </tr>
     <tr>
   <td width="13%"><bean:message key="lbl.approvalLevel" /></td>
      <td width="13%" ><html:select property="findApprovalSearch" styleClass="text" value="${requestScope.Vo.findApprovalSearch}" styleId="findApprovalLevel">
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
   </td>
  </tr>


<!---->
<!--    <tr> -->
<!--  -->
<!--    <td width="13%"><bean:message key="lbl.amountFrom" /></td>-->
<!--    <td width="13%" ><html:text property="amountFromSearch" styleClass="text" styleId="amountFrom" maxlength="10" /></td>-->
<!--    -->
<!--    <td width="13%"><bean:message key="lbl.amountTo" /></td>-->
<!--    <td width="13%" ><html:text property="amountToSearch" styleClass="text" styleId="amountTo" maxlength="10" /></td>-->
<!--        -->
<!--     </tr>-->

     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <tr> 
    <td>
     <button type="button"  id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return SearchApprovalLevel('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
    <button type="button" name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="AddApprovalLevel();"><bean:message key="button.add" /></button></td>
  </tr>
</table>

</logic:notPresent>
</fieldset>
<br/>
<fieldset>
<legend><bean:message key="lbl.userDetail" /></legend>

  <logic:present name="list">
  <logic:notEmpty name="list">
	  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/approvalLevelDefSearchBehind.do" >
	    <display:setProperty name="paging.banner.placement"  value="bottom"/>
	    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="approvalLevelID" titleKey="lbl.approvalLevelID"  sortable="true"  />
		<display:column property="productSearch" titleKey="lbl.product2"  sortable="true"  />
		<display:column property="scheme" titleKey="lbl.schemeforSearch"  sortable="true"  />
		<display:column property="findApprovalLevel" titleKey="lbl.approvalLevel"  style="text-align: center" sortable="true"  />
		<display:column property="amountFrom" titleKey="lbl.amountFrom"  style="text-align: right" sortable="true"  />
		<display:column property="amountTo" titleKey="lbl.amountTo"  style="text-align: right" sortable="true"  />
		<display:column property="status" titleKey="lbl.active"  style="text-align: center" sortable="true"  />
		
	</display:table>
</logic:notEmpty>
  </logic:present>
  <logic:notPresent name="list">
  <table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.approvalLevelID" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.product2" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.schemeforSearch" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.approvalLevel" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.amountFrom" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.amountTo" /> <br> </b>
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
  </logic:notPresent>
</fieldset>    
           
	</html:form>		
  </body>
		</html:html>