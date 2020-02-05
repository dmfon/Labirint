package labirint.interra.com.labirint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PosActivity extends AppCompatActivity
{
    private TextView numberView;
    private EditText nameView;
    private Spinner spinner5;
    private Spinner spinner6;
    private Button okButton;

    public PropertyMap pm = PropertyMap.proMap();
    private int icur = 0;

    private String curName = null;
    private String NG = null;
    private String NV = null;

    private String pdata1[];
    private String pdata2[];

    private boolean readyFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pos_layout);

        numberView = (TextView) findViewById(R.id.textView8);
        nameView = (EditText) findViewById(R.id.editTextName);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        okButton = (Button) findViewById(R.id.button3);

        okButton.setEnabled(false);

        setParam();
    }

    public void setParam()
    {
        String s = new String(Integer.toString(icur + 1));
        numberView.setText(s);
        nameView.setText("");
        nameView.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String str = s.toString();
                if (!str.equals(""))
                {
                    curName = str;

                    if (!(curName.equals("")) && (NG != null) && (NV != null) && !readyFlag)
                    {
                        readyFlag = true;
                        enableOk();
                    }
                }
            }
        });

        pdata1 = new String[pm.gorNames.length];
        for (int i = 0; i < pdata1.length; i++)
        {
            pdata1[i] = pm.getGorName(i);
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pdata1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner5.setAdapter(adapter1);
        // устанавливаем обработчик нажатия
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                NG = pdata1[position];
                //System.out.println(readyFlag + "  " + curName + "  " + NG + "  " + NV);

                if ((curName != null) && (NV != null) && !readyFlag && !(curName.equals("")))
                {
                    readyFlag = true;
                    enableOk();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        pdata2 = new String[pm.verNames.length];
        for (int i = 0; i < pdata2.length; i++)
        {
            pdata2[i] = pm.getVerName(i);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pdata2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner6.setAdapter(adapter2);
        // устанавливаем обработчик нажатия
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                NV = pdata2[position];

                if ((NG != null) && (curName != null) && !readyFlag && !(curName.equals("")))
                {
                    readyFlag = true;
                    enableOk();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    public void enableOk()
    {
        okButton.setEnabled(true);
        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (curName.equals(""))
                {
                    okButton.setEnabled(false);
                    readyFlag = false;
                }
                else
                {
                    pm.setName(icur, curName);
                    pm.setPos(icur, new String(NG + NV));
                    icur++;
                    if (icur != pm.getCountPlayers())
                    {
                        readyFlag = false;
                        okButton.setEnabled(false);
                        curName = null;
                        NG = null;
                        NV = null;
                        setParam();
                    }
                    else
                    {
                        //System.out.println("icur   " + icur);
                        TIMaster.tima(pm);
                        TIMaster.tima().getInfoMap();
                        Intent intent = new Intent(PosActivity.this, GameActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
