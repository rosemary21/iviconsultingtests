package com.example.iviconsultingtest.payment.repository;

import com.example.iviconsultingtest.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment,Long> {

    Payment findByReference(String reference);
}
