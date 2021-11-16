package model;

import java.io.Serializable;

public class Memo implements Serializable {
	private int id;
	private String content;
	private String day;
	private boolean done;
	
	public Memo() {}
	public Memo(int id, String content, String day, boolean done) {
		this.id = id;
		this.content = content;
		this.day = day;
		this.done = done;
	}
	public Memo(String content, String day, boolean done) {
		this.content = content;
		this.day = day;
		this.done = done;
	}
	public int getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	public String getDay() {
		return day;
	}
	public boolean isDone() {
		return done;
	}
	
}
