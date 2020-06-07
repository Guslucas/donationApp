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

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.faj.project.donationapp.model.Message;
import br.com.faj.project.donationapp.model.Person;

public class Messages extends AppCompatActivity {

    private EditText personET;
    private EditText newMessageET;

    private List<Message> messages = new ArrayList<>();
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

        Person eu = new Person(donatorId, "gus@gmail", "", "", null, "", "", "", null);
        Person outro = new Person(2, "joao@gmail", "", "", null, "", "", "", null);

        messages.add(new Message("Ola, bom dia!", eu, outro));
        messages.add(new Message("Tudo bem?", eu, outro));
        messages.add(new Message("Tudo sim, e você?", outro, eu));
        messages.add(new Message("Tudo tb, obg.", eu, outro));

        messagesAdapter = new MessagesAdapter(messages, donatorId);

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

    public void sendMessage(View view) {
        String message = newMessageET.getText().toString();
        if (message.trim().isEmpty()) return;
        messages.add(new Message(message, new Person(donatorId), new Person(2)));
        messagesAdapter.notifyDataSetChanged();
        messagesRecycler.scrollToPosition(messages.size() - 1);
        newMessageET.setText("");
    }
}
