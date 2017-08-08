package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

/**
 * Created by ximsfei on 2017/8/8.
 */
class TableTypeChunk extends Chunk {
    final byte id
    final byte res0
    final short res1
    final int entryCount;
    final int entriesStart;

    TableTypeChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        id = buffer.get()
        res0 = buffer.get()
        res1 = buffer.get()
        entryCount = buffer.getInt()
        entriesStart = buffer.getInt()
    }

    @Override
    Type getType() {
        Type.TABLE_TYPE
    }

    static class TableEntry {
    }
}
