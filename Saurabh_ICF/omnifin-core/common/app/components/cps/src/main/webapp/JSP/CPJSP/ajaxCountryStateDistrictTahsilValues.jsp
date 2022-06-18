<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="countryStateDistrictTahsilValue" >

   
	      <logic:iterate name="countryStateDistrictTahsilValue" id="subCountryStateDistrictTahsilValue">
	
				${subCountryStateDistrictTahsilValue.pincode}
				$:${subCountryStateDistrictTahsilValue.txnTahsilHID}
				$:${subCountryStateDistrictTahsilValue.tahsil}
				$:${subCountryStateDistrictTahsilValue.txtDistCode}
				$:${subCountryStateDistrictTahsilValue.dist}
				$:${subCountryStateDistrictTahsilValue.txtStateCode}
				$:${subCountryStateDistrictTahsilValue.state}
				$:${subCountryStateDistrictTahsilValue.txtCountryCode}
				$:${subCountryStateDistrictTahsilValue.country}
			
		</logic:iterate>

	
 </logic:present>  
 <logic:present name="coAppcountryStateDistrictTahsilValue" >

   
	      <logic:iterate name="coAppcountryStateDistrictTahsilValue" id="subCoAppcountryStateDistrictTahsilValue">
	
				${subCoAppcountryStateDistrictTahsilValue.coApppincode}
				$:${subCoAppcountryStateDistrictTahsilValue.coApptxnTahsilHID}
				$:${subCoAppcountryStateDistrictTahsilValue.coApptahsil}
				$:${subCoAppcountryStateDistrictTahsilValue.coApptxtDistCode}
				$:${subCoAppcountryStateDistrictTahsilValue.coAppdist}
				$:${subCoAppcountryStateDistrictTahsilValue.coApptxtStateCode}
				$:${subCoAppcountryStateDistrictTahsilValue.coAppstate}
				$:${subCoAppcountryStateDistrictTahsilValue.coApptxtCountryCode}
				$:${subCoAppcountryStateDistrictTahsilValue.coAppcountry}
			
		</logic:iterate>

	
 </logic:present>
 
 <logic:present name="gaurcountryStateDistrictTahsilValue" >

   
	      <logic:iterate name="gaurcountryStateDistrictTahsilValue" id="subGaurcountryStateDistrictTahsilValue">
	
				${subGaurcountryStateDistrictTahsilValue.gaurpincode}
				$:${subGaurcountryStateDistrictTahsilValue.gaurtxnTahsilHID}
				$:${subGaurcountryStateDistrictTahsilValue.gaurtahsil}
				$:${subGaurcountryStateDistrictTahsilValue.gaurtxtDistCode}
				$:${subGaurcountryStateDistrictTahsilValue.gaurdist}
				$:${subGaurcountryStateDistrictTahsilValue.gaurtxtStateCode}
				$:${subGaurcountryStateDistrictTahsilValue.gaurstate}
				$:${subGaurcountryStateDistrictTahsilValue.gaurtxtCountryCode}
				$:${subGaurcountryStateDistrictTahsilValue.gaurcountry}
			
		</logic:iterate>

	
 </logic:present>
    
         