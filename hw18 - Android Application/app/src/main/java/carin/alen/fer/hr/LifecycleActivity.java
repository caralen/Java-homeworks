package carin.alen.fer.hr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/**
 * This class is the first activity that opens when the application starts.
 * It provides functionality for division of two integer numbers.
 *
 * This activity has three buttons:
 * <li>The first one is used for calculation</li>
 * <li>The second button is used for jumping to another activity and sending the result of calculation</li>
 * <li>The third button is used for composing an email message</li>
 */
public class LifecycleActivity extends AppCompatActivity {

    /** An integer number representing a code for recognizing operation */
    static final int SHOW_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        TextView label = findViewById(R.id.prikaz);
        EditText firstInput = findViewById(R.id.unos1);
        EditText secondInput = findViewById(R.id.unos2);

        MaterialButton btnCalculate = findViewById(R.id.btn_calculate);
        MaterialButton btnSend = findViewById(R.id.btn_send);
        MaterialButton email = findViewById(R.id.btn_email);


        btnCalculate.setOnClickListener(view -> {
            String text1 = firstInput.getText().toString();
            String text2 = secondInput.getText().toString();

            int first_number = 0;
            int second_number = 0;
            try {
                first_number = Integer.parseInt(text1);
                second_number = Integer.parseInt(text2);
            } catch(NumberFormatException ignored){
            }

            if(second_number != 0){
                label.setText(String.valueOf(first_number / (double) second_number));
            } else{
                Snackbar.make(view, R.string.invalid_operation, Snackbar.LENGTH_LONG).show();
            }

        });

        btnSend.setOnClickListener(e -> {
            if(label.getText().toString().equals("")){
                Toast.makeText(
                        LifecycleActivity.this,
                        R.string.calculate_first,
                        Toast.LENGTH_LONG
                ).show();
                return;
            }
            Intent intent = new Intent(LifecycleActivity.this, ShowActivity.class);
            Bundle data = new Bundle();
            data.putString("rezultat", label.getText().toString());
            intent.putExtras(data);
            startActivityForResult(intent, SHOW_RESULT);
        });

        email.setOnClickListener(e -> {
            Intent intent = new Intent(LifecycleActivity.this, ComposeMailActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == SHOW_RESULT){
            if(resultCode == Activity.RESULT_OK && intent.getExtras() != null){
                String result = Objects.requireNonNull(intent.getExtras().get("odgovor")).toString();
                Toast.makeText(LifecycleActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }
}
