package com.me.viewcount.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StoreViewCount {

    @Id
    @GeneratedValue
    @Column(name = "store_view_count_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "view_count_id")
    private ViewCount viewCount;

    private Long count;
    private LocalDate date;


    private StoreViewCount(Store store, ViewCount viewCount, LocalDate viewCountDate) {
        this.store = store;
        this.viewCount = viewCount;
        this.count = 0L;
        this.date = viewCountDate;
    }

    public static StoreViewCount create(Store store, ViewCount viewCount) {
        StoreViewCount storeViewCount = new StoreViewCount(store, viewCount, viewCount.getCountDate());
        store.getStoreViewCounts().add(storeViewCount);
        viewCount.getStoreViewCountList().add(storeViewCount);
        return storeViewCount;
    }

    public void add() {
        this.count++;
    }


}
