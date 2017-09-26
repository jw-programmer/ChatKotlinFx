import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Principal : Application() {

    override fun start(primaryStage: Stage) {
        val principal: VBox = FXMLLoader.load(javaClass.getResource("br/josias/fxml/view_cliente.fxml"))
        val root = Scene(principal)
        primaryStage.scene = root
        primaryStage.title = "Cliente"
        primaryStage.setOnCloseRequest {
            Platform.exit()
            System.exit(0)
        }
        primaryStage.show()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(Principal::class.java,*args)
        }
    }
}
