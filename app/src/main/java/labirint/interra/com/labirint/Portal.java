package labirint.interra.com.labirint;

public class Portal
{
    private int idP;

    private PosOfCell pos;

    Portal (int a, PosOfCell p)
    {
        idP = a;
        pos = p;
    }

    public String inf ()
    {
        System.out.println (idP + ":  " + pos.gor + "  " + pos.ver);
        return new String(idP + ":  " + pos.gor + "  " + pos.ver + "\n");
    }

    public PosOfCell getPos() { return pos; }
    public int getIdP() { return idP; }

}
