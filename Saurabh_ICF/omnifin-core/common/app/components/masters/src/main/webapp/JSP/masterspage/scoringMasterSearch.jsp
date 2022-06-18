<!--Author Name : Arun Kumar MIshra-->
<!--Date of Creation : 01-Nov-2012-->
<!--Purpose  : Scoring Master Search-->
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

<body onload="enableAnchor();document.getElementById('scoringMasterSearch').searchScoringDesc.focus();init_fields();">
<html:form  styleId="scoringMasterSearch" method="post" action="/scoringMasterBehind">
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<fieldset>
<legend><bean:message key="lbl.scoringMasterSearch" /></legend> 
	<table width="100%">
	
	<tr>
     <td width="13%"><bean:message key="lbl.scoringDesc"/></td>
     <td width="13%">
     <html:text property="searchScoringDesc" styleId="searchScoringDesc" value="" styleClass="text" maxlength="50" />
      </td>
      
    <td width="13%"></td>
     <td width="13%">
   
      </td>
    </tr>

 <tr> 
     <td>
   	  <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchScoring();" ><bean:message key="button.search" /></button>
     <button type="button" name="button2"   id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="openAdd();" ><bean:message key="button.add" /></button></td>
  </tr>
	 </table>
</fieldset>

<br/>
<fieldset>
			<legend>
				<bean:message key="lbl.scoringMasterDtl" />
			</legend>

			<logic:present name="scoringMasterSearchList">
				<display:table id="scoringMasterSearchList" name="scoringMasterSearchList" style="width: 100%"
					class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
					partialList="true" size="${scoringMasterSearchList[0].totalRecordSize}"
					requestURI="/actionCodeMasterBehind.do">
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:setProperty name="locale.resolver"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:setProperty name="locale.provider"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:column property="scoringId" titleKey="lbl.scoringDesc" sortable="true" />
					<display:column property="product" titleKey="lbl.product" sortable="true" />
					<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
					<display:column property="recStatus" style="text-align: center" titleKey="lbl.status"
						sortable="true" />

				</display:table>
			</logic:present>

			<logic:notPresent name="scoringMasterSearchList">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.scoringDesc" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.product" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.scheme" /> <br> </b>
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
		</html:html>