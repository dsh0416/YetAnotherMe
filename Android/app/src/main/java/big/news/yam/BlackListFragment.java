package big.news.yam;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;


public class BlackListFragment extends Fragment {

    public TextView text;
    public FloatingActionButton fab;


    public BlackListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_black_list, container, false);

        text = (TextView) v.findViewById(R.id.text);
        fab = (FloatingActionButton) v.findViewById(R.id.add_fab);

        final LayoutInflater inner_inflater = inflater;
        final View inner_v = v;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View layout = inner_inflater.inflate(R.layout.dialog,(ViewGroup) inner_v.findViewById(R.id.dialog));
                final EditText name = (EditText) layout.findViewById(R.id.phoneNumber);
                final Switch sw = (Switch) layout.findViewById(R.id.switcher);

                new AlertDialog.Builder(getActivity())
                        .setTitle("添加名单")
                        .setIcon(R.mipmap.ic_launcher)
                        .setView(layout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                text.setText(text.getText().toString() + (sw.isChecked()?"黑名单":"白名单") + ": " + name.getText().toString() + "\n");
                            }
                        })
                .show();

            }
        });

        return v;
    }


}
