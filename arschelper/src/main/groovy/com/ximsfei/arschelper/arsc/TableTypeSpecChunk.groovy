package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

/**
 * Created by ximsfei on 2017/8/8.
 */
class TableTypeSpecChunk extends Chunk {
    enum Spec {
        SPEC_PUBLIC(0x40000000)
        int spec

        Spec(int spec) {
            this.spec = spec
        }
    }
    final byte id
    final byte res0
    final short res1
    final int entryCount;
    final int[] resources;

    TableTypeSpecChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        id = buffer.get()
        res0 = buffer.get()
        res1 = buffer.getShort()
        entryCount = buffer.getInt()
        resources = new int[entryCount]

        for (int i in 0..entryCount-1) {
            resources[i] = buffer.getInt()
        }
    }

    @Override
    Type getType() {
        Type.TABLE_TYPE_SPEC
    }
}
