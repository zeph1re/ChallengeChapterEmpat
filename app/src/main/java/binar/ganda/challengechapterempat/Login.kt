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
    private var userDB : UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = requireContext().getSharedPreferences("USER", Context.MODE_PRIVATE)

        belum_punya_punya_akun_btn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_to_registrasi)
        }

        login_btn.setOnClickListener {
            if (et_email.text.isNotEmpty() && et_password.text.isNotEmpty()){

                userDB = UserDatabase.getInstance(requireContext())

                val emailLogin = et_email.text.toString()
                val passwordLogin = et_password.text.toString()

                val user = userDB?.userDao()?.getUserData(emailLogin, passwordLogin)

                if (user.isNullOrEmpty()) {
                    Toast.makeText(requireContext(),"Email/Password Salah", Toast.LENGTH_LONG).show()
                } else {
                    val sf = shared.edit()
                    sf.putString("USERNAME", user)
                    sf.apply()

                    Navigation.findNavController(view).navigate(R.id.action_login_to_home2)
                }

            } else {
                Toast.makeText(requireContext(),"Isi Email dan Password", Toast.LENGTH_LONG).show()
            }
        }

    }


}
