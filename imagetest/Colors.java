/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetest;

/**
 *
 * @author Tobias
 */
public class Colors {

    public static int[] convert(int depth, int max) {
        double fraction = ((double) depth / max) * 4;
        int truncatedX = (int) fraction;
        int truncatedY = (int) (255 * (fraction - truncatedX));

        switch (truncatedX) {
            case 0:
                return new int[]{0, truncatedY, 0};
            case 1:
                return new int[]{0, 255, truncatedY};
            case 2:
                return new int[]{0, 255, 255 - truncatedY};
            case 3:
                return new int[]{255, truncatedY, 0};
            case 4:
                return new int[]{255, 0, 255 - truncatedY};
            default:
                return new int[]{1, 2, 3};
        }
    }
}
