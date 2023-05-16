package com.komshop.data.repo


import com.komshop.data.Session.authUser
import com.komshop.data.model.User
import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.retrofit.dto.UserDto
import com.komshop.data.room.dao.UserDao
import com.komshop.parseObject
import com.komshop.util.Resource
import com.komshop.data.toUser
import com.komshop.data.toUserEntity
import com.komshop.log
import com.komshop.processErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepo(
    private val retrofitInterface: RetrofitInterface,
    private val userDao: UserDao
) {
    suspend fun authenticate(phone: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.authenticate(phone.trim(), password)
            if (res.error == null) {
                val user: UserDto = parseObject(res.data, UserDto::class.java)

                // save user to database
                userDao.insert(user.toUserEntity())

                authUser.value = user.toUser()
                emit(Resource.success(user.toUser()))
            } else {
                emit(Resource.error(res.error, null))
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }

    /**
     * get previous logged in account
     */
    fun getAuthenticatedUser() : Flow<User?> {
        return userDao.getUser().map { it?.toUser() }
    }

    fun signup(name: String, phone: String, email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.signup(name, phone, email, password, password)
            log(res)
            if (res.error == null) {
                val user: UserDto = parseObject(res.data, UserDto::class.java)

                // save user to database
                userDao.insert(user.toUserEntity())
                authUser.value = user.toUser()
                log(authUser.value)
                emit(Resource.success(user.toUser()))
            } else {
                throw Exception(processErrors(res.errors))
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }
}