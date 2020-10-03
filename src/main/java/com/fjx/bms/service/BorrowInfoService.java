package com.fjx.bms.service;

import java.util.List;

import com.fjx.bms.dao.BorrowInfoDao;
import com.fjx.bms.domain.Borrowinfo;

/**
 * @author 冯金星
 *
 */
public class BorrowInfoService {

	
	BorrowInfoDao dao = new BorrowInfoDao();
	/**
	 * @return
	 */
	public String getMaxId() {
		String id = dao.getMaxId();
		if(id==null) {
			id = "0001";
		}
		return id;
	}
	/**
	 * @param borrowInfo
	 * @return
	 */
	public String save(Borrowinfo borrowInfo) {
		int num = dao.save(borrowInfo);
		if(num>0) {
			return "添加成功";
		}
		return "添加失败";
	}
	/**
	 * @param bookId
	 * @return
	 */
	public String checkBorrow(String bookId) {
		String res = "false";
		int num = dao.checkBorrow(bookId);
		System.out.println(num);
		if(num>0){
			res = "true";
		}
		return res;
	}
	/**
	 * @param i
	 * @param size
	 * @return
	 */
	public List<Borrowinfo> getBorrowinfoList(int start, int size) {
		return dao.getBorrowinfoList(start,size);
	}
	/**
	 * @return
	 */
	public Integer getTotal() {
		return dao.getTotal();
	}
	/**
	 * @param borrowid
	 * @return
	 */
	public String del(String borrowid) {
		int num = dao.del(borrowid);
		String res = "删除失败";
		if(num>0) {
			res = "删除成功";
		}
		return res;
	}
	/**
	 * @param borrowid
	 * @return
	 */
	public String update(String borrowid) {
		int num = dao.update(borrowid);
		String res = "还书失败";
		if(num>0) {
			res = "还书成功";
		}
		return res;
	}
	/**
	 * @param bookId
	 * @return
	 */
	public String checkNotReturn(String bookId) {
		int num = dao.checkNotReturn(bookId);
		if(num>0) {
			return "false";
		}
		return "true";
	}

}
