package org.example.testflyway.dao.model

enum class Permission {
    PAGE1_READ,
    PAGE1_WRITE,
    PAGE2_READ,
    PAGE2_WRITE;

    companion object {
        private val nameToPermission = HashMap<String, Permission>()

        init {
            entries.forEach {
                nameToPermission[it.toString()] = it
            }
        }

        fun toPermission(string: String): Permission? {
            return nameToPermission.getOrDefault(string.uppercase(), null)
        }
    }
}
