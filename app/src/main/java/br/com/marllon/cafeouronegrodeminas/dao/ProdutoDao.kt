package br.com.marllon.cafeouronegrodeminas.dao

import android.content.ContentValues.TAG
import android.util.Log
import br.com.marllon.cafeouronegrodeminas.model.Produto
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class ProdutoDao {
    private val banco = FirebaseFirestore.getInstance()
    val db = Firebase.firestore

    fun insere(produto: Produto) {
        val produtoId = db.collection("produtos").document().id
        val produtoComId = produto.copy(idProduto = produtoId)

        db.collection("produtos")
            .document(produtoId)
            .set(produtoComId)
            .addOnSuccessListener {
                Log.d(TAG, "Produto adicionado com sucesso com ID: $produtoId")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao adicionar produto", e)
            }
    }

    suspend fun recuperaTodos(): List<Produto> = coroutineScope {
        try {
            val result = banco.collection("produtos").get().await()
            val produtos = result.toObjects(Produto::class.java)
            Log.d("ProdutoDao", "Produtos recuperados: ${produtos.size}")
            produtos
        } catch (e: Exception) {
            Log.w(TAG, "Erro ao recuperar produtos: ", e)
            emptyList<Produto>()
        }
    }

    fun atualiza(produto: Produto) {
        db.collection("produtos")
            .whereEqualTo("idProduto", produto.idProduto)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("produtos").document(document.id)
                        .set(produto)
                        .addOnSuccessListener {
                            Log.d(TAG, "Produto atualizado com sucesso")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Erro ao atualizar produto", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao encontrar produto: ", e)
            }
    }

}