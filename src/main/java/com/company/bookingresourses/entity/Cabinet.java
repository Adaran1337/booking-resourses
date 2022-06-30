package com.company.bookingresourses.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@JmixEntity
@Table(name = "CABINET")
@Entity
public class Cabinet extends Resourse {
    @Column(name = "HAS_PROJECTOR", nullable = false)
    @NotNull
    private Boolean hasProjector = false;

    @Column(name = "HAS_INTERACTIVE_WHITEBOARD", nullable = false)
    @NotNull
    private Boolean hasInteractiveWhiteboard = false;

    @Column(name = "NUMBER_OF_SEATS", nullable = false)
    @NotNull
    private Integer numberOfSeats;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "cabinet")
    private List<Reservation> reservations;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Boolean getHasInteractiveWhiteboard() {
        return hasInteractiveWhiteboard;
    }

    public void setHasInteractiveWhiteboard(Boolean hasInteractiveWhiteboard) {
        this.hasInteractiveWhiteboard = hasInteractiveWhiteboard;
    }

    public Boolean getHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(Boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    @InstanceName
    @DependsOnProperties({"name"})
    public String getInstanceName() {
        return String.format("%s", getName());
    }
}