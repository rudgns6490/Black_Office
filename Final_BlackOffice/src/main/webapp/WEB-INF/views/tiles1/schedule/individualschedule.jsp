<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <!-- 캘린더 관련 css는 따로 입혔습니다. hjp 2020/01/13 -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/vendor/hjp/css/fullcalendar.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/vendor/hjp/css/bootstrap.min.css"> 
    <link rel="stylesheet" href='<%=request.getContextPath() %>/resources/vendor/hjp/css/select2.min.css' />
    <link rel="stylesheet" href='<%=request.getContextPath() %>/resources/vendor/hjp/css/bootstrap-datetimepicker.min.css' />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,500,600">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/vendor/hjp/css/main.css">
    
    
<style type="text/css">
    
	.star{
	  display:inline-block;
	  width: 50px;height: 60px;
	  cursor: pointer;
	}
	
	.star_left{
	  background : url('<%=request.getContextPath()%>/star.png')no-repeat 0 0;  
	  background-size: 60px; 
	  margin-right: -3px;
	}
	
	.star.on{
	  background-image: url('<%=request.getContextPath()%>/star_on.png'); 
	}
    
</style>


	
        <!-- 일자 클릭시 메뉴오픈 -->
        <div id="contextMenu" class="dropdown clearfix">
            <ul class="dropdown-menu dropNewEvent" role="menu" aria-labelledby="dropdownMenu"
                style="display:block;position:static;margin-bottom:5px;">
                <li><a tabindex="-1" href="#">일정추가하기</a></li>
                <li class="divider"></li>
                <li><a tabindex="-1" href="#" data-role="close">Close</a></li>
            </ul>
        </div>
		
		
        <div id="wrapper1" style="width: 100%">
        	<h2 align="center" style="padding: 30px;">개인일정</h2>
            <div id="loading"></div>
            	<div id="calendar" class="col-sm-11"></div>
        </div>


        <!-- 일정 추가 MODAL -->
        <div class="modal fade" tabindex="-1" role="dialog" id="eventModal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
                        <h4 class="modal-title"></h4>
                    </div>
                    <div class="modal-body">
                    
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-allDay">하루종일</label>
                                <input class='allDayNewEvent' id="edit-allDay" type="checkbox"></label>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-title">일정명</label>
                                <input class="inputModal" type="text" name="edit-title" id="edit-title"
                                    required="required" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-start">시작</label>
                                <input class="inputModal" type="text" name="edit-start" id="edit-start" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-end">끝</label>
                                <input class="inputModal" type="text" name="edit-end" id="edit-end" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-color">색상</label>
                                <select class="inputModal" name="color" id="edit-color">
                                    <option value="#D25565" style="color:#D25565;">빨간색</option>
                                    <option value="#9775fa" style="color:#9775fa;">보라색</option>
                                    <option value="#ffa94d" style="color:#ffa94d;">주황색</option>
                                    <option value="#74c0fc" style="color:#74c0fc;">파란색</option>
                                    <option value="#f06595" style="color:#f06595;">핑크색</option>
                                    <option value="#63e6be" style="color:#63e6be;">연두색</option>
                                    <option value="#a9e34b" style="color:#a9e34b;">초록색</option>
                                    <option value="#4d638c" style="color:#4d638c;">남색</option>
                                    <option value="#495057" style="color:#495057;">검정색</option>
                                </select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-desc">설명</label>
                                <textarea rows="4" cols="50" class="inputModal" name="edit-desc"
                                    id="edit-desc"></textarea>
                            </div>
                        </div>
                        
                       <!--   중요설정 제외할 예정 
                       
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="schedule_importance">중요설정</label>
                                <span class="star star_left"><input type="checkbox" id="starbool" style="display: none;"></span>
                                <input class='allDayNewEvent' id="schedule_importance" name="schedule_importance"type="checkbox">
                                </label>
                            </div>
                        </div>
                        
                         -->
                        
                        
                    </div>
                    <div class="modal-footer modalBtnContainer-addEvent">
                        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                        <button type="button" class="btn btn-primary" id="save-event">저장</button>
                    </div>
                    
                    <div class="modal-footer modalBtnContainer-modifyEvent">
                        <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                        <button type="button" class="btn btn-danger" id="deleteEvent">삭제</button>
                        <button type="button" class="btn btn-primary" id="updateEvent">저장</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <div class="panel panel-default" style="display: none;">

            <div class="panel-heading">
                <h3 class="panel-title">필터</h3>
            </div>

            <div class="panel-body">
<!-- 
                <div class="col-lg-6">
                    <label for="calendar_view">구분별</label>
                    <div class="input-group">
                        <select class="filter" id="type_filter" multiple="multiple">
                            <option value="0">개인일정</option>
                            <option value="1">부서일정</option>
                            <option value="2">회사일정</option>
                        </select>
                    </div>
                </div>

 -->

				
                <div class="col-lg-6">
                    <label for="calendar_view">등록자별</label>
                    <div class="input-group">
                        <label class="checkbox-inline"><input class='filter' type="checkbox" value="${sessionScope.loginuser.name}"
                                checked>1</label>
                    </div>
                </div>
                


            </div>
        </div>
        <!-- /.filter panel -->

    
    
    
    <!-- 캘린더 관련 js는 따로 입혔습니다. hjp 2020/01/13 -->
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/moment.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/fullcalendar.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/ko.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/select2.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/bootstrap-datetimepicker.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/main.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/addEvent.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/editEvent.js"></script>
    <script src="<%=request.getContextPath() %>/resources/vendor/hjp/js/etcSetting.js"></script>	
    
    

