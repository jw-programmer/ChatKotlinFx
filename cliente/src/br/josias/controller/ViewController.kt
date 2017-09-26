package br.josias.controller

import br.josias.entidades.Chat
import br.josias.entidades.Feed
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import java.net.ConnectException
import javax.swing.JOptionPane

class ViewController {

    lateinit var chat: Chat

    lateinit var feed: Feed

    @FXML
    lateinit var txtUsuario: TextField
    @FXML
    lateinit var txtServidor: TextField
    @FXML
    lateinit var txtPorta: TextField
    @FXML
    lateinit var buttonConectar: Button
    @FXML
    lateinit var buttonEnviar: Button
    @FXML
    lateinit var buttonParticular: Button
    @FXML
    lateinit var buttonDesconectar: Button
    @FXML
    lateinit var txtMsg: TextArea
    @FXML
    lateinit var flowFeed: TextArea
    @FXML
    lateinit var userList: ListView<String>

    fun conectar() {
        try {
            if (txtPorta.text == "" || txtServidor.text == "" || txtUsuario.text == "") {
                JOptionPane.showMessageDialog(null, "Não deixe nenhum dos campos acima vazios", "Erro", JOptionPane.WARNING_MESSAGE)
            } else {
                chat = Chat(txtServidor.getText(), txtPorta.getText().toInt(), txtUsuario.text)
                feed = chat.iniciarChat()
                buttonEnviar.isDisable = false
                buttonParticular.isDisable = false
                buttonDesconectar.isDisable = false
                txtMsg.isDisable = false
                val tfedd = Thread({ atualizarFeed() })
                tfedd.start()
                val tuser = Thread({ atulizarUsuarios() })
                tuser.start()
            }
        } catch (e: ConnectException) {
            JOptionPane.showMessageDialog(null, "Ip ou porta digitados errados. Servidor não encontrado ou desligado", "aviso", JOptionPane.WARNING_MESSAGE)
        }
    }

    fun enviarMensagem() {
        val msg = txtMsg.text
        chat.mensagemPublica(msg, chat.conexao.getOutputStream())
        txtMsg.text = ""
    }

    fun atualizarFeed() {
        println("chego em atualizar feed")
        var listSize: Int = 0
        while (true) {
            if (chat.mensagens.size > listSize) {
                println("chegei aatualizar o feed")
                flowFeed.text = flowFeed.text + chat.mensagens.last() + "\n"
                listSize++
            } else
                continue
        }
    }

    fun atulizarUsuarios() {
        println("chego em atualizar chat")
        var listSize: Int = 0
        while (true) {
            if (chat.companheiros.size > listSize) {
                println("chegei a atualizar os usuarios")
                userList.items = FXCollections.observableList(chat.companheiros)
                listSize++
            } else if (chat.companheiros.size < listSize){
                userList.items = FXCollections.observableList(chat.companheiros)
                listSize--
            }
        }
    }
}