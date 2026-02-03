package org.hit.hradar.domain.user.command.application.service;

import org.hit.hradar.domain.user.command.application.dto.request.ChangeMyPasswordRequest;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.global.client.AuthInternalClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MyPasswordCommandServiceTest {

    @InjectMocks
    private MyPasswordCommandService myPasswordCommandService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthInternalClient authInternalClient;

    @Test
    @DisplayName("비밀번호 변경 성공")
    void changeMyPassword_Success() {
        // given
        Long accId = 50L;
        ChangeMyPasswordRequest request = new ChangeMyPasswordRequest("oldPw", "newPw");

        Account account = Account.builder()
                .accId(accId)
                .password("hashed_old_pw")
                .isDeleted('N')
                .build();

        given(accountRepository.findByAccIdAndIsDeleted(accId, 'N')).willReturn(Optional.of(account));
        given(passwordEncoder.matches("oldPw", "hashed_old_pw")).willReturn(true);
        given(passwordEncoder.encode("newPw")).willReturn("hashed_new_pw");
        given(authInternalClient.revokeRefreshToken(anyLong())).willReturn(Mono.empty());

        // when
        myPasswordCommandService.changeMyPassword(accId, request);

        // then
        assertThat(account.getPassword()).isEqualTo("hashed_new_pw");
        verify(authInternalClient).revokeRefreshToken(accId);
    }
}
