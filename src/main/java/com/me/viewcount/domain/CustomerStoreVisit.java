package com.me.viewcount.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CustomerStoreVisit {

    @Id
    @GeneratedValue
    @Column(name = "customer_store_visit")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    private LocalDate visitDate;

    private CustomerStoreVisit(Customer customer, Store store, LocalDate visitDate) {

        if (customer == null || store == null || visitDate == null) {
            throw new RuntimeException();
        }

        this.customer = customer;
        this.store = store;
        this.visitDate = visitDate;
    }

    public static CustomerStoreVisit create(Customer customer, Store store, LocalDate visitDate) {
        return new CustomerStoreVisit(customer, store, visitDate);
    }
}
