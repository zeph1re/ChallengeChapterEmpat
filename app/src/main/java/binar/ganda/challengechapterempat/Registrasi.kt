package binar.ganda.challengechapterempat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.custom_dialog_add.*
import kotlinx.android.synthetic.main.fragment_registrasi.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class Registrasi : Fragment() {

    private lateinit var shared: SharedPreferences
    private var userDB: UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = requireContext().getSharedPreferences("USER", Context.MODE_PRIVATE)

        register_btn.setOnClickListener {

            if (et_username.text.isNotEmpty() && et_email.text.isNotEmpty() && et_confirm_password.text.isNotEmpty() && et_password.text.isNotEmpty()) {
                val username = et_username.text.toString()
                val email = et_email.text.toString()
                val confirmPass = et_confirm_password.text.toString()
                val password = et_password.text.toString()

                val saveShared = shared.edit()
                saveShared.putString("USERNAME", username)
                saveShared.putString("EMAIL", email)
                saveShared.putString("CONFIRM PASS", confirmPass)
                saveShared.putString("PASSWORD", password)
                saveShared.apply()

                GlobalScope.async {
                    val user = User(null, username, email, confirmPass, password)

                    val result = userDB?.userDao()?.insertUser(user)

                    activity?.runOnUiThread {
                        if (result != 0.toLong()) {
                            Toast.makeText(requireContext(), "Berhasil", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_LONG).show()
                        }
                    }
                    Navigation.findNavController(view).navigate(R.id.action_registrasi_to_login)
                }
            } else {
                Toast.makeText(requireContext(), "Anda Gagal Registrasi", Toast.LENGTH_LONG)
                    .show()
                Navigation.findNavController(view).navigate(R.id.action_registrasi_to_login)
            }
        }
    }
}


