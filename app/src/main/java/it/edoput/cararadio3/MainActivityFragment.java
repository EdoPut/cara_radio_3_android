package it.edoput.cararadio3;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends Fragment {

    // the fragment list view to show messages
    private ListView listView;

    // an adapter
    // private CustomAdapter mAdapter;

    //
    // private ArrayList<Message> messageList = new ArrayList<Message>();

    // hold the swiperefreshlayout reference
    // must implement `setOnRefreshListener` to listen
    // for swipe event
    private SwipeRefreshLayout refreshLayout;

    // Fragment constructor
    public MainActivityFragment() {
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // set global reference to fragment view
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // set global reference to swipeRefreshLayout
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        // TODO: move listener on its own class
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
//                new LoadMessageTask().execute();
            }
        });

        // set global reference to ListView in fragment
        listView = (ListView) view.findViewById(R.id.list);

        // return reference to fragment view
        return view;
    }

}

// implement async task to get text messages from the internet
// class LoadMessageTask extends AsyncTask<Void, Void, String> {
//     String uristring = "http://www.radio3.rai.it/radio3sms/smsradiotre.xml";
//     @Override
//     protected String doInBackground (Void ... params) {
//         try {
//             // receive xml
//             // publish to ui with publishProgress
//             loadXmlFromNetwork(uristring);
//         } catch (IOException ioexception) {
//             return getResources().getString(R.string.connection_error);
//         } catch (XmlPullParserException xmlexception) {
//             return getResources().getString(R.string.xml_error);
//         }
//         return null;
//     }

//     private String loadXmlFromNetwork(String uristring) throws XmlPullParserException, IOException {
//         InputStream in = null;
//         TextMessageXmlParser textMessageXmlParser = new TextMessageXmlParser();
//         List<TextMessage> messsages = null;

//     }

//     private InputStream downloadUrl(String uristring) throws IOException {
//         URL url = new URL(uristring);
//         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//         conn.setReadTimeout(10000;
//         conn.setConnectTimeout(15000;
//         conn.setRequestMethod("GET");
//         conn.setDoInput(true);
//         conn.connect();
//         return conn.getInputStream();
//  }

//     private void onPostExecute() {
//     }

// }

// class TextMessageXmlParser {
//     // namespace not setted, required for following xml tags
//     private static final String ns = null;

//     public List parse(InputStream in) throws XmlPullParserException, IOException{
//         try {
//             XmlPullParser parser = Xml.newPullParser();
//             parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//             parser.setInput(in, null);
//             parser.nextTag();
//             return readFeed(parser);
//         }
//         finally {
//             in.close();
//         }
//     }

//     private List readFeed (XmlPullParser parser) throws XmlPullParserException, IOException {
//         List entries = new ArrayList();

//         parser.require(XmlPullParser.START_TAG, ns, "smsradiotre");
//         while(parser.next() != XmlPullParser.END_TAG) {
//             if (parser.getEventType() != XmlPullParser.START_TAG) {
//                 continue;
//             }

//             String name = parser.getName();
//             if (name.equals("sms")) {
//                 entries.add(readTextMessage(parser));
//             } else {
//                 skip(parser);
//             }
//         }

//         return entries;
//     }

//     private TextMessage readTextMessage(XmlPullParser parser) throws XmlPullParserException, IOException {
//         parser.require(XmlPullParser.START_TAG, ns, "sms");
//         String message = null;
//         while (parser.next() != XmlPullParser.END_TAG) {
//             if (parser.getEventType() != XmlPullParser.START_TAG) {
//                 continue;
//             }
//             String name = parser.getName();
//             switch (name) {
//                 case "msgtesto":
//                     message = readMessage(parser);
//                     break;
//             }
//         }
//         return new TextMessage(message);
//     }

//     private String readMessage (XmlPullParser parser) throws XmlPullParserException, IOException {
//         parser.require(XmlPullParser.START_TAG, ns, "msgtesto");
//         String result = "";
//         if (parser.next() == XmlPullParser.TEXT) {
//             result = parser.getText();
//             parser.nextTag();
//         }
//         return result;
//     }

//     private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
//         if (parser.getEventType() != XmlPullParser.START_TAG) {
//             throw new IllegalStateException();
//         }
//         int depth = 1;

//         while (depth != 0) {
//             switch (parser.next()) {
//                 case XmlPullParser.END_TAG:
//                     depth--;
//                     break;
//                 case XmlPullParser.START_TAG:
//                     depth++;
//                     break;
//             }
//         }
//     }
// }

// class TextMessage {
//     public final String message;

//     private TextMessage(String message) {
//         this.message = message;
//     }
// }

