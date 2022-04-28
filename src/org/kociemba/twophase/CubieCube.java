package org.kociemba.twophase;

import static org.kociemba.twophase.EnumCorner.*;
import static org.kociemba.twophase.EnumEdge.*;



public class CubieCube {



EnumCorner[] cp = {URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB};

byte[] co = {0, 0, 0, 0, 0, 0, 0, 0};

EnumEdge[] ep = {UR, UF, UL, UB, DR, DF, DL, DB, FR, FL, BL, BR};

byte[] eo = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


private static final EnumCorner[] cpU = {UBR, URF, UFL, ULB, DFR, DLF, DBL, DRB};
    private static final byte[] coU = {0, 0, 0, 0, 0, 0, 0, 0};
    private static final EnumEdge[] epU = {UB, UR, UF, UL, DR, DF, DL, DB, FR, FL, BL, BR};
    private static final byte[] eoU = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static final EnumCorner[] cpR = {DFR, UFL, ULB, URF, DRB, DLF, DBL, UBR};
    private static final byte[] coR = {2, 0, 0, 1, 1, 0, 0, 2};
    private static final EnumEdge[] epR = {FR, UF, UL, UB, BR, DF, DL, DB, DR, FL, BL, UR};
    private static final byte[] eoR = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static final EnumCorner[] cpF = {UFL, DLF, ULB, UBR, URF, DFR, DBL, DRB};
    private static final byte[] coF = {1, 2, 0, 0, 2, 1, 0, 0};
    private static final EnumEdge[] epF = {UR, FL, UL, UB, DR, FR, DL, DB, UF, DF, BL, BR};
    private static final byte[] eoF = {0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0};

    private static final EnumCorner[] cpD = {URF, UFL, ULB, UBR, DLF, DBL, DRB, DFR};
    private static final byte[] coD = {0, 0, 0, 0, 0, 0, 0, 0};
    private static final EnumEdge[] epD = {UR, UF, UL, UB, DF, DL, DB, DR, FR, FL, BL, BR};
    private static final byte[] eoD = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static final EnumCorner[] cpL = {URF, ULB, DBL, UBR, DFR, UFL, DLF, DRB};
    private static final byte[] coL = {0, 1, 2, 0, 0, 2, 1, 0};
    private static final EnumEdge[] epL = {UR, UF, BL, UB, DR, DF, FL, DB, FR, UL, DL, BR};
    private static final byte[] eoL = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static final EnumCorner[] cpB = {URF, UFL, UBR, DRB, DFR, DLF, ULB, DBL};
    private static final byte[] coB = {0, 0, 1, 2, 0, 0, 2, 1};
    private static final EnumEdge[] epB = {UR, UF, UL, BR, DR, DF, DL, BL, FR, FL, UB, DB};
    private static final byte[] eoB = {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1};


    static CubieCube[] moveCube = new CubieCube[6];

    static {
        moveCube[0] = new CubieCube();
        moveCube[0].cp = cpU;
        moveCube[0].co = coU;
        moveCube[0].ep = epU;
        moveCube[0].eo = eoU;

        moveCube[1] = new CubieCube();
        moveCube[1].cp = cpR;
        moveCube[1].co = coR;
        moveCube[1].ep = epR;
        moveCube[1].eo = eoR;

        moveCube[2] = new CubieCube();
        moveCube[2].cp = cpF;
        moveCube[2].co = coF;
        moveCube[2].ep = epF;
        moveCube[2].eo = eoF;

        moveCube[3] = new CubieCube();
        moveCube[3].cp = cpD;
        moveCube[3].co = coD;
        moveCube[3].ep = epD;
        moveCube[3].eo = eoD;

        moveCube[4] = new CubieCube();
        moveCube[4].cp = cpL;
        moveCube[4].co = coL;
        moveCube[4].ep = epL;
        moveCube[4].eo = eoL;

        moveCube[5] = new CubieCube();
        moveCube[5].cp = cpB;
        moveCube[5].co = coB;
        moveCube[5].ep = epB;
        moveCube[5].eo = eoB;

    }

    CubieCube() {

    }





    static int Cnk(int n, int k) {
        int i, j, s;
        if (n < k)
            return 0;
        if (k > n / 2)
            k = n - k;
        for (s = 1, i = n, j = 1; i != n - k; i--, j++) {
            s *= i;
            s /= j;
        }
        return s;
    }

    static void rotateLeft(EnumCorner[] arr, int r)

    {
        EnumCorner temp = arr[0];
		if (r >= 0) System.arraycopy(arr, 1, arr, 0, r);
        arr[r] = temp;
    }

    static void rotateRight(EnumCorner[] arr, int r)

    {
        EnumCorner temp = arr[r];
		System.arraycopy(arr, 0, arr, 1, r);
        arr[0] = temp;
    }

    static void rotateLeft(EnumEdge[] arr, int r)

    {
        EnumEdge temp = arr[0];
		if (r >= 0) System.arraycopy(arr, 1, arr, 0, r);
        arr[r] = temp;
    }

    static void rotateRight(EnumEdge[] arr, int r)

    {
        EnumEdge temp = arr[r];
		System.arraycopy(arr, 0, arr, 1, r);
        arr[0] = temp;
    }


    FaceCube toFaceCube() {
        FaceCube fcRet = new FaceCube();
        for (EnumCorner c : EnumCorner.values()) {
            int i = c.ordinal();
            int j = cp[i].ordinal();

            byte ori = co[i];
            for (int n = 0; n < 3; n++)
                fcRet.f[FaceCube.cornerFacelet[i][(n + ori) % 3].ordinal()] = FaceCube.cornerColor[j][n];
        }
        for (EnumEdge e : EnumEdge.values()) {
            int i = e.ordinal();
            int j = ep[i].ordinal();

            byte ori = eo[i];
            for (int n = 0; n < 2; n++)
                fcRet.f[FaceCube.edgeFacelet[i][(n + ori) % 2].ordinal()] = FaceCube.edgeColor[j][n];
        }
        return fcRet;
    }











    void cornerMultiply(CubieCube b) {
        EnumCorner[] cPerm = new EnumCorner[8];
        byte[] cOri = new byte[8];
        for (EnumCorner corn : EnumCorner.values()) {
            cPerm[corn.ordinal()] = cp[b.cp[corn.ordinal()].ordinal()];

            byte oriA = co[b.cp[corn.ordinal()].ordinal()];
            byte oriB = b.co[corn.ordinal()];
            byte ori;
			if (oriA < 3 && oriB < 3)
            {
                ori = (byte) (oriA + oriB);
                if (ori >= 3)
                    ori -= 3;


            } else if (oriA < 3)

            {
                ori = (byte) (oriA + oriB);
                if (ori >= 6)
                    ori -= 3;
            } else if (oriB < 3)

            {
                ori = (byte) (oriA - oriB);
                if (ori < 3)
                    ori += 3;
            } else {
                ori = (byte) (oriA - oriB);
                if (ori < 0)
                    ori += 3;

            }
            cOri[corn.ordinal()] = ori;
        }
        for (EnumCorner c : EnumCorner.values()) {
            cp[c.ordinal()] = cPerm[c.ordinal()];
            co[c.ordinal()] = cOri[c.ordinal()];
        }
    }


    void edgeMultiply(CubieCube b) {
        EnumEdge[] ePerm = new EnumEdge[12];
        byte[] eOri = new byte[12];
        for (EnumEdge edge : EnumEdge.values()) {
            ePerm[edge.ordinal()] = ep[b.ep[edge.ordinal()].ordinal()];
            eOri[edge.ordinal()] = (byte) ((b.eo[edge.ordinal()] + eo[b.ep[edge.ordinal()].ordinal()]) % 2);
        }
        for (EnumEdge e : EnumEdge.values()) {
            ep[e.ordinal()] = ePerm[e.ordinal()];
            eo[e.ordinal()] = eOri[e.ordinal()];
        }
    }




    short getTwist() {
        short ret = 0;
        for (int i = URF.ordinal(); i < DRB.ordinal(); i++)
            ret = (short) (3 * ret + co[i]);
        return ret;
    }

    void setTwist(short twist) {
        int twistParity = 0;
        for (int i = DRB.ordinal() - 1; i >= URF.ordinal(); i--) {
            twistParity += co[i] = (byte) (twist % 3);
            twist /= 3;
        }
        co[DRB.ordinal()] = (byte) ((3 - twistParity % 3) % 3);
    }


    short getFlip() {
        short ret = 0;
        for (int i = UR.ordinal(); i < BR.ordinal(); i++)
            ret = (short) (2 * ret + eo[i]);
        return ret;
    }

    void setFlip(short flip) {
        int flipParity = 0;
        for (int i = BR.ordinal() - 1; i >= UR.ordinal(); i--) {
            flipParity += eo[i] = (byte) (flip % 2);
            flip /= 2;
        }
        eo[BR.ordinal()] = (byte) ((2 - flipParity % 2) % 2);
    }


    short cornerParity() {
        int s = 0;
        for (int i = DRB.ordinal(); i >= URF.ordinal() + 1; i--)
            for (int j = i - 1; j >= URF.ordinal(); j--)
                if (cp[j].ordinal() > cp[i].ordinal())
                    s++;
        return (short) (s % 2);
    }


    short edgeParity() {
        int s = 0;
        for (int i = BR.ordinal(); i >= UR.ordinal() + 1; i--)
            for (int j = i - 1; j >= UR.ordinal(); j--)
                if (ep[j].ordinal() > ep[i].ordinal())
                    s++;
        return (short) (s % 2);
    }


    short getFRtoBR() {
        int a = 0, x = 0;
        EnumEdge[] edge4 = new EnumEdge[4];

        for (int j = BR.ordinal(); j >= UR.ordinal(); j--)
            if (FR.ordinal() <= ep[j].ordinal() && ep[j].ordinal() <= BR.ordinal()) {
                a += Cnk(11 - j, x + 1);
                edge4[3 - x++] = ep[j];
            }

        int b = 0;
        for (int j = 3; j > 0; j--)

        {
            int k = 0;
            while (edge4[j].ordinal() != j + 8) {
                rotateLeft(edge4, j);
                k++;
            }
            b = (j + 1) * b + k;
        }
        return (short) (24 * a + b);
    }

    void setFRtoBR(short idx) {
        int x;
        EnumEdge[] sliceEdge = {FR, FL, BL, BR};
        EnumEdge[] otherEdge = {UR, UF, UL, UB, DR, DF, DL, DB};
        int b = idx % 24;
        int a = idx / 24;
        for (EnumEdge e : EnumEdge.values())
            ep[e.ordinal()] = DB;

        for (int j = 1, k; j < 4; j++)
        {
            k = b % (j + 1);
            b /= j + 1;
            while (k-- > 0)
                rotateRight(sliceEdge, j);
        }

        x = 3;
        for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
            if (a - Cnk(11 - j, x + 1) >= 0) {
                ep[j] = sliceEdge[3 - x];
                a -= Cnk(11 - j, x-- + 1);
            }
        x = 0;
        for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
            if (ep[j] == DB)
                ep[j] = otherEdge[x++];

    }


    short getURFtoDLF() {
        int a = 0, x = 0;
        EnumCorner[] corner6 = new EnumCorner[6];

        for (int j = URF.ordinal(); j <= DRB.ordinal(); j++)
            if (cp[j].ordinal() <= DLF.ordinal()) {
                a += Cnk(j, x + 1);
                corner6[x++] = cp[j];
            }

        int b = 0;
        for (int j = 5; j > 0; j--)

        {
            int k = 0;
            while (corner6[j].ordinal() != j) {
                rotateLeft(corner6, j);
                k++;
            }
            b = (j + 1) * b + k;
        }
        return (short) (720 * a + b);
    }

    void setURFtoDLF(short idx) {
        int x;
        EnumCorner[] corner6 = {URF, UFL, ULB, UBR, DFR, DLF};
        EnumCorner[] otherCorner = {DBL, DRB};
        int b = idx % 720;
        int a = idx / 720;
        for (EnumCorner c : EnumCorner.values())
            cp[c.ordinal()] = DRB;

        for (int j = 1, k; j < 6; j++)
        {
            k = b % (j + 1);
            b /= j + 1;
            while (k-- > 0)
                rotateRight(corner6, j);
        }
        x = 5;
        for (int j = DRB.ordinal(); j >= 0; j--)
            if (a - Cnk(j, x + 1) >= 0) {
                cp[j] = corner6[x];
                a -= Cnk(j, x-- + 1);
            }
        x = 0;
        for (int j = URF.ordinal(); j <= DRB.ordinal(); j++)
            if (cp[j] == DRB)
                cp[j] = otherCorner[x++];
    }


    int getURtoDF() {
        int a = 0, x = 0;
        EnumEdge[] edge6 = new EnumEdge[6];

        for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
            if (ep[j].ordinal() <= DF.ordinal()) {
                a += Cnk(j, x + 1);
                edge6[x++] = ep[j];
            }

        int b = 0;
        for (int j = 5; j > 0; j--)

        {
            int k = 0;
            while (edge6[j].ordinal() != j) {
                rotateLeft(edge6, j);
                k++;
            }
            b = (j + 1) * b + k;
        }
        return 720 * a + b;
    }

    void setURtoDF(int idx) {
        int x;
        EnumEdge[] edge6 = {UR, UF, UL, UB, DR, DF};
        EnumEdge[] otherEdge = {DL, DB, FR, FL, BL, BR};
        int b = idx % 720;
        int a = idx / 720;
        for (EnumEdge e : EnumEdge.values())
            ep[e.ordinal()] = BR;

        for (int j = 1, k; j < 6; j++)
        {
            k = b % (j + 1);
            b /= j + 1;
            while (k-- > 0)
                rotateRight(edge6, j);
        }
        x = 5;
        for (int j = BR.ordinal(); j >= 0; j--)
            if (a - Cnk(j, x + 1) >= 0) {
                ep[j] = edge6[x];
                a -= Cnk(j, x-- + 1);
            }
        x = 0;
        for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
            if (ep[j] == BR)
                ep[j] = otherEdge[x++];
    }


    public static int getURtoDF(short idx1, short idx2) {
        CubieCube a = new CubieCube();
        CubieCube b = new CubieCube();
        a.setURtoUL(idx1);
        b.setUBtoDF(idx2);
        for (int i = 0; i < 8; i++) {
            if (a.ep[i] != BR)
                if (b.ep[i] != BR)
                    return -1;
                else
                    b.ep[i] = a.ep[i];
        }
        return b.getURtoDF();
    }


    short getURtoUL() {
        int a = 0, x = 0;
        EnumEdge[] edge3 = new EnumEdge[3];

        for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
            if (ep[j].ordinal() <= UL.ordinal()) {
                a += Cnk(j, x + 1);
                edge3[x++] = ep[j];
            }

        int b = 0;
        for (int j = 2; j > 0; j--)

        {
            int k = 0;
            while (edge3[j].ordinal() != j) {
                rotateLeft(edge3, j);
                k++;
            }
            b = (j + 1) * b + k;
        }
        return (short) (6 * a + b);
    }

    void setURtoUL(short idx) {
        int x;
        EnumEdge[] edge3 = {UR, UF, UL};
        int b = idx % 6;
        int a = idx / 6;
        for (EnumEdge e : EnumEdge.values())
            ep[e.ordinal()] = BR;

        for (int j = 1, k; j < 3; j++)
        {
            k = b % (j + 1);
            b /= j + 1;
            while (k-- > 0)
                rotateRight(edge3, j);
        }
        x = 2;
        for (int j = BR.ordinal(); j >= 0; j--)
            if (a - Cnk(j, x + 1) >= 0) {
                ep[j] = edge3[x];
                a -= Cnk(j, x-- + 1);
            }
    }


    short getUBtoDF() {
        int a = 0, x = 0;
        EnumEdge[] edge3 = new EnumEdge[3];
        for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
            if (UB.ordinal() <= ep[j].ordinal() && ep[j].ordinal() <= DF.ordinal()) {
                a += Cnk(j, x + 1);
                edge3[x++] = ep[j];
            }

        int b = 0;
        for (int j = 2; j > 0; j--)

        {
            int k = 0;
            while (edge3[j].ordinal() != UB.ordinal() + j) {
                rotateLeft(edge3, j);
                k++;
            }
            b = (j + 1) * b + k;
        }
        return (short) (6 * a + b);
    }

    void setUBtoDF(short idx) {
        int x;
        EnumEdge[] edge3 = {UB, DR, DF};
        int b = idx % 6;
        int a = idx / 6;
        for (EnumEdge e : EnumEdge.values())
            ep[e.ordinal()] = BR;

        for (int j = 1, k; j < 3; j++)
        {
            k = b % (j + 1);
            b /= j + 1;
            while (k-- > 0)
                rotateRight(edge3, j);
        }
        x = 2;
        for (int j = BR.ordinal(); j >= 0; j--)
            if (a - Cnk(j, x + 1) >= 0) {
                ep[j] = edge3[x];
                a -= Cnk(j, x-- + 1);
            }
    }

	 void setURFtoDLB(int idx) {
        EnumCorner[] perm = {URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB};
        int k;
        for (int j = 1; j < 8; j++) {
            k = idx % (j + 1);
            idx /= j + 1;
            while (k-- > 0)
                rotateRight(perm, j);
        }
        int x = 7;
        for (int j = 7; j >= 0; j--)
            cp[j] = perm[x--];
    }

	 void setURtoBR(int idx) {
        EnumEdge[] perm = {UR, UF, UL, UB, DR, DF, DL, DB, FR, FL, BL, BR};
        int k;
        for (int j = 1; j < 12; j++) {
            k = idx % (j + 1);
            idx /= j + 1;
            while (k-- > 0)
                rotateRight(perm, j);
        }
        int x = 11;
        for (int j = 11; j >= 0; j--)
            ep[j] = perm[x--];
    }

    int verify() {
        int sum = 0;
        int[] edgeCount = new int[12];
        for (EnumEdge e : EnumEdge.values())
            edgeCount[ep[e.ordinal()].ordinal()]++;
        for (int i = 0; i < 12; i++)
            if (edgeCount[i] != 1)
                return -2;

        for (int i = 0; i < 12; i++)
            sum += eo[i];
        if (sum % 2 != 0)
            return -3;

        int[] cornerCount = new int[8];
        for (EnumCorner c : EnumCorner.values())
            cornerCount[cp[c.ordinal()].ordinal()]++;
        for (int i = 0; i < 8; i++)
            if (cornerCount[i] != 1)
                return -4;

        sum = 0;
        for (int i = 0; i < 8; i++)
            sum += co[i];
        if (sum % 3 != 0)
            return -5;

        if ((edgeParity() ^ cornerParity()) != 0)
            return -6;

        return 0;
    }
}
