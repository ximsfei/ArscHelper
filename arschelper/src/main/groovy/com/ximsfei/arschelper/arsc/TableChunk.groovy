package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log

import java.nio.ByteBuffer

/**
 * Created by ximsfei on 2017/8/8.
 */
class TableChunk extends ChunkWithChunks {

    StringPoolChunk stringPool;

    final Map<String, TablePackageChunk> packages = new HashMap<>();

    final int ident

    TableChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        Log.d("Table")
        ident = buffer.getInt()
    }

    @Override
    def init(ByteBuffer buffer) {
        super.init(buffer)
        packages.clear()

        for (Chunk chunk : chunks.values()) {
            if (chunk instanceof TablePackageChunk) {
                TablePackageChunk packageChunk = (TablePackageChunk) chunk;
                packages.put(packageChunk.packageName, packageChunk);
            } else if (chunk instanceof StringPoolChunk) {
                stringPool = (StringPoolChunk) chunk;
            }
        }
    }

    @Override
    Type getType() {
        Type.TABLE
    }
}
