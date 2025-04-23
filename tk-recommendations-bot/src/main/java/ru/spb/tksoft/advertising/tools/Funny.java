package ru.spb.tksoft.advertising.tools;

import java.util.Random;

public final class Funny {

    private Funny() {
    }

    private static final String[] fooNamesEn = {
            "🍯 Honey",
            "Babe",
            "Darling",
            "😘 Sweetheart",
            "😍Sweety",
            "Sweetie",
            "Sweetums",
            "🌞 Sunshine",
            "❤️ My Love",
            "My Dear",
            "Dearie",
            "Dearheart",
            "😊 Cutie",
            "Bubbles",
            "🎃 Pumpkin",
            "Sweetie Pie",
            "Snookums",
            "Pookie",
            "👻 Boo",
            "Muffin",
            "Lovebug",
            "🙄 Wifey",
            "Shorty",
            "🧁 Cupcake"
    };

    private static final Random random = new Random();

    public static String getRandomName() {
        return fooNamesEn[random.nextInt(0, fooNamesEn.length - 1)];
    }

    public static String getRandomNameOrReal(int from100,
            final String randomName, final String realName) {
        if (from100 <= 0) {
            from100 = 1;
        } else if (from100 > 100) {
            from100 = 100;
        }
        return random.nextInt(1, 100) <= from100 ? randomName : realName;
    }
}
