package utils

import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JPanel

internal class QuickDrawPanel : JPanel {
    var myImage: BufferedImage? = null
    var mySize = Dimension()

    constructor()
    constructor(image: BufferedImage?) {
        this.myImage = image
        setComponentSize()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.drawImage(myImage, 0, 0, this)
    }

    override fun getPreferredSize(): Dimension {
        return mySize
    }

    fun setImage(bi: BufferedImage?) {
        myImage = bi
        setComponentSize()
        repaint()
    }

    private fun setComponentSize() {
        if (myImage != null) {
            mySize.width = myImage!!.width
            mySize.height = myImage!!.height
            revalidate() // signal parent/scrollpane
        }
    }
}