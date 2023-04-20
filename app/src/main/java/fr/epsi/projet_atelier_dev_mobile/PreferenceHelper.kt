package fr.epsi.projet_atelier_dev_mobile

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PreferenceHelper(context: Context) {

    private val PREFS_NAME = "your_app_prefs"
    private val USER_KEY = "user_key"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

    // Save User object as a JSON string
    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit().putString(USER_KEY, userJson).apply()
    }

    // Retrieve User object from SharedPreferences
    fun getUser(): User? {
        val userJson = prefs.getString(USER_KEY, null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    fun clearSharedPreferences() {
        prefs.edit().clear().commit()
    }
}
