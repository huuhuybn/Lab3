package vn.edu.poly.lab3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnShowContact;
    private Button btnShowCallLogs;
    private Button btnShowMediaStore;
    private Button btnShowAppSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowContact = findViewById(R.id.btnShowContact);
        btnShowCallLogs = findViewById(R.id.btnShowCallLogs);
        btnShowMediaStore = findViewById(R.id.btnShowMediaStore);
        btnShowAppSettings = findViewById(R.id.btnShowAppSettings);


        btnShowContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted

                    //Toast.makeText(MainActivity.this, "Chua duoc cap quyen", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                            999);
                } else
                    showContacts();
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 999) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Toast.makeText(MainActivity.this, "Chua duoc cap quyen", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void showContacts() {
        // khoi tao duong dan bang doi tuong la Uri
        Uri uri = Uri.parse("content://contacts/people");

        // Khai bao 1 array de luu danh ba
        List<String> arrayContact = new ArrayList<>();
        String contact = "";

        Cursor cursor = getContentResolver().query(uri,
                null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                // di chuyen den vi tri dau tien o con tro cursor
                cursor.moveToFirst();

                // kiem tra dieu kien khi vong while doc het con tro
                while (cursor.isAfterLast() == false) {

                    // lay id contact thong qua ten co la _ID
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    // lay name contact thong qua ten co la DISPLAY_NAME
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    // noi chuoi id va name lay duoc vao bien contact
                    contact = contact + " \n " + id + name;

                    // di chuyen toi vi tri tiep theo trong con tro cursor
                    cursor.moveToNext();
                }
                cursor.close();

                Toast.makeText(this,
                        contact, Toast.LENGTH_LONG).show();


            } else {
                // Thong bao ko co danh ba
                Toast.makeText(this,
                        "Khong co danh ba tren dien thoai", Toast.LENGTH_SHORT).show();
            }

        } else {
            //Thong bao la ko truy cap dc danh ba hoac co loi
            Toast.makeText(this,
                    "Khong truy can duoc danh ba!", Toast.LENGTH_SHORT).show();

        }

    }
}
