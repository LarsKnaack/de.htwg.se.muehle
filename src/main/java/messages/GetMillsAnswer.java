package messages;

import java.util.List;

public class GetMillsAnswer {

    private List<Integer> mill1, mill2;

    public GetMillsAnswer(List<Integer> mill1, List<Integer> mill2) {
        this.mill1 = mill1;
        this.mill2 = mill2;
    }

    public List<Integer> getMill1() {
        return mill1;
    }

    public List<Integer> getMill2() {
        return mill2;
    }
}
