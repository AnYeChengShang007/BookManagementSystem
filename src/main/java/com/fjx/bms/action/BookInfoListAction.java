package com.fjx.bms.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fjx.bms.domain.BookInfo;
import com.fjx.bms.service.BookInfoService;

/**
 * 
 * @author 冯金星
 *
 */
@WebServlet("/bookinfolist")
public class BookInfoListAction extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BookInfoService service = new BookInfoService();
		String method = request.getParameter("method");

		if (!StringUtils.isEmpty(method) && method.equals("getallbookname")) {
			Integer total = service.getTotal(null, null);
			List<BookInfo> bookInfoList = service.getBookInfoList(0,total,null, null);
			response.getWriter().write(JSON.toJSONString(bookInfoList));
			return;
		}

		if (!StringUtils.isEmpty(method) && method.equals("update")) {
			String res = "修改失败";
			String data = request.getParameter("data");
			if (!StringUtils.isEmpty(data)) {
				BookInfo bookInfo = JSON.parseObject(data, BookInfo.class);
				res = service.update(bookInfo);
			}
			response.getWriter().write(res);
			return;
		}

		if (!StringUtils.isEmpty(method) && method.equals("getone")) {
			String bookId = request.getParameter("bookid");
			if (!StringUtils.isEmpty(bookId)) {
				BookInfo bookInfo = service.getOne(bookId);
				String res = JSON.toJSONString(bookInfo);
				response.getWriter().write(res);
			}
			return;
		}

		if (!StringUtils.isEmpty(method) && method.equals("checkbookname")) {
			String bookName = request.getParameter("bookname");
			String author = request.getParameter("author");
			if (!StringUtils.isEmpty(bookName) && !StringUtils.isEmpty(author)) {
				boolean theSame = service.checkBookName(bookName, author);
				if (theSame) {
					response.getWriter().write("true");
				} else {
					response.getWriter().write("false");
				}
			}
			return;
		}

		if (!StringUtils.isEmpty(method) && method.equals("save")) {
			String res = "添加失败";
			String data = request.getParameter("data");
			if (!StringUtils.isEmpty(data)) {
				BookInfo bookInfo = JSON.parseObject(data, BookInfo.class);
				bookInfo.setBookid(UUID.randomUUID().toString());
				res = service.save(bookInfo);
			}
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("del")) {
			String res = "删除失败";
			String bookId = request.getParameter("bookid");
			res = service.del(bookId);
			response.getWriter().write(res);
			return;
		}

		String bookType = request.getParameter("booktype");
		String bookName = request.getParameter("bookname");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		int start = Integer.parseInt(pageIndex);
		int size = Integer.parseInt(pageSize);
		List<BookInfo> data = service.getBookInfoList(start*size,size,bookType, bookName);
		Map<String, Object> map = new HashMap<String, Object>();
		Integer total = service.getTotal(bookType, bookName);
		map.put("data", data);
		map.put("total", total);
		String res = JSON.toJSONString(map);
		response.getWriter().write(res);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}