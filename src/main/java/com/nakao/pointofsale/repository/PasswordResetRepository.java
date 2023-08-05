package com.nakao.pointofsale.repository;

import com.nakao.pointofsale.model.PasswordReset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordReset, String> {
}
