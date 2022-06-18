<!--Author Name : Asesh Kuumar->
<!--Date of Creation : 19-Jan-2013-->
<!--Purpose  : Information of BenchMark Ratio  Master Ad-->
<!--Documentation : -->

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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/benchMarkRatioMaster.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>

<script type="text/javascript">
  $(function() {
				    	$("#effectiveDate").datepicker({
				    			changeMonth: true,
				    		changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
				            buttonImageOnly: true,
				            dateFormat: document.getElementById("formatD").value,
				    		defaultDate: document.getElementById("businessdate").value
				         });
				    	});	
</script>

<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	// document.getElementById('subDealerMaster').subDealerCode.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	//document.getElementById('subDealerMaster').subDealerCode.focus();
     }
     return true;
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


 <body onload="enableAnchor();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/benchMarkRatioAdd" styleId="benchmarkRatioAddForm" method="POST" >
 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 
 <html:errors/>
<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
            <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>

<fieldset>

<legend><bean:message key="lbl.benchmarkRatioMasterSearch" /></legend>

  <table width="100%" height="86">
    
<logic:present name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
<input type="hidden" id=benchmarkRatioSeq name="benchmarkRatioSeq" value="${benchmarkRatioSeq}"/>
    <tr>
          <td><bean:message key="lbl.benchmarkRatioCode"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="benchmarkRatioCode" styleClass="text" readonly="true" styleId="benchmarkRatioCode" maxlength="20"  value="${list[0].benchmarkRatioCode}" /></label>
		  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
     	  <html:hidden  property="lbxRatio" styleId="lbxRatio"  value="${list[0].lbxRatio}"/>
    	  </td>
		  <td><bean:message key="lbl.benchmarIndustry" /><span><font color="red">*</font></span></td>
	      <td><html:text property="benchmarkIndustryId" styleClass="text" styleId="benchmarkIndustryId" maxlength="10" tabindex="-1" readonly="true" value="${list[0].benchmarkIndustryId}" />
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/> 
	     	<html:hidden  property="lbxIndustry" styleId="lbxIndustry"  value="${list[0].lbxIndustry}"/>
	      </td>
	
	 </tr>
      
	<tr>
	 <td><bean:message key="lbl.benchMarkRatio"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="benchmarkRatio" styleClass="text" onkeypress="return isNumberAndSpecialChar(event);" styleId="benchmarkRatio" maxlength="50"  value="${list[0].benchmarkRatio}" /></label></td>	
	
	<td ><bean:message key="lbl.benchmarkEffFrom"/><span><font color="red">*</font></span></td>
		<td>
				<html:text property="effectiveDate"  styleId="effectiveDate" styleClass="text" value="${list[0].effectiveDate}" maxlength="10" onchange="checkDate('effectiveDate');ValidateEffectiveDate();"/>
	    </td>
	 </tr>
	 <tr>
	 <td> <bean:message key="lbl.benchMarkStatus"/> </td>   
	  <logic:equal value="A" name="status">
       <td>  <input type="checkbox" name="recStatus" id="recStatus" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="A" name="status">
       <td><input type="checkbox" name="recStatus" id="recStatus"  /></td>
      </logic:notEqual>
	 
  </tr>      

  </logic:present>
  
  <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/> 
   <input type="hidden" id=benchmarkRatioSeq name="benchmarkRatioSeq" value="${benchmarkRatioSeq}"/>
   
   <tr>
          <td><bean:message key="lbl.benchmarkRatioCode"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="benchmarkRatioCode" styleClass="text" readonly="true" value="${List[0].benchmarkRatioCode}" styleId="benchmarkRatioCode" maxlength="20"   /></label>
		  <html:button property="ratioButton" styleId="ratioButton" onclick="closeLovCallonLov1();openLOVCommon(496,'benchmarkRatioAddForm','benchmarkRatioCode','','', '','','','lbxRatio');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath" />
     	  <html:hidden  property="lbxRatio" styleId="lbxRatio"  value="${List[0].lbxRatio}"/>
    	  </td>
		  <td><bean:message key="lbl.benchmarIndustry" /><span><font color="red">*</font></span></td>
	      <td><html:text property="benchmarkIndustryId" styleClass="text" styleId="benchmarkIndustryId" maxlength="10" tabindex="-1" value="${List[0].benchmarkIndustryId}" readonly="true" />
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	     	<html:hidden  property="lbxIndustry" styleId="lbxIndustry"  value="${List[0].lbxIndustry}"/>
	      <html:button property="insusButton" styleId="insusButton" onclick="closeLovCallonLov1();openLOVCommon(10,'benchmarkRatioAddForm','benchmarkIndustryId','','', '','','','lbxIndustry');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>      
	      </td>
	
	 </tr>
      
	<tr>
	 <td><bean:message key="lbl.benchMarkRatio"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="benchmarkRatio" styleClass="text" styleId="benchmarkRatio" maxlength="50" onkeypress="return isNumberAndSpecialChar(event);"  /></label></td>	
	 
	  <td ><bean:message key="lbl.benchmarkEffFrom"/><span><font color="red">*</font></span></td>
		<td>
				<html:text property="effectiveDate"  styleId="effectiveDate" styleClass="text" value="" maxlength="10" onchange="checkDate('effectiveDate');ValidateEffectiveDate();"/>
	    </td>
	 </tr>
	 
	 <tr>
	 <td> <bean:message key="lbl.benchMarkStatus"/> </td>   
       <td><input type="checkbox" name="recStatus" id="recStatus"  checked="checked"/></td>
	 
  </tr>     
  </logic:notPresent>
   
  
  <tr><td>
     <logic:present name="editVal">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditBenchMark();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    
     <logic:present name="editValUpdate">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditBenchMark();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnSaveBenchMark();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> 
 </tr>
  
		
	</table>		

</fieldset></html:form>

<logic:present name="sms">
		<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("benchmarkRatioAddForm").action="benchmarkRatioBehind.do";
	    document.getElementById("benchmarkRatioAddForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("benchmarkRatioAddForm").action="benchmarkRatioBehind.do";
	    document.getElementById("benchmarkRatioAddForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	} 
	
	else if('<%=request.getAttribute("sms").toString()%>'=='UPDE')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	} 
	
	
	</script>
</logic:present>
</body>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</html:html>
