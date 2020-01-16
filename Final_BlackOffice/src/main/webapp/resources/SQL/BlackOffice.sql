
-- 직급 테이블
CREATE TABLE TBL_BO_POSITION(
     POSITIONNO         NUMBER          -- 직급 번호
    ,POSITIONNAME       NVARCHAR2(20)   -- 직급 이름
    ,POSITIONRANK       NUMBER          -- 직급 이 높을수록 번호가 낮다. ex)회장 1 이사 2
    ,CONSTRAINT PK_TBL_BO_POSITIONNO PRIMARY KEY (POSITIONNO)
);

CREATE SEQUENCE SEQ_BO_POSITION
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

select *
from TBL_BO_POSITION;


-- 부서 테이블
CREATE TABLE TBL_BO_DEPARTMENT(
     DEPARTMENTNO        NUMBER         -- 부서 번호
    ,DEPARTMENTNAME      NVARCHAR2(20)  -- 부서 이름 -- 인사팀, 개발1팀, 개발2팀
    ,CONSTRAINT PK_TBL_BO_DEPARTMENTNO PRIMARY KEY (DEPARTMENTNO)
);

CREATE SEQUENCE SEQ_BO_DEPARTMENT
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

select *
from TBL_BO_DEPARTMENT;

-- 사원 테이블
CREATE TABLE TBL_BO_EMPLOYEES (
     EMPLOYEENO      NUMBER                 -- 사원번호
    ,NAME            NVARCHAR2(10)          -- 사원이름
    ,ID              NVARCHAR2(100)         -- 아이디
    ,PASSWD          NVARCHAR2(100)         -- 비밀번호    
    ,JUBUN           NVARCHAR2(13)          -- 주민번호
    ,EMAIL           NVARCHAR2(100) NOT NULL    -- 이메일
    ,EMAILPW         NVARCHAR2(100) NOT NULL    -- 이메일 암호
    ,PHONE           NVARCHAR2(11)          -- 핸드폰
    ,ADDRESS         NVARCHAR2(100)         -- 주소
    ,DETAILADDRESS   NVARCHAR2(100)         -- 상세주소
    ,REGISTERDAY     DATE DEFAULT SYSDATE -- 입사일자
    ,GOTOWORK        NUMBER(1)                 -- 출근 1 퇴근 0
    ,STATUS          NUMBER(1)                      -- 퇴사는 0 재직 1 휴직 2
    ,FK_POSITIONNO   NUMBER                     -- 참조 키 직급번호
    ,FK_DEPARTMENTNO NUMBER                  -- 참조 키 부서번호
    ,CONSTRAINT PK_TBL_BO_EMPLOYEENO PRIMARY KEY (EMPLOYEENO)
    ,CONSTRAINT FK_TBL_BO_EMP_POSITIONNO FOREIGN KEY (FK_POSITIONNO)
                REFERENCES TBL_BO_POSITION (POSITIONNO) ON DELETE CASCADE
    ,CONSTRAINT FK_TBL_BO_EMP_DEPARTMENTNO FOREIGN KEY (FK_DEPARTMENTNO)
                REFERENCES TBL_BO_DEPARTMENT (DEPARTMENTNO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_EMPLOYEES
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

alter table TBL_BO_EMPLOYEES
add email nvarchar2(100) not null;

alter table TBL_BO_EMPLOYEES
add emailpw nvarchar2(100) not null;

select *
from TBL_BO_EMPLOYEES;

delete TBL_BO_EMPLOYEES;

commit;

-- 주소록 테이블
CREATE TABLE TBL_BO_ADDRESSBOOK (
     ADDRNO         NUMBER                  -- 주소록번호
    ,NAME           NVARCHAR2(10)           -- 이름
    ,PHONE          NVARCHAR2(11)           -- 핸드폰 번호
    ,EMAIL          NVARCHAR2(100)          -- 이메일
    ,ADDRESS        NVARCHAR2(100)          -- 주소
    ,DETAILADDRESS  NVARCHAR2(100)          -- 상세주소
    ,FK_EMPLOYEENO  NUMBER                  -- 참조키 사원번호
    ,CONSTRAINT     PK_TBL_BO_ADDRNO PRIMARY KEY (ADDRNO)
    ,CONSTRAINT     FK_TBL_BO_ADDR_EMPLOYEENO FOREIGN KEY (FK_EMPLOYEENO)
                    REFERENCES TBL_BO_EMPLOYEES (EMPLOYEENO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_ADDRESSBOOK
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

-- 일정 테이블
CREATE TABLE TBL_BO_SCHEDULE (
    SCHEDULE_NO NUMBER NOT NULL                 -- 일정번호
  , FK_EMPLOYEENO NUMBER NOT NULL                     -- 유저no
  , SCHEDULE_TITLE VARCHAR2(60)                 -- 일정제목
  , SCHEDULE_CONTENT CLOB                       -- 일정내용 
  , SCHEDULE_START DATE                         -- 시작날짜
  , SCHEDULE_END DATE                           -- 끝나는날짜
  , SCHEDULE_COLOR VARCHAR2(60)                 -- 색상
  , SCHEDULE_IMPORTANCE NUMBER                  -- 중요도( 중요하지 않으면 0, 중요하면 1 )
  , SCHEDULE_PROGRESSTAT NUMBER                 -- 진행여부  ( 미해결 0 , 해결 1 ) 
  , SCHEDULE_ALLDAY NUMBER                      --  allday 인지 아닌지 ( allday 아니면  0 , 맞으면 1 )  
  , SCHEDULE_AUTHORITY VARCHAR2(60)             -- 일정 수정권한 ( 없으면 0 있으면 1 )
  , SCHEDULE_TYPE  NUMBER                       -- 일정종류  (개일일정 0, 회사전체일정 1, 부서일정 2)
  , CONSTRAINT PK_TBL_BO_SCHEDULE_NO PRIMARY KEY(SCHEDULE_NO)
  , CONSTRAINT FK_TBL_BO_SCHEDULE_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES (EMPLOYEENO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_SCHEDULE
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

-- 보고 테이블
CREATE TABLE TBL_BO_REPORT (
     REPORTNO           NUMBER                  -- 보고서 번호
    ,FK_EMPLOYEENO      NUMBER                  -- 참조키 사원번호
    ,REPORTDAY          DATE DEFAULT SYSDATE    -- 보고 일자
    ,REPORTTYPE         NUMBER(1)               -- 일일보고는 0, 주간보고는 1, 월간보고 2
    ,TITLE              NVARCHAR2(100)          -- 제목
    ,CONTENT            CLOB                    -- 보고서 내용
    ,MEMO               NVARCHAR2(200)          -- 메모
    ,ADDRESSEE          NVARCHAR2(300)          -- 수신인 사번
    ,CONSTRAINT PK_TBL_BO_REPORTNO PRIMARY KEY(REPORTNO)
    ,CONSTRAINT FK_TBL_BO_REPORT_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_REPORT
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

select *
from TBL_BO_REPORT;

-- 지출결재 테이블
CREATE TABLE TBL_BO_EXPENDITURE (
     EXPENDITURENO           NUMBER                 -- 지출 결재 번호
    ,FK_EMPLOYEENO           NUMBER                 -- 참조 키 사원번호
    ,WRITEDAY                DATE DEFAULT SYSDATE   -- 결재 작성 일자
    ,EXPENDITUREDAY          DATE DEFAULT SYSDATE   -- 지출 일자
    ,EXPENDITUREMODE         NVARCHAR2(20)          -- 지출 종류
    ,TITLE                   NVARCHAR2(100)         -- 결재 제목
    ,CONTENT                 CLOB                   -- 갤재 내용
    ,SHAREDEPARTMENTNO       NUMBER                 -- 공유 부서 번호
    ,ATTACHFILENAME          NVARCHAR2(100)         -- 첨부 파일명
    ,STATUS                  NUMBER(1)              -- 결재 여부 1이면 결재완료 0이면 미결재
    ,APPROVER                NVARCHAR2(300)         -- 결재자 사원번호
    ,CONSTRAINT PK_TBL_BO_EXPENDITURENO PRIMARY KEY(EXPENDITURENO)
    ,CONSTRAINT FK_TBL_BO_EXPENDITURE_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_EXPENDITURE
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

select *
from TBL_BO_EXPENDITURE;

-- 휴가/휴직 테이블
CREATE TABLE TBL_BO_VACATION (
     VACATIONNO                 NUMBER                               -- 휴가/휴직 테이블
    ,FK_EMPLOYEENO              NUMBER                              -- 참조키 사원번호
    ,WRITEDAY                   DATE DEFAULT SYSDATE             -- 작성일자
    ,STARTDAY                   DATE DEFAULT SYSDATE             -- 시작일
    ,ENDDAY                     DATE DEFAULT SYSDATE + 1        -- 종료일
    ,TITLE                      NVARCHAR2(100)                           -- 제목
    ,REASON                     NVARCHAR2(200)                        -- 사유
    ,EMERGENCYCONTACTNETWORK    NVARCHAR2(11)           -- 비상 연락망
    ,SHAREDEPARTMENTNO          NUMBER                          -- 공유 부서번호
    ,ATTACHFILENAME             NVARCHAR2(100)                    -- 첨부 파일명
    ,STATUS                     NUMBER(1)                                 -- 결재 여부 1이면 결재완료, 0이면 결재대기중
    ,APPROVER                   NVARCHAR2(300)                       -- 결재자 사원번호
    ,CONSTRAINT PK_TBL_BO_VACATIONNO PRIMARY KEY(VACATIONNO)
    ,CONSTRAINT FK_TBL_BO_VACATION_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_VACATION
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

select *
from TBL_BO_VACATION;


-- 결재자 테이블
CREATE TABLE TBL_BO_APPROVER (
     APPROVERNO          NUMBER         -- 결재자 번호
    ,FK_EMPLOYEENO       NUMBER         -- 참조키 결재자 사원번호
    ,STATUS              NUMBER         -- 결재 유무
    ,FK_EXPENDITURENO    NUMBER         -- 참조키 지출결재 번호
    ,FK_VACATIONNO       NUMBER         -- 참조키 휴가결재 번호
    ,CONSTRAINT PK_TBL_BO_APPROVERNO PRIMARY KEY(APPROVERNO)
    ,CONSTRAINT FK_TBL_BO_APPROVER_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
    ,CONSTRAINT FK_TBL_BO_APPROVER_EXNO FOREIGN KEY(FK_EXPENDITURENO)
               REFERENCES TBL_BO_EXPENDITURE(EXPENDITURENO) ON DELETE CASCADE
    ,CONSTRAINT FK_TBL_BO_APPROVER_VACATIONNO FOREIGN KEY(FK_VACATIONNO)
               REFERENCES TBL_BO_VACATION(VACATIONNO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_APPROVER
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
    
-- 공지사항 테이블
CREATE TABLE TBL_BO_NOTICE (
     NOTICENO            NUMBER                 -- 공지사항 번호
    ,TITLE               NVARCHAR2(100)         -- 제목
    ,CONTENT             CLOB                   -- 글내용
    ,ATTACHFILENAME      NVARCHAR2(100)         -- 첨부 파일명
    ,WRITEDAY            DATE DEFAULT SYSDATE   -- 작성일자
    ,READCOUNT           NUMBER                 -- 조회 수
    ,FK_EMPLOYEENO       NUMBER                 -- 참조키 작성자 사원번호
    ,CONSTRAINT PK_TBL_BO_NOTICE PRIMARY KEY(NOTICENO)
    ,CONSTRAINT FK_TBL_BO_NOTICE_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_NOTICE
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

-- 업무게시판 테이블
CREATE TABLE TBL_BO_BUSINESSBOARD (
     BUSINESSBOARDNO         NUMBER                 -- 업무 게시판 글번호
    ,TITLE                   NVARCHAR2(100)         -- 제목
    ,CONTENT                 CLOB                   -- 글내용
    ,ATTACHFILENAME          NVARCHAR2(100)         -- 첨부파일명
    ,WRITEDAY                DATE DEFAULT SYSDATE   -- 작성일자
    ,READCOUNT               NUMBER                 -- 조회수
    ,SHAREDEPARTMENTNO       NUMBER                 -- 공유 부서번호
    ,SHAREPOSITIONNO         NUMBER                 -- 공유 직급번호
    ,CATEGORY                NVARCHAR2(100)         -- 업무게시판 카테고리
    ,FK_EMPLOYEENO       NUMBER                     -- 참조키 작성자 사원번호
    ,CONSTRAINT PK_TBL_BO_BUSINESSBOARD PRIMARY KEY(BUSINESSBOARDNO)
    ,CONSTRAINT FK_TBL_BO_BUSINESSBOARD_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_BUSINESSBOARD
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

-- 인사이동 테이블
CREATE TABLE TBL_BO_PERSONNELANNOUNCEMENT (
     ANNOUNCEMENTNO          NUMBER                 -- 인사이동 번호
    ,FK_EMPLOYEENO           NUMBER                 -- 참조키 사원번호
    ,ANNOUNCEMENTMODE        NUMBER                 -- 인사이동 종류 1 입사 2 퇴사 3 부서이동 4 승진 5 휴직 6 복직
    ,BEFOREPOSITIONNO        NUMBER                 -- 변경 전 직급번호
    ,BEFOREDEPARTMENTNO      NUMBER                 -- 변경 전 부서번호
    ,BEFORESTATUS            NUMBER                 -- 변경 전 상태  1 재직 2 휴직
    ,TITLE                   NVARCHAR2(100)         -- 제목
    ,CONTENT                 CLOB                   -- 내용
    ,ATTACHFILENAME          NVARCHAR2(100)         -- 첨부파일명
    ,ANNOUNCEMENTDAY         DATE DEFAULT SYSDATE   -- 인사이동 날짜
    ,CONSTRAINT PK_TBL_BO_ANNOUNCEMENT PRIMARY KEY(ANNOUNCEMENTNO)
    ,CONSTRAINT FK_TBL_BO_ANNOUNCEMENT_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_BO_PERSONNELANNOUNCEMENT
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- 채팅 테이블


