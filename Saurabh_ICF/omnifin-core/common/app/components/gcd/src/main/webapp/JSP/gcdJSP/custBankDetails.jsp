<!--
 	Author Name      :- Surendra
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Customer Bank Details
 	Documentation    :- 
 -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/custBankDetails.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

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
<body oncontextmenu="return false" onclick="parent_disable();" onload="enableAnchor();checkChanges('CustBankDetailsForm');">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			<logic:present name="image">
   	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
           </logic:present>
   		<logic:notPresent name="image">
   			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
   		</logic:notPresent>
   		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<div id="centercolumn">
<div id="revisedcontainer">
<html:form action="/custBankDetailAction" styleId="CustBankDetailsForm" method="post" >
	<fieldset>	  
		<legend><bean:message key="lbl.custBankDetails" /></legend>
		<table width="100%"  border="0" cellspacing="2" cellpadding="1">
		<logic:notPresent name="approve">
		<logic:present name="update">
   			<input type="hidden" name="customerId" id="customerId" value="${list[0].customerId}"/>
   			<tr>
							<td>
								<bean:message key="lbl.bank" />								
								
							</td>
							<td>
								<html:text property="bankCode" styleId="bankCode" styleClass="text" readonly="true" value="${list[0].bankCode}" tabindex="-1" />
                                <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'CustBankDetailsForm','bankCode','','bankBranchName', 'lbxBranchID','','clearBankLovChildNew','lbxBankID');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(7,'bankAccountMaster','bankCode','','bankBranchName', 'lbxBranchID','','','lbxBankID')" src="<%=request.getContextPath()%>/images/lov.gif"> --%>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
								<html:hidden property="lbxBankID" styleId="lbxBankID" value="${list[0].lbxBankID}"/>
							</td>

							<td>
								<bean:message key="lbl.bankBranchName" />								
								
							</td>
							<td>
								<html:text property="bankBranchName" styleId="bankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${list[0].bankBranchName}" />
                                <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(109,'CustBankDetailsForm','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','micrCode','ifscCode','lbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(109,'bankAccountMaster','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','lbxBranchID','ifscCode','micrCode')" src="<%=request.getContextPath()%>/images/lov.gif"> --%> 
								<html:hidden property="lbxBranchID" styleId="lbxBranchID" value="${list[0].lbxBranchID}"  />
							</td>	</tr><tr>			
							<td>
								<bean:message key="lbl.ifscCode" />
							
							</td>
							<td>
								<html:text property="ifscCode" styleId="ifscCode"
									styleClass="text" readonly="true" maxlength="20" value="${list[0].ifscCode}"  />
							</td>


							<td>
								<bean:message key="lbl.micrCode" />
								
							</td>
							<td>
								<html:text property="micrCode" styleId="micrCode"
									styleClass="text" maxlength="20" readonly="true" value="${list[0].micrCode}"  />
							</td>
						</tr>
						
      
      <tr>
      
							<td>
								<bean:message key="lbl.accountNo"  />								
								
							</td>
							<td>
								<html:text property="accountNo" styleId="accountNo"   tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);"
									styleClass="text" maxlength="20" value="${list[0].accountNo}"  />
							</td>
      
       
      </tr>
			<tr>
				<td>
					<button name="button1" type="button" class="topformbutton2" id="save" onclick="return saveCustBankDetails();this.disabled=true;" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
				</td>
			</tr>			
			</logic:present>
			<logic:notPresent name="update">
<!--			<input type="text" name="customerId" id="customerId" value="<%=session.getAttribute("idividualId").toString() %>"/>-->
			<input type="hidden" name="customerId" id="customerId" value="${list[0].customerId}"/>
		<tr>
							<td>
								<bean:message key="lbl.bank" />								
								
							</td>
							<td>
								<html:text property="bankCode" styleId="bankCode" styleClass="text" readonly="true" value="${list[0].bankCode}" tabindex="-1" />
                                <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'CustBankDetailsForm','bankCode','','bankBranchName', 'lbxBranchID','','clearBankLovChildNew','lbxBankID');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(7,'bankAccountMaster','bankCode','','bankBranchName', 'lbxBranchID','','','lbxBankID')" src="<%=request.getContextPath()%>/images/lov.gif"> --%>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
								<html:hidden property="lbxBankID" styleId="lbxBankID" value="${list[0].lbxBankID}"/>
							</td>

							<td>
								<bean:message key="lbl.bankBranchName" />								
								
							</td>
							<td>
								<html:text property="bankBranchName" styleId="bankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${list[0].bankBranchName}" />
                                <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(109,'CustBankDetailsForm','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','micrCode','ifscCode','lbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(109,'bankAccountMaster','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','lbxBranchID','ifscCode','micrCode')" src="<%=request.getContextPath()%>/images/lov.gif"> --%> 
								<html:hidden property="lbxBranchID" styleId="lbxBranchID" value="${list[0].lbxBranchID}"  />
							</td>	</tr><tr>			
							<td>
								<bean:message key="lbl.ifscCode" />
							
							</td>
							<td>
								<html:text property="ifscCode" styleId="ifscCode"
									styleClass="text" readonly="true" maxlength="20" value="${list[0].ifscCode}"  />
							</td>


							<td>
								<bean:message key="lbl.micrCode" />
								
							</td>
							<td>
								<html:text property="micrCode" styleId="micrCode"
									styleClass="text" maxlength="20" readonly="true" value="${list[0].micrCode}"  />
							</td>
						</tr>
						
      
      <tr>
      
							<td>
								<bean:message key="lbl.accountNo"  />								
								
							</td>
							<td>
								<html:text property="accountNo" styleId="accountNo"  tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);"
									styleClass="text" maxlength="20" value="${list[0].accountNo}"  />
							</td>							
      
      
       
      </tr>
			<tr>
				<td>
					<button name="button1" type="button" class="topformbutton2" id="save" onclick="return saveCustBankDetails();this.disabled=true;" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
				</td>
			</tr>	
	
				</logic:notPresent>
				</logic:notPresent>
				<logic:present name="approve">
				<% System.out.println("approve block"); %>
<input type="hidden" name="customerId" id="customerId" value="${list[0].customerId}"/>
				<tr>
							<td>
								<bean:message key="lbl.bank" />								
								
							</td>
							<td>
								<html:text property="bankCode" styleId="bankCode" styleClass="text" readonly="true" value="${list[0].bankCode}" tabindex="-1" />

								<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
								<html:hidden property="lbxBankID" styleId="lbxBankID" value="${list[0].lbxBankID}"/>
							</td>

							<td>
								<bean:message key="lbl.bankBranchName" />								
								
							</td>
							<td>
								<html:text property="bankBranchName" styleId="bankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${list[0].bankBranchName}" />
<!--                                <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(109,'CustBankDetailsForm','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','micrCode','ifscCode','lbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>-->
								<html:hidden property="lbxBranchID" styleId="lbxBranchID" value="${list[0].lbxBranchID}"  />
							</td>	</tr><tr>			
							<td>
								<bean:message key="lbl.ifscCode" />
							
							</td>
							<td>
								<html:text property="ifscCode" styleId="ifscCode"
									styleClass="text" readonly="true" maxlength="20" value="${list[0].ifscCode}"  />
							</td>


							<td>
								<bean:message key="lbl.micrCode" />
								
							</td>
							<td>
								<html:text property="micrCode" styleId="micrCode"
									styleClass="text" maxlength="20" readonly="true" value="${list[0].micrCode}"  />
							</td>
						</tr>
						
      
      <tr>
      
							<td>
								<bean:message key="lbl.accountNo"  />								
								
							</td>
							<td>
								<html:text property="accountNo" styleId="accountNo" readonly="true"  tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);"
									styleClass="text"  value="${list[0].accountNo}"  />
							</td>							
      
      
       
      </tr>
	
</logic:present>
</table>
</fieldset>
</html:form>
</div>
</div>
<logic:present name="back">
<script type="text/javascript">
 alert("You can't move without saving before Individual Details!!!");
</script>
</logic:present>
<logic:present name="backCor">
<script type="text/javascript">
 alert("You can't move without saving before Corporate Details!!!");
</script>
</logic:present>
<logic:present name="sms">

 <script type="text/javascript">	
  
 	if('<%=request.getAttribute("sms").toString()%>' == 'S')
	{
		alert('<bean:message key="lbl.corporateStageHolder" />');
		//document.getElementById('CustBankDetailsForm').action="<%=request.getContextPath() %>/custBankDetailAction.do?method=displayCustBankDetails";
	   // document.getElementById('CustBankDetailsForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'U')
	{
		alert('<bean:message key="lbl.corporateStakeHolderUpdated" />');
		//document.getElementById('CustBankDetailsForm').action="<%=request.getContextPath() %>/custBankDetailAction.do?method=displayCustBankDetails";
	   // document.getElementById('CustBankDetailsForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'E')
	{
		alert('<bean:message key="lbl.errorSuccess" />');
		//document.getElementById('CustBankDetailsForm').action="<%=request.getContextPath() %>/custBankDetailAction.do?method=displayCustBankDetails";
		//document.getElementById('CustBankDetailsForm').submit();
	}
</script>
</logic:present>
<script>
	setFramevalues("CustBankDetailsForm");
</script>
</body>
</html>
