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

/**
 * Created by jvocal on 2017-01-09.
 */

public class GpaForTargetCgpaFragment extends Fragment{

    private static final double MAX_GPA = 4.33;

    private static final String CURRENT_CGPA_ERROR_STRING = "currentCGPA";
    private static final String TOTAL_UNITS_ATTEMPTED_ERROR_STRING = "totalUnitsAttempted";
    private static final String TARGET_CGPA_ERROR_STRING = "targetCGPA";
    private static final String REMAINING_UNITS_ERROR_STRING = "remainingUnits";
    private static final String ALL_INPUTS_ARE_VALID_STRING = "allGood";

    private double currentCGPA;
    private double totalUnitsAttempted;
    private double targetCGPA;
    private double remainingUnits;

    private void resetAllInputValues() {

        EditText editTextCurrentCGPA = (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text_2);
        editTextCurrentCGPA.setText("");

        EditText editTextTotalUnitsAttempted =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text_2);
        editTextTotalUnitsAttempted.setText("");

        EditText editTextTargetCGPA =
                (EditText) getActivity().findViewById(R.id.target_cgpa_edit_text);
        editTextTargetCGPA.setText("");

        EditText editTextRemainingUnits =
                (EditText) getActivity().findViewById(R.id.remaining_units_edit_text);
        editTextRemainingUnits.setText("");

        Toast.makeText(getActivity(), R.string.resetMessage, Toast.LENGTH_SHORT).show();
    }

    private String areAllInputsValid() {

        if (!isCurrentGPAValid()) {
            return CURRENT_CGPA_ERROR_STRING;

        } else if (!isTotalUnitsAttemptedValid()) {
            return TOTAL_UNITS_ATTEMPTED_ERROR_STRING;

        } else if (!isTargetCGPAValid()) {
            return TARGET_CGPA_ERROR_STRING;

        } else if (!isRemainingUnitsValid()) {
            return REMAINING_UNITS_ERROR_STRING;

        } else {
            return ALL_INPUTS_ARE_VALID_STRING;
        }
    }

    private void displayMessage(String message) {

        Toast myToast = Toast.makeText(getActivity(), null, Toast.LENGTH_SHORT);

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

            case REMAINING_UNITS_ERROR_STRING:
                myToast.setText(R.string.remainingUnitsError);
                myToast.show();
                break;

            case ALL_INPUTS_ARE_VALID_STRING:
                setCurrentGPA();
                setTotalUnitsAttempted();
                setTargetCGPA();
                setRemainingUnits();

                double gpaToMaintain = getGPAForTargetCGPA(getCurrentGPA(),
                        getTotalUnitsAttempted(), getTargetCGPA(), getRemainingUnits());

                if (isMaintainedGpaPossible(gpaToMaintain)) {
                    showGpaDialogMessage(gpaToMaintain);
                } else {
                    myToast.setText(R.string.gpaNotPossibleError);
                    myToast.show();
                }

                break;
        }
    }

    private void setCurrentGPA() {

        EditText editTextCurrentCGPA = (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text_2);
        currentCGPA = Double.parseDouble(editTextCurrentCGPA.getText().toString());
    }

    private double getCurrentGPA() {

        return currentCGPA;
    }

    private boolean isCurrentGPAValid() {

        EditText editTextCurrentCGPA = (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text_2);
        String currentCGPAString = editTextCurrentCGPA.getText().toString();

        if (currentCGPAString.equals("") || currentCGPAString.equals(".")) {
            return false;
        }

        double currentCGPA = Double.parseDouble(currentCGPAString);

        if (currentCGPA > MAX_GPA || currentCGPA < 0) {
            return false;
        }

        return true;
    }

    private void setTotalUnitsAttempted() {

        EditText editTextTotalUnitsAttempted =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text_2);
        totalUnitsAttempted = Double.parseDouble(editTextTotalUnitsAttempted.getText().toString());
    }

    private double getTotalUnitsAttempted() {

        return totalUnitsAttempted;
    }

    private boolean isTotalUnitsAttemptedValid() {

        EditText editTextTotalUnitsAttempted =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text_2);
        String totalUnitsAttemptedString = editTextTotalUnitsAttempted.getText().toString();

        if (totalUnitsAttemptedString.equals("") || totalUnitsAttemptedString.equals(".")) {
            return false;
        }

        return true;
    }

    private void setTargetCGPA() {

        EditText editTextTargetCGPA =
                (EditText) getActivity().findViewById(R.id.target_cgpa_edit_text);
        targetCGPA = Double.parseDouble(editTextTargetCGPA.getText().toString());
    }

    private double getTargetCGPA() {

        return targetCGPA;
    }

    private boolean isTargetCGPAValid() {

        EditText editTextTargetCGPA =
                (EditText) getActivity().findViewById(R.id.target_cgpa_edit_text);
        String targetCGPAString = editTextTargetCGPA.getText().toString();

        if (targetCGPAString.equals("") || targetCGPAString.equals(".")) {
            return false;
        }

        return true;
    }

    private void setRemainingUnits() {

        EditText editTextRemainingUnits =
                (EditText) getActivity().findViewById(R.id.remaining_units_edit_text);
        remainingUnits = Double.parseDouble(editTextRemainingUnits.getText().toString());
    }

    private double getRemainingUnits() {

        return remainingUnits;
    }

    private boolean isRemainingUnitsValid() {

        EditText editTextRemainingUnits =
                (EditText) getActivity().findViewById(R.id.remaining_units_edit_text);
        String remainingUnitsString = editTextRemainingUnits.getText().toString();

        if (remainingUnitsString.equals("") || remainingUnitsString.equals(".")) {
            return false;
        }

        return true;
    }

    private void showGpaDialogMessage(double maintainedGPA) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String messageFormat = getResources().getString(R.string.gpaForTargetMessage);
        String message = String.format(messageFormat, maintainedGPA);

        builder.setMessage(message)
                .setTitle(R.string.gpaForTargetTitle)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private double getGPAForTargetCGPA(double currentCGPA, double totalUnitsAttempted,
                                       double targetCGPA, double remainingUnits) {

        this.currentCGPA = currentCGPA;
        this.totalUnitsAttempted = totalUnitsAttempted;
        this.targetCGPA = targetCGPA;
        this.remainingUnits = remainingUnits;

        double maintainedGPA;

        maintainedGPA = ((targetCGPA * (totalUnitsAttempted + remainingUnits)) -
                (currentCGPA * totalUnitsAttempted)) / (remainingUnits);

        maintainedGPA = (double) Math.round(maintainedGPA * 1000D) / 1000D;

        return maintainedGPA;
    }

    private boolean isMaintainedGpaPossible(double maintainedGpa) {

        return (maintainedGpa <= MAX_GPA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gpa_for_target_cgpa, container, false);

        Button calculateButton = (Button) rootView.findViewById(R.id.calculate_button_2);
        calculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                displayMessage(areAllInputsValid());
            }
        });

        Button resetButton = (Button) rootView.findViewById(R.id.reset_button_2);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetAllInputValues();
            }
        });

        return rootView;

    }
}
