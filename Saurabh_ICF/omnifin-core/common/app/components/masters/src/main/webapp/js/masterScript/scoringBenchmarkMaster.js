//scoring Benchmark Master


function scoringDetailsSave(val)
{		
	 var industry = document.getElementById("industry").value;
	 var scorecard = document.getElementById("scorecard").value;
	 var weightage = document.getElementById('weightage').value;
	 var rating = document.getElementsByName('rating');
	 var id="";
	 var a="";	
	 if(industry == "" || scorecard == "" || weightage == "")
	 {
		if(industry == "")
			a +="* Industry is required. \n";
		if(scorecard == "")
			a +="* Scorecard is required. \n";
		if(weightage == "")
			a +="* weightage is required.";
		alert(a);
		return false;		
	}
	for(var i=0;i<rating.length;i++)
	if(rating[i].value=='')
	{
		alert("All Rating is required.");
		return false;
	}		 
	var contextPath=document.getElementById('contextPath').value;
	document.getElementById("scoringBenchmarkSearch").action=contextPath+"/scoringBenchmarkMasterAdd.do?method=saveScoringDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("scoringBenchmarkSearch").submit();
	return true;		
}

function fnSearch(){	
	
	 var industry = document.getElementById("industry").value;
	 var scorecard = document.getElementById("scorecard").value;
	 var weightage = document.getElementById('weightage').value;
	
	var a = "";
	if(industry == "" || scorecard == "" ){
		if(industry == "")
			a +="* Industry is required. \n";
		if(scorecard == "")
			a +="* Scorecard is mandatory . \n";
		alert(a);
		return false;
	}
			var contextPath=document.getElementById('contextPath').value;
			document.getElementById('scoringBenchmarkSearch').action=contextPath+"/scoringBenchmarkMasterAdd.do?method=searchScoringDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("scoringBenchmarkSearch").submit();
			return true;	
	}

function isNumberKey(evt) 
	{
		var charCode = (evt.which) ? evt.which : event.keyCode;
		if (charCode > 31 && (charCode < 48 || charCode > 57))
	{
		alert("Only Numeric Allowed Here");
		return false;
	}
		return true;
	}

function allChecked(){
 	var chkbox=document.getElementsByName('chkd')
 		if(document.getElementById('allchkd').checked==true){
 			for(var i=0;i<chkbox.length;i++){
 					chkbox[i].checked=true;
 				}
 		}else{
 			 for(var i=0;i<chkbox.length;i++){
					 chkbox[i].checked=false;
 				}
 		}
  }

function viewScore()
{
	var industry = document.getElementById("lbxIndustryID").value;	
	var a = "";
	if(industry == "" )
	{
		if(industry == "")
			a +="* Industry is required. \n";	
		alert(a);
		return false;
	}
	var contextPath=document.getElementById('contextPath').value;
	var url=contextPath+"/scoringBenchmarkMasterAdd.do?method=scoreCardView&industry="+industry;;
	popupWin= window.open(url,'Score Card View','menubar, toolbar, location, directories, status, scrollbars, resizable, dependent, width=750, height=500, left=400, top=400')
	if (window.focus) 
	   popupWin.focus();	
}

function numbersonly(e){
	var dynaVal = this.id;
	document.getElementById(dynaVal).maxLength = 3;
	  var goods="0123456789.";
		    var key, keychar;
		    if (window.event)
		        key=window.event.keyCode;
		    else if (e)
		        key=e.which;
		    else
		        return true;
		    keychar = String.fromCharCode(key);
		    keychar = keychar.toLowerCase();
		    goods = goods.toLowerCase();
		    if (goods.indexOf(keychar) != -1)
		    {
		        goods="0123456789.";
		        return true;
		    }
		    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
		    {
		        goods="0123456789.";
		        return true;
		    }
		    return false;
	};