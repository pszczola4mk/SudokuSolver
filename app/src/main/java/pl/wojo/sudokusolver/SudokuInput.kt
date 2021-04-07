package pl.wojo.sudokusolver

import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity

class SudokuInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku_input)
        val sudoku: TableLayout = findViewById<TableLayout>(R.id.tbl_sudoku)

        for (i in 1..9) {
            val row: TableRow = TableRow(this)
            row.id = i * 100
            row.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            for (j in 1..9) {
                val editText: EditText = EditText(this)
                editText.width = 50
                editText.height = 50
                editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                editText.id = i * j
                editText.setPadding(5, 5, 5, 5)
                row.addView(editText,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            }
            sudoku.addView(row, TableLayout.LayoutParams( TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.MATCH_PARENT))
        }
    }
}