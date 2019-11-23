package app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.model.Reviewer;

@Controller
@RequestMapping(value = "reviewer")
public class ReviewerController {

	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(Model model, HttpServletRequest request) {

		// lay thong tin tu form dang nhap an danh cua nguoi review
		String alias = request.getParameter("alias");
		String position = request.getParameter("position");
		position = position != "" ? position : "không nói đâu";
		String password = request.getParameter("password");
		String _salary = request.getParameter("salary");
		// tao doi tuong
		Reviewer reviewer = new Reviewer();
		reviewer.setAlias(alias);
		reviewer.setPassword(password);
		reviewer.setPosition(position);
		reviewer.setPassword(password);
		if (_salary != "") {
			int salary = Integer.valueOf(_salary);
			reviewer.setSalary(salary);
		}
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// lay session trong request
			HttpSession httpSession = request.getSession(true);
			// kiem tra su ton tai cua reviewer neu co lay ra , nguoc lai tao moi
			String hql = "FROM Reviewer r WHERE r.alias = :alias AND r.password = :password";
			Query query = session.createQuery(hql);
			query.setParameter("alias", alias);
			query.setParameter("password", password);

			Reviewer reviewerInDB = (Reviewer) query.uniqueResult();
			
			if (reviewerInDB == null) {
				session.save(reviewer);
				tx.commit();
				httpSession.setAttribute("username", alias);
				httpSession.setAttribute("password", password);
			} else {
				// thong tin reviewer va mat khau trong db
				httpSession.setAttribute("username", reviewerInDB.getAlias());
				httpSession.setAttribute("password", reviewerInDB.getPassword());
			}
			httpSession.setAttribute("role", "reviewer"); // vai tro cua nguoi da them hoac dang nhap la reviewer
		} catch (Exception e) {
			model.addAttribute("error", "Người dùng " + alias + " đã tồn tại! Xem lại mật khẩu hoặc đổi bí danh khác");
			tx.rollback();
			return "error";
		} finally {
			session.close();
		}

		return "redirect:/home/index.html";
	}

	// logout xoa thong tin ra khoi session
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		HttpSession httpSession = request.getSession(true);
		httpSession.removeAttribute("username");
		httpSession.removeAttribute("password");
		httpSession.removeAttribute("error");
		httpSession.removeAttribute("role");
		return "redirect:/home/index.html";
	}

}
