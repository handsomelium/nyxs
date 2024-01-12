package com.liu.nyxs.socket.alipay.utils;


import java.util.BitSet;

public class BitUtil
{

    public BitUtil()
    {


    }

    public static boolean readBit(int data, int pos)
    {
        return (data & BITMASKS[pos]) != 0;
    }

    public static int setBit(int data, int pos)
    {
        return data | BITMASKS[pos];
    }

    public static int clearBit(int data, int pos)
    {
        return data & ~BITMASKS[pos];
    }

    public static int readBit(int data, int pos, int size)
    {
        int v = 0;
        int bit = 1;
        for(int i = 0; i < size; i++)
        {
            if((data & BITMASKS[pos + i]) != 0)
                v |= bit;
            bit <<= 1;
        }

        return v;
    }

    static int write(int data, int pos, int size, int v)
    {
        for(int i = 0; i < size; i++)
            if((v & BITMASKS[i]) != 0)
                data = setBit(data, pos + i);
            else
                data = clearBit(data, pos + i);

        return data;
    }

    public static BitSet toBitSet(int data)
    {
        BitSet s = new BitSet();
        for(int i = 0; i < 32; i++)
            if((data & BITMASKS[i]) != 0)
                s.set(i);

        return s;
    }

    public static int countSize(BitSet bs)
    {
        int size = 0;
        for(int i = 0; i < bs.length(); i++)
            if(bs.get(i))
                size++;

        return size;
    }

    static int BITMASKS[];

    static 
    {
        int bit = 1;
        BITMASKS = new int[32];
        for(int i = 0; i < 32; i++)
        {
            BITMASKS[i] = bit;
            bit <<= 1;
        }

    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\amber2_server\web\WEB-INF\respository\platform\base\com.ls.pf.base.utils_1.0.0.jar
	Total time: 45 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
