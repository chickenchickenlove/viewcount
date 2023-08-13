package com.me.viewcount;

import com.me.viewcount.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MyController {

    private final MyService myService;


    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/test")
    public String data() {
        myService.test1();
        return "ok";
    }

    @GetMapping("/count/{storeId}/{customerId}")
    public String count(@PathVariable Long storeId, @PathVariable Long customerId) {
        log.info("storeId = {}, customerId = {}", storeId, customerId);
        return "ok";
    }

    @GetMapping("/tx1/{storeId}/{customerId}")
    public String tx1(@PathVariable Long storeId, @PathVariable Long customerId) {
        log.info("storeId = {}, customerId = {}", storeId, customerId);
        boolean isNew = myService.tx1(customerId, storeId);
        if (isNew) {
            myService.tx1Update(storeId);
        }
        return "ok";
    }



}
