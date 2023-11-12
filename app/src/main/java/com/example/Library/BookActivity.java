package com.example.Library;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.R;

public class BookActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        DataBaseAccessor databaseAccessor = new DataBaseAccessor(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Book CurrentBook = (Book) bundle.get("book");
        int numberBook = bundle.getInt("number");

        // Настройка полей для редактирования
        EditText title = findViewById(R.id.title);
        EditText genre = findViewById(R.id.genre);
        EditText author = findViewById(R.id.author);
        EditText review = findViewById(R.id.review);

        int currentId = CurrentBook.getId();

        String currentTitle = CurrentBook.getTitle();
        String currentGenre = CurrentBook.getGenre();
        String currentAuthor = CurrentBook.getAuthor();
        String currentReview = CurrentBook.getReview();

        title.setText(currentTitle);
        genre.setText(currentGenre);
        author.setText(currentAuthor);
        review.setText(currentReview);

        genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre.setFocusableInTouchMode(true);
                genre.setFocusable(true);
                genre.setCursorVisible(true);
                genre.requestFocus();
            }
        });

        // Обработчики событий для полей редактирования
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setFocusableInTouchMode(true);
                title.setFocusable(true);
                title.setCursorVisible(true);
                title.requestFocus();
            }
        });

        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                author.setFocusableInTouchMode(true);
                author.setFocusable(true);
                author.setCursorVisible(true);
                author.requestFocus();
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review.setFocusableInTouchMode(true);
                review.setFocusable(true);
                review.setCursorVisible(true);
                review.requestFocus();
            }
        });
        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        // Обработчик события для кнопки сохранения
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем отредактированные данные из полей ввода
                String editedTitle = title.getText().toString();
                String editedGenre = genre.getText().toString();
                String editedAuthor = author.getText().toString();
                String editedReview = review.getText().toString();

                if (editedTitle.isEmpty() || editedGenre.isEmpty() || editedAuthor.isEmpty() || editedReview.isEmpty()) {
                    // Хотя бы одно поле не заполнено, высвечиваем Toast
                    Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    CurrentBook.setId(currentId);
                    CurrentBook.setTitle(editedTitle);
                    CurrentBook.setGenre(editedGenre);
                    CurrentBook.setAuthor(editedAuthor);
                    CurrentBook.setReview(editedReview);
                    // Обновляем данные в объекте
                    databaseAccessor.updateBook(currentId, editedTitle, editedGenre, editedAuthor, editedReview);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("num", numberBook); // Номер компьютера, который мы обновили
                    resultIntent.putExtra("del", false);
                    resultIntent.putExtra("updatedBook", CurrentBook); // Обновленный объект

                    // Устанавливаем результат и передаем Intent обратно в MainActivity
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent();
                int id = CurrentBook.getId();
                databaseAccessor.deleteBook(id);
                resultIntent.putExtra("num", numberBook);
                resultIntent.putExtra("del", true);

                // Устанавливаем результат и передаем Intent обратно в MainActivity
                setResult(Activity.RESULT_OK, resultIntent);

                finish();
            }
        });
    }
}