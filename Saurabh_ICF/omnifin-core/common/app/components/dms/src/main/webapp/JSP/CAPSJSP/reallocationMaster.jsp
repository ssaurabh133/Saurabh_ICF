
<!--Author Name : Kanika Maheshwari-->
<!--Date of Creation :15 October 2011  -->
<!--Purpose  : Infomation of Reallocation Search-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/reallocation.js"></script>
<script type="text/javascript" 
		src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>	


<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


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

<body oncontextmenu="return false" onload="enableAnchor();clearInput();fntab();init_fields();">
<html:form styleId="reallocationCollForm"  method="post"  action="/reallocation" >
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<html:errors/>
<fieldset>
<legend><bean:message key="lbl.reallocationmaster" /></legend>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td>
<table border="0" cellpadding="1" cellspacing="1" width="100%">
<tr> 
  
     <td ><bean:message key="lbl.loanAccountNo" /></td>
     <td>  <html:text styleClass="text" property="loanno" styleId="loanno"   readonly="true" value="${requestScope.lastval.loanno}" />
    <html:button property="loanSearchButton" styleId="loanSearchButton" onclick="openLOVCommon(16087,'reallocationCollForm','loanno','','', '','','','lbxLoanno');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
<html:hidden property="lbxLoanno" styleId="lbxLoanno"  />
     </td>
    <td ><bean:message key="lbl.product" /></td>
     
     
      <td><html:select  property="product" styleClass="text"  styleId="product" value="${requestScope.lastval.product}" >
       <html:option value="" >--Select--</html:option>
           <logic:present name="productList">
     <logic:notEmpty  name="productList">
       <html:optionsCollection name="productList" label="productval" value="productid"  />
     </logic:notEmpty>
       </logic:present>
       </html:select>
     </td>
      <td ><bean:message key="lbl.customername"/></td>

        <td>  <html:text property="customername" styleClass="text"  styleId="customername" maxlength="50" value="${requestScope.lastval.customername}"  />
      
     </td>
     
     
     </tr>
    
    <tr>
    <td><bean:message key="lbl.DPD1" /></td>
     
      <td><html:text property="dpd2" styleClass="text" style="text-align: right" styleId="dpd2" maxlength="5" value="${requestScope.lastval.dpd2}" onkeypress="return isNumberKey(event)"   />
  
     </td>
    <td><bean:message key="lbl.DPD2" /></td>
      <td><html:text property="dpd1" styleClass="text" style="text-align: right" styleId="dpd1" maxlength="5"  value="${requestScope.lastval.dpd1}" onkeypress="return isNumberKey(event)"   />
  
     </td>
      
    <td ><bean:message key="lbl.queue" /></td>
     <td><html:text property="queue" styleClass="text"  styleId="queue" maxlength="30"  readonly="true"  value="${requestScope.lastval.queue}"  />

      <html:button property="queueButton" styleId="queueButton" onclick="openLOVCommon(189,'reallocationCollForm','queue','','', '','','','lbxQueuesearchId')" value=" " styleClass="lovbutton"></html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxQueuesearchId" styleId="lbxQueuesearchId" />
     </td>
     </tr>
    
    <tr>
  <td><bean:message key="lbl.overDueAmount1" /></td>
       
      <td><html:text property="pos2" styleClass="text" style="text-align: right" styleId="pos2"  maxlength="18" value="${requestScope.lastval.pos2}" 
        onkeypress="return numbersonly(event,id,18)" 
		onblur="formatNumber(this.value,id);"
		onkeyup="checkNumber(this.value, event, 18,id);" 
		onfocus="keyUpNumber(this.value, event, 18,id);"  />
  
     </td>
    <td><bean:message key="lbl.overDueAmount2" /></td>
      <td><html:text property="pos1" styleClass="text" style="text-align: right" styleId="pos1"  maxlength="18" value="${requestScope.lastval.pos1}" 
        onkeypress="return numbersonly(event,id,18)" 
		onblur="formatNumber(this.value,id);"
		onkeyup="checkNumber(this.value, event, 18,id);" 
		onfocus="keyUpNumber(this.value, event, 18,id);"  />
      </td>
    
      <td><bean:message key="lbl.iduser" /></td>
    <td> <html:text property="user" styleClass="text"  styleId="user"  readonly="true" value="${requestScope.lastval.user}"  />
    <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(286,'reallocationCollForm','user','','', '','','','lbxUserSearchId')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId"  />
     </td>
     </tr>
    

    
    <tr>
         <td ><bean:message key="lbl.Type" /></td>
       <td >   <html:select  property="custype" styleClass="text"  styleId="custype" value="${requestScope.lastval.custype}"  >
       <html:option value="" >--Select--</html:option>
              <html:option value="I" >INDIVIDUAL</html:option>
              <html:option value="C" >CORPORATE</html:option>
       </html:select>
      </td>
         <td ><bean:message key="lbl.Category" /></td>
       <td> <html:select  property="custcategory" styleClass="text"  styleId="custcategory" value="${requestScope.lastval.custcategory}"  >
       <html:option value="" >--Select--</html:option>
     
       <logic:present name="customercatList">
     <logic:notEmpty  name="customercatList">
       <html:optionsCollection name="customercatList" label="cstcat" value="cstcatval" />
     </logic:notEmpty>
       </logic:present>
           
       </html:select>
      </td>
       <td ><bean:message key="lbl.balancePrincipal" /></td>
       <td><html:text property="balanceprincipal" styleClass="text" style="text-align: right" styleId="balanceprincipal"   maxlength="18" value="${requestScope.lastval.balanceprincipal}"  
        onkeypress="return numbersonly(event,id,18)" 
		onblur="formatNumber(this.value,id);"
		onkeyup="checkNumber(this.value, event, 18,id);" 
		onfocus="keyUpNumber(this.value, event, 18,id);"   />
      </td>
      
      </tr>
    
    <tr>
    
     <td ><bean:message key="lbl.assigndatefrom" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
       <td>
       <html:text property="assignfrom" tabindex="8"  styleClass="text"  styleId="assignfrom"  onchange="return checkDate('assignfrom');" value="${requestScope.lastval.assignfrom}"  />
 </td>  <td ><bean:message key="lbl.assigndateto" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
       <td>
        <html:text property="assignto" tabindex="8"  styleClass="text"  styleId="assignto"  onchange="return checkDate('assignto');" value="${requestScope.lastval.assignto}"  />
    
      </td>
     </tr>
     
    <tr> 
    <td>
    <!--   <input type="button"  value="Search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="this.disabled='true';reAllocationSearch('<bean:message key="lbl.selectAtleast" />');" />-->
    
<button type="button" class="topformbutton2" id="search"  onclick="this.disabled='true';return reAllocationSearch('<bean:message key="lbl.selectAtleast" />');"
 title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
  </td> 
  
 
  </tr>
</table>
</td>
</tr>
</table>
</fieldset>
</br>

<fieldset>
<legend><bean:message key="lbl.reallocationDetailSearch" /></legend>

<logic:present name="list">

  	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/reallocationSearch.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="checkBoxDis" title="Select"  sortable="true"  />
    <display:column property="gloanno" titleKey="lbl.loanAccountNo"  sortable="true"  />
	<display:column property="gdpd" titleKey="lbl.DPD"  sortable="true"  />
	<display:column property="gcustomername" titleKey="lbl.customername"  sortable="true"  />
	<display:column property="gpos" titleKey="lbl.overduePrincipal"  sortable="true"  />
<display:column property="gqueue" titleKey="lbl.queue"  sortable="true"  />
	<display:column property="gassignto" titleKey="lbl.assignedto"  sortable="true"  />
	<display:column property="gallocationdate" titleKey="lbl.allocationDate"  sortable="true"  />
	<display:column property="gqueuedate" titleKey="lbl.queuedate"  sortable="true"  />
	</display:table>

   
<table>
    <tr><td> <bean:message key="lbl.assignedto" /><span><font color="red">*</font></span></td>

      <td> <html:text property="heirarchyuser" styleClass="text"  styleId="heirarchyuser"  readonly="true" value="${requestScope.lastval.user}"  />
     <input type="hidden" name="sesuser" id="sesuser" value="${sessionScope.userobject.userId }" />
    <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(239,'reallocationCollForm','heirarchyuser','sesuser|sesuser','lbxHierarchyUserId','sesuser|sesuser','Please Select User','','lbxHierarchyUserId')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxHierarchyUserId" styleId="lbxHierarchyUserId" value="" />
     </td>
      <td><bean:message key="lbl.remark" /><span><font color="red">*</font></span></td>
      <td><html:textarea property="remarks" styleClass="text"  styleId="remarks"  value="" /></td>
   </tr>
 	<tr><td>  
 	<!--<html:button property="save" value="Save" styleId="save" styleClass="topformbutton2" title="Alt+A" accesskey="A" onclick="this.disabled='true';savereAllocation();"></html:button>-->
 	 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="this.disabled='true';savereAllocation();"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
 	</td>
    </tr>
</table>

</logic:present>

<logic:notPresent name="list">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tbody><tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tbody><tr align="center" class="white2">
<td><b>Select</b></td>
<td><b><bean:message key="lbl.loanAccountNo" /></b></td>
<td><b><bean:message key="lbl.DPD" /></b></td>
<td><b><bean:message key="lbl.customername" /></b></td>
<td><b><bean:message key="lbl.overduePrincipal" /></b></td>
<!--<td><b><bean:message key="lbl.totalOutstanding" /></b></td>-->
<td><b><bean:message key="lbl.queue" /></b></td>
<td><b><bean:message key="lbl.assignedto" /></b></td>
<td><b><bean:message key="lbl.allocationDate" /></b></td>
<td><b><bean:message key="lbl.queuedate" /></b></td>
</tr>
</tbody></table>
</td>
</tr></tbody></table>
</logic:notPresent>
</fieldset>
	</html:form>		
	<logic:present name="sms">
<script type="text/javascript">

	if('<%=request.getAttribute("sms").toString()%>'=='N')
	{
		alert("<bean:message key="lbl.noDataFound" />");

	}
	
	</script>
</logic:present>
	<logic:present name="flagg">
<script type="text/javascript">

	if('<%=request.getAttribute("flagg").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");

	}
	
	</script>
</logic:present>
	
<logic:present name="result">
<script type="text/javascript">
	
	 if('<%=request.getAttribute("result").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
	
		}
		
	else if('<%=request.getAttribute("result").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.erroruSuccess" />");
		
	}

</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>			
  </body>
		</html:html>