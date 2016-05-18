
var data=[{"wechat":"001","name":"name001","birth":"04/01/1988"},
		  {"wechat":"002","name":"name002","birth":"04/01/1988"},
		  {"wechat":"003","name":"name003","birth":"04/01/1988"}];

var isShown=false;

function Delete(){
	console.log("remove the row");
	$(this).closest('tr').remove();
}

function Delete_ShowTable(){
	console.log("remove one row of show table");
	$(this).closest('tr').remove();
}

function Save(){
	var par = $(this).parent().parent(); //tr 
	var wechat = par.children("td:nth-child(1)"); 
	var name = par.children("td:nth-child(2)"); 
	var birth = par.children("td:nth-child(3)"); 
	var buttons = par.children("td:nth-child(4)"); 

	wechat.html(wechat.children("input[type=text]").val()); 
	name.html(name.children("input[type=text]").val()); 
	birth.html(birth.children("input[type=text]").val()); 
	buttons.html("<button class='btnEdit'>edit</button><button class='btnDelete'>delete</button>"); 
	$(".btnEdit").bind("click", Edit); 
	$(".btnDelete").bind("click", Delete_ShowTable);
}

function Edit(){
	var par = $(this).parent().parent(); //tr 
	var wechat = par.children("td:nth-child(1)"); 
	var name = par.children("td:nth-child(2)"); 
	var birth = par.children("td:nth-child(3)"); 
	var buttons = par.children("td:nth-child(4)"); 

	wechat.html("<input type='text' value='"+wechat.html()+"'/>"); 
	name.html("<input type='text' value='"+name.html()+"'/>"); 
	birth.html("<input type='text' value='"+birth.html()+"'/>"); 
	buttons.html("<button class='btnSave'>save</button><button class='btnDelete'>delete</button>"); 

	$(".btnSave").bind("click", Save); 
	$(".btnDelete").bind("click", Delete_ShowTable);
}

$(document).on('click', '#btnAdd', function(){
	console.log("add row");
	$("#tbl").append(
		"<tr>"+
		"<td><input type='text' placeholder='WeChat'/></td>"+
		"<td><input type='text' placeholder='Name'/></td>"+
		"<td><label class='birthLabel'><input type='text' placeholder='Date: MM/DD/YYYY'/></label></td>"+
		"<td><button class='btnDelete'>delete</button></td>"+
		"</tr>"
	);
	$(".birthLabel input").datepicker();
	$(".btnDelete").bind('click',Delete);
});

$(document).on('click','#btnShow', function(){
	console.log("show existing urses");

	if(!isShown){
		for(var index in data){
			var values=[];
			for(var key in data[index]){
				var value=data[index][key];
				values.push(value);
			}
			$("#tbl1").append(
				"<tr>"+
				"<td>"+values[0]+"</td>"+
				"<td>"+values[1]+"</td>"+
				"<td>"+values[2]+"</td>"+
				"<td><button class='btnEdit'>edit</button><button class='btnDelete'>delete</button></td>"+
				"</tr>"
			);
			$(".btnDelete").bind('click',Delete_ShowTable);
			$(".btnEdit").bind('click',Edit);
		}

	}
	isShown=true;
});

/* send and update will be finished after building back end api
$(document).on('click', '.send', function(){
	console.log("send data");
    var tbl=$('#tbl tr').map(function() {
  				return $(this).find('td').map(function() {
    				return $(this).find('input').val();
  				}).get();
			}).get();

    console.log(tbl);
    
    $.ajax({
    	type : 'POST',
       	url : 'http://localhost:8085/solr/post/test',
       	headers: {
       		'Access-Control-Allow-Origin': '*',
       		'contentType': 'application/json'
       	},
       	data : JSON.stringify(tbl),
        dataType: 'json',
        success: function(msg) {
            console.log("success");
        },
        error : function(err) {
        	console.log("post error");
        }
   	});
});

$(document).on('click','.update',function(){
	console.log("update data");
});


*/


	