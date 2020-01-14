/* ****************
 *  일정 편집
 * ************** */
var editEvent = function (event, element, view) {

    $('.popover.fade.top').remove();
    $(element).popover("hide");

    if (event.allDay === true) {
        editAllDay.prop('checked', true);
    } else {
        editAllDay.prop('checked', false);
    }

    if (event.end === null) {
        event.end = event.start;
    }

    if (event.allDay === true && event.end !== event.start) {
        editEnd.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD HH:mm'))
    } else {
        editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
    }

    modalTitle.html('일정 수정');
    editTitle.val(event.title);
    editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
    editType.val(event.type);
    editDesc.val(event.description);
    editColor.val(event.backgroundColor).css('color', event.backgroundColor);

    addBtnContainer.hide();
    modifyBtnContainer.show();
    eventModal.modal('show');

    //업데이트 버튼 클릭시
    $('#updateEvent').unbind();
    $('#updateEvent').on('click', function () {

        if (editStart.val() > editEnd.val()) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (editTitle.val() === '') {
            alert('일정명은 필수입니다.'); 
            return false;
        }
        
        
        if(editType.val() == null) {
        	alert("일정 구분은 필수입니다."); 
        	return false; 
        }

        var statusAllDay;
        var startDate;
        var endDate;
        var displayDate;

        if (editAllDay.is(':checked')) {
            statusAllDay = true;
            startDate = moment(editStart.val()).format('YYYY-MM-DD');
            endDate = moment(editEnd.val()).format('YYYY-MM-DD');
            displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD');
        } else {
            statusAllDay = false;
            startDate = editStart.val();
            endDate = editEnd.val();
            displayDate = endDate;
        }

        eventModal.modal('hide');
        

        event.allDay = statusAllDay;
        event.title = editTitle.val();
        event.start = startDate;
        event.end = displayDate;
        event.type = editType.val();
        event.backgroundColor = $("#edit-color").val();
        event.description = editDesc.val();

        $("#calendar").fullCalendar('updateEvent', event);
        
        var eventData = {
        		
        		user_no: '1', 		//	사원db 만들어지면 연동하기 
           		schedule_title : editTitle.val(),
           		schedule_start: startDate,
           		schedule_end: displayDate,
                schedule_content : editDesc.val(),
                schedule_type: editType.val(),
                // username: "사장1",				// 사원db 만들어지면 연동하기 
                schedule_color: event.backgroundColor,
                allDay: statusAllDay,
                schedule_no : event.schedule_no
        }
        
        eventData = JSON.stringify(eventData); 
        
        //일정 업데이트
        $.ajax({
            type: "get",
            url: "/controller/calendarUpdate.action",
            dataType : "json", 
            data: {
            	"eventData" : eventData
            },
            success: function (response) {
            	
            	if(response.result == true) {
            		javascript:history.go(0);
            		alert("일정이 수정되었습니다."); 
            	}
            	
            	else { 
            		
            		javascript:history.go(0);
            		alert("일정수정 실패 !!"); 
            	}
            }
        });

    });
    
    
     
	
	    // 삭제버튼
    $('#deleteEvent').on('click', function () {
        $('#deleteEvent').unbind();
        $("#calendar").fullCalendar('removeEvents', event);
        eventModal.modal('hide');
        
		var eventData = {
				schedule_no : event.schedule_no
		}
		
		eventData = JSON.stringify(eventData); 
        
        //삭제시
        $.ajax({
            type: "get",
            url: "/controller/calendarDrop.action",
            dataType : "json", 
            data: {
            	"eventData" : eventData
            },
            success: function (response) {
            	
            	
            	if(response.result == true) {
            		javascript:history.go(0);
            		alert("일정이 삭제되었습니다."); 
            	}
            	
            	else { 
            		
            		javascript:history.go(0);
            		alert("일정삭제 실패 !!"); 
            	}
            	
            }
        });
    });
};