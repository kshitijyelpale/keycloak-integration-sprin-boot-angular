package org.example.testflyway.service

import org.example.testflyway.dao.model.Permission
import org.example.testflyway.dao.model.Role
import org.example.testflyway.dao.model.RolePermission
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

interface IAdminRolePermissionService {

    fun retrieveRolePermissions(role: Role): List<RolePermission>

    fun retrieveAllRoles(authentication: Authentication): Set<String>

    fun mapRoles(roles: Iterable<String>): Set<Role>

    fun retrievePermissionsForRoles(roles: Iterable<Role>): Set<Permission>

    /**
     * Convenience method combining retrieving roles from authentication, mapping and permission lookup.
     */
    fun retrievePermissions(authentication: Authentication): Set<Permission> {
        return retrievePermissions(retrieveAllRoles(authentication))
    }

    /**
     * Convenience method combining mapping and permission lookup.
     */
    fun retrievePermissions(roles: Iterable<String>): Set<Permission> {
        return retrievePermissionsForRoles(mapRoles(roles))
    }

    /**
     * Convenience method checking if provided permission exists.
     */
    fun hasPermission(permission: Permission): Boolean {
        return retrievePermissions(SecurityContextHolder.getContext().authentication).contains(permission)
    }
}
