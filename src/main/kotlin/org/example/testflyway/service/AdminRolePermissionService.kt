package org.example.testflyway.service

import mu.KotlinLogging
//import net.minidev.json.JSONArray
import org.example.testflyway.dao.model.Permission
import org.example.testflyway.dao.model.Role
import org.example.testflyway.dao.model.RolePermission
import org.example.testflyway.dao.repository.AdminRolePermissionRepository
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class AdminRolePermissionService(
    private val adminRolePermissionRepository: AdminRolePermissionRepository
) : IAdminRolePermissionService {

    private val logger = KotlinLogging.logger {}

    companion object {
        const val COGNITO_GROUPS = "cognito:groups"
    }
    override fun retrieveRolePermissions(role: Role): List<RolePermission> {
        logger.debug { "retrieving permissions for role '$role'" }
        return adminRolePermissionRepository.findAllByRole(role)
    }

    /**
     * Retrieves assigned roles (groups) from authentication via "cognito:groups"
     */
    override fun retrieveAllRoles(authentication: Authentication): Set<String> {
        return authentication.authorities.map { it.toString() }.toSet()
//        var userGroups: JSONArray? = null
//        if (authentication.principal is OAuth2User) {
//            val oauth2User = authentication.principal as OAuth2User
//            if (oauth2User.attributes[COGNITO_GROUPS] is JSONArray) {
//                userGroups = oauth2User.attributes[COGNITO_GROUPS] as JSONArray
//            }
//            // JWT Token based login from standalone Vue Application
//        } else if (authentication.principal is Jwt) {
//            val jwt = authentication.principal as Jwt
//            if (jwt.claims[COGNITO_GROUPS] is JSONArray) {
//                userGroups = jwt.claims[COGNITO_GROUPS] as JSONArray
//            }
//        }
//        val roles = HashSet<String>()
//        userGroups?.let {
//            userGroups.forEach {
//                roles.add(it.toString().lowercase())
//            }
//        }
//        return roles.toSet()
    }

    /**
     * Maps roles-as-strings to Role
     */
    override fun mapRoles(roles: Iterable<String>): Set<Role> {
        val mappedRoles = HashSet<Role>()
        roles.forEach {
            Role.toRole(it)?.let { role ->
                mappedRoles.add(role)
            }
        }
        return mappedRoles.toSet()
    }

    /**
     * Retrieves permissions from database for given roles (merged).
     */
    override fun retrievePermissionsForRoles(roles: Iterable<Role>): Set<Permission> {
        val permissions = HashSet<Permission>()
        roles.forEach { role ->
            retrieveRolePermissions(role).forEach {
                if (it.enabled) {
                    permissions.add(it.permission)
                }
            }
        }
        return permissions.toSet()
    }
}
