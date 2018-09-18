package carin.alen.fer.hr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This activity is used for composing an email message.
 * There are three input fields that take email address, email subject and the message.
 * On the click of a button (if no field is empty) application for sending emails will be started
 * with information that the user typed in this application.
 */
public class ComposeMailActivity extends AppCompatActivity {

    /** Array of strings containing email addresses that will be inserted in cc */
    static final String[] cc = new String[] {"ana@baotic.org", "marcupic@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);

        EditText emailAddress = findViewById(R.id.email_address);
        EditText emailTitle = findViewById(R.id.email_title);
        EditText emailMessage = findViewById(R.id.email_message);
        MaterialButton sendButton = findViewById(R.id.btn_send_email);

        sendButton.setOnClickListener(e -> {
            if(emailAddress.getText().toString().equals("") ||
                    emailTitle.getText().toString().equals("") ||
                    emailMessage.getText().toString().equals("")){

                Toast.makeText(
                        ComposeMailActivity.this,
                        R.string.form_incomplete,
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            composeEmail(
                    new String[] {emailAddress.getText().toString()},
                    cc,
                    emailTitle.getText().toString(),
                    emailMessage.getText().toString());

            finish();
        });
    }

    /**
     * Creates an implicit intent for creating an email.
     * @param address is an array of email addresses to which the email will be sent
     * @param ccAddresses is an array of email addresses that will be in cc
     * @param subject is the subject of the email
     * @param text is the text which will be in email
     */
    private void composeEmail(String[] address, String[] ccAddresses, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_CC, ccAddresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
