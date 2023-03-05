package com.pzwebdev.sfbe;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.pzwebdev.sfbe.databinding.FragmentFileSelectBinding;

import java.io.File;

public class FileSelectFragment extends Fragment {

    private File[] listFileBufor;

    private TextView textView_card_information;
    private String labelSDCard;
    private static final int REQUEST_CODE_PERMISSIONS = 100;
    private FragmentFileSelectBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFileSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Declare view elements.
        textView_card_information = view.findViewById(R.id.textView_card_information);

        if (checkSDCardStatus()) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);
            }

            // if `SDK_INT >= 30` it's a different request for authority.
            if (SDK_INT >= 30) {
                if (!Environment.isExternalStorageManager()) {
                    try {
                        Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                        startActivity(intent);
                    } catch (Exception ex) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivity(intent);
                    }
                } else {
                    //start the program if permission is granted
                    getFileList();
                }
            } else {
                getFileList();
            }
        } else {
            // TODO:
            //  Zablokować operację
            //  Inormacja o ponownej próbie wykrycia karty SD
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkSDCardStatus() {
        String SDCardStatus = Environment.getExternalStorageState();

        // MEDIA_UNKNOWN: unrecognized SD card
        // MEDIA_REMOVED: no SD card
        // MEDIA_UNMOUNTED: SD card exists but not mounted, deprecated in Android 4.0+
        // MEDIA_CHECKING: preparing SD card
        // MEDIA_MOUNTED: mounted and ready to use
        // MEDIA_MOUNTED_READ_ONLY
        switch (SDCardStatus) {
            case Environment.MEDIA_MOUNTED:
                return true;
            case Environment.MEDIA_MOUNTED_READ_ONLY:
                return false;
            default:
                textView_card_information.setText(R.string.file_select_sd_card_not_found);
                textView_card_information.setVisibility(View.VISIBLE);
                return false;
        }
    }

    private void getFileList() {
        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsoluteFile();
        File[] files = externalStorageDirectory.listFiles((dir, name) -> {
            // download only files with the extension .txt
            return name.toLowerCase().endsWith(".txt");
        });
        System.out.println("folder: " + externalStorageDirectory);

        if (files.length != 0) {
            for (File file : files) {
                // Do something with each text file
                System.out.println("File name: " + file.getName());
            }
        } else {
            System.out.println("Nie znaleziono pliku: " + files.length);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}