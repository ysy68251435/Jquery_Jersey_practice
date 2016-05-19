
/*var data=[{"wechat":"001","name":"name001","birth":"04/01/1988"},
		  {"wechat":"002","name":"name002","birth":"04/01/1988"},
		  {"wechat":"003","name":"name003","birth":"04/01/1988"}];*/

//var allusers=[];

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
	birth.html("<label class='birthLabel'><input type='text' value='"+birth.html()+"'/></label>"); 
	buttons.html("<button class='btnSave'>save</button><button class='btnDelete'>delete</button>"); 

	$(".btnSave").bind("click", Save); 
	$(".btnDelete").bind("click", Delete_ShowTable);
	$(".birthLabel input").datepicker();
}

var convertEditableTableToJson=function(tableId){
	var rows=[];
	$(tableId+" tr").each(function(i,n){
		var $row=$(n);
		rows.push({
			weChatId: $row.find("td:eq(0)").find("input").val(),
			name: $row.find("td:eq(1)").find("input").val(),
			birth: $row.find("td:eq(2)").find("input").val()
		});
	});
	return JSON.stringify(rows);
};

var convertFixTableToJson=function(tableId){
	var rows=[];
	$(tableId+" tr").each(function(i,n){
		var $row=$(n);
		rows.push({
			weChatId: $row.find("td:eq(0)").html(),
			name: $row.find("td:eq(1)").html(),
			birth: $row.find("td:eq(2)").html()
		});
	});
	return JSON.stringify(rows);
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
	$("#tbl1").empty();
	
	$.ajax({
		type: "GET",
		url: "http://localhost:8080/registration/service/allusers",
		dataType: "json",
		success: function(msg){
			console.log("success");
			console.log(msg);
			var allusers=msg;
			console.log(allusers);
			for(var index in allusers){
				var values=[];
				for(var key in allusers[index]){
					var value=allusers[index][key];
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
		},
		error: function(err){
			console.log("error");
			console.log(err);
		}
	});
});

$(document).on('click','#btnTest',function(){
	console.log("test get request");
	$.ajax({
		type: "GET",
		url:"http://localhost:8080/registration/service/test",
		dataType: "json",
		success: function(data){
			console.log(data);
			alert("jersey works");
		},
		error: function(err){
			console.log(err);
		}
	});
});

$(document).on('click', '.send', function(){
	console.log("send data");
    console.log("convertTableToJson");
    var jsonData=convertEditableTableToJson("#tbl");
    console.log(jsonData);
 
    $.ajax({
    	type : 'POST',
       	url : 'http://localhost:8080/registration/service/users',
       	contentType: 'application/json',
       	data : jsonData,
        dataType: 'json',
        success: function(msg) {
            console.log("success");
            console.log(msg);
            alert("submit success");
        },
        error: function(err) {
        	console.log("error");
        	console.log(err);
        }
   	});
});

$(document).on('click','.update',function(){
	console.log("update data");
	console.log("convertTableToJson");
	var jsonData=convertFixTableToJson("#tbl1");
	console.log(jsonData);
	
	$.ajax({
		type: "PUT",
		url:"http://localhost:8080/registration/service/newusers",
		contentType: "application/json",
		data: jsonData,
		dataType: "json",
		success: function(msg){
			console.log("success");
			console.log(msg);
			alert("update success");
		},
		error: function(err){
			console.log("error");
			console.log(err);
		}
	});
});


$(document).on('click','.search',function(e){
	console.log("search");
	var input=$('.searchForm').serialize();
	console.log(input);
	$("#p2").empty();
	$("#tbl2").empty();
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/registration/service/search/core2',
		data: input,
		dataType: 'json',
		cache:true,
		success: function(msg){
			console.log("success");
			console.log(msg);
			if(msg["response"]["numFound"]==0){
				$("#p2").append(
					"<h4>\""+"No Results"+"\"</h4>"
				);
			}else{
				var allusers=msg["response"]["docs"];
				console.log(allusers);
				$("#p2").append(
					"<h4>Found Results: "+msg["response"]["numFound"]+"</h4>"
				);
				for(var index in allusers){
					var values=[];
					for(var key in allusers[index]){
						var value=allusers[index][key];
						values.push(value);
					}
					$("#tbl2").append(
						"<tr>"+
						"<td>"+values[0]+"</td>"+
						"<td>"+values[1]+"</td>"+
						"<td>"+values[2]+"</td>"+
						"</tr>"
					);
				}
			}
		},
		error: function(err){
			console.log("error");
			console.log(err);
		}
	});

	e.preventDefault();
});



	