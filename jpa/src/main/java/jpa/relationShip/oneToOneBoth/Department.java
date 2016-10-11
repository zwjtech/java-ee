package jpa.relationShip.oneToOneBoth;

import javax.persistence.*;

@Table(name="JPA_DEPARTMENTS")
@Entity
public class Department {

	private Integer id;
	private String deptName;
	
	private Manager mgr;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 基于外键
	 * 使用 @OneToOne 来映射 1-1 关联关系。外键在这张表生成 
	 * 若需要在当前数据表中添加主键则需要使用 @JoinColumn 来进行映射. 注意, 1-1 关联关系, 所以需要添加 unique=true
	 */
	@JoinColumn(name="MGR_ID", unique=true) //在Department表中创建一列作外键，必须有唯一约束，表示由该表维护
	@OneToOne(fetch=FetchType.LAZY) //我们不希望通过左外连接获取其关联的对象，可以设置为懒加载
	public Manager getMgr() {
		return mgr;
	}

	public void setMgr(Manager mgr) {
		this.mgr = mgr;
	}
}
