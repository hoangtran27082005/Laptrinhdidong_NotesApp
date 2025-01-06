package com.example.myprojectonclass.ui.theme

fun main(){
    var x:Long=100L
    var y:Double=113.5
    var f:Float=113.5f
    var i:Int =113
    var s:Short=8
    var b:Byte=1
    var c:Char='c'
    var kq:Boolean=true
    var ten:String="vku.udn.vn"
    var address:String="""
    số 90 ngũ 
    hành sơn
    """
    println(address)

    var arrX:IntArray= intArrayOf(1,2,3,5)
    println(arrX[1])
    var arrY:DoubleArray= doubleArrayOf(1.5,2.6,9.0,10.3)
    println(arrY[3])
    var arrC:CharArray= charArrayOf('a','b','c')
    println(arrC[0])

    val PI:Double =3.14
    //PI=3.15//không được phép vì PI là readonly
    val numberOfDogs = 3
    val numberOfCats:Int = 2
    val stringnumber = "I have $numberOfDogs dogs" + " and $numberOfCats cats"
    println(stringnumber)



}