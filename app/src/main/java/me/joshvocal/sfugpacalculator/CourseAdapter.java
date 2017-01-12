package me.joshvocal.sfugpacalculator;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jvocal on 2017-01-09.
 */

public class CourseAdapter extends ArrayAdapter<Course>{

    private ArrayList<Course> list = new ArrayList<>();
    private Context mContext;
    private Map<Integer, Integer> gradeMap = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> unitMap = new HashMap<Integer, Integer>();

    public CourseAdapter(Activity context, ArrayList<Course> courses) {
        super(context, 0, courses);
        list = courses;
        mContext = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.course_item, parent, false);
        }

        Course currentCourse = getItem(position);

        TextView courseHeader = (TextView) listItemView.findViewById(R.id.course_header);
        courseHeader.setText(currentCourse.getCourseName());

        final Spinner courseLetterGradeSpinner = (Spinner) listItemView.findViewById(R.id.course_letter_grade_spinner);
        String[] grades = {"Grade", "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F"};

        courseLetterGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinnerPosition, long id) {
                Log.v("GradeSpinnerPosition: ", Integer.toString(spinnerPosition));
                gradeMap.put(position, spinnerPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, grades);
        courseLetterGradeSpinner.setAdapter(adapter);

        if (gradeMap.containsKey(position)) {
            courseLetterGradeSpinner.setSelection(gradeMap.get(position));
        }


        final Spinner courseUnitSpinner = (Spinner) listItemView.findViewById(R.id.course_units_spinner);
        String[] units = {"Units", "1", "2", "3", "4", "5"};

        courseUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int unitSpinnerPosition, long id) {
                Log.v("UnitSpinnerPosition: ", Integer.toString(unitSpinnerPosition));
                unitMap.put(position, unitSpinnerPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, units);
        courseUnitSpinner.setAdapter(unitAdapter);

        if (unitMap.containsKey(position)) {
            courseUnitSpinner.setSelection(unitMap.get(position));
        }

        return listItemView;
    }
}
