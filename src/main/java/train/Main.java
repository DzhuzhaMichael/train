package train;

import java.util.Set;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import train.config.AppConfig;
import train.model.EngineType;
import train.model.Locomotive;
import train.model.Train;
import train.model.Wagon;
import train.model.WagonType;
import train.service.LocomotiveService;
import train.service.TrainService;
import train.service.WagonService;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        LocomotiveService locomotiveService
                = context.getBean(LocomotiveService.class);
        WagonService wagonService = context.getBean(WagonService.class);
        TrainService trainService = context.getBean(TrainService.class);

        Locomotive locomotiveOne = new Locomotive();
        locomotiveOne.setTypeOfDesignation("DSGNTN-1");
        locomotiveOne.setManufacturer("Honda");
        locomotiveOne.setSerialNumber("111-222-333");
        locomotiveOne.setEngineType(EngineType.ELECTRIC.toString());
        locomotiveOne.setYearOfProduction(2015);
        locomotiveOne.setEmptyWeight(130000);
        locomotiveOne.setLength(15);
        locomotiveOne.setTractiveEffort(1000000);
        locomotiveOne.setNumberOfPassengers(4);
        locomotiveOne.setGoodsLoadingWeight(200);

        Locomotive locoFromDb = locomotiveService.add(locomotiveOne);
        System.out.println(locomotiveService.get(locoFromDb.getId()));

        Wagon wagonOne = new Wagon();
        wagonOne.setTypeOfDesignation("DSGNTN-A");
        wagonOne.setManufacturer("CRRC");
        wagonOne.setSerialNumber("444-555-666");
        wagonOne.setWagonType(WagonType.PASSENGER.toString());
        wagonOne.setYearOfProduction(2015);
        wagonOne.setEmptyWeight(60000);
        wagonOne.setLength(20);
        wagonOne.setNumberOfPassengers(58);
        wagonOne.setGoodsLoadingWeight(5800);

        Wagon wagonFromBd = wagonService.add(wagonOne);
        System.out.println(wagonService.get(wagonFromBd.getId()));

        Train trainOne = new Train();
        trainOne.setLocomotives(Set.of(locomotiveOne));
        trainOne.setWagons(Set.of(wagonOne));

        Train trainFromDb = trainService.add(trainOne);
//        System.out.println(trainService.get(trainFromDb.getId()));
    }
}
