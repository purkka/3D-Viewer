import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class ExceptionDialog(alertType: AlertType, exception: Exception) extends Alert(alertType) {
    title = "Exception Dialog"
    headerText = "An error occurred"
    contentText = exception.getMessage

    showAndWait()
}
