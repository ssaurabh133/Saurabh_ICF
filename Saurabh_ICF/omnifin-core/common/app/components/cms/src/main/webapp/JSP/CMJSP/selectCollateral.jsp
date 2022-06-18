<%--
      Author Name-     prashant  Kumar 
      Date of creation -20/04/2011
      Purpose-          Entry of Collateral Detail
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 


<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'selectCollateral.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		 	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body onload="enableAnchor();">
  <form action="selectProcessAction.do" id="selectForm">
  <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
    <fieldset>
	<legend><bean:message key="lbl.details"/></legend>
	
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    		       <tr>        
        			 <td class="white2" style="width:100px;"><span style="width:23%"><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><b><bean:message key="lbl.select" /></b></span></td>
        			   <td class="white2" style="width:100px;"><span style="width:23%"><strong><bean:message key="lbl.Assets/Collateral" /></strong></span></td>
        			  <td class="white2" style="width:100px;"><span style="width:23%"><strong><bean:message key="lbl.collateralSecurityType" /></strong></span></td>
        			 <td class="white2" style="width:100px;"><span style="width:23%"><strong><bean:message key="lbl.collateralDescription" /></strong></span></td>
		             <td class="white2" style="width:100px;"><span style="width:23%"><strong><bean:message key="lbl.collateralValue" /></strong></span></td>
                    
		            	
	               </tr>
	                <logic:present name="showCollateralDetails">
	                 <logic:iterate id="showCollateralDetails1" name="showCollateralDetails">	 
	               <tr> 
  				  <td class="white"><input type="checkbox" name="chk" id="chk" value="${showCollateralDetails1.assetsId }"/></td>	  				                  
	               <td class="white"><a href="#" id=collupdate" onclick="return collateralUpdate('${showCollateralDetails1.colltype1 }','${showCollateralDetails1.assetsId }');"/>${showCollateralDetails1.colltype1 }</a></td>	                      
	                   <td class="white">${showCollateralDetails1.colltype2 }</td>
	                  <td class="white">${showCollateralDetails1.assetsCollateralDesc }</td>
	                  <td class="white">${showCollateralDetails1.assetsCollateralValue }</td>		                
	               </tr>	 
                    </logic:iterate>
                    </logic:present>      
                </table>
              </td>
            </tr>            				
		<tr>	
 <td>    <button type="button" name="Submit20" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveCollateralDetails();"><bean:message key="button.save" /></button> </td>
		  </tr>	
		  
		 </table>
</fieldset>


<logic:present name="sms">

<script type="text/javascript">

if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.assetSuccess" />');
	}
	else
	{
		alert('<bean:message key="lbl.assetError" />');
	}	
  // opener.document.location.reload(true);
   opener.window.location.href="collaterlInCMBehindAction.do";
   window.close();
     
</script>
</logic:present>

</form>
  </body>
</html>
