package ru.leadexsystems.iot.rpisensortagble.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;
import java.util.Map;

/**
 * Created by Maxim Ivanov.
 */
@Entity("SensorData")
public class SensorData {
    @Id
    String _id = null;
    Date timestamp;
    String sensorId;
    Map<String, Object> metrics;

    public SensorData() {}

    public SensorData(Date timestamp, String sensorId, Map<String, Object> metrics) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.metrics = metrics;
    }

    public String get_id() {
        return _id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Object> metrics) {
        this.metrics = metrics;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}
