package com.lucio.appointments.domain.exception;

public class AppointmentOverlapException extends BusinessException {

    public AppointmentOverlapException() {
        super("Appointment overlaps with existing schedule",
                ErrorCode.APPOINTMENT_OVERLAP);
    }
}
