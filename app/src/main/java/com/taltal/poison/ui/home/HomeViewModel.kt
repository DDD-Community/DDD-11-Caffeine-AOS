package com.taltal.poison.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taltal.poison.data.repository.PoisonCoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val repository: PoisonCoffeeRepository,
    ) : ViewModel() {
        val uiState =
            MutableStateFlow(
                HomeUiState(
                    currentPoison = 0,
                    purposePoison = 4,
                    description = "카페인 과다섭취는 두통, 불면증, 행동 불안,\n혈압 상승 등을 일으킬 수 있대요!",
                    imageUrl = "poe_main_0.json",
                    hasPadding = false,
                ),
            )
        val currentPoisonJson = MutableStateFlow("poe_main_0.json")

        init {
            enter()
            getPoisonState()
        }

        private fun getPoisonState() {
            viewModelScope.launch {
                val currentData = repository.getToday()
                uiState.value =
                    HomeUiState(
                        currentPoison = currentData.todayCount,
                        purposePoison = currentData.targetCount,
                        description = currentData.description,
                        imageUrl = currentData.imageJson,
                        hasPadding = false,
                    )
                currentPoisonJson.value = currentData.imageJson
            }
        }

        private fun enter() {
            viewModelScope.launch {
                repository.enter()
            }
        }

        fun drink() {
            viewModelScope.launch {
                val data = repository.updatePoisonStatus()
                uiState.value =
                    HomeUiState(
                        currentPoison = data.todayCount,
                        purposePoison = data.targetCount,
                        description = data.description,
                        imageUrl = data.imageJson,
                        hasPadding = false,
                    )
                currentPoisonJson.value = data.imageJson
            }
        }
    }

data class HomeUiState(
    val currentPoison: Int,
    val purposePoison: Int,
    val description: String,
    val imageUrl: String,
    val hasPadding: Boolean,
)
