
function checkRadio()
{
	var check = document.getElementsByName("check") ;
	
	var hiddencode = document.getElementsByName("hiddencode") ;
	var hiddendesc = document.getElementsByName("hiddendesc") ;
	

	
	for(k=0;k<hiddencode.length;k++)
	{
			
		if(check[k].checked)
		{
			//alert(hiddencode[k].value);
			document.getElementById("branchCode").value=hiddencode[k].value;
			document.getElementById("branchDesc").value=hiddendesc[k].value;
			
			
			document.getElementById("branchCode").setAttribute('readonly', 'true');
			
			document.getElementById("update").style.display='';
			document.getElementById("save").style.display='none';
		
		}
		
		
	}
	
}

function checkRadioForProduct()
{
	var check = document.getElementsByName("check") ;
	
	var hiddencode = document.getElementsByName("hiddencode") ;
	var hiddendesc = document.getElementsByName("hiddendesc") ;
	

	
	for(k=0;k<hiddencode.length;k++)
	{
			
		if(check[k].checked)
		{
			//alert(hiddencode[k].value);
			document.getElementById("productCode").value=hiddencode[k].value;
			document.getElementById("productDesc").value=hiddendesc[k].value;
			
			
			document.getElementById("productCode").setAttribute('readonly', 'true');
			
			document.getElementById("update").style.display='';
			document.getElementById("save").style.display='none';
		
		}
		
		
	}
	
}