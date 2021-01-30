package fom.wolniakhajri.wa.models;

import java.util.ArrayList;
import java.util.Arrays;


public class CompanyList {

    public static ArrayList<Company> createCompanyList() {
        Company[] comps = new Company[30];
        comps[0] = new Company("adidas AG", "ADS.DE");
        comps[1] = new Company("Allianz SE", "ALV.DE");
        comps[2] = new Company("BASF SE", "BAS.DE");
        comps[3] = new Company("Bayer AG", "BAYN.DE");
        comps[4] = new Company("Beiersdorf AG", "BEI.DE");
        comps[5] = new Company("Bayerische Motoren Werke AG", "BMW.DE");
        comps[6] = new Company("Continental AG", "CON.DE");
        comps[7] = new Company("Covestro AG", "1COV.DE");
        comps[8] = new Company("Daimler AG", "DAI.DE");
        comps[9] = new Company("Delivery Hero SE", "DHER.DE");              //NEU: Statt Wirecard AG im DAX
        comps[10] = new Company("Deutsche Bank AG", "DBK.DE");
        comps[11] = new Company("Deutsche Börse AG", "DB1.DE");
        comps[12] = new Company("Deutsche Post AG", "DPW.DE");
        comps[13] = new Company("Deutsche Telekom AG", "DTE.DE");
        comps[14] = new Company("Deutsche Wohnen SE", "DWNI.DE");
        comps[15] = new Company("E.ON SE", "EOAN.DE");
        comps[16] = new Company("Fresenius SE & Co. KGaA", "FRE.DE");
        comps[17] = new Company("Fresenius Medical Care AG & Co. KGaA", "FME.DE");
        comps[18] = new Company("HeidelbergCement AG", "HEI.DE");
        comps[19] = new Company("Henkel AG & Co. KGaA", "HEN3.DE");
        comps[20] = new Company("Infineon Technologies AG", "IFX.DE");
        comps[21] = new Company("Linde plc", "LIN.DE");
        comps[22] = new Company("Merck KGaA", "MRK.DE");
        comps[23] = new Company("MTU Aero Engines AG", "MTX.DE");
        comps[24] = new Company("Münchener Rück", "MUV2.DE");
        comps[25] = new Company("RWE AG", "RWE.DE");
        comps[26] = new Company("SAP SE", "SAP.DE");
        comps[27] = new Company("Siemens AG", "SIE.DE");
        comps[28] = new Company("Volkswagen AG", "VOW3.DE");
        comps[29] = new Company("Vonovia SE", "VNA.DE");

        return new ArrayList<>(Arrays.asList(comps));
    }
}
