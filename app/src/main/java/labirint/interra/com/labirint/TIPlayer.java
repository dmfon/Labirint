package labirint.interra.com.labirint;

public class TIPlayer
{
    public String name;
    private Cell pos;

    private boolean treasure;

    private boolean flagWound;

    private int patrons;
    private boolean knife;
    private int dinamite;

    private int countMoves;

    public TIPlayer (Cell pos0, String n)
    {
        name = n;
        pos = pos0;
        treasure = false;
        flagWound = false;
        patrons = 0;
        knife = false;
        dinamite = 0;

        countMoves = 0;
    }

    public int getPatrons() { return patrons;}
    public int getDinamite() { return dinamite;}
    public boolean isKnife() { return knife; }

    public void move()
    {
        countMoves++;
    }

    public int getCountMoves() { return countMoves; }

    public void topMove() { pos = pos.getTopCell();}
    public void rightMove() { pos = pos.getRightCell();}
    public void bottomMove() { pos = pos.getBottomCell();}
    public void leftMove() { pos = pos.getLeftCell();}

    public void setPos (Cell p)
    {
        pos = p;
    }
    public Cell getPos ()
    {
        return pos;
    }

    public void takeTreasure()
    {
        treasure = true;
    }
    public void lostTreasure()
    {
        treasure = false;
    }
    public boolean isTreasure() { return treasure; }

    public void wound()
    {
        flagWound = true;
        treasure = false;
        patrons = 0;
        knife = false;
        dinamite = 0;
    }
    public void visitHospital()
    {
        flagWound = false;
    }

    public boolean isWound()
    {
        return flagWound;
    }

    public void visitArsenal()
    {
        if(!flagWound)
        {
            patrons = 3;
            knife = true;
            dinamite = 3;
        }
    }

    public boolean shot()
    {
        if (patrons > 0)
        {
            patrons--;
            return true;
        }
        return false;
    }
    public boolean punch()
    {
        if (knife) return true;
        return false;
    }
    public boolean explosion()
    {
        if (dinamite > 0)
        {
            dinamite--;
            return true;
        }
        return false;
    }
}

