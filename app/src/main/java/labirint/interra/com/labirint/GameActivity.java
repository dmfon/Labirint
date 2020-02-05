package labirint.interra.com.labirint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static labirint.interra.com.labirint.ListId.*;

public class GameActivity extends AppCompatActivity
{
    public final int MOVE_TOP = 0;
    public final int MOVE_RIGHT = 1;
    public final int MOVE_BOTTOM = 2;
    public final int MOVE_LEFT = 3;
    public final int MOVE_STOP = 4;

    public final int FIRE_TOP = 5;
    public final int FIRE_RIGHT = 6;
    public final int FIRE_BOTTOM = 7;
    public final int FIRE_LEFT = 8;

    public final int KNIFE_MOVE = 9;

    public final int BLOWON_TOP = 10;
    public final int BLOWON_RIGHT = 11;
    public final int BLOWON_BOTTOM = 12;
    public final int BLOWON_LEFT = 13;

    public final int INF_MOVE = 14;
    public final int EXIT_MOVE = 15;

    private Button topButton;
    private Button rightButton;
    private Button bottomButton;
    private Button leftButton;
    private Button stopButton;
    private boolean flagFire;
    private boolean flagBlowon;
    Button fireButton;
    Button knifeButton;
    Button blowonButton;
    Button infButton;
    Button exitButton;

    TextView numberView;
    TextView plView;
    TextView eventView;
    int flagView;

    String endEvent;
    boolean flagStart;

    boolean winFlag;
    int sevenFlag = -1;

    TIMaster TIM;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        winFlag = false;
        TIM = TIMaster.tima();
        startgame();
    }

    public void startView()
    {
        sevenFlag = 0;
        if (flagView == 0) plView.setText(getResources().getString(R.string.p_raund));
        else if (flagView == 2) plView.setText(getResources().getString(R.string.p_result));
        numberView.setText(TIM.getCurPlayer().name);

        numberView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("FV  " + flagView);
                if (flagView == 0)
                {
                    flagView = 1;
                    if (TIM.curFlag())
                    {
                        eventView.setText(getStartEvent0());
                        TIM.got();
                    }
                    else eventView.setText(getCurEvent());
                }
                else if (flagView == 1)
                {
                    flagView = 2;
                    showActionMenu();
                }
                else if (flagView == 2)
                {
                    flagView = 3;
                    eventView.setText(endEvent);
                    if (winFlag) win();
                    else
                    {
                        System.out.println("POS: " + TIM.getCurPlayer().getPos().getGorName() + TIM.getCurPlayer().getPos().getVerName());
                        endRaund();
                    }
                }
                else
                {
                    flagView = 0;
                    eventView.setText("");
                    plView.setText(getResources().getString(R.string.p_raund));
                    numberView.setText(TIM.getCurPlayer().name);
                }
            }
        });
        plView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("FV  " + flagView);
                if (flagView == 0)
                {
                    flagView = 1;
                    if (TIM.curFlag())
                    {
                        eventView.setText(getStartEvent0());
                        TIM.got();
                    }
                    else eventView.setText(getCurEvent());
                }
                else if (flagView == 1)
                {
                    flagView = 2;
                    showActionMenu();
                }
                else if (flagView == 2)
                {
                    flagView = 3;
                    eventView.setText(endEvent);
                    if (winFlag) win();
                    else
                    {
                        System.out.println("POS: " + TIM.getCurPlayer().getPos().getGorName() + TIM.getCurPlayer().getPos().getVerName());
                        endRaund();
                    }
                }
                else
                {
                    flagView = 0;
                    eventView.setText("");
                    plView.setText(getResources().getString(R.string.p_raund));
                    numberView.setText(TIM.getCurPlayer().name);
                }
            }
        });
        eventView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (flagView == 0)
                {
                    flagView = 1;
                    if (TIM.curFlag())
                    {
                        eventView.setText(getStartEvent0());
                        TIM.got();
                    }
                    else eventView.setText(getCurEvent());
                }
                else if (flagView == 1)
                {
                    flagView = 2;
                    showActionMenu();
                }
                else if (flagView == 2)
                {
                    flagView = 3;
                    eventView.setText(endEvent);
                    if (winFlag) win();
                    else
                    {
                        System.out.println("POS: " + TIM.getCurPlayer().getPos().getGorName() + TIM.getCurPlayer().getPos().getVerName());
                        endRaund();
                    }
                }
                else
                {
                    flagView = 0;
                    eventView.setText("");
                    plView.setText(getResources().getString(R.string.p_raund));
                    numberView.setText(TIM.getCurPlayer().name);
                }
            }
        });

    }

    public String getCurEvent()
    {
        int s = TIM.getCurSend();
        if (s == -1) return "....";
        else
        {
            switch (s)
            {
                case SEND_WOUND:
                {
                    TIM.setCurSend();
                    return new String(getResources().getString(R.string.w_player));
                }
                default: return "ERROR_IN_getCurEvent";
            }
        }
    }

    public void endRaund()
    {
        TIM.endRaund();
        /*setContentView(R.layout.raund_layout);
        plView = (TextView) findViewById(R.id.textView5);
        numberView = (TextView) findViewById(R.id.textView12);
        eventView = (TextView) findViewById(R.id.textView26);
        startView();*/
    }

    public void startgame()
    {
        setContentView(R.layout.raund_layout);
        plView = (TextView) findViewById(R.id.textView5);
        numberView = (TextView) findViewById(R.id.textView12);
        eventView = (TextView) findViewById(R.id.textView26);

        flagView = 0;
        startView();
        //TIM.getHistory();
    }

    public void setDefMoveButtons ()
    {
        topButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { actionMove(MOVE_TOP); }
        });
        rightButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { actionMove(MOVE_RIGHT); }
        });
        bottomButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { actionMove(MOVE_BOTTOM); }
        });
        leftButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { actionMove(MOVE_LEFT); }
        });
        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { actionMove(MOVE_STOP); }
        });
    }

    public void showActionMenu()
    {
        setContentView(R.layout.action_layout);

        TextView nameView = (TextView) findViewById(R.id.textView22);
        nameView.setText(TIM.getCurPlayer().name);

        TextView treasureView = (TextView) findViewById(R.id.textView20);
        if (TIM.getCurPlayer().isTreasure()) treasureView.setText(getResources().getString(R.string.r_treasure));
        if (TIM.getCurPlayer().isWound()) treasureView.setText(getResources().getString(R.string.r_wound));

        topButton = (Button) findViewById(R.id.button10);
        rightButton = (Button) findViewById(R.id.button7);
        bottomButton = (Button) findViewById(R.id.button8);
        leftButton = (Button) findViewById(R.id.button4);
        stopButton = (Button) findViewById(R.id.button9);

        fireButton = (Button) findViewById(R.id.button5);
        flagFire = false;
        knifeButton = (Button) findViewById(R.id.button11);
        blowonButton = (Button) findViewById(R.id.button12);
        flagBlowon = false;
        infButton = (Button) findViewById(R.id.button13);
        exitButton = (Button) findViewById(R.id.button14);

        boolean flagEnabled = true;
        if(TIM.getCurPlayer().isWound()) flagEnabled = false;
        fireButton.setEnabled(flagEnabled);
        knifeButton.setEnabled(flagEnabled);
        blowonButton.setEnabled(flagEnabled);

        if(TIM.getCurPlayer().getPatrons() < 1) fireButton.setEnabled(false);
        if(TIM.getCurPlayer().getDinamite() < 1) blowonButton.setEnabled(false);
        if(!TIM.getCurPlayer().isKnife()) knifeButton.setEnabled(false);

        setDefMoveButtons();

        exitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sevenFlag != 7 - 1) sevenFlag++;
                else superInf(true);
            }
        });

        knifeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { actionMove(KNIFE_MOVE); }
        });
        infButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.r_inf) + getNameCell(TIM.getCurPlayer().getPos()), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        fireButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (flagFire)
                {
                    setDefMoveButtons();
                    flagFire = false;
                    stopButton.setEnabled(true);
                    knifeButton.setEnabled(true);
                    blowonButton.setEnabled(true);
                }
                else
                {
                    flagFire = true;
                    stopButton.setEnabled(false);
                    knifeButton.setEnabled(false);
                    blowonButton.setEnabled(false);

                    topButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) { actionMove(FIRE_TOP); }
                    });
                    rightButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) { actionMove(FIRE_RIGHT); }
                    });
                    bottomButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) { actionMove(FIRE_BOTTOM); }
                    });
                    leftButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) { actionMove(FIRE_LEFT); }
                    });
                }
            }
        });


        blowonButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (flagBlowon)
                {
                    setDefMoveButtons();
                    flagBlowon = false;
                    stopButton.setEnabled(true);
                    knifeButton.setEnabled(true);
                    fireButton.setEnabled(true);
                }
                else
                {
                    flagBlowon = true;
                    stopButton.setEnabled(false);
                    knifeButton.setEnabled(false);
                    fireButton.setEnabled(false);

                    topButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) { actionMove(BLOWON_TOP); }
                    });
                    rightButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) { actionMove(BLOWON_RIGHT); }
                    });
                    bottomButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) { actionMove(BLOWON_BOTTOM); }
                    });
                    leftButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) { actionMove(BLOWON_LEFT); }
                    });
                }
            }
        });

    }

    private void superInf(final boolean source)
    {
        sevenFlag = 0;
        System.out.println("Super_INFO!");
        setContentView(R.layout.super_inf_layout);

        TextView si = (TextView) findViewById(R.id.textView30);
        si.setMovementMethod(new ScrollingMovementMethod());
        si.setText(TIM.getInfoMap());

        Button buttonR = (Button) findViewById(R.id.button6);
        buttonR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (source) showActionMenu();
                else
                {
                    TIMaster.del();
                    PropertyMap.del();
                    GameActivity.super.finish();
                }
            }
        });
    }

    public void win()
    {
        System.out.println(" Win!");
        setContentView(R.layout.win_layout);

        TextView nW = (TextView) findViewById(R.id.textView28);
        String name = new String(TIM.getCurPlayer().name);
        nW.setText(name);

        Button infButton = (Button) findViewById(R.id.button15);
        infButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                superInf(false);
            }
        });

        LinearLayout l = (LinearLayout) nW.getParent();
        l.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TIMaster.del();
                PropertyMap.del();
                GameActivity.super.finish();
            }
        });
        //while (true){}
    }

    public void actionMove (int idMove)
    {
        //System.out.println("Action:  " + idMove);
        int res;
        boolean flagRes = false;
        int w[];

        switch (idMove)
        {
            case MOVE_TOP:
            {
                res = TIM.topMove();
                w = new int[1];
                w[0] = -1;
                break;
            }
            case MOVE_RIGHT:
            {
                res = TIM.rightMove();
                w = new int[1];
                w[0] = -1;
                break;
            }
            case MOVE_BOTTOM:
            {
                res = TIM.bottomMove();
                w = new int[1];
                w[0] = -1;
                break;
            }
            case MOVE_LEFT:
            {
                res = TIM.leftMove();
                w = new int[1];
                w[0] = -1;
                break;
            }
            case MOVE_STOP:
            {
                res = TIM.stopMove();
                w = new int[1];
                w[0] = -1;
                break;
            }
            case FIRE_TOP:
            {
                w = TIM.fireTop();
                if (w[0] == -1)
                {
                    res = RES_FIRE_NO;
                    break;
                }
                else
                {
                    if (w.length > 1) res = RES_FIRE_YES_MANY;
                    else res = RES_FIRE_YES;
                    flagRes = TIM.setWound(w);
                    break;
                }
            }
            case FIRE_RIGHT:
            {
                w = TIM.fireRight();
                if (w[0] == -1)
                {
                    res = RES_FIRE_NO;
                    break;
                }
                else
                {
                    if (w.length > 1) res = RES_FIRE_YES_MANY;
                    else res = RES_FIRE_YES;
                    flagRes = TIM.setWound(w);
                    break;
                }
            }
            case FIRE_BOTTOM:
            {
                w = TIM.fireBottom();
                if (w[0] == -1)
                {
                    res = RES_FIRE_NO;
                    break;
                }
                else
                {
                    if (w.length > 1) res = RES_FIRE_YES_MANY;
                    else res = RES_FIRE_YES;
                    flagRes = TIM.setWound(w);
                    break;
                }
            }
            case FIRE_LEFT:
            {
                w = TIM.fireLeft();
                if (w[0] == -1)
                {
                    res = RES_FIRE_NO;
                    break;
                }
                else
                {
                    if (w.length > 1) res = RES_FIRE_YES_MANY;
                    else res = RES_FIRE_YES;
                    flagRes = TIM.setWound(w);
                    break;
                }
            }
            case KNIFE_MOVE:
            {
                w = TIM.knife();
                if (w[0] == -1)
                {
                    res = RES_KNIFE_NO;
                    break;
                }
                else
                {
                    if (w.length > 1) res = RES_KNIFE_YES_MANY;
                    else res = RES_KNIFE_YES;
                    flagRes = TIM.setWound(w);
                    break;
                }
            }
            case BLOWON_TOP:
            {
                res = TIM.blowon(TOP_RIVER_ID);
                w = new int[1];
                w[0] = -1;
                break;
            }
            case BLOWON_RIGHT:
            {
                res = TIM.blowon(RIGHT_RIVER_ID);
                w = new int[1];
                w[0] = -1;
                break;
            }
            case BLOWON_BOTTOM:
            {
                res = TIM.blowon(BOTTOM_RIVER_ID);
                w = new int[1];
                w[0] = -1;
                break;
            }
            case BLOWON_LEFT:
            {
                res = TIM.blowon(LEFT_RIVER_ID);
                w = new int[1];
                w[0] = -1;
                break;
            }
            default:
            {
                res = -1;
                w = new int[1];
                w[0] = -1;
            }
        }


        setEndEvent(res, flagRes, w);
        setContentView(R.layout.raund_layout);
        plView = (TextView) findViewById(R.id.textView5);
        numberView = (TextView) findViewById(R.id.textView12);
        eventView = (TextView) findViewById(R.id.textView26);
        startView();
    }

    public void setEndEvent (int r, boolean f, int w[])
    {
        String s2 = new String("");
        if (w.length > 1)
        {
            StringBuilder sb = new StringBuilder();
           for (int i = 0; i < w.length; i++)
           {
                sb.append(TIM.players[w[i]].name);
                if (i != w.length - 1) sb.append(", ");
           }

           if ((r == RES_KNIFE_YES_MANY) && f) s2 = new String(getResources().getString(R.string.k_punch) + "\n"
                    + getResources().getString(R.string.k_punch_21) + "  " + sb + "  " + getResources().getString(R.string.k_punch_22)
                    + "\n" + getResources().getString(R.string.r_treasure) + "  " + getResources().getString(R.string.r_treasure_1));
           else if (r == RES_KNIFE_YES_MANY) s2 = new String(getResources().getString(R.string.k_punch) + "\n"
                   + getResources().getString(R.string.k_punch_21) + "  " + sb + "  " + getResources().getString(R.string.k_punch_22));
            if (r == RES_FIRE_YES_MANY) s2 = new String(getResources().getString(R.string.f_fire) + "\n"
                    + getResources().getString(R.string.f_fire_21) + "  " + sb + "  " + getResources().getString(R.string.f_fire_22));
        }
        else if (w.length == 1 && w[0] != -1)
        {
            if ((r == RES_KNIFE_YES) && f) s2 = new String(getResources().getString(R.string.k_punch) + "\n"
                    + getResources().getString(R.string.k_punch_11) + "  " + TIM.players[w[0]].name + "  " + getResources().getString(R.string.k_punch_12)
                    + "\n" + getResources().getString(R.string.r_treasure) + "  " + getResources().getString(R.string.r_treasure_1));
            else if (r == RES_KNIFE_YES) s2 = new String(getResources().getString(R.string.k_punch) + "\n"
                    + getResources().getString(R.string.k_punch_11) + "  " + TIM.players[w[0]].name + "  " + getResources().getString(R.string.k_punch_12));
            if (r == RES_FIRE_YES) s2 = new String(getResources().getString(R.string.f_fire) + "\n"
                    + getResources().getString(R.string.f_fire11) + "  " + TIM.players[w[0]].name + "  " + getResources().getString(R.string.f_fire12));
        }
        else
        {
            String s1;
            if (r > RES_EXTRA2)
            {
                r = r - RES_EXTRA2;
                s1 = new String(getResources().getString(R.string.r_treasure) + "\n"
                        + getResources().getString(R.string.r_treasure_1) + "\n");
            }
            else if (r > RES_EXTRA1)
            {
                r = r - RES_EXTRA1;
                s1 = new String(getResources().getString(R.string.r_treasure) + "\n"
                        + getResources().getString(R.string.r_treasure_0) + "\n");
            }
            else s1 = new String("");
            switch (r)
            {
                case RES_STOK:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_river_s));
                    break;
                }
                case RES_NEW_TOP:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_river) + "\n"
                                    + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_up));
                    break;
                }
                case RES_NEW_RIGHT:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_river) + "\n"
                            + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_right));
                    break;
                }
                case RES_NEW_BOTTOM:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_river) + "\n"
                            + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_bottom));
                    break;
                }
                case RES_NEW_LEFT:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_river) + "\n"
                            + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_left));
                    break;
                }
                case RES_NEW_PORTAL:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_portal) + "\n"
                            + getResources().getString(R.string.r_portal_0));
                    break;
                }
                case RES_WIN:
                {
                    winFlag = true;
                    s2 = new String("");
                    break;
                }
                case RES_FIRE_NO:
                {
                    s2 = new String(s1 + getResources().getString(R.string.f_fire) + "\n"
                            + getResources().getString(R.string.f_fire0));
                    break;
                }
                case RES_BLOWON_YES:
                {
                    s2 = new String(s1 + getResources().getString(R.string.d_dinamite) + "\n"
                            + getResources().getString(R.string.d_dinamite_1));
                    break;
                }
                case RES_BLOWON_NO:
                {
                    s2 = new String(s1 + getResources().getString(R.string.d_dinamite) + "\n"
                            + getResources().getString(R.string.d_dinamite_01));
                    break;
                }
                case RES_BLOWON_GLOBAL:
                {
                    s2 = new String(s1 + getResources().getString(R.string.d_dinamite) + "\n"
                            + getResources().getString(R.string.d_dinamite_02));
                    break;
                }
                case RES_KNIFE_NO:
                {
                    s2 = new String(s1 + getResources().getString(R.string.k_punch) + "\n"
                            + getResources().getString(R.string.k_punch_0));
                    break;
                }
                case RES_ARSENAL_YES:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_arsenal) + "\n"
                            + getResources().getString(R.string.r_arsenal_1));
                    break;
                }
                case RES_ARSENAL_NO:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_arsenal) + "\n"
                            + getResources().getString(R.string.r_arsenal_0));
                    break;
                }
                case RES_HOSPITAL_YES:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_hospital) + "\n"
                            + getResources().getString(R.string.r_hospital_0));
                    break;
                }
                case RES_HOSPITAL_NO:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_hospital));
                    break;
                }
                case RES_TREASURE_YES:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_treasure) + "\n"
                            + getResources().getString(R.string.r_treasure_1));
                    break;
                }
                case RES_TREASURE_NO:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_treasure) + "\n"
                            + getResources().getString(R.string.r_treasure_0));
                    break;
                }
                case RES_EMPTY:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_empty));
                    break;
                }
                case RES_EXIT:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_exit));
                    break;
                }
                case RES_WALL:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_wall) + "\n"
                            + getResources().getString(R.string.r_stop));
                    break;
                }
                case RES_WALL_RIVER_TOP:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_wall) + "\n"
                            + getResources().getString(R.string.r_river) + "\n"
                            + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_up));
                    break;
                }
                case RES_WALL_RIVER_RIGHT:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_wall) + "\n"
                            + getResources().getString(R.string.r_river) + "\n"
                            + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_right));
                    break;
                }
                case RES_WALL_RIVER_BOTTOM:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_wall) + "\n"
                            + getResources().getString(R.string.r_river) + "\n"
                            + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_bottom));
                    break;
                }
                case RES_WALL_RIVER_LEFT:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_wall) + "\n"
                            + getResources().getString(R.string.r_river) + "\n"
                            + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_left));
                    break;
                }
                case RES_WALL_PORTAL:
                {
                    s2 = new String(s1 + getResources().getString(R.string.r_wall) + "\n"
                            + getResources().getString(R.string.r_portal) + "\n"
                            + getResources().getString(R.string.r_portal_0));
                    break;
                }
                default: s2 = new String("ERROR_IN_setEndEvent!");
            }
        }
        if (TIM.eflag != -1)
        {
            String s3;
            switch (TIM.eflag)
            {
                case RES_EXTRA_ARSENAL:
                {
                    s3 = new String("\n" + getResources().getString(R.string.r_inf) + " "
                            + getResources().getString(R.string.r_arsenal) + "\n" + getResources().getString(R.string.r_arsenal_1));
                    break;
                }
                case RES_EXTRA_PORTAL:
                {
                    s3 = new String("\n" + getResources().getString(R.string.r_portal_0));
                    break;
                }
                case RES_EXTRA_RIVER_TOP:
                {
                    s3 = new String("\n" + getResources().getString(R.string.r_river0) + "  "
                            + getResources().getString(R.string.s_up) + "  " + getResources().getString(R.string.r_river_e));
                    break;
                }
                case RES_EXTRA_RIVER_RIGHT:
                {
                    s3 = new String("\n" + getResources().getString(R.string.r_river0) + "  "
                            + getResources().getString(R.string.s_right) + "  " + getResources().getString(R.string.r_river_e));
                    break;
                }
                case RES_EXTRA_RIVER_BOTTOM:
                {
                    s3 = new String("\n" + getResources().getString(R.string.r_river0) + "  "
                            + getResources().getString(R.string.s_bottom) + "  " + getResources().getString(R.string.r_river_e));
                    break;
                }
                case RES_EXTRA_RIVER_LEFT:
                {
                    s3 = new String("\n" + getResources().getString(R.string.r_river0) + "  "
                            + getResources().getString(R.string.s_left) + "  " + getResources().getString(R.string.r_river_e));
                    break;
                }
                default: s3 = new String("\nERROR_IN_CREATE_EXTRA_SEND! " + TIM.eflag);
            }
            TIM.eflag = -1;
            endEvent = new String(s2 + s3);
        }
        else endEvent = s2;
    }

    public String getNameCell (Cell a)
    {
        switch (a.getValue())
        {
            case EMPTY_ID: return new String("  " + getResources().getString(R.string.r_empty));
            case TREASURE_ID: return new String("  " + getResources().getString(R.string.r_treasure));
            case ARSENAL_ID: return new String("  " + getResources().getString(R.string.r_arsenal));
            case HOSPITAL_ID: return new String("  " + getResources().getString(R.string.r_hospital));
            case TOP_RIVER_ID: return new String("  " + getResources().getString(R.string.r_river));
            case RIGHT_RIVER_ID: return new String("  " + getResources().getString(R.string.r_river));
            case BOTTOM_RIVER_ID: return new String("  " + getResources().getString(R.string.r_river));
            case LEFT_RIVER_ID: return new String("  " + getResources().getString(R.string.r_river));
            case PORTAL_ID: return new String("  " + getResources().getString(R.string.r_portal));
            case STOK_ID: return new String("  " + getResources().getString(R.string.r_river_s));
            default: return new String("ERROR_on_getNameCell");
        }
    }

    public String getStartEvent0()
    {
        Cell c = TIM.getCurPlayer().getPos();
        String nameCell = getNameCell(c);
        switch (c.getValue())
        {
            case EMPTY_ID: return new String(getResources().getString(R.string.r_start) + nameCell);
            case TREASURE_ID:
            {
                if(TIM.findTreasure()) return new String(getResources().getString(R.string.r_start)
                    + nameCell + "\n" + getResources().getString(R.string.r_treasure_1));
                else return new String("ERROR_on_getStartEvent0!");

            }
            case ARSENAL_ID:
            {
                if(TIM.findArsenal()) return new String(getResources().getString(R.string.r_start)
                        + nameCell + "\n" + getResources().getString(R.string.r_arsenal_1));
                else return new String("ERROR_on_getStartEvent0!");
            }
            case HOSPITAL_ID: return new String(getResources().getString(R.string.r_start) + nameCell);
            case TOP_RIVER_ID:
            {
                TIM.onTopRiver();
                return new String(getResources().getString(R.string.r_start) + nameCell
                        + "\n" + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_up)
                        + "\n" + getResources().getString(R.string.r_start) + getNameCell(c.getTopCell()));
            }
            case RIGHT_RIVER_ID:
            {
                TIM.onRightRiver();
                return new String(getResources().getString(R.string.r_start) + nameCell
                        + "\n" + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_right)
                        + "\n" + getResources().getString(R.string.r_start) + getNameCell(c.getRightCell()));
            }
            case BOTTOM_RIVER_ID:
            {
                TIM.onBottomRiver();
                return new String(getResources().getString(R.string.r_start) + nameCell
                        + "\n" + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_bottom)
                        + "\n" + getResources().getString(R.string.r_start) + getNameCell(c.getBottomCell()));
            }
            case LEFT_RIVER_ID:
            {
                TIM.onTopRiver();
                return new String(getResources().getString(R.string.r_start) + nameCell
                        + "\n" + getResources().getString(R.string.r_river0) + "  " + getResources().getString(R.string.s_left)
                        + "\n" + getResources().getString(R.string.r_start) + getNameCell(c.getLeftCell()));
            }
            case PORTAL_ID:
            {
                TIM.onPortal();
                return new String(getResources().getString(R.string.r_start) + nameCell
                        + "\n" + getResources().getString(R.string.r_portal_0));
            }
            case STOK_ID: return new String(getResources().getString(R.string.r_start) + nameCell);

            default: return new String("ERROR_on_getStartEvent0_DEF!");
        }
    }
}


