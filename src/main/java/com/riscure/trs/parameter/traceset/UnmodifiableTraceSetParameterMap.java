package com.riscure.trs.parameter.traceset;

import java.util.*;
import java.util.function.BiFunction;

/**
 * This class represents the header definitions of all user-added global parameters of the trace set
 *
 * This map is read from the file, and is therefore unmodifiable.
 */
public class UnmodifiableTraceSetParameterMap extends TraceSetParameterMap {
    private static final String UNABLE_TO_SET_PARAMETER =
            "Unable to set parameter `%s` to `%s`: This trace set is in read mode and cannot be modified.";
    private static final String REMOVAL_NOT_SUPPORTED_EXCEPTION =
            "Unable to remove parameter `%s`: This trace set is in read mode and cannot be modified.";
    private static final String MODIFICATION_NOT_SUPPORTED_EXCEPTION = "Unable to modify: This trace set is in read mode and cannot be modified.";

    private static final String UNABLE_TO_ADD_ALL_OF_S_THIS_TRACE_SET_IS_IN_READ_MODE_AND_CANNOT_BE_MODIFIED = "Unable to add all of `%s` : This trace set is in read mode and cannot be modified.";

    private UnmodifiableTraceSetParameterMap(TraceSetParameterMap delegate) {
        super.putAll(delegate.copy());
    }

    public static TraceSetParameterMap of(TraceSetParameterMap delegate) {
        return new UnmodifiableTraceSetParameterMap(delegate);
    }

    @Override
    public TraceSetParameter put(String key, TraceSetParameter value) {
        throw new UnsupportedOperationException(
                String.format(UNABLE_TO_SET_PARAMETER,
                        key,
                        value.getValue()
                )
        );
    }

    @Override
    public TraceSetParameter remove(Object key) {
        throw new UnsupportedOperationException(
                String.format(REMOVAL_NOT_SUPPORTED_EXCEPTION,
                        key
                )
        );
    }

    @Override
    public void putAll(Map<? extends String, ? extends TraceSetParameter> m) {
        throw new UnsupportedOperationException(
                String.format(
                        UNABLE_TO_ADD_ALL_OF_S_THIS_TRACE_SET_IS_IN_READ_MODE_AND_CANNOT_BE_MODIFIED,
                        m.toString()
                )
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
                MODIFICATION_NOT_SUPPORTED_EXCEPTION
        );
    }

    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(super.keySet());
    }

    @Override
    public Collection<TraceSetParameter> values() {
        return Collections.unmodifiableCollection(super.values());
    }

    @Override
    public Set<Map.Entry<String, TraceSetParameter>> entrySet() {
        return Collections.unmodifiableSet(super.entrySet());
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super TraceSetParameter, ? extends TraceSetParameter> function) {
        throw new UnsupportedOperationException(MODIFICATION_NOT_SUPPORTED_EXCEPTION);
    }

    @Override
    public TraceSetParameter putIfAbsent(String key, TraceSetParameter value) {
        throw new UnsupportedOperationException(
                String.format(UNABLE_TO_SET_PARAMETER,
                        key,
                        value.toString()
                )
        );
    }

    @Override
    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException(
                String.format(
                        REMOVAL_NOT_SUPPORTED_EXCEPTION,
                        key
                )
        );
    }

    @Override
    public boolean replace(String key, TraceSetParameter oldValue, TraceSetParameter newValue) {
        throw new UnsupportedOperationException(
                String.format(UNABLE_TO_SET_PARAMETER,
                        key,
                        newValue.toString()
                )
        );
    }

    @Override
    public TraceSetParameter replace(String key, TraceSetParameter value) {
        throw new UnsupportedOperationException(
                String.format(UNABLE_TO_SET_PARAMETER,
                        key,
                        value.toString()
                )
        );
    }

    @Override
    public TraceSetParameter merge(String key, TraceSetParameter value, BiFunction<? super TraceSetParameter, ? super TraceSetParameter, ? extends TraceSetParameter> remappingFunction) {
        throw new UnsupportedOperationException(MODIFICATION_NOT_SUPPORTED_EXCEPTION);
    }
}
