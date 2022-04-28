package org.kociemba.twophase;


public class Search {

    static int[] ax = new int[31];
    static int[] po = new int[31];

    static int[] flip = new int[31];
    static int[] twist = new int[31];
    static int[] slice = new int[31];

    static int[] parity = new int[31];
    static int[] URFtoDLF = new int[31];
    static int[] FRtoBR = new int[31];
    static int[] URtoUL = new int[31];
    static int[] UBtoDF = new int[31];
    static int[] URtoDF = new int[31];

    static int[] minDistPhase1 = new int[31];
    static int[] minDistPhase2 = new int[31];


    public static String solution(String facelets, int maxDepth, int maxtime) {
        int s;


        int[] count = new int[6];
        try {
            for (int i = 0; i < 54; i++)
                count[EnumColor.valueOf(facelets.substring(i, i + 1)).ordinal()]++;
        } catch (Exception e) {
            return "Error 1";
        }
        for (int i = 0; i < 6; i++)
            if (count[i] != 9)
                return "Error 1";
        CubieCube cc = new FaceCube(facelets).toCubieCube();
        if ((s = cc.verify()) != 0)
            return "Error " + Math.abs(s);


        CoordCube c = new CoordCube(cc);

        po[0] = 0;
        ax[0] = 0;
        flip[0] = c.flip;
        twist[0] = c.twist;
        parity[0] = c.parity;
        slice[0] = c.FRtoBR / 24;
        URFtoDLF[0] = c.URFtoDLF;
        FRtoBR[0] = c.FRtoBR;
        URtoUL[0] = c.URtoUL;
        UBtoDF[0] = c.UBtoDF;

        minDistPhase1[1] = 1;
        int mv, n = 0;
        boolean busy = false;
        int depthPhase1 = 1;

        long tStart = System.currentTimeMillis();

        do {
            do {
                if ((depthPhase1 - n > minDistPhase1[n + 1]) && !busy) {

                    if (ax[n] == 0 || ax[n] == 3)
                        ax[++n] = 1;
                    else
                        ax[++n] = 0;
                    po[n] = 1;
                } else if (++po[n] > 3) {
                    do {
                        if (++ax[n] > 5) {
                            if (System.currentTimeMillis() - tStart > maxtime << 10)
                                return "Error 8";
                            if (n == 0) {
                                if (depthPhase1 >= maxDepth)
                                    return "Error 7";
                                else {
                                    depthPhase1++;
                                    ax[n] = 0;
                                    po[n] = 1;
                                    busy = false;
                                    break;
                                }
                            } else {
                                n--;
                                busy = true;
                                break;
                            }

                        } else {
                            po[n] = 1;
                            busy = false;
                        }
                    } while (n != 0 && (ax[n - 1] == ax[n] || ax[n - 1] - 3 == ax[n]));
                } else
                    busy = false;
            } while (busy);


            mv = 3 * ax[n] + po[n] - 1;
            flip[n + 1] = CoordCube.flipMove[flip[n]][mv];
            twist[n + 1] = CoordCube.twistMove[twist[n]][mv];
            slice[n + 1] = CoordCube.FRtoBR_Move[slice[n] * 24][mv] / 24;
            minDistPhase1[n + 1] = Math.max(CoordCube.getPruning(CoordCube.Slice_Flip_Prun, CoordCube.N_SLICE1 * flip[n + 1]
                    + slice[n + 1]), CoordCube.getPruning(CoordCube.Slice_Twist_Prun, CoordCube.N_SLICE1 * twist[n + 1]
                    + slice[n + 1]));


            if (minDistPhase1[n + 1] == 0 && n >= depthPhase1 - 5) {
                minDistPhase1[n + 1] = 10;
                if (n == depthPhase1 - 1 && (s = totalDepth(depthPhase1, maxDepth)) >= 0) {
                    if (s == depthPhase1 || (ax[depthPhase1 - 1] != ax[depthPhase1] && ax[depthPhase1 - 1] != ax[depthPhase1] + 3)) {
                        String str = "";
                        for (int i = 0; i < s; i++) {
                            switch (ax[i]) {
                                case 0:
                                    str += "U";
                                    break;
                                case 1:
                                    str += "R";
                                    break;
                                case 2:
                                    str += "F";
                                    break;
                                case 3:
                                    str += "D";
                                    break;
                                case 4:
                                    str += "L";
                                    break;
                                case 5:
                                    str += "B";
                                    break;
                            }
                            switch (po[i]) {
                                case 1:
                                    str += " ";
                                    break;
                                case 2:
                                    str += "2 ";
                                    break;
                                case 3:
                                    str += "' ";
                                    break;

                            }
                        }
                        return str;
                    }
                }

            }
        } while (true);
    }


    static int totalDepth(int depthPhase1, int maxDepth) {
        int mv, d1, d2;
        int maxDepthPhase2 = Math.min(10, maxDepth - depthPhase1);
        for (int i = 0; i < depthPhase1; i++) {
            mv = 3 * ax[i] + po[i] - 1;
            URFtoDLF[i + 1] = CoordCube.URFtoDLF_Move[URFtoDLF[i]][mv];
            FRtoBR[i + 1] = CoordCube.FRtoBR_Move[FRtoBR[i]][mv];
            parity[i + 1] = CoordCube.parityMove[parity[i]][mv];
        }

        if ((d1 = CoordCube.getPruning(CoordCube.Slice_URFtoDLF_Parity_Prun,
                (CoordCube.N_SLICE2 * URFtoDLF[depthPhase1] + FRtoBR[depthPhase1]) * 2 + parity[depthPhase1])) > maxDepthPhase2)
            return -1;

        for (int i = 0; i < depthPhase1; i++) {
            mv = 3 * ax[i] + po[i] - 1;
            URtoUL[i + 1] = CoordCube.URtoUL_Move[URtoUL[i]][mv];
            UBtoDF[i + 1] = CoordCube.UBtoDF_Move[UBtoDF[i]][mv];
        }
        URtoDF[depthPhase1] = CoordCube.MergeURtoULandUBtoDF[URtoUL[depthPhase1]][UBtoDF[depthPhase1]];

        if ((d2 = CoordCube.getPruning(CoordCube.Slice_URtoDF_Parity_Prun,
                (CoordCube.N_SLICE2 * URtoDF[depthPhase1] + FRtoBR[depthPhase1]) * 2 + parity[depthPhase1])) > maxDepthPhase2)
            return -1;

        if ((minDistPhase2[depthPhase1] = Math.max(d1, d2)) == 0)
            return depthPhase1;


        int depthPhase2 = 1;
        int n = depthPhase1;
        boolean busy = false;
        po[depthPhase1] = 0;
        ax[depthPhase1] = 0;
        minDistPhase2[n + 1] = 1;

        do {
            do {
                if ((depthPhase1 + depthPhase2 - n > minDistPhase2[n + 1]) && !busy) {

                    if (ax[n] == 0 || ax[n] == 3) {
                        ax[++n] = 1;
                        po[n] = 2;
                    } else {
                        ax[++n] = 0;
                        po[n] = 1;
                    }
                } else if ((ax[n] == 0 || ax[n] == 3) ? (++po[n] > 3) : ((po[n] = po[n] + 2) > 3)) {
                    do {
                        if (++ax[n] > 5) {
                            if (n == depthPhase1) {
                                if (depthPhase2 >= maxDepthPhase2)
                                    return -1;
                                else {
                                    depthPhase2++;
                                    ax[n] = 0;
                                    po[n] = 1;
                                    busy = false;
                                    break;
                                }
                            } else {
                                n--;
                                busy = true;
                                break;
                            }

                        } else {
                            if (ax[n] == 0 || ax[n] == 3)
                                po[n] = 1;
                            else
                                po[n] = 2;
                            busy = false;
                        }
                    } while (n != depthPhase1 && (ax[n - 1] == ax[n] || ax[n - 1] - 3 == ax[n]));
                } else
                    busy = false;
            } while (busy);

            mv = 3 * ax[n] + po[n] - 1;

            URFtoDLF[n + 1] = CoordCube.URFtoDLF_Move[URFtoDLF[n]][mv];
            FRtoBR[n + 1] = CoordCube.FRtoBR_Move[FRtoBR[n]][mv];
            parity[n + 1] = CoordCube.parityMove[parity[n]][mv];
            URtoDF[n + 1] = CoordCube.URtoDF_Move[URtoDF[n]][mv];

            minDistPhase2[n + 1] = Math.max(CoordCube.getPruning(CoordCube.Slice_URtoDF_Parity_Prun, (CoordCube.N_SLICE2
                    * URtoDF[n + 1] + FRtoBR[n + 1])
                    * 2 + parity[n + 1]), CoordCube.getPruning(CoordCube.Slice_URFtoDLF_Parity_Prun, (CoordCube.N_SLICE2
                    * URFtoDLF[n + 1] + FRtoBR[n + 1])
                    * 2 + parity[n + 1]));
        } while (minDistPhase2[n + 1] != 0);
        return depthPhase1 + depthPhase2;
    }
}
