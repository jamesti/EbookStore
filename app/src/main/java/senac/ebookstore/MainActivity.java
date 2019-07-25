package senac.ebookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import senac.ebookstore.adapters.EbookAdapter;
import senac.ebookstore.models.Ebook;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private RecyclerView recyclerView;
    EbookAdapter adapter;

    static DatabaseReference myRef;

    private List<Ebook> ebookList = new ArrayList<>();

    ProgressDialog progressDialog;

    private static boolean FIREBASE_OFFLINE;

    private ValueEventListener ListenerGeral = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ebookList.clear();

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Ebook ebook = ds.getValue(Ebook.class);
                ebookList.add(ebook);
            }

            adapter = new EbookAdapter(ebookList, MainActivity.this);

            recyclerView.setAdapter(adapter);

            progressDialog.dismiss();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    myRef.limitToFirst(100).addValueEventListener(ListenerGeral);
                    return true;
                case R.id.navigation_romances:
                    myRef.limitToFirst(100).orderByChild("tipo").equalTo("Romances").addValueEventListener(ListenerGeral);
                    return true;
                case R.id.navigation_negocios:
                    myRef.limitToFirst(100).orderByChild("tipo").equalTo("Negócios").addValueEventListener(ListenerGeral);
                    return true;
                case R.id.navigation_tecnicos:
                    myRef.limitToFirst(100).orderByChild("tipo").equalTo("Técnicos").addValueEventListener(ListenerGeral);
                    return true;
                case R.id.navigation_ebook:
                    Intent intent = new Intent(getBaseContext(), EbookActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        if (!FIREBASE_OFFLINE) {
            database.setPersistenceEnabled(true);
            FIREBASE_OFFLINE = true;
        }

        myRef = database.getReference("ebooks");
        myRef.keepSynced(true);

        recyclerView = findViewById(R.id.listEbooks);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Carregando...");

        progressDialog.show();

        myRef.limitToFirst(100).addValueEventListener(ListenerGeral);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

}
