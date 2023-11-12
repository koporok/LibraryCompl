package com.example.Library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.notes.R;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        DataBaseAccessor databaseAccessor = new DataBaseAccessor(this);

        // Настройка полей для редактирования
        EditText title = findViewById(R.id.title);
        EditText genre = findViewById(R.id.genre);
        EditText author = findViewById(R.id.author);
        EditText review = findViewById(R.id.review);

        // Обработчики событий для полей редактирования
        genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genre.setFocusableInTouchMode(true);
                genre.setFocusable(true);
                genre.setCursorVisible(true);
                genre.requestFocus();
            }
        });
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setFocusableInTouchMode(true);
                title.setFocusable(true);
                title.setCursorVisible(true);
                title.requestFocus();
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
        // Обработчик события для кнопки сохранения
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем отредактированные данные из полей ввода
                String Title = title.getText().toString();
                String Genre = genre.getText().toString();
                String Author = author.getText().toString();
                String Review = review.getText().toString();

                if (Title.isEmpty() || Genre.isEmpty() || Author.isEmpty() || Review.isEmpty()) {
                    // Хотя бы одно поле не заполнено, высвечиваем Toast
                    Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    Book newBook = new Book(Title, Genre, Author, Review);

                    // Обновляем данные в объекте
                    databaseAccessor.addBook(Title, Genre, Author, Review);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedBook", newBook); // Обновленный объект

                    // Устанавливаем результат и передаем Intent обратно в MainActivity
                    setResult(Activity.RESULT_OK, resultIntent);

                    // Завершаем Activity
                    finish();
                }

            }
        });

    }
}