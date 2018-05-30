package com.sign;

import java.math.BigInteger;
import java.util.UUID;

public class App 
{
    public static void main( String[] args )
    {
        SignTx st = new SignTx();
        String seed = UUID.randomUUID().toString();
        String[] account = st.CreateAccount(seed);
        System.out.println("address: " + account[0] + ", privateKey: " + account[1]);
        String pubKey = st.PublicKeyFromPrivate(account[1]);
        System.out.println("publicKey: " + pubKey);
        String result = st.GetSign("token","transfer", new String[]{"if92ac76a4b94b60614e5454aa23f983997fde209", "TAB", "100"},"test",170754,"100000000000", account[1]);
        System.out.println("sign: 0x " + result);
    }
}
