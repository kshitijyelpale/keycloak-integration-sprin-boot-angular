package org.example.testflyway.dao.model

enum class Role {
    ROLE_SUPER_ADMIN,
    ROLE_ADMIN,
    ROLE_USER;

    companion object {
        private val nameToRole = HashMap<String, Role>()

        init {
            entries.forEach {
                nameToRole[it.toString()] = it
            }
        }

        fun toRole(string: String): Role? {
            return nameToRole.getOrDefault(string.uppercase(), null)
        }
    }
}
