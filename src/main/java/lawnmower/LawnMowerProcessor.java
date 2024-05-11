package lawnmower;

import org.springframework.batch.item.ItemProcessor;
import orientation.Orientation;

import java.util.ArrayList;
import java.util.List;

public class LawnMowerProcessor implements ItemProcessor<List<String>, List<LawnMower>> {

    private int maxX;
    private int maxY;

    @Override
    public List<LawnMower> process(List<String> item) {
        int numberOfMowers = (item.size() - 2) / 4;
        List<LawnMower> resultingMowers = new ArrayList<>();
        int i = updateLawnSize(item.get(0), item.get(1));

        while(numberOfMowers != 0){
            updateMowersList(item.get(i),
                    item.get(i+1),
                    item.get(i+2).charAt(0),
                    item.get(i+3),
                    resultingMowers);
            i+=4;
            numberOfMowers--;
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
        LawnMower mower = new LawnMower(Integer.parseInt(x),
                Integer.parseInt(y),
                Orientation.valueOf(String.valueOf(orientation)));
        mower.move(instructions, maxX, maxY);
        mowers.add(mower);
    }
}
