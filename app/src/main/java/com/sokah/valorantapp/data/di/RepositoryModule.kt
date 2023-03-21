package com.sokah.valorantapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.work.WorkManager
import com.sokah.valorantapp.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule {

    @Binds
    abstract fun bindAgentRepository(
        agentRepository: AgentRepository
    ): IAgentRepository

    @Binds
    abstract fun bindWeaponRepository(
        weaponRepository: WeaponRepository
    ): IWeaponRepository

    @Binds
    abstract fun bindSkinRepository(
        skinRepository: SkinRepository
    ): ISkinRepository
}

@Module
@InstallIn(ViewModelComponent::class)
object PreferenceModule {

    @Provides
    fun provideDataStore(dataStore: DataStore<Preferences>): IPreferencesRepository {

        return PreferencesRepository(dataStore)
    }

    @Provides
    fun providesWorkManager(@ApplicationContext appContext: Context): WorkManager {

        return WorkManager.getInstance(appContext)
    }
}

