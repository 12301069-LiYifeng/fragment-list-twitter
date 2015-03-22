package com.deitel.twittersearches;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/*Class for displaying website*/
public class WebFragment extends Fragment{
	
	private static String ARG_PARAM1 = "url";
	private static String ARG_PARAM2 = "tag";
	private String url;
	private String tag;
	
	public WebFragment(){
		
	}
	
	public static WebFragment newInstance(String url,String tag){
		
		WebFragment fragment = new WebFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, url);
		args.putString(ARG_PARAM2, tag);
		fragment.setArguments(args);
		
		return fragment;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
        	url = getArguments().getString(ARG_PARAM1);
        	tag = getArguments().getString(ARG_PARAM2);
		}
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View webView = inflater.inflate(R.layout.fragment_web,container,false);
    	
    	TextView textView = (TextView) webView.findViewById(R.id.webFargmentTextView);
    	
    	textView.setText(tag);
    	
    	WebView webViewShow = (WebView) webView.findViewById(R.id.webFargmentWebView);
    		
    	/*visit website*/
    	webViewShow.getSettings().setJavaScriptEnabled(true);
    	webViewShow.loadUrl(url);
    	
    	webViewShow.setWebViewClient(new WebViewClient(){
    		
    		@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            } 
    	});
    	return webView; 	
    }

}
