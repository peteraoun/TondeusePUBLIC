package lawnmower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import orientation.Orientation;

import java.util.ArrayList;
import java.util.List;

public class LawnMowerProcessor implements ItemProcessor<List<String>, List<LawnMower>> {

    private static Logger log = LoggerFactory.getLogger(LawnMowerProcessor.class);

    private int maxX;
    private int maxY;
    private final int numberOfMowerData = 4;

    @Override
    public List<LawnMower> process(List<String> item) {
        List<LawnMower> resultingMowers = new ArrayList<>();
        int i = updateLawnSize(item.get(0), item.get(1));
        int numberOfValidMowers = (item.size() - i) / numberOfMowerData;
        checkIfMowerDataIsMissingAndLog(item.size()-i);
        while(numberOfValidMowers != 0){
            updateMowersList(item.get(i),
                    item.get(i+1),
                    item.get(i+2).charAt(0),
                    item.get(i+3),
                    resultingMowers);
            i+=4;
            numberOfValidMowers--;
        }
        return resultingMowers;
    }

    private int updateLawnSize(String lawnX, String lawnY){
        if (maxX == 0 && maxY == 0) {
            maxX = Integer.parseInt(lawnX);
            maxY = Integer.parseInt(lawnY);
            return 2;
        }
        return 0;
    }

    private void updateMowersList(String x, String y, char orientation,
                                  String instructions,
                                  List<LawnMower> mowers){
        int xPos = Integer.parseInt(x);
        int yPos = Integer.parseInt(y);
        if (xPos < 0 || xPos > maxX || yPos < 0 || yPos > maxY) {
            throw new IllegalArgumentException("Invalid mower position: (" + xPos + ", " + yPos + ")");
        }
        LawnMower mower = new LawnMower(xPos,
                yPos,
                Orientation.valueOf(String.valueOf(orientation)));
        mower.move(instructions, maxX, maxY);
        mowers.add(mower);
    }

    private void checkIfMowerDataIsMissingAndLog(int totalMowerData){
        if(totalMowerData % numberOfMowerData != 0){
            log.info("One or more mower data are missing!");
        }
    }

    public void setLogger(Logger logger) {
        log = logger;
    }
}
