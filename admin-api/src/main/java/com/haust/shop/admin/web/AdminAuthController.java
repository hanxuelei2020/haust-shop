package com.haust.shop.admin.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.haust.shop.admin.util.AdminResponseCode;
import com.haust.shop.admin.util.AdminResponseUtil;
import com.haust.shop.admin.util.Permission;
import com.haust.shop.admin.util.PermissionUtil;
import com.haust.shop.admin.util.VerifyCodeUtils;
import com.haust.shop.core.captcha.CaptchaCodeManager;
import com.haust.shop.core.util.Base64;
import com.haust.shop.core.util.JacksonUtil;
import com.haust.shop.core.util.ResponseUtil;
import com.haust.shop.core.util.UUID;
import com.haust.shop.db.domain.DtsAdmin;
import com.haust.shop.db.service.DtsPermissionService;
import com.haust.shop.db.service.DtsRoleService;

@RestController
@RequestMapping("/admin/auth")
@Validated
public class AdminAuthController {
	private static final Logger logger = LoggerFactory.getLogger(AdminAuthController.class);

	@Autowired
	private DtsRoleService roleService;
	@Autowired
	private DtsPermissionService permissionService;

	/*
	 * { username : value, password : value }
	 */
	@PostMapping("/login")
	public Object login(@RequestBody String body) {
		logger.info("【请求开始】系统管理->用户登录,请求参数:body:{}", body);

		String username = JacksonUtil.parseString(body, "username");
		String password = JacksonUtil.parseString(body, "password");
		String code = JacksonUtil.parseString(body, "code");
		String uuid = JacksonUtil.parseString(body, "uuid");

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(uuid)) {
			return ResponseUtil.badArgument();
		}

		//验证码校验
		String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(uuid);
		if (cachedCaptcha == null) {
			logger.error("系统管理->用户登录  错误:{},", AdminResponseCode.AUTH_CAPTCHA_EXPIRED.desc());
			return AdminResponseUtil.fail(AdminResponseCode.AUTH_CAPTCHA_EXPIRED);
		}
		if (!code.equalsIgnoreCase(cachedCaptcha)) {
			logger.error("系统管理->用户登录  错误:{},输入验证码：{},后台验证码：{}", AdminResponseCode.AUTH_CAPTCHA_ERROR.desc(),code,cachedCaptcha);
			return AdminResponseUtil.fail(AdminResponseCode.AUTH_CAPTCHA_ERROR);
		}
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(new UsernamePasswordToken(username, password));
		} catch (UnknownAccountException uae) {
			logger.error("系统管理->用户登录  错误:{}", AdminResponseCode.ADMIN_INVALID_ACCOUNT_OR_PASSWORD.desc());
			return AdminResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT_OR_PASSWORD);
		} catch (LockedAccountException lae) {
			logger.error("系统管理->用户登录 错误:{}", AdminResponseCode.ADMIN_LOCK_ACCOUNT.desc());
			return AdminResponseUtil.fail(AdminResponseCode.ADMIN_LOCK_ACCOUNT);

		} catch (AuthenticationException ae) {
			logger.error("系统管理->用户登录 错误:{}", AdminResponseCode.ADMIN_LOCK_ACCOUNT.desc());
			return AdminResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_AUTH);
		}

		logger.info("【请求结束】系统管理->用户登录,响应结果:{}", JSONObject.toJSONString(currentUser.getSession().getId()));
		return ResponseUtil.ok(currentUser.getSession().getId());
	}

	/*
	 * 用户注销
	 */
	@RequiresAuthentication
	@PostMapping("/logout")
	public Object login() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();

		logger.info("【请求结束】系统管理->用户注销,响应结果:{}", JSONObject.toJSONString(currentUser.getSession().getId()));
		return ResponseUtil.ok();
	}

	@RequiresAuthentication
	@GetMapping("/info")
	public Object info() {
		Subject currentUser = SecurityUtils.getSubject();
		DtsAdmin admin = (DtsAdmin) currentUser.getPrincipal();

		Map<String, Object> data = new HashMap<>();
		data.put("name", admin.getUsername());
		data.put("avatar", admin.getAvatar());

		Integer[] roleIds = admin.getRoleIds();
		Set<String> roles = roleService.queryByIds(roleIds);
		Set<String> permissions = permissionService.queryByRoleIds(roleIds);
		data.put("roles", roles);
		// NOTE
		// 这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
		data.put("perms", toAPI(permissions));

		logger.info("【请求结束】系统管理->用户信息获取,响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	@Autowired
	private ApplicationContext context;
	private HashMap<String, String> systemPermissionsMap = null;

	private Collection<String> toAPI(Set<String> permissions) {
		if (systemPermissionsMap == null) {
			systemPermissionsMap = new HashMap<>();
			final String basicPackage = "com.haust.shop.admin";
			List<Permission> systemPermissions = PermissionUtil.listPermission(context, basicPackage);
			for (Permission permission : systemPermissions) {
				String perm = permission.getRequiresPermissions().value()[0];
				String api = permission.getApi();
				systemPermissionsMap.put(perm, api);
			}
		}

		Collection<String> apis = new HashSet<>();
		for (String perm : permissions) {
			String api = systemPermissionsMap.get(perm);
			apis.add(api);

			if (perm.equals("*")) {
				apis.clear();
				apis.add("*");
				return apis;
				// return systemPermissionsMap.values();
			}
		}
		return apis;
	}
	
	/**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public Object getCode(HttpServletResponse response) throws IOException {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 唯一标识
        String uuid = UUID.randomUUID().toString(true);
        boolean successful = CaptchaCodeManager.addToCache(uuid, verifyCode,10);//存储内存
        if (!successful) {
			logger.error("请求验证码出错:{}", AdminResponseCode.AUTH_CAPTCHA_FREQUENCY.desc());
			return AdminResponseUtil.fail(AdminResponseCode.AUTH_CAPTCHA_FREQUENCY);
		}
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
        	Map<String, Object> data = new HashMap<>();
            data.put("uuid", uuid);
            data.put("img", Base64.encode(stream.toByteArray()));
            return ResponseUtil.ok(data);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.serious();
        } finally {
            stream.close();
        }
    }

	@GetMapping("/401")
	public Object page401() {
		return ResponseUtil.unlogin();
	}

	@GetMapping("/index")
	public Object pageIndex() {
		return ResponseUtil.ok();
	}

	@GetMapping("/403")
	public Object page403() {
		return ResponseUtil.unauthz();
	}
}
