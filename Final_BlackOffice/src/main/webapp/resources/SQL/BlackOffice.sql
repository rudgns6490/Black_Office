
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

select positionname
from TBL_BO_POSITION;

insert into TBL_BO_POSITION(POSITIONNO, POSITIONNAME, POSITIONRANK)
values(SEQ_BO_POSITION.nextval, '사장', 1);

insert into TBL_BO_POSITION(POSITIONNO, POSITIONNAME, POSITIONRANK)
values(SEQ_BO_POSITION.nextval, '이사', 2);

insert into TBL_BO_POSITION(POSITIONNO, POSITIONNAME, POSITIONRANK)
values(SEQ_BO_POSITION.nextval, '부장', 3);

insert into TBL_BO_POSITION(POSITIONNO, POSITIONNAME, POSITIONRANK)
values(SEQ_BO_POSITION.nextval, '차장', 4);

insert into TBL_BO_POSITION(POSITIONNO, POSITIONNAME, POSITIONRANK)
values(SEQ_BO_POSITION.nextval, '과장', 5);

insert into TBL_BO_POSITION(POSITIONNO, POSITIONNAME, POSITIONRANK)
values(SEQ_BO_POSITION.nextval, '대리', 6);

insert into TBL_BO_POSITION(POSITIONNO, POSITIONNAME, POSITIONRANK)
values(SEQ_BO_POSITION.nextval, '사원', 7);

commit;

select *
from TBL_BO_POSITION;

select departmentname
from TBL_BO_DEPARTMENT
where DEPARTMENTNO = '3';


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

insert into TBL_BO_DEPARTMENT(DEPARTMENTNO, DEPARTMENTNAME)
values(SEQ_BO_DEPARTMENT.nextval, '인사팀');

insert into TBL_BO_DEPARTMENT(DEPARTMENTNO, DEPARTMENTNAME)
values(SEQ_BO_DEPARTMENT.nextval, '마케팅팀');

insert into TBL_BO_DEPARTMENT(DEPARTMENTNO, DEPARTMENTNAME)
values(SEQ_BO_DEPARTMENT.nextval, '개발1팀');

insert into TBL_BO_DEPARTMENT(DEPARTMENTNO, DEPARTMENTNAME)
values(SEQ_BO_DEPARTMENT.nextval, '개발2팀');

insert into TBL_BO_DEPARTMENT(DEPARTMENTNO, DEPARTMENTNAME)
values(SEQ_BO_DEPARTMENT.nextval, '영업팀');

commit;

select *
from TBL_BO_EMPLOYEES;

commit;

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
    
alter table TBL_BO_EMPLOYEES
add FILENAME nvarchar2(100);

alter table TBL_BO_EMPLOYEES
add ORGFILENAME nvarchar2(100);

alter table TBL_BO_EMPLOYEES
add FILESIZE nvarchar2(100);

alter table TBL_BO_EMPLOYEES
add THUMBNAILFILENAME nvarchar2(100);

update TBL_BO_EMPLOYEES set THUMBNAILFILENAME = '2020020917012995249332228000.png';

commit;

alter table TBL_BO_EMPLOYEES drop constraint FK_TBL_BO_EMP_DEPARTMENTNO;

CREATE SEQUENCE SEQ_BO_EMPLOYEES
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;


select *
from TBL_BO_SCHEDULE;

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

-- 부서번호 추가 
alter table TBL_BO_SCHEDULE
add department number; 

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
from TBL_BO_APPROVER;


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

select *
from TBL_BO_NOTICE;

drop table TBL_BO_NOTICE purge;

-- 공지사항 개선 테이블
create table TBL_BO_NOTICE
(seq            number                -- 글번호
,fk_userid      Nvarchar2(20)         -- 사용자ID
,name           Nvarchar2(20)         -- 글쓴이
,subject        Nvarchar2(200)        -- 글제목
,content        Nvarchar2(2000)       -- 글내용    -- clob
,readCount      number default 0      -- 글조회수
,regDate        date default sysdate  -- 글쓴시간
,status         number(1) default 1   -- 글삭제여부  1:사용가능한글,  0:삭제된글 
,commentCount   number default 0      -- 댓글의 갯수
,fk_employeeno  number                -- 참조키 작성자 사원번호

,fileName       varchar2(255)                    -- WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)                                       
,orgFilename    varchar2(255)                    -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
,fileSize       number                           -- 파일크기  

,constraint  PK_TBL_BO_NOTICE_seq primary key(seq)
--,constraint  FK_TBL_BO_NOTICE_userid foreign key(fk_userid) references TBL_BO_EMPLOYEES(ID) // 자꾸 에러남
,constraint  FK_TBL_BO_NOTICE_EMPNO FOREIGN KEY(fk_employeeno)
             REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
,constraint  CK_TBL_BO_NOTICE_status check( status in(0,1) )
);

create sequence notice_boardSeq
start with 1
increment by 1
nomaxvalue 
nominvalue
nocycle
nocache;

select *
from TBL_BO_DEPT_BOARD;

-- 업무게시판 개선 테이블
create table TBL_BO_DEPT_BOARD
(seq            number                -- 글번호
,fk_userid      Nvarchar2(20)         -- 사용자ID
,name           Nvarchar2(20)         -- 글쓴이
,subject        Nvarchar2(200)        -- 글제목
,content        Nvarchar2(2000)       -- 글내용    -- clob
,readCount      number default 0      -- 글조회수
,regDate        date default sysdate  -- 글쓴시간
,status         number(1) default 1   -- 글삭제여부  1:사용가능한글,  0:삭제된글 
,commentCount   number default 0      -- 댓글의 갯수
,fk_employeeno  number                -- 참조키 작성자 사원번호

,fileName       varchar2(255)                    -- WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)                                       
,orgFilename    varchar2(255)                    -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
,fileSize       number                           -- 파일크기  

,constraint  PK_TBL_BO_DEPT_BOARD_seq primary key(seq)
--,constraint  FK_TBL_BO_NOTICE_userid foreign key(fk_userid) references TBL_BO_EMPLOYEES(ID) // 자꾸 에러남
,constraint  FK_TBL_BO_DEPT_BOARD_EMPNO FOREIGN KEY(fk_employeeno)
             REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
,constraint  CK_TBL_DEPT_BOARD_status check( status in(0,1) )
);

create sequence dept_boardSeq
start with 1
increment by 1
nomaxvalue 
nominvalue
nocycle
nocache;

drop table tblComment purge;


----- **** 댓글 테이블 생성 **** -----
create table tblComment
(seq           number                  -- 댓글번호
,fk_userid     varchar2(20)            -- 사용자ID
,name          varchar2(20)            -- 성명
,content       varchar2(1000)          -- 댓글내용
,regDate       date default sysdate    -- 작성일자
,parentSeq     number                  -- 원게시물 글번호
,status        number(1) default 1     -- 글삭제여부
                                               -- 1 : 사용가능한 글,  0 : 삭제된 글
                                               -- 댓글은 원글이 삭제되면 자동적으로 삭제되어야 한다.
,constraint PK_tblComment_seq primary key(seq)
--,constraint FK_tblComment_userid foreign key(fk_userid)
--                                    references TBL_BO_EMPLOYEES(ID)
,constraint FK_tblComment_parentSeq foreign key(parentSeq) 
                                      references TBL_BO_DEPT_BOARD(seq) on delete cascade
,constraint CK_tblComment_status check( status in(1,0) ) 
);

create sequence commentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

--  인사이동 테이블
create table tbl_bo_personnelAnnouncement (
    seq                     number,
    employeeno              number,                     -- 사원번호
    name                    nvarchar2(100),             -- 이름
    departmentname          nvarchar2(100),             -- 변경전 부서명
    movedepartmentname      nvarchar2(100),             -- 변경후 부서명
    positionname            nvarchar2(100),             -- 변경전 직위명
    movepositionname        nvarchar2(100),             -- 변경후 직위명
    leaveofabsence          nvarchar2(100),             -- 휴직종류
    announcementmode        number,                      -- 1 퇴사 2 부서변경 3 진급 4 휴직 5 복직
    registerday             date default sysdate                      -- 등록일자
);

CREATE SEQUENCE SEQ_BO_PERSONNELANNOUNCEMENT
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;


-- 개인주소록 테이블
CREATE TABLE TBL_BO_ADDRESSBOOK_PERSONAL (
ADDRNO             NUMBER                        -- 주소록번호
,NAME                NVARCHAR2(10)               -- 이름
,EMAIL                NVARCHAR2(100)              -- 이메일
,PHONE               NVARCHAR2(20)               -- 전화  (개인이 마음대로 할 수 있게 설정함 - 해외전화도저장가능)
,POSITIONNAME    NVARCHAR2(20)               -- 직책
,DEPARTMENTNAME NVARCHAR2(20)           -- 부서
,COMPANYNAME   NVARCHAR2(20)               -- 회사
,GROUPNAME       NVARCHAR2(20)               -- 그룹
,CONSTRAINT     PK_TBL_BO_ADDRESSBOOK_P_ADDRNO PRIMARY KEY (ADDRNO)
);

alter table TBL_BO_ADDRESSBOOK_PERSONAL
add adressmode number(1);

alter table TBL_BO_ADDRESSBOOK_PERSONAL
add fk_employeeno number;

ALTER TABLE TBL_BO_ADDRESSBOOK_PERSONAL DROP COLUMN fk_employeeno;

CREATE SEQUENCE SEQ_BO_ADDRESSBOOK_PERSONAL
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

select *
from TBL_BO_ADDRESSBOOK_PERSONAL;

select name, email, phone, positionname, departmentname
		from 
		(
		    select rownum AS rno
		         , name, email, phone, positionname, departmentname
		    from
		    (
		        select name, email, phone, positionname, departmentname
		        from TBL_BO_ADDRESSBOOK_PERSONAL
		        where adressmode = 2 
		        and departmentname like '%'|| '인사' ||'%'

		    ) V
		) T
		where rno between 1 and 10
        
        -- 2020/01/22/ psj 
        -- 지출결재 테이블
CREATE TABLE TBL_BO_EXPENDITURE (
     EXPENDITURENO           NUMBER                 -- 지출 결재 번호
    ,FK_EMPLOYEENO           NUMBER                 -- 참조 키 사원번호
    ,WRITEDAY                DATE DEFAULT SYSDATE   -- 결재 작성 일자
    ,EXPENDITUREDAY          DATE DEFAULT SYSDATE   -- 지출 일자
    ,EXPENDITUREMODE         number(1) default 0    -- 지출 종류 -- -- 개인지출 이면 '0' , 법인지출 '1'
    ,TITLE                   NVARCHAR2(100)         -- 결재 제목
    ,CONTENT                 CLOB                   -- 갤재 내용
    ,SHAREDEPARTMENTNO       NUMBER default 0       -- 공유 부서 -- 공유 안하면 0
    ,STATUS                  NUMBER(1) default 0    -- 결재 여부 1이면 결재완료 0이면 미결재
    ,APPROVER                NVARCHAR2(300)         -- 결재자 사원번호
    ,FILENAME                varchar2(255)          -- WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)                                       
    ,ORGFILENAME             varchar2(255)          -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
    ,FILESIZE                number                 -- 파일크기 
    ,DEPARTMENTNAME          NVARCHAR2(10)          -- 부서명
    ,PAPERSNAME              NVARCHAR2(10) default '지출 결의서'-- 문서분류(명)
    ,EMPLOYEENAME            NVARCHAR2(10)               -- 사원명
    ,CONSTRAINT PK_TBL_BO_EXPENDITURENO PRIMARY KEY(EXPENDITURENO)
    ,CONSTRAINT FK_TBL_BO_EXPENDITURE_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

select count(*)
from TBL_BO_EXPENDITURE
where STATUS = 1 and PAPERSNAME = '지출 결의서';

select count(*)
from TBL_BO_EXPENDITURE
where STATUS = 1 and PAPERSNAME = '지출 결의서'
and EXPENDITUREDAY between '2020-01-11' and '2020-02-11' 
and DEPARTMENTNAME = '개발1팀';

select *
from TBL_BO_EXPENDITURE;

-- 휴가/휴직 테이블
CREATE TABLE TBL_BO_VACATION (
     VACATIONNO                 NUMBER                      -- 휴가/휴직 테이블
    ,FK_EMPLOYEENO              NUMBER                      -- 참조키 사원번호
    ,WRITEDAY                   DATE DEFAULT SYSDATE        -- 작성일자
    ,STARTDAY                   DATE DEFAULT SYSDATE        -- 시작일
    ,ENDDAY                     DATE DEFAULT SYSDATE + 1    -- 종료일
    ,TITLE                      NVARCHAR2(100)              -- 제목
    ,REASON                     NVARCHAR2(200)              -- 사유
    ,EMERGENCYCONTACTNETWORK    NVARCHAR2(11)               -- 비상 연락망
    ,SHAREDEPARTMENTNO          NUMBER default 0            -- 공유 부서번호 -- 공유 안하면 0
    ,STATUS                     NUMBER(1)default 0          -- 결재 여부 1이면 결재완료, 0이면 결재대기중
    ,APPROVER                   NVARCHAR2(300)              -- 결재자 사원번호
    ,DEPARTMENTNAME             NVARCHAR2(10)               -- 부서명
    ,RANK                       NUMBER(1)                   -- 직급
    ,FILENAME                   varchar2(255)               -- WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)                                       
    ,ORGFILENAME                varchar2(255)               -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
    ,FILESIZE                   number                      -- 파일크기 
    ,PAPERSNAME                 NVARCHAR2(10) default '휴가 신청서'-- 문서분류(명)
    ,EMPLOYEENAME               NVARCHAR2(10)               -- 사원명    
    ,CONSTRAINT PK_TBL_BO_VACATIONNO PRIMARY KEY(VACATIONNO)
    ,CONSTRAINT FK_TBL_BO_VACATION_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------  [ 지출결재 ]

select *
from TBL_BO_EXPENDITURE;

drop table TBL_BO_EXPENDITURE purge;

-- 지출결재 테이블
CREATE TABLE TBL_BO_EXPENDITURE (
    EXPENDITURENO           NUMBER                          -- 지출 결재 번호
    ,FK_EMPLOYEENO           NUMBER                         -- 참조 키 사원번호
    ,WRITEDAY                DATE DEFAULT SYSDATE        -- 결재 작성 일자
    ,EXPENDITUREDAY          DATE DEFAULT SYSDATE    -- 지출 일자
    ,EXPENDITUREMODE         number(1) default 0         -- 지출 종류 -- -- 개인지출 이면 '0' , 법인지출 '1'
    ,TITLE                   NVARCHAR2(100)                      -- 결재 제목
    ,CONTENT                 CLOB                                 -- 갤재 내용
    ,SHAREDEPARTMENTNO       NUMBER default 0        -- 공유 부서 -- 공유 안하면 0
    ,STATUS                  NUMBER(1) default 0               -- 결재 여부 1이면 결재완료 0이면 미결재
    ,APPROVER                NVARCHAR2(300)                  -- 결재자 사원번호
    ,FILENAME                varchar2(255)                         -- WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)                                       
    ,ORGFILENAME             varchar2(255)                      -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
    ,FILESIZE                number                                  -- 파일크기 
    ,DEPARTMENTNAME          NVARCHAR2(10)              -- 부서명
    ,PAPERSNAME              NVARCHAR2(10) default '지출 결의서'-- 문서분류(명)
    ,EMPLOYEENAME            NVARCHAR2(10)                -- 사원명
    ,TEXTNUMBER              NUMBER(10)                      -- 진짜 문서번호 (채번해온 것)
    ,PAYMENT_MONEY           NVARCHAR2(20) not null   -- 금액란
    ,CONSTRAINT PK_TBL_BO_EXPENDITURENO PRIMARY KEY(EXPENDITURENO)
    ,CONSTRAINT FK_TBL_BO_EXPENDITURE_EMPNO FOREIGN KEY(FK_EMPLOYEENO)
               REFERENCES TBL_BO_EMPLOYEES(EMPLOYEENO) ON DELETE CASCADE
);

alter table TBL_BO_EXPENDITURE add APPROVER_NAME NVARCHAR2(10);

-- 지출결재 시퀀스
CREATE SEQUENCE SEQ_BO_EXPENDITURE
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

-- 확인용
select *
from TBL_BO_EXPENDITURE;

-- Test용 데이터 집어넣기
insert into TBL_BO_EXPENDITURE(EXPENDITURENO, FK_EMPLOYEENO, DEPARTMENTNAME, EMPLOYEENAME, PAYMENT_MONEY,  STATUS, WRITEDAY, EXPENDITUREDAY, EXPENDITUREMODE, TITLE, CONTENT, SHAREDEPARTMENTNO, APPROVER, FILENAME, PAPERSNAME, TEXTNUMBER)
values                 (SEQ_BO_EXPENDITURE.nextval, 1,                      1,    '김경훈',       '10000',       1, DEFAULT , DEFAULT       , default                 , '결제', '메로나사먹음', default               , 180          , '111111'  , default       , 18               );

delete from TBL_BO_EXPENDITURE; 


select *
from TBL_BO_EMPLOYEES;

commit;




select EXPENDITURENO ,FK_EMPLOYEENO, DEPARTMENTNAME, FK_POSITIONNO, EMPLOYEENAME, PAYMENT_MONEY, PAPERSNAME, STATUS, EXPENDITUREDAY
		from
		    (
		    select ROWNUM AS RNO
		           ,EXPENDITURENO ,FK_EMPLOYEENO, DEPARTMENTNAME, FK_POSITIONNO, EMPLOYEENAME, PAYMENT_MONEY, PAPERSNAME, STATUS, EXPENDITUREDAY
		    FROM
		        (
		        select A.EXPENDITURENO	 						        AS EXPENDITURENO
		                ,A.FK_EMPLOYEENO   						            AS FK_EMPLOYEENO
		                ,A.DEPARTMENTNAME  						        AS DEPARTMENTNAME
		                ,B.FK_POSITIONNO   						            AS FK_POSITIONNO 
		                ,A.EMPLOYEENAME    						        AS EMPLOYEENAME
		                ,A.PAYMENT_MONEY   						        AS PAYMENT_MONEY
		                ,A.PAPERSNAME      						            AS PAPERSNAME 
		                ,A.STATUS          						                AS STATUS
		                , A.EXPENDITUREDAY	AS EXPENDITUREDAY
		        from TBL_BO_EXPENDITURE A LEFT JOIN TBL_BO_EMPLOYEES B
		        ON A.EMPLOYEENAME = B.NAME
		        where A.STATUS = 1 and A.PAPERSNAME = '지출 결의서'
		        and EXPENDITUREDAY between '2020-01-01' and '2020-02-05'
				and DEPARTMENTNAME = '개발1팀'
		        ) V 
		    ) T
		where RNO between 1 and 10
        
        
        select rno, textnumber, title, employeename, PAPERSNAME
		from
		(
		select rownum as rno, textnumber, title, employeename, PAPERSNAME
		from 
		(
		select textnumber, title, employeename, PAPERSNAME
		   from 
		   ( 
		    select expenditureno as writeno, title, content, textnumber, employeename, PAPERSNAME
		    from tbl_bo_expenditure
		    where status = 0
		    union all            
		    select vacationno, title, reason, textnumber, employeename, PAPERSNAME  
		    from tbl_bo_vacation
		    where status = 0
		   ) T
		   where T.writeno in ( 
		                           select max(no) as writeno  
		                           from  
		                           ( 
		                            select expenditureno as no, title, content as bodycontent, textnumber, employeename, PAPERSNAME
		                            from tbl_bo_expenditure
		                            where status = 0 and APPROVER = #{employeeno}
		                            union all            
		                            select vacationno, title, reason, textnumber, employeename, PAPERSNAME         
		                            from tbl_bo_vacation
		                            where status = 0 and APPROVER = #{employeeno}
		                           ) V
		                           group by V.textnumber
		                       )
		order by textnumber desc
		) W
		order by rno
		) A
		where rno < 5
