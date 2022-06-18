<!--Author Name : Amit Kumar-->
<!--Date of Creation : 23-june-2014  -->
<!--Dedupe Decision Grid after search : -->
<%@page import="java.util.Map"%>
<%@page import="com.lowagie.text.SplitCharacter"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.connect.CommonFunction"%>
<%@page import="com.connect.ConnectionDAO"%>
<%@page import="com.cp.vo.dedupeReferalVo"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"	content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<%
			String path=request.getContextPath();
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
			request.setAttribute("no",no);
		String dealId=	(String)session.getAttribute("lbxDealNo");
		System.out.println("dealId::::"+dealId);
		String query="select count(1) from cr_customer_search_dtl  where deal_id='"+dealId+"' and ifnull(dedupe_remarks,'')='' and ifnull(dedupe_decision,'')=''; ";	
		String res=ConnectionDAO.singleReturn(query);
		request.setAttribute("DEDUPE_REMARKS",res);
			
			%>	
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>	
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/dedupeReferal.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
	
		
		</head>
<body onload="checkChanges('dedupeReferalSearch');radioCheck();enableAnchor();">
	<html:form action="/dedupeReferalSearch" method="post" styleId="dedupeReferalSearch" >
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
		<html:hidden property="dedupeRemark" styleId="dedupeRemark" value="${requestScope.DEDUPE_REMARKS}" />
		<fieldset>	
		 <legend><bean:message key="lbl.customer_detail"/></legend>  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			 <td>
   				 <table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
    	 			 <logic:present name="list">
    	  		 
    	  			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
			   			 <td class="gridtd">
			   			 <table width="100%" border="0" cellspacing="1" cellpadding="1">
			   				 <tr class="white2">
			        		
			        				<td><strong><bean:message key="lbl.select" /> </strong></td>
									<td><strong><bean:message key="lbl.leadDalNo" /> </strong></td>
									<td><strong><bean:message key="lbl.customerName" /> </strong></td>
									<%-- <td><strong><bean:message key="msg.dedupefatherhus" /> </strong></td>
									<td><strong><bean:message key="msg.partner" /> </strong></td>
									<td><strong><bean:message key="msg.dedupeaddress1" /> </strong></td> --%>
									<td><strong><bean:message key="lbl.dob" />/<bean:message key="lbl.incorporationDate" /> </strong></td>
									<%-- <td><strong><bean:message key="msg.dateOfInc" /> </strong></td>
									<td><strong><bean:message key="msg.dedupepan" /> </strong></td>
									<td><strong><bean:message key="msg.passport" /> </strong></td>
									<td><strong><bean:message key="msg.registration" /> </strong></td> --%>
									<td><strong><bean:message key="lbl.applicantType" /> </strong></td>
							        <td><strong><bean:message key="msg.preDealStatus" /> </strong></td>
											
											<%-- <logic:notEqual value = "INDIVIDUAL" name ="list" property = "applicantType">
													<td><strong><bean:message key="lbl.select" /> </strong></td>
													<td><strong><bean:message key="lbl.leadDalNo" /> </strong></td>
													<td><strong><bean:message key="lbl.customerName" /> </strong></td>
													<td><strong><bean:message key="msg.partner" /> </strong></td>
													<td><strong><bean:message key="msg.dedupeaddress1" /> </strong></td>
													<td><strong><bean:message key="msg.dateOfInc" /> </strong></td>
													<td><strong><bean:message key="msg.dedupepan" /> </strong></td>
													<td><strong><bean:message key="msg.registration" /> </strong></td>
													<td><strong><bean:message key="lbl.applicantType" /> </strong></td>
											</logic:notEqual> --%>
			   			    </tr>
				<logic:present name="list" >
					
					<logic:notEmpty name="list" >
					<% String dupCustId = CommonFunction.checkNull(request.getAttribute("custId")); %>
				
						<logic:iterate id="list" name="list">
						<%String chkDupCustId = ((dedupeReferalVo)list).getDupCustomerID(); %>
					
						<tr class="white1" >
			 					
			 				<td   >
			 				<%if(dupCustId.equalsIgnoreCase(chkDupCustId)) {%>
			 				
			 				<input type='radio' name='chk' id='chk' value=${list.dupCustomerID } onclick="return getDedupeCusData();" checked="checked"/>
			 				<%}else{ %>
			 				<input type='radio' name='chk' id='chk' value=${list.dupCustomerID } onclick="return getDedupeCusData();" />
			 				<%} %>
			 				</td>
			 				
								<td><a href="#" id="dupDealNO" onclick="return linkDeal('${list.dupCustomerID }', '${list.applicantCategory}','${list.applicantType}');">${list.dupDealNO }</a></td>
								
								<%-- <logic:present name="PreDeal" >
			 					<td>${list.dupDealNO }</td>
			 					</logic:present>
			 					<logic:notPresent name="PreDeal">
			 					<td><a href="#" id="dupDealNO" onclick="return linkDeal('${list.dupCustomerID }', '${list.applicantCategory}','${list.applicantType}');">${list.dupDealNO }<!-- </a> --></td>
			 					</logic:notPresent> --%>
			 										
					
											<td>${list.dupCustomerName } </td>
											<td>${list.dedupeDOB } </td>
											<td>${list.customerRole } </td>
											<td>${list.dealStatus }</td>
								
						
					
							<html:hidden property="updateFlag" styleId="updateFlag" value="updateFlag"/>
							<html:hidden property="applicantType" styleId="applicantType" value="${list.applicantType}" />
							<html:hidden property="applicantCategory" styleId="applicantCategory" value="${list.applicantCategory}" />
							<%-- <html:hidden property="lbxDealNo" styleId="lbxDealNo" value="${list.lbxDealNo }" />--%>
							<html:hidden property="voterId" styleId="voterId" value="${list.voterId}" />
							<html:hidden property="dlnumber" styleId="dlnumber" value="${list.dlnumber}" />
							<html:hidden property="passsport" styleId="passsport" value="${list.passsport}" />
							<html:hidden property="pan" styleId="pan" value="${list.pan}" />
							<html:hidden property="uidno" styleId="uidno" value="${list.uidno}" />
							<html:hidden property="tinNum" styleId="tinNum" value="${list.tinNum}" />
							<html:hidden property="dist" styleId="dist" value="${list.dist}" />
							<html:hidden property="address1" styleId="address1" value="${list.address1}" />
							<html:hidden property="address2" styleId="address2" value="${list.address2}" />
							<html:hidden property="address3" styleId="address3" value="${list.address3}" />
							<html:hidden property="fName" styleId="fName" value="${list.fName}" />
							<html:hidden property="mName" styleId="mName" value="${list.mName}" />
							<html:hidden property="lName" styleId="lName" value="${list.lName}" />
								
					   </tr>
						</logic:iterate>
					</logic:notEmpty>
		
		   	</logic:present>
		 </table>  
	  </td>
	</tr>
 </table>
 </logic:present>
    	  		 
    	
	</tr>
	<%-- <logic:present name="list">
	<tr>
		  	<td align="left">
		 	    
		 	    <button type="button" name="dsearch" id="dsearchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return getDedupeCusData();"><bean:message key="button.search" /></button>
		     
		    </td>
		 </tr>
		 </logic:present>
		 --%>
	
 </table>    </td>
</tr>
</table>
	</fieldset>
	
	
	
	<fieldset>	
	
		<legend><bean:message key="lbl.matchingRecord"/></legend>    
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
			<table width="100%" border="0" cellspacing="1" cellpadding="1" id="matchingtable">
    <tr class="white2">
     <td width="3%" ><input type="radio" name="chk1" id="allchk" onclick="allChecked();" disabled="disabled"/></td> 
	 
       					<logic:present name="list1">
       							
       								
       							
       							<%	
       							ArrayList volist = (ArrayList)request.getAttribute("list1");
       								if(volist!=null && volist.size()>0)
       								{
       									dedupeReferalVo dvo = (dedupeReferalVo)volist.get(0);
       									System.out.println(CommonFunction.checkNull(dvo.getApplicantType())+" Applicant Type ");
       									System.out.println(CommonFunction.checkNull(dvo.getApplicantCategory())+" ApplicantCategory ");
											if(dvo.getApplicantType().equalsIgnoreCase("INDIVIDUAL"))
       									{
       								
       								
       								%>
								<td><strong><bean:message key="lbl.leadDalNo" /> </strong></td>
								<td><strong><bean:message key="lbl.corporateName" /> </strong></td>
								<td><strong><bean:message key="fatherHusband" /> </strong></td>
								<td><strong><bean:message key="msg.dedupeaddress1" /> </strong></td>
								<td><strong><bean:message key="msg.dedupedob" /> </strong></td>
								<td><strong><bean:message key="msg.dedupepan" /> </strong></td>
								<td><strong><bean:message key="msg.passport" /> </strong></td>
								<td><strong><bean:message key="msg.preDealStatus" /> </strong></td>
								<td><strong><bean:message key="msg.dedupeRemark" /> </strong></td>
								<td><strong><bean:message key="msg.match" /> </strong></td> 
								<td><strong><bean:message key="msg.dedupesource" /> </strong></td>
								<%} else {%>
								
       						
								<td><strong><bean:message key="lbl.leadDalNo" /> </strong></td>
								<td><strong><bean:message key="lbl.corporateName" /> </strong></td>
								<td><strong><bean:message key="msg.partner" /> </strong></td>
								<td><strong><bean:message key="msg.dedupeaddress1" /> </strong></td>
								<td><strong><bean:message key="msg.dateOfInc" /> </strong></td>
								<td><strong><bean:message key="msg.dedupepan" /> </strong></td>
								<td><strong><bean:message key="msg.registration" /> </strong></td>
								<td><strong><bean:message key="msg.preDealStatus" /> </strong></td>
								<td><strong><bean:message key="msg.dedupeRemark" /> </strong></td>
								 <td><strong><bean:message key="msg.match" /> </strong></td> 
								<td><strong><bean:message key="msg.dedupesource" /> </strong></td>
							<%}} else{%>
								<script type="text/javascript">
										alert("<bean:message key="lbl.dedupeNoMatch" />");
										</script>
								<td><strong><bean:message key="lbl.leadDalNo" /> </strong></td>
								<td><strong><bean:message key="lbl.corporateName" /> </strong></td>
								<td><strong><bean:message key="msg.partner" /> </strong></td>
								<td><strong><bean:message key="msg.dedupeaddress1" /> </strong></td>
								<td><strong><bean:message key="msg.dateOfInc" /> </strong></td>
								<td><strong><bean:message key="msg.dedupepan" /> </strong></td>
								<td><strong><bean:message key="msg.registration" /> </strong></td>
								<td><strong><bean:message key="msg.preDealStatus" /> </strong></td>
								<td><strong><bean:message key="msg.dedupeRemark" /> </strong></td>
								 <td><strong><bean:message key="msg.match" /> </strong></td> 
								<td><strong><bean:message key="msg.dedupesource" /> </strong></td>
						<tr class="white1" >	<td colspan="12" align="center"><bean:message key="lbl.dedupeNoMatch"/></td>  </tr>
							<%} %>
							</logic:present>
	</tr>
	
	     <logic:present name="list1" scope="request">
		      <logic:notEmpty name="list1" >
		    
				<logic:iterate id="list1" name="list1">
				 
				
		
					<tr class="white1" >
					<td>${list1.dedupeFlag }</td>
									<td>
						       <logic:present name="PreDeal" >
			 						<td>${list.dupCustomerID }</td>
			 					</logic:present>
			 					
			 					<logic:notPresent name="PreDeal">
				 					<logic:equal value = "INTERNAL" name ="list1" property = "source">
									<a href="#" id="dupDealNO" onclick="return linkDeal('${list1.dupCustomerID }', '${list1.applicantCategory}','${list1.applicantType}');">${list1.dupDealNO }</a>
									</logic:equal>
									<logic:notEqual value = "INTERNAL" name ="list1" property = "source">
									${list1.dupCustomerID }
									</logic:notEqual>
			 					</logic:notPresent>
			 					
									
								
								</td>	
									
									
								<%-- <logic:equal value = "INTERNAL" name ="list1" property = "source">
						<a href="#" id="dupDealNO" onclick="return linkDeal('${list1.dupCustomerID }', '${list1.applicantCategory}','${list1.applicantType}');">${list1.dupDealNO }</a>
							
								</logic:equal>
								<logic:notEqual value = "INTERNAL" name ="list1" property = "source">
								${list1.dupCustomerID }
								</logic:notEqual>
								</td>
								 --%>
							<logic:equal value = "INDIVIDUAL" name ="list1" property = "applicantType"> 
							
						
	
      		<%
			
			String custIdDedupe = CommonFunction.checkNull(request.getAttribute("custId"));
      		ArrayList voDedupeList = (ArrayList)request.getAttribute("list");
      		Iterator iterator = voDedupeList.iterator();
      		int count= -1;
      		while(iterator.hasNext())
      		{
      		dedupeReferalVo ddvo = (dedupeReferalVo)iterator.next();
      		count = count+1;
      		if(ddvo.getDupCustomerID().equalsIgnoreCase(custIdDedupe))
      			break;
      		
      		}
			ArrayList list = (ArrayList)request.getAttribute("list");
      		dedupeReferalVo vo = (dedupeReferalVo)list.get(count);
			
			String name = ((dedupeReferalVo)list1).getDupCustomerName();
			String passport = ((dedupeReferalVo)list1).getPasssport();
			String father= ((dedupeReferalVo)list1).getDedupefatherhus();
			String address1= ((dedupeReferalVo)list1).getAddress1();
			String dob= ((dedupeReferalVo)list1).getDedupeDOB();
			String pan= ((dedupeReferalVo)list1).getPan();
      		if(name.equalsIgnoreCase(vo.getDupCustomerName())) {%>
				
					 <td><font color="red">${list1.dupCustomerName }</font></td>
<%}else{%>
				
							<td>${list1.dupCustomerName }</td>
			<%}if(father.equalsIgnoreCase(vo.getDedupefatherhus())) {%>	
							<td><font color="red">${list1.dedupefatherhus }</font></td>
							<%}else{%>
							<td>${list1.dedupefatherhus }</td>
							<%}if(address1.equalsIgnoreCase(vo.getAddress1())) {%>	
							<td><font color="red">${list1.address1 }</font></td>
							<%}else{%>
							<td>${list1.address1 }</td> 
						<%}if(dob.equalsIgnoreCase(vo.getDedupeDOB())) {%>	
							<td><font color="red">${list1.dedupeDOB }</font></td>
							<%}else{%>
							<td>${list1.dedupeDOB }</td>  
							<%}if(pan.equalsIgnoreCase(vo.getPan())) {%>
							<td><font color="red">${list1.pan }</font></td>
								<%}else{%>
							<td>${list1.pan }</td>
							<%}if(passport.equalsIgnoreCase(vo.getPasssport())) {%>
							<td><font color="red">${list1.passsport }</font></td>
							<%}else{%>
							<td>${list1.passsport }</td> 
							<%}%>
				        	<td>${list1.dealStatus }</td>
						  <td> <html:textarea property="dedupeRemarks1" styleId="dedupeRemarks1" styleClass="text" value="${list1.dedupeRemarks1 }"  readonly="true"/></td>
						
							<td title="MATCH RECORDS: &#013; field : value &#013; ${list1.match}">${list1.matchValue}</td>
						
							<td>${list1.source }</td>
						</logic:equal>
							
								<logic:equal value = "CORPORATE" name ="list1" property = "applicantType">
						<%			String custIdDedupe = CommonFunction.checkNull(request.getAttribute("custId"));
      		ArrayList voDedupeList = (ArrayList)request.getAttribute("list");
      		Iterator iterator = voDedupeList.iterator();
      		int count= -1;
      		while(iterator.hasNext())
      		{
      		dedupeReferalVo ddvo = (dedupeReferalVo)iterator.next();
      		count = count+1;
      		if(ddvo.getDupCustomerID().equalsIgnoreCase(custIdDedupe))
      			break;
      		
      		}
			ArrayList list = (ArrayList)request.getAttribute("list");
      		dedupeReferalVo vo = (dedupeReferalVo)list.get(count);
			String name = ((dedupeReferalVo)list1).getDupCustomerName();
			String partner1 = ((dedupeReferalVo)list1).getPartner();
			String partner= vo.getPartner();
			String[] partnerArray = partner.split(",");
			String[] partner1Array = partner1.split(",");
			int len =partnerArray.length;
			int len1=partner1Array.length;
			StringBuffer bufInsSql=new StringBuffer();
				StringBuffer bufInsSql1=new StringBuffer();
			String address1= ((dedupeReferalVo)list1).getAddress1();
			String doi= ((dedupeReferalVo)list1).getDateOfInc();
			String pan= ((dedupeReferalVo)list1).getPan();
			String registration= ((dedupeReferalVo)list1).getRegistration();
				
      		if(name.equalsIgnoreCase(vo.getDupCustomerName())) {%>
				
					 <td><font color="red">${list1.dupCustomerName }</font></td>
<%}else{%>	
					
						<td>${list1.dupCustomerName }</td> 
						
						<%}%>
      		<%
			for(int ii=0;ii<len;ii++){
				for(int j=0;j<len1;j++){
				if(partnerArray[ii].equalsIgnoreCase(partner1Array[j])) {
					 bufInsSql.append(partner1Array[j]+"||");
					
					
					}
				
				}
					
			}
			for(int ii=0;ii<len;ii++){
				for(int j=0;j<len1;j++){
				if(!partnerArray[ii].equalsIgnoreCase(partner1Array[j])) {
					if(!(bufInsSql1.toString().contains(partner1Array[j]) ) 
					&& !bufInsSql.toString().contains(partner1Array[j]) ) 
					bufInsSql1.append(partner1Array[j]+"||");
					
					
					}
					}
					}
			
      		%>
						<td>
						<font color="red"><%=bufInsSql %></font><%=bufInsSql1 %>
						</td>
				
					<%if(address1.equalsIgnoreCase(vo.getAddress1())) {%>	
							<td><font color="red">${list1.address1 }</font></td>
							<%}else{%>		
						<td>${list1.address1 }</td> 
						<%}if(doi.equalsIgnoreCase(vo.getDedupeDOB())) {%>	
							<td><font color="red">${list1.dateOfInc }</font></td>	
							<%}else{%>			
						 <td>${list1.dateOfInc }</td>	
						<%}if(pan.equalsIgnoreCase(vo.getPan())) {%>
							<td><font color="red">${list1.pan }</font></td>
								<%}else{%>
							<td>${list1.pan }</td>
					<%}if(registration.equalsIgnoreCase(vo.getRegistration())) {%>		
					<td><font color="red">${list1.registration }</font></td>
					<%}else{%>
						<td>${list1.registration }</td> 
							<%} %>
						
							<td>${list1.dealStatus }</td>
							
					  <td> <html:textarea property="dedupeRemarks1" styleId="dedupeRemarks1" styleClass="text" value="${list1.dedupeRemarks1 }"  readonly="true"/></td>
							
							<td title="MATCH RECORDS: &#013; field : value &#013; ${list1.match}">${list1.matchValue}</td>
							
							<td>${list1.source }</td>
					</logic:equal>
						<html:hidden property="updateFlag" styleId="updateFlag" value="updateFlag"/>
						<html:hidden property="applicantType" styleId="applicantType" value="${list1.applicantType}" />
						<html:hidden property="voterId" styleId="voterId" value="${list1.voterId}" />
						<html:hidden property="dlnumber" styleId="dlnumber" value="${list1.dlnumber}" />
						<html:hidden property="passsport" styleId="passsport" value="${list1.passsport}" />
						<html:hidden property="pan" styleId="pan" value="${list1.pan}" />
						<html:hidden property="uidno" styleId="uidno" value="${list1.uidno}" />
						<html:hidden property="tinNum" styleId="tinNum" value="${list1.tinNum}" />
						<html:hidden property="dist" styleId="dist" value="${list1.dist}" />
						<html:hidden property="address1" styleId="address1" value="${list1.address1}" />
						<html:hidden property="address2" styleId="address2" value="${list1.address2}" />
						<html:hidden property="address3" styleId="address3" value="${list1.address3}" />
						<html:hidden property="fName" styleId="fName" value="${list1.fName}" />
						<html:hidden property="mName" styleId="mName" value="${list1.mName}" />
						<html:hidden property="lName" styleId="lName" value="${list1.lName}" />
						<html:hidden property="vatNo" styleId="vatNo" value="${list1.vatNo}" />
			       </tr>				
	          </logic:iterate>
	       </logic:notEmpty>
	  </logic:present> 
	
		</table>
		</td></tr>
</table>
</fieldset>
	
	<fieldset>
 <legend><bean:message key="lbl.dedupeDecisionAuthor"/></legend>
<table cellspacing=0 cellpadding=0 width="100%" border=0>
	<tr>
	<logic:notPresent name="view">
      <td><bean:message key="lbl.decison"/><font color="red">*</font></td>
	<logic:equal name ="decison" value = "Reject" >
	   <td><html:select property="decison" styleId="decison" styleClass="text" value="">
		 	<option value="X">Reject</option>
			<option value="A">Approved</option>
 		</html:select></td> 
	</logic:equal>
   <logic:notEqual name ="decison" value = "Reject" >
	   <td><html:select property="decison" styleId="decison" styleClass="text" value="">
		 	<option value="A">Approved</option>
		 	<option value="X">Reject</option>
 		</html:select></td> 
	</logic:notEqual>
             
        <td><bean:message key="lbl.remark"/><font color="red">*</font></td>
       
 
	 <td> <html:textarea property="dedupeRemarks" styleId="dedupeRemarks" styleClass="text" value="${remarks}"  /></td>
        </logic:notPresent>
        
	</tr>
	<tr>
	<logic:present name="view">
     <td><bean:message key="lbl.decison"/><font color="red">*</font></td>
     <td><html:textarea property="decison" styleId="decison" styleClass="text" value="${decison}"  readonly="true"/></td>
         
        <td><bean:message key="lbl.remark"/><font color="red">*</font></td>
        <td> <html:textarea property="dedupeRemarks" styleId="dedupeRemarks" styleClass="text" value="${remarks}"  readonly="true"/></td>
        </logic:present>
	</tr>
	<logic:notPresent name="view">
<tr>
 <td>
<button type="button" name="search" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return dealAuthorRemarks();"><bean:message key="button.save" /></button>
 </td>
  <td>
<logic:equal name="functionId" value="8000237">
<button type="button" name="search" id="forward" class="topformbutton2" title="Alt+F" accesskey="F" onclick="return dealAuthorforward();"><bean:message key="button.forward" /></button>
 </td>
</logic:equal>
 <logic:present name="list1">
  <td>
<button type="button" name="export Data" id="export_data" class="topformbutton2" title="Alt+E" accesskey="E" onclick="return exportData();"><bean:message key="button.exportData" /></button>
 </td>
 </logic:present>
</tr>
</logic:notPresent>
 </table>
</fieldset>

	</html:form>

	
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>	
	<logic:present name="msg">
	<script type="text/javascript">	
	if('<%=request.getAttribute("msg")%>'=='F')
	{
			alert("<bean:message key="lbl.dataSavedSucc" />");
	}
	else if('<%=request.getAttribute("msg")%>'=='N')
	{
		alert("<bean:message key="lbl.dataNtSavedSucc" />");
	}
	else if('<%=request.getAttribute("msg")%>'=='D')
	{
		alert("<bean:message key="lbl.canForward" />");
	}
	else if('<%=request.getAttribute("msg")%>'=='Z')
	{
		alert("First save Dedupe Referral and then move to Lead Decison.");
	}
	else if('<%=request.getAttribute("msg")%>'=='R')
		{
		alert("<bean:message key="lbl.rejectUnder" />");
		}
	else if('<%=request.getAttribute("msg")%>'=='S')
	{
	alert("<bean:message key="msg.ForwardNonEmiSuccessfully" />");
	   parent.location='<%=request.getContextPath()%>/dedupeReferalSearch.do?method=ForwordCusData';
	}

	</script>
	</logic:present>
	<script>
	setFramevalues("dedupeReferalSearch");
</script>
</body>
</html>