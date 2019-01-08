package com.edualonso.dynamik.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edualonso.dynamik.R;
import com.edualonso.dynamik.activities.LoginActivity;
import com.edualonso.dynamik.activities.MainActivity;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.user.User;
import com.edualonso.dynamik.utils.Utils;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by edu_g on 17/07/2017.
 */

/**
 * Esta clase sirve para mostrar la funcionalidad de cambiar los datos de usuario
 */
public class ChangeData extends BaseFragment {


    MyModel model;
    private Utils utils;
    private static User myUser;
    private List<User> users;
    private String name,email,pass,confPass;
    private ImageView userImage;
    private EditText et_name,et_email,et_pass,et_confPass;
    private Button button_changeData;
    private DatabaseReference dbUsers;
    private FirebaseStorage storage ;

    private boolean mailExist,nameExist,passEqConf;


    private static final int SELECT_PICTURE = 1;
    public static final int REQUEST_CODE_PICTURE = 1;

    public static ChangeData newInstance(User u){
        myUser = u;
        ChangeData changeData = new ChangeData();
        return changeData;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Mis datos";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //--------------MyModel----------------

        model = MyModel.getInstace();
        users = model.getUsers();

        utils = new Utils();

        //------------Firebase-------------

        dbUsers=FirebaseDatabase.getInstance().getReference()
                        .child("Users");
        storage = FirebaseStorage.getInstance();

        //------------Widgets--------------

        userImage = (ImageView) view.findViewById(R.id.iv_changeImage);
        et_name = (EditText) view.findViewById(R.id.te_changeUser);
        et_email = (EditText) view.findViewById(R.id.te_changeEmail);
        et_pass = (EditText) view.findViewById(R.id.te_changePass);
        et_confPass= (EditText) view.findViewById(R.id.te_changeConfPass);
        button_changeData = (Button) view.findViewById(R.id.button_changeMyData);

        et_name.setText(myUser.getName());
        et_email.setText(myUser.getEmail());


        StorageReference storageRef = storage.getReferenceFromUrl("gs://miaplicacion-37f77.appspot.com");
        StorageReference imagesRef = storageRef.child("images");
        final StorageReference spaceRef = imagesRef.child(String.valueOf(myUser.getId())+".jpg");
        //myUser.getId()


        spaceRef.getDownloadUrl().addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).using(new FirebaseImageLoader()).load(spaceRef).into(userImage);
                //Glide.with(getActivity()).load(spaceRef).into(userImage);
            }
        });






        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String pickTitle = "Tome una foto de la camara o elija una foto de la galería"; // Or get from strings.xml
                Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
                chooserIntent.putExtra
                        (
                                Intent.EXTRA_INITIAL_INTENTS,
                                new Intent[] { takePhotoIntent }
                        );

                startActivityForResult(chooserIntent, SELECT_PICTURE);


            }
        });

        button_changeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                mailExist=true;
                nameExist=true;
                passEqConf=true;

                name = et_name.getText().toString().trim();
                email = et_email.getText().toString().trim();
                pass = et_pass.getText().toString().trim();
                confPass = et_confPass.getText().toString().trim();


//                ///------------Codify Pass --------------
//                MessageDigest md=null;
//                try {
//                    md = MessageDigest.getInstance("SHA-256");
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//
//                byte[] hash = md.digest(pass.getBytes());
//                StringBuffer sb = new StringBuffer();
//
//                for(byte b : hash){
//                    sb.append(String.format("%02x",b));
//                }

                String sb = utils.codifyPass(pass);

                if (name.equals("")){
                    Toast toast1 = Toast.makeText(getActivity(), "El campo nombre no puede estar vacio", Toast.LENGTH_SHORT);
                    toast1.show();
                    nameExist = false;
                }if (email.equals("")){
                    Toast toast1 = Toast.makeText(getActivity(), "El campo Email no puede estar vacio", Toast.LENGTH_SHORT);
                    toast1.show();
                    mailExist = false;
                }if (pass.equals("") || confPass.equals("")) {
                    Toast toast1 = Toast.makeText(getActivity(), "Los campos de contraseñas no pueden estar vacios", Toast.LENGTH_SHORT);
                    toast1.show();
                    passEqConf = false;
                }
//                }else{
//                    nameExist=mailExist=passEqConf=true;
//                }
                // Si hemos cambiado el campo nombre

                if(!myUser.getName().equals(name)){
                    for(User u : users) {
                        if (u.getName().equals(name)) {
                            Toast toast1 = Toast.makeText(getActivity(), "El usuario introducido ya existe", Toast.LENGTH_SHORT);
                            toast1.show();
                            nameExist = false;
                        } else {
                            //nameExist = true;
                        }
                    }
                }

                // Si hemos cambiado el campo email
                if(!myUser.getEmail().equals(email)){
                    for(User u : users) {
                        if (u.getEmail().equals(email)) {
                            Toast toast1 = Toast.makeText(getActivity(), "El email introducido ya existe", Toast.LENGTH_SHORT);
                            toast1.show();
                            mailExist = false;
                        }else{
                            nameExist = true;
                        }
                    }
                }

                if (!pass.equals(confPass)){
                    Toast toast1=Toast.makeText(getActivity(),"Error, las contraseñas no coinciden",Toast.LENGTH_SHORT);
                    toast1.show();
                    passEqConf=false;

                }else{
                    if(myUser.getPass().equals(sb.toString())){
                        passEqConf=true;
                    }else{
                        Toast toast1=Toast.makeText(getActivity(),"Error, la contraseña no es correcta",Toast.LENGTH_SHORT);
                        toast1.show();
                        passEqConf=false;
                    }

                }

                if (passEqConf && nameExist && mailExist){

                    String key =  myUser.getId();
                    myUser.setName(name);
                    myUser.setEmail(email);
                    myUser.setPhoto(userImage.getId());
                    //myUser.setPass(sb.toString());

//                    Toast toast1=Toast.makeText(getActivity(),"Image value : "+userImage.getId(),Toast.LENGTH_SHORT);
//                    toast1.show();
                    //myUser.setPhoto(userImage.getId());

                    // Actualizamos el valor en base de datos
                    dbUsers.child(key).setValue(myUser);

                    //Actualizamos el valor en local
                    for(int i=0;i<users.size();i++){
                        if(users.get(i).getId().equals(key)){
                            users.remove(i);
                            users.add(myUser);
                        }
                    }

                    //Actualizamos el valor de los SharedPrefered

                    SharedPreferences prefs = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("id",myUser.getId());
                    editor.putString("user", name);
                    editor.putString("email",myUser.getEmail());
                    editor.putString("pass",myUser.getPass());

                    editor.commit();


                    //Subo la imagen a la nube

                    StorageReference storageRef = storage.getReferenceFromUrl("gs://miaplicacion-37f77.appspot.com");
                    StorageReference imagesRef = storageRef.child("images");
                    StorageReference spaceRef = imagesRef.child(String.valueOf(myUser.getId())+".jpg");

                    userImage.setDrawingCacheEnabled(true);
                    userImage.buildDrawingCache();
                    Bitmap bitmap = userImage.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    final StorageReference ref = storageRef.child("images/mountains.jpg");
                    UploadTask uploadTask = spaceRef.putBytes(data);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });

                    /*UploadTask uploadTask = spaceRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    });*/


                    Intent mainIntent = new Intent(getActivity(),MainActivity.class);
                    getActivity().startActivity(mainIntent);
                    getActivity().finish();

                }else{
                    Toast toast1=Toast.makeText(getActivity(),"Algo no ha ido bien",Toast.LENGTH_SHORT);
                    toast1.show();
                }

            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_mydata,container,false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICTURE && resultCode == getActivity().RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                long lengthbmp = imageInByte.length/1024;
                Toast toast = Toast.makeText(getActivity(),String.valueOf(lengthbmp),Toast.LENGTH_SHORT);
                toast.show();
                if(lengthbmp<1024) {
                    userImage.setImageBitmap(bitmap);
                }else {
                    Toast toast2 = Toast.makeText(getActivity(), "El tamaño de la foto no debe de superar los 1024kb", Toast.LENGTH_SHORT);
                    toast2.show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
