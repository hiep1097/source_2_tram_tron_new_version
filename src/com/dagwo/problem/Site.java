package com.dagwo.problem;

public class Site {
    public int siteID;              //Mã công trường
    public int SCT;                 //Thời gian công trường bắt đầu đổ bê Tông --> tính bằng Phút (quy về 00:00)
    public int R;                   //Số lượng bê tông công trường cần mua
    public String PT;               //Loại cấu kiện
    public int numOfTruck;          //Số xe công trường cần mua
    public int CD;                  //Thời gian để đổ 1 mét khối loại cấu kiện (phút/m3)

    public void calNumOfTruck(int powOfTruck){
        numOfTruck = R / powOfTruck + ((R % powOfTruck > 0) ? 1 : 0);
    }

    public int calTimeTruckMove(int TDG){
        return  SCT - TDG;
    }

    @Override
    public String toString()
    {
        return siteID + "\tSCT:" + SCT + "\tR:" + String.format("%03d", R) + "\tPT:" + PT + "\ttr:" + numOfTruck + "\tCD:" + CD;
    }
}
