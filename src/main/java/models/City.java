package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class City {
    private Long id;
    private String name;
    private Coord coord;
    private String country;
    private Long population;
}
