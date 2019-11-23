package app.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.helper.Helper;
import app.model.Comment;
import app.model.Company;
import app.model.Reviewer;

@Controller
@RequestMapping(value = "/comment/")
public class CommentController {
	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "add/{company_id}")
	public String addComment(Model model, @PathVariable("company_id") int company_id, HttpServletRequest request) {

		// kiem tra neu chua dang nhap thi yeu cau nguoi dung dang nhap va tro ve trang chu
		HttpSession httpSession = request.getSession(true);
		if (!Helper.isLogin(httpSession)) {
			model.addAttribute("error", "Bạn cần đăng nhập trước khi thực hiện chức năng này!");
			return "error";
		}

		String content = request.getParameter("comment");
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// lay thong tin reviewer trong session
			String alias = Helper.getFromSession(request, "username");
			Reviewer reviewer = (Reviewer) session.get(Reviewer.class, alias); // alias la PK
			// lay cong ty tu co so du lieu
			Company com = (Company) session.get(Company.class, company_id);
			// tao comment
			Comment cmt = new Comment();
			Date now = java.util.Calendar.getInstance().getTime();
			cmt.setComment_id(now);
			cmt.setContent(content);
			cmt.setCompany(com);
			cmt.setReviewer(reviewer);
			// them vao list comments company va reviewer
			com.getComments().add(cmt);
			reviewer.getComments().add(cmt);

			session.save(cmt);
			session.update(com);
			session.update(reviewer);
			
			// trong truong hop nguoi dung chinh sua binh luan
			// thuc hien xoa binh luan do truoc
			// binh luan da sua duoc xem nhu la binh luan moi
			// tranh truong hop nguoi binh luan sua chua binh luan trong qua khu
			// lay commment cu tu co so du lieu va xoa di
			String commentId = request.getParameter("comment-id");
			String sql = "DELETE FROM Comments WHERE comment_id = :comment_id";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("comment_id", commentId);
			query.executeUpdate();
			

			tx.commit();
		} catch (Exception e) {
			model.addAttribute("error", "Với tư cách là quản trị viên trang bạn không được quyền bình luận về nơi làm việc của công ty khác Chân thành xin lỗi!");
			tx.rollback();
			return "error";
		}
		session.close();
		return "redirect:/company/view/" + company_id + ".html";
	}
	
	@RequestMapping("delete/{commentId}")
	public String delete(Model model, @PathVariable("commentId") String commentId, @RequestParam("companyId") int company_id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		//lay commment ra tu co so du lieu
		String sql = "SELECT * FROM Comments WHERE comment_id = :comment_id";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Comment.class);
		query.setParameter("comment_id", commentId);
		Comment comment = (Comment)query.uniqueResult();
		session.delete(comment);
		tx.commit();
		session.close();
		return "redirect:/company/view/" + company_id + ".html";
	}
}
