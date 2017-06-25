/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package models.impl;

import akka.actor.UntypedAbstractActor;
import messages.GetMillsAnswer;
import messages.GetMillsRequest;
import models.IMills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 * 1----------2-----------3
 * |          |           |
 * |   9------10------11  |
 * |   |      |       |   |
 * |   |  17--18--19  |   |
 * |   |  |       |   |   |
 * 8---16-24      20--12--4
 * |   |  |       |   |   |
 * |   |  23--22--21  |   |
 * |   |      |       |   |
 * |   15-----14-----13   |
 * |          |           |
 * 7----------6-----------5

 *
 *
 * */

public class Mills extends UntypedAbstractActor implements IMills {
    private static final int NUMBERVERTEX = 24;
    private millsList millsArray[];

    public Mills() {
        millsArray = new millsList[NUMBERVERTEX];
        for (int i = 0; i < NUMBERVERTEX; i++) {
            millsArray[i] = new millsList();
        }
        fillMillsArray();
    }

    public List<Integer> getMill1(int v) {
        return millsArray[v - 1].mill1;
    }

    public List<Integer> getMill2(int v) {
        return millsArray[v - 1].mill2;
    }

    private void fillMillsArray() {
        try {
            InputStream stream = getClass().getResourceAsStream("/Millstable.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String zeile;
            while ((zeile = in.readLine()) != null) {

                String splitresult[] = zeile.split(" ");
                int temp1 = Integer.parseInt(splitresult[0]);
                int temp2 = Integer.parseInt(splitresult[1]);
                if (zeile.contains("mill1")) {
                    millsArray[temp1].mill1.add(temp2);
                }
                if (zeile.contains("mill2")) {
                    millsArray[temp1].mill2.add(temp2);
                }

            }
            in.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        int vertex = ((GetMillsRequest) message).getVertex();
        GetMillsAnswer answer = new GetMillsAnswer(millsArray[vertex - 1].mill1, millsArray[vertex - 1].mill2);
        getSender().tell(answer, getSelf());
    }

    class millsList {
        private List<Integer> mill1 = new ArrayList<Integer>();
        private List<Integer> mill2 = new ArrayList<Integer>();
    }
}