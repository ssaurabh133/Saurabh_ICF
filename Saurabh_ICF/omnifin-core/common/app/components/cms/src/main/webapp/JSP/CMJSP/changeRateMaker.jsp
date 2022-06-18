<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	
	<head>
	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
        int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
          request.setAttribute("no",no); %>

		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 
	<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
			
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>	   
		 <!-- css for Datepicker -->
		<link type="text/css" href="development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/nonEmiBasedLoan.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
		
		<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->
	
	<script type="text/javascript">
  		
		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
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
	<body onload="enableAnchor();checkChanges('nonEmiBasedMaker');document.getElementById('nonEmiBasedMaker').loanAcButton.focus();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:form action="/changeRateMakerNewAction" method="post" styleId="nonEmiBasedMaker">
	

		      <fieldset>	  
	<legend><bean:message key="lbl.nonEMIBasedLoanMaker"/></legend>  
	
	<!-- --------------------------------------- Rate Change Maker for New Screen--------------------------------- -->
	<logic:present name="Maker">  
	<logic:notPresent name="list"> 
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%">
	            
	             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	              <input type="hidden" name="saveRecord" value="S" id="saveRecord"/>
		   </td>
           </tr> 
		   <tr>	   
         <td><bean:message key="lbl.loanNo"></bean:message><font color="red">* </font></td>
	      <td>
		<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${defaultVal.loanNo}" readonly="true" tabindex="-1"/>
        <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo"  value="${defaultVal.lbxLoanNo}"/>
       <html:button property="loanAcButton" styleId="loanAcButton" onclick="openLOVCommon(374,'nonEmiBasedMaker','loanNo','','', '','','totalRecOfNonEmiLoan','customerName')" value=" " styleClass="lovbutton"></html:button>
       <%--<img onclick="openLOVCommon(374,'nonEmiBasedMaker','loanNo','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/> --%> 
        </td>
	
	    <td><bean:message key="lbl.customerName"></bean:message> </td>
		    <td>
			 <html:text property="customerName" styleClass="text" styleId="customerName" value="${defaultVal.customerName}" readonly="true" tabindex="-1" maxlength="50"></html:text>
            </td>
		</tr>	
		     <tr>
	      <td><bean:message key="lbl.loanAmount"></bean:message></td>
	       <td >
	       
	       <html:text property="loanAmount" style="text-align: right" styleClass="text" styleId="loanAmount" readonly="true" value="${defaultVal.loanAmount}" tabindex="-1" maxlength="22" ></html:text>
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	      </td>
	       <td><bean:message key="lbl.balanceAmount"></bean:message></td>
	       <td >
	       <html:text property="balanceAmount" style="text-align: right" styleClass="text" styleId="balanceAmount" readonly="true" value="${defaultVal.balanceAmount}" tabindex="-1" maxlength="22" ></html:text>
	       </td>
		   </tr>
		   <tr>		   	  
	       <td><bean:message key="lbl.currentEffectRate"></bean:message> </td>
		   <td>
		   <html:text styleClass="text" property="loanFinalRate" styleId="loanFinalRate" maxlength="6"  value="${defaultVal.loanFinalRate}" readonly="true" tabindex="-1"/>
            </td>
			 <td>
			 <bean:message key="lbl.currentRateType" />
			 </td>
			 <td>
			    <html:text property="currentRateType" styleClass="text" styleId="currentRateType" maxlength="20" readonly="true" tabindex="-1" value="${defaultVal.currentRateType}"/>
			    <input type="hidden" name="loanRateType" value="${defaultVal.loanRateType}" id="loanRateType"/>
			    </td>
			    </tr>
			    
			     <tr>
		   <td><bean:message key ="lbl.newEffectRate"></bean:message><font color="red">* </font></td>
	       <td >
	       <html:text property="newEffectRate"  styleClass="text" styleId="newEffectRate" value="" onfocus="keyUpNumber(this.value, event, 3, id);" 
           onblur="checkRate('newEffectRate');fourDecimalNumber(this.value, id);" onkeypress="return numbersonly(event, id, 4)" 
            onkeyup="checkNumber(this.value, event, 3, id);" value="${defaultVal.newEffectRate}"  maxlength="6"></html:text>
	       </td>
	        </tr>	


<logic:equal value="S" name="sms">
<tr>	
	      <td>
	      <button type="button" class="topformbutton2"  id="save" onclick="return fnSave('<bean:message key="msg.plsSelLoanNo"/>','<bean:message key="msg.plsfilnewEffRate"/>');" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	      <button type="button" name="button2" class="topformbutton2"  onclick="return forward();" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	      </td>
		  </tr>
		  
</logic:equal>


<logic:notEqual value="S" name="sms">

<tr>	
	      <td>
	      <button type="button" class="topformbutton2"  id="save" onclick="return fnSave('<bean:message key="msg.plsSelLoanNo"/>','<bean:message key="msg.plsfilnewEffRate"/>');" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	      <button type="button" name="button2" class="topformbutton2"  onclick="return forwardBeforeSave();" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	      </td>
		  </tr>
</logic:notEqual>
	        
		</table>
		
	      </td>
	</tr>

	
	</table>
	</logic:notPresent>
	 
	 
		 
<!-- --------------------------------------- Rate Change Maker for Saved/Update Record--------------------------------- -->

<logic:present name="list">
<logic:iterate id="defaultVal" name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%">
	            
	             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	              <input type="hidden" name="saveRecord" value="E" id="saveRecord"/>
		   </td>
           </tr> 
		   <tr>	   
         <td><bean:message key="lbl.loanNo"></bean:message><font color="red">* </font></td>
	      <td>
		<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${defaultVal.loanNo}" readonly="true" tabindex="-1"/>
        <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo"  value="${defaultVal.lbxLoanNo}"/>
       <%-- <html:button property="loanAcButton" styleId="loanAcButton" onclick="openLOVCommon(374,'nonEmiBasedMaker','loanNo','','', '','','totalRecOfNonEmiLoan','customerName')" value=" " styleClass="lovbutton"></html:button>--%> 
       <%--<img onclick="openLOVCommon(374,'nonEmiBasedMaker','loanNo','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/> --%> 
        </td>
	
	    <td><bean:message key="lbl.customerName"></bean:message> </td>
		    <td>
			 <html:text property="customerName" styleClass="text" styleId="customerName" value="${defaultVal.customerName}" readonly="true" tabindex="-1" maxlength="50"></html:text>
            </td>
		</tr>	
		     <tr>
	      <td><bean:message key="lbl.loanAmount"></bean:message></td>
	       <td >
	       
	       <html:text property="loanAmount" style="text-align: right" styleClass="text" styleId="loanAmount" readonly="true" value="${defaultVal.loanAmount}" tabindex="-1" maxlength="22" ></html:text>
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	      </td>
	       <td><bean:message key="lbl.balanceAmount"></bean:message></td>
	       <td >
	       <html:text property="balanceAmount" style="text-align: right" styleClass="text" styleId="balanceAmount" readonly="true" value="${defaultVal.balanceAmount}" tabindex="-1" maxlength="22" ></html:text>
	       </td>
		   </tr>
		   <tr>		   	  
	       <td><bean:message key="lbl.currentEffectRate"></bean:message> </td>
		   <td>
		   <html:text styleClass="text" property="loanFinalRate" styleId="loanFinalRate" maxlength="6"  value="${defaultVal.loanFinalRate}" readonly="true" tabindex="-1"/>
            </td>
			 <td>
			 <bean:message key="lbl.currentRateType" />
			 </td>
			 <td>
			    <html:text property="currentRateType" styleClass="text" styleId="currentRateType" maxlength="20" readonly="true" tabindex="-1" value="${defaultVal.currentRateType}"/>
			    <input type="hidden" name="loanRateType" value="${defaultVal.loanRateType}" id="loanRateType"/>
			    </td>
			    </tr>
			    
			     <tr>
		   <td><bean:message key ="lbl.newEffectRate"></bean:message><font color="red">* </font></td>
	       <td >
	       <html:text property="newEffectRate"  styleClass="text" styleId="newEffectRate" value="" onfocus="keyUpNumber(this.value, event, 3, id);" 
           onblur="checkRate('newEffectRate');fourDecimalNumber(this.value, id);" onkeypress="return numbersonly(event, id, 4)" 
            onkeyup="checkNumber(this.value, event, 3, id);" value="${defaultVal.newEffectRate}"  maxlength="6"></html:text>
	       </td>
	        </tr>	

	        <tr>	
	      <td>
	      <button type="button" class="topformbutton2"  id="save" onclick="return fnSave('<bean:message key="msg.plsSelLoanNo"/>','<bean:message key="msg.plsfilnewEffRate"/>');" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	      <button type="button" name="button2" class="topformbutton2"  onclick="return forward();" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	      </td>
		  </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
</logic:iterate>
	 </logic:present>
</logic:present>     

<!-- --------------------------------------- Rate Change Author---------------------------------- -->

<logic:present name="Author">
<logic:iterate id="defaultVal" name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%">
	            
	             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	              <input type="hidden" name="saveRecord" value="E" id="saveRecord"/>
		   </td>
           </tr> 
		   <tr>	   
         <td><bean:message key="lbl.loanNo"></bean:message><font color="red">* </font></td>
	      <td>
		<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${defaultVal.loanNo}" readonly="true" tabindex="-1" />
        <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo"  value="${defaultVal.lbxLoanNo}"/>
      
       <%--<img onclick="openLOVCommon(374,'nonEmiBasedMaker','loanNo','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/> --%> 
        </td>
	
	    <td><bean:message key="lbl.customerName"></bean:message> </td>
		    <td>
			 <html:text property="customerName" styleClass="text" styleId="customerName" value="${defaultVal.customerName}" readonly="true" tabindex="-1" maxlength="50"></html:text>
            </td>
		</tr>	
		     <tr>
	      <td><bean:message key="lbl.loanAmount"></bean:message></td>
	       <td >
	       
	       <html:text property="loanAmount" style="text-align: right" styleClass="text" styleId="loanAmount" readonly="true" tabindex="-1" value="${defaultVal.loanAmount}" maxlength="22" ></html:text>
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	      </td>
	       <td><bean:message key="lbl.balanceAmount"></bean:message></td>
	       <td >
	       <html:text property="balanceAmount" style="text-align: right" styleClass="text" styleId="balanceAmount" readonly="true" tabindex="-1" value="${defaultVal.balanceAmount}"  maxlength="22" ></html:text>
	       </td>
		   </tr>
		   <tr>		   	  
	       <td><bean:message key="lbl.currentEffectRate"></bean:message> </td>
		   <td>
		   <html:text styleClass="text" property="loanFinalRate" styleId="loanFinalRate" maxlength="6"  value="${defaultVal.loanFinalRate}" tabindex="-1" readonly="true" />
            </td>
			 <td>
			 <bean:message key="lbl.currentRateType" />
			 </td>
			 <td>
			    <html:text property="currentRateType" styleClass="text" styleId="currentRateType" maxlength="20" readonly="true" tabindex="-1" value="${defaultVal.currentRateType}"/>
			    <input type="hidden" name="loanRateType" value="${defaultVal.loanRateType}" id="loanRateType"/>
			    </td>
			    </tr>
			    
			     <tr>
		   <td><bean:message key ="lbl.newEffectRate"></bean:message><font color="red">* </font></td>
	       <td >
	       <html:text property="newEffectRate"  styleClass="text" readonly="true" styleId="newEffectRate" tabindex="-1" value="" onfocus="keyUpNumber(this.value, event, 3, id);" 
           onblur="checkRate('newEffectRate');fourDecimalNumber(this.value, id);" onkeypress="return numbersonly(event, id, 4)" 
            onkeyup="checkNumber(this.value, event, 3, id);" value="${defaultVal.newEffectRate}"  maxlength="6"></html:text>
	       </td>
	        </tr>	

		</table>
		
	      </td>
	</tr>

	
	</table>
</logic:iterate>
	 </logic:present>
 
	</fieldset>	



 </html:form>
  
		<logic:present name="sms"><br />
		
		<script type="text/javascript">
		
		if('<%=request.getAttribute("sms").toString()%>'=='S')
		{
		    alert("<bean:message key='msg.saveNonEmi' />");
		}
	
		else if('<%=request.getAttribute("sms").toString()%>'=='F')
		{
			alert("<bean:message key='msg.ForwardNonEmiSuccessfully' />");
			document.getElementById('nonEmiBasedMaker').action="<%=request.getContextPath()%>/changeRateMaker.do";
		    document.getElementById('nonEmiBasedMaker').submit();
			
		}
		
		else if('<%=request.getAttribute("sms").toString()%>'=='N')
		{
			alert("<bean:message key='msg.NonEmiError' />");

		}
		else if('<%=request.getAttribute("sms").toString()%>'=='E')
		{
			alert("<bean:message key='msg.ErrorForwardNonEmiLoan' />");

		}
		
		
		</script>
		</logic:present>
   
</div>



</div>
<script type="text/javascript">
	setFramevalues("nonEmiBasedMaker");
 </script>	

	</body>

</html>