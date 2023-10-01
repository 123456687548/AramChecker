package eu.time.datadragon;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class DDChampions {
    public String type;
    public String format;
    public String version;
    public Data data;

    public Map<Integer, Champion> getChampionList() {
        Map<Integer, Champion> result = new HashMap<>();

        Field[] declaredFields = data.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            try {
                Champion champion = (Champion) declaredField.get(data);
                if(champion != null) {
                    result.put(Integer.parseInt(champion.key), champion);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    public class Data {
        @SerializedName("Aatrox")
        public Champion aatrox;
        @SerializedName("Ahri")
        public Champion ahri;
        @SerializedName("Akali")
        public Champion akali;
        @SerializedName("Akshan")
        public Champion akshan;
        @SerializedName("Alistar")
        public Champion alistar;
        @SerializedName("Amumu")
        public Champion amumu;
        @SerializedName("Anivia")
        public Champion anivia;
        @SerializedName("Annie")
        public Champion annie;
        @SerializedName("Aphelios")
        public Champion aphelios;
        @SerializedName("Ashe")
        public Champion ashe;
        @SerializedName("AurelionSol")
        public Champion aurelionSol;
        @SerializedName("Azir")
        public Champion azir;
        @SerializedName("Bard")
        public Champion bard;
        @SerializedName("Belveth")
        public Champion belveth;
        @SerializedName("Blitzcrank")
        public Champion blitzcrank;
        @SerializedName("Brand")
        public Champion brand;
        @SerializedName("Braum")
        public Champion braum;
        @SerializedName("Briar")
        public Champion briar;
        @SerializedName("Caitlyn")
        public Champion caitlyn;
        @SerializedName("Camille")
        public Champion camille;
        @SerializedName("Cassiopeia")
        public Champion cassiopeia;
        @SerializedName("Chogath")
        public Champion chogath;
        @SerializedName("Corki")
        public Champion corki;
        @SerializedName("Darius")
        public Champion darius;
        @SerializedName("Diana")
        public Champion diana;
        @SerializedName("Draven")
        public Champion draven;
        @SerializedName("DrMundo")
        public Champion drMundo;
        @SerializedName("Ekko")
        public Champion ekko;
        @SerializedName("Elise")
        public Champion elise;
        @SerializedName("Evelynn")
        public Champion evelynn;
        @SerializedName("Ezreal")
        public Champion ezreal;
        @SerializedName("Fiddlesticks")
        public Champion fiddlesticks;
        @SerializedName("Fiora")
        public Champion fiora;
        @SerializedName("Fizz")
        public Champion fizz;
        @SerializedName("Galio")
        public Champion galio;
        @SerializedName("Gangplank")
        public Champion gangplank;
        @SerializedName("Garen")
        public Champion garen;
        @SerializedName("Gnar")
        public Champion gnar;
        @SerializedName("Gragas")
        public Champion gragas;
        @SerializedName("Graves")
        public Champion graves;
        @SerializedName("Gwen")
        public Champion gwen;
        @SerializedName("Hecarim")
        public Champion hecarim;
        @SerializedName("Heimerdinger")
        public Champion heimerdinger;
        @SerializedName("Illaoi")
        public Champion illaoi;
        @SerializedName("Irelia")
        public Champion irelia;
        @SerializedName("Ivern")
        public Champion ivern;
        @SerializedName("Janna")
        public Champion janna;
        @SerializedName("JarvanIV")
        public Champion jarvanIV;
        @SerializedName("Jax")
        public Champion jax;
        @SerializedName("Jayce")
        public Champion jayce;
        @SerializedName("Jhin")
        public Champion jhin;
        @SerializedName("Jinx")
        public Champion jinx;
        @SerializedName("Kaisa")
        public Champion kaisa;
        @SerializedName("Kalista")
        public Champion kalista;
        @SerializedName("Karma")
        public Champion karma;
        @SerializedName("Karthus")
        public Champion karthus;
        @SerializedName("Kassadin")
        public Champion kassadin;
        @SerializedName("Katarina")
        public Champion katarina;
        @SerializedName("Kayle")
        public Champion kayle;
        @SerializedName("Kayn")
        public Champion kayn;
        @SerializedName("Kennen")
        public Champion kennen;
        @SerializedName("Khazix")
        public Champion khazix;
        @SerializedName("Kindred")
        public Champion kindred;
        @SerializedName("Kled")
        public Champion kled;
        @SerializedName("KogMaw")
        public Champion kogMaw;
        @SerializedName("KSante")
        public Champion kSante;
        @SerializedName("Leblanc")
        public Champion leblanc;
        @SerializedName("LeeSin")
        public Champion leeSin;
        @SerializedName("Leona")
        public Champion leona;
        @SerializedName("Lillia")
        public Champion lillia;
        @SerializedName("Lissandra")
        public Champion lissandra;
        @SerializedName("Lucian")
        public Champion lucian;
        @SerializedName("Lulu")
        public Champion lulu;
        @SerializedName("Lux")
        public Champion lux;
        @SerializedName("Malphite")
        public Champion malphite;
        @SerializedName("Malzahar")
        public Champion malzahar;
        @SerializedName("Maokai")
        public Champion maokai;
        @SerializedName("MasterYi")
        public Champion masterYi;
        @SerializedName("Milio")
        public Champion milio;
        @SerializedName("MissFortune")
        public Champion missFortune;
        @SerializedName("MonkeyKing")
        public Champion monkeyKing;
        @SerializedName("Mordekaiser")
        public Champion mordekaiser;
        @SerializedName("Morgana")
        public Champion morgana;
        @SerializedName("Naafiri")
        public Champion naafiri;
        @SerializedName("Nami")
        public Champion nami;
        @SerializedName("Nasus")
        public Champion nasus;
        @SerializedName("Nautilus")
        public Champion nautilus;
        @SerializedName("Neeko")
        public Champion neeko;
        @SerializedName("Nidalee")
        public Champion nidalee;
        @SerializedName("Nilah")
        public Champion nilah;
        @SerializedName("Nocturne")
        public Champion nocturne;
        @SerializedName("Nunu")
        public Champion nunu;
        @SerializedName("Olaf")
        public Champion olaf;
        @SerializedName("Orianna")
        public Champion orianna;
        @SerializedName("Ornn")
        public Champion ornn;
        @SerializedName("Pantheon")
        public Champion pantheon;
        @SerializedName("Poppy")
        public Champion poppy;
        @SerializedName("Pyke")
        public Champion pyke;
        @SerializedName("Qiyana")
        public Champion qiyana;
        @SerializedName("Quinn")
        public Champion quinn;
        @SerializedName("Rakan")
        public Champion rakan;
        @SerializedName("Rammus")
        public Champion rammus;
        @SerializedName("RekSai")
        public Champion rekSai;
        @SerializedName("Rell")
        public Champion rell;
        @SerializedName("Renata")
        public Champion renata;
        @SerializedName("Renekton")
        public Champion renekton;
        @SerializedName("Rengar")
        public Champion rengar;
        @SerializedName("Riven")
        public Champion riven;
        @SerializedName("Rumble")
        public Champion rumble;
        @SerializedName("Ryze")
        public Champion ryze;
        @SerializedName("Samira")
        public Champion samira;
        @SerializedName("Sejuani")
        public Champion sejuani;
        @SerializedName("Senna")
        public Champion senna;
        @SerializedName("Seraphine")
        public Champion seraphine;
        @SerializedName("Sett")
        public Champion sett;
        @SerializedName("Shaco")
        public Champion shaco;
        @SerializedName("Shen")
        public Champion shen;
        @SerializedName("Shyvana")
        public Champion shyvana;
        @SerializedName("Singed")
        public Champion singed;
        @SerializedName("Sion")
        public Champion sion;
        @SerializedName("Sivir")
        public Champion sivir;
        @SerializedName("Skarner")
        public Champion skarner;
        @SerializedName("Sona")
        public Champion sona;
        @SerializedName("Soraka")
        public Champion soraka;
        @SerializedName("Swain")
        public Champion swain;
        @SerializedName("Sylas")
        public Champion sylas;
        @SerializedName("Syndra")
        public Champion syndra;
        @SerializedName("TahmKench")
        public Champion tahmKench;
        @SerializedName("Taliyah")
        public Champion taliyah;
        @SerializedName("Talon")
        public Champion talon;
        @SerializedName("Taric")
        public Champion taric;
        @SerializedName("Teemo")
        public Champion teemo;
        @SerializedName("Thresh")
        public Champion thresh;
        @SerializedName("Tristana")
        public Champion tristana;
        @SerializedName("Trundle")
        public Champion trundle;
        @SerializedName("Tryndamere")
        public Champion tryndamere;
        @SerializedName("TwistedFate")
        public Champion twistedFate;
        @SerializedName("Twitch")
        public Champion twitch;
        @SerializedName("Udyr")
        public Champion udyr;
        @SerializedName("Urgot")
        public Champion urgot;
        @SerializedName("Varus")
        public Champion varus;
        @SerializedName("Vayne")
        public Champion vayne;
        @SerializedName("Veigar")
        public Champion veigar;
        @SerializedName("Velkoz")
        public Champion velkoz;
        @SerializedName("Vex")
        public Champion vex;
        @SerializedName("Vi")
        public Champion vi;
        @SerializedName("Viego")
        public Champion viego;
        @SerializedName("Viktor")
        public Champion viktor;
        @SerializedName("Vladimir")
        public Champion vladimir;
        @SerializedName("Volibear")
        public Champion volibear;
        @SerializedName("Warwick")
        public Champion warwick;
        @SerializedName("Xayah")
        public Champion xayah;
        @SerializedName("Xerath")
        public Champion xerath;
        @SerializedName("XinZhao")
        public Champion xinZhao;
        @SerializedName("Yasuo")
        public Champion yasuo;
        @SerializedName("Yone")
        public Champion yone;
        @SerializedName("Yorick")
        public Champion yorick;
        @SerializedName("Yuumi")
        public Champion yuumi;
        @SerializedName("Zac")
        public Champion zac;
        @SerializedName("Zed")
        public Champion zed;
        @SerializedName("Zeri")
        public Champion zeri;
        @SerializedName("Ziggs")
        public Champion ziggs;
        @SerializedName("Zilean")
        public Champion zilean;
        @SerializedName("Zoe")
        public Champion zoe;
        @SerializedName("Zyra")
        public Champion zyra;
    }
}
