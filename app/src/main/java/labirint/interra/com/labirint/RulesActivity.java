package labirint.interra.com.labirint;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class RulesActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rules);
        TextView textEl = (TextView) findViewById(R.id.textView_rules_content);
        String infoText = getResources().getString(R.string.rules_text);
        textEl.setText(Html.fromHtml(infoText));
    }
}
