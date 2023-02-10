package com.example.accessibilityserviceproject.repository

open class BaseRepositoryBoth<T : IRemoteDataSource, R : ILocalDataSource>(
        protected val remoteDataSource: T,
        protected val localDataSource: R
) : IRepository

open class BaseRepositoryLocal<T : ILocalDataSource>(
        protected val localDataSource: T
) : IRepository

open class BaseRepositoryRemote<T : IRemoteDataSource>(
        protected val remoteDataSource: T
) : IRepository

open class BaseRepositoryNothing() : IRepository