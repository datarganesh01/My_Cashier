package com.example.mycashier.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mycashier.R;

public class ChatsFragment extends Fragment {

    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // enable menu
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        toolbar = view.findViewById(R.id.toolbarwhatsapp);
        if (toolbar != null) {
            toolbar.setTitle("WhatsApp");
            toolbar.inflateMenu(R.menu.whatsapp_menu);

            // Optional: set click actions
            toolbar.setOnMenuItemClickListener(item -> {
                Toast.makeText(getActivity(), item.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
                return true;
            });
        }

        return view;
    }
}
