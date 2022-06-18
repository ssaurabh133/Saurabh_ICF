<%@ page session="true"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
	<%
		ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");

			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
	%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/scoringScript.js"></script>		
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	
</head>

<body onload="enableAnchor();">
	<html:form action="/parameterScoreDef" styleId="ParameterScoreDef" method="post">
	
		<logic:present name="save">	
		<script type="text/javascript">

    if('<%=request.getAttribute("save").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");		
	}
	if('<%=request.getAttribute("save").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataError" />");		
	}
	</script>	
	</logic:present>
			
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<logic:present name="notshowLov">
		<fieldset>
			<legend>
				<bean:message key="lbl.parameterDesc" />
			</legend>
			<table width="100%">	
						
				<tr>
					<td>
						<bean:message key="lbl.scoreCardID" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="scoreCardId" value="${parameterScore[0].scardid}" readonly="true" styleClass="text" styleId="scoreCardId"/>
<!--				      <html:button styleClass="lovbutton" styleId="scoreButton" onclick="openLOVCommon(243,'ParameterScoreDef','scoreCardId','','lbxScoreDesc', '','','','description','profit');" value=" " tabindex="1" property="dealButton">-->
<!--				      </html:button>-->
				      <html:hidden property="lbxScoreDesc" styleId="lbxScoreDesc"/>
     					
				     </td>
				     <td>
						<bean:message key="lbl.parameterDescription" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="description" value="${parameterScore[0].sdescription}" readonly="true" styleClass="text" styleId="description"/>
				     </td></tr>
				     <tr>
				      <td>
						<bean:message key="lbl.parameterProfit" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="profit" value="${parameterScore[0].sproduct}" readonly="true" styleClass="text" styleId="profit"/>
				     </td> 
				</tr>
			</table>
		</fieldset>
		
		<fieldset>
			<legend>
				<bean:message key="lbl.parameterinfo" />
			</legend>
			<table width="100%">			
				<tr>
					<td>
						<bean:message key="lbl.parameterScoreCode" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="parameterCode" readonly="true" styleClass="text" styleId="parameterCode"/>
<!--				      <html:button styleClass="lovbutton" styleId="scoreButton" onclick="openLOVCommon(244,'ParameterScoreDef','parameterCode','','', '','','changeParameterType','type');" value=" " tabindex="1" property="dealButton">-->
<!--				      </html:button>-->
				     </td>
				        <td>
						<bean:message key="lbl.parameterScoreType" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="type" readonly="true" styleClass="text"  styleId="type"></html:text>
				     
				     </td>
				     </tr><tr>
				     <td>
					<bean:message key="lbl.parameterScore" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="score"  onkeypress="return isNumberKey(event);" styleClass="text" styleId="score"/>
				     </td>	
				     <td id="paramchar">
						<bean:message key="lbl.parameterScoreCharecter" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="charecter"  onkeypress="return isCharKey(event);" styleClass="text" styleId="charecter"/>
				     </td>				  
				    
					
				       </tr>
				       <tr>
					   <td id="nfrom">
						<bean:message key="lbl.parameterScoreNfrom" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="numericFrom"  onkeypress="return isNumberKey(event);" styleClass="text" styleId="numericFrom"/>
				     </td>
					<td id="nto">
						<bean:message key="lbl.parameterScoreNTo" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="numericTo"  onkeypress="return isNumberKey(event);" styleClass="text" styleId="numericTo"/>
				     </td>		
				      
				   
				</tr>
				<tr>
				<td>
				<button class="topformbutton2" type="button" onclick="return editRow();" accesskey="E" title="Alt+E" ><bean:message key="button.edit" /></button>
				</td>
				</tr>				
			</table>
			
		</logic:present>
		<logic:notPresent name="notshowLov">
		
				
		<fieldset>
			<legend>
				<bean:message key="lbl.parameterDesc" />
			</legend>
			<table width="100%">	
						
				<tr>
					<td>
						<bean:message key="lbl.scoreCardID" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="scoreCardId" value="${parameterScore[0].scardid}" readonly="true" styleClass="text" styleId="scoreCardId"/>
				      <html:button styleClass="lovbutton" styleId="scoreButton" onclick="openLOVCommon(243,'ParameterScoreDef','scoreCardId','','lbxScoreDesc', '','','','description','profit');" value=" " tabindex="1" property="dealButton">
				      </html:button>
				      <html:hidden property="lbxScoreDesc" styleId="lbxScoreDesc"/>
     					
				     </td>
				     <td>
						<bean:message key="lbl.parameterDescription" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="description" value="${parameterScore[0].sdescription}" readonly="true" styleClass="text" styleId="description"/>
				     </td></tr>
				     <tr>
				      <td>
						<bean:message key="lbl.parameterProfit" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="profit" value="${parameterScore[0].sproduct}" readonly="true" styleClass="text" styleId="profit"/>
				     </td> 
				</tr>
			</table>
		</fieldset>
		
		<fieldset>
			<legend>
				<bean:message key="lbl.parameterinfo" />
			</legend>
			<table width="100%">			
				<tr>
					<td>
						<bean:message key="lbl.parameterScoreCode" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="parameterCode" readonly="true" styleClass="text" value="${Paramdata[0].parameterCode}"  styleId="parameterCode"/>
				      <html:button styleClass="lovbutton" styleId="scoreButton" onclick="openLOVCommon(244,'ParameterScoreDef','parameterCode','','', '','','changeParameterType','type');" value=" " tabindex="1" property="dealButton">
				      </html:button>
				     </td>
				        <td>
						<bean:message key="lbl.parameterScoreType" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="type" readonly="true" styleClass="text" value="${Paramdata[0].type}"  styleId="type"></html:text>
				     
				     </td>
				     </tr><tr>
				     <td>
					<bean:message key="lbl.parameterScore" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="score" value="${Paramdata[0].score}" onkeypress="return isNumberKey(event);" styleClass="text" styleId="score"/>
				     </td>	
				     <td id="paramchar">
						<bean:message key="lbl.parameterScoreCharecter" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="charecter" value="${Paramdata[0].charecter}"  onkeypress="return isCharKey(event);" styleClass="text" styleId="charecter"/>
				     </td>				  
				    
					
				       </tr>
				       <tr>
					   <td id="nfrom">
						<bean:message key="lbl.parameterScoreNfrom" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="numericFrom" value="${Paramdata[0].numericFrom}" onkeypress="return isNumberKey(event);" styleClass="text" styleId="numericFrom"/>
				     </td>
					<td id="nto">
						<bean:message key="lbl.parameterScoreNTo" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="numericTo" value="${Paramdata[0].numericTo}" onkeypress="return isNumberKey(event);" styleClass="text" styleId="numericTo"/>
				     </td>		
				      
				   
				</tr>
				<tr>
				<td><button class="topformbutton2" type="button" onclick="return addParameter();" accesskey="A" title="Alt+A" ><bean:message key="button.addrow" /></button></td>
				</tr>				
			</table>
			</logic:notPresent>	
			<logic:present name="notshowLov">
		<div id="parameterid">
			<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">	
			<tbody>	
			<tr>
			<td class="gridtd">
			<table id="table1" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">			
				<tr class="white2" align="center">
					<td width="5%">
					<b><bean:message key="lbl.select" /></b>
					</td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreCode" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreType" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreNfrom" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreNTo" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreCharecter" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScore" /></b></td>					
				</tr>
					
		<logic:present name="Paramdata">
			<%int a=1; %>
			<logic:iterate id="Paramdata" name="Paramdata" >
			<tr class="white1" align="center">	
			<td><input type='radio' name='chk' value="${Paramdata.parameterCode }" id='chk<%=a %>' onclick="getParameterDef('chk<%=a%>');" /></td>	
				<td><input type="hidden" id="parameterCode<%=a%>" name="parametergGridCode" value="${Paramdata.parameterCode }"/>${Paramdata.parameterCode }</td>	
					<td><input type="hidden" id="gridtype" name="gridtype" value="${Paramdata.type }"/>${Paramdata.type }</td>	
						<td><input type="hidden" id="gridnumericFrom" name="gridnumericFrom" value="${Paramdata.numericFrom }" />${Paramdata.numericFrom }</td>	
							<td><input type="hidden" id="gridnumericTo" name="gridnumericTo" value="${Paramdata.numericTo }"/>${Paramdata.numericTo }</td>	
								<td><input type="hidden" id="gridcharecter" name="gridcharecter" value="${Paramdata.charecter }"/>${Paramdata.charecter }</td>	
									<td><input type="hidden" id="gridscore" name="gridscore" value="${Paramdata.score }"   />${Paramdata.score }</td>	
									</tr>	
									<% a++;%>
				</logic:iterate>
			</logic:present>
			
				</table>			
			  		
			</td>	
			</tr>
			
			</tbody>
			
			</table>	</div></logic:present>
			
				<logic:notPresent name="notshowLov">
		<div id="parameterid">
			<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">	
			<tbody>	
			<tr>
			<td class="gridtd">
			<table id="table1" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">			
				<tr class="white2" align="center">
					<td width="5%">
					<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"> </b>
					</td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreCode" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreType" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreNfrom" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreNTo" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScoreCharecter" /></b></td>
					<td width="13%"><b><bean:message key="lbl.parameterScore" /></b></td>					
				</tr>
					
		<logic:present name="Paramdata">
			<%int a1=1; %>
			<logic:iterate id="Paramdata" name="Paramdata" >
			<tr class="white1" align="center">	
			<td><input type='radio' name='chk' value="${Paramdata.parameterCode }" id='chk<%=a1 %>' onclick="getParameterDef('chk<%=a1%>');" /></td>	
				<td><input type="hidden" id="parameterCode<%=a1%>" name="parametergGridCode" value="${Paramdata.parameterCode }"/>${Paramdata.parameterCode }</td>	
					<td><input type="hidden" id="gridtype" name="gridtype" value="${Paramdata.type }"/>${Paramdata.type }</td>	
						<td><input type="hidden" id="gridnumericFrom" name="gridnumericFrom" value="${Paramdata.numericFrom }" />${Paramdata.numericFrom }</td>	
							<td><input type="hidden" id="gridnumericTo" name="gridnumericTo" value="${Paramdata.numericTo }"/>${Paramdata.numericTo }</td>	
								<td><input type="hidden" id="gridcharecter" name="gridcharecter" value="${Paramdata.charecter }"/>${Paramdata.charecter }</td>	
									<td><input type="hidden" id="gridscore" name="gridscore" value="${Paramdata.score }"   />${Paramdata.score }</td>	
									</tr>	
									<% a1++;%>
				</logic:iterate>
			</logic:present>
			
				</table>			
			  		
			</td>	
			</tr>
			
			</tbody>
			
			</table>	</div></logic:notPresent>
		<table>
		<tr>	<td>
				</td><td>
                <button class="topformbutton2" type="button" onclick="return removeRow();" accesskey="D" title="Alt+D" ><bean:message key="button.deleterow" /></button>
<!--                <input class="topformbutton2" type="button" onclick="return editRow();" accesskey="D" title="Alt+D" value="Edit">-->
				</td>
				</tr>
		</table>
		</fieldset>

			<table width="100%">			
			<tr><td style="margin-left: 20px;">
		    <button id="save" class="topformbutton2" type="button" onclick="saveParameterDesc();" accesskey="V" title="Alt+V" ><bean:message key="button.save" /></button>
		     </td></tr>
            </table>
	</html:form>	

</body>
</html:html>