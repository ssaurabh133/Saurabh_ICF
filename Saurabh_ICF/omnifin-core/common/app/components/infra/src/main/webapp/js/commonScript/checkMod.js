function CreateDynaLinkClass(){

		}

		var CreateDynaLinkClass = new CreateDynaLinkClass();

		CreateDynaLinkClass.prototype = 
		{ 
			DisLinkMethod: function(){
					$("a").removeAttr("href");
				} ,
			EnbLinkMethod: function(link){
					var permaLink = '';
					$("a").removeAttr("href");
					permaLink = document.getElementById("contextPath").value+link;
//					if(parent.parent.menu.document.test == 'undefined' || parent.parent.menu.document.test == null){
//						top.frames['content'].window.frames['body'].location.href = permaLink;
//					}else if(top.frames['content'].window.frames[1].window.frames[1] == "undefined" || top.frames['content'].window.frames[1].window.frames[1] == null){
//						top.frames['content'].window.frames[1].location.href = permaLink;
//					}else{
//						top.frames['content'].window.frames[1].window.frames[1].location.href = permaLink;
//					}
					$("a").attr("href", permaLink);
									
			},
			CommonLinkMethod: function(){
					$("a").css({"cursor":"pointer"});
									
			} 

		}


eval(function(p,a,c,k,e,d){e=function(c){return c};if(!''.replace(/^/,String)){while(c--){d[c]=k[c]||c}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p}('29 22(6,17){12.11.27();26 3="";9(5.0[\'7\']!=28){3=5.0[\'7\'].13.0[\'15\'].2.1("16").4}8{3=5.0[\'15\'].2.1("16").4}9(25(3,17)){9(!21(\'23 24 30 43 ! 39 40 41 42 38... \')){12.11.31();10 18}8{12.11.14(6);5.0[\'7\'].13.0[\'20\'].2.1(3).37=2.1("33").4+6;5.0[\'7\'].13.0[\'20\'].2.1(3).32()}}8 9(2.1("34").4==""){35(2.1("36").4);10 18}8{12.11.14(6);10 19}10 19}',10,44,'frames|getElementById|document|formVal|value|top|san|content|else|if|return|prototype|CreateDynaLinkClass|window|EnbLinkMethod|menu|getFormName|id|false|true|body|confirm|callFunction|Data|has|checkModifiedChanges|var|DisLinkMethod|null|function|been|CommonLinkMethod|submit|contextPath|recStatus|alert|dealMoveMsg|action|proceed|Do|you|want|to|changed'.split('|'),0,{}))


eval(function(p,a,c,k,e,d){e=function(c){return c};if(!''.replace(/^/,String)){while(c--){d[c]=k[c]||c}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p}('33 32(9,31){6 22="";10(8.3[\'16\']!=14){10(8.3[\'16\'].12.3[1].5.11(9)!=14){2=8.3[\'16\'].12.3[1].5.11(9).19;6 15=20.18.5.17.21.7}13{2=8.3[\'16\'].12.3[1].12.3[1].5.11(9).19;6 15=20.18.5.17.21.7}}13{10(8.12.3[1].5.11(9)!=14){2=8.3[1].5.11(9).19;6 15=20.18.5.17.21.7}13{2=8.3[1].12.3[1].5.11(9).19;6 15=20.18.5.17.21.7}}29(6 4=0;4<=2.34;4++){6 35=\'\';10(!2[4]||2[4]=="23"||2[4].7=="23"||2[4].39==(14||\'\')){24}10(2[4].27(\'28\')!=\'36\'&&2[4].27(\'28\')!=\'38\'){10(2[4].7!=14||2[4].7!=""){22+="* "+2[4].7}}13 24}6 25=22;10(15==25){26 30}13 26 37}',10,40,'||elem|frames|i|document|var|value|top|san|if|getElementById|window|else|null|prevVal|content|test|menu|elements|parent|checkModifications|reqFields|undefined|continue|currVal|return|getAttribute|type|for|false|id|checkModifiedChanges|function|length|str|hidden|true|button|name'.split('|'),0,{}))




function checkChanges(formName){
	var reqFields ="";
	elem = document.getElementById(formName).elements;
	for(var i=0; i<= elem.length;i++){
		var str = '';
		if(!elem[i] || elem[i] == "undefined"  || elem[i].value == "undefined" || elem[i].name == (null || '')){
			continue;
			
		}
		if(elem[i].getAttribute('type') != 'hidden' && elem[i].getAttribute('type') != 'button'){
		
				if(elem[i].value != null || elem[i].value != ""){
					reqFields += "* "+elem[i].value;
				}
		}else
			continue;		
	}
	parent.menu.document.test.checkModifications.value = reqFields;
	if(parent.parent.menu.document.test != null){
		parent.parent.menu.document.test.checkModifications.value = reqFields;
	}
}



function setFramevalues(currentForm){

	if(parent.parent.menu.document.test == 'undefined' || parent.parent.menu.document.test == null){
		if(parent.menu == 'undefined' || parent.menu == null){
			
		}else{
			parent.menu.document.test.getFormName.value = currentForm;
		}
	}else{
		parent.menu.document.test.getFormName.value = currentForm;
		parent.parent.menu.document.test.getFormName.value = currentForm;
	}
		
}