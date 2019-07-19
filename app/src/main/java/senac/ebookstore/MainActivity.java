package senac.ebookstore;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import java.util.List;

import senac.ebookstore.adapters.EbookAdapter;
import senac.ebookstore.models.Ebook;
import senac.ebookstore.models.EbookFirebaseHelper;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private static List<Ebook> ebookList;

    private RecyclerView recyclerView;

    private EbookFirebaseHelper ebookFirebaseHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                    = new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            //mTextMessage.setText(R.string.title_home);
                            return true;
                        case R.id.navigation_romances:
                            //mTextMessage.setText(R.string.title_dashboard);
                            return true;
                        case R.id.navigation_negocios:
                            //mTextMessage.setText(R.string.title_notifications);
                            return true;
                        case R.id.navigation_tecnicos:

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
        DatabaseReference myRef = database.getReference("ebooks");

        ebookFirebaseHelper = new EbookFirebaseHelper(myRef);

        //ebookList = ebookFirebaseHelper.retrieve();

        recyclerView = findViewById(R.id.listEbooks);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        EbookAdapter adapter = new EbookAdapter(ebookFirebaseHelper.retrieve(), this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
}
