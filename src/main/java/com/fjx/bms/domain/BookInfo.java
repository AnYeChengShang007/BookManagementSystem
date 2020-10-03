package com.fjx.bms.domain;

/**
 * @author 冯金星
 *
 */
public class BookInfo {

	private String bookid;
	private String bookname;
	private String publisher;
	private String author;
	private Integer booktype;
	private Integer remain;

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getBooktype() {
		return booktype;
	}

	public void setBooktype(Integer booktype) {
		this.booktype = booktype;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	@Override
	public String toString() {
		return "BookInfo [bookid=" + bookid + ", bookname=" + bookname + ", publisher=" + publisher + ", author="
				+ author + ", booktype=" + booktype + ", remain=" + remain + "]";
	}

}
