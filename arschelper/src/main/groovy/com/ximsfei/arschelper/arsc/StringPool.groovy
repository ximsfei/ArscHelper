package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by ximsfei on 2017/8/8.
 */
class StringPool {
    enum Flags {
        SORTED_FLAG(1<<0),
        UTF8_FLAG(1<<8)
    }
    static final int SIZE = ChunkHeader.SIZE
    ChunkHeader header
    int stringCount
    int styleCount
    int flags
    int stringsStart
    int stylesStart

    static def StringPool parse(ByteBuffer dexBuffer) {
        Log.d("StringPool parse")
        StringPool stringPool = new StringPool()
        stringPool.header = ChunkHeader.parse(dexBuffer)
        stringPool.stringCount = dexBuffer.getInt()
        Log.d("stringCount", stringPool.stringCount as String)
        stringPool.styleCount = dexBuffer.getInt()
        Log.d("styleCount", stringPool.styleCount as String)
        stringPool.flags = dexBuffer.getInt()
        Log.d("flags", stringPool.flags as String)
        stringPool.stringsStart = dexBuffer.getInt()
        Log.d("stringsStart", stringPool.stringsStart as String)
        stringPool.stylesStart = dexBuffer.getInt()
        Log.d("stylesStart", stringPool.stylesStart as String)
        stringPool
    }
}
