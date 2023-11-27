package com.example.Library;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.notes.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    final public static String KEY_TITLE = "title";
    final public static String KEY_AUTHOR = "author";
    final public static String KEY_GENRE = "genre";
    final public static String KEY_REVIEW = "review";
    final public static String KEY_POSITION = "position";

    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 123; // или любое другое уникальное число
    private static final String SERVICE_ADDRESS = "http://37.77.105.18/api/Books";

    ServerAccessor serverAccessor = new ServerAccessor(SERVICE_ADDRESS);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ListView listView;
    private ArrayAdapter<String> adapter1;
    private BookAdapter adapter;
    static ArrayList<Book> dataItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация dataItems здесь
        dataItems = new ArrayList<>();
        //dataItems.addAll(databaseAccessor.getAllData());
        //adapter1 = new BookAdapter(this, android.R.layout.simple_expandable_list_item_1, dataItems);
        // Инициализация адаптера

        Log.d("TAG", "листвю");

        listView = findViewById(R.id.listView);
        Log.d("TAG", "листвю");

        //listView.setAdapter(adapter1);
        adapter1 = AdapterUpdate(new ArrayList<>());
        Log.d("TAG", "листвю");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book selecteBook = dataItems.get(i);
                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                intent.putExtra("number", i);
                intent.putExtra("book", selecteBook);
                startActivity(intent);
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, запросить его у пользователя
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);
        }
        //Запуск фоновой задачи
        ProgressTask progressTask = new ProgressTask();
        executorService.submit(progressTask);
    }


    private ArrayAdapter<String> AdapterUpdate(ArrayList<Book> dataItems) {

        ArrayList<String> stringList = serverAccessor.getStringListFromBookList(dataItems);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                stringList);
        // установить адаптер в listview
        listView.setAdapter(adapter);
        return adapter;
    }
    class ProgressTask implements Runnable {
        String connectionError = null;

        @Override
        public void run() {
            try {
                // выполнение в фоне
                dataItems = serverAccessor.getData();
                // Обновление UI осуществляется в основном потоке
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (connectionError == null) {
                            adapter1 = AdapterUpdate(dataItems);
                        } else {
                            //проблемы с интернетом
                        }
                    }
                });

            } catch (Exception ex) {
                connectionError = ex.getMessage();
            }
        }
    }
}