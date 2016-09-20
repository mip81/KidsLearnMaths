package klm.mip.nz.klm;

import java.util.Random;

/**
 * Created by mikhailpastushkov on 9/20/16.
 */
public class MyRandomNumbers {
    public static int getWrongAnswer(int... answers) {
        boolean isConsist = true;
        int newWrongAnswer = new Random().nextInt(10);;
        while (isConsist) {
            isConsist = false;
            for (int i : answers) {
                if (i == newWrongAnswer) isConsist = true;

            }
        }
        return newWrongAnswer;
    }
}
