package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.repository.MahasiswaRepository
import com.example.pertemuan12.ui.view.mahasiswa.DestinasiUpdate
import kotlinx.coroutines.launch
class UpdateMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])
    init {
        viewModelScope.launch {
            updateUiState = mahasiswaRepository.getMahasiswabyNim(_nim)
                .toUiStateMhs()
        }
    }
    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }
    suspend fun updateMhs(){
        viewModelScope.launch {
            try {
                mahasiswaRepository.updateMahasiswa(_nim, updateUiState.insertUiEvent.toMhs())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}