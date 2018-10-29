package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sys {
    private Integer type;
    private Long id;
    private Double message;
    private String country;
    private Long sunrise;
    private Long sunset;
    private String pod;
}
