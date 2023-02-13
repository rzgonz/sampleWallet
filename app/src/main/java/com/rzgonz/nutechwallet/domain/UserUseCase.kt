package com.rzgonz.nutechwallet.domain

import com.rzgonz.nutechwallet.data.dto.LoginDto
import com.rzgonz.nutechwallet.data.dto.RegisterDto
import com.rzgonz.nutechwallet.data.dto.UpdateProfileDto
import com.rzgonz.nutechwallet.data.dto.UserProfileDto
import com.rzgonz.nutechwallet.data.remote.request.LoginRequest
import com.rzgonz.nutechwallet.data.remote.request.RegisterRequest
import com.rzgonz.nutechwallet.data.remote.request.UpdateProfileRequest

/**
 * Created by rzgonz on 12/02/23.
 *
 */
interface UserUseCase {
    suspend fun login(loginRequest: LoginRequest): LoginDto
    suspend fun register(request: RegisterRequest): RegisterDto
    suspend fun getProfileUser(): UserProfileDto
    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest): UpdateProfileDto
}