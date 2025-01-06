package com.example.myprojectonclass.ui.theme
fun main() {
    var n:Int = 5
    var gt:Int=1
    var i:Int = 1
    while (i <= n)
    {
        gt *= i
        i++
    }
    println("$n! =$gt")

    // dùng do while
    var gt1:Int = 1;
    i=1
    do
    {
        gt1 *= i
        i++
    }while (i<=n)
    println("$n! =$gt1")
    // repeat
    repeat(2) {
        print("Hello!")
    }

}