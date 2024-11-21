package org.example.expert.domain.user.controller

import lombok.RequiredArgsConstructor
import org.example.expert.domain.dto.response.UserResponse
import org.example.expert.domain.dto.response.UserSearchResponse
import org.example.expert.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class UserController(
    private val userService: UserService
) {

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUser(userId))
    }

    @PutMapping("/users")
    fun changePassword(
        @AuthenticationPrincipal authUser: AuthUser,
        @RequestBody userChangePasswordRequest: UserChangePasswordRequest
    ) {
        userService.changePassword(authUser.id, userChangePasswordRequest)
    }

    @GetMapping("/users")
    fun search(@RequestBody userSearchRequest: UserSearchRequest): ResponseEntity<UserSearchResponse> {
        val resp: UserSearchResponse = userService.searchNickname(userSearchRequest)
        return ResponseEntity.ok(resp)
    }
}
