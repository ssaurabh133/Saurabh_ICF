<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="assetList" >


   <div id="gridList">
    <table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="1">
        
        
    <tr class="white2">
			<td><input type="checkbox" name="chkd" id="allchkd"  onclick="allChecked();" /></td>
			<!--<td><strong><bean:message key="lbl.LoanNo"/></strong> </td>
			--><td><strong><bean:message key="lbl.AssetId"/></strong></td>
			<td><b><bean:message key="lbl.AssetDesc"/></b></td>
			<td><strong><bean:message key="lbl.AssetCost"/></strong> </td>
			<td><strong><bean:message key="lbl.AssetManufature"/></strong></td>
			<td><b><bean:message key="lbl.AssetSupplier"/></b></td>
     </tr>
<logic:notEmpty name="assetList">
	<logic:iterate name="assetList" id="listobj" >
         
			<tr class="white1">
		<td><input type="checkbox" name="chk" id="chk" value="${listobj.assetID}"/></td>
			<input type="hidden" name="loanidval" id="loanidval" value="${listobj.loanID}"/><!--
			
			<td>${listobj.loanAccNo}</td>
			<html:hidden property="loanAccNo1" styleId="loanAccNo1"  value="${listobj.loanAccNo}"/>
			
			--><td>${listobj.assetID}</td>
			<html:hidden property="assetID1" styleId="assetID1"  value="${listobj.assetID}"/>
			
			<td>${listobj.assetDescription}</td>
			<html:hidden property="assetDescription1" styleId="assetDescription1"  value="${listobj.assetDescription}"/>
			
			<td>${listobj.assetCost}</td>
			<html:hidden property="assetCost1" styleId="assetCost1"  value="${listobj.assetCost}"/>
			
			<td>${listobj.assetManufaturer}</td>
			<html:hidden property="assetManufaturer1" styleId="assetManufaturer1"  value="${listobj.assetManufaturer}"/>
			
			<td>${listobj.assetSupplier}</td>
			<html:hidden property="assetSupplier1" styleId="assetSupplier1" value="${listobj.assetSupplier}"/>
        
          
         </tr>
             
	</logic:iterate>  
	</logic:notEmpty>   
 </table>   
 </div>
    </logic:present>  
    
   