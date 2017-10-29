package com.example.nthan.notelifeadvance.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nthan.notelifeadvance.Model.Note;
import com.example.nthan.notelifeadvance.Presenter.NotePresenter;
import com.example.nthan.notelifeadvance.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    public static final int CREATE_NOTE = 100;
    public static final int UPDATE_NOTE = 101;
    private NotePresenter notePresenter;

    private Toolbar toolbar;
    private Spinner spinner;
    private int type = this.CREATE_NOTE;

    private int idNoteUpdate;
    private String color = Note.NOTE_COLOR_PURPLE;
    private EditText editTextTitle;
    private EditText editTextContent;

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        toolbar = (Toolbar)findViewById(R.id.toolbar_activity_edit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        editTextTitle = (EditText)findViewById(R.id.edittext_title_editactivity);
        editTextContent = (EditText)findViewById(R.id.edittext_content_editactivity);
        notePresenter = new NotePresenter(this);

        //------------------------------------------------------------------------------------------
        Bundle bundle = this.getIntent().getExtras();
        // Update note
        if(bundle != null){
            type = this.UPDATE_NOTE;
            Note note = notePresenter.getNoteByID(bundle.getInt("idNote"));
            this.idNoteUpdate = note.getId();
            setNoteForScreen(note);
        }
        else{
            editTextTitle.setBackgroundColor(Color.parseColor(color));
            editTextContent.setBackgroundColor(Color.parseColor(color));
            toolbar.setBackgroundColor(Color.parseColor(color));
        }
        //------------------------------------------------------------------------------------------
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_activity_edit, menu);

         MenuItem shareItem = menu.findItem(R.id.share_note_editactivity);
        ShareActionProvider myShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("text/plain");
        myShareIntent.putExtra(Intent.EXTRA_TEXT, editTextTitle.getText().toString() +
                "\n" +
                editTextContent.getText().toString());
        myShareActionProvider.setShareIntent(myShareIntent);
        //startActivity(Intent.createChooser(myShareIntent, "Share via"));
        return super.onCreateOptionsMenu(menu);
    }

    public void shareNote(String note){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, note);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Choose sharing method"));
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.color_blue:
                this.color = Note.NOTE_COLOR_BLUE;
                this.setColorForScreen(this.color);
                return true;
            case R.id.color_green:
                this.color = Note.NOTE_COLOR_GREEN;
                this.setColorForScreen(this.color);
                return true;
            case R.id.color_purple:
                this.color = Note.NOTE_COLOR_PURPLE;
                this.setColorForScreen(this.color);
                return true;
            case R.id.color_pink:
                this.color = Note.NOTE_COLOR_PINK;
                this.setColorForScreen(this.color);
                return true;
            case R.id.color_red:
                this.color = Note.NOTE_COLOR_RED;
                this.setColorForScreen(this.color);
                return true;
            case R.id.save_activity_edit:
                if(type == EditActivity.CREATE_NOTE){
                    saveNote();
                }
                if(type == EditActivity.UPDATE_NOTE){
                    updateNote();
                }
                this.type = EditActivity.CREATE_NOTE;
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case android.R.id.home:
                //Toast.makeText(this, "backHome", Toast.LENGTH_SHORT).show();
                handlingHomeBack();
                return true;
            case R.id.share_note_editactivity:

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    //Save note
    public void saveNote(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        String color = this.color;
        String noteCreate = dateFormat.format(date);
        String noteUpdate = "Not update yet";
        Note note =  new Note(title, content, noteCreate, noteUpdate, color);
        this.notePresenter.addNote(note);

    }

    // Update note
    public void updateNote(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Note note = notePresenter.getNoteByID(this.idNoteUpdate);
        String noteUpdate = dateFormat.format(date);
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        note.setTitle(title);
        note.setContent(content);
        note.setDate_Update_last(noteUpdate);
        note.setColor(this.color);
        this.notePresenter.updateNote(note);
    }

    public void handlingHomeBack(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to save?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // save Or Update Note
                        if(type == EditActivity.CREATE_NOTE){
                            saveNote();
                        }
                        else{
                            updateNote();
                        }
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        startActivity(new Intent(getApplication(), MainActivity.class));
                    }
                });

        builder.show();
    }



    @Override
    public void onBackPressed() {
        // your code.
        handlingHomeBack();
    }
    public void setNoteForScreen(Note note){

        this.color = note.getColor();
        editTextTitle.setText(note.getTitle());
        editTextContent.setText(note.getContent());

        editTextTitle.setBackgroundColor(Color.parseColor(color));
        editTextContent.setBackgroundColor(Color.parseColor(color));
        toolbar.setBackgroundColor(Color.parseColor(color));
    }
    public void setColorForScreen(String codeColor){
        editTextTitle.setBackgroundColor(Color.parseColor(codeColor));
        editTextContent.setBackgroundColor(Color.parseColor(codeColor));
        toolbar.setBackgroundColor(Color.parseColor(codeColor));
    }
}
