package com.example.notebook;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton add_note;
    RecyclerView recyclerView;
    ImageButton menuButton;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_note=findViewById(R.id.add_btn);
        recyclerView=findViewById(R.id.recycler);
        menuButton=findViewById(R.id.menu);
        add_note.setOnClickListener(V->startActivity(new Intent(MainActivity.this,NoteDetailsActivity.class)));
        menuButton.setOnClickListener((v)->showMenu());
    }



    void showMenu(){
        PopupMenu popupMenu=new PopupMenu(MainActivity.this,menuButton);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

   void setupRecyclerView(){
        Query query=Utility.getCollectionReferenceForNote().orderBy("timestamp",Query.Direction.DESCENDING);
       FirestoreRecyclerOptions <Note>options =new FirestoreRecyclerOptions.Builder<Note>()
               .setQuery(query,Note.class).build();
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       noteAdapter =new  NoteAdapter(options,this);
       recyclerView.setAdapter(noteAdapter);
   }

protected void onStart(){
        super.onStart();
        noteAdapter.startListening();
}
protected void onStop(){
        super.onStop();
        noteAdapter.stopListening();
}
protected void onResume(){
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }

}