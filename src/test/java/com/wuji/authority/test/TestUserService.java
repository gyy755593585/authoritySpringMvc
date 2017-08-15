/**
 *
 */
package com.wuji.authority.test;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wuji.authority.config.MyDaoConfig;
import com.wuji.authority.model.Permit;
import com.wuji.authority.model.Role;
import com.wuji.authority.model.User;
import com.wuji.authority.service.PermitService;
import com.wuji.authority.service.RoleService;
import com.wuji.authority.service.UserService;
import com.wuji.basic.model.SystemRequest;
import com.wuji.basic.model.SystemRequestHolder;

/**
 * @author Yayun
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MyDaoConfig.class })
public class TestUserService {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermitService permitService;

	@Before
	public void test() {
		SystemRequest systemRequest = new SystemRequest();
		systemRequest.setCurrentUser("admin");
		SystemRequestHolder.initRequestHolder(systemRequest);
	}

	@Test
	public void testUserAdd() throws NoSuchAlgorithmException {
		User user = new User();
		user.setUserName("admin");
		user.setNickName("管理员");
		user.setStatus(0);
		user.setType(1);
		user.setPassword("test");
		this.userService.add(user);
	}

	@Test
	public void testAddRole() {
		Role role = new Role();
		role.setRoleName("admin");
		role.setType(1);
		this.roleService.add(role);
	}

	@Test
	public void testAddRoleForUser() {
		User user = this.userService.findByUserName("admin");
		Role role = this.roleService.findAll().get(0);
		this.roleService.addRoleForUser(user, role);
	}

	@Test
	public void testAddPermit() {
		Permit permit = new Permit();
		permit.setPermitCode("userAction!getUserList");
		permit.setPermitName("用户查看");
		this.permitService.add(permit);
	}

	@Test
	public void testAddPeritForRole() {
		Permit permit = this.permitService.load(4L);
		Role role = this.roleService.load(2L);
		this.permitService.addPermitForRole(role, permit);
	}
}
