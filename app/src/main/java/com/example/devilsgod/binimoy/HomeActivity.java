package com.example.devilsgod.binimoy;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
public class HomeActivity extends AppCompatActivity {

private DrawerLayout drawer;
private ActionBarDrawerToggle toggle;
private NavigationView nav;

 private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView userName;
    private ImageView userImage;
  Fragment fragment=null;
    Class classFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_home);
        nav=findViewById(R.id.nav_viewId);
        drawer=findViewById(R.id.DrawerLayoutId);
        toggle=new ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BinimoyFragment fraagment=new BinimoyFragment();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.placeHolderId, fraagment).commit();
        ft.addToBackStack(null);



       addNavHeader();


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.homeId:
                        classFragment = BinimoyFragment.class;
                        drawer.closeDrawers();
                        break;

                    case R.id.exchangeId:
                        classFragment = ExchangeHomeFragment.class;
                        drawer.closeDrawers();
                        break;

                    case R.id.lostAndFoundId:
                        classFragment = LostandFoundFragment.class;
                        drawer.closeDrawers();
                        break;
                    case R.id.shareId:
                        classFragment = ShareFragment.class;
                        drawer.closeDrawers();
                        break;
                    case R.id.studyMaterialId:
                        classFragment = StudyMaterialFragment.class;
                        drawer.closeDrawers();
                        break;
                    case R.id.binimoy_logoutId:
                        logOut();
                        drawer.closeDrawers();
                        break;
                    default:
                        classFragment = BinimoyFragment.class;

                }
                try {
                    fragment = (Fragment) classFragment.newInstance();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                   FragmentManager fragmentManager=getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.placeHolderId, fragment).commit();
                    return true;
                }

        });

        }

    private void logOut(){
       AuthUI.getInstance().signOut(this)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       Toast.makeText(HomeActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                     Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                     startActivity(intent);
                   }
               });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    public void addNavHeader(){
        View navHeader=nav.getHeaderView(0);
        userName=navHeader.findViewById(R.id.nav_header_textView);
        userImage=navHeader.findViewById(R.id.nav_header_imageView);
        user=FirebaseAuth.getInstance().getCurrentUser();
        Uri photoUri=user.getPhotoUrl();
        String name=user.getEmail();
        Picasso.get().load(photoUri).into(userImage);
        userName.setText(name);
    }
}
