package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.*;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrentUserService {
    @Autowired
    private CurrentAuthentication currentAuthentication;

    public User getCurrentUser() {
        return (User) currentAuthentication.getAuthentication().getPrincipal();
    }

    public boolean isAdmin(User user) {
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().compareTo("system-full-access") == 0) {
                return true;
            }
        }

        return false;
    }

    public <T extends Resource> List<T> getUserAccessibleResources(List<T> resources, User user) {
        Office userOffice = user.getOffice();

        List<Resource> userReservedResources = user.getReservations().stream()
                .map(Reservation::getResource)
                .collect(Collectors.toList());

        return resources.stream()
                .filter(resource -> resource.getOffice().getId().compareTo(userOffice.getId()) == 0 ||
                        userReservedResources.contains(resource))
                .collect(Collectors.toList());
    }
}