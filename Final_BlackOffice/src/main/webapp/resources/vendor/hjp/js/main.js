var draggedEventIsAllDay;
var activeInactiveWeekends = true;






// 시간을 알아오는 fuction 
function getDisplayEventDate(event) {

  var displayEventDate;

  // allday 가 아니라면 
  if (event.allDay == false) {
    var startTimeEventInfo = moment(event.start).format('HH:mm');
    var endTimeEventInfo = moment(event.end).format('HH:mm');
    displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo;
  } 
  
  // allday라면
  else {
    displayEventDate = "하루종일";
  }
  
  // console.log(displayEventDate); 
  return displayEventDate;
}






function filtering(event) {
  var show_username = true;
  var show_type = true;

  var fillter = $('input:checkbox.filter:checked').map(function () {   // 필터링 되는 체크박스 
    return $(this).val();
  }).get();
  var types = $('#type_filter').val();

  show_username = fillter.indexOf(event.username) >= 0;  // 필터 되는곳 username

  if (types && types.length > 0) {
    if (types[0] == "all") {
      show_type = true;
    } else {
      show_type = types.indexOf(event.type) >= 0;
    }
  }
  
  return show_username && show_type;
}



// 리사이즈를 설정한 함수
function calDateWhenResize(event) {

  var newDates = {
    startDate: '',
    endDate: ''
  };

  if (event.allDay) {
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
    newDates.endDate = moment(event.end._d).subtract(1, 'days').format('YYYY-MM-DD');
  } 
  
  else {
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD HH:mm');
    newDates.endDate = moment(event.end._d).format('YYYY-MM-DD HH:mm');
  }
  
  // console.log(newDates); 

  return newDates;
}



// 드랍시 나타는 일정 이벤트 
function calDateWhenDragnDrop(event) {
	
  // 드랍시 수정된 날짜반영
  var newDates = {
    startDate: '',
    endDate: ''
  }

  //하루짜리 all day
  if (event.allDay && event.end === null) {
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
    newDates.endDate = newDates.startDate;
  }

  //2일이상 all day
  else if (event.allDay && event.end !== null) {
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
    newDates.endDate = moment(event.end._d).subtract(1, 'days').format('YYYY-MM-DD');
  }

  // 시간별 스케줄 
  else if (!event.allDay) {
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD HH:mm');
    newDates.endDate = moment(event.end._d).format('YYYY-MM-DD HH:mm');
  }

  return newDates;
}



// **************************
// 캘린더 초기화 부분  ************
//**************************
var calendar = $('#calendar').fullCalendar({

  eventRender: function (event, element, view) {
	  
	  
    // 일정에 hover시 popover event 요약
	  
    element.popover({
      title: $('<div />', {
        class: 'popoverTitleCalendar',
        text: event.title
      }).css({
        'background': event.backgroundColor,
        'color': event.textColor
      }),

      // 캘린더에 등록한 사람, 이벤트 구분, 시간, 내용을 얻어와서 출력하는부분 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      content: $('<div />', {
          class: 'popoverInfoCalendar'
        }).append('<p><strong>등록자:</strong> ' + event.username + '</p>')										// db에서 유저 아이 넣어오기 
        .append('<p><strong>구분:</strong> ' + event.type + '</p>')											// db에서 구분에서 한글로 바꿔주기 
        .append('<p><strong>시간:</strong> ' + getDisplayEventDate(event) + '</p>')						 
        .append('<div class="popoverDescCalendar"><strong>설명:</strong> ' + event.description + '</div>'),	
      delay: {
        show: "800",
        hide: "50"
      },

      trigger: 'hover',
      placement: 'top',
      html: true,
      container: 'wrapper1'
    	  
    }); // popover event ----------------------------------------------------------

    return filtering(event);

  },  // eventRender(이벤트를 읽어 오기위한 function) -------------------------------------------

  
  //주말 숨기기 & 보이기 버튼
  customButtons: {
    viewWeekends: {
      text: '주말제외하기',
      click: function () {
        activeInactiveWeekends ? activeInactiveWeekends = false : activeInactiveWeekends = true;   // 
        $('#calendar').fullCalendar('option', {
          weekends: activeInactiveWeekends
        });
      }
    },
    
    
    // 프린트 하는 버튼 
    printButton: {
    	text : ' ',
        click: function() {
          window.print();
        }
      }
    
  },
  
  
  // 달력의 header 부분 
  header: {
    left: 'today, prevYear, nextYear, viewWeekends, printButton', 
    center: 'prev, title, next', 
    right: 'month,agendaWeek,agendaDay,listWeek'  
  },
  
  
  
  views: {
    month: {
      columnFormat: 'dddd'
    },
    agendaWeek: {
      columnFormat: 'M/D ddd',
      titleFormat: 'YYYY년 M월 D일',
      eventLimit: false
    },
    agendaDay: {
      columnFormat: 'dddd',
      eventLimit: false
    },
    listWeek: {
      columnFormat: ''
    }
  },

  
  
  /* ****************
   *  일정 받아옴 
   * ************** */

  
  // 일정을 받아오는 곳  json 데이터 ------------------------------------
  events: 
	  function (start, end, timezone, callback) {

	  	  // 일정을 올린 db에서 가져온 경우  
		  $.ajax({
		      type: "get",
		      dataType : "JSON", 
		      url: "/controller/individualScheduleJSONList.action",
		      data: {
		    	  
		      },
		      success: function (response) {   
		    	  
		        var fixedDate = response.map(function (array) {
		          if (array.allDay && array.start !== array.end) {
		            // 이틀 이상 AllDay 일정인 경우 달력에 표기시 하루를 더해야 정상출력
		            array.end = moment(array.end).add(1, 'days');
		          }
		          return array;
		        })
		        callback(fixedDate); 
		        console.log(fixedDate);
		      }, 
		      error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
		    });
		  },

		  
		  
		  
		  
		  
		  
  // 여기 부분 화면  부분출력하는부분으로 javacript 수정하는곳이다. -------------------
  eventAfterAllRender: function (view) {
    
	if (view.name == "month") {
      $(".fc-content").css('text-align', 'center'); 
      $(".fc-time").remove();
      $(".fc-content").css('height', 'auto');
      
      // 휴일 부분 
      $.ajax({
	      type: "get",
	      dataType : "JSON", 
	      url: "/controller/resources/vendor/hjp/holiday_daty.json",
	      data: {
	    	  
	      },
	      success: function (response) {   
	    	  
	    	  $(".holiday").empty();
	    	  
	    	  $.each(response,function(key, value) {
	    		  $("td[data-date ="+ value.start +"]").css('color', 'red').append("</br>" +
	    					"<span class='holiday' style='text-align: center;'>"+value.dateName +"</span>"); 
	    		});
	       
	      }, 
	      error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
	    });
	    
      
      
    }
	
	if(view.name == "listWeek") {
		
	}
    
  },

  
  
  
  
  //일정 리사이즈
  eventResize: function (event, delta, revertFunc, jsEvent, ui, view) {
    $('.popover.fade.top').remove();

    /** 리사이즈시 수정된 날짜반영
     * 하루를 빼야 정상적으로 반영됨. */
    var newDates = calDateWhenResize(event);

    //리사이즈한 일정 업데이트
    $.ajax({
      type: "get",
      url: "",
      data: {
        //id: event._id,
        //....
      },
      success: function (response) {
        alert('수정 ~~ : ' + newDates.startDate + ' ~ ' + newDates.endDate);
      }
    });

  },

  eventDragStart: function (event, jsEvent, ui, view) {
    draggedEventIsAllDay = event.allDay;
  },
  

  //일정 드래그앤드롭
  eventDrop: function (event, delta, revertFunc, jsEvent, ui, view) {
    $('.popover.fade.top').remove();

    //주,일 view일때 종일 <-> 시간 변경불가
    if (view.type === 'agendaWeek' || view.type === 'agendaDay') {
      if (draggedEventIsAllDay !== event.allDay) {
        alert('드래그앤드롭으로 종일<->시간 변경은 불가합니다.');
        location.reload();
        return false;
      }
    }

    
    
    // 드랍시 수정된 날짜반영
    // 시작 날짜 , 끝나는 날짜, 스케줄no 
    var newDates = calDateWhenDragnDrop(event);

    var eventData = {
    		schedule_no : event.schedule_no,
       		schedule_start: newDates.startDate,
       		schedule_end:   newDates.endDate
    }
    
    
    eventData = JSON.stringify(eventData);
    
    //드롭한 일정 업데이트
    $.ajax({
      type: "get",
      url: "/controller/calendarDropUpdate.action",
      dataType : "json", 
      data: {
    	  "eventData" : eventData
      },
      success: function (response) {
    	  
    	  $(".holiday").empty();
    	  
    	  if(response.result == true) {
    		  
      		javascript:history.go(0);
    	  	alert('수정: ' + newDates.startDate + ' ~ ' + newDates.endDate);
      	}
      	
      	else { 
      		
      		javascript:history.go(0);
      		alert("일정수정 실패 !!"); 
      	}
        
      }
    });

  },

  select: function (startDate, endDate, jsEvent, view) {

    $(".fc-body").unbind('click');
    $(".fc-body").on('click', 'td', function (e) {

      $("#contextMenu")
        .addClass("contextOpened")
        .css({
          display: "block",
          left: e.pageX,
          top: e.pageY
        });
      return false;
    });

    var today = moment();

    if (view.name == "month") {
      startDate.set({
        hours: today.hours(),
        minute: today.minutes()
      });
      startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
      endDate = moment(endDate).subtract(1, 'days');

      endDate.set({
        hours: today.hours() + 1,
        minute: today.minutes()
      });
      endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
    } else {
      startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
      endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
    }

    //날짜 클릭시 카테고리 선택메뉴
    var $contextMenu = $("#contextMenu");
    $contextMenu.on("click", "a", function (e) {
      e.preventDefault();

      //닫기 버튼이 아닐때
      if ($(this).data().role !== 'close') {
        newEvent(startDate, endDate, $(this).html());
      }

      $contextMenu.removeClass("contextOpened");
      $contextMenu.hide();
    });

    $('body').on('click', function () {
      $contextMenu.removeClass("contextOpened");
      $contextMenu.hide();
    });

  },

  
  //이벤트 클릭시 수정이벤트
  eventClick: function (event, jsEvent, view) {
    editEvent(event);  // editEvent 함수 호출
  },

  
  locale: 'ko',
  timezone: "local",
  nextDayThreshold: "09:00:00",
  allDaySlot: true,
  displayEventTime: true,
  displayEventEnd: true,
  firstDay: 1, //월요일이 먼저 오게 하려면 1
  weekNumbers: false,
  selectable: true,
  weekNumberCalculation: "ISO",
  eventLimit: true,
  views: {
    month: {
      eventLimit: 12
    }
  },
  
  
  
  
  eventLimitClick: 'week', //popover
  navLinks: true,
  timeFormat: 'HH:mm',
  

  
  defaultTimedEventDuration: '01:00:00',
  editable: true,
  minTime: '00:00:00',
  maxTime: '24:00:00',
  slotLabelFormat: 'HH:mm',
  weekends: true,
  nowIndicator: true,
  dayPopoverFormat: 'MM/DD dddd',
  longPressDelay: 0,
  eventLongPressDelay: 0,
  selectLongPressDelay: 0
  
});  // 켈린더 초기화 부분 ------------------------------------------------------------------------------

