package com.github.zhangkaitao.shiro.chapter3.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 */
public class MyRolePermissionResolver implements RolePermissionResolver {

    /**
     * 权限集合，也就是角色
     *
     * @param roleString
     * @return
     */
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if ("role3".equals(roleString)) {
            return Arrays.asList((Permission) new WildcardPermission("menu:*"));
        }
        return null;

    }
}
