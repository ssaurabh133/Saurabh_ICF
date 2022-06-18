<!--Author Name : Asesh Kumar -->
<!--Date of Creation : 19-Jan-2013-->
<!--Purpose  : Benchmark Ratio Master Search-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/benchMarkRatioMaster.js"></script>

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

<body onload="enableAnchor();document.getElementById('BenchmarkRatioMasterSearch').searchScoringDesc.focus();init_fields();">
<html:form styleId="benchmarkRatioSearchForm" action="/benchMarkRatioAdd" method="post" >
<input type="hidden" name=contextPath id="contextPath" value="<%=request.getContextPath()%>" />
<fieldset>
<legend><bean:message key="lbl.benchmarkRatioMasterSearch" /></legend> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<table width="100%" height="65" border="0" cellpadding="4"
								cellspacing="2">

								<tr>
			<td width="13%">
				<bean:message key="lbl.benchmarkRatioCode" />
			</td>
			<td width="13%">
            <html:text property="benchmarkRatioCode" styleClass="text" styleId="benchmarkRatioCode" maxlength="100" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxRatioSearch" styleId="lbxRatioSearch" />
			<html:button property="ratioButton" styleId="ratioButton" onclick="closeLovCallonLov1();openLOVCommon(498,'benchmarkRatioSearchForm','benchmarkRatioCode','','', '','','','lbxRatioSearch');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	        
			</td>

			<td width="13%">
				<bean:message key="lbl.benchmarIndustry" />
			</td>
			<td width="13%">
			<html:text property="benchmarkIndustryId" styleClass="text" styleId="benchmarkIndustryId" maxlength="100" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxIndustrySearch" styleId="lbxIndustrySearch" />
			<html:button property="industryButton" styleId="industryButton" onclick="openLOVCommon(499,'benchmarkRatioSearchForm','benchmarkIndustryId','','', '','','','lbxIndustrySearch');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	        
									</td>
								</tr>

								<tr>
									<td>
										<button type="button" id="save" title="Alt+S" accesskey="S"
											class="topformbutton2"
											onclick="return fnSearchBenchMark('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
										<button type="button" name="add" id="add" title="Alt+A" accesskey="A" class="topformbutton2" onclick="return newAdd();"><bean:message key="button.add" /></button>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
</fieldset>

<br/>
<fieldset>
			<legend>
				<bean:message key="lbl.benchmarkRatioMasterDtl" />
			</legend>

			<logic:present name="BenchmarkRatioMasterSearchList">
				<display:table id="BenchmarkRatioMasterSearchList" name="BenchmarkRatioMasterSearchList" style="width: 100%"
					class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
					partialList="true" size="${BenchmarkRatioMasterSearchList[0].totalRecordSize}"
					requestURI="/benchmarkRatioBehind.do">
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:column property="ratioCodeModify" titleKey="lbl.ratioCode"  sortable="true"  />
					<display:column property="benchmarkRatioCode" titleKey="lbl.benchmarkRatio" sortable="true" />
					<display:column property="benchmarkIndustryId" titleKey="lbl.benchmarkIndustry" sortable="true" />
					<display:column property="recStatus" style="text-align: center" titleKey="lbl.status" sortable="true" />

				</display:table>
			</logic:present>

			<logic:notPresent name="BenchmarkRatioMasterSearchList">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
								
								<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.ratioCode" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.benchmarkRatio" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.benchmarIndustry" /> <br> </b>
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
  <div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
		</html:html>