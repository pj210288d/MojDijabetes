package rs.etf.mojdijabetes.hilt

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.etf.mojdijabetes.data.local.AppDatabase
import rs.etf.mojdijabetes.data.local.UserPreferences
import rs.etf.mojdijabetes.data.local.dao.InsulinDao
import rs.etf.mojdijabetes.data.local.dao.UserDao
import rs.etf.mojdijabetes.data.repository.AuthRepository
import rs.etf.mojdijabetes.data.repository.InsulinRepository
import rs.etf.mojdijabetes.domain.usecase.SignOutUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mojdijabetes.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideInsulinDao(database: AppDatabase) = database.insulinDao()

    @Provides
    @Singleton
    fun provideGlucoseDao(database: AppDatabase) = database.glucoseDao()

    @Provides
    @Singleton
    fun provideAuthRepository(
        userDao: UserDao,
        userPreferences: UserPreferences,
    ) = AuthRepository(userDao, userPreferences)

    @Provides
    @Singleton
    fun provideInsulinRepository(
        insulinDao: InsulinDao
    ) = InsulinRepository(insulinDao)

    @Provides
    @Singleton
    fun provideSignOutUseCase(
        userPreferences: UserPreferences
    ) = SignOutUseCase(userPreferences)
}