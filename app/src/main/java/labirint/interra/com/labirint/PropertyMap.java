package labirint.interra.com.labirint;

/**
 * Created by 1 on 19.02.2019.
 */

public class PropertyMap
{
    public final String[] DEF_GOR_NAMES = {
            " A", " B", " C", " D", " E", " F", " G", " H", " I", " J", " K", " L", " M", " N", " O", " P", " Q", " R", " S", " T", " U", " V", " W", " X", " Y", " Z",
            "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ",
            "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ",
            "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ"
    };
    public final String[] DEF_VER_NAMES = {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
            "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
            "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78",
            "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102", "103", "104",
            };
    public final int MAX_SIZE = 104;

    public int height;	// столбцы
    public int width;	// строки
    public String[] gorNames;
    public String[] verNames;

    public int countPlayers;
    public String[] namesOf0Cells;
    public String[] names;

    public boolean flagRiver = true;

    public int countPortal;
    public boolean flagPortal;

    //внутренние стенки
    //0 -- мало
    //1 -- средне
    //2 -- много
    public boolean inWallFlag;
    public int idCountInWall;

    public static PropertyMap PM;
    public static PropertyMap proMap (int h, int w, int p, boolean fr, int cp, boolean fp, boolean ifw, int iw)
    {
        if (PM == null) PM = new PropertyMap(h, w, p, fr, cp, fp, ifw, iw);
        return PM;
    }

    public static PropertyMap proMap ()
    {
        return PM;
    }

    private PropertyMap(int h, int w, int p, boolean fr, int cp, boolean fp, boolean ifw, int iw)
    {
        setHeight(h);
        setWidth(w);
        setCountPlayers(p);
        setFlagRiver(fr);
        setCountPortal(cp);
        setFlagPortal(fp);
        setInWallFlag(ifw);
        if (iw > 2) iw = 2;
        setIdCountInWall(iw);
    }

    public static void del()
    {
        PM = null;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        if (height <= MAX_SIZE)
        {
            this.height = height;
            verNames = new String[height];
            for (int i = 0; i < height; i++)
            {
                verNames[i] = new String (DEF_VER_NAMES[i]);
            }
        }
    }

    public int getWidth()
    {
        return width;

    }

    public void setWidth(int width)
    {
        this.width = width;
        gorNames = new String[width];
        for (int i = 0; i < width; i++)
        {
            gorNames[i] = new String (DEF_GOR_NAMES[i]);
        }
    }

    public int getCountPlayers()
    {
        return countPlayers;
    }

    public void setCountPlayers(int countPlayers)
    {
        this.countPlayers = countPlayers;
        namesOf0Cells = new String[countPlayers];
        names = new String[countPlayers];
    }

    public void setPos(int i, String pos)
    {
        namesOf0Cells[i] = pos;
    }

    public void setName(int i, String n)
    {
        names[i] = n;
    }

    public String getName(int i)
    {
        return names[i];
    }

    public boolean isFlagRiver()
    {
        return flagRiver;
    }

    public void setFlagRiver(boolean flagRiver)
    {
        this.flagRiver = flagRiver;
    }

    public int getCountPortal()
    {
        return countPortal;
    }

    public void setCountPortal(int countPortal)
    {
        this.countPortal = countPortal;
    }

    public String getGorName (int i)
    {
        return gorNames[i];
    }

    public String getVerName (int i)
    {
        return verNames[i];
    }

    public String getName0 (int i)
    {
        return namesOf0Cells[i];
    }

    public boolean isFlagPortal()
    {
        return flagPortal;
    }

    public void setFlagPortal(boolean flagPortal)
    {
        this.flagPortal = flagPortal;
    }

    public boolean isInWallFlag()
    {
        return inWallFlag;
    }

    public void setInWallFlag(boolean inWallFlag)
    {
        this.inWallFlag = inWallFlag;
    }

    public int getIdCountInWall()
    {
        return idCountInWall;
    }

    public void setIdCountInWall(int idCountInWall)
    {
        this.idCountInWall = idCountInWall;
    }
}
