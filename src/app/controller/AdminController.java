package app.controller;

import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.helper.Helper;

@Transactional
@Controller
@RequestMapping(value = "/admin/")
public class AdminController {

	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletRequest request) {

		HttpSession httpSession = request.getSession(true);
		if (!Helper.isLogin(httpSession)) {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Admin a WHERE a.username = :username AND a.password = :password";
			Query query = session.createQuery(hql);
			query.setParameter("password", password);
			query.setParameter("username", username);
			// neu dang nhap khong thanh cong
			if (query.uniqueResult() == null) {
				httpSession.setAttribute("error", "Kiểm tra lại thông tin đăng nhập!");
				return "redirect:/home/index.html";
			}
			// gui mail thong bao dang nhap
			trackingLoginMail(request);
			// luu thong tin dang nhap khi dang nhap thanh cong
			httpSession.setAttribute("username", username);
			httpSession.setAttribute("password", password);
			httpSession.removeAttribute("error");
			httpSession.setAttribute("role", "admin");// thiet lap vai tro cua nguoi dang nhap hien tai
		}
		return "redirect:/home/index.html";
	}

	// logout xoa thong tin ra khoi request session
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		HttpSession httpSession = request.getSession(true);
		httpSession.removeAttribute("username");
		httpSession.removeAttribute("password");
		httpSession.removeAttribute("error");
		httpSession.removeAttribute("role");
		return "redirect:/home/index.html";
	}

	@Autowired
	JavaMailSender mailer;

	public void trackingLoginMail(HttpServletRequest request) {
		String from = "n16dccn131@student.ptithcm.edu.vn";
		String to = "sang.tanhle@gmail.com";
		String subject = "CẢNH BÁO ĐĂNG NHẬP";
		String replyTo = from;

		Date now = java.util.Calendar.getInstance().getTime();
		String body = "<img src='cid:email-logo' style='float:left;width:200px;height:150px;'/> <br>"
				+ "<strong> Phát hiện phiên đăng nhập lúc " + now.toString() + " từ " + request.getHeader("User-Agent") + "</strong>"
				+ "<hr>" + request.getHeader("referer");
		try {
			//thiet lap mail
			MimeMessage mail = mailer.createMimeMessage();
			//su dung lop helper
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			//gửi mail
			helper.setFrom(from);
			helper.setTo(to);
			helper.setReplyTo(replyTo);
			helper.setSubject(subject);
			helper.setText(body, true);
			// gui hinh anh
			String filename = "D:/eclipse-workspace/ReviewingTechCompanyWorkplace/WebContent/files/company/email-logo.png";
			FileSystemResource res = new FileSystemResource(new File(filename));
			System.out.println(res.exists());
			helper.addInline("email-logo", res);
			
			mailer.send(mail);
		} catch (Exception e) {
			Logger.getLogger("mail").log(Level.INFO, "Không thể gủi mail cảnh báo " + e.getMessage());
		}
	}
}
