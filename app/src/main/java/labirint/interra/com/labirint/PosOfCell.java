package labirint.interra.com.labirint;

public class PosOfCell
{
    public int gor = -1;
    public int ver = -1;

    public PosOfCell(int a, int b)
    {
        gor = a;
        ver = b;
    }

    public boolean equals(PosOfCell a)
    {
        if((gor == a.gor) && (ver == a.ver)) return true;
        else return false;
    }

    public void inf ()
    {
        System.out.println("Gor = " + gor + " | Ver = " + ver);
    }
}
