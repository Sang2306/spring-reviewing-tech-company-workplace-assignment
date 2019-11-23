package app.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import app.helper.Helper;
import app.model.Company;

@Transactional
@Controller
@RequestMapping("/home/")
public class HomeController {

	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "index")
	public String homePage(ModelMap model, HttpServletRequest request) {
		// kiem tra trong session co thong tin 	dang nhap chua
		HttpSession httpSession = request.getSession(true);
		if (Helper.isLogin(httpSession)) {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("username", username);
			String role = (String) httpSession.getAttribute("role");
			model.addAttribute("role", role);
		} else { //chi khi dang nhap khong thanh cong thi trong session moi co thong tin error
			String error = (String) httpSession.getAttribute("error");
			model.addAttribute("error", error);
		}

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Company");
		@SuppressWarnings("unchecked")
		List<Company> companies = query.list();
		model.addAttribute("companies", companies);
		if (companies.size() == 0) {
			model.addAttribute("message", "Không có thông tin nào để hiển thị");
			return "index";
		}
		// loc lay thong tin quoc gia
		Set<String> countries = new HashSet<String>();
		Map<String, Integer> companies_comments = new HashMap<String, Integer>();
		for (Company com : companies) {
			// add country to countries set
			countries.add(com.getCountry());
			// count number comments of the company
			String sql = "SELECT COUNT(*) FROM Comments WHERE companyID = :companyID";
			SQLQuery countQuery = session.createSQLQuery(sql);
			countQuery.setParameter("companyID", com.getCompany_id());
			//add to map with key = company name and value = number of it's commnents
			companies_comments.put(com.getName(), (int)countQuery.uniqueResult());
		}
		model.addAttribute("countries", countries);
		model.addAttribute("companies_comments", companies_comments);
		return "index";
	}

}
