package com.mygdx.game.core;


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

    private final static String[] NOUNS = new String[]{
            "composition",
            "experience",
            "idea",
            "phrase",
            "phenomenon|phenomena"};


    private final static String[] ADJECTIVES = new String[]{
            "random",
            "traditional",
            "linear",
            "rigaudonish",
            "deconstructed",
            "gradual",
            "stockhausenesque",
            "wagnerian",
            "deep",
            "gender-biased",
            "feminist",
            "intrincated",
            "complex",

            "choreographic|choreographically"};
    private final static String[] ADJECTIVE_PREFIXES = new String[]{
            "post",
            "pre",
            "sub",
            "meta"};
    private final static String[] VERBS = new String[]{
            "dictate|dictates|dictating",
            "engage|engages|engaging",
            "incorporate|incorporates|incorporating",
            "overcome|overcomes|overcoming",
            "allow|allows|allowing"};
    private final static String[] BEGIN_SENTENCE_STRUCTURES = new String[]{
            "The |adjective| consequences of |verb-ing| |noun-plural| enables the use of a single |noun| amongst many |adjective| |noun-plural|. ",
            "By |verb-ing| in critical |verb-ing|, I seek to |verb| the existing radical models, and |verb|establish a more diametric and rhythmic paradigm. ",
            "My latest piece begins with a rather serialist ‘space-melody’, before post-serially transforming the existing passive material into a more post-Schoenbergly-structured state, a process I term ‘subtractively-multimedia-influencing’. ",
            "When planning my predominant motifs, I often find that creating a somewhat psycho-theoretical array of musical octaves helps a great deal. ",
            "Recently, I have started to embrace time-signatures as a strongly-diverse alternative to established forms of post-War sound-fundamentals, which has made my work iconically theoretical. ",
            "To write is a natural desire, but my current compositional activity seeks to undermine all continuities. ",
            "The pursuit of binary sculpture-noises to juxtapose the mostly-post-War paradigm is a key focus of my synthetic study. ",
            "As a primarily stylistic artist, I aim to integrate the unity within triadic-possibilities, and bring forth a single polyphony that really interprets the most tense issues. ",
            "To mix is a natural desire, but my current compositional activity seeks to inform all aesthetics. ",
            "My work has been seminal in the development of ‘iconically-gestural theoretical-music’, a highly intellectual, and rather non-linear genre. ",
            "It is of paramount importance that contemporary, intellectual continuity-non-linearities must never be allowed to become discontinuous, or symbolically complex. ",
            "My style is the only one of its kind, due in part to the inclusion of highly-orchestral semitone-linearities, with a hint of so-called ‘module-melodies’. ",
            " strongly believe???"

    };

    private final static String[] NON_BEGIN_SENTENCE_STRUCTURES = new String[]{
            "In short, the recording must never compose the notation. ",
            "In short, the timbre must never perceive the player. ",
            "It also incorporates and opposes generatively-diverse material-parts. ",
            "It must be remembered that challenging approaches, especially if they are complex (or even microtonal), should be avoided. ",
            "It also superimposes and examines neo-Romantically-literal riff-instrumentations. ",

            "It must be remembered that dominating pitches, especially if they are melodic (or even melodic), should be avoided. "
    };



    /*
 * 1a) For example, the NOUNS array might look like ("composition", "experience", "idea", "phrase",
 * "phenomenon|phenomena")... plus about 50 other words. Notice that nouns that can't be pluralised
 * by adding 's' are stored with a pipe symbol (|) separating the singular and plural forms so they
 * can be chosen later.
 *
 * 1b) The ADJECTIVES array might look like ("random", "traditional", "linear", "choreographic|choreographically")...
 * plus about 50 other words. Again, notice that adjectives that can't be turned into adverbs by
 * adding 'ly' have both forms manually stored.
 *
 * 1c) The ADJECTIVE_PREFIXES array might look like ("post", "pre", "sub", "meta")... and many others.
 *
 * 1d) The VERBS array has the infinitive, present, and past tenses of the verbs all manually written out,
 * with one string per verb, with pipes separating the tenses, like this:
 * ("dictate|dictates|dictating", "allow|allows|allowing")... with about 50 other verbs.
 *
 * 2) There is one array of SENTENCE_STRUCTURES, which is simply an array of strings, each of which
 * look something like this:
 * "The |adjective| consequences of |verb-ing| |noun-plural| enables the use of a single |noun| amongst
 * many |adjective| |noun-plural|."
 *
 * 3) To generate a random sentence, select one of the SENTENCE_STRUCTURES at random, and then replace
 * any instance of "|adjective|" or "|noun-plural|" (or whatever) with a randomly-selected word of the
 * appropriate type by following the steps below.
 *
 * 3a) Generating NOUNS
 * - Pick a random noun string from the array
 * - If the noun has not been split into singular and plural forms, then just return the word as it is.
 * - If there is a choice of singular and plural, then split the string and return the first 'half'.
 *
 * 3b) Generating ADJECTIVES
 * - Periodically (1 time in 10), add a prefix from the ADJECTIVE_PREFIXES array
 * - Pick a random adjective string from the array
 * - If the string contains separate adjective/adverb forms, split the string and return the first 'half'
 * - Otherwise, just return the word.
 *
 * 3c) Generating ADVERBS
 * - Generate an adjective
 * - If that adjective has a manually specified adverb form, pick that
 * - Otherwise use the adjective and append the literal string "ly"
 *
 * 3d) Generating VERBS
 * - Pick a random verb from the array
 * - Split the string into three segments, by the pipe symbol
 * - If just "verb" was chosen, return the first 1/3rd of the string
 * - If "verb-present" was chosen, return the second 1/3rd of the string
 * - If "verb-ing" was chosen, return the last 1/3rd of the string
 *
 * 4) Additional program logic:
 * The number of sentences generated per 'sitting' is hard-coded as 6.
 * Obviously, this means there must be a bare minimum of 6 sentence structures to choose from. (Mine has 50)
 * Keep track of the sentence structures used, and disallow the use of any structure already used (to maximise variety).
 */

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
        String returnString = null;
        switch (word) {
            case "|noun|":
                break;
            case "|noun-plural|":
                break;
            case "|verb|":
                break;
            case "|verb-s|":
                break;
            case "|verb-ing|":
                break;
            case "|adjective|":
                break;
            default:
                returnString = word;
        }

        return returnString;
    }
}
