import java.util.*;

public class Coder {
    Map<Character, Integer> hz = new HashMap<>();
    List<Map.Entry<Character, Integer>> q = new ArrayList<>();
    //итоговая кодировка (Код - Его символ)
    Map<String, Character> huffmanCode = new HashMap<>();
    Map<Character, String> huffmanDecode = new HashMap<>();
    String curStr = "";
    String decodedStr = "";
    String codedStr = "";
    class QComparator implements Comparator<Map.Entry<Character, Integer>>{
        public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
            if (o1.getValue() < o2.getValue())
                return -1;
            else if (o1.getValue() > o2.getValue())
                return 1;
            return 0;
        }
    }

    public Map<String, Character> getHuffmanCode(String str) {
        if (!curStr.equals(str)) {
            curStr = str;
            solve(str);
        }
        return huffmanCode;
    }

    public Map<Character, String> getHuffmanDecode(String str) {
        if (!curStr.equals(str)) {
            curStr = str;
            solve(str);
        }
        return huffmanDecode;
    }

    public String getCurStr() {
        return curStr;
    }

    public String getDecodedStr(String str, String code) {
        decodeStr(str, code);
        return decodedStr;
    }

    public void solve(String str) {
        huffmanCode.clear();
        huffmanDecode.clear();
        hz.clear();
        q.clear();
        makeHzMap(str);
        int deep = calcDeep(hz.size());
        q.sort(new QComparator());
        if (hz.size() <= Math.pow(2, deep) - Math.pow(2, deep - 1) / 2) {
            int cnt = (int)Math.pow(2, deep) - (int)Math.pow(2, deep - 1) / 2;
            int semiDeep = deep - 1;
            int startCode = toNormal(semiDeep);
            int forI = (int)Math.pow(2, deep) - cnt;
            while(forI-- > 0) {
                Map.Entry<Character, Integer> last = q.get(q.size() - 1);
                q.remove(q.size() - 1);
                huffmanCode.put(toBinary(startCode, semiDeep), last.getKey());
                huffmanDecode.put(last.getKey(), toBinary(startCode, semiDeep));
                startCode--;
            }
        }

        int startCode = 0;
        while(q.size() != 0) {
            Map.Entry<Character, Integer> node = q.get(0);
            q.remove(0);
            huffmanCode.put(toBinary(startCode, deep), node.getKey());
            huffmanDecode.put(node.getKey(), toBinary(startCode, deep));
            startCode++;
        }
        for (Map.Entry<String, Character> pair : huffmanCode.entrySet()) {
            System.out.println(pair.getValue() + " - " + pair.getKey());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < curStr.length(); i++) {
            if (huffmanDecode.containsKey(curStr.charAt(i))) {
                stringBuilder.append(huffmanDecode.get(curStr.charAt(i)));
            }
            if (curStr.charAt(i) == ' ') {
                stringBuilder.append(' ');
            }
        }
        codedStr = stringBuilder.toString();
    }

    public void decodeStr(String str, String code) {
        huffmanCode.clear();
        huffmanDecode.clear();
        String[] arr = code.split("\n");
        String arr0 = "";
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].replace("\r", "");
            huffmanCode.put(arr[i].split(" - ")[1], arr[i].split(" - ")[0].charAt(0));
            huffmanDecode.put(arr[i].split(" - ")[0].charAt(0), arr[i].split(" - ")[1]);
        }
        StringBuilder curStr = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            curStr.append(str.charAt(i));
            if (curStr.toString().equals(" ")) {
                result.append(" ");
                curStr = new StringBuilder();
            }
            if (huffmanCode.containsKey(curStr.toString())) {
                result.append(huffmanCode.get(curStr.toString()));
                curStr = new StringBuilder();
            }
        }

        decodedStr = result.toString();
    }

    public void makeHzMap(String str) {
        for(int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') continue;
            if (hz.containsKey(str.charAt(i))) {
                hz.replace(str.charAt(i), hz.get(str.charAt(i)) + 1);
            } else {
                hz.put(str.charAt(i), 1);
            }
        }
        for (Map.Entry<Character, Integer> m : hz.entrySet()) {
            q.add(new AbstractMap.SimpleEntry<>(m.getKey(), m.getValue()));
        }
    }

    private int calcDeep(int size) {
        int deep = 0;
        int num = 1;
        while (num < size) {
            num *= 2;
            deep++;
        }
        return deep;
    }

    private String toBinary(int n, int size) {
        StringBuilder s = new StringBuilder();
        size = Math.max(size, 1);
        while(n > 0) {
            s.append(n % 2);
            n /= 2;
        }
        while(s.length() < size) {
            s.append(0);
        }
        return s.reverse().toString();
    }

    private int toNormal(int cnt) {
        int ans = 0;
        while (cnt-- >= 0) {
            ans += (int) Math.pow(2, cnt);
        }
        return ans;
    }
}
