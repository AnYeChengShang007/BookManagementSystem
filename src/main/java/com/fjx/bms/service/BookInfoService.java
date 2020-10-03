package com.fjx.bms.service;

import java.util.List;

import com.fjx.bms.dao.BookInfoDao;
import com.fjx.bms.domain.BookInfo;

/**
 * @author 冯金星
 *
 */
public class BookInfoService {

	BookInfoDao dao = new BookInfoDao();

	/**
	 * @param bookName
	 * @param bookType
	 * @return
	 */
	public List<BookInfo> getBookInfoList(int start,int size,String bookType, String bookName) {
		List<BookInfo> res = dao.getBookInfoList(start,size,bookType, bookName);
		return res;
	}

	/**
	 * @param bookName
	 * @param bookType
	 * @return
	 */
	public Integer getTotal(String bookType, String bookName) {
		Integer res = dao.getTotal(bookType, bookName);
		return res;
	}

	/**
	 * @param bookInfo
	 * @return
	 */
	public String save(BookInfo bookInfo) {
		int num = dao.save(bookInfo);
		if(num>0) {
			return "添加成功";
		}
		return "添加失败";
	}

	/**
	 * @param bookName
	 * @param author
	 * @return
	 */
	public boolean checkBookName(String bookName, String author) {
		boolean res = true;
		res = dao.checkBookName(bookName,author);
		return res;
	}

	/**
	 * @param bookId
	 * @return
	 */
	public BookInfo getOne(String bookId) {
		return dao.getOne(bookId);
	}

	/**
	 * @param bookInfo
	 * @return
	 */
	public String update(BookInfo bookInfo) {
		int num = dao.update(bookInfo);
		if(num>0) {
			return "修改成功";
		}
		return "修改失败";
	}

	/**
	 * @param bookId
	 * @return
	 */
	public String del(String bookId) {
		int num = dao.del(bookId);
		if(num>0) {
			return "删除成功";
		}
		return "删除失败";
	}

}
