package hackeru.edu.sqlitenotes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

    }

    private ArrayList<Note> readFromDB() {
        NotesDBHelper helper = new NotesDBHelper(this /*context*/);
        SQLiteDatabase db = helper.getReadableDatabase();
        //db.query("notes", new String[]{"title", "content"}, null, null, null, null, "title ASC");
        Cursor cursor = db.query("notes", null, null, null, null, null, null);

        ArrayList<Note> notes = new ArrayList<>();


        if (cursor == null || !cursor.moveToFirst()) {
            Toast.makeText(this, "No Notes Yet...", Toast.LENGTH_SHORT).show();
            return notes;
        }

        do {
            int id = cursor.getInt(cursor.getColumnIndex("_ID"));

            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));

            notes.add(new Note(id, title, content));
        } while (cursor.moveToNext());

        return notes;
    }

    private void addNote() {
        AddNoteFragment noteFragment = new AddNoteFragment();
        noteFragment.show(getSupportFragmentManager(), "dialog");

        readFromDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
