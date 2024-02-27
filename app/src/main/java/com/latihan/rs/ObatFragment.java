package com.latihan.rs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObatFragment extends Fragment {

    LocalStorage localStorage;
    List<ModelObat> modelObatList;
    Context context;
    RecyclerView recyclerView;
    AdapterObat adapterObat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ObatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObatFragment newInstance(String param1, String param2) {
        ObatFragment fragment = new ObatFragment();
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
        return inflater.inflate(R.layout.fragment_obat, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localStorage = new LocalStorage(getContext());

        String token = localStorage.getData();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer" + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        ObatService obatService = ApiClient.getRetrofit().create(ObatService.class);

        Call<List<ModelObat>> call = obatService.getObatList();
        call.enqueue(new Callback<List<ModelObat>>() {
            @Override
            public void onResponse(Call<List<ModelObat>> call, retrofit2.Response<List<ModelObat>> response) {
                if (response.isSuccessful()) {
                    List<ModelObat> obatList = response.body();

                    recyclerView = view.findViewById(R.id.rv_obat);
                    adapterObat = new AdapterObat(getContext(), obatList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapterObat);
                } else {
                    try {
                        System.out.println("Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelObat>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error Koneksi berikut : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }
}