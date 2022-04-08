package binar.ganda.challengechapterempat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SharedMemory
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_dialog_add.*
import kotlinx.android.synthetic.main.custom_dialog_add.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class Home : Fragment() {

    private lateinit var shared : SharedPreferences
    private var notesDB : NotesDatabase? = null
    private var userDB : UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //view home_tv at Home Fragment
        shared = requireContext().getSharedPreferences("USER", Context.MODE_PRIVATE)
        val username = shared.getString("USERNAME", "")
        welcome_tv.text = "Welcome, $username"

        //add button to add notes to recycler view
        add_btn.setOnClickListener {
            val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.custom_dialog_add, null, false)
            val customDialog = AlertDialog.Builder(requireContext())
            customDialog.setView(view)
            customDialog.create()

            view.add_dialog_btn.setOnClickListener {
                GlobalScope.async {
                    val title = et_add_title.text.toString()
                    val desc = et_add_desc.text.toString()
                    val notes = Notes(null, title, desc)

                    val result = notesDB?.notesDao()?.insertNotes(notes)

                    activity?.runOnUiThread {
                        if (result != 0.toLong()) {
                            Toast.makeText(requireContext(), "Berhasil", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            notesDB = NotesDatabase.getInstance(requireContext())

            //Logout
            logout_btn.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Yakin ingin Logout?")
                    .setNegativeButton("Tidak"){ dialogInterface : DialogInterface, i : Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("YA") { dialogInterface : DialogInterface, i : Int ->
                        val sharedPreferences = requireContext().getSharedPreferences("USER", Context.MODE_PRIVATE)
                        val sf = sharedPreferences.edit()
                        sf.clear()
                        sf.apply()

                        val pindah = activity?.intent
                        activity?.finish()
                        startActivity(pindah)

                    }.show()
            }
        }

    }

    private fun getDataNotes() {
        notes_rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val listNotes = notesDB?.notesDao()?.getAllNotes()

        GlobalScope.launch {
            activity?.runOnUiThread {
                listNotes.let {
                    NotesAdapter(it!! as ArrayList<Notes>).also { notes_rv.adapter = it }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        getDataNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        NotesDatabase.destroyInstance()
    }
}