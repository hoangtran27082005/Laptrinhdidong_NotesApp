package com.example.myprojectonclass.ui.theme

fun main()
{
    var sum:Int=0
    val n:Int=5
    for (i in 1 until n)
    {
        sum += i
    }
    println("Tổng=$sum")

    // hien thi danh sach
    var dsSanPham= arrayOf("kotlin","java","c#","python","R")
    for (item in dsSanPham)
        println(item)

    for (i in dsSanPham.indices)
        println("Sản phâm thứ $i có tên "+dsSanPham[i])
    println("-------------")
    for ((index,value) in dsSanPham.withIndex())
    {
        println("Sản phẩm thứ $index có tên $value")
    }
}