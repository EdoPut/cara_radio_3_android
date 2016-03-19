package it.edoput.cararadio3;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends Fragment {

    // the fragment list view to show messages
    private ListView listView;

    // a subclass of ListAdapter
    private CustomAdapter mAdapter;

    //
    private ArrayList<TextMessage> messageList = new ArrayList<TextMessage>();

    // hold the swiperefreshlayout reference
    private SwipeRefreshLayout refreshLayout;

    // Fragment constructor
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                messageList.clear();
                new LoadMessageTask().execute();
            }
        });

        new LoadMessageTask().execute();

        mAdapter = new CustomAdapter(getActivity(), messageList);

        // set global reference to ListView in fragment
        listView = (ListView) view.findViewById(R.id.list);
        View emptyView = view.findViewById(R.id.empty);

        listView.setAdapter(mAdapter);
        listView.setEmptyView(emptyView);

        // return reference to fragment view
        return view;
    }

    // implement async task to get text messages from the internet
    private class LoadMessageTask extends AsyncTask<Void, Void, List<TextMessage>> {
        String uristring = "http://www.radio3.rai.it/radio3sms/smsradiotre.xml";
        InputStream response;
        TextMessageList textMessageList;
        @Override
        protected List<TextMessage> doInBackground (Void ... params) {
            try {
                // receive xml
                response = downloadUrl(uristring);
                Serializer serializer = new Persister();
                textMessageList = serializer.read(TextMessageList.class, response);

            } catch (IOException ioexception) {
                Log.e("SimpleXML", "Error reading response", ioexception);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return textMessageList.getSms();
        }

        private InputStream downloadUrl(String uristring) throws IOException {
            URL url = new URL(uristring);
            // the connection used to request data from the net
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            return conn.getInputStream();
        }

        @Override
        protected void onPostExecute(List<TextMessage> textMessages) {
            messageList = (ArrayList<TextMessage>) textMessages;
            mAdapter = new CustomAdapter(getActivity(), messageList);
            listView.setAdapter(mAdapter);
            refreshLayout.setRefreshing(false);
        }
    }

}

class CustomAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<TextMessage> list;

    public CustomAdapter (Activity activity, List<TextMessage> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_row, null);
        }

        TextView msgtesto = (TextView) convertView.findViewById(R.id.msgtesto);
        TextView msgdata  = (TextView) convertView.findViewById(R.id.msgdata);
        TextView msgora   = (TextView) convertView.findViewById(R.id.msgora);

        TextMessage message = list.get(position);
        msgtesto.setText(message.getMsgtesto());
        msgdata.setText(message.getMsgdata());
        msgora.setText(message.getMsgora());

        return convertView;

    }
}



/*
The XML tree is shaped like this

<smsradiotre>
    <sms>
        <msgdata></msgdata>
        <msgora></msgora>
        <programma></programma>
        <msgtesto></msgtesto>
    </sms>
</smsradiotre>
*/

@Root(name = "smsradiotre")
class TextMessageList {
    @ElementList(inline = true)
    private List<TextMessage> sms;

    public List<TextMessage> getSms() {
        return this.sms;
    }
}
@Root(name = "sms")
class TextMessage {
    @Element(name = "msgdata")
    public String msgdata;

    @Element(name = "msgora")
    public String msgora;

    @Element(name = "programma")
    public String programma;

    @Element(name = "msgtesto")
    public String msgtesto;

    public String getMsgdata() {
        return msgdata;
    }

    public String getMsgora() {
        return msgora;
    }

    public String getMsgtesto() {
        return msgtesto;
    }
}


