package pl.wojo.sudokusolver.model

class Image() {

    var name: String? = null
    var data: String? = null

    constructor(name: String, data: String) : this() {
        this.name = name
        this.data = data
    }
}