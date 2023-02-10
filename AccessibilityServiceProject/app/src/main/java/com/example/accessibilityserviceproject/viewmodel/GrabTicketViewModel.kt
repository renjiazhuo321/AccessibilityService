package com.example.accessibilityserviceproject.viewmodel

import android.widget.EditText
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.accessibilityserviceproject.DataStore
import com.example.accessibilityserviceproject.repository.GrabTicketRepository
import com.example.accessibilityserviceproject.util.ext.postNext
import com.example.accessibilityserviceproject.viewstate.GrabDataListState
import com.example.accessibilityserviceproject.viewstate.TripListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_grab_tickets.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrabTicketViewModel @Inject constructor(
    private val repo: GrabTicketRepository
) : BaseViewModel() {
    val TICKETS_NAME_SET = stringSetPreferencesKey("tickets_name_set")
    val TICKETS_TRIP_SET = stringSetPreferencesKey("tickets_trip_set")
    //查询姓名
    private val _grabDataListState: MutableLiveData<GrabDataListState> =
        MutableLiveData(GrabDataListState.initial())
    val grabDataListState: LiveData<GrabDataListState> = _grabDataListState

    private val _tripListState: MutableLiveData<TripListState> =
        MutableLiveData(TripListState.initial())
    val tripListState: LiveData<TripListState> = _tripListState
    fun saveNameAndTrip(nameList: MutableList<String>, tripsList: MutableList<String>) {
        viewModelScope.launch {
            DataStore.dataStore.edit { settings ->
                settings[TICKETS_NAME_SET] = nameList.toSet()
            }
            DataStore.dataStore.edit { settings ->
                settings[TICKETS_TRIP_SET] = tripsList.toSet()
            }
        }
    }

    fun queryNameAndTrip(){
        viewModelScope.launch {
            DataStore.dataStore.data.map { preferences ->
                preferences[TICKETS_NAME_SET]
            }.collect { data ->
                _grabDataListState.postNext {
                    it.copy(isLoading = false, throwable = null, data = data)
                }

            }
        }
        viewModelScope.launch {
            DataStore.dataStore.data.map { preferences ->
                preferences[TICKETS_TRIP_SET]
            }.collect { data ->
                _tripListState.postNext {
                    it.copy(isLoading = false, throwable = null, data = data)
                }

            }
        }
    }

}