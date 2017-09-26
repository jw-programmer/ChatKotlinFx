package br.josias.entidades

import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by alunom35 on 18/09/2017.
 */
class Feed(val inputStream: InputStream, val outputStream: OutputStream, val chat: Chat) : BaseIo, Runnable {
    var controle = true
    override fun run() {
        while (controle) {
            var msg: String? = receberPelaConexao(inputStream)
            if (msg != null) {
                var opms = msg.split(";")
                if(opms[0] !="recusado" && opms[0] != "lista" && opms[0] != "novo") {
                    chat.mensagens.add(opms[1])
                    println(opms[1])
                }
                else if (opms[0] == "novo"){
                 chat.companheiros.add(opms[1])
                }
                else if (opms[0] == "lista"){
                    for (i in 1..opms.size - 1){
                        println(opms[i])
                    }
                }
                else if(opms[0] == "recusado") {
                    chat.mensagens.add(opms[1])
                    controle = false
                    println(opms[1])
                }
                if (opms[0] == "fim")
                    controle = false
            }else
              continue
        }
    }

}