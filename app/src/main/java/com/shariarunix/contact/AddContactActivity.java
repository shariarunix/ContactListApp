package com.shariarunix.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    private TextView txtPageTitle;
    private ImageView imgEditContact;
    private EditText edtName, edtTitle, edtPhone, edtEmail;
    private AppCompatButton btnAdd, btnReset;
    private String name, title, phone, email;

    private String toastMessage;
    ContactTableClass contactTableClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        String fromIntent = getIntent().getStringExtra("passIntentFrom");
        int contactId = getIntent().getIntExtra("contactID", -1);

        txtPageTitle = findViewById(R.id.txt_page_title);
        imgEditContact = findViewById(R.id.img_edit_contact);

        edtName = findViewById(R.id.edt_name);
        edtTitle = findViewById(R.id.edt_title);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);

        btnAdd = findViewById(R.id.btn_add);
        btnReset = findViewById(R.id.btn_reset);

        contactTableClass = new ContactTableClass(AddContactActivity.this);

        if (fromIntent.equals("AddContacts")) {
            txtPageTitle.setText("Add Contact");
            imgEditContact.setVisibility(View.GONE);
        } else if (fromIntent.equals("EditContact")) {
            btnReset.setVisibility(View.GONE);
            btnAdd.setText("Update");
        }

        if (contactId != -1) {
            ContactModel contactModel = contactTableClass.readContact(contactId);
            edtName.setText(contactModel.getName());
            edtTitle.setText(contactModel.getTitle());
            edtPhone.setText(contactModel.getPhone());
            edtEmail.setText(contactModel.getEmail());

            edtName.setEnabled(false);
            edtTitle.setEnabled(false);
            edtPhone.setEnabled(false);
            edtEmail.setEnabled(false);

            txtPageTitle.setText(contactModel.getName());
        }
        imgEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPageTitle.setText("Edit Contact");

                edtName.setEnabled(true);
                edtTitle.setEnabled(true);
                edtPhone.setEnabled(true);
                edtEmail.setEnabled(true);

                edtName.requestFocus();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString();
                title = edtTitle.getText().toString();
                phone = edtPhone.getText().toString();
                email = edtEmail.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    edtName.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!phone.matches("^(?:\\+88|0088)?(01[3-9]\\d{8})$")) {
                    Toast.makeText(AddContactActivity.this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(AddContactActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (contactId == -1) {
                    toastMessage = "Contact Added Successfully";
                    contactTableClass.insertContact(new ContactModel(name, title, phone, email));
                } else {
                    toastMessage = "Contact Updated Successfully";
                    contactTableClass.updateContact(new ContactModel(contactId, name, title, phone, email));
                }

                Toast.makeText(AddContactActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}