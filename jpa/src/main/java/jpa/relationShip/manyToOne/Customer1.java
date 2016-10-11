package jpa.relationShip.manyToOne;

import javax.persistence.*;
import java.util.Date;

@Table(name="JPA_CUTOMERS")
@Entity
public class Customer1 {

	private Integer id;
	private String lastName;

	private String email;
	private int age;
	
	private Date createdTime;
	private Date birth;

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
	
	//注解@Temporal 调整时间精度 2015-11-11 10:11:11
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	//2015-11-11
	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}

}
