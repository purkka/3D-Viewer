package graphics.scene

import scalafx.scene.control.{Label, Slider}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Pane}
import scalafx.scene.paint.Color

abstract class ColorChooser(initialColor: Color) extends GridPane {
    val redSlider = new Slider(0, 1, initialColor.getRed)
    val greenSlider = new Slider(0, 1, initialColor.getGreen)
    val blueSlider = new Slider(0, 1, initialColor.getBlue)

    redSlider.valueProperty.addListener(_ => updateColor())
    greenSlider.valueProperty.addListener(_ => updateColor())
    blueSlider.valueProperty.addListener(_ => updateColor())

    private val redLabel = makeLabel()
    private val greenLabel = makeLabel()
    private val blueLabel = makeLabel()

    val colorPatch = new Pane()

    val c1 = new ColumnConstraints()
    c1.setPercentWidth(40)
    val c2 = new ColumnConstraints()
    c2.setPercentWidth(40)
    val c3 = new ColumnConstraints()
    c3.setPercentWidth(20)

    this.getColumnConstraints.addAll(c1, c2, c3)

    this.add(redSlider, 0, 3)
    this.add(greenSlider, 0, 4)
    this.add(blueSlider, 0, 5)
    this.add(redLabel, 1, 3)
    this.add(greenLabel, 1, 4)
    this.add(blueLabel, 1, 5)
    this.add(colorPatch, 2, 0, 1, 6)

    // function to run when color is updated
    def update(c: Color): Unit

    private def updateColor(): Unit = {
        val color = Color.rgb((redSlider.getValue * 255).toInt, (greenSlider.getValue * 255).toInt, (blueSlider.getValue * 255).toInt)
        val colorString: String = f"#${(255 * color.getRed).toInt}%02x${(255 * color.getGreen).toInt}%02x${(255 * color.getBlue).toInt}%02x"
        colorPatch.setStyle("-fx-background-color:" + colorString)
        redLabel.setText(f"Red = ${color.getRed}%1.3f")
        greenLabel.setText(f"Green = ${color.getGreen}%1.3f")
        blueLabel.setText(f"Blue = ${color.getBlue}%1.3f")

        update(color)
    }

    private def makeLabel(): Label = {
        val text = new Label()
        text.setStyle("-fx-padding: 6px 10px 6px 10px")
        text
    }

    this.updateColor()
}
