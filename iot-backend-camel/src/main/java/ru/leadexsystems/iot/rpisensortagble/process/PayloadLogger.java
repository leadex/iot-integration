package ru.leadexsystems.iot.rpisensortagble.process;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * Created by Maxim Ivanov.
 */
@Component
public class PayloadLogger implements Processor {
    static Logger logger = Logger.getLogger(PayloadLogger.class);

    Gson gson = new Gson();

    @Override
    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn().getBody();

        logger.log(Level.INFO, gson.toJson(body));

        exchange.getOut().setBody(body);
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
    }
}
