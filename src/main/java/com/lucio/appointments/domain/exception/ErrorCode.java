package com.lucio.appointments.domain.exception;

public enum ErrorCode {

    // Appointment
    APPOINTMENT_OVERLAP,
    APPOINTMENT_NOT_FOUND,

    // User
    USER_NOT_FOUND,

    // Organization
    TENANT_NOT_FOUND,
    MEMBERSHIP_NOT_FOUND,

    // Generic
    BUSINESS_ERROR
}