package com.ibm.mymedicalapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ibm.mymedicalapp.Adapters.PostAdapter;
import com.ibm.mymedicalapp.Interfaces.NotificationAPI;
import com.ibm.mymedicalapp.Models.Constants;
import com.ibm.mymedicalapp.Models.NotificationData;
import com.ibm.mymedicalapp.Models.Post;
import com.ibm.mymedicalapp.Models.PushNotification;

import com.ibm.mymedicalapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "QuestionsActivity";
    public static final String TOPIC = "/topics/";
    private static final int PReqCode = 2 ;
    private static final int REQUESCODE = 2 ;
    Dialog popAddPost ;
    Spinner medicalCatSpinner;
    ImageView popupPostImage,popupAddBtn;
    TextView popupQuestion;
    ProgressBar popupClickProgress;
    RecyclerView postRecyclerView;
    private Uri pickedImgUri = null;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PostAdapter postAdapter;
    List<Post> postList;
    String choice = "";

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList = new ArrayList<>();

                for (DataSnapshot postsnap: snapshot.getChildren()) {
                    Post post = postsnap.getValue(Post.class);
                    postList.add(post) ;
                }

                System.out.println(postList.size());
                postAdapter = new PostAdapter(QuestionsActivity.this, postList);
                postRecyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");

        postRecyclerView = findViewById(R.id.recycle_view_post);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        postRecyclerView.setHasFixedSize(true);

        iniPopupQuestion();
        setupPopupImageClick();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> popAddPost.show());

    }

    private void setupPopupImageClick() {
        // here when image clicked we need to open the gallery
        // before we open the gallery we need to check if our app have the access to user files
        // we did this before in register activity I'm just going to copy the code to save time ...
        popupPostImage.setOnClickListener(view -> checkAndRequestForPermission());
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(QuestionsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(QuestionsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(QuestionsActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(QuestionsActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();
    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    // when user picked an image ...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {
            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            popupPostImage.setImageURI(pickedImgUri);
        }
    }

    private void iniPopupQuestion() {

        popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.popup_add_post);
        popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popAddPost.getWindow().getAttributes().gravity = Gravity.TOP;

        // ini popup widgets
        medicalCatSpinner= popAddPost.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> medicalCatAdapter = ArrayAdapter.createFromResource(this,
                R.array.medical_department_array, R.layout.support_simple_spinner_dropdown_item);
        medicalCatAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        medicalCatSpinner.setAdapter(medicalCatAdapter);

        medicalCatSpinner.setOnItemSelectedListener(this);

        popupPostImage = popAddPost.findViewById(R.id.popup_img);
        popupQuestion = popAddPost.findViewById(R.id.popup_question);
        popupAddBtn = popAddPost.findViewById(R.id.popup_add);
        popupClickProgress = popAddPost.findViewById(R.id.popup_progressBar);

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupAddBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);

                if (!choice.isEmpty() && !popupQuestion.getText().toString().isEmpty()
                        && pickedImgUri != null ) {

                    //everything is okey no empty or null value
                    // TODO Create Post Object and add it to firebase database
                    // first we need to upload post Image
                    // access firebase storage

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("affection_images");
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownloadLink = uri.toString();
                                    // create post Object
                                    Post post = new Post(choice,
                                            popupQuestion.getText().toString(),
                                            imageDownloadLink,
                                            currentUser.getUid());

                                    // Add post to firebase database
                                    addPost(post);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // something goes wrong uploading picture
                                    showMessage(e.getMessage());
                                    popupClickProgress.setVisibility(View.INVISIBLE);
                                    popupAddBtn.setVisibility(View.VISIBLE);
                                }
                            });

                        }
                    });

                }
                else {
                    showMessage("Please verify all input fields and choose Post Image");
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void addPost(Post post) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts").push();

        // get post unique ID and update post key
        String key = myRef.getKey();
        post.setPostKey(key);

        // add post data to firebase database
        myRef.setValue(post).addOnSuccessListener(aVoid -> {
            showMessage("Post Added successfully");
            sendNotificationToDoctors(key);
            popupClickProgress.setVisibility(View.INVISIBLE);
            popupAddBtn.setVisibility(View.VISIBLE);
            popAddPost.dismiss();

        });
    }

    private void sendNotificationToDoctors(String postID) {
        NotificationData message = new NotificationData(currentUser.getDisplayName(), choice, postID);
        PushNotification data = new PushNotification(message, TOPIC + choice);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NotificationAPI apiService = retrofit.create(NotificationAPI.class);
        Call<Response> call = apiService.postNotification(data);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                Toast.makeText(QuestionsActivity.this, "Message Pushed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position > 0) {
            choice = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showMessage(String message) {

        Toast.makeText(QuestionsActivity.this,message,Toast.LENGTH_LONG).show();
    }
}
