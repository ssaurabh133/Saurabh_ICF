/**
 * Created by Rohit 20-Mar-2016
 */


function handleFiles(files) 
	{
		//alert('inside subLedgerUpload.js file');
		// Check for the various File API support
		if (window.FileReader)
		{
			// FileReader are supported.
			getAsText(files[0]);
		} 
		else 
		{
			alert('FileReader not supported on this browser.');
		}
	}

	function getAsText(fileToRead)
	{
		var reader = new FileReader();
		// Read file into memory as UTF-8      
		reader.readAsText(fileToRead);
		// Handle errors load
		reader.onload = loadHandler;
		reader.onerror = errorHandler;
	}

	function loadHandler(event) 
	{
		var csv = event.target.result;
		processData(csv);
	}
	
	function errorHandler(evt) 
	{
		if(evt.target.error.name == "NotReadableError") 
		{
			alert("Canno't read file !");
		}
	}
	
	
	function processData(csv)
	{
		var allTextLines = csv.split(/\n|\n/);
		
		goout:
		for (var i=1; i<allTextLines.length; i++) 
		{
			
				if(allTextLines[i].length > 1)
				{
					var data = allTextLines[i].split(',');
					
					if(data.length == 8 )
					{
						var result = validateData(data,i);
						if(!result)
						{
							document.getElementById("docFile").value = "";
							break goout;
						}
					}
					else
					{
						document.getElementById("docFile").value = "";
						alert('Some extra or less columns found on line NO '+i);
						break goout;
					}
				}
			
		}
		
	}
	
	
	function validateData(data,line_no)
	{
		var serial_no = data[0];
		var LOAN_ID= data[1];
		var VENDOR_NAME= data[2];
		var UMRN_NO= data[3];
		var FACILITY= data[4];
		var ACH_STATUS= data[5];
		var STATUS_DATE= data[6];
		var REMARKS= data[7];
		
		
		var msg = '';
		
		// test serial no 1
		try
		{
			var sn = parseInt(serial_no);
			if( sn > 0 && serial_no.indexOf(".") == -1 )
			{}
			else
			{ 
				msg += 'Blank or invalid serial no on line no. '+line_no+'\n'; 
				
			}
		}
		catch(err)
		{
			alert(err+' on line no. '+line_no);
		}
		
		// test DEAL_NO
		try
		{
			var sn = LOAN_ID;
			if( sn =="" )
			{
				msg += 'Blank or invalid LOAN_ID on line no. '+line_no+'\n'; 
			}
			else
			{ 
				
				
			}
		}
		catch(err)
		{
			alert(err+' on line no. '+line_no);
		}
		
		
		// VENDOR_NAME
		try
		{
			var sn = VENDOR_NAME;
			var sn1=ACH_STATUS;
			/*if( sn =="" )
			{
				msg += 'Blank or invalid VENDOR_NAME on line no. '+line_no+'\n';
			}*/
			if(sn=="" & sn1=="SR")
				{
				msg += ' VENDOR_NAME IS MANDATORY WITH ACH STATUS (SR) on line no. '+line_no+'\n';
				}
			else
			{ 
			 
				
			}
		}
		catch(err)
		{
			alert(err+' on line no. '+line_no);
		}
		// UMRN_NO
		try
		{
			var sn = UMRN_NO;
			var sn1 =ACH_STATUS;
			if( sn =="" && sn1=="A")
			{
				msg += 'UMRN_NO IS MANDATORY WITH STATUS A(APPROVED) on line no. '+line_no+'\n';
			}
			else
			{ 
				 
				
			}
		}
		catch(err)
		{
			alert(err+' on line no. '+line_no);
		}
		//FACILITY
		try
		{
			var sn =FACILITY;
			if( sn =="" )
			{
				msg += 'Blank or invalid FACILITY on line no. '+line_no+'\n';
			}
			else
			{ 
				 
				
			}
		}
		catch(err)
		{
			alert(err+' on line no. '+line_no);
		}
		// test ACH_STATUS
		try
		{
			var sn = ACH_STATUS;
			if( sn =="A" || sn=="R" || sn=="SR" )
			{
				
			}
			else
			{ 
				
				msg += 'Blank or invalid ACH_STATUS on line no. '+line_no+'\n'; 
			}
		}
		catch(err)
		{
			alert(err+' on line no. '+line_no);
		}
		//STATUS_DATE
		try
		{
			var sn =STATUS_DATE;
			if( sn =="" )
			{
				msg += 'Blank or invalid STATUS_DATE on line no. '+line_no+'\n';
			}
			else
			{ 
				 
				
			}
		}
		catch(err)
		{
			alert(err+' on line no. '+line_no);
		}
		
		//REMARKS
		try
		{
			var sn =REMARKS;
			var sn1 =ACH_STATUS;
			if( sn =="" && sn1=="R" )
			{
				msg += 'REJECT REASON IS MANDATORY IN REMARKS on line no. '+line_no+'\n';
			}
			else
			{ 
				 
				
			}
		}
		catch(err)
		{
			alert(err+' on line no. '+line_no);
		}
		
		
		
		
		if(msg.length > 0)
		{
			alert(msg);
			return false;
		}
		return true;
		
	}
	
