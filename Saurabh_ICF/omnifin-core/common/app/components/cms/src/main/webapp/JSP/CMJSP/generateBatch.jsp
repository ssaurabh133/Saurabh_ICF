<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 20-Apr-2012-->
<!--Documentation : -->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<%
			String path=request.getContextPath();
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no=Integer.parseInt(resource.getString("msg.pageSizeGenerateBatch"));
			request.setAttribute("no",no);
		%>	
<logic:present name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript"  src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript"  src="<%=path%>/js/cmScript/generateBatch.js"></script>
		
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

<script type="text/javascript">
$(function() 
{
	$("#prestDate").datepicker
	({
		changeMonth: true,
		changeYear: true,
		yearRange: '1900:+10',
		showOn: 'both',
		<logic:present name="image">buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',</logic:present>
    	<logic:notPresent name="image">buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',</logic:notPresent>
		buttonImageOnly: true,
		dateFormat:"<bean:message key="lbl.dateFormat"/>",
		defaultDate:'${userobject.businessdate }'
	});
});

</script> 
	
	
</head>		
<body onload="enableAnchor();">
	<html:form action="/generateBatchDispatchAction" styleId="generateBatch">
		<fieldset><legend><bean:message key="lbl.generateBatch"/></legend>  
				<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
				<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
				<html:hidden property="finalDate" styleId="finalDate" value="${record[0].finalDate}"/>
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr><td><bean:message key="lbl.paymentMode"/><span><font color="red">*</font></span></td>   
					    <td>
					    	<html:select property="paymentMode" styleId="paymentMode" styleClass="text" value="${record[0].paymentMode}" >	
								<html:option value="">----Select----</html:option>
								<html:option value="Q">Cheque</html:option>	
								<html:option value="DIR">Direct Debit</html:option>							
								<html:option value="E">ECS</html:option>
								<html:option value="H">NACH</html:option>
								
							</html:select>
				 		</td>
				 		<td><bean:message key="lbl.presentationDate"/></td>
						<td><html:text property="prestDate" styleClass="text" styleId="prestDate" value="${record[0].prestDate}" maxlength="10" onchange="checkDate('prestDate');convertDate()"></html:text></td>
			 		</tr>					
					<tr>
						<td><bean:message key="lbl.clearingType"/><span><font color="red">*</font></span></td>
						<td>
							<html:select property="clearingType" styleId="clearingType" styleClass="text" value="${record[0].clearingType}">
                 			<logic:present name="clearingTypeList" > 
                 			<html:option value="">--Select--</html:option>         	
          	     			<html:optionsCollection name="clearingTypeList" label="clTypeDesc" value="clTypeCode" />
                 			</logic:present>
          		 			</html:select>
          		 							    
				 		</td>
						<td><bean:message key="lbl.batchSize"/></td>
						<td><html:text property="batchSize" styleClass="text" styleId="batchSize" value="${record[0].batchSize}" maxlength="4" onkeypress="return isNumberKey(event);" style="text-align: right"></html:text></td>
						
					</tr>
					<tr>
						<td><bean:message key="lbl.branch"/></td>
						<td>
							<html:text property="branchid" styleClass="text" styleId="branchid" maxlength="100" readonly="true" value="${record[0].branchid}"/>
							<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="${record[0].lbxBranchId}" />
							<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(12,'generateBatch','branchid','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"  ></html:button>
						</td>
						<td><bean:message key="lbl.dealCat" /></td>
        				<td>
        					<html:select property="loanCategory" styleId="loanCategory" styleClass="text" value="${record[0].loanCategory}"  >
          	  					<html:option value="" >----Select----</html:option>
          	  					<logic:present name="dealCatList">
          	  						<html:optionsCollection name="dealCatList" label="name" value="id" />
          	  					</logic:present>
          	 				</html:select>
        	        	
        				</td>
				   </tr>
				   <tr>
				   		<td><bean:message key="lbl.batch"/></td>
						<td>
							<html:text property="batchNo" styleClass="text" styleId="batchNo" maxlength="100" readonly="true" value="${viewBatchList[0].batchNo}"/>
							<html:hidden property="lbxBatchNo" styleId="lbxBatchNo" value="${viewBatchList[0].batchNo}" />
							<html:hidden property="loanNo" styleId="loanNo" value="" />
							<input type="hidden" id="status" value="${viewBatchList[0].recordStatus}"/>
							<html:hidden property="instrumentId" styleId="instrumentId" value="" />
							<html:button property="batchButton" styleId="batchButton" tabindex="1" onclick="openLOVCommon(333,'generateBatch','batchNo','finalDate','','finalDate','','','status')" value=" " styleClass="lovbutton"  ></html:button>
						</td>
						<td>
							<bean:message key="lbl.totalNoInstrument"/>
						</td>
						
						<td>
						    <html:text property="totalNoInstrument" styleClass="text" styleId="totalNoInstrument" readonly="true" value="${totalInstrument}"/>
						</td>
				   </tr>
				   <tr>
				   		<td>
							<bean:message key="lbl.totalInstrumentAmount"/>
						</td>
						
						<td>
						    <html:text property="totalInstrumentAmount" styleClass="text" styleId="totalInstrumentAmount" readonly="true" value="${totalInstrumentAmount}"/>
						</td>
				   </tr>
				</table>
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td>
						<button type="button" name=" mybutton"  title="Alt+M" accesskey="M" class="topformbutton5" onclick="genrateMultiBatch();"><bean:message key="button.multipleBatch" /></button>
						<button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="genrateBatch();"><bean:message key="button.generatebatch" /></button>
						<button type="button" name=" mybutton"  title="Alt+B" accesskey="B" class="topformbutton3" onclick="viewBatch();"><bean:message key="button.viewBatch" /></button>
						<button type="button" name=" mybutton"  title="Alt+P" accesskey="P" class="topformbutton3" onclick="generatePaySlip();"><bean:message key="button.paySlip" /></button>
					</td>
				</tr>
				</table>				
		</fieldset>
		<logic:present name="viewBatchList">
			<fieldset><legend><bean:message key="lbl.viewBatch"/></legend>		
			<logic:notEmpty name="viewBatchList">
			         <input type="hidden" id="gridBatchNo" value="${viewBatchList[0].batchNo}">
			         <input type="hidden" name="instrumentId" id="instrumentId" value="${viewBatchList[0].instrumentId}">
			         <input type="hidden" name="instrumentAmt" id="instrumentAmt" value="${viewBatchList[0].instrumentAmt}">
			        <logic:present name="pending">
					<display:table  id="viewBatchList"  name="viewBatchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${viewBatchList[0].totalRecordSize}" requestURI="/generateBatchDispatchAction.do?method=viewBatch" >
	    				<display:setProperty name="paging.banner.placement"  value="bottom"/>
	    				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					    
					    <display:column property="checkBox" title="<input type='checkbox' name='allchkd'  id='allchkd' onclick='globleCheckBox();globalCalculateInstrumentNOAmount();'/>"  sortable="false" style="text-align: center" />
					    <display:column property="loanNo" titleKey="lbl.loanAccountNo"  sortable="true" style="text-align: center"/>
					    <display:column property="instrumentId" titleKey="lbl.instrumentId"  sortable="true" />
					    <display:column property="instrumentNo" titleKey="lbl.no"  sortable="true" />
					    <display:column property="instrumentMode" titleKey="lbl.instrumentMode"  sortable="true" />
					    <display:column property="instrumentAmt" titleKey="lbl.instrumentAmount"  sortable="true" style="text-align: right"/>
					    <display:column property="instrumentDate" titleKey="lbl.instrumentDate"  sortable="true" style="text-align: center"/>
					    <display:column property="installmentNo" titleKey="lbl.installNo"  sortable="true" />
					    <display:column property="pdcLocation" titleKey="lbl.pdcLocation"  sortable="true" />
					    <display:column property="recordStatus" titleKey="lbl.active"  sortable="true" style="text-align: center" />
					    <display:column property="makerID" titleKey="lbl.maker"  sortable="true" style="text-align: center" />
					    <display:column property="makerDate" titleKey="lbl.makerDate"  sortable="true" style="text-align: center" />
			 		</display:table>
			 		</logic:present>
			 		<logic:present name="finalized">
					<display:table  id="viewBatchList"  name="viewBatchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${viewBatchList[0].totalRecordSize}" requestURI="/generateBatchDispatchAction.do?method=viewBatch" >
	    				<display:setProperty name="paging.banner.placement"  value="bottom"/>
	    				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					    
					    <display:column property="checkBoxDis" title="<input type='checkbox' name='allchkd'  id='allchkd' disabled='disabled'/>" sortable="false" style="text-align: center" />
					    <display:column property="loanNo" titleKey="lbl.loanAccountNo"  sortable="true" style="text-align: center"/>
					    <display:column property="instrumentId" titleKey="lbl.instrumentId"  sortable="true" />
					    <display:column property="instrumentNo" titleKey="lbl.no"  sortable="true" />
					    <display:column property="instrumentMode" titleKey="lbl.instrumentMode"  sortable="true" />
					    <display:column property="instrumentAmt" titleKey="lbl.instrumentAmount"  sortable="true" style="text-align: right"/>
					    <display:column property="instrumentDate" titleKey="lbl.instrumentDate"  sortable="true" style="text-align: center"/>
					    <display:column property="installmentNo" titleKey="lbl.installNo"  sortable="true" />
					    <display:column property="pdcLocation" titleKey="lbl.pdcLocation"  sortable="true" />
					    <display:column property="recordStatus" titleKey="lbl.active"  sortable="true" style="text-align: center" />
					    
			 		</display:table>
			 		</logic:present>
			 <table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td>
						<logic:present name="pending">
							<button type="button" name=" mybutton"  title="Alt+D" accesskey="D" class="topformbutton3" onclick="deleatBatchRecord();"><bean:message key="button.delete" /></button>
							<button type="button" name=" mybutton"  title="Alt+n" accesskey="n" class="topformbutton3" onclick="finalizedBatch();"><bean:message key="button.finalizedBatch" /></button>
						</logic:present>
					</td>
				</tr>
				</table>				
			</logic:notEmpty>
			<logic:empty name="viewBatchList">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr class="white2">
								<td><input type='checkbox' name='chkCases'  id='chkCases' disabled="disabled"/></td>
								<td><strong><bean:message key="lbl.loanAccountNo" /> </strong></td>
								<td><strong><bean:message key="lbl.instrumentId" /> </strong></td>
								<td><strong><bean:message key="lbl.no" /> </strong></td>
								<td><strong><bean:message key="lbl.instrumentMode" /> </strong></td>
								<td><strong><bean:message key="lbl.instrumentAmount" /> </strong></td>
								<td><strong><bean:message key="lbl.instrumentDate" /> </strong></td>
								<td><strong><bean:message key="lbl.installNo" /> </strong></td>
								<td><strong><bean:message key="lbl.pdcLocation" /> </strong></td>
								<td><strong><bean:message key="lbl.active" /> </strong></td>
								<td><strong><bean:message key="lbl.maker" /> </strong></td>
								<td><strong><bean:message key="lbl.makerDate" /> </strong></td>
						</tr>
						<tr class="white2">
							<td colspan="12"><bean:message key="lbl.noDataFound" /></td>
						</tr>
						</table>
					</td>
				</tr>				
				</table>
			</logic:empty>
		</logic:present>
			
	</html:form>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>	

<logic:present name="error">
	<script type="text/javascript">	
		alert('${error}');
	</script>
</logic:present>
<logic:present name="empty">
	<script type="text/javascript">	
		alert("Data not Found.");
	</script>
</logic:present>
<logic:present name="success">
	<script type="text/javascript">	
		alert("Batch is generated Successfully.");
	</script>
</logic:present>
<logic:present name="finalized">
	<script type="text/javascript">	
		if(${finalized})
			alert("Batch is finalized Successfully.");
		else
			alert("Batch is not finalized.");
	</script>
</logic:present>
<logic:present name="delete">
	<script type="text/javascript">	
		if(${delete})
			alert("Selected record is deleted Successfully.");
		else
			alert("Selected record is not deleted.");
	</script>
</logic:present>
</body>
</html>
