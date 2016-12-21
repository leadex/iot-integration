package ru.leadexsystems.iot.rpisensortagble.route;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.leadexsystems.iot.rpisensortagble.process.Payload2SensorDataTransform;
import ru.leadexsystems.iot.rpisensortagble.process.PayloadLogger;
import ru.leadexsystems.iot.rpisensortagble.process.PublishJsonProcessor;
import ru.leadexsystems.iot.rpisensortagble.process.PutToMongoDB;

/**
 * @author Maxim Ivanov
 */
@Component
public class CreateRoutes extends RouteBuilder {

  @Autowired
  PayloadLogger payloadLogger;

  @Autowired
  Payload2SensorDataTransform payload2SensorDataTransform;

  @Autowired
  PutToMongoDB putToMongoDB;

  @Autowired
  PublishJsonProcessor publishJsonProcessor;

  @Override
  public void configure() throws Exception {
    from("mqtt://raspberry?host={{mqtt.host}}" +
        "&subscribeTopicNames={{mqtt.subscribeTopicNames}}")
        .convertBodyTo(byte[].class)
        .process(payload2SensorDataTransform) // Decode Google Protobuf and convert to SensorData model
        //.process(payloadLogger) // Write json to logs
        .process(putToMongoDB) // Save SensorData to mongodb
        .process(publishJsonProcessor) // Serialize SensorData and publish to message broker for webview usage
        .to("mqtt://backmq?host={{mqtt-back.host}}");
  }
}
