package labirint.interra.com.labirint;

import java.util.ArrayList;
import java.util.List;

import static labirint.interra.com.labirint.ListId.*;

/**
 * Created by 1 on 19.02.2019.
 */

public class TIMaster
{

    private static TIMaster TIM;
    public PropertyMap PM;

    public TIPlayer players[];
    private TIPlayer curPlayer;
    public int indCurPlayer;
    public int sendsPlayers[];
    public boolean firstFlags[];
    public int eflag;

    private TIMap map;
    private List<String> history = new ArrayList<String>();

    public static TIMaster tima(PropertyMap pm)
    {
        if (TIM == null)
        {
            TIM = new TIMaster(pm);
            return TIM;
        }
        return TIM;
    }
    public static TIMaster tima()
    {
        if (TIM == null) System.out.println(" TIM == null");
        return TIM;
    }

    public static void del()
    {
        TIM = null;
    }

   private TIMaster(PropertyMap pm)
    {
        PM = pm;
        players = new TIPlayer[PM.getCountPlayers()];
        map = new TIMap(PM);

        for(int i = 0; i < players.length; i++)
            players[i] = new TIPlayer(map.pos0[i], PM.getName(i));

        indCurPlayer = 0;
        curPlayer = players[indCurPlayer];
        sendsPlayers = new int[players.length];
        for (int i = 0; i < players.length; i++)
            sendsPlayers[i] = -1;
        firstFlags = new boolean[players.length];
        for (int i = 0; i < players.length; i++)
            firstFlags[i] = true;
        history0();
        eflag = -1;
    }

    public void got()
    {
        firstFlags[indCurPlayer] = false;
    }
    public boolean curFlag()
    {
        return firstFlags[indCurPlayer];
    }

    public void endRaund()
    {
        if (indCurPlayer == players.length - 1) indCurPlayer = 0;
        else indCurPlayer++;
        curPlayer = players[indCurPlayer];

    }

    public int getCurSend()
    {
        return sendsPlayers[indCurPlayer];
    }
    public void setCurSend()
    {
        sendsPlayers[indCurPlayer] = -1;
    }

    public boolean findTreasure()
    {
        addMoveInHistory("  --> treasure -->  ");
        if (curPlayer.isWound()) return false;
        else
        {
            curPlayer.takeTreasure();
            /*for (int i = 0; i < map.height; i++)
                for (int j = 0; j < map.width; j++)
                    if (map.cells[i][j].getValue() == TREASURE_ID)
                    {
                        map.cells[i][j].setValue(EMPTY_ID);
                        break;
                    }*/
            curPlayer.getPos().setValue(EMPTY_ID);
            return true;
        }
    }

    public boolean findArsenal()
    {
        addMoveInHistory("  --> arsenal -->  ");
        if (players[indCurPlayer].isWound()) return false;
        else
        {
            players[indCurPlayer].visitArsenal();
            return true;
        }
    }

    public boolean findHospital()
    {
        addMoveInHistory("  --> hospital -->  ");
        if (players[indCurPlayer].isWound())
        {
            players[indCurPlayer].visitHospital();
            return true;
        }
        return false;
    }

    public int topMove()
    {
        if (curPlayer.getPos().getTopL().getValue() == EXIT_WALL_ID)
        {
            if (curPlayer.isTreasure()) return RES_WIN;
            else return RES_EXIT;
        }
        if (curPlayer.getPos().getTopL().getValue() == EMPTY_WALL_ID)
        {
            curPlayer.topMove();
            addMoveInHistory("  u  ");
            return setEvent();
        }
        else
        {
            if (curPlayer.getPos().getValue() == PORTAL_ID)
            {
                onPortal();
                return RES_WALL_PORTAL;
            }
            if (curPlayer.getPos().getValue() == TOP_RIVER_ID)
            {
                onTopRiver();
                return RES_WALL_RIVER_TOP;
            }
            if (curPlayer.getPos().getValue() == RIGHT_RIVER_ID)
            {
                onRightRiver();
                return RES_WALL_RIVER_RIGHT;
            }
            if (curPlayer.getPos().getValue() == BOTTOM_RIVER_ID)
            {
                onBottomRiver();
                return RES_WALL_RIVER_BOTTOM;
            }
            if (curPlayer.getPos().getValue() == LEFT_RIVER_ID)
            {
                onLeftRiver();
                return RES_WALL_RIVER_LEFT;
            }
            return RES_WALL;
        }
    }
    public int rightMove()
    {
        if (curPlayer.getPos().getRightL().getValue() == EXIT_WALL_ID)
        {
            if (curPlayer.isTreasure()) return RES_WIN;
            else return RES_EXIT;
        }
        if (curPlayer.getPos().getRightL().getValue() == EMPTY_WALL_ID)
        {
            curPlayer.rightMove();
            addMoveInHistory("  r  ");
            return setEvent();
        }
        else
        {
            if (curPlayer.getPos().getValue() == PORTAL_ID)
            {
                onPortal();
                return RES_WALL_PORTAL;
            }
            if (curPlayer.getPos().getValue() == TOP_RIVER_ID)
            {
                onTopRiver();
                return RES_WALL_RIVER_TOP;
            }
            if (curPlayer.getPos().getValue() == RIGHT_RIVER_ID)
            {
                onRightRiver();
                return RES_WALL_RIVER_RIGHT;
            }
            if (curPlayer.getPos().getValue() == BOTTOM_RIVER_ID)
            {
                onBottomRiver();
                return RES_WALL_RIVER_BOTTOM;
            }
            if (curPlayer.getPos().getValue() == LEFT_RIVER_ID)
            {
                onLeftRiver();
                return RES_WALL_RIVER_LEFT;
            }
            return RES_WALL;
        }
    }
    public int bottomMove()
    {
        if (curPlayer.getPos().getBottomL().getValue() == EXIT_WALL_ID)
        {
            if (curPlayer.isTreasure()) return RES_WIN;
            else return RES_EXIT;
        }
        if (curPlayer.getPos().getBottomL().getValue() == EMPTY_WALL_ID)
        {
            curPlayer.bottomMove();
            addMoveInHistory("  d  ");
            return setEvent();
        }
        else
        {
            if (curPlayer.getPos().getValue() == PORTAL_ID)
            {
                onPortal();
                return RES_WALL_PORTAL;
            }
            if (curPlayer.getPos().getValue() == TOP_RIVER_ID)
            {
                onTopRiver();
                return RES_WALL_RIVER_TOP;
            }
            if (curPlayer.getPos().getValue() == RIGHT_RIVER_ID)
            {
                onRightRiver();
                return RES_WALL_RIVER_RIGHT;
            }
            if (curPlayer.getPos().getValue() == BOTTOM_RIVER_ID)
            {
                onBottomRiver();
                return RES_WALL_RIVER_BOTTOM;
            }
            if (curPlayer.getPos().getValue() == LEFT_RIVER_ID)
            {
                onLeftRiver();
                return RES_WALL_RIVER_LEFT;
            }
            return RES_WALL;
        }
    }
    public int leftMove()
    {
        if (curPlayer.getPos().getLeftL().getValue() == EXIT_WALL_ID)
        {
            if (curPlayer.isTreasure()) return RES_WIN;
            else return RES_EXIT;
        }
        if (curPlayer.getPos().getLeftL().getValue() == EMPTY_WALL_ID)
        {
            curPlayer.leftMove();
            addMoveInHistory("  l  ");
            return setEvent();
        }
        else
        {
            if (curPlayer.getPos().getValue() == PORTAL_ID)
            {
                onPortal();
                return RES_WALL_PORTAL;
            }
            if (curPlayer.getPos().getValue() == TOP_RIVER_ID)
            {
                onTopRiver();
                return RES_WALL_RIVER_TOP;
            }
            if (curPlayer.getPos().getValue() == RIGHT_RIVER_ID)
            {
                onRightRiver();
                return RES_WALL_RIVER_RIGHT;
            }
            if (curPlayer.getPos().getValue() == BOTTOM_RIVER_ID)
            {
                onBottomRiver();
                return RES_WALL_RIVER_BOTTOM;
            }
            if (curPlayer.getPos().getValue() == LEFT_RIVER_ID)
            {
                onLeftRiver();
                return RES_WALL_RIVER_LEFT;
            }
            return RES_WALL;
        }
    }
    public int stopMove()
    {
        addMoveInHistory("  s  ");
        return setEvent();
    }

    public int setEvent()
    {
        int r;
        switch (curPlayer.getPos().getValue())
        {
            case EMPTY_ID:
            {
                r = RES_EMPTY;
                break;
            }
            case TREASURE_ID:
            {
                boolean flag = findTreasure();
                if (flag) r = RES_TREASURE_YES;
                else r = RES_TREASURE_NO;
                break;
            }
            case ARSENAL_ID:
            {
                boolean flag = findArsenal();
                if (flag) r = RES_ARSENAL_YES;
                else r = RES_ARSENAL_NO;
                break;
            }
            case HOSPITAL_ID:
            {
                boolean flag = findHospital();
                if (flag) r = RES_HOSPITAL_YES;
                else r = RES_HOSPITAL_NO;
                break;
            }
            case TOP_RIVER_ID:
            {
                onTopRiver();
                r = RES_NEW_TOP;
                break;
            }
            case RIGHT_RIVER_ID:
            {
                onRightRiver();
                r = RES_NEW_RIGHT;
                break;
            }
            case BOTTOM_RIVER_ID:
            {
                onBottomRiver();
                r = RES_NEW_BOTTOM;
                break;
            }
            case LEFT_RIVER_ID:
            {
                onLeftRiver();
                r = RES_NEW_LEFT;
                break;
            }
            case PORTAL_ID:
            {
                onPortal();
                r = RES_NEW_PORTAL;
                break;
            }
            case STOK_ID:
            {
                r = RES_STOK;
                break;
            }
            default: return -1;
        }
        int res = 0;
        if (curPlayer.getPos().isFlagTreasure())
        {
            if (!curPlayer.isWound())
            {
                curPlayer.getPos().setFlagTreasure(false);
                curPlayer.takeTreasure();
                res = RES_EXTRA2;
            }
            else res = RES_EXTRA1;
        }
        res = r + res;
        return res;
    }

    public boolean setWound(int inds[])
    {

        boolean trFlag = false;
        boolean exP = true;
        int extraInd = -1;
        for (int i = 0; i < inds.length; i++)
        {
            sendsPlayers[inds[i]] = SEND_WOUND;
            if (players[inds[i]].isTreasure())
            {
                trFlag = true;
                extraInd = inds[i];
                players[inds[i]].wound();
            }
            else players[inds[i]].wound();
        }

        if (trFlag)
        {
            for (int i = 0; i < players.length; i++)
                if ((!players[i].isWound()) && (players[i].getPos().getPos().equals(players[extraInd].getPos().getPos())))
                {

                    players[i].takeTreasure();
                    exP = false;
                }
            if (exP)
            {
                players[extraInd].getPos().setFlagTreasure(true);
            }
        }

        if(exP) return false;
        else return true;
    }

    public int[] fireTop()
    {
        addMoveInHistory("  --> shot -->  ");
        curPlayer.shot();
        Cell curCell = curPlayer.getPos();
        while (true)
        {
            if (curCell.getTopL().getValue() != EMPTY_WALL_ID) break;
            curCell = curCell.getTopCell();
            int count = 0;
            for (int i = 0; i < players.length; i++)
            {
                if (players[i].getPos().getPos().equals(curCell.getPos()))
                    if (i != indCurPlayer) count++;
            }
            if (count != 0)
            {
                int wp[] = new int[count];
                for (int i = 0, j = 0; i < players.length; i++)
                {
                    if (players[i].getPos().getPos().equals(curCell.getPos()))
                    {
                        if (i != indCurPlayer)
                        {
                            wp[j] = i;
                            j++;
                        }
                    }
                }
                int cv = curPlayer.getPos().getValue();
                if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
                    setEflag();
                return wp;
            }
        }
        int wp[] = new int[1];
        wp[0] = -1;
        int cv = curPlayer.getPos().getValue();
        if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
            setEflag();
        return wp;
    }
    public int[] fireRight()
    {
        addMoveInHistory("  --> shot -->  ");
        curPlayer.shot();
        Cell curCell = curPlayer.getPos();
        while (true)
        {
            if (curCell.getRightL().getValue() != EMPTY_WALL_ID) break;
            curCell = curCell.getRightCell();
            int count = 0;
            for (int i = 0; i < players.length; i++)
            {
                if (players[i].getPos().getPos().equals(curCell.getPos()))
                    if (i != indCurPlayer) count++;
            }
            if (count != 0)
            {
                int wp[] = new int[count];
                for (int i = 0, j = 0; i < players.length; i++)
                {
                    if (players[i].getPos().getPos().equals(curCell.getPos()))
                    {
                        if (i != indCurPlayer)
                        {
                            wp[j] = i;
                            j++;
                        }
                    }
                }
                int cv = curPlayer.getPos().getValue();
                if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
                    setEflag();
                return wp;
            }
        }
        int wp[] = new int[1];
        wp[0] = -1;
        int cv = curPlayer.getPos().getValue();
        if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
            setEflag();
        return wp;
    }
    public int[] fireBottom()
    {
        addMoveInHistory("  --> shot -->  ");
        curPlayer.shot();
        Cell curCell = curPlayer.getPos();
        while (true)
        {
            if (curCell.getBottomL().getValue() != EMPTY_WALL_ID) break;
            curCell = curCell.getBottomCell();
            int count = 0;
            for (int i = 0; i < players.length; i++)
            {
                if (players[i].getPos().getPos().equals(curCell.getPos()))
                    if (i != indCurPlayer) count++;
            }
            if (count != 0)
            {
                int wp[] = new int[count];
                for (int i = 0, j = 0; i < players.length; i++)
                {
                    if (players[i].getPos().getPos().equals(curCell.getPos()))
                    {
                        if (i != indCurPlayer)
                        {
                            wp[j] = i;
                            j++;
                        }
                    }
                }
                int cv = curPlayer.getPos().getValue();
                if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
                    setEflag();
                return wp;
            }
        }
        int wp[] = new int[1];
        wp[0] = -1;
        int cv = curPlayer.getPos().getValue();
        if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
            setEflag();
        return wp;
    }
    public int[] fireLeft()
    {
        addMoveInHistory("  --> shot -->  ");
        curPlayer.shot();
        Cell curCell = curPlayer.getPos();
        while (true)
        {
            if (curCell.getLeftL().getValue() != EMPTY_WALL_ID) break;
            curCell = curCell.getLeftCell();
            int count = 0;
            for (int i = 0; i < players.length; i++)
            {
                if (players[i].getPos().getPos().equals(curCell.getPos()))
                    if (i != indCurPlayer) count++;
            }
            if (count != 0)
            {
                int wp[] = new int[count];
                for (int i = 0, j = 0; i < players.length; i++)
                {
                    if (players[i].getPos().getPos().equals(curCell.getPos()))
                    {
                        if (i != indCurPlayer)
                        {
                            wp[j] = i;
                            j++;
                        }
                    }
                }
                int cv = curPlayer.getPos().getValue();
                if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
                    setEflag();
                return wp;
            }
        }
        int wp[] = new int[1];
        wp[0] = -1;
        int cv = curPlayer.getPos().getValue();
        if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
            setEflag();
        return wp;
    }

    public int[] knife()
    {
        addMoveInHistory("  --> punch -->  ");
        curPlayer.punch();
        int count = 0;
        int res[];
        for (int i = 0; i < players.length; i++)
        {
            if ((curPlayer.getPos().getPos().equals(players[i].getPos().getPos()) && (i != indCurPlayer))) count++;
        }
        if (count == 0)
        {
            res = new int[1];
            res[0] = -1;
        }
        else
        {
            res = new int[count];
            for (int i = 0, j = 0; i < players.length; i++)
            {
                if ((curPlayer.getPos().getPos().equals(players[i].getPos().getPos()) && (i != indCurPlayer)))
                {
                    res[j] = i;
                    j++;
                }
            }
        }
        int cv = curPlayer.getPos().getValue();
        if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
            setEflag();
        return res;
    }

    public int blowon(int id)
    {
        addMoveInHistory("  --> blow up -->  ");
        curPlayer.explosion();
        int idWall = -1;
        if (id == TOP_RIVER_ID) idWall = curPlayer.getPos().getTopL().getValue();
        if (id == RIGHT_RIVER_ID) idWall = curPlayer.getPos().getRightL().getValue();
        if (id == BOTTOM_RIVER_ID) idWall = curPlayer.getPos().getBottomL().getValue();
        if (id == LEFT_RIVER_ID) idWall = curPlayer.getPos().getLeftL().getValue();

        if (idWall == INSIDE_WALL_ID)
        {
            if (id == TOP_RIVER_ID) curPlayer.getPos().getTopL().setValue(EMPTY_WALL_ID);
            if (id == RIGHT_RIVER_ID) curPlayer.getPos().getRightL().setValue(EMPTY_WALL_ID);
            if (id == BOTTOM_RIVER_ID) curPlayer.getPos().getBottomL().setValue(EMPTY_WALL_ID);
            if (id == LEFT_RIVER_ID) curPlayer.getPos().getLeftL().setValue(EMPTY_WALL_ID);
            int cv = curPlayer.getPos().getValue();
            if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
                setEflag();
            return RES_BLOWON_YES;
        }
        if (idWall == EMPTY_WALL_ID)
        {
            int cv = curPlayer.getPos().getValue();
            if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
                setEflag();
            return RES_BLOWON_NO;
        }
        else
        {
            int cv = curPlayer.getPos().getValue();
            if (cv == ARSENAL_ID || cv == PORTAL_ID || cv == TOP_RIVER_ID || cv == RIGHT_RIVER_ID || cv == BOTTOM_RIVER_ID || cv == LEFT_RIVER_ID)
                setEflag();
            return RES_BLOWON_GLOBAL;
        }
    }

    public void setEflag()
    {
        int cv = curPlayer.getPos().getValue();
        if (cv == ARSENAL_ID)
        {
            findArsenal();
            eflag = RES_EXTRA_ARSENAL;
        }else
        if (cv == PORTAL_ID)
        {
            onPortal();
            eflag = RES_EXTRA_PORTAL;
        }else
        if (cv == TOP_RIVER_ID)
        {
            onTopRiver();
            eflag = RES_EXTRA_RIVER_TOP;
        }else
        if (cv == RIGHT_RIVER_ID)
        {
            onRightRiver();
            eflag = RES_EXTRA_RIVER_RIGHT;
        }else
        if (cv == BOTTOM_RIVER_ID)
        {
            onBottomRiver();
            eflag = RES_EXTRA_RIVER_BOTTOM;
        }else
        if (cv == LEFT_RIVER_ID)
        {
            onLeftRiver();
            eflag = RES_EXTRA_RIVER_LEFT;
        }else
        eflag = -2 - cv;
    }

    public void onTopRiver() { players[indCurPlayer].topMove(); addMoveInHistory("  --> river -->  ");}
    public void onLeftRiver() { players[indCurPlayer].leftMove(); addMoveInHistory("  --> river -->  ");}
    public void onBottomRiver() { players[indCurPlayer].bottomMove(); addMoveInHistory("  --> river -->  ");}
    public void onRightRiver() { players[indCurPlayer].rightMove(); addMoveInHistory("  --> river -->  ");}

    public void onPortal()
    {
        PosOfCell p = players[indCurPlayer].getPos().getPos();
        int idP = -1;
        for (int i = 0; i < map.portalCount; i++)
        {
            if (p.equals(map.portals[i].getPos()))
            {
               idP = map.portals[i].getIdP();
               break;
            }
        }
        if(idP == -1) System.out.println("ERROR_IN_onPortal!");
        int nextIdP;
        if (idP == map.portalCount - 1) nextIdP = 0;
        else nextIdP = idP + 1;

        p = map.portals[nextIdP].getPos();
        for (int i = 0; i < map.height; i++)
            for (int j = 0; j < map.width; j++)
                if (map.cells[i][j].getPos().equals(p))
                {
                    players[indCurPlayer].setPos(map.cells[i][j]);
                    break;
                }
        addMoveInHistory("  --> portal -->  ");

    }

    private void addMoveInHistory()
    {
        history.add(curPlayer.name + " --> " + PM.getGorName(curPlayer.getPos().getPos().gor) + PM.getVerName(curPlayer.getPos().getPos().ver));
    }
    private void addMoveInHistory(String s)
    {
        history.add(curPlayer.name + s + PM.getGorName(curPlayer.getPos().getPos().gor) + PM.getVerName(curPlayer.getPos().getPos().ver));
    }

    private void history0()
    {
        for (int i = 0; i < players.length; i++)
        {
            String s = new String(players[i].name + "  started on  "  +
                    PM.getGorName(players[i].getPos().getPos().gor) + PM.getVerName(players[i].getPos().getPos().ver));
            history.add(s);
        }
        history.add("---START--GAME---");
    }

    public String getHistory ()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("History: \n");
        for (int i = 0; i < history.size(); i++)
        {
            System.out.println(i + ":    " + history.get(i));
            sb.append(i + ":    " + history.get(i) + "\n");
        }
        return new String(sb);
    }

    public TIPlayer getCurPlayer()
    {
        return curPlayer;
    }

    public String getInfoMap()
    {
        return new String(map.inf() + getHistory());
    }
}
