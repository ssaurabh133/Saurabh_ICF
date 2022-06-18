<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 28-Nov-2011-->
<!--Purpose  : User Approval Matrix -->
<!--Documentation : -->


<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userApprovalMatrix.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
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

<body onload="enableAnchor();document.getElementById('userApprovalMatrix').dealButton.focus();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<logic:notPresent name="makerAuthor">
<html:form action="/userApprovalMatrixAction" styleId="userApprovalMatrix" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>
<fieldset>
<legend><bean:message key="lbl.userApprovalMatrix"/></legend>
	<table width="100%">
	<tr>
      	<td width="13%">
      		<bean:message key="lbl.userNam" />
      		<logic:notPresent name="search">
      			<span><font color="red">*</font></span>
      		</logic:notPresent>
      	</td>
      	<td width="13%">
      		<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" readonly="true" value="${requestScope.userList[0].userId}"/>
			<html:hidden property="lbxUserId" styleId="lbxUserId" value="${requestScope.userList[0].lbxUserId}"/>
			<logic:present name="search">
				<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(260,'userApprovalMatrix','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</logic:present>
			<logic:notPresent name="search">
			<logic:notPresent name="userList">
				<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(246,'userApprovalMatrix','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</logic:notPresent>
			</logic:notPresent>
      	</td>
      	<td width="13%">
      		<bean:message key="lbl.UserRole" />
      			
      		<logic:notPresent name="search">
      			<span><font color="red">*</font></span>
      		</logic:notPresent>
      	</td>
      	<td width="13%">
      		<logic:notPresent name="userList">
      			<html:select property="role" styleId="role" styleClass="text"  value="" onchange="userRole();">
					<html:option value="" >---Select---</html:option>
					<html:option value="U">Under Writer</html:option>
					<html:option value="P">Policy Approval</html:option>
					
				</html:select>
			</logic:notPresent>	
			 <logic:present name="userList">
				<html:text property="role" styleClass="text" styleId="role" maxlength="100" readonly="true" value="${requestScope.userList[0].role}"/>
			</logic:present>
	  	</td>
	</tr>
	<logic:notPresent name="search">
	<logic:notPresent name="policy">
	<tr id=amount>
	 	<td width="13%"><bean:message key="lbl.amountFrom" /><span><font color="red">*</font></span></td>	
	 	<td width="13%"><html:text property="amountFrom" styleClass="text" style="text-align: right" styleId="amountFrom" value="${requestScope.userList[0].amountFrom}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	  	<td width="13%"><bean:message key="lbl.amountTo" /><span><font color="red">*</font></span></td>	
	 	<td width="13%"><html:text property="amountTo" styleClass="text" style="text-align: right" styleId="amountTo" value="${requestScope.userList[0].amountTo}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
    </tr>
    </logic:notPresent>
    <tr>
    	<td width="13%"><bean:message key="lbl.level" /></td>
      	<td width="13%">
      		<html:select property="level" styleId="level" styleClass="text" value="${requestScope.userList[0].level}" style="text-align: center">
				<logic:present name="pmstSize">
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { %>
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>
		<%} %>
		</logic:present>
			</html:select>	
	  	</td>
	  	<td>Status</td>
	  	<td>
	  		<logic:notPresent name="userList">
	  			<input type=checkbox id="status" name="status" checked="checked"/>
	  		</logic:notPresent>
	  		<logic:present name="userList">
	  			<logic:present name="A">
	  				<input type=checkbox id="status" name="status" checked="checked"/>
	  			</logic:present>
	  			<logic:present name="X">
	  				<input type=checkbox id="status" name="status"/>
	  			</logic:present>
	  		</logic:present>	
	  </td>
	</tr>
	<tr id=forP style="display: none;">
	      <td width="13%"><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="subRuleType" styleId="subRuleType" styleClass="text" value="${requestScope.userList[0].subRuleType}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td> 
	</tr>
	<logic:present name="policy">
	<tr id=forP>
	      <td width="13%"><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="subRuleTypeP" styleId="subRuleTypeP" styleClass="text" value="${requestScope.userList[0].subRuleTypeP}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td> 
	</tr>
	</logic:present>
	</logic:notPresent>
	</table>
	<table width="100%">
	<tr>
		<td>
			<logic:present name="search">
				<button type="button"  name="search" title="Alt+S" accesskey="S"  id="search" class="topformbutton2" onclick="return searchApprovedRecords();"><bean:message key="button.search" /></button>
				<button type="button"  name="add" id="add" title="Alt+A" accesskey="A"  class="topformbutton2" onclick="return addApprovalRecords('N');"><bean:message key="button.add" /></button>
			</logic:present>
			<logic:notPresent name="search">
				<logic:notPresent name="userList">
					<button type="button"  name="save" id="save" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return saveRecords('N');"><bean:message key="button.save" /></button>
				</logic:notPresent>
				<logic:present name="userList">
					<button type="button" name="update" id="update" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return updateRecords('N');"><bean:message key="button.save" /></button>
				</logic:present>
				<button type="button" name="branchAccess" id="branchAccess" class="topformbutton4" title="Alt+B" accesskey="B" onclick="return viewBranchAccess();"><bean:message key="button.viewbranchaccess" /></button>
				<button type="button" name="productAccess" id="productAccess" class="topformbutton4" title="Alt+P" accesskey="P" onclick="return viewProductAccess();"><bean:message key="button.viewproductaccess" /></button>
			</logic:notPresent>
		</td>
	</tr>
	</table>
</fieldset>
</html:form>
</logic:notPresent>

<!-- *******************************************************Maker Author Starts******************************************************* -->
<logic:present name="makerAuthor">
<html:form action="/userApprovalMatrixAction" styleId="userApprovalMatrix" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>
<fieldset>
<legend><bean:message key="lbl.userApprovalMatrix"/></legend>
	<table width="100%">
	<tr>
      	<td width="13%">
      		<bean:message key="lbl.userNam" />
      		<logic:notPresent name="search">
      			<span><font color="red">*</font></span>
      		</logic:notPresent>
      	</td>
      	<td width="13%">
      		<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" readonly="true" value="${requestScope.userList[0].userId}"/>
			<html:hidden property="lbxUserId" styleId="lbxUserId" value="${requestScope.userList[0].lbxUserId}"/>
			<logic:present name="search">
				<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(260,'userApprovalMatrix','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</logic:present>
			<logic:notPresent name="search">
			<logic:notPresent name="userList">
				<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(246,'userApprovalMatrix','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</logic:notPresent>
			</logic:notPresent>
      	</td>
      	<td width="13%">
      		<bean:message key="lbl.UserRole" />
      			
      		<logic:notPresent name="search">
      			<span><font color="red">*</font></span>
      		</logic:notPresent>
      	</td>
      	<td width="13%">
      		<logic:notPresent name="userList">
      			<html:select property="role" styleId="role" styleClass="text"  value="" onchange="userRole();">
					<html:option value="" >---Select---</html:option>
					<html:option value="U">Under Writer</html:option>
					<html:option value="P">Policy Approval</html:option>
					
				</html:select>
			</logic:notPresent>	
			 <logic:present name="userList">
				<html:text property="role" styleClass="text" styleId="role" maxlength="100" readonly="true" value="${requestScope.userList[0].role}"/>
			</logic:present>
	  	</td>
	</tr>
	<logic:present name="search">
	<tr>		
		   <td width="13%"><bean:message key="lbl.searchByStatus"/></td>
		  <td>
		      	<html:select property="statusCase" styleId="statusCase" styleClass="text" value="${requestScope.list[0].statusCase}">
		      		<html:option value="UnApproved">Draft</html:option>
		      		<html:option value="Approved">Approved</html:option>
		      	</html:select>		
	     </td>
	</tr>
	</logic:present>
	<logic:notPresent name="search">
	<logic:notPresent name="policy">
	<tr id=amount>
	 	<td width="13%"><bean:message key="lbl.amountFrom" /><span><font color="red">*</font></span></td>	
	 	<td width="13%"><html:text property="amountFrom" styleClass="text" style="text-align: right" styleId="amountFrom" value="${requestScope.userList[0].amountFrom}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	  	<td width="13%"><bean:message key="lbl.amountTo" /><span><font color="red">*</font></span></td>	
	 	<td width="13%"><html:text property="amountTo" styleClass="text" style="text-align: right" styleId="amountTo" value="${requestScope.userList[0].amountTo}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
    </tr>
    </logic:notPresent>
    <tr>
    	<td width="13%"><bean:message key="lbl.level" /></td>
      	<td width="13%">
      		<html:select property="level" styleId="level" styleClass="text" value="${requestScope.userList[0].level}" style="text-align: center">
				<logic:present name="pmstSize">
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { %>
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>
		<%} %>
		</logic:present>
			</html:select>	
	  	</td>
	  	<td>Status</td>
	  	<td>
	  		<logic:notPresent name="userList">
	  			<input type=checkbox id="status" name="status" checked="checked"/>
	  		</logic:notPresent>
	  		<logic:present name="userList">
	  			<logic:present name="A">
	  				<input type=checkbox id="status" name="status" checked="checked"/>
	  			</logic:present>
	  			<logic:present name="X">
	  				<input type=checkbox id="status" name="status"/>
	  			</logic:present>
	  		</logic:present>	
	  </td>
	</tr>
	<tr id=forP style="display: none;">
	      <td width="13%"><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="subRuleType" styleId="subRuleType" styleClass="text" value="${requestScope.userList[0].subRuleType}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td> 
	</tr>
	<logic:present name="policy">
	<tr id=forP>
	      <td width="13%"><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="subRuleTypeP" styleId="subRuleTypeP" styleClass="text" value="${requestScope.userList[0].subRuleTypeP}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td> 
	</tr>
	</logic:present>
	</logic:notPresent>
	</table>
	<table width="100%">
	<tr>
		<td>
			<logic:present name="search">
				<button type="button"  name="search" title="Alt+S" accesskey="S"  id="search" class="topformbutton2" onclick="return searchApprovedRecords('Y');"><bean:message key="button.search" /></button>
				<button type="button"  name="add" id="add" title="Alt+A" accesskey="A"  class="topformbutton2" onclick="return addApprovalRecords('Y');"><bean:message key="button.add" /></button>
			</logic:present>
			<logic:notPresent name="search">
				<logic:notPresent name="userList">
					<button type="button"  name="save" id="save" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return saveRecords('Y');"><bean:message key="button.save" /></button>
				</logic:notPresent>
				<logic:present name="userList">
					<button type="button" name="update" id="update" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return updateRecords('Y');"><bean:message key="button.save" /></button>
				</logic:present>
				<logic:notPresent name="userList">
					<button type="button" name="forward" id="forward" class="topformbutton2"  title="Alt+F" accesskey="F" onclick="return updateBeforeSave();"><bean:message key="button.forward" /></button>
				</logic:notPresent>
				<logic:present name="userList">
					<button type="button" name="forward" id="forward" class="topformbutton2"  title="Alt+F" accesskey="F" onclick="return forwardRecords();"><bean:message key="button.forward" /></button>
				</logic:present>
				
				<button type="button" name="branchAccess" id="branchAccess" class="topformbutton4" title="Alt+B" accesskey="B" onclick="return viewBranchAccess();"><bean:message key="button.viewbranchaccess" /></button>
				<button type="button" name="productAccess" id="productAccess" class="topformbutton4" title="Alt+P" accesskey="P" onclick="return viewProductAccess();"><bean:message key="button.viewproductaccess" /></button>
			</logic:notPresent>
		</td>
	</tr>
	</table>
</fieldset>
</html:form>
</logic:present>
<!-- *******************************************************Maker Author End******************************************************* -->
<logic:present name="list">
<fieldset><legend><bean:message key="lbl.approvedUser"/></legend> 
<logic:empty name="list">
  <table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.userNam" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.UserRole" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.level" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.subRuleType" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.amountFrom" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.amountTo" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.active" /> <br> </b>
									</td>
									<tr class="white2">
	                                  <td colspan="8"><bean:message key="lbl.noDataFound" /></td>
                                    </tr>
							</tr>
			            </table>
						</td>
					</tr>
				</table> 	
  </logic:empty>
<logic:notEmpty name="list"> 
<logic:notPresent name="searchList">
<logic:present name="list">

	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="/userApprovalMatrixBehindAction.do" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    
		<display:column property="userId" titleKey="lbl.userNam"  sortable="true" />
		<display:column property="role" titleKey="lbl.UserRole"  sortable="true"  />
		<display:column property="level" titleKey="lbl.level"  sortable="true"  style="text-align: center"/>
		<display:column property="subRuleType" titleKey="lbl.subRuleType"  sortable="true"  style="text-align: center" />
		<display:column property="amountFrom" titleKey="lbl.amountFrom"  sortable="true"  style="text-align: right"/>
		<display:column property="amountTo" titleKey="lbl.amountTo"  sortable="true"  style="text-align: right" />
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
	</display:table>
</logic:present>
</logic:notPresent>
<logic:present name="searchList">

	<logic:present name="list">
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="/userApprovalMatrixAction.do?method=searchApprovedRecords" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    
		<display:column property="userId" titleKey="lbl.userNam"  sortable="true" />
		<display:column property="role" titleKey="lbl.UserRole"  sortable="true"  />
		<display:column property="level" titleKey="lbl.level"  sortable="true"  style="text-align: center"/>
		<display:column property="subRuleType" titleKey="lbl.subRuleType"  sortable="true"  style="text-align: center" />
		<display:column property="amountFrom" titleKey="lbl.amountFrom"  sortable="true"  style="text-align: right"/>
		<display:column property="amountTo" titleKey="lbl.amountTo"  sortable="true"  style="text-align: right" />
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
	</display:table>
</logic:present>


</logic:present>
</logic:notEmpty>
</fieldset>
</logic:present>

<logic:present name="makerAuthor">
	<logic:present name="save">
		<script type="text/javascript">	
			alert("<bean:message key="lbl.dataSave" />");
		</script>
	</logic:present>
	<logic:present name="update">
		<script type="text/javascript">	
			alert("<bean:message key="lbl.dataUpdate" />");
		</script>
	</logic:present>
</logic:present>
<logic:notPresent name="makerAuthor">
	<logic:present name="save">
		<script type="text/javascript">	
			alert("<bean:message key="lbl.dataSave" />");
			var contextPath =document.getElementById('contextPath').value ;
			location.href=contextPath+"/userApprovalMatrixBehindAction.do";
			//document.getElementById("userApprovalMatrix").submit();
		</script>
	</logic:present>
	<logic:present name="update">
		<script type="text/javascript">	
			alert("<bean:message key="lbl.dataUpdate" />");
			var contextPath =document.getElementById('contextPath').value ;
			location.href=contextPath+"/userApprovalMatrixBehindAction.do";
			//document.getElementById("userApprovalMatrix").submit();
		</script>
	</logic:present>
</logic:notPresent>
<logic:present name="forward">
	<script type="text/javascript">	
		alert("<bean:message key="msg.disbForwardSuccess" />");
		var contextPath =document.getElementById('contextPath').value ;
		location.href=contextPath+"/userApprovalMatrixBehindAction.do";
	</script>
</logic:present>
<logic:present name="exist">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataExist" />");
		var contextPath =document.getElementById('contextPath').value ;
		location.href=contextPath+"/userApprovalMatrixBehindAction.do";
		//document.getElementById("userApprovalMatrix").submit();
	</script>
</logic:present>

</body>	
</html:html>
	