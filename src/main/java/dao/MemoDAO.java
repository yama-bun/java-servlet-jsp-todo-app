package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Memo;

public class MemoDAO {
	//データベース接続情報
	private final String JDBC_URL =
			"jdbc:h2:tcp://localhost/~/mydb";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";
	
	//Memo一覧取得
	public List<Memo> findAll() {
		List<Memo> memoList = new ArrayList<>();
		
		//データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			//SELECT文
			String sql = "SELECT ID, CONTENT, DAY, DONE FROM MEMO";
			PreparedStatement pSmt = conn.prepareStatement(sql);
			//SELECT文を実行、結果取得
			ResultSet rs = pSmt.executeQuery();
			
			//結果内容をMEMOインスタンス設定ArrayListに追加
			while(rs.next()) {
				int id = rs.getInt("ID");
				String content = rs.getString("CONTENT");
				String day = rs.getString("DAY");
				boolean done = rs.getBoolean("DONE");
				Memo memo = new Memo(id, content, day, done);
				memoList.add(memo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return memoList;
	}
	
	//Memo登録処理
	public boolean create(Memo memo) {
		//データベース接続
		try(Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			//INSERT文の準備(idは自動連番、DONEは初期値はfalse)
			String sql = "INSERT INTO MEMO(CONTENT, DAY, DONE) VALUES(?, ?, false)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//INSERT文の？の使用値を設定
			pStmt.setString(1, memo.getContent());
			pStmt.setString(2, memo.getDay());
			
			//INSERT文を実行
			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//Memo削除処理
	public boolean remove (int id) {
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "DELETE FROM MEMO WHERE ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			int result = pStmt.executeUpdate();
			return (result == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	//Memo更新処理
	public boolean update (int id) {
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "UPDATE MEMO SET `DONE`=TRUE WHERE ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			int result = pStmt.executeUpdate();
			return (result == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
