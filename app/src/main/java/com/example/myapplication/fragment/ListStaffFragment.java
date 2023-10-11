package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapter.UserAdapter;
import com.example.myapplication.R;
import com.example.myapplication.User.User;
import com.google.android.material.bottomnavigation.BottomNavigationView; // Import BottomNavigationView
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ListStaffFragment extends Fragment {

    private Button btnAdd;
    private View view;
    private RecyclerView recyclerView;
    private UserAdapter mUserAdapter;
    private List<User> mlistUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_staff, container, false);
        addControls();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thay thế fragment hiện tại bằng AddUserFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frContent, new AddUserFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                if (getActivity() != null) {
                    BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
                    if (bottomNavigationView != null) {
                        bottomNavigationView.setSelectedItemId(R.id.bot_addUser);
                    }
                }
            }
        });

        return view;
    }

    private void addControls() {
        recyclerView = view.findViewById(R.id.rcyView);
        btnAdd = view.findViewById(R.id.btnAdd);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        mlistUser = new ArrayList<>();
        mUserAdapter = new UserAdapter(mlistUser);
        recyclerView.setAdapter(mUserAdapter);

        // Lấy và hiển thị danh sách người dùng từ Firebase
        getListUser();
    }

    private void getListUser() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("User_Staff");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    mlistUser.add(user);
                    mUserAdapter.notifyItemInserted(mlistUser.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Xử lý sự thay đổi dữ liệu
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Xử lý xóa dữ liệu
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Xử lý di chuyển dữ liệu
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Lỗi khi truy xuất danh sách người dùng: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
