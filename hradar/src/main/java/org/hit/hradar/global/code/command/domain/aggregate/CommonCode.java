package org.hit.hradar.global.code.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "common_code")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonCode {

    @Id
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code", nullable = false)
    private CodeGroup groupCode;

    @Column(name = "code_name", nullable = false, length = 50)
    private String codeName;

    @Column(name = "code_order", nullable = false)
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang", nullable = false)
    private Language lang;

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    @Builder
    private  CommonCode (
            String code,
            CodeGroup groupCode,
            String codeName,
            Integer codeOrder,
            Language lang
    ){
        this.code = code;
        this.groupCode = groupCode;
        this.codeName = codeName;
        this.order = codeOrder;
        this.lang = lang;
    }

    public void update(String codeName, int order) {
        this.codeName = codeName;
        this.order = order;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}
