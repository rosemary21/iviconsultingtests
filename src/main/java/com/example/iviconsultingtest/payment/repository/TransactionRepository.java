package com.example.iviconsultingtest.payment.repository;

import com.example.iviconsultingtest.payment.model.Payment;
import com.example.iviconsultingtest.payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction,Long> {
}
