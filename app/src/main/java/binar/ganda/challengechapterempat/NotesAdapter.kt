package binar.ganda.challengechapterempat

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_adapter_notes.view.*
import kotlinx.android.synthetic.main.custom_dialog_edit.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class NotesAdapter(private val listNotes: List<Notes>): RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    private var notesDB : NotesDatabase? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter_notes, parent, false)
        return   ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.title_tv.text = listNotes[position].title
        holder.itemView.desc_tv.text = listNotes[position].desc


        //DeleteData
        holder.itemView.delete_btn.setOnClickListener {

            //get NotesDatabase
            notesDB = NotesDatabase.getInstance(it.context)

            AlertDialog.Builder(it.context)
                .setTitle("Hapus Data")
                .setMessage("Yakin Hapus Data")
                .setPositiveButton("Ya") { _: DialogInterface, _: Int ->
                    GlobalScope.async {
                        val result = notesDB?.notesDao()?.deleteNotes(listNotes[position])

                        (holder.itemView.context as MainActivity).runOnUiThread {
                            if (result != 0 ) {
                                Toast.makeText(it.context, "Data ${listNotes[position].title} Terhapus", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(it.context, "Data ${listNotes[position].title} Gagal terhapus", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Tidak") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }
                .show()

        }

        //Edit Data
        holder.itemView.edit_btn.setOnClickListener {

            //get NotesDatabase
            notesDB = NotesDatabase.getInstance(it.context)

            //create custom layout
            val view = LayoutInflater.from(it.context).inflate(R.layout.custom_dialog_edit, null, false)
            val customDialog = AlertDialog.Builder(it.context)
            customDialog.setView(view)
            customDialog.create()

            view.et_edit_title.setText(listNotes[position].title)
            view.et_edit_desc.setText(listNotes[position].desc)

            view.edit_dialog_btn.setOnClickListener {
                val newTitle = view.et_edit_title.text.toString()
                val newDesc = view.et_edit_desc.text.toString()

                listNotes[position].title = newTitle
                listNotes[position].desc = newDesc

                GlobalScope.async {
                    val result = notesDB?.notesDao()?.insertNotes(listNotes[position])
                    (view.context as MainActivity).runOnUiThread {
                        if (result != 0.toLong()){
                            Toast.makeText(it.context, "Berhasil", Toast.LENGTH_LONG).show()
                            (view.context as MainActivity).recreate()
                        } else {
                            Toast.makeText(it.context, "Gagal", Toast.LENGTH_LONG).show()
                        }
                    }

                }

            }
        }
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

}