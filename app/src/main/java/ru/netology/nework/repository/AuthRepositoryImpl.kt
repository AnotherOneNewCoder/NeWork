//package ru.netology.nework.repository
//
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import okhttp3.RequestBody.Companion.toRequestBody
//import ru.netology.nework.api.UsersApiService
//import ru.netology.nework.dto.ImageUpload
//import ru.netology.nework.dto.Token
//import ru.netology.nework.errors.*
//import java.io.IOException
//import javax.inject.Inject
//
//class AuthRepositoryImpl @Inject constructor(
//    private val usersApiService: UsersApiService
//) : AuthRepository {
//    override suspend fun singInUser(login: String, passwd: String): Token {
//        try {
//            val response = usersApiService.authUser(
//                login = login,
//                passwd = passwd
//            )
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            return response.body() ?: throw Exception()
//        } catch (e: IOException) {
//            throw NetworkError
//        } catch (e: Exception) {
//            throw UnknownError
//        }
//    }
//
//    override suspend fun singUpUser(
//        login: String,
//        passwd: String,
//        name: String,
//        photo: ImageUpload
//    ): Token {
//        try {
//
//
//            val media = MultipartBody.Part.createFormData(
//                "file", photo.file.name, photo.file.asRequestBody()
//            )
//            val response = usersApiService.regUser(
//                login = login.toRequestBody("text/lain".toMediaType()),
//                passwd = passwd.toRequestBody("text/lain".toMediaType()),
//                name = name.toRequestBody("text/lain".toMediaType()),
//                media
//            )
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            return response.body() ?: throw Exception()
//        } catch (e: IOException) {
//            throw NetworkError
//        } catch (e: Exception) {
//            throw UnknownError
//        }
//    }
//}