package com.iptvplayer.streaming.database

import android.content.Context
import androidx.room.*
import com.iptvplayer.streaming.model.*

@Entity(tableName = "favorite_channels")
data class FavoriteChannelEntity(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val logo: String?,
    val group: String?,
    val addedAt: Long
)

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey val id: String,
    val channelTitle: String,
    val channelUrl: String,
    val logo: String?,
    val watchedAt: Long,
    val duration: Long,
    val lastPosition: Long
)

@Entity(tableName = "server_configs")
data class ServerConfigEntity(
    @PrimaryKey val id: String,
    val name: String,
    val m3uUrl: String,
    val epgUrl: String?,
    val username: String?,
    val password: String?,
    val userAgent: String?,
    val httpReferrer: String?,
    val isActive: Boolean,
    val priority: Int,
    val lastSync: Long
)

@Entity(tableName = "cached_channels")
data class CachedChannelEntity(
    @PrimaryKey val id: String,
    val serverId: String,
    val title: String,
    val url: String,
    val group: String?,
    val logo: String?,
    val epgId: String?,
    val language: String?,
    val country: String?,
    val isRadio: Boolean,
    val isAdult: Boolean,
    val categories: String, // JSON string
    val lastUpdated: Long
)

@Dao
interface FavoriteChannelDao {
    @Query("SELECT * FROM favorite_channels ORDER BY addedAt DESC")
    suspend fun getAllFavorites(): List<FavoriteChannelEntity>
    
    @Query("SELECT * FROM favorite_channels WHERE id = :id")
    suspend fun getFavoriteById(id: String): FavoriteChannelEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteChannelEntity)
    
    @Delete
    suspend fun deleteFavorite(favorite: FavoriteChannelEntity)
    
    @Query("DELETE FROM favorite_channels WHERE id = :id")
    suspend fun deleteFavoriteById(id: String)
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_channels WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}

@Dao
interface WatchHistoryDao {
    @Query("SELECT * FROM watch_history ORDER BY watchedAt DESC LIMIT :limit")
    suspend fun getRecentHistory(limit: Int = 50): List<WatchHistoryEntity>
    
    @Query("SELECT * FROM watch_history WHERE channelUrl = :url ORDER BY watchedAt DESC LIMIT 1")
    suspend fun getLastWatchedPosition(url: String): WatchHistoryEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: WatchHistoryEntity)
    
    @Query("DELETE FROM watch_history WHERE watchedAt < :timestamp")
    suspend fun deleteOldHistory(timestamp: Long)
    
    @Query("DELETE FROM watch_history")
    suspend fun clearAllHistory()
}

@Dao
interface ServerConfigDao {
    @Query("SELECT * FROM server_configs ORDER BY priority ASC")
    suspend fun getAllServers(): List<ServerConfigEntity>
    
    @Query("SELECT * FROM server_configs WHERE isActive = 1 ORDER BY priority ASC")
    suspend fun getActiveServers(): List<ServerConfigEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServer(server: ServerConfigEntity)
    
    @Update
    suspend fun updateServer(server: ServerConfigEntity)
    
    @Delete
    suspend fun deleteServer(server: ServerConfigEntity)
    
    @Query("UPDATE server_configs SET lastSync = :timestamp WHERE id = :serverId")
    suspend fun updateLastSync(serverId: String, timestamp: Long)
}

@Dao
interface CachedChannelDao {
    @Query("SELECT * FROM cached_channels WHERE serverId = :serverId ORDER BY title ASC")
    suspend fun getChannelsByServer(serverId: String): List<CachedChannelEntity>
    
    @Query("SELECT * FROM cached_channels WHERE title LIKE '%' || :query || '%' OR `group` LIKE '%' || :query || '%'")
    suspend fun searchChannels(query: String): List<CachedChannelEntity>
    
    @Query("SELECT DISTINCT `group` FROM cached_channels WHERE `group` IS NOT NULL ORDER BY `group` ASC")
    suspend fun getAllGroups(): List<String>
    
    @Query("SELECT * FROM cached_channels WHERE `group` = :group ORDER BY title ASC")
    suspend fun getChannelsByGroup(group: String): List<CachedChannelEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<CachedChannelEntity>)
    
    @Query("DELETE FROM cached_channels WHERE serverId = :serverId")
    suspend fun deleteChannelsByServer(serverId: String)
    
    @Query("DELETE FROM cached_channels WHERE lastUpdated < :timestamp")
    suspend fun deleteOldChannels(timestamp: Long)
}

@Database(
    entities = [
        FavoriteChannelEntity::class,
        WatchHistoryEntity::class,
        ServerConfigEntity::class,
        CachedChannelEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class IPTVDatabase : RoomDatabase() {
    abstract fun favoriteChannelDao(): FavoriteChannelDao
    abstract fun watchHistoryDao(): WatchHistoryDao
    abstract fun serverConfigDao(): ServerConfigDao
    abstract fun cachedChannelDao(): CachedChannelDao
    
    companion object {
        @Volatile
        private var INSTANCE: IPTVDatabase? = null
        
        fun getDatabase(context: Context): IPTVDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IPTVDatabase::class.java,
                    "iptv_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }
}
