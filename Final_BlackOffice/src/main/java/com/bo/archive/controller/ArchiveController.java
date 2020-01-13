package com.bo.archive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArchiveController {

	// === 내 보고서함 ===
	@RequestMapping(value="/my_report_archive.action")
	public String my_report_archive() {
		return "archive/report/my_report.tiles1";
	}
	
	// === 보고서 수신함 ===
	@RequestMapping(value="/receive_report_archive.action")
	public String receive_report_archive() {
		return "archive/report/receive_report.tiles1";
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	////////////// === 결재문서함 ===  //////////////
	// === 미결재 문서 ===
	@RequestMapping(value="/incomplete_payment_archive.action")
	public String incomplete_payment_archive() {
		return "archive/payment/incomplete.tiles1";
	}
	// === 결재완료 문서 ===
	@RequestMapping(value="/complete_payment_archive.action")
	public String complete_payment_archive() {
		return "archive/payment/complete.tiles1";
	}
	
		
	// === 결재문서 수신함  ===
	@RequestMapping(value="/receive_payment_archive.action")
	public String receive_payment_archive() {
		return "archive/payment/receive_payment.tiles1";
	}
	
	
	
	
	
	
}	// End-----------------------------------------------------------------------------
