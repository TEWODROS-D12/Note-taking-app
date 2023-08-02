package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.sql.Timestamp;

import io.grpc.okhttp.internal.Util;

public class NoteDetailsActivity extends AppCompatActivity {
    EditText titledittext,contentedittext;
    ImageButton savebtn;
    TextView page_titleTextView;
    String content,title,docId;
    boolean isEditeMode=false;
    TextView delete_textView_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titledittext=findViewById(R.id.title);
        contentedittext=findViewById(R.id.content);
        savebtn=findViewById(R.id.save_note);
        page_titleTextView=findViewById(R.id.page_title);
        delete_textView_btn=findViewById(R.id.delete_textview_Button);

        savebtn.setOnClickListener(V->saveNote());
        delete_textView_btn.setOnClickListener((v)->deleteNoteFromFireBase());

        //retrieve data

        content=getIntent().getStringExtra("content");
        title=getIntent().getStringExtra("title");
        docId=getIntent().getStringExtra("dogId");

        if(docId==null && !docId.isEmpty())
        {
           isEditeMode=true;
        }



  titledittext.setText(title);
  contentedittext.setText(content);


        if(isEditeMode){
            page_titleTextView.setText("edite your Note");
            delete_textView_btn.setVisibility(View.VISIBLE);
        }}

    void saveNote(){
        String noteTitle=titledittext.getText().toString();
        String noteContent=contentedittext.getText().toString();

        if(noteTitle==null|| noteTitle.isEmpty()){
            titledittext.setError("title is required");
            return;
        }

        Note note=new Note();
           note.setTitle(noteTitle);
           note.setContent(noteContent);
           saveNoteToFirebase(note);

    }



void saveNoteToFirebase(Note note){
   DocumentReference documentReference;
   if(isEditeMode){
       //update note

       documentReference=Utility.getCollectionReferenceForNote().document(docId);
   }else{
       //create note
       documentReference=Utility.getCollectionReferenceForNote().document();
   }


    documentReference=Utility.getCollectionReferenceForNote().document();
    documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
       @Override
       public void onComplete(@NonNull Task<Void> task) {
           if(task.isSuccessful()){
               Utility.showTest(NoteDetailsActivity.this,"Note is saved successfully");
               finish();
           }
           else {
                Utility.showTest(NoteDetailsActivity.this,"failed to save note");
           }

       }
   });
    }

    void deleteNoteFromFireBase(){
        DocumentReference documentReference;


            documentReference=Utility.getCollectionReferenceForNote().document(docId);
            //delete note
            documentReference=Utility.getCollectionReferenceForNote().document();



        documentReference=Utility.getCollectionReferenceForNote().document();
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showTest(NoteDetailsActivity.this,"Note deleted successfully");
                    finish();
                }
                else {
                    Utility.showTest(NoteDetailsActivity.this,"failed to delete note");
                }

            }
        });
    }



}