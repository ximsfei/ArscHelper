package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log

import java.nio.ByteBuffer

/**
 * Created by ximsfei on 2017/8/8.
 */
class TablePackageChunk extends ChunkWithChunks {
    int id
    String packageName
    int typeStrings
    int lastPublicType
    int keyStrings
    int typeIdOffset
    final Map<Integer, TableTypeSpecChunk> typeSpecs = new HashMap<>()
    final Map<Integer, TableTypeChunk> types = new HashMap<>()

    TablePackageChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        Log.d("TablePackageChunk")
        int position = buffer.position()
        id = buffer.getInt()
        buffer.putInt(position, 0x6f)
        Log.d("id = " + Integer.toHexString(id))
        packageName = PackageUtils.readPackageName(buffer, buffer.position())
        Log.d("packageName", packageName)
        typeStrings = buffer.getInt()
        lastPublicType = buffer.getInt()
        keyStrings = buffer.getInt()
        typeIdOffset = buffer.getInt()
    }

    @Override
    def init(ByteBuffer buffer) {
        super.init(buffer)
        for (Chunk chunk : chunks.values()) {
            if (chunk instanceof TableTypeChunk) {
                TableTypeChunk typeChunk = (TableTypeChunk) chunk;
                types.put(typeChunk.getId(), typeChunk);
            } else if (chunk instanceof TableTypeSpecChunk) {
                TableTypeSpecChunk typeSpecChunk = (TableTypeSpecChunk) chunk;
                typeSpecs.put(typeSpecChunk.getId(), typeSpecChunk);
            } else if (!(chunk instanceof StringPoolChunk)) {
                throw new IllegalStateException(
                        String.format("PackageChunk contains an unexpected chunk: %s", chunk.getClass()));
            }
        }
    }

    @Override
    Type getType() {
        Type.TABLE_PACKAGE
    }
}
