package com.example.Library;


import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

public class ServerAccessor {

    private String serviceAddress;

    public ServerAccessor(String ServiceAddress) {
        serviceAddress = ServiceAddress;
    }


    /**
     * получить список тем из списка заметок
     *
     * @param BookList
     * @return
     */
    public ArrayList<String> getStringListFromBookList(ArrayList<Book> BookList) {
        ArrayList<String> stringList = new ArrayList<>();
        Log.d("TAG", "array");
        for (Book book : BookList) {
            stringList.add(String.valueOf(book.getTitle()));
        }
        return stringList;
    }


    /**
     * Получить данные с сервера
     *
     * @return список заисей с сервера
     * @throws IOException
     */
    public ArrayList<Book> getData() throws IOException {
        return Parse(GetContent());
    }


    /**
     * Разобрать содержимое json в данные
     * вместо этого можно использовать десериализацию
     *
     * @param content вернувшиеся с сервера данные
     * @return список заметок
     */
    private ArrayList<Book> Parse(String content) {
        ArrayList<Book> dataItems;
        try {
            Gson gson = new Gson();
            Type listOfBooks = new TypeToken<List<Book>>() {
            }.getType();
            content = content.substring(13);content = content.substring(0, content.length() - 2);
            dataItems = gson.fromJson(content, listOfBooks);
            return dataItems;
        } catch (Exception ex) {
            Log.d("TAG", "Parse: Error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * метод подключается к серверу и забирает оттуда данные.
     * может работать долго.
     *
     * @return строка с полученными от серввера данными
     */
    private String GetContent() throws IOException {
        BufferedReader reader = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(serviceAddress);
            connection = (HttpURLConnection) url.openConnection();
            // Установить метод запроса
            connection.setRequestMethod("GET");
            // Установить время ожидания соединения (мс)
            connection.setConnectTimeout(5000);
            // Установить время ожидания чтения (мс)
            connection.setReadTimeout(5000);

            // получение данных
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return buf.toString();
        } catch (Exception exception) {
        } finally {
            // независимо, пошло ли что-то не так или все хорошо
            // нужно закрыть соединение и всё прочее
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}

