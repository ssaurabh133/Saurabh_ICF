 <!--Author Name : Apoorva Joshi 
Date of Creation : 09-June-2011
-->

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
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/documentChecklistMaster.js"></script> 	
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
  <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript">

function addRow(val)
	{
	 // alert(val);
	  var ptable = document.getElementById('gridtable');
	  var lastElement = ptable.rows.length;
	  //var index=lastElement;
	  //document.getElementById("psize").value=1;
	 
      var pcheck = document.getElementById("pcheck").value; 
	  var psize= document.getElementById("psize").value;	
	if(psize==""){
		psize=lastElement;
	}

     //  alert(index);
	 var row =ptable.insertRow(lastElement);
	 row.setAttribute('className','white1' );
     row.setAttribute('class','white1' );
			
      var cellCheck = row.insertCell(0);
  	  var element = document.createElement('input');
  	  element.type = 'checkbox';
  	  element.name = 'chk';
  	  element.id = 'chk'+psize;	 
	  if(val=="Edit"){
	  var ele=document.createElement('input');
	   ele.type = 'hidden';
	   ele.name = 'docCheckId';
	   ele.id = 'docCheckId'+psize;
	  }

      var docCodeSelect = row.insertCell(1);
	  var docCode = document.createElement('input');
	  docCode.type = 'text';
	  docCode.readOnly=true;
	  docCode.name = 'docId';
	  docCode.id = 'docId'+psize;
	  docCode.setAttribute('className','text');
	  docCode.setAttribute('class','text');

      var docDescSelect = row.insertCell(2);
      var newdiv = document.createElement('span');
      var basePath='<%= request.getContextPath()%>';
	  var docDesc = document.createElement('input');
	  docDesc.type = 'text';
	  docDesc.readOnly=true;
	  docDesc.name = 'docDes';
	  docDesc.id = 'docDes'+psize;
	  docDesc.setAttribute('className','text');
	  docDesc.setAttribute('class','text');
   		
	 newdiv.innerHTML ='<img  src=\''+basePath+'/images/theme1/lov.gif\'   onclick="openLOVCommon(76,\'chargeMasterForm\',\'docDes'+psize+'\',\'\',\'\',\'\',\'\',\'\',\'docId'+psize+'\')" Class="lovimg" />';
	 docDescSelect.appendChild(docDesc);
	 docDescSelect.appendChild(newdiv);
		

      var mandatorySelect = row.insertCell(3);
	  var mandatory = document.createElement('input');
	  mandatory.type = 'checkbox';
	  mandatory.name = 'docMandatory';
	  mandatory.id = 'docMandatory'+psize;

      var originalSelect = row.insertCell(4);
	  var original = document.createElement('input');
	  original.type = 'checkbox';
	  original.name = 'docOriginal';
	  original.id = 'docOriginal'+psize;

	  var expFlagSlect = row.insertCell(5);
	  var expFlag = document.createElement('input');
	  expFlag.type = 'checkbox';
	  expFlag.name = 'docExpiryFlag';
	  expFlag.id = 'docExpiryFlag'+psize;

	  var StatuSelect = row.insertCell(6);
	  var Statu = document.createElement('input');
	  Statu.type = 'checkbox';
	  Statu.name = 'status';
	  Statu.checked = 'checked';
	  Statu.id = 'status'+psize;

	cellCheck.appendChild(element);	
	if(val=="Edit"){	
	cellCheck.appendChild(ele);	
	}
	docCodeSelect.appendChild(docCode);
	mandatorySelect.appendChild(mandatory);
	originalSelect.appendChild(original);	
	expFlagSlect.appendChild( expFlag);
	StatuSelect.appendChild(Statu);
		
	
	
	  //index++;
	  pcheck++;
	  psize++;	  
	  document.getElementById("psize").value=psize;
	  document.getElementById("pcheck").value=pcheck;
		  
	  }


function removeRow(val) {
      var table = document.getElementById("gridtable");
         var rowCount = table.rows.length;
         //alert("Row Count"+rowCount)
		var count=0;
         for(var i=1; i<rowCount; i++) {
//        	 alert("i: "+i);
//        	 alert("Row Count"+rowCount);
             var row = table.rows[i];
             var chkbox = row.cells[0].childNodes[0];
//             alert("chkbox.checked: "+chkbox.checked);
             if(null != chkbox && true == chkbox.checked) 
             {
             	 table.deleteRow(i);
                 //rowCount--;
                 count++;
                 i--;
              }        

            }        

      if(count==0){
           alert(val);
         return false;
         }

      }

function removeSearchRow(val) {

      var table = document.getElementById("gridtable");
        var rowCount = table.rows.length;
		var count=rowCount;

         for(var i=1; i<rowCount; i++) {
             var row = table.rows[i];
             var chkbox = row.cells[0].childNodes[0];
             if(null != chkbox && true == chkbox.checked) 
             {
             	 table.deleteRow(i);
                 rowCount--;
                 i--;
              return true;
              }        

            }        

      if(count>1){
           alert(val);
         return false;
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
  
  <body onload="enableAnchor();document.getElementById('documentChecklistMasterSearch').docEntity.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <html:form styleId="documentChecklistMasterSearch" action="/documentCheckListSearch" >

 <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
  <fieldset>
  <legend><bean:message key="lbl.documentChecklist"/></legend>
  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="4" cellspacing="2">
    
      
<tr>
<logic:notPresent name="list">    	  	
 <td width="13%"><bean:message key="lbl.docEntity"/><span><font color="red">*</font></span></td>
 <td width="13%">
	 <html:select property="docEntity" styleId="docEntity" styleClass="text" onchange="getlist();getChageClass();" >
	          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
	          	  	<logic:present name="docEntity">
	          	  	<html:optionsCollection name="docEntity" label="entityDescription" value="entityValue" />
	          	  	</logic:present>	         	  	
	  	  </html:select>

  </td>
  
 <td width="13%"><bean:message key="lbl.docStage" /><font color="red">*</font></td>
 <td width="13%">        
	  <html:select property="docStage" styleId="docStage" styleClass="text" >
		<option value="">--<bean:message key="lbl.select" />--</option>
		<logic:present name="docStage">
		            <html:optionsCollection name="docStage" label="stageDescription" value="stageValue" />
		</logic:present>
	</html:select>
 </td>
</logic:notPresent>   
 	  	
<logic:present name="list">
<td width="13%"><bean:message key="lbl.docEntity"/><span><font color="red">*</font></span></td>
 <td width="13%">
	 <html:select property="docEntity" styleId="docEntity" styleClass="text" onchange="getlist();getChageClass();" disabled="true" value="${documentChecklistMasterVo.docEntity}">
	          	  	<html:option value="" >--Select--</html:option>
	          	  	<logic:present name="docEntity">
	          	  	<html:optionsCollection name="docEntity" label="entityDescription" value="entityValue" />
	          	  	</logic:present>
	  </html:select>

  </td>
  
 <td width="13%"><bean:message key="lbl.docStage" /><font color="red">*</font></td>
 <td width="13%">        
	  <html:select property="docStage" styleId="docStage" styleClass="text" disabled="true" value="${documentChecklistMasterVo.docStage}">
		<option value="">--<bean:message key="lbl.select" />--</option>
		<logic:present name="docStage">
		            <html:optionsCollection name="docStage" label="stageDescription" value="stageValue" />
		</logic:present>
	</html:select>
 </td>

</logic:present>
</tr>

  <tr>

    <td><bean:message key="lbl.productDesc"/><span><font color="red">*</font></span></td>
      <td>
      <html:text property="productId" styleId="productId"  styleClass="text" onblur="fnChangeCase('documentChecklistMasterSearch','productId')" readonly="true" value="${documentChecklistMasterVo.productId}" tabindex="-1"/>
      <html:hidden  property="lbxProductID" styleId="lbxProductID"  value="${documentChecklistMasterVo.lbxProductID}"/>
      <input type="hidden"  name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
  	  <html:button property="productButton" disabled="true" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(89,'documentChecklistMasterSearch','productId','','schemeId', '','','','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>
  	  <%-- <img onClick="openLOVCommon(89,'documentChecklistMasterSearch','productId','','', '','','','lbxProductID')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
  </td>



    <td><bean:message key="lbl.SchemeDesc"/></td>
    <td><html:text property="schemeId" styleId="schemeId" styleClass="text" readonly="true" tabindex="-1" value="${documentChecklistMasterVo.schemeId}"/>
    <html:hidden  property="lbxSchemeID" styleId="lbxSchemeID"  value="${documentChecklistMasterVo.lbxSchemeID}" />
     <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
  	 <html:button property="schemeButton" disabled="true" styleId="schemeButton" onclick="openLOVCommon(75,'documentChecklistMasterSearch','schemeId','productId','', 'lbxProductID','Select Product Code','','lbxSchemeID');closeLovCallonLov('schemeId');" value=" " styleClass="lovbutton"> </html:button>
  	  <%-- <img onClick="openLOVCommon(75,'documentChecklistMasterSearch','schemeId','productId','lbxSchemeID', 'lbxSchemeID','Select Product Code','','lbxSchemeID')" src="<%= request.getContextPath()%>/images/lov.gif"></td>--%>

    
  </tr>
  

<tr>

<logic:present name="list">
	<logic:iterate name="list" id="subList" length="1">
	<logic:equal name="subList" property="docEntity" value="ASSET">
     	<td><span><bean:message key="lbl.docConstitution"/></span></td>
   			<td><html:select property="docConstitution" styleId="docConstitution" styleClass="text"  disabled="true">
          	  	<html:option value="">--Select--</html:option>
          	  	<logic:present name="docConstitution">
          	  	<html:optionsCollection name="docConstitution" label="consDescription" value="consValue" />
          	  	</logic:present>
          	  	</html:select>
          </td>
          </logic:equal>
          
	<logic:equal name="subList" property="docEntity" value="APPL">
     	<td><span><bean:message key="lbl.docConstitution"/></span></td>
   			<td><html:select property="docConstitution" styleId="docConstitution" styleClass="text"  disabled="true">
          	  	<html:option value="">--Select--</html:option>
          	  	<logic:present name="docConstitution">
          	  	<html:optionsCollection name="docConstitution" label="consDescription" value="consValue" />
          	  	</logic:present>
          	  	</html:select>
          </td>
     </logic:equal>   
     
     <logic:equal name="subList" property="docEntity" value="COLLATERAL">
     	<td><span><bean:message key="lbl.docConstitution"/></span></td>
   			<td><html:select property="docConstitution" styleId="docConstitution" styleClass="text" disabled="true">
          	  	<html:option value="">--Select--</html:option>
          	  	<logic:present name="docConstitution">
          	  	<html:optionsCollection name="docConstitution" label="consDescription" value="consValue" />
          	  	</logic:present>
          	  	</html:select>
          </td>
     </logic:equal>
     
    <logic:equal name="subList" property="docEntity" value="PRAPPL">
     	<td><span><bean:message key="lbl.docConstitution"/></span></td>
   			<td><html:select property="docConstitution" styleId="docConstitution" disabled="true" styleClass="text" value="${documentChecklistMasterVo.docConstitution}" >
          	  	<html:option value="">--Select--</html:option>
          	  	<logic:present name="docConstitution">
          	  	<html:optionsCollection name="docConstitution" label="consDescription" value="consValue" />
          	  	</logic:present>
          	  	</html:select>
          </td>
          </logic:equal>
          
	<logic:equal name="subList" property="docEntity" value="COAPPL">
     	<td><span><bean:message key="lbl.docConstitution"/></span></td>
   			<td><html:select property="docConstitution" styleId="docConstitution" disabled="true" styleClass="text" value="${documentChecklistMasterVo.docConstitution}" >
          	  	<html:option value="">--Select--</html:option>
          	  	<logic:present name="docConstitution">
          	  	<html:optionsCollection name="docConstitution" label="consDescription" value="consValue" />
          	  	</logic:present>
          	  	</html:select>
          </td>
     </logic:equal>   
     
     
     <logic:equal name="subList" property="docEntity" value="GUARANTOR">
     	<td><span><bean:message key="lbl.docConstitution"/></span></td>
   			<td><html:select property="docConstitution" styleId="docConstitution" disabled="true" styleClass="text" value="${documentChecklistMasterVo.docConstitution}" >
          	  	<html:option value="">--Select--</html:option>
          	  	<logic:present name="docConstitution">
          	  	<html:optionsCollection name="docConstitution" label="consDescription" value="consValue" />
          	  	</logic:present>
          	  	</html:select>
          </td>
          </logic:equal>
   
 </logic:iterate>      
</logic:present>
<logic:notPresent name="list">
<td><span><bean:message key="lbl.docConstitution"/></span></td>
   			<td><html:select property="docConstitution" styleId="docConstitution" styleClass="text" >
          	  	<html:option value="">--Select--</html:option>
          	  	<logic:present name="docConstitution">
          	  	<html:optionsCollection name="docConstitution" label="consDescription" value="consValue" />
          	  	</logic:present>
          	  	</html:select>
          </td>
</logic:notPresent>          	  	
			  
<logic:present name="list"> 
<logic:iterate name="list" id="subList" length="1">
	<logic:equal name="subList" property="docEntity" value="COLLATERAL">			  
		     <TD><bean:message key="lbl.assetClass"/></TD>
		     	<TD>
		          
		             <html:text property="assetClass" tabindex="-1" readonly="true" styleClass="text" styleId="assetClass" value="${documentChecklistMasterVo.assetClass}" style="float:left; margin:0px 6px 0px 0px;"/>
		           <div id="colleteralText">
		           	<html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" value="${documentChecklistMasterVo.lbxAssetCollId}"/>
		             <html:button property="assetButton" styleId="assetButton" disabled="true" onclick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		             <%-- <img onClick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
	  	          </div>
		 		 
		          <div id="assetText" style="display: none;">
		          		<html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId"/>
  	  					<html:button property="assetTextButton" styleId="assetTextButton" disabled="true" onclick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	  					<%-- <img onClick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
  	        </div>
	     </TD>
	 </logic:equal>
	 <logic:equal name="subList" property="docEntity" value="ASSET">			  
		     <TD><bean:message key="lbl.assetClass"/></TD>
		     	<TD>
		          
		             <html:text property="assetClass" readonly="true" tabindex="-1" styleClass="text" styleId="assetClass" value="${documentChecklistMasterVo.assetClass}" style="float:left; margin:0px 6px 0px 0px;"/>
		           <div id="colleteralText" style="display: none;">
		           <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" value="${documentChecklistMasterVo.lbxAssetCollId}" />
		             <html:button property="assetButton" styleId="assetButton" disabled="true" onclick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		            <%-- <img onClick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
	  	          </div>
		 		 
		          <div id="assetText" >
		           <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
  	  					<html:button property="assetTextButton" styleId="assetTextButton" disabled="true" onclick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	  					<%-- <img onClick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
  	        </div>
	     </TD>
	 </logic:equal>
	  <logic:equal name="subList" property="docEntity" value="APPL">			  
		     <TD><bean:message key="lbl.assetClass"/></TD>
		     	<TD>
		          
		             <html:text property="assetClass" readonly="true" tabindex="-1" styleClass="text" styleId="assetClass" value="${documentChecklistMasterVo.assetClass}" style="float:left; margin:0px 6px 0px 0px;"/>
		           <div id="colleteralText" style="display: none;">
		           <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
		             <html:button property="assetButton" disabled="true" styleId="assetButton" onclick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	  	          </div>
		 		 
		          <div id="assetText" style="display: none;">
		          <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
  	  					<html:button property="assetTextButton" disabled="true" styleId="assetTextButton" onclick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	        </div>
	     </TD>
	 </logic:equal>
	  <logic:equal name="subList" property="docEntity" value="PRAPPL">			  
		     <TD><bean:message key="lbl.assetClass"/></TD>
		     	<TD>
		          
		             <html:text property="assetClass" readonly="true" tabindex="-1" styleClass="text" styleId="assetClass" value="${documentChecklistMasterVo.assetClass}" style="float:left; margin:0px 6px 0px 0px;"/>
		           <div id="colleteralText" style="display: none;">
		           <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
		            <html:button property="assetButton" styleId="assetButton" disabled="true" onclick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		             <%-- <img onClick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
	  	          </div>
		 		 
		          <div id="assetText" style="display: none;">
		          <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
  	  					<html:button property="assetTextButton" disabled="true" styleId="assetTextButton" onclick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	  					<%-- <img onClick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
  	        </div>
	     </TD>
	 </logic:equal>
	  <logic:equal name="subList" property="docEntity" value="COAPPL">			  
		     <TD><bean:message key="lbl.assetClass"/></TD>
		     	<TD>
		          
		             <html:text property="assetClass" readonly="true" styleClass="text" tabindex="-1" styleId="assetClass" value="${documentChecklistMasterVo.assetClass}" style="float:left; margin:0px 6px 0px 0px;"/>
		           <div id="colleteralText" style="display: none;">
		           <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
		             <html:button property="assetButton" styleId="assetButton" disabled="true" onclick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		             <%-- <img onClick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
	  	          </div>
		 		 
		          <div id="assetText" style="display: none;">
		          <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
  	  					<html:button property="assetTextButton" styleId="assetTextButton" disabled="true" onclick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	  					<%-- <img onClick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
  	        </div>
	     </TD>
	 </logic:equal>
	  <logic:equal name="subList" property="docEntity" value="GUARANTOR">			  
		     <TD><bean:message key="lbl.assetClass"/></TD>
		     	<TD>
		          
		             <html:text property="assetClass" readonly="true" styleClass="text" tabindex="-1" styleId="assetClass" value="${documentChecklistMasterVo.assetClass}" style="float:left; margin:0px 6px 0px 0px;"/>
		           <div id="colleteralText" style="display: none;">
		           <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
		             <html:button property="assetButton" styleId="assetButton" disabled="true" onclick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		             <%-- <img onClick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
	  	          </div>
		 		 
		          <div id="assetText" style="display: none;">
		          <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
  	  					<html:button property="assetTextButton" styleId="assetTextButton" disabled="true" onclick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	  					<%-- <img onClick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
  	        </div>
	     </TD>
	 </logic:equal>
</logic:iterate>
</logic:present>
<logic:notPresent name="list">
 <TD><bean:message key="lbl.assetClass"/></TD>
		     	<TD>
		          
		             <html:text property="assetClass" readonly="true" styleClass="text" tabindex="-1" styleId="assetClass" style="float:left; margin:0px 6px 0px 0px;"/>
		           <div id="colleteralText" >
		           <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
		             <html:button property="assetButton" styleId="assetButton" disabled="true" onclick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		            <%--  <img onClick="openLOVCommon(106,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
	  	          </div>
		 		 
		          <div id="assetText" style="display: none;">
		          <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId" />
  	  			  <html:button property="assetTextButton" styleId="assetTextButton" onclick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	  					<%-- <img onClick="openLOVCommon(107,'documentChecklistMasterSearch','assetClass','','', '','','','lbxAssetCollId')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" />--%>
  	        </div>
	     </TD>

</logic:notPresent>    
 
 </tr>
 <tr>
 	 <td colspan="2"><button  type="button"  name="button" id="button" title="Alt+S" accesskey="S" class="topformbutton2" onclick="fnSearch();" ><bean:message key="button.search" /></button>
 	<logic:present name="list">
 	 &nbsp; <button  type="button" name="clearButton" id="clearButton" title="Alt+C" accesskey="C" class="topformbutton2" onclick="fnClears();" ><bean:message key="button.clear" /></button>
  </logic:present>
  </tr>
 
 </table></td>
  </tr></table>
  <br/>

	<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<input type="hidden" name="psize" id="psize" />
			<input type="hidden" name="pcheck" id="pcheck" />
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" /></td>
				 <td ><b><bean:message key="lbl.documentCode" /></b></td>
    			 <td ><b><bean:message key="lbl.documentDesc" /></b></td>
       			 <td ><b><bean:message key="lbl.docMandatory" /></b></td>
        		 <td ><b><bean:message key="lbl.docOriginal" /></b></td>
        		 <td ><b><bean:message key="lbl.docExpiryflag" /></b></td>
        		 <td ><b><bean:message key="lbl.recStatus" /></b></td> 
      		</tr>
      		
<logic:present name="list">
	
	<logic:iterate name="list" id="sublist" indexId="counter">
	<input type="hidden" name="docCheckIdForDel" id="docCheckIdForDel<%=counter.intValue()+1%>" value="${sublist.docCheckId}"/>
	<tr class="white1">
		<td  ><input type="checkbox" name="chk" id="chk<%=counter.intValue()+1%>" checked="checked"/>
		
			<input type="hidden" name="docCheckId" id="docCheckId<%=counter.intValue()+1%>" value="${sublist.docCheckId}"/>
			</td>
		
		<td><input type="text" name="docId" id="docId<%=counter.intValue()+1%>"  value="${sublist.docId}" readonly="readonly" class="text"/></td>
		
		<td ><input type="text" name="docDes" id="docDes<%=counter.intValue()+1%>" value="${sublist.docDes}" readonly="readonly" class="text"  />
						<html:hidden  property="lbxDocId" styleId="lbxDocId"  value="${sublist.lbxDocId}" />

						<html:button property="docButton" styleId="docButton" onclick="openLOVCommon(76,'documentChecklistMasterSearch','docDes${counter+1 }','','', '','','','docId${counter+1 }');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
			<!-- <img onClick="openLOVCommon(76,'documentChecklistMasterSearch','docDes<%=counter.intValue()+1%>','','', '','','','docId<%=counter.intValue()+1%>')" src="<%= request.getContextPath()%>/images/lov.gif" id="lovImg"> --></td>

		
		<td ><logic:equal value="Y" name="sublist" property="docMandatory">
				<input type="checkbox" name="docMandatory" id="docMandatory<%=counter.intValue()+1%>" checked="checked" value="${sublist.docMandatory}"/>
			</logic:equal>     
			<logic:notEqual value="Y" name="sublist" property="docMandatory">
				<input type="checkbox" name="docMandatory" id="docMandatory<%=counter.intValue()+1%>" value="${sublist.docMandatory}"/>
			</logic:notEqual></td>
			
		<td ><logic:equal value="Y" name="sublist" property="docOriginal">
				<input type="checkbox" name="docOriginal" id="docOriginal<%=counter.intValue()+1%>" checked="checked" value="${sublist.docOriginal}" />
			</logic:equal>      
			<logic:notEqual value="Y" name="sublist" property="docOriginal">
				<input type="checkbox" name="docOriginal" id="docOriginal<%=counter.intValue()+1%>" value="${sublist.docOriginal}" />
			</logic:notEqual></td>
		<td><logic:equal value="Y" name="sublist" property="docExpiryFlag">
				<input type="checkbox" name="docExpiryFlag" id="docExpiryFlag<%=counter.intValue()+1%>" checked="checked" value="${sublist.docExpiryFlag}"/>
			</logic:equal>
			<logic:notEqual value="Y" name="sublist" property="docExpiryFlag">
				<input type="checkbox" name="docExpiryFlag" id="docExpiryFlag<%=counter.intValue()+1%>"   />
			</logic:notEqual></td>
			
		<td ><logic:equal value="A" name="sublist" property="status">
					<input type="checkbox" name="status" id="status<%=counter.intValue()+1%>" checked="checked" value="${sublist.status}"/>
				</logic:equal>     
			<logic:notEqual value="A" name="sublist" property="status">
				<input type="checkbox" name="status" id="status<%=counter.intValue()+1%>" value="${sublist.status}"/>
			</logic:notEqual></td>
	</tr>
	
	</logic:iterate>
	
	<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="6">
			<button type="button" title="Alt+A" accesskey="A" onclick="return addRow('Edit');" class="topformbutton2"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.deleterow" /></button></td>
	</tr>
	
</table>
</logic:present>

<logic:notPresent  name="list">

<%for(int i=1;i<=5;i++){ 
request.setAttribute("i",i);
%>
<tr class="white1" style="width: 25px;">


<td ><input type="checkbox" name="chk" id="chk<%=i %>" value="${requestScope.list[0].docId}"/></td>
		<td >
		<input type="text" name="docId" id="docId<%=i%>"  value="${requestScope.list[0].docId}" readonly="readonly" class="text"/>
	
		</td>
		<td ><input type="text" name="docDes" id="docDes<%=i %>" value="${requestScope.list[0].docDes}" readonly="readonly" class="text" tabindex="-1"/>
			<html:hidden  property="lbxDocId" styleId="lbxDocId"  value="" />
			<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>

			 <html:button property="docButton" styleId="docButton" onclick="openLOVCommon(76,'documentChecklistMasterSearch','docDes${i }','','', '','','','docId${i }');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
			<!-- <img onClick="openLOVCommon(76,'documentChecklistMasterSearch','docDes<%=i %>','','', '','','','docId<%=i%>')" src="<%= request.getContextPath()%>/images/lov.gif" id="lovImg"> --></td>

		
		<td>
			<logic:equal value="Y" name="docMandatory">		
				<input type="checkbox" name="docMandatory" id="docMandatory<%=i %>" checked="checked" />			
			</logic:equal>   
	
			<logic:notEqual value="Y" name="docMandatory">
				<input type="checkbox" name="docMandatory" id="docMandatory<%=i %>"  />				
			</logic:notEqual>
			</td>
		
		<td >
 			 <logic:equal value="Y" name="docOriginal">
				<input type="checkbox" name="docOriginal" id="docOriginal<%=i %>" checked="checked" />
			</logic:equal>      
			<logic:notEqual value="Y" name="docOriginal">
				<input type="checkbox" name="docOriginal" id="docOriginal<%=i %>"  />
			</logic:notEqual></td>

		<td>
			 <logic:equal value="Y" name="docExpiryFlag">
				<input type="checkbox" name="docExpiryFlag" id="docExpiryFlag<%=i %>" checked="checked" />
			</logic:equal>
			<logic:notEqual value="Y" name="docExpiryFlag">
				<input type="checkbox" name="docExpiryFlag" id="docExpiryFlag<%=i %>"  />
			</logic:notEqual></td>

		<td>
<!--				<logic:equal value="A" name="status">-->
<!--					<input type="checkbox" name="status" id="status<//%=i %>" checked="checked"/>-->
<!--				</logic:equal>     -->
<!--			<logic:notEqual value="A" name="status">-->
				<input type="checkbox" name="status" id="status<%=i %>" checked="checked"/>
<!--			</logic:notEqual></td>-->

</tr>
<%} %>
<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="6">
			<button type="button"  title="Alt+A" accesskey="A" onclick="return addRow('Save');" class="topformbutton2"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.deleterow" /></button></td>
	</tr>
	
</table>
</logic:notPresent>
</table>


</td></tr></table>
<logic:present name="editVal">
<tr>
     <td> <button type="button" id="save" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return docChildModify('<bean:message key="lbl.selectOne" />');"> <bean:message key="button.save" /></button></td>
</tr>
</logic:present>

<logic:notPresent name="editVal">
<tr>
     <td> <button type="button" id="save" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return schemeDetailsSave('<bean:message key="lbl.selectOne" />');" ><bean:message key="button.save" /></button></td>
</tr>
</logic:notPresent>


  
  </fieldset>

</html:form>
		<logic:present name="sms">
<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("documentChecklistMasterSearch").action="documentChecklistMasterSearchBehind.do";
	    document.getElementById("documentChecklistMasterSearch").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("documentChecklistMasterSearch").action="documentChecklistMasterSearchBehind.do";
	    document.getElementById("documentChecklistMasterSearch").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		document.getElementById("documentChecklistMasterSearch").action="documentChecklistMasterSearchBehind.do";
	    document.getElementById("documentChecklistMasterSearch").submit();		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");
		document.getElementById("documentChecklistMasterSearch").action="documentChecklistMasterSearchBehind.do";
	    document.getElementById("documentChecklistMasterSearch").submit();
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		document.getElementById("documentChecklistMasterSearch").action="documentChecklistMasterSearchBehind.do";
	    document.getElementById("documentChecklistMasterSearch").submit();
	}
	

	
	
</script>
</logic:present>
   
  </body>
</html:html>
