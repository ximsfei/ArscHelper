package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log
import com.ximsfei.arschelper.utils.SizeOf
import org.gradle.internal.impldep.com.google.common.collect.HashMultimap
import org.gradle.internal.impldep.com.google.common.collect.Multimap

import java.nio.ByteBuffer
import java.nio.charset.Charset;

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
    final Map<Integer, TableTypeSpecChunk> typeSpecs = new HashMap<>();
    final Multimap<Integer, TableTypeChunk> types = HashMultimap.create();

    TablePackageChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        Log.d("TablePackageChunk")
        id = buffer.getInt()
        Log.d("id = " + Integer.toHexString(id))
        packageName = readPackageName(buffer, buffer.position())
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

    public static String readPackageName(ByteBuffer buffer, int offset) {
        Charset utf16 = Charset.forName("UTF-16LE");
        String str = new String(buffer.array(), offset, SizeOf.USHORT * 128, utf16);
        buffer.position(offset + SizeOf.USHORT * 128);
        return str;
    }

    public static void writePackageName(ByteBuffer buffer, String packageName) {
        buffer.put(packageName.getBytes(Charset.forName("UTF-16LE")), 0, SizeOf.USHORT * 128);
    }
}
