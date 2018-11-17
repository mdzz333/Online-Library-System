package controller.librarian;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BorrowCartDAO;
import entity.Cart;

/**
 * Servlet implementation class ShowCart
 */
public class ShowCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int start = 0;//起始行
		int count = 2;//每页显示行数

		try {
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			// 当浏览器没有传参数start时
		}

		int next = start + count;//下一页
		int pre = start - count;//前一页
		BorrowCartDAO bDAO = new BorrowCartDAO();
		int total = bDAO.getTotal();//获取borrow_cart中的总数

		//最后一页的起始行
		int last;
		if (0 == total % count)//每一页都能展示最大行数
			last = total - count;
		else
			last = total - total % count;

		pre = pre < 0 ? 0 : pre;
		next = next > last ? last : next;

		request.setAttribute("next", next);
		request.setAttribute("pre", pre);
		request.setAttribute("last", last);
		List<Cart> list=bDAO.getAllBorrowCartOrderByTime(start, count);
		request.setAttribute("cartList", list);
		request.getRequestDispatcher("showCart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}