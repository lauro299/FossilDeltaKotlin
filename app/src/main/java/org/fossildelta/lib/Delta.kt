package org.fossildelta.lib

import unsigned.Uint
import unsigned.plus
import unsigned.toUint
import java.math.BigInteger
import java.util.zip.Checksum
import kotlin.experimental.and

class RollingHash(){
    companion object {
        val NHASH=16
    }

    var a = 0
    var b = 0
    var i = 0
    var z = ByteArray(NHASH)

    fun init(z:ByteArray, pos:Int){
        var a = 0
        var b = 0
        var x:Byte
        for(i in 0 until NHASH){
            x = z[pos+i]
            a = (a + x) and (0xffff)
            b = (b + (NHASH-i)*x) and (0xffff)
            this.z[i] = x
        }
        this.a = a and 0xffff
        this.b = b and 0xffff
        this.i = 0
    }

    fun next(c:Byte){
        var old= this.z[this.i]
        this.z[this.i] = c
        this.i = (this.i+1) and (NHASH-1)
        this.a = (this.a  - old  +c) and 0xffff
        this.b = (this.b - NHASH*old +this.a) and 0xffff
    }

    fun value() = ((this.a and 0xffff) or (this.b and 0xffff) shl 16) ushr 0
}

class Reader(val array:ByteArray){
    var zDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~".toByteArray().map{Uint(it.toInt())}
    var zValue = intArrayOf(
            -1, -1, -1, -1, -1, -1, -1, -1,   -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1,   -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1,   -1, -1, -1, -1, -1, -1, -1, -1,
            0,  1,  2,  3,  4,  5,  6,  7,    8,  9, -1, -1, -1, -1, -1, -1,
            -1, 10, 11, 12, 13, 14, 15, 16,   17, 18, 19, 20, 21, 22, 23, 24,
            25, 26, 27, 28, 29, 30, 31, 32,   33, 34, 35, -1, -1, -1, -1, 36,
            -1, 37, 38, 39, 40, 41, 42, 43,   44, 45, 46, 47, 48, 49, 50, 51,
            52, 53, 54, 55, 56, 57, 58, 59,   60, 61, 62, -1, -1, -1, 63, -1
    )

    var pos:Int = 0
    var a = array.clone()

    fun haveBytes():Boolean{
        return pos < a.size
    }

    fun getByte():Byte{
        var b = this.a[pos]
        pos++
        if(this.pos > this.a.size) throw IndexOutOfBoundsException("out of bounds")
        return b
    }

    //si hay problemas revisar TODO:
    fun getChar():Char{
        var char = getByte().and(0xff.toByte()).toChar()
        return char//(getByte().and(0xffff) ).toChar()
    }

    fun getInt():Uint{
        var v = Uint(0)
        var c = zValue[(0x7f.toByte() and getByte()).toInt()]
        print("c:$c")
        while(this.haveBytes() && (c) >= 0){
            v = (v.shl(6) )+ c
            c = zValue[(0x7f.toByte() and getByte()).toInt()]
        }
        this.pos--
        println(v.v)
        return v
    }
}

class Writer(){
    var zDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~".toByteArray().map{Uint(it.toInt())}
    var a = ArrayList<Byte>()

    fun toArray():ByteArray{
        return a.toByteArray()!!
    }

    fun putByte(b:Byte){
        a.add((b and 0xff.toByte()))
    }

    fun putChar(c:Char){
        putByte(c.toByte())
    }

    fun putInt(vU:Uint){
        var zBuf = ArrayList<Byte>()
        var v = Uint(vU)
        if(v == Uint(0)){
            this.putChar('0')
            return
        }
        var i = 0
        while(v > Uint(0)){
            //var temp = v.and(0x3f)// and 0x3f
            zBuf.add(zDigits[v.and(0x3f).v].toByte())
            i++
            v = v.shr(6)
        }
        var j = i -1
        while(j >= 0){
            putByte(zBuf[j])
            j--
        }

    }

    fun putArray(a:ByteArray, start:Int, end:Int){
        for(i in start until end){
            this.a.add(a[i])
        }
    }
}

class FossilDelta(val src:ByteArray,val out:ByteArray) {

    fun digitCount(v:Int):Int{
        var i = 1
        var x = 64
        while(v >= x){
            x = x shl 6
            i++
        }
        return i
    }

    fun checksum(array:ByteArray):Uint{
        var sum0 = Uint(0)
        var sum1 = Uint(0)
        var sum2 = Uint(0)
        var sum = Uint(0)
        var z = 0
        var N = array.size
        while(N >= 16){
            sum0 = sum0.plus(array[z+0].toUint()).plus(array[z+4].toUint()).plus(array[z+8].toUint()).plus(array[z+12].toUint())
            sum1 = sum1.plus(array[z+1].toUint()).plus(array[z+5].toUint()).plus(array[z+9].toUint()).plus(array[z+13].toUint())
            sum2 = sum2.plus(array[z+2].toUint()).plus(array[z+6].toUint()).plus(array[z+10].toUint()).plus(array[z+14].toUint())
            sum = sum.plus(array[z+3].toUint()).plus(array[z+7].toUint()).plus(array[z+11].toUint()).plus(array[z+15].toUint())
            z+=16
            N -=16
        }

        while(N >= 4){
            sum0 = sum0.plus(array[z+0].toUint()) //or 0
            sum1 = sum1.plus(array[z+1].toUint()) //or 0
            sum2 = sum2.plus(array[z+2].toUint()) //or 0
            sum = sum.plus(array[z+3].toUint()) //or 0
            z+= 4
            N-=4
        }

        sum = sum.plus (sum2.shl(8).plus(sum1.shl(16)).plus(sum0.shl(24)))//sum+(sum2 shr 8) or 0) +  (sum1 shl 16) or 0) + (sum0 shl 24) or 0)
        when(N.and(3)){
            3 -> {
                sum = sum.plus((array[z+2].toUint().shl(8)))
                sum = sum.plus((array[z+1].toUint().shl(16)))
                sum = sum.plus((array[z+0].toUint().shl(24)))
            }
            2 -> {
                sum = sum.plus((array[z+1].toUint().shl(16)))
                sum = sum.plus((array[z+0].toUint().shl(24)))
            }
            3 -> {
                sum = sum.plus((array[z+0].toUint().shl(24)))
            }
        }

        return sum
    }
    var zDelta = Writer()
    var lenOut = Uint(out.size)
    var lenSrc = Uint(src.size)
    var lastRead = -1

    fun create():ByteArray{
        zDelta.putInt(Uint(lenOut))
        zDelta.putChar('\n')

        if(lenSrc <= RollingHash.NHASH){
            zDelta.putInt(lenOut)
            zDelta.putChar(':')
            zDelta.putArray(out,0,lenOut.v)
            zDelta.putInt(checksum(out))
            zDelta.putChar(';')
            return zDelta.toArray()
        }

        var nHash = lenSrc.div(RollingHash.NHASH).toInt()//Math.ceil(lenSrc.div(RollingHash.NHASH).toDouble())
        var collide = IntArray(nHash)
        var landmark = IntArray(nHash)
        for(i in 0 until collide.size){
            collide[i] = -1
        }
        for(i in 0 until landmark.size){
            landmark[i] = -1
        }

        var hv:Int
        var h = RollingHash()
        for(i in 0 until lenSrc.minus(RollingHash.NHASH).v step RollingHash.NHASH){
            h.init(src,i)
            hv = h.value() % nHash
            collide[i.div(RollingHash.NHASH)] = landmark[hv]
            landmark[hv] = i.div(RollingHash.NHASH)
        }

        var base = 0
        var iSrc:Int
        var iBlock:Int
        var bestCnt:Int
        var bestOfst:Int
        var bestLitsz:Int
        var i:Int
        while(base.plus(RollingHash.NHASH )< lenOut.v){
            bestOfst = 0
            bestLitsz = 0
            h.init(out, base)
            i = 0
            bestCnt = 0
            while (true){
                var limit = 250
                hv = h.value() % nHash
                iBlock = landmark[hv]
                while ((iBlock >=0) && (limit--) > 0){
                    var cnt:Int
                    var ofst:Int
                    var litsz:Int
                    var sz:Int

                    iSrc = iBlock*RollingHash.NHASH
                    var j = 0
                    var x = iSrc
                    var y = base + i
                    while(x < lenSrc.v &&  y < lenOut.v){
                        if(src[x] != out[y]) break
                        j++
                        y++
                        x++
                    }
                    j--

                    var k = 1
                    while(k < iSrc && k <= i){
                        if(src[iSrc-k] != out[base+i-k])break
                        k++
                    }
                    k--
                    ofst = iSrc - k
                    cnt = j+k+1
                    litsz  = i-k

                    sz = digitCount(i-k) + digitCount(cnt) + digitCount(ofst) + 3

                    if(cnt >= sz && cnt > bestCnt){
                        bestCnt = cnt
                        bestOfst = iSrc -k
                        bestLitsz = litsz
                    }
                    iBlock = collide[iBlock]
                }

                if(bestCnt > 0){
                    if(bestLitsz > 0){
                        zDelta.putInt(bestLitsz.toUint())
                        zDelta.putChar(':')
                        zDelta.putArray(out, base, base+bestLitsz)
                        base += bestLitsz
                    }

                    base += bestCnt
                    zDelta.putInt(bestCnt.toUint())
                    zDelta.putChar('@')
                    zDelta.putInt(bestOfst.toUint())
                    zDelta.putChar(',')
                    if(bestOfst + bestCnt-1 > lastRead){
                        lastRead = bestOfst + bestCnt-1
                    }
                    bestCnt = 0
                    break
                }

                if(base+i+RollingHash.NHASH >= lenOut.v){
                    zDelta.putInt(lenOut - base)
                    zDelta.putChar(':')
                    zDelta.putArray(out, base, base.plus(lenOut)-base)
                    base = lenOut.toInt()
                    break
                }
                h.next(out[base+i+RollingHash.NHASH])
                i++
            }
        }

        if(base < lenOut.v){
            zDelta.putInt(lenOut - base)
            zDelta.putChar(':')
            zDelta.putArray(out,base, base + lenOut - base)
        }
        zDelta.putInt(checksum(out))
        zDelta.putChar(';')
        return zDelta.toArray()
    }

    fun outputSize(delta:ByteArray):Int{
        var zDelta =  Reader(delta)
        var size = zDelta.getInt()
        if(zDelta.getChar() != '\n'){
            throw Error("Size integer  not terminated by '\\n'")
        }
        return size.v
    }

    fun apply(src:ByteArray, delta:ByteArray, opts:Option?):ByteArray{
        var limit = Uint(0)
        var total = Uint(0)
        var zDelta = Reader(delta)
        var lenSrc = src.size
        var lenDelta = delta.size

        limit = zDelta.getInt()
        if(zDelta.getChar() != '\n'){
            throw Error("size integer not terminated by '\\n'")
        }
        var zOut  = Writer()
        while (zDelta.haveBytes()){
            var ofst:Uint
            var cnt = zDelta.getInt()
            var char = zDelta.getChar()
            when(char){
                '@' -> {
                    ofst = zDelta.getInt()
                    if(zDelta.haveBytes() && zDelta.getChar() != ','){
                        throw Error("copy command not terminated by ','")
                    }
                    total += cnt
                    if(total > limit){
                        throw Error("copy exceeds output file size")
                    }
                    if(ofst+cnt > lenSrc){
                        throw Error("copy extends past end of input")
                    }
                    zOut.putArray(src, ofst.v, ofst.plus(cnt).v)
                }
                ':' -> {
                    total += cnt
                    if(total > limit){
                        throw Error("insert command gives an output larger than predicted")
                    }
                    if(cnt > lenDelta){
                        throw Error("insert count exceeds size of delta")
                    }
                    zOut.putArray(zDelta.a, zDelta.pos,zDelta.pos+cnt)
                    zDelta.pos += cnt
                }
                ';' -> {
                    var output = zOut.toArray()
                    println(cnt.toBigInt())
                    if(cnt.toBigInt() != checksum(output).toBigInt()){
                        throw Exception("Bad checksum")
                    }
                    if(total != limit){
                        throw Error("generated size does not match predicted size")
                    }
                    return output
                }
                else ->{
                    throw Error("unterminated delta")
                }
            }
        }
        throw Error("unterminated delta")
    }
}

interface Option {
    fun verifyChecksum():Boolean
}