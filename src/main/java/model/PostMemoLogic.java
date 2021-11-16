package model;

import dao.MemoDAO;

public class PostMemoLogic {
	public void execute(Memo memo) {
		MemoDAO dao = new MemoDAO();
		dao.create(memo);
	}
}
