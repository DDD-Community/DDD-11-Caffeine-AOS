package com.taltal.poison.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val uiState = MutableStateFlow(
        HomeUiState(
            currentPoison = 1,
            purposePoison = 4,
            description = "카페인 과다섭취는 두통, 불면증, 행동 불안,\n혈압 상승 등을 일으킬 수 있대요!",
            imageUrl = "",
            hasPadding = false,
        )
    )

    init {
        getPoisonState(false)
    }

    fun getPoisonState(isDummyAddCall: Boolean) {
        if (isDummyAddCall) {
            uiState.value = uiState.value.copy(currentPoison = uiState.value.currentPoison + 1)
            return
        }
        // TODO API CALL
        viewModelScope.launch {
            // uiState.value = fetchPoisonState()
        }
    }

    fun drink(isDummyCall: Boolean) {
        viewModelScope.launch {
            addPoison()
            getPoisonState(isDummyCall)
        }
    }

    private suspend fun fetchPoisonState() {
        // TODO API CALL
    }

    private suspend fun addPoison() {
        // TODO API CALL
    }
}

data class HomeUiState(
    val currentPoison: Int,
    val purposePoison: Int,
    val description: String,
    val imageUrl: String,
    val hasPadding: Boolean,
)