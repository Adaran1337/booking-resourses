package com.company.bookingresourses.screen.item;

import com.company.bookingresourses.entity.Reservation;
import com.company.bookingresourses.entity.Resource;
import com.company.bookingresourses.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {
    @Autowired
    private CurrentAuthentication currentAuthentication;

    public boolean isReserved(Resource resource) {
        List<Reservation> reservations = resource.getReservations();
        User employee = (User) currentAuthentication.getAuthentication().getPrincipal();

        for (GrantedAuthority authority : employee.getAuthorities()) {
            if (authority.getAuthority().compareTo("system-full-access") == 0) {
                return false;
            }
        }

        LocalDateTime currentDate = LocalDateTime.now();

        for (Reservation reservation : reservations) {
            if (reservation.getEmployee().getId().compareTo(employee.getId()) == 0 &&
                    reservation.getStartDate().isBefore(currentDate) &&
                    reservation.getEndDate().isAfter(currentDate)) {
                return false;
            }
        }

        return true;
    }
}