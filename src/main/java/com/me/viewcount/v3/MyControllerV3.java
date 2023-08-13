package com.me.viewcount.v3;

import com.me.viewcount.v2.MemoryViewCounterV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyControllerV3 {

    private final MemoryViewCounterV3 memoryViewCounter;

    @GetMapping("/v3/tx1/{storeId}/{customerId}")
    public String tx(@PathVariable Long storeId, @PathVariable Long customerId) {
//        log.info("storeId = {}, customerId = {}", storeId, customerId);
        memoryViewCounter.visit(customerId, storeId);
        return "ok";
    }

    @GetMapping("/v3/simple/{storeId}/{customerId}")
    public String simple(@PathVariable Long storeId, @PathVariable Long customerId) {
        return "ok";
    }

}
