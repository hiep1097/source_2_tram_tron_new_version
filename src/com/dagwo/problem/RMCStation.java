package com.dagwo.problem;

import java.util.ArrayList;

public class RMCStation {
    public String StationID;            // Mã trạm trộn
    public int  c;                      // Số xe ban đầu thuộc trạm trộn tương ứng
    public static int MD;                      // thời gian trộn bê tông tại trạm, giống nhau đối với tất cả các trạm trộn
//    public ArrayList<Integer> lstD;     // Danh sách khoảng cách từ trạm trộn đến các công trường
    public int FDT;

    public ArrayList<ScheduleTruck> lstIDT;     // Danh sách thời gian khởi hành của Xe từ trạm hiện tại (đã được xếp lịch) => IDT[i].OutputTime la gi tri cua IDT
    public ArrayList<ScheduleTruck> lstIDTForPrint; //Danh sách IDT cho việc in kết quả, lstIDT sẽ bị xóa trong quá trình tính toán
    public ArrayList<Integer> lstTDG;           // Danh sách Thời gian từ trạm trộn đến công trường
    public ArrayList<Integer> lstTDB;           // Danh sách Thời gian từ công trường về trạm trộn

    public RMCStation(){
//        lstD = new ArrayList<Integer>();
        lstIDT = new ArrayList<ScheduleTruck>();
        lstIDTForPrint = new ArrayList<ScheduleTruck>();

        lstTDG = new ArrayList<Integer>();
        lstTDB = new ArrayList<Integer>();

        FDT = 0;
    }

    public void initListOfIDT(){
        for(int i = 1; i <= c; i++){
            ScheduleTruck st = new ScheduleTruck();
            st.truckID = StationID+i;
            st.inputTime = 0;
            st.outputTime = FDT + MD * (i - 1);
            st.stationID = StationID;

            lstIDT.add(st);
            lstIDTForPrint.add(st);
        }
    }

    @Override
    public String toString(){
        String s = StationID + "\t" + c + "\t" + MD + "\t";
//        for(Integer i : lstD){
//            s += i + "|";
//        }
        return s.substring(0,s.length() - 1);
    }
}
