package pl.wojo.sudokusolver

import android.graphics.Bitmap
import android.widget.TextView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

class SudokuAnalyzer() {

    var imageBitmap: Bitmap? = null
    var textView: TextView? = null
    var result: Array<CharArray>? = null
    var isResult: Boolean = false

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

    fun solveSudoku(board: Array<CharArray>) {
        val res = solveSudoku(board, 0, 0)
        this.isResult = res == 1
    }

    fun convertedResult(): MutableList<String> {
        val res: MutableList<String> = mutableListOf()
        for (i in 0..8) {
            for (j in 0..8) {
                res.add(this.result!![i][j].toString())
            }
        }
        return res
    }

    private fun solveSudoku(board: Array<CharArray>, x: Int, y: Int): Int {
        var board = board
        var x = x
        var y = y
        return if (board[x][y] == '.') {
            val allowed = findAllowed(board, x, y) //znajdz dopuszczalne liczby
            if (allowed.size == 0) {
                //nic nie znalazł, brak rozwiązania
                return 0
            }
            for (c in allowed) {
                if (c != '.') {
                    val copy = copy(board)
                    copy[x][y] = c
                    val result = solveSudoku(copy, x, y)
                    if (result == 1) { //mam rozwiązanie
                        board = copy
                        return 1
                    }
                }
            }
            0
        } else { //gdy uzupełnione to idź dalej
            if (x == 8) {
                if (y == 8) {
                    this.result = board
                    return 1 //koniec, przeszedł wszystkie pola
                }
                y = y + 1
            } else {
                if (y == 8) {
                    x = x + 1
                    y = 0
                } else {
                    y = y + 1
                }
            }
            solveSudoku(board, x, y)
        }
    }

    private fun copy(board: Array<CharArray>): Array<CharArray> {
        val copy = Array(9) { CharArray(9) }
        for (i in 0..8) {
            for (j in 0..8) {
                copy[i][j] = board[i][j]
            }
        }
        return copy
    }

    private fun findAllowed(board: Array<CharArray>, x: Int, y: Int): CharArray {
        val allowed = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
        for (c in board[x]) {
            clear(allowed, c)
        }
        for (c in board.map { it[y] }) {
            clear(allowed, c)
        }
        val xMod = x % 3
        val yMod = y % 3
        for (i in x - xMod until x - xMod + 3) {
            for (j in y - yMod until y - yMod + 3) {
                val c = board[i][j]
                clear(allowed, c)
            }
        }
        return allowed
    }

    private fun clear(allowed: CharArray, c: Char) {
        if (c != '.') {
            allowed[(c.toString() + "").toInt() - 1] = '.'
        }
    }


}