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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalReports.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('legalAdvocateCaseReportForm').loanNo.focus();
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

<body onload="fntab();">
<html:form styleId="legalAdvocateCaseReportForm"  method="post"  action="/legalAdvocateCaseMisDispatchAction" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.advocateCaseReport" /></legend>
  <table width="100%">
  
    <tr>
		
		<td><bean:message key="lbl.legalReportLoanNo" /></td>
		<td>
		<html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxLoanId" styleId="lbxLoanId"  />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(1518,'legalAdvocateCaseReportForm','loanNo','','lbxLoanId','','','','customerName');" value=" " styleClass="lovbutton"> </html:button>
		<input type="hidden" name="customerName" id="customerName"/>
	</td> 
				
	<td><bean:message key="lbl.legalReportCaseType" /></td>
		<td>
		<html:text property="caseTypeDesc" styleId="caseTypeDesc" value="" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCaseTypeCode" styleId="lbxCaseTypeCode" value=""  />
		<html:button property="caseTypeButton" styleId="caseTypeButton" onclick="closeLovCallonLov1();openLOVCommon(1524,'legalAdvocateCaseReportForm','caseTypeDesc','','','','','','lbxCaseTypeCode');" value=" " styleClass="lovbutton"> </html:button>
		</td>
		
		
		<td><bean:message key="lbl.legalReprotAdvocate" /></td>
		<td>
		<html:text property="advocateDesc" styleId="advocateDesc" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxUserId" styleId="lbxUserId"  />
		<html:button property="advocateButton" styleId="advocateButton" onclick="closeLovCallonLov1();openLOVCommon(1535,'legalAdvocateCaseReportForm','advocateDesc','','','','','','lbxUserId');removeRejection();hideAsterik('A');" value=" " styleClass="lovbutton"> </html:button>
		</td>   
      
    </tr>
    
    <tr>
     	<td>
    
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<button type="button" name="generateReport" id="generateReport" title="Alt+G" accesskey="G" onclick="return generateLegalAdvocateCaseReport();" class="topformbutton3" ><bean:message key="button.generatereport" /></button>

    		<br>
    	</td> 
   </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		
  </body>
		</html:html>