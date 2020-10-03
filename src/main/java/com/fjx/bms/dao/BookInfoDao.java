package com.fjx.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.fjx.bms.domain.BookInfo;
import com.fjx.bms.util.DataSourceUtil;

/**
 * @author 冯金星
 *
 */
public class BookInfoDao {

	/**
	 * @param bookName
	 * @param bookType
	 * @return
	 */
	public List<BookInfo> getBookInfoList(int start, int size, String bookType, String bookName) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		List<BookInfo> res = new ArrayList<BookInfo>();
		ResultSet rs = null;

		try {
			int num = 1;
			connection = DataSourceUtil.getConnection();
			String sql = "select * from bookinfo where 1=1";
			if (!StringUtils.isEmpty(bookType)) {
				sql += " and booktype=? ";
			}
			if (!StringUtils.isEmpty(bookName)) {
				sql += " and bookname like concat(\'%\',?,\'%\') ";
			}
			sql += " limit ?,? ";
			prepareStatement = connection.prepareStatement(sql);
			if (!StringUtils.isEmpty(bookType)) {
				prepareStatement.setObject(num++, bookType);
			}
			if (!StringUtils.isEmpty(bookName)) {
				prepareStatement.setObject(num++, bookName);
			}
			prepareStatement.setObject(num++, start);
			prepareStatement.setObject(num++, size);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				BookInfo bookInfo = new BookInfo();
				bookInfo.setBookid((String) rs.getObject("bookid"));
				bookInfo.setAuthor((String) rs.getObject("author"));
				bookInfo.setBookname((String) rs.getObject("bookname"));
				bookInfo.setBooktype((Integer) rs.getObject("booktype"));
				bookInfo.setPublisher((String) rs.getObject("publisher"));
				bookInfo.setRemain((Integer) rs.getObject("remain"));
				res.add(bookInfo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param bookName
	 * @param bookType
	 * @return
	 */
	public Integer getTotal(String bookType, String bookName) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		Integer res = 0;
		ResultSet rs = null;

		try {
			int num = 1;
			connection = DataSourceUtil.getConnection();
			String sql = "select count(1) total from bookinfo where 1=1";
			if (!StringUtils.isEmpty(bookType)) {
				sql += " and booktype=? ";
			}
			if (!StringUtils.isEmpty(bookName)) {
				sql += " and bookname like concat(\'%\',?,\'%\') ";
			}
			prepareStatement = connection.prepareStatement(sql);
			if (!StringUtils.isEmpty(bookType)) {
				prepareStatement.setObject(num++, bookType);
			}
			if (!StringUtils.isEmpty(bookName)) {
				prepareStatement.setObject(num++, bookName);
			}
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
	 * @param bookInfo
	 * @return
	 */
	public int save(BookInfo bookInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "insert into bookinfo(bookid,bookname,publisher,author,booktype,remain)"
					+ " values(?,?,?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, bookInfo.getBookid());
			prepareStatement.setObject(2, bookInfo.getBookname());
			prepareStatement.setObject(3, bookInfo.getPublisher());
			prepareStatement.setObject(4, bookInfo.getAuthor());
			prepareStatement.setObject(5, bookInfo.getBooktype());
			prepareStatement.setObject(6, bookInfo.getRemain());
			res = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param bookName
	 * @param author
	 * @return
	 */
	public boolean checkBookName(String bookName, String author) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		boolean res = true;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select * from bookinfo where bookname=? and author=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, bookName);
			prepareStatement.setObject(2, author);
			rs = prepareStatement.executeQuery();
			if(rs.next()) {
				res = false;
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
	public BookInfo getOne(String bookId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		BookInfo res = new BookInfo();
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select * from bookinfo where bookid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, bookId);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				res.setBookid((String) rs.getObject("bookid"));
				res.setAuthor((String) rs.getObject("author"));
				res.setBookname((String) rs.getObject("bookname"));
				res.setBooktype((Integer) rs.getObject("booktype"));
				res.setPublisher((String) rs.getObject("publisher"));
				res.setRemain((Integer) rs.getObject("remain"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param bookInfo
	 * @return
	 */
	public int update(BookInfo bookInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "update bookinfo set booktype=?,publisher=? where bookid=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, bookInfo.getBooktype());
			prepareStatement.setObject(2, bookInfo.getPublisher());
			prepareStatement.setObject(3, bookInfo.getBookid());
			res = prepareStatement.executeUpdate();
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
	public int del(String bookId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			connection.setAutoCommit(false);
			String sql = "delete from bookinfo where bookid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, bookId);
			res = prepareStatement.executeUpdate();
			prepareStatement.close();
			sql = "delete from borrowinfo where bookid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, bookId);
			res = prepareStatement.executeUpdate();
			res = 1;
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

}
