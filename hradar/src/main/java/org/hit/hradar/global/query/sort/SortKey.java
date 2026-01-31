package org.hit.hradar.global.query.sort;

/**
 * MyBatis에서 ${} 로 컬럼을 주입하는 건 위험하니,
 * "SortKey enum"으로만 정렬 허용 + XML에서 <choose>로 매핑한다.
 */
public interface SortKey {
    String name();
}
