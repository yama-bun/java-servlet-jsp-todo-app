package model;

import java.util.List;

import dao.MemoDAO;

public class GetMemoListLogic {
	public List<Memo> execute() {
		MemoDAO dao = new MemoDAO();
		List<Memo> memoList = dao.findAll();
		return memoList;
	}
}
