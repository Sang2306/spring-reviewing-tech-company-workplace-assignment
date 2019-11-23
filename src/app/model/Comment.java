package app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Comments")
public class Comment {
	@Id
	@Column(name = "comment_id")
	private Date comment_id;

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "companyID", nullable = false)
	private Company company;

	@ManyToOne
	@JoinColumn(name = "reviewerID", nullable = false)
	private Reviewer reviewer;

	public Date getComment_id() {
		return comment_id;
	}

	public void setComment_id(Date comment_id) {
		this.comment_id = comment_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public void setReviewer(Reviewer reviewer) {
		this.reviewer = reviewer;
	}

}
