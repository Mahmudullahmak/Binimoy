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
public class ExchangeHomeFragment extends Fragment {
    private com.github.clans.fab.FloatingActionButton exfabAdd;
    private FragmentManager exFragmentManager;
    private FragmentTransaction exfragmentTransaction;
    private FloatingActionMenu exfabMenu;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public ExchangeHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_exchange_home, container, false);

        exfabAdd = view.findViewById(R.id.exchange_fav_addItemId);
        exfabMenu = view.findViewById(R.id.exchange_fav_menuId);
        exfabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExchangeItemFragment exFrag = new AddExchangeItemFragment();
                exFragmentManager = getActivity().getSupportFragmentManager();
                exfragmentTransaction = exFragmentManager.beginTransaction()
                        .replace(R.id.exchange_fragment_holderId, exFrag, "ex");
                exfragmentTransaction.addToBackStack(null);
                exfragmentTransaction.commit();
                exfabMenu.setVisibility(View.GONE);
            }
        });
               loadExchangeItemss();

        return view;
    }
    public void loadExchangeItemss(){
        LoadExchangeItems loadFrag=new LoadExchangeItems();
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction()
                .replace(R.id.exchange_fragment_holderId,loadFrag,"loadex");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
