package ru.netology.nework.dao

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import ru.netology.nework.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity ORDER BY name ASC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListUsers(users: List<UserEntity>)

    @Query("DELETE FROM UserEntity WHERE id = :id")
    suspend fun deleteUserById(id: Long)

    // for search
    @Query("SELECT * FROM UserEntity WHERE id LIKE :searchQuery OR name LIKE :searchQuery")
    fun searchUser(searchQuery: String): Flow<List<UserEntity>>
}