package com.shariarunix.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    List<ContactModel> contactModelList = new ArrayList<>();
    BaseAdapter adapterContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView contactList = findViewById(R.id.list_contact);
        CardView cardAddContactBtn = findViewById(R.id.card_add_contact_btn);
        // Add Contact Button
        cardAddContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(MainActivity.this, AddContactActivity.class);
                nextActivity.putExtra("passIntentFrom", "AddContacts");
                startActivity(nextActivity);
            }
        });

        ContactTableClass contactTableClass = new ContactTableClass(MainActivity.this);
        contactModelList = contactTableClass.readAllContact();

        // Reverse List Element
        Collections.reverse(contactModelList);

        adapterContactList = new BaseAdapter() {
            @Override
            public int getCount() {
            return contactModelList.size();
            }

            @Override
            public Object getItem(int i) {
            return null;
            }

            @Override
            public long getItemId(int i) {
            return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.contact_list_item, viewGroup, false);
                }

                TextView txtShowName = view.findViewById(R.id.txt_show_name);
                TextView txtShowTitle = view.findViewById(R.id.txt_show_title);

                ImageView imgBtnDelete = view.findViewById(R.id.img_btn_delete);

                ContactModel contactModel = contactModelList.get(i);

                txtShowName.setText(contactModel.getName());
                txtShowTitle.setText(contactModel.getTitle());

                // Deleting Contact
                imgBtnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ContactTableClass(MainActivity.this).deleteContact(contactModel.getId());

                        contactModelList = new ContactTableClass(MainActivity.this).readAllContact();

                        // Reverse List Element After Deleting Any Contact
                        Collections.reverse(contactModelList);
                        adapterContactList.notifyDataSetChanged();
                    }
                });

                return view;
            }
        };
        contactList.setAdapter(adapterContactList);

        // Item Click Listener on Contact List
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent nextActivity = new Intent(MainActivity.this, AddContactActivity.class);
                nextActivity.putExtra("passIntentFrom", "EditContact");
                nextActivity.putExtra("contactID", contactModelList.get(i).getId());
                startActivity(nextActivity);
            }
        });

    }
}