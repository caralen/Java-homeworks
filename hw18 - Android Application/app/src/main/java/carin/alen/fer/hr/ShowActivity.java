package carin.alen.fer.hr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This activity reads data that the calling activity passed as extras.
 * Data is displayed as a toast message.
 * This activity contains an edit text and a button.
 * When the button is pressed text from the edit text is read and sent back to the calling activity.
 */
public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String result = getIntent().getExtras().getString("rezultat");
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        Button btnSend = findViewById(R.id.btn_send);
        EditText inputText = findViewById(R.id.input_text);

        btnSend.setOnClickListener(e -> {

            if(inputText.getText().toString().equals("")){
                Toast.makeText(
                        ShowActivity.this,
                        R.string.incomplete_message,
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            Intent intent = new Intent();
            Bundle data = new Bundle();
            data.putString("odgovor", inputText.getText().toString());
            intent.putExtras(data);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }


}
