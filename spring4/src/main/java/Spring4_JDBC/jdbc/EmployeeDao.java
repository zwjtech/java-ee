package Spring4_JDBC.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * EmployeeDao
 *
 * @author Lcw 2015/11/14
 */
@Repository
public class EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Employee get(Integer id){
        String sql = "SELECT id, last_name lastName, email FROM employees WHERE id = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, id);

        return employee;
    }
}
