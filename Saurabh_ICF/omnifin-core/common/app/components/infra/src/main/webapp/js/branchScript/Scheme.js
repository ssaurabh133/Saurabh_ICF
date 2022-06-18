function checkRadio()
{
	alert("hello");
	var check = document.getElementsByName("check") ;
	
	var hiddencode = document.getElementsByName("hiddencode") ;
	var hiddenname = document.getElementsByName("hiddenname") ;
	var hiddendesc = document.getElementsByName("hiddendesc") ;
	
	for(k=0;k<hiddencode.length;k++)
	{
		
		if(check[k].checked)
		{
			alert("hello"+hiddencode[k].value+""+hiddenname[k].value+""+hiddendesc[k].value);		
			document.getElementById("schemeCode").value=hiddencode[k].value;
			document.getElementById("productName").value=hiddenname[k].value;
			document.getElementById("schemeDesc").value=hiddendesc[k].value;
			
			
			document.getElementById("schemeCode").setAttribute('readonly', 'true');
			
			document.getElementById("update").style.display='';
			document.getElementById("save").style.display='none';
		
		}
		
			}
	
}

function checkRadioForScheme()
{
	var check = document.getElementsByName("check") ;
	
	var hiddencode = document.getElementsByName("hiddencode") ;
	var hiddenname = document.getElementsByName("hiddenname") ;
	var hiddendesc = document.getElementsByName("hiddendesc") ;
	

	
	for(k=0;k<hiddencode.length;k++)
	{
			
		if(check[k].checked)
		{
						
			document.getElementById("schemeCode").value=hiddencode[k].value;
			document.getElementById("productName").value=hiddenname[k].value;
			document.getElementById("schemeDesc").value=hiddendesc[k].value;
			
			
			document.getElementById("schemeCode").setAttribute('readonly', 'true');
			
			document.getElementById("update").style.display='';
			document.getElementById("save").style.display='none';
		
		}
		
		
	}
	
}

