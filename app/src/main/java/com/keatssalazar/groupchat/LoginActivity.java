package com.keatssalazar.groupchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private String mDisplayName;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference;
    private RecyclerView recycler;
    private ScrollView sv;
    private Date date;
    private Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("messages");
        mDatabaseReference.keepSynced(true);



        mHandler=new Handler();

        // Link the Views in the layout to the Java code
        mInputText =  findViewById(R.id.mag);
        mSendButton = findViewById(R.id.send);
     //   mChatListView =findViewById(R.id.listview);


        recycler=findViewById(R.id.recycle);
        LinearLayoutManager layoutmanager=new LinearLayoutManager(LoginActivity.this);
      //  layoutmanager.setStackFromEnd(true);
      //  layoutmanager.setReverseLayout(true);
      //  recycler.setHasFixedSize(true);
        recycler.setLayoutManager(layoutmanager);


        // TODO: Send the message when the "enter" button is pressed
//        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                sendMessage();
//                return true;
//            }
//        });


        // TODO: Add an OnClickListener to the sendButton to send a message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }


    public static class viewholder extends RecyclerView.ViewHolder{
        View myview;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }
        public void setAuthour(String authour){



            TextView mAuthour=myview.findViewById(R.id.Author);
            mAuthour.setText(authour);

        }
        public void setMessage(String m){
            TextView messageView=myview.findViewById(R.id.Amessage);
            messageView.setText(m);
        }
        public void setDate(String date){
            TextView Mdate=myview.findViewById(R.id.datte);
            Mdate.setText(date);
        }
    }

    private void setupDisplayName(){
        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);

        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY,null);
        if(mDisplayName == null ) mDisplayName = "Anonymous";


    }
    private void sendMessage() {
        Log.d("FlashChat","I sent something");
        String input = mInputText.getText().toString();
        String date= DateFormat.getDateInstance().format(new Date());
        if(!input.equals("")){
            InstantMessage chat = new InstantMessage(input,mDisplayName,date);
            mDatabaseReference.push().setValue(chat);
            mInputText.setText("");
        }

        //Todo:Grab the text the user typed in and push the message to Firebase

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
    @Override
    public void onStart(){
        super.onStart();

        //   mAdapter = new ChatListAdapter(this,mDatabaseReference,mDisplayName);
      //  mChatListView.setAdapter(mAdapter);
        mDatabaseReference.keepSynced(true);
      FirebaseRecyclerAdapter<InstantMessage,viewholder>adapter=new FirebaseRecyclerAdapter<InstantMessage, viewholder>(InstantMessage.class,R.layout.chat_msg_row,viewholder.class,mDatabaseReference) {
          @Override
          protected void populateViewHolder(viewholder viewholder, InstantMessage instantMessage, int i) {

              viewholder.setAuthour(instantMessage.getAuthor());
              viewholder.setMessage(instantMessage.getMessage());
              viewholder.setDate(instantMessage.getDate());
          }
      };
      recycler.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
       // mAdapter.cleanup();
        FirebaseAuth current=FirebaseAuth.getInstance();
     //   current.signOut();

    }

}

