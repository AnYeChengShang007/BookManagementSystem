package com.fjx.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.fjx.bms.domain.Borrowinfo;
import com.fjx.bms.util.DataSourceUtil;

/**
 * @author 冯金星
 *
 */
public class BorrowInfoDao {

	/**
	 * @return
	 */
	public String getMaxId() {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String res = null;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select LPAD(max(RIGHT(borrowid,4))+1,4,'0') maxid from borrowinfo";
			prepareStatement = connection.prepareStatement(sql);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				res = rs.getString("maxid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param bookId
	 * @return
	 */
	public int checkBorrow(String bookId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select remain from bookinfo where bookid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, bookId);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param borrowInfo
	 * @return
	 */
	public int save(Borrowinfo borrowInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into borrowinfo(borrowid,bookid,borrower,phone,borrowtime,returntime)"
					+ " values(?,?,?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, borrowInfo.getBorrowid());
			prepareStatement.setObject(2, borrowInfo.getBookid());
			prepareStatement.setObject(3, borrowInfo.getBorrower());
			prepareStatement.setObject(4, borrowInfo.getPhone());
			prepareStatement.setObject(5, borrowInfo.getBorrowtime());
			prepareStatement.setObject(6, borrowInfo.getReturntime());
			prepareStatement.executeUpdate();
			prepareStatement.close();

			sql = "update bookinfo set remain = remain-1 where bookid=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, borrowInfo.getBookid());
			res = prepareStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param start
	 * @param size
	 * @return
	 */
	public List<Borrowinfo> getBorrowinfoList(int start, int size) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		List<Borrowinfo> res = new ArrayList<Borrowinfo>();
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select borrowinfo.*,bookname from borrowinfo,bookinfo where borrowinfo.bookid=bookinfo.bookid order by borrowtime desc limit ?,? ";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, start);
			prepareStatement.setObject(2, size);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				Borrowinfo borrowinfo = new Borrowinfo();
				borrowinfo.setBookid((String) rs.getObject("bookid"));
				borrowinfo.setBorrower((String) rs.getObject("borrower"));
				borrowinfo.setBorrowid((String) rs.getObject("borrowid"));
				borrowinfo.setBorrowtime((Date) rs.getObject("borrowtime"));
				borrowinfo.setPhone((String) rs.getObject("phone"));
				borrowinfo.setReturntime((Date) rs.getObject("returntime"));
				borrowinfo.setBookname((String) rs.getObject("bookname"));
				res.add(borrowinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;

	}

	/**
	 * @return
	 */
	public Integer getTotal() {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		Integer res = 0;
		ResultSet rs = null;

		try {
			int num = 1;
			connection = DataSourceUtil.getConnection();
			String sql = "select count(1) total from borrowinfo";
			prepareStatement = connection.prepareStatement(sql);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				res = rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param borrowid
	 * @return
	 */
	public int del(String borrowid) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "delete from borrowinfo where borrowid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, borrowid);
			res = prepareStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param borrowid
	 * @return
	 */
	public int update(String borrowid) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			connection.setAutoCommit(false);
			String sql = "select bookid from  borrowinfo where borrowid = ? ";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, borrowid);
			rs = prepareStatement.executeQuery();
			String bookid = "";
			if(rs.next()) {
				bookid = rs.getString(1);
			}
			sql = "update borrowinfo set returntime = now() where borrowid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, borrowid);
			res = prepareStatement.executeUpdate();
			prepareStatement.close();
			sql = "update bookinfo set remain = remain+1 where bookid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, bookid);
			res = prepareStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			if(connection!=null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param bookId
	 * @return
	 */
	public int checkNotReturn(String bookId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select borrowinfo.*,bookname from borrowinfo,bookinfo where borrowinfo.bookid=bookinfo.bookid and bookinfo.bookid=? and borrowinfo.returntime is null";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, bookId);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				res = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

}
