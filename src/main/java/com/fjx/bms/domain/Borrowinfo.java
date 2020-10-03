package com.fjx.bms.domain;

import java.util.Date;

/**
 * @author 冯金星
 *
 */
public class Borrowinfo {

	private String borrowid;
	private String bookid;
	private String borrower;
	private String phone;
	private Date borrowtime;
	private Date returntime;
	private String bookname;
	
	

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getBorrowid() {
		return borrowid;
	}

	public void setBorrowid(String borrowid) {
		this.borrowid = borrowid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBorrowtime() {
		return borrowtime;
	}

	public void setBorrowtime(Date borrowtime) {
		this.borrowtime = borrowtime;
	}

	public Date getReturntime() {
		return returntime;
	}

	public void setReturntime(Date returntime) {
		this.returntime = returntime;
	}

	@Override
	public String toString() {
		return "Borrowinfo [borrowid=" + borrowid + ", bookid=" + bookid + ", borrower=" + borrower + ", phone=" + phone
				+ ", borrowtime=" + borrowtime + ", returntime=" + returntime + ", bookname=" + bookname + "]";
	}

	
}
