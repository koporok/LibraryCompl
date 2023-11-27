package com.example.Library;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.R;

public class BookActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Book CurrentBook = (Book) bundle.get("book");

        // Настройка полей для редактирования
        TextView title = findViewById(R.id.title);
        TextView genre = findViewById(R.id.genre);
        TextView author = findViewById(R.id.author);
        TextView review = findViewById(R.id.review);

        int currentId = CurrentBook.getId();
        String currentTitle = CurrentBook.getTitle();
        String currentGenre = CurrentBook.getGenre();
        String currentAuthor = CurrentBook.getAuthor();
        String currentReview = CurrentBook.getReview();

        title.setText(currentTitle);
        genre.setText(currentGenre);
        author.setText(currentAuthor);
        review.setText(currentReview);
    }
}