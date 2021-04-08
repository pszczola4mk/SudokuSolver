package pl.wojo.sudokusolver

import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity

class SudokuInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku_input)
        //
        val sudoku: TableLayout = findViewById<TableLayout>(R.id.tbl_sudoku)
        for (i in 1..9) {
            val row: TableRow = TableRow(this)
            row.id = i * 100
            for (j in 1..9) {
                val editText: EditText = EditText(this)
                editText.width = 100
                editText.height = 100
                editText.id = i * j
                row.addView(editText,TableRow.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            }
            sudoku.addView(row, TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT)
        }
    }
}