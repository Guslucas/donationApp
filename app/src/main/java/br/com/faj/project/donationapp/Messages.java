package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.Donator;
import br.com.faj.project.donationapp.model.Message;
import br.com.faj.project.donationapp.model.Person;

public class Messages extends AppCompatActivity {

    List<Message> messages = new ArrayList<>();
    RecyclerView messagesRecycler;
    MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

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
}
