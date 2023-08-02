package com.example.notebook;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note,NoteAdapter.NoteViewHolder> {

    MainActivity context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, MainActivity context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.titleTextView.setText(note.Title);
        holder.contentTextView.setText(note.Content);
        holder.itemView.setOnClickListener((V)->{
            Intent intent=new Intent(context,NoteDetailsActivity.class);
             intent.putExtra("title",note.Title);
             intent.putExtra("content",note.Content);

             String docId=this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);

            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item,parent,false);
                return new NoteViewHolder(view);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{

      TextView contentTextView,titleTextView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView=itemView.findViewById(R.id.note_title_textView);
            contentTextView=itemView.findViewById(R.id.note_content_textView);

        }
    }
}
