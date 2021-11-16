package model;

public class LoginLogic {
	public boolean execute(User user) {
		if(!user.getName().equals("")) { return true; }
		return false;
	}
}
