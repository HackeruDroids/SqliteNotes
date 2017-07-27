package hackeru.edu.sqlitenotes;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNoteFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final int ACTION_INSERT = 1;
    public static final int ACTION_DELETE = 2;
    public static final int ACTION_UPDATE = 3;


    EditText etNoteTitle, etNoteContent;

    public static AddNoteFragment newInstance(int action, Note note) {
        Bundle args = new Bundle();
        args.putInt("action", action);
        args.putParcelable("note", note);

        AddNoteFragment fragment = new AddNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_note, container, false);

        etNoteTitle = v.findViewById(R.id.etTitle);
        etNoteContent = v.findViewById(R.id.etContent);
        Button btnSaveNote = v.findViewById(R.id.btnSaveNote);
        btnSaveNote.setOnClickListener(this);

        int action = getArguments().getInt("action");
        if (action == ACTION_UPDATE) {
            Note note = getArguments().getParcelable("note");
            if (note != null) {
                etNoteContent.setText(note.getContent());
                etNoteTitle.setText(note.getTitle());
            }
        }
        return v;
    }

    @Override
    public void onClick(View view) {
        String title = etNoteTitle.getText().toString();
        String content = etNoteContent.getText().toString();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(getContext(), "Must fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        //save the text to the database.
        NotesDBHelper helper = new NotesDBHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        //Prone to SQL INJECTION.
        //db.execSQL("INSERT INTO Notes(content, title) VALUES('" + content + "', '" + title + "');");

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);


        int action = getArguments().getInt("action");
        if (action == ACTION_INSERT) {
            db.insert("Notes", null, values);
        } else if (action == ACTION_UPDATE) {
            Note note = getArguments().getParcelable("note");
            if (note != null)
                db.update("notes", values, "_ID = ?", new String[]{note.getId() + ""});
        }

        //notify the listeners:
        LocalBroadcastManager.
                getInstance(getContext()).
                sendBroadcast(new Intent("noteAdded"));

        dismiss();
    }
}
