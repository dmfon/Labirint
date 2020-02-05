package labirint.interra.com.labirint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.Toast;

public class SetActivity extends AppCompatActivity
{
    Spinner spinner3;
    boolean flagSpinner3 = false;
    Button genButton;

    private String pdata[] = {"2", "3", "4", "5", "6", "7", "8"};
    private String pdata2[] = new String[2];
    private String pdata4[] = new String[4];

    private int countPlayers;
    private int height = -1;
    private int width = -1;
    private boolean riverFlag;
    private boolean portalFlag;
    private int portalCount;
    private boolean inWallFlag;
    private int inWallCount;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner3.setEnabled(false);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pdata);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
               countPlayers = position + 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

        EditText ETHeight = (EditText) findViewById(R.id.editTextHeight);
        EditText ETWidth = (EditText) findViewById(R.id.editTextWidth);

        ETHeight.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String st = s.toString();
                if(!st.equals(""))
                {
                    height = Integer.parseInt(st);

                    if ((width != -1))
                    {
                        //flagSpinner3 = true;
                        makeSpinner3();
                        enableGen();
                    }
                }
            }
        });

        ETWidth.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String st = s.toString();
                if(!st.equals(""))
                {
                    width = Integer.parseInt(st);

                    if ((height != -1))
                    {
                        //flagSpinner3 = true;
                        makeSpinner3();
                        enableGen();
                    }
                }
            }
        });

        pdata2[0] = getResources().getString(R.string.set_no);
        pdata2[1] = getResources().getString(R.string.set_yes);
        // адаптер
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pdata2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        // устанавливаем обработчик нажатия
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0) riverFlag = false;
                else riverFlag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

        pdata4[0] = getResources().getString(R.string.set_no);
        pdata4[1] = getResources().getString(R.string.set_count_wals1);
        pdata4[2] = getResources().getString(R.string.set_count_wals2);
        pdata4[3] = getResources().getString(R.string.set_count_wals3);
        // адаптер
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pdata4);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner4.setAdapter(adapter4);
        // устанавливаем обработчик нажатия
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0) inWallFlag = false;
                else
                {
                    inWallFlag = true;
                    inWallCount = position - 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

        genButton = (Button) findViewById(R.id.buttonGen);
        genButton.setEnabled(false);
    }

    private void makeSpinner3()
    {
        int count = (height + width)/2 + 1;
        String pdata3[] = new String[count];
        for (int i = 0; i < count; i++)
        {
           pdata3[i] = new String(Integer.toString(i));
        }

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pdata3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner3.setAdapter(adapter3);
        spinner3.setEnabled(true);
        // устанавливаем обработчик нажатия
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0) portalFlag = false;
                else
                {
                    portalFlag = true;
                    portalCount = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
    }

    private void enableGen()
    {
        genButton.setEnabled(true);
        genButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PropertyMap.proMap(height, width, countPlayers, riverFlag, portalCount, portalFlag, inWallFlag, inWallCount);
                Intent intent = new Intent(SetActivity.this, PosActivity.class);
                startActivity(intent);
            }
        });
    }

}
