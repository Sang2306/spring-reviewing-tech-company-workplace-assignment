package app.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import app.helper.Helper;
import app.model.Comment;
import app.model.Company;
import app.model.Country;

@Controller
@RequestMapping(value = "company")
public class CompanyController {
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ServletContext context;

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addCompany(Model model, HttpServletRequest request) {
		// kiem tra neu chua dang nhap thi yeu cau nguoi dung dang nhap va tro ve trang
		// chu
		HttpSession httpSession = request.getSession(true);
		if (!Helper.isLogin(httpSession)) {
			model.addAttribute("error", "Bạn cần đăng nhập trước khi thực hiện chức năng này!");
			return "error";
		}
		// Get list of countries in the world
		Session session = sessionFactory.openSession();
		String hql = "FROM Country";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Country> countries = query.list();
		session.close();

		model.addAttribute("countries", countries);
		return "add-company";
	}

	// Add company to the db and redirect to current page
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addCompany(Model model, HttpServletRequest request, @RequestParam("photo") MultipartFile photo) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String industryField = request.getParameter("industryField");
			String country = request.getParameter("country");
			int num_employee = Integer.valueOf(request.getParameter("num_employee"));

			// preparing object
			Company com = new Company();
			com.setName(name);
			com.setAddr(address);
			com.setIndustryField(industryField);
			com.setCountry(country);
			com.setNum_employee(num_employee);

			if (!photo.isEmpty()) {
				byte[] bytes = photo.getBytes();
				Path path = Paths
						.get("D:\\eclipse-workspace\\ReviewingTechCompanyWorkplace\\WebContent\\files\\company\\"
								+ photo.getOriginalFilename());
				Files.write(path, bytes);
				com.setPhotoPath(photo.getOriginalFilename());
			}

			session.save(com);
			tx.commit();
			return "redirect:/home/index.html";
		} catch (Exception e) {
			tx.rollback();
		} finally {
			session.close();
		}

		return "add-company";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view/{id}")
	public String view(Model model, HttpServletRequest request, @PathVariable("id") int id) {
		// kiem tra trong session co thong tin dang nhap chua
		HttpSession httpSession = request.getSession(true);
		model.addAttribute("session", httpSession);
		if (Helper.isLogin(httpSession)) {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("username", username);
			String role = (String) httpSession.getAttribute("role");
			model.addAttribute("role", role);
		} else { // chi khi dang nhap khong thanh cong thi trong session moi co thong tin error
			String error = (String) httpSession.getAttribute("error");
			model.addAttribute("error", error);
		}
		Session session = sessionFactory.openSession();
		String hql = "FROM Company AS c WHERE c.company_id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		Company company = (Company) query.uniqueResult();
		model.addAttribute("company", company);
		// lay cac comment cua company
		hql = "FROM Comment as c WHERE c.company = :company";
		query = session.createQuery(hql);
		query.setParameter("company", company);
		List<Comment> comments = query.list();
		model.addAttribute("comments", comments);
		
		session.close();
		return "view-company";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(Model model, @PathVariable("id") int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// xoa cac binh luan cua cong ty truoc
			String deleteComments = "DELETE FROM Comments WHERE companyID = :companyID";
			SQLQuery sqlComments = session.createSQLQuery(deleteComments);
			sqlComments.setParameter("companyID", id);
			sqlComments.executeUpdate();

			// select cong ty
			String sql = "SELECT * FROM Companies AS C WHERE C.company_id = :id";
			SQLQuery queryCompany = session.createSQLQuery(sql);
			queryCompany.addEntity(Company.class);
			queryCompany.setParameter("id", id);
			Company company = (Company) queryCompany.uniqueResult();

			// xoa logo trong he thong file
			String pathname = "D:\\eclipse-workspace\\ReviewingTechCompanyWorkplace\\WebContent\\files\\company\\"
					+ company.getPhotoPath();
			File file = new File(pathname);
			if (file.delete()) {
				Logger.getLogger("Delete photo").log(Level.INFO, "The photo has been deleted!");
			} else {
				Logger.getLogger("Delete photo").log(Level.WARNING, "The photo is still exist!");
			}

			// xoa cong ty
			sql = "DELETE FROM Companies WHERE company_id = :id";
			queryCompany = session.createSQLQuery(sql);
			queryCompany.addEntity(Company.class);
			queryCompany.setParameter("id", id);
			queryCompany.executeUpdate();

			tx.commit();
		} catch (Exception e) {
			model.addAttribute("error", "Xin lỗi admin! Đã có lỗi xảy ra " + e.toString());
			e.printStackTrace();
			return "error";
		} finally {
			session.close();
		}
		return "redirect:/home/index.html";
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("id") int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "FROM Country";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Country> countries = query.list();
			model.addAttribute("countries", countries);
			Company com = (Company) session.get(Company.class, id);
			model.addAttribute("company", com);
			model.addAttribute("id", id);
		} catch (Exception e) {
			tx.rollback();
		}
		session.close();
		return "edit-company";
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
	public String edit(Model model, @PathVariable("id") int id, HttpServletRequest request,
			@RequestParam("photo") MultipartFile photo) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String industryField = request.getParameter("industryField");
			String country = request.getParameter("country");
			int num_employee = Integer.valueOf(request.getParameter("num_employee"));

			// preparing object
			Company com = (Company) session.get(Company.class, id);
			com.setName(name);
			com.setAddr(address);
			com.setIndustryField(industryField);
			com.setCountry(country);
			com.setNum_employee(num_employee);

			if (!photo.isEmpty()) {
				// xoa logo cu cua cong ty
				String pathname = "D:\\eclipse-workspace\\ReviewingTechCompanyWorkplace\\WebContent\\files\\company\\"
						+ com.getPhotoPath();
				File file = new File(pathname);
				if (file.delete()) {
					Logger.getLogger("Delete photo").log(Level.INFO, "The photo has been deleted!");
				} else {
					Logger.getLogger("Delete photo").log(Level.WARNING, "The photo is still exist!");
				}
				// luu file moi tai vi tri cu va cap nhat duong dan file cho cong ty
				byte[] bytes = photo.getBytes();
				Path path = Paths
						.get("D:\\eclipse-workspace\\ReviewingTechCompanyWorkplace\\WebContent\\files\\company\\"
								+ photo.getOriginalFilename());
				Files.write(path, bytes);
				com.setPhotoPath(photo.getOriginalFilename());
			}

			session.save(com);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			session.close();
		}
		return "redirect:/home/index.html";
	}
}