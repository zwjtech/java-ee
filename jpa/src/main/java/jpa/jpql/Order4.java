package jpa.jpql;

import javax.persistence.*;


@Table(name="JPA_ORDERS")
@Entity
public class Order4 {

	private Integer id;
	private String orderName;

	private Customer4 customer4;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ORDER_NAME")
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	//映射单向 n-4 的关联关系，在多的一端写注解
	//使用 @ManyToOne 来映射多对一的关联关系
	//使用 @JoinColumn 来映射外键.外键命名随意 （会在多的那张表上生成一列CUSTOMER_ID）
	//可使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
	@JoinColumn(name="CUSTOMER_ID") //这个name要与Customer4类里的oneToMany一样！！！
	@ManyToOne(fetch=FetchType.LAZY) //如果用懒加载，则会有两条select语句，先查order,再customer
	public Customer4 getCustomer4() {
		return customer4;
	}

	public void setCustomer4(Customer4 customer4) {
		this.customer4 = customer4;
	}

}


