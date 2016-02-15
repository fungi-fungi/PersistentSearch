package com.quinny898.library.persistentsearch.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchBox.MenuListener;
import com.quinny898.library.persistentsearch.SearchBox.SearchListener;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;

public class MainActivity extends Activity {
	Boolean isSearch;
	private SearchBox search;
	private LongOperation mTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);



		search.setMenuListener(new MenuListener(){

			@Override
			public void onMenuClick() {
				//Hamburger has been clicked
				Toast.makeText(MainActivity.this, "Menu click", Toast.LENGTH_LONG).show();				
			}
			
		});
		search.setSearchListener(new SearchListener(){

			@Override
			public void onSearchOpened() {
				//Use this to tint the screen
			}

			@Override
			public void onSearchClosed() {
				//Use this to un-tint the screen
			}

			@Override
			public void onSearchTermChanged(String term) {
				//React to the search term changing
				//Called after it has updated results
				if (mTask != null){
					mTask.cancel(true);
				}
				mTask = (LongOperation) new LongOperation().execute(term);
			}

			@Override
			public void onSearch(String searchTerm) {
				Toast.makeText(MainActivity.this, searchTerm +" Searched", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onResultClick(SearchResult result) {
				//React to a result being clicked
			}

			@Override
			public void onSearchCleared() {
				//Called when the clear button is clicked
			}
			
		});
        search.setOverflowMenu(R.menu.overflow_menu);
        search.setOverflowMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.test_menu_item:
						Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
						return true;
				}
				return false;
			}
		});
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1234 && resultCode == RESULT_OK) {
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			search.populateEditText(matches.get(0));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void reveal(View v){
		startActivity(new Intent(this, RevealActivity.class));
	}

	private class LongOperation extends AsyncTask<String, Void, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(String... params) {

			ArrayList<String> results = new ArrayList<String>();

			try {
				Thread.sleep(1000);
				ArrayList<String> list = createTestList();

				for(String item : list){
					if (item.toLowerCase().matches("(?i).*" + params[0].toLowerCase() + ".*")){
						results.add(item);
					}
				}
			} catch (InterruptedException e) {
				Thread.interrupted();
			}

			return results;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {

			ArrayList<SearchResult> results = new ArrayList<SearchResult>();
			for(String item : result){
				SearchResult option = new SearchResult(item, getResources().getDrawable(R.drawable.ic_history));
				results.add(option);
			}

			search.addAllResults(results);

			search.showLoading(false);
		}

		@Override
		protected void onPreExecute() {
			search.showLoading(true);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			search.showLoading(true);
		}
	}

	private ArrayList<String> createTestList(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("Test 1");
		list.add("Result 23");
		list.add("Hello world 167");
		list.add("How are you 12");
		list.add("New test");
		list.add("3345435");
		list.add("What is you name");
		list.add("Just one more string");
		list.add("Good mornign");
		list.add("Res 12");
		list.add("API res 11");
		list.add("API res 23");

		return list;
	}

	
}
