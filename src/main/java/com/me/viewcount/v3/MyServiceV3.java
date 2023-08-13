package com.me.viewcount.v3;

import com.me.viewcount.MyRepository;
import com.me.viewcount.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
@Transactional(readOnly = false)
@Slf4j
public class MyServiceV3 {

    private final MyRepositoryV3 myRepository;

    public MyServiceV3(MyRepositoryV3 myRepository) {
        this.myRepository = myRepository;
    }

    public void tx1(Long customerId, Long storeId) {

        Store store = myRepository.findStore(storeId);
        Customer customer = myRepository.findCustomer(customerId);

        final String lockName = customerId + "-" + storeId;

        boolean acquireLock = false;
        for (int i = 0; i < 10; i++) {
            acquireLock = myRepository.acquireNamedLock(lockName);
            if (acquireLock) {
                log.info("get lock");
                break;
            }
        }

        if (!acquireLock) {
            throw new RuntimeException("fail to get Lock");
        }

        LocalDate now = LocalDate.now();
        CustomerStoreVisit customerStoreVisitByCondition =
                myRepository.findCustomerStoreVisitByCondition(customerId, storeId, now);

        if (customerStoreVisitByCondition != null) {
            log.info("already exist");
            log.info("release lock");
            myRepository.releaseNamedLock(lockName);
            return;
        }

        CustomerStoreVisit customerStoreVisit = CustomerStoreVisit.create(customer, store, now);
        myRepository.saveCustomerStoreVisit(customerStoreVisit);

        StoreViewCount findSVC = myRepository.findStoreViewCount(storeId, now);
        if (findSVC != null) {
            findSVC.add();
            return;
        }

        ViewCount viewCount = ViewCount.create(now);

        myRepository.saveViewCount(viewCount);
        StoreViewCount storeViewCount = StoreViewCount.create(store, viewCount);
        storeViewCount.add();

        myRepository.saveStoreViewCount(storeViewCount);
        myRepository.releaseNamedLock(lockName);
        log.info("release lock");
        return;
    }
}
