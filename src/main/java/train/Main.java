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

        Locomotive firstLocomotive = new Locomotive();
        firstLocomotive.setTypeOfDesignation("DSGNTN-1");
        firstLocomotive.setManufacturer("Honda");
        firstLocomotive.setSerialNumber("111-222-333");
        firstLocomotive.setEngineType(EngineType.ELECTRIC.toString());
        firstLocomotive.setYearOfProduction(2015);
        firstLocomotive.setEmptyWeight(130000);
        firstLocomotive.setLength(15);
        firstLocomotive.setTractiveEffort(1000000);
        firstLocomotive.setNumberOfPassengers(2);
        firstLocomotive.setGoodsLoadingWeight(200);

        Locomotive locoFromDb = locomotiveService.add(firstLocomotive);
        System.out.println(locomotiveService.get(locoFromDb.getId()));

        Wagon firstWagon = new Wagon();
        firstWagon.setTypeOfDesignation("DSGNTN-P");
        firstWagon.setManufacturer("CRRC");
        firstWagon.setSerialNumber("444-555-666");
        firstWagon.setWagonType(WagonType.PASSENGER.toString());
        firstWagon.setYearOfProduction(2015);
        firstWagon.setEmptyWeight(60000);
        firstWagon.setLength(20);
        firstWagon.setNumberOfPassengers(58);
        firstWagon.setGoodsLoadingWeight(5800);
        wagonService.add(firstWagon);

        Wagon secondWagon = new Wagon();
        secondWagon.setTypeOfDesignation("DSGNTN-S");
        secondWagon.setManufacturer("CRRC");
        secondWagon.setSerialNumber("777-888-999");
        secondWagon.setWagonType(WagonType.SLEEPING.toString());
        secondWagon.setYearOfProduction(2016);
        secondWagon.setEmptyWeight(60000);
        secondWagon.setLength(20);
        secondWagon.setNumberOfPassengers(55);
        secondWagon.setGoodsLoadingWeight(5000);
        wagonService.add(secondWagon);

        Wagon thirdWagon = new Wagon();
        thirdWagon.setTypeOfDesignation("DSGNTN-D");
        thirdWagon.setManufacturer("CRRC");
        thirdWagon.setSerialNumber("000-111-222");
        thirdWagon.setWagonType(WagonType.DINING.toString());
        thirdWagon.setYearOfProduction(2017);
        thirdWagon.setEmptyWeight(62000);
        thirdWagon.setLength(23);
        thirdWagon.setNumberOfPassengers(25);
        thirdWagon.setGoodsLoadingWeight(5800);
        wagonService.add(thirdWagon);

        Wagon fourthWagon = new Wagon();
        fourthWagon.setTypeOfDesignation("DSGNTN-G");
        fourthWagon.setManufacturer("CRRC");
        fourthWagon.setSerialNumber("333-444-555");
        fourthWagon.setWagonType(WagonType.GOODS.toString());
        fourthWagon.setYearOfProduction(2017);
        fourthWagon.setEmptyWeight(25000);
        fourthWagon.setLength(20);
        fourthWagon.setNumberOfPassengers(0);
        fourthWagon.setGoodsLoadingWeight(50000);
        wagonService.add(fourthWagon);

        Train trainOne = new Train();
        trainOne.setLocomotives(Set.of(firstLocomotive));
        trainOne.setWagons(Set.of(firstWagon, secondWagon, thirdWagon, fourthWagon));

        Train trainFromDb = trainService.add(trainOne);
        System.out.println(trainService.get(trainFromDb.getId()));

        System.out.println("Empty weight: "
                + trainService.getEmptyWeight(trainFromDb.getId()));

        System.out.println("Max passengers number: "
                + trainService.getMaxNumberOfPassengers(trainFromDb.getId()));

        System.out.println("Max goods loading weight: "
                + trainService.getMaxLoadingWeight(trainFromDb.getId()));

        System.out.println("Max train loading: "
                + trainService.getMaxTrainLoading(trainFromDb.getId()));

        System.out.println("Max train weight: "
                + trainService.getMaxTotalTrainWeight(trainFromDb.getId()));

        System.out.println("Length of the train: "
                + trainService.getTrainLength(trainFromDb.getId()));

        System.out.println("Train is capable to drive: "
                + trainService.isCapableToDrive(trainFromDb.getId()));

        System.out.println("Necessary number of conductors: "
                + trainService.getNecessaryConductorsNumber(trainFromDb.getId()));
    }
}
