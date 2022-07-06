package com.company.bookingresourses.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "CABINET")
@JmixEntity
@Entity
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "ID")
public class Cabinet extends Resource {
    @Column(name = "HAS_PROJECTOR", nullable = false)
    @NotNull
    private Boolean hasProjector = false;

    @Column(name = "HAS_INTERACTIVE_WHITEBOARD", nullable = false)
    @NotNull
    private Boolean hasInteractiveWhiteboard = false;

    @Column(name = "NUMBER_OF_SEATS", nullable = false)
    @NotNull
    private Integer numberOfSeats;

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