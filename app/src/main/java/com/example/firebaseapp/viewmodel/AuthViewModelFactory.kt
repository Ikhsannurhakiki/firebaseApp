import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseapp.data.AuthRepository
import com.example.firebaseapp.data.UserPreferences

class AuthViewModelFactory(private val pref: UserPreferences,private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(pref, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
