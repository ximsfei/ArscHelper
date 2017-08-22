package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

/**
 * Created by ximsfei on 2017/8/8.
 */
class TableTypeChunk extends Chunk {
    final byte id
    final byte res0
    final short res1
    final int entryCount
    final int entriesStart
    final ResourceConfiguration configuration
    final Map<Integer, TableEntry> entries = new TreeMap<>()

    TableTypeChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        id = buffer.get()
//        Log.d("id id ", Integer.toHexString(buffer.getInt()))
        res0 = buffer.get()
        res1 = buffer.getShort()
        entryCount = buffer.getInt()
        entriesStart = buffer.getInt()
        configuration = ResourceConfiguration.create(buffer)
    }

    @Override
    def init(ByteBuffer buffer) {
        int offset = this.offset + entriesStart
        for (int i = 0; i < entryCount; ++i) {
            TableEntry entry = TableEntry.create(buffer, offset, this)
            if (entry != null) {
                entries.put(i, entry)
            }
        }
    }

    @Override
    Type getType() {
        Type.TABLE_TYPE
    }

    static class TableEntry {
        static final int NO_ENTRY = 0xFFFFFFFF
        static final int FLAG_COMPLEX = 0x0001
//        static final int MAPPING_SIZE = 4 + ResourceValue.SIZE
        final short headerSize
//        final short size
        final short flags
        final int index
        final ResourceValue value
        final Map<Integer, ResourceValue> values
        final int parentEntry
        final TableTypeChunk parent

        TableEntry(int headerSize, int flags, int keyIndex, ResourceValue value,
                   Map<Integer, ResourceValue> values, int parentEntry, TableTypeChunk parent) {
            this.headerSize = headerSize
            this.flags = flags
            this.index = keyIndex
            this.value = value
            this.values = values
            this.parentEntry = parentEntry
            this.parent = parent
        }

        static TableEntry create(ByteBuffer buffer, int baseOffset, TableTypeChunk parent) {
            int offset = buffer.getInt()
            if (offset == NO_ENTRY) {
                null
            }
            int position = buffer.position()
            buffer.position(baseOffset + offset)
            TableEntry result = newInstance(buffer, parent)
            buffer.position(position)
            result
        }

        static TableEntry newInstance(ByteBuffer buffer, TableTypeChunk parent) {
            int headerSize = buffer.getShort() & 0xFFFF
            int flags = buffer.getShort() & 0xFFFF
            int keyIndex = buffer.getInt()
            Log.d("flags", Integer.toHexString(flags))
            Log.d("keyIndex", Integer.toHexString(keyIndex))
            ResourceValue value = null
            Map<Integer, ResourceValue> values = new LinkedHashMap<>()
            int parentEntry = 0
            if ((flags & FLAG_COMPLEX) != 0) {
                int position = buffer.position()
                parentEntry = buffer.getInt()
                Log.d("parentEntry", "" + Integer.toHexString(parentEntry))
                if (parentEntry >> 24 == 0x7f) {
                    parentEntry = 0x6f << 24 | parentEntry & 0x00ffffff
                    buffer.putInt(position, parentEntry)
                }
                int valueCount = buffer.getInt()
                for (int i = 0; i < valueCount; ++i) {
                    int pos = buffer.position()
                    int key = buffer.getInt()
                    Log.d("Key ", Integer.toHexString(key))
                    if (key >> 24 == 0x7f) {
                        key = 0x6f << 24 | key & 0x00ffffff
                        buffer.putInt(pos, key)
                    }
                    values.put(key, ResourceValue.create(buffer))
                }
            } else {
                value = ResourceValue.create(buffer)
            }
            new TableEntry(headerSize, flags, keyIndex, value, values, parentEntry, parent)
        }
    }
}
