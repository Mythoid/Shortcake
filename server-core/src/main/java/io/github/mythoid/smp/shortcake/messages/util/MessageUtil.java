package io.github.mythoid.smp.shortcake.messages.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.nio.charset.StandardCharsets;

public class MessageUtil {

    public static byte[] toMCProtocolString(String s) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.write(varInt(s.length()));
        out.write(s.getBytes(StandardCharsets.UTF_8));
        return out.toByteArray();
    }

    // Implementation from: https://wiki.vg/Protocol#VarInt_and_VarLong
    static final int SEGMENT_BITS = 0x7F;
    static final int CONTINUE_BIT = 0x80;
    public static byte[] varInt(int value) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                out.writeByte(value);
                return out.toByteArray();
            }
            out.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);
            value >>>= 7;
        }
    }

}
