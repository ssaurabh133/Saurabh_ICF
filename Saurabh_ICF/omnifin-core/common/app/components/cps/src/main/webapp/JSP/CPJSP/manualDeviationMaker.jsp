 
<!--
 	Author Name      :- Manisha Tomar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Manual Deviation Maker
 	Documentation    :- 
 -->
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<logic:present name="back">

<script type="text/javascript">
  alert("You can't move without saving before Lead Details!!!");
</script>

</logic:present>
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
	
	<body onload="enableAnchor();checkChanges('deviationMakerForm');document.getElementById('deviationMakerForm').remark.focus();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	<html:form action="/manualDeviationMakerAction" method="post" styleId="deviationMakerForm">
 	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" />
	<div id="centercolumn">
	
	<div id="revisedcontainer">

   <legend><bean:message key="lbl.manualdeviationMaker"></bean:message></legend>
 
  	
  	<fieldset>
 <logic:present name="dealHeader">
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <input type="hidden" id="dealId" class="text" name="dealId" value="${dealHeader[0].dealId}"/>
        <td>    
          ${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Manual Deviation</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</logic:present>
 
</fieldset>
  	
  	
   <fieldset>
  
  <legend><bean:message key="lbl.deviationTab"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td>
		   <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
			  	<input type="hidden" id="chkValue"  name="chkValue"  />		
			
			<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridTable">
							<tr class="white2">
							 <td ><B><input type="checkbox" name="chk" id="chk" onclick="allCheck();" /></b></td>                                
								<td><B><bean:message key="lbl.product" /></b></td>
								<td><B><bean:message key="lbl.scheme" /></b></td>
								<td><B><bean:message key="lbl.stage" /></b></td>
								<td><B><bean:message key="lbl.devRuleDesc" /></b></td>						
								<td><B><bean:message key="lbl.remark" /></b></td>
								
							</tr>
                            
								<logic:present name="manualDevList">
								
							   <logic:notEmpty name="manualDevList">
							<logic:iterate id="manualDevList" name="manualDevList" indexId="count">
							<tr class="white1">
							<td>	
								<logic:present name="manualDevList" property="remark">			
					          	<input type="checkbox" name="chk" id="chk<%=count.intValue()+1 %>"  checked="checked"/>
					         		</logic:present>    
					         		<logic:notPresent name="manualDevList" property="remark">			
					          	<input type="checkbox" name="chk" id="chk<%=count.intValue()+1 %>" />
					         		</logic:notPresent>           
					           	<input type="hidden" id="manualId<%=count.intValue()+1 %>"  name="manualId<%=count.intValue()+1 %>" value="${manualDevList.manualId}"  />		
					          			</td>
							
								<td >${manualDevList.productDesc }</td>
								<td>${manualDevList.scheme }</td>
								<td>${manualDevList.stage }</td>
								<td>${manualDevList.ruleDesc }</td>
								
								<td><input type="text" id="remarks<%=count.intValue()+1 %>" class="text" name="remarks<%=count.intValue()+1 %>" value="${manualDevList.remark}" maxlength="100" /> 
								<input type="hidden" id="approvalLevel<%=count.intValue()+1 %>"  name="approvalLevel<%=count.intValue()+1 %>" value="${manualDevList.approvalLevel}"  />
								</td>
								
							</tr>	
							</logic:iterate>
							</logic:notEmpty>
							
							<logic:empty name="manualDevList">
								<script type="text/javascript">
							
							            alert('No Data Found');
							            self.close(); 
						                 
						   	   </script>
                            </logic:empty>
								
								</logic:present>
										</table>
										</td></tr>
								</table>
		   </td>
		</tr>
		 
		</table>
		
	      </td>
	</tr>
	 <tr><td> 
	 			<html:button property="button" value="Save" styleClass="topformbutton2" onclick="return saveManualDeviationMaker();" title="Alt+V" accesskey="V"> </html:button>
	 			<logic:present name="waveoffStage" >
	          		<html:button property="button" value="Forward" styleClass="topformbutton2" onclick="return forwardManualDeviationMaker('Do you want to forward data');" title="Alt+F" accesskey="F"> </html:button>
	          	</logic:present> 
	 			<logic:notPresent name="waveoffStage" >
	          		<html:button property="button" value="Forward" styleClass="topformbutton2" onclick="return byPassManualDeviation('Manual deviation waived off.Do you want to continue?');" title="Alt+F" accesskey="F"> </html:button>
	          	</logic:notPresent>	 
	    </td>
	 </tr>
	</table>
	
	  </fieldset>
	<br/> 
  </html:form>
  	
 
  
</div>

</div>

<script>
	parent.menu.document.test.checkModifications.value = '';
	parent.menu.document.test.getFormName.value = "deviationMakerForm";
</script>
</body>
<logic:present name="sms">
<script type="text/javascript">
if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		 document.getElementById("deviationMakerForm").action="<%=request.getContextPath()%>/manualDeviationMakerAction.do?method=manualDeviationM";
	    document.getElementById("deviationMakerForm").submit();
	}
  else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="msg.ForwardSuccessfully" />");
		document.getElementById("deviationMakerForm").action="<%=request.getContextPath()%>/manualDeviationMakerSearchBehindAction.do";
	    document.getElementById("deviationMakerForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='X')
	{
		alert("<bean:message key="msg.ForwardNotSuccessfully" />");

	 document.getElementById("deviationMakerForm").action="<%=request.getContextPath()%>/manualDeviationMakerAction.do?method=manualDeviationM";
	    document.getElementById("deviationMakerForm").submit();
	}
	 else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('${status}');
		document.getElementById("deviationMakerForm").action="<%=request.getContextPath()%>/manualDeviationMakerSearchBehindAction.do";
	    document.getElementById("deviationMakerForm").submit();
	}
</script>
</logic:present>
</html:html>


