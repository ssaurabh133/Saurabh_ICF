
			function checkValidation()
			{
				var user_id=document.getElementById("userId").value;	
				var todate=document.getElementById("todate").value;	
				var fromdate=document.getElementById("fromdate").value;
				var queue=document.getElementById("queue").value;
				if(user_id==""&& todate=="" && fromdate=="" && queue=="")
				{
					alert("Select Any Field......");
					return false;
				}
				
						document.getElementById("performanceForm").submit();	
						Window.location.reload();
					}
//			}
//}
			function getDateObject(dateString,dateSeperator)
		    {
		    		var dateParts = dateString.split(dateSeperator);
					var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
					return dateObject;

		    }