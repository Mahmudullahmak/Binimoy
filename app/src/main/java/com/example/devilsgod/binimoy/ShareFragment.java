package com.example.devilsgod.binimoy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {
    private com.github.clans.fab.FloatingActionButton fab;
   private FragmentManager fragmentManager;
   private FragmentTransaction fragmentTransaction;
    private FloatingActionMenu fabMenu;


    public ShareFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);


        fab = view.findViewById(R.id.add_menu_item);
        fabMenu = view.findViewById(R.id.menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSharedItemsFragment newFragment = new AddSharedItemsFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.share_fragment_holder, newFragment, "tag");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fabMenu.setVisibility(View.GONE);
            }
        });
                  loadItemss();
        return view;

    }



    public void loadItemss(){
            LoadShareItems loadFrag=new LoadShareItems();
           fragmentManager=getActivity().getSupportFragmentManager();
         fragmentTransaction=fragmentManager.beginTransaction()
                    .replace(R.id.share_fragment_holder,loadFrag,"load");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
 }

}





