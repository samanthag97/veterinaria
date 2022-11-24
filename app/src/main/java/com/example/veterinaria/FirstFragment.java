package com.example.veterinaria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.veterinaria.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    TextView showCountTextView;

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