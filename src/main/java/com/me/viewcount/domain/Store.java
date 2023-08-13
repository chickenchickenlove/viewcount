package com.me.viewcount.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Store {

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<StoreViewCount> storeViewCounts = new ArrayList<>();

    private String name;

    private Store(String name) {
        this.name = name;
    }

    public static Store createStore(String name) {
        return new Store(name);
    }
}
