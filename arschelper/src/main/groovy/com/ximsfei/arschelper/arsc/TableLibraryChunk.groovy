package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.Log

import java.nio.ByteBuffer

class TableLibraryChunk extends Chunk {
    final int entryCount
    final List<Entry> entries = new ArrayList<>()

    TableLibraryChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        entryCount = buffer.getInt()
    }

    @Override
    def init(ByteBuffer buffer) {
        int offset = this.offset + headerSize
        int endOffset = offset + Entry.SIZE * entryCount

        while (offset < endOffset) {
            entries.add(Entry.create(buffer, offset))
            offset += Entry.SIZE
        }
    }

    @Override
    Type getType() {
        Type.TABLE_LIBRARY
    }

    static class Entry {

        private static final int SIZE = 4 + PackageUtils.PACKAGE_NAME_SIZE

        final int packageId

        final String packageName

        Entry(int packageId, String packageName) {
            this.packageId = packageId
            this.packageName = packageName
        }

        static Entry create(ByteBuffer buffer, int offset) {
            int packageId = buffer.getInt(offset)
            Log.d("entry packageId", Integer.toHexString(packageId))
            String packageName = PackageUtils.readPackageName(buffer, offset + 4)
            new Entry(packageId, packageName)
        }
    }
}