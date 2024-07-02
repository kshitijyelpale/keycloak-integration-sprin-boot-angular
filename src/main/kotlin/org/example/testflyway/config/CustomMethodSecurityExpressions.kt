package org.example.testflyway.config

import org.example.testflyway.dao.model.Permission
import org.example.testflyway.service.IAdminRolePermissionService
import org.springframework.security.access.expression.SecurityExpressionRoot
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.core.Authentication

class CustomMethodSecurityExpressions(
    authentication: Authentication,
    private val adminRolesPermissionsService: IAdminRolePermissionService,
) : SecurityExpressionRoot(authentication), MethodSecurityExpressionOperations {

    private var filterObject: Any? = null
    private var returnObject: Any? = null

    /**
     * Checks if the given Permission is contained in any of the active roles
     */
    fun hasPermission(permission: String): Boolean {
        val permissions = adminRolesPermissionsService.retrievePermissions(authentication)
        return Permission.toPermission(permission)?.let {
            permissions.contains(it)
        } ?: false
    }

    /**
     * Checks if the given permission is contained in any of the active roles and the tenant ID matches
     */
    fun hasPermissionAndMatchingTenant(permission: String, tenantId: Long): Boolean {
        val roleStrings = adminRolesPermissionsService.retrieveAllRoles(authentication)
        if (roleStrings.contains("tenant_${tenantId}")) {
            val permissions = adminRolesPermissionsService.retrievePermissions(roleStrings)
            Permission.toPermission(permission)?.let {
                return permissions.contains(it)
            }
        }
        return false
    }

    /**
     * Checking whether admin has access to _all_ of the specified tenant IDs.
     */
    fun hasAccessToAllTenantIds(tenantIds: List<Long>): Boolean {
        val roleStrings = adminRolesPermissionsService.retrieveAllRoles(authentication)
        return tenantIds.all { roleStrings.contains("tenant_${it}") }
    }

    /**
     * Checks for matching username and role "user"
     */
    fun isMatchingUser(userId: Any?): Boolean {
        if (authentication == null || userId == null) {
            return false
        }
        return authentication.name == userId && adminRolesPermissionsService.retrieveAllRoles(authentication).contains("user")
    }

    override fun getFilterObject(): Any? {
        return this.filterObject
    }

    override fun getReturnObject(): Any? {
        return this.returnObject
    }

    override fun getThis(): Any? {
        return this
    }

    override fun setFilterObject(obj: Any?) {
        this.filterObject = obj
    }

    override fun setReturnObject(obj: Any?) {
        this.returnObject = obj
    }
}
