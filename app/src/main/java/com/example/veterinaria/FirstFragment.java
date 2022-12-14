package com.example.veterinaria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.veterinaria.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    TextView showCountTextView;
    private EditText digitEdt;
    private Button sendDataEdt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    DigitInfo digitInfo;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //le due righe successive vedono il valore di showCount e lo sostituiscono a textview:
        //in questo caso, viene incrementato dal second button (nel primo fragment!)
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        showCountTextView = binding.textviewFirst.findViewById(R.id.textview_first);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        digitEdt = view.findViewById(R.id.editTextDigit);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        digitInfo = new DigitInfo();

        binding.firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = digitEdt.getText().toString();
                addDataToFirebase(name);

            }
        });



        //click listener for first button
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sends value to the second fragment:
                int currentCount = Integer.parseInt(showCountTextView.getText().toString());
                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections
                        .actionFirstFragmentToSecondFragment(currentCount);
                NavHostFragment.findNavController(FirstFragment.this).navigate(action);


            }
        });

        //click listener for the second button ("lo clicco e fa qualcosa")
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countMe(view);
               //toast -> it prints a temp. string at the bottom of the first fragment
               //Toast myToast = Toast.makeText(getActivity(), "Hello there", Toast.LENGTH_SHORT);
               //myToast.show();
            }
        });

        //click listener for third button
        binding.buttonToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_thirdFragment);
            }
        });
    }

    private void addDataToFirebase(String name) {
        digitInfo.setDigit(name);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(digitInfo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //il valore di textView viene memorizzato in una stringa,
    //convertito in intero e incrementato
    //riconvertito a stringa
    private void countMe(View view) {
        String countString = showCountTextView.getText().toString();
        Integer count = Integer.parseInt(countString);
        count++;
        showCountTextView.setText(count.toString());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}