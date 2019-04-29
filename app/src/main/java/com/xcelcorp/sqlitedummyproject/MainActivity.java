package com.xcelcorp.sqlitedummyproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterClass.OnItemClickListener {

    EditText edName,edAge,edContact,edEmail,edAddress,edDisplayName,edDisplayAge,edDisplayContact,edDisplayEmail,edDisplayAddress;
    Button btnAdd;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    private List<ModelClass> modelClasses;

    int id,age;
    String name,email,contact,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName=findViewById(R.id.et_name);
        edAge=findViewById(R.id.et_age);
        edContact=findViewById(R.id.et_contact);
        edEmail=findViewById(R.id.et_email);
        edAddress=findViewById(R.id.et_address);
        btnAdd = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        modelClasses = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(edName.getText().toString().equals("") || edAge.getText().toString().equals("") || edContact.getText().toString().equals("") || edEmail.getText().toString().equals("") || edAddress.getText().toString().equals(""))){

                    String name = edName.getText().toString().trim();
                    int age = Integer.parseInt(edAge.getText().toString().trim());
                    String contact = edContact.getText().toString().trim();
                    String email = edEmail.getText().toString().trim();
                    String address = edAddress.getText().toString().trim();

                    databaseHelper.addContact(name, age, contact, email, address);
                    getData();

                    edName.setText("");
                    edAge.setText("");
                    edContact.setText("");
                    edEmail.setText("");
                    edAddress.setText("");

                }
                else {

                    Toast.makeText(MainActivity.this,"Please enter data in all text field to continue",Toast.LENGTH_SHORT).show();

                }
            }
        });

        getData();
    }

    private void getData() {
        modelClasses = new ArrayList<>();
        try {
            JSONArray jsonArray=databaseHelper.getAllData();
            if(jsonArray.length()>0){
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String id=jsonObject.getString("id");
                    String name=jsonObject.getString("name");
                    String age=jsonObject.getString("age");
                    String email=jsonObject.getString("email");
                    String contact=jsonObject.getString("contact_number");
                    String address=jsonObject.getString("address");
                    modelClasses.add(new ModelClass(id,name,age,contact,email,address));
                }
                AdapterClass adapter = new AdapterClass(MainActivity.this,modelClasses);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(MainActivity.this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        ModelClass clickedItem = modelClasses.get(position);
        id = Integer.parseInt(clickedItem.getId());
        name = clickedItem.getName();
        age = Integer.parseInt(clickedItem.getAge());
        email = clickedItem.getEmail();
        contact = clickedItem.getContact();
        address = clickedItem.getAddress();

        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.item_display,null);
        edDisplayName=view.findViewById(R.id.et_display_name);
        edDisplayAge=view.findViewById(R.id.et_display_age);
        edDisplayEmail=view.findViewById(R.id.et_display_email);
        edDisplayContact=view.findViewById(R.id.et_display_contact);
        edDisplayAddress=view.findViewById(R.id.et_display_address);

        edDisplayName.setText(name);
        edDisplayAge.setText(String.valueOf(age));
        edDisplayEmail.setText(email);
        edDisplayContact.setText(contact);
        edDisplayAddress.setText(address);

        alert.setView(view);
        alert.setTitle("Update or Delete Contact Details");
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                databaseHelper.deleteContact(id);
                getData();
                Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                name = edDisplayName.getText().toString().trim();
                age = Integer.parseInt(edDisplayAge.getText().toString().trim());
                email = edDisplayEmail.getText().toString().trim();
                contact = edDisplayContact.getText().toString().trim();
                address = edDisplayAddress.getText().toString().trim();
                databaseHelper.updateContact(id,name,age,email,contact,address);
                getData();
            }
        });
        alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }
}
