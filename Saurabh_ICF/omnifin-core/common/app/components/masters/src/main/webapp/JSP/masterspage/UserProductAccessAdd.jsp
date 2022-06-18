<!--Author Name : vinod kumar gupta-->
<!--Date of Creation : 05-Jan-2012-->
<!--Purpose  : Make Model Master Add-->
<!--Documentation : -->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/UserProductAccess.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>

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

<body onload="enableAnchor();clearAll();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
<input type="hidden" name="refreshInSave" id="refreshInSave" value="<%=request.getAttribute("refresh")%>"/>
<html:errors/>

<html:form styleId="userProductAccessAdd" action="/openAddUserProductAccess" >
<fieldset>
<legend><bean:message key="lbl.UserProductAccess" /></legend>

<logic:present name="editVal">
 <input type="hidden" id="modifyRecord" name="modifyRecord"	value="M" />
 
  <table width="100%"> 
 
  
<tr>
   
      <td ><bean:message key="lbl.userNam" /><span><font color="red">*</font></span></td>
      <td>
      		<html:text property="showUserDesc" styleClass="text" styleId="showUserDesc" maxlength="100" readonly="true" value="${requestScope.list[0].showUserDesc}" />
			<html:hidden property="lbxUserId" styleId="lbxUserId" value="${requestScope.list[0].lbxUserId}" />
			<logic:notPresent name="list">
			<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(284,'userProductAccessAdd','showUserDesc','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  />
			</logic:notPresent>
	</td>

	 <td><bean:message key="lbl.ProductAccess" /><span><font color="red">*</font></span></td>
       
     
      
      <logic:present name="selectedProduct">
      
      
      <td><bean:message key="lbl.chkAll" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" onclick="allProduct();" name="radioSelection" value="A" id="allselection" /><br/>
      <bean:message key="lbl.chkSelective" /> 
       <input type="radio" onclick="selectiveProduct();"  name="radioSelection" value="S" id="singleselection"  checked="checked"/>
       
     
      </logic:present>
      <logic:notPresent name="selectedProduct">
      
     
       <td><bean:message key="lbl.chkAll" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" onclick="allProduct();" name="radioSelection" value="A" id="allselection" checked="checked" /><br/>
      <bean:message key="lbl.chkSelective" /> 
       <input type="radio" onclick="selectiveProduct();"  name="radioSelection" value="S" id="singleselection"  />
     </logic:notPresent>
   
</tr>
        
    <TR>
    

           <td><bean:message key="lbl.product" /></td>
               
       <td>
		  <html:select styleId="showProductDesc" value="${requestScope.upavo.showProductDesc}" property="showProductDesc" size="5" multiple="multiple" style="width: 120" tabindex="-1">-->
         	 <logic:present name="productList">
			<logic:notEmpty name="productList">
			<html:optionsCollection name="productList" label="showProductDesc" value="lbxProductId" />
			</logic:notEmpty>
			</logic:present>
		   </html:select>
		   <html:hidden property="lbxProductId" styleId="lbxProductId" value="${requestScope.list[0].lbxProductId}" />
		       <html:button property="productbutton" styleId="productbutton" onclick="return openMultiSelectLOVCommon(283,'userProductAccessAdd','showProductDesc','','','','','','lbxProductId');" value=" " styleClass="lovbutton"></html:button>		   
        </td>
       
           <td><bean:message key="lbl.active" /></td>

	  			<logic:equal value="A" name="status" >
	  				<td><input type=checkbox id="recStatus" name="recStatus" checked="checked"/></td>
	  			</logic:equal>
	  			<logic:notEqual value="A" name="status">
	  				<td><input type=checkbox id="recStatus" name="recStatus"/></td>
	  			</logic:notEqual>

      
      
         </TR>
         
         

    
    <tr><td>
    
   
    <button type="button"   id="save" title="Alt+V" accesskey="V" onclick="return saveUserProductAccess();" class="topformbutton2" ><bean:message key="button.save" /></button>
  
   
    </td> </tr>

	</table>		


</logic:present>

<logic:notPresent name="editVal">
  <table width="100%">
    
   
<tr>
   
      <td ><bean:message key="lbl.userNam" /><span><font color="red">*</font></span></td>
      <td>
      		<html:text property="showUserDesc" styleClass="text" value="${requestScope.upavo.showUserDesc}"  styleId="showUserDesc" maxlength="100" readonly="true" />
			<html:hidden property="lbxUserId" styleId="lbxUserId" value="${requestScope.upavo.lbxUserId}" />
			<logic:notPresent name="list">
			<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(284,'userProductAccessAdd','showUserDesc','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  />
			</logic:notPresent>
	</td>

	 <td><bean:message key="lbl.ProductAccess" /><span><font color="red">*</font></span></td>
      <td><bean:message key="lbl.chkAll" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
      <input type="radio" onclick="allProduct();" name="radioSelection" value="A" id="allselection"/><br/>
      <bean:message key="lbl.chkSelective" /> 
      <input type="radio" onclick="selectiveProduct();"  name="radioSelection" value="S" id="singleselection"/>
    </td>
</tr>
        
    <TR>
           <td><bean:message key="lbl.product" /></td>
        <td>
           <html:select styleId="showProductDesc"  property="showProductDesc" size="5" multiple="multiple" style="width: 120" tabindex="-1">
		   </html:select>
		    <html:hidden property="lbxProductId" styleId="lbxProductId" value="${requestScope.upavo.lbxProductId}" />
		       <html:button property="productbutton" styleId="productbutton" onclick="return openMultiSelectLOVCommon(283,'userProductAccessAdd','showProductDesc','','', '','','','lbxProductId');" value=" " styleClass="lovbutton"></html:button>	   
        </td>
       
           <td><bean:message key="lbl.active" /></td>
   			<td><input type=checkbox id="recStatus" name="recStatus" checked="checked"/></td>
	  	</TR>
         
         

    
    <tr><td>
    
     <button type="button" id="save" title="Alt+V" accesskey="V" onclick="return saveUserProductAccess();" class="topformbutton2" ><bean:message key="button.save" /></button>  
   
    </td> </tr>

	</table>		

      </logic:notPresent> 
     

</fieldset>       
	</html:form>
	
	<logic:present name="sms">
<script type="text/javascript">
    if(('<%=request.getAttribute("sms")%>')== "S")
	{
		alert("<bean:message key="lbl.dataSave" />");
		
		document.getElementById("userProductAccessAdd").action="userProductAccessBehindAction.do";
	    document.getElementById("userProductAccessAdd").submit();
	}
	if(('<%=request.getAttribute("sms")%>')== "N") 
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		
	}
	if(('<%=request.getAttribute("sms")%>')== "M")
	{
		alert("<bean:message key="msg.UserProductAccessUpdated" />");
		
		document.getElementById("userProductAccessAdd").action="userProductAccessBehindAction.do";
	    document.getElementById("userProductAccessAdd").submit();
	}
</script>
</logic:present>		
		
  </body>
		</html:html>