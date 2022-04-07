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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class NotesAdapter(private val listNotes : ArrayList<Notes>): RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    private var notesDB : NotesDatabase? = null

    class ViewHolder(Layout: View) : RecyclerView.ViewHolder(Layout) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter_notes, parent, false)
        return   ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.id_tv.text = listNotes[position].id.toString()
        holder.itemView.title_tv.text = listNotes[position].title.toString()
        holder.itemView.desc_tv.text = listNotes[position].desc.toString()

        holder.itemView.delete_btn.setOnClickListener {

            notesDB = NotesDatabase.getInstance(it.context)

            AlertDialog.Builder(it.context)
                .setTitle("Hapus Data")
                .setMessage("Yakin Hapus Data")
                .setPositiveButton("Ya") { dialogInterface: DialogInterface, i : Int ->
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
                .setNegativeButton("Tidak") { dialogInterface: DialogInterface, i : Int ->
                    dialogInterface.dismiss()
                }
                .show()

        }

        holder.itemView.edit_btn.setOnClickListener {

            notesDB = NotesDatabase.getInstance(it.context)

            val view = LayoutInflater.from(it.context).inflate(R.layout.custom_dialog_edit, null, false)
            val customDialog = AlertDialog.Builder(it.context)
            customDialog.setView(view)
            customDialog.create()

            view.edit_dialog_btn.setOnClickListener {
                val newTitle = view.et_edit_title.text.toString()
                val newDesc = view.et_edit_desc.text.toString()

                listNotes[position].title = newTitle
                listNotes[position].desc = newDesc

                GlobalScope.async {
                    val result = notesDB?.notesDao()?.insertNotes(listNotes[position])
                    (view.edit_dialog_btn.context as MainActivity).runOnUiThread {
                        if (result != 0.toLong()){
                            Toast.makeText(it.context, "Berhasil", Toast.LENGTH_LONG).show()
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