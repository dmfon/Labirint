package labirint.interra.com.labirint;

public abstract class ListId
{
    public static final int EMPTY_ID = 0;
    public static final int TREASURE_ID = 1;
    public static final int ARSENAL_ID = 2;
    public static final int HOSPITAL_ID = 3;
    public static final int TOP_RIVER_ID = 4;
    public static final int RIGHT_RIVER_ID = 5;
    public static final int BOTTOM_RIVER_ID = 6;
    public static final int LEFT_RIVER_ID = 7;
    public static final int PORTAL_ID = 8;
    public static final int STOK_ID = 9;

    public static final int GLOBAL_WALL_ID = 0;
    public static final int EMPTY_WALL_ID = 1;
    public static final int EXIT_WALL_ID = 2;
    public static final int INSIDE_WALL_ID = 3;
    public static final int ISTOK_WALL_ID = 4;

    public static final int RES_STOP = 0;
    public static final int RES_STOK = 1;
    public static final int RES_NEW_TOP = 2;
    public static final int RES_NEW_RIGHT = 3;
    public static final int RES_NEW_BOTTOM = 4;
    public static final int RES_NEW_LEFT = 5;
    public static final int RES_NEW_PORTAL = 6;
    public static final int RES_WIN = 7;
    public static final int RES_FIRE_YES = 8;
    public static final int RES_FIRE_NO = 9;
    public static final int RES_BLOWON_YES = 10;
    public static final int RES_BLOWON_GLOBAL = 11;
    public static final int RES_BLOWON_NO = 12;
    public static final int RES_KNIFE_YES = 13;
    public static final int RES_KNIFE_YES_MANY = 14;
    public static final int RES_KNIFE_NO = 15;
    public static final int RES_EXIT = 16;
    public static final int RES_ARSENAL_YES = 17;
    public static final int RES_HOSPITAL_YES = 18;
    public static final int RES_TREASURE_YES = 19;
    public static final int RES_EMPTY = 20;
    public static final int RES_WALL = 21;
    public static final int RES_FIRE_YES_MANY = 22;
    public static final int RES_TREASURE_NO = 23;
    public static final int RES_HOSPITAL_NO = 24;
    public static final int RES_ARSENAL_NO = 25;
    public static final int RES_WALL_PORTAL = 26;
    public static final int RES_WALL_RIVER_TOP = 27;
    public static final int RES_WALL_RIVER_RIGHT = 28;
    public static final int RES_WALL_RIVER_BOTTOM = 29;
    public static final int RES_WALL_RIVER_LEFT = 30;

    public static final int RES_EXTRA_RIVER_TOP = 31;
    public static final int RES_EXTRA_RIVER_RIGHT = 32;
    public static final int RES_EXTRA_RIVER_BOTTOM = 33;
    public static final int RES_EXTRA_RIVER_LEFT = 34;
    public static final int RES_EXTRA_ARSENAL = 35;
    public static final int RES_EXTRA_PORTAL = 36;

    public static final int RES_EXTRA1 = 50;
    public static final int RES_EXTRA2 = 100;

    public static final int SEND_WOUND = 0;

    public static String idInf()
    {
        String a;
        System.out.println("Cell ID:");
        System.out.println();
        System.out.println("EMPTY_ID = " + EMPTY_ID);
        System.out.println("TREASURE_ID = " + TREASURE_ID);
        System.out.println("ARSENAL_ID = " + ARSENAL_ID);
        System.out.println("HOSPITAL_ID = " + HOSPITAL_ID);
        System.out.println("TOP_RIVER_ID = " + TOP_RIVER_ID);
        System.out.println("RIGHT_RIVER_ID = " + RIGHT_RIVER_ID);
        System.out.println("BOTTOM_RIVER_ID = " + BOTTOM_RIVER_ID);
        System.out.println("LEFT_RIVER_ID = " + LEFT_RIVER_ID);
        System.out.println("PORTAL_ID = " + PORTAL_ID);
        System.out.println("STOK_ID = " + STOK_ID);

        System.out.println("Wall ID:");
        System.out.println();
        System.out.println("GLOBAL_WALL_ID = " + GLOBAL_WALL_ID);
        System.out.println("EMPTY_WALL_ID = " + EMPTY_WALL_ID);
        System.out.println("EXIT_WALL_ID = " + EXIT_WALL_ID);
        System.out.println("INSIDE_WALL_ID = " + INSIDE_WALL_ID);
        System.out.println("ISTOK_WALL_ID = " + ISTOK_WALL_ID);

        System.out.println();

        a = new String("Cell ID:"
        + "\n"
        + "\n" + "EMPTY_ID = " + EMPTY_ID
        + "\n" + "TREASURE_ID = " + TREASURE_ID
        + "\n" + "ARSENAL_ID = " + ARSENAL_ID
        + "\n" + "HOSPITAL_ID = " + HOSPITAL_ID
        + "\n" + "TOP_RIVER_ID = " + TOP_RIVER_ID
        + "\n" + "RIGHT_RIVER_ID = " + RIGHT_RIVER_ID
        + "\n" + "BOTTOM_RIVER_ID = " + BOTTOM_RIVER_ID
        + "\n" + "LEFT_RIVER_ID = " + LEFT_RIVER_ID
        + "\n" + "PORTAL_ID = " + PORTAL_ID
        + "\n" + "STOK_ID = " + STOK_ID

        + "\n" + "Wall ID:"
        + "\n"
        + "\n" + "GLOBAL_WALL_ID = " + GLOBAL_WALL_ID
        + "\n" + "EMPTY_WALL_ID = " + EMPTY_WALL_ID
        + "\n" + "EXIT_WALL_ID = " + EXIT_WALL_ID
        + "\n" + "INSIDE_WALL_ID = " + INSIDE_WALL_ID
        + "\n" + "ISTOK_WALL_ID = " + ISTOK_WALL_ID + "\n");

        return a;
    }
}

