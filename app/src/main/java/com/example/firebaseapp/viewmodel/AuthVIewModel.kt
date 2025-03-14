import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.firebaseapp.data.AuthRepository
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.data.state.AuthState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: UserPreferences, private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState

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

    fun onForgotPasswordClicked() {

    }

    fun getSession() {

    }

    fun login() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(2000) // Simulate API call
            _uiState.value =
                if (_uiState.value.email == "user@example.com" && _uiState.value.password == "password") {
                    _uiState.value.copy(isLoading = false, isSuccess = true)
                } else {
                    _uiState.value.copy(isLoading = false, snackbarMessage = "Invalid credentials")
                }
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
                var result = authRepository.registerUser(_uiState.value.username, _uiState.value.email, _uiState.value.password)
                if (result.isSuccess) {
                    try {
                        saveUserId(_uiState.value.email)
                    }catch (e: Exception){
                        println(e)
                    }
                }
            }
        } else {
            _uiState.value = _uiState.value.copy(isLoading = false)
            _uiState.value = _uiState.value.copy(snackbarMessage = "Please check your input")
        }
    }

    fun getUserId(): LiveData<String?> {
        return pref.getUserId().asLiveData()
    }

    fun saveUserId(userId: String) {
        viewModelScope.launch {
            pref.saveUserId(userId)
        }

    }
}

