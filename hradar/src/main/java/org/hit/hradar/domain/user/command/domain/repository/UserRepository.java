package org.hit.hradar.domain.user.command.domain.repository;

import org.hit.hradar.domain.user.command.domain.aggregate.Account;

public interface UserRepository {

  Account save(Account account);

}
