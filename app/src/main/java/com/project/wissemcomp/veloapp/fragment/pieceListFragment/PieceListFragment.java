package com.project.wissemcomp.veloapp.fragment.pieceListFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.project.wissemcomp.veloapp.MainActivity;
import com.project.wissemcomp.veloapp.Utility;
import com.project.wissemcomp.veloapp.adapter.AdapterPiece;
import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.model.Picture;
import com.project.wissemcomp.veloapp.R;
import com.project.wissemcomp.veloapp.mvp.presenter.PieceListPresenterImpl;
import com.project.wissemcomp.veloapp.mvp.view.PieceListMvpView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PieceListFragment extends Fragment implements PieceListMvpView, RecyclerPieceItemClickListener, View.OnClickListener {

    @BindView(R.id.recycler_view_piece)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar_piece)
    ProgressBar progressBar;

    private PieceListPresenterImpl picturePresenter;
    RecyclerView.Adapter adapter;

    private GridLayoutManager gridLayoutManager;
    private View rootView;

    public static PieceListFragment newInstance() {
        return new PieceListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_piece, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_piece_list, container, false);
        ButterKnife.bind(this, rootView);

        picturePresenter = new PieceListPresenterImpl(getContext());
        picturePresenter.attachedView(this);
        setupRecyclerView();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.piece_list_fragment));
        ((MainActivity)getActivity()).updateStatusBarColor(R.color.statusbar_piece, R.color.actionbar_piece);
        picturePresenter.onResume();
    }

    @Override
    public void setItems(ArrayList<Bike> pictureList) {
        adapter = new AdapterPiece(pictureList, R.layout.item_piece_list);
        recyclerView.setAdapter(adapter);
        ((AdapterPiece) adapter).setRecyclerPieceItemClickListener(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListPiece(int bike_id) {
        Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        picturePresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onPieceItemClickListener(int position) {
        picturePresenter.onItemSelected(position);
    }

    private void setupRecyclerView() {

        if (gridLayoutManager == null) {
            int mNoOfColumns = Utility.calculateNoOfColumns(getContext());
            gridLayoutManager = new GridLayoutManager(
                    getActivity(),
                    2,
                    GridLayoutManager.VERTICAL,
                    false);

            recyclerView.setLayoutManager(gridLayoutManager);
        } else
            recyclerView.setLayoutManager(gridLayoutManager);

        // recyclerView.addItemDecoration(new ItemOffsetDecoration(recyclerView.getContext(), R.dimen.item_decoration));
        // recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            case R.id.action_add:
                showDialogPieceSelection();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialogPieceSelection() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .title("Ajouter un type de pièce")
                .customView(R.layout.custom_view_piece_select, false)
                .positiveText("Confirmer")
                .negativeText("Annuler")
                .build();

        final Spinner sp = (Spinner) materialDialog.getCustomView().findViewById(R.id.spinner_pieces_type);

        materialDialog.getBuilder().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                Toast.makeText(getContext(), sp.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        materialDialog.show();

    }

    @Override
    public void onClick(View v) {

    }


}