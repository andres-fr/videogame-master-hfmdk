package com.mygdx.game.core;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by afr on 23.09.16.
 * This class is based on the behaviour of the famous "Contemporary Classical Composer's Bullshit Generator",
 * made by the composer and pianist Dominic Irving (http://www.dominicirving.com/).
 * We are very thankful to him for sharing the original algorithm.
 * As the rest of this project, this class is released under the GPLv3 license.
 */
public abstract class BullshitGenerator {
    private BullshitGenerator(){} // prevent the abstract class to be instantiated

    public final static String[] NOUNS = new String[]{
            "composition",
            "experience",
            "idea",
            "phrase",
            "phenomenon|phenomena"};


    public final static String[] ADJECTIVES = new String[]{
            // the second form is the adverb, in case -ly not allowed
            "choreographic|choreographically",
            "complex|complex",
            "deconstructed",
            "gradual",
            "instrumental",
            "intrincated",
            "linear",
            "modern",
            "natural",
            "paradigmatic",
            "random",
            "spectral",
            "stockhausenesque",
            "Schoenbergly-structured",
            "tonal",
            "traditional",
            "transversal"};
    public final static String[] ADJECTIVE_PREFIXES = new String[]{
            "anti",
            "endo",
            "intra",
            "meta",
            "mostly",
            "neuro",
            "para",
            "post",
            "pre",
            "pro",
            "proto",
            "pseudo",
            "sub",
            "tetra"};
    public final static String[] VERBS = new String[]{
            "dictate|dictates|dictating",
            "engage|engages|engaging",
            "establish|establishes|establishing",
            "incorporate|incorporates|incorporating",
            "overcome|overcomes|overcoming",
            "allow|allows|allowing"};
    public final static String[] BEGIN_SENTENCE_STRUCTURES = new String[]{
            "The |adjective| consequences of |verb-ing| |noun-plural| enables the use of a single |noun| amongst many |adjective| |noun-plural|. ",
            "By |verb-ing| in critical |verb-ing|, I seek to |verb| the existing radical models, and |verb| a more diametric and rhythmic paradigm. ",
            "My latest piece begins with a rather serialist ‘space-melody’, before post-serially transforming the existing passive material into a more post-Schoenbergly-structured state, a process I term ‘subtractively-multimedia-influencing’. ",
            "When planning my predominant motifs, I often find that creating a somewhat psycho-theoretical array of musical octaves helps a great deal. ",
            "Recently, I have started to embrace time-signatures as a strongly-diverse alternative to established forms of post-War sound-fundamentals, which has made my work iconically theoretical. ",
            "To write is a natural desire, but my current compositional activity seeks to undermine all continuities. ",
            "The pursuit of binary sculpture-noises to juxtapose the mostly-post-War paradigm is a key focus of my synthetic study. ",
            "As a primarily stylistic artist, I aim to integrate the unity within triadic-possibilities, and bring forth a single polyphony that really interprets the most tense issues. ",
            "To mix is a natural desire, but my current compositional activity seeks to inform all aesthetics. ",
            "My work has been seminal in the development of ‘iconically-gestural theoretical-music’, a highly intellectual, and rather non-linear genre. ",
            "It is of paramount importance that contemporary, intellectual continuity-non-linearities must never be allowed to become discontinuous, or symbolically complex. ",
            "My style is the only one of its kind, due in part to the inclusion of highly-orchestral semitone-linearities, with a hint of so-called ‘module-melodies’. "
    };

    public final static String[] NON_BEGIN_SENTENCE_STRUCTURES = new String[]{
            "In short, the recording must never compose the notation. ",
            "In short, the timbre must never perceive the player. ",
            "It also incorporates and opposes generatively-diverse material-parts. ",
            "It must be remembered that challenging approaches, especially if they are complex (or even microtonal), should be avoided. ",
            "It also superimposes and examines neo-Romantically-literal riff-instrumentations. ",
            "It must be remembered that dominating pitches, especially if they are melodic (or even melodic), should be avoided. "
    };


    private static void addRandomElementsWithoutRepetitions(int finalSizeofList, Array<String> list, String[] source) {
        if (finalSizeofList>source.length || finalSizeofList<1){
            throw new RuntimeException("ANTIBUGGING||BullshitGenerator condition (1<=finalSizeofList<=" +
                    source.length + ") unmet because finalSizeOfList=" + finalSizeofList);
        }
        int MAXLENGTH = source.length;
        Random r = new Random();
        while (list.size < finalSizeofList) {
            String candidate = source[r.nextInt(MAXLENGTH)];
            if (!list.contains(candidate, false)) list.add(candidate);
        }
    }

    public static Array<String> generateRawSentencesArray(int numberOfSentences) {
        // 1) concat the BEGIN and the NON_BEGIN sentences to ALL_SENTENCES
        List<String> temp =  Arrays.asList(BEGIN_SENTENCE_STRUCTURES);
        //temp.addAll(Arrays.asList(NON_BEGIN_SENTENCE_STRUCTURES));
        String[] ALL_SENTENCE_STRUCTURES = temp.toArray(new String[0]);
        // 2) create and fill the list:
        Array<String> finalText = new Array<String>();
        addRandomElementsWithoutRepetitions(1, finalText, BEGIN_SENTENCE_STRUCTURES);
        addRandomElementsWithoutRepetitions(numberOfSentences, finalText, ALL_SENTENCE_STRUCTURES);
        // 4) process nouns:
        for (String s : finalText) {
            break;
        }
        // finally return text as properly formatted string
        return finalText;
    }

    public static String collapseStringList(Array<String> list) {
        return  Arrays.toString(list.toArray()).replaceAll("\\[|\\]|( ,)", "").trim();
    }

    public static String generateFormattedSentences(int numberOfSentences) {
        Array<String> list = new Array<String>();
        for (String s : generateRawSentencesArray(numberOfSentences)) list.add(formatSentence(s));
        return collapseStringList(list);
    }

    public static String formatSentence(String sentence) {
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("\\|(\\w+(-\\w+)?)\\|");
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            String formattedWord = formatWord(matcher.group());
            matcher.appendReplacement(sb, Matcher.quoteReplacement(formattedWord));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    private static String formatWord(String word) {
        Random r = new Random();
        String returnString = "";
        String[] nouns = NOUNS[r.nextInt(NOUNS.length)].split("\\|");
        String[] verbs = VERBS[r.nextInt(VERBS.length)].split("\\|");
        String[] adjAdv = ADJECTIVES[r.nextInt(ADJECTIVES.length)].split("\\|");
        switch (word) {
            case "|noun|":
                returnString = nouns[0];
                break;
            case "|noun-plural|":
                returnString = (nouns.length == 1) ? nouns[0]+"s" : nouns[1];
                break;
            case "|verb|":
                returnString = verbs[0];
                break;
            case "|verb-present|":
                returnString = verbs[1];
                break;
            case "|verb-ing|":
                returnString = verbs[2];
                break;
            case "|adjective|":
                if (r.nextInt(10)<3)  returnString = (adjAdv.length == 1) ? adjAdv[0]+"ly" : adjAdv[1];
                if (r.nextInt(10)<1)  returnString = returnString+ADJECTIVE_PREFIXES[r.nextInt(ADJECTIVE_PREFIXES.length)]+"-";
                returnString = returnString + adjAdv[0];
                break;
            case "|adverb|":
                returnString = (adjAdv.length == 1) ? adjAdv[0]+"ly" : adjAdv[1];
                break;
            default:
                returnString = word;
                break;
        }
        return returnString;
    }
}
