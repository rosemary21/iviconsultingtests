package com.example.iviconsultingtest.notification.service;

import com.example.iviconsultingtest.notification.dto.EmailDto;

public interface NotificationService {
    void sendEmail(EmailDto emailDto);
}
