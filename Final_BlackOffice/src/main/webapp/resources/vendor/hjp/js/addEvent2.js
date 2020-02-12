var eventModal = $('#eventModal');

var modalTitle = $('.modal-title');
var editAllDay = $('#edit-allDay');
var editTitle = $('#edit-title');
var editStart = $('#edit-start');
var editEnd = $('#edit-end');
var editType = $('#edit-type');
var editColor = $('#edit-color');
var editDesc = $('#edit-desc');

var addBtnContainer = $('.modalBtnContainer-addEvent');
var modifyBtnContainer = $('.modalBtnContainer-modifyEvent');


/* ****************
 *  새로운 일정 생성
 * ************** */
var newEvent = function (start, end, eventType) {

    $("#contextMenu").hide(); //메뉴 숨김

    modalTitle.html('새로운 일정');
    editStart.val(start);
    editEnd.val(end);
    editType.val(eventType).prop("selected", true);

    addBtnContainer.show();
    modifyBtnContainer.hide();
    eventModal.modal('show');

    
    
    
    //새로운 일정 저장버튼 클릭
    $('#save-event').unbind();
    $('#save-event').on('click', function () {
    	
    	
        var eventData = {
       		schedule_title: editTitle.val(),
       		schedule_start: editStart.val(),
       		schedule_end: editEnd.val(),
            schedule_content : editDesc.val(),
            schedule_type: editType.val(),
            schedule_color: editColor.val(),
            allDay: false,
        };
        

        if (eventData.start > eventData.end) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (eventData.title === '') {
            alert('일정명은 필수입니다.');
            return false;
        }

        var realEndDay;

        if (editAllDay.is(':checked')) {
        	
            eventData.start = moment(eventData.start).format('YYYY-MM-DD');
            //render시 날짜표기수정
            eventData.end = moment(eventData.end).add(1, 'days').format('YYYY-MM-DD');
            
            //DB에 넣을때(선택)
            realEndDay = moment(eventData.end).format('YYYY-MM-DD');

            eventData.allDay = true;
        }

        $("#calendar").fullCalendar('renderEvent', eventData, true);
        eventModal.find('input, textarea').val('');
        editAllDay.prop('checked', false);
        eventModal.modal('hide');
        
        
        eventData = JSON.stringify(eventData);  // 객체를 String으로 

        //새로운 일정 저장
        $.ajax({
            type: "get",
            url: "/controller/departmentAddSchedule.action",
            dataType : "json", 
            data: {
            	"eventData" : eventData
            },
            success: function (response) {
            	
            	if(response.result == true) {
            		javascript:history.go(0);
            		alert("일정이 추가되었습니다."); 
            	}
            	else { 
            		
            		javascript:history.go(0);
            		alert("일정추가 실패 !!"); 
            	}
            },
            error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
        });
    });
};





