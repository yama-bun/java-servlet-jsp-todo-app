package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemoDAO;
import model.GetMemoListLogic;
import model.Memo;

/**
 * Servlet implementation class UpdateMemo
 */
@WebServlet("/UpdateMemo")
public class UpdateMemo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//取得したidで更新処理
		int id = Integer.parseInt(request.getParameter("id"));
		MemoDAO dao = new MemoDAO();
		dao.update(id);
		
		//メモリストをリクエストスコープから取得
		GetMemoListLogic getMemoListLogic = new GetMemoListLogic();
		List<Memo> memoList = getMemoListLogic.execute();
		request.setAttribute("memoList", memoList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/todo.jsp");
		dispatcher.forward(request, response);
	}

}
