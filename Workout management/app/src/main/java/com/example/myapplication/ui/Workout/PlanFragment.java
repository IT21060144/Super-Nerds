package com.example.myapplication.ui.Workout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.WorkoutPlan;
import com.example.myapplication.utils.PlanListAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PlanFragment extends Fragment {

    MaterialButton addPlan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<WorkoutPlan> arrayList = new ArrayList<WorkoutPlan>();

        PlanListAdapter planListAdapter = new PlanListAdapter(getActivity(), arrayList);
        RecyclerView recyclerView = view.findViewById(R.id.plan_items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        recyclerView.setAdapter(planListAdapter);

        db.collection("MyWorkoutPlans")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null) {
                            Log.e("Firestore", error.getMessage());
                            return;
                        }

                        assert value != null;
                        int i = 0;
                        for(DocumentChange dc : value.getDocumentChanges()) {
                            if(dc.getType() == DocumentChange.Type.ADDED) {
                                arrayList.add(dc.getDocument().toObject(WorkoutPlan.class));
                                Log.i("explore fragment", "array : " + arrayList.get(i).workouts);
                            }

                            planListAdapter.notifyDataSetChanged();
                            i++;
                        }

                    }
                });

        MaterialButton addPlan = view.findViewById(R.id.add_plan);

        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent(getActivity(), CreateWorkoutActivity.class );
                startActivity(intent);
            }
        });


    }
}