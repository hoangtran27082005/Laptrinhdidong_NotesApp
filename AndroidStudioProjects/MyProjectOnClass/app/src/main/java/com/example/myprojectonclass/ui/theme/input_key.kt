package com.example.myprojectonclass.ui.theme

fun main(){
    var a:Double=0.0
    var b:Double=0.0
    println("Nhập a:")
    var s= readLine()
    if(s!=null)
        a=s.toDouble()
    println("Nhập b:")
    s= readLine()
    if(s!=null)
        b=s.toDouble()
    if(a==0.0 && b==0.0)
    {
        println("Phương trình vô số nghiệm")
    }
    else if(a==0.0 && b!=0.0)
    {
        println("Phương trình vô nghiệm")
    }
    else
    {
        var x=-b/a
        println("No x="+x)
    }
}