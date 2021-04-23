import graphics.mesh.CorruptedObjFileException
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class ExceptionDialog(alertType: AlertType, oe: CorruptedObjFileException) extends Alert(alertType) {
    title = "Exception Dialog"
    headerText = "An error occurred"
    contentText = oe.getMessage

    showAndWait()
}
