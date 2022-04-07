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
import kotlinx.android.synthetic.main.fragment_registrasi.*


class Registrasi : Fragment() {

    private lateinit var shared : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = requireContext().getSharedPreferences("Notes", Context.MODE_PRIVATE)

        register_btn.setOnClickListener {

            val username = et_username.text.toString()
            val email = et_email.text.toString()
            val confirmPass = et_confirm_password.text.toString()
            val password = et_password.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && confirmPass.isNotEmpty() && password.isNotEmpty()) {
                val saveShared = shared.edit()
                saveShared.putString("USERNAME", username)
                saveShared.putString("EMAIL", email)
                saveShared.putString("CONFIRM PASS", confirmPass)
                saveShared.putString("PASSWORD", password)
                saveShared.apply()

                Navigation.findNavController(view).navigate(R.id.action_registrasi_to_login)

            } else {
                Toast.makeText(requireContext(), "Anda Gagal Registrasi", Toast.LENGTH_LONG).show()
            }

            Navigation.findNavController(view).navigate(R.id.action_registrasi_to_login)

        }
    }

}