# Android Persistent Search Library

A library that implements the persistent search bar seen on apps such as Google Now, Google Maps and Google Play

![GIF of its use](https://imgur.com/PUmdeqe.gif)


## Changes


1. Now result filtering will be trigerd by changes in ```ArrayList<SearchResult> searchables``` rather then by TextWatcher(). So now you can implement live search using any async requests.
2. Filtering has been removed, just set results you want to show
```
protected void onPostExecute(ArrayList<String> result) {

	ArrayList<SearchResult> results = new ArrayList<SearchResult>();
	for(String item : result){
		SearchResult option = new SearchResult(item, getResources().getDrawable(R.drawable.ic_history));
		results.add(option);
	}

	search.addAllResults(results);

	search.showLoading(false);
}
```

3. Removed ```MaterialMenuView ``` instead regular drawable is used.
4. You can set up how many results you want to show to user ```public void setSearchResultLimit(int searchResultLimit)```
5. You can setup which kind of input you want ```public void setInputType(int type)```
6. Fixed problem with double clicking on back button
7. Show specific message to user in case if there is no results after search.
8. You can add value to each SearchRequest
9. You can set any SearchRequest as non clickable.

## Usage

Android Studio:

You will have to clone whole project and import it as a library into your project.

In your layout:
```
<com.quinny898.library.persistentsearch.SearchBox
        android:layout_width="wrap_content"
		android:layout_height="wrap_content"
        android:id="@+id/searchbox"
        />
```
Please include this after any elements you wish to be hidden by it in a releativelayout.

**Absolute requirements in the activity code**

In your onCreate/onCreateView (activity or fragment):
```
search.enableVoiceRecognition(this);
```
And in the same class:
```
@Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (isAdded() && requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == getActivity().RESULT_OK) {
      ArrayList<String> matches = data
          .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      search.populateEditText(matches);
    }
    super.onActivityResult(requestCode, resultCode, data);
}
```

More on implementation:
```
search = (SearchBox) findViewById(R.id.searchbox);
for(int x = 0; x < 10; x++){
	SearchResult option = new SearchResult("Result " + Integer.toString(x), getResources().getDrawable(R.drawable.ic_history));
	search.addSearchable(option);
}		
search.setLogoText("My App");
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
	public void onSearchTermChanged() {
		//React to the search term changing
		//Called after it has updated results
	}

	@Override
	public void onSearch(String searchTerm) {
		Toast.makeText(MainActivity.this, searchTerm +" Searched", Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	public void onResultClick(SearchResult result){
		//React to a result being clicked
	}
	
	
	@Override
	public void onSearchCleared() {
				
	}
			
});
```

##Showing from a MenuItem
```
search.revealFromMenuItem(R.id.action_search, this);
```
Note that when a search occurs, the box closes. You should react to this in onSearch, maybe set your toolbar title?

## Custom
Set the logo text color:
```
search.setLogoTextColor(Color.parse("#000000"));
```

## SearchResult
This is a class that holds two parameters - Title and icon<br />
The title is displayed as a suggested result and will be used for searching, the icon is displayed to the left of the title in the suggestions (eg. a history icon)<br />
You can make a SearchResult as follows<br />
```
new SearchResult("Title", getResources().getDrawable(R.drawable.icon));
```

## All usage methods
See here for the documentation: http://quinny898.co.uk/PersistentSearch/

## Licence
Copyright 2015 Kieron Quinn<br />
<br />
Licensed under the Apache License, Version 2.0 (the "License");<br />
you may not use this file except in compliance with the License.<br />
You may obtain a copy of the License at<br />
<br />
   http://www.apache.org/licenses/LICENSE-2.0<br />
<br />
Unless required by applicable law or agreed to in writing, software<br />
distributed under the License is distributed on an "AS IS" BASIS,<br />
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br />
See the License for the specific language governing permissions and<br />
limitations under the License.
