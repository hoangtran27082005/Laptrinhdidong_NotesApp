package com.example.myprojectonclass.ui.theme


fun main(){
    println("toan tử gán")
    var a:Int=5
    a+=2
    println("x="+a)
    a-=2
    println("x="+a)
    a*=2
    println("x="+a)
    a/=2
    println("x="+a)
    a=7
    a%=3
    println("x="+a)
    // toan tử logic
    println("toan tu logic")
    var x:Boolean=true
    var y:Boolean=false
    var z:Boolean=false
    println("x="+x)
    println("y="+y)
    println("z="+z)
    println("x&&y="+(x&&y))
    println("x.and(y)="+x.and(y))
    println("x || y ="+(x || y))
    println("x.or(y)="+x.or(y))
    println("x || z ="+(x || z))
    println("x.or(z)="+x.or(z))
    println("x && z ="+(x && z))
    println("x.and(z)="+x.and(z))
    println("y || z ="+(y || z))
    println("y.or(z)="+y.or(z))
    println("y && z ="+(y && z))
    println("y.and(z)="+y.and(z))
    println("x && y && z ="+(x && y && z))
    println("x.and(y).and(z)="+x.and(y).and(z))
    println("x|| y||z ="+(x|| y||z))
    println("x.or(y).or(z)="+x.or(y).or(z))
    // đây là ví du toán tử số học
    println("Toán tử số học")
    var b:Int=8

    println("Phép cộng cách 1:"+a+"+"+b+"="+(a+b))
    println("Phép cộng cách 2:"+a+"+"+b+"="+(a.plus(b)))
    println("Phép trừ cách 1:"+a+"-"+b+"="+(a-b))
    println("Phép trừ cách 2:"+a+"-"+b+"="+(a.minus(b)))
    println("Phép nhân cách 1:"+a+"*"+b+"="+(a*b))
    println("Phép nhân cách 2:"+a+"*"+b+"="+(a.times(b)))
    println("Phép chia cách 1:"+a+"/"+b+"="+(a/b))
    println("Phép chia cách 2:"+a+"/"+b+"="+(a.div(b)))
    println("Lấy dư cách 1:"+a+"/"+b+"="+(a%b))
    println("Lấy dư cách 2:"+a+"/"+b+"="+(a.rem(b)))
    println(8.plus(9))
    // phan toan tu so sanh
    println("Toán tử so sánh")
    println(a==b)
    println(a.equals(b))
    println(!a.equals(b))
    println(a.compareTo(b))
    println(3.compareTo(3))
    println(3.compareTo(5))
    println(5.compareTo(3))
    // toán tử tăng giảm

    var c:Int=2
    a--
    b++
    var xx=a++ + ++b - --c + 7
    println("a="+a)
    println("b="+b)
    println("c="+c)
    println("z="+xx)

}