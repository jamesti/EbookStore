package senac.ebookstore.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import senac.ebookstore.R;
import senac.ebookstore.models.Ebook;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    private List<Ebook> ebookList;
    private Context context;
    public View.OnClickListener mOnItemClickListener;

    public EbookAdapter(List<Ebook> ebookList, Context context) {
        this.ebookList = ebookList;
        this.context = context;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ebook, parent, false);

        EbookViewHolder holder = new EbookViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {
        final Ebook ebook = ebookList.get(position);

        holder.imgEbook.setImageResource(R.drawable.common_google_signin_btn_icon_dark_normal);
        holder.txtTitulo.setText(ebook.getTitulo());
        holder.txtTipo.setText(ebook.getTipo());
        holder.txtAutor.setText(ebook.getAutor());
        holder.txtSinopse.setText(ebook.getSinopse());

        /*
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder
                        .setMessage("Deseja excluir este registro?")
                        .setPositiveButton("Sim",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.financeDB.delete(finance.getId());
                                removerItem(position);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return ebookList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    private void removerItem(int position) {
        ebookList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ebookList.size());
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgEbook;
        final TextView txtTitulo, txtAutor, txtTipo, txtSinopse;

        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);

            imgEbook = itemView.findViewById(R.id.imgEbook);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtAutor = itemView.findViewById(R.id.txtAutor);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            txtSinopse = itemView.findViewById(R.id.txtSinopse);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

}
