package binar.ganda.challengechapterempat

import androidx.room.*


@Dao
interface NotesDao {
    @Insert
    fun insertNotes(notes: Notes) : Long

    @Query ("SELECT * FROM Notes")
    fun getAllNotes() : List<Notes>

    @Update
    fun updateNotes(notes: Notes) : Int

    @Delete
    fun deleteNotes (notes: Notes) : Int

}