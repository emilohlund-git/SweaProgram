/*
* Copyright © Emil Öhlund 2019
*/
package com.mycompany.dbprogrammet;

/* Importerar alla bibliotek och interna klasser från Java för att komma
*  åt de funktioner som jag behöver. 
 */
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.mycompany.firebasedb.functions.Functions;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/* Utgående bibliotek, kan komma att tas bort efter nästa release av Java.
*  Använder dessa för kryptering utav lösenord till .txt fil.
*  Samt för att dekryptera lösenordet när jag läser in det i programmet igen.
 */
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Emilohlund
 */
public final class Program extends javax.swing.JFrame implements Serializable, Cloneable {

    /*ALLA VARIABLAR*/

 /* DATABASEN */
    private static final String PATH = new File("").getAbsolutePath();
    private static final String JSONPATH = PATH + "\\service_account.json";
    private static final String DBURL = "https://FirebaseDB.firebaseio.com/";
    private static FileInputStream serviceAccount;
    private static FirebaseOptions options;
    private static GoogleCredentials credentials;
    private static Storage storage;
    private static FirebaseApp app;
    private static Firestore db;

    /* DECIMALFORMAT */
    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
    DecimalFormat format = (DecimalFormat) nf;
    DecimalFormat no_decimals = new DecimalFormat("##");

    /* URL */
    URL url;

    /* BOOLEANS */
    boolean harKundKöptTidigare = false;
    boolean laddatKalkyl = false;
    boolean ämnesrör;
    boolean laddatOffert = false;
    boolean euroPerTonSjälv = true;
    boolean selected = false;
    boolean ändrarPåRad = false;

    /* DOUBLES */
    double valuta;
    double total_vikt;
    double antal;
    double längd;
    double pris_kund_inklusive;
    double pris_kund_inklusive_total;
    double prod_total_styck;
    double prod_total;
    double kostnad_styck_exkl;
    double total_leverans;
    double frakt_styck;
    double frakt_total;
    double kostnad_styck_inkl;
    double kostnad_leverans_inkl;
    double kund_pris_exklusive;
    double kund_pris_inklusive;
    double kund_pris_kilo_exklusive;
    double kund_pris_kilo_inklusive;
    double kund_pris_leverans;
    double spill;
    double material_form;
    double tolerans;
    double material;
    double kap_typ;
    double H23;
    double H24;
    double inställningar_bandkostnad;
    double inställningar_bandets_livslängd;
    double inställningar_upp_nergång_mm_sekund;
    double inställningar_rörframmatning;
    double inställningar_ställtid;
    double inställningar_bandbyte;
    double inställningar_toleransvidd_över_1mm;
    double inställningar_toleransvidd_1mm_mindre;
    double inställningar_lätta_material;
    double inställningar_hårda_material;
    double inställningar_hydraulik;
    double inställningar_seriekapning;
    double gerkap_faktor;
    double rör_diameter;
    double godstjocklek_kalkyl;
    double antal_snitt;
    double kaplängd;
    double matning;
    double godstjocklek;
    double yta;
    double diameter_ifyllt;
    double godstjocklek_ifyllt;
    double antal_snitt_ifyllt;
    double kaplängd_ifyllt;
    double summa_alla_parametrar;
    double H25;
    double ställ;
    double verktygsbyte;
    double grund_timkostnad;
    double tillägg_avdrag_homogent_etc;
    double timkostnad_till_kalkyl;
    double livslängd;
    double bandets_pris;
    double total_yta;
    double antal_band;
    double frammatningstid_mm_sec;
    double upp_ner_rörelser;
    double kaptid;
    double frammatningstid_min;
    double total_tid;
    double J31 = 0;
    double cykeltid;
    double totaltid;
    double kostnad_per_bit;
    double valutakurs, vikt_per_meter, euro_per_ton = 0;
    double get_spill = 0;
    double återanskaffningsvärde = 0;
    double full_kvantitet;
    double full_pris;
    double påslag = 0;
    double ställkostnad, produktions_kostnad_styck = 0;
    double total_produktionskostnad_styck = 0;
    double total_produktionskostnad = 0;
    double totalt_återanskaffningsvärde = 0;
    double återanskaffningsvärde_meter = 0;
    double styck_återanskaffningsvärde = 0;
    double påslag_totalt = 0;
    double påslag_styck = 0;
    double marginal = 0;
    double fraktkostnad_kilo = 0;
    double fraktkostnad_lagerhållare = 0;
    double fraktkostnad_styck = 0;
    double fraktkostnad_totalt = 0;
    double kostnad_per_styck_exklusive = 0;
    double total_kostnad_leverans = 0;
    double kostnad_per_styck_inklusive = 0;
    double kostnad_leverans_inklusive = 0;
    double kostnad_per_styck_kund = 0;
    double pris_kund_exklusive = 0;
    double pris_kund_leverans = 0;
    double pris_kund_kilo_exklusive = 0;
    double pris_kund_kilo_inklusive = 0;
    double full_marginal = 0;
    double total_längd;
    double total_antal;
    double totalvikt_total;
    double total_marginal;
    double marginal_styck;
    double marginal_leverans;
    double marginal_täckningsgrad;
    double vikt_per_styck;
    double längdfaktor = 1;
    double stålverk_faktor;
    double stålverk_euro_per_ton;

    /* STRING */
    public static final String DEFAULT_ENCODING = "UTF-8";
    String loaded_file = null;
    String nuvarande_val = "";
    String kund;
    String artikelnummer_offert;
    String path;
    String path2;
    String path3;
    String historik_path;
    String artikelnummer_dagens_datum_path;
    String nuvarande_offert;
    String lösenordet = "olle";
    String nuvarande_distrikt;
    String nuvarande_ansvarig;
    String valdRad;

    // STRING ARRAY
    String[] s;
    String[] ansvariga = {
        "Thomas", 
        "Mia", 
        "Mattias", 
        "Återförsäljare", 
        "Export", 
        "Johan", 
        "Ola", 
        "Magnus"
    };
    String[] distrikt = {
        "Distrikt 1",
        "Distrikt 2",
        "Distrikt 3",
        "Distrikt 4",
        "Distrikt 5",
        "Distrikt 6",
        "Distrikt 7",
        "Distrikt 8"
    };

    // STRING ARRAYLIST
    ArrayList<String> euro_per_ton_list = new ArrayList<>();
    ArrayList<String> valutakurs_list = new ArrayList<>();
    ArrayList<String> artikelnummer_list = new ArrayList<>();
    ArrayList<String> kategorier_list = new ArrayList<>();
    ArrayList<String> dimensioner_list = new ArrayList<>();
    ArrayList<String> total_vikt_list = new ArrayList<>();
    ArrayList<String> antal_list = new ArrayList<>();
    ArrayList<String> längd_list = new ArrayList<>();
    ArrayList<String> tolerans_list = new ArrayList<>();
    ArrayList<String> pris_styck_list = new ArrayList<>();
    ArrayList<String> gerkap_combobox_spara = new ArrayList<>();
    ArrayList<String> material_kvalitet_combobox_spara = new ArrayList<>();
    ArrayList<String> bandtyp_combobox_spara = new ArrayList<>();
    ArrayList<String> valutakurs_field_spara = new ArrayList<>();
    ArrayList<String> stålkvalitet_field_spara = new ArrayList<>();
    ArrayList<String> stålverk_field_spara = new ArrayList<>();
    ArrayList<String> säljare_field_spara = new ArrayList<>();
    ArrayList<String> datum_field_spara = new ArrayList<>();
    ArrayList<String> artikelnummer_field_spara = new ArrayList<>();
    ArrayList<String> bearbetning_field_spara = new ArrayList<>();
    ArrayList<String> dimension_field_spara = new ArrayList<>();
    ArrayList<String> antal_field_spara = new ArrayList<>();
    ArrayList<String> längd_field_spara = new ArrayList<>();
    ArrayList<String> längdtolerans_field_spara = new ArrayList<>();
    ArrayList<String> euro_per_ton_field_spara = new ArrayList<>();
    ArrayList<String> ställkostnad_field_spara = new ArrayList<>();
    ArrayList<String> ursprungslängd_field_spara = new ArrayList<>();
    ArrayList<String> påslag_field_spara = new ArrayList<>();
    ArrayList<String> fraktkostnad_field_spara = new ArrayList<>();
    ArrayList<String> fraktkostnad_sicam_field_spara = new ArrayList<>();
    ArrayList<String> spill_field_spara = new ArrayList<>();
    ArrayList<String> gerkap_combobox_spara_två = new ArrayList<>();
    ArrayList<String> material_kvalitet_combobox_spara_två = new ArrayList<>();
    ArrayList<String> bandtyp_combobox_spara_två = new ArrayList<>();
    ArrayList<String> valutakurs_field_spara_två = new ArrayList<>();
    ArrayList<String> stålkvalitet_field_spara_två = new ArrayList<>();
    ArrayList<String> stålverk_field_spara_två = new ArrayList<>();
    ArrayList<String> säljare_field_spara_två = new ArrayList<>();
    ArrayList<String> datum_field_spara_två = new ArrayList<>();
    ArrayList<String> artikelnummer_field_spara_två = new ArrayList<>();
    ArrayList<String> bearbetning_field_spara_två = new ArrayList<>();
    ArrayList<String> dimension_field_spara_två = new ArrayList<>();
    ArrayList<String> antal_field_spara_två = new ArrayList<>();
    ArrayList<String> längd_field_spara_två = new ArrayList<>();
    ArrayList<String> längdtolerans_field_spara_två = new ArrayList<>();
    ArrayList<String> euro_per_ton_field_spara_två = new ArrayList<>();
    ArrayList<String> ställkostnad_field_spara_två = new ArrayList<>();
    ArrayList<String> ursprungslängd_field_spara_två = new ArrayList<>();
    ArrayList<String> påslag_field_spara_två = new ArrayList<>();
    ArrayList<String> fraktkostnad_field_spara_två = new ArrayList<>();
    ArrayList<String> fraktkostnad_sicam_field_spara_två = new ArrayList<>();
    ArrayList<String> spill_field_spara_två = new ArrayList<>();
    ArrayList<String> index_list = new ArrayList<>();
    ArrayList<String> offertNamn = new ArrayList<>();

    /* INT */
    int counter = 0;
    int mouseX;
    int mouseY;
    int färdig_counter = 0;
    int clicks = 0;
    int offert_nummer = 0;
    int försäljning_nummer = 0;
    int rad = 0;

    // INT ARRAYLIST
    ArrayList<Integer> x_list = new ArrayList<>();
    ArrayList<Integer> y_list = new ArrayList<>();
    ArrayList<Integer> width_list = new ArrayList<>();
    ArrayList<Integer> height_list = new ArrayList<>();

    /* COLOR */
    Color c = new Color(191, 220, 220);
    Color d = new Color(213, 232, 232);

    /* RECTANGLE */
    Rectangle mouse_rectangle;

    /* JPANEL */
    JPanel current_panel_one;
    JPanel current_panel_two;

    // JPANEL ARRAYLIST
    ArrayList<JPanel> panels = new ArrayList<>();
    ArrayList<JPanel> panels_two = new ArrayList<>();
    
    // JTABLE ARRAYLIST
    ArrayList<JTable> tables = new ArrayList<>();

    /* JTEXTFIELD */
    // JTEXTFIELD ARRAYLIST
    ArrayList<JTextField> fields = new ArrayList<>();

    /* DEFAULTTABLEMODEL */
    DefaultTableModel offert_model;
    DefaultTableModel priser_model;
    DefaultTableModel kostnader_model;
    DefaultTableModel övrigt_model;
    DefaultTableModel tidigare_model;
    DefaultTableModel alla_artiklar_model;
    DefaultTableModel färdig_model;

    // DEFAULTTABLEMODEL ARRAYLIST
    ArrayList<DefaultTableModel> models = new ArrayList<>();

    /* CLASS REFERENSER */
    Loggare loggare;
    ExcelReader excelreader;
    UpdateCheck UC = new UpdateCheck();
    Functions functions;

    /* BASE64ENCODER */
    static BASE64Encoder enc = new BASE64Encoder();
    static BASE64Decoder dec = new BASE64Decoder();

    /* CACHE */
    File cache = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "//cache//" + kryptera(kryptera(kryptera("cache"))) + ".txt");
    File cacheFolder = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "//cache//");
    HashMap<String, String> userCache = new HashMap<>();
    
    HashMap<String, String> radInformation = new HashMap<>();

    /**
     * Creates new form NewJFrame
     */
    public Program() {
        format.applyPattern("##.##");
        Connect();
        functions = new Functions();
        
        //LADDA ALLA KOMPONENTER I PROGRAMMET
        initComponents();
        
        AutoCompletion.enable(välj_kund_combobox);
        AutoCompletion.enable(stålkvalitet_combobox);

        /*
        * Förinställer toleransvidden, då det i dagsläget inte finns behov
        * i att ändra den. Görs inga kapningar med precision under 1 mm.
         */
        tolerans = 1;
        /*
        * Ger alla tabeller en table model för att t.ex. dynamiskt kunna lägga till rader.
         */
        offert_model = (DefaultTableModel) offert_table.getModel();
        alla_artiklar_model = (DefaultTableModel) alla_artiklar_table.getModel();
        tidigare_model = (DefaultTableModel) tidigare_table.getModel();
        färdig_model = (DefaultTableModel) färdig_table.getModel();
        övrigt_model = (DefaultTableModel) övrigt_table.getModel();
        kostnader_model = (DefaultTableModel) kostnader_table.getModel();
        priser_model = (DefaultTableModel) priser_table.getModel();

        /*
        * Lägger till kostnader, priser & övrigt tabellerna i models arraylistan.
        * Finns inget behov att lägga till de andra tabellerna då jag hanterar dem annorlunda.
         */
        models.add(kostnader_model);
        models.add(priser_model);
        models.add(övrigt_model);

        /*
        * Gör de komponenter som inte ska synas vid start, eller hela tiden, osynliga.
         */
        info_label.setVisible(false);
        övergång_icon.setVisible(false);
        välj_kund_button.setEnabled(false);
        lägg_till_kund_button.setEnabled(false);
        glömt_lösenord_label.setVisible(false);
        välj_distrikt_panel.setVisible(false);
        uppdatera_rad_button.setVisible(false);
        offert_label.setVisible(false);
        kilo_per_meter_panel.setVisible(false);
        kilo_per_meter_panel2.setVisible(false);
        kilo_per_meter_field.setVisible(false);

        /*
        * Initializerar loggare klassen, skickar loggar till help@emilohlund.se med den.
        * Det finns en get_info metod som mailar exception meddelandet (stacktrace) till mig.
         */
        loggare = new Loggare();

        /*
        * Initializerar klassen där jag läser av samtliga Excel dokument för att hämta bland annat
        * vikt per meter för ämnesrör, samt euro per ton för olika ståltyper.
        * Man kan fylla i det här själv i euro per ton rutan i kalkylen, men bekvämlighetssak att 
        * inte behöva öppna varje excel dokument
        * för att ta reda på det.
         */
        //HÄMTA FRÅN EXCEL
        excelreader = new ExcelReader();
        try {
            /* Laddar arraylistorna i excelreader klassen för referens till den här klassen */
            excelreader.getValue();
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
        * Behövde manuellt ställa in så man inte kan ändra värden i celler i tabellerna, samt
        * så att det inte går att flytta på kolumner, och möjlighet att filtrera rader i kolumner.
         */
        initializera_kolumner();
        /*
        * Laddar inställningar för bandinställningarna, samt hämtar lösenordet till den rutan.
         */
        ladda_inställningar();
        
        if(internet_uppkoppling()) {
            setIconImages();
        }
    }
    
    private void setFrameIcon (String path) {
        try {
            File file = new File(path);
            Image image = ImageIO.read(file);
            this.setIconImage(image);
            InställningarFönster.setIconImage(image);
            FärgFönster.setIconImage(image);
            VäljKundFönster.setIconImage(image);
            Orderfönster.setIconImage(image);
            FelRapportFönster.setIconImage(image);
            OffertFönster.setIconImage(image);
            LaddaOffertFönster.setIconImage(image);
            AnvändareFönster.setIconImage(image);    
            LösenordFönster.setIconImage(image);
            FärdigställFörsäljningFönster.setIconImage(image);
            EPostFönster.setIconImage(image);
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getImageIcon (String path, JLabel label) {
        try {
            File file = new File(path);
            Image image = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(image);
            label.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setIconImages() {
        String användarePath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/user_male_circle_26px.png";
        String lösenordPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/password_26px.png";
        String epostPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/email_26px.png";
        String databasPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/database_26px.png";
        String databasPlusPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/add_database_26px.png";
        String databasKryssPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/delete_database_26px.png";
        String kundPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/gender_neutral_user_26px.png";
        String kundVitPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/user_male_circle_white_26px.png";
        String infoPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/info_filled_16px.png";
        String färgPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/paint_brush_26px.png";
        String distriktPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/double_right_26px.png";
        String bugPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/laptop_bug_26px.png";
        String sökPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/search_12px.png";
        String väljOffertPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/sell_26px.png";
        String trashPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/trash_26px.png";
        String försäljningPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/sales_performance_26px.png";
        String kundUppgifterPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/contacts_26px.png";
        String filPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/file_16px.png";
        String iconPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/images/swea.png";
        getImageIcon(användarePath, användare_icon);
        getImageIcon(användarePath, användare_icon1);
        getImageIcon(användarePath, användare_icon2);
        getImageIcon(lösenordPath, lösenord_icon);
        getImageIcon(lösenordPath, lösenord_icon1);
        getImageIcon(epostPath, epost_icon);
        getImageIcon(epostPath, epost_icon1);
        getImageIcon(databasPath, databas_icon);
        getImageIcon(databasPath, distrikt_label);
        getImageIcon(databasPlusPath, databas_plus_icon);
        getImageIcon(databasKryssPath, databas_kryss_icon);
        getImageIcon(kundPath, kund_label);
        getImageIcon(kundVitPath, kundDashboardLabel);
        getImageIcon(infoPath, info_label);
        getImageIcon(färgPath, färg_icon);
        getImageIcon(distriktPath, övergång_icon);
        getImageIcon(distriktPath, övergång_icon1);
        getImageIcon(distriktPath, välj_distrikt_icon);
        getImageIcon(bugPath, bug_icon);
        getImageIcon(sökPath, sök_tabel_label);
        getImageIcon(sökPath, sök_tabel_label1);
        getImageIcon(väljOffertPath, välj_offert_label);
        getImageIcon(trashPath, rensa_tabel_label);
        getImageIcon(kundUppgifterPath, uppgifterDashboardLabel);
        getImageIcon(filPath, offert_namn_label);
        setFrameIcon(iconPath);
    }
    
    private void Connect() {
        try {
            serviceAccount = new FileInputStream(JSONPATH);
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DBURL)
                    .build();
            credentials = GoogleCredentials.fromStream(new FileInputStream(JSONPATH))
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            app = FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException ex) {
            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FelmeddelandeFönster = new javax.swing.JDialog();
        felmeddelande_label = new java.awt.Label();
        felmeddelande_ok_knapp = new javax.swing.JButton();
        InställningarFönster = new javax.swing.JFrame();
        inställningar_panel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        label28 = new java.awt.Label();
        label29 = new java.awt.Label();
        bandkostnad = new javax.swing.JTextField();
        bandets_livslängd = new javax.swing.JTextField();
        label12 = new java.awt.Label();
        label13 = new java.awt.Label();
        label30 = new java.awt.Label();
        jSeparator3 = new javax.swing.JSeparator();
        label31 = new java.awt.Label();
        label32 = new java.awt.Label();
        upp_nergång_mm_sekund = new javax.swing.JTextField();
        rörframmatning = new javax.swing.JTextField();
        label33 = new java.awt.Label();
        label34 = new java.awt.Label();
        label35 = new java.awt.Label();
        jSeparator4 = new javax.swing.JSeparator();
        label36 = new java.awt.Label();
        label37 = new java.awt.Label();
        label38 = new java.awt.Label();
        ställ_tid = new javax.swing.JTextField();
        bandbyte = new javax.swing.JTextField();
        label40 = new java.awt.Label();
        jSeparator5 = new javax.swing.JSeparator();
        label41 = new java.awt.Label();
        label42 = new java.awt.Label();
        label43 = new java.awt.Label();
        label44 = new java.awt.Label();
        toleransvidd = new javax.swing.JTextField();
        toleransvidd_över_1mm = new javax.swing.JTextField();
        toleransvidd_under_1mm = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        diameter_text = new javax.swing.JTextField();
        godstjocklek_text = new javax.swing.JTextField();
        label3 = new java.awt.Label();
        label6 = new java.awt.Label();
        label7 = new java.awt.Label();
        label4 = new java.awt.Label();
        antal_snitt_text = new javax.swing.JTextField();
        kaplängd_text = new javax.swing.JTextField();
        seriekapning = new javax.swing.JTextField();
        label57 = new java.awt.Label();
        label56 = new java.awt.Label();
        label52 = new java.awt.Label();
        hydraulik = new javax.swing.JTextField();
        vanlig = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        label54 = new java.awt.Label();
        min_antal = new javax.swing.JTextField();
        label53 = new java.awt.Label();
        max_längd = new javax.swing.JTextField();
        label55 = new java.awt.Label();
        label51 = new java.awt.Label();
        jSeparator8 = new javax.swing.JSeparator();
        svåra_material = new javax.swing.JTextField();
        label50 = new java.awt.Label();
        label49 = new java.awt.Label();
        lätta_material = new javax.swing.JTextField();
        välj_material = new javax.swing.JTextField();
        label47 = new java.awt.Label();
        jSeparator7 = new javax.swing.JSeparator();
        label48 = new java.awt.Label();
        jSeparator10 = new javax.swing.JSeparator();
        label39 = new java.awt.Label();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        label45 = new java.awt.Label();
        lösenordet_field = new javax.swing.JTextField();
        LösenordFönster = new javax.swing.JDialog();
        lösenord_krävs_label = new javax.swing.JLabel();
        lösenord_passwordfield = new javax.swing.JPasswordField();
        lösenord_button = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        glömt_lösenord_label = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        FärgFönster = new javax.swing.JFrame();
        färg_panel = new javax.swing.JPanel();
        färg_väljare = new javax.swing.JColorChooser();
        färg_icon = new javax.swing.JLabel();
        färg_label = new javax.swing.JLabel();
        VäljKundFönster = new javax.swing.JFrame();
        välj_kund_panel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        välj_kund_combobox = new javax.swing.JComboBox<>();
        lägg_till_kund_button1 = new javax.swing.JButton();
        välj_kund_button = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lägg_till_kund_panel = new javax.swing.JPanel();
        lägg_till_kund_button = new javax.swing.JButton();
        lägg_till_kund_field = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        databas_plus_icon = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        databas_icon = new javax.swing.JLabel();
        databas_kryss_icon = new javax.swing.JLabel();
        laddar_panel = new javax.swing.JPanel();
        ladda_progressbar = new javax.swing.JProgressBar();
        jLabel7 = new javax.swing.JLabel();
        loading_text_field = new javax.swing.JTextField();
        välj_distrikt_panel = new javax.swing.JPanel();
        välj_distrikt_combobox = new javax.swing.JComboBox<>();
        välj_distrikt_button = new javax.swing.JButton();
        välj_distrikt_icon = new javax.swing.JLabel();
        Orderfönster = new javax.swing.JFrame();
        färdig_panel = new javax.swing.JPanel();
        kund_färdig_label = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        färdig_table = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        sammanräknad_total_vikt_field = new javax.swing.JTextField();
        sammanräknat_antal_field = new javax.swing.JTextField();
        sammanräknat_pris_per_styck_field = new javax.swing.JTextField();
        sammanräknad_marginal_field = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        skapa_offert_button = new javax.swing.JButton();
        FärdigställFörsäljningFönster = new javax.swing.JDialog();
        färdigställFörsäljningPanel = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel22 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        FelRapportFönster = new javax.swing.JFrame();
        mail_panel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        meddelande_area = new javax.swing.JTextArea();
        skicka_mail_button = new javax.swing.JButton();
        ämne_label = new javax.swing.JLabel();
        problemet_field = new javax.swing.JTextField();
        bug_icon = new javax.swing.JLabel();
        jSeparator19 = new javax.swing.JSeparator();
        ämne_label1 = new javax.swing.JLabel();
        tidpunkt_field = new javax.swing.JTextField();
        jSeparator20 = new javax.swing.JSeparator();
        ämne_label2 = new javax.swing.JLabel();
        ämne_label3 = new javax.swing.JLabel();
        OffertFönster = new javax.swing.JFrame();
        offertPanel = new javax.swing.JPanel();
        offert_namn_label = new javax.swing.JLabel();
        offert_namn_field = new javax.swing.JTextField();
        jSeparator29 = new javax.swing.JSeparator();
        offert_namn_label1 = new javax.swing.JLabel();
        referens_field = new javax.swing.JTextField();
        jSeparator30 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        certifikat_textfield = new javax.swing.JTextField();
        jSeparator31 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        transport_textfield = new javax.swing.JTextField();
        jSeparator32 = new javax.swing.JSeparator();
        betalningsvillkor_combobox = new javax.swing.JComboBox<>();
        leveransvillkor_combobox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        offert_scrollpane = new javax.swing.JScrollPane();
        offert_textarea = new javax.swing.JTextArea();
        offert_klar_button = new javax.swing.JButton();
        offert_stäng_button = new javax.swing.JButton();
        LaddaOffertFönster = new javax.swing.JFrame();
        ladda_offert_panel = new javax.swing.JPanel();
        välj_offert_combobox = new javax.swing.JComboBox<>();
        välj_offert_label = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        AnvändareFönster = new javax.swing.JFrame();
        användare_panel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lägg_till_användare_button4 = new javax.swing.JLabel();
        lägg_till_användare_button = new javax.swing.JToggleButton();
        jSeparator14 = new javax.swing.JSeparator();
        e_post_field = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        användarlösenord_passwordfield = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        användare_field = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        användare_icon = new javax.swing.JLabel();
        lösenord_icon = new javax.swing.JLabel();
        epost_icon = new javax.swing.JLabel();
        existerande_panel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lägg_till_användare_button2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        logga_in_användare_button = new javax.swing.JToggleButton();
        jSeparator16 = new javax.swing.JSeparator();
        existerande_passwordfield = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        existerande_field = new javax.swing.JTextField();
        användarnamn_lable3 = new javax.swing.JLabel();
        lösenord_icon1 = new javax.swing.JLabel();
        användare_icon1 = new javax.swing.JLabel();
        kom_ihåg_mig_radio = new javax.swing.JRadioButton();
        EPostFönster = new javax.swing.JDialog();
        e_post_panel = new javax.swing.JPanel();
        e_post_field1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        e_post_field2 = new javax.swing.JTextField();
        skicka_button = new javax.swing.JButton();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        användare_icon2 = new javax.swing.JLabel();
        epost_icon1 = new javax.swing.JLabel();
        rensa_tabel_dialog = new javax.swing.JDialog();
        rensa_tabel_panel = new javax.swing.JPanel();
        rensa_tabel_label = new javax.swing.JLabel();
        rensa_tabel_button = new javax.swing.JButton();
        avbryt_rensa_tabel_button = new javax.swing.JButton();
        KundDashboardFrame = new javax.swing.JFrame();
        kundDashBoardPanel = new javax.swing.JPanel();
        dashboardHeaderPanel = new javax.swing.JPanel();
        kundDashboardLabel = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        uppgifterDashboardPanel = new javax.swing.JPanel();
        uppgifterDashboardLabel = new javax.swing.JLabel();
        dashboardUppgifterPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        kundAdressField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        kundEpostField = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator25 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jSeparator26 = new javax.swing.JSeparator();
        kundSkapadField = new javax.swing.JTextField();
        taBortKundButton = new javax.swing.JButton();
        jSeparator27 = new javax.swing.JSeparator();
        kundAdressField1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        OrderOffertFönster = new javax.swing.JFrame();
        färdig_panel1 = new javax.swing.JPanel();
        kund_färdig_label1 = new javax.swing.JLabel();
        jSeparator28 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        offert_table = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        sammanräknad_total_vikt_field1 = new javax.swing.JTextField();
        sammanräknat_antal_field1 = new javax.swing.JTextField();
        sammanräknat_pris_per_styck_field1 = new javax.swing.JTextField();
        sammanräknad_marginal_field1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        ta_bort_offert_button1 = new javax.swing.JButton();
        skapa_offert_button1 = new javax.swing.JButton();
        FärdigställFörsäljningFönster1 = new javax.swing.JDialog();
        färdigställ_försäljning_panel1 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        kund_distrikt_panel = new javax.swing.JPanel();
        kund_label = new javax.swing.JLabel();
        övergång_icon = new javax.swing.JLabel();
        distrikt_label = new javax.swing.JLabel();
        info_label = new javax.swing.JLabel();
        övergång_icon1 = new javax.swing.JLabel();
        övergång_icon2 = new javax.swing.JLabel();
        övergång_icon3 = new javax.swing.JLabel();
        offert_label = new javax.swing.JLabel();
        panel_panel = new javax.swing.JPanel();
        påslag_panel = new javax.swing.JPanel();
        påslag_label = new javax.swing.JLabel();
        artikel_panel = new javax.swing.JPanel();
        artikelnummer_label = new javax.swing.JLabel();
        längd_panel = new javax.swing.JPanel();
        längd_label = new javax.swing.JLabel();
        ställkostnad_panel = new javax.swing.JPanel();
        ställkostnad_label = new javax.swing.JLabel();
        längdtolerans_panel = new javax.swing.JPanel();
        längdtolerans_label = new javax.swing.JLabel();
        antal_panel = new javax.swing.JPanel();
        antal_label = new javax.swing.JLabel();
        fraktkostnad_per_kilo_panel = new javax.swing.JPanel();
        fraktkostnad_label = new javax.swing.JLabel();
        fraktkostnad_sicam_panel = new javax.swing.JPanel();
        fraktkostnad_sicam_label = new javax.swing.JLabel();
        euro_per_ton_panel = new javax.swing.JPanel();
        euro_per_ton_label = new javax.swing.JLabel();
        dimension_panel = new javax.swing.JPanel();
        dimension_label = new javax.swing.JLabel();
        ursprungslängd_panel = new javax.swing.JPanel();
        ursprungslängd_label = new javax.swing.JLabel();
        säljare_panel = new javax.swing.JPanel();
        säljare_label = new javax.swing.JLabel();
        datum_panel = new javax.swing.JPanel();
        datum_label = new javax.swing.JLabel();
        spill_panel = new javax.swing.JPanel();
        spill_label = new javax.swing.JLabel();
        stålverk_field = new javax.swing.JTextField();
        stålkvalitet_field = new javax.swing.JTextField();
        valutakurs_field = new javax.swing.JTextField();
        spill_field = new javax.swing.JTextField();
        fraktkostnad_sicam_field = new javax.swing.JTextField();
        fraktkostnad_field = new javax.swing.JTextField();
        ursprungslängd_field = new javax.swing.JTextField();
        ställkostnad_field = new javax.swing.JTextField();
        euro_per_ton_field = new javax.swing.JTextField();
        längdtolerans_field = new javax.swing.JTextField();
        längd_field = new javax.swing.JTextField();
        antal_field = new javax.swing.JTextField();
        dimension_field = new javax.swing.JTextField();
        artikelnummer_field = new javax.swing.JTextField();
        datum_field = new javax.swing.JTextField();
        säljare_field = new javax.swing.JTextField();
        dimension_panel2 = new javax.swing.JPanel();
        dimension_mm_label = new javax.swing.JLabel();
        antal_panel2 = new javax.swing.JPanel();
        antal_styck_label = new javax.swing.JLabel();
        längd_panel2 = new javax.swing.JPanel();
        längd_mm_label = new javax.swing.JLabel();
        längdtolerans_panel2 = new javax.swing.JPanel();
        längdtolerans_mm_label = new javax.swing.JLabel();
        euro_per_ton_panel2 = new javax.swing.JPanel();
        euro_per_ton_euro_label = new javax.swing.JLabel();
        ställkostnad_panel2 = new javax.swing.JPanel();
        ställkostnad_kr_label = new javax.swing.JLabel();
        ursprungslängd_panel2 = new javax.swing.JPanel();
        ursprungslängd_mm_label = new javax.swing.JLabel();
        påslag_panel2 = new javax.swing.JPanel();
        påslag_procent_label = new javax.swing.JLabel();
        fraktkostnad_per_kilo_panel2 = new javax.swing.JPanel();
        fraktkostnad_kr_kg_label = new javax.swing.JLabel();
        fraktkostnad_sicam_panel2 = new javax.swing.JPanel();
        fraktkostnad_sicam_kr_kg_label = new javax.swing.JLabel();
        spill_procent_panel2 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        valutakurs_panel2 = new javax.swing.JPanel();
        valutakurs_kr_label = new javax.swing.JLabel();
        bearbetning_panel2 = new javax.swing.JPanel();
        bearbetning_kr_kg_label = new javax.swing.JLabel();
        bearbetning_field = new javax.swing.JTextField();
        bearbetning_panel = new javax.swing.JPanel();
        bearbetning_label = new javax.swing.JLabel();
        material_kvalitet_combobox = new javax.swing.JComboBox<>();
        bandtyp_combobox = new javax.swing.JComboBox<>();
        påslag_field = new javax.swing.JTextField();
        gerkap_combobox = new javax.swing.JComboBox<>();
        spara_artikelnummer_button = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        valutakurs_combobox = new javax.swing.JComboBox<>();
        stålkvalitet_combobox = new javax.swing.JComboBox<>();
        stålverk_combobox = new javax.swing.JComboBox<>();
        jSeparator21 = new javax.swing.JSeparator();
        kilo_per_meter_panel = new javax.swing.JPanel();
        kilo_per_meter_label = new javax.swing.JLabel();
        kilo_per_meter_field = new javax.swing.JTextField();
        kilo_per_meter_panel2 = new javax.swing.JPanel();
        kilo_per_meter_label2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        kostnader_table = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        priser_table = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        övrigt_table = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        avtalad_kvantitet_panel = new javax.swing.JPanel();
        kvantitet_år_panel = new javax.swing.JPanel();
        kvantitet_år_field = new javax.swing.JTextField();
        kvantitet_per_år_label = new javax.swing.JLabel();
        marginal_år_panel = new javax.swing.JPanel();
        marginal_år_field = new javax.swing.JTextField();
        marginal_per_år_label = new javax.swing.JLabel();
        omsättning_år_panel = new javax.swing.JPanel();
        omsättning_år_field = new javax.swing.JTextField();
        omsättning_per_år_label = new javax.swing.JLabel();
        ton_år_panel = new javax.swing.JPanel();
        ton_år_field = new javax.swing.JTextField();
        ton_per_år_label = new javax.swing.JLabel();
        försäljning_hittils_panel = new javax.swing.JPanel();
        kvantitet_hittils_panel = new javax.swing.JPanel();
        kvantitet_hittils_field = new javax.swing.JTextField();
        kvantitet_hittils_label = new javax.swing.JLabel();
        belopp_hittils_panel = new javax.swing.JPanel();
        vinst_hittils_field = new javax.swing.JTextField();
        belopp_hittils_label = new javax.swing.JLabel();
        aktuell_försäljningsruta_panel = new javax.swing.JPanel();
        marginal_total_panel = new javax.swing.JPanel();
        marginal_total_field = new javax.swing.JTextField();
        marginal_total_label = new javax.swing.JLabel();
        marginal_styck_panel = new javax.swing.JPanel();
        marginal_styck_field = new javax.swing.JTextField();
        marginal_styck_label = new javax.swing.JLabel();
        täckningsgrad_panel = new javax.swing.JPanel();
        täckningsgrad_field = new javax.swing.JTextField();
        täckningsgrad_label = new javax.swing.JLabel();
        totalvikt_panel = new javax.swing.JPanel();
        totalvikt_field = new javax.swing.JTextField();
        totalvikt_label = new javax.swing.JLabel();
        vikt_styck_panel = new javax.swing.JPanel();
        viktstyck_field = new javax.swing.JTextField();
        vikt_styck_label = new javax.swing.JLabel();
        pris_styck_panel = new javax.swing.JPanel();
        prisstyck_field = new javax.swing.JTextField();
        pris_styck_label = new javax.swing.JLabel();
        pris_totalt_panel = new javax.swing.JPanel();
        pristotalt_field = new javax.swing.JTextField();
        pris_totalt_label = new javax.swing.JLabel();
        cykeltid_text1 = new javax.swing.JTextField();
        cykeltid_label = new java.awt.Label();
        totaltid_label = new java.awt.Label();
        kostnadperbit_label = new java.awt.Label();
        kostnad_per_bit_text1 = new javax.swing.JTextField();
        totaltid_text1 = new javax.swing.JTextField();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        jSeparator24 = new javax.swing.JSeparator();
        tidigare_försäljning_panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tidigare_table = new javax.swing.JTable();
        förändring_i_procent_panel = new javax.swing.JPanel();
        förändring_field_procent = new javax.swing.JTextField();
        förändring_i_procent_label = new javax.swing.JLabel();
        förändring_i_kr_panel = new javax.swing.JPanel();
        förändring_i_kr_field = new javax.swing.JTextField();
        förändring_i_kr_label = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        sök_tabel_label1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        alla_artiklar_table = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        sök_tabel_label = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        för_till_orderfönster_button = new javax.swing.JButton();
        ta_bort_rad_button = new javax.swing.JButton();
        rensa_tabell_button = new javax.swing.JButton();
        till_orderfönster_button = new javax.swing.JButton();
        uppdatera_rad_button = new javax.swing.JButton();
        meny_menubar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        Klingkap_meny = new javax.swing.JCheckBoxMenuItem();
        Plockorder_meny = new javax.swing.JCheckBoxMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        FelmeddelandeFönster.setTitle("Felmeddelande");
        FelmeddelandeFönster.setAlwaysOnTop(true);
        FelmeddelandeFönster.setResizable(false);
        FelmeddelandeFönster.setType(java.awt.Window.Type.POPUP);

        felmeddelande_label.setText("Felaktigt värde.");

        felmeddelande_ok_knapp.setText("OK");
        felmeddelande_ok_knapp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                felmeddelande_ok_knappActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FelmeddelandeFönsterLayout = new javax.swing.GroupLayout(FelmeddelandeFönster.getContentPane());
        FelmeddelandeFönster.getContentPane().setLayout(FelmeddelandeFönsterLayout);
        FelmeddelandeFönsterLayout.setHorizontalGroup(
            FelmeddelandeFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FelmeddelandeFönsterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(felmeddelande_ok_knapp)
                .addContainerGap())
            .addGroup(FelmeddelandeFönsterLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(felmeddelande_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        FelmeddelandeFönsterLayout.setVerticalGroup(
            FelmeddelandeFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FelmeddelandeFönsterLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(felmeddelande_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(felmeddelande_ok_knapp)
                .addContainerGap())
        );

        FelmeddelandeFönster.getAccessibleContext().setAccessibleDescription("Please enter value, or table is full.");

        InställningarFönster.setTitle("Inställningar");
        InställningarFönster.setBackground(new java.awt.Color(159, 201, 232));
        InställningarFönster.setResizable(false);
        InställningarFönster.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                InställningarFönsterWindowClosing(evt);
            }
        });

        inställningar_panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(245, 245, 245));

        label28.setForeground(new java.awt.Color(102, 102, 102));
        label28.setText("Bandkostnad");

        label29.setForeground(new java.awt.Color(102, 102, 102));
        label29.setText("Bandets livslängd");

        bandkostnad.setForeground(new java.awt.Color(102, 102, 102));
        bandkostnad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        bandkostnad.setText("750");
        bandkostnad.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        bandets_livslängd.setForeground(new java.awt.Color(102, 102, 102));
        bandets_livslängd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        bandets_livslängd.setText("5");
        bandets_livslängd.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        bandets_livslängd.setPreferredSize(new java.awt.Dimension(18, 14));

        label12.setForeground(new java.awt.Color(153, 153, 153));
        label12.setText("mm / sekund");

        label13.setForeground(new java.awt.Color(153, 153, 153));
        label13.setText("kvadratmeter");

        label30.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        label30.setForeground(new java.awt.Color(102, 102, 102));
        label30.setText("Maskin parametrar");

        label31.setForeground(new java.awt.Color(102, 102, 102));
        label31.setText("Upp / nergång");

        label32.setForeground(new java.awt.Color(102, 102, 102));
        label32.setText("Rörframmatning");

        upp_nergång_mm_sekund.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        upp_nergång_mm_sekund.setText("57");
        upp_nergång_mm_sekund.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        rörframmatning.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        rörframmatning.setText("12.5");
        rörframmatning.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        label33.setForeground(new java.awt.Color(153, 153, 153));
        label33.setText("kronor");

        label34.setForeground(new java.awt.Color(153, 153, 153));
        label34.setText("mm / sekund");

        label35.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        label35.setForeground(new java.awt.Color(102, 102, 102));
        label35.setText("Ställtider med mera");

        label36.setForeground(new java.awt.Color(102, 102, 102));
        label36.setText("Ställtid");

        label37.setForeground(new java.awt.Color(102, 102, 102));
        label37.setText("Bandbyte");

        label38.setForeground(new java.awt.Color(153, 153, 153));
        label38.setText("minuter");

        ställ_tid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ställ_tid.setText("10");
        ställ_tid.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        bandbyte.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        bandbyte.setText("15");
        bandbyte.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        label40.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        label40.setForeground(new java.awt.Color(102, 102, 102));
        label40.setText("Kostpris kronor per timme");

        label41.setForeground(new java.awt.Color(102, 102, 102));
        label41.setText("Välj toleransvidd");

        label42.setForeground(new java.awt.Color(102, 102, 102));
        label42.setText("Toleransvidd över 1mm");

        label43.setForeground(new java.awt.Color(102, 102, 102));
        label43.setText("Toleransvidd 1mm eller mindre");

        label44.setForeground(new java.awt.Color(153, 153, 153));
        label44.setText("minuter");

        toleransvidd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        toleransvidd.setText("0");

        toleransvidd_över_1mm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        toleransvidd_över_1mm.setText("567");

        toleransvidd_under_1mm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        toleransvidd_under_1mm.setText("680");

        jPanel12.setBackground(new java.awt.Color(245, 245, 245));

        diameter_text.setEditable(false);

        godstjocklek_text.setEditable(false);

        label3.setText("Rör diameter");

        label6.setText("Gods tjocklek");

        label7.setText("Kaplängd");

        label4.setText("Antal snitt");

        antal_snitt_text.setEditable(false);

        kaplängd_text.setEditable(false);

        seriekapning.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        seriekapning.setText("50");

        label57.setForeground(new java.awt.Color(102, 102, 102));
        label57.setText("Seriekapning korta bitar");

        label56.setForeground(new java.awt.Color(102, 102, 102));
        label56.setText("Hydraulik");

        label52.setForeground(new java.awt.Color(102, 102, 102));
        label52.setText("Vanlig");

        hydraulik.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        hydraulik.setText("91");

        vanlig.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vanlig.setText("100");

        label54.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        label54.setForeground(new java.awt.Color(102, 102, 102));
        label54.setText("Kap typ (tillägg i procent)");

        min_antal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        min_antal.setText("49");

        label53.setForeground(new java.awt.Color(102, 102, 102));
        label53.setText("Min antal");

        max_längd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        max_längd.setText("151");

        label55.setForeground(new java.awt.Color(102, 102, 102));
        label55.setText("Max längd");

        label51.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        label51.setForeground(new java.awt.Color(102, 102, 102));
        label51.setText("Max längd, min antal på nattkap");

        svåra_material.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        svåra_material.setText("30");

        label50.setForeground(new java.awt.Color(102, 102, 102));
        label50.setText("Svåra material");

        label49.setForeground(new java.awt.Color(102, 102, 102));
        label49.setText("Lätta material");

        lätta_material.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lätta_material.setText("65");

        välj_material.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        välj_material.setText("0");

        label47.setForeground(new java.awt.Color(102, 102, 102));
        label47.setText("Välj material kvalitet");

        label48.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        label48.setForeground(new java.awt.Color(102, 102, 102));
        label48.setText("Material kvalitet, matning mm / minut");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(diameter_text, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(godstjocklek_text, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(antal_snitt_text, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kaplängd_text, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(label50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(svåra_material, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(välj_material)
                            .addComponent(lätta_material, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(label55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(max_längd, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(label53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(min_antal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(label57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(seriekapning, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(label52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(vanlig, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(label56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hydraulik, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(label48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(label47, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(välj_material, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lätta_material, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(svåra_material)
                    .addComponent(label50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(label51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(max_längd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label55, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(min_antal)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(label53, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(27, 27, 27)
                .addComponent(label54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label52, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vanlig))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label56, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hydraulik))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(seriekapning)
                        .addGap(1, 1, 1))
                    .addComponent(label57, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(diameter_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(godstjocklek_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(antal_snitt_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kaplängd_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        label39.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        label39.setForeground(new java.awt.Color(102, 102, 102));
        label39.setText("Bandet");

        label45.setForeground(new java.awt.Color(102, 102, 102));
        label45.setText("Lösenordet:");

        lösenordet_field.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addGap(0, 4, Short.MAX_VALUE)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(95, 95, 95)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(bandkostnad)
                                        .addGap(46, 46, 46)
                                        .addComponent(label33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(bandets_livslängd, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(label36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ställ_tid, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(label37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bandbyte, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator4)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rörframmatning, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                                    .addComponent(upp_nergång_mm_sekund))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(toleransvidd_under_1mm, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(toleransvidd)
                                        .addComponent(toleransvidd_över_1mm, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(label45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lösenordet_field, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(label33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(label28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bandkostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(label29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bandets_livslängd, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(25, 25, 25)
                        .addComponent(label30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(label31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(upp_nergång_mm_sekund, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rörframmatning, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(label35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(label38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(ställ_tid, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(bandbyte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(label40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(toleransvidd, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(toleransvidd_över_1mm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label43, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toleransvidd_under_1mm, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lösenordet_field, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout inställningar_panelLayout = new javax.swing.GroupLayout(inställningar_panel);
        inställningar_panel.setLayout(inställningar_panelLayout);
        inställningar_panelLayout.setHorizontalGroup(
            inställningar_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inställningar_panelLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        inställningar_panelLayout.setVerticalGroup(
            inställningar_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout InställningarFönsterLayout = new javax.swing.GroupLayout(InställningarFönster.getContentPane());
        InställningarFönster.getContentPane().setLayout(InställningarFönsterLayout);
        InställningarFönsterLayout.setHorizontalGroup(
            InställningarFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InställningarFönsterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inställningar_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        InställningarFönsterLayout.setVerticalGroup(
            InställningarFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inställningar_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        LösenordFönster.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                LösenordFönsterWindowClosed(evt);
            }
        });

        lösenord_krävs_label.setText("Lösenord krävs:");

        lösenord_passwordfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lösenord_passwordfieldActionPerformed(evt);
            }
        });
        lösenord_passwordfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lösenord_passwordfieldKeyReleased(evt);
            }
        });

        lösenord_button.setText("OK");
        lösenord_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lösenord_buttonActionPerformed(evt);
            }
        });

        glömt_lösenord_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        glömt_lösenord_label.setForeground(new java.awt.Color(0, 102, 204));
        glömt_lösenord_label.setText("Jag har glömt lösenordet.");
        glömt_lösenord_label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        glömt_lösenord_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                glömt_lösenord_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout LösenordFönsterLayout = new javax.swing.GroupLayout(LösenordFönster.getContentPane());
        LösenordFönster.getContentPane().setLayout(LösenordFönsterLayout);
        LösenordFönsterLayout.setHorizontalGroup(
            LösenordFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LösenordFönsterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LösenordFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator12)
                    .addGroup(LösenordFönsterLayout.createSequentialGroup()
                        .addGroup(LösenordFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lösenord_krävs_label)
                            .addGroup(LösenordFönsterLayout.createSequentialGroup()
                                .addComponent(lösenord_passwordfield, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lösenord_button))
                            .addComponent(jLabel4)
                            .addComponent(glömt_lösenord_label))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        LösenordFönsterLayout.setVerticalGroup(
            LösenordFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LösenordFönsterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lösenord_krävs_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LösenordFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lösenord_passwordfield, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lösenord_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(glömt_lösenord_label)
                .addGap(13, 13, 13)
                .addComponent(jLabel4))
        );

        FärgFönster.setAlwaysOnTop(true);
        FärgFönster.setResizable(false);

        färg_panel.setBackground(new java.awt.Color(255, 255, 255));

        färg_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        färg_label.setForeground(new java.awt.Color(0, 102, 153));
        färg_label.setText("Välj en färg och tryck på en panel för att färglägga den.");

        javax.swing.GroupLayout färg_panelLayout = new javax.swing.GroupLayout(färg_panel);
        färg_panel.setLayout(färg_panelLayout);
        färg_panelLayout.setHorizontalGroup(
            färg_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(färg_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(färg_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(färg_väljare, javax.swing.GroupLayout.PREFERRED_SIZE, 574, Short.MAX_VALUE)
                    .addGroup(färg_panelLayout.createSequentialGroup()
                        .addComponent(färg_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(färg_label)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        färg_panelLayout.setVerticalGroup(
            färg_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(färg_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(färg_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(färg_icon, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(färg_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(färg_väljare, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout FärgFönsterLayout = new javax.swing.GroupLayout(FärgFönster.getContentPane());
        FärgFönster.getContentPane().setLayout(FärgFönsterLayout);
        FärgFönsterLayout.setHorizontalGroup(
            FärgFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färg_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        FärgFönsterLayout.setVerticalGroup(
            FärgFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färg_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        VäljKundFönster.setResizable(false);
        VäljKundFönster.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                VäljKundFönsterWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                VäljKundFönsterWindowOpened(evt);
            }
        });

        välj_kund_panel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Välj existerande kund:");

        välj_kund_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        välj_kund_combobox.setForeground(new java.awt.Color(102, 102, 102));
        välj_kund_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sök kund" }));
        välj_kund_combobox.setBorder(null);
        välj_kund_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                välj_kund_comboboxActionPerformed(evt);
            }
        });

        lägg_till_kund_button1.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        lägg_till_kund_button1.setForeground(new java.awt.Color(102, 102, 102));
        lägg_till_kund_button1.setText("Starta");
        lägg_till_kund_button1.setBorder(null);
        lägg_till_kund_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lägg_till_kund_button1ActionPerformed(evt);
            }
        });

        välj_kund_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        välj_kund_button.setForeground(new java.awt.Color(102, 102, 102));
        välj_kund_button.setText("Välj kund");
        välj_kund_button.setBorder(null);
        välj_kund_button.setEnabled(false);
        välj_kund_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                välj_kund_buttonActionPerformed(evt);
            }
        });

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lägg_till_kund_panel.setBackground(new java.awt.Color(243, 243, 243));

        lägg_till_kund_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        lägg_till_kund_button.setForeground(new java.awt.Color(102, 102, 102));
        lägg_till_kund_button.setText("Lägg till");
        lägg_till_kund_button.setBorder(null);
        lägg_till_kund_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lägg_till_kund_buttonActionPerformed(evt);
            }
        });

        lägg_till_kund_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        lägg_till_kund_field.setForeground(new java.awt.Color(102, 102, 102));
        lägg_till_kund_field.setBorder(null);
        lägg_till_kund_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lägg_till_kund_fieldKeyReleased(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Namn:");

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Lägg till ny kund");

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        databas_plus_icon.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout lägg_till_kund_panelLayout = new javax.swing.GroupLayout(lägg_till_kund_panel);
        lägg_till_kund_panel.setLayout(lägg_till_kund_panelLayout);
        lägg_till_kund_panelLayout.setHorizontalGroup(
            lägg_till_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lägg_till_kund_panelLayout.createSequentialGroup()
                .addGroup(lägg_till_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lägg_till_kund_panelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(databas_plus_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lägg_till_kund_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(lägg_till_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lägg_till_kund_panelLayout.createSequentialGroup()
                        .addComponent(lägg_till_kund_field, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                        .addComponent(lägg_till_kund_button, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(lägg_till_kund_panelLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        lägg_till_kund_panelLayout.setVerticalGroup(
            lägg_till_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lägg_till_kund_panelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(lägg_till_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(databas_plus_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lägg_till_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel19)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lägg_till_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lägg_till_kund_field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lägg_till_kund_button, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("Snabbkalkyl");

        databas_icon.setBackground(new java.awt.Color(153, 153, 153));

        databas_kryss_icon.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout välj_kund_panelLayout = new javax.swing.GroupLayout(välj_kund_panel);
        välj_kund_panel.setLayout(välj_kund_panelLayout);
        välj_kund_panelLayout.setHorizontalGroup(
            välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(välj_kund_panelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(databas_kryss_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(välj_kund_panelLayout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(välj_kund_panelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(2, 2, 2)
                .addComponent(lägg_till_kund_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addComponent(lägg_till_kund_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(välj_kund_panelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(databas_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(välj_kund_panelLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(välj_kund_panelLayout.createSequentialGroup()
                        .addComponent(välj_kund_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(välj_kund_button, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
        );
        välj_kund_panelLayout.setVerticalGroup(
            välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(välj_kund_panelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel18)
                .addGap(16, 16, 16)
                .addGroup(välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(välj_kund_button, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(välj_kund_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel30)
                    .addComponent(databas_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addComponent(lägg_till_kund_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addGroup(välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addGroup(välj_kund_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lägg_till_kund_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(databas_kryss_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        laddar_panel.setBackground(new java.awt.Color(255, 255, 255));

        ladda_progressbar.setBackground(new java.awt.Color(0, 0, 0));
        ladda_progressbar.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 12)); // NOI18N
        ladda_progressbar.setForeground(new java.awt.Color(91, 189, 91));
        ladda_progressbar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ladda_progressbarStateChanged(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Laddar inställningar");

        loading_text_field.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 10)); // NOI18N
        loading_text_field.setForeground(new java.awt.Color(102, 102, 102));
        loading_text_field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        loading_text_field.setBorder(null);

        javax.swing.GroupLayout laddar_panelLayout = new javax.swing.GroupLayout(laddar_panel);
        laddar_panel.setLayout(laddar_panelLayout);
        laddar_panelLayout.setHorizontalGroup(
            laddar_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laddar_panelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(laddar_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ladda_progressbar, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                    .addComponent(loading_text_field))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, laddar_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        laddar_panelLayout.setVerticalGroup(
            laddar_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laddar_panelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ladda_progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(loading_text_field, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        välj_distrikt_panel.setBackground(new java.awt.Color(255, 255, 255));

        välj_distrikt_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        välj_distrikt_combobox.setForeground(new java.awt.Color(102, 102, 102));
        välj_distrikt_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Välj distrikt" }));
        välj_distrikt_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                välj_distrikt_comboboxActionPerformed(evt);
            }
        });

        välj_distrikt_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        välj_distrikt_button.setForeground(new java.awt.Color(102, 102, 102));
        välj_distrikt_button.setText("Välj distrikt");
        välj_distrikt_button.setEnabled(false);
        välj_distrikt_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                välj_distrikt_buttonActionPerformed(evt);
            }
        });

        välj_distrikt_icon.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout välj_distrikt_panelLayout = new javax.swing.GroupLayout(välj_distrikt_panel);
        välj_distrikt_panel.setLayout(välj_distrikt_panelLayout);
        välj_distrikt_panelLayout.setHorizontalGroup(
            välj_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(välj_distrikt_panelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(välj_distrikt_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(välj_distrikt_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                .addComponent(välj_distrikt_button)
                .addGap(28, 28, 28))
        );
        välj_distrikt_panelLayout.setVerticalGroup(
            välj_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(välj_distrikt_panelLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(välj_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(välj_distrikt_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(välj_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(välj_distrikt_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(välj_distrikt_button, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout VäljKundFönsterLayout = new javax.swing.GroupLayout(VäljKundFönster.getContentPane());
        VäljKundFönster.getContentPane().setLayout(VäljKundFönsterLayout);
        VäljKundFönsterLayout.setHorizontalGroup(
            VäljKundFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(laddar_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(välj_kund_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(välj_distrikt_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        VäljKundFönsterLayout.setVerticalGroup(
            VäljKundFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VäljKundFönsterLayout.createSequentialGroup()
                .addComponent(välj_kund_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(laddar_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(välj_distrikt_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Orderfönster.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                OrderfönsterWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                OrderfönsterWindowOpened(evt);
            }
        });

        färdig_panel.setBackground(new java.awt.Color(245, 245, 245));
        färdig_panel.setForeground(new java.awt.Color(167, 225, 239));

        kund_färdig_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        kund_färdig_label.setText("Kund");

        jButton5.setText("Försäljning");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setText("Tillbaka");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        färdig_table.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        färdig_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Artikelnummer", "Kategori", "Dimension", "Total vikt", "Antal", "Längd", "Tolerans", "Pris styck", "Pris leverans"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        färdig_table.setToolTipText("");
        färdig_table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                färdig_tablePropertyChange(evt);
            }
        });
        jScrollPane4.setViewportView(färdig_table);

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel15.setText("Total vikt:");

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel21.setText("Antal detaljer:");

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel23.setText("Ordervärde:");

        sammanräknad_total_vikt_field.setEditable(false);
        sammanräknad_total_vikt_field.setBackground(new java.awt.Color(255, 255, 255));

        sammanräknat_antal_field.setEditable(false);
        sammanräknat_antal_field.setBackground(new java.awt.Color(255, 255, 255));

        sammanräknat_pris_per_styck_field.setEditable(false);
        sammanräknat_pris_per_styck_field.setBackground(new java.awt.Color(255, 255, 255));

        sammanräknad_marginal_field.setEditable(false);
        sammanräknad_marginal_field.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel24.setText("Marginal:");

        jButton14.setText("Ta bort vald rad");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        skapa_offert_button.setText("Skapa offert");
        skapa_offert_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skapa_offert_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout färdig_panelLayout = new javax.swing.GroupLayout(färdig_panel);
        färdig_panel.setLayout(färdig_panelLayout);
        färdig_panelLayout.setHorizontalGroup(
            färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(färdig_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(färdig_panelLayout.createSequentialGroup()
                        .addComponent(kund_färdig_label, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                        .addGap(460, 460, 460))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdig_panelLayout.createSequentialGroup()
                        .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator6))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdig_panelLayout.createSequentialGroup()
                        .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(färdig_panelLayout.createSequentialGroup()
                                .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(sammanräknad_total_vikt_field, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(sammanräknat_antal_field, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sammanräknat_pris_per_styck_field, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sammanräknad_marginal_field, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton14))
                            .addGroup(färdig_panelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(skapa_offert_button)
                                .addGap(10, 10, 10)
                                .addComponent(jButton5)))
                        .addContainerGap())))
        );
        färdig_panelLayout.setVerticalGroup(
            färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(färdig_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kund_färdig_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton14)
                    .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdig_panelLayout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sammanräknad_total_vikt_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(färdig_panelLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sammanräknat_pris_per_styck_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(färdig_panelLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sammanräknad_marginal_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(färdig_panelLayout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sammanräknat_antal_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(39, 39, 39)
                .addGroup(färdig_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton8)
                    .addComponent(skapa_offert_button))
                .addContainerGap())
        );

        javax.swing.GroupLayout OrderfönsterLayout = new javax.swing.GroupLayout(Orderfönster.getContentPane());
        Orderfönster.getContentPane().setLayout(OrderfönsterLayout);
        OrderfönsterLayout.setHorizontalGroup(
            OrderfönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färdig_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        OrderfönsterLayout.setVerticalGroup(
            OrderfönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färdig_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        FärdigställFörsäljningFönster.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                FärdigställFörsäljningFönsterWindowOpened(evt);
            }
        });

        färdigställFörsäljningPanel.setBackground(new java.awt.Color(255, 255, 255));

        jButton17.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton17.setForeground(new java.awt.Color(102, 102, 102));
        jButton17.setText("Klar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(102, 102, 102));
        jButton6.setText("Stäng");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jDateChooser1.setForeground(new java.awt.Color(102, 102, 102));
        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooser1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 153));
        jLabel17.setText("Öppna kalendern för att välja ett datum.");

        jProgressBar1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jProgressBar1.setForeground(new java.awt.Color(0, 204, 0));
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("Ledigt");

        jButton12.setText("jButton12");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout färdigställFörsäljningPanelLayout = new javax.swing.GroupLayout(färdigställFörsäljningPanel);
        färdigställFörsäljningPanel.setLayout(färdigställFörsäljningPanelLayout);
        färdigställFörsäljningPanelLayout.setHorizontalGroup(
            färdigställFörsäljningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(färdigställFörsäljningPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(färdigställFörsäljningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(färdigställFörsäljningPanelLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdigställFörsäljningPanelLayout.createSequentialGroup()
                        .addGap(0, 370, Short.MAX_VALUE)
                        .addGroup(färdigställFörsäljningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdigställFörsäljningPanelLayout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton17))
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton12, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        färdigställFörsäljningPanelLayout.setVerticalGroup(
            färdigställFörsäljningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdigställFörsäljningPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGap(38, 38, 38)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(färdigställFörsäljningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        javax.swing.GroupLayout FärdigställFörsäljningFönsterLayout = new javax.swing.GroupLayout(FärdigställFörsäljningFönster.getContentPane());
        FärdigställFörsäljningFönster.getContentPane().setLayout(FärdigställFörsäljningFönsterLayout);
        FärdigställFörsäljningFönsterLayout.setHorizontalGroup(
            FärdigställFörsäljningFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färdigställFörsäljningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        FärdigställFörsäljningFönsterLayout.setVerticalGroup(
            FärdigställFörsäljningFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färdigställFörsäljningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mail_panel.setBackground(new java.awt.Color(255, 255, 255));

        meddelande_area.setColumns(20);
        meddelande_area.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        meddelande_area.setForeground(new java.awt.Color(102, 102, 102));
        meddelande_area.setLineWrap(true);
        meddelande_area.setRows(5);
        meddelande_area.setWrapStyleWord(true);
        meddelande_area.setBorder(null);
        jScrollPane6.setViewportView(meddelande_area);

        skicka_mail_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        skicka_mail_button.setForeground(new java.awt.Color(102, 102, 102));
        skicka_mail_button.setText("Skicka mail");
        skicka_mail_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skicka_mail_buttonActionPerformed(evt);
            }
        });

        ämne_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ämne_label.setForeground(new java.awt.Color(102, 102, 102));
        ämne_label.setText("Vart uppstod problemet:");

        problemet_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        problemet_field.setForeground(new java.awt.Color(102, 102, 102));
        problemet_field.setBorder(null);

        jSeparator19.setForeground(new java.awt.Color(204, 204, 204));

        ämne_label1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ämne_label1.setForeground(new java.awt.Color(102, 102, 102));
        ämne_label1.setText("Ange tidpunkt för händelsen:");

        tidpunkt_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tidpunkt_field.setForeground(new java.awt.Color(102, 102, 102));
        tidpunkt_field.setBorder(null);

        jSeparator20.setForeground(new java.awt.Color(204, 204, 204));

        ämne_label2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ämne_label2.setForeground(new java.awt.Color(102, 102, 102));
        ämne_label2.setText("Vad gjorde du i programmet när problemet uppstod?");

        ämne_label3.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ämne_label3.setForeground(new java.awt.Color(0, 102, 153));
        ämne_label3.setText("Jag kommer att återkomma så fort jag löst problemet.");

        javax.swing.GroupLayout mail_panelLayout = new javax.swing.GroupLayout(mail_panel);
        mail_panel.setLayout(mail_panelLayout);
        mail_panelLayout.setHorizontalGroup(
            mail_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mail_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mail_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator19)
                    .addComponent(jScrollPane6)
                    .addComponent(jSeparator20)
                    .addComponent(tidpunkt_field)
                    .addComponent(problemet_field)
                    .addGroup(mail_panelLayout.createSequentialGroup()
                        .addComponent(ämne_label3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addComponent(skicka_mail_button))
                    .addGroup(mail_panelLayout.createSequentialGroup()
                        .addGroup(mail_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ämne_label)
                            .addComponent(bug_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ämne_label2)
                            .addComponent(ämne_label1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mail_panelLayout.setVerticalGroup(
            mail_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mail_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bug_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ämne_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(problemet_field, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ämne_label2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(ämne_label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tidpunkt_field, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(mail_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ämne_label3)
                    .addComponent(skicka_mail_button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FelRapportFönsterLayout = new javax.swing.GroupLayout(FelRapportFönster.getContentPane());
        FelRapportFönster.getContentPane().setLayout(FelRapportFönsterLayout);
        FelRapportFönsterLayout.setHorizontalGroup(
            FelRapportFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mail_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        FelRapportFönsterLayout.setVerticalGroup(
            FelRapportFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mail_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        offertPanel.setBackground(new java.awt.Color(255, 255, 255));
        offertPanel.setForeground(new java.awt.Color(102, 102, 102));

        offert_namn_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        offert_namn_label.setForeground(new java.awt.Color(102, 102, 102));
        offert_namn_label.setText("Filnamn:");

        offert_namn_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        offert_namn_field.setBorder(null);
        offert_namn_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offert_namn_fieldActionPerformed(evt);
            }
        });

        jSeparator29.setForeground(new java.awt.Color(204, 204, 204));

        offert_namn_label1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        offert_namn_label1.setForeground(new java.awt.Color(102, 102, 102));
        offert_namn_label1.setText("Kund referens:");

        referens_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        referens_field.setForeground(new java.awt.Color(102, 102, 102));
        referens_field.setBorder(null);

        jSeparator30.setForeground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Kostnad certifikat:");

        certifikat_textfield.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        certifikat_textfield.setForeground(new java.awt.Color(102, 102, 102));
        certifikat_textfield.setText("0");
        certifikat_textfield.setBorder(null);

        jSeparator31.setForeground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Kostnad transport:");

        transport_textfield.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        transport_textfield.setForeground(new java.awt.Color(102, 102, 102));
        transport_textfield.setText("0");
        transport_textfield.setBorder(null);

        jSeparator32.setForeground(new java.awt.Color(204, 204, 204));

        betalningsvillkor_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        betalningsvillkor_combobox.setForeground(new java.awt.Color(102, 102, 102));
        betalningsvillkor_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Betalningsvillkor", "30 dagar netto", "45 dagar netto", "60 dagar netto", "Förskottsbetalning" }));
        betalningsvillkor_combobox.setBorder(null);

        leveransvillkor_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        leveransvillkor_combobox.setForeground(new java.awt.Color(102, 102, 102));
        leveransvillkor_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Leveransvillkor", "CPT -fraktfritt köparens lager", "FCA - Fritt säljarens lager", "Mottagarfrakt", "Hämtas" }));
        leveransvillkor_combobox.setBorder(null);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Lägg till beskrivning i offert:");

        offert_textarea.setColumns(20);
        offert_textarea.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        offert_textarea.setForeground(new java.awt.Color(102, 102, 102));
        offert_textarea.setRows(5);
        offert_textarea.setText("Priset gäller tills vidare. Med reservation för mellanförsäljning.");
        offert_textarea.setBorder(null);
        offert_scrollpane.setViewportView(offert_textarea);

        offert_klar_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        offert_klar_button.setForeground(new java.awt.Color(102, 102, 102));
        offert_klar_button.setText("Klar");
        offert_klar_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offert_klar_buttonActionPerformed(evt);
            }
        });

        offert_stäng_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        offert_stäng_button.setForeground(new java.awt.Color(102, 102, 102));
        offert_stäng_button.setText("Stäng");
        offert_stäng_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offert_stäng_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout offertPanelLayout = new javax.swing.GroupLayout(offertPanel);
        offertPanel.setLayout(offertPanelLayout);
        offertPanelLayout.setHorizontalGroup(
            offertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(offertPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(offertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(offert_namn_label)
                    .addComponent(offert_namn_label1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(offert_namn_field)
                    .addComponent(jSeparator29, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator30, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(certifikat_textfield)
                    .addComponent(transport_textfield)
                    .addComponent(jSeparator31, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referens_field)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, offertPanelLayout.createSequentialGroup()
                        .addComponent(offert_stäng_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(offert_klar_button))
                    .addComponent(offert_scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                    .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(betalningsvillkor_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leveransvillkor_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        offertPanelLayout.setVerticalGroup(
            offertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(offertPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(offert_namn_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offert_namn_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator29, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offert_namn_label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(referens_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator30, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(certifikat_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator31, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transport_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(betalningsvillkor_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(leveransvillkor_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offert_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(offertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(offert_klar_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(offert_stäng_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout OffertFönsterLayout = new javax.swing.GroupLayout(OffertFönster.getContentPane());
        OffertFönster.getContentPane().setLayout(OffertFönsterLayout);
        OffertFönsterLayout.setHorizontalGroup(
            OffertFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OffertFönsterLayout.createSequentialGroup()
                .addComponent(offertPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        OffertFönsterLayout.setVerticalGroup(
            OffertFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(offertPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ladda_offert_panel.setBackground(new java.awt.Color(255, 255, 255));

        välj_offert_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        välj_offert_combobox.setForeground(new java.awt.Color(102, 102, 102));
        välj_offert_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                välj_offert_comboboxActionPerformed(evt);
            }
        });

        välj_offert_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        välj_offert_label.setForeground(new java.awt.Color(102, 102, 102));
        välj_offert_label.setText("Välj offert:");

        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setText("Välj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ladda_offert_panelLayout = new javax.swing.GroupLayout(ladda_offert_panel);
        ladda_offert_panel.setLayout(ladda_offert_panelLayout);
        ladda_offert_panelLayout.setHorizontalGroup(
            ladda_offert_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ladda_offert_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ladda_offert_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ladda_offert_panelLayout.createSequentialGroup()
                        .addComponent(välj_offert_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ladda_offert_panelLayout.createSequentialGroup()
                        .addComponent(välj_offert_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(368, 368, 368))))
        );
        ladda_offert_panelLayout.setVerticalGroup(
            ladda_offert_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ladda_offert_panelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(välj_offert_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ladda_offert_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(välj_offert_combobox))
                .addContainerGap())
        );

        javax.swing.GroupLayout LaddaOffertFönsterLayout = new javax.swing.GroupLayout(LaddaOffertFönster.getContentPane());
        LaddaOffertFönster.getContentPane().setLayout(LaddaOffertFönsterLayout);
        LaddaOffertFönsterLayout.setHorizontalGroup(
            LaddaOffertFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ladda_offert_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        LaddaOffertFönsterLayout.setVerticalGroup(
            LaddaOffertFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ladda_offert_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        AnvändareFönster.setAlwaysOnTop(true);
        AnvändareFönster.setBackground(new java.awt.Color(255, 255, 255));
        AnvändareFönster.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                AnvändareFönsterWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                AnvändareFönsterWindowOpened(evt);
            }
        });

        användare_panel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        lägg_till_användare_button4.setForeground(new java.awt.Color(0, 102, 153));
        lägg_till_användare_button4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lägg_till_användare_button4.setText("Redan användare");
        lägg_till_användare_button4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lägg_till_användare_button4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lägg_till_användare_button4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lägg_till_användare_button4MouseReleased(evt);
            }
        });

        lägg_till_användare_button.setBackground(new java.awt.Color(255, 255, 255));
        lägg_till_användare_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lägg_till_användare_button.setForeground(new java.awt.Color(102, 102, 102));
        lägg_till_användare_button.setText("Skapa");
        lägg_till_användare_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lägg_till_användare_buttonActionPerformed(evt);
            }
        });

        jSeparator14.setForeground(new java.awt.Color(204, 204, 204));

        e_post_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        e_post_field.setForeground(new java.awt.Color(153, 153, 153));
        e_post_field.setText("E-post");
        e_post_field.setBorder(null);
        e_post_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                e_post_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                e_post_fieldFocusLost(evt);
            }
        });
        e_post_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e_post_fieldActionPerformed(evt);
            }
        });
        e_post_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                e_post_fieldKeyReleased(evt);
            }
        });

        jSeparator11.setForeground(new java.awt.Color(204, 204, 204));

        användarlösenord_passwordfield.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        användarlösenord_passwordfield.setForeground(new java.awt.Color(153, 153, 153));
        användarlösenord_passwordfield.setText("Lösenord");
        användarlösenord_passwordfield.setBorder(null);
        användarlösenord_passwordfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                användarlösenord_passwordfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                användarlösenord_passwordfieldFocusLost(evt);
            }
        });
        användarlösenord_passwordfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                användarlösenord_passwordfieldKeyReleased(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));

        användare_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        användare_field.setForeground(new java.awt.Color(153, 153, 153));
        användare_field.setText("Användarnamn");
        användare_field.setBorder(null);
        användare_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                användare_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                användare_fieldFocusLost(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(0, 102, 153));
        jLabel10.setText("Visa");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel10MouseReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("SKAPA ANVÄNDARE");
        jLabel8.setToolTipText("");

        användare_icon.setBackground(new java.awt.Color(153, 153, 153));

        lösenord_icon.setBackground(new java.awt.Color(153, 153, 153));

        epost_icon.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(användare_icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(epost_icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lösenord_icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(användare_field, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(lägg_till_användare_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(e_post_field, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(användarlösenord_passwordfield, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lägg_till_användare_button4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)))
                .addGap(56, 56, 56))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(användare_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(användare_field, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lösenord_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(användarlösenord_passwordfield, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(epost_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(e_post_field, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lägg_till_användare_button, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(lägg_till_användare_button4)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout användare_panel1Layout = new javax.swing.GroupLayout(användare_panel1);
        användare_panel1.setLayout(användare_panel1Layout);
        användare_panel1Layout.setHorizontalGroup(
            användare_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(användare_panel1Layout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(373, Short.MAX_VALUE))
        );
        användare_panel1Layout.setVerticalGroup(
            användare_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        existerande_panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        lägg_till_användare_button2.setForeground(new java.awt.Color(0, 102, 153));
        lägg_till_användare_button2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lägg_till_användare_button2.setText("Registrera ny användare");
        lägg_till_användare_button2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lägg_till_användare_button2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lägg_till_användare_button2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lägg_till_användare_button2MouseReleased(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(204, 0, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Jag har glömt mitt lösenord");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel5MouseReleased(evt);
            }
        });

        logga_in_användare_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        logga_in_användare_button.setForeground(new java.awt.Color(102, 102, 102));
        logga_in_användare_button.setText("Logga in");
        logga_in_användare_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logga_in_användare_buttonActionPerformed(evt);
            }
        });

        jSeparator16.setForeground(new java.awt.Color(204, 204, 204));

        existerande_passwordfield.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        existerande_passwordfield.setForeground(new java.awt.Color(153, 153, 153));
        existerande_passwordfield.setText("Lösenord");
        existerande_passwordfield.setBorder(null);
        existerande_passwordfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                existerande_passwordfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                existerande_passwordfieldFocusLost(evt);
            }
        });
        existerande_passwordfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                existerande_passwordfieldMouseReleased(evt);
            }
        });
        existerande_passwordfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                existerande_passwordfieldKeyReleased(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(0, 102, 153));
        jLabel9.setText("Visa");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel9MouseReleased(evt);
            }
        });

        jSeparator15.setForeground(new java.awt.Color(204, 204, 204));

        existerande_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        existerande_field.setForeground(new java.awt.Color(153, 153, 153));
        existerande_field.setText("Användarnamn");
        existerande_field.setBorder(null);
        existerande_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                existerande_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                existerande_fieldFocusLost(evt);
            }
        });

        användarnamn_lable3.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        användarnamn_lable3.setForeground(new java.awt.Color(102, 102, 102));
        användarnamn_lable3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        användarnamn_lable3.setText("LOGGA IN");

        lösenord_icon1.setBackground(new java.awt.Color(153, 153, 153));

        användare_icon1.setBackground(new java.awt.Color(153, 153, 153));

        kom_ihåg_mig_radio.setBackground(new java.awt.Color(255, 255, 255));
        kom_ihåg_mig_radio.setFont(new java.awt.Font("Yu Gothic UI", 0, 10)); // NOI18N
        kom_ihåg_mig_radio.setForeground(new java.awt.Color(102, 102, 102));
        kom_ihåg_mig_radio.setText("Kom ihåg mig");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(användare_icon1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lösenord_icon1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lägg_till_användare_button2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(användarnamn_lable3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                        .addComponent(existerande_field, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(kom_ihåg_mig_radio, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                            .addComponent(logga_in_användare_button, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jSeparator16, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(67, 67, 67)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(existerande_passwordfield)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(användarnamn_lable3)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(användare_icon1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(existerande_field, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(existerande_passwordfield)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lösenord_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logga_in_användare_button, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kom_ihåg_mig_radio))
                .addGap(62, 62, 62)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lägg_till_användare_button2)
                .addGap(77, 77, 77))
        );

        javax.swing.GroupLayout existerande_panelLayout = new javax.swing.GroupLayout(existerande_panel);
        existerande_panel.setLayout(existerande_panelLayout);
        existerande_panelLayout.setHorizontalGroup(
            existerande_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(existerande_panelLayout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(373, Short.MAX_VALUE))
        );
        existerande_panelLayout.setVerticalGroup(
            existerande_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(existerande_panelLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AnvändareFönsterLayout = new javax.swing.GroupLayout(AnvändareFönster.getContentPane());
        AnvändareFönster.getContentPane().setLayout(AnvändareFönsterLayout);
        AnvändareFönsterLayout.setHorizontalGroup(
            AnvändareFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(existerande_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(användare_panel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        AnvändareFönsterLayout.setVerticalGroup(
            AnvändareFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AnvändareFönsterLayout.createSequentialGroup()
                .addComponent(användare_panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(existerande_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );

        EPostFönster.setAlwaysOnTop(true);
        EPostFönster.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                EPostFönsterComponentHidden(evt);
            }
        });
        EPostFönster.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                EPostFönsterWindowClosed(evt);
            }
        });

        e_post_panel.setBackground(new java.awt.Color(255, 255, 255));

        e_post_field1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        e_post_field1.setForeground(new java.awt.Color(153, 153, 153));
        e_post_field1.setText("E-post");
        e_post_field1.setBorder(null);
        e_post_field1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                e_post_field1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                e_post_field1FocusLost(evt);
            }
        });
        e_post_field1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e_post_field1ActionPerformed(evt);
            }
        });
        e_post_field1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                e_post_field1KeyReleased(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(0, 102, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Skriv in ditt användarnamn & e-post");

        e_post_field2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        e_post_field2.setForeground(new java.awt.Color(153, 153, 153));
        e_post_field2.setText("Användarnamn");
        e_post_field2.setBorder(null);
        e_post_field2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                e_post_field2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                e_post_field2FocusLost(evt);
            }
        });
        e_post_field2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e_post_field2ActionPerformed(evt);
            }
        });

        skicka_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        skicka_button.setText("Skicka");
        skicka_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skicka_buttonActionPerformed(evt);
            }
        });
        skicka_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                skicka_buttonKeyReleased(evt);
            }
        });

        jSeparator17.setForeground(new java.awt.Color(204, 204, 204));

        jSeparator18.setForeground(new java.awt.Color(204, 204, 204));

        användare_icon2.setBackground(new java.awt.Color(153, 153, 153));

        epost_icon1.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout e_post_panelLayout = new javax.swing.GroupLayout(e_post_panel);
        e_post_panel.setLayout(e_post_panelLayout);
        e_post_panelLayout.setHorizontalGroup(
            e_post_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, e_post_panelLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(e_post_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(skicka_button)
                    .addGroup(e_post_panelLayout.createSequentialGroup()
                        .addGroup(e_post_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(användare_icon2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(epost_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(e_post_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator17)
                            .addComponent(e_post_field2)
                            .addComponent(e_post_field1)
                            .addComponent(jSeparator18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(84, 84, 84))
        );
        e_post_panelLayout.setVerticalGroup(
            e_post_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(e_post_panelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(e_post_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(e_post_panelLayout.createSequentialGroup()
                        .addComponent(e_post_field2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(användare_icon2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(e_post_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(e_post_panelLayout.createSequentialGroup()
                        .addComponent(e_post_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(epost_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(skicka_button)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout EPostFönsterLayout = new javax.swing.GroupLayout(EPostFönster.getContentPane());
        EPostFönster.getContentPane().setLayout(EPostFönsterLayout);
        EPostFönsterLayout.setHorizontalGroup(
            EPostFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(e_post_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        EPostFönsterLayout.setVerticalGroup(
            EPostFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(e_post_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        rensa_tabel_panel.setBackground(new java.awt.Color(255, 255, 255));

        rensa_tabel_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        rensa_tabel_label.setForeground(new java.awt.Color(204, 0, 0));
        rensa_tabel_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rensa_tabel_label.setText("Är du säker på att du vill rensa tabellen?");

        rensa_tabel_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        rensa_tabel_button.setForeground(new java.awt.Color(102, 102, 102));
        rensa_tabel_button.setText("Rensa");
        rensa_tabel_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rensa_tabel_buttonActionPerformed(evt);
            }
        });

        avbryt_rensa_tabel_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        avbryt_rensa_tabel_button.setForeground(new java.awt.Color(102, 102, 102));
        avbryt_rensa_tabel_button.setText("Avbryt");
        avbryt_rensa_tabel_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avbryt_rensa_tabel_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rensa_tabel_panelLayout = new javax.swing.GroupLayout(rensa_tabel_panel);
        rensa_tabel_panel.setLayout(rensa_tabel_panelLayout);
        rensa_tabel_panelLayout.setHorizontalGroup(
            rensa_tabel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rensa_tabel_panelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(rensa_tabel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rensa_tabel_panelLayout.createSequentialGroup()
                        .addComponent(rensa_tabel_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))
                    .addGroup(rensa_tabel_panelLayout.createSequentialGroup()
                        .addComponent(avbryt_rensa_tabel_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rensa_tabel_button)
                        .addGap(25, 25, 25))))
        );
        rensa_tabel_panelLayout.setVerticalGroup(
            rensa_tabel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rensa_tabel_panelLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(rensa_tabel_label)
                .addGap(18, 18, 18)
                .addGroup(rensa_tabel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rensa_tabel_button)
                    .addComponent(avbryt_rensa_tabel_button))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout rensa_tabel_dialogLayout = new javax.swing.GroupLayout(rensa_tabel_dialog.getContentPane());
        rensa_tabel_dialog.getContentPane().setLayout(rensa_tabel_dialogLayout);
        rensa_tabel_dialogLayout.setHorizontalGroup(
            rensa_tabel_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rensa_tabel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        rensa_tabel_dialogLayout.setVerticalGroup(
            rensa_tabel_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rensa_tabel_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        KundDashboardFrame.setAlwaysOnTop(true);
        KundDashboardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                KundDashboardFrameWindowOpened(evt);
            }
        });

        kundDashBoardPanel.setBackground(new java.awt.Color(255, 255, 255));

        dashboardHeaderPanel.setBackground(new java.awt.Color(0, 153, 204));

        kundDashboardLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        kundDashboardLabel.setForeground(new java.awt.Color(255, 255, 255));
        kundDashboardLabel.setText("Kund");
        kundDashboardLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        kundDashboardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kundDashboardLabelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout dashboardHeaderPanelLayout = new javax.swing.GroupLayout(dashboardHeaderPanel);
        dashboardHeaderPanel.setLayout(dashboardHeaderPanelLayout);
        dashboardHeaderPanelLayout.setHorizontalGroup(
            dashboardHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardHeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kundDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(657, 657, 657))
        );
        dashboardHeaderPanelLayout.setVerticalGroup(
            dashboardHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kundDashboardLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        uppgifterDashboardPanel.setBackground(new java.awt.Color(245, 245, 245));

        uppgifterDashboardLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        uppgifterDashboardLabel.setForeground(new java.awt.Color(102, 102, 102));
        uppgifterDashboardLabel.setText("Uppgifter");

        javax.swing.GroupLayout uppgifterDashboardPanelLayout = new javax.swing.GroupLayout(uppgifterDashboardPanel);
        uppgifterDashboardPanel.setLayout(uppgifterDashboardPanelLayout);
        uppgifterDashboardPanelLayout.setHorizontalGroup(
            uppgifterDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uppgifterDashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uppgifterDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addContainerGap())
        );
        uppgifterDashboardPanelLayout.setVerticalGroup(
            uppgifterDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uppgifterDashboardLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uppgifterDashboardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(uppgifterDashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 487, Short.MAX_VALUE))
        );

        dashboardUppgifterPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Faktureringsadress:");

        kundAdressField.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        kundAdressField.setForeground(new java.awt.Color(102, 102, 102));
        kundAdressField.setText("Adress");
        kundAdressField.setBorder(null);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("E-post:");

        kundEpostField.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        kundEpostField.setForeground(new java.awt.Color(102, 102, 102));
        kundEpostField.setText("E-post");
        kundEpostField.setBorder(null);

        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 102, 102));
        jButton2.setText("Spara ändringar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Kund upplagd:");

        kundSkapadField.setEditable(false);
        kundSkapadField.setBackground(new java.awt.Color(255, 255, 255));
        kundSkapadField.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        kundSkapadField.setForeground(new java.awt.Color(102, 102, 102));
        kundSkapadField.setText("Datum & tid");
        kundSkapadField.setBorder(null);

        taBortKundButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        taBortKundButton.setForeground(new java.awt.Color(102, 102, 102));
        taBortKundButton.setText("Ta bort kund");
        taBortKundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taBortKundButtonActionPerformed(evt);
            }
        });

        kundAdressField1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        kundAdressField1.setForeground(new java.awt.Color(102, 102, 102));
        kundAdressField1.setText("Adress");
        kundAdressField1.setBorder(null);

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Leveransadress");

        javax.swing.GroupLayout dashboardUppgifterPanelLayout = new javax.swing.GroupLayout(dashboardUppgifterPanel);
        dashboardUppgifterPanel.setLayout(dashboardUppgifterPanelLayout);
        dashboardUppgifterPanelLayout.setHorizontalGroup(
            dashboardUppgifterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardUppgifterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashboardUppgifterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kundAdressField)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardUppgifterPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(taBortKundButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(kundSkapadField)
                    .addComponent(kundEpostField)
                    .addGroup(dashboardUppgifterPanelLayout.createSequentialGroup()
                        .addGroup(dashboardUppgifterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator27, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(0, 383, Short.MAX_VALUE))
                    .addComponent(kundAdressField1))
                .addContainerGap())
        );
        dashboardUppgifterPanelLayout.setVerticalGroup(
            dashboardUppgifterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardUppgifterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kundSkapadField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kundAdressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kundAdressField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator27, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kundEpostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                .addGroup(dashboardUppgifterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taBortKundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout kundDashBoardPanelLayout = new javax.swing.GroupLayout(kundDashBoardPanel);
        kundDashBoardPanel.setLayout(kundDashBoardPanelLayout);
        kundDashBoardPanelLayout.setHorizontalGroup(
            kundDashBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kundDashBoardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboardUppgifterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(dashboardHeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kundDashBoardPanelLayout.setVerticalGroup(
            kundDashBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kundDashBoardPanelLayout.createSequentialGroup()
                .addComponent(dashboardHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kundDashBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dashboardUppgifterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout KundDashboardFrameLayout = new javax.swing.GroupLayout(KundDashboardFrame.getContentPane());
        KundDashboardFrame.getContentPane().setLayout(KundDashboardFrameLayout);
        KundDashboardFrameLayout.setHorizontalGroup(
            KundDashboardFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kundDashBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        KundDashboardFrameLayout.setVerticalGroup(
            KundDashboardFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kundDashBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        OrderOffertFönster.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                OrderOffertFönsterWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                OrderOffertFönsterWindowOpened(evt);
            }
        });

        färdig_panel1.setBackground(new java.awt.Color(245, 245, 245));
        färdig_panel1.setForeground(new java.awt.Color(167, 225, 239));

        kund_färdig_label1.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        kund_färdig_label1.setText("Kund");

        jButton7.setText("Försäljning");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("Tillbaka");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        offert_table.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        offert_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rad", "Artikelnummer", "Kategori", "Dimension", "Total vikt", "Antal", "Längd", "Tolerans", "Pris styck", "Pris leverans"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        offert_table.setToolTipText("");
        offert_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                offert_tableMouseReleased(evt);
            }
        });
        offert_table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                offert_tablePropertyChange(evt);
            }
        });
        jScrollPane8.setViewportView(offert_table);

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel16.setText("Total vikt:");

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel26.setText("Antal detaljer:");

        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel27.setText("Ordervärde:");

        sammanräknad_total_vikt_field1.setEditable(false);
        sammanräknad_total_vikt_field1.setBackground(new java.awt.Color(255, 255, 255));

        sammanräknat_antal_field1.setEditable(false);
        sammanräknat_antal_field1.setBackground(new java.awt.Color(255, 255, 255));

        sammanräknat_pris_per_styck_field1.setEditable(false);
        sammanräknat_pris_per_styck_field1.setBackground(new java.awt.Color(255, 255, 255));

        sammanräknad_marginal_field1.setEditable(false);
        sammanräknad_marginal_field1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel28.setText("Marginal:");

        jButton15.setText("Ta bort vald rad");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        ta_bort_offert_button1.setText("Ta bort offert");
        ta_bort_offert_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_bort_offert_button1ActionPerformed(evt);
            }
        });

        skapa_offert_button1.setText("Spara offert");
        skapa_offert_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skapa_offert_button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout färdig_panel1Layout = new javax.swing.GroupLayout(färdig_panel1);
        färdig_panel1.setLayout(färdig_panel1Layout);
        färdig_panel1Layout.setHorizontalGroup(
            färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(färdig_panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(färdig_panel1Layout.createSequentialGroup()
                        .addComponent(kund_färdig_label1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                        .addGap(460, 460, 460))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdig_panel1Layout.createSequentialGroup()
                        .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator28))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdig_panel1Layout.createSequentialGroup()
                        .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(färdig_panel1Layout.createSequentialGroup()
                                .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(sammanräknad_total_vikt_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(sammanräknat_antal_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sammanräknat_pris_per_styck_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sammanräknad_marginal_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton15))
                            .addGroup(färdig_panel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ta_bort_offert_button1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(skapa_offert_button1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7)))
                        .addContainerGap())))
        );
        färdig_panel1Layout.setVerticalGroup(
            färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(färdig_panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kund_färdig_label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator28, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton15)
                    .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdig_panel1Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sammanräknad_total_vikt_field1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(färdig_panel1Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sammanräknat_pris_per_styck_field1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(färdig_panel1Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sammanräknad_marginal_field1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(färdig_panel1Layout.createSequentialGroup()
                            .addComponent(jLabel26)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sammanräknat_antal_field1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(39, 39, 39)
                .addGroup(färdig_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton9)
                    .addComponent(ta_bort_offert_button1)
                    .addComponent(skapa_offert_button1))
                .addContainerGap())
        );

        javax.swing.GroupLayout OrderOffertFönsterLayout = new javax.swing.GroupLayout(OrderOffertFönster.getContentPane());
        OrderOffertFönster.getContentPane().setLayout(OrderOffertFönsterLayout);
        OrderOffertFönsterLayout.setHorizontalGroup(
            OrderOffertFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färdig_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        OrderOffertFönsterLayout.setVerticalGroup(
            OrderOffertFönsterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färdig_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        färdigställ_försäljning_panel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton10.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(102, 102, 102));
        jButton10.setText("Nej");
        jButton10.setBorder(null);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton18.setForeground(new java.awt.Color(102, 102, 102));
        jButton18.setText("Ja");
        jButton18.setBorder(null);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setText("Är du säker på att du vill skicka order till försäljning?");

        javax.swing.GroupLayout färdigställ_försäljning_panel1Layout = new javax.swing.GroupLayout(färdigställ_försäljning_panel1);
        färdigställ_försäljning_panel1.setLayout(färdigställ_försäljning_panel1Layout);
        färdigställ_försäljning_panel1Layout.setHorizontalGroup(
            färdigställ_försäljning_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(färdigställ_försäljning_panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(färdigställ_försäljning_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(färdigställ_försäljning_panel1Layout.createSequentialGroup()
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel29))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        färdigställ_försäljning_panel1Layout.setVerticalGroup(
            färdigställ_försäljning_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, färdigställ_försäljning_panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addGroup(färdigställ_försäljning_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout FärdigställFörsäljningFönster1Layout = new javax.swing.GroupLayout(FärdigställFörsäljningFönster1.getContentPane());
        FärdigställFörsäljningFönster1.getContentPane().setLayout(FärdigställFörsäljningFönster1Layout);
        FärdigställFörsäljningFönster1Layout.setHorizontalGroup(
            FärdigställFörsäljningFönster1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färdigställ_försäljning_panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        FärdigställFörsäljningFönster1Layout.setVerticalGroup(
            FärdigställFörsäljningFönster1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(färdigställ_försäljning_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Program");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(153, 0, 0));
        setIconImages(null);
        setLocationByPlatform(true);
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                avslut_metoder(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                start_metoder(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));

        kund_distrikt_panel.setBackground(new java.awt.Color(247, 247, 247));

        kund_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        kund_label.setForeground(new java.awt.Color(102, 102, 102));
        kund_label.setText("Kund");
        kund_label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        kund_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kund_labelMouseReleased(evt);
            }
        });

        övergång_icon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        distrikt_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        distrikt_label.setForeground(new java.awt.Color(102, 102, 102));
        distrikt_label.setText("Distrikt");

        info_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        info_label.setForeground(new java.awt.Color(204, 0, 0));
        info_label.setText("Det finns ohanterade offerter för den här kunden");
        info_label.setToolTipText("");
        info_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                info_labelMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                info_labelMouseReleased(evt);
            }
        });

        övergång_icon1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        övergång_icon2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        övergång_icon3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        offert_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        offert_label.setForeground(new java.awt.Color(102, 102, 102));
        offert_label.setText("Offert + Rad");
        offert_label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        offert_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                offert_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout kund_distrikt_panelLayout = new javax.swing.GroupLayout(kund_distrikt_panel);
        kund_distrikt_panel.setLayout(kund_distrikt_panelLayout);
        kund_distrikt_panelLayout.setHorizontalGroup(
            kund_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kund_distrikt_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(distrikt_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(övergång_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kund_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(övergång_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(info_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offert_label)
                .addContainerGap())
            .addGroup(kund_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kund_distrikt_panelLayout.createSequentialGroup()
                    .addGap(220, 220, 220)
                    .addComponent(övergång_icon2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(262, Short.MAX_VALUE)))
            .addGroup(kund_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kund_distrikt_panelLayout.createSequentialGroup()
                    .addGap(220, 220, 220)
                    .addComponent(övergång_icon3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(262, Short.MAX_VALUE)))
        );
        kund_distrikt_panelLayout.setVerticalGroup(
            kund_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kund_distrikt_panelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(kund_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(distrikt_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(övergång_icon1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(övergång_icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kund_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(offert_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(info_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(kund_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kund_distrikt_panelLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(övergång_icon2, javax.swing.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                    .addGap(53, 53, 53)))
            .addGroup(kund_distrikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kund_distrikt_panelLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(övergång_icon3, javax.swing.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                    .addGap(53, 53, 53)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(kund_distrikt_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(945, 945, 945))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(kund_distrikt_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        panel_panel.setBackground(new java.awt.Color(255, 255, 255));
        panel_panel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                panel_panelPropertyChange(evt);
            }
        });

        påslag_panel.setBackground(new java.awt.Color(213, 232, 232));
        påslag_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        påslag_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                påslag_panelMouseReleased(evt);
            }
        });

        påslag_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        påslag_label.setText("Påslag");
        påslag_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                påslag_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout påslag_panelLayout = new javax.swing.GroupLayout(påslag_panel);
        påslag_panel.setLayout(påslag_panelLayout);
        påslag_panelLayout.setHorizontalGroup(
            påslag_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, påslag_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(påslag_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        påslag_panelLayout.setVerticalGroup(
            påslag_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(påslag_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        artikel_panel.setBackground(new java.awt.Color(213, 232, 232));
        artikel_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        artikel_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                artikel_panelMouseReleased(evt);
            }
        });

        artikelnummer_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        artikelnummer_label.setText("Artikelnummer");
        artikelnummer_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                artikelnummer_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout artikel_panelLayout = new javax.swing.GroupLayout(artikel_panel);
        artikel_panel.setLayout(artikel_panelLayout);
        artikel_panelLayout.setHorizontalGroup(
            artikel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artikel_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(artikelnummer_label)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        artikel_panelLayout.setVerticalGroup(
            artikel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(artikelnummer_label, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        längd_panel.setBackground(new java.awt.Color(213, 232, 232));
        längd_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        längd_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                längd_panelMouseReleased(evt);
            }
        });

        längd_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        längd_label.setText("Längd");
        längd_label.setAlignmentX(-1.0F);
        längd_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                längd_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout längd_panelLayout = new javax.swing.GroupLayout(längd_panel);
        längd_panel.setLayout(längd_panelLayout);
        längd_panelLayout.setHorizontalGroup(
            längd_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, längd_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(längd_label)
                .addContainerGap(159, Short.MAX_VALUE))
        );
        längd_panelLayout.setVerticalGroup(
            längd_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(längd_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        ställkostnad_panel.setBackground(new java.awt.Color(213, 232, 232));
        ställkostnad_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        ställkostnad_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ställkostnad_panelMouseReleased(evt);
            }
        });

        ställkostnad_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ställkostnad_label.setText("Ställkostnad");
        ställkostnad_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ställkostnad_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout ställkostnad_panelLayout = new javax.swing.GroupLayout(ställkostnad_panel);
        ställkostnad_panel.setLayout(ställkostnad_panelLayout);
        ställkostnad_panelLayout.setHorizontalGroup(
            ställkostnad_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ställkostnad_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ställkostnad_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ställkostnad_panelLayout.setVerticalGroup(
            ställkostnad_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ställkostnad_label, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        längdtolerans_panel.setBackground(new java.awt.Color(213, 232, 232));
        längdtolerans_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        längdtolerans_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                längdtolerans_panelMouseReleased(evt);
            }
        });

        längdtolerans_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        längdtolerans_label.setText("Längdtolerans");
        längdtolerans_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                längdtolerans_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout längdtolerans_panelLayout = new javax.swing.GroupLayout(längdtolerans_panel);
        längdtolerans_panel.setLayout(längdtolerans_panelLayout);
        längdtolerans_panelLayout.setHorizontalGroup(
            längdtolerans_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, längdtolerans_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(längdtolerans_label)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        längdtolerans_panelLayout.setVerticalGroup(
            längdtolerans_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(längdtolerans_label, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        antal_panel.setBackground(new java.awt.Color(213, 232, 232));
        antal_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        antal_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                antal_panel2MouseReleased(evt);
            }
        });

        antal_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        antal_label.setText("Antal");
        antal_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                antal_panel2MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout antal_panelLayout = new javax.swing.GroupLayout(antal_panel);
        antal_panel.setLayout(antal_panelLayout);
        antal_panelLayout.setHorizontalGroup(
            antal_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, antal_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(antal_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        antal_panelLayout.setVerticalGroup(
            antal_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(antal_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        fraktkostnad_per_kilo_panel.setBackground(new java.awt.Color(213, 232, 232));
        fraktkostnad_per_kilo_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        fraktkostnad_per_kilo_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fraktkostnad_per_kilo_panelMouseReleased(evt);
            }
        });

        fraktkostnad_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        fraktkostnad_label.setText("Fraktkostnad");
        fraktkostnad_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fraktkostnad_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout fraktkostnad_per_kilo_panelLayout = new javax.swing.GroupLayout(fraktkostnad_per_kilo_panel);
        fraktkostnad_per_kilo_panel.setLayout(fraktkostnad_per_kilo_panelLayout);
        fraktkostnad_per_kilo_panelLayout.setHorizontalGroup(
            fraktkostnad_per_kilo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fraktkostnad_per_kilo_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fraktkostnad_label)
                .addContainerGap(124, Short.MAX_VALUE))
        );
        fraktkostnad_per_kilo_panelLayout.setVerticalGroup(
            fraktkostnad_per_kilo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fraktkostnad_label, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        fraktkostnad_sicam_panel.setBackground(new java.awt.Color(213, 232, 232));
        fraktkostnad_sicam_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        fraktkostnad_sicam_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fraktkostnad_sicam_panelMouseReleased(evt);
            }
        });

        fraktkostnad_sicam_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        fraktkostnad_sicam_label.setText("Fraktkostnad SICAM / lagerhållare");
        fraktkostnad_sicam_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fraktkostnad_sicam_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout fraktkostnad_sicam_panelLayout = new javax.swing.GroupLayout(fraktkostnad_sicam_panel);
        fraktkostnad_sicam_panel.setLayout(fraktkostnad_sicam_panelLayout);
        fraktkostnad_sicam_panelLayout.setHorizontalGroup(
            fraktkostnad_sicam_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fraktkostnad_sicam_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fraktkostnad_sicam_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        fraktkostnad_sicam_panelLayout.setVerticalGroup(
            fraktkostnad_sicam_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fraktkostnad_sicam_label, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        euro_per_ton_panel.setBackground(new java.awt.Color(213, 232, 232));
        euro_per_ton_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        euro_per_ton_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                euro_per_ton_panelMouseReleased(evt);
            }
        });

        euro_per_ton_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        euro_per_ton_label.setText("Euro per ton");
        euro_per_ton_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                euro_per_ton_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout euro_per_ton_panelLayout = new javax.swing.GroupLayout(euro_per_ton_panel);
        euro_per_ton_panel.setLayout(euro_per_ton_panelLayout);
        euro_per_ton_panelLayout.setHorizontalGroup(
            euro_per_ton_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, euro_per_ton_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(euro_per_ton_label)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        euro_per_ton_panelLayout.setVerticalGroup(
            euro_per_ton_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(euro_per_ton_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        dimension_panel.setBackground(new java.awt.Color(213, 232, 232));
        dimension_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        dimension_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dimension_panelMouseReleased(evt);
            }
        });

        dimension_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        dimension_label.setText("Dimension");
        dimension_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dimension_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout dimension_panelLayout = new javax.swing.GroupLayout(dimension_panel);
        dimension_panel.setLayout(dimension_panelLayout);
        dimension_panelLayout.setHorizontalGroup(
            dimension_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dimension_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dimension_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dimension_panelLayout.setVerticalGroup(
            dimension_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dimension_label, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        ursprungslängd_panel.setBackground(new java.awt.Color(213, 232, 232));
        ursprungslängd_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        ursprungslängd_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ursprungslängd_panelMouseReleased(evt);
            }
        });

        ursprungslängd_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ursprungslängd_label.setText("Ursprungslängd");
        ursprungslängd_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ursprungslängd_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout ursprungslängd_panelLayout = new javax.swing.GroupLayout(ursprungslängd_panel);
        ursprungslängd_panel.setLayout(ursprungslängd_panelLayout);
        ursprungslängd_panelLayout.setHorizontalGroup(
            ursprungslängd_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ursprungslängd_panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(ursprungslängd_label)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        ursprungslängd_panelLayout.setVerticalGroup(
            ursprungslängd_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ursprungslängd_label, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        säljare_panel.setBackground(new java.awt.Color(213, 232, 232));
        säljare_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                säljare_panelMouseReleased(evt);
            }
        });

        säljare_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        säljare_label.setText("Säljare");
        säljare_label.setToolTipText("");
        säljare_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                säljare_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout säljare_panelLayout = new javax.swing.GroupLayout(säljare_panel);
        säljare_panel.setLayout(säljare_panelLayout);
        säljare_panelLayout.setHorizontalGroup(
            säljare_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(säljare_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(säljare_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        säljare_panelLayout.setVerticalGroup(
            säljare_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(säljare_label, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );

        datum_panel.setBackground(new java.awt.Color(213, 232, 232));
        datum_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        datum_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                datum_panelMouseReleased(evt);
            }
        });

        datum_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        datum_label.setText("Datum");
        datum_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                datum_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout datum_panelLayout = new javax.swing.GroupLayout(datum_panel);
        datum_panel.setLayout(datum_panelLayout);
        datum_panelLayout.setHorizontalGroup(
            datum_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, datum_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(datum_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        datum_panelLayout.setVerticalGroup(
            datum_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(datum_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        spill_panel.setBackground(new java.awt.Color(213, 232, 232));
        spill_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        spill_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                spill_panelMouseReleased(evt);
            }
        });

        spill_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        spill_label.setText("Spill");
        spill_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                spill_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout spill_panelLayout = new javax.swing.GroupLayout(spill_panel);
        spill_panel.setLayout(spill_panelLayout);
        spill_panelLayout.setHorizontalGroup(
            spill_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, spill_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spill_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        spill_panelLayout.setVerticalGroup(
            spill_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spill_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        stålverk_field.setBackground(new java.awt.Color(243, 243, 243));
        stålverk_field.setForeground(new java.awt.Color(102, 102, 102));
        stålverk_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        stålverk_field.setText("0");
        stålverk_field.setToolTipText("");
        stålverk_field.setBorder(null);
        stålverk_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        stålverk_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stålverk_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                stålverk_fieldFocusLost(evt);
            }
        });

        stålkvalitet_field.setBackground(new java.awt.Color(243, 243, 243));
        stålkvalitet_field.setForeground(new java.awt.Color(102, 102, 102));
        stålkvalitet_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        stålkvalitet_field.setText("0");
        stålkvalitet_field.setToolTipText("");
        stålkvalitet_field.setBorder(null);
        stålkvalitet_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        stålkvalitet_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stålkvalitet_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                stålkvalitet_fieldFocusLost(evt);
            }
        });
        stålkvalitet_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stålkvalitet_fieldActionPerformed(evt);
            }
        });
        stålkvalitet_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                stålkvalitet_fieldKeyReleased(evt);
            }
        });

        valutakurs_field.setBackground(new java.awt.Color(243, 243, 243));
        valutakurs_field.setForeground(new java.awt.Color(102, 102, 102));
        valutakurs_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        valutakurs_field.setText("0");
        valutakurs_field.setToolTipText("");
        valutakurs_field.setBorder(null);
        valutakurs_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        valutakurs_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                valutakurs_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                valutakurs_fieldFocusLost(evt);
            }
        });
        valutakurs_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                valutakurs_fieldKeyReleased(evt);
            }
        });

        spill_field.setBackground(new java.awt.Color(243, 243, 243));
        spill_field.setColumns(3);
        spill_field.setForeground(new java.awt.Color(102, 102, 102));
        spill_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        spill_field.setText("0");
        spill_field.setBorder(null);
        spill_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        spill_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                spill_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                spill_fieldFocusLost(evt);
            }
        });
        spill_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                spill_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                spill_fieldKeyReleased(evt);
            }
        });

        fraktkostnad_sicam_field.setBackground(new java.awt.Color(243, 243, 243));
        fraktkostnad_sicam_field.setForeground(new java.awt.Color(102, 102, 102));
        fraktkostnad_sicam_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fraktkostnad_sicam_field.setText("0");
        fraktkostnad_sicam_field.setToolTipText("");
        fraktkostnad_sicam_field.setBorder(null);
        fraktkostnad_sicam_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        fraktkostnad_sicam_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fraktkostnad_sicam_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fraktkostnad_sicam_fieldFocusLost(evt);
            }
        });
        fraktkostnad_sicam_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fraktkostnad_sicam_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fraktkostnad_sicam_fieldKeyReleased(evt);
            }
        });

        fraktkostnad_field.setBackground(new java.awt.Color(243, 243, 243));
        fraktkostnad_field.setForeground(new java.awt.Color(102, 102, 102));
        fraktkostnad_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fraktkostnad_field.setText("0");
        fraktkostnad_field.setToolTipText("");
        fraktkostnad_field.setBorder(null);
        fraktkostnad_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        fraktkostnad_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fraktkostnad_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fraktkostnad_fieldFocusLost(evt);
            }
        });
        fraktkostnad_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fraktkostnad_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fraktkostnad_fieldKeyReleased(evt);
            }
        });

        ursprungslängd_field.setBackground(new java.awt.Color(243, 243, 243));
        ursprungslängd_field.setForeground(new java.awt.Color(102, 102, 102));
        ursprungslängd_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ursprungslängd_field.setText("0");
        ursprungslängd_field.setToolTipText("");
        ursprungslängd_field.setBorder(null);
        ursprungslängd_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        ursprungslängd_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ursprungslängd_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ursprungslängd_fieldFocusLost(evt);
            }
        });
        ursprungslängd_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ursprungslängd_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ursprungslängd_fieldKeyReleased(evt);
            }
        });

        ställkostnad_field.setBackground(new java.awt.Color(243, 243, 243));
        ställkostnad_field.setForeground(new java.awt.Color(102, 102, 102));
        ställkostnad_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ställkostnad_field.setText("0");
        ställkostnad_field.setToolTipText("");
        ställkostnad_field.setBorder(null);
        ställkostnad_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        ställkostnad_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ställkostnad_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ställkostnad_fieldFocusLost(evt);
            }
        });
        ställkostnad_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ställkostnad_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ställkostnad_fieldKeyReleased(evt);
            }
        });

        euro_per_ton_field.setBackground(new java.awt.Color(243, 243, 243));
        euro_per_ton_field.setForeground(new java.awt.Color(102, 102, 102));
        euro_per_ton_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        euro_per_ton_field.setText("0");
        euro_per_ton_field.setToolTipText("");
        euro_per_ton_field.setBorder(null);
        euro_per_ton_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        euro_per_ton_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                euro_per_ton_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                euro_per_ton_fieldFocusLost(evt);
            }
        });
        euro_per_ton_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                euro_per_ton_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                euro_per_ton_fieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                euro_per_ton_fieldKeyTyped(evt);
            }
        });

        längdtolerans_field.setBackground(new java.awt.Color(243, 243, 243));
        längdtolerans_field.setForeground(new java.awt.Color(102, 102, 102));
        längdtolerans_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        längdtolerans_field.setText("0");
        längdtolerans_field.setToolTipText("");
        längdtolerans_field.setBorder(null);
        längdtolerans_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        längdtolerans_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                längdtolerans_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                längdtolerans_fieldFocusLost(evt);
            }
        });
        längdtolerans_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                längdtolerans_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                längdtolerans_fieldKeyReleased(evt);
            }
        });

        längd_field.setBackground(new java.awt.Color(243, 243, 243));
        längd_field.setForeground(new java.awt.Color(102, 102, 102));
        längd_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        längd_field.setText("0");
        längd_field.setToolTipText("");
        längd_field.setBorder(null);
        längd_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        längd_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                längd_fieldFocusGained(evt);
            }
        });
        längd_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                längd_fieldActionPerformed(evt);
            }
        });
        längd_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                längd_fieldKeyReleased(evt);
            }
        });

        antal_field.setBackground(new java.awt.Color(243, 243, 243));
        antal_field.setForeground(new java.awt.Color(102, 102, 102));
        antal_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        antal_field.setText("0");
        antal_field.setToolTipText("");
        antal_field.setBorder(null);
        antal_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        antal_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                antal_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                antal_fieldFocusLost(evt);
            }
        });
        antal_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                antal_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                antal_fieldKeyReleased(evt);
            }
        });

        dimension_field.setBackground(new java.awt.Color(243, 243, 243));
        dimension_field.setForeground(new java.awt.Color(102, 102, 102));
        dimension_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        dimension_field.setText("0");
        dimension_field.setBorder(null);
        dimension_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        dimension_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dimension_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dimension_fieldFocusLost(evt);
            }
        });
        dimension_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dimension_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dimension_fieldKeyReleased(evt);
            }
        });

        artikelnummer_field.setBackground(new java.awt.Color(243, 243, 243));
        artikelnummer_field.setColumns(3);
        artikelnummer_field.setForeground(new java.awt.Color(102, 102, 102));
        artikelnummer_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        artikelnummer_field.setText("0");
        artikelnummer_field.setBorder(null);
        artikelnummer_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        artikelnummer_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                artikelnummer_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                artikelnummer_fieldFocusLost(evt);
            }
        });
        artikelnummer_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                artikelnummer_fieldKeyReleased(evt);
            }
        });

        datum_field.setEditable(false);
        datum_field.setBackground(new java.awt.Color(243, 243, 243));
        datum_field.setColumns(3);
        datum_field.setForeground(new java.awt.Color(102, 102, 102));
        datum_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        datum_field.setBorder(null);
        datum_field.setMargin(new java.awt.Insets(2, 5, 2, 2));

        säljare_field.setEditable(false);
        säljare_field.setBackground(new java.awt.Color(243, 243, 243));
        säljare_field.setForeground(new java.awt.Color(102, 102, 102));
        säljare_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        säljare_field.setToolTipText("");
        säljare_field.setBorder(null);
        säljare_field.setMargin(new java.awt.Insets(2, 5, 2, 2));

        dimension_panel2.setBackground(new java.awt.Color(213, 232, 232));
        dimension_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dimension_panel2MouseReleased(evt);
            }
        });

        dimension_mm_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        dimension_mm_label.setText("mm");
        dimension_mm_label.setToolTipText("");
        dimension_mm_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dimension_mm_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout dimension_panel2Layout = new javax.swing.GroupLayout(dimension_panel2);
        dimension_panel2.setLayout(dimension_panel2Layout);
        dimension_panel2Layout.setHorizontalGroup(
            dimension_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(dimension_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dimension_panel2Layout.createSequentialGroup()
                    .addGap(0, 17, Short.MAX_VALUE)
                    .addComponent(dimension_mm_label)
                    .addGap(0, 17, Short.MAX_VALUE)))
        );
        dimension_panel2Layout.setVerticalGroup(
            dimension_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(dimension_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dimension_panel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(dimension_mm_label)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        antal_panel2.setBackground(new java.awt.Color(213, 232, 232));
        antal_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                antal_panel2MouseReleased(evt);
            }
        });

        antal_styck_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        antal_styck_label.setText("styck");
        antal_styck_label.setToolTipText("");
        antal_styck_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                antal_styck_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout antal_panel2Layout = new javax.swing.GroupLayout(antal_panel2);
        antal_panel2.setLayout(antal_panel2Layout);
        antal_panel2Layout.setHorizontalGroup(
            antal_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
            .addGroup(antal_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(antal_panel2Layout.createSequentialGroup()
                    .addGap(0, 14, Short.MAX_VALUE)
                    .addComponent(antal_styck_label)
                    .addGap(0, 14, Short.MAX_VALUE)))
        );
        antal_panel2Layout.setVerticalGroup(
            antal_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(antal_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(antal_panel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(antal_styck_label)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        längd_panel2.setBackground(new java.awt.Color(213, 232, 232));
        längd_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                längd_panel2MouseReleased(evt);
            }
        });

        längd_mm_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        längd_mm_label.setText("mm");
        längd_mm_label.setToolTipText("");
        längd_mm_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                längd_mm_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout längd_panel2Layout = new javax.swing.GroupLayout(längd_panel2);
        längd_panel2.setLayout(längd_panel2Layout);
        längd_panel2Layout.setHorizontalGroup(
            längd_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(längd_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(längd_panel2Layout.createSequentialGroup()
                    .addGap(0, 17, Short.MAX_VALUE)
                    .addComponent(längd_mm_label)
                    .addGap(0, 17, Short.MAX_VALUE)))
        );
        längd_panel2Layout.setVerticalGroup(
            längd_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(längd_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(längd_panel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(längd_mm_label)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        längdtolerans_panel2.setBackground(new java.awt.Color(213, 232, 232));
        längdtolerans_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                längdtolerans_panel2MouseReleased(evt);
            }
        });

        längdtolerans_mm_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        längdtolerans_mm_label.setText("mm");
        längdtolerans_mm_label.setToolTipText("");
        längdtolerans_mm_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                längdtolerans_mm_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout längdtolerans_panel2Layout = new javax.swing.GroupLayout(längdtolerans_panel2);
        längdtolerans_panel2.setLayout(längdtolerans_panel2Layout);
        längdtolerans_panel2Layout.setHorizontalGroup(
            längdtolerans_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(längdtolerans_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(längdtolerans_panel2Layout.createSequentialGroup()
                    .addGap(0, 18, Short.MAX_VALUE)
                    .addComponent(längdtolerans_mm_label)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );
        längdtolerans_panel2Layout.setVerticalGroup(
            längdtolerans_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(längdtolerans_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(längdtolerans_panel2Layout.createSequentialGroup()
                    .addGap(0, 6, Short.MAX_VALUE)
                    .addComponent(längdtolerans_mm_label)
                    .addGap(0, 7, Short.MAX_VALUE)))
        );

        euro_per_ton_panel2.setBackground(new java.awt.Color(213, 232, 232));
        euro_per_ton_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                euro_per_ton_panel2MouseReleased(evt);
            }
        });

        euro_per_ton_euro_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        euro_per_ton_euro_label.setText("euro");
        euro_per_ton_euro_label.setToolTipText("");
        euro_per_ton_euro_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                euro_per_ton_euro_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout euro_per_ton_panel2Layout = new javax.swing.GroupLayout(euro_per_ton_panel2);
        euro_per_ton_panel2.setLayout(euro_per_ton_panel2Layout);
        euro_per_ton_panel2Layout.setHorizontalGroup(
            euro_per_ton_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(euro_per_ton_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(euro_per_ton_panel2Layout.createSequentialGroup()
                    .addGap(0, 16, Short.MAX_VALUE)
                    .addComponent(euro_per_ton_euro_label)
                    .addGap(0, 16, Short.MAX_VALUE)))
        );
        euro_per_ton_panel2Layout.setVerticalGroup(
            euro_per_ton_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(euro_per_ton_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(euro_per_ton_panel2Layout.createSequentialGroup()
                    .addGap(0, 2, Short.MAX_VALUE)
                    .addComponent(euro_per_ton_euro_label)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );

        ställkostnad_panel2.setBackground(new java.awt.Color(213, 232, 232));
        ställkostnad_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ställkostnad_panel2MouseReleased(evt);
            }
        });

        ställkostnad_kr_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ställkostnad_kr_label.setText("kr");
        ställkostnad_kr_label.setToolTipText("");
        ställkostnad_kr_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ställkostnad_kr_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout ställkostnad_panel2Layout = new javax.swing.GroupLayout(ställkostnad_panel2);
        ställkostnad_panel2.setLayout(ställkostnad_panel2Layout);
        ställkostnad_panel2Layout.setHorizontalGroup(
            ställkostnad_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(ställkostnad_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ställkostnad_panel2Layout.createSequentialGroup()
                    .addGap(0, 21, Short.MAX_VALUE)
                    .addComponent(ställkostnad_kr_label)
                    .addGap(0, 22, Short.MAX_VALUE)))
        );
        ställkostnad_panel2Layout.setVerticalGroup(
            ställkostnad_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(ställkostnad_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ställkostnad_panel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(ställkostnad_kr_label)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        ursprungslängd_panel2.setBackground(new java.awt.Color(213, 232, 232));
        ursprungslängd_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ursprungslängd_panel2MouseReleased(evt);
            }
        });

        ursprungslängd_mm_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ursprungslängd_mm_label.setText("mm");
        ursprungslängd_mm_label.setToolTipText("");
        ursprungslängd_mm_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ursprungslängd_mm_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout ursprungslängd_panel2Layout = new javax.swing.GroupLayout(ursprungslängd_panel2);
        ursprungslängd_panel2.setLayout(ursprungslängd_panel2Layout);
        ursprungslängd_panel2Layout.setHorizontalGroup(
            ursprungslängd_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(ursprungslängd_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ursprungslängd_panel2Layout.createSequentialGroup()
                    .addGap(0, 17, Short.MAX_VALUE)
                    .addComponent(ursprungslängd_mm_label)
                    .addGap(0, 17, Short.MAX_VALUE)))
        );
        ursprungslängd_panel2Layout.setVerticalGroup(
            ursprungslängd_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(ursprungslängd_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ursprungslängd_panel2Layout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(ursprungslängd_mm_label)
                    .addGap(0, 5, Short.MAX_VALUE)))
        );

        påslag_panel2.setBackground(new java.awt.Color(213, 232, 232));
        påslag_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                påslag_panel2MouseReleased(evt);
            }
        });

        påslag_procent_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        påslag_procent_label.setText("kr");
        påslag_procent_label.setToolTipText("");
        påslag_procent_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                påslag_procent_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout påslag_panel2Layout = new javax.swing.GroupLayout(påslag_panel2);
        påslag_panel2.setLayout(påslag_panel2Layout);
        påslag_panel2Layout.setHorizontalGroup(
            påslag_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(påslag_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(påslag_panel2Layout.createSequentialGroup()
                    .addGap(0, 21, Short.MAX_VALUE)
                    .addComponent(påslag_procent_label)
                    .addGap(0, 22, Short.MAX_VALUE)))
        );
        påslag_panel2Layout.setVerticalGroup(
            påslag_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(påslag_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(påslag_panel2Layout.createSequentialGroup()
                    .addGap(0, 2, Short.MAX_VALUE)
                    .addComponent(påslag_procent_label)
                    .addGap(0, 3, Short.MAX_VALUE)))
        );

        fraktkostnad_per_kilo_panel2.setBackground(new java.awt.Color(213, 232, 232));
        fraktkostnad_per_kilo_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fraktkostnad_per_kilo_panel2MouseReleased(evt);
            }
        });

        fraktkostnad_kr_kg_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        fraktkostnad_kr_kg_label.setText("kr/kg");
        fraktkostnad_kr_kg_label.setToolTipText("");
        fraktkostnad_kr_kg_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fraktkostnad_kr_kg_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout fraktkostnad_per_kilo_panel2Layout = new javax.swing.GroupLayout(fraktkostnad_per_kilo_panel2);
        fraktkostnad_per_kilo_panel2.setLayout(fraktkostnad_per_kilo_panel2Layout);
        fraktkostnad_per_kilo_panel2Layout.setHorizontalGroup(
            fraktkostnad_per_kilo_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(fraktkostnad_per_kilo_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fraktkostnad_per_kilo_panel2Layout.createSequentialGroup()
                    .addGap(0, 14, Short.MAX_VALUE)
                    .addComponent(fraktkostnad_kr_kg_label)
                    .addGap(0, 14, Short.MAX_VALUE)))
        );
        fraktkostnad_per_kilo_panel2Layout.setVerticalGroup(
            fraktkostnad_per_kilo_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(fraktkostnad_per_kilo_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fraktkostnad_per_kilo_panel2Layout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(fraktkostnad_kr_kg_label)
                    .addGap(0, 5, Short.MAX_VALUE)))
        );

        fraktkostnad_sicam_panel2.setBackground(new java.awt.Color(213, 232, 232));
        fraktkostnad_sicam_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fraktkostnad_sicam_panel2MouseReleased(evt);
            }
        });

        fraktkostnad_sicam_kr_kg_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        fraktkostnad_sicam_kr_kg_label.setText("kr/kg");
        fraktkostnad_sicam_kr_kg_label.setToolTipText("");
        fraktkostnad_sicam_kr_kg_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fraktkostnad_sicam_kr_kg_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout fraktkostnad_sicam_panel2Layout = new javax.swing.GroupLayout(fraktkostnad_sicam_panel2);
        fraktkostnad_sicam_panel2.setLayout(fraktkostnad_sicam_panel2Layout);
        fraktkostnad_sicam_panel2Layout.setHorizontalGroup(
            fraktkostnad_sicam_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(fraktkostnad_sicam_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fraktkostnad_sicam_panel2Layout.createSequentialGroup()
                    .addGap(0, 14, Short.MAX_VALUE)
                    .addComponent(fraktkostnad_sicam_kr_kg_label)
                    .addGap(0, 14, Short.MAX_VALUE)))
        );
        fraktkostnad_sicam_panel2Layout.setVerticalGroup(
            fraktkostnad_sicam_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(fraktkostnad_sicam_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fraktkostnad_sicam_panel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(fraktkostnad_sicam_kr_kg_label)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        spill_procent_panel2.setBackground(new java.awt.Color(213, 232, 232));
        spill_procent_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                spill_procent_panel2MouseReleased(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel44.setText("%");
        jLabel44.setToolTipText("");
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel44MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout spill_procent_panel2Layout = new javax.swing.GroupLayout(spill_procent_panel2);
        spill_procent_panel2.setLayout(spill_procent_panel2Layout);
        spill_procent_panel2Layout.setHorizontalGroup(
            spill_procent_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(spill_procent_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(spill_procent_panel2Layout.createSequentialGroup()
                    .addGap(0, 23, Short.MAX_VALUE)
                    .addComponent(jLabel44)
                    .addGap(0, 23, Short.MAX_VALUE)))
        );
        spill_procent_panel2Layout.setVerticalGroup(
            spill_procent_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(spill_procent_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(spill_procent_panel2Layout.createSequentialGroup()
                    .addGap(0, 4, Short.MAX_VALUE)
                    .addComponent(jLabel44)
                    .addGap(0, 4, Short.MAX_VALUE)))
        );

        valutakurs_panel2.setBackground(new java.awt.Color(213, 232, 232));
        valutakurs_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                valutakurs_panel2MouseReleased(evt);
            }
        });

        valutakurs_kr_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        valutakurs_kr_label.setText("kr");
        valutakurs_kr_label.setToolTipText("");

        javax.swing.GroupLayout valutakurs_panel2Layout = new javax.swing.GroupLayout(valutakurs_panel2);
        valutakurs_panel2.setLayout(valutakurs_panel2Layout);
        valutakurs_panel2Layout.setHorizontalGroup(
            valutakurs_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(valutakurs_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(valutakurs_panel2Layout.createSequentialGroup()
                    .addGap(0, 21, Short.MAX_VALUE)
                    .addComponent(valutakurs_kr_label)
                    .addGap(0, 22, Short.MAX_VALUE)))
        );
        valutakurs_panel2Layout.setVerticalGroup(
            valutakurs_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(valutakurs_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(valutakurs_panel2Layout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(valutakurs_kr_label)
                    .addGap(0, 4, Short.MAX_VALUE)))
        );

        bearbetning_panel2.setBackground(new java.awt.Color(213, 232, 232));
        bearbetning_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bearbetning_panel2MouseReleased(evt);
            }
        });

        bearbetning_kr_kg_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        bearbetning_kr_kg_label.setText("kr/kg");
        bearbetning_kr_kg_label.setToolTipText("");
        bearbetning_kr_kg_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bearbetning_kr_kg_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout bearbetning_panel2Layout = new javax.swing.GroupLayout(bearbetning_panel2);
        bearbetning_panel2.setLayout(bearbetning_panel2Layout);
        bearbetning_panel2Layout.setHorizontalGroup(
            bearbetning_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(bearbetning_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bearbetning_panel2Layout.createSequentialGroup()
                    .addGap(0, 14, Short.MAX_VALUE)
                    .addComponent(bearbetning_kr_kg_label)
                    .addGap(0, 14, Short.MAX_VALUE)))
        );
        bearbetning_panel2Layout.setVerticalGroup(
            bearbetning_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
            .addGroup(bearbetning_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bearbetning_panel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(bearbetning_kr_kg_label)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        bearbetning_field.setBackground(new java.awt.Color(243, 243, 243));
        bearbetning_field.setColumns(3);
        bearbetning_field.setForeground(new java.awt.Color(102, 102, 102));
        bearbetning_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        bearbetning_field.setText("0");
        bearbetning_field.setBorder(null);
        bearbetning_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        bearbetning_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bearbetning_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                bearbetning_fieldFocusLost(evt);
            }
        });
        bearbetning_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bearbetning_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bearbetning_fieldKeyReleased(evt);
            }
        });

        bearbetning_panel.setBackground(new java.awt.Color(213, 232, 232));
        bearbetning_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        bearbetning_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bearbetning_panelMouseReleased(evt);
            }
        });

        bearbetning_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        bearbetning_label.setText("Bearbetning");
        bearbetning_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bearbetning_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout bearbetning_panelLayout = new javax.swing.GroupLayout(bearbetning_panel);
        bearbetning_panel.setLayout(bearbetning_panelLayout);
        bearbetning_panelLayout.setHorizontalGroup(
            bearbetning_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bearbetning_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bearbetning_label)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        bearbetning_panelLayout.setVerticalGroup(
            bearbetning_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bearbetning_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        material_kvalitet_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        material_kvalitet_combobox.setForeground(new java.awt.Color(102, 102, 102));
        material_kvalitet_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Välj material kvalitet", "Lätta material", "Svåra material" }));
        material_kvalitet_combobox.setSelectedIndex(1);
        material_kvalitet_combobox.setBorder(null);
        material_kvalitet_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                material_kvalitet_comboboxActionPerformed(evt);
            }
        });

        bandtyp_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        bandtyp_combobox.setForeground(new java.awt.Color(102, 102, 102));
        bandtyp_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vanlig", "Hydraulik", "Seriekapning korta" }));
        bandtyp_combobox.setBorder(null);
        bandtyp_combobox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        bandtyp_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bandtyp_comboboxActionPerformed(evt);
            }
        });

        påslag_field.setBackground(new java.awt.Color(243, 243, 243));
        påslag_field.setForeground(new java.awt.Color(102, 102, 102));
        påslag_field.setText("0");
        påslag_field.setBorder(null);
        påslag_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        påslag_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                påslag_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                påslag_fieldFocusLost(evt);
            }
        });
        påslag_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                påslag_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                påslag_fieldKeyReleased(evt);
            }
        });

        gerkap_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        gerkap_combobox.setForeground(new java.awt.Color(102, 102, 102));
        gerkap_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gerkap", "J K N Q (Olika vinkel)", "B C D E F G", "J K N Q (Linka vikel)", "L M P R" }));
        gerkap_combobox.setBorder(null);
        gerkap_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerkap_comboboxActionPerformed(evt);
            }
        });

        spara_artikelnummer_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        spara_artikelnummer_button.setForeground(new java.awt.Color(102, 102, 102));
        spara_artikelnummer_button.setText("Spara artikelnummer");
        spara_artikelnummer_button.setBorder(null);
        spara_artikelnummer_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                spara_artikelnummer_buttonMouseReleased(evt);
            }
        });
        spara_artikelnummer_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spara_artikelnummer_buttonActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(102, 102, 102));
        jButton4.setText("Genomför kalkyl");
        jButton4.setBorder(null);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        valutakurs_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        valutakurs_combobox.setForeground(new java.awt.Color(102, 102, 102));
        valutakurs_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Valutakurs", "Dagens valutakurs" }));
        valutakurs_combobox.setBorder(null);
        valutakurs_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valutakurs_comboboxActionPerformed(evt);
            }
        });

        stålkvalitet_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        stålkvalitet_combobox.setForeground(new java.awt.Color(102, 102, 102));
        stålkvalitet_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stålkvalitet", "SVKKR", "SVVKR", "SVKCKR", "SÖE470", "SÖDIN", "VSS355", "VSS355S", "VSC45", "VSC45S", "Alternativ stålkvalitet" }));
        stålkvalitet_combobox.setBorder(null);
        stålkvalitet_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stålkvalitet_comboboxActionPerformed(evt);
            }
        });

        stålverk_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        stålverk_combobox.setForeground(new java.awt.Color(102, 102, 102));
        stålverk_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Välj stålverk" }));
        stålverk_combobox.setBorder(null);
        stålverk_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stålverk_comboboxActionPerformed(evt);
            }
        });

        jSeparator21.setForeground(new java.awt.Color(204, 204, 204));

        kilo_per_meter_panel.setBackground(new java.awt.Color(213, 232, 232));
        kilo_per_meter_panel.setPreferredSize(new java.awt.Dimension(253, 20));
        kilo_per_meter_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kilo_per_meter_panelMouseReleased(evt);
            }
        });

        kilo_per_meter_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        kilo_per_meter_label.setText("Vikt per meter");
        kilo_per_meter_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kilo_per_meter_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout kilo_per_meter_panelLayout = new javax.swing.GroupLayout(kilo_per_meter_panel);
        kilo_per_meter_panel.setLayout(kilo_per_meter_panelLayout);
        kilo_per_meter_panelLayout.setHorizontalGroup(
            kilo_per_meter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kilo_per_meter_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kilo_per_meter_label)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        kilo_per_meter_panelLayout.setVerticalGroup(
            kilo_per_meter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kilo_per_meter_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        kilo_per_meter_field.setBackground(new java.awt.Color(243, 243, 243));
        kilo_per_meter_field.setColumns(3);
        kilo_per_meter_field.setForeground(new java.awt.Color(102, 102, 102));
        kilo_per_meter_field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        kilo_per_meter_field.setText("0");
        kilo_per_meter_field.setBorder(null);
        kilo_per_meter_field.setMargin(new java.awt.Insets(2, 5, 2, 2));
        kilo_per_meter_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kilo_per_meter_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                kilo_per_meter_fieldFocusLost(evt);
            }
        });
        kilo_per_meter_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kilo_per_meter_fieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                kilo_per_meter_fieldKeyReleased(evt);
            }
        });

        kilo_per_meter_panel2.setBackground(new java.awt.Color(213, 232, 232));
        kilo_per_meter_panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kilo_per_meter_panel2MouseReleased(evt);
            }
        });

        kilo_per_meter_label2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        kilo_per_meter_label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kilo_per_meter_label2.setText("kg/m");
        kilo_per_meter_label2.setToolTipText("");
        kilo_per_meter_label2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kilo_per_meter_label2MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout kilo_per_meter_panel2Layout = new javax.swing.GroupLayout(kilo_per_meter_panel2);
        kilo_per_meter_panel2.setLayout(kilo_per_meter_panel2Layout);
        kilo_per_meter_panel2Layout.setHorizontalGroup(
            kilo_per_meter_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kilo_per_meter_label2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );
        kilo_per_meter_panel2Layout.setVerticalGroup(
            kilo_per_meter_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kilo_per_meter_label2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_panelLayout = new javax.swing.GroupLayout(panel_panel);
        panel_panel.setLayout(panel_panelLayout);
        panel_panelLayout.setHorizontalGroup(
            panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator21)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spara_artikelnummer_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_panelLayout.createSequentialGroup()
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(datum_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(artikel_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(artikelnummer_field)
                            .addComponent(datum_field)))
                    .addComponent(gerkap_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(material_kvalitet_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bandtyp_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_panelLayout.createSequentialGroup()
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(säljare_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stålverk_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stålkvalitet_combobox, javax.swing.GroupLayout.Alignment.LEADING, 0, 202, Short.MAX_VALUE)
                            .addComponent(valutakurs_combobox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stålkvalitet_field)
                            .addGroup(panel_panelLayout.createSequentialGroup()
                                .addComponent(valutakurs_field, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(valutakurs_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(stålverk_field)
                            .addComponent(säljare_field)))
                    .addGroup(panel_panelLayout.createSequentialGroup()
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bearbetning_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(dimension_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dimension_field, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bearbetning_field, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bearbetning_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dimension_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel_panelLayout.createSequentialGroup()
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ställkostnad_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(ursprungslängd_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ursprungslängd_field)
                            .addComponent(ställkostnad_field))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ställkostnad_panel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ursprungslängd_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel_panelLayout.createSequentialGroup()
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(påslag_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(spill_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(fraktkostnad_sicam_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(fraktkostnad_per_kilo_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_panelLayout.createSequentialGroup()
                                .addComponent(påslag_field)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(påslag_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_panelLayout.createSequentialGroup()
                                .addComponent(fraktkostnad_field)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fraktkostnad_per_kilo_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_panelLayout.createSequentialGroup()
                                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(spill_field)
                                    .addComponent(fraktkostnad_sicam_field, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fraktkostnad_sicam_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spill_procent_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(panel_panelLayout.createSequentialGroup()
                        .addComponent(euro_per_ton_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(euro_per_ton_field)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(euro_per_ton_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_panelLayout.createSequentialGroup()
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(längd_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(antal_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(antal_field)
                            .addComponent(längd_field, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(längd_panel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(antal_panel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_panelLayout.createSequentialGroup()
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(kilo_per_meter_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(längdtolerans_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kilo_per_meter_field)
                            .addComponent(längdtolerans_field))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kilo_per_meter_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(längdtolerans_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panel_panelLayout.setVerticalGroup(
            panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bandtyp_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(material_kvalitet_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gerkap_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(valutakurs_combobox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                        .addComponent(valutakurs_field, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(valutakurs_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stålkvalitet_combobox, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(stålkvalitet_field))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stålverk_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stålverk_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(säljare_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(säljare_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datum_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datum_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(artikel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(artikelnummer_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bearbetning_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bearbetning_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bearbetning_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dimension_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dimension_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dimension_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(antal_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(antal_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(antal_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(längd_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(längd_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(längd_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kilo_per_meter_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kilo_per_meter_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kilo_per_meter_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(längdtolerans_field, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(längdtolerans_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(längdtolerans_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(euro_per_ton_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(euro_per_ton_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(euro_per_ton_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ställkostnad_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ställkostnad_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ställkostnad_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ursprungslängd_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ursprungslängd_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ursprungslängd_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(påslag_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(påslag_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(påslag_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fraktkostnad_per_kilo_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fraktkostnad_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fraktkostnad_per_kilo_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fraktkostnad_sicam_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fraktkostnad_sicam_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fraktkostnad_sicam_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spill_procent_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spill_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spill_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(spara_artikelnummer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        stålverk_field.getAccessibleContext().setAccessibleParent(panel_panel);
        stålkvalitet_field.getAccessibleContext().setAccessibleParent(panel_panel);
        valutakurs_field.getAccessibleContext().setAccessibleParent(panel_panel);
        spill_field.getAccessibleContext().setAccessibleParent(panel_panel);
        fraktkostnad_sicam_field.getAccessibleContext().setAccessibleParent(panel_panel);
        fraktkostnad_field.getAccessibleContext().setAccessibleParent(panel_panel);
        ursprungslängd_field.getAccessibleContext().setAccessibleParent(panel_panel);
        ställkostnad_field.getAccessibleContext().setAccessibleParent(panel_panel);
        euro_per_ton_field.getAccessibleContext().setAccessibleParent(panel_panel);
        längdtolerans_field.getAccessibleContext().setAccessibleParent(panel_panel);
        längd_field.getAccessibleContext().setAccessibleParent(panel_panel);
        antal_field.getAccessibleContext().setAccessibleParent(panel_panel);
        dimension_field.getAccessibleContext().setAccessibleParent(panel_panel);
        artikelnummer_field.getAccessibleContext().setAccessibleParent(panel_panel);
        datum_field.getAccessibleContext().setAccessibleParent(panel_panel);
        säljare_field.getAccessibleContext().setAccessibleParent(panel_panel);
        bearbetning_field.getAccessibleContext().setAccessibleParent(panel_panel);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setToolTipText("Kostnader");
        jTabbedPane1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTabbedPane1.setName("Kostnader historik"); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        kostnader_table.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        kostnader_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Total Produktionskostnad per styck", "Total Produktionskostnad", "Kostnad per styck exkl. frakt + prod. kostnad", "Total kostnad per leverans", "Fraktkostnad per styck", "Fraktkostnad totalt", "Kostnad per styck inkl. frakt + prod. kostnad", "Kostnad leverans inkl. frakt + prod. kostnad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        kostnader_table.setGridColor(new java.awt.Color(0, 0, 0));
        kostnader_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        kostnader_table.setOpaque(false);
        kostnader_table.setRowHeight(25);
        kostnader_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                kostnader_tableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(kostnader_table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Kostnader", jPanel3);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        priser_table.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        priser_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pris KUND/KG exkl. prodkostnad + Frakt", "Pris KUND/KG inkl. prodkostnad + Frakt", "Pris KUND/styck inkl. frakt + prod.kostnad", "Pris KUND/leverans"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        priser_table.setGridColor(new java.awt.Color(0, 0, 0));
        priser_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        priser_table.setOpaque(false);
        priser_table.setRowHeight(25);
        jScrollPane2.setViewportView(priser_table);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Priser", jPanel9);

        övrigt_table.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        övrigt_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Artikelnummer", "Kategori", "Dimension", "Antal", "Längd", "Vikt", "Euro per ton", "Valutakurs", "Tolerans"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        övrigt_table.setGridColor(new java.awt.Color(0, 0, 0));
        övrigt_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        övrigt_table.setOpaque(false);
        övrigt_table.setRowHeight(25);
        jScrollPane5.setViewportView(övrigt_table);

        jTabbedPane1.addTab("Övrigt", jScrollPane5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Kostnader");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        avtalad_kvantitet_panel.setBackground(new java.awt.Color(255, 255, 255));

        kvantitet_år_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kvantitet_per_år_labelMouseReleased(evt);
            }
        });

        kvantitet_år_field.setForeground(new java.awt.Color(102, 102, 102));
        kvantitet_år_field.setText("0");
        kvantitet_år_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                kvantitet_år_fieldFocusLost(evt);
            }
        });
        kvantitet_år_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                kvantitet_år_fieldKeyReleased(evt);
            }
        });

        kvantitet_per_år_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        kvantitet_per_år_label.setForeground(new java.awt.Color(102, 102, 102));
        kvantitet_per_år_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kvantitet_per_år_label.setText("Kvantitet per år");
        kvantitet_per_år_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kvantitet_per_år_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout kvantitet_år_panelLayout = new javax.swing.GroupLayout(kvantitet_år_panel);
        kvantitet_år_panel.setLayout(kvantitet_år_panelLayout);
        kvantitet_år_panelLayout.setHorizontalGroup(
            kvantitet_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kvantitet_år_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kvantitet_per_år_label)
                .addGap(13, 13, 13))
            .addGroup(kvantitet_år_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kvantitet_år_field, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kvantitet_år_panelLayout.setVerticalGroup(
            kvantitet_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kvantitet_år_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kvantitet_per_år_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kvantitet_år_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        marginal_år_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                marginal_per_år_labelMouseReleased(evt);
            }
        });

        marginal_år_field.setEditable(false);
        marginal_år_field.setBackground(new java.awt.Color(255, 255, 255));
        marginal_år_field.setForeground(new java.awt.Color(102, 102, 102));
        marginal_år_field.setText("0");

        marginal_per_år_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        marginal_per_år_label.setForeground(new java.awt.Color(102, 102, 102));
        marginal_per_år_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        marginal_per_år_label.setText("Marginal / år");
        marginal_per_år_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                marginal_per_år_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout marginal_år_panelLayout = new javax.swing.GroupLayout(marginal_år_panel);
        marginal_år_panel.setLayout(marginal_år_panelLayout);
        marginal_år_panelLayout.setHorizontalGroup(
            marginal_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(marginal_år_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(marginal_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(marginal_år_field, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(marginal_per_år_label))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        marginal_år_panelLayout.setVerticalGroup(
            marginal_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, marginal_år_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(marginal_per_år_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(marginal_år_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        omsättning_år_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                omsättning_per_år_labelMouseReleased(evt);
            }
        });

        omsättning_år_field.setEditable(false);
        omsättning_år_field.setBackground(new java.awt.Color(255, 255, 255));
        omsättning_år_field.setForeground(new java.awt.Color(102, 102, 102));
        omsättning_år_field.setText("0");

        omsättning_per_år_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        omsättning_per_år_label.setForeground(new java.awt.Color(102, 102, 102));
        omsättning_per_år_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        omsättning_per_år_label.setText("Omsättning / år");
        omsättning_per_år_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                omsättning_per_år_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout omsättning_år_panelLayout = new javax.swing.GroupLayout(omsättning_år_panel);
        omsättning_år_panel.setLayout(omsättning_år_panelLayout);
        omsättning_år_panelLayout.setHorizontalGroup(
            omsättning_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(omsättning_år_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(omsättning_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(omsättning_år_field, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(omsättning_per_år_label))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        omsättning_år_panelLayout.setVerticalGroup(
            omsättning_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, omsättning_år_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(omsättning_per_år_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(omsättning_år_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ton_år_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ton_år_panelMouseReleased(evt);
            }
        });

        ton_år_field.setEditable(false);
        ton_år_field.setBackground(new java.awt.Color(255, 255, 255));
        ton_år_field.setForeground(new java.awt.Color(102, 102, 102));
        ton_år_field.setText("0");

        ton_per_år_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        ton_per_år_label.setForeground(new java.awt.Color(102, 102, 102));
        ton_per_år_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ton_per_år_label.setText("Ton / år");
        ton_per_år_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ton_år_panelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout ton_år_panelLayout = new javax.swing.GroupLayout(ton_år_panel);
        ton_år_panel.setLayout(ton_år_panelLayout);
        ton_år_panelLayout.setHorizontalGroup(
            ton_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ton_år_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ton_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ton_år_field, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ton_år_panelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(ton_per_år_label)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        ton_år_panelLayout.setVerticalGroup(
            ton_år_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ton_år_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ton_per_år_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ton_år_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout avtalad_kvantitet_panelLayout = new javax.swing.GroupLayout(avtalad_kvantitet_panel);
        avtalad_kvantitet_panel.setLayout(avtalad_kvantitet_panelLayout);
        avtalad_kvantitet_panelLayout.setHorizontalGroup(
            avtalad_kvantitet_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(avtalad_kvantitet_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kvantitet_år_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marginal_år_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(omsättning_år_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ton_år_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(691, Short.MAX_VALUE))
        );
        avtalad_kvantitet_panelLayout.setVerticalGroup(
            avtalad_kvantitet_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(avtalad_kvantitet_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(avtalad_kvantitet_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kvantitet_år_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(marginal_år_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(omsättning_år_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ton_år_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Avtalad kvantitet", avtalad_kvantitet_panel);

        försäljning_hittils_panel.setBackground(new java.awt.Color(255, 255, 255));

        kvantitet_hittils_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kvantitet_hittils_labelMouseReleased(evt);
            }
        });

        kvantitet_hittils_field.setEditable(false);
        kvantitet_hittils_field.setBackground(new java.awt.Color(255, 255, 255));
        kvantitet_hittils_field.setForeground(new java.awt.Color(102, 102, 102));
        kvantitet_hittils_field.setText("0");
        kvantitet_hittils_field.setToolTipText("");

        kvantitet_hittils_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        kvantitet_hittils_label.setForeground(new java.awt.Color(102, 102, 102));
        kvantitet_hittils_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kvantitet_hittils_label.setText("Kvantitet hittils");
        kvantitet_hittils_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kvantitet_hittils_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout kvantitet_hittils_panelLayout = new javax.swing.GroupLayout(kvantitet_hittils_panel);
        kvantitet_hittils_panel.setLayout(kvantitet_hittils_panelLayout);
        kvantitet_hittils_panelLayout.setHorizontalGroup(
            kvantitet_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kvantitet_hittils_panelLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(kvantitet_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kvantitet_hittils_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kvantitet_hittils_field))
                .addGap(13, 13, 13))
        );
        kvantitet_hittils_panelLayout.setVerticalGroup(
            kvantitet_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kvantitet_hittils_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kvantitet_hittils_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kvantitet_hittils_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        belopp_hittils_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                belopp_hittils_panelMouseReleased(evt);
            }
        });

        vinst_hittils_field.setEditable(false);
        vinst_hittils_field.setBackground(new java.awt.Color(255, 255, 255));
        vinst_hittils_field.setForeground(new java.awt.Color(102, 102, 102));
        vinst_hittils_field.setText("0");

        belopp_hittils_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        belopp_hittils_label.setForeground(new java.awt.Color(102, 102, 102));
        belopp_hittils_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        belopp_hittils_label.setText("Belopp hittils");
        belopp_hittils_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                belopp_hittils_panelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout belopp_hittils_panelLayout = new javax.swing.GroupLayout(belopp_hittils_panel);
        belopp_hittils_panel.setLayout(belopp_hittils_panelLayout);
        belopp_hittils_panelLayout.setHorizontalGroup(
            belopp_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(belopp_hittils_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(belopp_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(belopp_hittils_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vinst_hittils_field, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        belopp_hittils_panelLayout.setVerticalGroup(
            belopp_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, belopp_hittils_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(belopp_hittils_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vinst_hittils_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout försäljning_hittils_panelLayout = new javax.swing.GroupLayout(försäljning_hittils_panel);
        försäljning_hittils_panel.setLayout(försäljning_hittils_panelLayout);
        försäljning_hittils_panelLayout.setHorizontalGroup(
            försäljning_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(försäljning_hittils_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kvantitet_hittils_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(belopp_hittils_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(935, Short.MAX_VALUE))
        );
        försäljning_hittils_panelLayout.setVerticalGroup(
            försäljning_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(försäljning_hittils_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(försäljning_hittils_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(belopp_hittils_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kvantitet_hittils_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Försäljning hittils i år", försäljning_hittils_panel);

        aktuell_försäljningsruta_panel.setBackground(new java.awt.Color(255, 255, 255));

        marginal_total_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                marginal_total_labelMouseReleased(evt);
            }
        });

        marginal_total_field.setEditable(false);
        marginal_total_field.setBackground(new java.awt.Color(255, 255, 255));
        marginal_total_field.setForeground(new java.awt.Color(102, 102, 102));

        marginal_total_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        marginal_total_label.setForeground(new java.awt.Color(102, 102, 102));
        marginal_total_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        marginal_total_label.setText("Marginal total");
        marginal_total_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                marginal_total_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout marginal_total_panelLayout = new javax.swing.GroupLayout(marginal_total_panel);
        marginal_total_panel.setLayout(marginal_total_panelLayout);
        marginal_total_panelLayout.setHorizontalGroup(
            marginal_total_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, marginal_total_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(marginal_total_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(marginal_total_label, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(marginal_total_field))
                .addGap(13, 13, 13))
        );
        marginal_total_panelLayout.setVerticalGroup(
            marginal_total_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, marginal_total_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(marginal_total_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marginal_total_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        marginal_total_label.getAccessibleContext().setAccessibleName("Kvantitet hittils");

        marginal_styck_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                marginal_styck_labelMouseReleased(evt);
            }
        });

        marginal_styck_field.setEditable(false);
        marginal_styck_field.setBackground(new java.awt.Color(255, 255, 255));
        marginal_styck_field.setForeground(new java.awt.Color(102, 102, 102));

        marginal_styck_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        marginal_styck_label.setForeground(new java.awt.Color(102, 102, 102));
        marginal_styck_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        marginal_styck_label.setText("Marginal styck");
        marginal_styck_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                marginal_styck_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout marginal_styck_panelLayout = new javax.swing.GroupLayout(marginal_styck_panel);
        marginal_styck_panel.setLayout(marginal_styck_panelLayout);
        marginal_styck_panelLayout.setHorizontalGroup(
            marginal_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(marginal_styck_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(marginal_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(marginal_styck_field, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(marginal_styck_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        marginal_styck_panelLayout.setVerticalGroup(
            marginal_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, marginal_styck_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(marginal_styck_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marginal_styck_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        täckningsgrad_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                täckningsgrad_panelMouseReleased(evt);
            }
        });

        täckningsgrad_field.setEditable(false);
        täckningsgrad_field.setBackground(new java.awt.Color(255, 255, 255));
        täckningsgrad_field.setForeground(new java.awt.Color(102, 102, 102));

        täckningsgrad_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        täckningsgrad_label.setForeground(new java.awt.Color(102, 102, 102));
        täckningsgrad_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        täckningsgrad_label.setText("Täckningsgrad");
        täckningsgrad_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                täckningsgrad_panelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout täckningsgrad_panelLayout = new javax.swing.GroupLayout(täckningsgrad_panel);
        täckningsgrad_panel.setLayout(täckningsgrad_panelLayout);
        täckningsgrad_panelLayout.setHorizontalGroup(
            täckningsgrad_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, täckningsgrad_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(täckningsgrad_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(täckningsgrad_label, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(täckningsgrad_field))
                .addContainerGap())
        );
        täckningsgrad_panelLayout.setVerticalGroup(
            täckningsgrad_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, täckningsgrad_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(täckningsgrad_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(täckningsgrad_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        totalvikt_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                totalvikt_panelMouseReleased(evt);
            }
        });

        totalvikt_field.setEditable(false);
        totalvikt_field.setBackground(new java.awt.Color(255, 255, 255));
        totalvikt_field.setForeground(new java.awt.Color(102, 102, 102));

        totalvikt_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        totalvikt_label.setForeground(new java.awt.Color(102, 102, 102));
        totalvikt_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalvikt_label.setText("Totalvikt");
        totalvikt_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                totalvikt_panelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout totalvikt_panelLayout = new javax.swing.GroupLayout(totalvikt_panel);
        totalvikt_panel.setLayout(totalvikt_panelLayout);
        totalvikt_panelLayout.setHorizontalGroup(
            totalvikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalvikt_panelLayout.createSequentialGroup()
                .addGroup(totalvikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(totalvikt_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(totalvikt_field, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(totalvikt_panelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(totalvikt_label)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        totalvikt_panelLayout.setVerticalGroup(
            totalvikt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalvikt_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalvikt_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalvikt_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        vikt_styck_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                vikt_styck_panelMouseReleased(evt);
            }
        });

        viktstyck_field.setEditable(false);
        viktstyck_field.setBackground(new java.awt.Color(255, 255, 255));
        viktstyck_field.setForeground(new java.awt.Color(102, 102, 102));

        vikt_styck_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        vikt_styck_label.setForeground(new java.awt.Color(102, 102, 102));
        vikt_styck_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vikt_styck_label.setText("Vikt styck");
        vikt_styck_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                vikt_styck_panelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout vikt_styck_panelLayout = new javax.swing.GroupLayout(vikt_styck_panel);
        vikt_styck_panel.setLayout(vikt_styck_panelLayout);
        vikt_styck_panelLayout.setHorizontalGroup(
            vikt_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vikt_styck_panelLayout.createSequentialGroup()
                .addGroup(vikt_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vikt_styck_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(viktstyck_field, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vikt_styck_panelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(vikt_styck_label)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        vikt_styck_panelLayout.setVerticalGroup(
            vikt_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vikt_styck_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(vikt_styck_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viktstyck_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pris_styck_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pris_styck_labelMouseReleased(evt);
            }
        });

        prisstyck_field.setEditable(false);
        prisstyck_field.setBackground(new java.awt.Color(255, 255, 255));
        prisstyck_field.setForeground(new java.awt.Color(102, 102, 102));

        pris_styck_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        pris_styck_label.setForeground(new java.awt.Color(102, 102, 102));
        pris_styck_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pris_styck_label.setText("Pris styck");
        pris_styck_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pris_styck_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout pris_styck_panelLayout = new javax.swing.GroupLayout(pris_styck_panel);
        pris_styck_panel.setLayout(pris_styck_panelLayout);
        pris_styck_panelLayout.setHorizontalGroup(
            pris_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pris_styck_panelLayout.createSequentialGroup()
                .addGroup(pris_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pris_styck_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(prisstyck_field, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pris_styck_panelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(pris_styck_label)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        pris_styck_panelLayout.setVerticalGroup(
            pris_styck_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pris_styck_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pris_styck_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prisstyck_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pris_totalt_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pris_totalt_labelMouseReleased(evt);
            }
        });

        pristotalt_field.setEditable(false);
        pristotalt_field.setBackground(new java.awt.Color(255, 255, 255));
        pristotalt_field.setForeground(new java.awt.Color(102, 102, 102));

        pris_totalt_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        pris_totalt_label.setForeground(new java.awt.Color(102, 102, 102));
        pris_totalt_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pris_totalt_label.setText("Pris totalt");
        pris_totalt_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pris_totalt_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout pris_totalt_panelLayout = new javax.swing.GroupLayout(pris_totalt_panel);
        pris_totalt_panel.setLayout(pris_totalt_panelLayout);
        pris_totalt_panelLayout.setHorizontalGroup(
            pris_totalt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pris_totalt_panelLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(pristotalt_field, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pris_totalt_panelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(pris_totalt_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pris_totalt_panelLayout.setVerticalGroup(
            pris_totalt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pris_totalt_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pris_totalt_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pristotalt_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        cykeltid_text1.setEditable(false);
        cykeltid_text1.setBackground(new java.awt.Color(255, 255, 255));
        cykeltid_text1.setForeground(new java.awt.Color(102, 102, 102));
        cykeltid_text1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cykeltid_text1ActionPerformed(evt);
            }
        });

        cykeltid_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        cykeltid_label.setForeground(new java.awt.Color(102, 102, 102));
        cykeltid_label.setText("Cykeltid");

        totaltid_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        totaltid_label.setForeground(new java.awt.Color(102, 102, 102));
        totaltid_label.setText("Totaltid");

        kostnadperbit_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        kostnadperbit_label.setForeground(new java.awt.Color(102, 102, 102));
        kostnadperbit_label.setText("Kostnad per bit");

        kostnad_per_bit_text1.setEditable(false);
        kostnad_per_bit_text1.setBackground(new java.awt.Color(255, 255, 255));
        kostnad_per_bit_text1.setForeground(new java.awt.Color(102, 102, 102));
        kostnad_per_bit_text1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kostnad_per_bit_text1ActionPerformed(evt);
            }
        });

        totaltid_text1.setEditable(false);
        totaltid_text1.setBackground(new java.awt.Color(255, 255, 255));
        totaltid_text1.setForeground(new java.awt.Color(102, 102, 102));
        totaltid_text1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totaltid_text1ActionPerformed(evt);
            }
        });

        jSeparator22.setForeground(new java.awt.Color(204, 204, 204));

        jSeparator23.setForeground(new java.awt.Color(204, 204, 204));

        jSeparator24.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout aktuell_försäljningsruta_panelLayout = new javax.swing.GroupLayout(aktuell_försäljningsruta_panel);
        aktuell_försäljningsruta_panel.setLayout(aktuell_försäljningsruta_panelLayout);
        aktuell_försäljningsruta_panelLayout.setHorizontalGroup(
            aktuell_försäljningsruta_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(aktuell_försäljningsruta_panelLayout.createSequentialGroup()
                .addGroup(aktuell_försäljningsruta_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aktuell_försäljningsruta_panelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(aktuell_försäljningsruta_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cykeltid_text1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aktuell_försäljningsruta_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(kostnadperbit_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(kostnad_per_bit_text1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aktuell_försäljningsruta_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(totaltid_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(totaltid_text1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cykeltid_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(aktuell_försäljningsruta_panelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(marginal_total_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(marginal_styck_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(täckningsgrad_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalvikt_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vikt_styck_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pris_styck_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(pris_totalt_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 317, Short.MAX_VALUE)))
                .addContainerGap())
        );
        aktuell_försäljningsruta_panelLayout.setVerticalGroup(
            aktuell_försäljningsruta_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aktuell_försäljningsruta_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(aktuell_försäljningsruta_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pris_styck_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pris_totalt_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vikt_styck_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalvikt_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(marginal_styck_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(marginal_total_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(täckningsgrad_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(75, 75, 75)
                .addComponent(cykeltid_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(cykeltid_text1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(totaltid_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(totaltid_text1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(kostnadperbit_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(kostnad_per_bit_text1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Aktuell försäljningsruta", aktuell_försäljningsruta_panel);

        tidigare_försäljning_panel.setBackground(new java.awt.Color(255, 255, 255));

        tidigare_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tidigare_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Artikelnummer", "Datum", "Kategori", "Dimension", "Antal detaljer", "Längd", "Tidigare pris", "Euro per ton", "Valutakurs"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tidigare_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tidigare_table.setOpaque(false);
        tidigare_table.setRowHeight(25);
        tidigare_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tidigare_tableMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tidigare_table);

        förändring_i_procent_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                förändring_i_procent_labelMouseReleased(evt);
            }
        });

        förändring_field_procent.setEditable(false);
        förändring_field_procent.setBackground(new java.awt.Color(255, 255, 255));
        förändring_field_procent.setForeground(new java.awt.Color(102, 102, 102));

        förändring_i_procent_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        förändring_i_procent_label.setForeground(new java.awt.Color(102, 102, 102));
        förändring_i_procent_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        förändring_i_procent_label.setText("Förändring i %");
        förändring_i_procent_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                förändring_i_procent_labelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout förändring_i_procent_panelLayout = new javax.swing.GroupLayout(förändring_i_procent_panel);
        förändring_i_procent_panel.setLayout(förändring_i_procent_panelLayout);
        förändring_i_procent_panelLayout.setHorizontalGroup(
            förändring_i_procent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(förändring_i_procent_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(förändring_i_procent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(förändring_field_procent, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(förändring_i_procent_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        förändring_i_procent_panelLayout.setVerticalGroup(
            förändring_i_procent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, förändring_i_procent_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(förändring_i_procent_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(förändring_field_procent, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        förändring_i_kr_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                förändring_i_kr_panelMouseReleased(evt);
            }
        });

        förändring_i_kr_field.setEditable(false);
        förändring_i_kr_field.setBackground(new java.awt.Color(255, 255, 255));
        förändring_i_kr_field.setForeground(new java.awt.Color(102, 102, 102));

        förändring_i_kr_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        förändring_i_kr_label.setForeground(new java.awt.Color(102, 102, 102));
        förändring_i_kr_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        förändring_i_kr_label.setText("Förändring i kr");
        förändring_i_kr_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                förändring_i_kr_panelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout förändring_i_kr_panelLayout = new javax.swing.GroupLayout(förändring_i_kr_panel);
        förändring_i_kr_panel.setLayout(förändring_i_kr_panelLayout);
        förändring_i_kr_panelLayout.setHorizontalGroup(
            förändring_i_kr_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(förändring_i_kr_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(förändring_i_kr_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(förändring_i_kr_field, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(förändring_i_kr_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        förändring_i_kr_panelLayout.setVerticalGroup(
            förändring_i_kr_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, förändring_i_kr_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(förändring_i_kr_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(förändring_i_kr_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        sök_tabel_label1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        sök_tabel_label1.setForeground(new java.awt.Color(102, 102, 102));
        sök_tabel_label1.setText("Filtrera tabellen efter värde:");

        jTextField2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(102, 102, 102));
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton11.setText("Ta bort valt artikelnummer");
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton11MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(sök_tabel_label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton11))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(sök_tabel_label1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton11))
        );

        javax.swing.GroupLayout tidigare_försäljning_panelLayout = new javax.swing.GroupLayout(tidigare_försäljning_panel);
        tidigare_försäljning_panel.setLayout(tidigare_försäljning_panelLayout);
        tidigare_försäljning_panelLayout.setHorizontalGroup(
            tidigare_försäljning_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(tidigare_försäljning_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tidigare_försäljning_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tidigare_försäljning_panelLayout.createSequentialGroup()
                        .addComponent(förändring_i_procent_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(förändring_i_kr_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tidigare_försäljning_panelLayout.setVerticalGroup(
            tidigare_försäljning_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tidigare_försäljning_panelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tidigare_försäljning_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(förändring_i_procent_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(förändring_i_kr_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Tidigare försäljning", tidigare_försäljning_panel);

        jScrollPane7.setPreferredSize(new java.awt.Dimension(452, 250));

        alla_artiklar_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        alla_artiklar_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Artikelnummer", "Senast såld", "Kategori", "Dimension", "Antal detaljer", "Längd", "Euro per ton", "Valutakurs"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        alla_artiklar_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        alla_artiklar_table.setOpaque(false);
        alla_artiklar_table.setRowHeight(25);
        alla_artiklar_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                alla_artiklar_tableMouseReleased(evt);
            }
        });
        jScrollPane7.setViewportView(alla_artiklar_table);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        sök_tabel_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        sök_tabel_label.setForeground(new java.awt.Color(102, 102, 102));
        sök_tabel_label.setText("Filtrera tabellen efter värde:");

        jTextField1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton3.setText("Ta bort valt artikelnummer");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton3MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sök_tabel_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(sök_tabel_label, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton3))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Artikelnummer", jPanel1);

        jTabbedPane2.setSelectedIndex(2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        för_till_orderfönster_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        för_till_orderfönster_button.setForeground(new java.awt.Color(102, 102, 102));
        för_till_orderfönster_button.setText("För vald rad till orderfönstret");
        för_till_orderfönster_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                för_till_orderfönster_buttonActionPerformed(evt);
            }
        });

        ta_bort_rad_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        ta_bort_rad_button.setForeground(new java.awt.Color(102, 102, 102));
        ta_bort_rad_button.setText("Ta bort vald rad");
        ta_bort_rad_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_bort_rad_buttonActionPerformed(evt);
            }
        });

        rensa_tabell_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        rensa_tabell_button.setForeground(new java.awt.Color(102, 102, 102));
        rensa_tabell_button.setText("Rensa tabell");
        rensa_tabell_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rensa_tabell_buttonActionPerformed(evt);
            }
        });

        till_orderfönster_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        till_orderfönster_button.setForeground(new java.awt.Color(102, 102, 102));
        till_orderfönster_button.setText("Till orderfönstret");
        till_orderfönster_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                till_orderfönster_buttonActionPerformed(evt);
            }
        });

        uppdatera_rad_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        uppdatera_rad_button.setForeground(new java.awt.Color(102, 102, 102));
        uppdatera_rad_button.setText("Uppdatera rad");
        uppdatera_rad_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uppdatera_rad_buttonActionPerformed(evt);
            }
        });

        meny_menubar.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("Start");
        jMenu1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jMenuItem4.setText("Välj kund");
        jMenuItem4.setContentAreaFilled(false);
        jMenuItem4.setFocusable(true);
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Välj distrikt");
        jMenuItem5.setToolTipText("");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem7.setText("Stäng");
        jMenuItem7.setFocusable(true);
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        meny_menubar.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setText("Alternativ");
        jMenu2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        Klingkap_meny.setText("Klingkap");
        Klingkap_meny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Klingkap_menyActionPerformed(evt);
            }
        });
        jMenu2.add(Klingkap_meny);

        Plockorder_meny.setText("Plockorder");
        Plockorder_meny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Plockorder_menyActionPerformed(evt);
            }
        });
        jMenu2.add(Plockorder_meny);

        jMenuItem1.setText("Inställningar *");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        meny_menubar.add(jMenu2);

        jMenu3.setBackground(new java.awt.Color(255, 255, 255));
        jMenu3.setText("Hjälp");
        jMenu3.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Ändra färg");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem2.setText("Rapportera fel");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        meny_menubar.add(jMenu3);

        setJMenuBar(meny_menubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uppdatera_rad_button)
                        .addGap(1, 1, 1)
                        .addComponent(för_till_orderfönster_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ta_bort_rad_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rensa_tabell_button, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(till_orderfönster_button)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ta_bort_rad_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(för_till_orderfönster_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(uppdatera_rad_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(till_orderfönster_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rensa_tabell_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_panel.getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void felmeddelande_ok_knappActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_felmeddelande_ok_knappActionPerformed
        /* Stänger ner felmeddelande rutan om man trycker på 'OK' */
        FelmeddelandeFönster.setVisible(false);
    }//GEN-LAST:event_felmeddelande_ok_knappActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        /* Öppnar välj kund fönstret om man trycker på 'Välj kund' i menyn */
        VäljKundFönster.setLocationRelativeTo(this);
        VäljKundFönster.setLocation(this.getX() + 650, this.getY() + 220);
        VäljKundFönster.pack();
        VäljKundFönster.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        /* Stänger ner programmet om man trycker på 'Stäng' i menyn */
        System.exit(0);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        /* Öppnar lösenords fönstret om man trycker på 'Inställningar' i menyn */
        LösenordFönster.setLocationRelativeTo(this);
        LösenordFönster.pack();
        LösenordFönster.setAlwaysOnTop(true);
        LösenordFönster.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void lösenord_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lösenord_buttonActionPerformed
        /*
        ' Kollar ifall personen har knappat in rätt lösenord för att kommma åt
        * inställningar fönstret. Ställer först in ett actioncommand för lösenords
        * fältet så att programmet har någonting att lyssna efter.
         */
        lösenord_passwordfield.setActionCommand("OK");
        String cmd = evt.getActionCommand();

        if ("OK".equals(cmd)) {
            /* Sätter det man skriver i fältet till char arrayen input */
            char[] input;
            input = lösenord_passwordfield.getPassword();
            /* Kollar om lösenordet är det förväntade, och om det stämmer överrens så
            *  visar jag inställningar rutan för personen och återställer texten till 'Lösenord krävs'.
            *  Gömmer även lösenords rutan.
             */
            if (isPasswordCorrect(input)) {
                lösenord_krävs_label.setText("Lösenord krävs:");
                InställningarFönster.setLocationRelativeTo(this);
                InställningarFönster.setLocation(this.getX() + InställningarFönster.getX() / 2 - 25, this.getY() + 120);
                InställningarFönster.pack();
                InställningarFönster.setVisible(true);
                glömt_lösenord_label.setVisible(false);
                LösenordFönster.pack();
                LösenordFönster.setVisible(false);
            } else {
                /* Om man skrivit fel så byter jag texten till något passande och visar frågan om man glömt lösenordet */
                lösenord_krävs_label.setText("Fel lösenord.");
                glömt_lösenord_label.setVisible(true);
                LösenordFönster.pack();
            }

            /* Återställer texten i lösenords fältet till tomt när man är klar 
            *  Sätter även arrayen med lösenords-försök till 0. 
             */
            lösenord_passwordfield.setText("");
            Arrays.fill(input, '0');
        }
    }//GEN-LAST:event_lösenord_buttonActionPerformed

    /* Metod för att kolla om lösenordet är korrekt inslaget */
    private boolean isPasswordCorrect(char[] input) {
        boolean isCorrect;
        /* Hämtar lösenordet till en char array från strängen 'lösenordet',
        *  som skrivs över utav lösenordet i .txt filen (Om man valt ett nytt).
         */
        char[] correctPassword;
        correctPassword = lösenordet.toCharArray();

        /* Kollar om längden stämmer överrens med lösenordets längd */
        if (input.length != correctPassword.length) {
            /* Stämmer inte längden sätter jag false direkt */
            isCorrect = false;
        } else {
            /* Stämmer längden så kollar jag mot det faktiska lösenordet 
            *  och returnerar true om ifall det stämmer.
             */
            isCorrect = Arrays.equals(input, correctPassword);
        }

        /* Återställer lösenords-arrayen med försök till 0 */
        Arrays.fill(correctPassword, '0');

        /* Returnerar ja eller nej baserat på tidigare uträkningar i metoden */
        return isCorrect;
    }

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        /* Öppnar rutan där man kan välja färg för de olika paneler i programmet
        *  när man tryckt på 'Ändra färg' i menyn.
         */
        FärgFönster.setVisible(true);
        FärgFönster.setLocationRelativeTo(this);
        FärgFönster.setLocation(this.getX() + 450, this.getY() + 200);
        FärgFönster.pack();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void Klingkap_menyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Klingkap_menyActionPerformed
        /* Väljer man klingkap så tar jag bort valet för plockorder, om man valt det tidigare */
        Plockorder_meny.setSelected(false);
    }//GEN-LAST:event_Klingkap_menyActionPerformed

    private void Plockorder_menyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Plockorder_menyActionPerformed
        /* Väljer man plockorder så tar jag bort valet för klingkap, om man valt det tidigare */
        Klingkap_meny.setSelected(false);

        /*
        *  Klingkap ska inte medföra någon form av produktionskostnad i ordern, därav sätter jag
        *  de val som har med produktionen att göra osynliga, samt värdet till 0, ifall man har
        *  fyllt i någonting innan man väljer plockorder, för att motverka fel.
         */
        if (Plockorder_meny.isSelected()) {
            längdtolerans_field.setText("0");
            spill_field.setText("0");
            spill_panel.setVisible(false);
            spill_field.setVisible(false);
            spill_procent_panel2.setVisible(false);
            gerkap_combobox.setVisible(false);
            material_kvalitet_combobox.setVisible(false);
            bandtyp_combobox.setVisible(false);
            cykeltid_text1.setVisible(false);
            cykeltid_label.setVisible(false);
            totaltid_text1.setVisible(false);
            totaltid_label.setVisible(false);
            kostnad_per_bit_text1.setVisible(false);
            kostnadperbit_label.setVisible(false);
        } else {
            /* Gör motsatsen ifall man stänger av plockorder, och gör allt relevant synligt igen */
            spill_panel.setVisible(true);
            spill_field.setVisible(true);
            spill_procent_panel2.setVisible(true);
            gerkap_combobox.setVisible(true);
            material_kvalitet_combobox.setVisible(true);
            bandtyp_combobox.setVisible(true);
            cykeltid_text1.setVisible(true);
            cykeltid_label.setVisible(true);
            totaltid_text1.setVisible(true);
            totaltid_label.setVisible(true);
            kostnad_per_bit_text1.setVisible(true);
            kostnadperbit_label.setVisible(true);
        }
        this.pack();
    }//GEN-LAST:event_Plockorder_menyActionPerformed

    private void lägg_till_kund_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lägg_till_kund_buttonActionPerformed
        /*
        *  Lägg till kund knappen i fönstret som öppnas när man startar programmet, alternativt när
        *  man går till 'Välj kund' i menyn. Först kollar jag ifall fältet där man fyller i namn
        *  för ny kund inte är tomt, är det inte det så fortsätter jag med att kolla ifall
        *  namnet som man försöker lägga till redan finns i listan för tidigare inlagda kunder,
        *  och sätter då boolean match till true.
         */
        if (!lägg_till_kund_field.getText().isEmpty()) {
            this.setVisible(true);
            boolean match = false;
            laddatKalkyl = false;

            for (int i = 0; i < välj_kund_combobox.getItemCount(); i++) {
                if (lägg_till_kund_field.getText().matches(välj_kund_combobox.getItemAt(i))) {
                    FelmeddelandeFönster.setVisible(true);
                    FelmeddelandeFönster.setLocationRelativeTo(this);
                    FelmeddelandeFönster.pack();
                    match = true;
                }
            }

            /* Om fältet inte är tomt, och om boolean match är false. */
            if (!match) {
                if (!spara_artikelnummer_button.isVisible()) {
                    spara_artikelnummer_button.setVisible(true);
                    jButton5.setVisible(true);
                }
                /* Fortsätter med att lägga till kunden och sätter kund label till kundens namn som man fyllt i
                *  samt sparar ner kundens namn som en folder i projektets mapp, även de folders som behövs för
                *  kunden, t.ex. artiklar, försäljningar, offerter m.m.
                 */
                kund_label.setText(lägg_till_kund_field.getText());
                kund = kund_label.getText();

                /* TESTAR DATABASEN */
                try {
                    functions.läggTillKund(db, nuvarande_distrikt, nuvarande_ansvarig, kund, "Adress", "Epost");
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                }
                /**
                 * *****************
                 */
                /**
                 * *****************
                 */
                /* Lägger till kunden i välj kund comboboxen i 'Välj kund' menyn */
                välj_kund_combobox.addItem(lägg_till_kund_field.getText());
                path = System.getProperty("user.dir") + "//Sparat//Kunder//" + kund + "//Artikelnummer//";

                /* Återställer alla tabeller, tar bort alla rader */
                reset(tidigare_model);
                reset(kostnader_model);
                reset(priser_model);
                reset(övrigt_model);
                reset(alla_artiklar_model);

                /* Rensar de arraylistor jag använder för att spara ner artikelnummer vid olika stadier av kalkylerna 
                *  t.ex. då man genomför en offert, eller går till försäljning. Jag måste hålla koll på alla variablar
                *  vid alla tillfällen av programmet.
                 */
                artikelnummer_list.clear();
                kategorier_list.clear();
                dimensioner_list.clear();
                total_vikt_list.clear();
                antal_list.clear();
                längd_list.clear();
                tolerans_list.clear();
                pris_styck_list.clear();

                /* Sätter övriga uppgifter till 0, ifall man kalkylerat på en annan kund vid tidigare tillfällen och 
                *  senare byter kund, så att inte vissa uppgifter från den kalkylen är kvar. Samma mentalitet gäller
                *  vid tidigare återställningar i den här metoden.
                 */
                totalvikt_total = 0;
                total_antal = 0;
                total_längd = 0;
                pris_kund_inklusive_total = 0;
                total_marginal = 0;

                marginal_styck_field.setText("0" + " kr");
                marginal_total_field.setText("0" + " kr");
                påslag_field.setText("0" + " kr");
                viktstyck_field.setText("0" + " kg");
                totalvikt_field.setText("0" + " kg");
                prisstyck_field.setText("0" + " kr");
                pristotalt_field.setText("0" + " kr");
                täckningsgrad_field.setText("0" + "%");

                counter = 0;

                töm_fält();
                
                /* Sätter välj kund fönstret till osynligt då vi är klara och kunden har laddats. */
                VäljKundFönster.setVisible(false);
                ladda_ljushet();
                this.pack();
            }
        } else {
            /* Matchar kunden med något som finns i välj kund comboboxen eller om textfältet är tomt
            *  så visar jag ett felmeddelande istället för att genomföra något av ovanstående.
             */
            FelmeddelandeFönster.setVisible(true);
            FelmeddelandeFönster.setLocationRelativeTo(this);
            FelmeddelandeFönster.pack();
        }
    }//GEN-LAST:event_lägg_till_kund_buttonActionPerformed

    private void avslut_metoder(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_avslut_metoder
        /* Alla metoder som körs när man stänger ner programmet. 
        *  Sparar alla färger i .txt fil, sparar avtal upplagda för respektive kund (t.ex. avtalad kvantitet osv)
         */
        if (!"".equals(kund_label.getText())) {
            spara_kund_avtal();
        }
    }//GEN-LAST:event_avslut_metoder

    private void start_metoder(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_start_metoder

        this.setVisible(false);

        kund_label.setText("");

        AnvändareFönster.setVisible(true);
        AnvändareFönster.pack();
        AnvändareFönster.setLocationRelativeTo(null);
        //EN GLOBAL VARIABEL FÖR NUVARANDE KUNDS NAMN 
        kund = kund_label.getText();

        //SÄTTER DATUMET TILL DAGENS DATUM
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDateTime now = LocalDateTime.now();
        datum_field.setText(dtf.format(now));

        //GLOBALA PATHS FÖR ATT GE DIREKTIONER VART FILER SKA SPARAS OCH LADDAS IFRÅN
        path = System.getProperty("user.dir") + "//Sparat//Kunder//" + kund + "//Artikelnummer//";
        path2 = System.getProperty("user.dir") + "//Sparat//Positioner//";
        path3 = System.getProperty("user.dir") + "//Sparat//Kunder//" + kund + "//Kalkyler//";
        artikelnummer_dagens_datum_path = System.getProperty("user.dir") + "//Sparat//Kunder//" + kund + "//Artikelnummer//" + datum_field.getText() + "//";
        historik_path = System.getProperty("user.dir") + "//Sparat//Kunder//" + kund + "//Artikelnummer//Historik//";

        //LÄGGER TILL ALLA PANELER I EN ARRAY
        panels.add(säljare_panel);
        panels.add(datum_panel);
        panels.add(bearbetning_panel);
        panels.add(artikel_panel);
        panels.add(dimension_panel);
        panels.add(antal_panel);
        panels.add(längd_panel);
        panels.add(längdtolerans_panel);
        panels.add(euro_per_ton_panel);
        panels.add(ställkostnad_panel);
        panels.add(ursprungslängd_panel);
        panels.add(påslag_panel);
        panels.add(fraktkostnad_per_kilo_panel);
        panels.add(fraktkostnad_sicam_panel);
        panels.add(spill_panel);
        panels.add(valutakurs_panel2);
        panels.add(bearbetning_panel2);
        panels.add(dimension_panel2);
        panels.add(antal_panel2);
        panels.add(längd_panel2);
        panels.add(längdtolerans_panel2);
        panels.add(euro_per_ton_panel2);
        panels.add(ställkostnad_panel2);
        panels.add(ursprungslängd_panel2);
        panels.add(påslag_panel2);
        panels.add(fraktkostnad_per_kilo_panel2);
        panels.add(fraktkostnad_sicam_panel2);
        panels.add(spill_procent_panel2);
        panels.add(kvantitet_år_panel);
        panels.add(marginal_år_panel);
        panels.add(omsättning_år_panel);
        panels.add(ton_år_panel);
        panels.add(kvantitet_hittils_panel);
        panels.add(belopp_hittils_panel);
        panels.add(marginal_total_panel);
        panels.add(marginal_styck_panel);
        panels.add(täckningsgrad_panel);
        panels.add(totalvikt_panel);
        panels.add(vikt_styck_panel);
        panels.add(pris_styck_panel);
        panels.add(pris_totalt_panel);
        panels.add(förändring_i_procent_panel);
        panels.add(förändring_i_kr_panel);
        panels.add(kilo_per_meter_panel);
        panels.add(kilo_per_meter_panel2);

        panels.get(0).setName("Säljare");
        panels.get(1).setName("Datum");
        panels.get(2).setName("Bearbetning");
        panels.get(3).setName("Artikel");
        panels.get(4).setName("Dimension");
        panels.get(5).setName("Antal");
        panels.get(6).setName("Längd");
        panels.get(7).setName("Längdtolerans");
        panels.get(8).setName("EuroPerTon");
        panels.get(9).setName("Ställkostnad");
        panels.get(10).setName("Ursprungslängd");
        panels.get(11).setName("Påslag");
        panels.get(12).setName("Fraktkostnad");
        panels.get(13).setName("FraktkostnadSicam");
        panels.get(14).setName("Spill");
        panels.get(15).setName("Valutakurs");
        panels.get(16).setName("BearbetningSida");
        panels.get(17).setName("DimensionSida");
        panels.get(18).setName("AntalSida");
        panels.get(19).setName("LängdSida");
        panels.get(20).setName("LängdtoleransSida");
        panels.get(21).setName("EuroPerTonSida");
        panels.get(22).setName("StällkostnadSida");
        panels.get(23).setName("UrsprungslängdSida");
        panels.get(24).setName("PåslagSida");
        panels.get(25).setName("FraktkostnadSida");
        panels.get(26).setName("FraktkostnadSicamSida");
        panels.get(27).setName("SpillSida");
        panels.get(28).setName("KvantitetÅr");
        panels.get(29).setName("MarginalÅr");
        panels.get(30).setName("OmsättningÅr");
        panels.get(31).setName("TonÅr");
        panels.get(32).setName("KvantitetHittils");
        panels.get(33).setName("BeloppHittils");
        panels.get(34).setName("MarginalTotal");
        panels.get(35).setName("MarginalStyck");
        panels.get(36).setName("Täckningsgrad");
        panels.get(37).setName("Totalvikt");
        panels.get(38).setName("ViktStyck");
        panels.get(39).setName("PrisStyck");
        panels.get(40).setName("PrisTotalt");
        panels.get(41).setName("FörändringProcent");
        panels.get(42).setName("FörändringKronor");
        panels.get(43).setName("KiloPerMeter");
        panels.get(44).setName("KiloPerMeterSida");

        //LÄGG TILL ALLA TEXTFIELDS I ARRAY
        fields.add(säljare_field);
        fields.add(datum_field);
        fields.add(bearbetning_field);
        fields.add(artikelnummer_field);
        fields.add(dimension_field);
        fields.add(antal_field);
        fields.add(längd_field);
        fields.add(längdtolerans_field);
        fields.add(euro_per_ton_field);
        fields.add(ställkostnad_field);
        fields.add(ursprungslängd_field);
        fields.add(påslag_field);
        fields.add(fraktkostnad_field);
        fields.add(fraktkostnad_sicam_field);
        fields.add(spill_field);
        fields.add(valutakurs_field);
        fields.add(stålkvalitet_field);
        fields.add(stålverk_field);
        fields.add(kilo_per_meter_field);

        fields.get(0).setName("säljare_field");
        fields.get(1).setName("datum_field");
        fields.get(2).setName("bearbetning_field");
        fields.get(3).setName("artikel_field");
        fields.get(4).setName("dimension_field");
        fields.get(5).setName("antal_field");
        fields.get(6).setName("längd_field");
        fields.get(7).setName("längdtolerans_field");
        fields.get(8).setName("euro_per_ton_field");
        fields.get(9).setName("ställkostnad_field");
        fields.get(10).setName("ursprungslängd_field");
        fields.get(11).setName("täckningsgrad_field");
        fields.get(12).setName("fraktkostnad_field");
        fields.get(13).setName("fraktkostnad_sicam_field");
        fields.get(14).setName("spill_field");
        fields.get(15).setName("valutakurs_field");
        fields.get(16).setName("stålkvalitet_field");
        fields.get(17).setName("stålverk_field");
        fields.get(18).setName("kilo_per_meter_field");
        
        fields.forEach((field) -> {
            field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    uträkning();
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    uträkning();
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    uträkning();
                }
            
            });
        });

        //LÄGG TILL ALLA TABELLER I ARRAY
        tables.add(kostnader_table);
        tables.add(övrigt_table);
        tables.add(priser_table);
        
        this.pack();
    }//GEN-LAST:event_start_metoder

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (färdig_model.getRowCount() > 0) {
            FärdigställFörsäljningFönster.setLocationRelativeTo(this);
            FärdigställFörsäljningFönster.pack();
            FärdigställFörsäljningFönster.setVisible(true);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void OrderfönsterWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OrderfönsterWindowClosing

    }//GEN-LAST:event_OrderfönsterWindowClosing

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped

    }//GEN-LAST:event_formKeyTyped

    private void InställningarFönsterWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_InställningarFönsterWindowClosing
        spara_inställningar();
    }//GEN-LAST:event_InställningarFönsterWindowClosing

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if (färdig_table.isRowSelected(färdig_table.getSelectedRow())) {
            räkna_totalvärden();
            valutakurs_field_spara_två.remove(färdig_table.getSelectedRow());
            stålkvalitet_field_spara_två.remove(färdig_table.getSelectedRow());
            stålverk_field_spara_två.remove(färdig_table.getSelectedRow());
            säljare_field_spara_två.remove(färdig_table.getSelectedRow());
            artikelnummer_field_spara_två.remove(färdig_table.getSelectedRow());
            bearbetning_field_spara_två.remove(färdig_table.getSelectedRow());
            dimension_field_spara_två.remove(färdig_table.getSelectedRow());
            antal_field_spara_två.remove(färdig_table.getSelectedRow());
            längd_field_spara_två.remove(färdig_table.getSelectedRow());
            längdtolerans_field_spara_två.remove(färdig_table.getSelectedRow());
            euro_per_ton_field_spara_två.remove(färdig_table.getSelectedRow());
            ställkostnad_field_spara_två.remove(färdig_table.getSelectedRow());
            ursprungslängd_field_spara_två.remove(färdig_table.getSelectedRow());
            påslag_field_spara_två.remove(färdig_table.getSelectedRow());
            fraktkostnad_field_spara_två.remove(färdig_table.getSelectedRow());
            fraktkostnad_sicam_field_spara_två.remove(färdig_table.getSelectedRow());
            spill_field_spara_två.remove(färdig_table.getSelectedRow());
            euro_per_ton_list.remove(färdig_table.getSelectedRow());
            valutakurs_list.remove(färdig_table.getSelectedRow());
            if (färdig_model.getRowCount() > 0) {
                färdig_model.removeRow(färdig_table.getSelectedRow());
            }
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Orderfönster.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void lösenord_passwordfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lösenord_passwordfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lösenord_passwordfieldActionPerformed

    private void skapa_offert_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skapa_offert_buttonActionPerformed
        if (färdig_model.getRowCount() > 0) {
            OffertFönster.setVisible(true);
            OffertFönster.setLocationRelativeTo(this);
            OffertFönster.pack();
        }
    }//GEN-LAST:event_skapa_offert_buttonActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        försäljningKnappen(färdig_model);
        kollaOmArtikelÄrSåld();
        Orderfönster.setVisible(false);
        FärdigställFörsäljningFönster.setVisible(false);
        if(välj_offert_combobox.getItemCount() == 1) {
            LaddaOffertFönster.setVisible(false);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void försäljningKnappen(DefaultTableModel model) {
        /* TESTAR DATABASEN */
        if(model == färdig_model) {
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    HashMap<String, String> strings = new HashMap<>();
                    strings.put("Artikelnummer", model.getValueAt(i, 0).toString());
                    strings.put("Kategori", model.getValueAt(i, 1).toString());
                    strings.put("Dimension", model.getValueAt(i, 2).toString());
                    strings.put("Datum", datum_field.getText());
                    strings.put("Tolerans", model.getValueAt(i, 6).toString());
                    strings.put("TotalVikt", model.getValueAt(i, 3).toString());
                    strings.put("Antal",model.getValueAt(i, 4).toString());
                    strings.put("Längd", model.getValueAt(i, 5).toString());
                    strings.put("PrisStyck", model.getValueAt(i, 7).toString());
                    strings.put("PrisLeverans", model.getValueAt(i, 8).toString());
                    strings.put("Valutakurs", valutakurs_list.get(i));
                    strings.put("EuroPerTon", euro_per_ton_list.get(i));
                    functions.läggTillFörsäljning(db, nuvarande_distrikt, kund, model.getValueAt(i, 0).toString(), strings);
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (model == offert_model) {
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    HashMap<String, String> strings = new HashMap<>();
                    strings.put("Artikelnummer", model.getValueAt(i, 1).toString());
                    strings.put("Kategori", model.getValueAt(i, 2).toString());
                    strings.put("Dimension", model.getValueAt(i, 3).toString());
                    strings.put("Datum", datum_field.getText());
                    strings.put("Tolerans", model.getValueAt(i, 7).toString());
                    strings.put("TotalVikt", model.getValueAt(i, 4).toString().replaceAll(",", "."));
                    strings.put("Antal", model.getValueAt(i, 5).toString());
                    strings.put("Längd", model.getValueAt(i, 6).toString());
                    strings.put("PrisStyck", model.getValueAt(i, 8).toString().replaceAll(",", "."));
                    strings.put("PrisLeverans", model.getValueAt(i, 9).toString().replaceAll(",", "."));
                    strings.put("Valutakurs", valutakurs_list.get(i));
                    strings.put("EuroPerTon", euro_per_ton_list.get(i));
                    functions.läggTillFörsäljning(db, nuvarande_distrikt, kund, model.getValueAt(i, 1).toString(), strings);
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (laddatOffert) {
            functions.rensaOffert(db, nuvarande_distrikt, kund, nuvarande_offert);
            try {
                välj_offert_combobox.removeItem(nuvarande_offert + " " + functions.hämtaOffertDatum(db, nuvarande_distrikt, kund, nuvarande_offert));
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (välj_offert_combobox.getItemCount() == 0) {
                info_label.setVisible(false);
                övergång_icon.setVisible(false);
                LaddaOffertFönster.setVisible(false);
                Orderfönster.setVisible(false);
                laddatOffert = false;
            }
            nuvarande_offert = null;
        }
        försäljningsArtikelTillDatabas();
        try {
            reset(alla_artiklar_model);
            functions.hämtaAllaFörsäljningar(db, nuvarande_distrikt, kund, tidigare_model);
            functions.hämtaAllaArtiklar(db, nuvarande_distrikt, kund, alla_artiklar_model);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
         * *****************
         */
        reset(model);
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        FärdigställFörsäljningFönster.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void färdig_tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_färdig_tablePropertyChange

    }//GEN-LAST:event_färdig_tablePropertyChange

    private void skicka_mail_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skicka_mail_buttonActionPerformed
        Epost epost = new Epost();
        String problemet = problemet_field.getText();
        String händelse = meddelande_area.getText();
        String tidpunkt = tidpunkt_field.getText();
        File mainFrame = taSkärmDump(this);
        File orderFrame = taSkärmDump(Orderfönster);
        File offertFrame = taSkärmDump(OffertFönster);
        epost.rapporteraBug(
                problemet, 
                händelse,
                tidpunkt,
                mainFrame, 
                orderFrame, 
                offertFrame, 
                Orderfönster.isVisible(), 
                OffertFönster.isVisible()
        );
        FelRapportFönster.setVisible(false);
    }//GEN-LAST:event_skicka_mail_buttonActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        FelRapportFönster.setLocationRelativeTo(this);
        FelRapportFönster.setLocation(this.getX() + this.getWidth() / 2 - 100, this.getY() + 250);
        FelRapportFönster.pack();
        FelRapportFönster.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void välj_offert_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_välj_offert_comboboxActionPerformed
        
    }//GEN-LAST:event_välj_offert_comboboxActionPerformed

    private void LösenordFönsterWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_LösenordFönsterWindowClosed
        lösenord_krävs_label.setText("Lösenord krävs:");
        glömt_lösenord_label.setVisible(false);
        LösenordFönster.pack();
    }//GEN-LAST:event_LösenordFönsterWindowClosed

    private void OrderfönsterWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OrderfönsterWindowOpened

    }//GEN-LAST:event_OrderfönsterWindowOpened

    private void lägg_till_kund_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lägg_till_kund_button1ActionPerformed

        this.setVisible(true);
        boolean match = false;
        laddatKalkyl = false;

        for (int i = 0; i < välj_kund_combobox.getItemCount(); i++) {
            if (lägg_till_kund_field.getText().matches(välj_kund_combobox.getItemAt(i))) {
                FelmeddelandeFönster.setVisible(true);
                FelmeddelandeFönster.setLocationRelativeTo(this);
                FelmeddelandeFönster.pack();
                match = true;
            }
        }

        if (!match) {
            kund_label.setText("Snabbkalkyl");
            spara_artikelnummer_button.setVisible(false);
            jButton5.setVisible(false);
            kund = kund_label.getText();

            reset(tidigare_model);
            reset(kostnader_model);
            reset(priser_model);
            reset(övrigt_model);
            reset(alla_artiklar_model);

            artikelnummer_list.clear();
            kategorier_list.clear();
            dimensioner_list.clear();
            total_vikt_list.clear();
            antal_list.clear();
            längd_list.clear();
            tolerans_list.clear();
            pris_styck_list.clear();

            totalvikt_total = 0;
            total_antal = 0;
            total_längd = 0;
            pris_kund_inklusive_total = 0;
            total_marginal = 0;

            marginal_styck_field.setText("0" + " kr");
            marginal_total_field.setText("0" + " kr");
            påslag_field.setText("0" + " kr");
            viktstyck_field.setText("0" + " kg");
            totalvikt_field.setText("0" + " kg");
            prisstyck_field.setText("0" + " kr");
            pristotalt_field.setText("0" + " kr");
            täckningsgrad_field.setText("0" + "%");

            counter = 0;

            töm_fält();

            VäljKundFönster.setVisible(false);
            ladda_ljushet();
            this.pack();
        }
    }//GEN-LAST:event_lägg_till_kund_button1ActionPerformed

    private void VäljKundFönsterWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_VäljKundFönsterWindowClosing
        if (!this.isVisible()) {
            System.exit(0);
        }
    }//GEN-LAST:event_VäljKundFönsterWindowClosing

    private void välj_kund_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_välj_kund_buttonActionPerformed
        if (välj_kund_combobox.getSelectedIndex() > 0 && !välj_kund_combobox.getSelectedItem().toString().equals(kund)) {
            this.setVisible(true);
            if (!spara_artikelnummer_button.isVisible()) {
                spara_artikelnummer_button.setVisible(true);
                jButton5.setVisible(true);
            }
            kund_label.setText((String) välj_kund_combobox.getSelectedItem());
            kvantitet_hittils_field.setText("0" + " st");
            vinst_hittils_field.setText("0" + " kr");
            kund = kund_label.getText();
            path = System.getProperty("user.dir") + "//Sparat//Kunder//" + kund + "//Artikelnummer//";
            laddatKalkyl = false;
            ladda_kund_avtal();

            reset(tidigare_model);
            reset(kostnader_model);
            reset(priser_model);
            reset(övrigt_model);
            reset(alla_artiklar_model);

            artikelnummer_list.clear();
            kategorier_list.clear();
            dimensioner_list.clear();
            total_vikt_list.clear();
            antal_list.clear();
            längd_list.clear();
            tolerans_list.clear();
            pris_styck_list.clear();

            totalvikt_total = 0;
            total_antal = 0;
            total_längd = 0;
            pris_kund_inklusive_total = 0;
            total_marginal = 0;

            uträkning();

            counter = 0;
            töm_fält();
            VäljKundFönster.setVisible(false);

            marginal_styck_field.setText("0" + " kr");
            marginal_total_field.setText("0" + " kr");
            påslag_field.setText("0" + " kr");
            viktstyck_field.setText("0" + " kg");
            totalvikt_field.setText("0" + " kg");
            prisstyck_field.setText("0" + " kr");
            pristotalt_field.setText("0" + " kr");
            täckningsgrad_field.setText("0" + "%");
            ladda_offerter();
            kolla_tidigare_försäljning();
            if (harKundKöptTidigare) {
                try {
                    functions.hämtaAllaFörsäljningar(db, nuvarande_distrikt, kund, tidigare_model);
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (jTabbedPane2.getSelectedIndex() == 3) {
                    jTabbedPane2.setSelectedIndex(2);
                }
            }
            skapa_offert_button.setVisible(true);
            ta_bort_rad_button.setVisible(true);

            /* TESTAR DATABASEN */
            try {
                functions.hämtaAllaArtiklar(db, nuvarande_distrikt, kund, alla_artiklar_model);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
            /**
             * *****************
             */
            ladda_ljushet();
            kollaOmArtikelÄrSåld();
            this.pack();
        } else {
            FelmeddelandeFönster.setVisible(true);
            FelmeddelandeFönster.setLocationRelativeTo(this);
            FelmeddelandeFönster.pack();
        }
    }//GEN-LAST:event_välj_kund_buttonActionPerformed

    private void lägg_till_kund_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lägg_till_kund_fieldKeyReleased
        if (!"".equals(lägg_till_kund_field.getText())) {
            lägg_till_kund_button.setEnabled(true);
        } else if ("".equals(lägg_till_kund_field.getText())) {
            lägg_till_kund_button.setEnabled(false);
        }
    }//GEN-LAST:event_lägg_till_kund_fieldKeyReleased

    private void lösenord_passwordfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lösenord_passwordfieldKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            lösenord_button.doClick();
        }
    }//GEN-LAST:event_lösenord_passwordfieldKeyReleased

    private void glömt_lösenord_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_glömt_lösenord_labelMouseReleased
        Epost epost = new Epost();
        epost.skicka_lösenord(lösenordet, säljare_field.getText());
        glömt_lösenord_label.setVisible(false);
        lösenord_krävs_label.setText("En påminnelse har skickats per mail");
        LösenordFönster.pack();
    }//GEN-LAST:event_glömt_lösenord_labelMouseReleased

    private void VäljKundFönsterWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_VäljKundFönsterWindowOpened
        ladda_färger();
        läggTillAllaDistrikt(ansvariga);
    }//GEN-LAST:event_VäljKundFönsterWindowOpened

    private void välj_kund_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_välj_kund_comboboxActionPerformed
        if (välj_kund_combobox.getSelectedIndex() > 0) {
            välj_kund_button.setEnabled(true);
        } else if (välj_kund_combobox.getSelectedIndex() == 0) {
            välj_kund_button.setEnabled(false);
        }
    }//GEN-LAST:event_välj_kund_comboboxActionPerformed

    private void logga_in_användare_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logga_in_användare_buttonActionPerformed
        /* TESTAR DATABASEN */
        try {
            if (!"".equals(existerande_field.getText()) || !"".equals(existerande_passwordfield.getText())) {
                functions.kollaAnvändare(db, existerande_field.getText(), kryptera(existerande_passwordfield.getText()));
                if (functions.getAnv() != null) {
                    if (functions.getAnv().getNamn().equals(existerande_field.getText())
                        && functions.getAnv().getLösenord().equals(kryptera(existerande_passwordfield.getText()))) {
                        AnvändareFönster.setVisible(false);
                        säljare_field.setText(existerande_field.getText());
                        VäljKundFönster.setVisible(true);
                        VäljKundFönster.setLocationRelativeTo(this);
                        VäljKundFönster.setLocation(this.getX() + 650, this.getY() + 220);
                        VäljKundFönster.pack();
                    }
                } else {
                    existerande_field.setText("Fel lösenord eller användarnamn.");
                }
            } else {
                existerande_field.setText("Fälten är tomma.");
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(kom_ihåg_mig_radio.isSelected()) {
            lagraCache();
        } else {
            rensaCache();
        }
        /*********************/
    }//GEN-LAST:event_logga_in_användare_buttonActionPerformed

    private void AnvändareFönsterWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_AnvändareFönsterWindowOpened
        try {
            laddaCache();
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        existerande_passwordfield.setEchoChar((char) 0);
        existerande_passwordfield.setEchoChar('*');
        användarlösenord_passwordfield.setEchoChar((char) 0);
        användarlösenord_passwordfield.setEchoChar('*');
        välj_kund_panel.setVisible(false);
        VäljKundFönster.pack();
        användare_panel1.setVisible(false);
        AnvändareFönster.pack();
    }//GEN-LAST:event_AnvändareFönsterWindowOpened

    private void AnvändareFönsterWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_AnvändareFönsterWindowClosing
        System.exit(0);
    }//GEN-LAST:event_AnvändareFönsterWindowClosing

    private void ladda_progressbarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ladda_progressbarStateChanged
        if (ladda_progressbar.getValue() == ladda_progressbar.getMaximum()) {
            laddar_panel.setVisible(false);
            välj_distrikt_panel.setVisible(true);
            VäljKundFönster.pack();
        }
    }//GEN-LAST:event_ladda_progressbarStateChanged

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange

    }//GEN-LAST:event_formPropertyChange

    private void lägg_till_användare_button2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lägg_till_användare_button2MouseReleased
        Color color = new Color(0, 102, 153);
        lägg_till_användare_button2.setForeground(color);
        existerande_panel.setVisible(false);
        välj_kund_panel.setVisible(false);
        lägg_till_kund_panel.setVisible(false);
        användare_panel1.setVisible(true);
        AnvändareFönster.pack();
    }//GEN-LAST:event_lägg_till_användare_button2MouseReleased

    private void lägg_till_användare_button2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lägg_till_användare_button2MousePressed
        Color color = new Color(0, 51, 102);
        lägg_till_användare_button2.setForeground(color);
    }//GEN-LAST:event_lägg_till_användare_button2MousePressed

    private void jLabel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseReleased
        if (existerande_passwordfield.getEchoChar() == '*') {
            existerande_passwordfield.setEchoChar((char) 0);
        } else {
            existerande_passwordfield.setEchoChar('*');
        }
    }//GEN-LAST:event_jLabel9MouseReleased

    private void jLabel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseReleased
        EPostFönster.setVisible(true);
        EPostFönster.setLocationRelativeTo(null);
        EPostFönster.pack();
    }//GEN-LAST:event_jLabel5MouseReleased

    private void existerande_passwordfieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existerande_passwordfieldMouseReleased
        if (existerande_passwordfield.getText().equals("Lösenord")) {
            existerande_passwordfield.setText("");
        }
    }//GEN-LAST:event_existerande_passwordfieldMouseReleased

    private void EPostFönsterWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_EPostFönsterWindowClosed
        
    }//GEN-LAST:event_EPostFönsterWindowClosed

    private void för_till_orderfönster_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_för_till_orderfönster_buttonActionPerformed
        try {
            if (kostnader_model.getRowCount() <= 0) {
            } else {
                int selected_row = 0;

                for(JTable table : tables) {
                    if(table.isRowSelected(table.getSelectedRow())) {
                        selected_row = table.getSelectedRow();
                    }
                }

                spara_artikelnummer_två(selected_row);

                färdig_model.addRow(new Object[]{
                    //ARTIKELNUMMER
                    övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Artikelnummer")),
                    //KATEGORI
                    övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Kategori")),
                    //DIMENSION
                    övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Dimension")),
                    //TOTAL VIKT
                    övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Vikt")),
                    //ANTAL
                    övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Antal")),
                    //LÄNGD
                    övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Längd")),
                    //TOLERANS
                    övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Tolerans")),
                    //PRIS STYCK
                    priser_model.getValueAt(selected_row, 2),
                    //PRIS LEVERANS
                    priser_model.getValueAt(selected_row, 3)
                });
                
                euro_per_ton_list.add(övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Euro per ton")).toString().replaceAll(",", "."));
                valutakurs_list.add(övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Valutakurs")).toString().replaceAll(",", "."));
                
                räkna_totalvärden();
                
                for (DefaultTableModel tableModel : models) {
                    tableModel.removeRow(selected_row);
                }
            }
        } catch (NumberFormatException e) {
            loggare.log_info(e.getMessage(), "Programmet_för_till_orderfönster", e);
        }
    }//GEN-LAST:event_för_till_orderfönster_buttonActionPerformed

    private void till_orderfönster_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_till_orderfönster_buttonActionPerformed
        kund_färdig_label.setText(kund);
        skapa_offert_button.setVisible(true);
        jButton14.setVisible(true);
        Orderfönster.setLocationRelativeTo(this);
        Orderfönster.setLocation(this.getX() + 250, this.getY() + 200);
        Orderfönster.pack();
        Orderfönster.setVisible(true);
    }//GEN-LAST:event_till_orderfönster_buttonActionPerformed

    private void rensa_tabell_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rensa_tabell_buttonActionPerformed
        if(kostnader_model.getRowCount() > 0) {
            rensa_tabel_dialog.setLocationRelativeTo(null);
            rensa_tabel_dialog.setVisible(true);
            rensa_tabel_dialog.pack();
        }
    }//GEN-LAST:event_rensa_tabell_buttonActionPerformed

    private void ta_bort_rad_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_bort_rad_buttonActionPerformed
        if (kostnader_table.isRowSelected(kostnader_table.getSelectedRow()) || priser_table.isRowSelected(priser_table.getSelectedRow())
            || övrigt_table.isRowSelected(övrigt_table.getSelectedRow())) {
            int remove_row = 0;
            if (kostnader_table.isShowing()) {
                remove_row = kostnader_table.getSelectedRow();
            }
            if (priser_table.isShowing()) {
                remove_row = priser_table.getSelectedRow();
            }
            if (övrigt_table.isShowing()) {
                remove_row = övrigt_table.getSelectedRow();
            }
            kostnader_model.removeRow(remove_row);
            priser_model.removeRow(remove_row);
            övrigt_model.removeRow(remove_row);
            rensa_artikelnummer(remove_row);
        }
    }//GEN-LAST:event_ta_bort_rad_buttonActionPerformed

    private void alla_artiklar_tableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_alla_artiklar_tableMouseReleased
        if (evt.getClickCount() == 2) {
            if (alla_artiklar_table.isRowSelected(alla_artiklar_table.getSelectedRow())) {
                /* TESTAR DATABASEN */
                hämta_ett_artikelnummer(alla_artiklar_table.getValueAt(alla_artiklar_table.getSelectedRow(), 0).toString());
                /**
                * *****************
                */
                godstjocklek = 0;
                uträkning();
            }
        }
    }//GEN-LAST:event_alla_artiklar_tableMouseReleased

    private void förändring_i_kr_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_förändring_i_kr_panelMouseReleased
        sätt_färg(förändring_i_kr_label, förändring_i_kr_label, förändring_i_kr_panel);
    }//GEN-LAST:event_förändring_i_kr_panelMouseReleased

    private void förändring_i_procent_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_förändring_i_procent_labelMouseReleased
        sätt_färg(förändring_i_procent_label, förändring_i_procent_label, förändring_i_procent_panel);
    }//GEN-LAST:event_förändring_i_procent_labelMouseReleased

    private void tidigare_tableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tidigare_tableMouseReleased
        if (evt.getClickCount() == 2) {
            if (tidigare_table.isRowSelected(tidigare_table.getSelectedRow())) {
                hämta_ett_artikelnummer(tidigare_table.getValueAt(tidigare_table.getSelectedRow(), 0).toString());
                godstjocklek = 0;
                uträkning();
            }
        }
    }//GEN-LAST:event_tidigare_tableMouseReleased

    private void totaltid_text1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totaltid_text1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totaltid_text1ActionPerformed

    private void kostnad_per_bit_text1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kostnad_per_bit_text1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kostnad_per_bit_text1ActionPerformed

    private void cykeltid_text1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cykeltid_text1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cykeltid_text1ActionPerformed

    private void pris_totalt_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pris_totalt_labelMouseReleased
        sätt_färg(pris_totalt_label, pris_totalt_label, pris_totalt_panel);
    }//GEN-LAST:event_pris_totalt_labelMouseReleased

    private void pris_styck_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pris_styck_labelMouseReleased
        sätt_färg(pris_styck_label, pris_styck_label, pris_styck_panel);
    }//GEN-LAST:event_pris_styck_labelMouseReleased

    private void vikt_styck_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vikt_styck_panelMouseReleased
        sätt_färg(vikt_styck_label, vikt_styck_label, vikt_styck_panel);
    }//GEN-LAST:event_vikt_styck_panelMouseReleased

    private void totalvikt_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalvikt_panelMouseReleased
        sätt_färg(totalvikt_label, totalvikt_label, totalvikt_panel);
    }//GEN-LAST:event_totalvikt_panelMouseReleased

    private void täckningsgrad_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_täckningsgrad_panelMouseReleased
        sätt_färg(täckningsgrad_label, täckningsgrad_label, täckningsgrad_panel);
    }//GEN-LAST:event_täckningsgrad_panelMouseReleased

    private void marginal_styck_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_marginal_styck_labelMouseReleased
        sätt_färg(marginal_styck_label, marginal_styck_label, marginal_styck_panel);
    }//GEN-LAST:event_marginal_styck_labelMouseReleased

    private void marginal_total_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_marginal_total_labelMouseReleased
        sätt_färg(marginal_total_label, marginal_total_label, marginal_total_panel);
    }//GEN-LAST:event_marginal_total_labelMouseReleased

    private void belopp_hittils_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_belopp_hittils_panelMouseReleased
        sätt_färg(belopp_hittils_label, belopp_hittils_label, belopp_hittils_panel);
    }//GEN-LAST:event_belopp_hittils_panelMouseReleased

    private void kvantitet_hittils_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kvantitet_hittils_labelMouseReleased
        sätt_färg(kvantitet_hittils_label, kvantitet_hittils_label, kvantitet_hittils_panel);
    }//GEN-LAST:event_kvantitet_hittils_labelMouseReleased

    private void ton_år_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ton_år_panelMouseReleased
        sätt_färg(ton_per_år_label, ton_per_år_label, ton_år_panel);
    }//GEN-LAST:event_ton_år_panelMouseReleased

    private void omsättning_per_år_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_omsättning_per_år_labelMouseReleased
        sätt_färg(omsättning_per_år_label, omsättning_per_år_label, omsättning_år_panel);
    }//GEN-LAST:event_omsättning_per_år_labelMouseReleased

    private void marginal_per_år_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_marginal_per_år_labelMouseReleased
        sätt_färg(marginal_per_år_label, marginal_per_år_label, marginal_år_panel);
    }//GEN-LAST:event_marginal_per_år_labelMouseReleased

    private void kvantitet_per_år_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kvantitet_per_år_labelMouseReleased
        sätt_färg(kvantitet_per_år_label, kvantitet_per_år_label, kvantitet_år_panel);
    }//GEN-LAST:event_kvantitet_per_år_labelMouseReleased

    private void kvantitet_år_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kvantitet_år_fieldKeyReleased
        if(!kvantitet_år_field.getText().contains("[\\d]")) {
            kvantitet_år_field.setText(kvantitet_år_field.getText().replaceAll("[^\\d]", ""));
        }
    }//GEN-LAST:event_kvantitet_år_fieldKeyReleased

    private void panel_panelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_panel_panelPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_panelPropertyChange

    private void stålverk_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stålverk_comboboxActionPerformed
        stålverk_field.setText(stålverk_combobox.getSelectedItem().toString());
        uträkning();
    }//GEN-LAST:event_stålverk_comboboxActionPerformed

    private void stålkvalitet_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stålkvalitet_comboboxActionPerformed
        if(!stålkvalitet_combobox.getSelectedItem().toString().equals("Alternativ stålkvalitet")) {
            stålkvalitet_field.setText(stålkvalitet_combobox.getSelectedItem().toString());
        }
        if (kilo_per_meter_panel.isVisible() && !stålkvalitet_combobox.getSelectedItem().toString().equals("Alternativ stålkvalitet")) {
            kilo_per_meter_panel.setVisible(false);
            kilo_per_meter_field.setVisible(false);
            kilo_per_meter_panel2.setVisible(false);
        } else if (stålkvalitet_combobox.getSelectedItem().toString().equals("Alternativ stålkvalitet")) {
            stålkvalitet_field.setText("");
            kilo_per_meter_panel.setVisible(true);
            kilo_per_meter_field.setVisible(true);
            kilo_per_meter_panel2.setVisible(true);
        }
        uträkning();
        ändra_stålverk();
    }//GEN-LAST:event_stålkvalitet_comboboxActionPerformed

    private void valutakurs_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valutakurs_comboboxActionPerformed
        InputStream is;
        String line;
        valuta = 0;
        if (valutakurs_combobox.getSelectedIndex() != 1) {
        } else {
            try {
                this.url = new URL("http://eurokurs.se/");
                URLConnection con = url.openConnection();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                while ((line = br.readLine()) != null) {
                    if (line.contains("inputRateSEK")) {
                        String clean = line.replaceAll("[^ .0-9]", "");
                        valuta = Double.parseDouble(clean);
                    }
                }
                valutakurs_field.setText(Double.toString(valuta));
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_valutakurs", ex);
            } finally {

            }
        }
        uträkning();
    }//GEN-LAST:event_valutakurs_comboboxActionPerformed

    private boolean isEmpty () {
        for (JTextField field : fields) {
            return field.getText().equals("");
        }
        return true;
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
        if (material_kvalitet_combobox.getSelectedIndex() < 1 && !Plockorder_meny.isSelected()) {
            FelmeddelandeFönster.setLocationRelativeTo(this);
            FelmeddelandeFönster.pack();
            FelmeddelandeFönster.setVisible(true);
        } else {
            uträkning();
            if (isEmpty()) {
                FelmeddelandeFönster.setLocationRelativeTo(this);
                FelmeddelandeFönster.pack();
                FelmeddelandeFönster.setVisible(true);
            } else {
                spara_artikelnummer();
                //SKAPA KOSTNADS TABELLEN
                kostnader_model.addRow(
                    new Object[]{
                        format.format(total_produktionskostnad_styck),
                        format.format(total_produktionskostnad),
                        format.format(kostnad_per_styck_exklusive),
                        format.format(total_kostnad_leverans),
                        format.format(fraktkostnad_styck),
                        format.format(fraktkostnad_totalt),
                        format.format(kostnad_per_styck_inklusive),
                        format.format(kostnad_leverans_inklusive)
                    }
                );

                //SKAPA PRIS TABELLEN
                priser_model.addRow(
                    new Object[]{
                        format.format(pris_kund_kilo_exklusive),
                        format.format(pris_kund_kilo_inklusive),
                        format.format(pris_kund_inklusive),
                        format.format(pris_kund_leverans)
                    }
                );

                //SKAPA ÖVRIGT TABELLEN
                övrigt_model.addRow(
                    new Object[]{   
                        artikelnummer_field.getText(),
                        stålkvalitet_field.getText(),
                        dimension_field.getText(),
                        antal_field.getText(),
                        längd_field.getText(),
                        format.format(vikt_per_styck),
                        euro_per_ton_field.getText(),
                        valutakurs_field.getText(),
                        längdtolerans_field.getText()
                    }
                );
            }
        }

        //LÄGG TILL I LISTORNA
        if (!isEmpty()) {
            artikelnummer_list.add(artikelnummer_field.getText());
            kategorier_list.add(stålkvalitet_field.getText());
            dimensioner_list.add(dimension_field.getText());
            total_vikt_list.add(totalvikt_field.getText());
            antal_list.add(antal_field.getText());
            längd_list.add(längd_field.getText());
            tolerans_list.add(längdtolerans_field.getText());
            pris_styck_list.add(format.format(pris_kund_inklusive));
        }
        } catch (Exception ex) {
            loggare.log_info(ex.getMessage(), "Uträkning", ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void spara_artikelnummer_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spara_artikelnummer_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spara_artikelnummer_buttonActionPerformed

    private void spara_artikelnummer_buttonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spara_artikelnummer_buttonMouseReleased
        /* TESTAR DATABASEN */
        try {
            Map<String, String> strings = new HashMap<>();
            strings.put("Valutakurs", valutakurs_field.getText());
            strings.put("Antal", antal_field.getText());
            strings.put("Längd", längd_field.getText());
            strings.put("EuroPerTon", euro_per_ton_field.getText());
            strings.put("Ställkostnad", ställkostnad_field.getText());
            strings.put("Ursprungslängd", ursprungslängd_field.getText());
            strings.put("Påslag", påslag_field.getText().replaceAll("[^\\d]", ""));
            strings.put("Fraktkostnad", fraktkostnad_field.getText());
            strings.put("FraktkostnadSicam", fraktkostnad_sicam_field.getText());
            strings.put("Spill", spill_field.getText());
            strings.put("Bearbetning", bearbetning_field.getText());
            strings.put("Bandtyp", Integer.toString(bandtyp_combobox.getSelectedIndex()));
            strings.put("Material", Integer.toString(material_kvalitet_combobox.getSelectedIndex()));
            strings.put("Gerkap", Integer.toString(gerkap_combobox.getSelectedIndex()));
            strings.put("Säljare", säljare_field.getText());
            strings.put("Datum", datum_field.getText());
            strings.put("Stålkvalitet", stålkvalitet_combobox.getSelectedItem().toString());
            strings.put("Stålverk", stålverk_combobox.getSelectedItem().toString());
            strings.put("Längdtolerans", längdtolerans_field.getText());
            strings.put("Artikelnummer", artikelnummer_field.getText());
            strings.put("Dimension", dimension_field.getText());

            functions.läggTillArtikel(db, nuvarande_distrikt, kund, artikelnummer_field.getText(), strings);
            reset(alla_artiklar_model);
            functions.hämtaAllaArtiklar(db, nuvarande_distrikt, kund, alla_artiklar_model);
        } catch (ExecutionException | InterruptedException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
        * *****************
        */
    }//GEN-LAST:event_spara_artikelnummer_buttonMouseReleased

    private void gerkap_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerkap_comboboxActionPerformed
        uträkning();
    }//GEN-LAST:event_gerkap_comboboxActionPerformed

    private void påslag_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_påslag_fieldKeyReleased
        
    }//GEN-LAST:event_påslag_fieldKeyReleased

    private void påslag_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_påslag_fieldKeyPressed
        
    }//GEN-LAST:event_påslag_fieldKeyPressed

    private void påslag_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_påslag_fieldFocusLost
        set0(påslag_field);
    }//GEN-LAST:event_påslag_fieldFocusLost

    private void påslag_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_påslag_fieldFocusGained
        reset(påslag_field);
    }//GEN-LAST:event_påslag_fieldFocusGained

    private void bandtyp_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bandtyp_comboboxActionPerformed
        
    }//GEN-LAST:event_bandtyp_comboboxActionPerformed

    private void material_kvalitet_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_material_kvalitet_comboboxActionPerformed
        uträkning();
    }//GEN-LAST:event_material_kvalitet_comboboxActionPerformed

    private void bearbetning_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bearbetning_panelMouseReleased
        sätt_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel);
        sätt_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel2);
    }//GEN-LAST:event_bearbetning_panelMouseReleased

    private void bearbetning_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bearbetning_labelMouseReleased
        sätt_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel);
        sätt_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel2);
    }//GEN-LAST:event_bearbetning_labelMouseReleased

    private void bearbetning_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bearbetning_fieldKeyReleased
    
    }//GEN-LAST:event_bearbetning_fieldKeyReleased

    private void bearbetning_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bearbetning_fieldKeyPressed
        
    }//GEN-LAST:event_bearbetning_fieldKeyPressed

    private void bearbetning_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bearbetning_fieldFocusLost
        set0(bearbetning_field);
    }//GEN-LAST:event_bearbetning_fieldFocusLost

    private void bearbetning_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bearbetning_fieldFocusGained
        reset(bearbetning_field);
        
    }//GEN-LAST:event_bearbetning_fieldFocusGained

    private void bearbetning_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bearbetning_panel2MouseReleased
        sätt_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel);
        sätt_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel2);
    }//GEN-LAST:event_bearbetning_panel2MouseReleased

    private void bearbetning_kr_kg_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bearbetning_kr_kg_labelMouseReleased
        sätt_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel);
        sätt_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel2);
    }//GEN-LAST:event_bearbetning_kr_kg_labelMouseReleased

    private void valutakurs_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valutakurs_panel2MouseReleased
        sätt_färg(valutakurs_kr_label, valutakurs_kr_label, valutakurs_panel2);
    }//GEN-LAST:event_valutakurs_panel2MouseReleased

    private void spill_procent_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spill_procent_panel2MouseReleased
        sätt_färg(spill_label, jLabel44, spill_panel);
        sätt_färg(spill_label, jLabel44, spill_procent_panel2);
    }//GEN-LAST:event_spill_procent_panel2MouseReleased

    private void jLabel44MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseReleased
        sätt_färg(spill_label, jLabel44, spill_panel);
        sätt_färg(spill_label, jLabel44, spill_procent_panel2);
    }//GEN-LAST:event_jLabel44MouseReleased

    private void fraktkostnad_sicam_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraktkostnad_sicam_panel2MouseReleased
        sätt_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel);
        sätt_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel2);
    }//GEN-LAST:event_fraktkostnad_sicam_panel2MouseReleased

    private void fraktkostnad_sicam_kr_kg_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraktkostnad_sicam_kr_kg_labelMouseReleased
        sätt_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel);
        sätt_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel2);
    }//GEN-LAST:event_fraktkostnad_sicam_kr_kg_labelMouseReleased

    private void fraktkostnad_per_kilo_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraktkostnad_per_kilo_panel2MouseReleased
        sätt_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel);
        sätt_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel2);
    }//GEN-LAST:event_fraktkostnad_per_kilo_panel2MouseReleased

    private void fraktkostnad_kr_kg_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraktkostnad_kr_kg_labelMouseReleased
        sätt_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel);
        sätt_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel2);
    }//GEN-LAST:event_fraktkostnad_kr_kg_labelMouseReleased

    private void påslag_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_påslag_panel2MouseReleased
        sätt_färg(påslag_label, påslag_procent_label, påslag_panel);
        sätt_färg(påslag_label, påslag_procent_label, påslag_panel2);
    }//GEN-LAST:event_påslag_panel2MouseReleased

    private void påslag_procent_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_påslag_procent_labelMouseReleased
        sätt_färg(påslag_label, påslag_procent_label, påslag_panel);
        sätt_färg(påslag_label, påslag_procent_label, påslag_panel2);
    }//GEN-LAST:event_påslag_procent_labelMouseReleased

    private void ursprungslängd_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ursprungslängd_panel2MouseReleased
        sätt_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel);
        sätt_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel2);
    }//GEN-LAST:event_ursprungslängd_panel2MouseReleased

    private void ursprungslängd_mm_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ursprungslängd_mm_labelMouseReleased
        sätt_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel);
        sätt_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel2);
    }//GEN-LAST:event_ursprungslängd_mm_labelMouseReleased

    private void ställkostnad_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ställkostnad_panel2MouseReleased
        sätt_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel);
        sätt_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel2);
    }//GEN-LAST:event_ställkostnad_panel2MouseReleased

    private void ställkostnad_kr_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ställkostnad_kr_labelMouseReleased
        sätt_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel);
        sätt_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel2);
    }//GEN-LAST:event_ställkostnad_kr_labelMouseReleased

    private void euro_per_ton_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_euro_per_ton_panel2MouseReleased
        sätt_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel);
        sätt_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel2);
    }//GEN-LAST:event_euro_per_ton_panel2MouseReleased

    private void euro_per_ton_euro_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_euro_per_ton_euro_labelMouseReleased
        sätt_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel);
        sätt_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel2);
    }//GEN-LAST:event_euro_per_ton_euro_labelMouseReleased

    private void längdtolerans_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_längdtolerans_panel2MouseReleased
        sätt_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel);
        sätt_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel2);
    }//GEN-LAST:event_längdtolerans_panel2MouseReleased

    private void längdtolerans_mm_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_längdtolerans_mm_labelMouseReleased
        sätt_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel);
        sätt_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel2);
    }//GEN-LAST:event_längdtolerans_mm_labelMouseReleased

    private void längd_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_längd_panel2MouseReleased
        sätt_färg(längd_label, längd_mm_label, längd_panel);
        sätt_färg(längd_label, längd_mm_label, längd_panel2);
    }//GEN-LAST:event_längd_panel2MouseReleased

    private void längd_mm_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_längd_mm_labelMouseReleased
        sätt_färg(längd_label, längd_mm_label, längd_panel);
        sätt_färg(längd_label, längd_mm_label, längd_panel2);
    }//GEN-LAST:event_längd_mm_labelMouseReleased

    private void antal_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_antal_panel2MouseReleased
        sätt_färg(antal_label, antal_styck_label, antal_panel);
        sätt_färg(antal_label, antal_styck_label, antal_panel2);
    }//GEN-LAST:event_antal_panel2MouseReleased

    private void antal_styck_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_antal_styck_labelMouseReleased
        sätt_färg(antal_label, antal_styck_label, antal_panel);
        sätt_färg(antal_label, antal_styck_label, antal_panel2);
    }//GEN-LAST:event_antal_styck_labelMouseReleased

    private void dimension_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dimension_panel2MouseReleased
        sätt_färg(dimension_label, dimension_mm_label, dimension_panel);
        sätt_färg(dimension_label, dimension_mm_label, dimension_panel2);
    }//GEN-LAST:event_dimension_panel2MouseReleased

    private void dimension_mm_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dimension_mm_labelMouseReleased
        sätt_färg(dimension_label, dimension_mm_label, dimension_panel);
        sätt_färg(dimension_label, dimension_mm_label, dimension_panel2);
    }//GEN-LAST:event_dimension_mm_labelMouseReleased

    private void artikelnummer_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_artikelnummer_fieldKeyReleased

    }//GEN-LAST:event_artikelnummer_fieldKeyReleased

    private void artikelnummer_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_artikelnummer_fieldFocusLost
        set0(artikelnummer_field);
    }//GEN-LAST:event_artikelnummer_fieldFocusLost

    private void artikelnummer_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_artikelnummer_fieldFocusGained
        reset(artikelnummer_field);
        
    }//GEN-LAST:event_artikelnummer_fieldFocusGained

    private void dimension_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dimension_fieldKeyReleased
        euroPerTonSjälv = false;
        
    }//GEN-LAST:event_dimension_fieldKeyReleased

    private void dimension_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dimension_fieldKeyPressed
        
    }//GEN-LAST:event_dimension_fieldKeyPressed

    private void dimension_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dimension_fieldFocusLost
        set0(dimension_field);
    }//GEN-LAST:event_dimension_fieldFocusLost

    private void dimension_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dimension_fieldFocusGained
        reset(dimension_field);
        
    }//GEN-LAST:event_dimension_fieldFocusGained

    private void antal_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_antal_fieldKeyReleased
        
    }//GEN-LAST:event_antal_fieldKeyReleased

    private void antal_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_antal_fieldKeyPressed
        
    }//GEN-LAST:event_antal_fieldKeyPressed

    private void antal_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_antal_fieldFocusLost
        set0(antal_field);
    }//GEN-LAST:event_antal_fieldFocusLost

    private void antal_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_antal_fieldFocusGained
        reset(antal_field);
        
    }//GEN-LAST:event_antal_fieldFocusGained

    private void längd_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_längd_fieldKeyReleased
        
    }//GEN-LAST:event_längd_fieldKeyReleased

    private void längd_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_längd_fieldActionPerformed
        
    }//GEN-LAST:event_längd_fieldActionPerformed

    private void längd_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_längd_fieldFocusGained
        reset(längd_field);
        
    }//GEN-LAST:event_längd_fieldFocusGained

    private void längdtolerans_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_längdtolerans_fieldKeyReleased
        
    }//GEN-LAST:event_längdtolerans_fieldKeyReleased

    private void längdtolerans_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_längdtolerans_fieldKeyPressed
        
    }//GEN-LAST:event_längdtolerans_fieldKeyPressed

    private void längdtolerans_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_längdtolerans_fieldFocusLost
        set0(längdtolerans_field);
    }//GEN-LAST:event_längdtolerans_fieldFocusLost

    private void längdtolerans_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_längdtolerans_fieldFocusGained
        reset(längdtolerans_field);
        
    }//GEN-LAST:event_längdtolerans_fieldFocusGained

    private void euro_per_ton_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_euro_per_ton_fieldKeyTyped
        
    }//GEN-LAST:event_euro_per_ton_fieldKeyTyped

    private void euro_per_ton_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_euro_per_ton_fieldKeyReleased
        
    }//GEN-LAST:event_euro_per_ton_fieldKeyReleased

    private void euro_per_ton_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_euro_per_ton_fieldKeyPressed
        
    }//GEN-LAST:event_euro_per_ton_fieldKeyPressed

    private void euro_per_ton_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_euro_per_ton_fieldFocusLost
        set0(euro_per_ton_field);
    }//GEN-LAST:event_euro_per_ton_fieldFocusLost

    private void euro_per_ton_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_euro_per_ton_fieldFocusGained
        euroPerTonSjälv = true;
        reset(euro_per_ton_field);
        
    }//GEN-LAST:event_euro_per_ton_fieldFocusGained

    private void ställkostnad_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ställkostnad_fieldKeyReleased
        
    }//GEN-LAST:event_ställkostnad_fieldKeyReleased

    private void ställkostnad_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ställkostnad_fieldKeyPressed
        
    }//GEN-LAST:event_ställkostnad_fieldKeyPressed

    private void ställkostnad_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ställkostnad_fieldFocusLost
        set0(ställkostnad_field);
    }//GEN-LAST:event_ställkostnad_fieldFocusLost

    private void ställkostnad_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ställkostnad_fieldFocusGained
        reset(ställkostnad_field);
        
    }//GEN-LAST:event_ställkostnad_fieldFocusGained

    private void ursprungslängd_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ursprungslängd_fieldKeyReleased
        
    }//GEN-LAST:event_ursprungslängd_fieldKeyReleased

    private void ursprungslängd_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ursprungslängd_fieldKeyPressed
        
    }//GEN-LAST:event_ursprungslängd_fieldKeyPressed

    private void ursprungslängd_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ursprungslängd_fieldFocusLost
        set0(ursprungslängd_field);
    }//GEN-LAST:event_ursprungslängd_fieldFocusLost

    private void ursprungslängd_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ursprungslängd_fieldFocusGained
        reset(ursprungslängd_field);
        
    }//GEN-LAST:event_ursprungslängd_fieldFocusGained

    private void fraktkostnad_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fraktkostnad_fieldKeyReleased
        
    }//GEN-LAST:event_fraktkostnad_fieldKeyReleased

    private void fraktkostnad_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fraktkostnad_fieldKeyPressed
        
    }//GEN-LAST:event_fraktkostnad_fieldKeyPressed

    private void fraktkostnad_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fraktkostnad_fieldFocusLost
        set0(fraktkostnad_field);
    }//GEN-LAST:event_fraktkostnad_fieldFocusLost

    private void fraktkostnad_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fraktkostnad_fieldFocusGained
        reset(fraktkostnad_field);
        
    }//GEN-LAST:event_fraktkostnad_fieldFocusGained

    private void fraktkostnad_sicam_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fraktkostnad_sicam_fieldKeyReleased
        
    }//GEN-LAST:event_fraktkostnad_sicam_fieldKeyReleased

    private void fraktkostnad_sicam_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fraktkostnad_sicam_fieldKeyPressed
        
    }//GEN-LAST:event_fraktkostnad_sicam_fieldKeyPressed

    private void fraktkostnad_sicam_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fraktkostnad_sicam_fieldFocusLost
        set0(fraktkostnad_sicam_field);
    }//GEN-LAST:event_fraktkostnad_sicam_fieldFocusLost

    private void fraktkostnad_sicam_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fraktkostnad_sicam_fieldFocusGained
        reset(fraktkostnad_sicam_field);
        
    }//GEN-LAST:event_fraktkostnad_sicam_fieldFocusGained

    private void spill_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spill_fieldKeyReleased
        
    }//GEN-LAST:event_spill_fieldKeyReleased

    private void spill_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spill_fieldKeyPressed
        
    }//GEN-LAST:event_spill_fieldKeyPressed

    private void spill_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spill_fieldFocusLost
        set0(spill_field);
    }//GEN-LAST:event_spill_fieldFocusLost

    private void spill_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spill_fieldFocusGained
        reset(spill_field);
        
    }//GEN-LAST:event_spill_fieldFocusGained

    private void valutakurs_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_valutakurs_fieldKeyReleased
        
    }//GEN-LAST:event_valutakurs_fieldKeyReleased

    private void valutakurs_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_valutakurs_fieldFocusLost
        set0(valutakurs_field);
    }//GEN-LAST:event_valutakurs_fieldFocusLost

    private void valutakurs_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_valutakurs_fieldFocusGained
        reset(valutakurs_field);
        
    }//GEN-LAST:event_valutakurs_fieldFocusGained

    private void stålkvalitet_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stålkvalitet_fieldFocusLost
        if (!stålkvalitet_combobox.getSelectedItem().toString().equals("Alternativ stålkvalitet")) {
            stålkvalitet_field.setText(stålkvalitet_combobox.getSelectedItem().toString());
        }
    }//GEN-LAST:event_stålkvalitet_fieldFocusLost

    private void stålkvalitet_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stålkvalitet_fieldFocusGained
        reset(stålkvalitet_field);
        
    }//GEN-LAST:event_stålkvalitet_fieldFocusGained

    private void stålverk_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stålverk_fieldFocusLost
        stålverk_field.setText(stålverk_combobox.getSelectedItem().toString());
    }//GEN-LAST:event_stålverk_fieldFocusLost

    private void stålverk_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stålverk_fieldFocusGained
        reset(stålverk_field);
        
    }//GEN-LAST:event_stålverk_fieldFocusGained

    private void spill_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spill_panelMouseReleased
        sätt_färg(spill_label, jLabel44, spill_panel);
        sätt_färg(spill_label, jLabel44, spill_procent_panel2);
    }//GEN-LAST:event_spill_panelMouseReleased

    private void spill_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spill_labelMouseReleased
        sätt_färg(spill_label, jLabel44, spill_panel);
        sätt_färg(spill_label, jLabel44, spill_procent_panel2);
    }//GEN-LAST:event_spill_labelMouseReleased

    private void datum_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datum_panelMouseReleased
        sätt_färg(datum_label, datum_label, datum_panel);
    }//GEN-LAST:event_datum_panelMouseReleased

    private void datum_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datum_labelMouseReleased
        sätt_färg(datum_label, datum_label, datum_panel);
    }//GEN-LAST:event_datum_labelMouseReleased

    private void säljare_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_säljare_panelMouseReleased
        sätt_färg(säljare_label, säljare_label, säljare_panel);
    }//GEN-LAST:event_säljare_panelMouseReleased

    private void säljare_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_säljare_labelMouseReleased
        sätt_färg(säljare_label, säljare_label, säljare_panel);
    }//GEN-LAST:event_säljare_labelMouseReleased

    private void ursprungslängd_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ursprungslängd_panelMouseReleased
        sätt_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel);
        sätt_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel2);
    }//GEN-LAST:event_ursprungslängd_panelMouseReleased

    private void ursprungslängd_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ursprungslängd_labelMouseReleased
        sätt_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel);
        sätt_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel2);
    }//GEN-LAST:event_ursprungslängd_labelMouseReleased

    private void dimension_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dimension_panelMouseReleased
        sätt_färg(dimension_label, dimension_mm_label, dimension_panel);
        sätt_färg(dimension_label, dimension_mm_label, dimension_panel2);
    }//GEN-LAST:event_dimension_panelMouseReleased

    private void dimension_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dimension_labelMouseReleased
        sätt_färg(dimension_label, dimension_mm_label, dimension_panel);
        sätt_färg(dimension_label, dimension_mm_label, dimension_panel2);
    }//GEN-LAST:event_dimension_labelMouseReleased

    private void euro_per_ton_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_euro_per_ton_panelMouseReleased
        sätt_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel);
        sätt_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel2);
    }//GEN-LAST:event_euro_per_ton_panelMouseReleased

    private void euro_per_ton_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_euro_per_ton_labelMouseReleased
        sätt_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel);
        sätt_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel2);
    }//GEN-LAST:event_euro_per_ton_labelMouseReleased

    private void fraktkostnad_sicam_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraktkostnad_sicam_panelMouseReleased
        sätt_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel);
        sätt_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel2);
    }//GEN-LAST:event_fraktkostnad_sicam_panelMouseReleased

    private void fraktkostnad_sicam_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraktkostnad_sicam_labelMouseReleased
        sätt_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel);
        sätt_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel2);
    }//GEN-LAST:event_fraktkostnad_sicam_labelMouseReleased

    private void fraktkostnad_per_kilo_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraktkostnad_per_kilo_panelMouseReleased
        sätt_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel);
        sätt_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel2);
    }//GEN-LAST:event_fraktkostnad_per_kilo_panelMouseReleased

    private void fraktkostnad_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraktkostnad_labelMouseReleased
        sätt_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel);
        sätt_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel2);
    }//GEN-LAST:event_fraktkostnad_labelMouseReleased

    private void längdtolerans_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_längdtolerans_panelMouseReleased
        sätt_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel);
        sätt_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel2);
    }//GEN-LAST:event_längdtolerans_panelMouseReleased

    private void längdtolerans_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_längdtolerans_labelMouseReleased
        sätt_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel);
        sätt_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel2);
    }//GEN-LAST:event_längdtolerans_labelMouseReleased

    private void ställkostnad_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ställkostnad_panelMouseReleased
        sätt_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel);
        sätt_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel2);
    }//GEN-LAST:event_ställkostnad_panelMouseReleased

    private void ställkostnad_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ställkostnad_labelMouseReleased
        sätt_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel);
        sätt_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel2);
    }//GEN-LAST:event_ställkostnad_labelMouseReleased

    private void längd_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_längd_panelMouseReleased
        sätt_färg(längd_label, längd_mm_label, längd_panel);
        sätt_färg(längd_label, längd_mm_label, längd_panel2);
    }//GEN-LAST:event_längd_panelMouseReleased

    private void längd_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_längd_labelMouseReleased
        sätt_färg(längd_label, längd_mm_label, längd_panel);
        sätt_färg(längd_label, längd_mm_label, längd_panel2);
    }//GEN-LAST:event_längd_labelMouseReleased

    private void artikel_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_artikel_panelMouseReleased
        sätt_färg(artikelnummer_label, artikelnummer_label, artikel_panel);
    }//GEN-LAST:event_artikel_panelMouseReleased

    private void artikelnummer_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_artikelnummer_labelMouseReleased
        sätt_färg(artikelnummer_label, artikelnummer_label, artikel_panel);
    }//GEN-LAST:event_artikelnummer_labelMouseReleased

    private void påslag_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_påslag_panelMouseReleased
        sätt_färg(påslag_label, påslag_procent_label, påslag_panel);
        sätt_färg(påslag_label, påslag_procent_label, påslag_panel2);
    }//GEN-LAST:event_påslag_panelMouseReleased

    private void påslag_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_påslag_labelMouseReleased
        sätt_färg(påslag_label, påslag_procent_label, påslag_panel);
        sätt_färg(påslag_label, påslag_procent_label, påslag_panel2);
    }//GEN-LAST:event_påslag_labelMouseReleased

    private void info_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_info_labelMouseReleased
        välj_offert_combobox.setSelectedIndex(0);
        LaddaOffertFönster.setVisible(true);
        LaddaOffertFönster.setLocationRelativeTo(this);
        LaddaOffertFönster.pack();
    }//GEN-LAST:event_info_labelMouseReleased

    private void info_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_info_labelMouseEntered
        info_label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_info_labelMouseEntered

    private void EPostFönsterComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_EPostFönsterComponentHidden
        Color color = new Color(0,102,153);
        jLabel12.setForeground(color);
        jLabel12.setText("Skriv in ditt användarnamn & e-post");
    }//GEN-LAST:event_EPostFönsterComponentHidden

    private void existerande_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_existerande_fieldFocusGained
        if (existerande_field.getText().equals("Användarnamn")) {
            existerande_field.setText("");
        }
    }//GEN-LAST:event_existerande_fieldFocusGained

    private void existerande_passwordfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_existerande_passwordfieldFocusGained
        if (existerande_passwordfield.getText().equals("Lösenord")) {
            existerande_passwordfield.setText("");
        }
    }//GEN-LAST:event_existerande_passwordfieldFocusGained

    private void existerande_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_existerande_fieldFocusLost
        if (existerande_field.getText().equals("")) {
            existerande_field.setText("Användarnamn");
        }
    }//GEN-LAST:event_existerande_fieldFocusLost

    private void existerande_passwordfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_existerande_passwordfieldFocusLost
        if (existerande_passwordfield.getText().equals("")) {
            existerande_passwordfield.setText("Lösenord");
        }
    }//GEN-LAST:event_existerande_passwordfieldFocusLost

    private void existerande_passwordfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_existerande_passwordfieldKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            logga_in_användare_button.doClick();
        }
    }//GEN-LAST:event_existerande_passwordfieldKeyReleased

    private void jLabel10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseReleased
        if (användarlösenord_passwordfield.getEchoChar() == '*') {
            användarlösenord_passwordfield.setEchoChar((char) 0);
        } else {
            användarlösenord_passwordfield.setEchoChar('*');
        }
    }//GEN-LAST:event_jLabel10MouseReleased

    private void lägg_till_användare_button4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lägg_till_användare_button4MouseReleased
        Color color = new Color(0, 102, 153);
        lägg_till_användare_button4.setForeground(color);
        existerande_panel.setVisible(true);
        användare_panel1.setVisible(false);
        AnvändareFönster.pack();
    }//GEN-LAST:event_lägg_till_användare_button4MouseReleased

    private void lägg_till_användare_button4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lägg_till_användare_button4MousePressed
        Color color = new Color(0, 51, 102);
        lägg_till_användare_button4.setForeground(color);
    }//GEN-LAST:event_lägg_till_användare_button4MousePressed

    private void användare_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_användare_fieldFocusLost
        if (användare_field.getText().equals("")) {
            användare_field.setText("Användarnamn");
        }
    }//GEN-LAST:event_användare_fieldFocusLost

    private void användare_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_användare_fieldFocusGained
        if (användare_field.getText().equals("Användarnamn") || användare_field.getText().equals("Fälten är tomma.")
            || användare_field.getText().equals("Fel lösenord eller användarnamn.")) {
            användare_field.setText("");
        }
    }//GEN-LAST:event_användare_fieldFocusGained

    private void lägg_till_användare_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lägg_till_användare_buttonActionPerformed
        /* TESTAR DATABASEN */
        try {
            boolean allowLocal = false;
            if (EmailValidator.getInstance(allowLocal).isValid(e_post_field.getText())) {
                functions.läggTillAnvändare(db, användare_field.getText(), kryptera(användarlösenord_passwordfield.getText()), e_post_field.getText());
                AnvändareFönster.setVisible(false);
                säljare_field.setText(användare_field.getText());
                VäljKundFönster.setVisible(true);
                VäljKundFönster.setLocationRelativeTo(this);
                VäljKundFönster.setLocation(this.getX() + 650, this.getY() + 220);
                VäljKundFönster.pack();
            } else {
                e_post_field.setText("Ange en riktig e-post adress.");
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        /********************/
    }//GEN-LAST:event_lägg_till_användare_buttonActionPerformed

    private void användarlösenord_passwordfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_användarlösenord_passwordfieldKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            lägg_till_användare_button.doClick();
        }
    }//GEN-LAST:event_användarlösenord_passwordfieldKeyReleased

    private void användarlösenord_passwordfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_användarlösenord_passwordfieldFocusLost
        if (användarlösenord_passwordfield.getText().equals("")) {
            användarlösenord_passwordfield.setText("Lösenord");
        }
    }//GEN-LAST:event_användarlösenord_passwordfieldFocusLost

    private void användarlösenord_passwordfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_användarlösenord_passwordfieldFocusGained
        if (användarlösenord_passwordfield.getText().equals("Lösenord")) {
            användarlösenord_passwordfield.setText("");
        }
    }//GEN-LAST:event_användarlösenord_passwordfieldFocusGained

    private void e_post_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e_post_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_e_post_fieldActionPerformed

    private void e_post_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_e_post_fieldFocusLost
        if (e_post_field.getText().equals("")) {
            e_post_field.setText("E-post");
        }
    }//GEN-LAST:event_e_post_fieldFocusLost

    private void e_post_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_e_post_fieldFocusGained
        if(e_post_field.getText().equals("E-post")) {
            e_post_field.setText("");
        }
    }//GEN-LAST:event_e_post_fieldFocusGained

    private void skicka_buttonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_skicka_buttonKeyReleased

    }//GEN-LAST:event_skicka_buttonKeyReleased

    private void skicka_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skicka_buttonActionPerformed
        /* TESTAR DATABASEN */
        if(internet_uppkoppling()) {
            try {
                functions.kollaLösenordsUtskick(db, e_post_field2.getText(), e_post_field1.getText(), jLabel12);
                if(functions.getAnv() != null) {
                    if (functions.getAnv().getNamn().equals(e_post_field2.getText())
                        && functions.getAnv().getEpost().equals(e_post_field1.getText())) {
                        EPostFönster.setVisible(false);
                        Epost epost = new Epost();
                        epost.skickaLösenordPåminnelse(e_post_field1.getText(), dekryptera(functions.getAnv().getLösenord()));
                    } else {

                    }
                } else {

                }
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /**
        * *****************
        */
    }//GEN-LAST:event_skicka_buttonActionPerformed

    private void e_post_field2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e_post_field2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_e_post_field2ActionPerformed

    private void e_post_field2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_e_post_field2FocusLost
        if (e_post_field2.getText().equals("")) {
            e_post_field2.setText("Användarnamn");
        }
    }//GEN-LAST:event_e_post_field2FocusLost

    private void e_post_field2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_e_post_field2FocusGained
        if (e_post_field2.getText().equals("Användarnamn")) {
            e_post_field2.setText("");
        }
    }//GEN-LAST:event_e_post_field2FocusGained

    private void e_post_field1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_e_post_field1KeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            skicka_button.doClick();
        }
    }//GEN-LAST:event_e_post_field1KeyReleased

    private void e_post_field1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e_post_field1ActionPerformed

    }//GEN-LAST:event_e_post_field1ActionPerformed

    private void e_post_field1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_e_post_field1FocusLost
        if (e_post_field1.getText().equals("")) {
            e_post_field1.setText("E-post");
        }
    }//GEN-LAST:event_e_post_field1FocusLost

    private void e_post_field1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_e_post_field1FocusGained
        if (e_post_field1.getText().equals("E-post")) {
            e_post_field1.setText("");
        }
    }//GEN-LAST:event_e_post_field1FocusGained

    private void e_post_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_e_post_fieldKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            lägg_till_användare_button.doClick();
        }
    }//GEN-LAST:event_e_post_fieldKeyReleased

    private void kostnader_tableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kostnader_tableMousePressed

    }//GEN-LAST:event_kostnader_tableMousePressed

    private void välj_distrikt_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_välj_distrikt_comboboxActionPerformed
        if(välj_distrikt_combobox.getSelectedIndex() > 0) {
            välj_distrikt_button.setEnabled(true);
        } else {
            välj_distrikt_button.setEnabled(false);
        }
    }//GEN-LAST:event_välj_distrikt_comboboxActionPerformed

    private void välj_distrikt_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_välj_distrikt_buttonActionPerformed
        if(välj_distrikt_combobox.getSelectedIndex() > 0 ) {
            if (this.isVisible()) {
                this.setVisible(false);
            }
            
            if (välj_kund_combobox.getItemCount() > 1) {
                välj_kund_combobox.removeAllItems();
                välj_kund_combobox.addItem("Välj kund");
            }
            
            nuvarande_distrikt = distrikt[välj_distrikt_combobox.getSelectedIndex() - 1];
            nuvarande_ansvarig = ansvariga[välj_distrikt_combobox.getSelectedIndex() - 1];
            distrikt_label.setText(nuvarande_distrikt);
            välj_kund_panel.setVisible(true);
            lägg_till_kund_panel.setVisible(true);
            välj_distrikt_panel.setVisible(false);
            VäljKundFönster.pack();
            try {
                //LADDA ALLA KUNDER FRÅN DATABASEN
                functions.hämtaKunder(db, nuvarande_distrikt, välj_kund_combobox);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_välj_distrikt_buttonActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        TableRowSorter<TableModel> alla_artiklar_sorter = new TableRowSorter<>(alla_artiklar_table.getModel());
        alla_artiklar_table.setRowSorter(alla_artiklar_sorter);
        String text = jTextField1.getText();
        if (text.length() == 0) {
          alla_artiklar_sorter.setRowFilter(null);
        } else {
          alla_artiklar_sorter.setRowFilter(RowFilter.regexFilter(text));
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        TableRowSorter<TableModel> tidigare_sorter = new TableRowSorter<>(tidigare_table.getModel());
        tidigare_table.setRowSorter(tidigare_sorter);
        String text = jTextField2.getText();
        if (text.length() == 0) {
          tidigare_sorter.setRowFilter(null);
        } else {
          tidigare_sorter.setRowFilter(RowFilter.regexFilter(text));
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        reset(offert_model);
        laddatOffert = true;
        nuvarande_offert = "";
        String[] nuvarandeOffertNamn = välj_offert_combobox.getSelectedItem().toString().split(" ");
        if(nuvarandeOffertNamn.length == 1) {
            nuvarande_offert = nuvarandeOffertNamn[0];
        } else if (nuvarandeOffertNamn.length > 1) {
            for (int i = 0; i < nuvarandeOffertNamn.length - 1; i++) {
                nuvarande_offert += nuvarandeOffertNamn[i] + " ";
            }  
            nuvarande_offert = nuvarande_offert.substring(0, nuvarande_offert.lastIndexOf(" ")) + "";
        }
        System.out.println(nuvarande_offert);
        OrderOffertFönster.setLocationRelativeTo(this);
        OrderOffertFönster.setLocation(this.getX() + 250, this.getY() + 200);
        OrderOffertFönster.pack();
        OrderOffertFönster.setVisible(true);
        try {
            functions.hämtaOffert(db, nuvarande_distrikt, kund, nuvarande_offert, offert_model, valutakurs_list, euro_per_ton_list);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        välj_offert_combobox.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void kvantitet_år_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kvantitet_år_fieldFocusLost
        if(!kvantitet_år_field.getText().contains("st")) {
            kvantitet_år_field.setText(kvantitet_år_field.getText() + " st");
        }
    }//GEN-LAST:event_kvantitet_år_fieldFocusLost

    private void kund_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kund_labelMouseReleased
        if (!kund.equals("Snabbkalkyl")) {
            KundDashboardFrame.setLocationRelativeTo(null);
            KundDashboardFrame.setVisible(true);
            KundDashboardFrame.pack();
        }
    }//GEN-LAST:event_kund_labelMouseReleased

    private void rensa_tabel_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rensa_tabel_buttonActionPerformed
        reset(kostnader_model);
        reset(priser_model);
        reset(övrigt_model);

        totalvikt_total = 0;
        total_antal = 0;
        total_längd = 0;
        pris_kund_inklusive_total = 0;
        total_marginal = 0;

        artikelnummer_list.clear();
        kategorier_list.clear();
        dimensioner_list.clear();
        total_vikt_list.clear();
        antal_list.clear();
        längd_list.clear();
        tolerans_list.clear();
        pris_styck_list.clear();
        euro_per_ton_list.clear();
        valutakurs_list.clear();

        rensa_artikelnummer_helt();
        rensa_tabel_dialog.setVisible(false);

        counter = 0;
    }//GEN-LAST:event_rensa_tabel_buttonActionPerformed

    private void avbryt_rensa_tabel_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avbryt_rensa_tabel_buttonActionPerformed
        rensa_tabel_dialog.setVisible(false);
    }//GEN-LAST:event_avbryt_rensa_tabel_buttonActionPerformed

    private void kundDashboardLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kundDashboardLabelMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_kundDashboardLabelMouseReleased

    private void KundDashboardFrameWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_KundDashboardFrameWindowOpened

        dashboardItemColors(uppgifterDashboardPanel);

        try {
            if(!kund.equals("Snabbkalkyl")) {
                functions.hämtaKundUppgifter(db, nuvarande_distrikt, kund);
                kundAdressField.setText(functions.getKund().getAdress());
                kundEpostField.setText(functions.getKund().getEpost());
                kundSkapadField.setText(functions.getKund().getSkapad());
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_KundDashboardFrameWindowOpened

    private void taBortKundButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taBortKundButtonActionPerformed
        functions.taBortKund(db, nuvarande_distrikt, kund);
        KundDashboardFrame.setVisible(false);
        this.setVisible(false);
        try {
            välj_kund_combobox.removeItem(kund);
            functions.hämtaKunder(db, nuvarande_distrikt, välj_kund_combobox);
            kund = null;
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        VäljKundFönster.setVisible(true);
    }//GEN-LAST:event_taBortKundButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        HashMap<String, String> uppgifter = new HashMap<>();
        uppgifter.put("Namn", kund);
        uppgifter.put("Adress", kundAdressField.getText());
        uppgifter.put("Epost", kundEpostField.getText());
        functions.sparaKundUppgifter(db, nuvarande_distrikt, kund, uppgifter);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (offert_model.getRowCount() > 0) {
            FärdigställFörsäljningFönster1.setLocationRelativeTo(this);
            FärdigställFörsäljningFönster1.pack();
            FärdigställFörsäljningFönster1.setVisible(true);
        }
        System.out.println(artikelnummer_field_spara_två);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        OrderOffertFönster.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void offert_tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_offert_tablePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_offert_tablePropertyChange

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if(offert_model.getRowCount() > 1) {
            int selected_row = offert_table.getSelectedRow();
            if(offert_table.isRowSelected(selected_row)) {
                functions.taBortOffertRad(db, nuvarande_distrikt, kund, nuvarande_offert, offert_model, offert_model.getValueAt(selected_row, 0).toString());
                euro_per_ton_list.remove(selected_row);
                valutakurs_list.remove(selected_row);
            }
            offert_model.removeRow(selected_row);
        } else {
            functions.rensaOffert(db, nuvarande_distrikt, kund, nuvarande_offert);
            nuvarande_offert = null;
            OrderOffertFönster.setVisible(false);
            info_label.setVisible(false);
            LaddaOffertFönster.setVisible(false);
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void ta_bort_offert_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_bort_offert_button1ActionPerformed
        for(int i = 0; i < färdig_table.getRowCount(); i++) {
            if(euro_per_ton_list.size() > 0) {
                euro_per_ton_list.remove(i);
            }
            if(valutakurs_list.size() > 0) {
                valutakurs_list.remove(i);
            }
        }
        
        reset(offert_model);
        välj_offert_combobox.removeItem(nuvarande_offert);
        functions.rensaOffert(db, nuvarande_distrikt, kund, nuvarande_offert);
        if (välj_offert_combobox.getItemCount() == 1) {
            info_label.setVisible(false);
            övergång_icon.setVisible(false);
            VäljKundFönster.setVisible(false);
        }
        Orderfönster.setVisible(false);
    }//GEN-LAST:event_ta_bort_offert_button1ActionPerformed

    private void OrderOffertFönsterWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OrderOffertFönsterWindowClosing

    }//GEN-LAST:event_OrderOffertFönsterWindowClosing

    private void OrderOffertFönsterWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OrderOffertFönsterWindowOpened
        kund_färdig_label1.setText(kund);
    }//GEN-LAST:event_OrderOffertFönsterWindowOpened

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        FärdigställFörsäljningFönster1.setVisible(false);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        försäljningKnappen(offert_model);
        kollaOmArtikelÄrSåld();
        FärdigställFörsäljningFönster1.setVisible(false);
        OrderOffertFönster.setVisible(false);
        if(välj_offert_combobox.getItemCount() == 1) {
            LaddaOffertFönster.setVisible(false);
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void offert_tableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offert_tableMouseReleased
        if (evt.getClickCount() == 2) {
            if (offert_table.isRowSelected(offert_table.getSelectedRow())) {
                /* TESTAR DATABASEN */
                hämta_ett_artikelnummer(offert_table.getValueAt(offert_table.getSelectedRow(), 1).toString());
                /********************/
                godstjocklek = 0;
                valdRad = offert_table.getValueAt(offert_table.getSelectedRow(), 0).toString();
                rad = offert_table.getSelectedRow();
                ändrarPåRad = true;
                för_till_orderfönster_button.setVisible(false);
                till_orderfönster_button.setVisible(false);
                uppdatera_rad_button.setVisible(true);
                info_label.setVisible(false);
                offert_label.setVisible(true);
                offert_label.setText(nuvarande_offert + " > " + valdRad);
                uträkning();
                OrderOffertFönster.setVisible(false);
                LaddaOffertFönster.setVisible(false);
                this.pack();
                reset(kostnader_model);
                reset(priser_model);
                reset(övrigt_model);
            }
        }
    }//GEN-LAST:event_offert_tableMouseReleased

    private void uppdatera_rad_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uppdatera_rad_buttonActionPerformed
        if(kostnader_model.getRowCount() > 0) {
            
            OrderOffertFönster.setVisible(true);
            
            int selected_row = 0;

            for (JTable table : tables) {
                if (table.isRowSelected(table.getSelectedRow())) {
                    selected_row = table.getSelectedRow();
                }
            }
            
            radInformation.put("Kategori", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Kategori")).toString());
            radInformation.put("Dimension", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Dimension")).toString());
            radInformation.put("Antal", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Antal")).toString());
            radInformation.put("Längd", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Längd")).toString());
            radInformation.put("TotalVikt", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Vikt")).toString());
            radInformation.put("EuroPerTon", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Euro per ton")).toString());
            radInformation.put("Valutakurs", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Valutakurs")).toString());
            radInformation.put("Artikelnummer", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Artikelnummer")).toString());
            radInformation.put("Tolerans", övrigt_model.getValueAt(selected_row, övrigt_model.findColumn("Tolerans")).toString());
            radInformation.put("PrisStyck", priser_model.getValueAt(selected_row, 2).toString());
            radInformation.put("PrisLeverans", priser_model.getValueAt(selected_row, 3).toString());
            
            offert_model.setValueAt(radInformation.get("Artikelnummer"), rad, 1);
            offert_model.setValueAt(radInformation.get("Kategori"), rad, 2);
            offert_model.setValueAt(radInformation.get("Dimension"), rad, 3);
            offert_model.setValueAt(radInformation.get("TotalVikt"), rad, 4);
            offert_model.setValueAt(radInformation.get("Antal"), rad, 5);
            offert_model.setValueAt(radInformation.get("Längd"), rad, 6);
            offert_model.setValueAt(radInformation.get("Tolerans"), rad, 7);
            offert_model.setValueAt(radInformation.get("PrisStyck"), rad, 8);
            offert_model.setValueAt(radInformation.get("PrisLeverans"), rad, 9);
            euro_per_ton_list.set(rad, radInformation.get("EuroPerTon"));
            valutakurs_list.set(rad, radInformation.get("Valutakurs"));
            
            functions.uppdateraOffertRad(db, nuvarande_distrikt, kund, nuvarande_offert, valdRad, radInformation);
            uppdatera_rad_button.setVisible(false);
            offert_label.setVisible(false);
            för_till_orderfönster_button.setVisible(true);
            till_orderfönster_button.setVisible(true);
            info_label.setVisible(true);
            ändrarPåRad = false;
            rad = 0;
            valdRad = null;
            radInformation.clear();

            this.pack();
        }
    }//GEN-LAST:event_uppdatera_rad_buttonActionPerformed

    private void skapa_offert_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skapa_offert_button1ActionPerformed
        if (färdig_model.getRowCount() > 0) {
            OffertFönster.setVisible(true);
            OffertFönster.setLocationRelativeTo(this);
            OffertFönster.pack();
        }
    }//GEN-LAST:event_skapa_offert_button1ActionPerformed

    private void offert_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offert_labelMouseReleased
        OrderOffertFönster.setVisible(true);
        OrderOffertFönster.setLocationRelativeTo(this);
        OrderOffertFönster.pack();
    }//GEN-LAST:event_offert_labelMouseReleased

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        VäljKundFönster.setVisible(true);
        välj_kund_panel.setVisible(false);
        lägg_till_kund_panel.setVisible(false);
        laddar_panel.setVisible(false);
        välj_distrikt_panel.setVisible(true);
        VäljKundFönster.pack();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void kilo_per_meter_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kilo_per_meter_labelMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_kilo_per_meter_labelMouseReleased

    private void kilo_per_meter_panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kilo_per_meter_panelMouseReleased
        sätt_färg(kilo_per_meter_label, kilo_per_meter_label2, kilo_per_meter_panel);
        sätt_färg(kilo_per_meter_label, kilo_per_meter_label2, kilo_per_meter_panel2);
    }//GEN-LAST:event_kilo_per_meter_panelMouseReleased

    private void kilo_per_meter_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kilo_per_meter_fieldFocusGained
        reset(kilo_per_meter_field);
        ;
    }//GEN-LAST:event_kilo_per_meter_fieldFocusGained

    private void kilo_per_meter_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kilo_per_meter_fieldFocusLost
        set0(kilo_per_meter_field);
    }//GEN-LAST:event_kilo_per_meter_fieldFocusLost

    private void kilo_per_meter_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kilo_per_meter_fieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kilo_per_meter_fieldKeyPressed

    private void kilo_per_meter_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kilo_per_meter_fieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_kilo_per_meter_fieldKeyReleased

    private void kilo_per_meter_label2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kilo_per_meter_label2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_kilo_per_meter_label2MouseReleased

    private void kilo_per_meter_panel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kilo_per_meter_panel2MouseReleased
        sätt_färg(kilo_per_meter_label, kilo_per_meter_label2, kilo_per_meter_panel);
        sätt_färg(kilo_per_meter_label, kilo_per_meter_label2, kilo_per_meter_panel2);
    }//GEN-LAST:event_kilo_per_meter_panel2MouseReleased

    private void stålkvalitet_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stålkvalitet_fieldKeyReleased
        stålkvalitet_combobox.setSelectedItem("Alternativ stålkvalitet");
        kilo_per_meter_panel.setVisible(true);
        kilo_per_meter_field.setVisible(true);
        kilo_per_meter_panel2.setVisible(true);
        if (stålkvalitet_field.getText().length() < 1) {
            kilo_per_meter_panel.setVisible(false);
            kilo_per_meter_field.setVisible(false);
            kilo_per_meter_panel2.setVisible(false);
        }
    }//GEN-LAST:event_stålkvalitet_fieldKeyReleased

    private void stålkvalitet_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stålkvalitet_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stålkvalitet_fieldActionPerformed

    private void offert_stäng_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offert_stäng_buttonActionPerformed
        OffertFönster.setVisible(false);
    }//GEN-LAST:event_offert_stäng_buttonActionPerformed

    private void offert_klar_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offert_klar_buttonActionPerformed
        if (färdig_model.getRowCount() > 0) {
            try {
                offert(offert_nummer + "_" + offert_namn_field.getText());
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_offert_klar_button", ex);
            }

            försäljningsArtikelTillDatabas();
            reset(alla_artiklar_model);
            reset(färdig_model);

            /* TESTAR DATABASEN */
            try {
                functions.hämtaAllaArtiklar(db, nuvarande_distrikt, kund, alla_artiklar_model);
                välj_offert_combobox.removeAllItems();
                ladda_offerter();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
            /**
            * *****************
            */

            valutakurs_list.clear();
            euro_per_ton_list.clear();
            LaddaOffertFönster.setVisible(false);
            OffertFönster.setVisible(false);
        }
    }//GEN-LAST:event_offert_klar_buttonActionPerformed

    private void offert_namn_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offert_namn_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_offert_namn_fieldActionPerformed

    private void jButton3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseReleased
        if (alla_artiklar_table.isRowSelected(alla_artiklar_table.getSelectedRow())) {
            String artikelnummer = alla_artiklar_table.getValueAt(alla_artiklar_table.getSelectedRow(), 0).toString();
            functions.rensaArtikelnummer(db, nuvarande_distrikt, kund, artikelnummer);
            alla_artiklar_model.removeRow(alla_artiklar_table.getSelectedRow());
        }
    }//GEN-LAST:event_jButton3MouseReleased

    private void jButton11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseReleased
        if (tidigare_table.isRowSelected(tidigare_table.getSelectedRow())) {
            Map data = new HashMap<>();
            data.put("Antal", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Antal detaljer")));
            data.put("Artikelnummer", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Artikelnummer")));
            data.put("Datum", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Datum")));
            data.put("Dimension", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Dimension")));
            data.put("EuroPerTon", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Euro per ton")));
            data.put("Kategori", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Kategori")));
            data.put("Längd", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Längd")));
            data.put("PrisLeverans", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Tidigare pris")));
            data.put("Valutakurs", tidigare_table.getValueAt(tidigare_table.getSelectedRow(), tidigare_model.findColumn("Valutakurs")));
            String artikelnummer = tidigare_table.getValueAt(tidigare_table.getSelectedRow(), 0).toString();
            try {
                functions.rensaFörsäljning(db, nuvarande_distrikt, kund, artikelnummer, data);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
            tidigare_model.removeRow(tidigare_table.getSelectedRow());
        }
    }//GEN-LAST:event_jButton11MouseReleased

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        jDateChooser1.getDateEditor().getUiComponent().repaint();
        jDateChooser1.getDateEditor().getUiComponent().setForeground(Color.RED);
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void FärdigställFörsäljningFönsterWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_FärdigställFörsäljningFönsterWindowOpened
        jProgressBar1.setValue(0);
    }//GEN-LAST:event_FärdigställFörsäljningFönsterWindowOpened

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        jProgressBar1.setValue(jProgressBar1.getValue() + 10);
        System.out.println(jProgressBar1.getValue());
        
        if (jProgressBar1.getValue() < 50) {
            jProgressBar1.setStringPainted(true);
            jProgressBar1.setForeground(Color.GREEN);
            jLabel22.setText("Ledigt");
        } else if(jProgressBar1.getValue() >= 50 && jProgressBar1.getValue() < 75) {
            jProgressBar1.setStringPainted(true);
            jProgressBar1.setForeground(Color.YELLOW);
            jLabel22.setText("Ont om tid");
        } else if (jProgressBar1.getValue() >= 75) {
            jProgressBar1.setStringPainted(true);
            jProgressBar1.setForeground(Color.RED);
            jLabel22.setText("Upptaget");
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void dashboardItemColors (JPanel panel) {
        panel.addMouseListener(new MouseAdapter () {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(245, 245, 245));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if(!dashboardUppgifterPanel.isVisible()) {
                    panel.setBackground(new Color(255, 255, 255));
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
        });
    }
    
    // Kolla ljusstyrkan på paneler vid uppstart och avgör ifall texten behöver vara vit eller svart.
    private void ladda_ljushet() {
        ändra_text_färg(valutakurs_kr_label, valutakurs_kr_label, valutakurs_panel2.getBackground());

        ändra_text_färg(spill_label, jLabel44, spill_panel.getBackground());
        ändra_text_färg(spill_label, jLabel44, spill_procent_panel2.getBackground());

        ändra_text_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel.getBackground());
        ändra_text_färg(fraktkostnad_sicam_label, fraktkostnad_sicam_kr_kg_label, fraktkostnad_sicam_panel2.getBackground());

        ändra_text_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel.getBackground());
        ändra_text_färg(fraktkostnad_label, fraktkostnad_kr_kg_label, fraktkostnad_per_kilo_panel2.getBackground());

        ändra_text_färg(påslag_label, påslag_procent_label, påslag_panel.getBackground());
        ändra_text_färg(påslag_label, påslag_procent_label, påslag_panel2.getBackground());

        ändra_text_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel.getBackground());
        ändra_text_färg(ursprungslängd_label, ursprungslängd_mm_label, ursprungslängd_panel2.getBackground());

        ändra_text_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel.getBackground());
        ändra_text_färg(ställkostnad_label, ställkostnad_kr_label, ställkostnad_panel2.getBackground());

        ändra_text_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel.getBackground());
        ändra_text_färg(euro_per_ton_label, euro_per_ton_euro_label, euro_per_ton_panel2.getBackground());

        ändra_text_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel.getBackground());
        ändra_text_färg(längdtolerans_label, längdtolerans_mm_label, längdtolerans_panel2.getBackground());

        ändra_text_färg(längd_label, längd_mm_label, längd_panel.getBackground());
        ändra_text_färg(längd_label, längd_mm_label, längd_panel2.getBackground());

        ändra_text_färg(antal_label, antal_styck_label, antal_panel.getBackground());
        ändra_text_färg(antal_label, antal_styck_label, antal_panel2.getBackground());

        ändra_text_färg(dimension_label, dimension_mm_label, dimension_panel.getBackground());
        ändra_text_färg(dimension_label, dimension_mm_label, dimension_panel2.getBackground());

        ändra_text_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel.getBackground());
        ändra_text_färg(bearbetning_label, bearbetning_kr_kg_label, bearbetning_panel2.getBackground());

        ändra_text_färg(artikelnummer_label, artikelnummer_label, artikel_panel.getBackground());

        ändra_text_färg(datum_label, datum_label, datum_panel.getBackground());

        ändra_text_färg(säljare_label, säljare_label, säljare_panel.getBackground());

        ändra_text_färg(kvantitet_per_år_label, kvantitet_per_år_label, kvantitet_år_panel.getBackground());
        ändra_text_färg(marginal_per_år_label, marginal_per_år_label, marginal_år_panel.getBackground());
        ändra_text_färg(omsättning_per_år_label, omsättning_per_år_label, omsättning_år_panel.getBackground());
        ändra_text_färg(ton_per_år_label, ton_per_år_label, ton_år_panel.getBackground());
        ändra_text_färg(kvantitet_hittils_label, kvantitet_hittils_label, kvantitet_hittils_panel.getBackground());
        ändra_text_färg(belopp_hittils_label, belopp_hittils_label, belopp_hittils_panel.getBackground());
        ändra_text_färg(marginal_total_label, marginal_total_label, marginal_total_panel.getBackground());
        ändra_text_färg(marginal_styck_label, marginal_styck_label, marginal_styck_panel.getBackground());
        ändra_text_färg(täckningsgrad_label, täckningsgrad_label, täckningsgrad_panel.getBackground());
        ändra_text_färg(totalvikt_label, totalvikt_label, totalvikt_panel.getBackground());
        ändra_text_färg(vikt_styck_label, vikt_styck_label, vikt_styck_panel.getBackground());
        ändra_text_färg(pris_styck_label, pris_styck_label, pris_styck_panel.getBackground());
        ändra_text_färg(pris_totalt_label, pris_totalt_label, pris_totalt_panel.getBackground());
        ändra_text_färg(förändring_i_procent_label, förändring_i_procent_label, förändring_i_procent_panel.getBackground());
        ändra_text_färg(förändring_i_kr_label, förändring_i_kr_label, förändring_i_kr_panel.getBackground());
        
        ändra_text_färg(kilo_per_meter_label, kilo_per_meter_label2, kilo_per_meter_panel.getBackground());
    }

    // Sätt färg baserat på vilken panel man klickar på.
    private void sätt_färg(JLabel text1, JLabel text2, JPanel panel) {
        if (FärgFönster.isVisible()) {
            panel.setBackground(färg_väljare.getColor());
            ändra_text_färg(text1, text2, panel.getBackground());
            sparaFärgTillDatabas(panel);
        }
    }

    // Kolla ljusstyrkan på paneler och avgör ifall texten behöver vara vit eller svart.
    private static int kolla_ljushet(JLabel text1, JLabel text2, Color c) {
        return (int) Math.sqrt(
                c.getRed() * c.getRed() * .241
                + c.getGreen() * c.getGreen() * .691
                + c.getBlue() * c.getBlue() * .068);
    }

    // Ändra färgen på texten baserat på kolla_ljushet metoden.
    private static void ändra_text_färg(JLabel text1, JLabel text2, Color color) {
        if (kolla_ljushet(text1, text2, color) < 130) {
            text1.setForeground(Color.white);
            text2.setForeground(Color.white);
        } else {
            text1.setForeground(Color.black);
            text2.setForeground(Color.black);
        }
    }

    // Räkna totala värden för under färdig tabellen i orderfönstret.
    private void räkna_totalvärden() {
        //Sätt variablar för tabel
        double totalvikt = 0, totalantal = 0, totalpris = 0;
        for (int i = 0; i < färdig_model.getRowCount(); i++) { 
            totalvikt += Double.parseDouble(färdig_model.getValueAt(i, 3).toString().replaceAll("[^\\d.]", ""));
            totalantal += Double.parseDouble(färdig_model.getValueAt(i, 4).toString().replaceAll("[^\\d.]", ""));
            totalpris += Double.parseDouble(färdig_model.getValueAt(i, 8).toString().replaceAll("[^\\d.]", ""));
        }

        sammanräknat_pris_per_styck_field.setText(format.format(totalpris));
        sammanräknat_antal_field.setText(format.format(totalantal));
        sammanräknad_total_vikt_field.setText(format.format(totalvikt));
    }

    // Ersätter felaktiga tecken, som ',' och bokstäver i variabel textfälten.
    private void ersätt_tecken() {
        fields.stream().filter((field) -> (!field.getName().equals("dimension_field")
                && !field.getName().equals("datum_field"))).map((field) -> {
            if (field.getText().contains(",")) {
                field.setText(field.getText().replace(',', '.'));
            }
            return field;
        }).map((field) -> {
            if (field.getText().indexOf('.', field.getText().indexOf('.') + 1) != -1) {
                field.setText(field.getText().substring(0, field.getText().length() - 1));
            }
            return field;
        }).filter((field) -> (field.getText().indexOf(',', field.getText().indexOf(',') + 1) != -1)).forEachOrdered((field) -> {
            field.setText(field.getText().substring(0, field.getText().length() - 1));
        });

        fields.stream().filter((field) -> (field.getText().startsWith("."))).forEachOrdered((JTextField field) -> {
            field.setText(field.getText().replace(".", ""));
        });

        if (dimension_field.getText().contains(",")) {
            dimension_field.setText(dimension_field.getText().replace(',', '.'));
        } else if (dimension_field.getText().contains("X")) {
            dimension_field.setText(dimension_field.getText().replace('X', 'x'));
        } else if (!dimension_field.getText().contains("[\\d.x/]")) {
            dimension_field.setText(dimension_field.getText().replaceAll("[^\\d.x/]", ""));
        }

        if (dimension_field.getText().startsWith("[^\\d]")) {
            dimension_field.setText(dimension_field.getText().replaceAll("[^\\d]", ""));
        } else if (dimension_field.getText().startsWith("x")) {
            dimension_field.setText(dimension_field.getText().replaceAll("x", ""));
        }

        if (fraktkostnad_field.getText().contains(",") || !fraktkostnad_field.getText().contains("^\\d.")) {
            fraktkostnad_field.setText(fraktkostnad_field.getText().replace(',', '.'));
            fraktkostnad_field.setText(fraktkostnad_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (påslag_field.getText().contains(",") || !påslag_field.getText().contains("^\\d.")) {
            påslag_field.setText(påslag_field.getText().replace(',', '.'));
            påslag_field.setText(påslag_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (spill_field.getText().contains(",") || !spill_field.getText().contains("^\\d.")) {
            spill_field.setText(spill_field.getText().replace(',', '.'));
            spill_field.setText(spill_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (valutakurs_field.getText().contains(",") || !valutakurs_field.getText().contains("^\\d.")) {
            valutakurs_field.setText(valutakurs_field.getText().replace(',', '.'));
            valutakurs_field.setText(valutakurs_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (fraktkostnad_sicam_field.getText().contains(",") || !fraktkostnad_sicam_field.getText().contains("^\\d.")) {
            fraktkostnad_sicam_field.setText(fraktkostnad_sicam_field.getText().replace(',', '.'));
            fraktkostnad_sicam_field.setText(fraktkostnad_sicam_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (bearbetning_field.getText().contains(",") || !fraktkostnad_sicam_field.getText().contains("^\\d.")) {
            bearbetning_field.setText(bearbetning_field.getText().replace(',', '.'));
            bearbetning_field.setText(bearbetning_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (antal_field.getText().contains(",") || !antal_field.getText().contains("^\\d.")) {
            antal_field.setText(antal_field.getText().replace(',', '.'));
            antal_field.setText(antal_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (längd_field.getText().contains(",") || !längd_field.getText().contains("^\\d.")) {
            längd_field.setText(längd_field.getText().replace(',', '.'));
            längd_field.setText(längd_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (euro_per_ton_field.getText().contains(",") || !euro_per_ton_field.getText().contains("^\\d.")) {
            euro_per_ton_field.setText(euro_per_ton_field.getText().replace(',', '.'));
            euro_per_ton_field.setText(euro_per_ton_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (längdtolerans_field.getText().contains(",") || !längdtolerans_field.getText().contains("^\\d.+/-")) {
            längdtolerans_field.setText(längdtolerans_field.getText().replace(',', '.'));
            längdtolerans_field.setText(längdtolerans_field.getText().replaceAll("[^\\d.+/-]", ""));
        }
        if (ställkostnad_field.getText().contains(",") || !ställkostnad_field.getText().contains("^\\d.")) {
            ställkostnad_field.setText(ställkostnad_field.getText().replace(',', '.'));
            ställkostnad_field.setText(ställkostnad_field.getText().replaceAll("[^\\d.]", ""));
        }
        if (ursprungslängd_field.getText().contains(",") || !ursprungslängd_field.getText().contains("^\\d.")) {
            ursprungslängd_field.setText(ursprungslängd_field.getText().replace(',', '.'));
            ursprungslängd_field.setText(ursprungslängd_field.getText().replaceAll("[^\\d.]", ""));
        }
    }

    private boolean är_nummer(String input) {
        if (!"".equals(input)) {
            try {
                if (input.equals("[\\d]")) {
                    Double.parseDouble(input);
                }
                return true;
            } catch (NumberFormatException e) {
                loggare.log_info(e.getMessage(), "Program_är_nummer", e);
                return false;
            }
        } else {
            return false;
        }
    }

    private void ändra_stålverk() {
        switch (stålkvalitet_combobox.getSelectedItem().toString()) {
            case "SÖE470":
                if (stålkvalitet_combobox.getItemCount() > 1) {
                    for (int i = stålverk_combobox.getItemCount() - 1; i >= 1; i--) {
                        stålverk_combobox.removeItemAt(i);
                    }
                }
                stålverk_combobox.addItem("ESW");
                stålverk_combobox.addItem("V&M");
                stålverk_combobox.addItem("BENTELER");
                stålverk_combobox.addItem("TMK");
                stålverk_combobox.addItem("HUBEI");
                stålverk_combobox.addItem("OVARO");
                break;
            case "SVKCKR":
                if (stålkvalitet_combobox.getItemCount() > 1) {
                    for (int i = stålverk_combobox.getItemCount() - 1; i >= 1; i--) {
                        stålverk_combobox.removeItemAt(i);
                    }
                }
                stålverk_combobox.addItem("ESW");
                stålverk_combobox.addItem("HUBEI");
                stålverk_combobox.addItem("MORAVIA");
                stålverk_combobox.addItem("PIPEX");
                stålverk_combobox.addItem("PT");
                stålverk_combobox.addItem("ALCH. BAT");
                break;
            case "SÖDIN":
                if (stålkvalitet_combobox.getItemCount() > 1) {
                    for (int i = stålverk_combobox.getItemCount() - 1; i >= 1; i--) {
                        stålverk_combobox.removeItemAt(i);
                    }
                }
                stålverk_combobox.addItem("ESW");
                stålverk_combobox.addItem("HUBEI");
                stålverk_combobox.addItem("MORAVIA");
                stålverk_combobox.addItem("PIPEX");
                stålverk_combobox.addItem("PT");
                stålverk_combobox.addItem("BENTELER");
                stålverk_combobox.addItem("ALCH. BAT");
                break;
            case "SVKKR":
                if (stålkvalitet_combobox.getItemCount() > 1) {
                    for (int i = stålverk_combobox.getItemCount() - 1; i >= 1; i--) {
                        stålverk_combobox.removeItemAt(i);
                    }
                }
                stålverk_combobox.addItem("ARVEDI");
                stålverk_combobox.addItem("ALESSIO");
                stålverk_combobox.addItem("CORINTH");
                stålverk_combobox.addItem("ILVA");
                stålverk_combobox.addItem("MARCEGAGLIA");
                stålverk_combobox.addItem("SIDERALBA");
                stålverk_combobox.addItem("TATA");
                stålverk_combobox.addItem("TATA NL");
                break;
            case "SVVKR":
                if (stålkvalitet_combobox.getItemCount() > 1) {
                    for (int i = stålverk_combobox.getItemCount() - 1; i >= 1; i--) {
                        stålverk_combobox.removeItemAt(i);
                    }
                }
                stålverk_combobox.addItem("ARVEDI");
                stålverk_combobox.addItem("LORRAINE");
                stålverk_combobox.addItem("TATA");
                break;
            default:
                break;
        }
    }

    // Metod för att spara avtal med kund.
    private void spara_kund_avtal() {
        /* TESTAR DATABASEN AVTAL */
        HashMap<String, Integer> avtalData = new HashMap<>();
        avtalData.put("Kvantitet", Integer.parseInt(kvantitet_år_field.getText().replaceAll("[^\\d]", "")));
        avtalData.put("Offert", offert_nummer);
        try {
            functions.sparaKundAvtal(db, nuvarande_distrikt, kund, avtalData);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
         * ***********************
         */
    }

    // Ladda sparat kund avtal.
    private void ladda_kund_avtal() {
        /* TESTAR DATABASEN AVTAL */
        try {
            functions.laddaKundAvtal(db, nuvarande_distrikt, kund);
            offert_nummer = functions.getAvt().getOffert();
            kvantitet_år_field.setText(Integer.toString(functions.getAvt().getKvantitet()) + " st");
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
         * ***********************
         */
    }

    private void kolla_gerkap() {
        switch (gerkap_combobox.getSelectedIndex()) {
            case 0:
                gerkap_faktor = 0;
                break;
            case 1:
                gerkap_faktor = 1.5;
                break;
            case 2:
                gerkap_faktor = 2.0;
                break;
            case 3:
                gerkap_faktor = 2.5;
                break;
            case 4:
                gerkap_faktor = 3.0;
                break;
            default:
                break;
        }
    }

    private void kolla_dimension() {
        if (dimension_field.getText().contains("/")) {
            s = dimension_field.getText().split("/");
            ämnesrör = true;
        } else {
            s = dimension_field.getText().split("x");
            ämnesrör = false;
        }

        if (s.length > 0) {
            if (är_nummer(s[0])) {
                diameter_text.setText(s[0]);
            }
        } else {
            diameter_text.setText("0");
        }
    }

    private void räkna_ut_vikten() {
        if (!ämnesrör && !dimension_field.getText().isEmpty()) {
            switch (s.length) {
                case 3: {
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(s[2]);
                    } else {
                        godstjocklek_text.setText("0");
                    }
                    material_form = 2;
                    double B12;
                    double B12_1;
                    double B13;
                    double B13_1;
                    double C20 = (Double.parseDouble(s[0]) + Double.parseDouble(s[1])) * 2 / 1000;
                    double C21 = (((Double.parseDouble(s[0]) - 2 * Double.parseDouble(godstjocklek_text.getText())) + (Double.parseDouble(s[1]) - 2 * Double.parseDouble(godstjocklek_text.getText()))) * 2) / 1000;
                    double C22 = ((C20 + C21) * 137.5) / 1000;
                    //vikt_per_meter = (Double.parseDouble(diameter_text.getText()) * Double.parseDouble(diameter_text.getText())) * (Double.parseDouble(godstjocklek_text.getText()) / 3) * 0.000896;
                    if (Double.parseDouble(godstjocklek_text.getText()) <= 6) {
                        B13_1 = 1;
                    } else if (Double.parseDouble(godstjocklek_text.getText()) <= 10) {
                        B13_1 = 1.5;
                    } else {
                        B13_1 = 2;
                    }
                    B13 = B13_1 * Double.parseDouble(godstjocklek_text.getText());
                    if (Double.parseDouble(godstjocklek_text.getText()) <= 1.5) {
                        B12_1 = 1;
                    } else if (Double.parseDouble(godstjocklek_text.getText()) <= 6) {
                        B12_1 = 2;
                    } else if (Double.parseDouble(godstjocklek_text.getText()) <= 10) {
                        B12_1 = 2.5;
                    } else {
                        B12_1 = 3;
                    }
                    B12 = B12_1 * Double.parseDouble(godstjocklek_text.getText());
                    if (stålkvalitet_combobox.getSelectedIndex() <= 1) {
                        //               (1 / 10^2            * (2 * B11                                             * (B10                      +  B9                        - 2 * B11                                            ) - (4 - 3.1416) * (B12^2            - B13^2           )) * 0.785)
                        vikt_per_meter = (1 / Math.pow(10, 2) * (2 * Double.parseDouble(godstjocklek_text.getText()) * (Double.parseDouble(s[0]) + Double.parseDouble(s[1]) - 2 * Double.parseDouble(godstjocklek_text.getText())) - (4 - 3.1416) * (Math.pow(B12, 2) - Math.pow(B13, 2))) * 0.785);
                    } else if (stålkvalitet_combobox.getSelectedIndex() == 2) {
                        double C17 = 1.5 * Double.parseDouble(godstjocklek_text.getText());
                        double C18 = 1 * Double.parseDouble(godstjocklek_text.getText());
                        //           (1/         10^ 2  * (2 * C16                                             * (C15                      + C14                      - 2 * C16                                            ) - (4 - 3.1416) * (         C17^ 2  -          C18^ 2)) * 0.785)
                        vikt_per_meter = (1 / Math.pow(10, 2) * (2 * Double.parseDouble(godstjocklek_text.getText()) * (Double.parseDouble(s[0]) + Double.parseDouble(s[1]) - 2 * Double.parseDouble(godstjocklek_text.getText())) - (4 - 3.1416) * (Math.pow(C17, 2) - Math.pow(C18, 2))) * 0.785);
                    }
                    break;
                }
                case 2: {
                    double C19 = (Double.parseDouble(s[0]) * 3.1416) / 1000;
                    double C20 = ((Double.parseDouble(s[0]) - 2 * Double.parseDouble(godstjocklek_text.getText())) * 3.1416) / 1000;
                    double C21 = ((C19 + C20) * 137.5) / 1000;
                    //                   (3.1416 * (B9                               ^ 2   -          (B9                       - 2 * B10                                            )^ 2)   / (4 *          10^ 2))  * 0.785
                    double uträkning_1 = (3.1416 * (Math.pow(Double.parseDouble(s[0]), 2) - Math.pow((Double.parseDouble(s[0]) - 2 * Double.parseDouble(godstjocklek_text.getText())), 2)) / (4 * Math.pow(10, 2))) * 0.785;
                    //                   (3.1416 * (B9                               ^ 2   - (B9                                - 2 * B10                                            )^ 2)   / (4 *          10^ 2))  * 0.785 + B16
                    double uträkning_2 = (3.1416 * (Math.pow(Double.parseDouble(s[0]), 2)) - (Math.pow(Double.parseDouble(s[0]) - 2 * Double.parseDouble(godstjocklek_text.getText()), 2)) / (4 * Math.pow(10, 2)) * 0.785 + C21);
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(s[1]);
                    } else {
                        godstjocklek_text.setText("0");
                    }
                    material_form = 0;
                    vikt_per_meter = uträkning_1;
                    break;
                }
                case 1:
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(s[0]);
                    } else {
                        godstjocklek_text.setText("0");
                    }
                    material_form = 1;
                    if (är_nummer(diameter_text.getText())) {
                        vikt_per_meter = ((Double.parseDouble(diameter_text.getText()) / 10) * (Double.parseDouble(diameter_text.getText()) / 10)) * 0.6165;
                    }
                    break;
                default:
                    break;
            }
        } else if (ämnesrör && s.length == 2 && stålverk_combobox.getSelectedItem() == "ESW") {
            ArrayList<String> YD = excelreader.YD_ESW;
            ArrayList<String> ID = excelreader.ID_ESW;
            ArrayList<String> KGM = excelreader.KGM_ESW;

            double dimensionen = Double.parseDouble(s[0]);
            double godstjockleken = Double.parseDouble(s[1]);

            double[] YDS = new double[YD.size()];
            double[] IDS = new double[ID.size()];
            double[] KGMS = new double[KGM.size()];

            int m = 0;
            int n = 0;
            int o = 0;

            for (String yd : YD) {
                YDS[m++] = Double.parseDouble(yd);
            }

            for (String id : ID) {
                IDS[n++] = Double.parseDouble(id);
            }

            for (String kgm : KGM) {
                KGMS[o++] = Double.parseDouble(kgm);
            }

            for (int i = 0; i < YDS.length; i++) {
                double ytter_dimension = YDS[i];
                double inner_dimension = IDS[i];
                if (närmsta_värde(YDS, dimensionen) == ytter_dimension && närmsta_värde(IDS, godstjockleken) == inner_dimension) {
                    material_form = 1;
                    vikt_per_meter = KGMS[i];
                    double godstjocklek_ämnesrör = (ytter_dimension - inner_dimension) / 2;
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(Double.toString(godstjocklek_ämnesrör));
                    } else {
                        godstjocklek_text.setText("0");
                    }
                }
            }

            stålverk_euro_per_ton = excelreader.getESW_Euro();
            stålverk_faktor = (stålverk_euro_per_ton * valutakurs) / 1000;
        } else if (ämnesrör && s.length == 2 && stålverk_combobox.getSelectedItem() == "V&M") {
            ArrayList<String> YD = excelreader.YD_VM;
            ArrayList<String> ID = excelreader.ID_VM;
            ArrayList<String> KGM = excelreader.KGM_VM;

            double dimensionen = Double.parseDouble(s[0]);
            double godstjockleken = Double.parseDouble(s[1]);

            double[] YDS = new double[YD.size()];
            double[] IDS = new double[ID.size()];
            double[] KGMS = new double[KGM.size()];

            int m = 0;
            int n = 0;
            int o = 0;

            for (String yd : YD) {
                YDS[m++] = Double.parseDouble(yd);
            }

            for (String id : ID) {
                IDS[n++] = Double.parseDouble(id);
            }

            for (String kgm : KGM) {
                KGMS[o++] = Double.parseDouble(kgm);
            }

            for (int i = 0; i < YDS.length; i++) {
                double ytter_dimension = YDS[i];
                double inner_dimension = IDS[i];
                if (närmsta_värde(YDS, dimensionen) == ytter_dimension && närmsta_värde(IDS, godstjockleken) == inner_dimension) {
                    material_form = 1;
                    vikt_per_meter = KGMS[i];
                    double godstjocklek_ämnesrör = (ytter_dimension - inner_dimension) / 2;
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(Double.toString(godstjocklek_ämnesrör));
                    } else {
                        godstjocklek_text.setText("0");
                    }
                }
            }

            if (Double.parseDouble(s[0]) >= 60) {
                stålverk_euro_per_ton = excelreader.getVM_Euro3();
            } else if (Double.parseDouble(s[0]) < 60) {
                stålverk_euro_per_ton = excelreader.getVM_Euro2();
            }

            stålverk_faktor = (stålverk_euro_per_ton * valutakurs) / 1000;
        } else if (ämnesrör && s.length == 2 && stålverk_combobox.getSelectedItem() == "BENTELER") {
            ArrayList<String> YD = excelreader.YD_BEN;
            ArrayList<String> ID = excelreader.ID_BEN;
            ArrayList<String> KGM = excelreader.KGM_BEN;

            double dimensionen = Double.parseDouble(s[0]);
            double godstjockleken = Double.parseDouble(s[1]);

            double[] YDS = new double[YD.size()];
            double[] IDS = new double[ID.size()];
            double[] KGMS = new double[KGM.size()];

            int m = 0;
            int n = 0;
            int o = 0;

            for (String yd : YD) {
                YDS[m++] = Double.parseDouble(yd);
            }

            for (String id : ID) {
                IDS[n++] = Double.parseDouble(id);
            }

            for (String kgm : KGM) {
                KGMS[o++] = Double.parseDouble(kgm);
            }

            for (int i = 0; i < YDS.length; i++) {
                double ytter_dimension = YDS[i];
                double inner_dimension = IDS[i];
                if (närmsta_värde(YDS, dimensionen) == ytter_dimension && närmsta_värde(IDS, godstjockleken) == inner_dimension) {
                    material_form = 1;
                    vikt_per_meter = KGMS[i];
                    double godstjocklek_ämnesrör = (ytter_dimension - inner_dimension) / 2;
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(Double.toString(godstjocklek_ämnesrör));
                    } else {
                        godstjocklek_text.setText("0");
                    }
                }
            }

            stålverk_euro_per_ton = excelreader.getBEN_Euro();
            stålverk_faktor = (stålverk_euro_per_ton * valutakurs) / 1000 + 0.70;
        } else if (ämnesrör && s.length == 2 && stålverk_combobox.getSelectedItem() == "TMK") {
            ArrayList<String> YD = excelreader.YD_TMK;
            ArrayList<String> ID = excelreader.ID_TMK;
            ArrayList<String> KGM = excelreader.KGM_TMK;

            double dimensionen = Double.parseDouble(s[0]);
            double godstjockleken = Double.parseDouble(s[1]);

            double[] YDS = new double[YD.size()];
            double[] IDS = new double[ID.size()];
            double[] KGMS = new double[KGM.size()];

            int m = 0;
            int n = 0;
            int o = 0;

            for (String yd : YD) {
                YDS[m++] = Double.parseDouble(yd);
            }

            for (String id : ID) {
                IDS[n++] = Double.parseDouble(id);
            }

            for (String kgm : KGM) {
                KGMS[o++] = Double.parseDouble(kgm);
            }

            for (int i = 0; i < YDS.length; i++) {
                double ytter_dimension = YDS[i];
                double inner_dimension = IDS[i];
                if (närmsta_värde(YDS, dimensionen) == ytter_dimension && närmsta_värde(IDS, godstjockleken) == inner_dimension) {
                    material_form = 1;
                    vikt_per_meter = KGMS[i];
                    double godstjocklek_ämnesrör = (ytter_dimension - inner_dimension) / 2;
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(Double.toString(godstjocklek_ämnesrör));
                    } else {
                        godstjocklek_text.setText("0");
                    }
                }
            }

            stålverk_euro_per_ton = excelreader.getTMK_Assel_Euro();
            stålverk_faktor = (stålverk_euro_per_ton * valutakurs) / 1000;
        } else if (ämnesrör && s.length == 2 && stålverk_combobox.getSelectedItem() == "HUBEI") {
            ArrayList<String> YD = excelreader.YD_HUB;
            ArrayList<String> ID = excelreader.ID_HUB;
            ArrayList<String> KGM = excelreader.KGM_HUB;

            double dimensionen = Double.parseDouble(s[0]);
            double godstjockleken = Double.parseDouble(s[1]);

            double[] YDS = new double[YD.size()];
            double[] IDS = new double[ID.size()];
            double[] KGMS = new double[KGM.size()];

            int m = 0;
            int n = 0;
            int o = 0;

            for (String yd : YD) {
                YDS[m++] = Double.parseDouble(yd);
            }

            for (String id : ID) {
                IDS[n++] = Double.parseDouble(id);
            }

            for (String kgm : KGM) {
                KGMS[o++] = Double.parseDouble(kgm);
            }

            for (int i = 0; i < YDS.length; i++) {
                double ytter_dimension = YDS[i];
                double inner_dimension = IDS[i];
                if (närmsta_värde(YDS, dimensionen) == ytter_dimension && närmsta_värde(IDS, godstjockleken) == inner_dimension) {
                    material_form = 1;
                    vikt_per_meter = KGMS[i];
                    double godstjocklek_ämnesrör = (ytter_dimension - inner_dimension) / 2;
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(Double.toString(godstjocklek_ämnesrör));
                    } else {
                        godstjocklek_text.setText("0");
                    }
                }
            }

            stålverk_euro_per_ton = excelreader.getHUB_Euro();
            stålverk_faktor = (stålverk_euro_per_ton * valutakurs) / 1000 + 0.60;
        } else if (ämnesrör && s.length == 2 && stålverk_combobox.getSelectedItem() == "OVAKO") {
            ArrayList<String> YD = excelreader.YD_OVA;
            ArrayList<String> ID = excelreader.ID_OVA;
            ArrayList<String> KGM = excelreader.KGM_OVA;

            double dimensionen = Double.parseDouble(s[0]);
            double godstjockleken = Double.parseDouble(s[1]);

            double[] YDS = new double[YD.size()];
            double[] IDS = new double[ID.size()];
            double[] KGMS = new double[KGM.size()];

            int m = 0;
            int n = 0;
            int o = 0;

            for (String yd : YD) {
                YDS[m++] = Double.parseDouble(yd);
            }

            for (String id : ID) {
                IDS[n++] = Double.parseDouble(id);
            }

            for (String kgm : KGM) {
                KGMS[o++] = Double.parseDouble(kgm);
            }

            for (int i = 0; i < YDS.length; i++) {
                double ytter_dimension = YDS[i];
                double inner_dimension = IDS[i];
                if (närmsta_värde(YDS, dimensionen) == ytter_dimension && närmsta_värde(IDS, godstjockleken) == inner_dimension) {
                    material_form = 1;
                    vikt_per_meter = KGMS[i];
                    double godstjocklek_ämnesrör = (ytter_dimension - inner_dimension) / 2;
                    if (är_nummer(godstjocklek_text.getText())) {
                        godstjocklek_text.setText(Double.toString(godstjocklek_ämnesrör));
                    } else {
                        godstjocklek_text.setText("0");
                    }
                }
            }

            stålverk_euro_per_ton = excelreader.getOVA_Euro();
            stålverk_faktor = (stålverk_euro_per_ton * valutakurs) / 1000 + 0.70;
        } else {
            stålverk_faktor = 0;
            vikt_per_meter = 0;
            return;
        }

        if (!"".equals(diameter_text.getText())) {
            rör_diameter = Double.parseDouble(diameter_text.getText());
        }

        if (!antal_field.getText().isEmpty()) {
            antal = Double.parseDouble(antal_field.getText());
            antal_snitt_text.setText(format.format(antal));
        }

        if (!längd_field.getText().isEmpty()) {
            längd = Double.parseDouble(längd_field.getText());
            kaplängd_text.setText(format.format(längd));
        }
    }

    // Huvudmetod för uträkning utav alla parametrar i programmet. (Körs vid varje uppdatering av programmet)
    private void uträkning() {
        ersätt_tecken();
        kolla_gerkap();
        kolla_dimension();

        if (s != null && s.length > 1 && !euroPerTonSjälv && stålkvalitet_combobox.getSelectedItem().equals("SVKCKR")) {
            kolla_kckr();
        }

        if (s != null && s.length > 1 && !euroPerTonSjälv && stålkvalitet_combobox.getSelectedItem().equals("SVKKR")) {
            kolla_kkr();
        }

        räkna_ut_vikten();

        //GENOMFÖR KALKYL
        material_form = 1;
        tolerans = 2;
        material = material_kvalitet_combobox.getSelectedIndex() + 1;
        kap_typ = bandtyp_combobox.getSelectedIndex() + 1;
        H23 = (tolerans < 2) ? 0 : 1;
        H24 = (material < 2) ? 0 : 1;
        //BANDKOSTNAD
        inställningar_bandkostnad = Double.parseDouble(bandkostnad.getText());
        //BANDETS LIVSLÄNGD
        inställningar_bandets_livslängd = Double.parseDouble(bandets_livslängd.getText());
        //UPP & NERGÅNG I MM / SEC
        inställningar_upp_nergång_mm_sekund = Double.parseDouble(upp_nergång_mm_sekund.getText());
        //RÖRFRAMMATNING
        inställningar_rörframmatning = Double.parseDouble(rörframmatning.getText());
        //STÄLL TID
        inställningar_ställtid = Double.parseDouble(ställ_tid.getText());
        //BANDBYTE
        inställningar_bandbyte = Double.parseDouble(bandbyte.getText());
        //VÄLJ TOLERANSVIDD
        // double Förinställningar_G7 = 0;
        //TOLERANSVIDD ÖVER 1MM
        inställningar_toleransvidd_över_1mm = Double.parseDouble(toleransvidd_över_1mm.getText());
        //TOLERANSVIDD 1MM ELLER MINDRE
        inställningar_toleransvidd_1mm_mindre = inställningar_toleransvidd_över_1mm * 1.2;
        //LÄTTA MATERIAL
        inställningar_lätta_material = Double.parseDouble(lätta_material.getText());
        //HÅRDA MATERIAL
        inställningar_hårda_material = Double.parseDouble(svåra_material.getText());
        //VANLIG

        //HYDRAULIK
        inställningar_hydraulik = Double.parseDouble(hydraulik.getText()) / 100;
        //SERIEKAPNING KORTA BITAR(MIN. 50ST, MAX LÄNGD 150MM)
        inställningar_seriekapning = Double.parseDouble(seriekapning.getText()) / 100;

        //GODSTJOCKLEK
        if (är_nummer(godstjocklek_text.getText())) {
            godstjocklek_kalkyl = Double.parseDouble(godstjocklek_text.getText());
        } else {
            godstjocklek_kalkyl = 0;
        }
        //ANTAL SNITT
        if (är_nummer(antal_snitt_text.getText())) {
            antal_snitt = Double.parseDouble(antal_snitt_text.getText());
        } else {
            antal_snitt = 0;
        }
        //KAPLÄNGD
        if (är_nummer(kaplängd_text.getText())) {
            kaplängd = Double.parseDouble(kaplängd_text.getText());
        } else {
            kaplängd = 0;
        }
        
        //J18
        switch ((int) material) {
            case 1:
                matning = 0;
                break;
            case 2:
                matning = inställningar_lätta_material;
                break;
            case 3:
                matning = inställningar_hårda_material;
                break;
            default:
                break;
        }

        //J28
        if (material_form == 1) {
            godstjocklek = godstjocklek_kalkyl;
        } else if (material_form == 2) {
            godstjocklek = rör_diameter / 2;
        }

        yta = (((rör_diameter / 10 / 2) * (rör_diameter / 10 / 2)) * 3.14) - ((((godstjocklek * 2) - rör_diameter) / 10 / 2) * (((godstjocklek * 2) - rör_diameter) / 10 / 2) * 3.14);
        diameter_ifyllt = (rör_diameter <= 1) ? 0 : 1;
        godstjocklek_ifyllt = (godstjocklek < 1) ? 0 : 1;
        antal_snitt_ifyllt = (antal_snitt < 1) ? 0 : 1;
        kaplängd_ifyllt = (kaplängd < 10) ? 0 : 1;
        summa_alla_parametrar = kaplängd_ifyllt + antal_snitt_ifyllt + godstjocklek_ifyllt + diameter_ifyllt;
        H25 = H24 + H23 + material_form + summa_alla_parametrar;
        ställ = inställningar_ställtid;
        verktygsbyte = inställningar_bandbyte;
        grund_timkostnad = 0;
        
        switch ((int) tolerans) {
            case 1:
                grund_timkostnad = 0;
                break;
            case 2:
                grund_timkostnad = inställningar_toleransvidd_över_1mm;
                break;
            case 3:
                grund_timkostnad = inställningar_toleransvidd_1mm_mindre;
                break;
            default:
                break;
        }

        tillägg_avdrag_homogent_etc = 0;
        //C27
        switch ((int) kap_typ) {
            case 1:
                tillägg_avdrag_homogent_etc = 1;
                break;
            case 2:
                tillägg_avdrag_homogent_etc = inställningar_hydraulik;
                break;
            case 3:
                tillägg_avdrag_homogent_etc = inställningar_seriekapning;
                break;
            default:
                break;
        }

        timkostnad_till_kalkyl = grund_timkostnad * tillägg_avdrag_homogent_etc;
        livslängd = inställningar_bandets_livslängd;
        bandets_pris = inställningar_bandkostnad;
        total_yta = (antal_snitt * yta) / 10000;
        antal_band = total_yta / livslängd;
        frammatningstid_mm_sec = inställningar_rörframmatning;
        upp_ner_rörelser = rör_diameter / inställningar_upp_nergång_mm_sekund;
        kaptid = (H25 <= 6) ? 0 : (rör_diameter / matning);
        frammatningstid_min = ((kaplängd / frammatningstid_mm_sec) / 60 + (upp_ner_rörelser / 60));

        if (H25 <= 6) {
            totaltid = 0;
            cykeltid = 0;
            kostnad_per_bit = 0;
        } else {
            totaltid = ((kaptid + frammatningstid_min) * antal_snitt + ställ + (antal_band * verktygsbyte)) / 60;
            cykeltid = (totaltid / antal_snitt) * 60;
            if (gerkap_faktor <= 0 && !ämnesrör) {
                kostnad_per_bit = (((((((verktygsbyte * antal_band) + ställ) / antal_snitt) + kaptid + frammatningstid_min) / 60) * timkostnad_till_kalkyl) + ((antal_band / antal_snitt) * bandets_pris));
            } else if (gerkap_faktor > 0 && !ämnesrör) {
                kostnad_per_bit = (((((((verktygsbyte * antal_band) + ställ) / antal_snitt) + kaptid + frammatningstid_min) / 60) * timkostnad_till_kalkyl) + ((antal_band / antal_snitt) * bandets_pris)) * gerkap_faktor;
            } else if (gerkap_faktor <= 0 && ämnesrör) {
                kostnad_per_bit = ((vikt_per_styck) * stålverk_faktor);
            } else if (gerkap_faktor > 0 && ämnesrör) {
                kostnad_per_bit = ((vikt_per_styck) * stålverk_faktor) * gerkap_faktor;
            }

        }

        DecimalFormat df = new DecimalFormat("##.##");

        cykeltid_text1.setText(df.format(cykeltid).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " min");
        totaltid_text1.setText(df.format(totaltid).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " h");

        switch (bandtyp_combobox.getSelectedIndex()) {
            case 1:
                kostnad_per_bit_text1.setText(df.format(kostnad_per_bit).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");
                break;
            case 2:
                kostnad_per_bit_text1.setText(df.format(kostnad_per_bit).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");
                break;
            default:
                kostnad_per_bit_text1.setText(df.format(kostnad_per_bit).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");
                break;
        }

        //ÅTERANSKAFFNINGS VÄRDE
        if (!spill_field.getText().isEmpty()) {
            get_spill = Double.parseDouble(spill_field.getText());
            spill = get_spill / 100;
        }

        if (!valutakurs_field.getText().isEmpty()) {
            valutakurs = Double.parseDouble(valutakurs_field.getText());
        }

        if (!euroPerTonSjälv) {
            if (ämnesrör) {
                euro_per_ton = stålverk_euro_per_ton;
                euro_per_ton_field.setText(format.format(stålverk_euro_per_ton));
            }
        } else if (euroPerTonSjälv) {
            if (!"".equals(euro_per_ton_field.getText())) {
                euro_per_ton = Double.parseDouble(euro_per_ton_field.getText());
            }
        }

        återanskaffningsvärde = valutakurs * euro_per_ton / 1000;

        //TOTALVIKT
        if(!kilo_per_meter_panel.isVisible()) {
            vikt_per_styck = vikt_per_meter * längd / 1000;
        } else {
            if (är_nummer(kilo_per_meter_field.getText())) {
                vikt_per_styck = Double.parseDouble(kilo_per_meter_field.getText()) * längd / 1000;
            } else {
                vikt_per_styck = 0;
            }
        }
        total_vikt = vikt_per_styck * antal;
        //VIKT PER STYCK

        //PRODUKTIONS KOSTNADER
        if (!ställkostnad_field.getText().isEmpty()) {
            ställkostnad = Double.parseDouble(ställkostnad_field.getText());
        }

        produktions_kostnad_styck = kostnad_per_bit;
        total_produktionskostnad_styck = (ställkostnad / antal) + produktions_kostnad_styck;
        total_produktionskostnad = kostnad_per_bit * antal + ställkostnad;

        //ÖVRIGA VARIABLAR
        totalt_återanskaffningsvärde = återanskaffningsvärde * total_vikt;
        återanskaffningsvärde_meter = återanskaffningsvärde * vikt_per_meter;
        styck_återanskaffningsvärde = totalt_återanskaffningsvärde / antal;

        //FRAKTKOSTNAD
        if (!"".equals(fraktkostnad_field.getText())) {
            fraktkostnad_kilo = Double.parseDouble(fraktkostnad_field.getText());
        } else {
            fraktkostnad_kilo = 0;
        }
        if (!"".equals(fraktkostnad_sicam_field.getText())) {
            fraktkostnad_lagerhållare = Double.parseDouble(fraktkostnad_sicam_field.getText());
        } else {
            fraktkostnad_lagerhållare = 0;
        }
        fraktkostnad_styck = fraktkostnad_kilo * vikt_per_styck + fraktkostnad_lagerhållare * vikt_per_styck;
        fraktkostnad_totalt = fraktkostnad_styck * antal;

        //KOSTNAD PER STYCK INKLUSIVE FRAKT + PRODUKTIONS KOSTNAD
        kostnad_per_styck_exklusive = vikt_per_styck * återanskaffningsvärde * (1 + spill);
        total_kostnad_leverans = antal * kostnad_per_styck_exklusive;
        kostnad_per_styck_inklusive = kostnad_per_styck_exklusive + fraktkostnad_styck + total_produktionskostnad_styck;
        kostnad_leverans_inklusive = kostnad_per_styck_inklusive * antal;

        if (!"".equals(påslag_field.getText())) {
            påslag = Double.parseDouble(påslag_field.getText());
        } else {
            påslag = 0;
        }

        //PRIS FÖR KUND
        kostnad_per_styck_kund = styck_återanskaffningsvärde + produktions_kostnad_styck + fraktkostnad_styck;
        pris_kund_exklusive = kostnad_per_styck_exklusive + (kostnad_per_styck_exklusive + påslag);
        pris_kund_inklusive = pris_kund_exklusive + fraktkostnad_styck + total_produktionskostnad_styck;
        pris_kund_leverans = pris_kund_inklusive * antal;
        pris_kund_kilo_exklusive = (vikt_per_styck * återanskaffningsvärde / vikt_per_styck) + påslag;
        pris_kund_kilo_inklusive = pris_kund_inklusive / vikt_per_styck;

        marginal_styck = pris_kund_inklusive - kostnad_per_styck_inklusive;
        marginal_leverans = marginal_styck * antal;
        marginal_täckningsgrad = (marginal_styck / pris_kund_inklusive) * 100;

        //MARGINALER
        marginal_styck_field.setText(format.format(marginal_styck).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");
        marginal_total_field.setText(format.format(marginal_leverans).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");
        täckningsgrad_field.setText(format.format(marginal_täckningsgrad).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + "%");
        viktstyck_field.setText(format.format(vikt_per_styck).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kg");
        totalvikt_field.setText(format.format(total_vikt).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kg");
        prisstyck_field.setText(format.format(pris_kund_inklusive).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");
        pristotalt_field.setText(format.format(pris_kund_leverans).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");

        if (!kvantitet_år_field.getText().isEmpty()) {
            double marginal_år = marginal_styck * Double.parseDouble(kvantitet_år_field.getText().replaceAll("[^\\d]", ""));
            double omsättning_år = pris_kund_inklusive * Double.parseDouble(kvantitet_år_field.getText().replaceAll("[^\\d]", ""));
            double ton_år = (total_vikt * Double.parseDouble(kvantitet_år_field.getText().replaceAll("[^\\d]", ""))) / 100;
            marginal_år_field.setText(format.format(marginal_år).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");
            omsättning_år_field.setText(format.format(omsättning_år).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " kr");
            ton_år_field.setText(format.format(ton_år).replaceAll("[^!-~\\u20000-\\uFE1F\\uFF00-\\uFFEF]", "0") + " ton");
        }

        if (tidigare_model.getRowCount() > 0 && tidigare_table.isRowSelected(tidigare_table.getSelectedRow())) {
            double tidigare_pris = Double.parseDouble(tidigare_table.getValueAt(tidigare_table.getSelectedRow(), 5).toString());
            double tidigare_antal = Double.parseDouble(tidigare_table.getValueAt(tidigare_table.getSelectedRow(), 3).toString());
            förändring_field_procent.setText(format.format(((pris_kund_leverans / antal) / (tidigare_pris / tidigare_antal) - 1) * 100) + "%");
            förändring_i_kr_field.setText(format.format(((pris_kund_leverans / antal) - (tidigare_pris / tidigare_antal))) + " kr");
        }
    }

    // Huvudmetod för att starta programmet.
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Program.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Program().setVisible(true);
        });
    }

    private void ladda_offerter() {
        if(internet_uppkoppling()) {
            /* TESTAR DATABASEN LADDA OFFERT */
            try {
                functions.hämtaOfferter(db, nuvarande_distrikt, kund, offertNamn);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_ladda_offerter", ex);
            }
            /*********************************/
            if (!laddatOffert) {
                if (offertNamn.size() > 0) {
                    övergång_icon.setVisible(true);
                    info_label.setVisible(true);
                    offertNamn.forEach((offert) -> {
                        try {
                            välj_offert_combobox.addItem(offert + " " + functions.hämtaOffertDatum(db, nuvarande_distrikt, kund, offert));
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
            
            offertNamn.clear();
        }
    }

    private void kolla_kckr() {
        // SVKCKR
        if (stålkvalitet_combobox.getSelectedItem().equals("SVKCKR") && stålverk_combobox.getSelectedItem().equals("ESW")) {
            for (int i = 0; i < excelreader.KCKR_DIMENSION.size(); i++) {
                if (närmsta_värde(excelreader.KCKR_DIMENSION, Double.parseDouble(s[0])) == excelreader.KCKR_DIMENSION.get(i) && närmsta_värde(excelreader.KCKR_GODSTJOCKLEK, Double.parseDouble(s[1])) == excelreader.KCKR_GODSTJOCKLEK.get(i)) {
                    euro_per_ton_field.setText(Double.toString(excelreader.KCKR_EURO_TON_ESW.get(i)));
                }
            }
        } else if (stålkvalitet_combobox.getSelectedItem().equals("SVKCKR") && stålverk_combobox.getSelectedItem().equals("HUBEI")) {
            for (int i = 0; i < excelreader.KCKR_DIMENSION.size(); i++) {
                if (närmsta_värde(excelreader.KCKR_DIMENSION, Double.parseDouble(s[0])) == excelreader.KCKR_DIMENSION.get(i) && närmsta_värde(excelreader.KCKR_GODSTJOCKLEK, Double.parseDouble(s[1])) == excelreader.KCKR_GODSTJOCKLEK.get(i)) {
                    euro_per_ton_field.setText(Double.toString(excelreader.KCKR_EURO_TON_HUBEI.get(i)));
                }
            }
        } else if (stålkvalitet_combobox.getSelectedItem().equals("SVKCKR") && stålverk_combobox.getSelectedItem().equals("ALCH.BAT")) {
            for (int i = 0; i < excelreader.KCKR_DIMENSION.size(); i++) {
                if (närmsta_värde(excelreader.KCKR_DIMENSION, Double.parseDouble(s[0])) == excelreader.KCKR_DIMENSION.get(i) && närmsta_värde(excelreader.KCKR_GODSTJOCKLEK, Double.parseDouble(s[1])) == excelreader.KCKR_GODSTJOCKLEK.get(i)) {
                    euro_per_ton_field.setText(Double.toString(excelreader.KCKR_EURO_TON_ALCH_BAT.get(i)));
                }
            }
        } else if (stålkvalitet_combobox.getSelectedItem().equals("SVKCKR") && stålverk_combobox.getSelectedItem().equals("MORAVIA")) {
            for (int i = 0; i < excelreader.KCKR_DIMENSION.size(); i++) {
                if (närmsta_värde(excelreader.KCKR_DIMENSION, Double.parseDouble(s[0])) == excelreader.KCKR_DIMENSION.get(i) && närmsta_värde(excelreader.KCKR_GODSTJOCKLEK, Double.parseDouble(s[1])) == excelreader.KCKR_GODSTJOCKLEK.get(i)) {
                    euro_per_ton_field.setText(Double.toString(excelreader.KCKR_EURO_TON_MORAVIA.get(i)));
                }
            }
        } else if (stålkvalitet_combobox.getSelectedItem().equals("SVKCKR") && stålverk_combobox.getSelectedItem().equals("PIPEX")) {
            for (int i = 0; i < excelreader.KCKR_DIMENSION.size(); i++) {
                if (närmsta_värde(excelreader.KCKR_DIMENSION, Double.parseDouble(s[0])) == excelreader.KCKR_DIMENSION.get(i) && närmsta_värde(excelreader.KCKR_GODSTJOCKLEK, Double.parseDouble(s[1])) == excelreader.KCKR_GODSTJOCKLEK.get(i)) {
                    euro_per_ton_field.setText(Double.toString(excelreader.KCKR_EURO_TON_PIPEX.get(i)));
                }
            }
        } else if (stålkvalitet_combobox.getSelectedItem().equals("SVKCKR") && stålverk_combobox.getSelectedItem().equals("PT")) {
            for (int i = 0; i < excelreader.KCKR_DIMENSION.size(); i++) {
                if (närmsta_värde(excelreader.KCKR_DIMENSION, Double.parseDouble(s[0])) == excelreader.KCKR_DIMENSION.get(i) && närmsta_värde(excelreader.KCKR_GODSTJOCKLEK, Double.parseDouble(s[1])) == excelreader.KCKR_GODSTJOCKLEK.get(i)) {
                    euro_per_ton_field.setText(Double.toString(excelreader.KCKR_EURO_TON_PT.get(i)));
                }
            }
        }
    }

    private void kolla_kkr() {
        // SVKKR
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVKKR") && stålverk_combobox.getSelectedItem().equals("ARVEDI")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.KKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.KKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.KKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.KKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.KKR_EURO_TON_ARVEDI.get(i)));
                    }
                }
            }
        }
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVKKR") && stålverk_combobox.getSelectedItem().equals("ALESSIO")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.KKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.KKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.KKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.KKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.KKR_EURO_TON_ALESSIO.get(i)));
                    }
                }
            }
        }
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVKKR") && stålverk_combobox.getSelectedItem().equals("CORINTH")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.KKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.KKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.KKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.KKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.KKR_EURO_TON_CORINTH.get(i)));
                    }
                }
            }
        }
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVKKR") && stålverk_combobox.getSelectedItem().equals("ILVA")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.KKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.KKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.KKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.KKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.KKR_EURO_TON_ILVA.get(i)));
                    }
                }
            }
        }
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVKKR") && stålverk_combobox.getSelectedItem().equals("MARCEGAGLIA")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.KKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.KKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.KKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.KKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.KKR_EURO_TON_MARCEGAGLIA.get(i)));
                    }
                }
            }
        }
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVKKR") && stålverk_combobox.getSelectedItem().equals("SIDERALBA")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.KKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.KKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.KKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.KKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.KKR_EURO_TON_SIDERALBA.get(i)));
                    }
                }
            }
        }
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVKKR") && stålverk_combobox.getSelectedItem().equals("TATA")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.KKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.KKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.KKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.KKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.KKR_EURO_TON_TATA.get(i)));
                    }
                }
            }
        }
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVKKR") && stålverk_combobox.getSelectedItem().equals("TATA NL")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.KKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.KKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.KKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.KKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.KKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.KKR_EURO_TON_TATA_NL.get(i)));
                    }
                }
            }
        }
    }

    private void kolla_vkr() {
        if (s.length > 2) {
            if (stålkvalitet_combobox.getSelectedItem().equals("SVVKR") && stålverk_combobox.getSelectedItem().equals("ARVEDI")) {
                for (int i = 0; i < excelreader.KKR_DIMENSION.size(); i++) {
                    if (excelreader.VKR_DIMENSION.get(i).equals(hitta_närmaste(excelreader.VKR_DIMENSION, Double.parseDouble(s[0])))
                            && excelreader.VKR_DIMENSION_TVÅ.get(i).equals(hitta_närmaste(excelreader.VKR_DIMENSION_TVÅ, Double.parseDouble(s[1])))
                            && excelreader.VKR_GODSTJOCKLEK.get(i).equals(hitta_närmaste(excelreader.VKR_GODSTJOCKLEK, Double.parseDouble(s[2])))) {
                        euro_per_ton_field.setText(Double.toString(excelreader.VKR_EURO_TON_ARVEDI.get(i)));
                    }
                }
            }
        }
    }

    public void töm_fält() {
        fields.stream().filter((field) -> (!field.getName().equals("datum_field") && !field.getName().equals("säljare_field"))).forEachOrdered((field) -> {
            field.setText("0");
        });
    }

    private void ladda_färger() {
        if(internet_uppkoppling()) {
            ladda_progressbar.setMaximum(panels.size());
            /* TESTAR DATABASEN */
            new Thread(() -> {
                panels.forEach((panel) -> {
                    try {
                        functions.hämtaFärger(db, säljare_field.getText(), panel, ladda_progressbar);
                        ladda_progressbar.setValue(ladda_progressbar.getValue() + 1);
                        loading_text_field.setText("Färglägger: " + panel.getName());
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                        loggare.log_info(ex.getMessage(), "Program_ladda_färger", ex);
                    }
                });
            }).start();
            /********************/
        }
    }

    //TÖMMER TEXTFÄLT PÅ INNEHÅLL
    public void reset(JTextField textField) {
        if (textField.getText().matches("0")) {
            textField.setText("");
        }
    }

    public void set0(JTextField textField) {
        if (textField.getText().matches("")) {
            textField.setText("0");
        }
    }

    //ÅTERSTÄLLER VÄRDET I TABELLER GENOM ATT SÄTTA ANTAL RADER TILL 0
    public void reset(DefaultTableModel table_model) {
        table_model.setRowCount(0);
    }

    public void spara_inställningar() {
        if(internet_uppkoppling()) {
            /* TESTAR DATABASEN */
            HashMap<String, String> inställningar = new HashMap<>();
            inställningar.put("Bandkostnad", bandkostnad.getText());
            inställningar.put("BandetsLivslängd", bandets_livslängd.getText());
            inställningar.put("UppNergångMmSekund", upp_nergång_mm_sekund.getText());
            inställningar.put("RörFrammatning", rörframmatning.getText());
            inställningar.put("StällTid", ställ_tid.getText());
            inställningar.put("Bandbyte", bandbyte.getText());
            inställningar.put("Toleransvidd", toleransvidd.getText());
            inställningar.put("ToleransviddÖver1", toleransvidd_över_1mm.getText());
            inställningar.put("ToleransviddUnder1", toleransvidd_under_1mm.getText());
            inställningar.put("VäljMaterial", välj_material.getText());
            inställningar.put("LättaMaterial", lätta_material.getText());
            inställningar.put("SvåraMaterial", svåra_material.getText());
            inställningar.put("MaxLängd", max_längd.getText());
            inställningar.put("MinAntal", min_antal.getText());
            inställningar.put("Vanlig", vanlig.getText());
            inställningar.put("Hydraulik", hydraulik.getText());
            inställningar.put("Seriekapning", seriekapning.getText());
            inställningar.put("Lösenord", kryptera(lösenordet_field.getText()));
            try {
                functions.programInställningar(db, inställningar);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_spara_inställningar", ex);
            }
            /*******************/
        }
    }

    public void ladda_inställningar() {
        if(internet_uppkoppling()) {
            /* TESTAR DATABASEN */
            try {
                functions.laddaProgramInställningar(db);
                if (functions.kollaInställningar(db)) {
                    bandkostnad.setText(functions.getInst().getBandkostnad());
                    bandets_livslängd.setText(functions.getInst().getBandetsLivslängd());
                    upp_nergång_mm_sekund.setText(functions.getInst().getUppNergångMmSekund());
                    rörframmatning.setText(functions.getInst().getRörFrammatning());
                    ställ_tid.setText(functions.getInst().getStällTid());
                    bandbyte.setText(functions.getInst().getBandbyte());
                    toleransvidd.setText(functions.getInst().getToleransvidd());
                    toleransvidd_över_1mm.setText(functions.getInst().getToleransviddÖver1());
                    toleransvidd_under_1mm.setText(functions.getInst().getToleransviddUnder1());
                    välj_material.setText(functions.getInst().getVäljMaterial());
                    lätta_material.setText(functions.getInst().getLättaMaterial());
                    svåra_material.setText(functions.getInst().getSvåraMaterial());
                    max_längd.setText(functions.getInst().getMaxLängd());
                    min_antal.setText(functions.getInst().getMinAntal());
                    vanlig.setText(functions.getInst().getVanlig());
                    hydraulik.setText(functions.getInst().getHydraulik());
                    seriekapning.setText(functions.getInst().getSeriekapning());
                    lösenordet_field.setText(dekryptera(functions.getInst().getLösenord()));
                    lösenordet = lösenordet_field.getText();
                }
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_ladda_inställningar", ex);
            }
            /*******************/
        }
    }

    public void hämta_ett_artikelnummer(String artnr) {
        if(internet_uppkoppling()) {
            try {
                /* TESTAR DATABASEN */
                if (functions.hittaArtikelnummer(db, nuvarande_distrikt, kund, artnr)) {
                    functions.hämtaArtikel(db, nuvarande_distrikt, kund, artnr);
                    bandtyp_combobox.setSelectedIndex(Integer.parseInt(functions.getArtnr().getBandtyp()));
                    material_kvalitet_combobox.setSelectedIndex(Integer.parseInt(functions.getArtnr().getMaterial()));
                    gerkap_combobox.setSelectedIndex(Integer.parseInt(functions.getArtnr().getGerkap()));
                    valutakurs_field.setText(functions.getArtnr().getValutakurs());
                    stålkvalitet_combobox.setSelectedItem(functions.getArtnr().getStålkvalitet());
                    stålverk_combobox.setSelectedItem(functions.getArtnr().getStålverk());
                    säljare_field.setText(functions.getArtnr().getSäljare());
                    artikelnummer_field.setText(functions.getArtnr().getArtikelnummer());
                    bearbetning_field.setText(functions.getArtnr().getBearbetning());
                    dimension_field.setText(functions.getArtnr().getDimension());
                    antal_field.setText(functions.getArtnr().getAntal());
                    längd_field.setText(functions.getArtnr().getLängd());
                    längdtolerans_field.setText(functions.getArtnr().getLängdtolerans());
                    euro_per_ton_field.setText(functions.getArtnr().getEuroPerTon());
                    ställkostnad_field.setText(functions.getArtnr().getStällkostnad());
                    ursprungslängd_field.setText(functions.getArtnr().getUrsprungslängd());
                    påslag_field.setText(functions.getArtnr().getPåslag());
                    fraktkostnad_field.setText(functions.getArtnr().getFraktkostnad());
                    fraktkostnad_sicam_field.setText(functions.getArtnr().getFraktkostnadSicam());
                    spill_field.setText(functions.getArtnr().getSpill());
                }
                /*******************/
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_hämta_ett_artikelnummer", ex);
            }
        }
    }

    public String ämnesrör(String ytter_diamater, String hålet) {
        double godstjockleken;

        godstjockleken = (Double.parseDouble(ytter_diamater) - Double.parseDouble(hålet)) / 2;

        return Double.toString(godstjockleken);
    }

    public void offert(String offert_name) throws IOException {
        File skapa_mapp = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "//Offerter//" + kund + "//");

        if (!skapa_mapp.exists()) {
            skapa_mapp.mkdirs();
        }

        String filename = Paths.get(".").toAbsolutePath().normalize().toString() + "//Offerter//" + kund + "//" + offert_name + ".pdf";
        final PDPage singlePage = new PDPage(PDRectangle.A4);
        final PDPage secondPage = new PDPage(PDRectangle.A4);
        final PDFont font = PDType1Font.HELVETICA;
        final int fontSize = 8;
        double totalbelopp = 0;
        double totalvikt = 0;

        String[] wrT;
        String string;
        int i = 0;

        boolean second_page = false;

        int y = 650;

        try (final PDDocument document = new PDDocument()) {
            if (!second_page) {
                try (
                    PDPageContentStream contentStream = new PDPageContentStream(document, singlePage)) {
                    //contentStream.drawImage(pdImage, 40, 700);

                    float yCordinate = singlePage.getCropBox().getUpperRightY() - 30;
                    float startX = singlePage.getCropBox().getLowerLeftX() + 30;
                    float endX = singlePage.getCropBox().getUpperRightX() - 30;
                    yCordinate -= 125;

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 35);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(30, 800);
                    contentStream.setNonStrokingColor(Color.BLUE);
                    contentStream.showText("SWEA");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.showText("SWEA RÖR & STÅL AB");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText("SWEA TUBES AND STEEL COMPANY");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(325, 800);
                    contentStream.showText("Offertnr / Kundnr");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(Integer.toString(offert_nummer));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(485, 800);
                    contentStream.showText("Offertdatum");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(datum_field.getText());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(30, 720);
                    contentStream.showText("Leveransvillkor");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(leveransvillkor_combobox.getSelectedItem().toString());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(325, 720);
                    contentStream.showText("Betalningsvillkor");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(betalningsvillkor_combobox.getSelectedItem().toString());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(30, 750);
                    contentStream.showText("Vår referens");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(säljare_field.getText());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(485, 720);
                    contentStream.showText("Er referens");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(referens_field.getText());
                    contentStream.endText();

                    contentStream.moveTo(startX, yCordinate);
                    contentStream.lineTo(endX, yCordinate);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    contentStream.setLeading(25.0f);
                    contentStream.newLineAtOffset(30, 670);
                    contentStream.showText("Art. nr                        Kategori          Dimension            Vikt styck             Antal           Längd          Tolerans            Pris styck                Totalt Pris");
                    yCordinate -= 25;
                    contentStream.endText();
                    contentStream.moveTo(startX, yCordinate);
                    contentStream.lineTo(endX, yCordinate);
                    contentStream.stroke();

                    for (i = 0; i < färdig_model.getRowCount(); i++) {
                        if (!second_page) {
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(30, 650 - i * 20);
                            contentStream.showText(färdig_model.getValueAt(i, 0).toString());
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(109, 650 - i * 20);
                            
                            switch (färdig_model.getValueAt(i, 1).toString()) {
                                case "SVVKR":
                                    contentStream.showText("VKR");
                                    contentStream.endText();
                                    break;
                                case "SVKKR":
                                    contentStream.showText("KKR");
                                    contentStream.endText();
                                    break;
                                case "SVVCKR":
                                    contentStream.showText("VCKR");
                                    contentStream.endText();
                                    break;
                                case "SVKCKR":
                                    contentStream.showText("KCKR");
                                    contentStream.endText();
                                    break;
                                case "SÖDIN":
                                    contentStream.showText("Varmvalsat sömlöst S355J2H");
                                    contentStream.endText();
                                    break;
                                case "SÖE470":
                                    contentStream.showText("Ämnesrör E470");
                                    contentStream.endText();
                                    break;
                                default:
                                    contentStream.showText(färdig_model.getValueAt(i, 1).toString());
                                    contentStream.endText();
                                    break;
                            }
                            
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(163, 650 - i * 20);
                            contentStream.showText(färdig_model.getValueAt(i, 2).toString());
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(230, 650 - i * 20);
                            contentStream.showText(färdig_model.getValueAt(i, 3).toString() + " kg");
                            totalvikt += Double.parseDouble(färdig_model.getValueAt(i, 3).toString().replaceAll("[^\\d.]", "")) * Double.parseDouble(färdig_model.getValueAt(i, 4).toString().replaceAll("[^\\d.]", ""));
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(293, 650 - i * 20);
                            contentStream.showText(färdig_model.getValueAt(i, 4).toString() + " st");
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(338, 650 - i * 20);
                            contentStream.showText(färdig_model.getValueAt(i, 5).toString() + " mm");
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(385, 650 - i * 20);
                            contentStream.showText(färdig_model.getValueAt(i, 6).toString() + " mm");
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(444, 650 - i * 20);
                            contentStream.showText(färdig_model.getValueAt(i, 7).toString() + " kr");
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.setFont(font, 8);
                            contentStream.newLineAtOffset(518, 650 - i * 20);
                            contentStream.showText(färdig_model.getValueAt(i, 8).toString() + " kr");
                            totalbelopp += Double.parseDouble(färdig_model.getValueAt(i, 8).toString().replaceAll("[^\\d.]", ""));
                            contentStream.endText();

                            y = y - i * 2;

                            if (y < 150) {
                                second_page = true;
                            }

                            if (i == färdig_model.getRowCount() - 1) {
                                contentStream.moveTo(startX, 650 - i * 20 - 8);
                                contentStream.lineTo(endX, 650 - i * 20 - 8);
                                contentStream.stroke();

                                contentStream.beginText();
                                contentStream.newLineAtOffset(325, 650 - i * 20 - 22);
                                contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                                contentStream.showText("TOTAL");
                                contentStream.endText();
                                contentStream.beginText();
                                contentStream.newLineAtOffset(365, 650 - i * 20 - 22);
                                contentStream.setFont(PDType1Font.COURIER, 10);
                                contentStream.showText(format.format(totalvikt) + " kg");
                                contentStream.endText();

                                contentStream.beginText();
                                contentStream.newLineAtOffset(450, 650 - i * 20 - 22);
                                contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                                contentStream.showText("TOTAL");
                                contentStream.endText();
                                contentStream.beginText();
                                contentStream.newLineAtOffset(490, 650 - i * 20 - 22);
                                contentStream.setFont(PDType1Font.COURIER, 10);
                                contentStream.showText(format.format(totalbelopp) + " kr");
                                contentStream.endText();

                                String text = offert_textarea.getText();
                                wrT = WordUtils.wrap(text, 200).split("\\r?\\n");

                                for (int j = 0; j < wrT.length; j++) {
                                    contentStream.beginText();
                                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                                    string = wrT[j];
                                    contentStream.newLineAtOffset(30, 650 - i * 20 - (j * 12) - 50);
                                    contentStream.showText(string);
                                    contentStream.endText();
                                }
                            }
                        } else {
                            break;
                        }
                    }
                    if (!second_page) {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.setLeading(10.5f);
                        contentStream.newLineAtOffset(30, 110);
                        contentStream.showText("Total exklusive extra kostnader:");
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.newLineAtOffset(170, 110);
                        contentStream.setFont(PDType1Font.HELVETICA, 8);
                        contentStream.showText(format.format(totalbelopp + Double.parseDouble(certifikat_textfield.getText()) + Double.parseDouble(transport_textfield.getText())) + " kr");
                        contentStream.endText();
                        
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.setLeading(10.5f);
                        contentStream.newLineAtOffset(400, 110);
                        contentStream.showText("Eventuellt emballage debiteras i efterhand.");
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.setLeading(10.5f);
                        contentStream.newLineAtOffset(30, 130);
                        contentStream.showText("Transport:");
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.newLineAtOffset(170, 130);
                        contentStream.setFont(PDType1Font.HELVETICA, 8);
                        contentStream.showText(transport_textfield.getText() + " kr");
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.setLeading(10.5f);
                        contentStream.newLineAtOffset(30, 150);
                        contentStream.showText("Certifikat:");
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.newLineAtOffset(170, 150);
                        contentStream.setFont(PDType1Font.HELVETICA, 8);
                        contentStream.showText(certifikat_textfield.getText() + " kr");
                        contentStream.endText();
                    }
                    contentStream.moveTo(startX, 100);
                    contentStream.lineTo(endX, 100);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(30, 85);
                    contentStream.showText("Adress");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("Tel: 035-22 15 90");
                    contentStream.newLine();
                    contentStream.showText("Sadelvägen 11");
                    contentStream.newLine();
                    contentStream.showText("302 62, Halmstad");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(147, 85);
                    contentStream.showText("Swea Rör AB");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("035-22 15 90");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("Fax");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("035-22 15 95");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(267, 85);
                    contentStream.showText("Filial");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("Strandgatan 11");
                    contentStream.newLine();
                    contentStream.showText("89133, Örnsköldsvik");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("Telefon");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("0660-245590");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(387, 85);
                    contentStream.showText("Bankgiro");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("5202-8982");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("Organisationsnr.");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("556549-4241");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(505, 85);
                    contentStream.showText("Internet");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("www.swearor.se");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("E-post");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("info@swearor.se");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("Momsreg.nr.");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("SE556549424101");
                    contentStream.newLine();
                    contentStream.showText("Innehar F-skattebevis");
                    contentStream.endText();
                    // Stream must be closed before saving document.

                    document.addPage(singlePage);
                    contentStream.close();
                }
            }
            if (second_page) {
                try (
                        PDPageContentStream contentStream = new PDPageContentStream(document, secondPage)) {
                    //contentStream.drawImage(pdImage, 40, 700);

                    float yCordinate = singlePage.getCropBox().getUpperRightY() - 30;
                    float startX = singlePage.getCropBox().getLowerLeftX() + 30;
                    float endX = singlePage.getCropBox().getUpperRightX() - 30;
                    yCordinate -= 125;

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 35);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(30, 800);
                    contentStream.setNonStrokingColor(Color.BLUE);
                    contentStream.showText("SWEA");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.showText("SWEA RÖR & STÅL AB");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText("SWEA TUBES AND STEEL COMPANY");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(325, 800);
                    contentStream.showText("Offertnr / Kundnr");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(Integer.toString(offert_nummer));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(485, 800);
                    contentStream.showText("Offertdatum");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(datum_field.getText());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(30, 720);
                    contentStream.showText("Leveransvillkor");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(leveransvillkor_combobox.getSelectedItem().toString());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(325, 720);
                    contentStream.showText("Betalningsvillkor");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(betalningsvillkor_combobox.getSelectedItem().toString());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(30, 750);
                    contentStream.showText("Vår referens");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(säljare_field.getText());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                    contentStream.setLeading(12.0f);
                    contentStream.newLineAtOffset(485, 720);
                    contentStream.showText("Er referens");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.COURIER, 8);
                    contentStream.showText(referens_field.getText());
                    contentStream.endText();

                    contentStream.moveTo(startX, yCordinate);
                    contentStream.lineTo(endX, yCordinate);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    contentStream.setLeading(25.0f);
                    contentStream.newLineAtOffset(30, 670);
                    contentStream.showText("Art. nr                        Kategori          Dimension         Vikt styck              Antal            Längd           Tolerans            Pris styck                Totalt Pris");
                    yCordinate -= 25;
                    contentStream.endText();
                    contentStream.moveTo(startX, yCordinate);
                    contentStream.lineTo(endX, yCordinate);
                    contentStream.stroke();

                    int n = 0;

                    for (i = i; i < färdig_model.getRowCount(); i++) {
                        contentStream.beginText();
                        contentStream.setFont(font, 8);
                        contentStream.newLineAtOffset(30, 650 - n * 20);
                        contentStream.showText(färdig_model.getValueAt(i, 0).toString());
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.newLineAtOffset(109, 650 - n * 20);
                        switch (färdig_model.getValueAt(i, 1).toString()) {
                            case "SVVKR":
                                contentStream.setFont(font, 8);
                                contentStream.showText("VKR");
                                contentStream.endText();
                                break;
                            case "SVKKR":
                                contentStream.setFont(font, 8);
                                contentStream.showText("KKR");
                                contentStream.endText();
                                break;
                            case "SVVCKR":
                                contentStream.setFont(font, 8);
                                contentStream.showText("VCKR");
                                contentStream.endText();
                                break;
                            case "SVKCKR":
                                contentStream.setFont(font, 8);
                                contentStream.showText("KCKR");
                                contentStream.endText();
                                break;
                            case "SÖDIN":
                                contentStream.setFont(font, 3);
                                contentStream.showText("Varmvalsat sömlöst S355J2H");
                                contentStream.endText();
                                break;
                            case "SÖE470":
                                contentStream.setFont(font, 8);
                                contentStream.showText("Ämnesrör E470");
                                contentStream.endText();
                                break;
                            default:
                                contentStream.setFont(font, 8);
                                contentStream.showText(färdig_model.getValueAt(i, 1).toString());
                                contentStream.endText();
                                break;
                        }
                        contentStream.beginText();
                        contentStream.setFont(font, 8);
                        contentStream.newLineAtOffset(163, 650 - n * 20);
                        contentStream.showText(färdig_model.getValueAt(i, 2).toString());
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.setFont(font, 8);
                        contentStream.newLineAtOffset(224, 650 - n * 20);
                        contentStream.showText(färdig_model.getValueAt(i, 3).toString() + " kg");
                        totalvikt += Double.parseDouble(färdig_model.getValueAt(i, 3).toString().replaceAll("[^\\d.]", "")) * Double.parseDouble(färdig_model.getValueAt(i, 4).toString().replaceAll("[^\\d.]", ""));
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.setFont(font, 8);
                        contentStream.newLineAtOffset(289, 650 - n * 20);
                        contentStream.showText(färdig_model.getValueAt(i, 4).toString() + " st");
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.setFont(font, 8);
                        contentStream.newLineAtOffset(337, 650 - n * 20);
                        contentStream.showText(färdig_model.getValueAt(i, 5).toString() + " mm");
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.setFont(font, 8);
                        contentStream.newLineAtOffset(385, 650 - n * 20);
                        contentStream.showText(färdig_model.getValueAt(i, 6).toString() + " mm");
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.setFont(font, 8);
                        contentStream.newLineAtOffset(444, 650 - n * 20);
                        contentStream.showText(färdig_model.getValueAt(i, 7).toString() + " kr");
                        contentStream.endText();
                        contentStream.beginText();
                        contentStream.setFont(font, 8);
                        contentStream.newLineAtOffset(518, 650 - n * 20);
                        contentStream.showText(färdig_model.getValueAt(i, 8).toString() + " kr");
                        totalbelopp += Double.parseDouble(färdig_model.getValueAt(i, 8).toString().replaceAll("[^\\d.]", ""));
                        contentStream.endText();

                        y = y - i * 20;

                        if (i == färdig_model.getRowCount() - 1) {
                            contentStream.moveTo(startX, 650 - n * 20 - 8);
                            contentStream.lineTo(endX, 650 - n * 20 - 8);
                            contentStream.stroke();

                            contentStream.beginText();
                            contentStream.newLineAtOffset(325, 650 - n * 20 - 22);
                            contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                            contentStream.showText("TOTAL");
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.newLineAtOffset(365, 650 - n * 20 - 22);
                            contentStream.setFont(PDType1Font.COURIER, 10);
                            contentStream.showText(format.format(totalvikt) + " kg");
                            contentStream.endText();

                            contentStream.beginText();
                            contentStream.newLineAtOffset(450, 650 - n * 20 - 22);
                            contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                            contentStream.showText("TOTAL");
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.newLineAtOffset(490, 650 - n * 20 - 22);
                            contentStream.setFont(PDType1Font.COURIER, 10);
                            contentStream.showText(format.format(totalbelopp) + " kr");
                            contentStream.endText();

                            String text = offert_textarea.getText();
                            wrT = WordUtils.wrap(text, 200).split("\\r?\\n");

                            for (int j = 0; j < wrT.length; j++) {
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA, 10);
                                string = wrT[j];
                                contentStream.newLineAtOffset(30, 650 - n * 20 - (j * 12) - 50);
                                contentStream.showText(string);
                                contentStream.endText();
                            }
                        }

                        n++;
                    }

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(30, 110);
                    contentStream.showText("Total exklusive extra kostnader:");
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.newLineAtOffset(170, 110);
                    contentStream.setFont(PDType1Font.HELVETICA, 8);
                    contentStream.showText(format.format(totalbelopp + Double.parseDouble(certifikat_textfield.getText()) + Double.parseDouble(transport_textfield.getText())) + " kr");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(30, 130);
                    contentStream.showText("Transport:");
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.newLineAtOffset(170, 130);
                    contentStream.setFont(PDType1Font.HELVETICA, 8);
                    contentStream.showText(transport_textfield.getText() + " kr");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(30, 150);
                    contentStream.showText("Certifikat:");
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.newLineAtOffset(170, 150);
                    contentStream.setFont(PDType1Font.HELVETICA, 8);
                    contentStream.showText(certifikat_textfield.getText() + " kr");
                    contentStream.endText();

                    contentStream.moveTo(startX, 100);
                    contentStream.lineTo(endX, 100);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(30, 85);
                    contentStream.showText("Adress");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("Tel: 035-22 15 90");
                    contentStream.newLine();
                    contentStream.showText("Sadelvägen 11");
                    contentStream.newLine();
                    contentStream.showText("302 62, Halmstad");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(147, 85);
                    contentStream.showText("Swea Rör AB");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("035-22 15 90");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("Fax");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("035-22 15 95");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(267, 85);
                    contentStream.showText("Filial");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("Strandgatan 11");
                    contentStream.newLine();
                    contentStream.showText("89133, Örnsköldsvik");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("Telefon");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("0660-245590");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(387, 85);
                    contentStream.showText("Bankgiro");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("5202-8982");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("Organisationsnr.");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("556549-4241");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.setLeading(10.5f);
                    contentStream.newLineAtOffset(505, 85);
                    contentStream.showText("Internet");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("www.swearor.se");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("E-post");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("info@swearor.se");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.showText("Momsreg.nr.");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 6);
                    contentStream.showText("SE556549424101");
                    contentStream.newLine();
                    contentStream.showText("Innehar F-skattebevis");
                    contentStream.endText();
                    // Stream must be closed before saving document.
                    document.addPage(secondPage);
                    contentStream.close();
                }
            }
            document.save(filename);
            document.close();
            offertHistorikTillDatabas(offert_nummer + "_" + offert_namn_field.getText());
            försäljningsArtikelTillDatabas();
            öppna_fil(filename);
            övergång_icon.setVisible(true);
            info_label.setVisible(true);
            offert_nummer++;
        } catch (IOException ioEx) {
            loggare.log_info(ioEx.getMessage(), "Program_offert", ioEx);
        }
    }

    public void offertHistorikTillDatabas(String offert_namn) {
        /* TESTAR DATABASEN OFFERT */
        if(internet_uppkoppling()) {
            HashMap<String, String> offertDataString = new HashMap<>();
            for (int i = 0; i < färdig_model.getRowCount(); i++) {
                offertDataString.put("Artikelnummer", färdig_model.getValueAt(i, 0).toString());
                offertDataString.put("Kategori", färdig_model.getValueAt(i, 1).toString());
                offertDataString.put("Dimension", färdig_model.getValueAt(i, 2).toString());
                offertDataString.put("TotalVikt", färdig_model.getValueAt(i, 3).toString());
                offertDataString.put("Antal", färdig_model.getValueAt(i, 4).toString());
                offertDataString.put("Längd", färdig_model.getValueAt(i, 5).toString());
                offertDataString.put("Tolerans", färdig_model.getValueAt(i, 6).toString());
                offertDataString.put("PrisStyck", färdig_model.getValueAt(i, 7).toString());
                offertDataString.put("PrisLeverans", färdig_model.getValueAt(i, 8).toString());
                offertDataString.put("Valutakurs", valutakurs_list.get(i));
                offertDataString.put("EuroPerTon", euro_per_ton_list.get(i));
                try {
                    functions.läggTillOffert(db, nuvarande_distrikt, kund, offert_namn, offertDataString, i);
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                    loggare.log_info(ex.getMessage(), "Program_offertHistorikTillDatabas", ex);
                }
            }
        /**************************/
        }
    }

    private void öppna_fil(String sök_path) {
        File file = new File(sök_path);

        if (!Desktop.isDesktopSupported()) {
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Programmet_öppna_fil", ex);
            }
        }
    }

    private void kolla_tidigare_försäljning() {
        /* TESTAR DATABASEN TIDIGARE FÖRSÄLJNING */
        if(internet_uppkoppling()) {
            try {
                harKundKöptTidigare = functions.kollaFörsäljning(db, nuvarande_distrikt, kund);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_kolla_tidigare_försäljning", ex);
            }
        }
        /*****************************************/
    }

    private void spara_artikelnummer() {
        gerkap_combobox_spara.add(Integer.toString(gerkap_combobox.getSelectedIndex()));
        material_kvalitet_combobox_spara.add(Integer.toString(material_kvalitet_combobox.getSelectedIndex()));
        bandtyp_combobox_spara.add(Integer.toString(bandtyp_combobox.getSelectedIndex()));
        valutakurs_field_spara.add(valutakurs_field.getText());
        stålkvalitet_field_spara.add(stålkvalitet_combobox.getSelectedItem().toString());
        stålverk_field_spara.add(stålverk_combobox.getSelectedItem().toString());
        säljare_field_spara.add(säljare_field.getText());
        datum_field_spara.add(datum_field.getText());
        artikelnummer_field_spara.add(artikelnummer_field.getText());
        bearbetning_field_spara.add(bearbetning_field.getText());
        dimension_field_spara.add(dimension_field.getText());
        antal_field_spara.add(antal_field.getText());
        längd_field_spara.add(längd_field.getText());
        längdtolerans_field_spara.add(längdtolerans_field.getText());
        euro_per_ton_field_spara.add(euro_per_ton_field.getText());
        ställkostnad_field_spara.add(ställkostnad_field.getText());
        ursprungslängd_field_spara.add(ursprungslängd_field.getText());
        påslag_field_spara.add(påslag_field.getText());
        fraktkostnad_field_spara.add(fraktkostnad_field.getText());
        fraktkostnad_sicam_field_spara.add(fraktkostnad_sicam_field.getText());
        spill_field_spara.add(spill_field.getText());
    }

    private void spara_artikelnummer_två(int selected_row) {
        gerkap_combobox_spara_två.add(gerkap_combobox_spara.get(selected_row));
        gerkap_combobox_spara.remove(selected_row);
        material_kvalitet_combobox_spara_två.add(material_kvalitet_combobox_spara.get(selected_row));
        material_kvalitet_combobox_spara.remove(selected_row);
        bandtyp_combobox_spara_två.add(bandtyp_combobox_spara.get(selected_row));
        bandtyp_combobox_spara.remove(selected_row);
        valutakurs_field_spara_två.add(valutakurs_field_spara.get(selected_row));
        valutakurs_field_spara.remove(selected_row);
        stålkvalitet_field_spara_två.add(stålkvalitet_field_spara.get(selected_row));
        stålkvalitet_field_spara.remove(selected_row);
        stålverk_field_spara_två.add(stålverk_field_spara.get(selected_row));
        stålverk_field_spara.remove(selected_row);
        säljare_field_spara_två.add(säljare_field_spara.get(selected_row));
        säljare_field_spara.remove(selected_row);
        datum_field_spara_två.add(datum_field_spara.get(selected_row));
        datum_field_spara.remove(selected_row);
        artikelnummer_field_spara_två.add(artikelnummer_field_spara.get(selected_row));
        artikelnummer_field_spara.remove(selected_row);
        bearbetning_field_spara_två.add(bearbetning_field_spara.get(selected_row));
        bearbetning_field_spara.remove(selected_row);
        dimension_field_spara_två.add(dimension_field_spara.get(selected_row));
        dimension_field_spara.remove(selected_row);
        antal_field_spara_två.add(antal_field_spara.get(selected_row));
        antal_field_spara.remove(selected_row);
        längd_field_spara_två.add(längd_field_spara.get(selected_row));
        längd_field_spara.remove(selected_row);
        längdtolerans_field_spara_två.add(längdtolerans_field_spara.get(selected_row));
        längdtolerans_field_spara.remove(selected_row);
        euro_per_ton_field_spara_två.add(euro_per_ton_field_spara.get(selected_row));
        euro_per_ton_field_spara.remove(selected_row);
        ställkostnad_field_spara_två.add(ställkostnad_field_spara.get(selected_row));
        ställkostnad_field_spara.remove(selected_row);
        ursprungslängd_field_spara_två.add(ursprungslängd_field_spara.get(selected_row));
        ursprungslängd_field_spara.remove(selected_row);
        påslag_field_spara_två.add(påslag_field_spara.get(selected_row));
        påslag_field_spara.remove(selected_row);
        fraktkostnad_field_spara_två.add(fraktkostnad_field_spara.get(selected_row));
        fraktkostnad_field_spara.remove(selected_row);
        fraktkostnad_sicam_field_spara_två.add(fraktkostnad_sicam_field_spara.get(selected_row));
        fraktkostnad_sicam_field_spara.remove(selected_row);
        spill_field_spara_två.add(spill_field_spara.get(selected_row));
        spill_field_spara.remove(selected_row);
    }
    
    private void uppdateraArtikelnummer (int selected_row) {
        gerkap_combobox_spara_två.set(selected_row, gerkap_combobox_spara.get(selected_row));
        gerkap_combobox_spara.remove(selected_row);
        material_kvalitet_combobox_spara_två.set(selected_row, material_kvalitet_combobox_spara.get(selected_row));
        material_kvalitet_combobox_spara.remove(selected_row);
        bandtyp_combobox_spara_två.set(selected_row, bandtyp_combobox_spara.get(selected_row));
        bandtyp_combobox_spara.remove(selected_row);
        valutakurs_field_spara_två.set(selected_row, valutakurs_field_spara.get(selected_row));
        valutakurs_field_spara.remove(selected_row);
        stålkvalitet_field_spara_två.set(selected_row, stålkvalitet_field_spara.get(selected_row));
        stålkvalitet_field_spara.remove(selected_row);
        stålverk_field_spara_två.set(selected_row, stålverk_field_spara.get(selected_row));
        stålverk_field_spara.remove(selected_row);
        säljare_field_spara_två.set(selected_row, säljare_field_spara.get(selected_row));
        säljare_field_spara.remove(selected_row);
        datum_field_spara_två.set(selected_row, datum_field_spara.get(selected_row));
        datum_field_spara.remove(selected_row);
        artikelnummer_field_spara_två.set(selected_row, artikelnummer_field_spara.get(selected_row));
        artikelnummer_field_spara.remove(selected_row);
        bearbetning_field_spara_två.set(selected_row, bearbetning_field_spara.get(selected_row));
        bearbetning_field_spara.remove(selected_row);
        dimension_field_spara_två.set(selected_row, dimension_field_spara.get(selected_row));
        dimension_field_spara.remove(selected_row);
        antal_field_spara_två.set(selected_row, antal_field_spara.get(selected_row));
        antal_field_spara.remove(selected_row);
        längd_field_spara_två.set(selected_row, längd_field_spara.get(selected_row));
        längd_field_spara.remove(selected_row);
        längdtolerans_field_spara_två.set(selected_row, längdtolerans_field_spara.get(selected_row));
        längdtolerans_field_spara.remove(selected_row);
        euro_per_ton_field_spara_två.set(selected_row, euro_per_ton_field_spara.get(selected_row));
        euro_per_ton_field_spara.remove(selected_row);
        ställkostnad_field_spara_två.set(selected_row, ställkostnad_field_spara.get(selected_row));
        ställkostnad_field_spara.remove(selected_row);
        ursprungslängd_field_spara_två.set(selected_row, ursprungslängd_field_spara.get(selected_row));
        ursprungslängd_field_spara.remove(selected_row);
        påslag_field_spara_två.set(selected_row, påslag_field_spara.get(selected_row));
        påslag_field_spara.remove(selected_row);
        fraktkostnad_field_spara_två.set(selected_row, fraktkostnad_field_spara.get(selected_row));
        fraktkostnad_field_spara.remove(selected_row);
        fraktkostnad_sicam_field_spara_två.set(selected_row, fraktkostnad_sicam_field_spara.get(selected_row));
        fraktkostnad_sicam_field_spara.remove(selected_row);
        spill_field_spara_två.set(selected_row, spill_field_spara.get(selected_row));
        spill_field_spara.remove(selected_row);
    }

    private void rensa_artikelnummer(int selected_row) {
        gerkap_combobox_spara.remove(selected_row);
        material_kvalitet_combobox_spara.remove(selected_row);
        bandtyp_combobox_spara.remove(selected_row);
        valutakurs_field_spara.remove(selected_row);
        stålkvalitet_field_spara.remove(selected_row);
        stålverk_field_spara.remove(selected_row);
        säljare_field_spara.remove(selected_row);
        datum_field_spara.remove(selected_row);
        artikelnummer_field_spara.remove(selected_row);
        bearbetning_field_spara.remove(selected_row);
        dimension_field_spara.remove(selected_row);
        antal_field_spara.remove(selected_row);
        längd_field_spara.remove(selected_row);
        längdtolerans_field_spara.remove(selected_row);
        euro_per_ton_field_spara.remove(selected_row);
        ställkostnad_field_spara.remove(selected_row);
        ursprungslängd_field_spara.remove(selected_row);
        påslag_field_spara.remove(selected_row);
        fraktkostnad_field_spara.remove(selected_row);
        fraktkostnad_sicam_field_spara.remove(selected_row);
        spill_field_spara.remove(selected_row);
    }

    private void rensa_artikelnummer_helt() {
        gerkap_combobox_spara.clear();
        material_kvalitet_combobox_spara.clear();
        bandtyp_combobox_spara.clear();
        valutakurs_field_spara.clear();
        stålkvalitet_field_spara.clear();
        stålverk_field_spara.clear();
        säljare_field_spara.clear();
        datum_field_spara.clear();
        artikelnummer_field_spara.clear();
        bearbetning_field_spara.clear();
        dimension_field_spara.clear();
        antal_field_spara.clear();
        längd_field_spara.clear();
        längdtolerans_field_spara.clear();
        euro_per_ton_field_spara.clear();
        ställkostnad_field_spara.clear();
        ursprungslängd_field_spara.clear();
        påslag_field_spara.clear();
        fraktkostnad_field_spara.clear();
        fraktkostnad_sicam_field_spara.clear();
        spill_field_spara.clear();
    }

    private void initializera_kolumner() {
        TableRowSorter<TableModel> offert_sorter = new TableRowSorter<>(offert_table.getModel());
        TableRowSorter<TableModel> färdig_sorter = new TableRowSorter<>(färdig_table.getModel());
        TableRowSorter<TableModel> tidigare_sorter = new TableRowSorter<>(tidigare_table.getModel());
        TableRowSorter<TableModel> övrigt_sorter = new TableRowSorter<>(övrigt_table.getModel());
        TableRowSorter<TableModel> kostnader_sorter = new TableRowSorter<>(kostnader_table.getModel());
        TableRowSorter<TableModel> priser_sorter = new TableRowSorter<>(priser_table.getModel());
        TableRowSorter<TableModel> alla_artiklar_sorter = new TableRowSorter<>(alla_artiklar_table.getModel());

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

        renderer.setHorizontalAlignment(SwingConstants.LEFT);

        for (int i = 0; i < offert_table.getColumnCount(); i++) {
            offert_table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        for (int i = 0; i < övrigt_table.getColumnCount(); i++) {
            övrigt_table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        for (int i = 0; i < färdig_table.getColumnCount(); i++) {
            färdig_table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        for (int i = 0; i < tidigare_table.getColumnCount(); i++) {
            tidigare_table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        for (int i = 0; i < kostnader_table.getColumnCount(); i++) {
            kostnader_table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        for (int i = 0; i < priser_table.getColumnCount(); i++) {
            priser_table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        for (int i = 0; i < alla_artiklar_table.getColumnCount(); i++) {
            alla_artiklar_table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        offert_table.setRowSorter(offert_sorter);
        färdig_table.setRowSorter(färdig_sorter);
        tidigare_table.setRowSorter(tidigare_sorter);
        övrigt_table.setRowSorter(övrigt_sorter);
        kostnader_table.setRowSorter(kostnader_sorter);
        priser_table.setRowSorter(priser_sorter);
        alla_artiklar_table.setRowSorter(alla_artiklar_sorter);

        offert_table.getTableHeader().setReorderingAllowed(false);
        färdig_table.getTableHeader().setReorderingAllowed(false);
        tidigare_table.getTableHeader().setReorderingAllowed(false);
        övrigt_table.getTableHeader().setReorderingAllowed(false);
        kostnader_table.getTableHeader().setReorderingAllowed(false);
        priser_table.getTableHeader().setReorderingAllowed(false);
        alla_artiklar_table.getTableHeader().setReorderingAllowed(false);
    }

    private double närmsta_värde(double[] sorterat, double nyckel) {
        if (sorterat.length == 1) {
            return sorterat[0];
        }
        if (nyckel < sorterat[0]) {
            return sorterat[0];
        }
        if (nyckel > sorterat[sorterat.length - 1]) {
            return sorterat[sorterat.length - 1];
        }
        int position = Arrays.binarySearch(sorterat, nyckel);
        if (position >= 0) {
            return sorterat[position];
        }
        int ip = -position - 1;
        double närmsta;
        if (sorterat[ip] - nyckel < nyckel - sorterat[ip - 1]) {
            närmsta = sorterat[ip];
        } else {
            närmsta = sorterat[ip - 1];
        }
        return närmsta;
    }

    private double närmsta_värde(ArrayList<Double> sorterat, double nyckel) {
        double distance = Math.abs(sorterat.get(0) - nyckel);
        int idx = 0;
        for (int i = 1; i < sorterat.size(); i++) {
            double cdistance = Math.abs(sorterat.get(i) - nyckel);
            if (cdistance < distance) {
                idx = i;
                distance = cdistance;
            }
        }
        return sorterat.get(idx);
    }

    private double hitta_närmaste(ArrayList<Double> sorterat, double nyckeln) {
        double värde = 0;
        double minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < sorterat.size(); i++) {
            double curDistance = Math.abs(sorterat.get(i) - nyckeln);
            if (curDistance < minDistance) {
                värde = sorterat.get(i);
                minDistance = curDistance;
            }
        }
        return värde;
    }

    private double formel_tuber(double yd, double gt) {
        double minus_1_procent = yd * 0.99;
        double min_10_procent = gt * 0.9;
        double max_yd = minus_1_procent;
        double min_yd = min_10_procent;

        return max_yd - min_yd - min_yd;
    }

    private boolean internet_uppkoppling() {
        boolean status = false;
        Socket sock = new Socket();
        InetSocketAddress address = new InetSocketAddress("www.google.com", 80);

        try {
            sock.connect(address, 3000);
            if (sock.isConnected()) {
                status = true;
            }
        } catch (IOException e) {
            status = false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {

            }
        }
        return status;
    }

    private String kryptera(String text) {
        try {
            return enc.encode(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private String dekryptera(String text) {
        try {
            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }
    
    private void kollaOmArtikelÄrSåld() {
        if(alla_artiklar_table.getRowCount() > 0) {
            for(int i = 0; i < tidigare_table.getRowCount(); i++) {
                String artikelnummer = tidigare_model.getValueAt(i, 0).toString();
                String datum = tidigare_model.getValueAt(i, 1).toString();
                for(int j = 0; j < alla_artiklar_table.getRowCount(); j++) {
                    if(alla_artiklar_table.getValueAt(j, 0).toString().matches(artikelnummer)) {
                        alla_artiklar_table.setValueAt(datum, j, 1);
                    }
                }
            }
        }
    }

    private void försäljningsArtikelTillDatabas() {
        /* TESTAR DATABASEN */
        if(internet_uppkoppling()) {
        for (int j = artikelnummer_field_spara_två.size() - 1; j >= 0; j--) {
            try {
                Map<String, String> strings = new HashMap<>();
                strings.put("Valutakurs", valutakurs_field_spara_två.get(j));
                strings.put("Antal", antal_field_spara_två.get(j));
                strings.put("Längd", längd_field_spara_två.get(j));
                strings.put("EuroPerTon", euro_per_ton_field_spara_två.get(j));
                strings.put("Ställkostnad", ställkostnad_field_spara_två.get(j));
                strings.put("Ursprungslängd", ursprungslängd_field_spara_två.get(j));
                strings.put("Påslag", påslag_field_spara_två.get(j));
                strings.put("Fraktkostnad", fraktkostnad_field_spara_två.get(j));
                strings.put("FraktkostnadSicam", fraktkostnad_sicam_field_spara_två.get(j));
                strings.put("Spill", spill_field_spara_två.get(j));
                strings.put("Bearbetning",bearbetning_field_spara_två.get(j));
                strings.put("Bandtyp", bandtyp_combobox_spara_två.get(j));
                strings.put("Material", material_kvalitet_combobox_spara_två.get(j));
                strings.put("Gerkap", gerkap_combobox_spara_två.get(j));
                strings.put("Stålkvalitet", stålkvalitet_field_spara_två.get(j));
                strings.put("Stålverk", stålverk_field_spara_två.get(j));
                strings.put("Längdtolerans", längdtolerans_field_spara_två.get(j));
                strings.put("Säljare", säljare_field_spara_två.get(j));
                strings.put("Datum", datum_field_spara_två.get(j));
                strings.put("Artikelnummer", artikelnummer_field_spara_två.get(j));
                strings.put("Dimension", dimension_field_spara_två.get(j));

                functions.läggTillArtikel(db, nuvarande_distrikt, kund, artikelnummer_field_spara_två.get(j), strings);
            } catch (ExecutionException | InterruptedException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_försäljningsArtikelTillDatabas", ex);

            }
        }
            /**
             * *****************
             */
        }
    }
    
    public static final File taSkärmDump(JFrame argFrame) {
        if(argFrame.isVisible()) {
            Rectangle rec = argFrame.getBounds();
            BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
            argFrame.paint(bufferedImage.getGraphics());

            try {
                // Create temp file
                File temp = File.createTempFile("screenshot", ".png");

                // Use the ImageIO API to write the bufferedImage to a temporary file
                ImageIO.write(bufferedImage, "png", temp);

                // Delete temp file when program exits
                temp.deleteOnExit();

                return temp;
            } catch (IOException ioe) {
                return null;
            }
        } else {
            return null;
        }
    }

    private void sparaFärgTillDatabas(JPanel panel) {
        /* TESTAR DATABASEN */
        if(internet_uppkoppling()) {
            HashMap<String, Integer> colors = new HashMap<>();
            int panelRed = panel.getBackground().getRed();
            int panelGreen = panel.getBackground().getGreen();
            int panelBlue = panel.getBackground().getBlue();
            colors.put("Red", panelRed);
            colors.put("Green", panelGreen);
            colors.put("Blue", panelBlue);
            try {
                functions.sparaFärger(db, säljare_field.getText(), panel.getName(), colors);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /********************/
    }
    
    private void läggTillAllaDistrikt(String[] ansvarig) {
        //THOMAS
        läggTillDistrikt("Distrikt 1", ansvarig[0]);
        //MIA
        läggTillDistrikt("Distrikt 2", ansvarig[1]);
        //MATTIAS
        läggTillDistrikt("Distrikt 3", ansvarig[2]);
        //ÅTERFÖRSÄLJARE
        läggTillDistrikt("Distrikt 4", ansvarig[3]);
        //EXPORT
        läggTillDistrikt("Distrikt 5", ansvarig[4]);
        //JOHAN
        läggTillDistrikt("Distrikt 6", ansvarig[5]);
        //OLA
        läggTillDistrikt("Distrikt 7", ansvarig[6]);
        //MAGNUS
        läggTillDistrikt("Distrikt 8", ansvarig[7]);
    }
    
    private void läggTillDistrikt(String distrikt, String ansvarig) {
        välj_distrikt_combobox.addItem(distrikt + " " + ansvarig);
    }
    
    private void lagraCache () {
        if(!cache.exists()) {
            try {
                cacheFolder.mkdirs();
                cache.createNewFile();
                try (PrintStream out = new PrintStream(new FileOutputStream(cache))) {
                out.print(kryptera(kryptera(kryptera(existerande_field.getText()))) + "\n" + kryptera(kryptera(kryptera(existerande_passwordfield.getText()))));  
                out.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                    loggare.log_info(ex.getMessage(), "Program_lagraCache", ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                loggare.log_info(ex.getMessage(), "Program_lagraCache", ex);
            }
        }
    }
    
    private void laddaCache () throws FileNotFoundException, IOException {
        File laddaCache = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "//cache//" + kryptera(kryptera(kryptera("cache"))) + ".txt");
        ArrayList<String> cacheList = new ArrayList<>();
        if(laddaCache.exists()) {
            kom_ihåg_mig_radio.setSelected(true);
            try (BufferedReader br = new BufferedReader(new FileReader(laddaCache))) {
                String line = br.readLine();
                while (line != null) {
                    cacheList.add(line);
                    line = br.readLine();               
                }  
            }
            if(!cacheList.isEmpty()) {
                existerande_field.setText(dekryptera(dekryptera(dekryptera(cacheList.get(0)))));
                existerande_passwordfield.setText(dekryptera(dekryptera(dekryptera(cacheList.get(1)))));
            }
        }
    }
    
    private void rensaCache () {
        cache.delete();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame AnvändareFönster;
    private javax.swing.JDialog EPostFönster;
    private javax.swing.JFrame FelRapportFönster;
    private javax.swing.JDialog FelmeddelandeFönster;
    private javax.swing.JDialog FärdigställFörsäljningFönster;
    private javax.swing.JDialog FärdigställFörsäljningFönster1;
    private javax.swing.JFrame FärgFönster;
    private javax.swing.JFrame InställningarFönster;
    private javax.swing.JCheckBoxMenuItem Klingkap_meny;
    private javax.swing.JFrame KundDashboardFrame;
    private javax.swing.JFrame LaddaOffertFönster;
    private javax.swing.JDialog LösenordFönster;
    private javax.swing.JFrame OffertFönster;
    private javax.swing.JFrame OrderOffertFönster;
    private javax.swing.JFrame Orderfönster;
    private javax.swing.JCheckBoxMenuItem Plockorder_meny;
    private javax.swing.JFrame VäljKundFönster;
    private javax.swing.JPanel aktuell_försäljningsruta_panel;
    private javax.swing.JTable alla_artiklar_table;
    private javax.swing.JTextField antal_field;
    private javax.swing.JLabel antal_label;
    private javax.swing.JPanel antal_panel;
    private javax.swing.JPanel antal_panel2;
    private javax.swing.JTextField antal_snitt_text;
    private javax.swing.JLabel antal_styck_label;
    private javax.swing.JTextField användare_field;
    private javax.swing.JLabel användare_icon;
    private javax.swing.JLabel användare_icon1;
    private javax.swing.JLabel användare_icon2;
    private javax.swing.JPanel användare_panel1;
    private javax.swing.JPasswordField användarlösenord_passwordfield;
    private javax.swing.JLabel användarnamn_lable3;
    private javax.swing.JPanel artikel_panel;
    private javax.swing.JTextField artikelnummer_field;
    private javax.swing.JLabel artikelnummer_label;
    private javax.swing.JButton avbryt_rensa_tabel_button;
    private javax.swing.JPanel avtalad_kvantitet_panel;
    private javax.swing.JTextField bandbyte;
    private javax.swing.JTextField bandets_livslängd;
    private javax.swing.JTextField bandkostnad;
    private javax.swing.JComboBox<String> bandtyp_combobox;
    private javax.swing.JTextField bearbetning_field;
    private javax.swing.JLabel bearbetning_kr_kg_label;
    private javax.swing.JLabel bearbetning_label;
    private javax.swing.JPanel bearbetning_panel;
    private javax.swing.JPanel bearbetning_panel2;
    private javax.swing.JLabel belopp_hittils_label;
    private javax.swing.JPanel belopp_hittils_panel;
    private javax.swing.JComboBox<String> betalningsvillkor_combobox;
    private javax.swing.JLabel bug_icon;
    private javax.swing.JTextField certifikat_textfield;
    private java.awt.Label cykeltid_label;
    private javax.swing.JTextField cykeltid_text1;
    private javax.swing.JPanel dashboardHeaderPanel;
    private javax.swing.JPanel dashboardUppgifterPanel;
    private javax.swing.JLabel databas_icon;
    private javax.swing.JLabel databas_kryss_icon;
    private javax.swing.JLabel databas_plus_icon;
    private javax.swing.JTextField datum_field;
    private javax.swing.JLabel datum_label;
    private javax.swing.JPanel datum_panel;
    private javax.swing.JTextField diameter_text;
    private javax.swing.JTextField dimension_field;
    private javax.swing.JLabel dimension_label;
    private javax.swing.JLabel dimension_mm_label;
    private javax.swing.JPanel dimension_panel;
    private javax.swing.JPanel dimension_panel2;
    private javax.swing.JLabel distrikt_label;
    private javax.swing.JTextField e_post_field;
    private javax.swing.JTextField e_post_field1;
    private javax.swing.JTextField e_post_field2;
    private javax.swing.JPanel e_post_panel;
    private javax.swing.JLabel epost_icon;
    private javax.swing.JLabel epost_icon1;
    private javax.swing.JLabel euro_per_ton_euro_label;
    private javax.swing.JTextField euro_per_ton_field;
    private javax.swing.JLabel euro_per_ton_label;
    private javax.swing.JPanel euro_per_ton_panel;
    private javax.swing.JPanel euro_per_ton_panel2;
    private javax.swing.JTextField existerande_field;
    private javax.swing.JPanel existerande_panel;
    private javax.swing.JPasswordField existerande_passwordfield;
    private java.awt.Label felmeddelande_label;
    private javax.swing.JButton felmeddelande_ok_knapp;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField fraktkostnad_field;
    private javax.swing.JLabel fraktkostnad_kr_kg_label;
    private javax.swing.JLabel fraktkostnad_label;
    private javax.swing.JPanel fraktkostnad_per_kilo_panel;
    private javax.swing.JPanel fraktkostnad_per_kilo_panel2;
    private javax.swing.JTextField fraktkostnad_sicam_field;
    private javax.swing.JLabel fraktkostnad_sicam_kr_kg_label;
    private javax.swing.JLabel fraktkostnad_sicam_label;
    private javax.swing.JPanel fraktkostnad_sicam_panel;
    private javax.swing.JPanel fraktkostnad_sicam_panel2;
    private javax.swing.JPanel färdig_panel;
    private javax.swing.JPanel färdig_panel1;
    private javax.swing.JTable färdig_table;
    private javax.swing.JPanel färdigställFörsäljningPanel;
    private javax.swing.JPanel färdigställ_försäljning_panel1;
    private javax.swing.JLabel färg_icon;
    private javax.swing.JLabel färg_label;
    private javax.swing.JPanel färg_panel;
    private javax.swing.JColorChooser färg_väljare;
    private javax.swing.JButton för_till_orderfönster_button;
    private javax.swing.JPanel försäljning_hittils_panel;
    private javax.swing.JTextField förändring_field_procent;
    private javax.swing.JTextField förändring_i_kr_field;
    private javax.swing.JLabel förändring_i_kr_label;
    private javax.swing.JPanel förändring_i_kr_panel;
    private javax.swing.JLabel förändring_i_procent_label;
    private javax.swing.JPanel förändring_i_procent_panel;
    private javax.swing.JComboBox<String> gerkap_combobox;
    private javax.swing.JLabel glömt_lösenord_label;
    private javax.swing.JTextField godstjocklek_text;
    private javax.swing.JTextField hydraulik;
    private javax.swing.JLabel info_label;
    private javax.swing.JPanel inställningar_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField kaplängd_text;
    private javax.swing.JTextField kilo_per_meter_field;
    private javax.swing.JLabel kilo_per_meter_label;
    private javax.swing.JLabel kilo_per_meter_label2;
    private javax.swing.JPanel kilo_per_meter_panel;
    private javax.swing.JPanel kilo_per_meter_panel2;
    private javax.swing.JRadioButton kom_ihåg_mig_radio;
    private javax.swing.JTextField kostnad_per_bit_text1;
    private javax.swing.JTable kostnader_table;
    private java.awt.Label kostnadperbit_label;
    private javax.swing.JTextField kundAdressField;
    private javax.swing.JTextField kundAdressField1;
    private javax.swing.JPanel kundDashBoardPanel;
    private javax.swing.JLabel kundDashboardLabel;
    private javax.swing.JTextField kundEpostField;
    private javax.swing.JTextField kundSkapadField;
    private javax.swing.JPanel kund_distrikt_panel;
    private javax.swing.JLabel kund_färdig_label;
    private javax.swing.JLabel kund_färdig_label1;
    private javax.swing.JLabel kund_label;
    private javax.swing.JTextField kvantitet_hittils_field;
    private javax.swing.JLabel kvantitet_hittils_label;
    private javax.swing.JPanel kvantitet_hittils_panel;
    private javax.swing.JLabel kvantitet_per_år_label;
    private javax.swing.JTextField kvantitet_år_field;
    private javax.swing.JPanel kvantitet_år_panel;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label28;
    private java.awt.Label label29;
    private java.awt.Label label3;
    private java.awt.Label label30;
    private java.awt.Label label31;
    private java.awt.Label label32;
    private java.awt.Label label33;
    private java.awt.Label label34;
    private java.awt.Label label35;
    private java.awt.Label label36;
    private java.awt.Label label37;
    private java.awt.Label label38;
    private java.awt.Label label39;
    private java.awt.Label label4;
    private java.awt.Label label40;
    private java.awt.Label label41;
    private java.awt.Label label42;
    private java.awt.Label label43;
    private java.awt.Label label44;
    private java.awt.Label label45;
    private java.awt.Label label47;
    private java.awt.Label label48;
    private java.awt.Label label49;
    private java.awt.Label label50;
    private java.awt.Label label51;
    private java.awt.Label label52;
    private java.awt.Label label53;
    private java.awt.Label label54;
    private java.awt.Label label55;
    private java.awt.Label label56;
    private java.awt.Label label57;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private javax.swing.JPanel ladda_offert_panel;
    private javax.swing.JProgressBar ladda_progressbar;
    private javax.swing.JPanel laddar_panel;
    private javax.swing.JComboBox<String> leveransvillkor_combobox;
    private javax.swing.JTextField loading_text_field;
    private javax.swing.JToggleButton logga_in_användare_button;
    private javax.swing.JToggleButton lägg_till_användare_button;
    private javax.swing.JLabel lägg_till_användare_button2;
    private javax.swing.JLabel lägg_till_användare_button4;
    private javax.swing.JButton lägg_till_kund_button;
    private javax.swing.JButton lägg_till_kund_button1;
    private javax.swing.JTextField lägg_till_kund_field;
    private javax.swing.JPanel lägg_till_kund_panel;
    private javax.swing.JTextField längd_field;
    private javax.swing.JLabel längd_label;
    private javax.swing.JLabel längd_mm_label;
    private javax.swing.JPanel längd_panel;
    private javax.swing.JPanel längd_panel2;
    private javax.swing.JTextField längdtolerans_field;
    private javax.swing.JLabel längdtolerans_label;
    private javax.swing.JLabel längdtolerans_mm_label;
    private javax.swing.JPanel längdtolerans_panel;
    private javax.swing.JPanel längdtolerans_panel2;
    private javax.swing.JTextField lätta_material;
    private javax.swing.JButton lösenord_button;
    private javax.swing.JLabel lösenord_icon;
    private javax.swing.JLabel lösenord_icon1;
    private javax.swing.JLabel lösenord_krävs_label;
    private javax.swing.JPasswordField lösenord_passwordfield;
    private javax.swing.JTextField lösenordet_field;
    private javax.swing.JPanel mail_panel;
    private javax.swing.JLabel marginal_per_år_label;
    private javax.swing.JTextField marginal_styck_field;
    private javax.swing.JLabel marginal_styck_label;
    private javax.swing.JPanel marginal_styck_panel;
    private javax.swing.JTextField marginal_total_field;
    private javax.swing.JLabel marginal_total_label;
    private javax.swing.JPanel marginal_total_panel;
    private javax.swing.JTextField marginal_år_field;
    private javax.swing.JPanel marginal_år_panel;
    private javax.swing.JComboBox<String> material_kvalitet_combobox;
    private javax.swing.JTextField max_längd;
    private javax.swing.JTextArea meddelande_area;
    private javax.swing.JMenuBar meny_menubar;
    private javax.swing.JTextField min_antal;
    private javax.swing.JPanel offertPanel;
    private javax.swing.JButton offert_klar_button;
    private javax.swing.JLabel offert_label;
    private javax.swing.JTextField offert_namn_field;
    private javax.swing.JLabel offert_namn_label;
    private javax.swing.JLabel offert_namn_label1;
    private javax.swing.JScrollPane offert_scrollpane;
    private javax.swing.JButton offert_stäng_button;
    private javax.swing.JTable offert_table;
    private javax.swing.JTextArea offert_textarea;
    private javax.swing.JLabel omsättning_per_år_label;
    private javax.swing.JTextField omsättning_år_field;
    private javax.swing.JPanel omsättning_år_panel;
    private javax.swing.JPanel panel_panel;
    private javax.swing.JLabel pris_styck_label;
    private javax.swing.JPanel pris_styck_panel;
    private javax.swing.JLabel pris_totalt_label;
    private javax.swing.JPanel pris_totalt_panel;
    private javax.swing.JTable priser_table;
    private javax.swing.JTextField prisstyck_field;
    private javax.swing.JTextField pristotalt_field;
    private javax.swing.JTextField problemet_field;
    private javax.swing.JTextField påslag_field;
    private javax.swing.JLabel påslag_label;
    private javax.swing.JPanel påslag_panel;
    private javax.swing.JPanel påslag_panel2;
    private javax.swing.JLabel påslag_procent_label;
    private javax.swing.JTextField referens_field;
    private javax.swing.JButton rensa_tabel_button;
    private javax.swing.JDialog rensa_tabel_dialog;
    private javax.swing.JLabel rensa_tabel_label;
    private javax.swing.JPanel rensa_tabel_panel;
    private javax.swing.JButton rensa_tabell_button;
    private javax.swing.JTextField rörframmatning;
    private javax.swing.JTextField sammanräknad_marginal_field;
    private javax.swing.JTextField sammanräknad_marginal_field1;
    private javax.swing.JTextField sammanräknad_total_vikt_field;
    private javax.swing.JTextField sammanräknad_total_vikt_field1;
    private javax.swing.JTextField sammanräknat_antal_field;
    private javax.swing.JTextField sammanräknat_antal_field1;
    private javax.swing.JTextField sammanräknat_pris_per_styck_field;
    private javax.swing.JTextField sammanräknat_pris_per_styck_field1;
    private javax.swing.JTextField seriekapning;
    private javax.swing.JButton skapa_offert_button;
    private javax.swing.JButton skapa_offert_button1;
    private javax.swing.JButton skicka_button;
    private javax.swing.JButton skicka_mail_button;
    private javax.swing.JButton spara_artikelnummer_button;
    private javax.swing.JTextField spill_field;
    private javax.swing.JLabel spill_label;
    private javax.swing.JPanel spill_panel;
    private javax.swing.JPanel spill_procent_panel2;
    private javax.swing.JTextField ställ_tid;
    private javax.swing.JTextField ställkostnad_field;
    private javax.swing.JLabel ställkostnad_kr_label;
    private javax.swing.JLabel ställkostnad_label;
    private javax.swing.JPanel ställkostnad_panel;
    private javax.swing.JPanel ställkostnad_panel2;
    private javax.swing.JComboBox<String> stålkvalitet_combobox;
    private javax.swing.JTextField stålkvalitet_field;
    private javax.swing.JComboBox<String> stålverk_combobox;
    private javax.swing.JTextField stålverk_field;
    private javax.swing.JTextField svåra_material;
    private javax.swing.JTextField säljare_field;
    private javax.swing.JLabel säljare_label;
    private javax.swing.JPanel säljare_panel;
    private javax.swing.JLabel sök_tabel_label;
    private javax.swing.JLabel sök_tabel_label1;
    private javax.swing.JButton taBortKundButton;
    private javax.swing.JButton ta_bort_offert_button1;
    private javax.swing.JButton ta_bort_rad_button;
    private javax.swing.JPanel tidigare_försäljning_panel;
    private javax.swing.JTable tidigare_table;
    private javax.swing.JTextField tidpunkt_field;
    private javax.swing.JButton till_orderfönster_button;
    private javax.swing.JTextField toleransvidd;
    private javax.swing.JTextField toleransvidd_under_1mm;
    private javax.swing.JTextField toleransvidd_över_1mm;
    private javax.swing.JLabel ton_per_år_label;
    private javax.swing.JTextField ton_år_field;
    private javax.swing.JPanel ton_år_panel;
    private java.awt.Label totaltid_label;
    private javax.swing.JTextField totaltid_text1;
    private javax.swing.JTextField totalvikt_field;
    private javax.swing.JLabel totalvikt_label;
    private javax.swing.JPanel totalvikt_panel;
    private javax.swing.JTextField transport_textfield;
    private javax.swing.JTextField täckningsgrad_field;
    private javax.swing.JLabel täckningsgrad_label;
    private javax.swing.JPanel täckningsgrad_panel;
    private javax.swing.JTextField upp_nergång_mm_sekund;
    private javax.swing.JButton uppdatera_rad_button;
    private javax.swing.JLabel uppgifterDashboardLabel;
    private javax.swing.JPanel uppgifterDashboardPanel;
    private javax.swing.JTextField ursprungslängd_field;
    private javax.swing.JLabel ursprungslängd_label;
    private javax.swing.JLabel ursprungslängd_mm_label;
    private javax.swing.JPanel ursprungslängd_panel;
    private javax.swing.JPanel ursprungslängd_panel2;
    private javax.swing.JComboBox<String> valutakurs_combobox;
    private javax.swing.JTextField valutakurs_field;
    private javax.swing.JLabel valutakurs_kr_label;
    private javax.swing.JPanel valutakurs_panel2;
    private javax.swing.JTextField vanlig;
    private javax.swing.JLabel vikt_styck_label;
    private javax.swing.JPanel vikt_styck_panel;
    private javax.swing.JTextField viktstyck_field;
    private javax.swing.JTextField vinst_hittils_field;
    private javax.swing.JButton välj_distrikt_button;
    private javax.swing.JComboBox<String> välj_distrikt_combobox;
    private javax.swing.JLabel välj_distrikt_icon;
    private javax.swing.JPanel välj_distrikt_panel;
    private javax.swing.JButton välj_kund_button;
    private javax.swing.JComboBox<String> välj_kund_combobox;
    private javax.swing.JPanel välj_kund_panel;
    private javax.swing.JTextField välj_material;
    private javax.swing.JComboBox<String> välj_offert_combobox;
    private javax.swing.JLabel välj_offert_label;
    private javax.swing.JLabel ämne_label;
    private javax.swing.JLabel ämne_label1;
    private javax.swing.JLabel ämne_label2;
    private javax.swing.JLabel ämne_label3;
    private javax.swing.JLabel övergång_icon;
    private javax.swing.JLabel övergång_icon1;
    private javax.swing.JLabel övergång_icon2;
    private javax.swing.JLabel övergång_icon3;
    private javax.swing.JTable övrigt_table;
    // End of variables declaration//GEN-END:variables
}
