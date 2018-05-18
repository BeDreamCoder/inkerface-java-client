package com.sign;

import com.google.protobuf.ByteString;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

public class SignTx {

    public String[] CreateAccount(String seed) {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
            String sPrivatekeyInHex = privateKeyInDec.toString(16);

            WalletFile wallet = Wallet.createLight(seed, ecKeyPair);
            String address = wallet.getAddress();

            return new String[]{"i" + address, sPrivatekeyInHex};
        } catch (NoSuchAlgorithmException | CipherException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger PublicKeyFromPrivate(String priKey) {
        return Sign.publicKeyFromPrivate(new BigInteger(Numeric.cleanHexPrefix(priKey), 16));
    }

    private byte[] concat(byte[] b1, byte[] b2) {
        byte[] result = Arrays.copyOf(b1, b1.length + b2.length);
        System.arraycopy(b2, 0, result, b1.length, b2.length);
        return result;
    }

    public String GetSign(String ccId, String fcn,String[] args,String msg,long counter,String inkLimit,String priKey) {

        BigInteger pubKey = Sign.publicKeyFromPrivate(new BigInteger(Numeric.cleanHexPrefix(priKey), 16));
        String signerAddress = 'i' + Keys.getAddress(pubKey);
        System.out.println("Recovered address: " + signerAddress);

        Chaincode.ChaincodeInput.Builder input = Chaincode.ChaincodeInput.newBuilder();
        input.addArgs(ByteString.copyFromUtf8(fcn != null ? fcn : "invoke"));
        for(String arg : args){
            input.addArgs(ByteString.copyFromUtf8(arg));
        }

        Chaincode.SignContent.Builder builder = Chaincode.SignContent.newBuilder();
        Chaincode.ChaincodeSpec ccSpec = Chaincode.ChaincodeSpec.newBuilder().setType(Chaincode.ChaincodeSpec.Type.GOLANG)
                .setChaincodeId(Chaincode.ChaincodeID.newBuilder().setName(ccId).build())
                .setInput(input.build()).build();

        Chaincode.ChaincodeInvocationSpec.Builder cciSpec = Chaincode.ChaincodeInvocationSpec.newBuilder();

        Chaincode.SenderSpec senderSpec = Chaincode.SenderSpec.newBuilder().setSender(ByteString.copyFromUtf8(signerAddress))
                .setCounter(counter).setInkLimit(ByteString.copyFromUtf8(inkLimit)).setMsg(ByteString.copyFromUtf8(msg)).build();

        builder.setChaincodeSpec(ccSpec);
        builder.setIdGenerationAlg(cciSpec.getIdGenerationAlg());
        builder.setSenderSpec(senderSpec);

        // Message to sign
        byte[] hexMessage = Hash.sha256(builder.build().toByteArray());

//        String ss = "hello world";
//        byte[] hexMessage = Hash.sha256(ss.getBytes());
//        System.out.println("hexMessage: " + Numeric.toHexString(hexMessage));

        // Use java to sign and verify the signature
        Credentials credentials = Credentials.create(priKey);
        Sign.SignatureData signMessage = Sign.signMessage(hexMessage, credentials.getEcKeyPair(), false);

//        String pubKey = "";
//        {
//            try {
//                pubKey = Sign.signedMessageToKey(hexMessage, signMessage).toString(16);
//            } catch (SignatureException e) {
//                e.printStackTrace();
//            }
//        }
//        String signerAddress = Keys.getAddress(pubKey);
//        System.out.println("Signer address : 0x" + signerAddress);

        // Now use java signature to verify from the blockchain
//        Bytes32 message = new Bytes32(hexMessage);

        byte[] v = Numeric.toBytesPadded(BigInteger.valueOf(signMessage.getV()-27), 1);
        byte[] result = concat(concat(signMessage.getR(), signMessage.getS()), v);

//        System.out.println("Result: " +  Numeric.cleanHexPrefix(Numeric.toHexString(result)));
//        System.out.println("R: " + Numeric.toHexString(signMessage.getR()));
//        System.out.println("S: " + Numeric.toHexString(signMessage.getS()));
//        System.out.println("V: " + Integer.toString(signMessage.getV()-27));

//        String address = contract.verify(message, v, r, s).get().getValue().toString(16);
//        String address2 = contract.verifyWithPrefix(message, v, r, s).get().getValue().toString(16);
//
//        System.out.println("Recovered address1 : 0x" + address);
//        System.out.println("Recovered address2 : 0x" + address2);

        return Numeric.cleanHexPrefix(Numeric.toHexString(result));
    }
}
