package Spring4_JDBC.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBCTest
 *
 * @author Lcw 2015/11/13
 */
public class JDBCTest {
    private JdbcTemplate jdbcTemplate;
    private ApplicationContext ctx;

    {
        ctx = new ClassPathXmlApplicationContext("Spring4_JDBC/applicationContext.xml");
        jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
    }

    //测试数据库连接
    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    /**
     * 批量insert, update, delete
     * 最后一个参数是 Object[] 的 List 类型: 因为修改一条记录需要一个 Object 的数组,
     * 那么多条不就需要多个 Object 的数组吗
     */
    @Test
    public void batchUpdate() {
        String sql1 = "insert into Employee (lastName, email,dept_id)" +
                "VALUES (?,?,?)";
        List<Object[] > batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[]{"AA","AA@163.com",1});
        batchArgs.add(new Object[]{"BB","BB@163.com",2});

        jdbcTemplate.batchUpdate(sql1, batchArgs);
    }
    /**
     * 执行insert, update, delete
     */
    @Test
    public void testUpdate() {
        String sql = "UPDATE employees SET last_name = ? WHERE id = ?";
        jdbcTemplate.update(sql, "Jack", 1);

        String sql2 = "UPDATE examstudent SET Grade = ? where flow_id = ?";
        jdbcTemplate.update(sql2, "80", 5);
    }

    /**
     * 从数据库中获取一条记录, 实际得到对应的一个对象
     * 注意不是调用 queryForObject(String sql, Class<Employee> requiredType, Object... args) 方法!
     * 而需要调用 queryForObject(String sql, RowMapper<Employee> rowMapper, Object... args)
     * 1. 其中的 RowMapper 指定如何去映射结果集的行, 常用的实现类为 BeanPropertyRowMapper
     * 2. 使用 SQL 中列的别名完成列名和类的属性名的映射. 例如 last_name lastName
     * 3. 不支持级联属性. JdbcTemplate 到底是一个 JDBC 的小工具, 而不是 ORM 框架!!!
     */
    @Test
    public void testQueryForObject() {
        String sql = "SELECT id, lastname , email, dept_id as \"department.id\" FROM employee WHERE id = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 1);

        System.out.println(employee);
    }

    /**
     * 查到实体类的集合
     * 注意调用的不是 queryForList 方法
     */
    @Test
    public void testQueryForList(){
        String sql = "SELECT id, lastname , email FROM employee WHERE id > ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
        List<Employee> employees = jdbcTemplate.query(sql, rowMapper,1);

        System.out.println(employees);
    }

    /**
     * 获取单个列的值, 或做统计查询
     * 使用 queryForObject(String sql, Class<Long> requiredType)
     */
    @Test
    public void testQueryForObject2(){
        String sql = "SELECT count(id) FROM employee";
        long count = jdbcTemplate.queryForObject(sql, Long.class);

        System.out.println(count);
    }

}
