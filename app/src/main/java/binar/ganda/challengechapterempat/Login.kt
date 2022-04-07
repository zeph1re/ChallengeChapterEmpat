package binar.ganda.challengechapterempat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*


class Login : Fragment() {

    private lateinit var shared : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = this.requireActivity()
            .getSharedPreferences("Notes", Context.MODE_PRIVATE)

        login_btn.setOnClickListener {

            val emailLogin = et_email.text.toString()
            val passwordLogin = et_password.text.toString()

            val getEmail =shared.getString("EMAIL", "")
            val getPassword = shared.getString("PASSWORD", "")

            if (emailLogin == getEmail && passwordLogin == getPassword) {
                view?.let {
                    Navigation.findNavController(view).navigate(R.id.action_login_to_home2)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Email dan Password anda salah, anda dapat Registrasi terlebih dahulu",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        belum_punya_punya_akun_btn.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_login_to_registrasi)
            }
        }
    }
}