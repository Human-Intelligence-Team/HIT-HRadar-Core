package org.hit.hradar.domain.user.command.application.service;

import org.hit.hradar.domain.user.command.application.dto.request.CreateFirstUserRequest;
import org.hit.hradar.domain.user.command.application.dto.request.UpdateUserAccountRequest;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.domain.user.command.infrastructure.AccountJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

        @InjectMocks
        private UserService userService;

        @Mock
        private AccountRepository accountRepository;

        @Mock
        private AccountJpaRepository userJpaRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Test
        @DisplayName("첫 사용자 계정 생성 성공")
        void createFirstUserRequest_Success() {
                // given
                Long comId = 1L;
                String companyCode = "COM123";
                Long empId = 1001L;

                Account savedAccount = Account.builder()
                                .accId(50L)
                                .loginId("admin01")
                                .build();

                given(userJpaRepository.existsByComIdAndLoginIdAndStatus(any(), anyString(), any())).willReturn(false);
                given(passwordEncoder.encode(anyString())).willReturn("hashed_pw");
                given(accountRepository.save(any(Account.class))).willReturn(savedAccount);

                // when
                CreateFirstUserRequest response = userService.createFirstUserRequest(comId, companyCode, empId,
                                "admin01",
                                "CEO", "ceo@company.com", "password");

                // then
                assertThat(response.getAccId()).isEqualTo(50L);
                assertThat(response.getLoginId()).isEqualTo("admin01");
        }

        @Test
        @DisplayName("사용자 계정 정보 수정 성공")
        void updateUserAccount_Success() {
                // given
                Long accId = 50L;
                Long comId = 1L;
                UpdateUserAccountRequest request = new UpdateUserAccountRequest("newid", "New Name", "new@test.com");

                Account account = Account.builder()
                                .accId(accId)
                                .comId(comId)
                                .loginId("oldid")
                                .name("Old Name")
                                .status(AccountStatus.ACTIVE)
                                .build();

                given(userJpaRepository.findByAccIdAndComIdAndStatus(accId, comId, AccountStatus.ACTIVE))
                                .willReturn(Optional.of(account));
                given(userJpaRepository.existsByComIdAndLoginIdAndStatus(comId, "newid", AccountStatus.ACTIVE))
                                .willReturn(false);

                // when
                userService.updateUserAccount(accId, comId, request);

                // then
                assertThat(account.getLoginId()).isEqualTo("newid");
                assertThat(account.getName()).isEqualTo("New Name");
        }
}
