package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.Donator;
import br.com.faj.project.donationapp.model.Message;
import br.com.faj.project.donationapp.model.Person;

public class Messages extends AppCompatActivity {

    private EditText personET;
    private EditText newMessageET;

    private List<Message> messages = new ArrayList<>();
    private RecyclerView messagesRecycler;
    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        personET = findViewById(R.id.personET);
        newMessageET = findViewById(R.id.newMessageET);
        personET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Person eu = new Person(1, "gus@gmail", "", "", null, "", "", "", null);
        Person outro = new Person(2, "joao@gmail", "", "", null, "", "", "", null);

        messages.add(new Message("Ola, bom dia!", eu, outro));
        messages.add(new Message("Tudo bem?", eu, outro));
        messages.add(new Message("Tudo sim, e você?", outro, eu));
        messages.add(new Message("Tudo tb, obg.", eu, outro));

        messagesAdapter = new MessagesAdapter(messages);
        messagesRecycler = findViewById(R.id.messages_recycler);
        messagesRecycler.setLayoutManager(new LinearLayoutManager(this));
        messagesRecycler.setAdapter(messagesAdapter);

    }

    public void sendMessage(View view) {
        String message = newMessageET.getText().toString();
        if (message.trim().isEmpty()) return;
        messages.add(new Message(message, new Person(1), new Person(2)));
        messagesAdapter.notifyDataSetChanged();
        messagesRecycler.scrollToPosition(messages.size() - 1);
        newMessageET.setText("");
    }
}
