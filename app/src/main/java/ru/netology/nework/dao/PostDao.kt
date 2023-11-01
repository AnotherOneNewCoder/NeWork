package ru.netology.nework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.entity.PostEntity


@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAllPosts(): Flow<List<PostEntity>>
    @Query("SELECT * FROM PostEntity WHERE authorId= :authorId ORDER BY id DESC")
    fun getAllUserPosts(authorId: Long): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListPosts(posts: List<PostEntity>)
// Как сделать запрос с изменением Embedded?
//    @Query("UPDATE PostEntity SET content = :content, link = :link, attachment = :attachment, coords = :coords WHERE id = :id")
//    suspend fun updatePostById(id: Long, content: String, link: String, attachment: AttachmentEmbedded)
    @Query("UPDATE PostEntity SET content = :content, link = :link WHERE id = :id")
    suspend fun updatePostById(id: Long, content: String, link: String?)

    suspend fun savePost(postEntity: PostEntity) =
        if (postEntity.id == 0L) insertPost(postEntity)
        else updatePostById(postEntity.id, postEntity.content, postEntity.link)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    suspend fun deletePostById(id: Long)

    @Query("DELETE FROM PostEntity")
    suspend fun clearPostEntity()

    @Query("UPDATE PostEntity SET likedByMe = 1 WHERE id = :id AND likedByMe = 0")
    suspend fun likePostById(id: Long)
    @Query("UPDATE PostEntity SET likedByMe = 0 WHERE id = :id AND likedByMe = 1")
    suspend fun unlikePostById(id: Long)

    @Query("UPDATE PostEntity SET `mentionId` = `mentionId` + 1 WHERE id = :id")
    suspend fun mentionById(id: Long)
}