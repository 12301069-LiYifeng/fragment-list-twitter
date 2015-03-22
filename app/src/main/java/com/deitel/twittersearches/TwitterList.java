package com.deitel.twittersearches;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/*Class for twitter list*/
public class TwitterList extends ListFragment{
	
	private ArrayList<String> twitters;//save name of twitters
	private OnFragmentInteractionListener mListener;
	
	public TwitterList(ArrayList<String> twitters){
		this.twitters = twitters;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
	public View onCreateView(LayoutInflater inflater,ViewGroup container,
			Bundle savedInstanceState){
		
		ArrayAdapter<String> list = new ArrayAdapter<String>(
				getActivity(),android.R.layout.simple_list_item_1,twitters);
		
		setListAdapter(list);

		return inflater.inflate(R.layout.fragment_list, container,false);
	}
	
	/*Provide options for long click,include share,delete and edit*/
	public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                final String tag = twitters.get(position);
                final String url = getString(R.string.searchURL) +
                        Uri.encode(MainActivity.savedSearches.getString(tag, ""), "UTF-8");

                // create a new AlertDialog
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(TwitterList.this.getActivity());

                // set the AlertDialog's title
                builder.setTitle(
                        getString(R.string.shareEditDeleteTitle, tag));

                // set list of items to display in dialog
                builder.setItems(R.array.dialog_items,
                        new DialogInterface.OnClickListener() {
                            // responds to user touch by sharing, editing or
                            // deleting a saved search
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.sendTagToMain(tag,which);
                            }
                        } // end DialogInterface.OnClickListener
                ); // end call to builder.setItems

                // set the AlertDialog's negative Button
                builder.setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            // called when the "Cancel" Button is clicked
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel(); // dismiss the AlertDialog
                            }
                        }
                ); // end call to setNegativeButton

                builder.create().show(); // display the AlertDialog
                return true;
            }
        };

        getListView().setOnItemLongClickListener(listener);

    }
	
	/*Provide options for click:visit website*/
	public void onListItemClick(ListView l,View v,int position,long id){
		if(mListener!=null){
			mListener.sendWebToWebFragment(getString(R.string.searchURL) +
		            Uri.encode(MainActivity.savedSearches.getString(twitters.get(position), ""), 
		            "UTF-8"),twitters.get(position));
		}
	}
	
	public void onAttach(Activity activity){
		
		super.onAttach(activity);
		
		try{
			mListener = (OnFragmentInteractionListener)activity;
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString());
		}
		
	}
	
	public void onDetach(){
		super.onDetach();
		mListener = null;
	}
	
	
	public interface OnFragmentInteractionListener{
		public void sendWebToWebFragment(String url,String tag);
		public void sendTagToMain(String tag,int which);
	}
}
