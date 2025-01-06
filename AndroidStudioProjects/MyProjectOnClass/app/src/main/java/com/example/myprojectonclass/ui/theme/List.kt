package com.example.myprojectonclass.ui.theme

fun main(){
    var ds1:MutableList<Int> = mutableListOf(1,2,3,4)


    println(ds1)
    ds1.clear()
    println(ds1)
    var ds2:List<Int> = listOf(1,2,3,4)

    println(ds2)
    /*ds2.clear()
    println(ds2)*/
    var ds3:MutableList<Int> =mutableListOf(5,6,1,0,4)
    ds3.add(4)
    ds3.add(5)
    println(ds3)
}