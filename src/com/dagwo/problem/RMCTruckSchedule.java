package com.dagwo.problem;

//Thông tin chi tiết của lần lần đổ ~ một xe
public class RMCTruckSchedule {
    public int rmcID;       // Thứ tự lần đổ
    public Site s;          // Thông tin công trường
    public int k;           // Lần đổ thứ k của công trình j (siteID) (k được tính từ 1)
    public int delivery;    // Sô khối Bê tông cần vận chuyển cho công trình j tại lần đổ thứ k
    public int CD_RMC;      // Thời gian bơm hết bê tông vào công trường của lần đổ thứ k với loại cấu kiện tương ứng của Site
    public String truckID;     // Mã của xe đang thực hiện đổ

    public int SDT = 0;     // Thời điểm bắt đầu khởi hành
    public int TAC = 0;     // Thời điểm đến công trường
    public int PTF = 0;     // Thời điểm bắt đầu đổ bê tông
    public int WC = 0;      // Thời gian chờ (WC > 0: xe đợi công trường; WC < 0: công trường đợi xe; WC = 0: lý tưởng)
    public int LT = 0;      // Thời điểm rời khỏi công trường
    public int TBB = 0;     // Thời điểm về tới trạm trộn

    public int TDG;     //Thời gian từ trạm trộn đến công trường
    public int TDB;     //Thời gian từ công trường về trạm trộn

    public String StationID_Go;     // Mã trạm trộn đi
    public String StationID_Back;   // Mã trạm trộn quay về

    public int distanceGo;
    public int distanceBack;

    public void calDelivery(int powerOfTruck){
        if(k < s.numOfTruck) {
            delivery = powerOfTruck;
        } else {
            if(k == s.numOfTruck) {
                delivery = s.R - (powerOfTruck * (k - 1));
            } else {
                delivery = 0;
            }
        }
    }

    @Override
    public String toString(){
        return rmcID + "\t" + s.toString() + "\tTDG:" + TDG + "\tTDB:" + TDB + "\t" + k + "\t" + delivery + "\t" + CD_RMC + "\t|\tSDT:"
                + String.format("%04d", SDT) + "\tTAC:" + String.format("%04d", TAC) + "\tPTF:" + String.format("%04d", PTF) + "\tWC:"
                + String.format("%05d", WC) + "\tLT:" + LT + "\tTBB:" + String.format("%04d", TBB) + "\tTruckID:" + truckID
                + "\tFrom: " + StationID_Go + " -> To: " + StationID_Back;
    }
}
