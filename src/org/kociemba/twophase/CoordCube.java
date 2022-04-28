package org.kociemba.twophase;


class CoordCube {

    static final short N_TWIST = 2187;
    static final short N_FLIP = 2048;
    static final short N_SLICE1 = 495;
    static final short N_SLICE2 = 24;
    static final short N_PARITY = 2;
    static final short N_URFtoDLF = 20160;
    static final short N_FRtoBR = 11880;
    static final short N_URtoUL = 1320;
    static final short N_UBtoDF = 1320;
    static final short N_URtoDF = 20160;

    static final int N_URFtoDLB = 40320;
    static final int N_URtoBR = 479001600;

    static final short N_MOVE = 18;


    short twist;
    short flip;
    short parity;
    short FRtoBR;
    short URFtoDLF;
    short URtoUL;
    short UBtoDF;
    int URtoDF;


    CoordCube(CubieCube c) {
        twist = c.getTwist();
        flip = c.getFlip();
        parity = c.cornerParity();
        FRtoBR = c.getFRtoBR();
        URFtoDLF = c.getURFtoDLF();
        URtoUL = c.getURtoUL();
        UBtoDF = c.getUBtoDF();
        URtoDF = c.getURtoDF();
    }

    static short[][] twistMove = new short[N_TWIST][N_MOVE];

    static {
        CubieCube a = new CubieCube();
        for (short i = 0; i < N_TWIST; i++) {
            a.setTwist(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    a.cornerMultiply(CubieCube.moveCube[j]);
                    twistMove[i][3 * j + k] = a.getTwist();
                }
                a.cornerMultiply(CubieCube.moveCube[j]);
            }
        }
    }

    static short[][] flipMove = new short[N_FLIP][N_MOVE];

    static {
        CubieCube a = new CubieCube();
        for (short i = 0; i < N_FLIP; i++) {
            a.setFlip(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    a.edgeMultiply(CubieCube.moveCube[j]);
                    flipMove[i][3 * j + k] = a.getFlip();
                }
                a.edgeMultiply(CubieCube.moveCube[j]);

            }
        }
    }


    static short[][] parityMove = {{1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1},
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0}};


    static short[][] FRtoBR_Move = new short[N_FRtoBR][N_MOVE];

    static {
        CubieCube a = new CubieCube();
        for (short i = 0; i < N_FRtoBR; i++) {
            a.setFRtoBR(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    a.edgeMultiply(CubieCube.moveCube[j]);
                    FRtoBR_Move[i][3 * j + k] = a.getFRtoBR();
                }
                a.edgeMultiply(CubieCube.moveCube[j]);
            }
        }
    }


    static short[][] URFtoDLF_Move = new short[N_URFtoDLF][N_MOVE];

    static {
        CubieCube a = new CubieCube();
        for (short i = 0; i < N_URFtoDLF; i++) {
            a.setURFtoDLF(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    a.cornerMultiply(CubieCube.moveCube[j]);
                    URFtoDLF_Move[i][3 * j + k] = a.getURFtoDLF();
                }
                a.cornerMultiply(CubieCube.moveCube[j]);
            }
        }
    }


    static short[][] URtoDF_Move = new short[N_URtoDF][N_MOVE];

    static {
        CubieCube a = new CubieCube();
        for (short i = 0; i < N_URtoDF; i++) {
            a.setURtoDF(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    a.edgeMultiply(CubieCube.moveCube[j]);
                    URtoDF_Move[i][3 * j + k] = (short) a.getURtoDF();


                }
                a.edgeMultiply(CubieCube.moveCube[j]);
            }
        }
    }


    static short[][] URtoUL_Move = new short[N_URtoUL][N_MOVE];

    static {
        CubieCube a = new CubieCube();
        for (short i = 0; i < N_URtoUL; i++) {
            a.setURtoUL(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    a.edgeMultiply(CubieCube.moveCube[j]);
                    URtoUL_Move[i][3 * j + k] = a.getURtoUL();
                }
                a.edgeMultiply(CubieCube.moveCube[j]);
            }
        }
    }


    static short[][] UBtoDF_Move = new short[N_UBtoDF][N_MOVE];

    static {
        CubieCube a = new CubieCube();
        for (short i = 0; i < N_UBtoDF; i++) {
            a.setUBtoDF(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    a.edgeMultiply(CubieCube.moveCube[j]);
                    UBtoDF_Move[i][3 * j + k] = a.getUBtoDF();
                }
                a.edgeMultiply(CubieCube.moveCube[j]);
            }
        }
    }


    static short[][] MergeURtoULandUBtoDF = new short[336][336];

    static {


        for (short uRtoUL = 0; uRtoUL < 336; uRtoUL++) {
            for (short uBtoDF = 0; uBtoDF < 336; uBtoDF++) {
                MergeURtoULandUBtoDF[uRtoUL][uBtoDF] = (short) CubieCube.getURtoDF(uRtoUL, uBtoDF);
            }
        }
    }


    static byte[] Slice_URFtoDLF_Parity_Prun = new byte[N_SLICE2 * N_URFtoDLF * N_PARITY / 2];

    static {
        for (int i = 0; i < N_SLICE2 * N_URFtoDLF * N_PARITY / 2; i++)
            Slice_URFtoDLF_Parity_Prun[i] = -1;
        int depth = 0;
        setPruning(Slice_URFtoDLF_Parity_Prun, 0, (byte) 0);
        int done = 1;
        while (done != N_SLICE2 * N_URFtoDLF * N_PARITY) {
            for (int i = 0; i < N_SLICE2 * N_URFtoDLF * N_PARITY; i++) {
                int parity = i % 2;
                int URFtoDLF = (i / 2) / N_SLICE2;
                int slice = (i / 2) % N_SLICE2;
                if (getPruning(Slice_URFtoDLF_Parity_Prun, i) == depth) {
                    for (int j = 0; j < 18; j++) {
                        switch (j) {
                            case 3:
                            case 5:
                            case 6:
                            case 8:
                            case 12:
                            case 14:
                            case 15:
                            case 17:
                                continue;
                            default:
                                int newSlice = FRtoBR_Move[slice][j];
                                int newURFtoDLF = URFtoDLF_Move[URFtoDLF][j];
                                int newParity = parityMove[parity][j];
                                if (getPruning(Slice_URFtoDLF_Parity_Prun, (N_SLICE2 * newURFtoDLF + newSlice) * 2 + newParity) == 0x0f) {
                                    setPruning(Slice_URFtoDLF_Parity_Prun, (N_SLICE2 * newURFtoDLF + newSlice) * 2 + newParity,
                                            (byte) (depth + 1));
                                    done++;
                                }
                        }
                    }
                }
            }
            depth++;
        }
    }


    static byte[] Slice_URtoDF_Parity_Prun = new byte[N_SLICE2 * N_URtoDF * N_PARITY / 2];

    static {
        for (int i = 0; i < N_SLICE2 * N_URtoDF * N_PARITY / 2; i++)
            Slice_URtoDF_Parity_Prun[i] = -1;
        int depth = 0;
        setPruning(Slice_URtoDF_Parity_Prun, 0, (byte) 0);
        int done = 1;
        while (done != N_SLICE2 * N_URtoDF * N_PARITY) {
            for (int i = 0; i < N_SLICE2 * N_URtoDF * N_PARITY; i++) {
                int parity = i % 2;
                int URtoDF = (i / 2) / N_SLICE2;
                int slice = (i / 2) % N_SLICE2;
                if (getPruning(Slice_URtoDF_Parity_Prun, i) == depth) {
                    for (int j = 0; j < 18; j++) {
                        switch (j) {
                            case 3:
                            case 5:
                            case 6:
                            case 8:
                            case 12:
                            case 14:
                            case 15:
                            case 17:
                                continue;
                            default:
                                int newSlice = FRtoBR_Move[slice][j];
                                int newURtoDF = URtoDF_Move[URtoDF][j];
                                int newParity = parityMove[parity][j];
                                if (getPruning(Slice_URtoDF_Parity_Prun, (N_SLICE2 * newURtoDF + newSlice) * 2 + newParity) == 0x0f) {
                                    setPruning(Slice_URtoDF_Parity_Prun, (N_SLICE2 * newURtoDF + newSlice) * 2 + newParity,
                                            (byte) (depth + 1));
                                    done++;
                                }
                        }
                    }
                }
            }
            depth++;
        }
    }


    static byte[] Slice_Twist_Prun = new byte[N_SLICE1 * N_TWIST / 2 + 1];

    static {
        for (int i = 0; i < N_SLICE1 * N_TWIST / 2 + 1; i++)
            Slice_Twist_Prun[i] = -1;
        int depth = 0;
        setPruning(Slice_Twist_Prun, 0, (byte) 0);
        int done = 1;
        while (done != N_SLICE1 * N_TWIST) {
            for (int i = 0; i < N_SLICE1 * N_TWIST; i++) {
                int twist = i / N_SLICE1, slice = i % N_SLICE1;
                if (getPruning(Slice_Twist_Prun, i) == depth) {
                    for (int j = 0; j < 18; j++) {
                        int newSlice = FRtoBR_Move[slice * 24][j] / 24;
                        int newTwist = twistMove[twist][j];
                        if (getPruning(Slice_Twist_Prun, N_SLICE1 * newTwist + newSlice) == 0x0f) {
                            setPruning(Slice_Twist_Prun, N_SLICE1 * newTwist + newSlice, (byte) (depth + 1));
                            done++;
                        }
                    }
                }
            }
            depth++;
        }
    }


    static byte[] Slice_Flip_Prun = new byte[N_SLICE1 * N_FLIP / 2];

    static {
        for (int i = 0; i < N_SLICE1 * N_FLIP / 2; i++)
            Slice_Flip_Prun[i] = -1;
        int depth = 0;
        setPruning(Slice_Flip_Prun, 0, (byte) 0);
        int done = 1;
        while (done != N_SLICE1 * N_FLIP) {
            for (int i = 0; i < N_SLICE1 * N_FLIP; i++) {
                int flip = i / N_SLICE1, slice = i % N_SLICE1;
                if (getPruning(Slice_Flip_Prun, i) == depth) {
                    for (int j = 0; j < 18; j++) {
                        int newSlice = FRtoBR_Move[slice * 24][j] / 24;
                        int newFlip = flipMove[flip][j];
                        if (getPruning(Slice_Flip_Prun, N_SLICE1 * newFlip + newSlice) == 0x0f) {
                            setPruning(Slice_Flip_Prun, N_SLICE1 * newFlip + newSlice, (byte) (depth + 1));
                            done++;
                        }
                    }
                }
            }
            depth++;
        }
    }


    static void setPruning(byte[] table, int index, byte value) {
        if ((index & 1) == 0)
            table[index / 2] &= 0xf0 | value;
        else
            table[index / 2] &= 0x0f | (value << 4);
    }


    static byte getPruning(byte[] table, int index) {
        if ((index & 1) == 0)
            return (byte) (table[index / 2] & 0x0f);
        else
            return (byte) ((table[index / 2] & 0xf0) >>> 4);
    }
}
