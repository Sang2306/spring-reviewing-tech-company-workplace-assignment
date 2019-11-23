package app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.model.Company;

@Transactional
@Controller
public class SearchController {

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "search")
	public String seach(ModelMap model, @RequestParam("q") String q, HttpServletRequest request) {
		String country = request.getParameter("country");
		country = country == null ? "" : country;

		Session session = sessionFactory.openSession();
		String sql = "SELECT * FROM Companies as C WHERE C.name LIKE '%"+ q +"%' AND C.country LIKE '%"+ country +"%'";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Company.class);

		List<Company> companies = query.list();
		model.addAttribute("companies", companies);
		model.addAttribute("message", "Kết quả tìm kiếm cho: " + q + " " + country);

		session.close();
		return "search-result";
	}
}
