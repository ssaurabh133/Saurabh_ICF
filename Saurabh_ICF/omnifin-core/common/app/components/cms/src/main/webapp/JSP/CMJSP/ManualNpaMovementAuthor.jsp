<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/contentstyle.css"/>
		 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/subpage.css"/>
	   
		 
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	 	
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/manual_npa.js"></script>

		
		<script type="text/javascript">
		
		
        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      

		
		
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
		</script>
		
		
	</head>
	<body onclick="parent_disable();" onload="enableAnchor();checkChanges('authorForm');document.getElementById('sourcingForm').comments.focus();init_fields();">
	<div id="centercolumn">
	<div id="revisedcontainer">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:form action="/manualNpaMovementAuthorProcess" method="post" styleId="authorForm">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
  <fieldset>
  <legend><bean:message key="lbl.author"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		 
		 
		<tr>
					
		   <td><bean:message key="lbl.comments"/><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td><div style="float:left;">
			 <html:textarea styleClass="text" property="comments" styleId="comments" value=""></html:textarea>
			  
			 </div></td>
		
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="decision" styleId="decision" styleClass="text" onchange="hideAsterik(value)" >
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
             
		     </html:select>
		     </td>
		</tr> 		  
		<tr>
	      <td align="left" colspan="3" class="form2">
	       <button type="button" class="topformbutton2"  onclick="return saveManualNpaAuthor();"
 title="Alt+V" accesskey="V" id="save"><bean:message key="button.save" /></button>
	      
	     </td>
		 
		</tr> 
		
		 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>
	<br/> 



	


</html:form>
</div>

</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("authorForm");
</script>
</body>
</html:html>
<logic:present name="procval">
	<script type="text/javascript">

	
	alert('${procval}');
 </script>
    
	</logic:present>
 <logic:present name="message">
	<script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/manualNpaMovementAuthorSearch.do?method=searchManualNpaAuthor";
	}
    </script>
    
	</logic:present>
	