





//SELECT 색 변경
$('#edit-color').change(function () {
    $(this).css('color', $(this).val());
});



//필터
$('.filter').on('change', function () {
    $('#calendar').fullCalendar('rerenderEvents');
});


$("#type_filter").select2({
    placeholder: "선택..",
    allowClear: true
});



//datetimepicker
$("#edit-start, #edit-end").datetimepicker({
    format: 'YYYY-MM-DD HH:mm'
});


// 프린트 아이콘 추가 
$(".fc-printButton-button").addClass("glyphicon glyphicon-print"); 




$(".star").on('click',function(){
	
	var bool = $("input:checkbox[id='starbool']").prop("checked");  // 체크 박스검사 
	
	// checkbox가  false 경우 
	if( bool == false ) {
		$("input:checkbox[id='starbool']").prop("checked", true);  // 체크를 해주고 
		
		var bool2 = $("input:checkbox[id='starbool']").prop("checked"); // 현재 상태 가져오기 
		
		
		if(bool2 == true) {
			$(".star").addClass("on");
		}
		
		if(bool2 == false) {
			$(".star").removeClass("on");
		}
	}
	
	
	// checkbox가 true 경우 
	if( bool == true ) {
		$("input:checkbox[id='starbool']").prop("checked", false);  // 체크를 해제해주고 
		
		var bool2 = $("input:checkbox[id='starbool']").prop("checked"); // 현재 상태 가져오기 
		
		if(bool2 == true) {
			$(".star").addClass("on");
		}
		
		if(bool2 == false) {
			$(".star").removeClass("on");
		}
	}
});



 



