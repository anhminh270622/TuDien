package sample;

import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    private ArrayList<Word> words;

    public Dictionary() {
        words = new ArrayList<Word>();
    }

    public Word getWord(int i) {
        return words.get(i);
    }

    public int getLength() {
        return words.size();
    }
    Scanner Scan = new Scanner(System.in);

    public void addWord(Word newWord) {
            words.add(newWord);
    }
//    public void addWord(Word newWord) {
//        newWord.setWord_target(Scan.next().trim());
//        newWord.setWord_explain(Scan.nextLine().trim());
//        for (int i = 0; i < words.size() - 1; i++) {
//            if (newWord.getWord_target().equals(words.get(i).getWord_target())) {
//
//            } else {
//                if (newWord.getWord_target().compareTo(words.get(i).getWord_target()) > 0
//                        && newWord.getWord_target().compareTo(words.get(i + 1).getWord_target()) < 0) {
//                    words.add(i+1, newWord);
//                    return;
//                }
//            }
//        }
//        words.add(newWord);
//    }

    public void removeWord(Word word) {
            words.remove(word);
    }

//    public void removeWord(Word a) {
//       for(int i=0;i<words.size();i++){
//           if(words.get(i).equals(a.getWord_target())){
//               words.remove(words.get(i));
//           }
//       }
//    }

}