package com.me.viewcount.v2;

import com.me.viewcount.MyRepository;
import com.me.viewcount.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
@Transactional(readOnly = false)
@Slf4j
public class MyServiceV2 {

    private final MyRepository myRepository;

    public MyServiceV2(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public boolean tx1(Long customerId, Long storeId) {
        Store store = myRepository.findStore(storeId);
        Customer customer = myRepository.findCustomer(customerId);

        CustomerStoreVisit customerStoreVisitByCondition = myRepository.findCustomerStoreVisitByCondition(customerId, storeId, LocalDate.now());
        if (customerStoreVisitByCondition != null) {
            log.info("already exist");
            return false ;
        }

        CustomerStoreVisit customerStoreVisit = CustomerStoreVisit.create(customer, store, LocalDate.now());
        myRepository.saveCustomerStoreVisit(customerStoreVisit);
        log.info("tx1 end");
        return true;
    }

    public void tx1Update(Long storeId) {

        LocalDate now = LocalDate.now();
        StoreViewCount findSVC = myRepository.findStoreViewCount(storeId, now);
        if (findSVC != null) {
            findSVC.add();
            return;
        }

        Store store = myRepository.findStore(storeId);
        ViewCount viewCount = ViewCount.create(LocalDate.now());

        myRepository.saveViewCount(viewCount);
        StoreViewCount storeViewCount = StoreViewCount.create(store, viewCount);
        storeViewCount.add();

        myRepository.saveStoreViewCount(storeViewCount);
    }

}
