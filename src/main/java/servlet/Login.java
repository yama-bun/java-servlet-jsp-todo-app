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
import javax.servlet.http.HttpSession;

import model.GetMemoListLogic;
import model.LoginLogic;
import model.Memo;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//リクエストパラメーター取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		//メモリストをリクエストスコープから取得
		GetMemoListLogic getMemoListLogic = new GetMemoListLogic();
		List<Memo> memoList = getMemoListLogic.execute();
		request.setAttribute("memoList", memoList);
		//取得できない場合、メモリストを新規作成して保存
		if(memoList == null) {
			memoList = new ArrayList<>();
			request.setAttribute("memoList", memoList);
		}
		//Userインスタンス生成
		User user = new User(name);
		
		//ログイン処理
		LoginLogic loginLogic = new LoginLogic();
		boolean isLogin = loginLogic.execute(user);
		
		//ログイン成功時
		if(isLogin) {
			//ユーザー情報セッションに保存
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			session.setAttribute("memoList", memoList);
			
		}
		
		//ToDo画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/todo.jsp");
		dispatcher.forward(request, response);
		
	}

}
