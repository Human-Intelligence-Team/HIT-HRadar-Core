package org.hit.hradar.domain.companyApplication.command.infrastructure;

import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.repository.ComAppRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ComAppJpaRepository
    extends JpaRepository<CompanyApplication, Long>, ComAppRepository {



}
