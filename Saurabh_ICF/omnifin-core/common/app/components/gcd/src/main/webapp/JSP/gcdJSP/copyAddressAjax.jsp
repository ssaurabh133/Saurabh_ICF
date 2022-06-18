<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="copyAddressList" >
	<logic:notEmpty name="copyAddressList" >
      <logic:iterate name="copyAddressList" id="list">
			$:${list.addr_type}
			$:${list.addr1}
			$:${list.addr2}
			$:${list.addr3}
			$:${list.txtCountryCode}
			$:${list.country}
			$:${list.txtStateCode}
			$:${list.state}
			$:${list.tahsil}
			$:${list.txtDistCode}
			$:${list.dist}
			$:${list.pincode}
			$:${list.primaryPhoneNo}			
			$:${list.alternatePhoneNo}
			$:${list.tollfreeNo}
			$:${list.faxNo}
			$:${list.landMark}
			$:${list.noYears}
			$:${list.noMonths}
			$:${list.distanceBranch}
			$:${list.communicationAddress}			
			$:${list.addDetails}
	</logic:iterate>
    </logic:notEmpty>    
</logic:present>

<logic:present name="applicantAddress" >
	<logic:notEmpty name="applicantAddress" >
      <logic:iterate name="applicantAddress" id="list">
      $:${list.addresstype}
      $:${list.address1}
      $:${list.address2}
      $:${list.address3}
      $:${list.txtCountryCode}
      $:${list.country}
      $:${list.txtStateCode}
      $:${list.state}
      $:${list.txtDistCode}
      $:${list.dist}
      $:${list.pincode}
      $:${list.txnTahsilHID}
      $:${list.tahsil}
	  $:${list.tahsilDesc}	
	  $:${list.phoneOff}	
	  $:${list.landmark}	
	  $:${list.email}			
</logic:iterate>
</logic:notEmpty>
</logic:present>