package org.hit.hradar.global.audit;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.context.RequestUserContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestScopeAuditorAware implements AuditorAware<Long> {

    private final RequestUserContext requestUserContext;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long userId = requestUserContext.userId();
        return userId != null
                ? Optional.of(userId)
                : Optional.of(0L); // 또는 SYSTEM ID
    }
}
