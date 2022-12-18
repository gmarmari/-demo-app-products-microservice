package gmarmari.demo.microservices.products;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

public class CommonDataFactory {

    private static final Random random = new Random();

    private static final String abc = "abcdefghijklmnopqrstuvwxyz";
    private CommonDataFactory() {
        // Hide constructor
    }

    public static boolean aBoolean() {
        return random.nextBoolean();
    }

    public static int aInt() {
        return random.nextInt();
    }

    public static int aInt(int bound) {
        return random.nextInt(bound);
    }

    public static long aLong() {
        return random.nextLong();
    }

    public static double aDouble() {
        return random.nextDouble();
    }

    public static String aText() {
        return "" + abc.charAt(aInt(abc.length()))
                + abc.charAt(aInt(abc.length()))
                + abc.charAt(aInt(abc.length()))
                + " "
                + abc.charAt(aInt(abc.length()))
                + abc.charAt(aInt(abc.length()))
                + abc.charAt(aInt(abc.length()));
    }

    public static String aNullableText() {
        return aBoolean() ? aText() : null;
    }

    public static ZonedDateTime aDate() {
        return ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    }

}
