package pl.wojo.sudokusolver

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.wojo.sudokusolver.api.ServiceClient
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"
    var photoButton: Button? = null
    var analyzeButton: Button? = null
    var byHandButton: Button? = null
    var fileUploadButton: Button? = null
    var imageView: ImageView? = null
    var textView: TextView? = null
    var imageBitmap: Bitmap? = null
    val analyzer: SudokuAnalyzer = SudokuAnalyzer()
    private val REQUEST_IMAGE_CAPTURE = 1
    private val IMAGE_UPLOAD = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.imageView = findViewById<ImageView>(R.id.image)
        this.photoButton = findViewById<Button>(R.id.photo)
        this.photoButton?.setOnClickListener(View.OnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        })
        this.textView = findViewById<TextView>(R.id.sudoku)
        this.analyzeButton = findViewById<Button>(R.id.ocr)
        this.analyzeButton?.setOnClickListener(View.OnClickListener { this.analyze() })
        this.byHandButton = findViewById<Button>(R.id.btn_grid)
        this.byHandButton?.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this, SudokuInput::class.java)
            this.startActivity(myIntent)
        })
        this.fileUploadButton = findViewById<Button>(R.id.btn_file)
        this.fileUploadButton?.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            this.startActivityForResult(myIntent, IMAGE_UPLOAD)
        })
    }


    private fun analyze() {
        val thread = Thread {
            val client = ServiceClient()
            this.textView?.text = client.sendImg(this.imageBitmap)
        }
        thread.start()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            this.imageBitmap = data?.extras?.get("data") as Bitmap
            this.imageView?.setImageBitmap(imageBitmap)
            this.textView?.text = "Fotka ok"
        }
        if (requestCode == IMAGE_UPLOAD && resultCode == RESULT_OK) {
            if (data != null) { // e.g. "back" pressed"
                val contentURI: Uri = Uri.parse(data.dataString)
                val cr = contentResolver
                val instream: InputStream? = cr.openInputStream(contentURI)
                val options = BitmapFactory.Options()
                options.inSampleSize = 8
                this.imageBitmap = BitmapFactory.decodeStream(instream, null, options)
                this.imageView?.setImageBitmap(this.imageBitmap)
                this.textView?.text = "Fotka ok"
            }
        }
    }
}