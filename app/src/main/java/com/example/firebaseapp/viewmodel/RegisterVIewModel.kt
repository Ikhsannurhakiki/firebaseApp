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

class RegisterViewModel(private val pref: UserPreferences, private val authRepository: AuthRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState

    fun showSnackbar(message: String) {
        _uiState.value = _uiState.value.copy(snackbarMessage = message)
    }

    fun clearSnackbarMessage() {
        _uiState.value = _uiState.value.copy(snackbarMessage = null)
    }

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
        _uiState.value = _uiState.value.copy(errorUsernameMessage = null)
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

    fun onConfirmPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = password)
        if (password.length < 6) {
            _uiState.value = _uiState.value.copy(errorConfirmPasswordMessage = "Min 6 characters")
        } else if (password != _uiState.value.password) {
            _uiState.value = _uiState.value.copy(errorConfirmPasswordMessage = "Password not match")
        } else {
            _uiState.value = _uiState.value.copy(errorConfirmPasswordMessage = null)
        }
    }

    fun register() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val errors = mapOf(
            "username" to "Username required".takeIf { _uiState.value.username.isEmpty() },
            "email" to "Email required".takeIf { _uiState.value.email.isEmpty() }
        )
        _uiState.value =
            _uiState.value.copy(errorUsernameMessage = "Username required".takeIf { _uiState.value.username.isEmpty() })
        _uiState.value =
            _uiState.value.copy(errorEmailMessage = "Email required".takeIf { _uiState.value.email.isEmpty() })
        _uiState.value =
            _uiState.value.copy(errorPasswordMessage = "Password required".takeIf { _uiState.value.password.isEmpty() })
        _uiState.value =
            _uiState.value.copy(errorConfirmPasswordMessage = "Confirm Password required".takeIf { _uiState.value.confirmPassword.isEmpty() })

        var isError = errors.values.any { it != null }

        if (!isError) {
            viewModelScope.launch {
                delay(2000)
                _uiState.value = _uiState.value.copy(isLoading = false)
                var result = authRepository.registerUser(
                    _uiState.value.username,
                    _uiState.value.email,
                    _uiState.value.password
                )
                if (result.isSuccess) {
                    try {
                        pref.saveUserId(_uiState.value.email)
                        _uiState.value = _uiState.value.copy(isSuccess = true)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
        } else {
            _uiState.value = _uiState.value.copy(isLoading = false)
            _uiState.value = _uiState.value.copy(snackbarMessage = "Please check your input")
        }
    }
}

