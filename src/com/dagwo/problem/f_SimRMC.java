package com.dagwo.problem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class f_SimRMC {

    public ArrayList<Site> lstSite = new ArrayList<>();                                         // Danh sách Công trường
    public ArrayList<ConstructionType> lstCT = new ArrayList<>();                               // Danh sách loại cấu kiện (Dầm, sàn, cột)
    public ArrayList<RMCTruckSchedule> lstRMCTruckSchedule = new ArrayList<>();                 // Thông tin chi tiết 1 lần đổ bên tông của một xe
    public ArrayList<ScheduleTruck> lstScheduleTrucks = new ArrayList<ScheduleTruck>();         // Thông tin lịch đổ bê tông của các xe tải
    public ArrayList<RMCStation> lstRMCStation = new ArrayList<>();                                   // danh sách trạm trộn
    public boolean[] arrUsedTruckTBBMin = new boolean[1000];       // Mảng đánh dấu đã sử dụng TBB làm min

    public int powerOfTruck = 0; // Khả năng của xe có thể chở được tối đa bao nhiêu m3 bê tông

    public float TWC = 0;       // Tổng thời gian Xe đợi Công trường
    public float CWT = 0;       // Tổng thời gian Công trường đợi xe
//    public int TOTAL_DISTANCE = 0;   // Tổng quãng đường tất cả xe phải đi

    public double[] x_rand;

    public int N = 0;        // Tổng số xe cần bàn cho tất cả các công trường
    public int m = 0;        // Tổng số công trường

    int infinity = 1000000000;

    public f_SimRMC(){
        try {
            ReadFile("Data/input_2_tram.data");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Tính tổng số xe

        for(Site s : lstSite){
            N += s.numOfTruck;
        }

    }

    public int strHourToMinute(String strHour){
        String data[] = strHour.split(":");
        int iHour = Integer.parseInt(data[0]);
        int iMinute = Integer.parseInt(data[1]);
        return (iHour * 60) + iMinute;
    }

    public String strMinuteToHour(int iNum){
        int iHour = iNum / 60;
        int iMinute = iNum % 60;
        return String.format("%02d", iHour) + ":" + String.format("%02d", iMinute);
    }

    public String readLineFromFile(Scanner sc){
        String sLine = "";

        do {
            if(sc.hasNextLine() == false)
                return null;
            sLine = sc.nextLine();
        } while(sLine.length() == 0);

        return sLine;
    }

    public void ReadFile(String fileName) throws IOException {
        File f = new File(fileName);

        if(f.exists()) {
            try (Scanner scan = new Scanner(f)) {
                //Đọc thông tin công trường

                if (scan.hasNextLine()){
                    String line = readLineFromFile(scan);
                    String data[] = line.split("\\:");
                    m = Integer.parseInt(data[1]);
                }

                for(int i = 0; i < m; i++){
                    if(scan.hasNextLine()){
                        String line = readLineFromFile(scan);
                        String data[] = line.split("\\|");
                        Site s = new Site();
                        s.siteID = Integer.parseInt(data[0]);
                        s.SCT = strHourToMinute(data[1]);
                        s.R = Integer.parseInt(data[2]);
                        s.PT = data[3];
                        lstSite.add(s);
                    }
                }

                //Đọc thông tin Loại cấu kiện
                readLineFromFile(scan); //Đọc bỏ dòng -----
                for(int i = 0; i < 3; i++){
                    if(scan.hasNextLine()){
                        String line = readLineFromFile(scan);
                        String data[] = line.split("\\:");
                        ConstructionType c = new ConstructionType();
                        c.constructionName = data[0];
                        c.TPT = Integer.parseInt(data[1]);
                        lstCT.add(c);
                    }
                }

                readLineFromFile(scan); //Đọc bỏ dòng -----

                //Khả năng chở bê tông của một xe tải
                if(scan.hasNextLine()){
                    String line = readLineFromFile(scan);
                    String data[] = line.split("\\:");
                    powerOfTruck = Integer.parseInt(data[1]);
                }

                readLineFromFile(scan); //Đọc bỏ dòng -----

                if(scan.hasNextLine()){
                    String line = readLineFromFile(scan);
                    String data[] = line.split("\\:");
                    RMCStation.MD = Integer.parseInt(data[1]);
                }

                readLineFromFile(scan); //Đọc bỏ dòng -----

                int numberOfStation = 0;
                if(scan.hasNextLine()){
                    String line = readLineFromFile(scan);
                    String data[] = line.split("\\:");
                    numberOfStation = Integer.parseInt(data[1]);
                }

                for(int i = 0; i < numberOfStation; i++){
                    RMCStation rmcStation = new RMCStation();
                    readLineFromFile(scan); //Đọc bỏ dòng -----

                    String line = readLineFromFile(scan);
                    String data[] = line.split("\\:");
                    rmcStation.StationID = data[1];

                    line = readLineFromFile(scan);
                    data = line.split("\\:");
                    rmcStation.c = Integer.parseInt(data[1]);

//                    line = readLineFromFile(scan);
//                    data = line.split("\\:");
//                    String arrD[] = data[1].split("\\|");
//
//                    for(int j = 0; j < arrD.length; j++){
//                        rmcStation.lstD.add(Integer.parseInt(arrD[j]));
//                    }

                    line = readLineFromFile(scan);
                    data = line.split("\\:");
                    String arrTDG[] = data[1].split("\\|");
                    for(int j = 0; j < arrTDG.length; j++){
                        rmcStation.lstTDG.add(Integer.parseInt(arrTDG[j]));
                    }

                    line = readLineFromFile(scan);
                    data = line.split("\\:");
                    String arrTDB[] = data[1].split("\\|");
                    for(int j = 0; j < arrTDB.length; j++){
                        rmcStation.lstTDB.add(Integer.parseInt(arrTDB[j]));
                    }

                    lstRMCStation.add(rmcStation);
                }
            }

            for(Site s : lstSite) {
                s.calNumOfTruck(powerOfTruck);

                for(ConstructionType t : lstCT){
                    if(s.PT.equals(t.constructionName)) {
                        s.CD = t.TPT;
                    }
                }
            }
        }
        else {
            System.out.println("--> Error 404: File input.data not found!!!");
        }
    }

    public void calFDT(){
        // Tính min thời gian khởi hành của các trạm trộn tới công trường

        for (RMCStation rmcStation: lstRMCStation){
            int fdt = lstSite.get(0).calTimeTruckMove(rmcStation.lstTDG.get(0));

            for(int i = 1; i < lstSite.size(); i++){
                if(fdt > lstSite.get(i).calTimeTruckMove(rmcStation.lstTDG.get(i))){
                    fdt = lstSite.get(i).calTimeTruckMove(rmcStation.lstTDG.get(i));
                }
            }

            rmcStation.FDT = fdt;
            rmcStation.initListOfIDT();
        }
    }

    public void initRMC(){
        ArrayList<RMCSite> arrSite = new ArrayList<RMCSite>();

        //Thực hiện khởi tạo quần thể sắp lịch ban đầu từ kết quả của thuật toán GWO thông qua x_rand[]
        int t = 0;
        for(Site s : lstSite){
            for(int i = 0; i < s.numOfTruck; i++){
                RMCSite rmcSite = new RMCSite();
                rmcSite.siteID = s.siteID;
                rmcSite.value = (int) (x_rand[t++] * 10000);
                arrSite.add(rmcSite);
            }
        }

        // Dựa và giá trị value của dãy Random/Thuật toán GWO, sắp sếp lại thứ tự đổ tại các công trường
        for(int i = 0; i < arrSite.size() - 1; i++){
            for(int j = i + 1; j < arrSite.size(); j++){
                if( arrSite.get(i).value > arrSite.get(j).value){
                    int tempSiteID = arrSite.get(i).siteID;
                    int tempValue = arrSite.get(i).value;

                    arrSite.get(i).siteID = arrSite.get(j).siteID;
                    arrSite.get(i).value = arrSite.get(j).value;

                    arrSite.get(j).siteID = tempSiteID;
                    arrSite.get(j).value = tempValue;
                }
            }
        }

        //Cập nhật k lại cho RMCSite
        for(int i = 0; i < arrSite.size(); i++){
            int k = 1;
            for(int j = i - 1; j >= 0; j--){
                if(arrSite.get(i).siteID == arrSite.get(j).siteID)
                    k++;
            }
            arrSite.get(i).k = k;
        }

        //for test
//        arrSite = new ArrayList<>();
//        arrSite.add(new RMCSite(2,0,1));
//        arrSite.add(new RMCSite(3,0,1));
//        arrSite.add(new RMCSite(2,0,2));
//        arrSite.add(new RMCSite(1,0,1));
//        arrSite.add(new RMCSite(3,0,2));
//        arrSite.add(new RMCSite(1,0,2));
//        arrSite.add(new RMCSite(3,0,3));
//        arrSite.add(new RMCSite(3,0,4));
//        arrSite.add(new RMCSite(1,0,3));
//        arrSite.add(new RMCSite(2,0,3));
//        arrSite.add(new RMCSite(3,0,5));
//        arrSite.add(new RMCSite(2,0,4));
        //1,3,2,1,2,1,3,2,3,3,3,2
//        arrSite = new ArrayList<>();
//        arrSite.add(new RMCSite(1,0,1));
//        arrSite.add(new RMCSite(3,0,1));
//        arrSite.add(new RMCSite(2,0,1));
//        arrSite.add(new RMCSite(1,0,2));
//        arrSite.add(new RMCSite(2,0,2));
//        arrSite.add(new RMCSite(1,0,3));
//        arrSite.add(new RMCSite(3,0,2));
//        arrSite.add(new RMCSite(2,0,3));
//        arrSite.add(new RMCSite(3,0,3));
//        arrSite.add(new RMCSite(3,0,4));
//        arrSite.add(new RMCSite(3,0,5));
//        arrSite.add(new RMCSite(2,0,4));

        int No = 1;
        for(RMCSite rm : arrSite) {
            RMCTruckSchedule rmc = new RMCTruckSchedule();
            rmc.rmcID = No++;
            rmc.s = lstSite.get(rm.siteID - 1);
            rmc.k = rm.k;
            rmc.calDelivery(powerOfTruck);
            rmc.TBB = infinity;
            lstRMCTruckSchedule.add(rmc);
        }
    }

    public void initScheduleTruck(){
        for (RMCStation rmcStation: lstRMCStation){
            for (int i = 1; i <= rmcStation.c; i++) {
                ScheduleTruck scheduleTruck = new ScheduleTruck();
                scheduleTruck.truckID = rmcStation.StationID+i;
                lstScheduleTrucks.add(scheduleTruck);
            }
        }
    }

    public void printSimulatedResult(){
        System.out.println("Simulated result:");
        System.out.print("i\t\t");
        for (int i=1; i<=N; i++) {
            System.out.print(i+"\t\t");
        }
        System.out.println();

        for (RMCStation rmcStation: lstRMCStation){
            System.out.print("IDTi " + rmcStation.StationID+ ":\t");
            for (int i=0; i<rmcStation.c; i++) {
                System.out.print(strMinuteToHour(rmcStation.lstIDTForPrint.get(i).outputTime)+"\t");
            }
            System.out.println();
        }

        System.out.print("i\t\t");
        for (int i=1; i<=N; i++) {
            System.out.print(i+"\t\t");
        }
        System.out.println();

        System.out.print("j\t\t");
        for (int i=0; i<N; i++) {
            System.out.print(lstRMCTruckSchedule.get(i).s.siteID+"\t\t");
        }
        System.out.println();

        System.out.print("k\t\t");
        for (int i=0; i<N; i++) {
            System.out.print(lstRMCTruckSchedule.get(i).k+"\t\t");
        }
        System.out.println();

        System.out.print("Deliver\t");
        for (int i=0; i<N; i++) {
            System.out.print(lstRMCTruckSchedule.get(i).delivery+"\t\t");
        }
        System.out.println();

        System.out.print("CDji\t");
        for (int i=0; i<N; i++) {
            System.out.print(lstRMCTruckSchedule.get(i).CD_RMC+"\t\t");
        }
        System.out.println();

        System.out.print("SDTi\t");
        for (int i=0; i<N; i++) {
            System.out.print(strMinuteToHour(lstRMCTruckSchedule.get(i).SDT)+"\t");
        }
        System.out.println();

        System.out.print("TACji\t");
        for (int i=0; i<N; i++) {
            System.out.print(strMinuteToHour(lstRMCTruckSchedule.get(i).TAC)+"\t");
        }
        System.out.println();

        System.out.print("PTFji\t");
        for (int i=0; i<N; i++) {
            System.out.print(strMinuteToHour(lstRMCTruckSchedule.get(i).PTF)+"\t");
        }
        System.out.println();

        System.out.print("WCji\t");
        for (int i=0; i<N; i++) {
            System.out.print(lstRMCTruckSchedule.get(i).WC+"\t\t");
        }
        System.out.println();

        System.out.print("LTji\t");
        for (int i=0; i<N; i++) {
            System.out.print(strMinuteToHour(lstRMCTruckSchedule.get(i).LT)+"\t");
        }
        System.out.println();

        System.out.print("TBBi\t");
        for (int i=0; i<N; i++) {
            System.out.print(strMinuteToHour(lstRMCTruckSchedule.get(i).TBB)+"\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        f_SimRMC fSimRMC = new f_SimRMC();
        fSimRMC.ExecuteAlgorithm();
        fSimRMC.printSimulatedResult();
    }


    public void ExecuteAlgorithm(){

        for(int i = 0; i < N; i++){
            arrUsedTruckTBBMin[i] = false;
        }

        initScheduleTruck();

        //Khởi tạo quần thể RMCTruckSchedule
        initRMC();

        //Tính FDT, IDT
        calFDT();

        int C_total = 0;    //tong so xe cua tat ca cac tram tron
        for (RMCStation rmcStation: lstRMCStation){
            C_total += rmcStation.c;
        }


        //tinh toan cac gia tri
        for(int i=0; i< N; i++){
            RMCTruckSchedule rmcTruckSchedule = lstRMCTruckSchedule.get(i);

            RMCStation rmcStationGo;    //di tu tram tron den cong truong

            if (i < C_total){   //luot xe dau tien
                ScheduleTruck scheduleTruck;
                scheduleTruck = calTruckFromStation(i, rmcTruckSchedule);
                rmcTruckSchedule.StationID_Go = scheduleTruck.stationID;
                rmcTruckSchedule.SDT = scheduleTruck.outputTime;
                rmcTruckSchedule.truckID = scheduleTruck.truckID;
                rmcStationGo = findRMCStation(scheduleTruck.stationID);
            } else {
                int minTBB = infinity;
                int position_minTBB = -1;
                for (int l=0; l<i; l++){
                    if (!arrUsedTruckTBBMin[l] && minTBB > lstRMCTruckSchedule.get(l).TBB){
                        minTBB = lstRMCTruckSchedule.get(l).TBB;
                        position_minTBB = l;
                    }
                }
                arrUsedTruckTBBMin[position_minTBB] = true;
                rmcTruckSchedule.SDT = minTBB + RMCStation.MD;
                rmcTruckSchedule.truckID = lstRMCTruckSchedule.get(position_minTBB).truckID;
                rmcTruckSchedule.StationID_Go = lstRMCTruckSchedule.get(position_minTBB).StationID_Back;
                rmcStationGo = findRMCStation(rmcTruckSchedule.StationID_Go);
            }

            rmcTruckSchedule.CD_RMC = rmcTruckSchedule.delivery * rmcTruckSchedule.s.CD;

            rmcTruckSchedule.TDG = rmcStationGo.lstTDG.get(rmcTruckSchedule.s.siteID-1);

            rmcTruckSchedule.TAC = rmcTruckSchedule.SDT + rmcTruckSchedule.TDG;

            //Tính PTF
            if(rmcTruckSchedule.k == 1) {
                rmcTruckSchedule.PTF = rmcTruckSchedule.s.SCT;
            } else {
                //find LT of (k-1)th truck leaves site j
                int siteID = rmcTruckSchedule.s.siteID;
                int position = -1;
                for (int l=0; l<i; l++){
                    if (lstRMCTruckSchedule.get(l).s.siteID == siteID && lstRMCTruckSchedule.get(l).k == rmcTruckSchedule.k-1){
                        position = l;
                    }
                }
                rmcTruckSchedule.PTF = lstRMCTruckSchedule.get(position).LT;
            }

            rmcTruckSchedule.WC = rmcTruckSchedule.PTF - rmcTruckSchedule.TAC;

            if(rmcTruckSchedule.WC >= 0){
                rmcTruckSchedule.LT = rmcTruckSchedule.TAC + rmcTruckSchedule.WC + rmcTruckSchedule.CD_RMC;
            } else {
                rmcTruckSchedule.LT = rmcTruckSchedule.TAC + rmcTruckSchedule.CD_RMC;
            }

            RMCStation rmcStationBack;  //ve tram tron tu cong truong

            ScheduleTruck scheduleTruckBack = calTruckBackToStation(rmcTruckSchedule);
            rmcTruckSchedule.StationID_Back = scheduleTruckBack.stationID;
            rmcStationBack = findRMCStation(scheduleTruckBack.stationID);

            rmcTruckSchedule.TDB = rmcStationBack.lstTDB.get(rmcTruckSchedule.s.siteID-1);
            rmcTruckSchedule.TBB = rmcTruckSchedule.LT + rmcTruckSchedule.TDB;

            //quang duong di = quang duong tu rmcStationGo den cong truong + quang duong tu cong truong ve rmcStationBack
//            rmcTruckSchedule.distanceGo = rmcStationGo.lstD.get(rmcTruckSchedule.s.siteID-1);
//            rmcTruckSchedule.distanceBack = rmcStationBack.lstD.get(rmcTruckSchedule.s.siteID-1);
            lstRMCTruckSchedule.set(i, rmcTruckSchedule);
        }

        calcSum_TWC_CWT();
    }

    public RMCStation findRMCStation(String stationID){
        for(RMCStation station : lstRMCStation)
            if(station.StationID.equals(stationID))
                return station;
        return null;
    }

    private ScheduleTruck calTruckBackToStation(RMCTruckSchedule rmcTruckSchedule) {    //cal station has TBB min
        ScheduleTruck scheduleTruck = new ScheduleTruck();

        RMCStation goodStation = lstRMCStation.get(0);

        for(int i = 1; i < lstRMCStation.size(); i++){
            RMCStation rmcStation = lstRMCStation.get(i);
            int TBBofGoodStation = rmcTruckSchedule.LT + goodStation.lstTDB.get(rmcTruckSchedule.s.siteID-1);
            int TBBofCurrentStation = rmcTruckSchedule.LT + rmcStation.lstTDB.get(rmcTruckSchedule.s.siteID-1);

            if (TBBofCurrentStation<TBBofGoodStation){
                goodStation = rmcStation;
            }
        }

        scheduleTruck.stationID = goodStation.StationID;

        return scheduleTruck;
    }

    private ScheduleTruck calTruckFromStation(int currentI, RMCTruckSchedule rmcTruckSchedule) {
        ScheduleTruck scheduleTruck = new ScheduleTruck();

        RMCStation goodStation = lstRMCStation.get(0);

        for(int i = 1; i < lstRMCStation.size(); i++){
            //neu tram tron goodStation khong con xe tai, gan goodStation thanh tram tron hien tai
            if (goodStation.lstIDT.size() == 0){
                goodStation = lstRMCStation.get(i);
                continue;
            }

            RMCStation rmcStation = lstRMCStation.get(i);
            //neu tram tron hien tai khong con xe tai, bo qua va tiep tuc
            if (rmcStation.lstIDT.size()==0){
                continue;
            }

            int SDTofGoodStation = goodStation.lstIDT.get(0).outputTime;
            int SDTofCurrentStation = rmcStation.lstIDT.get(0).outputTime;
            int TACofGoodStation = SDTofGoodStation + goodStation.lstTDG.get(rmcTruckSchedule.s.siteID-1);
            int TACofCurrentStation = SDTofCurrentStation + rmcStation.lstTDG.get(rmcTruckSchedule.s.siteID-1);
            int PTF = 0;
            if (rmcTruckSchedule.k == 1){
                PTF = rmcTruckSchedule.s.SCT;
            } else {
                //find LT of (k-1)th truck leaves site j
                int siteID = rmcTruckSchedule.s.siteID;
                int position = -1;
                for (int l=0; l<currentI; l++){
                    if (lstRMCTruckSchedule.get(l).s.siteID == siteID && lstRMCTruckSchedule.get(l).k == rmcTruckSchedule.k-1){
                        position = l;
                    }
                }
                PTF = lstRMCTruckSchedule.get(position).LT;
            }

            int WCofGoodStation = PTF - TACofGoodStation;
            int WCofCurrentStation = PTF - TACofCurrentStation;

            if (Math.abs(WCofCurrentStation) < Math.abs(WCofGoodStation)){
                goodStation = lstRMCStation.get(i);
            }
        }

        scheduleTruck.truckID = goodStation.lstIDT.get(0).truckID;
        scheduleTruck.outputTime = goodStation.lstIDT.get(0).outputTime;
        scheduleTruck.stationID = goodStation.StationID;

        goodStation.lstIDT.remove(0);

        return scheduleTruck;
    }

    public void PrintRMC(){
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");

        for(RMCTruckSchedule rmc : lstRMCTruckSchedule){
            System.out.println(rmc.toString());
        }
        System.out.println("=> TWC: " + TWC);
        System.out.println("=> CWT: " + CWT);

        System.out.println("Chuoi phan phoi: ");
        System.out.print("[");
        for(int i=0; i<lstRMCTruckSchedule.size(); i++){
            if (i!=lstRMCTruckSchedule.size()-1){
                System.out.print(lstRMCTruckSchedule.get(i).s.siteID+",");
            } else {
                System.out.print(lstRMCTruckSchedule.get(i).s.siteID);
            }
        }
        System.out.print("]");
        System.out.println();
    }

    public void PrintPlanOfTruck(){
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Arrive at plant\tGo to site \t Arrive at site \t Leave from site \t Return to plant \t Plant go \t Plant back");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        for (ScheduleTruck scheduleTruck : lstScheduleTrucks){
            PlanOfTruck planOfTruck = new PlanOfTruck();
            planOfTruck.TruckID = scheduleTruck.truckID;

            for (RMCTruckSchedule rmcTruckSchedule : lstRMCTruckSchedule){
                if(rmcTruckSchedule.truckID.equals(planOfTruck.TruckID)){
                    Plan p = new Plan();
                    p.SiteID = rmcTruckSchedule.s.siteID;
                    p.ArriveAtStation = strMinuteToHour(rmcTruckSchedule.SDT);
                    p.ArriveAtSite = strMinuteToHour(rmcTruckSchedule.TAC);
                    p.LeaveFromSite = strMinuteToHour(rmcTruckSchedule.LT);
                    p.ReturnToPlant = strMinuteToHour(rmcTruckSchedule.TBB);
                    p.GoStation = rmcTruckSchedule.StationID_Go;
                    p.BackStation = rmcTruckSchedule.StationID_Back;
                    planOfTruck.lstPlan.add(p);
                }
            }
            System.out.println(planOfTruck);
        }
    }

    public void calcSum_TWC_CWT(){
        int sumTWC = 0;
        int sumCWT = 0;
        int sumDistance = 0;
        for(RMCTruckSchedule rmc : lstRMCTruckSchedule){
            if(rmc.WC > 0)
                sumTWC += rmc.WC;
            else
                sumCWT += rmc.WC;

//            sumDistance += rmc.distanceGo + rmc.distanceBack;
        }

        TWC = sumTWC;
        CWT = Math.abs(sumCWT);
//        TOTAL_DISTANCE = sumDistance;
    }

    public double Execute_CWT(double x[]) {
        x_rand = x;
        ExecuteAlgorithm();
        return CWT;
    }

    public double Execute_TWC(double x[]) {
        x_rand = x;
        ExecuteAlgorithm();
        return TWC;
    }

    public void Execute(double x[]){
        x_rand = x;
        ExecuteAlgorithm();
    }
}
