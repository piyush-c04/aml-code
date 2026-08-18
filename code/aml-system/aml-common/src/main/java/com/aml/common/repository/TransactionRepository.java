package com.aml.common.repository;

import com.aml.common.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
