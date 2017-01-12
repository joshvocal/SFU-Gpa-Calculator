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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static me.joshvocal.sfugpacalculator.R.layout.course_item_footer;
import static me.joshvocal.sfugpacalculator.R.layout.course_item_header;

/**
 * Created by jvocal on 2017-01-09.
 */

public class CgpaCalculatorFragment extends Fragment{

    private static final String CURRENT_CGPA_ERROR_STRING = "currentCGPA";
    private static final String TOTAL_UNITS_ATTEMPTED_ERROR_STRING = "totalUnitsAttempted";
    private static final String SPINNER_VALUES_ERROR_STRING = "spinnerValues";
    private static final String ALL_INPUTS_ARE_VALID_STRING = "allGood";

    private static final double MAX_GPA = 4.33;
    private static final int COURSE_LIMIT_NUMBER = 8;

    private static final double A_PLUS_GRADE_VALUE = 4.33;
    private static final double A_GRADE_VALUE = 4.00;
    private static final double A_MINUS_GRADE_VALUE = 3.66;

    private static final double B_PLUS_GRADE_VALUE = 3.33;
    private static final double B_GRADE_VALUE = 3.00;
    private static final double B_MINUS_GRADE_VALUE = 2.66;

    private static final double C_PLUS_GRADE_VALUE = 2.33;
    private static final double C_GRADE_VALUE = 2.00;
    private static final double C_MINUS_GRADE_VALUE = 1.66;

    private static final double D_GRADE_VALUE = 1.00;
    private static final double F_GRADE_VALUE = 0.00;

    ArrayList<Course> courses = new ArrayList<>();
    private ListView listView;

    private double currentCgpa;
    private double totalUnitsAttempted;

    private void setCurrentCgpa() {

        EditText currentCcgpaEditText =
                (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text);
        currentCgpa = Double.parseDouble(currentCcgpaEditText.getText().toString());
    }

    private double getCurrentCgpa() {

        return currentCgpa;
    }

    private boolean isCurrentCgpaValid() {

        EditText currentCgpaEditText =
                (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text);
        String currentCgpaString = currentCgpaEditText.getText().toString();

        if (currentCgpaString.equals("") || currentCgpaString.equals(".")) {
            return false;
        }

        double currentCgpa = Double.parseDouble(currentCgpaString);

        if (currentCgpa > MAX_GPA || currentCgpa < 0) {
            return false;
        }

        return true;
    }

    private void setTotalUnitsAttempted() {

        EditText totalUnitsAttemptedEditText =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text);
        totalUnitsAttempted = Double.parseDouble(totalUnitsAttemptedEditText.getText().toString());
    }

    private double getTotalUnitsAttempted() {

        return totalUnitsAttempted;
    }

    private boolean areTotalUnitsAttemptedValid() {

        EditText totalUnitsAttemptedEditText =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text);
        String totalUnitsAttemptedString = totalUnitsAttemptedEditText.getText().toString();

        if (totalUnitsAttemptedString.equals("") || totalUnitsAttemptedString.equals(".")) {
            return false;
        }

        return true;
    }

    private double getTotalUnits(List<Double> unitsListThisSemester) {

        double totalUnits = 0.00;

        for (double unit : unitsListThisSemester) {
            totalUnits += unit;
        }

        return totalUnits;
    }

    private double getGpaTimesTotalUnitsAdded(List<Double> gpaListThisSemester,
                                              List<Double> unitsListThisSemester) {

        double unitsTimesGpaAdded = 0.00;

        for (int index = 0; index < gpaListThisSemester.size(); index++) {
            unitsTimesGpaAdded += gpaListThisSemester.get(index) * unitsListThisSemester.get(index);
        }

        return unitsTimesGpaAdded;
    }

    private double getGpaCurrentTerm(List<Double> gpaListThisSemester, List<Double> unitsListThisSemester) {

        double gpaCurrentTerm;

        double unitsTimesGpaAdded = getGpaTimesTotalUnitsAdded(gpaListThisSemester, unitsListThisSemester);
        double totalUnits = getTotalUnits(unitsListThisSemester);

        gpaCurrentTerm = (unitsTimesGpaAdded) / totalUnits;

        gpaCurrentTerm = (double) Math.round(gpaCurrentTerm * 1000D) / 1000D;

        return gpaCurrentTerm;
    }

    private double getCgpaAllTerms(double currentCgpa, double totalUnitsAttempted, List<Double> gpaListThisSemester,
                                   List<Double> unitsListThisSemester) {

        this.currentCgpa = currentCgpa;
        this.totalUnitsAttempted = totalUnitsAttempted;

        double totalUnits = getTotalUnits(unitsListThisSemester);
        double unitsTimesGpaAdded =
                getGpaTimesTotalUnitsAdded(gpaListThisSemester, unitsListThisSemester);

        double cgpaAllTerms;

        cgpaAllTerms = (unitsTimesGpaAdded + currentCgpa * totalUnitsAttempted) /
                (totalUnits + totalUnitsAttempted);

        cgpaAllTerms = (double) Math.round(cgpaAllTerms * 1000D) / 1000D;

        return cgpaAllTerms;
    }

    private void resetAllInputs() {

        EditText currentCcgpaEditText =
                (EditText) getActivity().findViewById(R.id.current_cgpa_edit_text);
        currentCcgpaEditText.setText("");

        EditText totalUnitsAttemptedEditText =
                (EditText) getActivity().findViewById(R.id.total_units_attempted_edit_text);
        totalUnitsAttemptedEditText.setText("");

        for (int i = 1; i < listView.getChildCount() - 1; i++) {

            View listItem = listView.getChildAt(i);

            Spinner letterGradeSpinner = (Spinner) listItem.findViewById(R.id.course_letter_grade_spinner);
            letterGradeSpinner.setSelection(0);

            Spinner unitsSpinner = (Spinner) listItem.findViewById(R.id.course_units_spinner);
            unitsSpinner.setSelection(0);

        }

        Toast.makeText(getActivity(), R.string.resetMessage, Toast.LENGTH_SHORT).show();
    }

    public boolean checkCorrectSpinnerValues() {
        boolean correctValues = true;

        for (int i = 1; i < listView.getChildCount() - 1; i++) {

            View listItem = listView.getChildAt(i);
            Spinner spinner = (Spinner) listItem.findViewById(R.id.course_letter_grade_spinner);
            Spinner spinner2 = (Spinner) listItem.findViewById(R.id.course_units_spinner);


            String selection = (String) spinner.getSelectedItem();
            String selection2 = (String) spinner2.getSelectedItem();

            if (selection == "Grade" || selection2 == "Units") {
                return false;
            }

        }

        return correctValues;
    }

    public List<Double> getGpaListThisSemester() {

        List<String> selections = new ArrayList<>();
        List<Double> gpaList = new ArrayList<>();

        for (int i = 1; i < listView.getChildCount() - 1; i++) {

            View listItem = listView.getChildAt(i);
            Spinner spinner = (Spinner) listItem.findViewById(R.id.course_letter_grade_spinner);


            String selection = (String) spinner.getSelectedItem();
            selections.add(selection);
        }

        for (String selection : selections) {
            double gpaItem = 0.00;

            switch (selection) {
                case "A+":
                    gpaItem = A_PLUS_GRADE_VALUE;
                    break;
                case "A":
                    gpaItem = A_GRADE_VALUE;
                    break;
                case "A-":
                    gpaItem = A_MINUS_GRADE_VALUE;
                    break;
                case "B+":
                    gpaItem = B_PLUS_GRADE_VALUE;
                    break;
                case "B":
                    gpaItem = B_GRADE_VALUE;
                    break;
                case "B-":
                    gpaItem = B_MINUS_GRADE_VALUE;
                    break;
                case "C+":
                    gpaItem = C_PLUS_GRADE_VALUE;
                    break;
                case "C":
                    gpaItem = C_GRADE_VALUE;
                    break;
                case "C-":
                    gpaItem = C_MINUS_GRADE_VALUE;
                    break;
                case "D":
                    gpaItem = D_GRADE_VALUE;
                    break;
                case "F":
                    gpaItem = F_GRADE_VALUE;
                    break;
                case "Grade":
                    gpaItem = 0.00;
                    break;
            }

            gpaList.add(gpaItem);
        }

        return gpaList;
    }

    private String areAllInputsValid() {

        if (!isCurrentCgpaValid()) {
            return CURRENT_CGPA_ERROR_STRING;

        } else if (!areTotalUnitsAttemptedValid()) {
            return TOTAL_UNITS_ATTEMPTED_ERROR_STRING;

        } else if (!checkCorrectSpinnerValues()) {
            return SPINNER_VALUES_ERROR_STRING;

        } else {
            return ALL_INPUTS_ARE_VALID_STRING;

        }
    }

    private void showDialogMessage(double gpaCurrentTerm, double cgpaAllTerms) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String messageFormat = getResources().getString(R.string.gpaCalculatorMessage);
        String message = String.format(messageFormat, gpaCurrentTerm, cgpaAllTerms);

        builder.setMessage(message)
                .setTitle(R.string.cgpaCalculatorTitle)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();

        dialog.show();
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
            case SPINNER_VALUES_ERROR_STRING:
                myToast.setText(R.string.spinnerValuesError);
                myToast.show();
                break;
            case ALL_INPUTS_ARE_VALID_STRING:
                setCurrentCgpa();
                setTotalUnitsAttempted();

                double gpaCurrentTerm = getGpaCurrentTerm(getGpaListThisSemester(),
                        getUnitsListThisSemester());
                double cgpaAllTerms = getCgpaAllTerms(getCurrentCgpa(), getTotalUnitsAttempted(),
                        getGpaListThisSemester(), getUnitsListThisSemester());

                showDialogMessage(gpaCurrentTerm, cgpaAllTerms);
        }
    }

    private List<Double> getUnitsListThisSemester() {

        List<String> units = new ArrayList<>();
        List<Double> unitList = new ArrayList<>();

        for (int i = 1; i < listView.getChildCount() - 1; i++) {

            View listItem = listView.getChildAt(i);
            Spinner spinner = (Spinner) listItem.findViewById(R.id.course_units_spinner);


            String selection = (String) spinner.getSelectedItem();
            units.add(selection);
        }

        for (String unit : units) {
            double unitItem = 0.00;

            switch (unit) {
                case "1":
                    unitItem = 1.00;
                    break;
                case "2":
                    unitItem = 2.00;
                    break;
                case "3":
                    unitItem = 3.00;
                    break;
                case "4":
                    unitItem = 4.00;
                    break;
                case "5":
                    unitItem = 5.00;
                    break;
                case "Units":
                    unitItem = 0.00;
                    break;
            }

            unitList.add(unitItem);
        }

        return unitList;
    }

    private void addCourse(CourseAdapter courseAdapter) {

        int courseCounter = courses.size() + 1;
        Toast myToast = Toast.makeText(getActivity(), null, Toast.LENGTH_SHORT);

        if (courses.size() < COURSE_LIMIT_NUMBER) {
            courses.add(new Course("Course " + courseCounter));
            courseAdapter.notifyDataSetChanged();
        } else {
            myToast.setText(R.string.tooManyCoursesError);
            myToast.show();
        }
    }

    private void removeCourse(CourseAdapter courseAdapter) {

        Toast myToast = Toast.makeText(getActivity(), null, Toast.LENGTH_SHORT);

        if (courses.size() >= 1) {
            courses.remove(courses.size() - 1);
            courseAdapter.notifyDataSetChanged();
        } else {
            myToast.setText(R.string.noMoreCoursesToRemove);
            myToast.show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cgpa_calculator, container, false);

        final CourseAdapter courseAdapter = new CourseAdapter(getActivity(), courses);

        listView = (ListView) rootView.findViewById(R.id.list);

        View header_list = getActivity().getLayoutInflater().inflate(course_item_header, null);
        View footer_list = getActivity().getLayoutInflater().inflate(course_item_footer, null);

        listView.addHeaderView(header_list);

        listView.setAdapter(courseAdapter);

        listView.addFooterView(footer_list);

        final Button addCourseButton = (Button) rootView.findViewById(R.id.add_course_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addCourse(courseAdapter);
            }
        });

        Button removeCourseButton = (Button) rootView.findViewById(R.id.remove_course_button);
        removeCourseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                removeCourse(courseAdapter);
            }
        });

        Button calculateButton = (Button) rootView.findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                displayMessage(areAllInputsValid());
            }
        });

        Button resetButton = (Button) rootView.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                resetAllInputs();
            }
        });

        return rootView;
    }
}
