package com.cxl.life.app.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.R;
import com.cxl.life.adapter.AttendanceAdapter;
import com.cxl.life.bean.Attendance;
import com.cxl.life.data.DbManage;
import com.cxl.life.util.Constants;
import com.cxl.life.util.L;
import com.cxl.life.util.TimeUtil;

import org.litepal.crud.DataSupport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by cxl on 2017/9/12.
 * excel解析,考勤统计
 */

public class ExcelFragment extends Fragment implements View.OnClickListener {
    private ListView listView;
    private AttendanceAdapter adapter;
    private List<Attendance> list;

    private Spinner spinner;
    private Button lateGet, reset;//获取迟到早退信息   重置数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_excel, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.excel_listView);
        listView.setEmptyView(view.findViewById(R.id.excel_data_null));
        list = DataSupport.findAll(Attendance.class);
        adapter = new AttendanceAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        if (list.size() == 0) {
            initData();
        }

        spinner = (Spinner) view.findViewById(R.id.excel_spinner);
        List<String> aList = DbManage.getAttendanceNumber();
        //绑定数据源
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, aList);//设置背景布局
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉每一条的布局
        //绑定 Adapter到控件
        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "你点击的是:" + ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lateGet = (Button) view.findViewById(R.id.excel_attendance_get);
        lateGet.setOnClickListener(this);
        reset = (Button) view.findViewById(R.id.excel_attendance_reset);
        reset.setOnClickListener(this);
    }

    //初始化数据
    private void initData() {
        DataSupport.deleteAll(Attendance.class);
        InputStream is;
        try {
            is = new FileInputStream(Constants.journal_sd + "boneng.xls");
            //Workbook workbook = Workbook.getWorkbook(new File("mnt/sdcard/test.xls"));
            Workbook workbook = Workbook.getWorkbook(is);
            int sheetNum = workbook.getNumberOfSheets();//总共几个工作表
            L.e("the num of sheets is " + sheetNum);
            // 获得第一个工作表对象
            Sheet sheet = workbook.getSheet(0);//取第一个工作表
            int rows = sheet.getRows();//工作表的行数
            int cols = sheet.getColumns();//工作表的列
            L.e("the name of sheet is " + sheet.getName());//工作表的名字
            L.e("total rows is " + rows);//工作表的行
            L.e("total cols is " + cols);//工作表的列
            for (int i = 1; i < rows; i++) {
                // getCell(Col,Row)获得单元格的值
                Attendance att = new Attendance();
                att.setName(sheet.getCell(2, i).getContents());
                att.setNumber(sheet.getCell(3, i).getContents());
                att.setDateTime(sheet.getCell(4, i).getContents());
                list.add(att);
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        DataSupport.saveAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.excel_attendance_get:
                getLateData();
                break;
            case R.id.excel_attendance_reset:
                initData();
                reset.setEnabled(false);
                break;
        }
    }

    //获取迟到早退信息
    private void getLateData() {
        list.clear();
        String number = spinner.getSelectedItem().toString();
        String name = DbManage.getAttendanceName(number);
        //获取上个月初的时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);//获取前一个月
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 9, 0, 0);
        int day = calendar.getActualMaximum(Calendar.DATE);
        SimpleDateFormat sdf3 = new SimpleDateFormat("M/d/yy H:mm", Locale.getDefault());
        String date = sdf3.format(calendar.getTime());//上个月初时间

        long time = TimeUtil.stringToLong(date, 3);
        for (int i = 0; i < day; i++) {//当月天数
            int week = TimeUtil.longToWeek(time);
            if (week > 1 && week < 7) {//判断在工作日
                String d = date.split(" ")[0];
                Attendance att1 = DbManage.getAttendance(d, number, true);
                if (att1 == null) {//没取到
                    att1 = new Attendance();
                    att1.setDateTime(d + " --:--");
                    att1.setName(name);
                    att1.setNumber(number);
                    att1.setState(1);
                } else {
                    long t1 = TimeUtil.stringToLong(att1.getDateTime(), 3);
                    if (t1 > time) {//迟到
                        att1.setState(1);
                    } else {//正常
                        att1.setState(0);
                    }
                }
                list.add(att1);
                Attendance att2 = DbManage.getAttendance(d, number, false);
                if (att2 == null) {//没取到
                    att2 = new Attendance();
                    att2.setDateTime(d + " --:--");
                    att2.setName(name);
                    att2.setNumber(number);
                    att2.setState(2);
                } else {
                    long t2 = TimeUtil.stringToLong(att2.getDateTime(), 3);
                    if (t2 < time + 9 * 60 * 60 * 1000) {//早退
                        att2.setState(2);
                    } else {//正常
                        att2.setState(0);
                    }
                }
                list.add(att2);
            }
            time += 24 * 60 * 60 * 1000;
            date = TimeUtil.longToString(time, 3);
        }
        adapter.notifyDataSetChanged();
    }
}
