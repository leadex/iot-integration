package ru.leadexsystems.iot.rpisensortagble.process;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.leadexsystems.iot.rpisensortagble.utils.MongoDbManager;


/**
 * Created by Maxim Ivanov.
 */
@Component
public class PutToMongoDB implements Processor {
    static Logger logger = Logger.getLogger(PutToMongoDB.class);

    @Autowired
    MongoDbManager mongoDbManager;

    @Override
    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn().getBody();

        mongoDbManager.getDatastore().save(body);

        exchange.getOut().setBody(body);
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
    }
}
