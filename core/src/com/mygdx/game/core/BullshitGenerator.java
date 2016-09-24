package com.mygdx.game.core;


import com.badlogic.gdx.utils.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by afr on 23.09.16.
 * This class is a version of the famous "Contemporary Classical Composer's Bullshit Generator",
 * made by the composer and pianist Dominic Irving (http://www.dominicirving.com/).
 * We are very thankful to him for sharing the following algorithm. As the rest of this project,
 * this class is released under the GPLv3 license. Run it for a test, or check its main function!
 *
 * 1) There are four arrays of strings to hold the individual words: one array each for nouns, adjectives, adjective-prefixes, and verbs.
 *
 * 1a) For example, the NOUNS array might look like ("composition", "experience", "idea", "phrase", "phenomenon|phenomena")... plus about 50 other words. Notice that nouns that can't be pluralised by adding 's' are stored with a pipe symbol (|) separating the singular and plural forms so they can be chosen later.
 *
 * 1b) The ADJECTIVES array might look like ("random", "traditional", "linear", "choreographic|choreographically")... plus about 50 other words. Again, notice that adjectives that can't be turned into adverbs by adding 'ly' have both forms manually stored.
 *
 * 1c) The ADJECTIVE_PREFIXES array might look like ("post", "pre", "sub", "meta")... and many others.
 *
 * 1d) The VERBS array has the infinitive, present, and past tenses of the verbs all manually written out, with one string per verb, with pipes separating the tenses, like this:
 * ("dictate|dictates|dictating", "allow|allows|allowing")... with about 50 other verbs.
 *
 * 2) There is one array of SENTENCE_STRUCTURES, which is simply an array of strings, each of which look something like this:
 * "The |adjective| consequences of |verb-ing| |noun-plural| enables the use of a single |noun| amongst many |adjective| |noun-plural|."
 *
 * 3) To generate a random sentence, select one of the SENTENCE_STRUCTURES at random, and then replace any instance of "|adjective|" or "|noun-plural|" (or whatever) with a randomly-selected word of the appropriate type by following the steps below.
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


public abstract class BullshitGenerator {
    private BullshitGenerator(){} // prevent the abstract class to be instantiated

    public final static String[] NOUNS = new String[]{
            "activity|activities",
            "aesthetic",
            "alter-ego",
            "alternative",
            "analogy|analogies",
            "approach|approaches",
            "arbitrariness|arbitrarinesses",
            "array",
            "composition",
            "concept",
            "continuity|continuities",
            "contradiction",
            "experience",
            "figuration",
            "form",
            "genre",
            "homophony|homophonies",
            "idea",
            "impossibility|impossibilities",
            "instrumentation",
            "issue",
            "linearity|linearities",
            "manifold",
            "material",
            "medium|media",
            "melody|melodies",
            "meta-layer",
            "meta-level",
            "micropolyphony|micropolyphonies",
            "model",
            "module",
            "motif",
            "notation",
            "onthology|onthologies",
            "paradigm",
            "part",
            "performance",
            "permutation",
            "phenomenon|phenomena",
            "phrase",
            "pitch-class-set",
            "player",
            "polyphony|polyphonies",
            "possibility|possibilities",
            "post-tonality|post-tonalities",
            "proto-melody|proto-melodies",
            "randomness|randomnesses",
            "recording",
            "representation",
            "riff",
            "sculpture-noise",
            "sound-fundamental",
            "spectrum|spectra",
            "state",
            "study|studies",
            "style",
            "synergy|sinergies",
            "tautology|tautologies",
            "tension",
            "texture",
            "thematization",
            "timbre",
            "time-signature",
            "transformation",
            "unity|unities",
            "work",
            "‘Gesamtkunstwerk’",
            "‘Gestalt’",
            "‘Leitmotiv’",
            "‘Sprechgesang’",
            "‘Zeitgeist’",
            "‘Zukunftsmusik’",
            "‘aha-experience’",
            "‘space-melody’|‘space-melodies’"
    };


    public final static String[] ADJECTIVES = new String[]{
            "Schoenbergly-structured",
            "arbitrary|arbitrarily",
            "binary|binarily",
            "choreographical",
            "complex|complex",
            "compositional",
            "contemporary|contemporarily",
            "counter-cultural",
            "critical",
            "deconstructed",
            "dialectic|dialectically",
            "diametric|diametrically",
            "discontinuous",
            "diverse",
            "established",
            "extreme",
            "fantastical",
            "figurative",
            "generative",
            "gestural",
            "gradual",
            "iconical",
            "instrumental",
            "intellectual",
            "intrincated",
            "linear",
            "literal",
            "melodic|melodically",
            "metaphisical",
            "metaphoric|metaphorically",
            "microtonal",
            "modern",
            "multimedia|multimedially",
            "musical",
            "natural",
            "non-linear",
            "orchestral",
            "paradigmatic|paradigmatically",
            "passive",
            "post-War|post-War",
            "predominant",
            "primary|primarily",
            "radical",
            "random",
            "representative",
            "rhythmic|rythmically",
            "romantical",
            "serialist|serially",
            "spectral",
            "stockhausenesque",
            "sturmunddrang-ish",
            "style-biased",
            "stylistic|stylistically",
            "subtractive",
            "symbolic|symbolically",
            "synthetic|synthetically",
            "tense",
            "thematical",
            "theoretical",
            "tonal",
            "traditional",
            "transversal",
            "triadic|triadically"
    };


    public final static String[] PREFIXES = new String[]{
            "a-priori",
            "anti",
            "circa",
            "endo",
            "intra",
            "macro",
            "meta",
            "micro",
            "mostly",
            "multi",
            "non",
            "para",
            "post",
            "pre",
            "pro",
            "proto",
            "pseudo",
            "psycho",
            "quasi",
            "strongly",
            "sub",
            "tetra",
            "über"
            };
    public final static String[] VERBS = new String[]{
            "achieve|achieves|achieving",
            "alienate|alienates|alienating",
            "allow|allows|allowing",
            "challenge|challenges|challenging",
            "concentrate|concentrates|concentrating",
            "contract|contracts|contracting",
            "create|creates|creating",
            "cultivate|cultivates|cultivating",
            "derivate|derivates|derivating",
            "develop|develops|developing",
            "dictate|dictates|dictating",
            "discover|discovers|discovering",
            "distribute|distributes|distributing",
            "dominate|dominates|dominating",
            "embrace|embraces|embracing",
            "endeavour|endeavours|endeavouring",
            "engage|engages|engaging",
            "establish|establishes|establishing",
            "examine|examines|examining",
            "expand|expands|expanding",
            "include|includes|including",
            "incorporate|incorporates|incorporating",
            "influence|influences|influencing",
            "inform|informs|informing",
            "integrate|integrates|integrating",
            "interpret|interprets|interpreting",
            "juxtapose|juxtaposes|juxtaposing",
            "mix|mixes|mixing",
            "oppose|opposes|opposing",
            "overcome|overcomes|overcoming",
            "plan|plans|planning",
            "regard|regards|regarding",
            "superimpose|superimposes|superimposing",
            "tackle|tackles|tackling",
            "transform|transforms|transforming",
            "undermine|undermines|undermining",
            "write|writes|writing"
           };
    public final static String[] BEGIN_SENTENCE_STRUCTURES = new String[]{
            "As a |adverb| |adjective| artist, I aim to |verb-present| the |noun| within |adjective|-|noun-plural|, and bring forth a single |noun| that really |verb-present| the most |adjective| |noun-both|.",
            "By |verb-ing| in |adjective| |verb-ing|, I seek to |verb| the existing |adjective| |noun-plural|, and |verb| a more |adjective| and |adjective| |noun|.",
            "It is of paramount importance that |adjective|, |adjective| |noun|-|noun-plural| must never be allowed to become |adjective|, or |adverb| |adjective|.",
            "My latest piece begins with a rather |adjective| |noun|, before |adverb| |verb-ing| the existing |adjective| |noun-both| into a more |adjective| |noun|, a process I term ‘|adverb|-|adjective|-|verb-ing|’.",
            "My work has been seminal in the development of ‘|adjective| |adjective|-music’, a highly |adjective|, and rather |adjective| genre.",
            "My |noun| is the only one of its kind, due in part to the fact that it |verb-present| |adjective| |noun|-|noun-plural|, with a hint of so-called ‘|noun|-|noun-plural|’.",
            "Recently, I have started to embrace |noun-plural| as a |adjective| |noun| to |adjective| forms of |adjective| |noun-plural|, which has made my |noun-plural| |adverb| |adjective|.",
            "The pursuit of |adjective| |noun-plural| to |verb| the |adjective| |noun| is a key focus of my |adjective| |noun-both|.",
            "The |adjective| consequences of |verb-ing| |noun-plural| enables the use of a single |noun| amongst many |adjective| |noun-plural|.",
            "To |verb| is a natural desire, but this specifical |adjective| |noun| seeks to |verb| all |noun-plural|.",
            "When |verb-ing| my |adjective| |noun|, I often find that |verb-ing| a somewhat |adjective| |noun| of |adjective| |noun-plural| helps a great deal.",
          };

    public final static String[] NON_BEGIN_SENTENCE_STRUCTURES = new String[]{
            "Although not every |noun| of |noun-plural| |verb-present| that way (except maybe |noun-plural| or |adjective| |noun-plural|).",
            "In short, the |noun-both| must never |verb| the |noun-both|.",
            "It also |verb-present| and |verb-present| |adverb|-|adjective| |noun|-|noun-plural|.",
            "It must be remembered that |verb-ing| |noun-plural|, especially if they are |adjective| (or even |adjective|), should be avoided.",
            "That said, all |noun-plural| |verb| similar |noun-plural|."
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
        // 1) concat the BEGIN and the NON_BEGIN sentences to allSentencesArray
        Array<String> allSentencesList = new Array<>();
        for (String s : BEGIN_SENTENCE_STRUCTURES) allSentencesList.add(s);
        for (String s : NON_BEGIN_SENTENCE_STRUCTURES) allSentencesList.add(s);
        String[] allSentencesArray = allSentencesList.toArray(String.class);
        // 2) create and fill the list:
        Array<String> finalText = new Array<String>();
        addRandomElementsWithoutRepetitions(1, finalText, BEGIN_SENTENCE_STRUCTURES);
        addRandomElementsWithoutRepetitions(numberOfSentences, finalText, allSentencesArray);
        // 4) process nouns:
        for (String s : finalText) {
            break;
        }
        // finally return text as properly formatted string
        return finalText;
    }

    public static String collapseStringList(Array<String> list) {
        String temp = Arrays.toString(list.toArray());
        temp = temp.replaceAll("\\[|\\]", "");
        temp = temp.replaceAll("\\.,", ".");
        return  temp.trim();
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
                if (r.nextInt(30)<1)  returnString = returnString+PREFIXES[r.nextInt(PREFIXES.length)]+"-";
                returnString = nouns[0];
                break;
            case "|noun-plural|":
                if (r.nextInt(30)<1)  returnString = returnString+PREFIXES[r.nextInt(PREFIXES.length)]+"-";
                returnString = (nouns.length == 1) ? nouns[0]+"s" : nouns[1];
                break;
            case "|noun-both|":
                returnString = (r.nextBoolean()) ? formatWord("|noun|") : formatWord("|noun-plural|");
            case "|verb|":
                if (r.nextInt(30)<1)  returnString = returnString+PREFIXES[r.nextInt(PREFIXES.length)]+"-";
                returnString = verbs[0];
                break;
            case "|verb-present|":
                if (r.nextInt(30)<1)  returnString = returnString+PREFIXES[r.nextInt(PREFIXES.length)]+"-";
                returnString = verbs[1];
                break;
            case "|verb-ing|":
                if (r.nextInt(30)<1)  returnString = returnString+PREFIXES[r.nextInt(PREFIXES.length)]+"-";
                returnString = verbs[2];
                break;
            case "|adjective|":
                if (r.nextInt(10)<3) returnString = formatWord("|adverb|")+" ";
                if (r.nextInt(10)<1)  returnString = returnString+ PREFIXES[r.nextInt(PREFIXES.length)]+"-";
                returnString = returnString + adjAdv[0];
                break;
            case "|adverb|":
                if (r.nextInt(10)<1)  returnString = returnString+PREFIXES[r.nextInt(PREFIXES.length)]+"-";
                returnString = (adjAdv.length == 1) ? adjAdv[0]+"ly" : adjAdv[1];
                break;
            default:
                throw new RuntimeException("BullshitGenerator.formatWord parse error: unknown keyword " + word);
        }
        return returnString;
    }


    public static void main(String[] args){
        for (int i = 0; i<5; i++) {
            System.out.println("\n" + BullshitGenerator.generateFormattedSentences(5));
        }
    }

}
