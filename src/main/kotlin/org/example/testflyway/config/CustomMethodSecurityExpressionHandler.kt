package org.example.testflyway.config

import org.aopalliance.intercept.MethodInvocation
import org.example.testflyway.service.IAdminRolePermissionService
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.authentication.AuthenticationTrustResolverImpl
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.core.Authentication

@Configuration
@Profile("!integration")
class CustomMethodSecurityExpressionHandler(
    private val adminRolesPermissionsService: IAdminRolePermissionService,
) : DefaultMethodSecurityExpressionHandler() {
    private val trustResolver = AuthenticationTrustResolverImpl()

    override fun createSecurityExpressionRoot(authentication: Authentication?, invocation: MethodInvocation?)
            : MethodSecurityExpressionOperations? {
        val root = authentication?.let { CustomMethodSecurityExpressions(it, adminRolesPermissionsService) }
        root?.setTrustResolver(this.trustResolver)
        root?.setRoleHierarchy(roleHierarchy)
        return root
    }
}
