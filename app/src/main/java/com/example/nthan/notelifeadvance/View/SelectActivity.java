package com.example.nthan.notelifeadvance.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nthan.notelifeadvance.Adapter.NoteAdapter;
import com.example.nthan.notelifeadvance.Adapter.NoteAdapterSelect;
import com.example.nthan.notelifeadvance.Model.Note;
import com.example.nthan.notelifeadvance.Presenter.NotePresenter;
import com.example.nthan.notelifeadvance.R;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity implements NoteAdapterSelect.RecyclerViewClickListener{

    private List<Note> listNote;
    private List<Boolean> listIDNoteDelete;
    private Toolbar toolbar;
    private NotePresenter notePresenter;
    private RecyclerView recyclerView;
    private NoteAdapterSelect noteAdapterSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__select);
        notePresenter = new NotePresenter(this);
        listNote = notePresenter.getAllNote();
        initListIDNoteDelete();

        toolbar = (Toolbar)findViewById(R.id.toolbar_activity_select);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initRecycleView();
    }

    public void initRecycleView(){

        recyclerView = (RecyclerView)findViewById(R.id.recycleview_activity_select);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                staggeredGridLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        noteAdapterSelect = new NoteAdapterSelect(listNote, getApplicationContext(), this);
        recyclerView.setAdapter(noteAdapterSelect);
    }

    public void initListIDNoteDelete(){

        listIDNoteDelete = new ArrayList<>();
        for(int i = 0; i < listNote.size(); i++){
            listIDNoteDelete.add(false);
        }
    }

    public void deleteListNote(List<Boolean> listIDNoteDelete){
        List<String> listID = new ArrayList<>();
        if(!listIDNoteDelete.isEmpty()){
            for(int i = 0; i <listIDNoteDelete.size(); i++){

                if(listIDNoteDelete.get(i).equals(true)){
                    listID.add(String.valueOf(listNote.get(i).getId()));
                }
            }
        }
        notePresenter.deleteNote(listID);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_activity_select_note, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_select_note:
                deleteListNote(this.listIDNoteDelete);
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View view, int position) {
        if(listIDNoteDelete.get(position).equals(false)){
            listIDNoteDelete.set(position, true);
        }
        else{
            listIDNoteDelete.set(position, false);
        }
    }
}
