package org.hit.hradar.domain.user.query.dto;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.user.query.dto.UserAccountListItemResponse;

@Getter
public class UserAccountListResponse {

  private final List<UserAccountListItemResponse> accounts;

  private UserAccountListResponse(List<UserAccountListItemResponse> accounts) {
    this.accounts = accounts;
  }

  public static UserAccountListResponse of(List<UserAccountListItemResponse> accounts) {
    return new UserAccountListResponse(accounts);
  }
}
