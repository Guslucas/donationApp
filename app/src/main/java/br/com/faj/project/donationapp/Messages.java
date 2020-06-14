package br.com.faj.project.donationapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import br.com.faj.project.donationapp.model.Donator;
import br.com.faj.project.donationapp.model.Message;
import br.com.faj.project.donationapp.model.Person;

public class Messages extends AppCompatActivity {

    private EditText personET;
    private EditText newMessageET;

    private List<Message> mMessagesList = new ArrayList<>();
    private RecyclerView messagesRecycler;
    private MessagesAdapter messagesAdapter;

    private SharedPreferences loginInfoSP;
    private long donatorId;

    private RequestQueue queue;

    private Timer timer = new Timer();
    private final long DELAY = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        queue = Volley.newRequestQueue(this);

        personET = findViewById(R.id.personET);
        newMessageET = findViewById(R.id.newMessageET);
        personET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.toString().matches("\\w+@\\w+\\.\\w+")) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //getMessagesServer(s.toString());
                        }
                    }, DELAY);
                }
            }
        });

        loginInfoSP = getSharedPreferences("loginInfo", MODE_PRIVATE);
        donatorId = loginInfoSP.getLong("ID_DONATOR", -1);

        Log.i("ID_USUARIO", String.valueOf(donatorId));

        if (donatorId == -1) try {
            throw new Exception("Donator invalido");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Person eu = new Person(donatorId, "gus@gmail", "", "", null, "", "", "", null);
//        Person outro = new Person(2, "joao@gmail", "", "", null, "", "", "", null);
//        mMessagesList.add(new Message("Ola, bom dia!", eu, outro));
//        mMessagesList.add(new Message("Tudo bem?", eu, outro));
//        mMessagesList.add(new Message("Tudo sim, e você?", outro, eu));
//        mMessagesList.add(new Message("Tudo tb, obg.", eu, outro));
//
        messagesAdapter = new MessagesAdapter(mMessagesList, donatorId);

        messagesRecycler = findViewById(R.id.messages_recycler);
        messagesRecycler.setLayoutManager(new LinearLayoutManager(this));
        messagesRecycler.setAdapter(messagesAdapter);


    }

    private void getMessagesServer(String s) {
        String url = getResources().getString(R.string.url);
        url += "/donator/" + donatorId + "/message";
        Log.i("URL sendo usada", url);

        JSONObject json = new JSONObject();
        try {
            json.put("email",s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String email = json.toString();

        StringRequest messageRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listMessage(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
                } catch (Exception e) {
                    showError(e);
                }

            }
        }, null) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return email.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

    }

    public void sendMessage(View view) throws JSONException {
        String email = personET.getText().toString();
        String message = newMessageET.getText().toString();
        if (email.trim().isEmpty() || message.trim().isEmpty()) {
            showError("O usuário não pode estar vazio");
            return;
        }
        mMessagesList.add(new Message(message, new Person(donatorId), new Person(-10))); //TODO REMOVER DEPOIS DE TESTADO
        messagesAdapter.notifyDataSetChanged();
        messagesRecycler.scrollToPosition(mMessagesList.size() - 1);
        sendMessageServer();
        newMessageET.setText("");
    }

    public void listMessage(String response) throws JSONException{

        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        if (!jsonObject.getString("status").equalsIgnoreCase("OK")) {
            showError(jsonObject.getString("errorMessage"));
            return;
        }

        List<Message> messages = new ArrayList<>();
        JSONArray messagesArray = jsonObject.getJSONArray("object");

        for(int i=0; i<messagesArray.length(); i++){
            Message m = null;

            JSONObject jsonMessage = messagesArray.getJSONObject(i);

            long id = jsonMessage.getLong("id");
            String  content = jsonMessage.getString("content");
            //Date date = jsonMessage.getString();
            Donator idSender =  new Donator(jsonMessage.getJSONObject("sender").getLong("id"));
            Donator idReciver = new Donator(jsonMessage.getJSONObject("reciver").getLong("id"));

            m = new Message(id, content, null, idSender, idReciver);

            messages.add(m);
        }

        mMessagesList.clear();
        mMessagesList.addAll(messages);
        messagesAdapter.notifyDataSetChanged();

    }

    private void sendMessageServer() throws JSONException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        String url = getResources().getString(R.string.url);
        url += "/donator/" + donatorId + "/message/new";
        Log.i("URL sendo usada", url);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("content", newMessageET.getText().toString());
        jsonObject.put("receiver", new JSONObject().put("type", "Person").put("email", personET.getText().toString()));
        jsonObject.put("date",sdf.format(new Date()));

        final String message  = jsonObject.toString();

        StringRequest messageRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //listMessage(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
                    showError("Adicionado?");
                } catch (Exception e) {
                    showError(e);
                }
            }
        }, null){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return message.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

            queue.add(messageRequest);
    }

    private void showError(Exception e) {
        Snackbar.make(messagesRecycler, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String errorMessage) {
        Snackbar.make(messagesRecycler, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

}
