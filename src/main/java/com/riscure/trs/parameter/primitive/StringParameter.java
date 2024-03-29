package com.riscure.trs.parameter.primitive;

import com.riscure.trs.enums.ParameterType;
import com.riscure.trs.io.LittleEndianInputStream;
import com.riscure.trs.io.LittleEndianOutputStream;
import com.riscure.trs.parameter.TraceParameter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class StringParameter extends TraceParameter {
    private static final String INVALID_LENGTH = "Error parsing string: Expected (%d) bytes but found (%d)";
    private final String value;

    public StringParameter(String value) {
        this.value = value;
    }

    public StringParameter(StringParameter toCopy) {
        this(toCopy.getValue());
    }

    public void serialize(LittleEndianOutputStream dos) throws IOException {
        dos.write(value.getBytes(StandardCharsets.UTF_8));
    }

    public static StringParameter deserialize(LittleEndianInputStream dis, int length) throws IOException {
        byte[] bytes = new byte[length];
        if (length > 0) {
            int bytesRead = dis.read(bytes);
            if (bytesRead != length) throw new IOException(String.format(INVALID_LENGTH, length, bytesRead));
        }
        return new StringParameter(new String(bytes, StandardCharsets.UTF_8));
    }

    @Override
    public int length() {
        return value.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public ParameterType getType() {
        return ParameterType.STRING;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public StringParameter copy() {
        return new StringParameter(this);
    }

    @Override
    public String getScalarValue() {
        return getValue();
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringParameter that = (StringParameter) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
