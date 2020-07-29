package br.com.faj.project.donationapp;

import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtil {

    private View snackView;

    public SnackbarUtil(View snackView) {
        this.snackView = snackView;
    }

    public void showError(Exception e) {
        Snackbar.make(snackView, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    public void showError(String e) {
        Snackbar.make(snackView, e, BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}
