package com.sparrowwallet.sparrow.io;

import com.google.common.io.ByteStreams;
import com.sparrowwallet.drongo.Utils;
import com.sparrowwallet.drongo.policy.PolicyType;
import com.sparrowwallet.drongo.protocol.ScriptType;
import com.sparrowwallet.drongo.wallet.Wallet;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ElectrumTest extends IoTest {
    @Test
    public void testSinglesigImport() throws ImportException {
        Electrum electrum = new Electrum();
        Wallet wallet = electrum.importWallet(getInputStream("electrum-singlesig-wallet.json"), null);

        Assert.assertEquals(PolicyType.SINGLE, wallet.getPolicyType());
        Assert.assertEquals(ScriptType.P2SH_P2WPKH, wallet.getScriptType());
        Assert.assertEquals(1, wallet.getDefaultPolicy().getNumSignaturesRequired());
        Assert.assertEquals("pkh(trezortest)", wallet.getDefaultPolicy().getMiniscript().getScript());
        Assert.assertEquals("ab543c67", wallet.getKeystores().get(0).getKeyDerivation().getMasterFingerprint());
        Assert.assertEquals("m/49'/0'/0'", wallet.getKeystores().get(0).getKeyDerivation().getDerivationPath());
        Assert.assertEquals("xpub6FFEQVG6QR28chQzgSJ7Gjx5j5BGLkCMgZ9bc41YJCXfwYiCKUQdcwm4Fe1stvzRjosz5udMedYZFRL56AeZXCsiVmnVUysio4jkAKTukmN", wallet.getKeystores().get(0).getExtendedPublicKey().toString());
        Assert.assertTrue(wallet.isValid());
    }

    @Test
    public void testSinglesigExport() throws ImportException, ExportException, IOException {
        Electrum electrum = new Electrum();
        byte[] walletBytes = ByteStreams.toByteArray(getInputStream("electrum-singlesig-wallet.json"));
        Wallet wallet = electrum.importWallet(new ByteArrayInputStream(walletBytes), null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        electrum.exportWallet(wallet, baos);

        wallet = electrum.importWallet(new ByteArrayInputStream(baos.toByteArray()), null);
        Assert.assertTrue(wallet.isValid());
        Assert.assertEquals(PolicyType.SINGLE, wallet.getPolicyType());
        Assert.assertEquals(ScriptType.P2SH_P2WPKH, wallet.getScriptType());
        Assert.assertEquals(1, wallet.getDefaultPolicy().getNumSignaturesRequired());
        Assert.assertEquals("pkh(trezortest)", wallet.getDefaultPolicy().getMiniscript().getScript());
        Assert.assertEquals("ab543c67", wallet.getKeystores().get(0).getKeyDerivation().getMasterFingerprint());
        Assert.assertEquals("m/49'/0'/0'", wallet.getKeystores().get(0).getKeyDerivation().getDerivationPath());
        Assert.assertEquals("xpub6FFEQVG6QR28chQzgSJ7Gjx5j5BGLkCMgZ9bc41YJCXfwYiCKUQdcwm4Fe1stvzRjosz5udMedYZFRL56AeZXCsiVmnVUysio4jkAKTukmN", wallet.getKeystores().get(0).getExtendedPublicKey().toString());
    }

    @Test
    public void testMultisigImport() throws ImportException {
        Electrum electrum = new Electrum();
        Wallet wallet = electrum.importWallet(getInputStream("electrum-multisig-wallet.json"), null);

        Assert.assertEquals(PolicyType.MULTI, wallet.getPolicyType());
        Assert.assertEquals(ScriptType.P2SH_P2WSH, wallet.getScriptType());
        Assert.assertEquals(2, wallet.getDefaultPolicy().getNumSignaturesRequired());
        Assert.assertEquals("multi(2,coldcard6ba6cfd0,coldcard747b698e,coldcard7bb026be,coldcard0f056943)", wallet.getDefaultPolicy().getMiniscript().getScript());
        Assert.assertEquals("6ba6cfd0", wallet.getKeystores().get(0).getKeyDerivation().getMasterFingerprint());
        Assert.assertEquals("m/48'/1'/0'/1'", wallet.getKeystores().get(0).getKeyDerivation().getDerivationPath());
        Assert.assertEquals("xpub6FFEQVG6QR28chQzgSJ7Gjx5j5BGLkCMgZ9bc41YJCXfwYiCKUQdcwm4Fe1stvzRjosz5udMedYZFRL56AeZXCsiVmnVUysio4jkAKTukmN", wallet.getKeystores().get(0).getExtendedPublicKey().toString());
        Assert.assertEquals("7bb026be", wallet.getKeystores().get(2).getKeyDerivation().getMasterFingerprint());
        Assert.assertEquals("m/48'/1'/0'/1'", wallet.getKeystores().get(2).getKeyDerivation().getDerivationPath());
        Assert.assertTrue(wallet.isValid());
    }

    @Test
    public void testMultisigExport() throws ImportException, ExportException, IOException {
        Electrum electrum = new Electrum();
        byte[] walletBytes = ByteStreams.toByteArray(getInputStream("electrum-multisig-wallet.json"));
        Wallet wallet = electrum.importWallet(new ByteArrayInputStream(walletBytes), null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        electrum.exportWallet(wallet, baos);

        wallet = electrum.importWallet(new ByteArrayInputStream(baos.toByteArray()), null);
        Assert.assertTrue(wallet.isValid());
        Assert.assertEquals(PolicyType.MULTI, wallet.getPolicyType());
        Assert.assertEquals(ScriptType.P2SH_P2WSH, wallet.getScriptType());
        Assert.assertEquals(2, wallet.getDefaultPolicy().getNumSignaturesRequired());
        Assert.assertEquals("multi(2,coldcard6ba6cfd0,coldcard747b698e,coldcard7bb026be,coldcard0f056943)", wallet.getDefaultPolicy().getMiniscript().getScript());
        Assert.assertEquals("6ba6cfd0", wallet.getKeystores().get(0).getKeyDerivation().getMasterFingerprint());
        Assert.assertEquals("m/48'/1'/0'/1'", wallet.getKeystores().get(0).getKeyDerivation().getDerivationPath());
        Assert.assertEquals("xpub6FFEQVG6QR28chQzgSJ7Gjx5j5BGLkCMgZ9bc41YJCXfwYiCKUQdcwm4Fe1stvzRjosz5udMedYZFRL56AeZXCsiVmnVUysio4jkAKTukmN", wallet.getKeystores().get(0).getExtendedPublicKey().toString());
        Assert.assertEquals("7bb026be", wallet.getKeystores().get(2).getKeyDerivation().getMasterFingerprint());
        Assert.assertEquals("m/48'/1'/0'/1'", wallet.getKeystores().get(2).getKeyDerivation().getDerivationPath());
    }

    @Test
    public void testEncryptedImport() throws ImportException, IOException {
        Electrum electrum = new Electrum();
        byte[] walletBytes = ByteStreams.toByteArray(getInputStream("electrum-encrypted"));
        Wallet wallet = electrum.importWallet(new ByteArrayInputStream(walletBytes), "pass");

        Assert.assertTrue(wallet.isValid());
        Assert.assertEquals(PolicyType.SINGLE, wallet.getPolicyType());
        Assert.assertEquals(ScriptType.P2WPKH, wallet.getScriptType());
        Assert.assertEquals(1, wallet.getDefaultPolicy().getNumSignaturesRequired());
        Assert.assertEquals("pkh(electrumf881eac5)", wallet.getDefaultPolicy().getMiniscript().getScript());
        Assert.assertEquals("f881eac5", wallet.getKeystores().get(0).getKeyDerivation().getMasterFingerprint());
        Assert.assertEquals("m/0'", wallet.getKeystores().get(0).getKeyDerivation().getDerivationPath());
        Assert.assertEquals("xpub69iSRreMB6fu24sU8Tdxv7yYGqzPkDwPkwqUfKJTxW3p8afW7XvTewVCapuX3dQjdD197iF65WcjYaNpFbwWT3RyuZ1KJ3ToJNVWKWyAJ6f", wallet.getKeystores().get(0).getExtendedPublicKey().toString());
    }

    @Test
    public void testSinglesigSeedExport() throws ImportException, ExportException, IOException {
        Electrum electrum = new Electrum();
        byte[] walletBytes = ByteStreams.toByteArray(getInputStream("electrum-singlesig-seed-wallet.json"));
        Wallet wallet = electrum.importWallet(new ByteArrayInputStream(walletBytes), null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        electrum.exportWallet(wallet, baos);
        Assert.assertEquals("e14c40c638e2c83d1f20e5ee9cd744bc2ba1ef64fa939926f3778fc8735e891f56852f687b32bbd044f272d2831137e3eeba61fd1f285fa73dcc97d9f2be3cd1", Utils.bytesToHex(wallet.getKeystores().get(0).getSeed().getSeedBytes()));

        wallet = electrum.importWallet(new ByteArrayInputStream(baos.toByteArray()), null);
        Assert.assertTrue(wallet.isValid());
        Assert.assertEquals(PolicyType.SINGLE, wallet.getPolicyType());
        Assert.assertEquals(ScriptType.P2WPKH, wallet.getScriptType());
        Assert.assertEquals(1, wallet.getDefaultPolicy().getNumSignaturesRequired());
        Assert.assertEquals("pkh(electrum59c5474f)", wallet.getDefaultPolicy().getMiniscript().getScript());
        Assert.assertEquals("59c5474f", wallet.getKeystores().get(0).getKeyDerivation().getMasterFingerprint());
        Assert.assertEquals("m/0'", wallet.getKeystores().get(0).getKeyDerivation().getDerivationPath());
        Assert.assertEquals("xpub68YmVxWbxqjpxbUqqaPrgkBQPBSJuq6gEaL22uuytSEojtS2x5eLPN2uspUuyigtnMkoHrFSF1KwoXPwjzuaUjErUwztxfHquAwuaQhSd9J", wallet.getKeystores().get(0).getExtendedPublicKey().toString());
        Assert.assertEquals(wallet.getKeystores().get(0).getSeed().getMnemonicString(), "coach fan denial rifle frost rival join install one wasp cool antique");
        Assert.assertEquals("e14c40c638e2c83d1f20e5ee9cd744bc2ba1ef64fa939926f3778fc8735e891f56852f687b32bbd044f272d2831137e3eeba61fd1f285fa73dcc97d9f2be3cd1", Utils.bytesToHex(wallet.getKeystores().get(0).getSeed().getSeedBytes()));
    }
}