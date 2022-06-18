<!--Author Name : Vinod Kumar Gupta-->
<!--Date of Creation : 11-04-2013-->
<!--Purpose  : Advocate Report for Legal module-->
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
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoReports.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('loanNoButton').focus();
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

<body onload="enableAnchor();fntab();">
<html:form styleId="repoRejectCaseMisForm"  method="post"  action="/repossessedRejectCaseMISDispatchAction" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.repossessedRejectCaseMis" /></legend>
  <table width="100%">
  
   <tr>
		
			<td><bean:message key="lbl.loanNo" /></td>
	<td>
		<html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxLoanId" styleId="lbxLoanId"  />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(1614,'repossessedMisForm','loanNo','','lbxLoanId','','','','customerName');" value=" " styleClass="lovbutton"> </html:button>
		<input type="hidden" name="customerName" id="customerName"/>
	</td> 
	
		<td><bean:message key="lbl.repoStockyard" /></td>
		
		<td>
		<html:text property="stockYardDesc" styleId="stockYardDesc" value="${requestScope.list[0].stockYardDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxStockYard" styleId="lbxStockYard" value="${requestScope.list[0].lbxStockYard}"  />
		<html:button property="stockyardButton" styleId="stockyardButton" onclick="closeLovCallonLov1();openLOVCommon(1620,'repossessedMisForm','stockYardDesc','','','','','','lbxStockYard');" value=" " styleClass="lovbutton"> </html:button>
		</td>
		
      
    </tr>
    
    <tr>
    
    	<td><bean:message key="lbl.approverName" /></td>
		
		<td><html:text property="approverName" styleId="approverName" value="${requestScope.list[0].approverName}" styleClass="text"  tabindex="-1"/>
		<html:hidden  property="lbxUserId" styleId="lbxUserId" value="${requestScope.list[0].lbxUserId}"  />
		<html:button property="approverNameButton" styleId="approverNameButton" onclick="closeLovCallonLov1();openLOVCommon(1622,'repossessedMisForm','approverName','','','','','','lbxUserId');" value=" " styleClass="lovbutton"> </html:button>
		</td>
    
    	<td><bean:message key="lbl.agency" /></td>
		
		<td><html:text property="agency" styleId="agency" value="${requestScope.list[0].agency}" styleClass="text"  tabindex="-1"/>
		<html:hidden  property="lbxAgencyId" styleId="lbxAgencyId" value="${requestScope.list[0].lbxAgencyId}"  />
		<html:button property="agencyButton" styleId="agencyButton" onclick="closeLovCallonLov1();openLOVCommon(1624,'repossessedMisForm','agency','','','','','','lbxAgencyId');" value=" " styleClass="lovbutton"> </html:button>
		</td>
    
    </tr>
    
    <tr>
     	<td>
    
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<button type="button" name="generateReport" id="generateReport" title="Alt+G" accesskey="G" onclick="return generateRepoRjectCaseReport();" class="topformbutton3" ><bean:message key="button.generatereport" /></button>

    		<br>
    	</td> 
   </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		
  </body>
		</html:html>