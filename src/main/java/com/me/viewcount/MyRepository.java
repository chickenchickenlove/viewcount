package com.me.viewcount;

import com.me.viewcount.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyRepository {

    private final EntityManager em;

    public Long saveCustomer(Customer customer) {
        em.persist(customer);
        return customer.getId();
    }

    public Long saveStore(Store store) {
        em.persist(store);
        return store.getId();
    }

    public Long saveViewCount(ViewCount viewCount) {
        em.persist(viewCount);
        return viewCount.getId();
    }

    public Long saveStoreViewCount(StoreViewCount storeViewCount) {
        em.persist(storeViewCount);
        return storeViewCount.getId();
    }

    public Long saveCustomerStoreVisit(CustomerStoreVisit customerStoreVisit) {
        em.persist(customerStoreVisit);
        return customerStoreVisit.getId();
    }

    public Customer findCustomer(Long customerId) {
        return em.find(Customer.class, customerId);
    }

    public CustomerStoreVisit findCustomerStoreVisitByCondition(Long customerId,
                                                                Long storeId,
                                                                LocalDate visitDate) {

        List<CustomerStoreVisit> results = em.createQuery("SELECT csv FROM CustomerStoreVisit csv WHERE csv.customer.id =: customerId and csv.store.id =: storeId and csv.visitDate =: visitDate", CustomerStoreVisit.class)
                .setParameter("customerId", customerId)
                .setParameter("storeId", storeId)
                .setParameter("visitDate", visitDate)
                .getResultList();

        return results.size() > 0 ?
                results.get(0) :
                null;
    }

    public StoreViewCount findStoreViewCount(Long storeId,
                                             LocalDate visitDate) {

        List<StoreViewCount> results = em.createQuery("SELECT svc FROM StoreViewCount svc WHERE svc.store.id =: storeId and svc.date =: visitDate", StoreViewCount.class)
                .setParameter("storeId", storeId)
                .setParameter("visitDate", visitDate)
                .getResultList();

        return results.size() > 0 ?
                results.get(0) :
                null;
    }


    public Store findStore(Long storeId) {
        return em.find(Store.class, storeId);
    }
}
