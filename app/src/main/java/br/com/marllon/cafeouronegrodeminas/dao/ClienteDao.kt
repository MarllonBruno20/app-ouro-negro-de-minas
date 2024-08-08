package br.com.marllon.cafeouronegrodeminas.dao

import android.content.ContentValues.TAG
import android.util.Log
import br.com.marllon.cafeouronegrodeminas.model.Cliente
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class ClienteDao {
    private val banco = FirebaseFirestore.getInstance()
    val db = Firebase.firestore

    fun insere(cliente: Cliente) {
        db.collection("clientes")
            .add(cliente)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Cliente adicionado com o ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao adicionar cliente", e)
            }
    }

    suspend fun recuperaTodos(): List<Cliente> = coroutineScope {
        try {
            val result = banco.collection("clientes").get().await()
            result.toObjects(Cliente::class.java)
        } catch (e: Exception) {
            Log.w(TAG, "Erro ao recuperar clientes: ", e)
            emptyList<Cliente>()
        }
    }

    fun atualiza(cliente: Cliente) {
        db.collection("clientes")
            .whereEqualTo("cpf", cliente.cpf)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("clientes").document(document.id)
                        .set(cliente)
                        .addOnSuccessListener {
                            Log.d(TAG, "Cliente atualizado com sucesso")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Erro ao atualizar cliente", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao encontrar cliente: ", e)
            }
    }

    suspend fun recuperaCPFs(): List<String> = coroutineScope {
        try {
            val result = db.collection("clientes").get().await()
            result.documents.map { it.getString("cpf") ?: "" }.filter { it.isNotEmpty() }
        } catch (e: Exception) {
            Log.w(TAG, "Erro ao recuperar CPFs: ", e)
            emptyList()
        }
    }

}