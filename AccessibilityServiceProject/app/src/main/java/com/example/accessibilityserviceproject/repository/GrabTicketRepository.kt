package com.example.accessibilityserviceproject.repository

import com.example.accessibilityserviceproject.api.service.ServiceManager
import javax.inject.Inject

class GrabTicketRepository @Inject constructor( familyRemoteData: FamilyRemoteDataSource)
    : BaseRepositoryRemote<FamilyRemoteDataSource>
    (familyRemoteData) {

}


class FamilyRemoteDataSource @Inject constructor(
    private val serviceManager: ServiceManager
) : IRemoteDataSource {

}