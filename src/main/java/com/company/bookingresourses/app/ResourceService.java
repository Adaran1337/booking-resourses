package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.Reservation;
import com.company.bookingresourses.entity.Resource;
import com.company.bookingresourses.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceService {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CurrentUserService currentUserService;

    public boolean isReserved(Resource resource) {
        List<Reservation> reservations = resource.getReservations();
        User user = currentUserService.getCurrentUser();

        if (currentUserService.isAdmin(user)) {
            return false;
        }

        LocalDateTime currentDate = LocalDateTime.now();

        for (Reservation reservation : reservations) {
            if (reservation.getEmployee().getId().compareTo(user.getId()) == 0 &&
                    reservation.getStartDate().isBefore(currentDate) &&
                    reservation.getEndDate().isAfter(currentDate)) {
                return false;
            }
        }

        return true;
    }
}