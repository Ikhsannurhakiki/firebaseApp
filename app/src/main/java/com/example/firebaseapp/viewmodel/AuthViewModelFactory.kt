import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseapp.data.AuthRepository
import com.example.firebaseapp.data.UserPreferences

class AuthViewModelFactory(private val pref: UserPreferences,private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(pref, authRepository) as T
        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(pref, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
