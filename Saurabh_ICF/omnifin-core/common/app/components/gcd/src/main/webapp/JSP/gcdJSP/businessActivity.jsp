<%--
 	Author Name      :- Amit Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for business activity Details
 	Documentation    :- 
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/businessActivity.js"></script>
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
<body oncontextmenu="return false" onclick="parent_disable();" onload="enableAnchor();checkChanges('BusinessActivityForm');">
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
<logic:notPresent name="approve">
<div id="centercolumn">
<div id="revisedcontainer">
<html:form action="/corporateBusinessActivityDispatchAction" styleId="BusinessActivityForm" method="post" >
<logic:present name="update">
	<fieldset>	  
		<legend><bean:message key="lbl.businessActivityDetails" /></legend>
		<input type="hidden" name="customerId" id="customerId" value="${businessActivityDetails[0].customerId}"/>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
   			<tr>
   				<td>
   					<table width="100%" border="0" cellspacing="1" cellpadding="1">
     						<tr>
   							<td>
   								<bean:message key="lbl.brands" />
   							</td>
   							<td>
   								<html:text property="brands" styleId="brands" styleClass="text" value="${businessActivityDetails[0].brands}"
   									maxlength="100" onchange="return upperMe('brands');"/>
   							</td>
      							<td>
      								<bean:message key="lbl.productServices" />
      							</td>
      							<td>
      								<html:text property="productServices" styleId="productServices" value="${businessActivityDetails[0].productServices}" 
      									styleClass="text" maxlength="100" onchange="return upperMe('productServices');" />
      							</td>
      						</tr>
     						<tr>
       						<td>
       							<bean:message key="lbl.noOfEmployees" />
       						</td>
       						<td>
       							<html:text property="noOfEmployees" styleId="noOfEmployees" value="${businessActivityDetails[0].noOfEmployees}" 
       								styleClass="text" onkeypress="return numbersonly(event, id, 7);" style="text-align: right;"/>
       						</td>
       						<td>
       							<bean:message key="lbl.auditors" />
       						</td>
							<td>
								<html:text property="auditors" styleId="auditors" value="${businessActivityDetails[0].auditors}" styleClass="text" />
							</td>
						</tr>
						<tr>
       						<td>
       							<bean:message key="lbl.certifications" />
       						</td>
       						<td>
       							<html:text property="certifications" styleId="certifications" value="${businessActivityDetails[0].certifications}" 
       								styleClass="text" maxlength="100"/>
       						</td>
       						<td>
       							<bean:message key="lbl.awards" />
       						</td>
							<td>
								<html:text property="awards" styleId="awards" value="${businessActivityDetails[0].awards}" styleClass="text" />
							</td>
						</tr>
						<tr>
       						<td>
       							<bean:message key="lbl.assocoationMembershipName" />
       						</td>
       						<td>
       							<html:text property="assocoationMembershipName" styleId="assocoationMembershipName" value="${businessActivityDetails[0].assocoationMembershipName}" 
       								styleClass="text" maxlength="100"/>
       						</td>
       						<td></td>
							<td></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<button name="button1" type="button" class="topformbutton2" id="save" onclick="return saveBusinessActivity();this.disabled=true;" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
				</td>
			</tr>
		</table>
	</fieldset>
</logic:present>
<logic:notPresent name="update">
	<fieldset>	  
		<legend><bean:message key="lbl.businessActivityDetails" /></legend>
		<input type="hidden" name="customerId" id="customerId" value="${businessActivityDetails[0].customerId}"/>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
   			<tr>
   				<td>
   					<table width="100%" border="0" cellspacing="1" cellpadding="1">
     						<tr>
   							<td>
   								<bean:message key="lbl.brands" />
   							</td>
   							<td>
   								<html:text property="brands" styleId="brands" styleClass="text" value="${businessActivityDetails[0].brands}"
   									maxlength="100" onchange="return upperMe('brands');"/>
   							</td>
      							<td>
      								<bean:message key="lbl.productServices" />
      							</td>
      							<td>
      								<html:text property="productServices" styleId="productServices" value="${businessActivityDetails[0].productServices}" 
      									styleClass="text" maxlength="100" onchange="return upperMe('productServices');" />
      							</td>
      						</tr>
     						<tr>
       						<td>
       							<bean:message key="lbl.noOfEmployees" />
       						</td>
       						<td>
       							<html:text property="noOfEmployees" styleId="noOfEmployees" value="${businessActivityDetails[0].noOfEmployees}" 
       								styleClass="text" onkeypress="return numbersonly(event, id, 7);" style="text-align: right;"/>
       						</td>
       						<td>
       							<bean:message key="lbl.auditors" />
       						</td>
							<td>
								<html:text property="auditors" styleId="auditors" value="${businessActivityDetails[0].auditors}" styleClass="text" />
							</td>
						</tr>
						<tr>
       						<td>
       							<bean:message key="lbl.certifications" />
       						</td>
       						<td>
       							<html:text property="certifications" styleId="certifications" value="${businessActivityDetails[0].certifications}" 
       								styleClass="text" maxlength="100"/>
       						</td>
       						<td>
       							<bean:message key="lbl.awards" />
       						</td>
							<td>
								<html:text property="awards" styleId="awards" value="${businessActivityDetails[0].awards}" styleClass="text" />
							</td>
						</tr>
						<tr>
       						<td>
       							<bean:message key="lbl.assocoationMembershipName" />
       						</td>
       						<td>
       							<html:text property="assocoationMembershipName" styleId="assocoationMembershipName" value="${businessActivityDetails[0].assocoationMembershipName}" 
       								styleClass="text" maxlength="100"/>
       						</td>
       						<td></td>
							<td></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<button name="button1" type="button" class="topformbutton2" id="save" onclick="return saveBusinessActivity();this.disabled=true;" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
				</td>
			</tr>
		</table>
	</fieldset>
</logic:notPresent>
</html:form>
</div>
</div>
</logic:notPresent>

<logic:present name="approve">
<div id="centercolumn">
<div id="revisedcontainer">
<html:form action="/corporateBusinessActivityDispatchAction" styleId="BusinessActivityForm" method="post" >
<logic:present name="custEntryU">
	<fieldset>	  
		<legend><bean:message key="lbl.businessActivityDetails" /></legend>
		<input type="hidden" name="customerId" id="customerId" value="${businessActivityDetails[0].customerId}"/>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
   			<tr>
   				<td>
   					<table width="100%" border="0" cellspacing="1" cellpadding="1">
     						<tr>
   							<td>
   								<bean:message key="lbl.brands" />
   							</td>
   							<td>
   								<html:text property="brands" styleId="brands" styleClass="text" value="${businessActivityDetails[0].brands}"
   									maxlength="100" onchange="return upperMe('brands');"/>
   							</td>
      							<td>
      								<bean:message key="lbl.productServices" />
      							</td>
      							<td>
      								<html:text property="productServices" styleId="productServices" value="${businessActivityDetails[0].productServices}" 
      									styleClass="text" maxlength="100" onchange="return upperMe('productServices');" />
      							</td>
      						</tr>
     						<tr>
       						<td>
       							<bean:message key="lbl.noOfEmployees" />
       						</td>
       						<td>
       							<html:text property="noOfEmployees" styleId="noOfEmployees" value="${businessActivityDetails[0].noOfEmployees}" 
       								styleClass="text" onkeypress="return numbersonly(event, id, 7);" style="text-align: right;"/>
       						</td>
       						<td>
       							<bean:message key="lbl.auditors" />
       						</td>
							<td>
								<html:text property="auditors" styleId="auditors" value="${businessActivityDetails[0].auditors}" styleClass="text" />
							</td>
						</tr>
						<tr>
       						<td>
       							<bean:message key="lbl.certifications" />
       						</td>
       						<td>
       							<html:text property="certifications" styleId="certifications" value="${businessActivityDetails[0].certifications}" 
       								styleClass="text" maxlength="100"/>
       						</td>
       						<td>
       							<bean:message key="lbl.awards" />
       						</td>
							<td>
								<html:text property="awards" styleId="awards" value="${businessActivityDetails[0].awards}" styleClass="text" />
							</td>
						</tr>
						<tr>
       						<td>
       							<bean:message key="lbl.assocoationMembershipName" />
       						</td>
       						<td>
       							<html:text property="assocoationMembershipName" styleId="assocoationMembershipName" value="${businessActivityDetails[0].assocoationMembershipName}" 
       								styleClass="text" maxlength="100"/>
       						</td>
       						<td></td>
							<td></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<button name="button1" type="button" class="topformbutton2" id="save" onclick="return saveBusinessActivity();this.disabled=true;" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
				</td>
			</tr>
		</table>
	</fieldset>
</logic:present>
<logic:notPresent name="custEntryU">
	<fieldset>
		<legend><bean:message key="lbl.businessActivityDetails" /></legend>
		<input type="hidden" name="customerId" value="${businessActivityDetails[0].customerId}"/>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
   							<td>
   								approve Present 
   								<bean:message key="lbl.brands" />
   							</td>
   							<td>
   								<html:text property="brands" styleId="brands" styleClass="text" value="${businessActivityDetails[0].brands}"
   									maxlength="100" disabled="true" tabindex="-1"/>
   							</td>
      							<td>
      								<bean:message key="lbl.productServices" />
      							</td>
      							<td>
      								<html:text property="productServices" styleId="productServices" value="${businessActivityDetails[0].productServices}" 
      									styleClass="text" maxlength="100" disabled="true" tabindex="-1" />
      							</td>
      						</tr>
     						<tr>
       						<td>
       							<bean:message key="lbl.noOfEmployees" />
       						</td>
       						<td>
       							<html:text property="noOfEmployees" styleId="noOfEmployees" value="${businessActivityDetails[0].noOfEmployees}" 
       								styleClass="text" style="text-align: right;" disabled="true" tabindex="-1"/>
       						</td>
       						<td>
       							<bean:message key="lbl.auditors" />
       						</td>
							<td>
								<html:text property="auditors" styleId="auditors" value="${businessActivityDetails[0].auditors}" styleClass="text"
									disabled="true" tabindex="-1" />
							</td>
						</tr>
						<tr>
       						<td>
       							<bean:message key="lbl.certifications" />
       						</td>
       						<td>
       							<html:text property="certifications" styleId="certifications" value="${businessActivityDetails[0].certifications}" 
       								styleClass="text" maxlength="100" disabled="true" tabindex="-1"/>
       						</td>
       						<td>
       							<bean:message key="lbl.awards" />
       						</td>
							<td>
								<html:text property="awards" styleId="awards" value="${businessActivityDetails[0].awards}" styleClass="text" 
									disabled="true" tabindex="-1" />
							</td>
						</tr>
						<tr>
       						<td>
       							<bean:message key="lbl.assocoationMembershipName" />
       						</td>
       						<td>
       							<html:text property="assocoationMembershipName" styleId="assocoationMembershipName" value="${businessActivityDetails[0].assocoationMembershipName}" 
       								styleClass="text" maxlength="100" disabled="true" tabindex="-1"/>
       						</td>
       						<td></td>
							<td></td>
						</tr>
					</table>
    			</td>
    		</tr>
    	</table>
    </fieldset>  
</logic:notPresent>
</html:form>
</div>
</div>
</logic:present>


<logic:present name="sms">

 <script type="text/javascript">	
  
 	if('<%=request.getAttribute("sms").toString()%>' == 'S')
	{
		alert('<bean:message key="lbl.corporateStageHolder" />');
		document.getElementById('BusinessActivityForm').action="<%=request.getContextPath() %>/corporateBusinessActivityBehindAction.do?method=displayBusinessActivity";
	    document.getElementById('BusinessActivityForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'U')
	{
		alert('<bean:message key="lbl.corporateStakeHolderUpdated" />');
		document.getElementById('BusinessActivityForm').action="<%=request.getContextPath() %>/corporateBusinessActivityBehindAction.do?method=displayBusinessActivity";
	    document.getElementById('BusinessActivityForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'E')
	{
		alert('<bean:message key="lbl.errorSuccess" />');
		document.getElementById('BusinessActivityForm').action="<%=request.getContextPath() %>/corporateBusinessActivityBehindAction.do?method=displayBusinessActivity";
		document.getElementById('BusinessActivityForm').submit();
	}
</script>
</logic:present>
<script>
	setFramevalues("BusinessActivityForm");
</script>
</body>
</html:html>