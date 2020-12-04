package it.unito.ium_android.ui.prenota;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import it.unito.ium_android.R;
import it.unito.ium_android.requests.Requests;

public class PrenotaFragment extends Fragment {

    private PrenotaViewModel prenotaViewModel;
    private String materia = "";
    private String docente = "";
    private int firstTimeSpinner = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        prenotaViewModel =
                new ViewModelProvider(this).get(PrenotaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_prenota, container, false);
        RelativeLayout loadingLayout = (RelativeLayout) root.findViewById(R.id.loadingPanel);
        makeRequests(root);

        Spinner spinnerDocenti = (Spinner) root.findViewById(R.id.seleziona_docente);
        Spinner spinnerMaterie = (Spinner) root.findViewById(R.id.seleziona_materia);
        spinnerDocenti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeSpinner < 2) {
                    firstTimeSpinner++;
                    return;
                }
                loadingLayout.setVisibility(View.VISIBLE);
                docente = ((Integer) view.getTag()).toString();
                if (docente.equals("0"))
                    docente = "";

                Requests requests = new Requests(getActivity(), "lessons", root);
                try {
                    String data = "course=" + URLEncoder.encode(materia, "UTF-8") + "&teacherId=" + URLEncoder.encode(docente, "UTF-8") + "&action=lessons";
                    String url = "http://10.0.2.2:8080/ProgettoTWEB_war_exploded/Controller";
                    String method = "POST";
                    requests.execute(data, url, method);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerMaterie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeSpinner < 2) {
                    firstTimeSpinner++;
                    return;
                }
                loadingLayout.setVisibility(View.VISIBLE);
                materia = ((TextView) view).getText().toString();
                if (materia.equals("Seleziona Materia"))
                    materia = "";

                Requests requests = new Requests(getActivity(), "lessons", root);
                try {
                    String data = "course=" + URLEncoder.encode(materia, "UTF-8") + "&teacherId=" + URLEncoder.encode(docente, "UTF-8") + "&action=lessons";
                    String url = "http://10.0.2.2:8080/ProgettoTWEB_war_exploded/Controller";
                    String method = "POST";
                    requests.execute(data, url, method);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SwipeRefreshLayout refreshPanel = root.findViewById(R.id.refreshPanel);
        refreshPanel.setOnRefreshListener(() -> {
            makeRequests(root);
            refreshPanel.setRefreshing(false);
        });

        return root;
    }

    private void makeRequests(View root)
    {
        Requests requests = new Requests(getActivity(), "docenti", root);

        String data = "action=docenti";
        String url = "http://10.0.2.2:8080/ProgettoTWEB_war_exploded/Controller";
        String method = "GET";
            requests.execute(data, url, method);

        requests = new Requests(getActivity(), "materie", root);

        data = "action=materie";
        url = "http://10.0.2.2:8080/ProgettoTWEB_war_exploded/Controller";
        method = "GET";

            requests.execute(data, url, method);


        requests = new Requests(getActivity(), "lessons", root);
        try {
            data = "course=" + URLEncoder.encode(materia, "UTF-8") + "&teacherId=" + URLEncoder.encode(docente, "UTF-8") + "&action=lessons";
            url = "http://10.0.2.2:8080/ProgettoTWEB_war_exploded/Controller";
            method = "POST";
            requests.execute(data, url, method);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        firstTimeSpinner = 0;
    }
}