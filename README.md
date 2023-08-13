### View-Count
- 조회수 문제를 어떻게 하면 잘 구현해 볼 수 있는지, 여러가지 방법을 시도해봤습니다.
- 각 결과에 대한 자세한 설명은 다음 글에서 확인할 수 있습니다. (https://ojt90902.tistory.com/1555)


#### VERSION1
- MySQL 트랜잭션을 이용해 하는 방법
- 성능 문제 / 동시성 문제 존재함. 


#### VERSION2
- `ConcurrentLinkedQue` + 스케쥴러를 이용해 처리하는 방법
- 결과
  - `요청 - 응답` 시간은 많이 개선됨.
  - `DB 정합성` 문제 발생. 이것은 `VERSION1`에서도 존재 했었음.


#### VERSION3
- `VERSION2` + 네임드락을 이용한 방법
- `VERSION2`의 DB 정합성 문제를 네임드락으로 해결함.
- 아래 문제가 있을 것으로 예상됨.
  - `ConcurrentLinekdQue`의 `CAS` 오버헤드
  - 수평 스케일링 환경에서`NamedLock`의 RaceCondition으로 인해, 큰 오버헤드 발생 시나리오 존재.
  - `NamedLock` 자체가 JPA와 맞지 않음. DB 종류마다 `NamedLock`을 얻기 위해 새로운 `Native Query` 작성 필요.

#### VERSION4
- TBD