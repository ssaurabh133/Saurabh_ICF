 
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/underWriter.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/masterScript/irrCalculation.js"></script>
		<%
			ResourceBundle resource =  ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
			
			com.login.roleManager.UserObject userobj=(com.login.roleManager.UserObject)session.getAttribute("userobject");
		
		if(userobj!=null){
			
			session.setAttribute("userId", userobj.getUserId());
		}
		%>
		
<!--[if IE 8]>
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
	<body onunload="closeAllLovCallUnloadBody()">
	<div  align="center" class="opacity" style="display:none;" id="processingImage"></div>
	<input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>

	<html:form action="/irrCalculation" method="post" styleId="irrCalculationSearchForm">
	<input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<input type="hidden" id="irrID" name="irrID" value="${irrID}"/>
	
	<fieldset>	  
	  <legend>
	  <bean:message key="lbl.irrCalculationSearch"/>

	  </legend>         
	    <logic:notPresent name="modify">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr> 
		<td><bean:message key="lbl.product"/><span><font color="red">*</font></span></td> 
       
        <td> 
          
	        <html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(6001,'irrCalculationSearchForm','product','','','','','','productId')" value=" " styleClass="lovbutton"> </html:button>
	       	<input type="hidden" id="productId" name="productId"/>
	       </td>
        
        
        <td width="8%"><bean:message key="lbl.scheme"/><span><font color="red">*</font></span></td>
            
		     <td width="20%">
            
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          	   <html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          	   <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(22,'irrCalculationSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" value=" " styleClass="lovbutton"> </html:button>
               <%-- <img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" />--%>
           	   <input type="hidden" id="schemeId" name="schemeId"/> 	 
           	   		    
             </td>
		</tr> 
		<tr>
		<td width="20%"><bean:message key="lbl.irrType"/><span><font color="red">*</font></span></td>
		<td width="35%">			
			<html:text property="irrType" styleId="irrType" styleClass="text"  value="" readonly="true" onchange="" tabindex="-1"></html:text>
			<input type="button" class="lovbutton" id="irrButton" onclick="openLOVCommon(6002,'irrCalculationSearchForm','irrType','','','','','','lbxIrrType')" value=" " tabindex="3" name="dealButton"/>
			<html:hidden property="lbxIrrType" styleId="lbxIrrType" value="" styleClass="text"></html:hidden>
	    </td>
	    </tr>
	    <tr>
	  
	    <td width="20%"><bean:message key="lbl.irrChargeCode"/><span><font color="red">*</font></span></td>
	    
	    <td id="chargecodeV"> 
	      <select id="irrChargeCode"  name="irrChargeCode" size="5" multiple="multiple" style="width: 150px"></select>
          <html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(5058,'irrCalculationSearchForm','irrChargeCode','','', '','','','lbxIrrChargeCode');" value=" " styleClass="lovbutton"></html:button>
          <html:hidden property="lbxIrrChargeCode" styleId="lbxIrrChargeCode" value="" styleClass="text"></html:hidden>	      
        </td>
    
	   
	    </tr>
	    
		 <tr>
		    <td align="left" class="form2" colspan="4">
		     <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return irrCalSave();" class="topformbutton2" ><bean:message key="button.save" /></button>
		  </td>
		  </tr>
		</table>
		
	      </td>
	      </tr>
	
	</table>
	
	</logic:notPresent>
	
	 <logic:present name="modify">	
	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr> 
		<td><bean:message key="lbl.product"/><span><font color="red">*</font></span></td>        
        <td> 
        	<html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1" value="${requestScope.list[0].product}"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${requestScope.list[0].lbxProductID}" />
	    </td>        
        <td width="8%"><bean:message key="lbl.scheme"/><span><font color="red">*</font></span></td>
        <td width="20%">
        	<html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1" value="${requestScope.list[0].scheme}"/>
          	<html:hidden  property="lbxscheme" styleId="lbxscheme"  value="${requestScope.list[0].lbxscheme}"/>
        </td>
		</tr> 
		<tr>
		<td width="20%"><bean:message key="lbl.irrType"/><span><font color="red">*</font></span></td>
		<td width="35%">
			<html:text property="irrType" styleId="irrType" styleClass="text" readonly="true" value="${requestScope.list[0].irrType}" onchange="" tabindex="-1"></html:text>
			<html:hidden property="lbxIrrType" styleId="lbxIrrType" value="${requestScope.list[0].lbxIrrType}" styleClass="text"></html:hidden>
	    </td>
	    </tr>
	    <tr>	  
	    <td width="20%"><bean:message key="lbl.irrChargeCode"/><span><font color="red">*</font></span></td>	    
	    <td id="chargecodeV"> 
	    	<html:hidden property="lbxIrrChargeCode" styleId="lbxIrrChargeCode" value="${requestScope.list[0].lbxIrrChargeCode}" styleClass="text"></html:hidden>	
	    	<html:select property="irrChargeCode" styleId="irrChargeCode" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="chargeDetail">
       			 <html:optionsCollection name="chargeDetail" value="chargeCode" label="chargeDesc"/>
      		</logic:present>
     		</html:select>
     		<html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(5058,'irrCalculationSearchForm','irrChargeCode','','', '','','','lbxIrrChargeCode');" value=" " styleClass="lovbutton"></html:button>
     	</td>
	    </tr>	    
		<tr>
		    <td align="left" class="form2" colspan="4">
		     <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return irrCalUpdate();" class="topformbutton2" ><bean:message key="button.save" /></button>
		  </td>
		</tr>
		</table>
		
	      </td>
	      </tr>
	
	</table>
	</logic:present>
	 
	</fieldset>
	
	</html:form>	
<logic:present name="update">
<script type="text/javascript">
	alert('<bean:message key="lbl.dataModify" />');
	document.getElementById("irrCalculationSearchForm").action=document.getElementById("contextPath").value+"/irrCalculation.do?method=irrCalculationSearch&source=Y";
	document.getElementById("irrCalculationSearchForm").submit();
</script>		
</logic:present>
	
<logic:present name="save">
		<script type="text/javascript">
			alert('<bean:message key="msg.DataSaved" />');
			document.getElementById("irrCalculationSearchForm").action=document.getElementById("contextPath").value+"/irrCalculation.do?method=irrCalculationSearch&source=Y";
			document.getElementById("irrCalculationSearchForm").submit();
		</script>		
</logic:present>
<logic:present name="error">
<script type="text/javascript">
	if('<%=request.getAttribute("error").toString()%>'=='ER')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
		document.getElementById("irrCalculationSearchForm").action=document.getElementById("contextPath").value+"/irrCalculation.do?method=irrCalculationSearch&source=Y";
		document.getElementById("irrCalculationSearchForm").submit();
	}
	if('<%=request.getAttribute("error").toString()%>'=='EX')
	{
		alert('<bean:message key="lbl.dataExist" />');
		document.getElementById("irrCalculationSearchForm").action=document.getElementById("contextPath").value+"/irrCalculation.do?method=irrCalculationSearch&source=Y";
		document.getElementById("irrCalculationSearchForm").submit();
	}	
</script>
</logic:present>		
</body>
</html:html>