
<!--Author Name : Nazia-->
<!--Date of Creation :29 june 2013  -->
<!--Purpose  : Adhoc Contact Recording Search Page-->
<!--Documentation : -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

				
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/adhocContactRecordingScript.js"></script>
<script type="text/javascript" 
		src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>


<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
<script type="text/javascript">
	function fntab(){
    document.getElementById('loanSearchButton').focus();
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

<body oncontextmenu="return false" onload="enableAnchor();fntab();init_fields();">
<html:form styleId="contactRecordingSearch"  method="post"  action="/AdhocContactRecordingBehindAction" >
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		
	<input type="hidden" name="searchFlag" id="searchFlag" />
<html:errors/>
<fieldset>
<legend><bean:message key="lbl.adhocContactRecordingSearch" /></legend>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td>
<table border="0" cellpadding="1" cellspacing="1" width="100%">
<tr> 
  
     <td ><bean:message key="lbl.loanAccountNo" /></td>
     <td>  <html:text styleClass="text" property="loanno" styleId="loanno"   readonly="true" />
    <html:button property="loanSearchButton" styleId="loanSearchButton" onclick="openLOVCommon(16087,'contactRecordingSearch','loanno','','', '','','','lbxLoanno');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
<html:hidden property="lbxLoanno" styleId="lbxLoanno"  />
     </td>
    <td ><bean:message key="lbl.product" /></td>
     
     
      <td><html:select  property="product" styleClass="text"  styleId="product"  >
       <html:option value="" >--Select--</html:option>
           <logic:present name="productList">
     <logic:notEmpty name="productList">
       <html:optionsCollection name="productList" label="productval" value="productid" />
     </logic:notEmpty>
       </logic:present>
       </html:select>
     </td>
      <td ><bean:message key="lbl.customername"/></td>

        <td>  <html:text property="customername" styleClass="text"  styleId="customername" maxlength="50"  />
      
     </td>
     
     
     </tr>
    
    <tr>
     <td><bean:message key="lbl.DPD1" /></td>
      <td><html:text property="dpd2" styleClass="text" onkeypress="return isNumberKey(event)" style="text-align: right"  styleId="dpd2" maxlength="5"  />
  
     </td>
    <td><bean:message key="lbl.DPD2" /></td>
      <td><html:text property="dpd1" styleClass="text" onkeypress="return isNumberKey(event)" style="text-align: right"  styleId="dpd1" maxlength="5"     />
  
     </td>
      
    <td ><bean:message key="lbl.queue" /></td>
     <td><html:text property="queue" styleClass="text"  styleId="queue" maxlength="30"  readonly="true"  />

      <html:button property="queueButton" styleId="queueButton" onclick="openLOVCommon(189,'contactRecordingSearch','queue','','', '','','','lbxQueuesearchId')" value=" " styleClass="lovbutton"></html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxQueuesearchId" styleId="lbxQueuesearchId" />
     </td>
     </tr>
    
    <tr>
 
       <td><bean:message key="lbl.overDueAmount1" /></td>
      <td><html:text property="pos2" styleClass="text"  styleId="pos2"   onkeypress="return numbersonly(event,id,18)" 
		onblur="formatNumber(this.value,id);"
		onkeyup="checkNumber(this.value, event, 18,id);" 
		onfocus="keyUpNumber(this.value, event, 18,id);" 
		style="text-align: right"
		 />
  
     </td>
     <td><bean:message key="lbl.overDueAmount2" /></td>
      <td><html:text property="pos1" styleClass="text"  styleId="pos1"    
      onkeypress="return numbersonly(event,id,18)" 
		onblur="formatNumber(this.value,id);"
		onkeyup="checkNumber(this.value, event, 18,id);" 
		onfocus="keyUpNumber(this.value, event, 18,id);"  
		style="text-align: right" />
      </td>
    
      <td><bean:message key="lbl.iduser" /></td>
    <td> <html:text property="user" styleClass="text"  styleId="user"  readonly="true"  />
    <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(286,'AllocationMasterSearchForm','user','','', '','','','lbxUserSearchId')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId"  />
     </td>
     </tr>
   
    <tr>
         <td ><bean:message key="lbl.Type" /></td>
       <td >   <html:select  property="custype" styleClass="text"  styleId="custype" >
       <html:option value="" >--Select--</html:option>
              <html:option value="I" >INDIVIDUAL</html:option>
              <html:option value="C" >CORPORATE</html:option>
       </html:select>
      </td>
         <td ><bean:message key="lbl.Category" /></td>
       <td> <html:select  property="custcategory" styleClass="text"  styleId="custcategory" >
       <html:option value="" >--Select--</html:option>
     
       <logic:present name="customercatList">

     <logic:notEmpty  name="customercatList">

       <html:optionsCollection name="customercatList" label="cstcat" value="cstcatval" />

     </logic:notEmpty>

       </logic:present>
           
       </html:select>
      </td>
       <td ><bean:message key="lbl.balancePrincipal" /></td>
       <td><html:text property="balanceprincipal" styleClass="text"  styleId="balanceprincipal"  
        onkeypress="return numbersonly(event,id,18)" 
		onblur="formatNumber(this.value,id);"
		onkeyup="checkNumber(this.value, event, 18,id);" 
		onfocus="keyUpNumber(this.value, event, 18,id);"  style="text-align: right" />
      </td>
      
      </tr>
    
    <tr>
    
     <td ><bean:message key="lbl.assigndatefrom" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
       <td>
       <html:text property="assignfrom" tabindex="8"  styleClass="text"  styleId="assignfrom"  onchange="return checkDate('assignfrom');"/>
 </td>  <td ><bean:message key="lbl.assigndateto" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
       <td>
        <html:text property="assignto" tabindex="8"  styleClass="text"  styleId="assignto"  onchange="return checkDate('assignto');"/>

      </td>
      
      <td ><bean:message key="lbl.actionDue" /></td>
       <td >   <html:select  property="actionDue" styleClass="text"  styleId="actionDue" >
       <html:option value="" >--Select--</html:option>
              <html:option value="DT" >Due Today</html:option>
              <html:option value="DL" >Due Later</html:option>
              <html:option value="NA" >No Action Taken</html:option>
       </html:select>
     </tr>
     
    <tr> 
    <td>
    <!--   <input type="button"  value="Search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="this.disabled='true';searchContactRecording('<bean:message key="lbl.selectAtleast" />');" />
  -->
  
<button type="button" class="topformbutton2"   id="search"  onclick="searchAdhocContactRecording('<bean:message key="lbl.selectAtleast" />');"
 title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
  </td> 
  
 
  </tr>
</table>
</td>
</tr>
</table> 
</fieldset>
<fieldset>
<legend><bean:message key="lbl.adhocContactRecordingSearchDtl" /></legend>

 <logic:present name="list">

  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}"  cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/AdhocContactRecordingBehindAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
  
    <display:column property="loanno" titleKey="lbl.loanAccountNo"  sortable="true"  />
	<display:column property="customername" titleKey="lbl.customername"  sortable="true"  />
	<display:column property="pos1" titleKey="lbl.overduePrincipal"  sortable="true"  />
	<display:column property="queue" titleKey="lbl.queue"  sortable="true"  />
	<display:column property="assignto" titleKey="lbl.assignedto"  sortable="true"  />
	<display:column property="loanDpdStatus" titleKey="lbl.dpdStatus"  sortable="true"  />
</display:table>
    
 
   </logic:present>

<logic:notPresent name="list">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tbody><tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tbody><tr align="center" class="white2">

<td><b><bean:message key="lbl.loanAccountNo" /></b></td>
<td><b><bean:message key="lbl.customername" /></b></td>
<td><b><bean:message key="lbl.overduePrincipal" /></b></td>
<td><b><bean:message key="lbl.queue" /></b></td>
<td><b><bean:message key="lbl.assignedto" /></b></td>
<td><b><bean:message key="lbl.dpdStatus" /></b></td>

</tr>
</tbody></table>
</td>
</tr></tbody></table>
</logic:notPresent>
</fieldset>
	</html:form>	

	<logic:present name="flagg">
<script type="text/javascript">

	if('<%=request.getAttribute("flagg").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		////document.getElementById("contactRecordingSearch").action="contactRecordingBehindAction.do";
		//document.getElementById("contactRecordingSearch").submit();
	
		
	}
	</script>
</logic:present>	
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>			
  </body>
		</html:html>