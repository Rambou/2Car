package gr.rambou.s2car;

import java.util.Random;

public class Cheeses {

    public static final String[] sCheeseStrings = {
            "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru", "Kawasaki z750", "BMW", "Ferrar", "Renaut", "Crysler",
            "Honda", "Ducati", "Subaru",
    };
    private static final Random RANDOM = new Random();

    public static int getRandomCheeseDrawable() {
        switch (RANDOM.nextInt(5)) {
            default:
            case 0:
                return R.drawable.car_1;
            case 1:
                return R.drawable.car_2;
            case 2:
                return R.drawable.car_3;
            case 3:
                return R.drawable.car_4;
            case 4:
                return R.drawable.car_5;
        }
    }

}