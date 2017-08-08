package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log
import com.ximsfei.arschelper.utils.SizeOf

import java.nio.ByteBuffer

/**
 * Created by ximsfei on 2017/8/8.
 */
class ChunkHeader {
    static final int SIZE = SizeOf.USHORT + SizeOf.USHORT + SizeOf.UINT

    short type
    short headSize
    int size;

    static def ChunkHeader parse(ByteBuffer dexBuffer) {
        ChunkHeader header = new ChunkHeader()
        header.type = dexBuffer.getShort()
        header.headSize = dexBuffer.getShort()
        header.size = dexBuffer.getInt()
        Log.d("type = " + Integer.toHexString(header.type) + ", headSize = " + header.headSize + ", size = " + header.size)
        header
    }
}
