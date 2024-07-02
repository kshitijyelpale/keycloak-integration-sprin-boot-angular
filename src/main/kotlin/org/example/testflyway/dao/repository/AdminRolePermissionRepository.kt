package org.example.testflyway.dao.repository

import org.example.testflyway.dao.model.Role
import org.example.testflyway.dao.model.RolePermission
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRolePermissionRepository : CrudRepository<RolePermission, Long> {
    fun findAllByRole(role: Role): List<RolePermission>
}
