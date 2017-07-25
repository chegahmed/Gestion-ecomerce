$(".delete").on('click', function() {
	$('.case:checkbox:checked').parents("tr").remove();
	$('.check_all').prop("checked", false); 
	check();
});
var i=$('#table1 tr').length;

$(".addmore").on('click',function(){
	count=$('#table1 tr').length;
	
    var data="<tr><td><input type='checkbox' class='case'/></td><td><span id='snum"+i+"'>"+count+".</span></td>";
    data +="<td><input class='form-control' type='text' id='countryname_"+i+"' name='countryname[]'/></td> <td><input class='form-control' type='text' id='country_no_"+i+"' name='country_no[]'/></td><td><input class='form-control' type='text' id='phone_code_"+i+"' name='phone_code[]'/></td><td><input class='form-control' type='text' id='country_code_"+i+"' name='country_code[]'/></td></tr>";
	$('#table1').append(data);
	alert(i);
	row = i ;
	i++;
});
				
function select_all() {
	$('input[class=case]:checkbox').each(function(){ 
		if($('input[class=check_all]:checkbox:checked').length == 0){ 
			$(this).prop("checked", false); 
		} else {
			$(this).prop("checked", true); 
		} 
	});
}

function check(){
	obj=$('table tr').find('span');
	$.each( obj, function( key, value ) {
		id=value.id;
		$('#'+id).html(key+1);
	});
}
										
