import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.viewmodel.ChatViewModel
import com.example.firebaseapp.viewmodel.ProfileViewModel

class MainViewModelFactory(private val pref: UserPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel() as T

        }else if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
