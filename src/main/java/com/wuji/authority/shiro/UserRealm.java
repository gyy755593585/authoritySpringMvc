package com.wuji.authority.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.wuji.authority.model.Permit;
import com.wuji.authority.model.Role;
import com.wuji.authority.model.User;
import com.wuji.authority.service.PermitService;
import com.wuji.authority.service.RoleService;
import com.wuji.authority.service.UserService;
import com.wuji.authority.util.GlobalConstant;
import com.wuji.authority.vo.ActivityUser;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermitService permitService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ActivityUser activityUser = (ActivityUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(activityUser.getRoles());
		authorizationInfo.setStringPermissions(activityUser.getPermitCodes());
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();

		User loginUser = this.userService.findByUserName(username);

		if (loginUser == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		if (Boolean.TRUE.equals(loginUser.getStatus())) {
			throw new LockedAccountException(); // 帐号锁定
		}

		// 放入Session中用户对象
		ActivityUser activityUser = new ActivityUser(username);
		activityUser.setName(loginUser.getNickName());
		activityUser.setId(loginUser.getId());
		List<Role> roleList = this.roleService.findRoleByUserName(username);
		// 用户所有角色名
		Set<String> roles = new HashSet<>();
		// 用户允许被访问的URL
		Set<String> permits = new HashSet<>();
		for (Role role : roleList) {
			// 判断角色是否为admin
			if (role.getType() == 1) {
				activityUser.setAdmin(true);
				// break;
			}
			// 根据角色获取用户系统和对应的权限
			List<Permit> permitList = this.permitService.findPermitByRoleId(role.getId());
			for (Permit systemPermit2 : permitList) {
				String permitCode = systemPermit2.getPermitCode();
				permits.add(permitCode);

			}
			roles.add(role.getRoleName());
		}
		if (activityUser.isAdmin()) {

		}
		activityUser.setPermitCodes(permits);
		activityUser.setRoles(roles);
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(activityUser, // 用户名
				loginUser.getPassword(), // 密码
				ByteSource.Util.bytes(loginUser.getSalt()), // salt=username+salt
				this.getName() // realm name
		);
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute(GlobalConstant.ACTIVITY_USER, activityUser);
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		this.getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		this.getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		this.getCacheManager().getCache("passwordRetryCache").clear();
		this.clearAllCachedAuthenticationInfo();
		this.clearAllCachedAuthorizationInfo();
	}
}
