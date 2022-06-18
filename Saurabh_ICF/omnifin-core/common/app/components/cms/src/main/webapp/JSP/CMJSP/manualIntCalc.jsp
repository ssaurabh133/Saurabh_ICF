<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	UserObject userobj = (UserObject) session
			.getAttribute("userobject");
	String initiationDate = userobj.getBusinessdate();
%>

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
	<!-- css for Datepicker -->
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
		rel="stylesheet" />
	<!-- jQuery for Datepicker -->

	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

	<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImagetransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImagetransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->
	<script type="text/javascript">
	function fntab()
{
     if(document.getElementById('modifyRecord').value =='CM')	
     {
    	 document.getElementById('manualIntCalcForm').requestNumber.focus();
     }
     else if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('manualIntCalcForm').loanClosureLOV.focus();
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

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cmScript/manualIntCalc.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" 
	src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>


<body onload="enableAnchor();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllWindowCallUnloadBody();">

	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<div id="centercolumn">
		<div id="revisedcontainer">
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
			<input type="hidden" id="checkFlag" value="${sessionScope.checkFlag}" />
			<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />				
<!--			<input type="hidden" id="modifyRecord" name="modifyRecord" value="CM"/>-->			
<html:form action="/manualInterestCalc" styleId="manualIntCalcForm"
					method="post">		
					
					<fieldset>
						<fieldset>							
							<LEGEND>
								<bean:message key="lbl.manualIntCalc" />
							</LEGEND>
							
							<table width="100%" border="0" cellspacing="1" cellpadding="1">								
								<tr>

									<td width="25%">
										<bean:message key="lbl.loanAc" /><span><font color="red">*</font></span>
									</td>
									<td width="25%">
										<html:text styleClass="text" property="loanAc"
											styleId="loanAc" value="" readonly="true" tabindex="-1"/>									
										<html:button property="loanClosureLOV" value=" "
											onclick="openLOVCommon(479,'closureForm','loanAc','','', '','','generateValuesManualIntCalc','customerName');closeLovCallonLov1();"
											styleClass="lovButton"/>
										<input type="hidden" name="contextPath"
											value="<%=request.getContextPath()%>" id="contextPath" />
										<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
											value="" />
									</td>
									<td width="25%">
										<bean:message key="lbl.customerName" />
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="customerName" tabindex="-1"
											maxlength="50" disabled="true" property="customerName" />
									</td>
									
								</tr>
								<tr>
								   <td>
										<bean:message key="lbl.product" />
									</td>
									<td>
										<html:text styleClass="text" styleId="product" disabled="true"
											maxlength="50" property="product" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.scheme" />
									</td>
									<td>
										<html:text styleClass="text" styleId="scheme" disabled="true"
											maxlength="50" property="scheme" tabindex="-1"/>
									</td>									
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.initiationDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="initDate"
											maxlength="10" disabled="true" property="initiationDate"
											value="<%=initiationDate %>" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.loanDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="loanDate" maxlength="10"
											disabled="true" property="loanDate" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.repayEffectiveDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="repayEffectiveDate"
											maxlength="10" disabled="true" property="repayEffectiveDate"
											 tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.lastAccrualDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="lastAccrualDate" maxlength="10"
											disabled="true" property="lastAccrualDate" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td >
										<bean:message key="lbl.lastIntCalcDate" />
									</td>
									<td >
										<html:text styleClass="text" styleId="lastIntCalcDate"
											readonly="true" value="" maxlength="18"
											property="lastIntCalcDate" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.roi" />
									</td>
									<td>
										<html:text styleClass="text" styleId="roi" maxlength="10"
											disabled="true" property="roi" style="text-align:right;" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td width="25%" >
										<bean:message key="lbl.balancePrincipal" />
									</td>
									<td width="25%" >
										<html:text styleClass="text" styleId="balancePrincipal"
											readonly="true" value="" maxlength="18"
											property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>									
								
									<td width="25%">
										<bean:message key="lbl.interestTillDate" />
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="interestTillDate"
											value="" readonly="true" maxlength="18"
											property="interestTillDate" tabindex="-1"
											style="text-align:right;"/>
									</td>																	
								</tr>
							
								<tr>
									<td colspan="2">
										<button  type="button"  name="getDetail" id="getDetail"
										class="topformbutton2" 
											
											onclick="return getDetails();" 
											accesskey="G" title="Alt+G" ><bean:message key="button.getInterest" /></button>

									</td>								</tr>	
							</table>
						</fieldset>					
						<table>							
							<tr>
								<td>									
									<button type="button" name="generate" id="generate" 
										class="topformbutton3" accesskey="V" title="Alt+V"
										onclick="return generateAdvice();" ><bean:message key="button.GenerateAdvice" /></button>	
										<button type="button"
										class="topformbutton3" accesskey="P" title="Alt+P"
										onclick="return viewPayable('<bean:message key="msg.LoanAccountNo" />');" >
										<bean:message key="button.viewPayable" /></button>
									<button type="button" class="topformbutton3" 
									accesskey="R" title="Alt+R"
									onclick="return viewReceivable('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
															
								</td>
							</tr>
						</table>

					</fieldset>
				</html:form>
			
		</div>
	</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<!--<script>-->
<!--	setFramevalues("closureForm");-->
<!--</script>	-->
</body>
</html:html>

<logic:present name="message">

<script type="text/javascript">

	if("<%=request.getAttribute("message")%>"=="S")
	{
		alert("Advice Generated Successfully for the Loan No: "+"<%=request.getAttribute("loanAc")%>");
		window.location="<%=request.getContextPath()%>/manualInterestCalc.do?method=openPage";
		
	}
		else if("<%=request.getAttribute("message")%>"=="E")
	{
		alert("No Advice Generated for the Loan No: "+"<%=request.getAttribute("loanAc")%>");
		window.location="<%=request.getContextPath()%>/manualInterestCalc.do?method=openPage";
	}	


</script>
</logic:present>

