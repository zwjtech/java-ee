package jpa.relationShip.manyToOne;

import javax.persistence.*;


@Table(name="JPA_ORDERS")
@Entity
public class Order1 {
	private Integer id;
	private String orderName;
	private Customer1 customer1;

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

	//映射单向 n-1 的关联关系，在多的一端写注解
	//使用 @ManyToOne 来映射多对一的关联关系
	//使用 @JoinColumn 来映射外键.外键命名随意 （会在多的那张表上生成一列CUSTOMER_ID）
	//可使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
	@JoinColumn(name="CUSTOMER_ID")
	@ManyToOne(fetch=FetchType.LAZY) //如果用懒加载，则会有两条select语句，先查order,再customer
	public Customer1 getCustomer1() {
		return customer1;
	}
	public void setCustomer1(Customer1 customer1) {
		this.customer1 = customer1;
	}
}

