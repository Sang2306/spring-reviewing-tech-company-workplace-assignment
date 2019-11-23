package app.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Helper {
	public static boolean isLogin(HttpSession session) {
		// kiem tra thong tin co nam trong session khong neu co thi da dang nhap thanh
		// cong nguoc lai la chua dang nhap
		if (session.getAttribute("username") != null && session.getAttribute("password") != null) {
			return true;
		}
		return false;
	}
	//lay gia tri thuoc tinh trong session
	public static String getFromSession(HttpServletRequest request, String attribute) {
		HttpSession httpSession = request.getSession(true);
		String value = (String) httpSession.getAttribute(attribute);
		return value;
	}
}
