package br.com.faj.project.donationapp;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class EditTextUtil {
    public static boolean validateEditText(TextInputLayout inputLayout, EditText editText) {
        boolean isValid = true;
        if (editText.getText().toString().trim().isEmpty()) {
            inputLayout.setError("Campo não pode estar vazio.");
            isValid = false;
        } else {
            inputLayout.setError(null);
        }
        return isValid;
    }

    public static boolean validateNumber(TextInputLayout inputLayout, EditText editText){
        boolean isValid = true;
        if (editText.getText().toString().trim().isEmpty()) {
            inputLayout.setError("Inválido.");
            isValid = false;
        } else {
            inputLayout.setError(null);
        }
        return isValid;
    }

    public static boolean validatePassword(TextInputLayout til1, EditText et1, TextInputLayout til2, EditText et2) {
        boolean isValid = true;

        if (!validateEditText(til1, et1) | !validateEditText(til2, et2)) {
            return false; // cant be empty
        }

        String password1 = et1.getText().toString();
        String password2 = et2.getText().toString();

        if (!password1.equals(password2)) {
            til2.setError("As senhas não são iguais.");
            til1.setError("As senhas não são iguais.");
            isValid = false;
        } else {
            til1.setError(null);
            til2.setError(null);
        }

        return isValid;
    }
}
