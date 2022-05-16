package train.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "wagons")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Wagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeOfDesignation;
    private String manufacturer;
    private String serialNumber;
    private String wagonType;
    private int yearOfProduction;
    private int emptyWeight;
    private int length;
    private int numberOfPassengers;
    private int goodsLoadingWeight;

}
