package com.eliseylobanov.cloudnotes.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.User
import com.eliseylobanov.cloudnotes.errors.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val NOTES_COLLECTION = "notes"
private const val USERS_COLLECTION = "users"
const val TAG = "FireStoreDatabase"

class FireStoreProvider : RemoteDataProvider {

    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)
    private val result = MutableStateFlow<List<Note>?>(null)
    private val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    private var subscribedOnDb = false

    override fun observeNotes(): Flow<List<Note>> {
        if (!subscribedOnDb) subscribeForDbChanging()
        return result.filterNotNull()
    }

    override fun getCurrentUser(): User? {
        return currentUser?.run { User(displayName, email) }
    }

    override suspend fun addOrReplaceNote(newNote: Note) {
        suspendCoroutine<Unit> { continuation ->
            handleNotesReference(
                {
                    getUserNotesCollection()
                        .document(newNote.noteId.toString())
                        .set(newNote)
                        .addOnSuccessListener {
                            continuation.resumeWith(Result.success(Unit))
                        }
                        .addOnFailureListener {
                            continuation.resumeWithException(it)
                        }
                }, {
                    continuation.resumeWithException(it)
                }
            )
        }
    }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override suspend fun deleteNote(id: Long) {
        suspendCancellableCoroutine<Unit> { continuation ->
            getUserNotesCollection()
                .document(id.toString())
                .delete()
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(Unit))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    private fun subscribeForDbChanging() {
        handleNotesReference(
            {
                getUserNotesCollection().addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e(TAG, "Observe note exception:$e")
                    } else if (snapshot != null) {
                        val notes = mutableListOf<Note>()

                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }

                        result.value = notes
                    }
                }
                subscribedOnDb = true
            }, {
                Log.e(TAG, "Error getting reference while subscribed for notes")
            }
        )
    }

    private inline fun handleNotesReference(
        referenceHandler: (CollectionReference) -> Unit,
        exceptionHandler: (Throwable) -> Unit = {}
    ) {
        kotlin.runCatching {
            getUserNotesCollection()
        }
            .fold(referenceHandler, exceptionHandler)
    }

}