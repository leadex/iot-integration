package ru.leadexsystems.iot.rpisensortagble.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.eclipse.kura.core.message.protobuf.KuraPayloadProto.KuraPayload;
import org.springframework.stereotype.Component;
import ru.leadexsystems.iot.rpisensortagble.model.SensorData;
import ru.leadexsystems.iot.rpisensortagble.utils.KuraUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Maxim Ivanov.
 */
@Component
public class Payload2SensorDataTransform implements Processor {
    static Logger logger = Logger.getLogger(Payload2SensorDataTransform.class);

    static KuraUtils kuraUtils = new KuraUtils();

    @Override
    public void process(Exchange exchange) throws Exception {
        byte[] body = (byte[]) exchange.getIn().getBody();
        KuraPayload kuraPayload = KuraPayload.parseFrom(body);

        Map<String, Object> metrics = new HashMap<>();

        for (KuraPayload.KuraMetric metric : kuraPayload.getMetricList()) {
            metrics.put(metric.getName(), kuraUtils.getObjectValue(metric));
        }

        // Extract sensor id
        String[] topicPath = exchange.getIn().getHeader("CamelMQTTSubscribeTopic").toString().split("/");
        String sensorId = topicPath[topicPath.length - 1];

        exchange.getOut().setBody(new SensorData(new Date((long)kuraPayload.getTimestamp()), sensorId, metrics));
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
    }
}
