package com.mycompany.dbprogrammet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader  {
    
    public static String VIKTJÄMFÖRELSE_EXCEL = System.getProperty("user.dir") + "//Sparat//Leverantör Viktjämförelse E470.xlsx";
    public static String KCKR_EXCEL = System.getProperty("user.dir") + "//Sparat//Jämf KCKR 2019-04-30.xlsx";
    public static String KKR_EXCEL = System.getProperty("user.dir") + "//Sparat//Jämf KKR 2019-05-09.xlsx";
    public static String SÖDIN_EXCEL = System.getProperty("user.dir") + "//Sparat//Jämf. Södin 2019-05-23.xlsx";
    public static String VKR_EXCEL = System.getProperty("user.dir") + "//Sparat//Jämf. VKR 2019-05-15.xlsx";
    
    private final File VIKTJÄMFÖRELSE = new File (System.getProperty("user.dir") + "//Sparat//Leverantör Viktjämförelse E470.xlsx");
    private final File KCKR = new File (System.getProperty("user.dir") + "//Sparat//Jämf KCKR 2019-04-30.xlsx");
    private final File KKR = new File (System.getProperty("user.dir") + "//Sparat//Jämf KKR 2019-05-09.xlsx");
    private final File SÖDIN = new File (System.getProperty("user.dir") + "//Sparat//Jämf. Södin 2019-05-23.xlsx");
    private final File VKR = new File (System.getProperty("user.dir") + "//Sparat//Jämf. VKR 2019-05-15.xlsx");
    
    ArrayList<String> YD_ESW = new ArrayList<>();
    ArrayList<String> ID_ESW = new ArrayList<>();
    ArrayList<String> KGM_ESW = new ArrayList<>();
    
    ArrayList<String> YD_VM = new ArrayList<>();
    ArrayList<String> ID_VM = new ArrayList<>();
    ArrayList<String> KGM_VM = new ArrayList<>();
    
    ArrayList<String> YD_BEN = new ArrayList<>();
    ArrayList<String> ID_BEN = new ArrayList<>();
    ArrayList<String> KGM_BEN = new ArrayList<>();
    
    ArrayList<String> YD_TMK = new ArrayList<>();
    ArrayList<String> ID_TMK = new ArrayList<>();
    ArrayList<String> KGM_TMK = new ArrayList<>();
    
    ArrayList<String> YD_HUB = new ArrayList<>();
    ArrayList<String> ID_HUB = new ArrayList<>();
    ArrayList<String> KGM_HUB = new ArrayList<>();
    
    ArrayList<String> YD_OVA = new ArrayList<>();
    ArrayList<String> ID_OVA = new ArrayList<>();
    ArrayList<String> KGM_OVA = new ArrayList<>();
    
    ArrayList<Double> KKR_DIMENSION = new ArrayList<>();
    ArrayList<Double> KKR_DIMENSION_TVÅ = new ArrayList<>();
    ArrayList<Double> KKR_GODSTJOCKLEK = new ArrayList<>();
    
    ArrayList<Double> KKR_EURO_TON_ARVEDI = new ArrayList<>();
    ArrayList<Double> KKR_EURO_TON_ALESSIO = new ArrayList<>();
    ArrayList<Double> KKR_EURO_TON_CORINTH = new ArrayList<>();
    ArrayList<Double> KKR_EURO_TON_ILVA = new ArrayList<>();
    ArrayList<Double> KKR_EURO_TON_MARCEGAGLIA = new ArrayList<>();
    ArrayList<Double> KKR_EURO_TON_SIDERALBA = new ArrayList<>();
    ArrayList<Double> KKR_EURO_TON_TATA = new ArrayList<>();
    ArrayList<Double> KKR_EURO_TON_TATA_NL = new ArrayList<>();
    
    ArrayList<Double> KCKR_DIMENSION = new ArrayList<>();
    ArrayList<Double> KCKR_GODSTJOCKLEK = new ArrayList<>();
    
    ArrayList<Double> KCKR_EURO_TON_ALCH_BAT = new ArrayList<>();
    ArrayList<Double> KCKR_EURO_TON_ESW = new ArrayList<>();
    ArrayList<Double> KCKR_EURO_TON_HUBEI = new ArrayList<>();
    ArrayList<Double> KCKR_EURO_TON_MORAVIA = new ArrayList<>();
    ArrayList<Double> KCKR_EURO_TON_PT = new ArrayList<>();
    ArrayList<Double> KCKR_EURO_TON_PIPEX = new ArrayList<>();
    
    ArrayList<Double> VKR_DIMENSION = new ArrayList<>();
    ArrayList<Double> VKR_DIMENSION_TVÅ = new ArrayList<>();
    ArrayList<Double> VKR_GODSTJOCKLEK = new ArrayList<>();
    
    ArrayList<Double> VKR_EURO_TON_ARVEDI = new ArrayList<>();
    ArrayList<Double> VKR_EURO_TON_LORRAINE = new ArrayList<>();
    ArrayList<Double> VKR_EURO_TON_TATA = new ArrayList<>();
    
    double ESW_Euro, VM_Euro, VM_Euro_2, VM_Euro_3, BEN_Euro, TMK_Cold_Euro, TMK_Assel_Euro, HUB_Euro, OVA_Euro;
    
    public void getValue() throws IOException {
        if(VIKTJÄMFÖRELSE.exists()) {
            FileInputStream file = new FileInputStream(new File(VIKTJÄMFÖRELSE_EXCEL));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for(int rowIndex = 0; rowIndex <= 10; rowIndex ++) {
                XSSFRow row = sheet.getRow(rowIndex);
                for(int columnIndex = 1; columnIndex < 2; columnIndex ++) {
                    XSSFCell cell = row.getCell(columnIndex);
                    if(cell.getRowIndex() == 2) {
                        String value_string = cell.toString();
                        ESW_Euro = Double.parseDouble(value_string);
                    }
                    if(cell.getRowIndex() == 3) {
                        String value_string = cell.toString();
                        VM_Euro = Double.parseDouble(value_string);
                    }
                    if(cell.getRowIndex() == 4) {
                        String value_string = cell.toString();
                        VM_Euro_2 = Double.parseDouble(value_string);
                    }
                    if(cell.getRowIndex() == 5) {
                        String value_string = cell.toString();
                        VM_Euro_3 = Double.parseDouble(value_string);
                    }
                    if(cell.getRowIndex() == 6) {
                        String value_string = cell.toString();
                        BEN_Euro = Double.parseDouble(value_string);
                    }
                    if(cell.getRowIndex() == 7) {
                        String value_string = cell.toString();
                        TMK_Cold_Euro = Double.parseDouble(value_string);
                    }
                    if(cell.getRowIndex() == 8) {
                        String value_string = cell.toString();
                        TMK_Assel_Euro = Double.parseDouble(value_string);
                    }
                    if(cell.getRowIndex() == 9) {
                        String value_string = cell.toString();
                        HUB_Euro = Double.parseDouble(value_string);
                    }
                    if(cell.getRowIndex() == 10) {
                        String value_string = cell.toString();
                        OVA_Euro = Double.parseDouble(value_string);
                    }
                }
            }

            for(int rowIndex = 13; rowIndex < 280; rowIndex ++) {
                XSSFRow row = sheet.getRow(rowIndex);
                for(int columnIndex = 9; columnIndex < 39; columnIndex ++) {
                    XSSFCell cell = row.getCell(columnIndex);
                        if(cell.getColumnIndex() == 9) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            YD_ESW.add(value_string);
                        }
                        if(cell.getColumnIndex() == 10) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            ID_ESW.add(value_string);
                        }
                        if(cell.getColumnIndex() == 12) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            KGM_ESW.add(value_string);
                        }
                        if(cell.getColumnIndex() == 14) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            YD_VM.add(value_string);
                        }
                        if(cell.getColumnIndex() == 15) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            ID_VM.add(value_string);
                        }
                        if(cell.getColumnIndex() == 17) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            KGM_VM.add(value_string);
                        }

                        if(cell.getColumnIndex() == 19) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            YD_BEN.add(value_string);
                        }
                        if(cell.getColumnIndex() == 20) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            ID_BEN.add(value_string);
                        }
                        if(cell.getColumnIndex() == 21) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            KGM_BEN.add(value_string);
                        }
                        if(cell.getColumnIndex() == 23) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            YD_TMK.add(value_string);
                        }
                        if(cell.getColumnIndex() == 24) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            ID_TMK.add(value_string);
                        }
                        if(cell.getColumnIndex() == 26) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            KGM_TMK.add(value_string);
                        }
                        if(cell.getColumnIndex() == 28) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            YD_HUB.add(value_string);
                        }
                        if(cell.getColumnIndex() == 29) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            ID_HUB.add(value_string);
                        }
                        if(cell.getColumnIndex() == 33) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            KGM_HUB.add(value_string);
                        }
                        if(cell.getColumnIndex() == 35) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            YD_OVA.add(value_string);
                        }
                        if(cell.getColumnIndex() == 36) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            ID_OVA.add(value_string);
                        }
                        if(cell.getColumnIndex() == 38) {
                            String value_string = cell.toString();
                            if(!"-".equals(value_string) && !value_string.isEmpty())
                            KGM_OVA.add(value_string);
                        }
                }

            }
        }
        
        if (KCKR.exists()) {
            FileInputStream file_two = new FileInputStream(new File(KCKR_EXCEL));
            XSSFWorkbook workbook_two = new XSSFWorkbook(file_two);
            XSSFSheet sheet_two = workbook_two.getSheetAt(0);

            for(int rowIndex = 8; rowIndex <= 1700; rowIndex ++) {
                XSSFRow row = sheet_two.getRow(rowIndex);
                for(int columnIndex = 0; columnIndex < 9; columnIndex ++) {
                    XSSFCell cell = row.getCell(columnIndex);
                    if(cell.getColumnIndex() == 3) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KCKR_EURO_TON_ALCH_BAT.add(value);
                    }
                    if(cell.getColumnIndex() == 4) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KCKR_EURO_TON_ESW.add(value);
                    }
                    if(cell.getColumnIndex() == 5) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KCKR_EURO_TON_HUBEI.add(value);
                    }
                    if(cell.getColumnIndex() == 6) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KCKR_EURO_TON_MORAVIA.add(value);
                    }
                    if(cell.getColumnIndex() == 7) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KCKR_EURO_TON_PIPEX.add(value);
                    }
                    if(cell.getColumnIndex() == 8) {
                        double value;
                        String value_string = cell.toString();
                         if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KCKR_EURO_TON_PT.add(value);
                    }
                    if(cell.getColumnIndex() == 0) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KCKR_DIMENSION.add(value);
                    }
                    if(cell.getColumnIndex() == 1) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                        value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KCKR_GODSTJOCKLEK.add(value);
                    }
                }
            }
        }
        
        if (KKR.exists()) {
            FileInputStream file_three = new FileInputStream(new File(KKR_EXCEL));
            XSSFWorkbook workbook_three = new XSSFWorkbook(file_three);
            XSSFSheet sheet_three = workbook_three.getSheetAt(0);

            for(int rowIndex = 6; rowIndex <= 435; rowIndex ++) {
                XSSFRow row = sheet_three.getRow(rowIndex);
                for(int columnIndex = 0; columnIndex < 13; columnIndex ++) {
                    XSSFCell cell = row.getCell(columnIndex);
                    if(cell.getColumnIndex() == 5) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_EURO_TON_ARVEDI.add(value);
                    }
                    if(cell.getColumnIndex() == 6) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_EURO_TON_ALESSIO.add(value);
                    }
                    if(cell.getColumnIndex() == 7) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_EURO_TON_CORINTH.add(value);
                    }
                    if(cell.getColumnIndex() == 8) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_EURO_TON_ILVA.add(value);
                    }
                    if(cell.getColumnIndex() == 9) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_EURO_TON_MARCEGAGLIA.add(value);
                    }
                    if(cell.getColumnIndex() == 10) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_EURO_TON_SIDERALBA.add(value);
                    }
                    if(cell.getColumnIndex() == 11) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_EURO_TON_TATA.add(value);
                    }
                    if(cell.getColumnIndex() == 12) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_EURO_TON_TATA_NL.add(value);
                    }
                    if(cell.getColumnIndex() == 1) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_DIMENSION.add(value);
                    }
                    if(cell.getColumnIndex() == 2) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                            value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_DIMENSION_TVÅ.add(value);
                    }
                    if(cell.getColumnIndex() == 3) {
                        double value;
                        String value_string = cell.toString();
                        if(!"".equals(value_string) && !"FALSO".equals(value_string)) {
                        value = Double.parseDouble(value_string);
                        } else {
                            value = 0;
                        }
                        KKR_GODSTJOCKLEK.add(value);
                    }
                }
            }
        }
        
        /*
        FileInputStream file_four = new FileInputStream(new File(VKR_EXCEL));
        XSSFWorkbook workbook_four = new XSSFWorkbook(file_four);
        XSSFSheet sheet_four = workbook_four.getSheetAt(0);
        
        for(int rowIndex = 3; rowIndex <= 672; rowIndex ++) {
            XSSFRow row = sheet_four.getRow(rowIndex);
            for(int columnIndex = 0; columnIndex < 6; columnIndex ++) {
                XSSFCell cell = row.getCell(columnIndex);
                if(cell.getColumnIndex() == 0) {
                    double value;
                    String value_string = cell.toString();
                    if(!"".equals(value_string) && !"FALSO".equals(value_string) && !"-".equals(value_string)) {
                        value = Double.parseDouble(value_string);
                    } else {
                        value = 0;
                    }
                    VKR_DIMENSION.add(value);
                }
                if(cell.getColumnIndex() == 1) {
                    double value;
                    String value_string = cell.toString();
                    if(!"".equals(value_string) && !"FALSO".equals(value_string) && !"-".equals(value_string)) {
                        value = Double.parseDouble(value_string);
                    } else {
                        value = 0;
                    }
                    VKR_DIMENSION_TVÅ.add(value);
                }
                if(cell.getColumnIndex() == 2) {
                    double value;
                    String value_string = cell.toString();
                    if(!"".equals(value_string) && !"FALSO".equals(value_string) && !"-".equals(value_string)) {
                    value = Double.parseDouble(value_string);
                    } else {
                        value = 0;
                    }
                    VKR_GODSTJOCKLEK.add(value);
                }
                if(cell.getColumnIndex() == 3) {
                    double value;
                    String value_string = cell.toString();
                    if(!"".equals(value_string) && !"FALSO".equals(value_string) && !"-".equals(value_string)) {
                    value = Double.parseDouble(value_string);
                    } else {
                        value = 0;
                    }
                    VKR_EURO_TON_ARVEDI.add(value);
                }
                if(cell.getColumnIndex() == 4) {
                    double value;
                    String value_string = cell.toString();
                    if(!"".equals(value_string) && !"FALSO".equals(value_string) && !"-".equals(value_string)) {
                    value = Double.parseDouble(value_string);
                    } else {
                        value = 0;
                    }
                    VKR_EURO_TON_LORRAINE.add(value);
                }
                if(cell.getColumnIndex() == 5) {
                    double value;
                    String value_string = cell.toString();
                    if(!"".equals(value_string) && !"FALSO".equals(value_string) && !"-".equals(value_string)) {
                    value = Double.parseDouble(value_string);
                    } else {
                        value = 0;
                    }
                    VKR_EURO_TON_TATA.add(value);
                }
            }
        }
        */
    }
    
    public double getESW_Euro() {
        return ESW_Euro;
    }
    
    public double getVM_Euro() {
        return VM_Euro;
    }
    
    public double getVM_Euro2() {
        return VM_Euro;
    }
    
    public double getVM_Euro3() {
        return VM_Euro;
    }
    
    public double getBEN_Euro() {
        return BEN_Euro;
    }
    
    public double getTMK_Cold_Euro() {
        return TMK_Cold_Euro;
    }
        
    public double getTMK_Assel_Euro() {
        return TMK_Assel_Euro;
    }
    
    public double getHUB_Euro() {
        return HUB_Euro;
    }
    
    public double getOVA_Euro() {
        return OVA_Euro;
    }
}