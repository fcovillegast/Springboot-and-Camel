package com.example.demo.camelservices;

import com.example.demo.beans.MyBean;
import com.example.demo.services.ExampleServices;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SayHello extends RouteBuilder {

    @Value("${server.port}")
    String serverPort;

    @Value("${baeldung.api.path}")
    String contextPath;

    @Override
    public void configure() {

        CamelContext context = new DefaultCamelContext();

        from("direct:"+this.getClass().getName())
                .routeId("direct-route-"+this.getClass().getName())
                .tracing()
                .log(">>> Saying Hello!")
//                .transform().simple("blue ${in.body.name}")
                .process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        MyBean bean = (MyBean) exchange.getIn().getBody();
                        bean.setName("Hello My Friend");

                        exchange.getIn().setBody(bean);
                    }
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }
}
