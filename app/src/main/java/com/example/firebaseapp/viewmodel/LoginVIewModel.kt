import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.firebaseapp.data.AuthRepository
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.data.state.LoginState
import com.example.firebaseapp.data.state.RegisterState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreferences, private val authRepository: AuthRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    fun showSnackbar(message: String) {
        _uiState.value = _uiState.value.copy(snackbarMessage = message)
    }

    fun clearSnackbarMessage() {
        _uiState.value = _uiState.value.copy(snackbarMessage = null)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        _uiState.value = _uiState.value.copy(errorEmailMessage = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
        if (password.length < 6) {
            _uiState.value = _uiState.value.copy(errorPasswordMessage = "Min 6 characters")
        } else {
            _uiState.value = _uiState.value.copy(errorPasswordMessage = null)
        }
    }

    fun onForgotPasswordClicked() {

    }



    fun login() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val errors = mapOf(
            "email" to "Email required".takeIf { _uiState.value.email.isEmpty() },
            "password" to "Password required".takeIf { _uiState.value.password.isEmpty() }
        )
        var isError = errors.values.any { it != null }

        if (!isError) {
            viewModelScope.launch {
                delay(2000)
                var result = authRepository.loginUser(_uiState.value.email, _uiState.value.password)
                if (result.isSuccess) {
                    try {
                        pref.saveUserId(_uiState.value.email)
                        _uiState.value = _uiState.value.copy(isSuccess = true)
                    } catch (e: Exception) {
                        println(e)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _uiState.value = _uiState.value.copy(snackbarMessage = "Invalid credentials")
                }
            }
        } else {
            _uiState.value = _uiState.value.copy(isLoading = false)
           showSnackbar("Please check your input")
        }
    }
}

