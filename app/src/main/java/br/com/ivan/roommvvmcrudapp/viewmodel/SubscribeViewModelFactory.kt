package br.com.ivan.roommvvmcrudapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.ivan.roommvvmcrudapp.data.repository.SubscriberRepository
import java.lang.IllegalArgumentException

class SubscribeViewModelFactory(
    private val repository: SubscriberRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
     if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
         return SubscriberViewModel(repository) as T
     }
        throw IllegalArgumentException("Unknown View Model class")
    }
}