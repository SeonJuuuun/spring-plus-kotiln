package org.example.expert.domain.user.service

import lombok.RequiredArgsConstructor
import org.example.expert.domain.dto.response.UserResponse
import org.example.expert.domain.dto.response.UserSearchResponse
import org.example.expert.domain.user.entity.User
import org.example.expert.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional(readOnly = true)
    fun getUser(userId: Long): UserResponse {
        val user = userRepository.findById(userId)
            ?: throw InvalidRequestException("User not found")
        return UserResponse(user.id, user.getEmail())
    }

    fun changePassword(userId: Long, userChangePasswordRequest: UserChangePasswordRequest) {
        validateNewPassword(userChangePasswordRequest)

        val user = userRepository.findById(userId)
            ?: throw InvalidRequestException("User not found")

        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.password)) {
            throw InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.")
        }

        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.password)) {
            throw InvalidRequestException("잘못된 비밀번호입니다.")
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()))
    }

    companion object {
        fun validateNewPassword(userChangePasswordRequest UserChangePasswordRequest) {
            val newPassword = userChangePasswordRequest.newPassword
            if (newPassword.length < 8 ||
                !newPassword.matches(".*\\d.*".toRegex()) ||
                !newPassword.matches(".*[A-Z].*".toRegex())
            ) {
                throw InvalidRequestException("새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.")
            }
        }
    }

    fun searchNickname(userSearchRequest: UserSearchRequest): UserSearchResponse {
        val user: User = userRepository.findByNickname(userSearchRequest.nickname())
            ?: throw InvalidRequestException("닉네임이 유효하지 않습니다.")
        return UserSearchResponse.from(user.nickname)
    }
}
