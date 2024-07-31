package com.example.naderesto2.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.naderesto2.Domain.Photo;
import com.example.naderesto2.Domain.Review;
import com.example.naderesto2.Helper.PhotoDataSource;
import com.example.naderesto2.Helper.ReviewDataSource;
import com.example.naderesto2.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class ProfileActivity extends BaseActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private EditText nameEditText, feedbackEditText;
    private Review review;
    Photo currentphoto;
    final int PERMISSION_REQUEST_CAMERA = 103;
    final int PERMISSION_REQUEST_PHONE = 102;
    ImageView BackBtn,userPhoto;
    private Button saveButton;
    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap photo = (Bitmap) data.getExtras().get("data");
                                float density = ProfileActivity.this.getResources().getDisplayMetrics().density;
                                int dp = 140;
                                int pixels = (int) ((dp * density) + 0.5);

                                Bitmap scaledPhoto = Bitmap.createScaledBitmap(photo, pixels, pixels, true);
                                currentphoto.setContactPhoto(scaledPhoto); // Ensure you have a method to convert Bitmap to a storable format

                                // Use Glide to load the photo and apply circular crop
                                Glide.with(ProfileActivity.this)
                                        .load(scaledPhoto)
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(userPhoto);

                                // Save the photo to the database
                            }
                        }
                    }
            );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityprofile);
        review = new Review();  // Initialize the review object
        initLayoutComponents();
        initTextChangedEvents();
        ButtomNavigation();
        initPhoto();
        initSaveButton();
        initCameraButton();


    }
    private void initPhoto() {
        currentphoto = new Photo();
        PhotoDataSource ds = new PhotoDataSource(this);
        try {
            ds.open();
            currentphoto = ds.getSpecificContact(ds.getLastphoto());
            ds.close();
            if (currentphoto.getContactPhoto() != null) {
                Glide.with(this)
                        .load(currentphoto.getContactPhoto())
                        .apply(RequestOptions.circleCropTransform())
                        .into(userPhoto);
                Log.d("initPhoto", "Photo successfully loaded and set");
            } else {
                Glide.with(this)
                        .load(R.drawable.person)
                        .apply(RequestOptions.circleCropTransform())
                        .into(userPhoto);
                Log.d("initPhoto", "Default photo set");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error Loading Data", Toast.LENGTH_SHORT).show();
            Log.d("initPhoto", "Photo loading failed", e);
        }
    }

private void deletePhotoandName(){




}

    private void initSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasSuccessful = false;
                ReviewDataSource reviewDataSource = new ReviewDataSource(ProfileActivity.this);
                try {
                    reviewDataSource.open();
                    Log.d("initSaveButton", "Review database opened");

                    if (review.getReview_Id() == 0) {
                        Log.d("initSaveButton", "Review ID is 0, attempting to insert new review");
                        wasSuccessful = reviewDataSource.insertReview(review);
                        if (wasSuccessful) {
                            Log.d("initSaveButton", "Review inserted successfully");
                            int newId = reviewDataSource.getLastReview();
                            review.setReview_Id(newId);
                            Log.d("initSaveButton", "New review ID set: " + newId);
                        } else {
                            Log.e("initSaveButton", "Failed to insert review");
                        }
                    } else {
                        Log.d("initSaveButton", "Review ID is not 0, skipping insert");
                    }
                } catch (Exception e) {
                    Log.e("initSaveButton", "Error saving review", e);
                } finally {
                    reviewDataSource.close();
                    Log.d("initSaveButton", "Review database closed");
                }

                boolean wasSuccessful2 = false;
                PhotoDataSource photoDataSource = new PhotoDataSource(ProfileActivity.this);
                try {
                    photoDataSource.open();
                    Log.d("initSaveButton", "Photo database opened");

                    if (currentphoto.getContactID() == -1) {
                        Log.d("initSaveButton", "Contact ID is -1, attempting to insert new contact photo");
                        wasSuccessful2 = photoDataSource.insertContact(currentphoto);
                        if (wasSuccessful2) {
                            Log.d("initSaveButton", "Contact photo inserted successfully");
                            int newId = photoDataSource.getLastphoto();
                            currentphoto.setContactID(newId);
                            Log.d("initSaveButton", "New contact ID set: " + newId);
                        } else {
                            Log.e("initSaveButton", "Failed to insert contact photo");
                        }
                    } else {
                        Log.d("initSaveButton", "Contact ID is not -1, attempting to update contact photo");
                        if (wasSuccessful2) {
                            Log.d("initSaveButton", "Contact photo updated successfully");
                        } else {
                            Log.e("initSaveButton", "Failed to update contact photo");
                        }
                    }
                } catch (Exception e) {
                    Log.e("initSaveButton", "Error saving contact photo", e);
                } finally {
                    photoDataSource.close();
                    Log.d("initSaveButton", "Photo database closed");
                }
            }
        });
    }

    private void initCameraButton() {
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            ProfileActivity.this, Manifest.permission.CAMERA)) {
                        Snackbar.make(findViewById(R.layout.activityprofile),
                                        "The app needs permission to take photo",
                                        Snackbar.LENGTH_INDEFINITE)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("MainActivity Camera permission", "");
                                        ActivityCompat.requestPermissions(ProfileActivity.this,
                                                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                                    }
                                }).show();
                    } else {
                        Log.d("MainActivity Camera permission", "");
                        ActivityCompat.requestPermissions(ProfileActivity.this,
                                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    }
                }
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_PHONE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You may now make phone calls from this app",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,
                            "You will not be able to make phone calls from this app",
                            Toast.LENGTH_LONG).show();
                }
                break;
            }
            case PERMISSION_REQUEST_CAMERA:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Toast.makeText(this,
                            "You will not be able to save contact photo from this app",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private void ButtomNavigation(){

        ImageView floatingActionButton=findViewById(R.id.CartNavBtn);
        ImageView homeBtn=findViewById(R.id.homeBtnNav);
        ImageView reviewBtn=findViewById(R.id.supportimageView7);
        ImageView navperson=findViewById(R.id.personnav);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ProfileActivity.this, MainActivity.class));
            }
        });
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ReviewsActivity.class));

            }
        });
        navperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));

            }
        });

    }

    private void initTextChangedEvents() {
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Before", nameEditText.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("on", nameEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("After", nameEditText.getText().toString());
                review.setCostumerName(nameEditText.getText().toString());
            }
        });

        feedbackEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Before", feedbackEditText.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("on", feedbackEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("After", feedbackEditText.getText().toString());
                review.setCostumerfeedback(feedbackEditText.getText().toString());
            }
        });



    }
    private void initLayoutComponents(){

        nameEditText = findViewById(R.id.UserNameeditTextText2);
        feedbackEditText = findViewById(R.id.Reviewedittext);
        saveButton = findViewById(R.id.reviewsavebutton);
        userPhoto = findViewById(R.id.profileImageView1);
        BackBtn = findViewById(R.id.backBtn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}