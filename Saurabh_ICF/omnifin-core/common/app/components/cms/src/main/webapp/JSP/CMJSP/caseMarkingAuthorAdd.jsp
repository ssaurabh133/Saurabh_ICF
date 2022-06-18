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
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/caseMarkingAuthor.js"></script>
	
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
 <input type="hidden" name ="updateForwardFlag" id="updateForwardFlag" />
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<!--end  -->
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	
	<html:form action="/caseMarkingAuthorDispatch" method="post" styleId="caseMarkingAuthorForm">
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<fieldset>	  
	
		<legend>
		<bean:message key="lbl.caseMarkingAuthor"/>
		</legend>
       
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
	
		<tr>
		<td width="20%"><bean:message key="lbl.loanNo"/></td>
		
			 
			 <td width="35%">
			<html:text property="searchLoanNo" styleClass="text" styleId="searchLoanNo" maxlength="10" readonly="true"  tabindex="-1" value="${list[0].searchLoanNo}"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${list[0].lbxDealNo}"/>
		

</td>

	  <td width="17%"><bean:message key="lbl.customerName"/></td>
	   
		<td width="28%" >
		<html:text property="searchCustomerName" tabindex="2" readonly="true" styleClass="text" styleId="searchCustomerName" maxlength="50" value="${list[0].searchCustomerName}"/>
		</td> 
		
  </tr><!--
	  

		 --><tr>
		  
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
			 	 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" disabled="disabled"/></td>
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
		<td><logic:equal property="chk" name="sublist" value="Y">
  <input type="checkbox" name="chk"  id="chk<%=counter.intValue()+1%>" checked="checked" value="${sublist.chk}" disabled="disabled" />
  </logic:equal>
  			<logic:notEqual property="chk" name="sublist" value="Y">
  		<input type="checkbox" name="chk"  id="chk<%=counter.intValue()+1%>" value="${sublist.chk}"disabled="disabled" />
  		</logic:notEqual>
		<input type="hidden" name="caseId"  id="caseId<%=counter.intValue()+1%>" value="${sublist.caseId}" /></td>
		<td>
	
			<input type="text" name="caseStatus" id="caseStatus<%=counter.intValue()+1%>" value="${sublist.caseStatus}" class="text" readonly="true"/>
		
		</td>
		
						
	<td>
	
	
	<input type="text" name="markingDate"  id="markingDate<%=counter.intValue()+1%>" value="${sublist.markingDate} " readonly="true"  class="text" onchange="return checkDate('markingDate<%=counter.intValue()+1%>');"/>

	</td>
	<td>
	<input type="text" name="agency" id="agency<%=counter.intValue()+1%>" value="${sublist.agency}" class="text" readonly="true"/>
	<input type="hidden" name="agencyId" id="agencyId<%=counter.intValue()+1%>" value="" />
	<input type="hidden" name="lbxAgencyId" id="lbxAgencyId" value="" />
</td>
	<td><textarea class="text" name="otherDetails" id="otherDetails<%=counter.intValue()+1%>" readonly="true" maxlength="500" value="" >${sublist.otherDetails}</textarea></td>
	<td><textarea class="text" name="remarks" id="remarks<%=counter.intValue()+1%>" readonly="true" maxlength="500" value="" >${sublist.remarks}</textarea></td>
	
	</tr>
	
	</logic:iterate>
	
</logic:notEmpty>	

</logic:present>
	
</table>
</td>
</tr>
</table>
		</fieldset>
	<!-- Grid Closed Here -->
	</html:form>
	</div>

 <!-- Grid starts Here -->
    
		


</div>

<logic:present name="message">
 <script type="text/javascript"><!--
	if('<%=request.getAttribute("message")%>'=='S')
	{
		alert("<bean:message key="lbl.dataSaveSuccess" />");
	
		
	}else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert("<bean:message key="lbl.NonDataSave" />");
		
	}
	else if('<%=request.getAttribute("message")%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("updateform").action="caseMarkingMakerDispatch.do?method=search ";
	    document.getElementById("updateform").submit();
	}
  </script>
</logic:present>
<logic:present name="datalist">
 <script type="text/javascript"><!--
	
		alert("<bean:message key="lbl.dataAlreadyMarked" />");
	
  </script>
</logic:present>
</body>
</html:html>