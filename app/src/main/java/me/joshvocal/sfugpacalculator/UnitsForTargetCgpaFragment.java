package me.joshvocal.sfugpacalculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * Created by jvocal on 2017-01-09.
 */

public class UnitsForTargetCgpaFragment extends Fragment{

    private static final double MAX_GPA = 4.33;

    private static final String CURRENT_CGPA_ERROR_STRING = "currentCGPA";
    private static final String TOTAL_UNITS_ATTEMPTED_ERROR_STRING = "totalUnitsAttempted";
    private static final String TARGET_CGPA_ERROR_STRING = "targetCGPA";
    private static final String TARGET_GPA_FOR_EACH_TERM = "targetGPAForEachTerm";
    private static final String ALL_INPUTS_ARE_VALID_STRING = "allGood";

    private double currentCGPA;
    private double totalUnitsAttempted;
    private double targetCGPA;
    private double targetGPAForEachTerm;

    private void setCurrentGPA() {

        EditText editTextCurrentCGPA = (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text_3);
        currentCGPA = Double.parseDouble(editTextCurrentCGPA.getText().toString());
    }

    private double getCurrentGPA() {

        return currentCGPA;
    }

    private boolean isCurrentGPAValid() {

        EditText editTextCurrentCGPA = (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text_3);
        String currentCgpaString = editTextCurrentCGPA.getText().toString();

        if (currentCgpaString.equals("") || currentCgpaString.equals(".")) {
            return false;
        }

        double currentCgpa = Double.parseDouble(currentCgpaString);

        return (currentCgpa <= MAX_GPA && currentCgpa > 0);
    }

    private void setTotalUnitsAttempted() {

        EditText totalUnitsAttemptedEditText =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text_3);
        totalUnitsAttempted = Double.parseDouble(totalUnitsAttemptedEditText.getText().toString());
    }

    private double getTotalUnitsAttempted() {

        return totalUnitsAttempted;
    }

    private boolean isTotalUnitsAttemptedValid() {

        EditText totalUnitsAttemptedEditText =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text_3);
        String totalUnitsAttemptedString = totalUnitsAttemptedEditText.getText().toString();

        return !totalUnitsAttemptedString.equals("") || !totalUnitsAttemptedString.equals(".");
    }

    private void setTargetCGPA() {

        EditText targetCgpaEditText =
                (EditText) getActivity().findViewById(R.id.target_cgpa_edit_text_3);
        targetCGPA = Double.parseDouble(targetCgpaEditText.getText().toString());
    }

    private double getTargetCGPA() {

        return targetCGPA;
    }

    private boolean isTargetCGPAValid() {

        EditText targetCgpaEditText =
                (EditText) getActivity().findViewById(R.id.target_cgpa_edit_text_3);
        String targetCgpaString = targetCgpaEditText.getText().toString();

        if (targetCgpaString.equals("") || targetCgpaEditText.equals(".")) {
            return false;
        }

        double targetCgpa = Double.parseDouble(targetCgpaString);

        return (targetCgpa <= MAX_GPA && targetCgpa > 0);
    }

    private void setTargetGpaForEachTerm() {

        EditText setTargetGpaForEachTermEditText =
                (EditText) getActivity().findViewById(R.id.target_gpa_each_term_edit_text);
        targetGPAForEachTerm =
                Double.parseDouble(setTargetGpaForEachTermEditText.getText().toString());
    }

    private double getTargetGpaForEachTerm() {

        return targetGPAForEachTerm;
    }

    private boolean isTargetGpaForEachTermValid() {

        EditText targetGpaForEachTermEditText =
                (EditText) getActivity().findViewById(R.id.target_gpa_each_term_edit_text);
        String targetGpaForEachTermString = targetGpaForEachTermEditText.getText().toString();

        if (targetGpaForEachTermString.equals("") || targetGpaForEachTermString.equals(".")) {
            return false;
        }

        double targetGpaForEachTerm = Double.parseDouble(targetGpaForEachTermString);

        return (targetGpaForEachTerm <= MAX_GPA && targetGpaForEachTerm > 0);
    }


    private double getRequiredUnitsForTargetGpa(double currentCGPA, double totalUnitsAttempted,
                                                double targetCGPA, double targetGPAForEachTerm) {

        this.currentCGPA = currentCGPA;
        this.totalUnitsAttempted = totalUnitsAttempted;
        this.targetCGPA = targetCGPA;
        this.targetGPAForEachTerm = targetGPAForEachTerm;

        double requiredUnitsForTargetGpa;

        requiredUnitsForTargetGpa = ((currentCGPA * totalUnitsAttempted) - (targetCGPA * totalUnitsAttempted)) /
                (targetCGPA - targetGPAForEachTerm);
        requiredUnitsForTargetGpa = Math.ceil(requiredUnitsForTargetGpa);

        return requiredUnitsForTargetGpa;
    }

    private void showDialogMessage(double requiredUnitsForTargetGPA, double targetGpa,
                                   double targetGpaForEachTerm) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String messageFormat = getResources().getString(R.string.unitsForTargetMessage);
        String message = String.format(messageFormat, requiredUnitsForTargetGPA,
                targetGpa, targetGpaForEachTerm);

        builder.setMessage(message)
                .setTitle(R.string.unitsForTargetTitle)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private String areAllInputsValid() {

        if (!isCurrentGPAValid()) {
            return CURRENT_CGPA_ERROR_STRING;

        } else if (!isTotalUnitsAttemptedValid()) {
            return TOTAL_UNITS_ATTEMPTED_ERROR_STRING;

        } else if (!isTargetCGPAValid()) {
            return TARGET_CGPA_ERROR_STRING;

        } else if (!isTargetGpaForEachTermValid()) {
            return TARGET_GPA_FOR_EACH_TERM;

        } else {
            return ALL_INPUTS_ARE_VALID_STRING;
        }
    }

    private void displayMessage(String message) {

        Toast myToast = makeText(getActivity(), null, Toast.LENGTH_SHORT);

        switch (message) {
            case CURRENT_CGPA_ERROR_STRING:
                myToast.setText(R.string.currentCgpaError);
                myToast.show();
                break;

            case TOTAL_UNITS_ATTEMPTED_ERROR_STRING:
                myToast.setText(R.string.totalUnitsAttemptedError);
                myToast.show();
                break;

            case TARGET_CGPA_ERROR_STRING:
                myToast.setText(R.string.targetCgpaError);
                myToast.show();
                break;

            case TARGET_GPA_FOR_EACH_TERM:
                myToast.setText(R.string.targetGpaForEachTermError);
                myToast.show();
                break;

            case ALL_INPUTS_ARE_VALID_STRING:
                setCurrentGPA();
                setTotalUnitsAttempted();
                setTargetCGPA();
                setTargetGpaForEachTerm();

                double requiredUnitsForTargetGPA = getRequiredUnitsForTargetGpa(getCurrentGPA(),
                        getTotalUnitsAttempted(), getTargetCGPA(), getTargetGpaForEachTerm());

                if (areRequiredUnitsForTargetGpaValid(requiredUnitsForTargetGPA)) {
                    showDialogMessage(requiredUnitsForTargetGPA, getTargetCGPA(),
                            getTargetGpaForEachTerm());
                } else {
                    myToast.setText(R.string.unitsForTargetCgpaError);
                    myToast.show();
                }
        }
    }

    private boolean areRequiredUnitsForTargetGpaValid(double requiredUnitsForTargetGPA) {

        if (requiredUnitsForTargetGPA >= 0) {
            return true;
        } else {
            return false;
        }
    }

    private void resetAllInputValues() {

        EditText editTextCurrentCGPA = (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text_3);
        editTextCurrentCGPA.setText("");

        EditText totalUnitsAttemptedEditText =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text_3);
        totalUnitsAttemptedEditText.setText("");


        EditText targetCgpaEditText =
                (EditText) getActivity().findViewById(R.id.target_cgpa_edit_text_3);
        targetCgpaEditText.setText("");

        EditText targetGpaForEachTermEditText =
                (EditText) getActivity().findViewById(R.id.target_gpa_each_term_edit_text);
        targetGpaForEachTermEditText.setText("");

        makeText(getActivity(), R.string.resetMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_units_for_target_cgpa, container, false);

        Button calculateButton = (Button) rootView.findViewById(R.id.calculate_button_3);
        calculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayMessage(areAllInputsValid());
            }
        });

        Button resetButton = (Button) rootView.findViewById(R.id.reset_button_3);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetAllInputValues();
            }
        });

        return rootView;
    }
}
