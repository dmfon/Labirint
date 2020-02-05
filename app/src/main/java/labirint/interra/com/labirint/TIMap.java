package labirint.interra.com.labirint;

import java.util.Random;
import static labirint.interra.com.labirint.ListId.*;

class blackmas
{
    int[] mgor;
    int[] mver;

    blackmas (int a)
    {
        mgor = new int[a];
        mver = new int[a];

        for (int i = 0; i < a; i++)
        {
            mgor[i] = -1;
            mver[i] = -1;
        }
    }

    public void add (int a, int b)
    {
        if (!this.search(a,b))
        {
            for (int i = 0; i < mgor.length; i++)
            {
                if (mgor[i] == -1)
                {
                    mgor[i] = a;
                    mver[i] = b;
                    return;
                }
            }
        }
    }

    public boolean search (int a, int b)
    {
        for (int i = 0; i < mgor.length; i++)
        {
            if (mgor[i] == -1) return false;
            if ((mgor[i] == a) && (mver[i] == b)) return true;
        }
        return false;
    }

    public void del (int a, int b)
    {
        for (int i = 0; i < mgor.length; i++)
        {
            if ((mgor[i] == a)&&(mver[i] == b))
            {
                mgor[i] = -1;
                mver[i] = -1;
            }
        }
    }

    public void inf ()
    {
        for (int i = 0; i < mgor.length; i++)
        {
            if (mgor[i] == -1) return;
            System.out.println (mgor[i] + "  " + mver[i]);
        }
    }
}


class TIMap extends ListId
{
    public final int MAX_WAIT_NUMBER = 30;

    public Cell[][] cells;
    Line[] gLines;
    Line[] vLines;

    private PropertyMap PM;

    public int height;
    public int width;

    public int playersCount;
    public Cell[] pos0;

    public int portalCount;

    int gorLineCount;
    int verLineCount;

    public Portal[] portals;
    public PosOfCell[] portalPos;

    int inWallCount = 0;

    int treasureHeight, treasureWidth;
    int arsenalHeight, arsenalWidth;
    int hospitalHeight, hospitalWidth;

    Cell arsenalCell;

    int p0 = -1;

    TIMap(PropertyMap mp)
    {
        PM = mp;
        height = PM.getHeight(); // кол-во строк
        width = PM.getWidth();	// кол-во столбцов

        playersCount = PM.getCountPlayers();
        pos0 = new Cell[playersCount];

        portalCount = PM.getCountPortal();

        gorLineCount = width*(height + 1);
        verLineCount = height*(width + 1);

        cells = new Cell[height][width];
        gLines = new Line[gorLineCount];
        vLines = new Line[verLineCount];

        for (int i = 0; i < gorLineCount; i++)
        {
            if ((i < width)||(i > gorLineCount - width - 1)) gLines[i] = new Line(GLOBAL_WALL_ID);
            else gLines[i] = new Line(EMPTY_WALL_ID);
        }

        for (int i = 0; i < verLineCount; i++)
        {
            if ((i < height)||(i > verLineCount - height - 1)) vLines[i] = new Line(GLOBAL_WALL_ID);
            else vLines[i] = new Line(EMPTY_WALL_ID);
        }

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                cells[i][j] = new Cell(gLines[i*width + j], gLines[i*width + j + width], vLines[j*height + i], vLines[j*height + i + height],
                        PM.getGorName(j), PM.getVerName(i), i, j);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
            {
                if (i == 0) cells[i][j].setVerNeighbours(null, cells[i + 1][j]);
                else if (i == height - 1) cells[i][j].setVerNeighbours(cells[i - 1][j], null);
                else cells[i][j].setVerNeighbours(cells[i - 1][j], cells[i + 1][j]);

                if (j == 0) cells[i][j].setGorNeighbours(null, cells[i][j + 1]);
                else if (j == width - 1) cells[i][j].setGorNeighbours(cells[i][j - 1], null);
                else cells[i][j].setGorNeighbours(cells[i][j - 1], cells[i][j + 1]);
            }

        for (int k = 0; k < playersCount; k++)
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                {
                    String name = new String(cells[i][j].getGorName() + cells[i][j].getVerName());
                    if (PM.getName0(k).equals(name))
                    {
                        pos0[k] = cells[i][j];
                    }
                }

        this.gen();
        if (PM.isInWallFlag()) genInWall();
        //this.inf();

    }

    public String inf()
    {
        String res;
        String a = ListId.idInf();

        StringBuilder b = new StringBuilder();
        b.append("\n");

        for (int i = 0; i < height; i++)
        {
            for (int j = i*width; j < i*width + width; j++)
            {
                System.out.print("  " + gLines[j].getValue() + " ");
                b.append("  " + gLines[j].getValue() + "  ");
            }
            System.out.println();
            b.append("\n");

            for (int j = 0, k = i; j < width; j++, k += height)
            {
                System.out.print (vLines[k].getValue() + " ");
                b.append(vLines[k].getValue() + " ");
                System.out.print (cells[i][j].getValue() + " ");
                b.append(cells[i][j].getValue() + " ");
                if (j == width - 1)
                {
                    System.out.print (vLines[k + height].getValue());
                    b.append(vLines[k + height].getValue());
                }
            }
            System.out.println();
            b.append("\n");

            if (i == height - 1)
            {
                for (int j = height*width; j < height*width + width; j++)
                {
                    System.out.print("  " + gLines[j].getValue() + " ");
                    b.append("  " + gLines[j].getValue() + "  ");
                }
                System.out.println();
                b.append("\n");
            }
        }

        //информация о порталах
        if (PM.isFlagPortal())
        {
            System.out.println();
            b.append("\n");
            System.out.println("Portals:");
            b.append("Portals:\n");
            if (PM.isFlagPortal())
            {
                System.out.println();
                b.append("\n");
                for (int i = 0; i < portals.length; i++)
                {
                    String c = portals[i].inf();
                    b.append(c);
                }
            }
            System.out.println();
            b.append("\n");
        }

        //количество внутренних стен
        if (PM.isInWallFlag())
        {
            System.out.println("count inside wall:    " + inWallCount);
            b.append("count inside wall:    " + inWallCount);
        }
        System.out.println();
        b.append("\n");

		/*System.out.println(n1 + " " + m1);
		System.out.println(n2 + " " + m2);
		System.out.println(n3 + " " + m3);
		System.out.println(p0);*/
        //System.out.println();

        // проверка правильности выдачи имен
		/*for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				System.out.print(cells[i][j].getGorName() + cells[i][j].getVerName() + " ");
			}
			System.out.println();
		}*/

        // проверка правильности выдачи позиций
		/*for (int i = 0; i < playersCount; i++)
		{
			System.out.println(pos0[i].gor + "  " + pos0[i].ver);
		}*/

        // проверка правильности определения соседей
		/*cells[0][0].getRightCell().getPos().inf();
		cells[2][3].getTopCell().getPos().inf();
		if (cells[4][2].getBottomCell() == null) System.out.println("AYAYAYA");*/

        // проверка работы нахождения существования пути
        //System.out.println(checkAccess(arsenalCell, ARSENAL_ID));
        return new String(a + b);
    }

    public void gen()
    {
        //genexit
        Random r = new Random ();
        int ver = r.nextInt (4);

        switch (ver)
        {
            case 0:
                ver = r.nextInt (width);
                gLines[ver].setValue(EXIT_WALL_ID);
                break;
            case 1:
                ver = r.nextInt (width);
                gLines[gorLineCount - 1 - ver].setValue(EXIT_WALL_ID);
                break;

            case 2:
                ver = r.nextInt (height);
                vLines[ver].setValue(EXIT_WALL_ID);
                break;
            case 3:
                ver = r.nextInt (height);
                vLines[verLineCount - 1 - ver].setValue(EXIT_WALL_ID);
                break;
        }

        //gentreasure
        int gor = r.nextInt (height);
        ver = r.nextInt (width);
        cells[gor][ver].setValue(TREASURE_ID);
        treasureHeight = gor;
        treasureWidth = ver;

        //genarsinal
        while (true)
        {
            gor = r.nextInt (height);
            ver = r.nextInt (width);
            if (cells[gor][ver].getValue() == EMPTY_ID)
            {
                cells[gor][ver].setValue(ARSENAL_ID);
                arsenalCell = cells[gor][ver];
                break;
            }
        }
        arsenalHeight = gor;
        arsenalWidth = ver;

        //gengospital
        while (true)
        {
            gor = r.nextInt (height);
            ver = r.nextInt (width);
            if (cells[gor][ver].getValue() == EMPTY_ID)
            {
                cells[gor][ver].setValue(HOSPITAL_ID);
                break;
            }
        }
        hospitalHeight = gor;
        hospitalWidth = ver;

        //genportal
        if (PM.isFlagPortal())
        {
            int maxp = 0;
            if (width > height) maxp = width - 1;
            else maxp = height - 1;
            int minp = (int) maxp/2 + 1;
            //int portalCount = r.nextInt (maxp - minp + 1) + minp;

            portals = new Portal[portalCount];
            portalPos = new PosOfCell[portalCount];
            for (int i = 0; i < portalCount; i++)
            {
                while (true)
                {
                    gor = r.nextInt (height);
                    ver = r.nextInt (width);
                    if (cells[gor][ver].getValue() == EMPTY_ID)
                    {
                        cells[gor][ver].setValue(PORTAL_ID);
                        portalPos[i] = new PosOfCell(gor, ver);
                        portals[i] = new Portal (i, portalPos[i]);

                        break;
                    }
                }
            }
        }

        //genriver
        if (PM.isFlagRiver())
        {
            boolean fr;
            do
            {
                boolean fline = false;
                Line isline = vLines[0];

                while (true)
                {
                    boolean exit = true;
                    ver = r.nextInt (4);
                    int verm = r.nextInt (width);
                    int vern = r.nextInt (height);
                    if (((ver < 2)&&(gLines[verm].getValue() != EXIT_WALL_ID))||(ver > 1)&&(vLines[vern].getValue() != EXIT_WALL_ID))
                    {
                        exit = false;
                        switch (ver)
                        {
                            case 0:
                                if (((verm == 0)&&(vLines[0].getValue() != GLOBAL_WALL_ID)) ||
                                        ((verm == width - 1)&&(vLines[verLineCount - height].getValue() != GLOBAL_WALL_ID)))
                                    break;
                                else
                                {
                                    gLines[verm].setValue(ISTOK_WALL_ID);
                                    isline = gLines[verm];
                                    fline = true;
                                    break;
                                }
                            case 1:
                                if (((verm == 0)&&(vLines[verLineCount - 1].getValue() != GLOBAL_WALL_ID)) ||
                                        ((verm == width - 1)&&(vLines[height - 1].getValue() != GLOBAL_WALL_ID)))
                                    break;
                                else
                                {
                                    gLines[gorLineCount - 1 - verm].setValue(ISTOK_WALL_ID);
                                    isline = gLines[gorLineCount - 1 - verm];
                                    fline = true;
                                    break;
                                }
                            case 2:
                                if (((vern == 0)&&(gLines[0].getValue() != GLOBAL_WALL_ID)) ||
                                        ((vern == height - 1)&&(gLines[gorLineCount - width].getValue() != GLOBAL_WALL_ID)))
                                    break;
                                else
                                {
                                    vLines[vern].setValue(ISTOK_WALL_ID);
                                    isline = vLines[vern];
                                    fline = false;
                                    break;
                                }
                            case 3:
                                if (((vern == 0)&&(gLines[gorLineCount - 1].getValue() != GLOBAL_WALL_ID)) ||
                                        ((vern == height - 1)&&(gLines[width - 1].getValue() != GLOBAL_WALL_ID)))
                                    break;
                                else
                                {
                                    vLines[verLineCount - 1 - vern].setValue(ISTOK_WALL_ID);
                                    isline = vLines[verLineCount - 1 - vern];
                                    fline = false;
                                    break;
                                }
                        }
                    }

                    if (!exit) break;
                }

                while (true)
                {
                    gor = r.nextInt (height);
                    ver = r.nextInt (width);
                    if ((cells[gor][ver].getValue() == EMPTY_ID) && (cells[gor][ver].forin() != 0))
                    {
                        cells[gor][ver].setValue(STOK_ID);
                        break;
                    }
                }

                fr = makeRiver(isline, fline, gor, ver);

            }
            while (fr);
        }
    }

    public boolean makeRiver(Line isline, boolean fline, int fgor, int fver)
    {
        int way = -1;
        int sgor = -1;
        int sver = -1;

        go1:
        {
            if (fline)
            {
                for (int i = 0; i < width; i++)
                {
                    if (cells[0][i].search(isline) > 0)
                    {
                        way = cells[0][i].search(isline);
                        sgor = 0;
                        sver = i;
                        break go1;
                    }
                    if (cells[height - 1][i].search(isline) > 0)
                    {
                        way = cells[height - 1][i].search(isline);
                        sgor = height - 1;
                        sver = i;
                        break go1;
                    }
                }
            }
            else
            {
                for (int i = 0; i < height; i++)
                {
                    if (cells[i][0].search(isline) > 0)
                    {
                        way = cells[i][0].search(isline);
                        sgor = i;
                        sver = 0;
                        break go1;
                    }
                    if (cells[i][width - 1].search(isline) > 0)
                    {
                        way = cells[i][width - 1].search(isline);
                        sgor = i;
                        sver = width - 1;
                        break go1;
                    }
                }
            }
        }

        if (cells[sgor][sver].getValue() != EMPTY_ID)
        {
            cells[fgor][fver].setValue(EMPTY_ID);
            helpDeleteForRiverMake(fline);
            return true;
        }
        cells[sgor][sver].setValue(way);
        int curgor = sgor;
        int curver = sver;
        switch (way)
        {
            case TOP_RIVER_ID:
                curgor--;
                break;
            case RIGHT_RIVER_ID:
                curver++;
                break;
            case BOTTOM_RIVER_ID:
                curgor++;
                break;
            case LEFT_RIVER_ID:
                curver--;
                break;
        }
        Cell curcell = cells[curgor][curver];

        if (curcell.getValue() != EMPTY_ID)
        {
            cells[fgor][fver].setValue(EMPTY_ID);
            helpDeleteForRiverMake(fline);
            return true;
        }

        blackmas black = new blackmas (height*width);
        black.add (sgor, sver);

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if ((cells[i][j].getValue() != EMPTY_ID)&&(cells[i][j].getValue() != STOK_ID))
                    black.add(i, j);
            }
        }

        while (curcell != cells[fgor][fver])
        {
            //this.inf();

            boolean bw = false;
            boolean lw = false;
            boolean tw = false;
            boolean rw = false;

            if (curgor + 1 != height)
                if (!black.search(curgor + 1, curver)) bw = true;

            if (curver - 1 >= 0)
                if (!black.search(curgor, curver - 1)) lw = true;

            if (curgor - 1 >= 0)
                if (!black.search(curgor - 1, curver)) tw = true;

            if (curver + 1 != width)
                if (!black.search(curgor, curver + 1)) rw = true;


            if ((!bw)&&(!lw)&&(!tw)&&(!rw))
            {
                //test++;
                if ((curgor == sgor)&&(curver == sver))
                {
                    cells[fgor][fver].setValue(EMPTY_ID);
                    helpDeleteForRiverMake(fline);
                    return true;
                }
                else
                {
                    black.add(curgor, curver);
                    switch (way)
                    {
                        case 4:
                            curgor++;
                            break;
                        case 5:
                            curver--;
                            break;
                        case 6:
                            curgor--;
                            break;
                        case 7:
                            curver++;
                            break;
                    }

                    curcell = cells[curgor][curver];
                    curcell.setValue(EMPTY_ID);

                    if ((curgor + 1 != height) && (cells[curgor + 1][curver].getValue() == TOP_RIVER_ID)) way = TOP_RIVER_ID;
                    if ((curver - 1 >= 0) && (cells[curgor][curver - 1].getValue() == RIGHT_RIVER_ID)) way = RIGHT_RIVER_ID;
                    if ((curgor - 1 >= 0) && (cells[curgor - 1][curver].getValue() == BOTTOM_RIVER_ID)) way = BOTTOM_RIVER_ID;
                    if ((curver + 1 != height) && (cells[curgor][curver + 1].getValue() == LEFT_RIVER_ID)) way = LEFT_RIVER_ID;
                }
            }
            else
            {
                int nw = 0;

                if (bw) nw++;
                if (lw) nw++;
                if (tw) nw++;
                if (rw) nw++;

                if (nw == 1)
                {
                    black.add (curgor, curver);

                    if (bw)
                    {
                        way = BOTTOM_RIVER_ID;
                        curcell.setValue(way);
                        curgor++;
                    }
                    if (lw)
                    {
                        way = LEFT_RIVER_ID;
                        curcell.setValue(way);
                        curver--;
                    }
                    if (tw)
                    {
                        way = TOP_RIVER_ID;
                        curcell.setValue(way);
                        curgor--;
                    }
                    if (rw)
                    {
                        way = RIGHT_RIVER_ID;
                        curcell.setValue(way);
                        curver++;
                    }
                    curcell = cells[curgor][curver];
                }
                else
                {
                    int bch = 0;
                    int lch = 0;
                    int tch = 0;
                    int rch = 0;

                    if (bw) bch++;
                    if (lw) lch++;
                    if (tw) tch++;
                    if (rw) rch++;

                    if ((bw)&&(fgor > curgor)) bch+=2;
                    if ((lw)&&(fver < curver)) lch+=2;
                    if ((tw)&&(fgor < curgor)) tch+=2;
                    if ((rw)&&(fver > curver)) rch+=2;

                    int och = bch + lch + tch + rch;

                    Random ran = new Random ();
                    int chance = ran.nextInt (och);

                    black.add (curgor, curver);

                    if (chance < bch)
                    {
                        way = BOTTOM_RIVER_ID;
                        curgor++;
                        curcell.setValue(way);
                        if (cells[curgor][curver].getValue() != EMPTY_ID) p0 = chance;
                        curcell = cells[curgor][curver];
                    }
                    else
                    {
                        if (chance < bch + lch)
                        {
                            way = LEFT_RIVER_ID;
                            curver--;
                            curcell.setValue(way);
                            if (cells[curgor][curver].getValue() != EMPTY_ID) p0 = chance;
                            curcell = cells[curgor][curver];
                        }
                        else
                        {
                            if (chance < bch + lch + tch)
                            {
                                way = TOP_RIVER_ID;
                                curgor--;
                                curcell.setValue(way);
                                if (cells[curgor][curver].getValue() != EMPTY_ID) p0 = chance;
                                curcell = cells[curgor][curver];
                            }
                            else
                            {
                                if (chance < bch + lch + tch + rch)
                                {
                                    way = RIGHT_RIVER_ID;
                                    curver++;
                                    curcell.setValue(way);
                                    if (cells[curgor][curver].getValue() != EMPTY_ID) p0 = chance;
                                    curcell = cells[curgor][curver];
                                }
                            }
                        }
                    }
                }
            }

        }
        //black.inf();
        return false;

    }

    public void helpDeleteForRiverMake (boolean fline)
    {
        if (fline)
        {
            for (int u = 0; u < gLines.length; u++)
            {
                if (gLines[u].getValue() == ISTOK_WALL_ID)
                {
                    gLines[u].setValue(GLOBAL_WALL_ID);
                    return;
                }
            }
        }
        else
        {
            for (int u = 0; u < vLines.length; u++)
            {
                if (vLines[u].getValue() == ISTOK_WALL_ID)
                {
                    vLines[u].setValue(GLOBAL_WALL_ID);
                    return;
                }
            }
        }
    }

    // метод проверяет можно ли из клетки a попасть в клетку с индексом idTarget
    public boolean checkAccess(Cell a, int idTarget)
    {
        if (a.getValue() == idTarget) return true;
        Cell[] findCells = new Cell[height*width];
        Cell[] blackCells = new Cell[height*width];
        for (int i = 0; i < height*width; i++)
        {
            findCells[i] = null;
            blackCells[i] = null;
        }

        int ind = -1;
        do
        {
            //a.getPos().inf();
            for (int i = 0; i < height*width; i++)
                if (findCells[i] == null)
                {
                    ind = i;
                    break;
                }
            if (ind == -1) return false;
            if (a.getLeftL().getValue() == EMPTY_WALL_ID)
            {
                Cell fCell = a.getLeftCell();
                if (fCell.getValue() == idTarget) return true;
                boolean blackFlag = false;
                for (int i = 0; i < height*width; i++)
                {
                    if (blackCells[i] == null) break;
                    if (blackCells[i] == fCell)
                    {
                        blackFlag = true;
                        break;
                    }
                }
                if (!blackFlag)
                {
                    for (int i = 0; i < height*width; i++)
                    {
                        if (findCells[i] == null) break;
                        if (findCells[i] == fCell)
                        {
                            blackFlag = true;
                            break;
                        }
                    }
                    if (!blackFlag)
                    {
                        findCells[ind] = fCell;
                        ind++;
                    }
                }
            }
            if (a.getTopL().getValue() == EMPTY_WALL_ID)
            {
                Cell fCell = a.getTopCell();
                if (fCell.getValue() == idTarget) return true;
                boolean blackFlag = false;
                for (int i = 0; i < height*width; i++)
                {
                    if (blackCells[i] == null) break;
                    if (blackCells[i] == fCell)
                    {
                        blackFlag = true;
                        break;
                    }
                }
                if (!blackFlag)
                {
                    for (int i = 0; i < height*width; i++)
                    {
                        if (findCells[i] == null) break;
                        if (findCells[i] == fCell)
                        {
                            blackFlag = true;
                            break;
                        }
                    }
                    if (!blackFlag)
                    {
                        findCells[ind] = fCell;
                        ind++;
                    }
                }
            }
            if (a.getRightL().getValue() == EMPTY_WALL_ID)
            {
                Cell fCell = a.getRightCell();
                if (fCell.getValue() == idTarget) return true;
                boolean blackFlag = false;
                for (int i = 0; i < height*width; i++)
                {
                    if (blackCells[i] == null) break;
                    if (blackCells[i] == fCell)
                    {
                        blackFlag = true;
                        break;
                    }
                }
                if (!blackFlag)
                {
                    for (int i = 0; i < height*width; i++)
                    {
                        if (findCells[i] == null) break;
                        if (findCells[i] == fCell)
                        {
                            blackFlag = true;
                            break;
                        }
                    }
                    if (!blackFlag)
                    {
                        findCells[ind] = fCell;
                        ind++;
                    }
                }
            }
            if (a.getBottomL().getValue() == EMPTY_WALL_ID)
            {
                Cell fCell = a.getBottomCell();
                if (fCell.getValue() == idTarget) return true;
                boolean blackFlag = false;
                for (int i = 0; i < height*width; i++)
                {
                    if (blackCells[i] == null) break;
                    if (blackCells[i] == fCell)
                    {
                        blackFlag = true;
                        break;
                    }
                }
                if (!blackFlag)
                {
                    for (int i = 0; i < height*width; i++)
                    {
                        if (findCells[i] == null) break;
                        if (findCells[i] == fCell)
                        {
                            blackFlag = true;
                            break;
                        }
                    }
                    if (!blackFlag)
                    {
                        findCells[ind] = fCell;
                        ind++;
                    }
                }
            }

            for (int i = 0; i < height*width; i++)
                if(blackCells[i] == null)
                {
                    blackCells[i] = a;
                    break;
                }
            for (int i = 0; i < height*width; i++)
                if ((findCells[i] == a) && (i != height*width - 1))
                {
                    findCells[i] = findCells[i + 1];
                    a = findCells[i + 1];
                }
            a = findCells[0];
        } while (findCells[0] != null);
        return false;
    }

    // генерация внутренних стенок
    public void genInWall()
    {
        int countW = (height - 1)*width + (width - 1)*height;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if
                        (
                        (cells[i][j].getValue() == TOP_RIVER_ID)
                                ||(cells[i][j].getValue() == RIGHT_RIVER_ID)
                                ||(cells[i][j].getValue() == BOTTOM_RIVER_ID)
                                || (cells[i][j].getValue() == LEFT_RIVER_ID)
                                ||(cells[i][j].getValue() == STOK_ID)
                        ) countW--;
        countW = countW/2;
        if(countW <= 0) return;
        if(PM.getIdCountInWall() == 2) inWallCount = countW;
        if(PM.getIdCountInWall() == 1) inWallCount = countW/2;
        if(PM.getIdCountInWall() == 0) inWallCount = countW/3;
        countW = inWallCount;
        //System.out.println(inWallCount);

        boolean endFlag = false;
        int k = 0;

        while (!endFlag)
        {
            k++;
            //System.out.println("1a");
            Random r = new Random();
            int gorW = r.nextInt(countW + 1);
            //System.out.println(gorW);
            int verW = countW - gorW;

            for (int i = 0, j = 0; i < gorW; j++)
            {
                //System.out.println("1.1a");
                int ind1 = r.nextInt(height);
                int ind2 = r.nextInt(width);
                boolean flagTop = r.nextBoolean();
                if
                        (((flagTop) && !(cells[ind1][ind2].getValue() == TOP_RIVER_ID))
                        || ((!flagTop) && !(cells[ind1][ind2].getValue() == BOTTOM_RIVER_ID)))
                {
                    if ((cells[ind1][ind2].getTopL().getValue() == EMPTY_WALL_ID) && flagTop)
                    {
                        if (cells[ind1][ind2].getTopCell() != null)
                        {
                            if (!(cells[ind1][ind2].getTopCell().getValue() == BOTTOM_RIVER_ID))
                            {
                                cells[ind1][ind2].getTopL().setValue(INSIDE_WALL_ID);
                                i++;
                            }
                        }
                    }
                    //System.out.println("1.2a");
                    if ((cells[ind1][ind2].getBottomL().getValue() == EMPTY_WALL_ID) && !flagTop)
                    {
                        if (cells[ind1][ind2].getBottomCell()!= null)
                        {
                            if (!(cells[ind1][ind2].getBottomCell().getValue() == TOP_RIVER_ID))
                            {
                                cells[ind1][ind2].getBottomL().setValue(INSIDE_WALL_ID);
                                i++;
                            }
                        }
                    }
                    //System.out.println("1.3a");
                }
                if (j == MAX_WAIT_NUMBER)
                {
                    j = 0;
                    gorW--;
                    countW--;
                }
            }
            //System.out.println("2a");

            for (int i = 0, j = 0; i < verW; j++)
            {
                //System.out.println("2.1a");
                int ind1 = r.nextInt(height);
                int ind2 = r.nextInt(width);
                boolean flagLeft = r.nextBoolean();
                if
                        (((flagLeft) && !(cells[ind1][ind2].getValue() == LEFT_RIVER_ID))
                        || ((!flagLeft) && !(cells[ind1][ind2].getValue() == RIGHT_RIVER_ID)))
                {
                    if ((cells[ind1][ind2].getLeftL().getValue() == EMPTY_WALL_ID) && flagLeft)
                    {
                        if (cells[ind1][ind2].getLeftCell()!= null)
                        {
                            if (!(cells[ind1][ind2].getLeftCell().getValue() == RIGHT_RIVER_ID))
                            {
                                cells[ind1][ind2].getLeftL().setValue(INSIDE_WALL_ID);
                                i++;
                            }
                        }
                    }
                    if ((cells[ind1][ind2].getRightL().getValue() == EMPTY_WALL_ID) && !flagLeft)
                    {
                        if (cells[ind1][ind2].getRightCell()!= null)
                        {
                            if (!(cells[ind1][ind2].getRightCell().getValue() == LEFT_RIVER_ID))
                            {
                                cells[ind1][ind2].getRightL().setValue(INSIDE_WALL_ID);
                                i++;
                            }
                        }
                    }
                }
                //System.out.println("2.3a");
                if (j == MAX_WAIT_NUMBER)
                {
                    j = 0;
                    verW--;
                    countW--;
                }
            }
            //System.out.println("3a");

            boolean flagAP = checkAccess(arsenalCell, PORTAL_ID);
            boolean accessPortalFlags[] = new boolean[playersCount];
            if ((!PM.isFlagPortal()) || flagAP)
            {
                for (int i = 0; i < playersCount; i++)
                    accessPortalFlags[i] = false;
            }
            else
            {
                for (int i = 0; i < playersCount; i++)
                    accessPortalFlags[i] = checkAccess(pos0[i], PORTAL_ID);
            }

            endFlag = true;
            for (int i = 0; i < playersCount; i++)
            {
                if (!((checkAccess(pos0[i], ARSENAL_ID)) || (flagAP && accessPortalFlags[i])))
                {
                    endFlag = false;
                    break;
                }
            }

            if (!endFlag)
            {
                for (int i = 0; i < height; i++)
                    for (int j = 0; j < width; j++)
                    {
                        if (cells[i][j].getTopL().getValue() == INSIDE_WALL_ID)
                            cells[i][j].getTopL().setValue(EMPTY_WALL_ID);
                        if (cells[i][j].getRightL().getValue() == INSIDE_WALL_ID)
                            cells[i][j].getRightL().setValue(EMPTY_WALL_ID);
                        if (cells[i][j].getBottomL().getValue() == INSIDE_WALL_ID)
                            cells[i][j].getBottomL().setValue(EMPTY_WALL_ID);
                        if (cells[i][j].getLeftL().getValue() == INSIDE_WALL_ID)
                            cells[i][j].getLeftL().setValue(EMPTY_WALL_ID);
                    }
            }

            if (k == MAX_WAIT_NUMBER)
            {
                if (countW < 1) endFlag = true;
                k = 0;
                countW--;
            }
            //System.out.println(k);
        }
        inWallCount = countW;
        //System.out.println(countW);
    }
}

