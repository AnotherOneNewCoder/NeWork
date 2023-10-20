//package ru.netology.nework.repository
//
//import ru.netology.nework.dto.ImageUpload
//import ru.netology.nework.dto.Token
//
//interface AuthRepository {
//    suspend fun singInUser(login: String, passwd: String): Token
//    suspend fun singUpUser(login: String, passwd: String, name: String, photo: ImageUpload): Token
//}