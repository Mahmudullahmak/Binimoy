package com.example.devilsgod.binimoy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyMaterialFragment extends Fragment {
private Spinner spinner1,spinner2;
private Button findBtn;
private LinearLayout linearLayout;

    public StudyMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_study_material, container, false);
       findBtn=view.findViewById(R.id.findButtonId);
       linearLayout=view.findViewById(R.id.visibilityId);
             spinner1=view.findViewById(R.id.spinner1Id);
             spinner2=view.findViewById(R.id.spinner2Id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.deptName, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
  ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.semester, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
      findBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                MaterialsDetails mDetailsFrag=new MaterialsDetails();
                 String dept=spinner1.getSelectedItem().toString();
                 String semester=spinner2.getSelectedItem().toString();
              Bundle args=new Bundle();
              args.putString("dept",dept);
              args.putString("semester",semester);
              mDetailsFrag.setArguments(args);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
              FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction()
                      .replace(R.id.studyFragmentHolder,mDetailsFrag,"m");
              fragmentTransaction.addToBackStack(null);
              fragmentTransaction.commit();
             linearLayout.setVisibility(View.GONE);

          }
      });
       return view;
    }

}
