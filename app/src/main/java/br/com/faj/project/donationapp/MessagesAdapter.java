package br.com.faj.project.donationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.faj.project.donationapp.model.Message;

class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageItemHolder> {

    private final int MY_MESSAGE = 1;
    private final int THEIR_MESSAGE = 2;

    private List<Message> messageList;

    MessagesAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == MY_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
        } else if (viewType == THEIR_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.their_message, parent, false);
        } else {
            try {
                throw new Exception("Tipo de mensagem inválido");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return new MessageItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageItemHolder holder, int position) {
        Message message = messageList.get(position);
        holder.mMessage.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {

        //TODO pegar usuário doando
        int my_id = 1;
        Message message = messageList.get(position);
        System.out.println(message.getSender().getId());
        if (message.getSender().getId() == 1) {
            return MY_MESSAGE;
        } else {
            return THEIR_MESSAGE;
        }
    }

    public class MessageItemHolder extends RecyclerView.ViewHolder {
        TextView mMessage;

        public MessageItemHolder(@NonNull View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.messageVT);
        }
    }
}
