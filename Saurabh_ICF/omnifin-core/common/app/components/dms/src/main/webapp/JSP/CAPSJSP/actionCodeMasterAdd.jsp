<!--Author Name : Anil Yadav-->
<!--Date of Creation :13 Oct 2011 -->
<!--Purpose  : Information of Collection Application Master Add-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="/JSP/sessioncheck.jsp"%>
<html:html>
<head>

	<title><bean:message key="a3s.noida" />
	</title>

	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/capsScript/actionCodeScript.js"></script>


	<link type="text/css" 	href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('actionCodeMasterForm').codeDesc.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('actionCodeMasterForm').codeId.focus();
     }
     return true;
}
</script>

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

<body onload="enableAnchor();fntab();init_fields();">


	<html:form styleId="actionCodeMasterForm" method="post"
		action="/actionCodeMasterAdd" >
		<html:javascript formName="ActionCodeMasterAddDyanavalidatiorForm" />
		<fieldset>
			<legend>
				<bean:message key="lbl.actionCodeMaster" />
			</legend>
			<table cellpadding="0" cellspacing="1" width="100%">


				<logic:present name="editVal">
				<logic:notEmpty name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="M" />
					<logic:iterate id="listObj" name="list">
						<tr>
							<td>
								<bean:message key="lbl.actionCode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="codeId" styleClass="text" styleId="codeId"
									maxlength="10" readonly="true" value="${listObj.codeId}" />
							</td>

							<td>
								<bean:message key="lbl.actionDescription" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="codeDesc" styleClass="text"
									styleId="codeDesc" maxlength="50"
									onblur="fnChangeCase('actionCodeMasterForm','codeDesc')"
									value="${listObj.codeDesc}" />
							</td>

						</tr>
						<tr>
							<td>
								<bean:message key="lbl.active" />
							</td>
							<td>
								<logic:equal value="Active" name="status">
									<input type="checkbox" name="codeStatus" id="codeStatus"
										checked="checked" />
								</logic:equal>
								<logic:notEqual value="Active" name="status">
									<input type="checkbox" name="codeStatus" id="codeStatus" />
								</logic:notEqual>
							</td>

						</tr>
					</logic:iterate>
					</logic:notEmpty>
				</logic:present>

				<logic:notPresent name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="I" />
					<tr>
						<td>
							<bean:message key="lbl.actionCode" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="codeId" styleClass="text" styleId="codeId"
								maxlength="10"
								onblur="fnChangeCase('actionCodeMasterForm','codeId')" />
						</td>

						<td>
							<bean:message key="lbl.actionDescription" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="codeDesc" styleClass="text"
								styleId="codeDesc" maxlength="50"
								onblur="fnChangeCase('actionCodeMasterForm','codeDesc')" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.active" />
						</td>
						<td>
							<input type="checkbox" name="codeStatus" id="codeStatus"
								checked="checked" />
						</td>

					</tr>

				</logic:notPresent>

				<tr>
					<td>
						<logic:present name="editVal">
							
								 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return fnEditActionCode('${list[0].codeId }');" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
								
						</logic:present>

						<logic:notPresent name="editVal">
						<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
						 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveActionCode();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

						</logic:notPresent>
					</td>
				</tr>
				
 
 


			</table>


		</fieldset>


	</html:form>

	<logic:present name="sms">
		<script type="text/javascript">

    
			if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("actionCodeMasterForm").action="actionCodeMasterBehind.do";
	    document.getElementById("actionCodeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("actionCodeMasterForm").action="actionCodeMasterBehind.do";
	    document.getElementById("actionCodeMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	
</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>