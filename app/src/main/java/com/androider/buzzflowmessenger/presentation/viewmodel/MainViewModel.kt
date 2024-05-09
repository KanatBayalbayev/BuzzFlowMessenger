package com.androider.buzzflowmessenger.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androider.buzzflowmessenger.domain.models.CurrentUserEntity
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.domain.models.MessageEntity
import com.androider.buzzflowmessenger.domain.usecases.AddFoundUserToChatsUseCase
import com.androider.buzzflowmessenger.domain.usecases.FindUserUseCase
import com.androider.buzzflowmessenger.domain.usecases.GetChatsUseCase
import com.androider.buzzflowmessenger.domain.usecases.GetCurrentUserUseCase
import com.androider.buzzflowmessenger.domain.usecases.GetMessagesUseCase
import com.androider.buzzflowmessenger.domain.usecases.SendMessageUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val findUserUseCase: FindUserUseCase,
    private val addFoundUserToChatsUseCase: AddFoundUserToChatsUseCase,
    private val getChatsUseCase: GetChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,

) : ViewModel() {
    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state


    fun findUser(email: String) {
        _state.value = MainState.Loading
        findUserUseCase(email){
            if (it.success){
                _state.value = MainState.Success(it.foundUser)
            } else {
                _state.value = MainState.Error(it.errorMessage)
            }
        }
    }

    fun addFoundUserToChats(foundUserEntity: FoundUserEntity) =
        addFoundUserToChatsUseCase(foundUserEntity)


    fun getChats(){
        _state.value = MainState.Loading
        getChatsUseCase(){
            if (it.success){
                _state.value = MainState.Chats(it.chats)
            } else {
                _state.value = MainState.Error(it.errorMessage)
            }
        }
    }

    fun sendMessage(
        messageEntity: MessageEntity,
        currentUserEntity: CurrentUserEntity
    ) = sendMessageUseCase(messageEntity, currentUserEntity)

    fun getMessages(currentUserID: String, anotherUserID: String) {
        getMessagesUseCase(currentUserID, anotherUserID){
            if (it.success){
                _state.value = MainState.Messages(it.messages)
            } else {
                _state.value = MainState.Error(it.errorMessage)
            }
        }
    }

    fun getCurrentUser(currentUserID: String){
        getCurrentUserUseCase(currentUserID){
            if (it.success){
                _state.value = MainState.CurrentUser(it.currentUserEntity)
            } else {
                _state.value = MainState.Error(it.errorMessage)
            }
        }
    }


}




