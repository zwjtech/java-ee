package jpa.annotation;

import javax.persistence.*;
import java.util.Date;

//JPA 基本注解:@Entity, @Table, @Id, @GeneratedValue, @Column, @Basic
//           @Transient, @Temporal, 用 table 来生成主键详解

/**
 * 注解@Entity映射的表名和类名一样
 * 注解@Table：当实体类与其映射的数据库表名不同名时使用。其中name，用于指明数据库的表名 
 */
@Table(name="JPA_CUTOMERS")
@Entity
public class Customer {

	private Integer id;
	private String lastName;

	private String email;
	private int age;
	
	private Date createdTime;
	private Date birth;

	public Customer() {
		
	}
	
	
	/**
	 * 注解@Id 标注用于声明一个实体类的属性映射为数据库的主键列
	 * 
	 * 还需知道标注主键的生成策略：注解@GeneratedValue，
	 * 如果只有该注解后面没有参数，默认情况下，JPA 自动选择一个最适合底层数据库的主键生成策略
	 * SqlServer 对应 identity，MySQL 对应 auto increment。
	 * 
	 * IDENTITY：采用数据库 ID自增长的方式来自增主键字段，Oracle 不支持这种方式；
	 * AUTO： JPA自动选择合适的策略，是默认选项；
	 * SEQUENCE：通过序列产生主键，通过 @SequenceGenerator 注解指定序列名，MySql 不支持这种方式
	 * TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。
	 */
//用 table 来生成主键详解:
//将当前主键的值单独保存到一个数据库的表中，主键的值每次都是从指定的表中查询来获得
//这种方法生成主键的策略可以适用于任何数据库，不必担心不同数据库不兼容造成的问题
//	@TableGenerator(name="ID_GENERATOR", --name 属性表示该主键生成的名称，它被引用在@GeneratedValue中设置的generator 值中(即这两个名字要一样）
//			table="jpa_id_generators",   --table 属性表示表生成策略所持久化的表名
//			pkColumnName="PK_NAME",      --pkColumnName 属性的值表示在持久化表中，该主键生成策略所对应键值的名称
//			pkColumnValue="CUSTOMER_ID", --pkColumnValue 属性的值表示在持久化表中，该生成策略所对应的主键（跟pkColumnName属性可以确定唯一的一行，该行有很多列）
//			valueColumnName="PK_VALUE",  --valueColumnName 属性的值表示在持久化表中，该主键当前所生成的值，它的值将会随着每次创建累加，（再加这个属性能确定唯一的那个点）
//			allocationSize=100)          --allocationSize 表示每次主键值增加的大小, 默认值为 50
//	@GeneratedValue(strategy=GenerationType.TABLE,generator="ID_GENERATOR")
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

	//工具方法. 不需要映射为数据表的一列. 如果没有加@Transient，则会出错，因为没有set方法
	@Transient
	public String getInfo(){
		return "lastName: " + lastName + ", email: " + email;
	}

}
