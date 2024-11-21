package org.example.expert.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.expert.common.Timestamped
import org.example.expert.domain.user.enums.UserRole

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    var email: String,

    var password: String,

    var nickname: String,

    @Enumerated(EnumType.STRING)
    var userRole: UserRole,
) : Timestamped() {

    constructor(email: String, password: String, nickname: String, userRole: UserRole) : this(
        id = null,
        email = email,
        password = password,
        nickname = nickname,
        userRole = userRole
    )

    constructor(id: Long?, email: String, role: String) : this(
        id = id,
        email = email,
        password = "",
        nickname = "",
        userRole = UserRole.from(role)
    )

    constructor(nickname: String) : this(
        id = null,
        email = "",
        password = "",
        nickname = nickname,
        userRole = UserRole.USER
    )

    fun getEmail(): String {
        return email
    }
}
