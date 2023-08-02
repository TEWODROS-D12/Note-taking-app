package com.example.notebook;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {
     static void showTest(Context context, String msg){

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceForNote(){
         FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

         return FirebaseFirestore.getInstance().collection("note").
                 document(currentUser.getUid()).collection("my Notes");

    }


}
