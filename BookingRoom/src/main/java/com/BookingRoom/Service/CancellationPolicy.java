package com.BookingRoom.Service;



import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CancellationPolicy {

    /**
     * Calculates the refund amount based on the cancellation date.
     *
     * @param checkInDate     the date the booking was supposed to start
     * @param cancellationDate the date the booking was cancelled
     * @return the refund amount
     */
    public static double calculateRefund(LocalDate checkInDate, LocalDate cancellationDate) {
        long daysBetween = ChronoUnit.DAYS.between(cancellationDate, checkInDate);
        double refund;

        if (daysBetween >= 14) {
            // Full refund if cancellation is more than 14 days in advance
            refund = 1.0;
        } else if (daysBetween >= 7) {
            // 50% refund if cancellation is between 7 and 13 days in advance
            refund = 0.5;
        } else {
            // No refund if cancellation is less than 7 days in advance
            refund = 0.0;
        }

        return refund;
    }
}



