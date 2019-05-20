package com.example.devilsgod.binimoy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class LostandFoundFragment extends Fragment {
    private FloatingActionMenu fabMenu;
    private FloatingActionButton addFab;
      private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;




    public LostandFoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lostand_found, container, false);

        addFab = view.findViewById(R.id.lostFabAdd);
        fabMenu = view.findViewById(R.id.lostFabMenu);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLostItem ali=new AddLostItem();
              fragmentManager=getActivity().getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction()
                        .replace(R.id.lost_fragment_holder,ali,"asa");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fabMenu.setVisibility(View.GONE);

            }
        });
      loadLostItemss();

        return view;
    }
    public void loadLostItemss(){
        LoadLostItems loadFrag=new LoadLostItems();
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction()
                .replace(R.id.lost_fragment_holder,loadFrag,"loadex");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
