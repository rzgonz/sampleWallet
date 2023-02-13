package com.rzgonz.nutechwallet.domain

import com.rzgonz.nutechwallet.data.AppRepository
import com.rzgonz.nutechwallet.data.DataMapper
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
class UserUseCaseImpl(
    private val repository: AppRepository
) : UserUseCase {

    override suspend fun login(loginRequest: LoginRequest): LoginDto {
        val response = repository.login(loginRequest)
        return DataMapper.loginResponseToDto(response.data)
    }

    override suspend fun register(request: RegisterRequest): RegisterDto {
        val response = repository.register(request)
        return DataMapper.registerResponseToDto(response.data)
    }

    override suspend fun getProfileUser(): UserProfileDto {
        val response = repository.getProfile()
        return DataMapper.userProfileResponseToDto(response.data)
    }

    override suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest): UpdateProfileDto {
        val response = repository.updateProfile(updateProfileRequest)
        return DataMapper.updateProfileResponseToDto(response.data)
    }

}