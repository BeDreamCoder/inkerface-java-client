package com.sign;

public class App {
    public static void main(String[] args) {
        String[] account = SignTx.CreateAccount();
        assert account != null;
        System.out.println("address: " + account[0] + ", privateKey: " + account[1]);
        String address = SignTx.AddressFromPrivateKey(account[1]);
        System.out.println("address: " + address);
        SignTx.PrvkeyCode valid = SignTx.PrivateKeyVerify("e468a4649068a17186a938ac0c55d4cc92c001bcd64ead5b6eb3135bd766d3f1");
        System.out.println("private key: " + valid);
        String result = SignTx.GetSign("token", "transfer", new String[]{"if92ac76a4b94b60614e5454aa23f983997fde209", "INK", "100"}, "test", 170754, "100000000000", account[1]);
        System.out.println("sign: 0x " + result);
    }
}
