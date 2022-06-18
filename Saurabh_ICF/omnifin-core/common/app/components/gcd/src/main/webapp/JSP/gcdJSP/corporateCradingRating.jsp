<!--
 	Auther Name :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose :- To provide user interface for corporate Credit Rating 
 	Documentation :-  
 -->
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
		
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/corporateCreditRating.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
	     <script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
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
	<body onclick="parent_disable();" onload="enableAnchor();checkChanges('RatingForm');document.getElementById('RatingForm').institute.focus();">
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
	
	
	<html:form action="/creditRatingPageAction" styleId="RatingForm" method="post">
	<logic:present name="update">

<fieldset>	  
	<legend><bean:message key="credit.detail" /></legend>   
	
	<input type="hidden" name="ratingId" value="${creditRatingList[0].cRatingId}" />
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td>
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    
	 <tr>
       <td width="30%">
       <bean:message key="credit.name" /><font color="red">*</font></td>
         <td>
            <html:select property="institute" styleId="institute" value="${creditRatingList[0].institute}" styleClass="text" >
              <option value="">--Select--</option>
              <logic:present name="institutionNameList" >
			<logic:notEmpty name="institutionNameList" >
			<html:optionsCollection name="institutionNameList" label="agencyDesc" value="agencyCode" />
		</logic:notEmpty>
			  </logic:present>
            </html:select></td>
         <td><bean:message key="credit.rating" /><font color="red">*</font></td>
         <td><html:text property="rating" styleId="rating" styleClass="text" value="${creditRatingList[0].rating}" onkeyup="return upperMe('rating');" /></td>
     </tr>
  
     <tr>
        <td><bean:message key="credit.date" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>    
	    <td><html:text property="creditDate" styleClass="text" styleId="creditDate" value="${creditRatingList[0].creditDate}" onchange="checkDate('creditDate');" /></td>    			
	 </tr>
	
     <tr>
        <td >
        <logic:notPresent name="creditRatingList">
        
    	<button name="button1"  class="topformbutton2" id="save" onclick="return saveRatingDetail();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
        <button name="button" class="topformbutton2" onclick="return creditClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
         </logic:notPresent>
        <logic:present name="creditRatingList">
        <button name="button1" id="save"  class="topformbutton2" onclick="return updateRatingDetail();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
        </logic:present>
        &nbsp;&nbsp;
        <logic:notPresent name="ratingList">
        <button type="button" name="button" class="topformbutton2"  onclick="return creditClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
        </logic:notPresent></td>
      </tr>
 </table>  
</td>
</tr>
</table>
 </fieldset>
    <span class="">&nbsp;</span>
	<br/> 
  <fieldset><legend><bean:message key="credit.detail" /></legend> 

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr>
                                <td class="gridheader"><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="select" /></b></td> 
								<td class="gridheader"><b><bean:message key="credit.name" /></b></td>
								<td class="gridheader"><b><bean:message key="credit.rating" /></b></td>
								<td class="gridheader"><b><bean:message key="credit.date" /></b></td>	
                                
							</tr>
							
							<logic:present name="creditRatingDetail">
							<logic:iterate id="creditRatingId" name="creditRatingDetail">
							<tr class="white1">
							<td ><input type="checkbox" name="chk" id="chk" value="${creditRatingId.ratingId }"/></td>
								<td ><a href="#" id="anchor0" onclick="return modifyCreditRatingDetails(${creditRatingId.ratingId });">${creditRatingId.institute }</a></td>
								<td >${creditRatingId.rating }</td>
								<td>${creditRatingId.creditDate }</td>
								</tr>	
							</logic:iterate>
							</logic:present>		
			</table>
			</td>
			</tr>
			</table>

 <button type="button" name="delete" id="delete" class="topformbutton2" onclick="return deleteCreditRating();" accesskey="D" title="Alt+D"><bean:message key="button.delete" /></button>
<!--					  <html:button property="modify" styleClass="topformbutton2" value="Modify" onclick="return modifyCreditRatingDetails();"/>-->
			
		</fieldset>
        </logic:present>

	<logic:notPresent name="update">
<fieldset>	  
	<legend><bean:message key="credit.detail" /></legend>   
	<!--  <center><font color="red"><html:errors /></font></center> 
	<logic:present name="corporateId">
    <font color="red">Corporate Code: ${sessionScope.corporateId}</font>

    </logic:present>
    
    -->

		<input type="hidden" name="ratingId" value="${creditRatingList[0].cRatingId}" />
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr>
    <td width="30%"><bean:message key="credit.name" /><font color="red">*</font></td>
    <td>
         <html:select property="institute" styleId="institute" styleClass="text" value="${creditRatingList[0].institute}">
              <option value="">--Select--</option>
              <logic:present name="institutionNameList" >
			<logic:notEmpty name="institutionNameList" >
		 <html:optionsCollection name="institutionNameList" label="agencyDesc" value="agencyCode" />
		</logic:notEmpty>
		      </logic:present>
         </html:select></td>
         <td> <bean:message key="credit.rating" /><font color="red">*</font></td>
         <td><html:text property="rating" styleId="rating" styleClass="text" value="${creditRatingList[0].rating}" onkeyup="return upperMe('rating');"/></td>
     </tr>
  
     <tr>
        <td><bean:message key="credit.date" /><font color="red">*</font></td>    
	    <td><html:text property="creditDate" styleClass="text" styleId="creditDate" value="${creditRatingList[0].creditDate}" onchange="checkDate('creditDate');" /></td>    			
	</tr>
	
     <tr>
         <td>
         <logic:present name="creditRatingList">
         	<button name="button1" type="button"  class="topformbutton2" id="save" onclick="return updateRatingDetail();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
         </logic:present>
         <logic:notPresent name="creditRatingList">
         	 <button name="button1" type="button" class="topformbutton2" id="save" onclick="return saveRatingDetail();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
         </logic:notPresent>
        
        <button type="button" name="button" class="topformbutton2" onclick="return creditClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
      </td>
      </tr>
 </table>  
</td>
</tr>
</table>
</fieldset>
<span class="">&nbsp;</span>
<br/> 
  <fieldset><legend><bean:message key="credit.detail" /></legend> 

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr class="white2">
                                <td><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="select" /></b></td> 
								<td ><b><bean:message key="credit.name" /></b></td>
								<td ><b><bean:message key="credit.rating" /></b></td>
								<td ><b><bean:message key="credit.date" /></b></td>	
                                
							</tr>
							
							<logic:present name="creditRatingDetail">
							<logic:iterate id="creditRatingId" name="creditRatingDetail">
							<tr class="white1">
							<td ><input type="checkbox" name="chk" id="chk" value="${creditRatingId.ratingId }"/></td>
								<td ><a href="#" id="anchor0" onclick="return modifyCreditRatingDetails(${creditRatingId.ratingId });">${creditRatingId.institute }</a></td>
								<td >${creditRatingId.rating }</td>
								<td>${creditRatingId.creditDate }</td>
																
							</tr>	
							</logic:iterate>
							</logic:present>		
			</table>
			</td>
			</tr>
			</table>

	 <button type="button" id="delete" name="button1" class="topformbutton2" onclick="deleteCreditRating();" accesskey="D" title="Alt+D"><bean:message key="button.delete" /></button>
			</fieldset>
	
	</logic:notPresent>
</html:form>
<br/> 
</div>          
</div>
</logic:notPresent>
<logic:present name="approve">

	<div id="centercolumn">
	<div id="revisedcontainer">
	<html:form action="/creditRatingPageAction" styleId="RatingForm" method="post">
<fieldset>	  
	<legend><bean:message key="credit.detail" /></legend>   
	<!--  <center><font color="red"><html:errors />${requestScope.status}</font></center> 
	<logic:present name="corporateId">
<font color="red">Corporate Code: ${sessionScope.corporateId}</font>

</logic:present>
-->
<html:hidden property="corporateCode" styleId="corporateCode" styleClass="text" value="${sessionScope.corporateId}" />
	<br/>
	<input type="hidden" name="ratingId" value="${creditRatingList[0].cRatingId}" />
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td>
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    
	<tr>
      <td>
          <bean:message key="credit.name" /><font color="red">*</font></td>
      <td>
          <html:select property="institute" styleId="institute" value="${creditRatingList[0].institute}" disabled="true" tabindex="-1" styleClass="text">
              <option value="">--Select--</option>
              <logic:present name="institutionNameList" >
			<logic:notEmpty name="institutionNameList" >
			<html:optionsCollection name="institutionNameList" label="agencyDesc" value="agencyCode" />
		</logic:notEmpty>
			</logic:present>
         </html:select></td>
         <td><bean:message key="credit.rating" /><font color="red">*</font></td>
         <td><html:text property="rating" styleId="rating" styleClass="text" value="${creditRatingList[0].rating}" disabled="true" tabindex="-1"/></td>
    </tr>
  
     <tr>
        <td> <bean:message key="credit.date" /><font color="red">*</font></td>    
	    <td><html:text property="creditDate" styleClass="text" styleId="creditDate1" value="${creditRatingList[0].creditDate}" disabled="true" tabindex="-1"/></td>    			
	</tr>
	
 
 </table>  
</td>
</tr>

</table>
 
</fieldset>

<div class="" >
    <span class="">&nbsp;</span>
	 <br/>  
<fieldset>	
		 <legend><bean:message key="lbl.creditRatingDetails"/></legend>  

  
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr class="white2">
                                <td ><b>
                                <logic:notPresent name="underWriter">
                                <input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="select" />
                                </logic:notPresent>
                                <logic:present name="underWriter">
                                <input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="select" />
                                </logic:present>
                                </b></td> 
								<td ><b><bean:message key="credit.name" /></b></td>
								<td ><b><bean:message key="credit.rating" /></b></td>
								<td ><b><bean:message key="credit.date" /></b></td>	
                                
							</tr>
							
							<logic:present name="ratingList">
							<logic:iterate id="subratingList" name="ratingList">
							<tr class="white1">
							<td >
							<logic:notPresent name="underWriter">
							<input type="checkbox" name="chk" id="chk" value="${subratingList.ratingId }" disabled="disabled"/>
							</logic:notPresent>
							<logic:present name="underWriter">
							<input type="checkbox" name="chk" id="chk" value="${subratingList.ratingId }" disabled="disabled" tabindex="-1"/>
							</logic:present>
							</td>
								<td ><a href="#" id="anchor0" onclick="return modifyCreditRatingDetails(${subratingList.ratingId });">${subratingList.institute }</a></td>
								<td >${subratingList.rating }</td>
								<td>${subratingList.creditDate }</td>
																
							</tr>	
							</logic:iterate>
								</logic:present>		
</table>
</td>
</tr>
</table>
<logic:notPresent name="approve">
	<logic:notPresent name="underWriter">
	<button type="button" name="button1" class="topformbutton2" onclick="deleteCreditRating();" accesskey="D" title="Alt+D"><bean:message key="button.delete" /></button>
	</logic:notPresent>
	</logic:notPresent>
<!--	<html:button property="modify" styleClass="topformbutton2" value="View" onclick="return modifyCreditRatingDetails();"/>-->

</fieldset>
<br/> 
</div>          
</html:form>
</div>
</div>
</logic:present>

<logic:present name="procval">
	<script type="text/javascript">
	if(('<%=request.getAttribute("procval")%>'!= '') || ('<%=request.getAttribute("procval")%>'!='S'))
	{
	   
		alert('<%=request.getAttribute("procval").toString()%>');

	}
	</script>
</logic:present>
<logic:present name="sms">

 <script type="text/javascript">
 
 	if('<%=request.getAttribute("sms").toString()%>' == 'S')
	{
		alert('<bean:message key="lbl.creditrating" />');
		document.getElementById('RatingForm').action="<%=request.getContextPath() %>/corporateCradingRatingAction.do";
	    document.getElementById('RatingForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'U')
	{
		alert('<bean:message key="lbl.creditratingUpdated" />');
		document.getElementById('RatingForm').action="<%=request.getContextPath() %>/corporateCradingRatingAction.do";
	    document.getElementById('RatingForm').submit();
		
	}
	
 	else if('<%=request.getAttribute("sms").toString()%>' == 'E')
	{
		alert('<bean:message key="lbl.errorSuccess" />');
		document.getElementById('RatingForm').action="<%=request.getContextPath() %>/corporateCradingRatingAction.do";
	
	}
	else
	{
		document.getElementById('RatingForm').action="<%=request.getContextPath() %>/corporateCradingRatingAction.do";
	}
	</script>
</logic:present>
<script>
	setFramevalues("RatingForm");
</script>
</body>
</html:html>