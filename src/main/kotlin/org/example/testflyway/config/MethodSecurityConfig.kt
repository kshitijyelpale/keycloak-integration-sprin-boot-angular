package org.example.testflyway.config

import org.example.testflyway.service.IAdminRolePermissionService
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!integration")
class MethodSecurityConfig(
    private val adminRolesPermissionsService: IAdminRolePermissionService,
) : GlobalMethodSecurityConfiguration() {
    override fun createExpressionHandler(): MethodSecurityExpressionHandler {
        return CustomMethodSecurityExpressionHandler(adminRolesPermissionsService)
    }
}
