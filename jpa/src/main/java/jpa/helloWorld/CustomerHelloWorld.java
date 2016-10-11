package jpa.helloWorld;

import javax.persistence.*;

@Table(name="JPA_CUTOMERS")
@Entity
public class CustomerHelloWorld {

	private Integer id;
	private String lastName;

	private String email;
	private int age;
	

	public CustomerHelloWorld() {
		
	}
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="LAST_NAME",length=50,nullable=false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//如果 列名跟字段一样如列名是email，则可以不用写
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
	

}
