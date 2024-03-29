package com.riscure.trs.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LittleEndianOutputStream extends FilterOutputStream implements DataOutput {
    /**
     * The number of bytes written to the data output stream so far.
     * If this counter overflows, it will be wrapped to Integer.MAX_VALUE.
     */
    protected int written;

    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * @param out the underlying output stream to be assigned to
     *            the field <code>this.out</code> for later use, or
     *            <code>null</code> if this instance is to be
     *            created without an underlying stream.
     */
    public LittleEndianOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Increases the written counter by the specified value
     * until it reaches Integer.MAX_VALUE.
     */
    private void incCount(int value) {
        int temp = written + value;
        if (temp < 0) {
            temp = Integer.MAX_VALUE;
        }
        written = temp;
    }

    /**
     * Writes the specified byte (the low eight bits of the argument
     * <code>b</code>) to the underlying output stream. If no exception
     * is thrown, the counter <code>written</code> is incremented by
     * <code>1</code>.
     * <p>
     * Implements the <code>write</code> method of <code>OutputStream</code>.
     *
     * @param b the <code>byte</code> to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public synchronized void write(int b) throws IOException {
        out.write(b);
        incCount(1);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to the underlying output stream.
     * If no exception is thrown, the counter <code>written</code> is
     * incremented by <code>len</code>.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        incCount(len);
    }

    /**
     * Flushes this data output stream. This forces any buffered output
     * bytes to be written out to the stream.
     * <p>
     * The <code>flush</code> method of <code>DataOutputStream</code>
     * calls the <code>flush</code> method of its underlying output stream.
     *
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     * @see java.io.OutputStream#flush()
     */
    @Override
    public void flush() throws IOException {
        out.flush();
    }

    /**
     * Writes a <code>boolean</code> to the underlying output stream as
     * a 1-byte value. The value <code>true</code> is written out as the
     * value <code>(byte)1</code>; the value <code>false</code> is
     * written out as the value <code>(byte)0</code>. If no exception is
     * thrown, the counter <code>written</code> is incremented by
     * <code>1</code>.
     *
     * @param v a <code>boolean</code> value to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeBoolean(boolean v) throws IOException {
        out.write(v ? 1 : 0);
        incCount(1);
    }

    /**
     * Writes out a <code>byte</code> to the underlying output stream as
     * a 1-byte value. If no exception is thrown, the counter
     * <code>written</code> is incremented by <code>1</code>.
     *
     * @param v a <code>byte</code> value to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeByte(int v) throws IOException {
        out.write(v);
        incCount(1);
    }

    /**
     * Writes a <code>short</code> to the underlying output stream as two
     * bytes, high byte first. If no exception is thrown, the counter
     * <code>written</code> is incremented by <code>2</code>.
     *
     * @param v a <code>short</code> to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeShort(int v) throws IOException {
        out.write((v >>> 0) & 0xFF);
        out.write((v >>> 8) & 0xFF);
        incCount(2);
    }

    /**
     * Writes a <code>char</code> to the underlying output stream as a
     * 2-byte value, high byte first. If no exception is thrown, the
     * counter <code>written</code> is incremented by <code>2</code>.
     *
     * @param v a <code>char</code> value to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeChar(int v) throws IOException {
        out.write((v >>> 0) & 0xFF);
        out.write((v >>> 8) & 0xFF);
        incCount(2);
    }

    /**
     * Writes an <code>int</code> to the underlying output stream as four
     * bytes, high byte first. If no exception is thrown, the counter
     * <code>written</code> is incremented by <code>4</code>.
     *
     * @param v an <code>int</code> to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeInt(int v) throws IOException {
        out.write((v >>> 0) & 0xFF);
        out.write((v >>> 8) & 0xFF);
        out.write((v >>> 16) & 0xFF);
        out.write((v >>> 24) & 0xFF);
        incCount(4);
    }

    private byte[] writeBuffer = new byte[8];

    /**
     * Writes a <code>long</code> to the underlying output stream as eight
     * bytes, high byte first. In no exception is thrown, the counter
     * <code>written</code> is incremented by <code>8</code>.
     *
     * @param v a <code>long</code> to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeLong(long v) throws IOException {
        writeBuffer[0] = (byte) (v >>> 0);
        writeBuffer[1] = (byte) (v >>> 8);
        writeBuffer[2] = (byte) (v >>> 16);
        writeBuffer[3] = (byte) (v >>> 24);
        writeBuffer[4] = (byte) (v >>> 32);
        writeBuffer[5] = (byte) (v >>> 40);
        writeBuffer[6] = (byte) (v >>> 48);
        writeBuffer[7] = (byte) (v >>> 56);
        out.write(writeBuffer, 0, 8);
        incCount(8);
    }

    /**
     * Converts the float argument to an <code>int</code> using the
     * <code>floatToIntBits</code> method in class <code>Float</code>,
     * and then writes that <code>int</code> value to the underlying
     * output stream as a 4-byte quantity, high byte first. If no
     * exception is thrown, the counter <code>written</code> is
     * incremented by <code>4</code>.
     *
     * @param v a <code>float</code> value to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     * @see java.lang.Float#floatToIntBits(float)
     */
    @Override
    public final void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    /**
     * Converts the double argument to a <code>long</code> using the
     * <code>doubleToLongBits</code> method in class <code>Double</code>,
     * and then writes that <code>long</code> value to the underlying
     * output stream as an 8-byte quantity, high byte first. If no
     * exception is thrown, the counter <code>written</code> is
     * incremented by <code>8</code>.
     *
     * @param v a <code>double</code> value to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     * @see java.lang.Double#doubleToLongBits(double)
     */
    @Override
    public final void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    /**
     * Writes out the string to the underlying output stream as a
     * sequence of bytes. Each character in the string is written out, in
     * sequence, by discarding its high eight bits. If no exception is
     * thrown, the counter <code>written</code> is incremented by the
     * length of <code>s</code>.
     *
     * @param s a string of bytes to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeBytes(String s) throws IOException {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            out.write((byte) s.charAt(i));
        }
        incCount(len);
    }

    /**
     * Writes a string to the underlying output stream as a sequence of
     * characters. Each character is written to the data output stream as
     * if by the <code>writeChar</code> method. If no exception is
     * thrown, the counter <code>written</code> is incremented by twice
     * the length of <code>s</code>.
     *
     * @param s a <code>String</code> value to be written.
     * @throws IOException if an I/O error occurs.
     * @see java.io.DataOutputStream#writeChar(int)
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeChars(String s) throws IOException {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int v = s.charAt(i);
            out.write((v >>> 0) & 0xFF);
            out.write((v >>> 8) & 0xFF);
        }
        incCount(len * 2);
    }

    /**
     * Writes a string to the underlying output stream using
     * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
     * encoding in a machine-independent manner.
     * <p>
     * First, two bytes are written to the output stream as if by the
     * <code>writeShort</code> method giving the number of bytes to
     * follow. This value is the number of bytes actually written out,
     * not the length of the string. Following the length, each character
     * of the string is output, in sequence, using the modified UTF-8 encoding
     * for the character. If no exception is thrown, the counter
     * <code>written</code> is incremented by the total number of
     * bytes written to the output stream. This will be at least two
     * plus the length of <code>str</code>, and at most two plus
     * thrice the length of <code>str</code>.
     *
     * @param str a string to be written.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public final void writeUTF(String str) throws IOException {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        writeShort(bytes.length);
        out.write(bytes);
        incCount(bytes.length);
    }

    /**
     * Returns the current value of the counter <code>written</code>,
     * the number of bytes written to this data output stream so far.
     * If the counter overflows, it will be wrapped to Integer.MAX_VALUE.
     *
     * @return the value of the <code>written</code> field.
     * @see LittleEndianOutputStream#written
     */
    public final int size() {
        return written;
    }
}
