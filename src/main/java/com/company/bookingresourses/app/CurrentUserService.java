package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.*;
import io.jmix.core.security.CurrentAuthentication;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrentUserService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CurrentUserService.class);
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

    public List<Resource> getUserAccessibleResources(List<? extends Resource> resources, User user) {
        Office userOffice = user.getOffice();

        List<Resource> userReservedResources = user.getReservations().stream()
                .map(Reservation::getResources)
                .collect(Collectors.toList());

        return resources.stream()
                .filter(resource -> resource.getOffice().getId().compareTo(userOffice.getId()) == 0 ||
                        userReservedResources.contains(resource))
                .collect(Collectors.toList());
    }

    public List<Item> getUserAccessibleItems(List<Item> items, User user) {
        return getUserAccessibleResources(items, user).stream()
                .map(resource -> (Item) resource)
                .collect(Collectors.toList());
    }

    public List<Cabinet> getUserAccessibleCabinets(List<Cabinet> items, User user) {
        return getUserAccessibleResources(items, user).stream()
                .map(resource -> (Cabinet) resource)
                .collect(Collectors.toList());
    }
}