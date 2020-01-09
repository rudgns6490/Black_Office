package com.bo.archive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArchiveController {

	// === 보고서함 ===
	@RequestMapping(value="/report_archive.action")
	public String report_archive() {
		return "archive/report_archive.tiles1";
	}
	
	
	

	
	//////////////  === 임시보관함 ===  //////////////
	// 임시 일반결재 보관함
	@RequestMapping(value="/temp_archive_normal.action")
	public String temp_archive_normal() {
		return "archive/temporary/normal.tiles1";
	}
	
	// 임시 지출결재 보관함
	@RequestMapping(value="/temp_archive_expenses.action")
	public String temp_archive_expenses() {
		return "archive/temporary/expenses.tiles1";
	}
	
	// 임시 휴가/휴직 결재 보관함
	@RequestMapping(value="/temp_archive_leave.action")
	public String temp_archive_leave() {
		return "archive/temporary/leave.tiles1";
	}
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	// ##### 결재함 #####
	////////////// === 미결재보관함 ===  //////////////
	// === 미결재 일반결재 보관함 ===
	@RequestMapping(value="/incomplete_archive_normal.action")
	public String incomplete_archive_normal() {
		return "archive/incomplete_payment/normal.tiles1";
	}
	
	// === 미결재 지출결재 보관함 ===
	@RequestMapping(value="/incomplete_archive_expenses.action")
	public String incomplete_archive_expenses() {
		return "archive/incomplete_payment/expenses.tiles1";
	}
	
	// === 미결재 휴가/휴직 결재 보관함 ===
	@RequestMapping(value="/incomplete_archive_leave.action")
	public String incomplete_archive_leave() {
		return "archive/incomplete_payment/leave.tiles1";
	}
	
	
	
	
	
	//////////////=== 결재완료 보관함 ===  //////////////
	// === 결재완료 일반결재 보관함 ===
	@RequestMapping(value="/complete_archive_normal.action")
	public String complete_archive_normal() {
		return "archive/complete_payment/normal.tiles1";
	}	
		
	// === 결재완료 지출결재 보관함 ===
	@RequestMapping(value="/complete_archive_expenses.action")
	public String complete_archive_expenses() {
		return "archive/complete_payment/expenses.tiles1";
	}
	
	// === 결재완료 휴가/휴직 결재 보관함 ===
	@RequestMapping(value="/complete_archive_leave.action")
	public String complete_archive_leave() {
		return "archive/complete_payment/leave.tiles1";
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
		
	//////////////=== 내문서함 ===  //////////////
	// === 내문서 일반결재 보관함 ===
	@RequestMapping(value="/mydocument_archive_normal.action")
	public String mydocument_archive_normal() {
		return "archive/mydocument/normal.tiles1";
	}
	// === 내문서 지출결재 보관함 ===
	@RequestMapping(value="/mydocument_archive_expenses.action")
	public String mydocument_archive_expenses() {
		return "archive/mydocument/expenses.tiles1";
	}
	// === 내문서 휴가/휴직 결재 보관함 ===
	@RequestMapping(value="/mydocument_archive_leave.action")
	public String mydocument_archive_leave() {
		return "archive/mydocument/leave.tiles1";
	}
	
	
		
	
	
	
	
	//////////////=== 부서문서함 ===  //////////////
	// === 부서문서 일반결재 보관함 ===
	@RequestMapping(value="/deptdocument_archive_normal.action")
	public String deptdocument_archive_normal() {
		return "archive/deptdocument/normal.tiles1";
	}
	// === 부서문서 지출결재 보관함 ===
	@RequestMapping(value="/deptdocument_archive_expenses.action")
	public String deptdocument_archive_expenses() {
		return "archive/deptdocument/expenses.tiles1";
	}
	// === 부서문서 휴가/휴직 결재 보관함 ===
	@RequestMapping(value="/deptdocument_archive_leave.action")
	public String deptdocument_archive_leave() {
		return "archive/deptdocument/leave.tiles1";
	}
	
	
	
	
	
	
}	// End-----------------------------------------------------------------------------
