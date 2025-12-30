package com.tc.demo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @Autowired
    Controller(
            KVClient client,
            @Value("${kv.secret.name}") String secret
    ){
        client.refreshVersionList(secret);
        this.client = client;
    }

    KVClient client;

    class ResponseObject {
        public ResponseObject(String m) {
            this.message = m;
        }
        String message;
    }

    @GetMapping("/endpoint")
    Mono<ResponseEntity<ResponseObject>> endpoint(){
        return Mono.just(ResponseEntity.ok(new ResponseObject("Hello World")));
    }
}
