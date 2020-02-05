package labirint.interra.com.labirint;

public class Cell extends ListId
{
    private int val;

    private boolean flagTreasure;

    public Line topL;
    public Line bottomL;

    public Line leftL;
    public Line rightL;

    public String gorName;
    public String verName;

    public Cell topCell;
    public Cell bottomCell;

    public Cell leftCell;
    public Cell rightCell;

    public PosOfCell pos;

    public Cell (Line t, Line b, Line l, Line r, String gn, String vn, int g, int v)
    {
        val = 0;
        topL = t;
        bottomL = b;
        leftL = l;
        rightL = r;

        gorName = gn;
        verName = vn;

        pos = new PosOfCell(g, v);
        flagTreasure = false;
    }

    public void setFlagTreasure (boolean flag)
    {
        flagTreasure = flag;
    }
    public boolean isFlagTreasure () { return  flagTreasure; }

    public void setVerNeighbours(Cell t, Cell b)
    {
        topCell = t;
        bottomCell = b;
    }

    public void setGorNeighbours(Cell l, Cell r)
    {
        leftCell = l;
        rightCell = r;
    }

    public Cell()
    {
    }

    public void setValue (int id)
    {
        val = id;
    }

    public int getValue()
    {
        return val;
    }

    public int forin()  // возвращает число, по которому можно определить годную для истока или стока клетку.
    {
        if ((getTopL().getValue() == EXIT_WALL_ID)||(getBottomL().getValue() == EXIT_WALL_ID)||
                (getLeftL().getValue() == EXIT_WALL_ID)||(getRightL().getValue() == EXIT_WALL_ID)) return 9;
        if ((getLeftL().getValue() == GLOBAL_WALL_ID)&&(getTopL().getValue() == GLOBAL_WALL_ID)) return 1;
        if ((getTopL().getValue() == GLOBAL_WALL_ID)&&(getRightL().getValue() == GLOBAL_WALL_ID)) return 3;
        if ((getRightL().getValue() == GLOBAL_WALL_ID)&&(getBottomL().getValue() == GLOBAL_WALL_ID)) return 5;
        if ((getLeftL().getValue() == GLOBAL_WALL_ID)&&(getBottomL().getValue() == GLOBAL_WALL_ID)) return 7;
        if (getTopL().getValue() == GLOBAL_WALL_ID) return 2;
        if (getRightL().getValue() == GLOBAL_WALL_ID) return 4;
        if (getBottomL().getValue() == GLOBAL_WALL_ID) return 6;
        if (getLeftL().getValue() == GLOBAL_WALL_ID) return 8;
        return 0;
    }

    public int search (Line l)  // возвращает id для определения направления течения.
    {
        if (getTopL() == l) return BOTTOM_RIVER_ID;
        if (getRightL() == l) return LEFT_RIVER_ID;
        if (getBottomL() == l) return TOP_RIVER_ID;
        if (getLeftL() == l) return RIGHT_RIVER_ID;
        return 0;
    }

    public String getGorName()
    {
        return gorName;
    }

    public String getVerName()
    {
        return verName;
    }

    public Line getTopL()
    {
        return topL;
    }

    public Line getBottomL()
    {
        return bottomL;
    }

    public Line getLeftL()
    {
        return leftL;
    }

    public Line getRightL()
    {
        return rightL;
    }

    public Cell getTopCell()
    {
        return topCell;
    }

    public Cell getBottomCell()
    {
        return bottomCell;
    }

    public Cell getLeftCell()
    {
        return leftCell;
    }

    public Cell getRightCell()
    {
        return rightCell;
    }

    public PosOfCell getPos()
    {
        return pos;
    }

}

