import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class ExceptionDialog(alertType: AlertType, oe: Exception) extends Alert(alertType) {
    title = "Exception Dialog"
    headerText = "An error occurred"
    contentText = oe.getMessage

    showAndWait()
}
