package pl.wojo.sudokusolver

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var photoButton: Button? = null
    var analyzeButton: Button? = null
    var byHandButton: Button? = null
    var imageView: ImageView? = null
    var textView: TextView? = null
    var imageBitmap: Bitmap? = null
    val analyzer: SudokuAnalyzer = SudokuAnalyzer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.imageView = findViewById<ImageView>(R.id.image)
        this.photoButton = findViewById<Button>(R.id.photo)
        this.photoButton?.setOnClickListener(View.OnClickListener { this.dispatchTakePictureIntent() })
        this.textView = findViewById<TextView>(R.id.sudoku)
        this.analyzeButton = findViewById<Button>(R.id.ocr)
        this.analyzeButton?.setOnClickListener(View.OnClickListener { this.analyze() })
        this.byHandButton = findViewById<Button>(R.id.btn_grid)
        this.byHandButton?.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this, SudokuInput::class.java)
            this.startActivity(myIntent) })
    }

    private fun analyze() {
        analyzer.imageBitmap = this.imageBitmap
        analyzer.textView = this.textView
        analyzer.captureText()
    }


    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            this.imageBitmap = data?.extras?.get("data") as Bitmap
            this.imageView?.setImageBitmap(imageBitmap)
            this.textView?.text = "Fotka ok"
        }
    }
}