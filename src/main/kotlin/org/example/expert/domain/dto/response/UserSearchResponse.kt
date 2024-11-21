package org.example.expert.domain.dto.response

data class UserSearchResponse (
    val nickname: String
){
    companion object {
        fun from(nickname: String): UserSearchResponse {
            return UserSearchResponse(nickname)
        }
    }
}
