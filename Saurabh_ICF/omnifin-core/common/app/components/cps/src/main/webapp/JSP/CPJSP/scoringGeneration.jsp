<!--Author Name : Arun Kumar MIshra-->
<!--Date of Creation : 01-Nov-2012-->
<!--Purpose  : Scoring Generation-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/scoringMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
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

<body onload="enableAnchor();generateJSPView();init_fields();">
<html:form  styleId="scoringDtl" method="post" action="/scoringMasterBehind">
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
<br/>
<!--<fieldset>-->
<!--			<legend>-->
<!--				<bean:message key="lbl.scoringDtl" />-->
<!--			</legend>-->
<!---->
<!---->
<!--			<table width="100%" border="0" cellpadding="0" cellspacing="1">-->
<!--					<tr>-->
<!--						<td class="gridtd">-->
<!--							<table width="100%" border="0" cellpadding="4" cellspacing="1">-->
<!--								<tr class="white2">-->
<!--									<td >-->
<!--										<strong><bean:message key="lbl.scoringParamName" /> </strong>-->
<!--									</td>-->
<!--									-->
<!--									<td >-->
<!--										<b><bean:message key="lbl.applicationValue" /> <br> </b>-->
<!--									</td>-->
<!--									<td >-->
<!--										<b><bean:message key="lbl.scoringWeightage" /> <br> </b>-->
<!--									</td>-->
<!--									<td >-->
<!--										<b><bean:message key="lbl.score" /> <br> </b>-->
<!--									</td>-->
<!--									<td>-->
<!--										<b><bean:message key="lbl.weightage" /> <br> </b>-->
<!--									</td>-->
<!--									-->
<!--							</tr>-->
<!--							<logic:present name="scoringDtlList">-->
<!--							<logic:iterate id="scoringDtlListObj" name="scoringDtlList">-->
<!--							<tr class="white1">-->
<!--							<td>${scoringDtlListObj.scoringParamName}</td>-->
<!--							<td>${scoringDtlListObj.applicationValue}</td>-->
<!--							<td>${scoringDtlListObj.scoringWeightage}</td>-->
<!--							<td>${scoringDtlListObj.score}</td>-->
<!--							<td>${scoringDtlListObj.weightage}</td>-->
<!--							</tr>-->
<!--							</logic:iterate>-->
<!--							</logic:present>-->
<!--							<logic:notPresent name="scoringDtlList">-->
<!--							<tr class="white2" >-->
<!--	                         <td colspan="7"><bean:message key="lbl.noDataFound" /></td>-->
<!--                          </tr>-->
<!--                          </logic:notPresent >-->
<!--			            </table>-->
<!--						</td>-->
<!--					</tr>-->
<!--				</table>-->
<!--				-->
<!--		</fieldset> -->
<!--   <script>-->
<!--	setFramevalues("scoringDtl");-->
<!--</script>        -->
	</html:form>		

  </body>
		</html:html>