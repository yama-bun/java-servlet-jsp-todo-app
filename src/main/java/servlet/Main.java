package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GetMemoListLogic;
import model.Memo;
import model.PostMemoLogic;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//メモリストをリクエストスコープから取得
		GetMemoListLogic getMemoListLogic = new GetMemoListLogic();
		List<Memo> memoList = getMemoListLogic.execute();
		request.setAttribute("memoList", memoList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/todo.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//メモリストをリクエストスコープから取得
		GetMemoListLogic getMemoListLogic = new GetMemoListLogic();
		List<Memo> memoList = getMemoListLogic.execute();
		request.setAttribute("memoList", memoList);
		//取得できない場合、メモリストを新規作成して保存
		if(memoList == null) {
			memoList = new ArrayList<>();
			request.setAttribute("memoList", memoList);
		}
		//リクエストパラメーター取得
		request.setCharacterEncoding("UTF-8");
		String content = request.getParameter("content");
		String day = request.getParameter("day");
		boolean done = false;
		//入力チェック
		if(content != null && content.length() != 0 && day != null && day.length() != 0) {
			//メモをリストに追加
			Memo memo = new Memo(content, day, done);
			PostMemoLogic postMemoLogic = new PostMemoLogic();
			postMemoLogic.execute(memo);
			request.setAttribute("memoList", memoList);
		} else {
			//未入力項目があればメッセージを作成
			request.setAttribute("errorMsg", "未入力項目があります。");
		}
		
//		response.sendRedirect("");
		//todo画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/todo.jsp");
		dispatcher.forward(request, response);
	}

}
