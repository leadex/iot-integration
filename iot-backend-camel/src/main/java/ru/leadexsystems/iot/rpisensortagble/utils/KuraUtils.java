package ru.leadexsystems.iot.rpisensortagble.utils;

import org.eclipse.kura.core.message.protobuf.KuraPayloadProto;

/**
 * Created by Maxim Ivanov.
 */
public class KuraUtils {
    public String getStringValue(KuraPayloadProto.KuraPayload.KuraMetric metric) {
        switch (metric.getType()) {
            case STRING: return metric.getStringValue();
            case DOUBLE: return "" + metric.getDoubleValue();
            case BOOL: return "" + metric.getBoolValue();
            case FLOAT: return "" + metric.getFloatValue();
            case INT32: return "" + metric.getIntValue();
            case INT64: return "" + metric.getLongValue();
            case BYTES: return "Not implement BYTES";
        }

        return "Wrong metric type.";
    }

    public Object getObjectValue(KuraPayloadProto.KuraPayload.KuraMetric metric) {
        switch (metric.getType()) {
            case STRING: return metric.getStringValue();
            case DOUBLE: return metric.getDoubleValue();
            case BOOL: return metric.getBoolValue();
            case FLOAT: return metric.getFloatValue();
            case INT32: return metric.getIntValue();
            case INT64: return metric.getLongValue();
            case BYTES: return metric.getBytesValue();
        }

        return "Wrong metric type.";
    }
}
