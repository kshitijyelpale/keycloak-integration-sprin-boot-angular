package org.example.testflyway.controller

import org.example.testflyway.dao.model.Permission
import org.example.testflyway.jwt.CustomJwt
import org.example.testflyway.service.IAdminRolePermissionService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(
    origins = ["http://localhost:4200"],
    allowedHeaders = ["*"],
    methods = [ RequestMethod.GET ]
)
class MyController(private val iAdminRolePermissionService: IAdminRolePermissionService) {

    @GetMapping("/home")
    fun getHome(): ResponseEntity<Map<String, String>> {
        val jwt = SecurityContextHolder.getContext().authentication as CustomJwt
        return ResponseEntity.ok(mapOf("message" to "Welcome to home page, ${jwt.firstName} ${jwt.lastName}"))
    }

    @GetMapping("/permissions")
    fun getPermissions(): ResponseEntity<Set<Permission>> {
        val jwt = SecurityContextHolder.getContext().authentication as CustomJwt
        return ResponseEntity.ok(iAdminRolePermissionService.retrievePermissions(jwt))
    }

    @GetMapping("/page1")
    @PreAuthorize("hasPermission('PAGE1_READ')")
    fun getPage1() = ResponseEntity.ok("Welcome to page 1")

    @GetMapping("/page2")
    @PreAuthorize("hasPermission('PAGE2_READ')")
    fun getPage2() = ResponseEntity.ok("Welcome to page 2")
}
