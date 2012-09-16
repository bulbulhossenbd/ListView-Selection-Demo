package com.alexfu.listviewselection;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.alexfu.listviewselection.MainActivityAdapter.ChoiceMode;

public class MainActivity extends SherlockListActivity implements AdapterView.OnItemLongClickListener {
	private MainActionMode mActionMode;
	private ListView mListView;
	private MainActivityAdapter mListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = getListView();
        mListAdapter = new MainActivityAdapter(this, R.layout.activity_main_row, getResources().getStringArray(R.array.data));
        
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemLongClickListener(this);
    }

	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		if(mActionMode == null) {
			mActionMode = new MainActionMode();
		}
				
		mListAdapter.setChoiceMode(ChoiceMode.SINGLE); // Set the desired choice mode.
		mListAdapter.setItemChecked(position, true); // Set the current position as checked.
		
		startActionMode(mActionMode);
		return true;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if(mActionMode != null) { // Contextual ActionBar is showing.
			boolean currentState = mListAdapter.getCheckedItemPositions().get(position);
			mListAdapter.setItemChecked(position, !currentState); // Toggle states.
		}
	}

	private final class MainActionMode implements ActionMode.Callback {

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch(item.getItemId()) {
			case R.id.info:
				SparseBooleanArray checked = mListAdapter.getCheckedItemPositions();
				for(int i = 0, max = checked.size(); i < max; i ++) {
					Toast.makeText(MainActivity.this, mListAdapter.getItem(checked.keyAt(i)).toString(), Toast.LENGTH_SHORT).show();
				}
				break;
			}
			mode.finish();
			return true;
		}

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			getSupportMenuInflater().inflate(R.menu.activity_main, menu);
			mode.setTitle(R.string.action_mode_title);
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
			mListAdapter.setChoiceMode(ChoiceMode.NONE);
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}		
	}
}