
<!--Author Name : nazia parvez -->
<!--Date of Creation : 11-march-2013-->
<!--Purpose  : Information of Legal stage Master-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.repo.vo.RepoAssetChecklistVo"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoAssetChecklist.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('productButton').focus();
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
    var size=document.getElementById("psize").value;
    for (var j=1;j<size;j++)
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

<body onload="enableAnchor();fntab();">
<html:form styleId="repoAssetChecklistMasterForm"  method="post"  action="/RepoAssetChecklistDispatch" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.repoAssetChecklist" /></legend>
  <table width="100%">
    <tr>  <td><bean:message key="lbl.repoProduct" /><span><font color="red">*</font></span></td>
    <logic:present name="editVal">
      <td><html:text property="repoProduct" styleClass="text" styleId="repoProduct" maxlength="10" onblur="fnChangeCase('repoAssetChecklistMasterForm','repoProduct')"  value="${list[0].repoProduct}" readonly="true" />
    
          <html:hidden  property="lbxProductSearchID" styleId="lbxProductSearchID" value="${list[0].lbxProductSearchID}"  />
		
   </logic:present>
   
   <logic:present name="save">
       <td><html:text property="repoProduct" styleClass="text" styleId="repoProduct" maxlength="10" onblur="fnChangeCase('repoAssetChecklistMasterForm','repoProduct')"  readonly="true" />
     <html:button property="productButton" styleId="productButton" tabindex="1"  onclick="openLOVCommon(1602,'repoAssetChecklistMasterForm','repoProduct','','', '','','','lbxProductSearchID')" value=" " styleClass="lovbutton"></html:button></td>   
          <html:hidden  property="lbxProductSearchID" styleId="lbxProductSearchID" value="${list[0].lbxProductSearchID}"  />
		
   </logic:present>

      <td><bean:message key="lbl.repoAssetClass" /><span><font color="red">*</font></span></td>
      <logic:present name="save">
	  <td>
		 <html:select property="assetClass" styleClass="text"
				styleId="assetClass" value="${list[0].assetClass}" >
			   <html:option value="" >--Select--</html:option>
				<logic:present name="assetList">
					<logic:notEmpty name="assetList">
						<html:optionsCollection name="assetList" label="assetClassId"
							value="assetClass" />
					</logic:notEmpty>
				</logic:present>
			</html:select>
		</td>
     </logic:present>
      
     <logic:present name="editVal">
     <td>
     <html:text property="assetClass" styleClass="text" styleId="assetClass" maxlength="10"  readonly="true" value="${list[0].assetClass}" />
     <html:hidden property="assetClassId" styleClass="text" styleId="assetClassId"   value="${list[0].assetClassId}" />
     </td>
     </logic:present>
    </tr>
     <tr>
      
    <td><bean:message key="lbl.status" /></td>
      <td>
      
      <logic:present name="editVal">
         <logic:equal value="A" name="status">
         <input type="checkbox" name=recStatus id="status" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="status">
         <input type="checkbox" name="recStatus" id="status"  />
      </logic:notEqual> 
   </logic:present>
   
   <logic:present name="save">
    <input type="checkbox" name=recStatus id="status" checked="checked" />
   </logic:present>
      
   
    
      </td>
    
    </tr>
  </table>
    
<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<input type="hidden" name="psize" id="psize" />
			<input type="hidden" name="pcheck" id="pcheck" />
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" /></td>
				 <td ><b><bean:message key="lbl.checklist" /></b></td>
      		</tr>
      		
<logic:present name="list"><div> 
 </div><logic:notEmpty name="checklist"><div>	 
	</div><logic:iterate name="checklist" id="sublist" indexId="counter">
	<tr class="white1">
		<td><input type="checkbox" name="chk" checked="checked" id="chk<%=counter.intValue()+1%>" /></td>
		<td>
			<input type="text" name="checkList" id="checkList<%=counter.intValue()+1%>" value="${sublist.checklist}" class="text"/>
		</td>
	</tr>
	
	</logic:iterate>
</logic:notEmpty>	
	<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="6">
			<button type="button" title="Alt+A" accesskey="A" onclick="return addRow('Edit');" class="topformbutton2"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.deleterow" /></button></td>
	</tr>
	
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
	<logic:present name="editVal">
     <td><button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return updateRepoAsset();" class="topformbutton2" ><bean:message key="button.save" /></button></td> 
   </logic:present>
   
   <logic:present name="save">
    <td><button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRepoAsset();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
   </logic:present>
	</tr>
</table>
</logic:present>

<logic:notPresent  name="list">

<%for(int i=1;i<=5;i++){ 
request.setAttribute("i",i);
%>
<tr class="white1" style="width: 25px;">


		<td ><input type="checkbox" name="chk" id="chk<%=i %>" /></td>
		<td >
			<input type="text" name="checkList" id="checkList<%=i%>"  class="text"/>
		</td>
		
</tr>
<%} %>
<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="2">
			<button type="button"  title="Alt+A" accesskey="A" onclick="return addRow('Save');" class="topformbutton2"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.deleterow" /></button></td>
	</tr>
	</table>
<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
	<logic:present name="editVal">
     <td><button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return openEditRepoAsset();" class="topformbutton2" ><bean:message key="button.save" /></button></td> 
   </logic:present>
   
   <logic:present name="save">
    <td><button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRepoAsset();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
   </logic:present>
	</tr>
</table>
</logic:notPresent>

</table>
</td>
</tr>
</table>



</fieldset>
           
	</html:form>		
<logic:present name="sms">


		<script type="text/javascript">
		
		//alert("vinod");
		   
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("repoAssetChecklistMasterForm").action="RepoAssetChecklistDispatch.do?method=searchRepoAsset";
	    document.getElementById("repoAssetChecklistMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("repoAssetChecklistMasterForm").action="RepoAssetChecklistDispatch.do?method=searchRepoAsset";
	    document.getElementById("repoAssetChecklistMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
	
	
	

	
	
</script>
</logic:present>
  </body>
		</html:html>