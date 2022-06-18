<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>

<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		<logic:present name="css">
				<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/rmScript.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>


<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForRm"));
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
<body onload="enableAnchor();document.getElementById('rmChangeForm').dealButton.focus();init_fields();">


<html:form styleId="rmChangeForm"  method="post"  action="/rmChangeBehindAction" >
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.rmChangeAuthor" /></legend>
  <table width="100%">
		<tr>
				<td><bean:message key="lbl.dealNo"/></td>
				<td>	
					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>	
					<html:hidden property="userID" styleId="userID" value="${userID}"/>
					<html:text property="dealNo" styleClass="text" styleId="dealNo" value="${dealNo}" maxlength="100" readonly="true" tabindex="-1" />
					<html:hidden property="lbxDealNo" styleId="lbxDealNo" value="${lbxDealNo}"/>
					<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(386,'rmChangeForm','dealNo','userID','rm', 'userID','','','customerName')" value=" " styleClass="lovbutton"> </html:button>
				</td>
				<td><bean:message key="lbl.customerName"/></td>
				<td><html:text property="customerName" styleClass="text" styleId="customerName" value="${customerName}" maxlength="50" readonly="true" onchange="upperMe('customerName');"/></td> 
		</tr>
       	<tr>
       		<td>
       			<bean:message key="lbl.newRmName"/>
       		</td>
       		<td>
       			<html:hidden property="userID" styleId="userID" value="${userID}"/>
      		    <html:text property="rm" readonly="true" styleId="rm" styleClass="text" value="${rm}" tabindex="-1" />
                <html:button property="rmButton" styleId="rmButton" onclick="closeLovCallonLov1();openLOVCommon(385,'rmChangeForm','rm','userID','', 'userID','','','lbxRM')" value=" " styleClass="lovbutton"> </html:button>
                <html:hidden  property="lbxRM" styleId="lbxRM" value="${lbxRM}"/>
          </td>
       
       	</tr>
       	
	<tr> 
   
    	<td>
    		 <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="fnAuthorSearch();" ><bean:message key="button.search" /></button>
   		</td>
  </tr>
</table>		
</fieldset>

<logic:present name="searchRmAuthor">
 <fieldset>
 	<legend><bean:message key="ldl.rmChangeSearch" /></legend>
 		<logic:notEmpty name="searchRmAuthor">
 			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
     					<display:table  id="searchRmAuthor"  name="searchRmAuthor" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${searchRmAuthor[0].totalRecordSize}" requestURI="/rmChangeDispatchAction.do?method=searchRmChange" >
     						<display:setProperty name="paging.banner.placement"  value="bottom"/>
     						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
     						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	 					<logic:equal value="" property="dealIdForCheckBox" name="searchRmAuthor">
	 							<display:column title="<input type='checkbox' name='allchkd'  id='allchkd' onclick='allChecked();'/>" sortable="false" style="text-align: center"  >
	 							<input type="checkbox" name='chkCases' id='chkCases' value="${searchRmAuthor.dealId}"/>
	 						</display:column>
						</logic:equal>
						<logic:notEqual value="" property="dealIdForCheckBox" name="searchRmAuthor">
	 						<display:column title="<input type='checkbox' name='allchkd'  id='allchkd' onclick='allChecked();'/>" sortable="false" style="text-align: center"  >
	 							<input type="checkbox" name='chkCases' id='chkCases' checked="checked" value="${searchRmAuthor.dealId}"/>
	 						</display:column>
						</logic:notEqual>
	 						<display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
	 						<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	 						<display:column property="product" titleKey="lbl.product" sortable="true"  />
	 						<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	 						<display:column property="rmName" titleKey="lbl.rmname"  sortable="true"  />
	 						<display:column property="newRmName" titleKey="lbl.newRm"  sortable="true"  />
	 						<display:column property="roName" titleKey="lbl.roname"  sortable="true"  />
	 						<display:column property="newRoName" titleKey="lbl.newRo"  sortable="true"  />
	 						<display:column property="userName" titleKey="lbl.maker"  sortable="true"  />
	 						<display:column property="newMaker" titleKey="lbl.newMaker"  sortable="true"  />
	 					</display:table>
					</td>
				</tr>
				<tr>
     				<td>
     					<bean:message key="lbl.decison"/><font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;
         			
         				<html:select property="decison" styleId="decison" styleClass="text" value="">
		 						<option value="A">Approve</option>
<!--								<option value="P">Send Back</option>-->
								<option value="X">Reject</option>
 						</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
 					        
        				<bean:message key="lbl.remark"/><font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;
          				<label><textarea name="textArea" class="text" maxlength="500" id="textArea" cols="70" rows="1"></textarea> </label>&nbsp;&nbsp;&nbsp;&nbsp;
        				<button type="button" name="search" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return rmAuthorRemarks();"><bean:message key="button.save" /></button>
 					</td>
				
				</tr>
			</table>
		</logic:notEmpty>
		<logic:empty name="searchRmAuthor">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
 				<tr>
 					<td class="gridtd">
 						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr class="white2" align="center">
								<td ><input type=checkbox id="allchkd" name=allchkd disabled="disabled"" /></td> 
   								<td ><b><bean:message key="lbl.dealNo" /></b></td>
  								<td ><b><bean:message key="lbl.CustomerName" /></b></td>
  								<td ><b><bean:message key="lbl.product" /></b></td>
  								<td ><b><bean:message key="lbl.scheme" /></b></td>
  								<td ><b><bean:message key="lbl.rmname" /></b></td>
  								<td ><b><bean:message key="lbl.newRm" /></b></td>
  								<td ><b><bean:message key="lbl.roname" /></b></td>
  								<td ><b><bean:message key="lbl.newRo" /></b></td>
  								<td ><b><bean:message key="lbl.maker" /></b></td>
  								<td ><b><bean:message key="lbl.newMaker" /></b></td>
  								
							</tr>
							<tr class="white2">
								<td colspan="11"><bean:message key="lbl.noDataFound" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</logic:empty>
	</fieldset>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>   
</html:form>	
<logic:present name="sms">

		<script type="text/javascript">
				
			if('<%=request.getAttribute("sms").toString()%>'=="P")
			{
				alert("<bean:message key="msg.sendBackSuccessfully" />");
			}
			
			else if('<%=request.getAttribute("sms").toString()%>'=="A")
			{
				alert("<bean:message key="msg.approvedSuccessfully" />");
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="X")
			{
				alert("<bean:message key="msg.Datareject" />");
			}
				
			else if('<%=request.getAttribute("sms").toString()%>'=="E")
			{
				alert("<bean:message key="msg.notepadError" />");
			}
			else
			{
				alert("<bean:message key="lbl.dataExist" />");
			}
	</script>
</logic:present>
</body>
</html:html>