package org.example.expert.domain.user.enums

enum class UserRole {
    ADMIN, USER;

    companion object {
        fun from(role: String): UserRole {
            return values().find { it.name.equals(role, ignoreCase = true) }
                ?: throw InvalidRequestException("유효하지 않은 UserRole")
        }
    }
}
