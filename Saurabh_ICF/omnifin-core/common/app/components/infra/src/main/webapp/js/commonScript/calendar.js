var date = new Date();
$(function() {
	$("#custDOB").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
        buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
     });
	});
	
$(function() {
	$("#applicationdate").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
        buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
        
	});
});
//method added by manish
$(function() {
	$("#dateOfBirth").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
        buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
        
	});
});
$(function() {

	$("#assignDate").datepicker({
	format: "%Y-%m-%d %H:%i:%s %E %#",
	formatUtcOffset: "%: (%@)",
	placement: "inline",
	
	 changeMonth: true,
	 changeYear: true,
	 yearRange: '1900:+10',
	 showOn: 'both',
	 buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value
		});
		});

$(function() {

	$("#month").datepicker({
	format: "%Y-%m-%d %H:%i:%s %E %#",
	formatUtcOffset: "%: (%@)",
	placement: "inline",
	
	 changeMonth: true,
	 changeYear: true,
	 yearRange: '1900:+10',
	 showOn: 'both',
	 buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value
		});
		});
//method added by neeraj tripathi
//sachin
$(function() {
	$("#policyStartDate").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
        buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
        
	});
});
$(function() {
	$("#insMatureDate").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
        buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
        
	});
});
//end by sachin
function checkFormate(value,id)
{
	var length=parseInt(value.length);
	var flg=1;
	if(length!=7)
		flg=0;
	if(length==7)
	for(var i=0;i<7;i++)
	{
		if(i==2)
		{
			var month=value.substring(0,2);
			var mon=parseFloat(month);
			if(mon<=0 || mon>12)
			{
				flg=0;
				break;
			}
			var code=value.charCodeAt(i);
			if(code==45)
			  continue;
			else
			{
				flg=0;
				break;
			}
		}
		else
		{
			var code=value.charCodeAt(i);
			if(code>=48 && code<=57)
			{
			 if(i==6)	
			 {
				 var year=value.substring(3);
					var yr=parseFloat(year);
					if(yr<=0)
					{
						flg=0;
						break;
					}
			 }
			  continue;
			}
			else
			{
				flg=0;
				break;
			}
		}
	}
	if(flg==0)
	{
		alert("Invalid Date format,Please enter as 'MM-YYYY'('01-2012').");
		document.getElementById(id).value="";
		return false;
	}
}




$(function() {
	$("#initiationDate").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
        buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
     });
	});


$(function() {
		$("#bgInDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	        buttonImage: document.getElementById("CalImage").value,
	        buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
	    	
	});
     	});
     	
$(function() {
		$("#bgValidity").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	        buttonImage: document.getElementById("CalImage").value,
	        buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
	});
     	});	




	
//commented by neeraj
/*$(function() {
		$("#vehicleYearOfManufact").datepicker({
		 changeMonth: true,
		 changeYear: true,
		 yearRange: '1900:+10',
		 showOn: 'both',
		 buttonImage: document.getElementById("CalImage").value,
		 buttonImageOnly: true,
		 dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
		 
			
		});
	});*/
	
$(function() {
		$("#vehicleRegDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
		 yearRange: '1900:+10',
		 showOn: 'both',
		 buttonImage: document.getElementById("CalImage").value,
		 buttonImageOnly: true,
		 dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
		 
			
		});
	});	
	
	 $(function() {
$("#vehicleInsureDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
		 yearRange: '1900:+10',
		 showOn: 'both',
		 buttonImage: document.getElementById("CalImage").value,
		 buttonImageOnly: true,
		 dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
			
		});
	});	
	 
// kumar aman change start
	 $(function() {
		 $("#renewaldate").datepicker({
		 		 changeMonth: true,
		 		 changeYear: true,
		 		 yearRange: '1900:+10',
		 		 showOn: 'both',
		 		 buttonImage: document.getElementById("CalImage").value,
		 		 buttonImageOnly: true,
		 		 dateFormat: document.getElementById("formatD").value,
		 			defaultDate: document.getElementById("businessdate").value
		 			
		 		});
		 	});	
	 
	 
	 $(function() {
		 $("#premdate").datepicker({
		 		 changeMonth: true,
		 		 changeYear: true,
		 		 yearRange: '1900:+10',
		 		 showOn: 'both',
		 		 buttonImage: document.getElementById("CalImage").value,
		 		 buttonImageOnly: true,
		 		 dateFormat: document.getElementById("formatD").value,
		 			defaultDate: document.getElementById("businessdate").value
		 			
		 		});
		 	});	
	 
	 $(function() {
		 $("#endorsementdate").datepicker({
		 		 changeMonth: true,
		 		 changeYear: true,
		 		 yearRange: '1900:+10',
		 		 showOn: 'both',
		 		 buttonImage: document.getElementById("CalImage").value,
		 		 buttonImageOnly: true,
		 		 dateFormat: document.getElementById("formatD").value,
		 			defaultDate: document.getElementById("businessdate").value
		 			
		 		});
		 	});	
	 
	 
//kumar aman change end	 
	 
	 
	 $(function() {
		 $("#vehicleInvoiceDate").datepicker({
		 		 changeMonth: true,
		 		 changeYear: true,
		 		 yearRange: '1900:+10',
		 		 showOn: 'both',
		 		 buttonImage: document.getElementById("CalImage").value,
		 		 buttonImageOnly: true,
		 		 dateFormat: document.getElementById("formatD").value,
		 			defaultDate: document.getElementById("businessdate").value
		 			
		 		});
		 	});	
	 
	 $(function() {
		 $("#rcReceivedDate").datepicker({
		 		 changeMonth: true,
		 		 changeYear: true,
		 		 yearRange: '1900:+10',
		 		 showOn: 'both',
		 		 buttonImage: document.getElementById("CalImage").value,
		 		 buttonImageOnly: true,
		 		 dateFormat: document.getElementById("formatD").value,
		 			defaultDate: document.getElementById("businessdate").value
		 			
		 		});
		 	});	
$(function() {
		$("#appraisalDate").datepicker({
		changeMonth: true,
		changeYear: true,
		yearRange: '1900:+10',
		showOn: 'both',
		buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
		dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
		
		});
});

$(function() {
	$("#reportDate").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
		dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
     });
	});

$(function() {
		$("#machineYearOfManufact").datepicker({
			 changeMonth: true,
			 changeYear: true,
			 yearRange: '1900:+10',
			 showOn: 'both',
				buttonImage: document.getElementById("CalImage").value,
				buttonImageOnly: true,
				dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
			
		});
	});


$(function() {
	$("#sblcValidity").datepicker({
	 changeMonth: true,
	 changeYear: true,
     yearRange: '1900:+10',
     showOn: 'both',
		buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
		dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
    	
});
 	});
 $(function() {
	$("#sblcIssuingDate").datepicker({
	 changeMonth: true,
	 changeYear: true,
     yearRange: '1900:+10',
     showOn: 'both',
		buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
		dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
    	
});
 	});	
 
 $(function() {
		$("#invoiceDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
			buttonImage: document.getElementById("CalImage").value,
			buttonImageOnly: true,
			dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
	    	
	});
  	});
 

	$(function() {
	$("#fdBookDate").datepicker({
	 changeMonth: true,
	 changeYear: true,
  yearRange: '1900:+10',
  showOn: 'both',
	buttonImage: document.getElementById("CalImage").value,
	buttonImageOnly: true,
	dateFormat: document.getElementById("formatD").value,
	defaultDate: document.getElementById("businessdate").value
 	
});
	});
$(function() {
	$("#fdMatureDate").datepicker({
	 changeMonth: true,
	 changeYear: true,
  yearRange: '1900:+10',
  showOn: 'both',
	buttonImage: document.getElementById("CalImage").value,
	buttonImageOnly: true,
	dateFormat: document.getElementById("formatD").value,
	defaultDate: document.getElementById("businessdate").value
 	
});
	});	


$(function() {
	$("#date_of_sanction").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1950:2070',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
    	buttonImageOnly: true,
    	dateFormat: document.getElementById("formatD").value,
    	defaultDate: document.getElementById("businessdate").value

     });
	});
$(function() {
	$("#sancDate").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
    	buttonImageOnly: true,
    	dateFormat: document.getElementById("formatD").value,
    	defaultDate: document.getElementById("businessdate").value


	});
		});



	$(function() {

	$("#leadDate").datepicker({
	format: "%Y-%m-%d %H:%i:%s %E %#",
    formatUtcOffset: "%: (%@)",
    placement: "inline",

	 changeMonth: true,
	 changeYear: true,
     yearRange: '1900:+10',
     showOn: 'both',
     buttonImage: document.getElementById("CalImage").value,
 	buttonImageOnly: true,
 	dateFormat: document.getElementById("formatD").value,
 	defaultDate: document.getElementById("businessdate").value
	
		});
 	});   
  	
	
	$(function() {
		$("#date_of_reciept").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1950:2010',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
	  	buttonImageOnly: true,
	  	dateFormat: document.getElementById("formatD").value,
	  	defaultDate: document.getElementById("businessdate").value
	
	
	});
     	});
	

  	$(function() {
		$("#date").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1950:2070',
            showOn: 'both',
            buttonImage: document.getElementById("CalImage").value,
    	  	buttonImageOnly: true,
    	  	dateFormat: document.getElementById("formatD").value,
    	  	defaultDate: document.getElementById("businessdate").value

	     });
		});
	$(function() {
		$("#followupDate").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1950:2070',
            showOn: 'both',
            buttonImage: document.getElementById("CalImage").value,
    	  	buttonImageOnly: true,
    	  	dateFormat: document.getElementById("formatD").value,
    	  	defaultDate: document.getElementById("businessdate").value


		});
			});

	

	
	
	$(function() {
		$("#expectedDisbursalDate").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1900:+10',
            showOn: 'both',
            buttonImage: document.getElementById("CalImage").value,
    	  	buttonImageOnly: true,
    	  	dateFormat: document.getElementById("formatD").value,
    	  	defaultDate: document.getElementById("businessdate").value
	     });
		});
		  	
	
	
	$(function() {
		$("#expectedLoginDate").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1900:+10',
            showOn: 'both',
            buttonImage: document.getElementById("CalImage").value,
    	  	buttonImageOnly: true,
    	  	dateFormat: document.getElementById("formatD").value,
    	  	defaultDate: document.getElementById("businessdate").value
	     });
		});
		  	
	$(function() {
		$("#to_datepicker").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1950:2070',
            showOn: 'both',
            buttonImage: document.getElementById("CalImage").value,
    	  	buttonImageOnly: true,
    	  	dateFormat: document.getElementById("formatD").value,
    	  	defaultDate: document.getElementById("businessdate").value

		});
			});
	
	$(function() {
		$("#pdDate").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1950:2070',
            showOn: 'both',
            buttonImage: document.getElementById("CalImage").value,
    	  	buttonImageOnly: true,
    	  	dateFormat: document.getElementById("formatD").value,
    	  	defaultDate: document.getElementById("businessdate").value

	     });
		});
	

	
		
		$(function() {
		$("#repayEffectiveDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
	 	  	buttonImageOnly: true,
	 	  	dateFormat: document.getElementById("formatD").value,
	 	  	defaultDate: document.getElementById("businessdate").value
	
	
	});
     	});
		
		

	  	$(function() {
			$("#from_datepicker").datepicker({
					changeMonth: true,
				changeYear: true,
	            yearRange: '1950:2070',
	            showOn: 'both',
	            buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value

		     });
			});
			
		
		
		$(function() {
			$("#fromdate").datepicker({
				changeMonth: true,
				changeYear: true,
        		yearRange: '1900:+10',
        		showOn: 'both',
        		buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
				});
		});


		
				
		$(function() {
			$("#todate").datepicker({
					changeMonth: true,
				changeYear: true,
	            yearRange: '1900:+10',
	            showOn: 'both',
	            buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
	
				});
		});	
		
		
		 $(function() {
				$("#nxtActionDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
		            buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value
			     });
				});
		 $(function() {
				$("#actionDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
		            buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value
			     });
				});
				    $(function() {
				$("#instrumentDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
		            buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value
			     });
				});
				    
				    $(function() {
						$("#receivedDate").datepicker({
								changeMonth: true,
							changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					     });
						});

		    $(function() {
				$("#assignto").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
		            buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value
			     });
				});
		    $(function() {
				$("#assignfrom").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
		            buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value
			     });
				});
		    
		    $(function() {
				$("#effectiveDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
		            buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value

				});
					});
		    
		    
			 $(function() {
					$("#queueDateTo").datepicker({
							changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
				     });
					});
			    $(function() {
					$("#queueDateFrom").datepicker({
							changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
				     });
					});
					
			    
			    $(function() {
					$("#creditDate").datepicker({
					 changeMonth: true,
					 changeYear: true,
				     yearRange: '1900:+10',
				     showOn: 'both',
				     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
				
				
				});
			     	});
			    
			    $(function() {
					$("#incorporationDate").datepicker({
					 changeMonth: true,
					 changeYear: true,
				     yearRange: '1900:+10',
				     showOn: 'both',
				     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
				
				});
			     	});
			    
			    $(function() {
					$("#dob").datepicker({
					 changeMonth: true,
					 changeYear: true,
				     yearRange: '1900:+10',
				     showOn: 'both',
				     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
				
				});
			     	});
			    

				$(function() {
				$("#doj").datepicker({
				 changeMonth: true,
				 changeYear: true,
			     yearRange: '1900:+10',
			     showOn: 'both',
			     buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value
			
			
			});
		     	});
				
				$(function() {
					$("#depreciationDate").datepicker({
							changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
				     });
					});
				
				$(function() {
					$("#additionDate").datepicker({
						changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value

				     });
					});
					
				$(function() {
					$("#billDate").datepicker({
						changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value

				     });
					});
				
				$(function() {
					$("#voucherDate").datepicker({
						changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value

				     });
					});


			  	$(function() {
					$("#startDate").datepicker({
						changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
						
				     });
					
					});
			  	
				
				$(function() {
					$("#endDate").datepicker({
						changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value

					});
						});

				$(function() {
					$("#saleDate").datepicker({
						changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value

				     });
					});
				
				
				$(function() {
					$("#asondate").datepicker({
						changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value

					});
						});
				

				
				$(function() {
				$("#datepicker").datepicker({
					changeMonth: true,
					changeYear: true,
		            yearRange: '1950:2010',
		            showOn: 'both',
		            buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value
			
				});

			});
				
				$(function() {
					$("#effectiveFromDateSearch").datepicker({
							changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
			            buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
				     });
					
					$(function() {
						$("#effectiveFromDate").datepicker({
								changeMonth: true,
							changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value

					     });
						});
					});
				
				  $(function() {
						$("#approvalDate").datepicker({
						format: "%Y-%m-%d %H:%i:%s %E %#",
						formatUtcOffset: "%: (%@)",
						placement: "inline",
						
						 changeMonth: true,
						 changeYear: true,
						 yearRange: '1900:+10',
						 showOn: 'both',
						 buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
						
							});
							});
				  
				  
				  $(function() {

						$("#visitDate").datepicker({
						format: "%Y-%m-%d %H:%i:%s %E %#",
						formatUtcOffset: "%: (%@)",
						placement: "inline",
						
						 changeMonth: true,
						 changeYear: true,
						 yearRange: '1900:+10',
						 showOn: 'both',
						 buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
						
							});

							});
				  
				  $(function() {
						$("#agrementDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					
					
					});
				     	});
				  
				  
		
				  	
				  	$(function() {
						$("#generateDate").datepicker({
								changeMonth: true,
							changeYear: true,
				            yearRange: '1950:2070',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value

					     });
						});
				  	
				    $(function() {
				        
						$("#generateBankingDate").datepicker({
						format: "%Y-%m-%d %H:%i:%s %E %#",
						formatUtcOffset: "%: (%@)",
						placement: "inline",
						
						 changeMonth: true,
						 changeYear: true,
						 yearRange: '1900:+10',
						 showOn: 'both',
						 buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
						
							});
							});
				    
					$(function() {
						$("#agreementDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					
					
					});
				     	});
					
					$(function() {
						$("#paymentDate").datepicker({
							changeMonth: true,
							changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
						});
							});  
					
					$(function() {

						$("#ok").datepicker({
						format: "%Y-%m-%d %H:%i:%s %E %#",
						formatUtcOffset: "%: (%@)",
						placement: "inline",
						
						 changeMonth: true,
						 changeYear: true,
						 yearRange: '1900:+10',
						 showOn: 'both',
						 buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
						
							});
							});
					
					$(function() {
					$("#presentaionDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1950:2070',
		            showOn: 'both',
		            buttonImage: document.getElementById("CalImage").value,
			 	  	buttonImageOnly: true,
			 	  	dateFormat: document.getElementById("formatD").value,
			 	  	defaultDate: document.getElementById("businessdate").value

			     });
				});
					
					$(function() {
						$("#creationDate").datepicker({
							changeMonth: true,
							changeYear: true,
				            yearRange: '1950:2070',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value

					     });
						});
					
					$(function() {

						$("#cutOffDate").datepicker({
						format: "%Y-%m-%d %H:%i:%s %E %#",
						formatUtcOffset: "%: (%@)",
						placement: "inline",
						
						 changeMonth: true,
						 changeYear: true,
						 yearRange: '1900:+10',
						 showOn: 'both',
						 buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
							});
							});
				
				
				 $(function() {

						$("#poolCreationDate").datepicker({
						format: "%Y-%m-%d %H:%i:%s %E %#",
						formatUtcOffset: "%: (%@)",
						placement: "inline",
						
						 changeMonth: true,
						 changeYear: true,
						 yearRange: '1900:+10',
						 showOn: 'both',
						 buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
							});
							});
				 
					
				  	$(function() {

					$("#receiptDate").datepicker({
					changeMonth: true,
							changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
						});
							});  
				  
				  
					
					$(function() {
						$("#maturityDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value
					
					});
				     	});

					
					
					$(function() {
						$("#nextDueDate").datepicker({
						changeMonth: true,
						changeYear: true,
						yearRange: '1900:+10',
						showOn: 'both',
						buttonImage: document.getElementById("CalImage").value,
						buttonImageOnly: true,
						dateFormat: document.getElementById("formatD").value,
						defaultDate: document.getElementById("businessdate").value
					
							});

				     	});
					
					$(function() {
						$("#dueDate").datepicker({
						changeMonth: true,
						changeYear: true,
						yearRange: '1900:+10',
						showOn: 'both',
						buttonImage: document.getElementById("CalImage").value,
						buttonImageOnly: true,
						dateFormat: document.getElementById("formatD").value,
						defaultDate: document.getElementById("businessdate").value
					
							});
				     	});
					

					$(function() {
						$("#reversal_date").datepicker({
						changeMonth: true,
						changeYear: true,
						yearRange: '1900:+10',
						showOn: 'both',
						buttonImage: document.getElementById("CalImage").value,
						buttonImageOnly: true,
						dateFormat: document.getElementById("formatD").value,
						defaultDate: document.getElementById("businessdate").value
					
							});
				     	});
			        
			        $(function() {
						$("#holidayDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					
					
					});
				     	});
				 
				 $(function() {
						$("#holidaySearchDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					
					
					});
				     	});
				 //commented by neeraj tripathi
				/* $(function() {
						$("#mfgYear").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					
					
					});
				     	});
				 
				 $(function() {
						$("#loanApprovalDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					
					
					});
				     	});		 
*/
			//ravindra	 
				//Arun 
							/*	*/ $(function() {
										$("#repayEffBusDate").datepicker({
												changeMonth: true,
											changeYear: true,
									        yearRange: '1900:+10',
									        showOn: 'both',
									        buttonImage: document.getElementById("CalImage").value,
											buttonImageOnly: true,
											dateFormat: document.getElementById("formatD").value,
											defaultDate: document.getElementById("businessdate").value
									     });
										});
							$(function() {
								$("#repayEffDate").datepicker({
										changeMonth: true,
									changeYear: true,
							        yearRange: '1900:+10',
							        showOn: 'both',
							        buttonImage: document.getElementById("CalImage").value,
									buttonImageOnly: true,
									dateFormat: document.getElementById("formatD").value,
									defaultDate: document.getElementById("businessdate").value
							     });
								});
							$(function() {
								$("#penalIntCalcDate").datepicker({
										changeMonth: true,
									changeYear: true,
							        yearRange: '1900:+10',
							        showOn: 'both',
							        buttonImage: document.getElementById("CalImage").value,
									buttonImageOnly: true,
									dateFormat: document.getElementById("formatD").value,
									defaultDate: document.getElementById("businessdate").value
							     });
				});
				 
//				 $(function() {
//						$("#expectedPmntDt").datepicker({
//								changeMonth: true,
//							changeYear: true,
//					        yearRange: '1900:+10',
//					        showOn: 'both',
//					        buttonImage: document.getElementById("CalImage").value,
//							buttonImageOnly: true,
//							dateFormat: document.getElementById("formatD").value,
//							defaultDate: document.getElementById("businessdate").value,
//							minDate : document.getElementById("businessdate").value
//					     });
//						});
				 
//				 $(function() {
//						$("#expectedPmntDtIns").datepicker({
//								changeMonth: true,
//							changeYear: true,
//					        yearRange: '1900:+10',
//					        showOn: 'both',
//					        buttonImage: document.getElementById("CalImage").value,
//							buttonImageOnly: true,
//							dateFormat: document.getElementById("formatD").value,
//							defaultDate: document.getElementById("businessdate").value,
//							minDate : document.getElementById("businessdate").value
//					     });
//						});
				 
				 
					
				 
// code starts Aditi 
				 
				   $(function() {
						$("#fromDueDate").datepicker({
								changeMonth: true,
							changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					     });
						});
				    $(function() {
						$("#toDueDate").datepicker({
								changeMonth: true,
							changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					     });
						});
				 
	//added by aditi
				    
				    
				    $(function() {
						$("#presentationDateForBatch").datepicker({
								changeMonth: true,
							changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value
					     });
						});
				    
				   
//	  $(function() {
//				    	$("#instrumentPreEmiDate").datepicker({
//				    			changeMonth: true,
//				    		changeYear: true,
//				            yearRange: '1900:+10',
//				            showOn: 'both',
//				            buttonImage: document.getElementById("CalImage").value,
//				            buttonImageOnly: true,
//				            dateFormat: document.getElementById("formatD").value,
//				    		defaultDate: document.getElementById("businessdate").value
//				            
//				    	});
//				    });		

		//ravi 
				    $(function() {
						$("#valueDate").datepicker({
								changeMonth: true,
							changeYear: true,
					        yearRange: '1900:+10',
					        showOn: 'both',
					        buttonImage: document.getElementById("CalImage").value,
							buttonImageOnly: true,
							dateFormat: document.getElementById("formatD").value,
							defaultDate: document.getElementById("businessdate").value,
							maxDate : document.getElementById("businessdate").value
					     });
						});
	 // added by Ankit
				
				    $(function() {
						$("#capitalizedDate").datepicker({
								changeMonth: true,
							changeYear: true,
				            yearRange: '1950:2070',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
				    	  	buttonImageOnly: true,
				    	  	dateFormat: document.getElementById("formatD").value,
				    	  	defaultDate: document.getElementById("businessdate").value

						});
							}); 
				    
				    
				    function makeChoice(abc){
				    	
				    	var str="";
				    	if(abc!=null){
				    		str=abc;
				    	}else{	
				    	 	str= document.getElementById("psize").value;
				    		str = str - 1;
				    	}
				    	
				    	// alert(str);
				    	//var recievedDate = 'dateOfDisbursal'+str;
				    		$(function() {
				    			var contextPath =document.getElementById('contextPath').value ;
				    			$("#dateOfDisbursal"+str).datepicker({
				    			format: "%Y-%m-%d %H:%i:%s %E %#",
				    			formatUtcOffset: "%: (%@)",
				    			placement: "inline",
				     			changeMonth: true,
				     			changeYear: true,
				     			yearRange: '1900:+10',
				     			showOn: 'both',
				     			buttonImage: contextPath+'/images/theme1/calendar.gif',
				    			buttonImageOnly: true,
				    	        dateFormat: document.getElementById("formatD").value,
				    			defaultDate: document.getElementById("businessdate").value
				    			});
				    		});
				    	}
				    
				    $(function() {
				    	$("#dateOfDisbursal1").datepicker({
				    			changeMonth: true,
				    		changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
				            buttonImageOnly: true,
				            dateFormat: document.getElementById("formatD").value,
				    		defaultDate: document.getElementById("businessdate").value
				            
				    	});
				    });

				    //By Ritesh
				    $(function() {
				    	$("#legalDate").datepicker({
				    			changeMonth: true,
				    		changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
				            buttonImageOnly: true,
				            dateFormat: document.getElementById("formatD").value,
				            defaultDate: document.getElementById("businessdate").value
				    		
				         });
				    	});

				    $(function() {
				    	$("#repoDate").datepicker({
				    			changeMonth: true,
				    		changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
				            buttonImageOnly: true,
				            dateFormat: document.getElementById("formatD").value,
				            defaultDate: document.getElementById("businessdate").value
				         });
				    	});
				    
				    $(function() {
				    	$("#disbursalDate").datepicker({
				    			changeMonth: true,
				    		changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
				            buttonImageOnly: true,
				            dateFormat: document.getElementById("formatD").value,
				            defaultDate: document.getElementById("businessdate").value
				         });
				    	});
				    $(function() {
						$("#expiryDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
					 	  	buttonImageOnly: true,
					 	  	dateFormat: document.getElementById("formatD").value,
					 	  	defaultDate: document.getElementById("businessdate").value,
					 	  	minDate : document.getElementById("businessdate").value
					
					});
				     	});
				    
				    $(function() {
				    	$("#bankingSince").datepicker({
				    			changeMonth: true,
				    		changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
				            buttonImageOnly: true,
				            dateFormat: document.getElementById("formatD").value,
				    		defaultDate: document.getElementById("businessdate").value
				            
				    	});
				    });
				    
				  //Nishant Space Starts
					$(function() {
						$("#lastStatementDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					
					$(function() {
						$("#lastStatementDate1").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					
					$(function() {
						$("#lastMonth1").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					
					});
				     	});
					$(function() {
						$("#lastMonth2").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth3").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth4").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth5").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth6").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth7").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth8").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth9").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth10").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth11").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					$(function() {
						$("#lastMonth12").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					//Nishant Space End
					
					$(function() {
						$("#vehicleRegDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					
					$(function() {
						$("#vehicleInsureDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					
					$(function() {
						$("#rcReceivedDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					
					$(function() {
						$("#vehicleInvoiceDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	maxDate : document.getElementById("businessdate").value
					});
				     	});
					
					
					$(function() {
						$("#permitReceivedDate").datepicker({
						 changeMonth: true,
						 changeYear: true,
					     yearRange: '1900:+10',
					     showOn: 'both',
					     buttonImage: document.getElementById("CalImage").value,
				 	  	buttonImageOnly: true,
				 	  	dateFormat: document.getElementById("formatD").value,
				 	  	defaultDate: document.getElementById("businessdate").value,
				 	  	minDate : document.getElementById("businessdate").value
					});
				     	});
					
	function checkFormateYear(value,id)
		{
						var length=parseInt(value.length);
						var flg=1;
						if(length!=4)
							flg=0;
						if(length==4)
						for(var i=0;i<4;i++)
						{
							var code=value.charCodeAt(i);
								if(code>=48 && code<=57)
								{
								 if(i==3)	
								 {
									 var year=value.substring(3);
									 var yr=parseFloat(year);
										if(yr<0)
										{
											flg=0;
											break;
										}
								 }
								  continue;
								}
								else
								{
									flg=0;
									break;
								}
						
						
						}
						if(flg==0)
						{
							alert("Invalid Date format,Please enter as 'YYYY'('2012').");
							document.getElementById(id).value="";
							return false;
						}
					}

	$(function() {
		$("#dispatchDate").datepicker({
				changeMonth: true,
			changeYear: true,
	        yearRange: '1900:+10',
	        showOn: 'both',
	        buttonImage: document.getElementById("CalImage").value,
	        buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
	        
		});
	});
	
	$(function() {
		$("#delieveryDate").datepicker({
				changeMonth: true,
			changeYear: true,
	        yearRange: '1900:+10',
	        showOn: 'both',
	        buttonImage: document.getElementById("CalImage").value,
	        buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
	        
		});
	});	
	
	$(function() {
		$("#assetInvoiceDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value,
 	  	maxDate : document.getElementById("businessdate").value
	});
     	});
	
	// Code added for handling firstInterestDueDate Start| Rahul papneja
	$(function() {
		$("#firstInterestDueDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value
	});
     	});
	// Ends Here
	
	// Code added for handling maturityDate Start| Rahul papneja
	$(function() {
		$("#maturityDate1").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value
	});
     	});
	// Ends Here
	
	
	
	
	
	
	
	$(function() {
		$("#interestDueDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value
	});
     	});
	$(function() {
		$("#repayEffectiveDateOneLoan").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value
	});
     	});
	

	$(function() {
		$("#interestDueDateDis").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value
	});
     	});
	
	$(function() {
		$("#loanStartDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
	     buttonImage: document.getElementById("CalImage").value,
 	  	buttonImageOnly: true,
 	  	dateFormat: document.getElementById("formatD").value,
 	  	defaultDate: document.getElementById("businessdate").value
	});
     	});
	
	$(function() {
    		$("#IPVDate").datepicker({
    			 changeMonth: true,
    			 changeYear: true,
    		     yearRange: '1900:+10',
    		     showOn: 'both',
    		     buttonImage: document.getElementById("CalImage").value,
    		 	  	buttonImageOnly: true,
    		 	  	dateFormat: document.getElementById("formatD").value,
    		 	  	defaultDate: document.getElementById("businessdate").value
    		
    		});
    	     	});
	
	$(function() {
		$("#releaseDate").datepicker({
			 changeMonth: true,
			 changeYear: true,
		     yearRange: '1900:+10',
		     showOn: 'both',
		     buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
		
		});
	     	});

	 /*Added by Pranaya - for Interest Working-Collection Report*/
	 $(function() {
			$("#interestWorkingFromDate").datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
			
			 changeMonth: true,
			 changeYear: true,
			 yearRange: '1900:+10',
			 showOn: 'both',
			 buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
				});
				});
	 $(function() {
			$("#interestWorkingToDate").datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
			
			 changeMonth: true,
			 changeYear: true,
			 yearRange: '1900:+10',
			 showOn: 'both',
			 buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
				});
				});	
	 /*End by Pranaya*/
	
	 $(function() {
			$("#partPaymentDate").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
		        buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
		 
			});
		});

	 $(function() {
			$("#prfIdentityDocExpDate").datepicker({
			 changeMonth: true,
			 changeYear: true,
		     yearRange: '1900:+10',
		     showOn: 'both',
		     buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
		});
	     	});

		 $(function() {
			$("#repricingDate").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
		        buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value

	});
		});
	 $(function() {
			$("#prfAddressDocExpDate").datepicker({
			 changeMonth: true,
			 changeYear: true,
		     yearRange: '1900:+10',
		     showOn: 'both',
		     buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
		
		});
	     	});
	 
	 $(function() {
			$("#relatedPersonPrfIdntyDocExpDate").datepicker({
			 changeMonth: true,
			 changeYear: true,
		     yearRange: '1900:+10',
		     showOn: 'both',
		     buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
		
		});
	     	});
	 
	 $(function() {
			$("#proofOfIdentityDocumentExpDate").datepicker({
			 changeMonth: true,
			 changeYear: true,
		     yearRange: '1900:+10',
		     showOn: 'both',
		     buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
		
		});
	     	});
	 
	 $(function() {
			$("#proofOfAddressDocumentExpDate").datepicker({
			 changeMonth: true,
			 changeYear: true,
		     yearRange: '1900:+10',
		     showOn: 'both',
		     buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
		
		});
	     	});
	 
	 $(function() {
			$("#firstDueDate").datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
			
			 changeMonth: true,
			 changeYear: true,
			 yearRange: '1900:+10',
			 showOn: 'both',
			 buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
				});
				});	
	 
		
	 $(function() {
			$("#facilityMaturityDate").datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
			
			 changeMonth: true,
			 changeYear: true,
			 yearRange: '1900:+10',
			 showOn: 'both',
			 buttonImage: document.getElementById("CalImage").value,
		 	  	buttonImageOnly: true,
		 	  	dateFormat: document.getElementById("formatD").value,
		 	  	defaultDate: document.getElementById("businessdate").value
				});
				});	
	 
	 $(function() {
			$("#dateOfbirth1").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
		        buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
		        
			});
		});
	 $(function() {
			$("#dateOfbirth2").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
		        buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
		        
			});
		});
	 $(function() {
			$("#dateOfbirth3").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
		        buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
		        
			});
		});
	 $(function() {
			$("#dateOfbirth4").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
		        buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
		        
			});
		});
	 $(function() {
			$("#coAppcustDOB").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
		        buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
		     });
			});
	 $(function() {
			$("#gaurcustDOB").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
		        buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
		     });
			});