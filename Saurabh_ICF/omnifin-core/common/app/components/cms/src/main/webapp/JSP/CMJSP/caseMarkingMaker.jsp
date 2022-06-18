<!--
 	Author Name      :- Richa Bansal
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Case Marking
 	Documentation    :- 
 -->
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
		 		
		  <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/caseMarkingMaker.js"></script>
	
		<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
		
       
			  <%
			ResourceBundle resource =  ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
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
	<body  oncontextmenu="return false" onload="enableAnchor(); disable();">
	
	 <div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
	 <input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
<!--start  --> 
<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
    </logic:present>
<logic:notPresent name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>

    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	
	<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
	
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<!--end  -->
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	
	<html:form action="caseMarkingMakerDispatch.do" method="post" styleId="caseMarkingMakerForm">
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<input type="hidden" name="recStatus" id="recStatus" value="${list[0].statusCase}" />
	<input type="hidden" name="canForward" value="${requestScope.canForward}" id="canForward" />
	<fieldset>	  
	
		<legend>
		<bean:message key="lbl.caseMarkingMaker"/>
		</legend>
       
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
	
		<tr>
		<td width="20%"><bean:message key="lbl.loanNo"/></td>
		<html:hidden  property="statusCase" styleId="statusCase" value="${list[0].statusCase}"/>
			 <logic:notPresent name="editVal">
			 <td width="35%">
			<html:text property="searchLoanNo" styleClass="text" styleId="searchLoanNo" maxlength="10" readonly="true"  tabindex="-1" value="${list[0].searchLoanNo}"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${list[0].lbxDealNo}"/>
		<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(554,'caseMarkingMakerForm','searchLoanNo','','', '','','','searchCustomerName')" value=" " styleClass="lovbutton"></html:button>

</td>
		 </logic:notPresent>
		 
			<logic:present name="editVal">
			 <td width="35%">
			<html:text property="searchLoanNo" styleClass="text" styleId="searchLoanNo" maxlength="10" readonly="true"  tabindex="-1" value="${list[0].searchLoanNo}"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${list[0].lbxDealNo}"/>
			

		</td>
	</logic:present>
		 <td width="17%"><bean:message key="lbl.customerName"/></td>
	   <logic:notPresent name="editVal">
		<td width="28%" >
		<html:text property="searchCustomerName" tabindex="2"  styleClass="text" styleId="searchCustomerName" maxlength="50" value="${list[0].searchCustomerName}"/>
		</td> 
		</logic:notPresent>
		<logic:present name="editVal">
		<td width="28%" >
		<html:text property="searchCustomerName" tabindex="2"  styleClass="text" styleId="searchCustomerName" maxlength="50" value="${list[0].searchCustomerName}" readonly="true"/>
		</td> 
		</logic:present>
	   
	    </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	 
	</fieldset>
	
	<fieldset>
			<legend>
				<bean:message key="lbl.caseMarkingMakerDetails" />
			</legend>
			 
    
  <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<input type="hidden" name="psize" id="psize" />
			<input type="hidden" name="pcheck" id="pcheck" />
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" /></td>
				 <td ><b><bean:message key="lbl.caseStatus" /></b></td>
				 <td ><b><bean:message key="lbl.markingDate" /></b></td>
				 <td ><b><bean:message key="lbl.agencyName" /></b></td>
				 <td ><b><bean:message key="lbl.otherDetails" /></b></td>
				 <td ><b><bean:message key="lbl.remarks" /></b></td>
      		</tr>
      		
<logic:present name="list">
<logic:notEmpty name="list">	 
	<logic:iterate name="list" id="sublist" indexId="counter">
	<tr class="white1">
		<td>
		
			<logic:equal property="chk" name="sublist" value="Y">
  <input type="checkbox" name="chk"  id="chk<%=counter.intValue()+1%>" checked="checked" value="${sublist.chk}" />
  </logic:equal>
  			<logic:notEqual property="chk" name="sublist" value="Y">
  		<input type="checkbox" name="chk"  id="chk<%=counter.intValue()+1%>" value="${sublist.chk}" />
  		</logic:notEqual>
		
	<input type="hidden" name="caseId"  id="caseId<%=counter.intValue()+1%>" value="${sublist.caseId}" /></td>
<td>

			<input type="text" name="caseStatus" id="caseStatus<%=counter.intValue()+1%>" value="${sublist.caseStatus}" class="text" readonly="true"/>
</td>
<td>
			<input type="text" name="markingDate"  id="markingDate<%=counter.intValue()+1%>" value="${sublist.markingDate}" class="text" readonly="readonly"/>
	<script type="text/javascript">
               				$(function() {
								var contextPath =document.getElementById('contextPath').value ;
								$("#markingDate"+<%=counter.intValue()+1%>).datepicker({
								format: "%Y-%m-%d %H:%i:%s %E %#",
								formatUtcOffset: "%: (%@)",
								placement: "inline",
								changeMonth: true,
								changeYear: true,
								yearRange: '1900:+10',
								showOn: 'both',
								buttonImage: contextPath+'/images/theme1/calendar.gif',
								buttonImageOnly: true,
					        	dateFormat: document.getElementById("formatD").value,
								defaultDate: document.getElementById("bDate").value
								});
							});
               			</script>
	</td>
	<td>
	<input type="text" name="agency" id="agency<%=counter.intValue()+1%>" value="${sublist.agency}" class="text" readonly="true"/>
	<input type="hidden" name="agencyId" id="agencyId<%=counter.intValue()+1%>" value="" />
	<input type="hidden" name="lbxAgencyId" id="lbxAgencyId" value="" />
	<button type="button"  name="agencyButton" id="agencyButton<%=counter.intValue()+1%>" onclick="closeLovCallonLov1();openLOVCommon(1605,'caseMarkingMakerForm','agency<%=counter.intValue()+1%>','','','','','','agencyId<%=counter.intValue()+1%>');" value=" " class="lovbutton"/> 
	
	</td>
	<td><textarea class="text" name="otherDetails" id="otherDetails<%=counter.intValue()+1%>" maxlength="500" value="" >${sublist.otherDetails}</textarea></td>
	<td><textarea class="text" name="remarks" id="remarks<%=counter.intValue()+1%>" maxlength="500" value="" >${sublist.remarks}</textarea></td>
	
	</tr>
	</logic:iterate>
	</logic:notEmpty>

	
	<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="7">
			<button type="button"  title="Alt+A" accesskey="A" onclick="return updateCrCaseMarkingDetails();" class="topformbutton2"><bean:message key="button.save" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" onclick="return forwardCaseMarking();"><bean:message key="button.forward" /></button>
			<logic:present name="editVal">
          	   	<button type="button" id="delete" class="topformbutton2" onclick="return deleteCaseMarking();" title="Alt+D" accesskey="D">
				<bean:message key="button.delete" />
				</button>
          	</logic:present>
<!--			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.delete" /></button>-->
		</td>
	</tr>
	
</table>
	</logic:present>

     		
<logic:present name="list1">
<logic:notEmpty name="list1">
	 
	<logic:iterate name="list1" id="sublist1" indexId="counter">
	<tr class="white1">
		<td><input type="checkbox" name="chk"  id="chk<%=counter.intValue()+1%>" />
		<input type="hidden" name="caseId"  id="caseId<%=counter.intValue()+1%>" />
</td>
		<td>
			<input type="hidden" name="caseStatus" id="caseStatus<%=counter.intValue()+1%>" value="${sublist1.id}" class="text"/>
			${sublist1.name}
		</td>
		
						
	<td>	
		<input type="text" name="markingDate" id="markingDate<%=counter.intValue()+1%>" value='${userobject.businessdate}' class="text" readonly="readonly"/>
		<script type="text/javascript">
               				$(function() {
								var contextPath =document.getElementById('contextPath').value ;
								$("#markingDate"+<%=counter.intValue()+1%>).datepicker({
								format: "%Y-%m-%d %H:%i:%s %E %#",
								formatUtcOffset: "%: (%@)",
								placement: "inline",
								changeMonth: true,
								changeYear: true,
								yearRange: '1900:+10',
								showOn: 'both',
								buttonImage: contextPath+'/images/theme1/calendar.gif',
								buttonImageOnly: true,
					        	dateFormat: document.getElementById("formatD").value,
								defaultDate: document.getElementById("bDate").value
								});
							});
               			</script>
	</td>
	<td>
	<input type="text" name="agency" id="agency<%=counter.intValue()+1%>" value="" class="text" readonly="true"/>
	<input type="hidden" name="agencyId" id="agencyId<%=counter.intValue()+1%>" value="" />
	<input type="hidden" name="lbxAgencyId" id="lbxAgencyId" value="" />
	<button type="button"  name="agencyButton" id="agencyButton<%=counter.intValue()+1%>" onclick="closeLovCallonLov1();openLOVCommon(1605,'caseMarkingMakerForm','agency<%=counter.intValue()+1%>','','','','','','agencyId<%=counter.intValue()+1%>');" value=" " class="lovbutton"/> 
	
	</td>
	<td><textarea class="text" name="otherDetails" id="otherDetails<%=counter.intValue()+1%>" maxlength="500" value="" ></textarea></td>
	<td><textarea class="text" name="remarks" id="remarks<%=counter.intValue()+1%>" maxlength="500" value="" ></textarea></td>
	
	</tr>
	
	</logic:iterate>

</logic:notEmpty>	
	<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="7">
			<button type="button"  title="Alt+A" accesskey="A" onclick="return insertCrCaseMarkingDetails();" class="topformbutton2"><bean:message key="button.save" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" onclick="return forwardCaseMarking();"><bean:message key="button.forward" /></button>
          	<logic:present name="editVal">
          	   	<button type="button" id="delete" class="topformbutton2" onclick="return deleteCaseMarking();" title="Alt+D" accesskey="D">
				<bean:message key="button.delete" />
				</button>
          	</logic:present>
		</td>
	</tr>
	
</table>

</logic:present>
	
</table>
</td>
</tr>
</table>
		</fieldset>
	
	</html:form>
	</div>

</div>

<logic:present name="message">
 <script type="text/javascript"><!--
 
 if('<%=request.getAttribute("message")%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		//document.getElementById("caseMarkingMakerForm").action="caseMarkingMakerBehind.do";
	    document.getElementById("caseMarkingMakerForm").action="caseMarkingMakerDispatch.do?method=openEditCaseMarkingMaker&loanId="+<%=request.getAttribute("loanId")%>;
	    document.getElementById("caseMarkingMakerForm").submit();
	}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert("<bean:message key="lbl.NonDataSave" />");
		
	}
	else if('<%=request.getAttribute("message")%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		//document.getElementById("caseMarkingMakerForm").action="caseMarkingMakerDispatch.do?method=search ";
		  document.getElementById("caseMarkingMakerForm").action="caseMarkingMakerDispatch.do?method=openEditCaseMarkingMaker&loanId="+<%=request.getAttribute("loanId")%>;
  		 document.getElementById("caseMarkingMakerForm").submit();
	}
	
	else if('<%=request.getAttribute("message")%>'=='F')
	{
		alert("<bean:message key="lbl.forwardSuccess" />");
		document.getElementById("caseMarkingMakerForm").action="caseMarkingMakerDispatch.do?method=search ";
	    document.getElementById("caseMarkingMakerForm").submit();
	}
  </script>
</logic:present>
<logic:present name="datalist">
 <script type="text/javascript"><!--
	
		alert("<bean:message key="lbl.dataAlreadyMarked" />");
	
  </script>
</logic:present>
<logic:present name="deleteMsg">
 <script type="text/javascript">
 
	if('<%=request.getAttribute("deleteMsg")%>'=='Y')
	{
	    alert("<bean:message key="lbl.dataDeleted" />");
		location.href='<%=request.getContextPath()%>/caseMarkingMakerDispatch.do?method=search';
	}
	else
	{
		alert("<bean:message key="lbl.dataNtDeleted" />");
		location.href='<%=request.getContextPath()%>/caseMarkingMakerDispatch.do?method=search';
	}
	
  </script>
</logic:present>

</body>
</html:html>