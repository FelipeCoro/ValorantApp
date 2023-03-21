package com.sokah.valorantapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.sokah.valorantapp.data.exceptions.CustomException
import com.sokah.valorantapp.data.repository.IAgentRepository
import com.sokah.valorantapp.data.repository.IPreferencesRepository
import com.sokah.valorantapp.ui.viewStates.AgentViewStates
import com.sokah.valorantapp.workers.AgentDownloadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AgentListViewModel @Inject constructor(
    private val repository: IAgentRepository,
    private val preferencesRepository: IPreferencesRepository,
    private val workmanager: WorkManager
) :
    ViewModel() {

    private val _viewState = MutableLiveData<AgentViewStates>()
    val viewState get() = _viewState

    private val _preference = MutableLiveData<Int>()
    val preference get() = _preference

    init {
        workAgents()
    }

    fun getAgents() {

        viewModelScope.launch {

            _viewState.postValue(AgentViewStates.Loading)
            val result = repository.getAllAgents()

            when {
                result.isSuccess -> {
                    _viewState.postValue(AgentViewStates.AgentListSuccess(result.getOrThrow()))
                }
                else -> {

                    _viewState.postValue(AgentViewStates.Error(result.exceptionOrNull() as Exception))
                }
            }

        }


    }

    fun workAgents() {

        val request = PeriodicWorkRequestBuilder<AgentDownloadWorker>(30, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .build()

        workmanager.enqueueUniquePeriodicWork(
            "cache_agentss",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )

    }

    fun filterAgent(role: String) {
        viewModelScope.launch {
            val result = repository.getAgentByRole(role)
            if (result.isEmpty()) _viewState.postValue(AgentViewStates.Error(CustomException("No agent with this role found")))
            else _viewState.postValue(AgentViewStates.AgentListSuccess(result))
        }
    }


    fun getPreference() {

        viewModelScope.launch {

            preferencesRepository.getPreference().collect {
                _preference.postValue(it)


            }
        }

    }

    fun savePreference(value: Int) {

        viewModelScope.launch {
            preferencesRepository.savePreference(value)
        }
    }


}


