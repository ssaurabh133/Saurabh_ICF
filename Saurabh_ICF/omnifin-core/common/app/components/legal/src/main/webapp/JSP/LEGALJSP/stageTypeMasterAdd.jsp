<!--Author Name : nazia parvez -->
<!--Date of Creation : 11-march-2013-->
<!--Purpose  : Information of Legal stage Master-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.legal.vo.StageTypeMasterVo"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.connect.CommonFunction"%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/stageTypeMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('stageCode').focus();
   return true;
}

function checkUncheckAll(elementId)
{
	 var value = false;
      if(document.getElementById(elementId).checked==true){
        value = true;
      }else{
        value = false;
      }
    var size=document.getElementById("listSize").value;
    for (var j=0;j<size;j++)
    {
        document.getElementById(elementId+j).checked=value;
    }

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

<body onload="fntab();">
<html:form styleId="stageTypeMasterForm"  method="post"  action="/stageTypeMasterAdd" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.stageTypeMaster" /></legend>
  <table width="100%">
    <tr>
    <logic:present name="editVal">
      <td><bean:message key="lbl.stageCode" /><span><font color="red">*</font></span></td>
      <td><html:text property="stageCode" styleClass="text" styleId="stageCode" maxlength="10"  onblur="fnChangestage('stageTypeMasterForm','stageCode')" value="${requestScope.list[0].stageCode}" readonly ="true"/></td>
   
   </logic:present>
   
   <logic:present name="save">
      <td><bean:message key="lbl.stageCode" /><span><font color="red">*</font></span></td>
      <td><html:text property="stageCode" styleClass="text" styleId="stageCode" maxlength="10" tabindex="1" onblur="fnChangeCase('stageTypeMasterForm','stageCode')" value="${requestScope.list[0].stageCode}"/></td>
   </logic:present>

      <td><bean:message key="lbl.stageTypeDesc" /><span><font color="red">*</font></span></td>
     
      
      <td><html:text property="stageTypeDesc" styleClass="text" styleId="stageTypeDesc" maxlength="50" tabindex="2" onblur="fnChangeCase('stageTypeMasterForm','stageTypeDesc')" value="${requestScope.list[0].stageTypeDesc}" />
     </td>
    </tr><tr>
<td><bean:message key="lbl.sequenceNumber" /><span><font color="red">*</font></span></td>
      <td><html:text property="sequenceNumber" styleClass="text" styleId="sequenceNumber" maxlength="10"  tabindex="3"  value="${requestScope.list[0].sequenceNumber}" onkeypress="return isNumberKey(event);"/></td>
   
	

      <td><bean:message key="lbl.caseType" /><span><font color="red">*</font></span></td>
      <td><html:text property="caseTypeDesc" styleClass="text" styleId="caseTypeDesc" maxlength="10" value="${list[0].caseTypeDesc}"  onblur="fnChangeCase('StageTypeMasterForm','caseTypeDesc')"  readonly="true" />
     <html:button property="caseButton" styleId="caseButton" tabindex="4" onclick="openLOVCommon(1523,'StageTypeMasterForm','caseTypeDesc','','', '','','','lbxCaseTypeCode')" value=" " styleClass="lovbutton"></html:button></td>   
          <html:hidden  property="lbxCaseTypeCode" styleId="lbxCaseTypeCode" value="${list[0].lbxCaseTypeCode}"  />
		</tr>
		<tr>
		 <td><bean:message key="lbl.Stagetat" /><span><font color="red">*</font></span></td>
           <td><html:text property="tat" styleClass="text" styleId="tat" maxlength="10"  tabindex="3"  value="${requestScope.list[0].tat}"  onkeypress="return isNumberKey(event);"/></td>
           </tr>
     <tr>
       
    <td><bean:message key="lbl.status" /></td>
      <td>
      
		<logic:present name="editVal">
		
		<logic:equal value="A" name="status">
         <input type="checkbox" name=recStatus id="countryStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="status">
         <input type="checkbox" name="recStatus" id="countryStatus"  />
      </logic:notEqual>
		
		</logic:present>
		
		<logic:present name="save">
		
		 <input type="checkbox" name=recStatus id="countryStatus" checked="checked" />
		
		</logic:present>
      
      
     
      
    
      </td>
    <td><bean:message key="lbl.closureStage" /></td>
      <td>
      
      <logic:equal value="Y" name="closureStage">
         <input type="checkbox" name=closureStage id="closureStage" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Y" name="closureStage">
         <input type="checkbox" name="closureStage" id="closureStage"  />
      </logic:notEqual>
      
    </tr>
  </table>
    	<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	 <td ><input type="checkbox" id="productIds" onclick="checkUncheckAll('productIds');" /></td>
				 <td ><b><bean:message key="lbl.stageProduct" /></b></td>
    			 <td ><input type="checkbox" id="paymentStages" onclick="checkUncheckAll('paymentStages');" /><b><bean:message key="lbl.paymentStage" /></b></td>
       			  <td ><input type="checkbox" id="repetitives" onclick="checkUncheckAll('repetitives');" /><b><bean:message key="lbl.Repetitive" /></b></td>
      		</tr>
      		
      		<% ArrayList<StageTypeMasterVo> list = new ArrayList<StageTypeMasterVo>();
      		
      		list = (ArrayList<StageTypeMasterVo>)request.getAttribute("productList");
      		
      		if(list!=null)
      		{
      			for(int i=0;i<list.size();i++)
      			{
      				
      				StageTypeMasterVo vo = list.get(i);
      				System.out.println("vo.getProduct("+i+") : "+vo.getProduct());
      				System.out.println("vo.getPaymentStage("+i+") : "+vo.getPaymentStage());
      				System.out.println("vo.getRepetitive("+i+") : "+vo.getRepetitive());
      		 %>
      		 <tr class="white1" style="width: 25px;">
      		  <%if(!CommonFunction.checkNull(vo.getProduct()).equalsIgnoreCase("")) {%>
      		  <td ><input type="checkbox" name="productIds" id="productIds<%=i%>" checked="checked" value="<%=vo.getProductCode()%>" /></td>
      		  <%}else{ %>
      		  <td ><input type="checkbox" name="productIds" id="productIds<%=i%>" value="<%=vo.getProductCode()%>" /></td>
      		  <%}%>
      		  <td><%=vo.getProductDesc()%></td>
      		  <%
      		  if(CommonFunction.checkNull(vo.getPaymentStage()).equalsIgnoreCase("Y"))
      		  {
      		  %>
			  <td><input type="checkbox" name="paymentStages" checked="checked" id="paymentStages<%=i%>" /></td>
			  <%}else{ %>
			   <td><input type="checkbox" name="paymentStages" id="paymentStages<%=i%>" /></td>
			  <%}
			 
			  if(CommonFunction.checkNull(vo.getRepetitive()).equalsIgnoreCase("Y"))
      		  {
      		  %>
			  <td ><input type="checkbox" name="repetitives" checked="checked" id="repetitives<%=i%>" /></td>
			  <%}else{ %>
			  <td ><input type="checkbox" name="repetitives" id="repetitives<%=i%>" /></td>
			  <%}%>
			 </tr>
      		<%}} %>
      		
    		</table>
    </tr>
    
    
    
    <tr><td>
    
    	<%if(list!=null){ %>
       <input type="hidden" name="listSize" id="listSize" value="<%=list.size()%>" />
    <%}%>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditStageType('${requestScope.list[0].stageCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
   
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveStageType();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>

	</table>		


</fieldset>
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("stageTypeMasterForm").action="stageTypeMasterBehind.do";
	    document.getElementById("stageTypeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("stageTypeMasterForm").action="stageTypeMasterBehind.do";
	    document.getElementById("stageTypeMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
//	{
//		alert("<bean:message key="msg.notepadError" />");
//	}
//	else
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
	
	
	

	
	
</script>
</logic:present>
  </body>
		</html:html>