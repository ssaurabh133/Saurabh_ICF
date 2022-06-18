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
   <script type="text/javascript" src="<%=path%>/js/cmScript/cmLoanInitjs.js"></script>

		
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
	<body onclick="parent_disable();" onload="enableAnchor();checkChanges('sourcingForm');document.getElementById('sourcingForm').comments.focus();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	<html:form action="/assetVerificationInit" method="post" styleId="sourcingForm">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
  <fieldset>
  <legend><bean:message key="lbl.author"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		 
		 
		<tr>
					
		   <td><bean:message key="lbl.comments"/><font color="red">*</font></td>
			 <td><div style="float:left;">
			 <html:textarea styleClass="text" property="comments" styleId="comments" value=""></html:textarea>
			  
			 </div></td>
		
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="decision" styleId="decision" styleClass="text">
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="I">Send Back</html:option>
             
		     </html:select>
		     </td>
		</tr> 		  
		<tr>
	      <td align="left" colspan="3" class="form2">
	       <button type="button" class="topformbutton2"  onclick="return saveAssetCapRepAuthor();"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	      
	     <!--  <input type="button" value="Save"  class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveAssetCapRepAuthor();"/> --> 
	     </td>
		 
		</tr> 
		
		 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>
	<br/> 
	<logic:present name="list">
	<logic:notEmpty name="list">
	<logic:iterate id="listobj" name="list">
	
	<input type="hidden" name="assetVerificationID" id="assetVerificationID" value="${listobj.assetVerificationID}" />

	</logic:iterate>
		</logic:notEmpty>
	</logic:present>

 <logic:present name="dss">
	<script type="text/javascript">
	if('<%=request.getAttribute("dss").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/assetVerificationInit.do?method=searchAssetVerRepComplete";
		
	}else if('<%=request.getAttribute("dss").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	} 
    </script>
	</logic:present>


</html:form>
</div>

</div>
<script>
	setFramevalues("sourcingForm");
</script>
</body>
</html:html>