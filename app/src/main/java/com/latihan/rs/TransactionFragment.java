package com.latihan.rs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {

    LocalStorage localStorage;
    EditText etObat;
    BottomSheetDialog dialog;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserData> userList;
    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");

        localStorage =  new LocalStorage(getActivity());
        String token = localStorage.getData();

        etObat = view.findViewById(R.id.et_obat);
        dialog = new BottomSheetDialog(getActivity());
        createDialog();

        etObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    private void createDialog() {
        View view = getLayoutInflater().inflate(R.layout.bottom_dialog,  null, false);

        Button btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        DataUserService dataUserService = ApiClient.getRetrofit().create(DataUserService.class);
        Call<List<UserData>> call = dataUserService.getAllUser();
        call.enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                progressDialog.dismiss();
                recyclerView = view.findViewById(R.id.rv_obat);
                userAdapter = new UserAdapter(getActivity(), userList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                progressDialog.dismiss();
//                Toast.makeText(getActivity(), "Throwable : " + t, Toast.LENGTH_LONG).show();
                FailedMsg("Throwable : " + t);
            }

            private void FailedMsg(String s) {
                AlertDialog.Builder alertBuild = new AlertDialog.Builder(getActivity());
                alertBuild.setTitle("Gagal memuat")
                        .setMessage(s)
                        .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = alertBuild.create();
                alert.show();
            }

//        DataUserService dataUserService = ApiClient.getRetrofit().create(DataUserService.class);
//        Call<JsonObject> call = dataUserService.getAllUser();
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                progressDialog.dismiss();
//                recyclerView = view.findViewById(R.id.rv_obat);
//                userAdapter = new UserAdapter(getActivity(), userList);
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                progressDialog.dismiss();
//                FailedMsg("Throwable : " + t);
//            }
//
//            private void FailedMsg(String s) {
//                AlertDialog.Builder alertBuild = new AlertDialog.Builder(getActivity());
//                alertBuild.setTitle("Gagal memuat")
//                        .setMessage(s)
//                        .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                AlertDialog alert = alertBuild.create();
//                alert.show();
//            }
        });
    }
}