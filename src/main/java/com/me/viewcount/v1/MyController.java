package com.me.viewcount.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MyController {

    private final MyServiceV1 myService;


    public MyController(MyServiceV1 myService) {
        this.myService = myService;
    }

    @GetMapping("/v1/tx1/{storeId}/{customerId}")
    public String tx1(@PathVariable Long storeId, @PathVariable Long customerId) {
        log.info("storeId = {}, customerId = {}", storeId, customerId);
        boolean isNew = myService.tx1(customerId, storeId);
        if (isNew) {
            myService.tx1Update(storeId);
        }
        return "ok";
    }

    @GetMapping("/dummy")
    public String dummy() {
        log.info("dummy called()");
        for (int i = 0; i < 100; i++) {
            myService.dummy();
        }
        return "ok";
    }
}
