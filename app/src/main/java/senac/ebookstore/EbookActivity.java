package senac.ebookstore;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import senac.ebookstore.models.Ebook;

public class EbookActivity extends AppCompatActivity {

    TextView txtIsbn, txtTitulo, txtAutor, txtSinopse;
    Spinner spinnerTipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtIsbn = findViewById(R.id.txtISBN);
        spinnerTipos = findViewById(R.id.spinnerTipos);
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
                String tipo = (String) spinnerTipos.getSelectedItem();
                String sinopse = txtSinopse.getText().toString();

                if (isbn.isEmpty()) {
                    txtIsbn.setError("Campo obrigatório!");
                    txtIsbn.requestFocus();
                    return;
                }
                if (titulo.isEmpty()) {
                    txtTitulo.setError("Título do Livro obrigatório!");
                    txtTitulo.requestFocus();
                    return;
                }
                if (autor.isEmpty()) {
                    autor = "Autor desconhecido";
                }
                if (sinopse.isEmpty()) {
                    sinopse = "Sem descrição...";
                }


                Ebook ebook = new Ebook(isbn, null, titulo, autor, sinopse, tipo, null);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("ebooks").child(ebook.getIsbn());

                myRef.setValue(ebook);

                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
