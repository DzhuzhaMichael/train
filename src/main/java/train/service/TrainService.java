package train.service;

import train.model.Train;

public interface TrainService {
    Train add(Train train);

    Train get(Long id);

    int getEmptyWeight(Long id);

    int getMaxNumberOfPassengers(Long id);

    int getMaxLoadingWeight(Long id);

    int getMaxTrainLoading(Long id);

    int getMaxTotalTrainWeight(Long id);

    int getTrainLength(Long id);

    boolean isCapableToDrive(Long id);

    int getNecessaryConductorsNumber(Long id);
}
