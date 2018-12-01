package org.priyanka.cmpe220.dataobj;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

@Embedded
public class LocationDo {

    private String type = "Point";
    private List<Double> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

}
