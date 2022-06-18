<!--
 	Author Name      :- prashant Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Deviation Approval 
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

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanDetails.js"></script>
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
	<body onload="enableAnchor();checkChanges('deviationApprovalForm');document.getElementById('deviationApprovalForm').remark.focus();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	<logic:notPresent name="viewDevJSP">
	<logic:notPresent name="cmAuthor">
	<html:form action="/saveLoanDeviationActionProcess" method="post" styleId="deviationApprovalForm">
 	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" />

	
	<fieldset>
	  <legend><bean:message key="lbl.manualDeviation"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td>
		   <table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td class="gridtd">
			<input type="hidden" id="dealId" class="text" name="dealId" value="${requestScope.dealId}"/> 
			<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridTable">
							<tr class="white2">
                                
                                <td><b><bean:message key="lbl.select" /></b></td> 
								<td><b><bean:message key="lbl.stage" /></b></td>								
								<td><b><bean:message key="lbl.ruleDescription" /></b></td>
								<td><b><bean:message key="lbl.deviationType" /></b></td>		
								<td><b><bean:message key="lbl.action" /></b></td>	
								<td><b><bean:message key="lbl.approvalLevel" /></b></td>
													
								<td><b><bean:message key="lbl.authorRemarks" /></b></td>
								<td><b><bean:message key="lbl.remark" /></b></td>	
								
								
							</tr>

							<logic:present name="manualDevList">
							<logic:iterate id="list" name="manualDevList" indexId="count">
							<tr class="white1">
								<td>
									<logic:equal name="list" property="recStatus" value="P">
									<input type='checkbox' name='chkCases' value=''/>
									</logic:equal>
									<logic:equal name="list" property="recStatus" value="S">
									<input type='checkbox' name='chkCases' value='' checked="checked"/>
									</logic:equal>
								</td>
								<td>
									<input type="hidden" id="loanDeviationId<%=count.intValue()+1 %>" class="text" name="loanDeviationId" value="${list.loanDevId}"/> 
									<input type="hidden" id="manualDeviId<%=count.intValue()+1 %>" class="text" name="manualDeviId" value="${list.manualDevId}"/> 
									LOAN INITIATION MAKER
								</td>
								<td>${list.ruleDesc }</td>
								<td>
									<logic:equal name="list" property="deviationType" value="G">MANUAL DEVIATION</logic:equal>
									<logic:equal name="list" property="deviationType" value="A">AUTO DEVIATION</logic:equal>
								</td>
								<td> 
									<logic:equal name="list" property="ruleAction" value="D">DEVIATION</logic:equal>
									<logic:equal name="list" property="ruleAction" value="S">STOP</logic:equal>
								</td>								
								<td>${list.approvalLevel }</td>
								<td>${list.authorRemark}</td>
								<input type="hidden" id="approvalLev<%=count.intValue()+1 %>" class="text" name="approvalLev" value="${list.approvalLevel}"/>
								<td><input type="text" id="manualRemark<%=count.intValue()+1 %>" class="text" name="manualRemark" value="${list.remark }" maxlength="100"/> </td>
							</tr>	
							</logic:iterate>
								</logic:present>		
										</table>
										</td></tr>
								</table>
		   </td>
		</tr>
		 
		</table>
		
	      </td>
	</tr>
	
	</table>
	
	  </fieldset>
	
	   <tr><td> <button type="button" name="button" value="Save" class="topformbutton2" onclick="saveDeviationApproval();" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button> </td></tr>
	  
	<!-- Changes by Prashant Ends -->
	 
	<br/> 
  </html:form>
  </logic:notPresent>
  <logic:present name="cmAuthor">
  	
  	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	   <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Deviation Author</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
  	
  	<html:form action="/saveLoanDeviationActionProcess" method="post" styleId="deviationApprovalForm">
 	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" />
 		<fieldset>
	  <legend><bean:message key="lbl.manualDeviation"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td>
		   <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
			
			<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridTable">
							<tr class="white2">
                                
								<td><b><bean:message key="lbl.stage" /></b></td>								
								<td><b><bean:message key="lbl.ruleDescription" /></b></td>	
								<td><b><bean:message key="lbl.deviationType" /></b></td>		
								<td><b><bean:message key="lbl.action" /></b></td>	
								<td><b><bean:message key="lbl.approvalLevel" /></b></td>
								<td><b><bean:message key="lbl.makerRemarks" /></b></td>	
								<td><b><bean:message key="lbl.remark" /></b></td>	
								<%-- <td><b><bean:message key="lbl.approve" /></b></td>	--%>
								
							</tr>

							<logic:present name="manualDevList">
							<logic:iterate id="list" name="manualDevList" indexId="count">
							<tr class="white1">
							    <input type="hidden" id="loanDeviationId" class="text" name="loanDeviationId" value="${list.loanDevId}"/> 
								<td><input type="hidden" id="manualDeviId" class="text" name="manualDeviId" value="${list.manualDevId}"/> 
									   LOAN INITIATION MAKER                                 
							    </td>
								<td><input type="hidden" id="policyDecisionIds" class="text" name="policyDecisionIds" value="${list.policyDecisionId }"/>
								${list.ruleDesc }</td>
								
								<td><logic:equal name="list" property="deviationType" value="G">
												MANUAL DEVIATION
										</logic:equal>
										<logic:equal name="list" property="deviationType" value="A">
												AUTO DEVIATION
										</logic:equal>
										
										</td>
								
								<td><logic:equal name="list" property="ruleAction" value="D">
												DEVIATION
										</logic:equal>
										<logic:equal name="list" property="ruleAction" value="S">
												STOP
										</logic:equal>
										
										</td>
								
								<td>${list.approvalLevel }</td>
								<td>${list.remark }</td>	
								
								<td><input type="text" id="manualRemark<%=count.intValue()+1 %>" class="text" name="manualRemark" value="" maxlength="100" /> </td>
								
													
								
								<%-- <td>
							     		<input type="checkbox" name="manRecStatus" id="recStatuses<%=count.intValue()+1 %>" checked="checked"  />
								</td> --%>
							    
							</tr>	
							</logic:iterate>
							
								
								
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
	
	<tr><td> <button type="button" name="button" value="Forward" class="topformbutton2" onclick="loanDeviationApproval();" title="Alt+F" accesskey="F"><bean:message key="button.approve" /></button>
	 <button type="button" name="button" value="SendBack" class="topformbutton2" onclick="loanDeviationSendBack();" title="Alt+B" accesskey="B"><bean:message key="button.sendBack" /></button> </td>
	</tr>
	</table>
	
	  </fieldset>
  </html:form>
  	
  </logic:present>
 </logic:notPresent>
 <logic:present name="viewDevJSP"> 
 
 	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	   <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Deviation Author</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
  	
  	<html:form action="/saveLoanDeviationActionProcess" method="post" styleId="deviationApprovalForm">
 	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" />

	<fieldset>
	  <legend><bean:message key="lbl.manualDeviation"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td>
		   <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
			
			<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridTable">
							<tr class="white2">
                                
								<td><b><bean:message key="lbl.stage" /></b></td>								
								<td><b><bean:message key="lbl.ruleDescription" /></b></td>
								<td><b><bean:message key="lbl.deviationType" /></b></td>			
								<td><b><bean:message key="lbl.action" /></b></td>	
								<td><b><bean:message key="lbl.approvalLevel" /></b></td>
								<td><b><bean:message key="lbl.makerRemarks" /></b></td>	
								<td><b><bean:message key="lbl.remark" /></b></td>	
								<%-- <td><b><bean:message key="lbl.approve" /></b></td>	--%>
								
							</tr>

							<logic:present name="manualDevList">
							<logic:iterate id="list" name="manualDevList" indexId="count">
							<tr class="white1">
							    <input type="hidden" id="loanDeviationId" class="text" name="loanDeviationId" value="${list.loanDevId}"/> 
								<td><input type="hidden" id="manualDeviId" class="text" name="manualDeviId" value="${list.manualDevId}"/> 
									   LOAN INITIATION MAKER                                 
							    </td>
								<td><input type="hidden" id="policyDecisionIds" class="text" name="policyDecisionIds" value="${list.policyDecisionId }"/>
								${list.ruleDesc }</td>
								<td><logic:equal name="list" property="deviationType" value="G">
												MANUAL DEVIATION
										</logic:equal>
										<logic:equal name="list" property="deviationType" value="A">
												AUTO DEVIATION
										</logic:equal>
										</td>
								<td><logic:equal name="list" property="ruleAction" value="D">
												DEVIATION
										</logic:equal>
										
										<logic:equal name="list" property="ruleAction" value="S">
												STOP
										</logic:equal>
										
										</td>
								
								<td>${list.approvalLevel }</td>
								<td>${list.remark }</td>								
								<td><input type="text" id="manualRemark<%=count.intValue()+1 %>" class="text" name="manualRemark" value="${list.authorRemark }" readonly="readonly" maxlength="100" /> </td>
								<%--
									 <td>
							     		<input type="checkbox" name="manRecStatus" id="recStatuses<%=count.intValue()+1 %>" checked="checked" disabled="disabled" />
								</td>
								 --%>
							    
							</tr>	
							</logic:iterate>
							
								
								
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
	
	
	</table>
	
	  </fieldset>
  </html:form>
 
 </logic:present>
</div>

</div>

<script>
	parent.menu.document.test.checkModifications.value = '';
	parent.menu.document.test.getFormName.value = "deviationApprovalForm";
</script>
</body>
<logic:present name="sms">
<script type="text/javascript">
 if('<%=request.getAttribute("sms")%>'=='Saved')
	{
		alert("<bean:message key="lbl.dataSave" />");
	
	}
	else if('<%=request.getAttribute("sms")%>'=='notSaved')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
	
	}
	else if('<%=request.getAttribute("sms")%>'=='A')
	{
		alert("<bean:message key="lbl.dataSave" />");
		parent.location="<%=request.getContextPath()%>/creditListAction.do?method=searchLinkForDeviationAuthor";
	
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.location="<%=request.getContextPath()%>/creditListAction.do?method=searchLinkForDeviationAuthor";
	
	}
	
</script>
</logic:present>
</html:html>


