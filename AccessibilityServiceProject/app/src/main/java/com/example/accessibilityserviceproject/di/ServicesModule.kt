package com.example.accessibilityserviceproject.di

import RequestService
import com.example.accessibilityserviceproject.api.service.ServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

//    @Provides
//    @Singleton
//    fun provideUserService(retrofit: Retrofit): RequestService {
//        return retrofit.create(RequestService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideServiceManager(userService: RequestService): ServiceManager {
//        return ServiceManager(userService)
//    }


}
