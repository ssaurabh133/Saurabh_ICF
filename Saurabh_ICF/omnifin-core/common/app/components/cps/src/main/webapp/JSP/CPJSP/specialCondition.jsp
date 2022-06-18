<!--  
Author Name:- Amit Kumar
Date of Creation:- 30/04/2011
Purpose:-  
-->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		


		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
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
<style type="text/css">
textarea {

color:#000;
font-family:arial,serif;
font-size:13px;
padding-left:2px;
width:700px;
resize:none;
height:50px;
}

</style>
<title><bean:message key="lbl.specialConditionDetails"/></title>
    </head>
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('specialConditionForm');">
	
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
    </div>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	<logic:notPresent name="authorSpecialCondition">
	<logic:notPresent name="viewSpecialCondition">
			<fieldset>
		
		<table cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <tr>
		     <td class="gridtd">
		 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
		        <tr class="white2">
		          <td><b><bean:message key="lbl.dealNo" /></b></td>
		          <td>${dealHeader[0].dealNo}</td>
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
			          <td >Special Condition</td>
		          </tr>
		        </table> 
		</td>
		</tr>
		</table>
		 
		</fieldset>
	<html:form styleId="specialConditionForm" action="/specialConditionSave" method="post">

	<fieldset>
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
	<html:hidden property="specialDealId" styleId="specialDealId" value="${fecthList[0].specialDealId}" />
		  <legend><bean:message key="lbl.specialConditionDetails"/></legend>
<logic:notPresent name = "specialConditionDV">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
			<td ><bean:message key="lbl.specialCondition"/><font color="red">*</font></td>
	    	<td ><textarea name="specialCondition" id="specialCondition" maxlength="2000" >${fecthList[0].specialCondition}</textarea></td>
	    	<td ><bean:message key="lbl.specialCategory"/><font color="red">*</font></td>
	    	<td >
	    		<html:select property="specialCategory" styleId="specialCategory" styleClass="text" value="${fecthList[0].specialCategory}">
	    			<html:option value="INTERNAL">INTERNAL</html:option>
	    			<html:option value="EXTERNAL">EXTERNAL</html:option>
	    		</html:select>
	    	</td>
	    </tr>
	 <logic:equal name="functionId" value="3000296">		
		 <tr>
		   <td >
		    <button type="button" name="saveButton" id="saveButton" onclick="saveSpecialCondition();" title="Alt+V" accesskey="V" class="topformbutton2" ><bean:message key="button.save" /></button>
											
			</td>
		   </tr>
	</logic:equal>
	
	<logic:equal name="functionId" value="3000294">	
		 <tr>
		   <td >
		    <button type="button" name="saveButton" id="saveButton" onclick="saveSpecialCondition();" title="Alt+V" accesskey="V" class="topformbutton2" ><bean:message key="button.save" /></button>
											
			</td>
		   </tr>
	</logic:equal>

	<logic:equal name="functionId" value="500000123">	
		 <tr>
		   <td >
		    <button type="button" name="saveButton" id="saveButton" onclick="saveSpecialCondition();" title="Alt+V" accesskey="V" class="topformbutton2" ><bean:message key="button.save" /></button>
											
			</td>
		   </tr>
		   </logic:equal>


						
		</table>
		
	      </td>
	</tr>	
	</table>
</logic:notPresent>
	<logic:present name = "specialConditionDV">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
							<td ><bean:message key="lbl.specialCondition"/><font color="red">*</font></td>
					    	<td ><textarea name="specialCondition" id="specialCondition" readonly="readonly" maxlength="2000" >${fecthList[0].specialCondition}</textarea></td>
					    	<td ><bean:message key="lbl.specialCategory"/><font color="red">*</font></td>
	    					<td >
	    						<html:select property="specialCategory" styleId="specialCategory" styleClass="text" value="${fecthList[0].specialCategory}" disabled="true">
					    			<html:option value="INTERNAL">INTERNAL</html:option>
	    							<html:option value="EXTERNAL">EXTERNAL</html:option>
					    		</html:select>
					    	</td>
					    </tr>
					</table>
	     		</td>
			</tr>	
		</table>
	</logic:present>  
	</fieldset>
	
	
	
	</div>

<fieldset>	
		 <legend><bean:message key="lbl.specialConditionDetails"/></legend>

  <logic:notPresent name = "specialConditionDV">
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
       	 <td width="3%" ><input type="checkbox"  name="allchk" id="allchk" onclick="allChecked();"></td>
          <td><b><bean:message key="lbl.specialId"/></b></td>
          <td><b><bean:message key="lbl.specialCondition"/></b></td>
          <td ><b><bean:message key="lbl.specialCategory"/></b></td>
        </tr>
        <logic:present name="specialConditionList">
        <logic:iterate name="specialConditionList" id="specialConditionListData">
	<tr class="white1">
	<td ><input type="checkbox"  name="chk" id="chk" value="${specialConditionListData.specialDealId }"/></td>
	 <td><a href="#" onclick="updateSpecialCondition('${specialConditionListData.specialDealId}');">${specialConditionListData.specialDealId}</a></td>
     <td>${specialConditionListData.specialCondition}</td>
     <td>${specialConditionListData.specialCategory}</td>
	 </tr>
	 </logic:iterate>
	 </logic:present>
 </table>    </td>
</tr>
 <logic:present name="specialConditionList">
 <logic:notEmpty name="specialConditionList">
 
<logic:equal name="functionId" value="3000296">
 
<tr>

<td ><button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteSpecialCondition();"><bean:message key="button.delete" /></button></td>

</tr>
</logic:equal>

<logic:equal name="functionId" value="3000294">
 
<tr>

<td ><button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteSpecialCondition();"><bean:message key="button.delete" /></button></td>
</tr>
</logic:equal>

<logic:notEqual name="functionId" value="3000296">
						
</logic:notEqual>
</logic:notEmpty>
 </logic:present>
</table>
</logic:notPresent>
<logic:present name = "specialConditionDV">
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
       	 <td width="3%" ><input type="checkbox"  name="allchk" id="allchk" disabled="disabled" onclick="allChecked();"/></td>
          <td><b><bean:message key="lbl.specialId"/></b></td>
          <td><b><bean:message key="lbl.specialCondition"/></b></td>
          <td ><b><bean:message key="lbl.specialCategory"/></b></td>
        </tr>
        <logic:present name="specialConditionList">
        <logic:iterate name="specialConditionList" id="specialConditionListData">
	<tr class="white1">
	<td ><input type="checkbox"  name="chk" id="chk" disabled="disabled" value="${specialConditionListData.specialDealId }"/></td>
	 <td><a href="#" onclick="updateSpecialCondition('${specialConditionListData.specialDealId}');">${specialConditionListData.specialDealId}</a></td>
     <td>${specialConditionListData.specialCondition}</td>
     <td>${specialConditionListData.specialCategory}</td>
	 </tr>
	 </logic:iterate>
	 </logic:present>
 </table>    </td>
</tr>
</table>
</logic:present>
	</fieldset>

</html:form>
</logic:notPresent>

<logic:present name="viewSpecialCondition">
	<html:form styleId="specialConditionForm" action="/updateSpecialConditionRemarks" method="post">
   <html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
   
</div>   
   <logic:present name="loanHeaderView">
<fieldset>

<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td >
           <input type="hidden" id="loanId" value="${loanHeader[0].loanId}"/>
          <b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}
          <input type="hidden" id="dealCustomerId" value="${loanHeader[0].dealCustomerId}"/>
          </td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>
</logic:present>
<fieldset>	
		 <legend><bean:message key="lbl.specialConditionDetails"/></legend>

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
    <tr class="white2">
       	 <td width="3%" ><input type="checkbox"  name="allchk" id="allchk" onclick="allChecked();"></td>
          <td><b><bean:message key="lbl.specialId"/></b></td>
          <td><b><bean:message key="lbl.specialCondition"/></b></td>
          <td><b><bean:message key="lbl.specialCategory"/></b></td>
          <td><b><bean:message key="lbl.status"/></b></td>
          <td><b><bean:message key="lbl.remarks"/></b></td>
          
        </tr>
        <logic:present name="specialConditionList">
        <logic:iterate name="specialConditionList" id="specialConditionListData" indexId="counter">
	<tr class="white1">
	<td ><input type="checkbox"  name="chk" id="chk${counter+1}" value="${specialConditionListData.specialDealId }"/></td>
	 <td>${specialConditionListData.specialDealId}</td>
     <td>${specialConditionListData.specialCondition}</td>
     <td>${specialConditionListData.specialCategory}</td>
     <td><html:select property="specConditionStatus" styleId="specConditionStatus${counter+1}" styleClass="text" value="${specialConditionListData.specConditionStatusCode}" >
     		<html:option value="">--Select--</html:option>
     		<html:option value="C">Complied</html:option>
     		<html:option value="D">Deferred</html:option>
     		<html:option value="W">Waived</html:option>
     	</html:select>
     </td>
     <td><html:text property="specRemark" styleId="specRemark${counter+1}" styleClass="text" value="${specialConditionListData.specialRemarks}" /></td>
	 </tr>
	 </logic:iterate>
	 </logic:present>
 </table>    </td>
</tr>
 <logic:present name="specialConditionList">
 <logic:notEmpty name="specialConditionList">
<tr>

<td ><button type="button" name="button1" class="topformbutton2" title="Alt+V" accesskey="V" onclick="updateSpecialConditionRemarks();"><bean:message key="button.save" /></button></td>

</tr>
</logic:notEmpty>
 </logic:present>
</table>

	</fieldset>

</html:form>

</logic:present>
</logic:notPresent>
<logic:present name="authorSpecialCondition">
		<html:form styleId="specialConditionForm" action="/updateSpecialConditionRemarks" method="post">
   <html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
	
<logic:present name="loanHeaderView">
<fieldset>

<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td >
           <input type="hidden" id="loanId" value="${loanHeader[0].loanId}"/>
          <b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}
          <input type="hidden" id="dealCustomerId" value="${loanHeader[0].dealCustomerId}"/>
          </td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>
</logic:present>

<fieldset>	
		 <legend><bean:message key="lbl.specialConditionDetails"/></legend>

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
    <tr class="white2">
       	 <td width="3%" ><input type="checkbox"  name="allchk" id="allchk" disabled="disabled" onclick="allChecked();"></td>
          <td><b><bean:message key="lbl.specialId"/></b></td>
          <td><b><bean:message key="lbl.specialCondition"/></b></td>
          <td><b><bean:message key="lbl.specialCategory"/></b></td>
          <td><b><bean:message key="lbl.status"/></b></td>
          <td><b><bean:message key="lbl.remarks"/></b></td>
          
        </tr>
        <logic:present name="specialConditionList">
        <logic:iterate name="specialConditionList" id="specialConditionListData" indexId="counter">
	<tr class="white1">
	<td ><input type="checkbox"  name="chk" id="chk${counter+1}" disabled="disabled" value="${specialConditionListData.specialDealId }"/></td>
	 <td>${specialConditionListData.specialDealId}</td>
     <td>${specialConditionListData.specialCondition}</td>
     <td>${specialConditionListData.specialCategory}</td>
     <td><html:select property="specConditionStatus" styleId="specConditionStatus${counter+1}" styleClass="text" value="${specialConditionListData.specConditionStatusCode}" disabled="true">
     		<html:option value="">--Select--</html:option>
     		<html:option value="C">Complied</html:option>
     		<html:option value="D">Deferred</html:option>
     		<html:option value="W">Waived</html:option>
     	</html:select>
     </td>
     <td>${specialConditionListData.specialRemarks}</td>
	 </tr>
	 </logic:iterate>
	 </logic:present>
 </table>    </td>
</tr>
 
</table>

	</fieldset>

</html:form>
</logic:present>



<script>
	setFramevalues("specialConditionForm");
</script>
</body>
</html:html>


<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='D')
	{
		alert('<bean:message key="lbl.deleted" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='N')
	{
		alert('<bean:message key="lbl.notDelete" />');
	}
</script>
</logic:present>