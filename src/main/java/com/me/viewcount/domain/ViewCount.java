package com.me.viewcount.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewCount {

    @Id
    @GeneratedValue
    @Column(name = "view_count_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "viewCount")
    private List<StoreViewCount> storeViewCountList = new ArrayList<>();

    private LocalDate countDate;

    private ViewCount(LocalDate countDate) {
        this.countDate = countDate;
    }

    // 특정 날짜에 대한 것들 다 가진다.
    public static ViewCount create(LocalDate localDate) {
        return new ViewCount(localDate);
    }


}
