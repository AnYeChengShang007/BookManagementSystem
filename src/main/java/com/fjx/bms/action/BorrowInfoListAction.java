package com.fjx.bms.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fjx.bms.domain.Borrowinfo;
import com.fjx.bms.service.BorrowInfoService;

/**
 * 
 * @author 冯金星
 *
 */
@WebServlet("/borrowinfolist")
public class BorrowInfoListAction extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BorrowInfoService service = new BorrowInfoService();
		String method = request.getParameter("method");
		if (!StringUtils.isEmpty(method) && method.equals("getmaxid")) {
			String id = service.getMaxId();
			response.getWriter().write(id);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("del")) {
			String borrowid = request.getParameter("borrowid");
			String res = service.del(borrowid);
			response.getWriter().write(res);
			return;
		}
		if (!StringUtils.isEmpty(method) && method.equals("save")) {
			String data = request.getParameter("data");
			Borrowinfo borrowInfo = JSON.parseObject(data,Borrowinfo.class);
			String res = service.save(borrowInfo);
			response.getWriter().write(res);
			return;
		}
		if (!StringUtils.isEmpty(method) && method.equals("checkborrow")) {
			String bookId = request.getParameter("bookid");
			String res = service.checkNotReturn(bookId);
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("update")) {
			String borrowid = request.getParameter("borrowid");
			String res = service.update(borrowid);
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("checknumber")) {
			String bookId = request.getParameter("bookid");
			String res = service.checkBorrow(bookId);
			response.getWriter().write(res);
			return;
		}
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		int start = Integer.parseInt(pageIndex);
		int size = Integer.parseInt(pageSize);
		List<Borrowinfo> list = service.getBorrowinfoList(start*size,size);
		Map<String, Object> map = new HashMap<String, Object>();
		Integer total = service.getTotal();
		map.put("data", list);
		map.put("total", total);
		String res = JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd hh:mm:ss");
		response.getWriter().write(res);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
