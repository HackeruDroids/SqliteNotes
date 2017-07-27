package hackeru.edu.sqlitenotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvNotes;
    private NotesAdapter adapter;

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


        rvNotes = (RecyclerView) findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Note> notes = readFromDB();
        adapter = new NotesAdapter(this, notes);
        rvNotes.setAdapter(adapter);
    }

    //listener to events:
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            reloadRecycler();
        }
    };

    //register the receiver:
    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.
                getInstance(this).
                registerReceiver(receiver, new IntentFilter("noteAdded"));
    }
    //stop listening to the event.
    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void reloadRecycler() {
        ArrayList<Note> notes = readFromDB();
        adapter.notes = notes;

        adapter.notifyDataSetChanged();
        rvNotes.scrollToPosition(notes.size() - 1);


    }

    private ArrayList<Note> readFromDB() {
        NotesDBHelper helper = new NotesDBHelper(this /*activity*/);
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
        AddNoteFragment noteFragment =
                AddNoteFragment.newInstance(AddNoteFragment.ACTION_INSERT, null);

        noteFragment.show(getSupportFragmentManager(), "dialog");
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

    static class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
        //properties:
        AppCompatActivity activity;
        LayoutInflater inflater;
        ArrayList<Note> notes;

        //constructor:
        public NotesAdapter(AppCompatActivity activity, ArrayList<Note> notes) {
            this.activity = activity;
            this.notes = notes;
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.note_item, parent, false);
            return new NotesViewHolder(v);
        }

        @Override
        public void onBindViewHolder(NotesViewHolder h, int position) {
            Note model = notes.get(position);
            h.tvTitle.setText(model.getTitle());
            h.tvContent.setText(model.getContent());
            h.model = model;
            h.activity = activity;
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        static final class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvTitle, tvContent;
            AppCompatActivity activity;
            Note model;

            NotesViewHolder(View v) {
                super(v);
                tvTitle = v.findViewById(R.id.tvTitle);
                tvContent = v.findViewById(R.id.tvContent);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                AddNoteFragment noteFragment =
                        AddNoteFragment.newInstance(AddNoteFragment.ACTION_UPDATE ,model);

                noteFragment.show(activity.getSupportFragmentManager(), "dialog");
            }
        }
    }
}
