package com.bo.payment_report.model;

import java.util.HashMap;
import java.util.List;

public interface InterPayment_Report_DAO {

	List<HashMap<String, String>> addAprrovalModal(); // Modal ajax로 부서 넣어주기

	List<HashMap<String, String>> addModalSelect(String departmentname); // Modal ajax로 넣어준 부서에 대한 사원명단 select

}
