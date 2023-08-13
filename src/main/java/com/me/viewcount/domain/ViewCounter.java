package com.me.viewcount.domain;

import java.util.concurrent.locks.ReentrantLock;

public interface ViewCounter {
    void visit(Long customerId, Long storeId);

    void commit();

}
