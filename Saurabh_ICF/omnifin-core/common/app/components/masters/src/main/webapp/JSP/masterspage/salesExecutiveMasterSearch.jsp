

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
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/salesExecutiveMaster.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
                			<body onload="enableAnchor();">
							<html:form styleId="salesExecutiveMaster" action="/salesExecutiveMaster" method="post" >
							<input type="hidden" name=contextPath id="contextPath" value="<%=request.getContextPath()%>" />
							<fieldset>
							<legend><bean:message key="lbl.salesExecutiveMaster" /></legend> 
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td>
							<table width="100%" height="65" border="0" cellpadding="4"	cellspacing="2">
							
						
						    <tr>
						    <td><bean:message key="lbl.name"/></td>
   		                    <td>
   		 
   		  					<html:text property="businessPartnerName" styleId="businessPartnerName" styleClass="text" readonly="true" value="${requestScope.list[0].businessPartnerName}"/>
   		 					<html:hidden  property="lbxBusinessPartnerId" styleId="lbxBusinessPartnerId" value="${requestScope.list[0].lbxBusinessPartnerId}"/>
          					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(2052,'salesExecutiveMasterForm','businessPartnerName','','', '','','','businessPartnerName','businessPartnerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>									
    	   		  
    	   		  			</td>
    	   		  
    	   		  
    	   		  
         					
	       					
                             <td>
			                 <bean:message key="lbl.type" />
		                     </td>
	                          <td>
			                 <html:select property="employeeType" value="${requestScope.list[0].employeeType}" styleClass="text" styleId="employeeType">
				             <html:option value="">--Select--</html:option>
				             <logic:present name="typeList">
					         <logic:notEmpty name="typeList">
					         <html:optionsCollection name="typeList" label="name" value="id" />
					         </logic:notEmpty>		
				             </logic:present>
				             </html:select>
                             </td>

 	   						
			                </tr>
				            <tr>
						    <td><bean:message key="lbl.employeeName"/></td>
                            <td><html:text property="employeeName" styleId="employeeName" styleClass="text" maxlength="50" value=""/></td>
                            
                            <td><bean:message key="lbl.bankAccountNo"/></td>
    	    				<td><html:text property="bankAccountNo" styleId="bankAccountNo" styleClass="text"  value="${requestScope.list[0].bankAccountNo}"/></td>
	    					</tr>
	                        	
			                <tr>
						    <td>
						      <button type="button" id="searchButton" title="Alt+S" accesskey="S"class="topformbutton2"onclick="return searchSalesExecutive();" ><bean:message key="button.search" /></button>
						     <button type="button" name="add" id="add" title="Alt+A" accesskey="A" class="topformbutton2" onclick="return salesAddd();"><bean:message key="button.add" /></button>
						    </td>
						    </tr>
						
							</table>
							       
							
							
						 </td>
						 </tr>
						 </table> 
				  

</fieldset>

             <fieldset>
			<legend>
				<bean:message key="lbl.salesExecutiveMasterDetails" />
			</legend>

			<logic:present name="SalesExecutiveList">
				<display:table id="SalesExecutiveList" name="SalesExecutiveList" style="width: 100%"
					class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
					partialList="true" size="${SalesExecutiveList[0].totalRecordSize}"
					requestURI="//salesExecutiveMaster.do">
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:column property="bpEmpUniqueId" titleKey="lbl.bpEmployeeId"  sortable="true"  />
					<display:column property="employeeName" titleKey="lbl.employeeName"  sortable="true"  />
					<display:column property="businessPartnerName" titleKey="lbl.name"  sortable="true"  />
					<display:column property="bank" titleKey="lbl.bankName"  sortable="true"  />
					<display:column property="branch" titleKey="lbl.bankBranchName"  sortable="true"  />
					<display:column property="recStatus" titleKey="lbl.status"  sortable="true"  />

				</display:table>
			</logic:present>

			<logic:notPresent name="SalesExecutiveList">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
								<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.bpEmployeeId" /> </strong>
									</td>
								<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.employeeName" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.name" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.bankName" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.bankBranchName" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.status" /> <br> </b>
									</td>
									<tr class="white2" >
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
  <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>		

		</html:html>