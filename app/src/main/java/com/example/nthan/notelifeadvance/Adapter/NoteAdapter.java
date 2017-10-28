package com.example.nthan.notelifeadvance.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nthan.notelifeadvance.Model.Note;
import com.example.nthan.notelifeadvance.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nthan on 10/13/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<Note> listNote;
    Context context;

    private RecyclerViewClickListener mListener;
    private RecyclerViewLongClickListener mLongClickListener;

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

    public interface RecyclerViewLongClickListener{
        void onLongClick(View view, int position);
    }

    public NoteAdapter(List<Note> listNote, Context context,
                       RecyclerViewClickListener recyclerViewClickListener,
                       RecyclerViewLongClickListener recyclerViewLongClickListener) {
        this.mListener = recyclerViewClickListener;
        this.mLongClickListener = recyclerViewLongClickListener;
        this.listNote = listNote;
        this.context = context;
    }

    public void removeItem(int position){
        listNote.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_recycleview_activity_main, parent,false);
        return new ViewHolder(itemView, mListener, mLongClickListener);
}

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewTitle.setText(listNote.get(position).getTitle());
        holder.textViewContent.setText(listNote.get(position).getContent());
        holder.textViewDateCreate.setText(listNote.get(position).getDate_create());
        holder.textViewDateUpdateLast.setText(listNote.get(position).getDate_Update_last());

        holder.textViewTitle.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));
        holder.textViewContent.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));
        holder.textViewDateCreate.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));
        holder.textViewDateUpdateLast.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));

    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{

        private RecyclerViewClickListener mListener;
        private RecyclerViewLongClickListener mLongClickListenner;
        TextView textViewTitle;
        TextView textViewContent;
        TextView textViewDateCreate;
        TextView textViewDateUpdateLast;

        public ViewHolder(View itemView, RecyclerViewClickListener listener,
                          RecyclerViewLongClickListener recyclerViewLongClickListener) {
            super(itemView);
            textViewTitle = (TextView)itemView.findViewById(R.id.textview_title_recycleview_acitivity);
            textViewContent = (TextView)itemView.findViewById(R.id.textview_content_recycleview_activity);
            textViewDateCreate = (TextView)itemView.findViewById(R.id.textview_datecreate_recycleview_activity);
            textViewDateUpdateLast = (TextView)itemView.findViewById(R.id.textview_datelastupdate_recycleview_activity);
            mListener = listener;
            mLongClickListener = recyclerViewLongClickListener;
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());


        }

        @Override
        public boolean onLongClick(View v) {
            mLongClickListener.onLongClick(v, getAdapterPosition());
            //removeItem(v, getAdapterPosition());
            return true;
        }

        public void removeItem(View view, final int position){
            final AlertDialog.Builder builder =
                    new AlertDialog.Builder(view.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            builder.setTitle("Message");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listNote.remove(position);
                    notifyItemRemoved(position);
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }
}
