package Mino;

import java.awt.Color;

public class Mino_Z1 extends Mino {

    public Mino_Z1() {
        create(Color.red);
    }

    @Override
    public void setXY(int x, int y) {
        // Initialisation :
        //     [2]
        // [0][1]
        //     [3]

        b[0].x = x - Block.SIZE;
        b[0].y = y;

        b[1].x = x;
        b[1].y = y;

        b[2].x = x;
        b[2].y = y - Block.SIZE;

        b[3].x = x + Block.SIZE;
        b[3].y = y - Block.SIZE;
    }

    @Override
    public void getDirection1() {
        // Forme en Z horizontale
        // [2]
        // [0][1]
        //    [3]

        tempB[0].x = b[1].x - Block.SIZE;
        tempB[0].y = b[1].y;

        tempB[1].x = b[1].x;
        tempB[1].y = b[1].y;

        tempB[2].x = b[1].x;
        tempB[2].y = b[1].y - Block.SIZE;

        tempB[3].x = b[1].x + Block.SIZE;
        tempB[3].y = b[1].y - Block.SIZE;

        updateXY(1);
    }

    @Override
    public void getDirection2() {
        // Forme en Z verticale
        //   [2][3]
        //   [1]
        //   [0]

        tempB[0].x = b[1].x;
        tempB[0].y = b[1].y + Block.SIZE;

        tempB[1].x = b[1].x;
        tempB[1].y = b[1].y;

        tempB[2].x = b[1].x - Block.SIZE;
        tempB[2].y = b[1].y;

        tempB[3].x = b[1].x - Block.SIZE;
        tempB[3].y = b[1].y - Block.SIZE;

        updateXY(2);
    }

    @Override
    public void getDirection3() {
        // Identique à direction 1
        getDirection1();
    }

    @Override
    public void getDirection4() {
        // Identique à direction 2
        getDirection2();
    }
}

