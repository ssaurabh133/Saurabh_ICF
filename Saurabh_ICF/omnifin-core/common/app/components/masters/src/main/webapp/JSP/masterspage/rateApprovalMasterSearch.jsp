<!--Author Name : Manish Shukla-->
<!--Date of Creation : jun-2013-->
<!--Purpose  : Rate Aproval Matrix Master-->
<!--Documentation : -->

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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/rateApprovalMaker.js"></script>
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

<body onload="enableAnchor();" >
<html:form action="/rateMatrixMakerDispatchAction" styleId="rateApprovalMakerForm" method="post" > 	
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.rateApprovalMatrix"/></legend>
	<table width="100%">
	
    <tr>
		
	<td>
		<bean:message key="lbl.rateApprovalProduct"/>  </td>
	<td > 
		 <html:text  property="product" styleId="product" styleClass="text" readonly="true" tabindex="-1"/>
         <html:hidden  property="lbxProductID" styleId="lbxProductID" />
         <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(2017,'rateApprovalMakerForm','product','','scheme','lbxScheme','','','lbxProductID');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	</td>
	<td> <bean:message key="lbl.rateApprovalScheme"/>  </td>
	<td > 
	      <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          <html:hidden  property="lbxScheme" styleId="lbxScheme" value="" />

    		 <html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('product');openLOVCommon(2018,'rateApprovalMakerForm','scheme','product','', 'lbxProductID','Please Select Product','','lbxScheme');closeLovCallonLov('scheme');" value=" " styleClass="lovbutton"></html:button>
   </td>
	     
	</tr>
      <tr>
	
		<td><bean:message key="lbl.searchByStatus" /><span><font color="red">*</font></span></td>
		
		 <td>
		<html:select property="searchByStatus" styleId="searchByStatus" styleClass="text" value="${searchByStatus}" >
			<html:option value="P">Draft</html:option>
			<html:option value="A">Approved</html:option>
		</html:select> 
		</td>  
	
	
	</tr>     
    </table>
	<table>	
    
	 <tr>
	 <td>
	 	  <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchRateApprovalMaker('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
	 	  <button type="button"  name="openadd" id="openadd" class="topformbutton2" title="Alt+N" accesskey="N" onclick="addRateApprovalMaker()" ><bean:message key="button.add" /></button></td>
	
	</tr>
	 </table>
</fieldset>

<br/>

<fieldset>
		 <legend><bean:message key="lbl.rateApprovalMatrix"/></legend> 

  <logic:present name="list">
   <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/rateMatrixMakerDispatchAction.do?method=rateApprovalMakerSearch" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="rateApprovalId" titleKey="lbl.rateApprovalId"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
 	<display:column property="rackRate" titleKey="lbl.rackRate"  sortable="true"  />
 	<display:column property="rackProcessingFee" titleKey="lbl.rackProcessingFee"  sortable="true"  />	
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;" align="center">
										<strong><bean:message key="lbl.rateApprovalId" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.product" /> <br> </b>
									</td>
									
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.scheme" /> <br> </b>
									</td>
																
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.rackRate" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.rackProcessingFee" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.active" /> <br> </b>
									</td>
									<tr class="white2" >
	                                  <td colspan="8"><bean:message key="lbl.noDataFound" /></td>
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