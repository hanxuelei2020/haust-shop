package com.haust.shop.admin.util;

import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import org.apache.shiro.authz.annotation.RequiresPermissions;

public class Permission {
	private RequiresPermissions requiresPermissions;
	private RequiresPermissionsDesc requiresPermissionsDesc;
	private String api;

	public RequiresPermissions getRequiresPermissions() {
		return requiresPermissions;
	}

	public RequiresPermissionsDesc getRequiresPermissionsDesc() {
		return requiresPermissionsDesc;
	}

	public void setRequiresPermissions(RequiresPermissions requiresPermissions) {
		this.requiresPermissions = requiresPermissions;
	}

	public void setRequiresPermissionsDesc(RequiresPermissionsDesc requiresPermissionsDesc) {
		this.requiresPermissionsDesc = requiresPermissionsDesc;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}
}
