package senac.ebookstore;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import senac.ebookstore.models.Ebook;

public class EbookActivity extends AppCompatActivity {

    TextView txtIsbn, txtTitulo, txtAutor, txtTipo, txtSinopse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtIsbn = findViewById(R.id.txtISBN);
        txtTipo = findViewById(R.id.txtTipo);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtAutor = findViewById(R.id.txtAutor);
        txtSinopse = findViewById(R.id.txtSinopse);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String isbn = txtIsbn.getText().toString();
                String titulo = txtTitulo.getText().toString();
                String autor = txtAutor.getText().toString();
                String tipo = txtTipo.getText().toString();
                String sinopse = txtSinopse.getText().toString();

                Ebook ebook = new Ebook(isbn, null, titulo, autor, sinopse, tipo,null);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("ebook-" + ebook.getIsbn());

                myRef.setValue(ebook);

                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
