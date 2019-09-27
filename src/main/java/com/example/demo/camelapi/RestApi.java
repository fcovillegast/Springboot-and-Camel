package com.example.demo.camelapi;

import com.example.demo.beans.MyBean;
import com.example.demo.camelservices.BeanPlus10;
import com.example.demo.camelservices.SayHello;
import com.example.demo.services.ExampleServices;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
class RestApi extends RouteBuilder {

    @Value("${server.port}")
    String serverPort;

    @Value("${baeldung.api.path}")
    String contextPath;

    @Override
    public void configure() {

        CamelContext context = new DefaultCamelContext();


        // http://localhost:8080/camel/api-doc
        restConfiguration().contextPath(contextPath) //
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true") // cross-site
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");
/**
 The Rest DSL supports automatic binding json/xml contents to/from
 POJOs using Camels Data Format.
 By default the binding mode is off, meaning there is no automatic
 binding happening for incoming and outgoing messages.
 You may want to use binding if you develop POJOs that maps to
 your REST services request and response types.
 */

        rest("/api/").description("Teste REST Service")
                .id("api-route")
                .post("/bean")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
//                .get("/hello/{place}")
                .bindingMode(RestBindingMode.auto)
                .type(MyBean.class)
                .enableCORS(true)
//                .outType(OutBean.class)

                .to("direct:"+ SayHello.class.getName());




    }
}
