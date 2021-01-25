package com.shiv.musicwiki.di

import com.shiv.musicwiki.data.repositories.MusicRepository
import com.shiv.musicwiki.data.repositories.MusicRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun authenticationRepository(authenticationRepositoryImp: MusicRepositoryImp): MusicRepository
}