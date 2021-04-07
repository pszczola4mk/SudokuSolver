package pl.wojo.sudokusolver

import android.graphics.Bitmap
import android.widget.TextView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

class SudokuAnalyzer() {

    var imageBitmap: Bitmap? = null
    var textView: TextView? = null

    constructor(imageBitmap: Bitmap) : this() {
        this.imageBitmap = imageBitmap
    }

    fun captureText() {
        val image = InputImage.fromBitmap(this.imageBitmap, 0)
        val recognizer = TextRecognition.getClient()
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                textView?.text = visionText.text
            }
            .addOnFailureListener { e ->
                textView?.text = "BŁĄD"
            }
    }


}