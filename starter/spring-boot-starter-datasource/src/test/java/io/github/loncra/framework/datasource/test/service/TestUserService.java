package io.github.loncra.framework.datasource.test.service;

import io.github.loncra.framework.datasource.test.entity.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试用户服务
 *
 * @author maurice.chen
 */
@Service
public class TestUserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询所有用户（只读操作）
     *
     * @return 用户列表
     */
    @Transactional(readOnly = true)
    public List<TestUser> findAll() {
        String sql = "SELECT id, username, email, age, creation_time as creationTime, update_time as updateTime FROM tb_test_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TestUser.class));
    }

    /**
     * 根据 ID 查询用户（只读操作）
     *
     * @param id 用户 ID
     *
     * @return 用户
     */
    @Transactional(readOnly = true)
    public TestUser findById(Long id) {
        String sql = "SELECT id, username, email, age, creation_time as creationTime, update_time as updateTime FROM tb_test_user WHERE id = ?";
        List<TestUser> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TestUser.class), id);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * 保存用户（写操作）
     *
     * @param user 用户
     *
     * @return 保存的用户 ID
     */
    @Transactional
    public Long save(TestUser user) {
        String sql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getAge());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    /**
     * 更新用户（写操作）
     *
     * @param user 用户
     */
    @Transactional
    public void update(TestUser user) {
        String sql = "UPDATE tb_test_user SET username = ?, email = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getAge(), user.getId());
    }

    /**
     * 删除用户（写操作）
     *
     * @param id 用户 ID
     */
    @Transactional
    public void delete(Long id) {
        String sql = "DELETE FROM tb_test_user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * 无事务的查询操作（应该使用主库）
     *
     * @return 用户数量
     */
    public int count() {
        String sql = "SELECT COUNT(*) FROM tb_test_user";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
