package app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Companies")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int company_id;

	@Column(name = "addrr")
	private String addr;

	private String name;

	private String industryField;

	@Column(name = "num_employee")
	private int num_employee;

	@Column(name = "country")
	private String country;

	@Column(name = "photoPath")
	private String photoPath;

	@OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
	private List<Comment> comments;

	public Company() {
		super();
	}

	public Company(int company_id, String addr, int num_employee, String country, String photoPath,
			List<Comment> comments) {
		super();
		this.company_id = company_id;
		this.addr = addr;
		this.num_employee = num_employee;
		this.country = country;
		this.photoPath = photoPath;
		this.comments = comments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getId() {
		return company_id;
	}

	public void setId(int company_id) {
		this.company_id = company_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getNum_employee() {
		return num_employee;
	}

	public void setNum_employee(int num_employee) {
		this.num_employee = num_employee;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getIndustryField() {
		return industryField;
	}

	public void setIndustryField(String industryField) {
		this.industryField = industryField;
	}

}
