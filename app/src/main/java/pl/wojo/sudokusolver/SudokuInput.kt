package pl.wojo.sudokusolver

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SudokuInput : AppCompatActivity() {
    val sudokuVals: MutableList<EditText> = mutableListOf()

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
                row.addView(editText, TableRow.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                sudokuVals.add(editText)
            }
            sudoku.addView(row, TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
        }

        val resolveButton = findViewById<Button>(R.id.btn_resolve)
        resolveButton.setOnClickListener(View.OnClickListener { this.resolve() })
    }

    private fun resolve() {
        var analyzer: SudokuAnalyzer = SudokuAnalyzer()
        val sudoku: Array<CharArray> = Array(9) { CharArray(9) }
        var row: CharArray = CharArray(9)
        var counter: Int = 0
        for (editText: EditText in sudokuVals) {
            if (counter % 9 == 0) {
                row = CharArray(9)
                val idx: Int = counter / 9
                sudoku[idx] = row
            }
            val idx: Int = counter % 9
            if (editText.text.isNotEmpty()) {
                row[idx] = editText.text.toList()[0]
            } else {
                row[idx] = '.'
            }
            counter = counter + 1
        }
        analyzer.solveSudoku(sudoku)
        if (analyzer.isResult) {
            val solvedSudoku: MutableList<String> = analyzer.convertedResult()
            for (i in 0..80) {
                sudokuVals[i].setText(solvedSudoku[i], TextView.BufferType.EDITABLE)
            }
        }
    }
}