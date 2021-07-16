package com.aisoft.ticketmaster;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<Uploads> uploadsList;
    private OnItemClickListener listener;

    public MyAdapter(Context context, List<Uploads> uploadsList) {
        this.context = context;
        this.uploadsList = uploadsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_layout,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Uploads uploads = uploadsList.get(i);
        myViewHolder.textView.setText(uploads.getImageName());
        Picasso.with(context)
                .load(uploads.getImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .into(myViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return uploadsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.cardTextViewId);
            imageView = itemView.findViewById(R.id.cardImageViewId);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {

            if (listener!=null)
            {
                int position = getAbsoluteAdapterPosition();

                if (position!=RecyclerView.NO_POSITION)
                {
                    listener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Choose an action");
            MenuItem doAnyTask = menu.add(Menu.NONE,1,1,"do any task");
            MenuItem delete = menu.add(Menu.NONE,2,2,"delete");
            doAnyTask.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (listener!=null)
            {
                int position = getAbsoluteAdapterPosition();

                if (position!=RecyclerView.NO_POSITION)
                {
                    switch (item.getItemId())
                    {
                        case 1:
                            listener.onDoAnyTask(position);
                            return true;

                        case 2:
                            listener.onDelete(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDoAnyTask(int position);
        void onDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;
    }
}
