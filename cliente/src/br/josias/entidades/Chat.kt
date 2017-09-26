package br.josias.entidades

import java.io.OutputStream
import java.net.Socket
import java.util.*

/**
 * Created by alunom35 on 18/09/2017.
 */
class Chat(val ipServidor: String, val portaServidor: Int, val usuario: String) : BaseIo, Runnable {
    var mensagens: MutableList<String>
    var companheiros: MutableList<String>
    lateinit var conexao: Socket

    init {
        mensagens = LinkedList()
        companheiros = LinkedList()
    }

    fun deslogar(entrada: String, saida: OutputStream): Boolean {
        val msgLogg = "logoff;${usuario};publica"
        enviarPelaCoenxao(saida, msgLogg)
        return false
    }

    fun mensagemPublica(entrada: String, saida: OutputStream) {
        val msgLogg = "mensagem;${usuario};publica;${entrada}"
        enviarPelaCoenxao(saida, msgLogg)
    }

    fun mensagemPrivada(entrada: String, saida: OutputStream) {
        println("digite o usuario que vai receber a mensagem")
        val destinatario = readLine()
        println("digite a sua mensagem")
        val mensagem = readLine()
        val msg = "mensagem;${usuario};privado;${mensagem};${destinatario}"
        enviarPelaCoenxao(saida, msg)
    }

    fun listaUsuario(msg: String, saida: OutputStream) {
        val msg = "listarUsuario;${usuario};publica"
        enviarPelaCoenxao(saida,msg)
    }

    fun iniciarChat(): Feed{
        conexao = Socket(ipServidor, portaServidor)
        val saida = conexao.getOutputStream()
        var teste = "novo;${usuario};publica"
        enviarPelaCoenxao(saida, teste)
        val feed = Feed(conexao.getInputStream(), saida, this)
        val f = Thread(feed)
        f.start()
        return feed
    }

    override fun run() {
        iniciarChat()
    }




}