package br.josias.controller

import br.josias.entidades.ConexaoUsuario
import br.josias.entidades.Servidor
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javax.swing.JOptionPane

class ViewServidor {

    lateinit var servidor: Servidor

    lateinit var t: Thread

    @FXML
    lateinit var buttonDesconetar: Button
    @FXML
    lateinit var onButton: Button
    @FXML
    lateinit var offButton: Button
    @FXML
    lateinit var refresh: Button
    @FXML
    lateinit var porta: TextField
    @FXML
    lateinit var listConexoes: ListView<ConexaoUsuario>
    @FXML
    lateinit var onOffButtons: ButtonBar


    fun iniciarServico() {
        if (porta.text == "") {
            JOptionPane.showMessageDialog(null, "Você não passou nenhuma porta", "porta vazia", JOptionPane.ERROR_MESSAGE)
        } else {
            servidor = Servidor("localhost", porta.text.toInt())
            t = Thread(servidor)
            t.start()
            refresh.isDisable = false
            buttonDesconetar.isDisable = false

        }
    }

    fun desconetarUsuario(){
      if(listConexoes.selectionModel.selectedItem == null){
          JOptionPane.showMessageDialog(null, "Você não selecionou nenhum usuario" +
                  "", "selecao", JOptionPane.ERROR_MESSAGE)
      }else{
          val user = listConexoes.selectionModel.selectedItem
          val msg = "logoff;${user.id};publico"
          servidor.atualizarStatus(msg)
          servidor.removerObservavel(user)
          atualizar()
      }
    }

    fun atualizar() {
         val processos = servidor.observaveis.map { it as ConexaoUsuario }
         listConexoes.items = FXCollections.observableList(processos)
    }
}




