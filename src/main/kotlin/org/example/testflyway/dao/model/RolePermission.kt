package org.example.testflyway.dao.model

import jakarta.persistence.*

@Entity
@Table(name = "role_permissions")
data class RolePermission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val permission: Permission,

    @Column(nullable = false)
    var enabled: Boolean
)
