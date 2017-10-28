package com.example.nthan.notelifeadvance.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nthan.notelifeadvance.Adapter.NoteAdapter;
import com.example.nthan.notelifeadvance.Model.Note;
import com.example.nthan.notelifeadvance.Presenter.NotePresenter;
import com.example.nthan.notelifeadvance.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NoteAdapter.RecyclerViewClickListener,
        NoteAdapter.RecyclerViewLongClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private SearchView searchView;
    private List<Note> listNote;
    private NotePresenter notePresenter;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        floatingActionButton = (FloatingActionButton) findViewById(
                R.id.floattingbutton_add_note_actiivity_main);
        notePresenter = new NotePresenter(this);
        listNote = notePresenter.getAllNote();

        toolbar = (Toolbar) findViewById(R.id.toobar_activity_main);
        setSupportActionBar(toolbar);

        initRecycleView(listNote);

        // EVENT CLICK FLOATTING_BUTTON_ADD_NOTE
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), EditActivity.class));
            }
        });
        //------------------------------------------------------------------------------------------

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.searchview_toolbar_activity_main:
                return true;
            case R.id.sortby_toolbar_Activity_main:
                displayAlertDialog();
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_activity_main, menu);

        MenuItem searchItem = menu.findItem(R.id.searchview_toolbar_activity_main);
        searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        // Configure the search info and add any event listeners...

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    initRecycleView(listNote);
                }

                List<Note> list = searchTextInData(newText);
                initRecycleView(list);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void initRecycleView(List<Note> list) {

        recyclerView = (RecyclerView) findViewById(R.id.recycleview_activity_main);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                staggeredGridLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        noteAdapter = new NoteAdapter(list, getApplicationContext(), this, this);
        recyclerView.setAdapter(noteAdapter);
    }

    //RecycleView click
    @Override
    public void onClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("idNote", listNote.get(position).getId());
        Intent intent = new Intent(getApplication(), EditActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

        startActivity(new Intent(this, SelectActivity.class));
    }

    public List<Note> searchTextInData(String input) {
        List<Note> list = new ArrayList<>();
        for (int i = 0; i < listNote.size(); i++) {
            if (listNote.get(i).getTitle().indexOf(input) != -1
                    || listNote.get(i).getContent().indexOf(input) != -1) {
                list.add(listNote.get(i));
            }
        }
        return list;
    }

    public void displayAlertDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View alertLayout = layoutInflater.inflate(R.layout.dialog_sortby, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Sort By");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        final RadioGroup radioGroup = (RadioGroup)alertLayout.findViewById(R.id.radiobutton_sortby_diglog);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                Map<String, Long> mapCreate = new ArrayMap<String, Long>();
                Map<String, Long> mapUpdate = new ArrayMap<String, Long>();
                for(int i = 0; i < listNote.size(); i++){

                    mapCreate.put(String.valueOf(listNote.get(i).getId()),
                            convertDateToLongTime(listNote.get(i).getDate_create()));
                    mapUpdate.put(String.valueOf(listNote.get(i).getId()),
                            convertDateToLongTime(listNote.get(i).getDate_Update_last()));
                }

                switch (checkedId){
                    case R.id.sortby_datecreate_decrement_toolbar_Activity_main:
                        List<Map.Entry<String, Long>> list = sortDate(mapCreate);
                        List<Note> listNoteSort = new ArrayList<Note>();
                        for(Map.Entry<String, Long> entry: list){
                            listNoteSort.add(notePresenter.getNoteByID(Integer.parseInt(entry.getKey())));
                        }
                        initRecycleView(listNoteSort);
                        Toast.makeText(MainActivity.this, String.valueOf(convertDateToLongTime(
                                listNote.get(0).getDate_create())), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sortby_datecreate_increment_toolbar_Activity_main:

                        Toast.makeText(MainActivity.this, String.valueOf(convertDateToLongTime(
                                listNote.get(0).getDate_Update_last())), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sortby_dateupdate_decrement_toolbar_Activity_main:
                        break;
                    case R.id.sortby_dateupdate_increment_toolbar_Activity_main:
                        break;
                }
            }
        });

//        alert.setNegativeButton("Cancel", null);
//        alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        AlertDialog dig = alert.create();
        dig.show();
    }

    public long convertDateToLongTime(String date){
        long time = -1;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date _date = sdf.parse(date);
            time = _date.getTime();
        }
        catch (ParseException p){p.printStackTrace();}
        return time;
    }

    public  List<Map.Entry<String, Long>> sortDate(Map<String, Long> date){
        Map<String, Long> dateSort = date;
        Set<Map.Entry<String, Long>> set = dateSort.entrySet();
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        return list;
    }
    @Override
    public void onBackPressed() {
        // your code.
        this.finish();
    }
}
