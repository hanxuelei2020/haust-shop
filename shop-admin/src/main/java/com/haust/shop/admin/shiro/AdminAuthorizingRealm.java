package com.haust.shop.admin.shiro;

import com.haust.common.util.bcrypt.BCryptPasswordEncoder;
import com.haust.service.domain.admin.DtsAdmin;
import com.haust.service.service.admin.DtsAdminService;
import com.haust.service.service.admin.DtsPermissionService;
import com.haust.service.service.admin.DtsRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * 授权相关服务-shiro
 * 
 * @author 韩雪磊
 * @since 1.0.0
 */
@Component
public class AdminAuthorizingRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(AdminAuthorizingRealm.class);
	@Autowired
	private DtsAdminService adminService;
	@Autowired
	private DtsRoleService roleService;
	@Autowired
	private DtsPermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}

		DtsAdmin admin = (DtsAdmin) getAvailablePrincipal(principals);
		Integer[] roleIds = admin.getRoleIds();
		Set<String> roles = roleService.queryByIds(roleIds);
		Set<String> permissions = permissionService.queryByRoleIds(roleIds);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());

		if (StringUtils.isEmpty(username)) {
			throw new AccountException("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)) {
			throw new AccountException("密码不能为空");
		}

		List<DtsAdmin> adminList = adminService.findAdmin(username);
		Assert.state(adminList.size() < 2, "同一个用户名存在两个账户");
		if (adminList.size() == 0) {
			logger.error("找不到用户（" + username + "）的帐号信息");
			throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
		}
		DtsAdmin admin = adminList.get(0);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(password, admin.getPassword())) {
			logger.error("找不到用户（" + username + "）的帐号信息");
			throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
		}

		return new SimpleAuthenticationInfo(admin, password, getName());
	}

}
