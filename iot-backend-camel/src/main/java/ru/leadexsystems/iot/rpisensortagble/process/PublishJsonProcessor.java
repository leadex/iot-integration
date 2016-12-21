package ru.leadexsystems.iot.rpisensortagble.process;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leadexsystems.iot.rpisensortagble.model.SensorData;
import ru.leadexsystems.iot.rpisensortagble.utils.MongoDbManager;


/**
 * Created by Maxim Ivanov.
 */
@Component
public class PublishJsonProcessor implements Processor {
    static Logger logger = Logger.getLogger(PublishJsonProcessor.class);

    @Value("${mqtt-back.topicPrefix}")
    String topicPrefix;

    Gson gson = new Gson();

    @Override
    public void process(Exchange exchange) throws Exception {
        SensorData sensorData = (SensorData) exchange.getIn().getBody();

        String body = gson.toJson(sensorData);

        exchange.getOut().setBody(body);
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());

        // Select mqtt topic name
        exchange.getOut().setHeader("CamelMQTTPublishTopic", topicPrefix + sensorData.getSensorId());
    }
}
