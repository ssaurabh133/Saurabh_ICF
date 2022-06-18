<!--Author Name : Saurabh Singh-->
<!--Date of Creation : 16-April -2013-->
<!--Purpose  : Information of Link Loan  Maker Add-->
<!--Documentation : -->

<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <title><bean:message key="a3s.noida" /></title>

		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/linkLoan.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);

	ArrayList myList=new ArrayList();	
	myList=(ArrayList)session.getAttribute("list");	
	request.setAttribute("list",myList);

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
  
  <body onload="enableAnchor();noChecked()">
    <html:form action="/linkLoanAuthorApprove" styleId="linkLoanMakerAddForm" method="post" >
  	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<html:hidden  property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>

  	<fieldset>
		<legend><bean:message key="lbl.linkLoanMaker" /></legend>		
  	<table width="100%">
	
	<tr>
      <td><bean:message key="lbl.loanNumber"></bean:message><span><font color="red">*</font></span></td>
		     <td>
		     	<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${getList[0].loanNo}" readonly="true" tabindex="-1"/>
             	<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${getList[0].lbxLoanNoHID}" />
             	<html:hidden  property="loanNoSave" styleId="loanNoSave"   value="${getList[0].lbxLoanNoHID}" />
            </td>

     <td><bean:message key="lbl.customerName"/></td>
	     	<td>
	     		<html:text property="customerName"  styleClass="text" styleId="customerName" value="${getList[0].customerName}" readonly="true" ></html:text>
	     	</td>   
    </tr>
    <tr>
    	<td><bean:message key="lbl.loanAmount" ></bean:message></td>
    	   <td>
    	     <html:text property="loanAmount"  styleClass="text"	styleId="loanAmount" readonly="true" value="${getList[0].loanAmount}" ></html:text>
    	   </td>
    	 <td><bean:message key="lbl.loanTenure" ></bean:message></td>
    	   <td>
    	    <html:text property="loanTenure"  styleClass="text"	styleId="loanTenure" readonly="true" value="${getList[0].loanTenure}" ></html:text>
    	   </td>
    </tr>

	 </table>
  	</fieldset>
  	
  	<fieldset>
  		<legend><bean:message key="lbl.linkMakerDetail" /></legend>
  		<logic:present name="list">
		   <logic:notEmpty name="list">		  
		     <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="50" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/linkLoanAuthorApprove.do" >
			    <display:setProperty name="paging.banner.placement"  value="bottom"/>
			    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
			    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
			    <display:column property="checkBoxLink" title="<input type='checkbox' name='checkId' id='allchkd'  onclick='noChecked();' />"/>
			    <display:column property="primaryLoanNumber" titleKey="lbl.primaryLoan"  sortable="true"  />
				<display:column property="loanNumber" titleKey="lbl.linkLoan"  sortable="true"  />
				<display:column property="custName"   titleKey="lbl.customerName"  sortable="true"  />
				<display:column property="loanAmt" titleKey="lbl.loanAmount"  sortable="true"  />
				<display:column property="loanTenureTable" titleKey="lbl.loanTenure"  sortable="true"  />
				<display:column property="loanBalPrin" titleKey="lbl.loanBalPrin"  sortable="true"  />
				<display:column property="loanStatus" titleKey="lbl.loanStatus"  sortable="true"  />	
		     </display:table>	     
		    </logic:notEmpty>
		  <logic:empty name="list">
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td class="gridtd">
							   <table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.loanNumber" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.customerName" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.loanAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.loanTenure" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.loanBalPrin" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.loanStatus" /> <br> </b>
									</td>
									<tr class="white2" >
	                                  <td colspan="7"><bean:message key="lbl.noDataFound" /></td>
                                    </tr>
							      </tr>
			            </table>
						</td>
					</tr>
				</table>
		</logic:empty>	

  </logic:present>
  	</fieldset>

    </html:form>
  </body>
</html:html>
