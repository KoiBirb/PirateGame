package tiles;

import java.security.SecureRandom;
import java.util.*;


public class Cell implements Comparable<Cell>{

    private ArrayList<int[]> options;
    public double[] weight = {1,1,1,1,1,1,1,1};
    private boolean collapsed;
    private final SecureRandom random = new SecureRandom();
    private final HashMap<int[], Integer> optionMap;
    private final int[] defaultTile;

    public Cell(ArrayList<int[]> options, HashMap<int[], Integer> optionMap){
        this.options = options;
        this.optionMap = optionMap;
        this.defaultTile = options.get(0);
        collapsed = false;
    }

    public double entropy(){
        double entropy = 0;

        for (double probability : this.weight) {
            if (probability > 0) {
                entropy -= probability * (Math.log(probability) / Math.log(2));
            }
        }
        if (entropy == 0) {
            entropy = 999;
        }
        return entropy;
    }

    private double minWeightSize(double[] weight){
        double min = weight[0];
        for (double v : weight) {
            if (v < min) {
                min = v;
            }
        }
        return min;
    }

    public void collapse(){
            try {
                if (weight[0] != 1) {
                    int[] tileChoice = null;
                    double[] collapsedTileWeight = new double[options.size()];
                    collapsedTileWeight[0] = 1;
                    for(int i = 0; i < collapsedTileWeight.length; i++) {
                        int index = i;
                        if (index != 0) {
                            index--;
                        }
                        collapsedTileWeight[i] = collapsedTileWeight[index] - weight[optionMap.get(options.get(i))];
                    }
                    double randomDouble = random.nextDouble(minWeightSize(collapsedTileWeight),1);
                    for(int i = 0; i < collapsedTileWeight.length; i++) {
                        if (randomDouble >= collapsedTileWeight[i]){
                            tileChoice = options.get(i);
                            break;
                        }
                    }
                    options = new ArrayList<>(Collections.singletonList(tileChoice));
                }else{
                    options = new ArrayList<>(Collections.singletonList(options.get(random.nextInt(options.size()))));
                }
            } catch (IndexOutOfBoundsException e){
                options = new ArrayList<>(Collections.singletonList(defaultTile));
            }
        collapsed = true;
    }

    public boolean isCollapsed(){
        return collapsed;
    }

    public void setOptions(ArrayList<int[]> options){
        this.options = options;
    }

    public void setWeight(double[] weight){
        this.weight = weight;
    }

    public ArrayList<int[]> getOptions() {
        return options;
    }

    @Override
    public int compareTo(Cell o) {
        return Double.compare(entropy(), o.entropy());
    }
}
