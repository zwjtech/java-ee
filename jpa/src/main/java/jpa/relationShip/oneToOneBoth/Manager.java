package jpa.relationShip.oneToOneBoth;

import javax.persistence.*;

@Table(name="JPA_MANAGERS")
@Entity
public class Manager {
	private Integer id;
	private String mgrName;
	private Department dept;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="MGR_NAME")
	public String getMgrName() {
		return mgrName;
	}
	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	/**
	 * 基于外键，由对方维护关联关系
	 * 
	 * 对于(不维护!!!)关联关系, 没有外键的一方,
	 *  使用 @OneToOne 来进行映射, 建议设置 mappedBy=true
	 */
	@OneToOne(mappedBy="mgr") //这里的值是Department里的字段 private Manager mgr;
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
}
