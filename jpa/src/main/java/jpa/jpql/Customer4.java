package jpa.jpql;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NamedQuery(name="testNamedQuery", query="select c FROM Customer4 c WHERE c.id = ?")
@Table(name="JPA_CUTOMERS")
@Entity
public class Customer4 {

	private Integer id;
	private String lastName;
	private String email;
	private int age;
	private Date createdTime;
	private Date birth;
	
	private Set<Order4> orders = new HashSet<Order4>();
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 注解@Column 当实体的属性与其映射的数据库表的列不同名时需要使用
	 * nullable=false不能为空
	 */
	@Column(name="LAST_NAME",length=50,nullable=false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//如果 列名跟字段一样如列名是email，则可以不用写，相关加了@Basic，但字段的属性都是默认的
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	//注解@Temporal 调整时间精度 4015-11-11 10:11:11
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	//4015-11-11
	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	//映射单向 1-n 的关联关系
	//使用 @OneToMany 来映射 1-n 的关联关系
	//使用 @JoinColumn 来映射外键列的名称(在多的那张表JPA_ORDERS!!!!生成一列CUSTOMER_ID
	//可以使用 @OneToMany 的 fetch 属性来修改默认的加载策略,如果是eager，则输出的sql语句是左外连接
	
	//可以通过 @OneToMany 的 cascade 属性来修改默认的删除策略. remove会在删除一的一端的同时，也会把多的一端删除
	//注意: 若在 1 的一端的 @OneToMany 中使用 mappedBy 属性, 则 @OneToMany 端就不能再使用 @JoinColumn 属性了.
	//并且mappedBy会让一的这一端放弃维护，而由多的一端维护（mappedBy里的会值是Order4里的字段customer4）
//	@JoinColumn(name="CUSTOMER_ID")
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.REMOVE}, mappedBy="customer4")
	public Set<Order4> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order4> orders) {
		this.orders = orders;
	}

}
